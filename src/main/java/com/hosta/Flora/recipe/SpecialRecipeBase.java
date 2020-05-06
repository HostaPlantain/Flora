package com.hosta.Flora.recipe;

import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.util.ResourceLocation;

public abstract class SpecialRecipeBase extends SpecialRecipe {

	public SpecialRecipeBase(ResourceLocation idIn)
	{
		super(idIn);
	}

	@Override
	public IRecipeSerializer<?> getSerializer()
	{
		return null;
	}
}
