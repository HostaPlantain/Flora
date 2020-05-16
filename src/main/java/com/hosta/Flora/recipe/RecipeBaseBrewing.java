package com.hosta.Flora.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.common.brewing.BrewingRecipe;

public class RecipeBaseBrewing extends BrewingRecipe {

	public RecipeBaseBrewing(Ingredient input, Ingredient ingredient, ItemStack output)
	{
		super(input, ingredient, output);
	}

	@Override
	public ItemStack getOutput(ItemStack input, ItemStack ingredient)
	{
		return isInput(input) && isIngredient(ingredient) ? getResultOutput(input) : ItemStack.EMPTY;
	}

	protected ItemStack getResultOutput(ItemStack input)
	{
		ItemStack itemStack = getOutput().copy();
		itemStack.setTag(input.getTag());
		return itemStack;
	}
}
