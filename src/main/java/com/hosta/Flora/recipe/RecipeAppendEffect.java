package com.hosta.Flora.recipe;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.hosta.Flora.potion.EffectInstanceBuilder;
import com.hosta.Flora.util.EffectHelper;
import com.hosta.Flora.util.FlagHelper;
import com.hosta.Flora.util.UtilHelper;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class RecipeAppendEffect extends SpecialRecipeBase {

	private final String					GROUP;
	private final ItemStack					OUTPUT;
	private final NonNullList<Ingredient>	RECIPE_ITEMS;
	private final short						FLAG;

	private static final String[]	WHITELIST	= new String[] { "all", "instant", "no_instant", "beneficial", "harmful", "neutral" };

	public RecipeAppendEffect(ResourceLocation idIn, String groupIn, ItemStack recipeOutputIn, NonNullList<Ingredient> recipeItemsIn, String[] potionCode)
	{
		this(idIn, groupIn, recipeOutputIn, recipeItemsIn, genFlag(potionCode));
	}

	public RecipeAppendEffect(ResourceLocation idIn, String groupIn, ItemStack recipeOutputIn, NonNullList<Ingredient> recipeItemsIn, short flag)
	{
		super(idIn, recipeItemsIn.size(), recipeItemsIn.size());
		this.GROUP = groupIn;
		this.OUTPUT = recipeOutputIn;
		this.RECIPE_ITEMS = recipeItemsIn;
		this.FLAG = flag;
	}

	private static short genFlag(String[] potionCode)
	{
		short flag = 0;
		for (String str : potionCode)
		{
			flag |= genFlag(WHITELIST, str, 0);
			flag |= genFlag(EffectInstanceBuilder.CODE, str, WHITELIST.length);
		}
		return flag;
	}

	private static int genFlag(String[] strs, String str, int buffer)
	{
		int i = UtilHelper.search(strs, str);
		return i >= 0 ? 1 << (i + buffer) : 0;
	}

	@Override
	public String getGroup()
	{
		return this.GROUP;
	}

	@Override
	public ItemStack getRecipeOutput()
	{
		return this.OUTPUT;
	}

	@Override
	public NonNullList<Ingredient> getIngredients()
	{
		return this.RECIPE_ITEMS;
	}

	@Override
	protected boolean match(int index, ItemStack itemIn)
	{
		return this.RECIPE_ITEMS.get(index).test(itemIn);
	}

	@Override
	public ItemStack getCraftingResult(CraftingInventory inv)
	{
		List<EffectInstance> list = new ArrayList<EffectInstance>();
		for (EffectInstance effectIn : EffectHelper.getEffects(inv))
		{
			if (whiteList(effectIn.getPotion()))
			{
				list.add(EffectInstanceBuilder.form(effectIn, (short) (FLAG >> WHITELIST.length)));
			}
		}
		ItemStack output = getRecipeOutput().copy();
		PotionUtils.appendEffects(output, list);
		return output;
	}

	private boolean whiteList(Effect effect)
	{
		if (!FlagHelper.flag(FLAG, 1))
		{
			boolean flag = false;
			for (int i = 1; i < WHITELIST.length; ++i)
			{
				if (!FlagHelper.flag(FLAG, 1 << i))
				{
					flag = whiteList(effect, i);
					if (flag)
					{
						return false;
					}
				}
			}
		}
		return true;
	}

	private boolean whiteList(Effect effect, int i)
	{
		switch (WHITELIST[i])
		{
			case "instant":
				return effect.isInstant();
			case "no_instant":
				return !effect.isInstant();
			case "beneficial":
				return effect.getEffectType() == EffectType.BENEFICIAL;
			case "harmful":
				return effect.getEffectType() == EffectType.HARMFUL;
			case "neutral":
				return effect.getEffectType() == EffectType.NEUTRAL;
			default:
				return true;
		}
	}

	@Override
	public boolean isDynamic()
	{
		return false;
	}

	public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<RecipeAppendEffect> {

		@Override
		public RecipeAppendEffect read(ResourceLocation recipeId, JsonObject json)
		{
			NonNullList<Ingredient> nonnulllist = readIngredients(JSONUtils.getJsonArray(json, "ingredients"));
			if (nonnulllist.isEmpty())
			{
				throw new JsonParseException("No ingredients for shapeless recipe");
			}
			else
			{
				String group = JSONUtils.getString(json, "group", "");
				ItemStack itemstack = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "result"));
				String[] potionCode = UtilHelper.getStringArray(JSONUtils.getJsonArray(json, "potion"));
				return new RecipeAppendEffect(recipeId, group, itemstack, nonnulllist, potionCode);
			}
		}

		private static NonNullList<Ingredient> readIngredients(JsonArray array)
		{
			NonNullList<Ingredient> nonnulllist = NonNullList.create();
			for (int i = 0; i < array.size(); ++i)
			{
				Ingredient ingredient = Ingredient.deserialize(array.get(i));
				if (!ingredient.hasNoMatchingItems())
				{
					nonnulllist.add(ingredient);
				}
			}
			return nonnulllist;
		}

		@Override
		public RecipeAppendEffect read(ResourceLocation recipeId, PacketBuffer buffer)
		{
			String group = buffer.readString(32767);
			NonNullList<Ingredient> nonnulllist = NonNullList.withSize(buffer.readVarInt(), Ingredient.EMPTY);
			for (int i = 0; i < nonnulllist.size(); ++i)
			{
				nonnulllist.set(i, Ingredient.read(buffer));
			}
			ItemStack itemstack = buffer.readItemStack();
			short flag = buffer.readShort();
			return new RecipeAppendEffect(recipeId, group, itemstack, nonnulllist, flag);
		}

		@Override
		public void write(PacketBuffer buffer, RecipeAppendEffect recipe)
		{
			buffer.writeString(recipe.GROUP);
			buffer.writeVarInt(recipe.RECIPE_ITEMS.size());
			for (Ingredient ingredient : recipe.RECIPE_ITEMS)
			{
				ingredient.write(buffer);
			}
			buffer.writeItemStack(recipe.OUTPUT);
			buffer.writeShort(recipe.FLAG);
		}
	}
}
