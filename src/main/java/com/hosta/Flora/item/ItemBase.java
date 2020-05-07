package com.hosta.Flora.item;

import com.hosta.Flora.IMod;

import net.minecraft.item.Item;

public class ItemBase extends Item {

	public ItemBase(IMod mod)
	{
		this(mod.getDefaultProp());
	}

	public ItemBase(Item.Properties property)
	{
		super(property);
	}
}
