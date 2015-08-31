package com.bioxx.tfc.Render.Blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

import com.bioxx.tfc.TileEntities.TEToolRack;

public class RenderToolRack implements ISimpleBlockRenderingHandler
{

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderblocks)
	{
		renderblocks.overrideBlockTexture = block.getIcon(0, metadata);

		renderblocks.setRenderBounds(0.3F, 0.3, 0.95F, 1.7F, 0.45F, 1.1F);
		renderInvBlock(block,metadata,renderblocks);

		renderblocks.setRenderBounds(0.3F, 0.9, 0.95F, 1.7F, 1.05F, 1.1F);
		renderInvBlock(block,metadata,renderblocks);

		renderblocks.clearOverrideBlockTexture();
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int i, int j, int k, Block block, int modelId, RenderBlocks renderblocks)
	{
		IBlockAccess blockAccess = renderblocks.blockAccess;
		TEToolRack te = (TEToolRack)blockAccess.getTileEntity(i, j, k);
		int dir = blockAccess.getBlockMetadata(i, j, k);
		boolean breaking = renderblocks.overrideBlockTexture != null;
		if(te != null)
		{
			if ( ! breaking )
				renderblocks.overrideBlockTexture = block.getIcon(0, te.woodType);
			
			//double minX = 0;
			//double maxX = 0.5;
			//double minZ = 0;
			//double maxZ = 0.5;
			//First we render the rack itself.
			if(dir == 0)
			{
				renderRackDir0(block, i, j, k, renderblocks, 0.7f);
				renderRackDir0(block, i, j, k, renderblocks, 0.3f);
			}
			else if(dir == 1)
			{
				renderRackDir1(block, i, j, k, renderblocks, 0.7f);
				renderRackDir1(block, i, j, k, renderblocks, 0.3f);
			}
			else if(dir == 2)
			{
				renderRackDir2(block, i, j, k, renderblocks, 0.7f);
				renderRackDir2(block, i, j, k, renderblocks, 0.3f);
			}
			else if(dir == 3)
			{
				renderRackDir3(block, i, j, k, renderblocks, 0.7f);
				renderRackDir3(block, i, j, k, renderblocks, 0.3f);
			}
		}
		
		if ( ! breaking )
			renderblocks.clearOverrideBlockTexture();
		
		return true;
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

	private static void renderRackDir0(Block block, int i, int j, int k, RenderBlocks renderblocks, float yOffset)
	{
		renderblocks.setRenderBounds(0.0F, yOffset, 0.95F, 1F, yOffset + 0.05F, 1F);
		renderblocks.renderStandardBlock(block, i, j, k);

		renderblocks.setRenderBounds(0.2F, yOffset, 0.9F, 0.22F, yOffset + 0.05F, 0.95F);
		renderblocks.renderStandardBlock(block, i, j, k);
		renderblocks.setRenderBounds(0.3F, yOffset, 0.9F, 0.32F, yOffset + 0.05F, 0.95F);
		renderblocks.renderStandardBlock(block, i, j, k);

		renderblocks.setRenderBounds(0.68F, yOffset, 0.9F, 0.70F, yOffset + 0.05F, 0.95F);
		renderblocks.renderStandardBlock(block, i, j, k);
		renderblocks.setRenderBounds(0.78F, yOffset, 0.9F, 0.8F, yOffset + 0.05F, 0.95F);
		renderblocks.renderStandardBlock(block, i, j, k);
	}

	private static void renderRackDir1(Block block, int i, int j, int k, RenderBlocks renderblocks, float yOffset)
	{
		renderblocks.setRenderBounds(0.0F, yOffset, 0.0F, 0.05F, yOffset + 0.05F, 1F);
		renderblocks.renderStandardBlock(block, i, j, k);

		renderblocks.setRenderBounds(0.05F, yOffset, 0.2F, 0.1F, yOffset + 0.05F, 0.22F);
		renderblocks.renderStandardBlock(block, i, j, k);
		renderblocks.setRenderBounds(0.05F, yOffset, 0.3F, 0.1F, yOffset + 0.05F, 0.32F);
		renderblocks.renderStandardBlock(block, i, j, k);

		renderblocks.setRenderBounds(0.05F, yOffset, 0.68F, 0.1F, yOffset + 0.05F, 0.7F);
		renderblocks.renderStandardBlock(block, i, j, k);
		renderblocks.setRenderBounds(0.05F, yOffset, 0.78F, 0.1F, yOffset + 0.05F, 0.8F);
		renderblocks.renderStandardBlock(block, i, j, k);
	}

	private static void renderRackDir2(Block block, int i, int j, int k, RenderBlocks renderblocks, float yOffset)
	{
		renderblocks.setRenderBounds(0.0F, yOffset, 0.0F, 1F, yOffset + 0.05F, 0.05F);
		renderblocks.renderStandardBlock(block, i, j, k);

		renderblocks.setRenderBounds(0.2F, yOffset, 0.05F, 0.22F, yOffset + 0.05F, 0.1F);
		renderblocks.renderStandardBlock(block, i, j, k);
		renderblocks.setRenderBounds(0.3F, yOffset, 0.05F, 0.32F, yOffset + 0.05F, 0.1F);
		renderblocks.renderStandardBlock(block, i, j, k);

		renderblocks.setRenderBounds(0.68F, yOffset, 0.05F, 0.70F, yOffset + 0.05F, 0.1F);
		renderblocks.renderStandardBlock(block, i, j, k);
		renderblocks.setRenderBounds(0.78F, yOffset, 0.05F, 0.8F, yOffset + 0.05F, 0.1F);
		renderblocks.renderStandardBlock(block, i, j, k);
	}

	private static void renderRackDir3(Block block, int i, int j, int k, RenderBlocks renderblocks, float yOffset)
	{
		renderblocks.setRenderBounds(0.95F, 		yOffset, 	0.0F, 	1F, 	yOffset + 0.05F, 	1F);
		renderblocks.renderStandardBlock(block, i, j, k);

		renderblocks.setRenderBounds(0.9F, 	yOffset, 	0.2F, 	0.95F, 	yOffset + 0.05F, 	0.22F);
		renderblocks.renderStandardBlock(block, i, j, k);
		renderblocks.setRenderBounds(0.9F, 	yOffset, 	0.3F, 	0.95F, 	yOffset + 0.05F, 	0.32F);
		renderblocks.renderStandardBlock(block, i, j, k);

		renderblocks.setRenderBounds(0.9F, 	yOffset, 	0.68F, 	0.95F, 	yOffset + 0.05F, 	0.7F);
		renderblocks.renderStandardBlock(block, i, j, k);
		renderblocks.setRenderBounds(0.9F, 	yOffset, 	0.78F, 	0.95F, 	yOffset + 0.05F, 	0.8F);
		renderblocks.renderStandardBlock(block, i, j, k);
	}

	public static void renderInvBlock(Block block, int meta, RenderBlocks renderer)
	{
		Tessellator var14 = Tessellator.instance;
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
