package com.hosta.Flora.registry;

import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

@SuppressWarnings("unchecked")
public class RegistryBase<T extends IForgeRegistryEntry<T>> {

	final List<T> LIST = new ArrayList<T>();

	protected void register(IForgeRegistryEntry<?> entry)
	{
		LIST.add(get(entry));
	}

	private T get(IForgeRegistryEntry<?> entry)
	{
		try
		{
			return (T) entry;
		}
		catch (Exception e)
		{

		}
		return null;
	}

	protected void registerFinal(IForgeRegistry<?> registry)
	{
		IForgeRegistry<T> registryT = (IForgeRegistry<T>) registry;
		LIST.forEach(entry -> registryT.register(get(entry)));
	}

	protected List<T> values()
	{
		return new ArrayList<T>(LIST);
	}

	protected int size()
	{
		return LIST.size();
	}
}
