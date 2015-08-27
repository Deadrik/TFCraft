package com.bioxx.tfc.Render.Blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

import org.lwjgl.opengl.GL11;

public class RenderOilLamp implements ISimpleBlockRenderingHandler
{
	//private static float min = 0.1F;
	//private static float max = 0.9F;

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
	{
		renderer.renderAllFaces = true;
		//Tessellator tessellator = Tessellator.instance;
		renderer.setRenderBounds(0.275, 0.0, 0.275, 0.725, 0.0625F, 0.725);
		renderer.renderStandardBlock(block, x, y, z);
		renderer.setRenderBounds(0.25, 0.0625, 0.25, 0.75, 0.375F, 0.75);
		renderer.renderStandardBlock(block, x, y, z);
		renderer.setRenderBounds(0.3125, 0.375, 0.3125, 0.6875, 0.4375, 0.6875);
		renderer.renderStandardBlock(block, x, y, z);
		renderer.setRenderBounds(0.375, 0.4375, 0.375, 0.625, 0.5, 0.625);
		renderer.renderStandardBlock(block, x, y, z);
		renderer.setRenderBounds(0.46875, 0.5, 0.46875, 0.53125, 0.5625F, 0.53125);
		renderer.renderStandardBlock(Blocks.snow, x, y, z);
		renderer.renderAllFaces = false;
		return true;
	}

	@Override
	public void renderInventoryBlock(Block block, int meta, int modelID, RenderBlocks renderer)
	{
		renderer.setRenderBounds(0.275, 0.0, 0.275, 0.725, 0.0625F, 0.725);
		renderInvBlock(block, meta, renderer);
		renderer.setRenderBounds(0.25, 0.0625, 0.25, 0.75, 0.375F, 0.75);
		renderInvBlock(block, meta, renderer);
		renderer.setRenderBounds(0.3125, 0.375, 0.3125, 0.6875, 0.4375, 0.6875);
		renderInvBlock(block, meta, renderer);
		renderer.setRenderBounds(0.375, 0.4375, 0.375, 0.625, 0.5, 0.625);
		renderInvBlock(block, meta, renderer);
		renderer.setRenderBounds(0.46875, 0.5, 0.46875, 0.53125, 0.5625F, 0.53125);
		renderInvBlock(Blocks.snow, meta, renderer);
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

	public static void renderInvBlock(Block block, int m, RenderBlocks renderer)
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
		var14.setNormal(0.0F, 0.0F, -1.0F);
		renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(2, m));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(0.0F, 0.0F, 1.0F);
		renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(3, m));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(-1.0F, 0.0F, 0.0F);
		renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(4, m));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(1.0F, 0.0F, 0.0F);
		renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(5, m));
		var14.draw();
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
	}
}
