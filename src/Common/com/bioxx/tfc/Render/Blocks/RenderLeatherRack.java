package com.bioxx.tfc.Render.Blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

import com.bioxx.tfc.Blocks.Devices.BlockLeatherRack;
import com.bioxx.tfc.TileEntities.TELeatherRack;

public class RenderLeatherRack implements ISimpleBlockRenderingHandler
{

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
	{
		TELeatherRack te = (TELeatherRack)world.getTileEntity(x, y, z);
		BlockLeatherRack blk = (BlockLeatherRack)block;
		//float f0 = 0;
		//float f1 = 0.25f;
		//float f2 = 0.5f;
		//float f3 = 0.75f;
		//float f4 = 1f;
		boolean breaking = renderer.overrideBlockTexture != null;
		
		for(int k = 0; k < 4; k++)
		{
			for(int i = 0; i < 4; i++)
			{
				if(((te.workedArea >> (k*4+i)) & 1) != 0 && ! breaking)
					renderer.overrideBlockTexture = blk.scrapedTex;
				renderer.setRenderBounds(0.25*i, 0, 0.25*k, 0.25*i+0.25, 0.001, 0.25*k+0.25);
				renderer.renderStandardBlock(block, x, y, z);
				if ( ! breaking )
					renderer.clearOverrideBlockTexture();
			}
		}
		return true;
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
	{

	}

	@Override
	public boolean shouldRender3DInInventory(int modelId)
	{
		return false;
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
