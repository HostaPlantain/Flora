package com.hosta.Flora.recipe;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.hosta.Flora.util.EffectHelper;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class RecipeAppendEffect extends SpecialRecipeBase {

	private final String					GROUP;
	private final ItemStack					OUTPUT;
	private final NonNullList<Ingredient>	RECIPE_ITEMS;

	public RecipeAppendEffect(ResourceLocation idIn, String groupIn, ItemStack recipeOutputIn, NonNullList<Ingredient> recipeItemsIn)
	{
		super(idIn);
		this.GROUP = groupIn;
		this.OUTPUT = recipeOutputIn;
		this.RECIPE_ITEMS = recipeItemsIn;
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
	public boolean matches(CraftingInventory inv, World worldIn)
	{
		short flag = 0;
		for (int i = 0; i < inv.getSizeInventory(); ++i)
		{
			ItemStack itemIn = inv.getStackInSlot(i);
			for (int j = 0; j < this.RECIPE_ITEMS.size(); ++j)
			{
				if (!flag(flag, 1 << j) && this.RECIPE_ITEMS.get(j).test(itemIn))
				{
					flag |= 1 << j;
				}
			}
		}
		return flag(flag, getSizeKey());
	}

	private static boolean flag(short flag, int key)
	{
		return (flag & key) == key;
	}

	private short getSizeKey()
	{
		short flag = 0;
		for (int i = 0; i < this.RECIPE_ITEMS.size(); ++i)
		{
			flag |= 1 << i;
		}
		return flag;
	}

	@Override
	public ItemStack getCraftingResult(CraftingInventory inv)
	{
		Map<Effect, EffectInstance> list = new HashMap<Effect, EffectInstance>();
		for (int i = 0; i < inv.getSizeInventory(); ++i)
		{
			ItemStack itemIn = inv.getStackInSlot(i);
			EffectHelper.mergeEffects(list, PotionUtils.getEffectsFromStack(itemIn));
		}

		ItemStack output = this.OUTPUT.copy();
		PotionUtils.appendEffects(output, list.values());
		return output;
	}

	@Override
	public boolean canFit(int width, int height)
	{
		return width * height >= this.RECIPE_ITEMS.size();
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
				String s = JSONUtils.getString(json, "group", "");
				ItemStack itemstack = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "result"));
				return new RecipeAppendEffect(recipeId, s, itemstack, nonnulllist);
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
			String s = buffer.readString(32767);
			NonNullList<Ingredient> nonnulllist = NonNullList.withSize(buffer.readVarInt(), Ingredient.EMPTY);
			for (int i = 0; i < nonnulllist.size(); ++i)
			{
				nonnulllist.set(i, Ingredient.read(buffer));
			}
			ItemStack itemstack = buffer.readItemStack();
			return new RecipeAppendEffect(recipeId, s, itemstack, nonnulllist);
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
		}
	}
}
