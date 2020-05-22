package com.hosta.Flora.item;

import java.util.Collection;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.NonNullList;

public interface IEffectsList extends IPotionList {

	public default void addToGroup(NonNullList<ItemStack> items, Item item)
	{
		if (getEffectsList() != null)
		{
			for (Collection<EffectInstance> potion : getEffectsList())
			{
				items.add(PotionUtils.appendEffects(new ItemStack(item), potion));
			}
		}
		IPotionList.super.addToGroup(items, item);
	}

	public Collection<EffectInstance>[] getEffectsList();

	@SuppressWarnings("unchecked")
	public static <T extends EffectInstance> Collection<T>[] getEffectsList(List<Collection<T>> list)
	{
		return list.toArray(new Collection[list.size()]);
	}
}
