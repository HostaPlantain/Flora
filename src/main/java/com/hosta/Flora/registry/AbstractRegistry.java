package com.hosta.Flora.registry;

import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

@SuppressWarnings("rawtypes")
public abstract class AbstractRegistry {

	protected final List<IForgeRegistryEntry> LIST = new ArrayList<>();

	public void register(IForgeRegistryEntry entry)
	{
		LIST.add(entry);
	}

	@SuppressWarnings("unchecked")
	public void registerFinal(IForgeRegistry registry)
	{
		LIST.forEach(entry -> registerNamed(registry, entry));
	}

	<V extends IForgeRegistryEntry<V>> void registerNamed(IForgeRegistry<V> registry, V entry)
	{
		registry.register(entry);
	}
}
