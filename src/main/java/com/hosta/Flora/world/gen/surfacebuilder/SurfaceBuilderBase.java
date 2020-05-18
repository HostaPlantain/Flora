package com.hosta.Flora.world.gen.surfacebuilder;

import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public abstract class SurfaceBuilderBase extends SurfaceBuilder<SurfaceBuilderConfig> {

	public SurfaceBuilderBase()
	{
		super(SurfaceBuilderConfig::deserialize);
	}

	@Override
	public void buildSurface(Random random, IChunk chunkIn, Biome biomeIn, int x, int z, int startHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, SurfaceBuilderConfig config)
	{
		BlockPos.Mutable mutable = new BlockPos.Mutable();
		mutable.setPos(x & 15, startHeight, z & 15);
		buildSurface(random, chunkIn, mutable, defaultBlock, defaultFluid, seaLevel, config);
	}

	protected abstract void buildSurface(Random random, IChunk chunkIn, BlockPos.Mutable mutable, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, SurfaceBuilderConfig config);
}
