package com.hosta.Flora;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hosta.Flora.config.AbstractConfig;
import com.hosta.Flora.config.ConfigClient;
import com.hosta.Flora.config.ConfigCommon;
import com.hosta.Flora.module.Module;
import com.hosta.Flora.module.ModuleFlora;
import com.mojang.datafixers.util.Pair;

import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.common.Mod;

@Mod(Flora.ID)
public class Flora implements IMod {

	public static final String	ID		= "flora";
	public static final Logger	LOGGER	= LogManager.getLogger(Flora.ID);

	public static final ConfigClient	CONFIG_CLIENT	= new ConfigClient();
	public static final ConfigCommon	CONFIG_COMMON	= new ConfigCommon();

	public static final List<Pair<String, Supplier<Module>>> MODULES = new ArrayList<>();

	public Flora()
	{
		AbstractConfig.registerConfigs(CONFIG_CLIENT, CONFIG_COMMON);
		registerMod();
	}

	@Override
	public void registerModules()
	{
		registerModule(null, ModuleFlora::new);
	}

	@Override
	public List<Pair<String, Supplier<Module>>> getModuleList()
	{
		return MODULES;
	}

	@Override
	public ItemGroup getTab()
	{
		return ItemGroup.TOOLS;
	}
}
