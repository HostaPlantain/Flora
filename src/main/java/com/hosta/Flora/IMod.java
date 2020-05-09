package com.hosta.Flora;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;

public interface IMod {

	public ItemGroup getTab();

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
