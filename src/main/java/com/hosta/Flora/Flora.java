package com.hosta.Flora;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hosta.Flora.config.AbstractConfig;
import com.hosta.Flora.config.ConfigClient;
import com.hosta.Flora.config.ConfigCommon;
import com.hosta.Flora.event.EventHandler;
import com.hosta.Flora.module.ModuleFlora;
import com.hosta.Flora.registry.RegistryHandler;

import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

@Mod(Flora.ID)
public class Flora implements IMod {

	public static final String	ID		= "flora";
	public static final Logger	LOGGER	= LogManager.getLogger(Flora.ID);

	public static final ConfigClient	CONFIG_CLIENT	= new ConfigClient();
	public static final ConfigCommon	CONFIG_COMMON	= new ConfigCommon();

	public Flora()
	{
		AbstractConfig.registerConfigs(CONFIG_CLIENT, CONFIG_COMMON);
		MinecraftForge.EVENT_BUS.register(new EventHandler());
		new RegistryHandler(this, new ModuleFlora());
	}

	@Override
	public ItemGroup getTab()
	{
		return ItemGroup.TOOLS;
	}
}
