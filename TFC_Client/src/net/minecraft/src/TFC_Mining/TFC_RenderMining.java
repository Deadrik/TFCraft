package net.minecraft.src.TFC_Mining;

import net.minecraft.src.Block;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.Tessellator;
import net.minecraft.src.TFC_Core.*;

public class TFC_RenderMining
{
	
	public static boolean RenderSluice(Block block, int i, int j, int k, RenderBlocks renderblocks)
	{
		IBlockAccess blockAccess = renderblocks.blockAccess;
		Tessellator tessellator = Tessellator.instance;
		int l = blockAccess.getBlockMetadata(i, j, k);
		int i1 = ((BlockTerraSluice)block).getDirectionFromMetadata(l);
		float f = 0.5F;
		float f1 = 1.0F;
		float f2 = 0.8F;
		float f3 = 0.6F;
		int j1 = block.getMixedBrightnessForBlock(blockAccess, i, j, k);
		tessellator.setBrightness(j1);
		tessellator.setColorOpaque_F(f, f, f);
		int k1 = block.getBlockTexture(blockAccess, i, j, k, 0);
		int l1 = (k1 & 0xf) << 4;
		int i2 = k1 & 0xf0;
		double d = (float)l1 / 256F;
		double d1 = ((double)(l1 + 16) - 0.01D) / 256D;
		double d2 = (float)i2 / 256F;
		double d3 = ((double)(i2 + 16) - 0.01D) / 256D;

		double minX = (double)i + block.minX;
		double maxX = (double)i + block.maxX;
		double minY = (double)j + block.minY;
		double minZ = (double)k + block.minZ;
		double maxZ = (double)k + block.maxZ;
		double maxY = (double)j + block.maxY;

		//render ramp
		if(!((BlockTerraSluice)block).isBlockFootOfBed(l))
		{
			if(i1 == 0)
			{
				//ribs
				block.setBlockBounds(0.0F, 0.0F, 0.75F, 1F, 0.75F, 0.8F);
				renderblocks.renderStandardBlock(block, i, j, k);
				block.setBlockBounds(0.0F, 0.0F, 0.45F, 1F, 0.9F, 0.5F);
				renderblocks.renderStandardBlock(block, i, j, k);

				tessellator.setColorOpaque_F(f * f1, f * f2, f * f3);
				tessellator.addVertexWithUV(minX, maxY, minZ, d1, d2);//d,d3
				tessellator.addVertexWithUV(minX, minY+0.5F, maxZ, d1, d3);//d,d2
				tessellator.addVertexWithUV(maxX, minY+0.5f, maxZ, d, d3);//d1,d2
				tessellator.addVertexWithUV(maxX, maxY, minZ, d, d2);//d1,d3
				if(((BlockTerraSluice)block).getIsRecievingWater(l))
				{
					//get water texture
					k1 = block.getBlockTextureFromSideAndMetadata(0, 4);
					l1 = (k1 & 0xf) << 4;
					i2 = k1 & 0xf0;
					l = block.colorMultiplier(blockAccess, i, j, k);
					//reassign the uv coords
					d = (float)l1 / 256F;
					d1 = ((double)(l1 + 16) - 0.01D) / 256D;
					d2 = (float)i2 / 256F;
					d3 = ((double)(i2 + 16) - 0.01D) / 256D;

					//draw water plane
					tessellator.setColorOpaque_F(0.8F, 0.8F, 0.8F);
					tessellator.addVertexWithUV(minX, maxY, minZ, d1, d2);//d,d3
					tessellator.addVertexWithUV(minX, minY+0.6F, maxZ, d1, d3);//d,d2
					tessellator.addVertexWithUV(maxX, minY+0.6f, maxZ, d, d3);//d1,d2
					tessellator.addVertexWithUV(maxX, maxY, minZ, d, d2);//d1,d3
				}
			}
			if(i1 == 1)
			{
				//ribs
				block.setBlockBounds(0.2F, 0.0F, 0.0F, 0.25F, 0.75F, 1.0F);
				renderblocks.renderStandardBlock(block, i, j, k);
				block.setBlockBounds(0.5F, 0.0F, 0.0F, 0.55F, 0.9F, 1.0F);
				renderblocks.renderStandardBlock(block, i, j, k);

				tessellator.setColorOpaque_F(f * f1, f * f2, f * f3);
				tessellator.addVertexWithUV(minX, minY+0.5F, maxZ, d, d3);
				tessellator.addVertexWithUV(maxX, maxY, maxZ, d, d2);
				tessellator.addVertexWithUV(maxX, maxY, minZ, d1, d2);
				tessellator.addVertexWithUV(minX, minY+0.5F, minZ, d1, d3);

				if(((BlockTerraSluice)block).getIsRecievingWater(l))
				{
					//get water texture
					k1 = block.getBlockTextureFromSideAndMetadata(0, 4);
					l1 = (k1 & 0xf) << 4;
					i2 = k1 & 0xf0;
					l = block.colorMultiplier(blockAccess, i, j, k);
					//reassign the uv coords
					d = (float)l1 / 256F;
					d1 = ((double)(l1 + 16) - 0.01D) / 256D;
					d2 = (float)i2 / 256F;
					d3 = ((double)(i2 + 16) - 0.01D) / 256D;

					//draw water plane
					tessellator.setColorOpaque_F(0.8F, 0.8F, 0.8F);
					tessellator.addVertexWithUV(minX, minY+0.6F, maxZ, d, d3);
					tessellator.addVertexWithUV(maxX, maxY, maxZ, d, d2);
					tessellator.addVertexWithUV(maxX, maxY, minZ, d1, d2);
					tessellator.addVertexWithUV(minX, minY+0.6F, minZ, d1, d3);
				}
			}
			if(i1 == 2)
			{
				//ribs
				block.setBlockBounds(0.0F, 0.0F, 0.2F, 1F, 0.75F, 0.25F);
				renderblocks.renderStandardBlock(block, i, j, k);
				block.setBlockBounds(0.0F, 0.0F, 0.5F, 1F, 0.9F, 0.55F);
				renderblocks.renderStandardBlock(block, i, j, k);

				tessellator.setColorOpaque_F(f * f1, f * f2, f * f3);
				tessellator.addVertexWithUV(minX, minY+0.5F, minZ, d, d3);
				tessellator.addVertexWithUV(minX, maxY, maxZ, d, d2);
				tessellator.addVertexWithUV(maxX, maxY, maxZ, d1, d2);
				tessellator.addVertexWithUV(maxX, minY+0.5F, minZ, d1, d3);

				if(((BlockTerraSluice)block).getIsRecievingWater(l))
				{
					//get water texture
					k1 = block.getBlockTextureFromSideAndMetadata(0, 4);
					l1 = (k1 & 0xf) << 4;
					i2 = k1 & 0xf0;
					l = block.colorMultiplier(blockAccess, i, j, k);
					//reassign the uv coords
					d = (float)l1 / 256F;
					d1 = ((double)(l1 + 16) - 0.01D) / 256D;
					d2 = (float)i2 / 256F;
					d3 = ((double)(i2 + 16) - 0.01D) / 256D;

					//draw water plane
					tessellator.setColorOpaque_F(0.8F, 0.8F, 0.8F);
					tessellator.addVertexWithUV(minX, minY+0.6F, minZ, d, d3);
					tessellator.addVertexWithUV(minX, maxY, maxZ, d, d2);
					tessellator.addVertexWithUV(maxX, maxY, maxZ, d1, d2);
					tessellator.addVertexWithUV(maxX, minY+0.6F, minZ, d1, d3);
				}


			}
			if(i1 == 3)
			{        
				//ribs
				block.setBlockBounds(0.75F, 0.0F, 0.0F, 0.8F, 0.75F, 1.0F);
				renderblocks.renderStandardBlock(block, i, j, k);
				block.setBlockBounds(0.45F, 0.0F, 0.0F, 0.5F, 0.9F, 1.0F);
				renderblocks.renderStandardBlock(block, i, j, k);

				tessellator.setColorOpaque_F(f * f1, f * f2, f * f3);
				tessellator.addVertexWithUV(maxX, minY+0.5f, minZ, d, d3);
				tessellator.addVertexWithUV(minX, maxY, minZ, d, d2);
				tessellator.addVertexWithUV(minX, maxY, maxZ, d1, d2);
				tessellator.addVertexWithUV(maxX, minY+0.5F, maxZ, d1, d3);

				if(((BlockTerraSluice)block).getIsRecievingWater(l))
				{
					//get water texture
					k1 = 223;
					l1 = (k1 & 0xf) << 4;
					i2 = k1 & 0xf0;
					l = block.colorMultiplier(blockAccess, i, j, k);
					//reassign the uv coords
					d = (float)l1 / 256F;
					d1 = ((double)(l1 + 16) - 0.01D) / 256D;
					d2 = (float)i2 / 256F;
					d3 = ((double)(i2 + 16) - 0.01D) / 256D;

					tessellator.setColorOpaque_F(0.8F, 0.8F, 0.8F);
					tessellator.addVertexWithUV(maxX, minY+0.6f, minZ, d, d3);
					tessellator.addVertexWithUV(minX, maxY, minZ, d, d2);
					tessellator.addVertexWithUV(minX, maxY, maxZ, d1, d2);
					tessellator.addVertexWithUV(maxX, minY+0.6F, maxZ, d1, d3);
				}
			}
		}
		else
		{
			if(i1 == 0)
			{
				//ribs
				block.setBlockBounds(0.0F, 0.0F, 0.70F, 1F, 0.3F, 0.75F);
				renderblocks.renderStandardBlock(block, i, j, k);
				block.setBlockBounds(0.0F, 0.0F, 0.4F, 1F, 0.45F, 0.45F);
				renderblocks.renderStandardBlock(block, i, j, k);
				block.setBlockBounds(0.0F, 0.0F, 0.1F, 1F, 0.6F, 0.15F);
				renderblocks.renderStandardBlock(block, i, j, k);

				tessellator.setColorOpaque_F(f * f1, f * f2, f * f3);
				tessellator.addVertexWithUV(minX, minY+0.5F, minZ, d1, d2);//d,d3
				tessellator.addVertexWithUV(minX, minY, maxZ, d1, d3);//d,d2
				tessellator.addVertexWithUV(maxX, minY, maxZ, d, d3);//d1,d2
				tessellator.addVertexWithUV(maxX, minY+0.5F, minZ, d, d2);//d1,d3

				if(((BlockTerraSluice)block).getIsRecievingWater(l))
				{
					//get water texture
					k1 = block.getBlockTextureFromSideAndMetadata(0, 4);
					l1 = (k1 & 0xf) << 4;
					i2 = k1 & 0xf0;
					l = block.colorMultiplier(blockAccess, i, j, k);
					//reassign the uv coords
					d = (float)l1 / 256F;
					d1 = ((double)(l1 + 16) - 0.01D) / 256D;
					d2 = (float)i2 / 256F;
					d3 = ((double)(i2 + 16) - 0.01D) / 256D;

					//draw water plane
					tessellator.setColorOpaque_F(0.8F, 0.8F, 0.8F);
					tessellator.addVertexWithUV(minX, minY+0.6F, minZ, d1, d2);//d,d3
					tessellator.addVertexWithUV(minX, minY, maxZ, d1, d3);//d,d2
					tessellator.addVertexWithUV(maxX, minY, maxZ, d, d3);//d1,d2
					tessellator.addVertexWithUV(maxX, minY+0.6F, minZ, d, d2);//d1,d3
				}
			}
			if(i1 == 1)
			{
				//ribs
				block.setBlockBounds(0.9F, 0.0F, 0.0F, 0.95F, 0.6F, 1.0F);
				renderblocks.renderStandardBlock(block, i, j, k);
				block.setBlockBounds(0.6F, 0.0F, 0.0F, 0.65F, 0.45F, 1.0F);
				renderblocks.renderStandardBlock(block, i, j, k);
				block.setBlockBounds(0.3F, 0.0F, 0.0F, 0.35F, 0.3F, 1.0F);
				renderblocks.renderStandardBlock(block, i, j, k);

				tessellator.setColorOpaque_F(f * f1, f * f2, f * f3);
				tessellator.addVertexWithUV(minX, minY, maxZ, d, d3);
				tessellator.addVertexWithUV(maxX, minY+0.5F, maxZ, d, d2);
				tessellator.addVertexWithUV(maxX, minY+0.5F, minZ, d1, d2);
				tessellator.addVertexWithUV(minX, minY, minZ, d1, d3);

				if(((BlockTerraSluice)block).getIsRecievingWater(l))
				{
					//get water texture
					k1 = block.getBlockTextureFromSideAndMetadata(0, 4);
					l1 = (k1 & 0xf) << 4;
					i2 = k1 & 0xf0;
					l = block.colorMultiplier(blockAccess, i, j, k);
					//reassign the uv coords
					d = (float)l1 / 256F;
					d1 = ((double)(l1 + 16) - 0.01D) / 256D;
					d2 = (float)i2 / 256F;
					d3 = ((double)(i2 + 16) - 0.01D) / 256D;

					//draw water plane
					tessellator.setColorOpaque_F(0.8F, 0.8F, 0.8F);
					tessellator.addVertexWithUV(minX, minY, maxZ, d, d3);
					tessellator.addVertexWithUV(maxX, minY+0.6F, maxZ, d, d2);
					tessellator.addVertexWithUV(maxX, minY+0.6F, minZ, d1, d2);
					tessellator.addVertexWithUV(minX, minY, minZ, d1, d3);
				}
			}
			if(i1 == 2)
			{        		
				//ribs
				block.setBlockBounds(0.0F, 0.0F, 0.3F, 1F, 0.3F, 0.35F);
				renderblocks.renderStandardBlock(block, i, j, k);
				block.setBlockBounds(0.0F, 0.0F, 0.6F, 1F, 0.45F, 0.65F);
				renderblocks.renderStandardBlock(block, i, j, k);
				block.setBlockBounds(0.0F, 0.0F, 0.9F, 1F, 0.6F, 0.95F);
				renderblocks.renderStandardBlock(block, i, j, k);

				tessellator.setColorOpaque_F(f * f1, f * f2, f * f3);
				tessellator.addVertexWithUV(minX, minY, minZ, d, d3);
				tessellator.addVertexWithUV(minX, minY+0.5f, maxZ, d, d2);
				tessellator.addVertexWithUV(maxX, minY+0.5f, maxZ, d1, d2);
				tessellator.addVertexWithUV(maxX, minY, minZ, d1, d3);

				if(((BlockTerraSluice)block).getIsRecievingWater(l))
				{
					//get water texture
					k1 = 223;
					l1 = (k1 & 0xf) << 4;
					i2 = k1 & 0xf0;
					l = block.colorMultiplier(blockAccess, i, j, k);
					//reassign the uv coords
					d = (float)l1 / 256F;
					d1 = ((double)(l1 + 16) - 0.01D) / 256D;
					d2 = (float)i2 / 256F;
					d3 = ((double)(i2 + 16) - 0.01D) / 256D;

					tessellator.setColorOpaque_F(0.8F, 0.8F, 0.8F);
					tessellator.addVertexWithUV(minX, minY, minZ, d, d3);
					tessellator.addVertexWithUV(minX, minY+0.6f, maxZ, d, d2);
					tessellator.addVertexWithUV(maxX, minY+0.6f, maxZ, d1, d2);
					tessellator.addVertexWithUV(maxX, minY, minZ, d1, d3);
				}
			}
			if(i1 == 3)
			{        		
				//ribs
				block.setBlockBounds(0.7F, 0.0F, 0.0F, 0.75F, 0.3F, 1.0F);
				renderblocks.renderStandardBlock(block, i, j, k);
				block.setBlockBounds(0.4F, 0.0F, 0.0F, 0.45F, 0.45F, 1.0F);
				renderblocks.renderStandardBlock(block, i, j, k);
				block.setBlockBounds(0.1F, 0.0F, 0.0F, 0.15F, 0.6F, 1.0F);
				renderblocks.renderStandardBlock(block, i, j, k);

				tessellator.setColorOpaque_F(f * f1, f * f2, f * f3);
				tessellator.addVertexWithUV(maxX, minY, minZ, d, d3);
				tessellator.addVertexWithUV(minX, minY+0.5f, minZ, d, d2);
				tessellator.addVertexWithUV(minX, minY+0.5f, maxZ, d1, d2);
				tessellator.addVertexWithUV(maxX, minY, maxZ, d1, d3);

				if(((BlockTerraSluice)block).getIsRecievingWater(l))
				{
					//get water texture
					k1 = 223;
					l1 = (k1 & 0xf) << 4;
					i2 = k1 & 0xf0;
					l = block.colorMultiplier(blockAccess, i, j, k);
					//reassign the uv coords
					d = (float)l1 / 256F;
					d1 = ((double)(l1 + 16) - 0.01D) / 256D;
					d2 = (float)i2 / 256F;
					d3 = ((double)(i2 + 16) - 0.01D) / 256D;
					tessellator.setColorOpaque_F(0.8F, 0.8F, 0.8F);
					tessellator.addVertexWithUV(maxX, minY, minZ, d, d3);
					tessellator.addVertexWithUV(minX, minY+0.6f, minZ, d, d2);
					tessellator.addVertexWithUV(minX, minY+0.6f, maxZ, d1, d2);
					tessellator.addVertexWithUV(maxX, minY, maxZ, d1, d3);
				}
			}
		}


		//set the block collision box
		block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);

		return true;
	}
}
