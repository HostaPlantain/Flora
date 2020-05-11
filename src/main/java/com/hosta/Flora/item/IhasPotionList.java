package com.hosta.Flora.item;

import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface IhasPotionList {

	public default void addToGroup(NonNullList<ItemStack> items, Item item)
	{
		if (getPotionList() != null)
		{
			for (Potion potion : getPotionList())
			{
				items.add(PotionUtils.addPotionToItemStack(new ItemStack(item), potion));
			}
		}
		else
		{
			items.add(new ItemStack(item));
		}
	}

	public Potion[] getPotionList();

	public static Potion[] getPotionList(List<Potion> list)
	{
		return list.toArray(new Potion[list.size()]);
	}

	@OnlyIn(Dist.CLIENT)
	public default void addPotionInformation(ItemStack stack, List<ITextComponent> tooltip)
	{
		PotionUtils.addPotionTooltip(stack, tooltip, 1.0F);
	}
}
