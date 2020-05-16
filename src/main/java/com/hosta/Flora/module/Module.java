package com.hosta.Flora.module;

import java.util.ArrayList;
import java.util.List;

import com.hosta.Flora.IMod;
import com.hosta.Flora.item.ItemBase;
import com.hosta.Flora.potion.PotionBase;
import com.hosta.Flora.registry.RegistryHandler;

import net.minecraft.item.Item;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Potion;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.common.brewing.IBrewingRecipe;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.IForgeRegistryEntry;

public abstract class Module {

	protected IMod			mod;
	private RegistryHandler	registry;

	public boolean isEnable()
	{
		return true;
	}

	public static boolean isModLoaded(String modName)
	{
		return ModList.get().isLoaded(modName);
	}

	public void preInit(FMLCommonSetupEvent event)
	{
	}

	public void set(IMod mod, RegistryHandler registry)
	{
		this.mod = mod;
		this.registry = registry;
	}

	protected void registerItems(String... items)
	{
		for (String name : items)
		{
			this.register(name, this.mod.getDefaultProp());
		}
	}

	protected Item register(String name, Item.Properties property)
	{
		return this.register(name, new ItemBase(property));
	}

	protected <V extends IForgeRegistryEntry<V>> V register(String name, V entry)
	{
		return register(this.registry, name, entry);
	}

	protected static <V extends IForgeRegistryEntry<V>> V register(RegistryHandler registry, String name, V entry)
	{
		return registry.register(name, entry);
	}

	public void registerBlocks()
	{
	}

	public void registerTileEntities()
	{
	}

	public void registerItems()
	{
	}

	public void registerEffects()
	{
	}

	public static void registerPotions(RegistryHandler registry, List<Effect> list)
	{
		for (Effect effct : list)
		{
			register(registry, effct.getRegistryName().getPath(), new PotionBase(effct));
		}
	}

	public void registerPotions(List<Effect> potions)
	{
	}

	private final List<IBrewingRecipe> BREWINGS = new ArrayList<IBrewingRecipe>();

	protected void register(IBrewingRecipe recipe)
	{
		BREWINGS.add(recipe);
	}

	@SuppressWarnings("unchecked")
	public void registerRecipeAll(List<?> potions)
	{
		registerPotionRecipes((List<Potion>) potions);
		registerRecipes();
		for (IBrewingRecipe recipe : BREWINGS)
		{
			BrewingRecipeRegistry.addRecipe(recipe);
		}
	}

	public void registerPotionRecipes(List<Potion> potions)
	{
	}

	public void registerRecipes()
	{
	}

	public void registerLootModifiers()
	{
	}

	@OnlyIn(Dist.CLIENT)
	public void registerModels()
	{
	}

	protected void registerEventHandler(Object handler)
	{
		MinecraftForge.EVENT_BUS.register(handler);
	}
}