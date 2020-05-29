package com.hosta.Flora.item;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;

public interface IAttributeHolder {

	public default IAttributeHolder.Builder getIAttributeBuilder(ItemStack output, EquipmentSlotType slot)
	{
		return new IAttributeHolder.Builder(getIAttribute(output, slot));
	}

	public IAttribute getIAttribute(ItemStack output, EquipmentSlotType slot);

	public static class Builder {

		private final String				ATTRIBUTE;
		private AttributeModifier.Operation	operation	= AttributeModifier.Operation.ADDITION;
		private double						amount		= 0.1D;

		public Builder(IAttribute iAttribute)
		{
			this.ATTRIBUTE = iAttribute.getName();
		}

		public void setAmount(boolean isMultiply, double amount)
		{
			this.operation = isMultiply ? AttributeModifier.Operation.MULTIPLY_BASE : AttributeModifier.Operation.ADDITION;
			this.amount = amount;
		}

		public void setAmount(double amount)
		{
			this.amount = amount;
		}

		public double getAmount()
		{
			return this.amount;
		}

		public String getAttributeName()
		{
			return ATTRIBUTE;
		}

		public AttributeModifier build(String name)
		{
			return new AttributeModifier(name, amount, operation);
		}
	}
}
