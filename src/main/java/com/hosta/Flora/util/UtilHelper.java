package com.hosta.Flora.util;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.IProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;

public class UtilHelper {

	public static String[] getStringArray(JsonArray json)
	{
		List<String> list = new ArrayList<String>();
		for (JsonElement element : json)
		{
			list.add(element.getAsString());
		}
		return list.toArray(new String[list.size()]);
	}

	public static int search(String[] source, String target)
	{
		for (int i = 0; i < source.length; ++i)
		{
			if (target.equals(source[i]))
			{
				return i;
			}
		}
		return -1;
	}

	public static Ingredient getIngredient(ResourceLocation rl)
	{
		String item = "{\"item\": \"" +  rl.toString() + "\"}";
		JsonObject json = (JsonObject) new JsonParser().parse(item);
		return Ingredient.fromStacks(CraftingHelper.getItemStack((JsonObject) json, false));
	}

	public static ItemStack getItemStack(JsonObject json)
	{
		return CraftingHelper.getItemStack(json, true);
	}

	public static BlockState getBlockState(ItemStack stack)
	{
		BlockState blockstate = Block.getBlockFromItem(stack.getItem()).getDefaultState();
		CompoundNBT compoundnbt = stack.getTag();
		if (compoundnbt != null)
		{
			CompoundNBT compoundnbt1 = compoundnbt.getCompound("BlockStateTag");
			StateContainer<Block, BlockState> statecontainer = blockstate.getBlock().getStateContainer();
			for (String prop : compoundnbt1.keySet())
			{
				IProperty<?> iproperty = statecontainer.getProperty(prop);
				if (iproperty != null)
				{
					String value = compoundnbt1.get(prop).getString();
					blockstate = withProperty(blockstate, iproperty, value);
				}
			}
		}
		return blockstate;
	}

	private static <T extends Comparable<T>> BlockState withProperty(BlockState blockstate, IProperty<T> iproperty, String value)
	{
		return iproperty.parseValue(value).map((value2) -> {
			return blockstate.with(iproperty, value2);
		}).orElse(blockstate);
	}
}
