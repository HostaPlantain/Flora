package com.hosta.Flora.module;

import java.util.List;

import net.minecraft.potion.Effect;
import net.minecraft.potion.Potion;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public interface IRegisterer {

	public default void setup(FMLCommonSetupEvent event)
	{
	}

	public default void registerBlocks()
	{
	}

	public default void registerTileEntities()
	{
	}

	public default void registerItems()
	{
	}

	public default void registerEffects()
	{
	}

	public default void registerPotions(List<Effect> potions)
	{
	}

	public default void registerPotionRecipes(List<Potion> potions)
	{
	}

	public default void registerRecipes()
	{
	}

	public default void registerLootModifiers()
	{
	}

	@OnlyIn(Dist.CLIENT)
	public default void registerModels()
	{
	}

}
