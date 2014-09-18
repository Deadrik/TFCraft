package com.bioxx.tfc.Render.Blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.TileEntities.TESmokeRack;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderSmokeRack implements ISimpleBlockRenderingHandler
{
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
	{
		TESmokeRack te = (TESmokeRack) world.getTileEntity(x, y, z);
		renderer.renderAllFaces = true;
		renderer.renderStandardBlock(block, x, y, z);
		if((world.getBlockMetadata(x, y, z) & 1) == 0)
		{
			if(te.getStackInSlot(0) != null)
			{
				double mid = 0.25;
				renderer.setRenderBounds(0.43, 0.43, mid-0.07, 0.57, 0.57, mid+0.07);
				renderer.renderStandardBlock(block, x, y, z);
				renderer.setRenderBounds(0.491, 0.2, mid-0.009, 0.509, 0.5, mid+0.009);
				renderer.renderStandardBlock(block, x, y, z);
			}
			if(te.getStackInSlot(1) != null)
			{
				double mid = 0.75;
				renderer.setRenderBounds(0.43, 0.43, mid-0.07, 0.57, 0.57, mid+0.07);
				renderer.renderStandardBlock(block, x, y, z);
				renderer.setRenderBounds(0.491, 0.2, mid-0.009, 0.509, 0.5, mid+0.009);
				renderer.renderStandardBlock(block, x, y, z);
			}
		}
		else
		{
			if(te.getStackInSlot(0) != null)
			{
				double mid = 0.25;
				renderer.setRenderBounds(mid-0.07, 0.43, 0.43, mid+0.07, 0.57, 0.57);
				renderer.renderStandardBlock(block, x, y, z);
				renderer.setRenderBounds(mid-0.009, 0.2, 0.491, mid+0.009, 0.5, 0.509);
				renderer.renderStandardBlock(block, x, y, z);
			}
			if(te.getStackInSlot(1) != null)
			{
				double mid = 0.75;
				renderer.setRenderBounds(mid-0.07, 0.43, 0.43, mid+0.07, 0.57, 0.57);
				renderer.renderStandardBlock(block, x, y, z);
				renderer.setRenderBounds(mid-0.009, 0.2, 0.491, mid+0.009, 0.5, 0.509);
				renderer.renderStandardBlock(block, x, y, z);
			}
		}
		renderer.renderAllFaces = false;

		return true;
	}

	public void rotate(RenderBlocks renderer, int i)
	{
		renderer.uvRotateEast = i;
		renderer.uvRotateWest = i;
		renderer.uvRotateNorth = i;
		renderer.uvRotateSouth = i;
	}

	@Override
	public void renderInventoryBlock(Block block, int meta, int modelID, RenderBlocks renderer)
	{

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
