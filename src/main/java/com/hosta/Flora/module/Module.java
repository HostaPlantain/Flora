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
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.common.brewing.IBrewingRecipe;
import net.minecraftforge.registries.IForgeRegistryEntry;

public abstract class Module implements IRegisterer {

	protected final String NAME;

	protected IMod			mod;
	private RegistryHandler	registry;

	public Module()
	{
		this(null);
	}

	public Module(String name)
	{
		NAME = name;
	}

	public boolean isEnable()
	{
		return true;
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
		return this.registry.register(name, entry);
	}

	public static void registerPotions(RegistryHandler registry, Effect[] list)
	{
		for (Effect effct : list)
		{
			registry.register(effct.getRegistryName().getPath(), new PotionBase(effct));
		}
	}

	private final List<IBrewingRecipe> BREWINGS = new ArrayList<IBrewingRecipe>();

	protected void register(IBrewingRecipe recipe)
	{
		BREWINGS.add(recipe);
	}

	public void registerRecipeAll(Potion[] potions)
	{
		registerPotionRecipes(potions);
		registerRecipes();
		for (IBrewingRecipe recipe : BREWINGS)
		{
			BrewingRecipeRegistry.addRecipe(recipe);
		}
	}

	protected void registerEventHandler(Object handler)
	{
		MinecraftForge.EVENT_BUS.register(handler);
	}
}
