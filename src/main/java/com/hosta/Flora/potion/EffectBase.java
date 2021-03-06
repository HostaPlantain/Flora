package com.hosta.Flora.potion;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

public abstract class EffectBase extends Effect {

	public EffectBase(EffectType typeIn, int liquidColorIn)
	{
		super(typeIn, liquidColorIn);
	}

	@Override
	public void performEffect(LivingEntity entityLivingBaseIn, int amplifier)
	{

	}

	@Override
	public void affectEntity(Entity source, Entity indirectSource, LivingEntity entityLivingBaseIn, int amplifier, double health)
	{
		this.performEffect(entityLivingBaseIn, amplifier);
	}

	public abstract boolean isReady(int duration, int amplifier);

	public abstract boolean isInstant();
}
