package com.hosta.Flora.block;

import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface IRenderType {

	@OnlyIn(Dist.CLIENT)
	public RenderType getRenderType();
}
