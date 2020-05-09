package com.hosta.Flora.module;

import java.util.ArrayList;
import java.util.List;

import com.hosta.Flora.IMod;
import com.hosta.Flora.item.ItemBase;
import com.hosta.Flora.registry.RegistryHandler;

import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.common.brewing.IBrewingRecipe;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.minecraftforge.registries.IForgeRegistryEntry;

public abstract class AbstractModule {

	protected IMod			mod;
	private RegistryHandler	registry;

	public void set(IMod mod, RegistryHandler registry)
	{
		this.mod = mod;
		this.registry = registry;
	}

	protected Item register(String name)
	{
		return this.register(name, this.mod.getDefaultProp());
	}

	protected Item register(String name, Item.Properties property)
	{
		return this.register(name, new ItemBase(property));
	}

	protected <V extends IForgeRegistryEntry<V>> V register(String name, V entry)
	{
		return this.registry.register(name, entry);
	}

	public void registerBlocks()
	{
	}

	public void registerItems()
	{
	}

	public void registerEffects()
	{
	}

	public void registerPotions()
	{
	}

	private final List<IBrewingRecipe> BREWINGS = new ArrayList<IBrewingRecipe>();

	protected void register(IBrewingRecipe recipe)
	{
		BREWINGS.add(recipe);
	}

	@SuppressWarnings("unchecked")
	public void registerRecipeAll(List<?> list)
	{
		registerPotionRecipes((List<Potion>) list);
		registerRecipes();
		for (IBrewingRecipe recipe : BREWINGS)
		{
			BrewingRecipeRegistry.addRecipe(recipe);
		}
	}

	public void registerPotionRecipes(List<Potion> list)
	{
	}

	public void registerRecipes()
	{
	}

	@OnlyIn(Dist.CLIENT)
	public void registerModels()
	{
	}

	public void gatherData(GatherDataEvent event)
	{
	}
}
