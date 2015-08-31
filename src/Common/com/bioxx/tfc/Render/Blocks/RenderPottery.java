package com.bioxx.tfc.Render.Blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

import com.bioxx.tfc.TileEntities.TEPottery;
import com.bioxx.tfc.api.TFCBlocks;

public class RenderPottery implements ISimpleBlockRenderingHandler
{

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer)
	{
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
	{
		TEPottery te = (TEPottery) world.getTileEntity(x, y, z);
		boolean breaking = renderer.overrideBlockTexture != null;
		
		if(te.straw > 0)
		{
			if ( ! breaking )
				renderer.overrideBlockTexture = TFCBlocks.thatch.getIcon(world, x, y, z, 0);
			
			renderer.setRenderBounds(0, 0, 0, 1, 0.0625*te.straw, 1);
			renderer.renderStandardBlock(block, x, y, z);
		}

		if(te.wood > 0)
		{
			if ( ! breaking )
				renderer.overrideBlockTexture = TFCBlocks.logPile.getIcon(0, 0);
			
			int w = te.wood;
			if(te.wood > 4)
			{
				w = te.wood-4;
				renderer.setRenderBounds(0, 0.75, 0, 0.25*w, 1, 1);
				renderer.renderStandardBlock(block, x, y, z);
				w=4;
			}
			renderer.setRenderBounds(0, 0.5, 0, 0.25*w, 0.75, 1);
			renderer.renderStandardBlock(block, x, y, z);
		}
		
		if ( ! breaking )
			renderer.clearOverrideBlockTexture();
		
		return true;
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
}

