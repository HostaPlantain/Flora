package com.hosta.Flora.recipe.flora;

import com.google.gson.JsonObject;
import com.hosta.Flora.recipe.RecipeBaseShapeless;
import com.hosta.Flora.recipe.RecipeBaseShapelessSerializer;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

public class RecipeAppendDurability extends RecipeBaseShapeless {

	private final ItemStack STACK;

	public RecipeAppendDurability(RecipeBaseShapelessSerializer<?>.Builder builder, ItemStack stack)
	{
		super(builder);
		this.STACK = stack;
	}

	@Override
	public NonNullList<ItemStack> getRemainingItems(CraftingInventory inv)
	{
		NonNullList<ItemStack> nonnulllist = NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);
		for (int i = 0; i < nonnulllist.size(); ++i)
		{
			ItemStack item = getRemainingItem(inv.getStackInSlot(i));
			if (!item.isEmpty())
			{
				nonnulllist.set(i, item);
			}
		}
		return nonnulllist;
	}

	private ItemStack getRemainingItem(ItemStack stack)
	{
		ItemStack remain = ItemStack.EMPTY;
		if (stack.getItem() == STACK.getItem() && stack.getDamage() + 1 < stack.getMaxDamage())
		{
			remain = stack.copy();
			remain.setDamage(stack.getDamage() + 1);
		}
		else if (stack.hasContainerItem())
		{
			remain = stack.getContainerItem();
		}
		return remain;
	}

	public static class Serializer extends RecipeBaseShapelessSerializer<RecipeAppendDurability> {

		@Override
		public RecipeAppendDurability read(ResourceLocation recipeId, JsonObject json)
		{
			RecipeBaseShapelessSerializer<?>.Builder builder = genBuilder(recipeId, json);
			ItemStack stack = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "use"));
			;
			return new RecipeAppendDurability(builder, stack);
		}

		@Override
		public RecipeAppendDurability read(ResourceLocation recipeId, PacketBuffer buffer)
		{
			RecipeBaseShapelessSerializer<?>.Builder builder = genBuilder(recipeId, buffer);
			ItemStack stack = buffer.readItemStack();
			return new RecipeAppendDurability(builder, stack);
		}

		@Override
		public void write(PacketBuffer buffer, RecipeAppendDurability recipe)
		{
			super.write(buffer, recipe);
			buffer.writeItemStack(recipe.STACK);
		}
	}
}
