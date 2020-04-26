package com.hosta.Flora.module;

import com.hosta.Flora.registry.Registries;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public abstract class AbstractMod {

	public abstract Registries getRegistry();
	public abstract Item.Properties getDefaultProp();
	public abstract String getID();

	public ResourceLocation getResourceLocation(String name)
	{
		return name == null ? (ResourceLocation) null : new ResourceLocation(this.getID(), name);
	}
}
