package com.hosta.Flora.block;

import net.minecraft.block.Block;
import net.minecraft.block.CropsBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.IItemProvider;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BlockBaseCrops extends CropsBlock implements IItemName, IRenderType {

	private final String NAME_ITEM;

	public BlockBaseCrops(String nameItem, Material materialIn)
	{
		this(nameItem, Block.Properties.create(materialIn).doesNotBlockMovement().tickRandomly().hardnessAndResistance(0.0F).sound(SoundType.CROP));
	}

	public BlockBaseCrops(String nameItem, Block.Properties property)
	{
		super(property);
		this.NAME_ITEM = nameItem;
	}

	@Override
	protected IItemProvider getSeedsItem()
	{
		return this.asItem();
	}

	@Override
	public String getItemName()
	{
		return NAME_ITEM;
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public IRenderType.Type getType()
	{
		return IRenderType.Type.CUTOUT;
	}

}
