package com.hosta.Flora.recipe;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;

public abstract class RecipeBaseNoDynamic extends RecipeBase {

	protected final String					GROUP;
	protected final ItemStack				OUTPUT;
	protected final NonNullList<Ingredient>	RECIPE_ITEMS;

	public RecipeBaseNoDynamic(RecipeBaseShapelessSerializer<?>.Builder builder)
	{
		this(builder, builder.RECIPE_ITEMS.size(), builder.RECIPE_ITEMS.size());
	}

	public RecipeBaseNoDynamic(RecipeBaseShapelessSerializer<?>.Builder builder, int min, int max)
	{
		super(builder.RESOURCELOCATION, min, max);
		this.GROUP = builder.GROUP;
		this.OUTPUT = builder.OUTPUT;
		this.RECIPE_ITEMS = builder.RECIPE_ITEMS;
	}

	@Override
	public String getGroup()
	{
		return this.GROUP;
	}

	@Override
	public ItemStack getCraftingResult(CraftingInventory inv)
	{
		return getRecipeOutput().copy();
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
	public boolean isDynamic()
	{
		return false;
	}
}
