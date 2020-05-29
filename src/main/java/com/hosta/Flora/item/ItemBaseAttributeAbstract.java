package com.hosta.Flora.item;

import com.hosta.Flora.IMod;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public abstract class ItemBaseAttributeAbstract extends ItemBase implements IAttributeHolder {

	private final boolean	MULTIPLY;
	private final double	AMOUNT;

	public ItemBaseAttributeAbstract(IMod mod, boolean isMultiply, double amount)
	{
		this(mod.getDefaultProp(), isMultiply, amount);
	}

	public ItemBaseAttributeAbstract(Item.Properties property, boolean isMultiply, double amount)
	{
		super(property);
		this.MULTIPLY = isMultiply;
		this.AMOUNT = amount;
	}

	@Override
	public IAttributeHolder.Builder getIAttributeBuilder(ItemStack output, EquipmentSlotType slot)
	{
		IAttributeHolder.Builder builder = IAttributeHolder.super.getIAttributeBuilder(output, slot);
		builder.setAmount(MULTIPLY, AMOUNT);
		return builder;
	}
}
