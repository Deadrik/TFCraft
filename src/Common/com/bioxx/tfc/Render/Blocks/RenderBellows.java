package com.bioxx.tfc.Render.Blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Blocks.Devices.BlockBellows;

public class RenderBellows implements ISimpleBlockRenderingHandler
{
	@Override
	public boolean shouldRender3DInInventory(int modelId)
	{
		return true;
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
	{
		//forward
		renderer.overrideBlockTexture = BlockBellows.bellowsFront;
		renderer.setRenderBounds(0.0F, 0.0F, 0.9F, 1.0F, 1.0F, 1.0F);
		renderInvBlock(block, renderer);
		//mid
		renderer.clearOverrideBlockTexture();
		renderer.setRenderBounds(0.1F, 0.1F, 0.05F, 0.9F, 0.9F, 0.95F);
		renderInvBlock(block, renderer);
		//back
		renderer.overrideBlockTexture = BlockBellows.bellowsBack;
		renderer.setRenderBounds(0.0F, 0.0F, 0, 1.0F, 1.0F, 0.1F);
		renderInvBlock(block, renderer);

		renderer.clearOverrideBlockTexture();
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
	{
		//renderBlock(modelId, renderer, block, x, y, z);
		return false;
	}

	@Override
	public int getRenderId()
	{
		return 0;
	}

	public static void renderInvBlock(Block block, RenderBlocks renderer)
	{
		Tessellator var14 = Tessellator.instance;
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		var14.startDrawingQuads();
		var14.setNormal(0.0F, -1.0F, 0.0F);
		renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(0, 2));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(0.0F, 1.0F, 0.0F);
		renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(0, 2));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(-1.0F, 0.0F, 0.0F);
		renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(0, 1));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(-1.0F, 0.0F, 0.0F);
		renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(0, 3));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(0.0F, 0.0F, -1.0F);
		renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(0, 0));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(0.0F, 0.0F, 1.0F);
		renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(0, 0));
		var14.draw();
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
	}

	/*private void renderBlock(int modelId, RenderBlocks renderer, Block block, int x, int y, int z)
	{
		if(modelId == TFCBlocks.BellowsRenderId)
		{
			IBlockAccess blockAccess = renderer.blockAccess;
			int meta = blockAccess.getBlockMetadata(x, y, z);
			int direction = BlockBellows.getDirectionFromMetadata(meta);
			TEBellows te = (TEBellows)blockAccess.getTileEntity(x, y, z);
			float pos = te.blowTimer * 0.1F;
			if(pos < 0)
				pos = 0;

			if(te != null)
			{
				boolean breaking = false;
				if(renderer.overrideBlockTexture != null)
					breaking = true;

				if(direction == 0)
				{
					//forward
					if(!breaking) renderer.overrideBlockTexture = BlockBellows.BellowsFront;
					renderer.setRenderBounds(0.0F, 0.0F, 0.9F, 1.0F, 1.0F, 1.0F);
					renderer.renderStandardBlock(block, x, y, z);
					//mid
					if(!breaking) renderer.clearOverrideBlockTexture();
					renderer.setRenderBounds(0.1F, 0.1F, 0.05F+pos, 0.9F, 0.9F, 0.95F);
					renderer.renderStandardBlock(block, x, y, z);
					//back
					if(!breaking) renderer.overrideBlockTexture = BlockBellows.BellowsBack;
					renderer.setRenderBounds(0.0F, 0.0F, pos, 1.0F, 1.0F, 0.1F+pos);
					renderer.renderStandardBlock(block, x, y, z);
					renderer.clearOverrideBlockTexture();
				}
				else if(direction == 1)
				{
					//forward
					if(!breaking) renderer.overrideBlockTexture = BlockBellows.BellowsFront;
					renderer.setRenderBounds(0.0F, 0.0F, 0.0F, 0.1F, 1.0F, 1.0F);
					renderer.renderStandardBlock(block, x, y, z);
					//mid
					if(!breaking) renderer.clearOverrideBlockTexture();
					renderer.setRenderBounds(0.1F, 0.1F, 0.1F, 0.9F-pos, 0.9F, 0.9F);
					renderer.renderStandardBlock(block, x, y, z);
					//back
					if(!breaking) renderer.overrideBlockTexture = BlockBellows.BellowsBack;
					renderer.setRenderBounds(0.9F-pos, 0.0F, 0.0F, 1.0F-pos, 1.0F, 1.0F);
					renderer.renderStandardBlock(block, x, y, z);
					renderer.clearOverrideBlockTexture();
				}
				else if(direction == 2)
				{
					//forward
					if(!breaking) renderer.overrideBlockTexture = BlockBellows.BellowsFront;
					renderer.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.1F);
					renderer.renderStandardBlock(block, x, y, z);
					//mid
					if(!breaking) renderer.clearOverrideBlockTexture();
					renderer.setRenderBounds(0.1F, 0.1F, 0.05F, 0.9F, 0.9F, 0.95F-pos);
					renderer.renderStandardBlock(block, x, y, z);
					//back
					if(!breaking) renderer.overrideBlockTexture = BlockBellows.BellowsBack;
					renderer.setRenderBounds(0.0F, 0.0F, 0.9F-pos, 1.0F, 1.0F, 1.0F-pos);
					renderer.renderStandardBlock(block, x, y, z);
					renderer.clearOverrideBlockTexture();
				}
				else if(direction == 3)
				{
					//forward
					if(!breaking)  renderer.overrideBlockTexture = BlockBellows.BellowsFront;
					renderer.setRenderBounds(0.9F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
					renderer.renderStandardBlock(block, x, y, z);
					//mid
					if(!breaking) renderer.clearOverrideBlockTexture();
					renderer.setRenderBounds(0.1F+pos, 0.1F, 0.1F, 0.9F, 0.9F, 0.9F);
					renderer.renderStandardBlock(block, x, y, z);
					//back
					if(!breaking) renderer.overrideBlockTexture = BlockBellows.BellowsBack;
					renderer.setRenderBounds(pos, 0.0F, 0.0F, 0.1F+pos, 1.0F, 1.0F);
					renderer.renderStandardBlock(block, x, y, z);
					renderer.clearOverrideBlockTexture();
				}
				renderer.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
			}
		}
	}*/
}
