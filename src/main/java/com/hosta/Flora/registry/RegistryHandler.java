package com.hosta.Flora.registry;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

import com.hosta.Flora.Flora;
import com.hosta.Flora.IMod;
import com.hosta.Flora.module.IModDependency;
import com.hosta.Flora.module.Module;
import com.hosta.Flora.util.UtilHelper;
import com.mojang.datafixers.util.Pair;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.particles.ParticleType;
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
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class RegistryHandler {

	public static void registerMod(IMod mod)
	{
		new RegistryHandler(mod);
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
		try
		{
			loadConfig(ModConfig.Type.COMMON);
		}
		catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e)
		{
			e.printStackTrace();
		}

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

	@SuppressWarnings("unchecked")
	private void loadConfig(ModConfig.Type type) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, InvocationTargetException
	{
		String fileName = String.format("%s-%s.toml", this.MOD.getID(), type.extension());
		Field field = UtilHelper.getAccesable(ConfigTracker.class, "fileMap");
		ConcurrentHashMap<String, ModConfig> map = (ConcurrentHashMap<String, ModConfig>) field.get(ConfigTracker.INSTANCE);
		Method method = UtilHelper.getAccesable(ConfigTracker.class, "openConfig", ModConfig.class, Path.class);
		method.invoke(ConfigTracker.INSTANCE, map.get(fileName), FMLPaths.CONFIGDIR.get());
		Flora.LOGGER.debug(String.format("%s is loaded.", fileName));
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

	private RegistryBase<?> currentRegistry;

	private void setRegistry(RegistryBase<?> registry)
	{
		this.currentRegistry = registry;
	}

	public <V extends IForgeRegistryEntry<V>> V register(String name, V entry)
	{
		entry.setRegistryName(this.MOD.getResourceLocation(name));
		currentRegistry.register(entry);
		return entry;
	}

	private void registerFinal(IForgeRegistry<?> registry)
	{
		this.currentRegistry.registerFinal(registry);
	}

	private final RegistryBlocks BLOCKS = new RegistryBlocks();

	@SubscribeEvent
	public void registerBlocks(RegistryEvent.Register<Block> event)
	{
		setRegistry(BLOCKS);
		MODULES.forEach(module -> module.registerBlocks());
		registerFinal(event.getRegistry());
	}

	@SubscribeEvent
	public void registerTileEntities(RegistryEvent.Register<TileEntityType<?>> event)
	{
		setRegistry(new RegistryBase<TileEntityType<?>>());
		MODULES.forEach(module -> module.registerTileEntities());
		registerFinal(event.getRegistry());
	}

	@SubscribeEvent
	public void registerItems(RegistryEvent.Register<Item> event)
	{
		setRegistry(new RegistryBase<Item>());
		MODULES.forEach(module -> module.registerItems());
		BLOCKS.registerItems(this, MOD);
		registerFinal(event.getRegistry());
	}

	@SubscribeEvent
	public void registerSurfacebuilders(RegistryEvent.Register<SurfaceBuilder<?>> event)
	{
		setRegistry(new RegistryBase<SurfaceBuilder<?>>());
		MODULES.forEach(module -> module.registerSurfacebuilders());
		registerFinal(event.getRegistry());
	}

	@SubscribeEvent
	public void registerBiomes(RegistryEvent.Register<Biome> event)
	{
		setRegistry(new RegistryBase<Biome>());
		MODULES.forEach(module -> module.registerBiomes());
		registerFinal(event.getRegistry());
	}

	Effect[] EFFECTS;

	@SubscribeEvent
	public void registerEffects(RegistryEvent.Register<Effect> event)
	{
		RegistryBase<Effect> effects = new RegistryBase<Effect>();
		setRegistry(effects);
		MODULES.forEach(module -> module.registerEffects());
		registerFinal(event.getRegistry());
		EFFECTS = effects.values().toArray(new Effect[effects.size()]);
	}

	Potion[] POTIONS;

	@SubscribeEvent
	public void registerPotions(RegistryEvent.Register<Potion> event)
	{
		RegistryBase<Potion> potions = new RegistryBase<Potion>();
		setRegistry(potions);
		MODULES.forEach(module -> module.registerPotions(EFFECTS));
		Module.registerPotions(this, EFFECTS);
		registerFinal(event.getRegistry());
		POTIONS = potions.values().toArray(new Potion[potions.size()]);
	}

	@SubscribeEvent
	public void registerRecipes(RegistryEvent.Register<IRecipeSerializer<?>> event)
	{
		setRegistry(new RegistryBase<ParticleType<?>>());
		MODULES.forEach(module -> module.registerRecipeAll(POTIONS));
		registerFinal(event.getRegistry());
	}

	@SubscribeEvent
	public void registerParticleTypes(RegistryEvent.Register<ParticleType<?>> event)
	{
		setRegistry(new RegistryBase<ParticleType<?>>());
		MODULES.forEach(module -> module.registerParticleTypes());
		registerFinal(event.getRegistry());
	}

	@SubscribeEvent
	public void registerLootModifiers(RegistryEvent.Register<GlobalLootModifierSerializer<?>> event)
	{
		setRegistry(new RegistryBase<GlobalLootModifierSerializer<?>>());
		MODULES.forEach(module -> module.registerLootModifiers());
		registerFinal(event.getRegistry());
	}

	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public void registerModels(ModelRegistryEvent event)
	{
		MODULES.forEach(module -> module.registerModels());
		BLOCKS.registerRenders();
	}

	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public void setup(FMLClientSetupEvent event)
	{
		for (Module module : MODULES)
		{
			module.setup(event);
		}
	}

	@SubscribeEvent
	public void setup(FMLCommonSetupEvent event)
	{
		for (Module module : MODULES)
		{
			module.setup(event);
		}
	}
}
