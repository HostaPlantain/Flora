package com.hosta.Flora.config;

import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import net.minecraftforge.fml.config.ModConfig;

public class ConfigClient extends AbstractConfig {

	public ConfigClient()
	{
		super(ModConfig.Type.CLIENT);
	}

	public BooleanValue showItemTags;

	@Override
	protected AbstractConfig build(Builder builder)
	{
		builder.comment("Client only settings for Flora lib.").push("client");

		showItemTags = builder.comment("Show item tags on tooltip").translation("flora.configgui.showItemTags").define("showItemTags", true);

		builder.pop();
		return this;
	}
}
