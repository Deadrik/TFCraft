package com.bioxx.tfc.Render.Blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

import com.bioxx.tfc.TileEntities.TEMetalSheet;

public class RenderMetalSheet implements ISimpleBlockRenderingHandler
{
	public static boolean render(Block block, int i, int j, int k, RenderBlocks renderblocks)
	{
		IBlockAccess access = renderblocks.blockAccess;
		TEMetalSheet te = (TEMetalSheet)access.getTileEntity(i, j, k);

		double yMax = 1;
		double yMin = 0;
		double f0 = 0.0625;
		double f1 = 0.9375;

		if(te.bottomExists())
		{
			renderblocks.setRenderBounds(0, 0, 0, 1, f0, 1);
			renderblocks.renderStandardBlock(block, i, j, k);
			yMin = 0.0625;
		}
		if(te.topExists())
		{
			renderblocks.setRenderBounds(0, f1, 0, 1, 1, 1);
			renderblocks.renderStandardBlock(block, i, j, k);
			yMax = 0.9375;
		}
		if(te.northExists())
		{
			renderblocks.setRenderBounds(0, yMin, 0, 1, yMax, f0);
			renderblocks.renderStandardBlock(block, i, j, k);
		}
		if(te.southExists())
		{
			renderblocks.setRenderBounds(0, yMin, f1, 1, yMax, 1);
			renderblocks.renderStandardBlock(block, i, j, k);
		}
		if(te.eastExists())
		{
			renderblocks.setRenderBounds(0, yMin, 0, f0, yMax, 1);
			renderblocks.renderStandardBlock(block, i, j, k);
		}
		if(te.westExists())
		{
			renderblocks.setRenderBounds(f1, yMin, 0, 1, yMax, 1);
			renderblocks.renderStandardBlock(block, i, j, k);
		}

		return true;
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer)
	{
		renderer.setRenderBounds(0F, 0F, 0F, 1F, 0.2F, 1f);
		renderInvBlock(block, metadata, renderer);
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
	{
		return render(block, x, y, z, renderer);
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
		int meta = m;
		if (meta >= 8)
			meta -= 8;
		var14.startDrawingQuads();
		var14.setNormal(0.0F, -1.0F, 0.0F);
		renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(0, meta));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(0.0F, 1.0F, 0.0F);
		renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(1, meta));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(0.0F, 0.0F, -1.0F);
		renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(2, meta));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(0.0F, 0.0F, 1.0F);
		renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(3, meta));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(-1.0F, 0.0F, 0.0F);
		renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(4, meta));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(1.0F, 0.0F, 0.0F);
		renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(5, meta));
		var14.draw();
	}

}
