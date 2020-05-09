package com.hosta.Flora.item;

import com.hosta.Flora.IMod;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.GlassBottleItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext.FluidMode;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public abstract class ItemBaseBottle extends GlassBottleItem {

	public ItemBaseBottle(IMod mod)
	{
		this(mod.getDefaultProp());
	}

	public ItemBaseBottle(Properties builder)
	{
		super(builder);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
	{
		ItemStack item = playerIn.getHeldItem(handIn);
		ItemStack result = getFilledItem(worldIn, playerIn);
		if (result.isEmpty())
		{
			return ActionResult.resultPass(item);
		}
		else
		{
			worldIn.playSound(playerIn, playerIn.getPosition(), SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.PLAYERS, 1.0F, 1.0F);
			return ActionResult.resultSuccess(this.turnBottleIntoItem(item, playerIn, result));
		}
	}

	protected ItemStack getFilledItem(World worldIn, PlayerEntity playerIn)
	{
		ItemStack result = ItemStack.EMPTY;
		RayTraceResult trace = Item.rayTrace(worldIn, playerIn, FluidMode.SOURCE_ONLY);
		if (trace.getType() == RayTraceResult.Type.BLOCK)
		{
			result = getFilledItem(worldIn, ((BlockRayTraceResult) trace).getPos());
		}
		return result;
	}

	protected abstract ItemStack getFilledItem(World worldIn, BlockPos pos);
}
