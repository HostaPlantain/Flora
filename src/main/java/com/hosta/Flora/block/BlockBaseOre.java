package com.hosta.Flora.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.OreBlock;
import net.minecraft.util.math.MathHelper;

public class BlockBaseOre extends OreBlock {

	protected final int	EP_MIN;
	protected final int	EP_MAX;

	public BlockBaseOre(Block.Properties property)
	{
		this(property, 2, 4);
	}

	public BlockBaseOre(Block.Properties property, int min, int max)
	{
		super(property);
		this.EP_MIN = min;
		this.EP_MAX = max;
	}

	protected int getExperience(Random rand)
	{
		return MathHelper.nextInt(rand, EP_MIN, EP_MAX);
	}
}
