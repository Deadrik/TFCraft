package com.bioxx.tfc.Render.Blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Blocks.Devices.BlockHopper;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderHopper implements ISimpleBlockRenderingHandler
{
	@Override
	public boolean shouldRender3DInInventory(int modelId)
	{
		return true;
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
	{


		renderer.clearOverrideBlockTexture();
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
	{
		Tessellator tessellator = Tessellator.instance;
		tessellator.setBrightness(block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z));
		int l = block.colorMultiplier(renderer.blockAccess, x, y, z);
		float f = (l >> 16 & 255) / 255.0F;
		float f1 = (l >> 8 & 255) / 255.0F;
		float f2 = (l & 255) / 255.0F;

		if (EntityRenderer.anaglyphEnable)
		{
			float f3 = (f * 30.0F + f1 * 59.0F + f2 * 11.0F) / 100.0F;
			float f4 = (f * 30.0F + f1 * 70.0F) / 100.0F;
			float f5 = (f * 30.0F + f2 * 70.0F) / 100.0F;
			f = f3;
			f1 = f4;
			f2 = f5;
		}

		tessellator.setColorOpaque_F(f, f1, f2);
		return this.renderBlockHopperMetadata(block, x, y, z, renderer.blockAccess.getBlockMetadata(x, y, z), false, renderer);
	}

	public boolean renderBlockHopperMetadata(Block block, int x, int y, int z, int meta, boolean unknownBool, RenderBlocks renderer)
	{
		Tessellator tessellator = Tessellator.instance;
		int i1 = BlockHopper.getDirectionFromMetadata(meta);
		double d0 = 0.625D;
		renderer.setRenderBounds(0.0D, d0, 0.0D, 1.0D, 1.0D, 1.0D);

		if (unknownBool)
		{
			tessellator.startDrawingQuads();
			tessellator.setNormal(0.0F, -1.0F, 0.0F);
			renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 0, meta));
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(0.0F, 1.0F, 0.0F);
			renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 1, meta));
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(0.0F, 0.0F, -1.0F);
			renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 2, meta));
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(0.0F, 0.0F, 1.0F);
			renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 3, meta));
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(-1.0F, 0.0F, 0.0F);
			renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 4, meta));
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(1.0F, 0.0F, 0.0F);
			renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 5, meta));
			tessellator.draw();
		}
		else
		{
			renderer.renderStandardBlock(block, x, y, z);
		}

		float f1;

		if (!unknownBool)
		{
			tessellator.setBrightness(block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z));
			int j1 = block.colorMultiplier(renderer.blockAccess, x, y, z);
			float f = (j1 >> 16 & 255) / 255.0F;
			f1 = (j1 >> 8 & 255) / 255.0F;
			float f2 = (j1 & 255) / 255.0F;

			if (EntityRenderer.anaglyphEnable)
			{
				float f3 = (f * 30.0F + f1 * 59.0F + f2 * 11.0F) / 100.0F;
				float f4 = (f * 30.0F + f1 * 70.0F) / 100.0F;
				float f5 = (f * 30.0F + f2 * 70.0F) / 100.0F;
				f = f3;
				f1 = f4;
				f2 = f5;
			}

			tessellator.setColorOpaque_F(f, f1, f2);
		}

		IIcon iicon = BlockHopper.getHopperIcon("hopper_outside");
		IIcon iicon1 = BlockHopper.getHopperIcon("hopper_inside");
		f1 = 0.125F;

		if (unknownBool)
		{
			tessellator.startDrawingQuads();
			tessellator.setNormal(1.0F, 0.0F, 0.0F);
			renderer.renderFaceXPos(block, -1.0F + f1, 0.0D, 0.0D, iicon);
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(-1.0F, 0.0F, 0.0F);
			renderer.renderFaceXNeg(block, 1.0F - f1, 0.0D, 0.0D, iicon);
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(0.0F, 0.0F, 1.0F);
			renderer.renderFaceZPos(block, 0.0D, 0.0D, -1.0F + f1, iicon);
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(0.0F, 0.0F, -1.0F);
			renderer.renderFaceZNeg(block, 0.0D, 0.0D, 1.0F - f1, iicon);
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(0.0F, 1.0F, 0.0F);
			renderer.renderFaceYPos(block, 0.0D, -1.0D + d0, 0.0D, iicon1);
			tessellator.draw();
		}
		else
		{
			renderer.renderFaceXPos(block, x - 1.0F + f1, y, z, iicon);
			renderer.renderFaceXNeg(block, x + 1.0F - f1, y, z, iicon);
			renderer.renderFaceZPos(block, x, y, z - 1.0F + f1, iicon);
			renderer.renderFaceZNeg(block, x, y, z + 1.0F - f1, iicon);
			renderer.renderFaceYPos(block, x, y - 1.0F + d0, z, iicon1);
		}

		renderer.setOverrideBlockTexture(iicon);
		double d3 = 0.25D;
		double d4 = 0.25D;
		renderer.setRenderBounds(d3, d4, d3, 1.0D - d3, d0 - 0.002D, 1.0D - d3);

		if (unknownBool)
		{
			tessellator.startDrawingQuads();
			tessellator.setNormal(1.0F, 0.0F, 0.0F);
			renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, iicon);
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(-1.0F, 0.0F, 0.0F);
			renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, iicon);
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(0.0F, 0.0F, 1.0F);
			renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, iicon);
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(0.0F, 0.0F, -1.0F);
			renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, iicon);
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(0.0F, 1.0F, 0.0F);
			renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, iicon);
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(0.0F, -1.0F, 0.0F);
			renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, iicon);
			tessellator.draw();
		}
		else
		{
			renderer.renderStandardBlock(block, x, y, z);
		}

		if (!unknownBool)
		{
			double d1 = 0.375D;
			double d2 = 0.25D;
			renderer.setOverrideBlockTexture(iicon);

			if (i1 == 0)
			{
				renderer.setRenderBounds(d1, 0.0D, d1, 1.0D - d1, 0.25D, 1.0D - d1);
				renderer.renderStandardBlock(block, x, y, z);
			}

			if (i1 == 2)
			{
				renderer.setRenderBounds(d1, d4, 0.0D, 1.0D - d1, d4 + d2, d3);
				renderer.renderStandardBlock(block, x, y, z);
			}

			if (i1 == 3)
			{
				renderer.setRenderBounds(d1, d4, 1.0D - d3, 1.0D - d1, d4 + d2, 1.0D);
				renderer.renderStandardBlock(block, x, y, z);
			}

			if (i1 == 4)
			{
				renderer.setRenderBounds(0.0D, d4, d1, d3, d4 + d2, 1.0D - d1);
				renderer.renderStandardBlock(block, x, y, z);
			}

			if (i1 == 5)
			{
				renderer.setRenderBounds(1.0D - d3, d4, d1, 1.0D, d4 + d2, 1.0D - d1);
				renderer.renderStandardBlock(block, x, y, z);
			}
		}

		renderer.clearOverrideBlockTexture();
		return true;
	}

	@Override
	public int getRenderId()
	{
		return 0;
	}

	public static void renderInvBlock(Block block, RenderBlocks renderer)
	{
		Tessellator tess = Tessellator.instance;
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		tess.startDrawingQuads();
		tess.setNormal(0.0F, -1.0F, 0.0F);
		renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(0, 2));
		tess.draw();
		tess.startDrawingQuads();
		tess.setNormal(0.0F, 1.0F, 0.0F);
		renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(0, 2));
		tess.draw();
		tess.startDrawingQuads();
		tess.setNormal(-1.0F, 0.0F, 0.0F);
		renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(0, 1));
		tess.draw();
		tess.startDrawingQuads();
		tess.setNormal(-1.0F, 0.0F, 0.0F);
		renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(0, 3));
		tess.draw();
		tess.startDrawingQuads();
		tess.setNormal(0.0F, 0.0F, -1.0F);
		renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(0, 0));
		tess.draw();
		tess.startDrawingQuads();
		tess.setNormal(0.0F, 0.0F, 1.0F);
		renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(0, 0));
		tess.draw();
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
	}
}
