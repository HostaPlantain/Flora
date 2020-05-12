package com.hosta.Flora.block;

import java.util.function.Supplier;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public interface ITileEntitySupplier {

	public Supplier<TileEntity> getSupplier();

	public static TileEntityType<TileEntity> getType(Block block)
	{
		return TileEntityType.Builder.create(((ITileEntitySupplier) block).getSupplier(), block).build(null);
	}
}
