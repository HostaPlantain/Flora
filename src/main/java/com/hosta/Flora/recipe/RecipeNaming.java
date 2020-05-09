package com.hosta.Flora.recipe;

import com.hosta.Flora.util.ColorHelper;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.DyeColor;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.NameTagItem;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class RecipeNaming extends SpecialRecipeBase {

	public RecipeNaming(ResourceLocation idIn)
	{
		super(idIn, 2, 3);
	}

	@Override
	protected boolean match(int index, ItemStack itemIn)
	{
		switch (index)
		{
			case 0:
				return itemIn.getItem() instanceof NameTagItem && itemIn.hasDisplayName();
			case 1:
				return !itemIn.isEmpty() && !(itemIn.getItem() instanceof NameTagItem) && !(itemIn.getItem() instanceof DyeItem);
			case 2:
				return itemIn.getItem() instanceof DyeItem;
			default:
				return false;
		}
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
}
