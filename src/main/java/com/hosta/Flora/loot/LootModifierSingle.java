package com.hosta.Flora.loot;

import java.util.List;

import com.google.gson.JsonObject;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.conditions.ILootCondition;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;

public class LootModifierSingle extends LootModifierBase {

	private final ItemStack	ITEMSTACK;

	protected LootModifierSingle(ILootCondition[] conditionsIn, ItemStack itemstack)
	{
		super(conditionsIn);
		ITEMSTACK = itemstack;
	}

	@Override
	protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context)
	{
		generatedLoot.add(ITEMSTACK);
		return generatedLoot;
	}

	public static class Serializer extends GlobalLootModifierSerializer<LootModifierSingle> {

		public Serializer()
		{
		}

		@Override
		public LootModifierSingle read(ResourceLocation name, JsonObject object, ILootCondition[] conditionsIn)
		{
			ItemStack stack = CraftingHelper.getItemStack((JsonObject) object.get("loot"), true);
			return new LootModifierSingle(conditionsIn, stack);
		}
	}
}
