package com.bioxx.tfc.Render.Blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.TileEntities.TELoom;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderLoom implements ISimpleBlockRenderingHandler
{
	static float min = 0F;
	static float max = 1F;

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
	{
		TELoom te = (TELoom) world.getTileEntity(x, y, z);
		Block materialBlock;
		if(te.loomType < 16)
		{
			materialBlock = TFCBlocks.WoodSupportH;
		}
		else
		{
			materialBlock = TFCBlocks.WoodSupportH2;
		}
		renderer.renderAllFaces = true;
		GL11.glPushMatrix();

		if(te.rotation == 0)
		{
			//Arms
			renderer.setRenderBounds(min+0.1F, min, min+0.75F, max-0.8F, max, max-0.15F);
			GL11.glRotatef(90*te.rotation, 0, 1, 0);
			renderer.renderStandardBlock(materialBlock, x, y, z);
			
			renderer.setRenderBounds(min+0.8F, min, min+0.75F, max-0.1F, max, max-0.15F);
			renderer.renderStandardBlock(materialBlock, x, y, z);
			
			//Arm holding sections
			//L
			renderer.setRenderBounds(min+0.1F, min+0.25F, min+0.5F, max-0.8F, max-0.7F, max-0.25F);
			renderer.renderStandardBlock(materialBlock, x, y, z);
			
			renderer.setRenderBounds(min+0.1F, min+0.05F, min+0.5F, max-0.8F, max-0.9F, max-0.25F);
			renderer.renderStandardBlock(materialBlock, x, y, z);
			
			//R
			renderer.setRenderBounds(min+0.8F, min+0.25F, min+0.5F, max-0.1F, max-0.7F, max-0.25F);
			renderer.renderStandardBlock(materialBlock, x, y, z);
			
			renderer.setRenderBounds(min+0.8F, min+0.05F, min+0.5F, max-0.1F, max-0.9F, max-0.25F);
			renderer.renderStandardBlock(materialBlock, x, y, z);
			
			//cross
			renderer.setRenderBounds(max-0.8F, min+0.8F, min+0.75F, min+0.8F, max-0.1F, max-0.15F);
			renderer.renderStandardBlock(materialBlock, x, y, z);
			
			renderer.setRenderBounds(max-0.8F, 0F, min+0.75F, min+0.8F, min+0.1F, max-0.15F);
			renderer.renderStandardBlock(materialBlock, x, y, z);
		}
		else
		{
			if((te.rotation & 3) == 0)
			{
				renderer.setRenderBounds(min, min, min+0.05F, 0.95F, min+0.05F, max-0.05F);
				renderer.renderStandardBlock(materialBlock, x, y, z);
			}
			if((te.rotation & 3) == 1)
			{
				renderer.setRenderBounds(min+0.05F, min, min,max-0.05F, min+0.05F, 0.95F);
				renderer.renderStandardBlock(materialBlock, x, y, z);
			}
		}


		renderer.renderAllFaces = false;
		GL11.glPopMatrix();
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
		Block materialBlock;

		if(meta < 16)
		{
			materialBlock = TFCBlocks.WoodSupportH;
		}
		else
		{
			materialBlock = TFCBlocks.WoodSupportH2;
		}
		
		GL11.glPushMatrix();
		GL11.glRotatef(180, 0, 1, 0);
		//Arms
		renderer.setRenderBounds(min+0.1F, min, min+0.75F, max-0.8F, max, max-0.15F);
		rotate(renderer, 1);
		renderInvBlock(materialBlock, meta, renderer);
		
		renderer.setRenderBounds(min+0.8F, min, min+0.75F, max-0.1F, max, max-0.15F);
		rotate(renderer, 1);
		renderInvBlock(materialBlock, meta, renderer);
		
		//Arm holding sections
		//L
		renderer.setRenderBounds(min+0.1F, min+0.35F, min+0.6F, max-0.8F, max-0.6F, max-0.25F);
		rotate(renderer, 1);
		renderInvBlock(materialBlock, meta, renderer);
		
		renderer.setRenderBounds(min+0.1F, min+0.15F, min+0.6F, max-0.8F, max-0.8F, max-0.25F);
		rotate(renderer, 1);
		renderInvBlock(materialBlock, meta, renderer);
		
		//R
		renderer.setRenderBounds(min+0.8F, min+0.35F, min+0.6F, max-0.1F, max-0.6F, max-0.25F);
		rotate(renderer, 1);
		renderInvBlock(materialBlock, meta, renderer);
		
		renderer.setRenderBounds(min+0.8F, min+0.15F, min+0.6F, max-0.1F, max-0.8F, max-0.25F);
		rotate(renderer, 1);
		renderInvBlock(materialBlock, meta, renderer);
		
		//cross
		renderer.setRenderBounds(max-0.8F, min+0.8F, min+0.75F, min+0.8F, max-0.1F, max-0.15F);
		rotate(renderer, 1);
		renderInvBlock(materialBlock, meta, renderer);
		
		renderer.setRenderBounds(max-0.8F, 0F, min+0.75F, min+0.8F, min+0.1F, max-0.15F);
		rotate(renderer, 1);
		renderInvBlock(materialBlock, meta, renderer);
		GL11.glPopMatrix();
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

	public static void renderInvBlockHoop(Block block, int m, RenderBlocks renderer)
	{
		Tessellator var14 = Tessellator.instance;
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		var14.startDrawingQuads();
		var14.setNormal(0.0F, -1.0F, 0.0F);
		renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(10, m));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(0.0F, 1.0F, 0.0F);
		renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(11, m));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(0.0F, 0.0F, -1.0F);
		renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(12, m));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(0.0F, 0.0F, 1.0F);
		renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(13, m));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(-1.0F, 0.0F, 0.0F);
		renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(14, m));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(1.0F, 0.0F, 0.0F);
		renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(15, m));
		var14.draw();
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
	}
}
