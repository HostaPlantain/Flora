package com.hosta.Flora.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockBase extends Block {

	public BlockBase(Material materialIn)
	{
		this(Block.Properties.create(materialIn));
	}

	public BlockBase(Block.Properties property)
	{
		super(property);
	}

}
