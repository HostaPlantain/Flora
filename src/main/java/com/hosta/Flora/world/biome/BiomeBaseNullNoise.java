package com.hosta.Flora.world.biome;

import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.world.chunk.IChunk;

public class BiomeBaseNullNoise extends BiomeBase {

	public BiomeBaseNullNoise(Builder biomeBuilder)
	{
		super(biomeBuilder);
	}

	@Override
	public void buildSurface(Random random, IChunk chunkIn, int x, int z, int startHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed)
	{
		super.buildSurface(random, chunkIn, x, z, startHeight, 0, defaultBlock, defaultFluid, seaLevel, seed);
	}
}
