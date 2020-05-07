package com.hosta.Flora.config;

import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import net.minecraftforge.fml.config.ModConfig;

public class ConfigCommon extends AbstractConfig {

	public ConfigCommon()
	{
		super(ModConfig.Type.COMMON);
	}

	public BooleanValue enableNamingRecipe;

	@Override
	protected AbstractConfig build(Builder builder)
	{
		builder.comment("Common settings for Flora lib.").push("common");

		enableNamingRecipe = builder.comment("Enable naming recipe").translation("flora.configgui.enableNamingRecipe").define("enableNamingRecipe", true);

		builder.pop();
		return this;
	}
}
