package com.hosta.Flora.recipe;

public abstract class RecipeBaseShapeless extends RecipeBaseNoDynamic {

	public RecipeBaseShapeless(RecipeBaseShapelessSerializer<?>.Builder builder)
	{
		super(builder);
	}

	public RecipeBaseShapeless(RecipeBaseShapelessSerializer<?>.Builder builder, int min, int max)
	{
		super(builder, min, max);
	}
}
