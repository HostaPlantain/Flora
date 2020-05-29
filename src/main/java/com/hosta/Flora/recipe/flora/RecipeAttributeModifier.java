package com.hosta.Flora.recipe.flora;

import com.google.common.collect.Multimap;
import com.hosta.Flora.item.IAttributeHolder;
import com.hosta.Flora.module.ModuleFlora;
import com.hosta.Flora.recipe.RecipeBase;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ResourceLocation;

public class RecipeAttributeModifier extends RecipeBase {

	public RecipeAttributeModifier(ResourceLocation idIn)
	{
		super(idIn, 2);
	}

	@Override
	protected boolean match(int index, ItemStack itemIn)
	{
		switch (index)
		{
			case 0:
				return itemIn.getItem() instanceof IAttributeHolder;
			case 1:
				return !itemIn.isEmpty() && !(itemIn.getItem() instanceof IAttributeHolder);
			default:
				return false;
		}
	}

	@Override
	public ItemStack getCraftingResult(CraftingInventory inv)
	{
		ItemStack output = ItemStack.EMPTY;
		IAttributeHolder itemAttributer = null;
		for (int i = 0; i < inv.getSizeInventory(); ++i)
		{
			ItemStack itemIn = inv.getStackInSlot(i);
			if (itemIn.getItem() instanceof IAttributeHolder)
			{
				itemAttributer = (IAttributeHolder) itemIn.getItem();
			}
			else if (!itemIn.isEmpty())
			{
				output = itemIn.copy();
				output.setCount(1);
			}
		}
		addAttributeModifier(output, itemAttributer);
		return output;
	}

	private static void addAttributeModifier(ItemStack output, IAttributeHolder attributer)
	{
		EquipmentSlotType slot = output.getItem() instanceof ArmorItem ? ((ArmorItem) output.getItem()).getEquipmentSlot() : output.getEquipmentSlot();
		IAttributeHolder.Builder builder = attributer.getIAttributeBuilder(output, slot);
		if (slot != null)
		{
			addAttributeModifier(output, builder, slot);
		}
		else
		{
			addAttributeModifier(output, builder, EquipmentSlotType.MAINHAND);
			addAttributeModifier(output, builder, EquipmentSlotType.OFFHAND);
		}
	}

	private static final String NAME = "crafted_attribute";

	private static void addAttributeModifier(ItemStack output, IAttributeHolder.Builder builder, EquipmentSlotType slot)
	{
		ListNBT list = new ListNBT();
		for (EquipmentSlotType slotType : EquipmentSlotType.values())
		{
			addAttributeModifier(output, builder, slotType, list, slotType == slot);
		}
		output.getOrCreateTag().put("AttributeModifiers", list);
	}

	private static void addAttributeModifier(ItemStack output, IAttributeHolder.Builder builder, EquipmentSlotType slot, ListNBT list, boolean selectedSlot)
	{
		boolean flag = !selectedSlot;
		Multimap<String, AttributeModifier> map = output.getAttributeModifiers(slot);
		for (String key : map.keySet())
		{
			boolean flag2 = selectedSlot && builder.getAttributeName().equals(key);
			for (AttributeModifier modifierOld : map.get(key))
			{
				if (flag2 && modifierOld.getName().equals(NAME))
				{
					builder.setAmount(builder.getAmount() + modifierOld.getAmount());
					list.add(getModifier(builder, slot));
					flag = true;
				}
				else
				{
					list.add(getModifier(modifierOld, key, slot));
				}
			}
		}
		if (!flag)
		{
			list.add(getModifier(builder, slot));
		}
	}

	private static CompoundNBT getModifier(IAttributeHolder.Builder builder, EquipmentSlotType slot)
	{
		return getModifier(builder.build(NAME), builder.getAttributeName(), slot);
	}

	private static CompoundNBT getModifier(AttributeModifier modifier, String attribute, EquipmentSlotType slot)
	{
		CompoundNBT compoundnbt = SharedMonsterAttributes.writeAttributeModifier(modifier);
		compoundnbt.putString("AttributeName", attribute);
		compoundnbt.putString("Slot", slot.getName());
		return compoundnbt;
	}

	@Override
	public IRecipeSerializer<?> getSerializer()
	{
		return ModuleFlora.recipeAttribute;
	}
}
