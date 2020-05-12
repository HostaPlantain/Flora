package com.hosta.Flora.client.render.tileentity;

import com.hosta.Flora.tileentity.TileEntityBase;

import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;

public abstract class TileEntityBaseRenderer<T extends TileEntityBase> extends TileEntityRenderer<T> {

	public TileEntityBaseRenderer(TileEntityRendererDispatcher rendererDispatcherIn)
	{
		super(rendererDispatcherIn);
	}
}
