package com.hosta.Flora.recipe;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.IShapedRecipe;

public abstract class RecipeBaseShaped extends RecipeBaseNoDynamic implements IShapedRecipe<CraftingInventory> {

	protected final int	WIDTH;
	protected final int	HEIGHT;

	public RecipeBaseShaped(RecipeBaseSerializer<?>.Builder builder)
	{
		this(builder, builder.width, builder.height);
	}

	public RecipeBaseShaped(RecipeBaseSerializer<?>.Builder builder, int width, int height)
	{
		super(builder);
		this.WIDTH = width;
		this.HEIGHT = height;
	}

	@Override
	public boolean matches(CraftingInventory inv, World worldIn)
	{
		for (int i = 0; i <= inv.getWidth() - this.WIDTH; ++i)
		{
			for (int j = 0; j <= inv.getHeight() - this.HEIGHT; ++j)
			{
				if (checkMatch(inv, i, j, true) || checkMatch(inv, i, j, false))
				{
					if (checkEmpty(inv, i, j))
					{
						return true;
					}
				}
			}
		}
		return false;
	}

	private boolean checkMatch(CraftingInventory inv, int startWidth, int startHight, boolean mirror)
	{
		for (int i = startWidth; i < startWidth + this.WIDTH; ++i)
		{
			int width = mirror ? this.WIDTH - (i - startWidth) - 1 : i - startWidth;
			for (int j = startHight; j < startHight + this.HEIGHT; ++j)
			{
				int hight = j - startHight;
				ItemStack itemIn = inv.getStackInSlot(i + (j * inv.getWidth()));
				if (!match(width + (hight * this.WIDTH), itemIn))
				{
					return false;
				}
			}
		}
		return true;
	}

	private boolean checkEmpty(CraftingInventory inv, int startWidth, int startHight)
	{
		for (int i = 0; i < inv.getWidth(); ++i)
		{
			for (int j = 0; j < inv.getHeight(); ++j)
			{
				if (i < startWidth || startWidth + this.WIDTH <= i || j < startHight || startHight + this.WIDTH <= j)
				{
					if (!inv.getStackInSlot(i + (j * inv.getWidth())).isEmpty())
					{
						return false;
					}
				}
			}
		}
		return true;
	}

	@Override
	public int getRecipeWidth()
	{
		return 0;
	}

	@Override
	public int getRecipeHeight()
	{
		return 0;
	}
}
