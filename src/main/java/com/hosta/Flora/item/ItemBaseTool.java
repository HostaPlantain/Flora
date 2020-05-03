package com.hosta.Flora.item;

import com.hosta.Flora.IMod;

public class ItemBaseTool extends ItemBase {

	public ItemBaseTool(int maxDamageIn, IMod mod)
	{
		this(maxDamageIn, mod.getDefaultProp());
	}

	public ItemBaseTool(int maxDamageIn, Properties property)
	{
		super(property.maxStackSize(1).defaultMaxDamage(maxDamageIn));
	}
}
