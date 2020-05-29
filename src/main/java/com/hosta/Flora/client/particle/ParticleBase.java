package com.hosta.Flora.client.particle;

import net.minecraft.client.particle.Particle;
import net.minecraft.world.World;

public abstract class ParticleBase extends Particle {

	public ParticleBase(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn)
	{
		super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
	}

	public ParticleBase(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn)
	{
		super(worldIn, xCoordIn, yCoordIn, zCoordIn);
	}
}
