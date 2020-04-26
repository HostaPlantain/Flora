package com.hosta.Flora.module;

import com.hosta.Flora.item.ItemBase;

import net.minecraft.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.IForgeRegistryEntry;

public abstract class AbstractModule {

	protected final AbstractMod MOD;
	public AbstractModule(AbstractMod mod)
	{
		this.MOD = mod;
	}

	public void registerBlocks() { }
	public void registerItems() { }
	public void registerEffects() { }
	public void registerPotions() { }
	@OnlyIn(Dist.CLIENT)
	public void registerModels() { }

	protected Item register(String name)
	{
		return this.register(name, new ItemBase(this.MOD));
	}

	protected <V extends IForgeRegistryEntry<V>> V register(String name, V entry)
	{
		entry.setRegistryName(this.MOD.getResourceLocation(name));
		return this.MOD.getRegistry().register(entry);
	}
}
