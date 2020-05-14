package com.hosta.Flora.recipe;

import com.google.gson.JsonObject;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.item.crafting.SingleItemRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public abstract class SingleItemRecipeBase extends SingleItemRecipe {

	public SingleItemRecipeBase(IRecipeType<?> type, IRecipeSerializer<?> serializer, ResourceLocation id, String group, Ingredient ingredient, ItemStack result)
	{
		super(type, serializer, id, group, ingredient, result);
	}

	public static <T extends SingleItemRecipeBase> IRecipeType<T> register(ResourceLocation key)
	{
		return Registry.register(Registry.RECIPE_TYPE, key, new IRecipeType<T>()
		{
			public String toString()
			{
				return key.getPath();
			}
		});
	}

	public static class Serializer<T extends SingleItemRecipeBase> extends net.minecraftforge.registries.ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<T> {

		private final SingleItemRecipeBase.Serializer.IRecipeFactory<T> factory;

		public Serializer(SingleItemRecipeBase.Serializer.IRecipeFactory<T> factory)
		{
			this.factory = factory;
		}

		public T read(ResourceLocation recipeId, JsonObject json)
		{
			String s = JSONUtils.getString(json, "group", "");
			Ingredient ingredient;
			if (JSONUtils.isJsonArray(json, "ingredient"))
			{
				ingredient = Ingredient.deserialize(JSONUtils.getJsonArray(json, "ingredient"));
			}
			else
			{
				ingredient = Ingredient.deserialize(JSONUtils.getJsonObject(json, "ingredient"));
			}

			ItemStack itemstack = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "result"));
			return this.factory.create(recipeId, s, ingredient, itemstack);
		}

		public T read(ResourceLocation recipeId, PacketBuffer buffer)
		{
			String s = buffer.readString(32767);
			Ingredient ingredient = Ingredient.read(buffer);
			ItemStack itemstack = buffer.readItemStack();
			return this.factory.create(recipeId, s, ingredient, itemstack);
		}

		public void write(PacketBuffer buffer, T recipe)
		{
			buffer.writeString(recipe.group);
			recipe.ingredient.write(buffer);
			buffer.writeItemStack(recipe.result);
		}

		public interface IRecipeFactory<T extends SingleItemRecipeBase> {
			T create(ResourceLocation p_create_1_, String p_create_2_, Ingredient p_create_3_, ItemStack p_create_4_);
		}
	}
}
