package com.hosta.Flora;

import com.hosta.Flora.registry.Registries;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Potion;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = Flora.ID, bus = Bus.MOD)
public class RegistriesSample {

	private static Registries getRegistry()
	{
		return Flora.instance.getRegistry();
	}

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event)
	{
		getRegistry().registerBlocks(event.getRegistry());
	}

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event)
	{
		getRegistry().registerItems(event.getRegistry(), Flora.instance);
	}

	@SubscribeEvent
	public static void registerEffects(RegistryEvent.Register<Effect> event)
	{
		getRegistry().registerEffects(event.getRegistry());
	}

	@SubscribeEvent
	public static void registerPotions(RegistryEvent.Register<Potion> event)
	{
		getRegistry().registerPotions(event.getRegistry());
	}

	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event)
	{
		getRegistry().registerModels();
	}
}
