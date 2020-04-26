package com.hosta.Flora.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.material.Material;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BlockBaseFalling extends FallingBlock {

	protected final int colorDust;

	public BlockBaseFalling(int color, Material materialIn)
	{
		this(color, Block.Properties.create(materialIn));
	}

	public BlockBaseFalling(int color, Block.Properties properties)
	{
		super(properties);
		this.colorDust = color;
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public int getDustColor(BlockState state)
	{
		return colorDust;
	}
}
