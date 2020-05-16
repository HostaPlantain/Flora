package com.hosta.Flora.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

public abstract class RecipeBaseShapelessSerializer<T extends RecipeBaseShapeless> extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<T> {

	public Builder genBuilder(ResourceLocation recipeId, JsonObject json)
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
			return new Builder(recipeId, group, itemstack, nonnulllist);
		}
	}

	public Builder genBuilder(ResourceLocation recipeId, PacketBuffer buffer)
	{
		String group = buffer.readString(32767);
		NonNullList<Ingredient> nonnulllist = NonNullList.withSize(buffer.readVarInt(), Ingredient.EMPTY);
		for (int i = 0; i < nonnulllist.size(); ++i)
		{
			nonnulllist.set(i, Ingredient.read(buffer));
		}
		ItemStack itemstack = buffer.readItemStack();
		return new Builder(recipeId, group, itemstack, nonnulllist);
	}

	@Override
	public void write(PacketBuffer buffer, T recipe)
	{
		buffer.writeString(recipe.GROUP);
		buffer.writeVarInt(recipe.RECIPE_ITEMS.size());
		for (Ingredient ingredient : recipe.RECIPE_ITEMS)
		{
			ingredient.write(buffer);
		}
		buffer.writeItemStack(recipe.OUTPUT);
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

	public class Builder {
		protected final ResourceLocation		RESOURCELOCATION;
		protected final String					GROUP;
		protected final ItemStack				OUTPUT;
		public final NonNullList<Ingredient>	RECIPE_ITEMS;

		public Builder(ResourceLocation idIn, String groupIn, ItemStack recipeOutputIn, NonNullList<Ingredient> recipeItemsIn)
		{
			this.RESOURCELOCATION = idIn;
			this.GROUP = groupIn;
			this.OUTPUT = recipeOutputIn;
			this.RECIPE_ITEMS = recipeItemsIn;
		}
	}
}
