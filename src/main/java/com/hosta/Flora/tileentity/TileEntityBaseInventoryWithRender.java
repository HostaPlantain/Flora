package com.hosta.Flora.tileentity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntityType;

public class TileEntityBaseInventoryWithRender extends TileEntityBaseInventory {

	public TileEntityBaseInventoryWithRender(TileEntityType<?> tileEntityTypeIn, int size)
	{
		super(tileEntityTypeIn, size);
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
		sendPacket();
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
