package com.hosta.Flora.tileentity;

import java.util.Collection;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.ItemHandlerHelper;

public class TileEntityInventory extends TileEntityBase implements ISidedInventory {

	private final int[]					DEFAULT_SLOTS;
	protected NonNullList<ItemStack>	items;
	protected final int					SIZE;
	private int							limit	= 64;

	public TileEntityInventory(TileEntityType<?> tileEntityTypeIn, int size)
	{
		super(tileEntityTypeIn);
		this.SIZE = size;
		this.DEFAULT_SLOTS = new int[SIZE];
		for (int i = 0; i < DEFAULT_SLOTS.length; ++i)
		{
			DEFAULT_SLOTS[i] = i;
		}
		this.items = NonNullList.withSize(getSizeInventory(), ItemStack.EMPTY);
	}

	public void read(CompoundNBT compound)
	{
		super.read(compound);
		this.items = NonNullList.withSize(getSizeInventory(), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(compound, this.items);
	}

	public CompoundNBT write(CompoundNBT compound)
	{
		compound = super.write(compound);
		return ItemStackHelper.saveAllItems(compound, this.items);
	}

	@Override
	public void clear()
	{
		this.items.clear();
	}

	@Override
	public int getSizeInventory()
	{
		return SIZE;
	}

	@Override
	public boolean isEmpty()
	{
		for (ItemStack stack : items)
		{
			if (!stack.isEmpty())
			{
				return false;
			}
		}
		return true;
	}

	@Override
	public ItemStack getStackInSlot(int index)
	{
		return items.get(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count)
	{
		return ItemStackHelper.getAndSplit(this.items, index, count);
	}

	@Override
	public ItemStack removeStackFromSlot(int index)
	{
		return ItemStackHelper.getAndRemove(this.items, index);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		if (stack.getCount() > this.getInventoryStackLimit())
		{
			stack.setCount(this.getInventoryStackLimit());
		}
		this.items.set(index, stack);
		this.markDirty();
	}

	@Override
	public boolean isUsableByPlayer(PlayerEntity player)
	{
		return this.world.getTileEntity(this.pos) == this;
	}

	@Override
	public int[] getSlotsForFace(Direction side)
	{
		return DEFAULT_SLOTS;
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, Direction direction)
	{
		ItemStack stack = items.get(index);
		return stack.isEmpty() || stack.getCount() + itemStackIn.getCount() <= getInventoryStackLimit();
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, Direction direction)
	{
		return true;
	}

	@Override
	public int getInventoryStackLimit()
	{
		return limit;
	}

	public void setInventoryStackLimit(int i)
	{
		limit = i;
	}

	public void putHoldItemIn(PlayerEntity player, Hand handIn, int index)
	{
		ItemStack stack = player.getHeldItem(handIn);
		if (!stack.isEmpty() && isEmpty())
		{
			putItemIn(player, handIn, stack, index);
		}
		else if (!isEmpty())
		{
			ItemStack invItem = getStackInSlot(index);
			putItemIn(player, handIn, stack, index);
			ItemHandlerHelper.giveItemToPlayer(player, invItem);
		}
	}

	private void putItemIn(PlayerEntity player, Hand handIn, ItemStack stackIn, int index)
	{
		ItemStack stackReturn = ItemStack.EMPTY;
		if (stackIn.getCount() > getInventoryStackLimit())
		{
			stackReturn = stackIn.split(stackIn.getCount() - getInventoryStackLimit());
		}
		setInventorySlotContents(index, stackIn);
		player.setHeldItem(handIn, stackReturn);
	}

	public Collection<ItemStack> getDrops()
	{
		return items;
	}
}
