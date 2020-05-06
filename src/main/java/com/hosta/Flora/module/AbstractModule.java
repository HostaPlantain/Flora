package com.hosta.Flora.module;

import com.hosta.Flora.IMod;
import com.hosta.Flora.item.ItemBase;
import com.hosta.Flora.registry.RegistryHandler;

import net.minecraft.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.minecraftforge.registries.IForgeRegistryEntry;

public abstract class AbstractModule {

	protected IMod mod;
	private RegistryHandler registry;

	public void set(IMod mod, RegistryHandler registry)
	{
		this.mod = mod;
		this.registry = registry;
	}

	protected Item register(String name)
	{
		return this.register(name, new ItemBase(this.mod));
	}

	protected <V extends IForgeRegistryEntry<V>> V register(String name, V entry)
	{
		entry.setRegistryName(this.mod.getResourceLocation(name));
		return this.registry.register(entry);
	}

	public void registerBlocks() { }
	public void registerItems() { }
	public void registerEffects() { }
	public void registerPotions() { }
	@OnlyIn(Dist.CLIENT)
	public void registerModels() { }
	public void gatherData(GatherDataEvent event) {}
}
