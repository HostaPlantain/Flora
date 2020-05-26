package com.hosta.Flora.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;

public abstract class RecipeBaseShapelessSerializer<T extends RecipeBaseShapeless> extends RecipeBaseSerializer<T> {

	@Override
	protected NonNullList<Ingredient> getIngredientList(JsonObject json)
	{
		NonNullList<Ingredient> nonnulllist = readIngredients(JSONUtils.getJsonArray(json, "ingredients"));
		if (nonnulllist.isEmpty())
		{
			throw new JsonParseException("No ingredients for shapeless recipe");
		}
		else
		{
			return nonnulllist;
		}
	}

	protected static NonNullList<Ingredient> readIngredients(JsonArray array)
	{
		NonNullList<Ingredient> nonnulllist = NonNullList.create();
		for (JsonElement element : array)
		{
			Ingredient ingredient = Ingredient.deserialize(element);
			if (!ingredient.hasNoMatchingItems())
			{
				nonnulllist.add(ingredient);
			}
		}
		return nonnulllist;
	}

	@Override
	protected NonNullList<Ingredient> getIngredientList(PacketBuffer buffer)
	{
		return NonNullList.withSize(buffer.readVarInt(), Ingredient.EMPTY);
	}

	@Override
	public void write(PacketBuffer buffer, T recipe)
	{
		buffer.writeVarInt(recipe.RECIPE_ITEMS.size());
		super.write(buffer, recipe);
	}
}
