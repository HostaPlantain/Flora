package com.hosta.Flora.item;

import com.hosta.Flora.module.AbstractMod;

import net.minecraft.item.Item;

public class ItemBase extends Item {

	public ItemBase(AbstractMod mod)
	{
		this(mod.getDefaultProp());
	}

	public ItemBase(Item.Properties property)
	{
		super(property);
	}

}
