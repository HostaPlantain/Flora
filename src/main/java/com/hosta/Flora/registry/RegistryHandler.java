package com.hosta.Flora.registry;

import java.util.ArrayList;
import java.util.List;

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
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class RegistryHandler {

	private final IMod MOD;

	private final List<AbstractModule>	MODULES	= new ArrayList<AbstractModule>();

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

	private final RegistryBlocks							BLOCKS	= new RegistryBlocks();
	private final AbstractRegistry<Item>					ITEMS	= new AbstractRegistry<Item>(entry -> entry instanceof Item);
	private final AbstractRegistry<Effect>					EFFECTS	= new AbstractRegistry<Effect>(entry -> entry instanceof Effect);
	private final AbstractRegistry<Potion>					POTIONS	= new AbstractRegistry<Potion>(entry -> entry instanceof Potion);
	private final AbstractRegistry<IRecipeSerializer<?>>	RECIPES	= new AbstractRegistry<IRecipeSerializer<?>>(entry -> entry instanceof IRecipeSerializer<?>);

	private final AbstractRegistry<?>[]	REGISTRIES	= new AbstractRegistry[] { BLOCKS, ITEMS, EFFECTS, POTIONS, RECIPES };

	public <V extends IForgeRegistryEntry<V>> V register(V entry)
	{
		for (AbstractRegistry<?> registry : REGISTRIES)
		{
			if (registry.match(entry))
			{
				registry.register(entry);
			}
		}
		return entry;
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
		ITEMS.registerFinal(event.getRegistry());
		BLOCKS.registerItems(event.getRegistry(), MOD);
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
		POTIONS.registerFinal(event.getRegistry());
	}

	@SubscribeEvent
	public void registerRecipes(RegistryEvent.Register<IRecipeSerializer<?>> event)
	{
		MODULES.forEach(module -> module.registerRecipes());
		RECIPES.registerFinal(event.getRegistry());
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
