package com.hosta.Flora.item;

import java.util.List;

import com.hosta.Flora.IMod;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ItemBasePotionTooltip extends ItemBase {

	public ItemBasePotionTooltip(IMod mod)
	{
		super(mod);
	}

	public ItemBasePotionTooltip(Item.Properties property)
	{
		super(property);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
	{
		PotionUtils.addPotionTooltip(stack, tooltip, 1.0F);
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
}
