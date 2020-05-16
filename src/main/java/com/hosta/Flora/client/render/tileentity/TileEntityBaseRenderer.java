package com.hosta.Flora.client.render.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.model.generators.ModelBuilder;

public abstract class TileEntityBaseRenderer<T extends TileEntity> extends TileEntityRenderer<T> {

	protected static final ItemRenderer ITEM_RENDER = Minecraft.getInstance().getItemRenderer();

	public TileEntityBaseRenderer(TileEntityRendererDispatcher rendererDispatcherIn)
	{
		super(rendererDispatcherIn);
	}

	protected void renderItem(MatrixStack matrixStackIn, double[] pos, float[] rotate, float scale, ItemStack itemstack, int combinedLightIn, IRenderTypeBuffer bufferIn)
	{
		matrixStackIn.push();
		matrixStackIn.translate(0.5D + pos[0], 0.5D + pos[1], 0.5D + pos[2]);
		matrixStackIn.rotate(Vector3f.YP.rotationDegrees(rotate[1]));
		matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(rotate[2]));
		matrixStackIn.rotate(Vector3f.XP.rotationDegrees(rotate[0]));
		matrixStackIn.scale(scale, scale, scale);
		ITEM_RENDER.renderItem(itemstack, ModelBuilder.Perspective.FIXED.vanillaType, combinedLightIn, OverlayTexture.NO_OVERLAY, matrixStackIn, bufferIn);
		matrixStackIn.pop();
	}
}
