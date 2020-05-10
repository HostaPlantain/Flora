package com.hosta.Flora.loot;

import net.minecraft.world.storage.loot.conditions.ILootCondition;
import net.minecraftforge.common.loot.LootModifier;

public abstract class LootModifierBase extends LootModifier {

	protected LootModifierBase(ILootCondition[] conditionsIn)
	{
		super(conditionsIn);
	}
}
