package com.hosta.Flora.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class BlockBaseOre extends BlockBase {

	public BlockBaseOre(Block.Properties property)
	{
		super(property);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void spawnAdditionalDrops(BlockState state, World worldIn, BlockPos pos, ItemStack stack)
	{
		super.spawnAdditionalDrops(state, worldIn, pos, stack);
	}

	@Override
	public int getExpDrop(BlockState state, IWorldReader reader, BlockPos pos, int fortune, int silktouch)
	{
		return silktouch == 0 ? this.getExp(RANDOM) : 0;
	}

	protected int getExp(Random rand)
	{
		return MathHelper.nextInt(rand, 2, 4);
	}
}
