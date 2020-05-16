package com.hosta.Flora.tileentity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntityType;

public class TileEntityBaseInventoryWithRender extends TileEntityBaseInventory {

	private CompoundNBT lastNBT = null;

	public TileEntityBaseInventoryWithRender(TileEntityType<?> tileEntityTypeIn, int size)
	{
		super(tileEntityTypeIn, size);
	}

	public TileEntityBaseInventoryWithRender(TileEntityType<?> tileEntityTypeIn, int size, Ingredient ingredient)
	{
		super(tileEntityTypeIn, size, ingredient);
	}

	@Override
	public CompoundNBT getUpdateTag()
	{
		return write(new CompoundNBT());
	}

	@Override
	public SUpdateTileEntityPacket getUpdatePacket()
	{
		return new SUpdateTileEntityPacket(this.pos, 0, getUpdateTag());
	}

	@Override
	public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt)
	{
		super.onDataPacket(net, pkt);
		read(pkt.getNbtCompound());
	}

	@Override
	public void markDirty()
	{
		super.markDirty();
		CompoundNBT nbt = getUpdateTag();
		if (lastNBT != nbt)
		{
			sendPacket();
			lastNBT = nbt;
		}
	}

	private void sendPacket()
	{
		if (!this.world.isRemote)
		{
			for (PlayerEntity player : getWorld().getPlayers())
			{
				ServerPlayerEntity playerMP = (ServerPlayerEntity) player;
				playerMP.connection.sendPacket(getUpdatePacket());
			}
		}
	}
}
