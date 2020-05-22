package com.hosta.Flora.registry;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import com.hosta.Flora.Flora;
import com.hosta.Flora.IMod;
import com.hosta.Flora.module.IModDependency;
import com.hosta.Flora.module.Module;
import com.mojang.datafixers.util.Pair;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Potion;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.config.ConfigTracker;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class RegistryHandler {

	public static final List<RegistryHandler> REGISTRY_HANDLERS = new ArrayList<>();

	public static void registerMod(IMod mod)
	{
		REGISTRY_HANDLERS.add(new RegistryHandler(mod));
	}

	private final IMod			MOD;
	private final List<Module>	MODULES	= new ArrayList<Module>();

	public RegistryHandler(IMod instance)
	{
		this.MOD = instance;
		FMLJavaModLoadingContext.get().getModEventBus().register(this);
	}

	@SubscribeEvent
	public void preLoadModules(RegistryEvent.NewRegistry event)
	{
		ConfigTracker.INSTANCE.loadConfigs(ModConfig.Type.COMMON, FMLPaths.CONFIGDIR.get());
		for (Pair<String, Supplier<Module>> pair : MOD.getModuleList())
		{
			Module module = getModule(pair.getFirst(), pair.getSecond());
			if (module != null && module.isEnable())
			{
				module.set(this.MOD, this);
				Flora.LOGGER.debug(module.getClass().getName() + " is loaded as a module for " + this.MOD.getID());
				MODULES.add(module);
			}
		}
		MOD.getModuleList().clear();
	}

	private Module getModule(String key, Supplier<Module> supplier)
	{
		Module module = null;
		if (key == null || IModDependency.isModLoaded(key))
		{
			module = supplier.get();
			if (module instanceof IModDependency)
			{
				((IModDependency) module).setMod(key);
			}
		}
		return module;
	}

	private final RegistryBlocks						BLOCKS			= new RegistryBlocks();
	private final RegistryBase<TileEntityType<?>>		TILE_ENTITIES	= new RegistryBase<TileEntityType<?>>(entry -> entry instanceof TileEntityType<?>);
	private final RegistryBase<Item>					ITEMS			= new RegistryBase<Item>(entry -> entry instanceof Item);
	private final RegistryBase<SurfaceBuilder<?>>		SURFACES		= new RegistryBase<SurfaceBuilder<?>>(entry -> entry instanceof SurfaceBuilder<?>);
	private final RegistryBase<Biome>					BIOMES			= new RegistryBase<Biome>(entry -> entry instanceof Biome);
	private final RegistryBase<Effect>					EFFECTS			= new RegistryBase<Effect>(entry -> entry instanceof Effect);
	private final RegistryBase<Potion>					POTIONS			= new RegistryBase<Potion>(entry -> entry instanceof Potion);
	private final RegistryBase<IRecipeSerializer<?>>	RECIPES			= new RegistryBase<IRecipeSerializer<?>>(entry -> entry instanceof IRecipeSerializer<?>);

	private final RegistryBase<GlobalLootModifierSerializer<?>> LOOT_MODIFIER = new RegistryBase<GlobalLootModifierSerializer<?>>(entry -> entry instanceof GlobalLootModifierSerializer<?>);

	private final RegistryBase<?>[] REGISTRIES = new RegistryBase[] { BLOCKS, TILE_ENTITIES, ITEMS, SURFACES, BIOMES, EFFECTS, POTIONS, RECIPES, LOOT_MODIFIER };

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
	public void registerTileEntities(RegistryEvent.Register<TileEntityType<?>> event)
	{
		MODULES.forEach(module -> module.registerTileEntities());
		TILE_ENTITIES.registerFinal(event.getRegistry());
	}

	@SubscribeEvent
	public void registerItems(RegistryEvent.Register<Item> event)
	{
		MODULES.forEach(module -> module.registerItems());
		BLOCKS.registerItems(this, MOD);
		ITEMS.registerFinal(event.getRegistry());
	}

	@SubscribeEvent
	public void registerSurfacebuilders(RegistryEvent.Register<SurfaceBuilder<?>> event)
	{
		MODULES.forEach(module -> module.registerSurfacebuilders());
		SURFACES.registerFinal(event.getRegistry());
	}

	@SubscribeEvent
	public void registerBiomes(RegistryEvent.Register<Biome> event)
	{
		MODULES.forEach(module -> module.registerBiomes());
		BIOMES.registerFinal(event.getRegistry());
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
		MODULES.forEach(module -> module.registerPotions(EFFECTS.LIST));
		Module.registerPotions(this, EFFECTS.LIST);
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
	public void setup(FMLCommonSetupEvent event)
	{
		for (Module module : MODULES)
		{
			module.setup(event);
		}
	}

	protected void clear()
	{
		for (RegistryBase<?> registry : REGISTRIES)
		{
			registry.LIST.clear();
		}
		MODULES.clear();
	}
}
