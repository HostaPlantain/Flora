package com.hosta.Flora.registry;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class RegistryBase<T extends IForgeRegistryEntry<T>> {

	protected final List<T>							LIST	= new ArrayList<T>();
	private final Predicate<IForgeRegistryEntry<?>>	PRE;

	public RegistryBase(Predicate<IForgeRegistryEntry<?>> pre)
	{
		this.PRE = pre;
	}

	public boolean match(IForgeRegistryEntry<?> entry)
	{
		return PRE.test(entry);
	}

	@SuppressWarnings("unchecked")
	public void register(IForgeRegistryEntry<?> entry)
	{
		LIST.add((T) entry);
	}

	public void registerFinal(IForgeRegistry<T> registry)
	{
		LIST.forEach(entry -> registry.register(entry));
	}

	public List<T> values()
	{
		return new ArrayList<T>(LIST);
	}
}
