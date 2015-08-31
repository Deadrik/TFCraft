package com.bioxx.tfc.Render.Blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

import com.bioxx.tfc.Blocks.Devices.BlockEarlyBloomery;
import com.bioxx.tfc.TileEntities.TEBloomery;
import com.bioxx.tfc.api.TFCBlocks;

public class RenderBloomery implements ISimpleBlockRenderingHandler
{

	public static boolean render(Block block, int i, int j, int k, RenderBlocks renderblocks)
	{
		IBlockAccess blockAccess = renderblocks.blockAccess;

		int meta = blockAccess.getBlockMetadata(i, j, k);
		int dir = meta & 3;
		TEBloomery te = (TEBloomery)blockAccess.getTileEntity(i, j, k);
		if(te != null)
			if(te.isFlipped)
				dir = BlockEarlyBloomery.flipDir(dir);
		float f = 0.125F;
		//float xMin = 0.0625f;
		//float yMin = 0.0625f;
		//float zMin = 0.0625f;

		if (!BlockEarlyBloomery.isOpen(meta))
		{
			switch(dir)
			{
			case 0:
				renderblocks.setRenderBounds(0.0F, 0.0F, 0.0f, 1.0f, 1.0F, f);break;
			case 1:
				renderblocks.setRenderBounds(1.0f - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0f);break;
			case 2:
				renderblocks.setRenderBounds(0.0f, 0.0F, 1.0f - f, 1.0F, 1.0F, 1.0F);break;
			case 3:
				renderblocks.setRenderBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);break;
			}
			renderblocks.renderStandardBlock(block, i, j, k);
		}
		else
		{
			if (dir == 0)
			{
				//Render frame
				renderblocks.setRenderBounds(0, 0, 0, 1, 0.0625, f);
				renderblocks.renderStandardBlock(block, i, j, k);
				renderblocks.setRenderBounds(0, 0.9375, 0, 1, 1, f);
				renderblocks.renderStandardBlock(block, i, j, k);
				renderblocks.setRenderBounds(0, 0.0625, 0, 0.0625, 0.9375, f);
				renderblocks.renderStandardBlock(block, i, j, k);
				renderblocks.setRenderBounds(0.9375, 0.0625, 0, 1, 0.9375, f);
				renderblocks.renderStandardBlock(block, i, j, k);
				//Render doors
				renderblocks.setRenderBounds(0, 0.0625f, 0.0625f, f, 0.9375, 0.5F);
				renderblocks.renderStandardBlock(block, i, j, k);
				renderblocks.setRenderBounds(1 - f, 0.0625f, 0.0625f, 1, 0.9375, 0.5F);
				renderblocks.renderStandardBlock(block, i, j, k);
			}
			else if (dir == 1)
			{
				//Render frame
				renderblocks.setRenderBounds(1-f, 0, 0, 1, 0.0625, 1);
				renderblocks.renderStandardBlock(block, i, j, k);
				renderblocks.setRenderBounds(1-f, 0.9375, 0, 1, 1, 1);
				renderblocks.renderStandardBlock(block, i, j, k);
				renderblocks.setRenderBounds(1-f, 0.0625, 0, 1, 0.9375, 0.0625);
				renderblocks.renderStandardBlock(block, i, j, k);
				renderblocks.setRenderBounds(1-f, 0.0625, 0.9375, 1, 0.9375, 1);
				renderblocks.renderStandardBlock(block, i, j, k);
				//Render doors
				renderblocks.setRenderBounds(0.5, 0.0625f, 1 - f, 0.9375, 0.9375, 1);
				renderblocks.renderStandardBlock(block, i, j, k);
				renderblocks.setRenderBounds(0.5, 0.0625f, 0, 0.9375, 0.9375, f);
				renderblocks.renderStandardBlock(block, i, j, k);
			}
			else if (dir == 2)
			{
				//Render frame
				renderblocks.setRenderBounds(0, 0, 1-f, 1, 0.0625, 1);
				renderblocks.renderStandardBlock(block, i, j, k);
				renderblocks.setRenderBounds(0, 0.9375, 1-f, 1, 1, 1);
				renderblocks.renderStandardBlock(block, i, j, k);
				renderblocks.setRenderBounds(0, 0.0625, 1-f, 0.0625, 0.9375, 1);
				renderblocks.renderStandardBlock(block, i, j, k);
				renderblocks.setRenderBounds(0.9375, 0.0625, 1-f, 1, 0.9375, 1);
				renderblocks.renderStandardBlock(block, i, j, k);
				//Render doors
				renderblocks.setRenderBounds(0, 0.0625f, 0.5f, f, 0.9375, 0.9375);
				renderblocks.renderStandardBlock(block, i, j, k);
				renderblocks.setRenderBounds(1 - f, 0.0625f, 0.5f, 1, 0.9375, 0.9375);
				renderblocks.renderStandardBlock(block, i, j, k);
			}
			else if (dir == 3)
			{
				//Render frame
				renderblocks.setRenderBounds(0, 0, 0, f, 0.0625, 1);
				renderblocks.renderStandardBlock(block, i, j, k);
				renderblocks.setRenderBounds(0, 0.9375, 0, f, 1, 1);
				renderblocks.renderStandardBlock(block, i, j, k);
				renderblocks.setRenderBounds(0, 0.0625, 0, f, 0.9375, 0.0625);
				renderblocks.renderStandardBlock(block, i, j, k);
				renderblocks.setRenderBounds(0, 0.0625, 0.9375, f, 0.9375, 1);
				renderblocks.renderStandardBlock(block, i, j, k);
				//Render doors
				renderblocks.setRenderBounds(0.0625f, 0.0625f, 1 - f, 0.5, 0.9375, 1);
				renderblocks.renderStandardBlock(block, i, j, k);
				renderblocks.setRenderBounds(0.0625f, 0.0625f, 0, 0.5, 0.9375, f);
				renderblocks.renderStandardBlock(block, i, j, k);
			}
		}
		return true;
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer)
	{
		if (modelId == TFCBlocks.bloomeryRenderId)
		{
			renderer.setRenderBounds(0.5F, 0.0F, 0F, 0.7F, 1F, 1f);
			renderInvBlock(block, metadata, renderer);
		}
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
	{
		if (modelId == TFCBlocks.bloomeryRenderId)
			return render(block, x, y, z, renderer);
		return false;
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
