package com.hosta.Flora.recipe;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

public abstract class RecipeBaseSerializer<T extends RecipeBaseNoDynamic> extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<T> {

	public Builder genBuilder(ResourceLocation recipeId, JsonObject json)
	{
		NonNullList<Ingredient> nonnulllist;
		try
		{
			nonnulllist = getIngredientList(json);
		}
		catch (Exception e)
		{
			throw new JsonSyntaxException(recipeId.toString() + " has illegal recipe");
		}
		String group = JSONUtils.getString(json, "group", "");
		ItemStack itemstack = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "result"));
		return new Builder(recipeId, group, itemstack, nonnulllist);
	}

	protected abstract NonNullList<Ingredient> getIngredientList(JsonObject json);

	public Builder genBuilder(ResourceLocation recipeId, PacketBuffer buffer)
	{
		NonNullList<Ingredient> nonnulllist = getIngredientList(buffer);
		String group = buffer.readString(32767);
		for (int i = 0; i < nonnulllist.size(); ++i)
		{
			nonnulllist.set(i, Ingredient.read(buffer));
		}
		ItemStack itemstack = buffer.readItemStack();
		return new Builder(recipeId, group, itemstack, nonnulllist);
	}

	protected abstract NonNullList<Ingredient> getIngredientList(PacketBuffer buffer);

	@Override
	public void write(PacketBuffer buffer, T recipe)
	{
		buffer.writeString(recipe.GROUP);
		for (Ingredient ingredient : recipe.RECIPE_ITEMS)
		{
			ingredient.write(buffer);
		}
		buffer.writeItemStack(recipe.OUTPUT);
	}

	public class Builder {

		protected final ResourceLocation		RESOURCELOCATION;
		protected final String					GROUP;
		protected final ItemStack				OUTPUT;
		public final NonNullList<Ingredient>	RECIPE_ITEMS;

		protected int	width;
		protected int	height;

		public Builder(ResourceLocation idIn, String groupIn, ItemStack recipeOutputIn, NonNullList<Ingredient> recipeItemsIn)
		{
			this.RESOURCELOCATION = idIn;
			this.GROUP = groupIn;
			this.OUTPUT = recipeOutputIn;
			this.RECIPE_ITEMS = recipeItemsIn;
		}

		public void setSize(int width, int height)
		{
			this.width = width;
			this.height = height;
		}
	}
}
