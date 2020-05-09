package com.hosta.Flora.registry;

import com.hosta.Flora.IMod;
import com.hosta.Flora.block.IItemName;
import com.hosta.Flora.block.IRenderType;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockNamedItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class RegistryBlocks extends RegistryBase<Block> {

	public RegistryBlocks()
	{
		super(entry -> entry instanceof Block);
	}

	public void registerItems(RegistryHandler registryHandler, IMod mod)
	{
		for (Block block : LIST)
		{
			if (block instanceof IItemName)
			{
				registryHandler.register((((IItemName) block).getItemName()), new BlockNamedItem((Block) block, mod.getDefaultProp()));
			}
			else
			{
				registryHandler.register((block.getRegistryName().getPath()), new BlockItem((Block) block, mod.getDefaultProp()));
			}
		}
	}

	@OnlyIn(Dist.CLIENT)
	public void registerRenders()
	{
		for (Block block : LIST)
		{
			if (block instanceof IRenderType)
			{
				RenderTypeLookup.setRenderLayer((Block) block, ((IRenderType) block).getType().getRenderType());
			}
		}
	}
}
