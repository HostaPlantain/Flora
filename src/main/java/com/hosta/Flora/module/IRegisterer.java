package com.hosta.Flora.module;

import net.minecraft.potion.Effect;
import net.minecraft.potion.Potion;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public interface IRegisterer {

	@OnlyIn(Dist.CLIENT)
	public default void setup(FMLClientSetupEvent event)
	{
	}

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

	public default void registerSurfacebuilders()
	{
	}

	public default void registerBiomes()
	{
	}

	public default void registerEffects()
	{
	}

	public default void registerPotions(Effect[] potions)
	{
	}

	public default void registerPotionRecipes(Potion[] potions)
	{
	}

	public default void registerRecipes()
	{
	}

	public default void registerLootModifiers()
	{
	}

	public default void registerParticleTypes()
	{
	}

	@OnlyIn(Dist.CLIENT)
	public default void registerModels()
	{
	}

}
