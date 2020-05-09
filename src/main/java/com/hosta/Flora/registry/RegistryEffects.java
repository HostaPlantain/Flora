package com.hosta.Flora.registry;

import com.hosta.Flora.potion.PotionBase;

import net.minecraft.potion.Effect;

public class RegistryEffects extends RegistryBase<Effect> {

	public RegistryEffects()
	{
		super(entry -> entry instanceof Effect);
	}

	public void registerPotions(RegistryHandler registryHandler)
	{
		for (Effect effct : LIST)
		{
			registryHandler.register(effct.getRegistryName().getPath(), new PotionBase(effct));
		}
	}
}
