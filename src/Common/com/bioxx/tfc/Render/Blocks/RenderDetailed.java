package com.bioxx.tfc.Render.Blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;

import com.bioxx.tfc.Render.RenderBlocksFixUV;
import com.bioxx.tfc.TileEntities.TEDetailed;

public class RenderDetailed 
{
	private static RenderBlocksFixUV renderer;

	public static boolean renderBlockDetailed(Block block, int i, int j, int k, RenderBlocks renderblocks)
	{
		TEDetailed te = (TEDetailed) renderblocks.blockAccess.getTileEntity(i, j, k);
		int md = renderblocks.blockAccess.getBlockMetadata(i, j, k);

		if(renderer == null)
			renderer = new RenderBlocksFixUV(renderblocks);
		else
			renderer.update(renderblocks);

		if(te.TypeID <= 0)
			return false;

		int type = te.TypeID;
		int meta = te.MetaID;

		boolean breaking = false;
		if(renderer.overrideBlockTexture != null)
			breaking = true;
		else
			renderer.setOverrideBlockTexture(Block.getBlockById(te.TypeID).getIcon(0, te.MetaID));

		int l = block.colorMultiplier(renderblocks.blockAccess, i, j, k);
		float f = (l >> 16 & 255) / 255.0F;
		float f1 = (l >> 8 & 255) / 255.0F;
		float f2 = (l & 255) / 255.0F;

		for(int subX = 0; subX < 2; subX++)
		{
			for(int subZ = 0; subZ < 2; subZ++)
			{
				for(int subY = 0; subY < 2; subY++)
				{
					if(!te.isQuadSolid(subX, subY, subZ))
					{
						renderMiniBlock(block, i, j, k, subX, subY, subZ, renderer, te, type, meta);
					}
					else
					{
						float minX = 0.5f * subX;
						float maxX = minX + 0.5f;
						float minY = 0.5f * subY;
						float maxY = minY + 0.5f;
						float minZ = 0.5f * subZ;
						float maxZ = minZ + 0.5f;

						renderer.setRenderBounds(minX, minY, minZ, maxX, maxY, maxZ);
						renderer.renderStandardBlock(block, i, j, k);
					}
				}
			}
		}
		renderer.clearOverrideBlockTexture();
		return true;
	}

	private static void renderMiniBlock(Block block, int i, int j, int k, int x, int y, int z, RenderBlocks renderblocks, TEDetailed te, int type, int meta)
	{
		int l = block.colorMultiplier(renderblocks.blockAccess, i, j, k);
		float f = (l >> 16 & 255) / 255.0F;
		float f1 = (l >> 8 & 255) / 255.0F;
		float f2 = (l & 255) / 255.0F;

		for(int subX = x*4; subX < 4+x*4; subX++)
		{
			for(int subZ = z*4; subZ < 4+z*4; subZ++)
			{
				for(int subY = y*4; subY < 4+y*4; subY++)
				{
					boolean subExists = isOpaque(te,subX, subY, subZ);
					if(subExists)
					{
						float minX = 0.125f*subX;
						float maxX = minX + 0.125f;
						float minY = 0.125f*subY;
						float maxY = minY + 0.125f;
						float minZ = 0.125f*subZ;
						float maxZ = minZ + 0.125f;

						renderblocks.setRenderBounds(minX, minY, minZ, maxX, maxY, maxZ);
						renderblocks.renderStandardBlock(block, i, j, k);
					}
				}
			}
		}
	}

	public static boolean renderStandardBlock(Block block, RenderBlocks renderblocks, int x, int y, int z, int meta)
	{
		renderblocks.enableAO = false;
		Tessellator tess = Tessellator.instance;

		float r = 0.5F;
		float g = 0.8F;
		float b = 0.6F;
		float var17 = r;
		float var18 = g;
		float var19 = b;
		float var20 = r;
		float var21 = g;
		float var22 = b;
		float var23 = r;
		float var24 = g;
		float var25 = b;

		int var26 = block.getMixedBrightnessForBlock(renderblocks.blockAccess, x, y, z);

		tess.setBrightness(renderblocks.renderMinY > 0.0D ? var26 : block.getMixedBrightnessForBlock(renderblocks.blockAccess, x, y - 1, z));
		tess.setColorOpaque_F(var17, var20, var23);
		renderblocks.renderFaceYNeg(block, x, y, z, block.getIcon(0, meta));

		tess.setBrightness(renderblocks.renderMaxY < 1.0D ? var26 : block.getMixedBrightnessForBlock(renderblocks.blockAccess, x, y + 1, z));
		tess.setColorOpaque_F(1, 1, 1);
		renderblocks.renderFaceYPos(block, x, y, z, block.getIcon(1, meta));

		tess.setBrightness(renderblocks.renderMaxX > 0.0D ? var26 : block.getMixedBrightnessForBlock(renderblocks.blockAccess, x+1, y, z));
		tess.setColorOpaque_F(var18, var21, var24);
		renderblocks.renderFaceXPos(block, x, y, z, block.getIcon(2, meta));

		tess.setBrightness(renderblocks.renderMinX < 1.0D ? var26 : block.getMixedBrightnessForBlock(renderblocks.blockAccess, x-1, y, z));
		tess.setColorOpaque_F(var18, var21, var24);
		renderblocks.renderFaceXNeg(block, x, y, z, block.getIcon(3, meta));

		tess.setBrightness(renderblocks.renderMinZ > 0.0D ? var26 : block.getMixedBrightnessForBlock(renderblocks.blockAccess, x, y, z-1));
		tess.setColorOpaque_F(var19, var22, var25);
		renderblocks.renderFaceZNeg(block, x, y, z, block.getIcon(4, meta));

		tess.setBrightness(renderblocks.renderMaxZ < 1.0D ? var26 : block.getMixedBrightnessForBlock(renderblocks.blockAccess, x, y, z+1));
		tess.setColorOpaque_F(var19, var22, var25);
		renderblocks.renderFaceZPos(block, x, y, z, block.getIcon(5, meta));

		return true;
	}

	public static boolean isOpaque(TEDetailed te, int x, int y, int z)
	{
		return te.data.get((x * 8 + z)*8 + y);
	}
}
