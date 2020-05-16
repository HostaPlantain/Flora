package com.hosta.Flora.block;

import java.util.function.Function;

import com.hosta.Flora.client.render.tileentity.TileEntityBaseRenderer;

import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface IRenderTileEntity extends IRenderType {

	@OnlyIn(Dist.CLIENT)
	public <T extends TileEntity> TileEntityType<T> getTileEntityType();

	@OnlyIn(Dist.CLIENT)
	public <T extends TileEntity> Function<TileEntityRendererDispatcher, ? extends TileEntityBaseRenderer<T>> getRenderer();
}
