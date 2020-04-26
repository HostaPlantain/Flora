package com.hosta.Flora.item;

import com.hosta.Flora.module.AbstractMod;

import net.minecraft.item.DyeColor;
import net.minecraft.item.DyeItem;

public class ItemBaseColor extends DyeItem {

	public ItemBaseColor(DyeColor dyeColorIn, AbstractMod mod)
	{
		this(dyeColorIn, mod.getDefaultProp());
	}

	public ItemBaseColor(DyeColor dyeColorIn, Properties builder)
	{
		super(dyeColorIn, builder);
	}

}
