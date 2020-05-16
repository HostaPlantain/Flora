package com.hosta.Flora;

import java.util.List;
import java.util.function.Supplier;

import com.hosta.Flora.module.Module;
import com.hosta.Flora.registry.RegistryHandler;
import com.mojang.datafixers.util.Pair;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;

public interface IMod {

	public ItemGroup getTab();

	public List<Pair<String, Supplier<Module>>> getModuleList();

	public void registerModules();

	public default void registerMod()
	{
		registerModules();
		RegistryHandler.registerMod(this);
	}

	public default void registerModule(String name, Supplier<Module> supplier)
	{
		getModuleList().add(Pair.of(name, supplier));
	}

	public default String getID()
	{
		return this.getClass().getAnnotation(Mod.class).value();
	}

	public default Item.Properties getDefaultProp()
	{
		return (new Item.Properties()).group(getTab());
	}

	public default ResourceLocation getResourceLocation(String name)
	{
		return name == null ? (ResourceLocation) null : new ResourceLocation(this.getID(), name);
	}
}
