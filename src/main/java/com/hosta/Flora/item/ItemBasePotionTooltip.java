package com.hosta.Flora.item;

import java.util.List;

import com.hosta.Flora.IMod;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ItemBasePotionTooltip extends ItemBase implements IhasPotionList {

	public ItemBasePotionTooltip(IMod mod)
	{
		this(mod.getDefaultProp().maxStackSize(1));
	}

	public ItemBasePotionTooltip(Item.Properties property)
	{
		super(property);
	}

	@Override
	public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items)
	{
		if (this.isInGroup(group))
		{
			this.addToGroup(items, this);
		}
	}

	@Override
	public Potion[] getPotionList()
	{
		return null;
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
	{
		addPotionInformation(stack, tooltip);
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
}
