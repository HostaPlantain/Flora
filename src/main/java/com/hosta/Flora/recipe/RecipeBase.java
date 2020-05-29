package com.hosta.Flora.recipe;

import com.hosta.Flora.util.FlagHelper;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public abstract class RecipeBase extends SpecialRecipe {

	protected final int	MIN_SIZE;
	protected final int	MAX_SIZE;

	public RecipeBase(ResourceLocation idIn, int size)
	{
		this(idIn, size, size);
	}

	public RecipeBase(ResourceLocation idIn, int min, int max)
	{
		super(idIn);
		this.MIN_SIZE = min;
		this.MAX_SIZE = max;
	}

	@Override
	public boolean matches(CraftingInventory inv, World worldIn)
	{
		short flag = 0;
		for (int i = 0; i < inv.getSizeInventory(); ++i)
		{
			ItemStack itemIn = inv.getStackInSlot(i);
			boolean flag2 = false;
			for (int j = 0; j < this.MAX_SIZE; ++j)
			{
				if (!FlagHelper.flag(flag, 1 << j) && match(j, itemIn))
				{
					flag |= 1 << j;
					flag2 = true;
					break;
				}
			}
			if (!itemIn.isEmpty() && !flag2)
			{
				return false;
			}
		}
		return FlagHelper.flag(flag, FlagHelper.getSizeKey(this.MIN_SIZE));
	}

	protected abstract boolean match(int index, ItemStack itemIn);

	@Override
	public boolean canFit(int width, int height)
	{
		return (width * height) >= this.MIN_SIZE;
	}
}
