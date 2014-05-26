package com.bioxx.tfc.Render.Blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderLeatherRack implements ISimpleBlockRenderingHandler
{

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
	{
		renderer.setRenderBounds(0, 0, 0.475, 1, 0.05, 0.525);
		renderer.renderStandardBlock(block, x, y, z);
		renderer.setRenderBounds(0, 0.95, 0.475, 1, 1, 0.525);
		renderer.renderStandardBlock(block, x, y, z);
		renderer.setRenderBounds(0, 0.05, 0.475, 0.05, 0.95, 0.525);
		renderer.renderStandardBlock(block, x, y, z);
		renderer.setRenderBounds(0.95, 0.05, 0.475, 1, 0.95, 0.525);
		renderer.renderStandardBlock(block, x, y, z);
		return false;
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
	{
		renderer.setRenderBounds(0, 0, 0.475, 1, 0.05, 0.525);
		renderInvBlock(block, metadata, renderer);
		renderer.setRenderBounds(0, 0.95, 0.475, 1, 1, 0.525);
		renderInvBlock(block, metadata, renderer);
		renderer.setRenderBounds(0, 0.05, 0.475, 0.05, 0.95, 0.525);
		renderInvBlock(block, metadata, renderer);
		renderer.setRenderBounds(0.95, 0.05, 0.475, 1, 0.95, 0.525);
		renderInvBlock(block, metadata, renderer);
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

	public static void renderInvBlock(Block block, int meta, RenderBlocks renderer)
	{
		Tessellator tess = Tessellator.instance;
		tess.startDrawingQuads();
		tess.setNormal(0.0F, -1.0F, 0.0F);
		renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(0, meta));
		tess.draw();
		tess.startDrawingQuads();
		tess.setNormal(0.0F, 1.0F, 0.0F);
		renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(1, meta));
		tess.draw();
		tess.startDrawingQuads();
		tess.setNormal(0.0F, 0.0F, -1.0F);
		renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(2, meta));
		tess.draw();
		tess.startDrawingQuads();
		tess.setNormal(0.0F, 0.0F, 1.0F);
		renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(3, meta));
		tess.draw();
		tess.startDrawingQuads();
		tess.setNormal(-1.0F, 0.0F, 0.0F);
		renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(4, meta));
		tess.draw();
		tess.startDrawingQuads();
		tess.setNormal(1.0F, 0.0F, 0.0F);
		renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(5, meta));
		tess.draw();
	}
}
