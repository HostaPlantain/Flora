package com.hosta.Flora.config;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public abstract class AbstractConfig {

	public ForgeConfigSpec spec;
	private final ModConfig.Type TYPE;

	public AbstractConfig(ModConfig.Type type)
	{
		this.TYPE = type;
	}

	protected abstract AbstractConfig build(ForgeConfigSpec.Builder builder);

	public static void registerConfigs(AbstractConfig... configs)
	{
		for (AbstractConfig config : configs)
		{
			ModLoadingContext.get().registerConfig(config.TYPE, config.getConfigSpec());
		}
	}

	private <T extends AbstractConfig> ForgeConfigSpec getConfigSpec()
	{
		Pair<Object, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(builder -> this.build(builder));
		spec = specPair.getRight();
		return spec;
	}
}
