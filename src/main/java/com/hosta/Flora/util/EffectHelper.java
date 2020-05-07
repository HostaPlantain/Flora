package com.hosta.Flora.util;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;

public class EffectHelper {

	public static void healBadEffect(LivingEntity entity, int reduceTick, boolean reduceAmplifier)
	{
		List<EffectInstance> list = new ArrayList<EffectInstance>();
		for (EffectInstance effectOld : entity.getActivePotionEffects())
		{
			if (effectOld.getPotion().getEffectType() == EffectType.HARMFUL && effectOld.getDuration() > reduceTick)
			{
				int amplifier = (reduceAmplifier && effectOld.getAmplifier() > 0) ? effectOld.getAmplifier() - 1 : effectOld.getAmplifier();
				EffectInstance effectNew = new EffectInstance(effectOld.getPotion(), effectOld.getDuration() - reduceTick, amplifier, effectOld.isAmbient(), effectOld.doesShowParticles());
				entity.removePotionEffect(effectOld.getPotion());
				list.add(effectNew);
			}
		}
		for (EffectInstance effectNew : list)
		{
			entity.addPotionEffect(effectNew);
		}
	}

	public static boolean mergeEffect(LivingEntity entity, EffectInstance effect, int buffer)
	{
		boolean flag = !entity.isPotionActive(effect.getPotion());
		if (!entity.world.isRemote)
		{
			if (!flag)
			{
				EffectInstance active = entity.getActivePotionEffect(effect.getPotion());
				flag = EffectHelper.canMerge(effect, active, buffer);
			}
			if (flag)
			{
				entity.addPotionEffect(effect);
			}
		}
		return flag;
	}

	public static boolean canMerge(EffectInstance EffectNew, EffectInstance EffectOld, int buffer)
	{
		if (EffectNew.getAmplifier() > EffectOld.getAmplifier())
		{
			return true;
		}
		else if (EffectNew.getAmplifier() == EffectOld.getAmplifier())
		{
			return EffectNew.getDuration() > EffectOld.getDuration() + buffer;
		}
		else
		{
			return false;
		}
	}
}
