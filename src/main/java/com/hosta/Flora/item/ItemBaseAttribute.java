package com.hosta.Flora.item;

import com.hosta.Flora.IMod;

import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemBaseAttribute extends ItemBaseAttributeAbstract {

	protected final IAttribute ATTRIBUTE;

	public ItemBaseAttribute(IMod mod, IAttribute attribute, boolean isMultiply, double amount)
	{
		super(mod, isMultiply, amount);
		this.ATTRIBUTE = attribute;
	}

	public ItemBaseAttribute(Item.Properties property, IAttribute attribute, boolean isMultiply, double amount)
	{
		super(property, isMultiply, amount);
		this.ATTRIBUTE = attribute;
	}

	@Override
	public IAttribute getIAttribute(ItemStack output, EquipmentSlotType slot)
	{
		return ATTRIBUTE;
	}
}
