package com.hosta.Flora.item;

import com.hosta.Flora.IMod;

import net.minecraft.item.DyeColor;
import net.minecraft.item.DyeItem;

public class ItemBaseColor extends DyeItem {

	public ItemBaseColor(DyeColor dyeColorIn, IMod mod)
	{
		this(dyeColorIn, mod.getDefaultProp());
	}

	public ItemBaseColor(DyeColor dyeColorIn, Properties builder)
	{
		super(dyeColorIn, builder);
	}

}
