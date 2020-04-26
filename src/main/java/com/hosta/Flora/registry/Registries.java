package com.hosta.Flora.registry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.hosta.Flora.module.AbstractMod;
import com.hosta.Flora.module.AbstractModule;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Potion;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class Registries {

	private final List<AbstractModule>	MODULES	= new ArrayList<AbstractModule>();

	public void register(AbstractModule... modules)
	{
		MODULES.addAll(Arrays.asList(modules));
	}

	private final RegistryBlocks	BLOCKS	= new RegistryBlocks();
	private final AbstractRegistry	ITEMS	= new AbstractRegistry(){};
	private final AbstractRegistry	EFFECTS	= new AbstractRegistry(){};
	private final AbstractRegistry	POTIONS	= new AbstractRegistry(){};

	public <V extends IForgeRegistryEntry<V>> V register(V entry)
	{
		if (entry instanceof Block)
		{
			BLOCKS.register(entry);
		}
		else if (entry instanceof Item)
		{
			ITEMS.register(entry);
		}
		else if (entry instanceof Effect)
		{
			EFFECTS.register(entry);
		}
		else if (entry instanceof Potion)
		{
			POTIONS.register(entry);
		}
		return entry;
	}

	public void registerBlocks(IForgeRegistry<Block> registry)
	{
		MODULES.forEach(module -> module.registerBlocks());
		BLOCKS.registerFinal(registry);
	}

	public void registerItems(IForgeRegistry<Item> registry, AbstractMod mod)
	{
		MODULES.forEach(module -> module.registerItems());
		ITEMS.registerFinal(registry);
		BLOCKS.registerItems(registry, mod);
	}

	public void registerEffects(IForgeRegistry<Effect> registry)
	{
		MODULES.forEach(module -> module.registerEffects());
		EFFECTS.registerFinal(registry);
	}

	public void registerPotions(IForgeRegistry<Potion> registry)
	{
		MODULES.forEach(module -> module.registerPotions());
		POTIONS.registerFinal(registry);
	}

	@OnlyIn(Dist.CLIENT)
	public void registerModels()
	{
		MODULES.forEach(module -> module.registerModels());
		BLOCKS.registerRenders();
	}
}
