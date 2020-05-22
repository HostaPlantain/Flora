package com.hosta.Flora.client.render.tileentity;

import java.util.Random;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.block.BlockState;
import net.minecraft.block.DoublePlantBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockModelRenderer;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.client.model.generators.ModelBuilder;

public abstract class TileEntityBaseRenderer<T extends TileEntity> extends TileEntityRenderer<T> {

	protected static final ItemRenderer				ITEM_RENDER		= Minecraft.getInstance().getItemRenderer();
	protected static final BlockRendererDispatcher	DISPATCHER		= Minecraft.getInstance().getBlockRendererDispatcher();
	protected static final BlockModelRenderer		BLOCK_RENDER	= DISPATCHER.getBlockModelRenderer();
	protected static final float[] ROTATE0 = new float[] { 0.0F, 0.0F, 0.0F };

	public TileEntityBaseRenderer(TileEntityRendererDispatcher rendererDispatcherIn)
	{
		super(rendererDispatcherIn);
	}

	protected void relocate(MatrixStack matrixStackIn, double[] pos, float[] rotate, float scale)
	{
		matrixStackIn.translate(pos[0], pos[1], pos[2]);
		matrixStackIn.rotate(Vector3f.YP.rotationDegrees(rotate[1]));
		matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(rotate[2]));
		matrixStackIn.rotate(Vector3f.XP.rotationDegrees(rotate[0]));
		matrixStackIn.scale(scale, scale, scale);
	}

	protected void renderItem(MatrixStack matrixStackIn, double[] pos, float[] rotate, float scale, ItemStack itemstack, int combinedLightIn, IRenderTypeBuffer bufferIn)
	{
		matrixStackIn.push();
		relocate(matrixStackIn, pos, rotate, scale);
		ITEM_RENDER.renderItem(itemstack, ModelBuilder.Perspective.FIXED.vanillaType, combinedLightIn, OverlayTexture.NO_OVERLAY, matrixStackIn, bufferIn);
		matrixStackIn.pop();
	}

	@SuppressWarnings("deprecation")
	protected void renderBlock(MatrixStack matrixStackIn, double[] pos, float[] rotate, float scale, TileEntity tileentity, BlockState blockstate, int combinedLightIn, IVertexBuilder vertex)
	{
		World world = tileentity.getWorld();
		BlockPos blockpos = tileentity.getPos();
		Vec3d vec3d = blockstate.getOffset(world, blockpos);
		IBakedModel model = DISPATCHER.getModelForState(blockstate);

		matrixStackIn.push();
		relocate(matrixStackIn, pos, rotate, scale);
		matrixStackIn.translate(-vec3d.x, -vec3d.y, -vec3d.z);
		BLOCK_RENDER.renderModel(world, model, blockstate, blockpos, matrixStackIn, vertex, false, new Random(), 0, combinedLightIn);
		matrixStackIn.pop();
	}

	protected void renderPlant(MatrixStack matrixStackIn, double[] pos, float[] rotate, float scale, TileEntity tileentity, BlockState blockstate, int combinedLightIn, IRenderTypeBuffer bufferIn)
	{
		IVertexBuilder vertex = bufferIn.getBuffer(RenderType.getCutout());
		renderBlock(matrixStackIn, pos, rotate, scale, tileentity, blockstate, combinedLightIn, vertex);
		if (blockstate.getBlock() instanceof DoublePlantBlock)
		{
			pos[1] += scale;
			renderBlock(matrixStackIn, pos, rotate, scale, tileentity, blockstate.with(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER), combinedLightIn, vertex);
		}
	}
}
