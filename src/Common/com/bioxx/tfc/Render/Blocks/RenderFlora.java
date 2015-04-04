package com.bioxx.tfc.Render.Blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;

import com.bioxx.tfc.Core.TFC_Core;

public class RenderFlora
{
	public static boolean render(Block block, int x, int y, int z, RenderBlocks renderer)
	{
		Block blockDirectlyAbove = renderer.blockAccess.getBlock(x, y + 1, z);
		boolean hasAirTwoAbove = renderer.blockAccess.getBlock(x, y + 2, z).isAir(renderer.blockAccess,x, y + 2, z);
		if(TFC_Core.isWater(blockDirectlyAbove))
		{
			if(TFC_Core.isFreshWater(blockDirectlyAbove))
			{
				if(hasAirTwoAbove)
				{
					renderCatTails(block,x,y + 1,z,renderer);
				}
				else
				{
					renderShortWaterPlant(block, x, y+ 1, z,renderer, 1);
				}
			}
			else if(TFC_Core.isSaltWater(blockDirectlyAbove)){
				renderShortWaterPlant(block, x, y + 1, z, renderer, 0);
			}
		}
		return true;
	}

	public static boolean renderShortWaterPlant(Block block, int x, int y, int z, RenderBlocks renderer, int plantType)
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
		double d1 = x;
		double d2 = y;
		double d0 = z;
		long i1;

		if (block == Blocks.tallgrass)
		{
			i1 = x * 3129871 ^ z * 116129781L ^ y;
			i1 = i1 * i1 * 42317861L + i1 * 11L;
			d1 += ((i1 >> 16 & 15L) / 15.0F - 0.5D) * 0.5D;
			d2 += ((i1 >> 20 & 15L) / 15.0F - 1.0D) * 0.2D;
			d0 += ((i1 >> 24 & 15L) / 15.0F - 0.5D) * 0.5D;
		}
		else if (block == Blocks.red_flower || block == Blocks.yellow_flower)
		{
			i1 = x * 3129871 ^ z * 116129781L ^ y;
			i1 = i1 * i1 * 42317861L + i1 * 11L;
			d1 += ((i1 >> 16 & 15L) / 15.0F - 0.5D) * 0.3D;
			d0 += ((i1 >> 24 & 15L) / 15.0F - 0.5D) * 0.3D;
		}

		IIcon iicon = block.getIcon(0, plantType);
		renderer.drawCrossedSquares(iicon, d1, d2, d0, 1.0F);
		return true;
	}

	public static void renderCatTails(Block block, int x, int y, int z, RenderBlocks renderer)
	{
		//Tessellator tessellator = Tessellator.instance;
		IIcon icon = block.getIcon(0, 2);
		renderer.drawCrossedSquares(icon, x, y, z, 2.0F);
		/*
		double minU = icon.getMinU();
		double minV = icon.getMinV();
		double maxU = icon.getMaxU();
		double maxV = icon.getMaxV();

		double minX = x + 0.5D - 0.25D;
		double maxX = x + 0.5D + 0.25D;
		double minZ = z + 0.5D - 0.75D;
		double maxZ = z + 0.5D + 0.75D;

		tessellator.addVertexWithUV(minX, y + 1.0D, minZ, minU, minV);
		tessellator.addVertexWithUV(minX, y - 1.0D, minZ, minU, maxV);
		tessellator.addVertexWithUV(minX, y - 1.0D, maxZ, maxU, maxV);
		tessellator.addVertexWithUV(minX, y + 1.0D, maxZ, maxU, minV);
		tessellator.addVertexWithUV(minX, y + 1.0D, maxZ, minU, minV);
		tessellator.addVertexWithUV(minX, y - 1.0D, maxZ, minU, maxV);
		tessellator.addVertexWithUV(minX, y - 1.0D, minZ, maxU, maxV);
		tessellator.addVertexWithUV(minX, y + 1.0D, minZ, maxU, minV);
		tessellator.addVertexWithUV(maxX, y + 1.0D, maxZ, minU, minV);
		tessellator.addVertexWithUV(maxX, y - 1.0D, maxZ, minU, maxV);
		tessellator.addVertexWithUV(maxX, y - 1.0D, minZ, maxU, maxV);
		tessellator.addVertexWithUV(maxX, y + 1.0D, minZ, maxU, minV);
		tessellator.addVertexWithUV(maxX, y + 1.0D, minZ, minU, minV);
		tessellator.addVertexWithUV(maxX, y - 1.0D, minZ, minU, maxV);
		tessellator.addVertexWithUV(maxX, y - 1.0D, maxZ, maxU, maxV);
		tessellator.addVertexWithUV(maxX, y + 1.0D, maxZ, maxU, minV);
		minX = x + 0.5D - 0.75D;
		maxX = x + 0.5D + 0.75D;
		minZ = z + 0.5D - 0.25D;
		maxZ = z + 0.5D + 0.25D;
		tessellator.addVertexWithUV(minX, y + 1.0D, minZ, minU, minV);
		tessellator.addVertexWithUV(minX, y - 1.0D, minZ, minU, maxV);
		tessellator.addVertexWithUV(maxX, y - 1.0D, minZ, maxU, maxV);
		tessellator.addVertexWithUV(maxX, y + 1.0D, minZ, maxU, minV);
		tessellator.addVertexWithUV(maxX, y + 1.0D, minZ, minU, minV);
		tessellator.addVertexWithUV(maxX, y - 1.0D, minZ, minU, maxV);
		tessellator.addVertexWithUV(minX, y - 1.0D, minZ, maxU, maxV);
		tessellator.addVertexWithUV(minX, y + 1.0D, minZ, maxU, minV);
		tessellator.addVertexWithUV(maxX, y + 1.0D, maxZ, minU, minV);
		tessellator.addVertexWithUV(maxX, y - 1.0D, maxZ, minU, maxV);
		tessellator.addVertexWithUV(minX, y - 1.0D, maxZ, maxU, maxV);
		tessellator.addVertexWithUV(minX, y + 1.0D, maxZ, maxU, minV);
		tessellator.addVertexWithUV(minX, y + 1.0D, maxZ, minU, minV);
		tessellator.addVertexWithUV(minX, y - 1.0D, maxZ, minU, maxV);
		tessellator.addVertexWithUV(maxX, y - 1.0D, maxZ, maxU, maxV);
		tessellator.addVertexWithUV(maxX, y + 1.0D, maxZ, maxU, minV);*/
	}
}
