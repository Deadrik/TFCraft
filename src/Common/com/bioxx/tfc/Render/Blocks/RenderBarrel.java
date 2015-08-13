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
	static float min = 0.1F;
	static float max = 0.9F;

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
	{
		TEBarrel te = (TEBarrel) world.getTileEntity(x, y, z);
		Block planksBlock;
		Block lidBlock;
		if(te.barrelType < 16)
		{
			planksBlock = TFCBlocks.Planks;
			lidBlock = TFCBlocks.WoodSupportH;
		}
		else
		{
			planksBlock = TFCBlocks.Planks2;
			lidBlock = TFCBlocks.WoodSupportH2;
		}
		renderer.renderAllFaces = true;

		if((te.rotation & -128) == 0)
		{
			if(te.getSealed())
			{
				renderer.setRenderBounds(min+0.05F, min, min+0.05F, max-0.05F, 0.95F, max-0.05F);
				renderer.renderStandardBlock(lidBlock, x, y, z);
			}
			else
			{
				renderer.setRenderBounds(min+0.05F, min, min+0.05F, max-0.05F, min+0.05F, max-0.05F);
				renderer.renderStandardBlock(lidBlock, x, y, z);

				if(te.fluid != null && renderer.overrideBlockTexture == null)
				{
					int color = te.fluid.getFluid().getColor(te.fluid);
					float f = (color >> 16 & 255) / 255.0F;
					float f1 = (color >> 8 & 255) / 255.0F;
					float f2 = (color & 255) / 255.0F;
					float h = 0.75f*(te.fluid.amount/10000f);
					renderer.setRenderBounds(min+0.05F, min+0.05, min+0.05F, max-0.05F, min+0.05f+h, max-0.05F);
					IIcon still = te.fluid.getFluid().getStillIcon();
					renderer.setOverrideBlockTexture(still);
					renderer.renderStandardBlockWithColorMultiplier(lidBlock, x, y, z, f, f1, f2);
					renderer.clearOverrideBlockTexture();
				}
			}
			renderer.setRenderBounds(min, 0F, min+0.05F, min+0.05F, 1F, max-0.05F);
			rotate(renderer, 1);
			renderer.renderStandardBlock(planksBlock, x, y, z);

			renderer.setRenderBounds(max-0.05F, 0F, min+0.05F, max, 1F, max-0.05F);
			rotate(renderer, 1);
			renderer.renderStandardBlock(planksBlock, x, y, z);

			renderer.setRenderBounds(min, 0F, min, max, 1F, min+0.05F);
			rotate(renderer, 1);
			renderer.renderStandardBlock(planksBlock, x, y, z);

			renderer.setRenderBounds(min, 0F, max-0.05F, max, 1F, max);
			rotate(renderer, 1);
			renderer.renderStandardBlock(planksBlock, x, y, z);

			renderer.setRenderBounds(min-0.001, 0F, min-0.001, max+0.001, 1F, max+0.001);
			rotate(renderer, 0);
			renderer.renderStandardBlock(block, x, y, z);
		}
		else
		{
			if((te.rotation & 3) == 0)
			{
				renderer.setRenderBounds(min, min, min+0.05F, 0.95F, min+0.05F, max-0.05F);
				renderer.renderStandardBlock(lidBlock, x, y, z);
			}
			if((te.rotation & 3) == 1)
			{
				renderer.setRenderBounds(min+0.05F, min, min,max-0.05F, min+0.05F, 0.95F);
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
			lidBlock = TFCBlocks.WoodSupportH;
		}
		else
		{
			lidBlock = TFCBlocks.WoodSupportH2;
		}

		renderer.setRenderBounds(min+0.05F, min, min+0.05F, max-0.05F, 0.95F, max-0.05F);
		rotate(renderer, 1);
		renderInvBlock(lidBlock, meta, renderer);

		renderer.setRenderBounds(min, 0F, min+0.05F, min+0.05F, 1F, max-0.05F);
		rotate(renderer, 1);
		renderInvBlock(block, meta, renderer);
		rotate(renderer, 0);
		renderInvBlockHoop(block, meta, renderer);

		renderer.setRenderBounds(max-0.05F, 0F, min+0.05F, max, 1F, max-0.05F);
		rotate(renderer, 1);
		renderInvBlock(block, meta, renderer);
		rotate(renderer, 0);
		renderInvBlockHoop(block, meta, renderer);

		renderer.setRenderBounds(min, 0F, min, max, 1F, min+0.05F);
		rotate(renderer, 1);
		renderInvBlock(block, meta, renderer);
		rotate(renderer, 0);
		renderInvBlockHoop(block, meta, renderer);

		renderer.setRenderBounds(min, 0F, max-0.05F, max, 1F, max);
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
