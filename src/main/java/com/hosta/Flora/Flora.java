package com.hosta.Flora;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hosta.Flora.module.AbstractMod;
import com.hosta.Flora.registry.Registries;

import net.minecraft.item.Item;
import net.minecraft.item.Item.Properties;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.common.Mod;

@Mod(Flora.ID)
public class Flora extends AbstractMod {

	public static final String ID = "flora";
	public static Flora instance;

	public static final Logger		LOGGER	= LogManager.getLogger(Flora.ID);
	private static final Registries REGISTRY = new Registries();

	public Flora()
	{
		Flora.instance = this;
	}

	@Override
	public Registries getRegistry()
	{
		return REGISTRY;
	}

	private static final Item.Properties	PROP = new Item.Properties().group(ItemGroup.TOOLS);
	@Override
	public Properties getDefaultProp()
	{
		return PROP;
	}

	@Override
	public String getID()
	{
		return Flora.ID;
	}
}
