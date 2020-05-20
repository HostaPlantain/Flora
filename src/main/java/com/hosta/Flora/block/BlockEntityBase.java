package com.hosta.Flora.block;

import java.util.List;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import com.hosta.Flora.tileentity.TileEntityBaseInventory;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootContext.Builder;
import net.minecraft.world.storage.loot.LootParameters;

public class BlockEntityBase extends BlockBase implements ITileEntitySupplier {

	private final Supplier<TileEntity> SUPPLIER;

	public BlockEntityBase(Block.Properties property, Supplier<TileEntity> supplier)
	{
		super(property);
		this.SUPPLIER = supplier;
	}

	@Override
	public boolean hasTileEntity(BlockState state)
	{
		return true;
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world)
	{
		return getSupplier().get();
	}

	@Override
	public Supplier<TileEntity> getSupplier()
	{
		return SUPPLIER;
	}

	@Nullable
	@Override
	public INamedContainerProvider getContainer(BlockState state, World worldIn, BlockPos pos)
	{
		TileEntity tileentity = worldIn.getTileEntity(pos);
		return tileentity instanceof INamedContainerProvider ? (INamedContainerProvider) tileentity : null;
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<ItemStack> getDrops(BlockState state, Builder builder)
	{
		List<ItemStack> list = super.getDrops(state, builder);
		TileEntity tileEntity = builder.get(LootParameters.BLOCK_ENTITY);
		if (tileEntity != null && tileEntity instanceof TileEntityBaseInventory)
		{
			list.addAll(((TileEntityBaseInventory) tileEntity).getDrops());
		}
		return list;
	}
}
