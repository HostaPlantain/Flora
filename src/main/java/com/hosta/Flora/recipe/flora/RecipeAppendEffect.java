package com.hosta.Flora.recipe.flora;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;
import com.hosta.Flora.potion.EffectInstanceBuilder;
import com.hosta.Flora.recipe.RecipeBaseShapeless;
import com.hosta.Flora.recipe.RecipeBaseShapelessSerializer;
import com.hosta.Flora.util.EffectHelper;
import com.hosta.Flora.util.FlagHelper;
import com.hosta.Flora.util.UtilHelper;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;

public class RecipeAppendEffect extends RecipeBaseShapeless {

	private final short FLAG;

	private static final String[] WHITELIST = new String[] { "all", "instant", "no_instant", "beneficial", "harmful", "neutral" };

	public RecipeAppendEffect(RecipeBaseShapelessSerializer<?>.Builder builder, String[] potionCode)
	{
		this(builder, genFlag(potionCode));
	}

	public RecipeAppendEffect(RecipeBaseShapelessSerializer<?>.Builder builder, short flag)
	{
		super(builder);
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

	public static class Serializer extends RecipeBaseShapelessSerializer<RecipeAppendEffect> {

		@Override
		public RecipeAppendEffect read(ResourceLocation recipeId, JsonObject json)
		{
			RecipeBaseShapelessSerializer<?>.Builder builder = genBuilder(recipeId, json);
			String[] potionCode = UtilHelper.getStringArray(JSONUtils.getJsonArray(json, "potion"));
			return new RecipeAppendEffect(builder, potionCode);
		}

		@Override
		public RecipeAppendEffect read(ResourceLocation recipeId, PacketBuffer buffer)
		{
			RecipeBaseShapelessSerializer<?>.Builder builder = genBuilder(recipeId, buffer);
			short flag = buffer.readShort();
			return new RecipeAppendEffect(builder, flag);
		}

		@Override
		public void write(PacketBuffer buffer, RecipeAppendEffect recipe)
		{
			super.write(buffer, recipe);
			buffer.writeShort(recipe.FLAG);
		}
	}
}
