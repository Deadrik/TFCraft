package com.bioxx.tfc.Render.Blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.TileEntities.TEBarrel;
import com.bioxx.tfc.api.TFCBlocks;

public class RenderBarrel implements ISimpleBlockRenderingHandler
{
	private static final float MIN = 0.1F;
	private static final float MAX = 0.9F;

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
	{
		TEBarrel te = (TEBarrel) world.getTileEntity(x, y, z);
		Block planksBlock;
		Block lidBlock;
		if(te.barrelType < 16)
		{
			planksBlock = TFCBlocks.planks;
			lidBlock = TFCBlocks.woodSupportH;
		}
		else
		{
			planksBlock = TFCBlocks.planks2;
			lidBlock = TFCBlocks.woodSupportH2;
		}
		renderer.renderAllFaces = true;

		if((te.rotation & -128) == 0)
		{
			if(te.getSealed())
			{
				renderer.setRenderBounds(MIN+0.05F, MIN, MIN+0.05F, MAX-0.05F, 0.95F, MAX-0.05F);
				renderer.renderStandardBlock(lidBlock, x, y, z);
			}
			else
			{
				renderer.setRenderBounds(MIN+0.05F, MIN, MIN+0.05F, MAX-0.05F, MIN+0.05F, MAX-0.05F);
				renderer.renderStandardBlock(lidBlock, x, y, z);

				if(te.fluid != null && renderer.overrideBlockTexture == null)
				{
					int color = te.fluid.getFluid().getColor(te.fluid);
					float f = (color >> 16 & 255) / 255.0F;
					float f1 = (color >> 8 & 255) / 255.0F;
					float f2 = (color & 255) / 255.0F;
					float h = 0.75f*(te.fluid.amount/10000f);
					renderer.setRenderBounds(MIN+0.05F, MIN+0.05, MIN+0.05F, MAX-0.05F, MIN+0.05f+h, MAX-0.05F);
					IIcon still = te.fluid.getFluid().getStillIcon();
					renderer.setOverrideBlockTexture(still);
					renderer.renderStandardBlockWithColorMultiplier(lidBlock, x, y, z, f, f1, f2);
					renderer.clearOverrideBlockTexture();
				}
			}
			renderer.setRenderBounds(MIN, 0F, MIN+0.05F, MIN+0.05F, 1F, MAX-0.05F);
			rotate(renderer, 1);
			renderer.renderStandardBlock(planksBlock, x, y, z);

			renderer.setRenderBounds(MAX-0.05F, 0F, MIN+0.05F, MAX, 1F, MAX-0.05F);
			rotate(renderer, 1);
			renderer.renderStandardBlock(planksBlock, x, y, z);

			renderer.setRenderBounds(MIN, 0F, MIN, MAX, 1F, MIN+0.05F);
			rotate(renderer, 1);
			renderer.renderStandardBlock(planksBlock, x, y, z);

			renderer.setRenderBounds(MIN, 0F, MAX-0.05F, MAX, 1F, MAX);
			rotate(renderer, 1);
			renderer.renderStandardBlock(planksBlock, x, y, z);

			renderer.setRenderBounds(MIN-0.001, 0F, MIN-0.001, MAX+0.001, 1F, MAX+0.001);
			rotate(renderer, 0);
			renderer.renderStandardBlock(block, x, y, z);
		}
		else
		{
			if((te.rotation & 3) == 0)
			{
				renderer.setRenderBounds(MIN, MIN, MIN+0.05F, 0.95F, MIN+0.05F, MAX-0.05F);
				renderer.renderStandardBlock(lidBlock, x, y, z);
			}
			if((te.rotation & 3) == 1)
			{
				renderer.setRenderBounds(MIN+0.05F, MIN, MIN,MAX-0.05F, MIN+0.05F, 0.95F);
				renderer.renderStandardBlock(lidBlock, x, y, z);
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
		Block lidBlock;

		if(meta < 16)
		{
			lidBlock = TFCBlocks.woodSupportH;
		}
		else
		{
			lidBlock = TFCBlocks.woodSupportH2;
		}

		renderer.setRenderBounds(MIN+0.05F, MIN, MIN+0.05F, MAX-0.05F, 0.95F, MAX-0.05F);
		rotate(renderer, 1);
		renderInvBlock(lidBlock, meta, renderer);

		renderer.setRenderBounds(MIN, 0F, MIN+0.05F, MIN+0.05F, 1F, MAX-0.05F);
		rotate(renderer, 1);
		renderInvBlock(block, meta, renderer);
		rotate(renderer, 0);
		renderInvBlockHoop(block, meta, renderer);

		renderer.setRenderBounds(MAX-0.05F, 0F, MIN+0.05F, MAX, 1F, MAX-0.05F);
		rotate(renderer, 1);
		renderInvBlock(block, meta, renderer);
		rotate(renderer, 0);
		renderInvBlockHoop(block, meta, renderer);

		renderer.setRenderBounds(MIN, 0F, MIN, MAX, 1F, MIN+0.05F);
		rotate(renderer, 1);
		renderInvBlock(block, meta, renderer);
		rotate(renderer, 0);
		renderInvBlockHoop(block, meta, renderer);

		renderer.setRenderBounds(MIN, 0F, MAX-0.05F, MAX, 1F, MAX);
		rotate(renderer, 1);
		renderInvBlock(block, meta, renderer);
		rotate(renderer, 0);
		renderInvBlockHoop(block, meta, renderer);
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
