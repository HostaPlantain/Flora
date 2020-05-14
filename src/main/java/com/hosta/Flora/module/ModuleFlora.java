package com.hosta.Flora.module;

import com.hosta.Flora.Flora;
import com.hosta.Flora.event.EventHandler;
import com.hosta.Flora.loot.LootModifierSingle;
import com.hosta.Flora.recipe.RecipeAppendEffect;
import com.hosta.Flora.recipe.RecipeNaming;

import net.minecraft.item.crafting.SpecialRecipeSerializer;

public class ModuleFlora extends AbstractModule {

	@Override
	public void preInit()
	{
		registerEventHandler(new EventHandler());
	}

	@Override
	public void registerRecipes()
	{
		if (Flora.CONFIG_COMMON.enableNamingRecipe.get())
		{
			register("crafting_special_naming", new SpecialRecipeSerializer<RecipeNaming>(RecipeNaming::new));
		}
		register("crafting_effect", new RecipeAppendEffect.Serializer());
	}

	@Override
	public void registerLootModifiers()
	{
		register("loot_single", new LootModifierSingle.Serializer());
	}
}
