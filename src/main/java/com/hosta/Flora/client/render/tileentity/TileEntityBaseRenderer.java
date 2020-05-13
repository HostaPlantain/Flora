package com.hosta.Flora.client.render.tileentity;

import com.hosta.Flora.tileentity.TileEntityBase;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.generators.ModelBuilder;

public abstract class TileEntityBaseRenderer<T extends TileEntityBase> extends TileEntityRenderer<T> {

	private static final ItemRenderer RENDER = Minecraft.getInstance().getItemRenderer();

	public TileEntityBaseRenderer(TileEntityRendererDispatcher rendererDispatcherIn)
	{
		super(rendererDispatcherIn);
	}

	protected void renderItem(MatrixStack matrixStackIn, double x, double y, double z, float degree, float rotate, float scale, ItemStack itemstack, int combinedLightIn, IRenderTypeBuffer bufferIn)
	{
		matrixStackIn.push();
		matrixStackIn.translate(0.5D + x, 0.5D, 0.5D + z);
		matrixStackIn.rotate(Vector3f.YP.rotationDegrees(degree));
		matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(rotate));
		matrixStackIn.scale(scale, scale, scale);
		RENDER.renderItem(itemstack, ModelBuilder.Perspective.FIXED.vanillaType, combinedLightIn, OverlayTexture.NO_OVERLAY, matrixStackIn, bufferIn);
		matrixStackIn.pop();
	}
}
