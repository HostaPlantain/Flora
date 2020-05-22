package com.hosta.Flora.client.registry;

import com.hosta.Flora.util.UtilHelper;

import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GrassColors;
import net.minecraft.world.ILightReader;
import net.minecraft.world.biome.BiomeColors;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ClientRegistries {

	public static final BlockColors	BLOCK_COLORS	= Minecraft.getInstance().getBlockColors();
	public static final ItemColors	ITEM_COLORS		= Minecraft.getInstance().getItemColors();

	public static final IBlockColor GRASS_COLOR = (state, worldIn, pos, tintIndex) -> {
		return worldIn != null && pos != null ? BiomeColors.getGrassColor(worldIn, pos) : GrassColors.get(0.5D, 1.0D);
	};

	public static final IItemColor COLOR_FROM_BLOCK = (stack, tintIndex) -> {
		BlockState blockstate = UtilHelper.getBlockState(stack);
		return BLOCK_COLORS.getColor(blockstate, (ILightReader) null, (BlockPos) null, tintIndex);
	};
}
