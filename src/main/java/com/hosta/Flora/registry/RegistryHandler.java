package com.hosta.Flora.registry;

import java.util.ArrayList;
import java.util.List;

import com.hosta.Flora.Flora;
import com.hosta.Flora.IMod;
import com.hosta.Flora.module.AbstractModule;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Potion;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class RegistryHandler {

	private final IMod MOD;

	private final List<AbstractModule> MODULES = new ArrayList<AbstractModule>();

	public RegistryHandler(IMod instance, AbstractModule... modules)
	{
		this.MOD = instance;
		this.register(modules);
		RegistryHandler.registerEventHandler(this);
	}

	public static void registerEventHandler(Object handler)
	{
		FMLJavaModLoadingContext.get().getModEventBus().register(handler);
	}

	public void register(AbstractModule... modules)
	{
		for (AbstractModule module : modules)
		{
			module.set(this.MOD, this);
			MODULES.add(module);
		}
	}

	private final RegistryBlocks						BLOCKS	= new RegistryBlocks();
	private final RegistryBase<Item>					ITEMS	= new RegistryBase<Item>(entry -> entry instanceof Item);
	private final RegistryEffects						EFFECTS	= new RegistryEffects();
	private final RegistryBase<Potion>					POTIONS	= new RegistryBase<Potion>(entry -> entry instanceof Potion);
	private final RegistryBase<IRecipeSerializer<?>>	RECIPES	= new RegistryBase<IRecipeSerializer<?>>(entry -> entry instanceof IRecipeSerializer<?>);

	private final RegistryBase<GlobalLootModifierSerializer<?>> LOOT_MODIFIER = new RegistryBase<GlobalLootModifierSerializer<?>>(entry -> entry instanceof GlobalLootModifierSerializer<?>);

	private final RegistryBase<?>[] REGISTRIES = new RegistryBase[] { BLOCKS, ITEMS, EFFECTS, POTIONS, RECIPES, LOOT_MODIFIER };

	public <V extends IForgeRegistryEntry<V>> V register(String name, V entry)
	{
		entry.setRegistryName(this.MOD.getResourceLocation(name));
		return register(entry);
	}

	public <V extends IForgeRegistryEntry<V>> V register(V entry)
	{
		for (RegistryBase<?> registry : REGISTRIES)
		{
			if (registry.match(entry))
			{
				registry.register(entry);
				return entry;
			}
		}
		Flora.LOGGER.debug(String.format("%s matchs with nothing!", entry.getRegistryName().toString()), entry);
		return null;
	}

	@SubscribeEvent
	public void registerBlocks(RegistryEvent.Register<Block> event)
	{
		MODULES.forEach(module -> module.registerBlocks());
		BLOCKS.registerFinal(event.getRegistry());
	}

	@SubscribeEvent
	public void registerItems(RegistryEvent.Register<Item> event)
	{
		MODULES.forEach(module -> module.registerItems());
		BLOCKS.registerItems(this, MOD);
		ITEMS.registerFinal(event.getRegistry());
	}

	@SubscribeEvent
	public void registerEffects(RegistryEvent.Register<Effect> event)
	{
		MODULES.forEach(module -> module.registerEffects());
		EFFECTS.registerFinal(event.getRegistry());
	}

	@SubscribeEvent
	public void registerPotions(RegistryEvent.Register<Potion> event)
	{
		MODULES.forEach(module -> module.registerPotions());
		EFFECTS.registerPotions(this);
		POTIONS.registerFinal(event.getRegistry());
	}

	@SubscribeEvent
	public void registerRecipes(RegistryEvent.Register<IRecipeSerializer<?>> event)
	{
		MODULES.forEach(module -> module.registerRecipeAll(POTIONS.values()));
		RECIPES.registerFinal(event.getRegistry());
	}

	@SubscribeEvent
	public void registerLootModifiers(RegistryEvent.Register<GlobalLootModifierSerializer<?>> event)
	{
		MODULES.forEach(module -> module.registerLootModifiers());
		LOOT_MODIFIER.registerFinal(event.getRegistry());
	}

	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public void registerModels(ModelRegistryEvent event)
	{
		MODULES.forEach(module -> module.registerModels());
		BLOCKS.registerRenders();
	}

	@SubscribeEvent
	public void gatherData(GatherDataEvent event)
	{
		MODULES.forEach(module -> module.gatherData(event));
	}
}
