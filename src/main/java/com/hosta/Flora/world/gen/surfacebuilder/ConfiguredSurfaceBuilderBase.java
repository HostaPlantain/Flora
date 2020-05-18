package com.hosta.Flora.world.gen.surfacebuilder;

import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public abstract class ConfiguredSurfaceBuilderBase extends ConfiguredSurfaceBuilder<SurfaceBuilderConfig> {

	public ConfiguredSurfaceBuilderBase(SurfaceBuilderConfig config)
	{
		super(null, config);
	}

	@Override
	public void buildSurface(Random random, IChunk chunkIn, Biome biomeIn, int x, int z, int startHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed)
	{
		getBuilder().buildSurface(random, chunkIn, biomeIn, x, z, startHeight, noise, defaultBlock, defaultFluid, seaLevel, seed, this.config);
	}

	@Override
	public void setSeed(long seed)
	{
		getBuilder().setSeed(seed);
	}

	protected abstract SurfaceBuilder<SurfaceBuilderConfig> getBuilder();
}
