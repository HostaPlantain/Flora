package com.hosta.Flora.potion;

import com.hosta.Flora.util.FlagHelper;

import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;

public class EffectInstanceBuilder {

	public static final int S20 = 415;

	public Effect	effect;
	public int		duration	= 1;
	public int		amplifier	= 0;
	public boolean	ambient		= false;
	public boolean	particles	= true;

	public static final String[] CODE = new String[] { "duration20s", "amplifier0", "show_particles", "hide_particles" };

	public EffectInstanceBuilder(Effect effectIn)
	{
		this.effect = effectIn;
	}

	public EffectInstanceBuilder(EffectInstance effectIn)
	{
		this(effectIn.getPotion());
		this.duration = effectIn.getDuration();
		this.amplifier = effectIn.getAmplifier();
		this.ambient = effectIn.isAmbient();
		this.particles = effectIn.doesShowParticles();
	}

	public EffectInstance build()
	{
		return new EffectInstance(effect, duration, amplifier, ambient, particles);
	}

	public void formFromFlag(short flag)
	{
		for (int i = 0; i < CODE.length; ++i)
		{
			if (FlagHelper.flag(flag, 1 << i))
			{
				form(i);
			}
		}
	}

	private void form(int i)
	{
		switch (CODE[i])
		{
			case "duration20s":
				this.duration = S20;
				break;
			case "amplifier0":
				this.amplifier = 0;
				break;
			case "show_particles":
				this.particles = true;
				break;
			case "hide_particles":
				this.particles = false;
				break;
		}
	}

	public static EffectInstance passiveOf(int id)
	{
		return passiveOf(Effect.get(id));
	}

	public static EffectInstance passiveOf(Effect effect)
	{
		return new EffectInstance(effect, S20, 0, false, false);
	}

	public static EffectInstance form(EffectInstance effectIn, short flag)
	{
		if (flag == 0)
		{
			return effectIn;
		}
		EffectInstanceBuilder builder = new EffectInstanceBuilder(effectIn);
		builder.formFromFlag(flag);
		return builder.build();
	}
}
