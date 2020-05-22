package com.hosta.Flora.world.biome;

import net.minecraft.block.BlockState;
import net.minecraft.block.DoublePlantBlock;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;

public class BiomeBase extends Biome {

	public BiomeBase(Builder biomeBuilder)
	{
		super(biomeBuilder);
	}

	public static void setBlockState(IWorld world, BlockPos pos, BlockState state)
	{
		world.setBlockState(pos, state, 3);
		if (state.getBlock() instanceof DoublePlantBlock)
		{
			world.setBlockState(pos.up(), state.with(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER), 3);
		}
	}
}
