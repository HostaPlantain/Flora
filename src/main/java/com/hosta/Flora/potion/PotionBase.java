package com.hosta.Flora.potion;

import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Potion;

public class PotionBase extends Potion {

	public PotionBase(EffectInstance... effectIn)
	{
		super(effectIn);
	}

	public PotionBase(Effect effect)
	{
		this(new EffectInstance(effect, initDurationIn(effect)));
	}

	private static int initDurationIn(Effect effect)
	{
		if (effect instanceof EffectBase && ((EffectBase) effect).isInstant())
		{
			return 1;
		}
		return 3600;
	}
}
