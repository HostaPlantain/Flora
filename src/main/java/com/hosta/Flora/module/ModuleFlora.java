package com.hosta.Flora.module;

import com.hosta.Flora.Flora;
import com.hosta.Flora.event.EventHandlerFlora;
import com.hosta.Flora.loot.LootModifierSingle;
import com.hosta.Flora.recipe.flora.RecipeAppendDurability;
import com.hosta.Flora.recipe.flora.RecipeAppendEffect;
import com.hosta.Flora.recipe.flora.RecipeNaming;

import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class ModuleFlora extends Module {

	@Override
	public void setup(FMLCommonSetupEvent event)
	{
		registerEventHandler(new EventHandlerFlora());
	}

	@Override
	public void registerRecipes()
	{
		if (Flora.CONFIG_COMMON.enableNamingRecipe.get())
		{
			register("crafting_special_naming", new SpecialRecipeSerializer<RecipeNaming>(RecipeNaming::new));
		}
		register("crafting_effect", new RecipeAppendEffect.Serializer());
		register("crafting_durability", new RecipeAppendDurability.Serializer());
	}

	@Override
	public void registerLootModifiers()
	{
		register("loot_single", new LootModifierSingle.Serializer());
	}
}
