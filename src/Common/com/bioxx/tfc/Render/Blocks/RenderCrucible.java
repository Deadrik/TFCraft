package com.bioxx.tfc.Render.Blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

import org.lwjgl.opengl.GL11;

public class RenderCrucible  implements ISimpleBlockRenderingHandler 
{
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int i, int j, int k, Block block, int modelId, RenderBlocks renderer)
	{
		//int meta = world.getBlockMetadata(i, j, k);
		float s0 = 0f;
		float s1 = 1f/16f;
		//float s2 = 2f/16f;
		float s3 = 3f/16f;
		//float s12 = 12f/16f;
		float s13 = 13f/16f;
		//float s14 = 14f/16f;
		float s15 = 15f/16f;
		//float s16 = 1f;
		renderer.setRenderBounds(s1, s0, s1, s15, s1, s15);
		renderer.renderStandardBlock(block, i, j, k);
		renderer.setRenderBounds(s1, s1, s3, s3, s15, s13);
		renderer.renderStandardBlock(block, i, j, k);
		renderer.setRenderBounds(s13, s1, s3, s15, s15, s13);
		renderer.renderStandardBlock(block, i, j, k);
		renderer.setRenderBounds(s1, s1, s1, s15, s15, s3);
		renderer.renderStandardBlock(block, i, j, k);
		renderer.setRenderBounds(s1, s1, s13, s15, s15, s15);
		renderer.renderStandardBlock(block, i, j, k);
		return true;
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
	{
		float s0 = 0f;
		float s1 = 1f/16f;
		//float s2 = 2f/16f;
		float s3 = 3f/16f;
		float s13 = 13f/16f;
		//float s14 = 14f/16f;
		float s15 = 15f/16f;

		renderer.setRenderBounds(s1, s0, s1, s15, s1, s15);
		renderInvBlock(block,metadata,renderer);
		renderer.setRenderBounds(s1, s1, s3, s3, s15, s13);
		renderInvBlock(block,metadata,renderer);
		renderer.setRenderBounds(s13, s1, s3, s15, s15, s13);
		renderInvBlock(block,metadata,renderer);
		renderer.setRenderBounds(s1, s1, s1, s15, s15, s3);
		renderInvBlock(block,metadata,renderer);
		renderer.setRenderBounds(s1, s1, s13, s15, s15, s15);
		renderInvBlock(block,metadata,renderer);
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId)
	{
		return true;
	}

	@Override
	public int getRenderId()
	{
		return 0;
	}

	public static void renderInvBlock(Block block, int m,  RenderBlocks renderer)
	{
		Tessellator var14 = Tessellator.instance;
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		var14.startDrawingQuads();
		var14.setNormal(0.0F, -1.0F, 0.0F);
		renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(0, m));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(0.0F, 1.0F, 0.0F);
		renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(1, m));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(-1.0F, 0.0F, 0.0F);
		renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(2, m));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(0.0F, 0.0F, -1.0F);
		renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(3, m));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(-1.0F, 0.0F, 0.0F);
		renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(4, m));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(0.0F, 0.0F, 1.0F);
		renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(5, m));
		var14.draw();
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
	}
}
