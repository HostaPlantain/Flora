package com.hosta.Flora.client.particle;

import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class ParticleBaseSpriteTextured extends SpriteTexturedParticle {

	public ParticleBaseSpriteTextured(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn)
	{
		super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
	}

	public ParticleBaseSpriteTextured(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn)
	{
		super(worldIn, xCoordIn, yCoordIn, zCoordIn);
	}

	@Override
	public void tick()
	{
		this.onGround = (this.prevPosY == this.posY && this.age > 10);

		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;

		if (++this.age >= this.maxAge)
		{
			this.setExpired();
		}
	}
}
