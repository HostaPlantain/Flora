package com.hosta.Flora.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;

public class Helper {

	public static void healBadEffect(LivingEntity entity, int reduceTick, boolean reduceAmplifier)
	{
		Collection<EffectInstance> collection = entity.getActivePotionEffects();
		List<EffectInstance> list = new ArrayList<EffectInstance>();

		for (EffectInstance effectOld : collection)
		{
			if (effectOld.getPotion().getEffectType() == EffectType.HARMFUL && effectOld.getDuration() > reduceTick)
			{
				int amplifier = (reduceAmplifier && effectOld.getAmplifier() != 0) ? effectOld.getAmplifier() - 1 : effectOld.getAmplifier();
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

	public static boolean mergeEffect(PlayerEntity player, EffectInstance effect, int buffer)
	{
		boolean flag = !player.isPotionActive(effect.getPotion());
		if (!player.world.isRemote)
		{
			if (!flag)
			{
				EffectInstance active = player.getActivePotionEffect(effect.getPotion());
				flag = Helper.canMerge(effect, active, buffer);
			}
			if (flag)
			{
				player.addPotionEffect(effect);
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
