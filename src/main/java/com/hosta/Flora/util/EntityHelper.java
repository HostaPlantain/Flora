package com.hosta.Flora.util;

import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;

public class EntityHelper {

	public static void splitItem(ItemEntity entityItem, int n)
	{
		ItemStack itemIn = entityItem.getItem();
		itemIn.split(n);
		if (!itemIn.isEmpty())
		{
			entityItem.setItem(itemIn);
		}
		else
		{
			entityItem.remove();
		}
	}
}
