package com.hosta.Flora.recipe;

import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.hosta.Flora.util.UtilHelper;

import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

public abstract class RecipeBaseShapedSerializer<T extends RecipeBaseShaped> extends RecipeBaseSerializer<T> {

	private int	width;
	private int	height;

	@Override
	public RecipeBaseSerializer<T>.Builder genBuilder(ResourceLocation recipeId, JsonObject json)
	{
		RecipeBaseSerializer<T>.Builder builder = super.genBuilder(recipeId, json);
		builder.setSize(width, height);
		return builder;
	}

	@Override
	protected NonNullList<Ingredient> getIngredientList(JsonObject json)
	{
		Map<String, Ingredient> map = deserializeKey(JSONUtils.getJsonObject(json, "key"));
		String[] astring = shrink(UtilHelper.getStringArray(JSONUtils.getJsonArray(json, "pattern")));
		width = astring[0].length();
		height = astring.length;
		return deserializeIngredients(astring, map, width, height);
	}

	@Override
	public RecipeBaseSerializer<T>.Builder genBuilder(ResourceLocation recipeId, PacketBuffer buffer)
	{
		RecipeBaseSerializer<T>.Builder builder = super.genBuilder(recipeId, buffer);
		builder.setSize(width, height);
		return builder;
	}

	@Override
	protected NonNullList<Ingredient> getIngredientList(PacketBuffer buffer)
	{
		width = buffer.readVarInt();
		height = buffer.readVarInt();
		return NonNullList.withSize(width * height, Ingredient.EMPTY);
	}

	@Override
	public void write(PacketBuffer buffer, T recipe)
	{
		buffer.writeVarInt(recipe.WIDTH);
		buffer.writeVarInt(recipe.HEIGHT);
		super.write(buffer, recipe);
	}

	private static Map<String, Ingredient> deserializeKey(JsonObject json)
	{
		Map<String, Ingredient> map = Maps.newHashMap();
		for (Entry<String, JsonElement> entry : json.entrySet())
		{
			map.put(entry.getKey(), Ingredient.deserialize(entry.getValue()));
		}
		map.put(" ", Ingredient.EMPTY);
		return map;
	}

	// To Do
	private static String[] shrink(String... toShrink)
	{
		int i = Integer.MAX_VALUE;
		int j = 0;
		int k = 0;
		int l = 0;
		for (int i1 = 0; i1 < toShrink.length; ++i1)
		{
			String s = toShrink[i1];
			i = Math.min(i, firstNonSpace(s));
			int j1 = lastNonSpace(s);
			j = Math.max(j, j1);
			if (j1 < 0)
			{
				if (k == i1)
				{
					++k;
				}
				++l;
			}
			else
			{
				l = 0;
			}
		}
		if (toShrink.length == l)
		{
			return new String[0];
		}
		else
		{
			String[] astring = new String[toShrink.length - l - k];
			for (int k1 = 0; k1 < astring.length; ++k1)
			{
				astring[k1] = toShrink[k1 + k].substring(i, j + 1);
			}
			return astring;
		}
	}

	private static int firstNonSpace(String str)
	{
		int i;
		for (i = 0; i < str.length() && str.charAt(i) == ' '; ++i)
		{
			;
		}
		return i;
	}

	private static int lastNonSpace(String str)
	{
		int i;
		for (i = str.length() - 1; i >= 0 && str.charAt(i) == ' '; --i)
		{
			;
		}
		return i;
	}

	private static NonNullList<Ingredient> deserializeIngredients(String[] pattern, Map<String, Ingredient> keys, int patternWidth, int patternHeight)
	{
		NonNullList<Ingredient> nonnulllist = NonNullList.withSize(patternWidth * patternHeight, Ingredient.EMPTY);
		for (int i = 0; i < patternHeight; ++i)
		{
			for (int j = 0; j < patternWidth; ++j)
			{
				String s = pattern[i].substring(j, j + 1);
				Ingredient ingredient = keys.get(s);
				nonnulllist.set(j + patternWidth * i, ingredient);
			}
		}
		return nonnulllist;
	}
}
