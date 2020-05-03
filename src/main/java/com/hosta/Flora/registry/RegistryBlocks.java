package com.hosta.Flora.registry;

import com.hosta.Flora.IMod;
import com.hosta.Flora.block.IItemName;
import com.hosta.Flora.block.IRenderType;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockNamedItem;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

@SuppressWarnings("rawtypes")
public class RegistryBlocks extends AbstractRegistry {

	public void registerItems(IForgeRegistry<Item> registry, IMod mod)
	{
		for (IForgeRegistryEntry block : LIST)
		{
			Item item;
			if (block instanceof IItemName)
			{
				ResourceLocation name = mod.getResourceLocation(((IItemName) block).getItemName());
				item = new BlockNamedItem((Block) block, mod.getDefaultProp()).setRegistryName(name);
			}
			else
			{
				item = new BlockItem((Block) block, mod.getDefaultProp()).setRegistryName(block.getRegistryName());
			}
			this.registerNamed(registry, item);
		}
	}

	@OnlyIn(Dist.CLIENT)
	public void registerRenders()
	{
		for (IForgeRegistryEntry block : LIST)
		{
			if (block instanceof IRenderType)
			{
				RenderTypeLookup.setRenderLayer((Block) block, ((IRenderType) block).getType().getRenderType());
			}
		}
	}
}
