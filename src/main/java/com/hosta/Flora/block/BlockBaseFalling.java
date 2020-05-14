package com.hosta.Flora.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;

public class BlockBaseFalling extends FallingBlock {

	protected final int colorDust;

	public BlockBaseFalling(int color, Block.Properties properties)
	{
		super(properties);
		this.colorDust = color;
	}

	@Override
	public ToolType getHarvestTool(BlockState state)
	{
		return ToolType.SHOVEL;
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public int getDustColor(BlockState state)
	{
		return colorDust;
	}
}
