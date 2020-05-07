package com.hosta.Flora.recipe;

import com.hosta.Flora.util.ColorHelper;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.DyeColor;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.NameTagItem;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

public class RecipeNaming extends SpecialRecipeBase {

	public RecipeNaming(ResourceLocation idIn)
	{
		super(idIn);
	}

	@Override
	public boolean matches(CraftingInventory inv, World worldIn)
	{
		byte flag = 0b000;
		for (int i = 0; i < inv.getSizeInventory(); ++i)
		{
			ItemStack itemIn = inv.getStackInSlot(i);
			if (!flag(flag, 0b001) && itemIn.getItem() instanceof NameTagItem && itemIn.hasDisplayName())
			{
				flag |= 0b001;
			}
			else if (!flag(flag, 0b100) && itemIn.getItem() instanceof DyeItem)
			{
				flag |= 0b100;
			}
			else if (!flag(flag, 0b010) && !itemIn.isEmpty() && !(itemIn.getItem() instanceof NameTagItem) && !(itemIn.getItem() instanceof DyeItem))
			{
				flag |= 0b010;
			}
			else if (!itemIn.isEmpty())
			{
				return false;
			}
		}
		return flag(flag, 0b011);
	}

	private static boolean flag(byte flag, int key)
	{
		return (flag & key) == key;
	}

	@Override
	public ItemStack getCraftingResult(CraftingInventory inv)
	{
		ITextComponent name = null;
		ColorHelper color = ColorHelper.WHITE;
		ItemStack output = ItemStack.EMPTY;
		for (int i = 0; i < inv.getSizeInventory(); ++i)
		{
			ItemStack itemIn = inv.getStackInSlot(i);
			if (itemIn.getItem() instanceof NameTagItem)
			{
				name = itemIn.getDisplayName();
			}
			else if (itemIn.getItem() instanceof DyeItem)
			{
				color = ColorHelper.getColor(DyeColor.getColor(itemIn));
			}
			else if (!itemIn.isEmpty())
			{
				output = itemIn.copy();
				output.setCount(1);
			}
		}
		setDisplayName(output, name, color);
		return output;
	}

	private void setDisplayName(ItemStack output, ITextComponent name, ColorHelper color)
	{
		name.getStyle().setItalic(false);
		name.getStyle().setColor(color.getTextFormatting());
		output.setDisplayName(name);
	}

	@Override
	public boolean canFit(int width, int height)
	{
		return (width * height) >= 2;
	}
}
