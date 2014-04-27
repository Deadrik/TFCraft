package com.bioxx.tfc.Render.Blocks;

import com.bioxx.tfc.Blocks.Terrain.BlockGrass;
import com.bioxx.tfc.Render.RenderBlocksFixUV;
import com.bioxx.tfc.TileEntities.TileEntityDetailed;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;

public class RenderDetailed 
{
	private static RenderBlocksFixUV renderer;

	public static boolean renderBlockDetailed(Block block, int i, int j, int k, RenderBlocks renderblocks)
	{
		TileEntityDetailed te = (TileEntityDetailed) renderblocks.blockAccess.getTileEntity(i, j, k);
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
						//renderStandardBlockWithAmbientOcclusionPartial(Block.blocksList[type], renderer, i, j, k, f, f1, f2, meta);
					}
				}
			}
		}
		renderer.clearOverrideBlockTexture();
		return true;
	}

	private static void renderMiniBlock(Block block, int i, int j, int k, int x, int y, int z, RenderBlocks renderblocks, TileEntityDetailed te, int type, int meta)
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
						//renderStandardBlockWithAmbientOcclusionPartial(Block.blocksList[type], renderblocks, i, j, k, f, f1, f2, meta);
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

	public static boolean isOpaque(TileEntityDetailed te, int x, int y, int z)
	{
		return te.data.get((x * 8 + z)*8 + y);
	}

	public static boolean renderStandardBlockWithAmbientOcclusionPartial(Block block, RenderBlocks renderer, int par2, int par3, int par4, float par5, float par6, float par7, int meta)
	{
		renderer.enableAO = true;
		boolean flag = false;
		float f3 = 0.0F;
		float f4 = 0.0F;
		float f5 = 0.0F;
		float f6 = 0.0F;
		boolean flag1 = true;
		int l = block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3, par4);
		Tessellator tessellator = Tessellator.instance;
		tessellator.setBrightness(983055);

		if (renderer.getBlockIcon(block).getIconName().equals("grass_top"))
			flag1 = false;
		else if (renderer.hasOverrideBlockTexture())
			flag1 = false;

		boolean flag2;
		boolean flag3;
		boolean flag4;
		boolean flag5;
		float f7;
		int i1;

		if (renderer.renderAllFaces || block.shouldSideBeRendered(renderer.blockAccess, par2, par3 - 1, par4, 0))
		{
			if (renderer.renderMinY <= 0.0D)
				--par3;

			renderer.aoBrightnessXYNN = block.getMixedBrightnessForBlock(renderer.blockAccess, par2 - 1, par3, par4);
			renderer.aoBrightnessYZNN = block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3, par4 - 1);
			renderer.aoBrightnessYZNP = block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3, par4 + 1);
			renderer.aoBrightnessXYPN = block.getMixedBrightnessForBlock(renderer.blockAccess, par2 + 1, par3, par4);
			renderer.aoLightValueScratchXYNN = renderer.blockAccess.getBlock(par2 - 1, par3, par4).getAmbientOcclusionLightValue();
			renderer.aoLightValueScratchYZNN = renderer.blockAccess.getBlock(par2, par3, par4 - 1).getAmbientOcclusionLightValue();
			renderer.aoLightValueScratchYZNP = renderer.blockAccess.getBlock(par2, par3, par4 + 1).getAmbientOcclusionLightValue();
			renderer.aoLightValueScratchXYPN = renderer.blockAccess.getBlock(par2 + 1, par3, par4).getAmbientOcclusionLightValue();
			flag3 = renderer.blockAccess.getBlock(par2 + 1, par3 - 1, par4).getCanBlockGrass();
			flag2 = renderer.blockAccess.getBlock(par2 - 1, par3 - 1, par4).getCanBlockGrass();
			flag5 = renderer.blockAccess.getBlock(par2, par3 - 1, par4 + 1).getCanBlockGrass();
			flag4 = renderer.blockAccess.getBlock(par2, par3 - 1, par4 - 1).getCanBlockGrass();

			if (!flag4 && !flag2)
			{
				renderer.aoLightValueScratchXYZNNN = renderer.aoLightValueScratchXYNN;
				renderer.aoBrightnessXYZNNN = renderer.aoBrightnessXYNN;
			}
			else
			{
				renderer.aoLightValueScratchXYZNNN = renderer.blockAccess.getBlock(par2 - 1, par3, par4 - 1).getAmbientOcclusionLightValue();
				renderer.aoBrightnessXYZNNN = block.getMixedBrightnessForBlock(renderer.blockAccess, par2 - 1, par3, par4 - 1);
			}

			if (!flag5 && !flag2)
			{
				renderer.aoLightValueScratchXYZNNP = renderer.aoLightValueScratchXYNN;
				renderer.aoBrightnessXYZNNP = renderer.aoBrightnessXYNN;
			}
			else
			{
				renderer.aoLightValueScratchXYZNNP = renderer.blockAccess.getBlock(par2 - 1, par3, par4 + 1).getAmbientOcclusionLightValue();
				renderer.aoBrightnessXYZNNP = block.getMixedBrightnessForBlock(renderer.blockAccess, par2 - 1, par3, par4 + 1);
			}

			if (!flag4 && !flag3)
			{
				renderer.aoLightValueScratchXYZPNN = renderer.aoLightValueScratchXYPN;
				renderer.aoBrightnessXYZPNN = renderer.aoBrightnessXYPN;
			}
			else
			{
				renderer.aoLightValueScratchXYZPNN = renderer.blockAccess.getBlock(par2 + 1, par3, par4 - 1).getAmbientOcclusionLightValue();
				renderer.aoBrightnessXYZPNN = block.getMixedBrightnessForBlock(renderer.blockAccess, par2 + 1, par3, par4 - 1);
			}

			if (!flag5 && !flag3)
			{
				renderer.aoLightValueScratchXYZPNP = renderer.aoLightValueScratchXYPN;
				renderer.aoBrightnessXYZPNP = renderer.aoBrightnessXYPN;
			}
			else
			{
				renderer.aoLightValueScratchXYZPNP = renderer.blockAccess.getBlock(par2 + 1, par3, par4 + 1).getAmbientOcclusionLightValue();
				renderer.aoBrightnessXYZPNP = block.getMixedBrightnessForBlock(renderer.blockAccess, par2 + 1, par3, par4 + 1);
			}

			if (renderer.renderMinY <= 0.0D)
			{
				++par3;
			}

			i1 = l;

			if (renderer.renderMinY <= 0.0D || !renderer.blockAccess.getBlock(par2, par3 - 1, par4).isOpaqueCube())
			{
				i1 = block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3 - 1, par4);
			}

			f7 = renderer.blockAccess.getBlock(par2, par3 - 1, par4).getAmbientOcclusionLightValue();
			f3 = (renderer.aoLightValueScratchXYZNNP + renderer.aoLightValueScratchXYNN + renderer.aoLightValueScratchYZNP + f7) / 4.0F;
			f6 = (renderer.aoLightValueScratchYZNP + f7 + renderer.aoLightValueScratchXYZPNP + renderer.aoLightValueScratchXYPN) / 4.0F;
			f5 = (f7 + renderer.aoLightValueScratchYZNN + renderer.aoLightValueScratchXYPN + renderer.aoLightValueScratchXYZPNN) / 4.0F;
			f4 = (renderer.aoLightValueScratchXYNN + renderer.aoLightValueScratchXYZNNN + f7 + renderer.aoLightValueScratchYZNN) / 4.0F;
			renderer.brightnessTopLeft = renderer.getAoBrightness(renderer.aoBrightnessXYZNNP, renderer.aoBrightnessXYNN, renderer.aoBrightnessYZNP, i1);
			renderer.brightnessTopRight = renderer.getAoBrightness(renderer.aoBrightnessYZNP, renderer.aoBrightnessXYZPNP, renderer.aoBrightnessXYPN, i1);
			renderer.brightnessBottomRight = renderer.getAoBrightness(renderer.aoBrightnessYZNN, renderer.aoBrightnessXYPN, renderer.aoBrightnessXYZPNN, i1);
			renderer.brightnessBottomLeft = renderer.getAoBrightness(renderer.aoBrightnessXYNN, renderer.aoBrightnessXYZNNN, renderer.aoBrightnessYZNN, i1);

			if (flag1)
			{
				renderer.colorRedTopLeft = renderer.colorRedBottomLeft = renderer.colorRedBottomRight = renderer.colorRedTopRight = par5 * 0.5F;
				renderer.colorGreenTopLeft = renderer.colorGreenBottomLeft = renderer.colorGreenBottomRight = renderer.colorGreenTopRight = par6 * 0.5F;
				renderer.colorBlueTopLeft = renderer.colorBlueBottomLeft = renderer.colorBlueBottomRight = renderer.colorBlueTopRight = par7 * 0.5F;
			}
			else
			{
				renderer.colorRedTopLeft = renderer.colorRedBottomLeft = renderer.colorRedBottomRight = renderer.colorRedTopRight = 0.5F;
				renderer.colorGreenTopLeft = renderer.colorGreenBottomLeft = renderer.colorGreenBottomRight = renderer.colorGreenTopRight = 0.5F;
				renderer.colorBlueTopLeft = renderer.colorBlueBottomLeft = renderer.colorBlueBottomRight = renderer.colorBlueTopRight = 0.5F;
			}

			renderer.colorRedTopLeft *= f3;
			renderer.colorGreenTopLeft *= f3;
			renderer.colorBlueTopLeft *= f3;
			renderer.colorRedBottomLeft *= f4;
			renderer.colorGreenBottomLeft *= f4;
			renderer.colorBlueBottomLeft *= f4;
			renderer.colorRedBottomRight *= f5;
			renderer.colorGreenBottomRight *= f5;
			renderer.colorBlueBottomRight *= f5;
			renderer.colorRedTopRight *= f6;
			renderer.colorGreenTopRight *= f6;
			renderer.colorBlueTopRight *= f6;
			renderer.renderFaceYNeg(block, par2, par3, par4, block.getIcon(0, meta));
			flag = true;
		}

		if (renderer.renderAllFaces || block.shouldSideBeRendered(renderer.blockAccess, par2, par3 + 1, par4, 1))
		{
			if (renderer.renderMaxY >= 1.0D)
			{
				++par3;
			}

			renderer.aoBrightnessXYNP = block.getMixedBrightnessForBlock(renderer.blockAccess, par2 - 1, par3, par4);
			renderer.aoBrightnessXYPP = block.getMixedBrightnessForBlock(renderer.blockAccess, par2 + 1, par3, par4);
			renderer.aoBrightnessYZPN = block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3, par4 - 1);
			renderer.aoBrightnessYZPP = block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3, par4 + 1);
			renderer.aoLightValueScratchXYNP = renderer.blockAccess.getBlock(par2 - 1, par3, par4).getAmbientOcclusionLightValue();
			renderer.aoLightValueScratchXYPP = renderer.blockAccess.getBlock(par2 + 1, par3, par4).getAmbientOcclusionLightValue();
			renderer.aoLightValueScratchYZPN = renderer.blockAccess.getBlock(par2, par3, par4 - 1).getAmbientOcclusionLightValue();
			renderer.aoLightValueScratchYZPP = renderer.blockAccess.getBlock(par2, par3, par4 + 1).getAmbientOcclusionLightValue();
			flag3 = renderer.blockAccess.getBlock(par2 + 1, par3 + 1, par4).getCanBlockGrass();
			flag2 = renderer.blockAccess.getBlock(par2 - 1, par3 + 1, par4).getCanBlockGrass();
			flag5 = renderer.blockAccess.getBlock(par2, par3 + 1, par4 + 1).getCanBlockGrass();
			flag4 = renderer.blockAccess.getBlock(par2, par3 + 1, par4 - 1).getCanBlockGrass();

			if (!flag4 && !flag2)
			{
				renderer.aoLightValueScratchXYZNPN = renderer.aoLightValueScratchXYNP;
				renderer.aoBrightnessXYZNPN = renderer.aoBrightnessXYNP;
			}
			else
			{
				renderer.aoLightValueScratchXYZNPN = renderer.blockAccess.getBlock(par2 - 1, par3, par4 - 1).getAmbientOcclusionLightValue();
				renderer.aoBrightnessXYZNPN = block.getMixedBrightnessForBlock(renderer.blockAccess, par2 - 1, par3, par4 - 1);
			}

			if (!flag4 && !flag3)
			{
				renderer.aoLightValueScratchXYZPPN = renderer.aoLightValueScratchXYPP;
				renderer.aoBrightnessXYZPPN = renderer.aoBrightnessXYPP;
			}
			else
			{
				renderer.aoLightValueScratchXYZPPN = renderer.blockAccess.getBlock(par2 + 1, par3, par4 - 1).getAmbientOcclusionLightValue();
				renderer.aoBrightnessXYZPPN = block.getMixedBrightnessForBlock(renderer.blockAccess, par2 + 1, par3, par4 - 1);
			}

			if (!flag5 && !flag2)
			{
				renderer.aoLightValueScratchXYZNPP = renderer.aoLightValueScratchXYNP;
				renderer.aoBrightnessXYZNPP = renderer.aoBrightnessXYNP;
			}
			else
			{
				renderer.aoLightValueScratchXYZNPP = renderer.blockAccess.getBlock(par2 - 1, par3, par4 + 1).getAmbientOcclusionLightValue();
				renderer.aoBrightnessXYZNPP = block.getMixedBrightnessForBlock(renderer.blockAccess, par2 - 1, par3, par4 + 1);
			}

			if (!flag5 && !flag3)
			{
				renderer.aoLightValueScratchXYZPPP = renderer.aoLightValueScratchXYPP;
				renderer.aoBrightnessXYZPPP = renderer.aoBrightnessXYPP;
			}
			else
			{
				renderer.aoLightValueScratchXYZPPP = renderer.blockAccess.getBlock(par2 + 1, par3, par4 + 1).getAmbientOcclusionLightValue();
				renderer.aoBrightnessXYZPPP = block.getMixedBrightnessForBlock(renderer.blockAccess, par2 + 1, par3, par4 + 1);
			}

			if (renderer.renderMaxY >= 1.0D)
			{
				--par3;
			}

			i1 = l;

			if (renderer.renderMaxY >= 1.0D || !renderer.blockAccess.getBlock(par2, par3 + 1, par4).isOpaqueCube())
			{
				i1 = block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3 + 1, par4);
			}

			f7 = renderer.blockAccess.getBlock(par2, par3 + 1, par4).getAmbientOcclusionLightValue();
			f6 = (renderer.aoLightValueScratchXYZNPP + renderer.aoLightValueScratchXYNP + renderer.aoLightValueScratchYZPP + f7) / 4.0F;
			f3 = (renderer.aoLightValueScratchYZPP + f7 + renderer.aoLightValueScratchXYZPPP + renderer.aoLightValueScratchXYPP) / 4.0F;
			f4 = (f7 + renderer.aoLightValueScratchYZPN + renderer.aoLightValueScratchXYPP + renderer.aoLightValueScratchXYZPPN) / 4.0F;
			f5 = (renderer.aoLightValueScratchXYNP + renderer.aoLightValueScratchXYZNPN + f7 + renderer.aoLightValueScratchYZPN) / 4.0F;
			renderer.brightnessTopRight = renderer.getAoBrightness(renderer.aoBrightnessXYZNPP, renderer.aoBrightnessXYNP, renderer.aoBrightnessYZPP, i1);
			renderer.brightnessTopLeft = renderer.getAoBrightness(renderer.aoBrightnessYZPP, renderer.aoBrightnessXYZPPP, renderer.aoBrightnessXYPP, i1);
			renderer.brightnessBottomLeft = renderer.getAoBrightness(renderer.aoBrightnessYZPN, renderer.aoBrightnessXYPP, renderer.aoBrightnessXYZPPN, i1);
			renderer.brightnessBottomRight = renderer.getAoBrightness(renderer.aoBrightnessXYNP, renderer.aoBrightnessXYZNPN, renderer.aoBrightnessYZPN, i1);
			renderer.colorRedTopLeft = renderer.colorRedBottomLeft = renderer.colorRedBottomRight = renderer.colorRedTopRight = par5;
			renderer.colorGreenTopLeft = renderer.colorGreenBottomLeft = renderer.colorGreenBottomRight = renderer.colorGreenTopRight = par6;
			renderer.colorBlueTopLeft = renderer.colorBlueBottomLeft = renderer.colorBlueBottomRight = renderer.colorBlueTopRight = par7;
			renderer.colorRedTopLeft *= f3;
			renderer.colorGreenTopLeft *= f3;
			renderer.colorBlueTopLeft *= f3;
			renderer.colorRedBottomLeft *= f4;
			renderer.colorGreenBottomLeft *= f4;
			renderer.colorBlueBottomLeft *= f4;
			renderer.colorRedBottomRight *= f5;
			renderer.colorGreenBottomRight *= f5;
			renderer.colorBlueBottomRight *= f5;
			renderer.colorRedTopRight *= f6;
			renderer.colorGreenTopRight *= f6;
			renderer.colorBlueTopRight *= f6;
			renderer.renderFaceYPos(block, par2, par3, par4, block.getIcon(1, meta));
			flag = true;
		}

		float f8;
		float f9;
		float f10;
		float f11;
		int j1;
		int k1;
		int l1;
		int i2;
		IIcon icon;

		if (renderer.renderAllFaces || block.shouldSideBeRendered(renderer.blockAccess, par2, par3, par4 - 1, 2))
		{
			if (renderer.renderMinZ <= 0.0D)
			{
				--par4;
			}

			renderer.aoLightValueScratchXZNN = renderer.blockAccess.getBlock(par2 - 1, par3, par4).getAmbientOcclusionLightValue();
			renderer.aoLightValueScratchYZNN = renderer.blockAccess.getBlock(par2, par3 - 1, par4).getAmbientOcclusionLightValue();
			renderer.aoLightValueScratchYZPN = renderer.blockAccess.getBlock(par2, par3 + 1, par4).getAmbientOcclusionLightValue();
			renderer.aoLightValueScratchXZPN = renderer.blockAccess.getBlock(par2 + 1, par3, par4).getAmbientOcclusionLightValue();
			renderer.aoBrightnessXZNN = block.getMixedBrightnessForBlock(renderer.blockAccess, par2 - 1, par3, par4);
			renderer.aoBrightnessYZNN = block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3 - 1, par4);
			renderer.aoBrightnessYZPN = block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3 + 1, par4);
			renderer.aoBrightnessXZPN = block.getMixedBrightnessForBlock(renderer.blockAccess, par2 + 1, par3, par4);
			flag3 = renderer.blockAccess.getBlock(par2 + 1, par3, par4 - 1).getCanBlockGrass();
			flag2 = renderer.blockAccess.getBlock(par2 - 1, par3, par4 - 1).getCanBlockGrass();
			flag5 = renderer.blockAccess.getBlock(par2, par3 + 1, par4 - 1).getCanBlockGrass();
			flag4 = renderer.blockAccess.getBlock(par2, par3 - 1, par4 - 1).getCanBlockGrass();

			if (!flag2 && !flag4)
			{
				renderer.aoLightValueScratchXYZNNN = renderer.aoLightValueScratchXZNN;
				renderer.aoBrightnessXYZNNN = renderer.aoBrightnessXZNN;
			}
			else
			{
				renderer.aoLightValueScratchXYZNNN = renderer.blockAccess.getBlock(par2 - 1, par3 - 1, par4).getAmbientOcclusionLightValue();
				renderer.aoBrightnessXYZNNN = block.getMixedBrightnessForBlock(renderer.blockAccess, par2 - 1, par3 - 1, par4);
			}

			if (!flag2 && !flag5)
			{
				renderer.aoLightValueScratchXYZNPN = renderer.aoLightValueScratchXZNN;
				renderer.aoBrightnessXYZNPN = renderer.aoBrightnessXZNN;
			}
			else
			{
				renderer.aoLightValueScratchXYZNPN = renderer.blockAccess.getBlock(par2 - 1, par3 + 1, par4).getAmbientOcclusionLightValue();
				renderer.aoBrightnessXYZNPN = block.getMixedBrightnessForBlock(renderer.blockAccess, par2 - 1, par3 + 1, par4);
			}

			if (!flag3 && !flag4)
			{
				renderer.aoLightValueScratchXYZPNN = renderer.aoLightValueScratchXZPN;
				renderer.aoBrightnessXYZPNN = renderer.aoBrightnessXZPN;
			}
			else
			{
				renderer.aoLightValueScratchXYZPNN = renderer.blockAccess.getBlock(par2 + 1, par3 - 1, par4).getAmbientOcclusionLightValue();
				renderer.aoBrightnessXYZPNN = block.getMixedBrightnessForBlock(renderer.blockAccess, par2 + 1, par3 - 1, par4);
			}

			if (!flag3 && !flag5)
			{
				renderer.aoLightValueScratchXYZPPN = renderer.aoLightValueScratchXZPN;
				renderer.aoBrightnessXYZPPN = renderer.aoBrightnessXZPN;
			}
			else
			{
				renderer.aoLightValueScratchXYZPPN = renderer.blockAccess.getBlock(par2 + 1, par3 + 1, par4).getAmbientOcclusionLightValue();
				renderer.aoBrightnessXYZPPN = block.getMixedBrightnessForBlock(renderer.blockAccess, par2 + 1, par3 + 1, par4);
			}

			if (renderer.renderMinZ <= 0.0D)
			{
				++par4;
			}

			i1 = l;

			if (renderer.renderMinZ <= 0.0D || !renderer.blockAccess.getBlock(par2, par3, par4 - 1).isOpaqueCube())
			{
				i1 = block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3, par4 - 1);
			}

			f7 = renderer.blockAccess.getBlock(par2, par3, par4 - 1).getAmbientOcclusionLightValue();
			f9 = (renderer.aoLightValueScratchXZNN + renderer.aoLightValueScratchXYZNPN + f7 + renderer.aoLightValueScratchYZPN) / 4.0F;
			f8 = (f7 + renderer.aoLightValueScratchYZPN + renderer.aoLightValueScratchXZPN + renderer.aoLightValueScratchXYZPPN) / 4.0F;
			f11 = (renderer.aoLightValueScratchYZNN + f7 + renderer.aoLightValueScratchXYZPNN + renderer.aoLightValueScratchXZPN) / 4.0F;
			f10 = (renderer.aoLightValueScratchXYZNNN + renderer.aoLightValueScratchXZNN + renderer.aoLightValueScratchYZNN + f7) / 4.0F;
			f3 = (float)(f9 * renderer.renderMaxY * (1.0D - renderer.renderMinX) + f8 * renderer.renderMinY * renderer.renderMinX + f11 * (1.0D - renderer.renderMaxY) * renderer.renderMinX + f10 * (1.0D - renderer.renderMaxY) * (1.0D - renderer.renderMinX));
			f4 = (float)(f9 * renderer.renderMaxY * (1.0D - renderer.renderMaxX) + f8 * renderer.renderMaxY * renderer.renderMaxX + f11 * (1.0D - renderer.renderMaxY) * renderer.renderMaxX + f10 * (1.0D - renderer.renderMaxY) * (1.0D - renderer.renderMaxX));
			f5 = (float)(f9 * renderer.renderMinY * (1.0D - renderer.renderMaxX) + f8 * renderer.renderMinY * renderer.renderMaxX + f11 * (1.0D - renderer.renderMinY) * renderer.renderMaxX + f10 * (1.0D - renderer.renderMinY) * (1.0D - renderer.renderMaxX));
			f6 = (float)(f9 * renderer.renderMinY * (1.0D - renderer.renderMinX) + f8 * renderer.renderMinY * renderer.renderMinX + f11 * (1.0D - renderer.renderMinY) * renderer.renderMinX + f10 * (1.0D - renderer.renderMinY) * (1.0D - renderer.renderMinX));
			k1 = renderer.getAoBrightness(renderer.aoBrightnessXZNN, renderer.aoBrightnessXYZNPN, renderer.aoBrightnessYZPN, i1);
			j1 = renderer.getAoBrightness(renderer.aoBrightnessYZPN, renderer.aoBrightnessXZPN, renderer.aoBrightnessXYZPPN, i1);
			i2 = renderer.getAoBrightness(renderer.aoBrightnessYZNN, renderer.aoBrightnessXYZPNN, renderer.aoBrightnessXZPN, i1);
			l1 = renderer.getAoBrightness(renderer.aoBrightnessXYZNNN, renderer.aoBrightnessXZNN, renderer.aoBrightnessYZNN, i1);
			renderer.brightnessTopLeft = renderer.mixAoBrightness(k1, j1, i2, l1, renderer.renderMaxY * (1.0D - renderer.renderMinX), renderer.renderMaxY * renderer.renderMinX, (1.0D - renderer.renderMaxY) * renderer.renderMinX, (1.0D - renderer.renderMaxY) * (1.0D - renderer.renderMinX));
			renderer.brightnessBottomLeft = renderer.mixAoBrightness(k1, j1, i2, l1, renderer.renderMaxY * (1.0D - renderer.renderMaxX), renderer.renderMaxY * renderer.renderMaxX, (1.0D - renderer.renderMaxY) * renderer.renderMaxX, (1.0D - renderer.renderMaxY) * (1.0D - renderer.renderMaxX));
			renderer.brightnessBottomRight = renderer.mixAoBrightness(k1, j1, i2, l1, renderer.renderMinY * (1.0D - renderer.renderMaxX), renderer.renderMinY * renderer.renderMaxX, (1.0D - renderer.renderMinY) * renderer.renderMaxX, (1.0D - renderer.renderMinY) * (1.0D - renderer.renderMaxX));
			renderer.brightnessTopRight = renderer.mixAoBrightness(k1, j1, i2, l1, renderer.renderMinY * (1.0D - renderer.renderMinX), renderer.renderMinY * renderer.renderMinX, (1.0D - renderer.renderMinY) * renderer.renderMinX, (1.0D - renderer.renderMinY) * (1.0D - renderer.renderMinX));

			if (flag1)
			{
				renderer.colorRedTopLeft = renderer.colorRedBottomLeft = renderer.colorRedBottomRight = renderer.colorRedTopRight = par5 * 0.8F;
				renderer.colorGreenTopLeft = renderer.colorGreenBottomLeft = renderer.colorGreenBottomRight = renderer.colorGreenTopRight = par6 * 0.8F;
				renderer.colorBlueTopLeft = renderer.colorBlueBottomLeft = renderer.colorBlueBottomRight = renderer.colorBlueTopRight = par7 * 0.8F;
			}
			else
			{
				renderer.colorRedTopLeft = renderer.colorRedBottomLeft = renderer.colorRedBottomRight = renderer.colorRedTopRight = 0.8F;
				renderer.colorGreenTopLeft = renderer.colorGreenBottomLeft = renderer.colorGreenBottomRight = renderer.colorGreenTopRight = 0.8F;
				renderer.colorBlueTopLeft = renderer.colorBlueBottomLeft = renderer.colorBlueBottomRight = renderer.colorBlueTopRight = 0.8F;
			}

			renderer.colorRedTopLeft *= f3;
			renderer.colorGreenTopLeft *= f3;
			renderer.colorBlueTopLeft *= f3;
			renderer.colorRedBottomLeft *= f4;
			renderer.colorGreenBottomLeft *= f4;
			renderer.colorBlueBottomLeft *= f4;
			renderer.colorRedBottomRight *= f5;
			renderer.colorGreenBottomRight *= f5;
			renderer.colorBlueBottomRight *= f5;
			renderer.colorRedTopRight *= f6;
			renderer.colorGreenTopRight *= f6;
			renderer.colorBlueTopRight *= f6;
			icon = renderer.getBlockIcon(block, renderer.blockAccess, par2, par3, par4, 2);
			//renderer.uvRotateEast = 3;
			renderer.renderFaceZNeg(block, par2, par3, par4, block.getIcon(2, meta));
			//renderer.uvRotateEast = 0;

			flag = true;
		}

		if (renderer.renderAllFaces || block.shouldSideBeRendered(renderer.blockAccess, par2, par3, par4 + 1, 3))
		{
			if (renderer.renderMaxZ >= 1.0D)
			{
				++par4;
			}

			renderer.aoLightValueScratchXZNP = renderer.blockAccess.getBlock(par2 - 1, par3, par4).getAmbientOcclusionLightValue();
			renderer.aoLightValueScratchXZPP = renderer.blockAccess.getBlock(par2 + 1, par3, par4).getAmbientOcclusionLightValue();
			renderer.aoLightValueScratchYZNP = renderer.blockAccess.getBlock(par2, par3 - 1, par4).getAmbientOcclusionLightValue();
			renderer.aoLightValueScratchYZPP = renderer.blockAccess.getBlock(par2, par3 + 1, par4).getAmbientOcclusionLightValue();
			renderer.aoBrightnessXZNP = block.getMixedBrightnessForBlock(renderer.blockAccess, par2 - 1, par3, par4);
			renderer.aoBrightnessXZPP = block.getMixedBrightnessForBlock(renderer.blockAccess, par2 + 1, par3, par4);
			renderer.aoBrightnessYZNP = block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3 - 1, par4);
			renderer.aoBrightnessYZPP = block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3 + 1, par4);
			flag3 = renderer.blockAccess.getBlock(par2 + 1, par3, par4 + 1).getCanBlockGrass();
			flag2 = renderer.blockAccess.getBlock(par2 - 1, par3, par4 + 1).getCanBlockGrass();
			flag5 = renderer.blockAccess.getBlock(par2, par3 + 1, par4 + 1).getCanBlockGrass();
			flag4 = renderer.blockAccess.getBlock(par2, par3 - 1, par4 + 1).getCanBlockGrass();

			if (!flag2 && !flag4)
			{
				renderer.aoLightValueScratchXYZNNP = renderer.aoLightValueScratchXZNP;
				renderer.aoBrightnessXYZNNP = renderer.aoBrightnessXZNP;
			}
			else
			{
				renderer.aoLightValueScratchXYZNNP = renderer.blockAccess.getBlock(par2 - 1, par3 - 1, par4).getAmbientOcclusionLightValue();
				renderer.aoBrightnessXYZNNP = block.getMixedBrightnessForBlock(renderer.blockAccess, par2 - 1, par3 - 1, par4);
			}

			if (!flag2 && !flag5)
			{
				renderer.aoLightValueScratchXYZNPP = renderer.aoLightValueScratchXZNP;
				renderer.aoBrightnessXYZNPP = renderer.aoBrightnessXZNP;
			}
			else
			{
				renderer.aoLightValueScratchXYZNPP = renderer.blockAccess.getBlock(par2 - 1, par3 + 1, par4).getAmbientOcclusionLightValue();
				renderer.aoBrightnessXYZNPP = block.getMixedBrightnessForBlock(renderer.blockAccess, par2 - 1, par3 + 1, par4);
			}

			if (!flag3 && !flag4)
			{
				renderer.aoLightValueScratchXYZPNP = renderer.aoLightValueScratchXZPP;
				renderer.aoBrightnessXYZPNP = renderer.aoBrightnessXZPP;
			}
			else
			{
				renderer.aoLightValueScratchXYZPNP = renderer.blockAccess.getBlock(par2 + 1, par3 - 1, par4).getAmbientOcclusionLightValue();
				renderer.aoBrightnessXYZPNP = block.getMixedBrightnessForBlock(renderer.blockAccess, par2 + 1, par3 - 1, par4);
			}

			if (!flag3 && !flag5)
			{
				renderer.aoLightValueScratchXYZPPP = renderer.aoLightValueScratchXZPP;
				renderer.aoBrightnessXYZPPP = renderer.aoBrightnessXZPP;
			}
			else
			{
				renderer.aoLightValueScratchXYZPPP = renderer.blockAccess.getBlock(par2 + 1, par3 + 1, par4).getAmbientOcclusionLightValue();
				renderer.aoBrightnessXYZPPP = block.getMixedBrightnessForBlock(renderer.blockAccess, par2 + 1, par3 + 1, par4);
			}

			if (renderer.renderMaxZ >= 1.0D)
			{
				--par4;
			}

			i1 = l;

			if (renderer.renderMaxZ >= 1.0D || !renderer.blockAccess.getBlock(par2, par3, par4 + 1).isOpaqueCube())
			{
				i1 = block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3, par4 + 1);
			}

			f7 = renderer.blockAccess.getBlock(par2, par3, par4 + 1).getAmbientOcclusionLightValue();
			f9 = (renderer.aoLightValueScratchXZNP + renderer.aoLightValueScratchXYZNPP + f7 + renderer.aoLightValueScratchYZPP) / 4.0F;
			f8 = (f7 + renderer.aoLightValueScratchYZPP + renderer.aoLightValueScratchXZPP + renderer.aoLightValueScratchXYZPPP) / 4.0F;
			f11 = (renderer.aoLightValueScratchYZNP + f7 + renderer.aoLightValueScratchXYZPNP + renderer.aoLightValueScratchXZPP) / 4.0F;
			f10 = (renderer.aoLightValueScratchXYZNNP + renderer.aoLightValueScratchXZNP + renderer.aoLightValueScratchYZNP + f7) / 4.0F;
			f3 = (float)(f9 * renderer.renderMaxY * (1.0D - renderer.renderMinX) + f8 * renderer.renderMaxY * renderer.renderMinX + f11 * (1.0D - renderer.renderMaxY) * renderer.renderMinX + f10 * (1.0D - renderer.renderMaxY) * (1.0D - renderer.renderMinX));
			f4 = (float)(f9 * renderer.renderMinY * (1.0D - renderer.renderMinX) + f8 * renderer.renderMinY * renderer.renderMinX + f11 * (1.0D - renderer.renderMinY) * renderer.renderMinX + f10 * (1.0D - renderer.renderMinY) * (1.0D - renderer.renderMinX));
			f5 = (float)(f9 * renderer.renderMinY * (1.0D - renderer.renderMaxX) + f8 * renderer.renderMinY * renderer.renderMaxX + f11 * (1.0D - renderer.renderMinY) * renderer.renderMaxX + f10 * (1.0D - renderer.renderMinY) * (1.0D - renderer.renderMaxX));
			f6 = (float)(f9 * renderer.renderMaxY * (1.0D - renderer.renderMaxX) + f8 * renderer.renderMaxY * renderer.renderMaxX + f11 * (1.0D - renderer.renderMaxY) * renderer.renderMaxX + f10 * (1.0D - renderer.renderMaxY) * (1.0D - renderer.renderMaxX));
			k1 = renderer.getAoBrightness(renderer.aoBrightnessXZNP, renderer.aoBrightnessXYZNPP, renderer.aoBrightnessYZPP, i1);
			j1 = renderer.getAoBrightness(renderer.aoBrightnessYZPP, renderer.aoBrightnessXZPP, renderer.aoBrightnessXYZPPP, i1);
			i2 = renderer.getAoBrightness(renderer.aoBrightnessYZNP, renderer.aoBrightnessXYZPNP, renderer.aoBrightnessXZPP, i1);
			l1 = renderer.getAoBrightness(renderer.aoBrightnessXYZNNP, renderer.aoBrightnessXZNP, renderer.aoBrightnessYZNP, i1);
			renderer.brightnessTopLeft = renderer.mixAoBrightness(k1, l1, i2, j1, renderer.renderMaxY * (1.0D - renderer.renderMinX), (1.0D - renderer.renderMaxY) * (1.0D - renderer.renderMinX), (1.0D - renderer.renderMaxY) * renderer.renderMinX, renderer.renderMaxY * renderer.renderMinX);
			renderer.brightnessBottomLeft = renderer.mixAoBrightness(k1, l1, i2, j1, renderer.renderMinY * (1.0D - renderer.renderMinX), (1.0D - renderer.renderMinY) * (1.0D - renderer.renderMinX), (1.0D - renderer.renderMinY) * renderer.renderMinX, renderer.renderMinY * renderer.renderMinX);
			renderer.brightnessBottomRight = renderer.mixAoBrightness(k1, l1, i2, j1, renderer.renderMinY * (1.0D - renderer.renderMaxX), (1.0D - renderer.renderMinY) * (1.0D - renderer.renderMaxX), (1.0D - renderer.renderMinY) * renderer.renderMaxX, renderer.renderMinY * renderer.renderMaxX);
			renderer.brightnessTopRight = renderer.mixAoBrightness(k1, l1, i2, j1, renderer.renderMaxY * (1.0D - renderer.renderMaxX), (1.0D - renderer.renderMaxY) * (1.0D - renderer.renderMaxX), (1.0D - renderer.renderMaxY) * renderer.renderMaxX, renderer.renderMaxY * renderer.renderMaxX);

			if (flag1)
			{
				renderer.colorRedTopLeft = renderer.colorRedBottomLeft = renderer.colorRedBottomRight = renderer.colorRedTopRight = par5 * 0.8F;
				renderer.colorGreenTopLeft = renderer.colorGreenBottomLeft = renderer.colorGreenBottomRight = renderer.colorGreenTopRight = par6 * 0.8F;
				renderer.colorBlueTopLeft = renderer.colorBlueBottomLeft = renderer.colorBlueBottomRight = renderer.colorBlueTopRight = par7 * 0.8F;
			}
			else
			{
				renderer.colorRedTopLeft = renderer.colorRedBottomLeft = renderer.colorRedBottomRight = renderer.colorRedTopRight = 0.8F;
				renderer.colorGreenTopLeft = renderer.colorGreenBottomLeft = renderer.colorGreenBottomRight = renderer.colorGreenTopRight = 0.8F;
				renderer.colorBlueTopLeft = renderer.colorBlueBottomLeft = renderer.colorBlueBottomRight = renderer.colorBlueTopRight = 0.8F;
			}

			renderer.colorRedTopLeft *= f3;
			renderer.colorGreenTopLeft *= f3;
			renderer.colorBlueTopLeft *= f3;
			renderer.colorRedBottomLeft *= f4;
			renderer.colorGreenBottomLeft *= f4;
			renderer.colorBlueBottomLeft *= f4;
			renderer.colorRedBottomRight *= f5;
			renderer.colorGreenBottomRight *= f5;
			renderer.colorBlueBottomRight *= f5;
			renderer.colorRedTopRight *= f6;
			renderer.colorGreenTopRight *= f6;
			renderer.colorBlueTopRight *= f6;
			icon = renderer.getBlockIcon(block, renderer.blockAccess, par2, par3, par4, 3);
			renderer.renderFaceZPos(block, par2, par3, par4, block.getIcon(3, meta));

			if (renderer.fancyGrass && icon.getIconName().equals("grass_side") && !renderer.hasOverrideBlockTexture())
			{
				renderer.colorRedTopLeft *= par5;
				renderer.colorRedBottomLeft *= par5;
				renderer.colorRedBottomRight *= par5;
				renderer.colorRedTopRight *= par5;
				renderer.colorGreenTopLeft *= par6;
				renderer.colorGreenBottomLeft *= par6;
				renderer.colorGreenBottomRight *= par6;
				renderer.colorGreenTopRight *= par6;
				renderer.colorBlueTopLeft *= par7;
				renderer.colorBlueBottomLeft *= par7;
				renderer.colorBlueBottomRight *= par7;
				renderer.colorBlueTopRight *= par7;
				renderer.renderFaceZPos(block, par2, par3, par4, BlockGrass.getIconSideOverlay());
			}

			flag = true;
		}

		if (renderer.renderAllFaces || block.shouldSideBeRendered(renderer.blockAccess, par2 - 1, par3, par4, 4))
		{
			if (renderer.renderMinX <= 0.0D)
			{
				--par2;
			}

			renderer.aoLightValueScratchXYNN = renderer.blockAccess.getBlock(par2, par3 - 1, par4).getAmbientOcclusionLightValue();
			renderer.aoLightValueScratchXZNN = renderer.blockAccess.getBlock(par2, par3, par4 - 1).getAmbientOcclusionLightValue();
			renderer.aoLightValueScratchXZNP = renderer.blockAccess.getBlock(par2, par3, par4 + 1).getAmbientOcclusionLightValue();
			renderer.aoLightValueScratchXYNP = renderer.blockAccess.getBlock(par2, par3 + 1, par4).getAmbientOcclusionLightValue();
			renderer.aoBrightnessXYNN = block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3 - 1, par4);
			renderer.aoBrightnessXZNN = block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3, par4 - 1);
			renderer.aoBrightnessXZNP = block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3, par4 + 1);
			renderer.aoBrightnessXYNP = block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3 + 1, par4);
			flag3 = renderer.blockAccess.getBlock(par2 - 1, par3 + 1, par4).getCanBlockGrass();
			flag2 = renderer.blockAccess.getBlock(par2 - 1, par3 - 1, par4).getCanBlockGrass();
			flag5 = renderer.blockAccess.getBlock(par2 - 1, par3, par4 - 1).getCanBlockGrass();
			flag4 = renderer.blockAccess.getBlock(par2 - 1, par3, par4 + 1).getCanBlockGrass();

			if (!flag5 && !flag2)
			{
				renderer.aoLightValueScratchXYZNNN = renderer.aoLightValueScratchXZNN;
				renderer.aoBrightnessXYZNNN = renderer.aoBrightnessXZNN;
			}
			else
			{
				renderer.aoLightValueScratchXYZNNN = renderer.blockAccess.getBlock(par2, par3 - 1, par4 - 1).getAmbientOcclusionLightValue();
				renderer.aoBrightnessXYZNNN = block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3 - 1, par4 - 1);
			}

			if (!flag4 && !flag2)
			{
				renderer.aoLightValueScratchXYZNNP = renderer.aoLightValueScratchXZNP;
				renderer.aoBrightnessXYZNNP = renderer.aoBrightnessXZNP;
			}
			else
			{
				renderer.aoLightValueScratchXYZNNP = renderer.blockAccess.getBlock(par2, par3 - 1, par4 + 1).getAmbientOcclusionLightValue();
				renderer.aoBrightnessXYZNNP = block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3 - 1, par4 + 1);
			}

			if (!flag5 && !flag3)
			{
				renderer.aoLightValueScratchXYZNPN = renderer.aoLightValueScratchXZNN;
				renderer.aoBrightnessXYZNPN = renderer.aoBrightnessXZNN;
			}
			else
			{
				renderer.aoLightValueScratchXYZNPN = renderer.blockAccess.getBlock(par2, par3 + 1, par4 - 1).getAmbientOcclusionLightValue();
				renderer.aoBrightnessXYZNPN = block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3 + 1, par4 - 1);
			}

			if (!flag4 && !flag3)
			{
				renderer.aoLightValueScratchXYZNPP = renderer.aoLightValueScratchXZNP;
				renderer.aoBrightnessXYZNPP = renderer.aoBrightnessXZNP;
			}
			else
			{
				renderer.aoLightValueScratchXYZNPP = renderer.blockAccess.getBlock(par2, par3 + 1, par4 + 1).getAmbientOcclusionLightValue();
				renderer.aoBrightnessXYZNPP = block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3 + 1, par4 + 1);
			}

			if (renderer.renderMinX <= 0.0D)
			{
				++par2;
			}

			i1 = l;

			if (renderer.renderMinX <= 0.0D || !renderer.blockAccess.getBlock(par2 - 1, par3, par4).isOpaqueCube())
			{
				i1 = block.getMixedBrightnessForBlock(renderer.blockAccess, par2 - 1, par3, par4);
			}

			f7 = renderer.blockAccess.getBlock(par2 - 1, par3, par4).getAmbientOcclusionLightValue();
			f9 = (renderer.aoLightValueScratchXYNN + renderer.aoLightValueScratchXYZNNP + f7 + renderer.aoLightValueScratchXZNP) / 4.0F;
			f8 = (f7 + renderer.aoLightValueScratchXZNP + renderer.aoLightValueScratchXYNP + renderer.aoLightValueScratchXYZNPP) / 4.0F;
			f11 = (renderer.aoLightValueScratchXZNN + f7 + renderer.aoLightValueScratchXYZNPN + renderer.aoLightValueScratchXYNP) / 4.0F;
			f10 = (renderer.aoLightValueScratchXYZNNN + renderer.aoLightValueScratchXYNN + renderer.aoLightValueScratchXZNN + f7) / 4.0F;
			f3 = (float)(f8 * renderer.renderMaxY * renderer.renderMaxZ + f11 * renderer.renderMaxY * (1.0D - renderer.renderMaxZ) + f10 * (1.0D - renderer.renderMaxY) * (1.0D - renderer.renderMaxZ) + f9 * (1.0D - renderer.renderMaxY) * renderer.renderMaxZ);
			f4 = (float)(f8 * renderer.renderMaxY * renderer.renderMinZ + f11 * renderer.renderMaxY * (1.0D - renderer.renderMinZ) + f10 * (1.0D - renderer.renderMaxY) * (1.0D - renderer.renderMinZ) + f9 * (1.0D - renderer.renderMaxY) * renderer.renderMinZ);
			f5 = (float)(f8 * renderer.renderMinY * renderer.renderMinZ + f11 * renderer.renderMinY * (1.0D - renderer.renderMinZ) + f10 * (1.0D - renderer.renderMinY) * (1.0D - renderer.renderMinZ) + f9 * (1.0D - renderer.renderMinY) * renderer.renderMinZ);
			f6 = (float)(f8 * renderer.renderMinY * renderer.renderMaxZ + f11 * renderer.renderMinY * (1.0D - renderer.renderMaxZ) + f10 * (1.0D - renderer.renderMinY) * (1.0D - renderer.renderMaxZ) + f9 * (1.0D - renderer.renderMinY) * renderer.renderMaxZ);
			k1 = renderer.getAoBrightness(renderer.aoBrightnessXYNN, renderer.aoBrightnessXYZNNP, renderer.aoBrightnessXZNP, i1);
			j1 = renderer.getAoBrightness(renderer.aoBrightnessXZNP, renderer.aoBrightnessXYNP, renderer.aoBrightnessXYZNPP, i1);
			i2 = renderer.getAoBrightness(renderer.aoBrightnessXZNN, renderer.aoBrightnessXYZNPN, renderer.aoBrightnessXYNP, i1);
			l1 = renderer.getAoBrightness(renderer.aoBrightnessXYZNNN, renderer.aoBrightnessXYNN, renderer.aoBrightnessXZNN, i1);
			renderer.brightnessTopLeft = renderer.mixAoBrightness(j1, i2, l1, k1, renderer.renderMaxY * renderer.renderMaxZ, renderer.renderMaxY * (1.0D - renderer.renderMaxZ), (1.0D - renderer.renderMaxY) * (1.0D - renderer.renderMaxZ), (1.0D - renderer.renderMaxY) * renderer.renderMaxZ);
			renderer.brightnessBottomLeft = renderer.mixAoBrightness(j1, i2, l1, k1, renderer.renderMaxY * renderer.renderMinZ, renderer.renderMaxY * (1.0D - renderer.renderMinZ), (1.0D - renderer.renderMaxY) * (1.0D - renderer.renderMinZ), (1.0D - renderer.renderMaxY) * renderer.renderMinZ);
			renderer.brightnessBottomRight = renderer.mixAoBrightness(j1, i2, l1, k1, renderer.renderMinY * renderer.renderMinZ, renderer.renderMinY * (1.0D - renderer.renderMinZ), (1.0D - renderer.renderMinY) * (1.0D - renderer.renderMinZ), (1.0D - renderer.renderMinY) * renderer.renderMinZ);
			renderer.brightnessTopRight = renderer.mixAoBrightness(j1, i2, l1, k1, renderer.renderMinY * renderer.renderMaxZ, renderer.renderMinY * (1.0D - renderer.renderMaxZ), (1.0D - renderer.renderMinY) * (1.0D - renderer.renderMaxZ), (1.0D - renderer.renderMinY) * renderer.renderMaxZ);

			if (flag1)
			{
				renderer.colorRedTopLeft = renderer.colorRedBottomLeft = renderer.colorRedBottomRight = renderer.colorRedTopRight = par5 * 0.6F;
				renderer.colorGreenTopLeft = renderer.colorGreenBottomLeft = renderer.colorGreenBottomRight = renderer.colorGreenTopRight = par6 * 0.6F;
				renderer.colorBlueTopLeft = renderer.colorBlueBottomLeft = renderer.colorBlueBottomRight = renderer.colorBlueTopRight = par7 * 0.6F;
			}
			else
			{
				renderer.colorRedTopLeft = renderer.colorRedBottomLeft = renderer.colorRedBottomRight = renderer.colorRedTopRight = 0.6F;
				renderer.colorGreenTopLeft = renderer.colorGreenBottomLeft = renderer.colorGreenBottomRight = renderer.colorGreenTopRight = 0.6F;
				renderer.colorBlueTopLeft = renderer.colorBlueBottomLeft = renderer.colorBlueBottomRight = renderer.colorBlueTopRight = 0.6F;
			}

			renderer.colorRedTopLeft *= f3;
			renderer.colorGreenTopLeft *= f3;
			renderer.colorBlueTopLeft *= f3;
			renderer.colorRedBottomLeft *= f4;
			renderer.colorGreenBottomLeft *= f4;
			renderer.colorBlueBottomLeft *= f4;
			renderer.colorRedBottomRight *= f5;
			renderer.colorGreenBottomRight *= f5;
			renderer.colorBlueBottomRight *= f5;
			renderer.colorRedTopRight *= f6;
			renderer.colorGreenTopRight *= f6;
			renderer.colorBlueTopRight *= f6;
			icon = renderer.getBlockIcon(block, renderer.blockAccess, par2, par3, par4, 4);
			renderer.renderFaceXNeg(block, par2, par3, par4, block.getIcon(4, meta));

			if (renderer.fancyGrass && icon.getIconName().equals("grass_side") && !renderer.hasOverrideBlockTexture())
			{
				renderer.colorRedTopLeft *= par5;
				renderer.colorRedBottomLeft *= par5;
				renderer.colorRedBottomRight *= par5;
				renderer.colorRedTopRight *= par5;
				renderer.colorGreenTopLeft *= par6;
				renderer.colorGreenBottomLeft *= par6;
				renderer.colorGreenBottomRight *= par6;
				renderer.colorGreenTopRight *= par6;
				renderer.colorBlueTopLeft *= par7;
				renderer.colorBlueBottomLeft *= par7;
				renderer.colorBlueBottomRight *= par7;
				renderer.colorBlueTopRight *= par7;
				renderer.renderFaceXNeg(block, par2, par3, par4, BlockGrass.getIconSideOverlay());
			}

			flag = true;
		}

		if (renderer.renderAllFaces || block.shouldSideBeRendered(renderer.blockAccess, par2 + 1, par3, par4, 5))
		{
			if (renderer.renderMaxX >= 1.0D)
			{
				++par2;
			}

			renderer.aoLightValueScratchXYPN = renderer.blockAccess.getBlock(par2, par3 - 1, par4).getAmbientOcclusionLightValue();
			renderer.aoLightValueScratchXZPN = renderer.blockAccess.getBlock(par2, par3, par4 - 1).getAmbientOcclusionLightValue();
			renderer.aoLightValueScratchXZPP = renderer.blockAccess.getBlock(par2, par3, par4 + 1).getAmbientOcclusionLightValue();
			renderer.aoLightValueScratchXYPP = renderer.blockAccess.getBlock(par2, par3 + 1, par4).getAmbientOcclusionLightValue();
			renderer.aoBrightnessXYPN = block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3 - 1, par4);
			renderer.aoBrightnessXZPN = block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3, par4 - 1);
			renderer.aoBrightnessXZPP = block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3, par4 + 1);
			renderer.aoBrightnessXYPP = block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3 + 1, par4);
			flag3 = renderer.blockAccess.getBlock(par2 + 1, par3 + 1, par4).getCanBlockGrass();
			flag2 = renderer.blockAccess.getBlock(par2 + 1, par3 - 1, par4).getCanBlockGrass();
			flag5 = renderer.blockAccess.getBlock(par2 + 1, par3, par4 + 1).getCanBlockGrass();
			flag4 = renderer.blockAccess.getBlock(par2 + 1, par3, par4 - 1).getCanBlockGrass();

			if (!flag2 && !flag4)
			{
				renderer.aoLightValueScratchXYZPNN = renderer.aoLightValueScratchXZPN;
				renderer.aoBrightnessXYZPNN = renderer.aoBrightnessXZPN;
			}
			else
			{
				renderer.aoLightValueScratchXYZPNN = renderer.blockAccess.getBlock(par2, par3 - 1, par4 - 1).getAmbientOcclusionLightValue();
				renderer.aoBrightnessXYZPNN = block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3 - 1, par4 - 1);
			}

			if (!flag2 && !flag5)
			{
				renderer.aoLightValueScratchXYZPNP = renderer.aoLightValueScratchXZPP;
				renderer.aoBrightnessXYZPNP = renderer.aoBrightnessXZPP;
			}
			else
			{
				renderer.aoLightValueScratchXYZPNP = renderer.blockAccess.getBlock(par2, par3 - 1, par4 + 1).getAmbientOcclusionLightValue();
				renderer.aoBrightnessXYZPNP = block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3 - 1, par4 + 1);
			}

			if (!flag3 && !flag4)
			{
				renderer.aoLightValueScratchXYZPPN = renderer.aoLightValueScratchXZPN;
				renderer.aoBrightnessXYZPPN = renderer.aoBrightnessXZPN;
			}
			else
			{
				renderer.aoLightValueScratchXYZPPN = renderer.blockAccess.getBlock(par2, par3 + 1, par4 - 1).getAmbientOcclusionLightValue();
				renderer.aoBrightnessXYZPPN = block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3 + 1, par4 - 1);
			}

			if (!flag3 && !flag5)
			{
				renderer.aoLightValueScratchXYZPPP = renderer.aoLightValueScratchXZPP;
				renderer.aoBrightnessXYZPPP = renderer.aoBrightnessXZPP;
			}
			else
			{
				renderer.aoLightValueScratchXYZPPP = renderer.blockAccess.getBlock(par2, par3 + 1, par4 + 1).getAmbientOcclusionLightValue();
				renderer.aoBrightnessXYZPPP = block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3 + 1, par4 + 1);
			}

			if (renderer.renderMaxX >= 1.0D)
			{
				--par2;
			}

			i1 = l;

			if (renderer.renderMaxX >= 1.0D || !renderer.blockAccess.getBlock(par2 + 1, par3, par4).isOpaqueCube())
			{
				i1 = block.getMixedBrightnessForBlock(renderer.blockAccess, par2 + 1, par3, par4);
			}

			f7 = renderer.blockAccess.getBlock(par2 + 1, par3, par4).getAmbientOcclusionLightValue();
			f9 = (renderer.aoLightValueScratchXYPN + renderer.aoLightValueScratchXYZPNP + f7 + renderer.aoLightValueScratchXZPP) / 4.0F;
			f8 = (renderer.aoLightValueScratchXYZPNN + renderer.aoLightValueScratchXYPN + renderer.aoLightValueScratchXZPN + f7) / 4.0F;
			f11 = (renderer.aoLightValueScratchXZPN + f7 + renderer.aoLightValueScratchXYZPPN + renderer.aoLightValueScratchXYPP) / 4.0F;
			f10 = (f7 + renderer.aoLightValueScratchXZPP + renderer.aoLightValueScratchXYPP + renderer.aoLightValueScratchXYZPPP) / 4.0F;
			f3 = (float)(f9 * (1.0D - renderer.renderMinY) * renderer.renderMaxZ + f8 * (1.0D - renderer.renderMinY) * (1.0D - renderer.renderMaxZ) + f11 * renderer.renderMinY * (1.0D - renderer.renderMaxZ) + f10 * renderer.renderMinY * renderer.renderMaxZ);
			f4 = (float)(f9 * (1.0D - renderer.renderMinY) * renderer.renderMinZ + f8 * (1.0D - renderer.renderMinY) * (1.0D - renderer.renderMinZ) + f11 * renderer.renderMinY * (1.0D - renderer.renderMinZ) + f10 * renderer.renderMinY * renderer.renderMinZ);
			f5 = (float)(f9 * (1.0D - renderer.renderMaxY) * renderer.renderMinZ + f8 * (1.0D - renderer.renderMaxY) * (1.0D - renderer.renderMinZ) + f11 * renderer.renderMaxY * (1.0D - renderer.renderMinZ) + f10 * renderer.renderMaxY * renderer.renderMinZ);
			f6 = (float)(f9 * (1.0D - renderer.renderMaxY) * renderer.renderMaxZ + f8 * (1.0D - renderer.renderMaxY) * (1.0D - renderer.renderMaxZ) + f11 * renderer.renderMaxY * (1.0D - renderer.renderMaxZ) + f10 * renderer.renderMaxY * renderer.renderMaxZ);
			k1 = renderer.getAoBrightness(renderer.aoBrightnessXYPN, renderer.aoBrightnessXYZPNP, renderer.aoBrightnessXZPP, i1);
			j1 = renderer.getAoBrightness(renderer.aoBrightnessXZPP, renderer.aoBrightnessXYPP, renderer.aoBrightnessXYZPPP, i1);
			i2 = renderer.getAoBrightness(renderer.aoBrightnessXZPN, renderer.aoBrightnessXYZPPN, renderer.aoBrightnessXYPP, i1);
			l1 = renderer.getAoBrightness(renderer.aoBrightnessXYZPNN, renderer.aoBrightnessXYPN, renderer.aoBrightnessXZPN, i1);
			renderer.brightnessTopLeft = renderer.mixAoBrightness(k1, l1, i2, j1, (1.0D - renderer.renderMinY) * renderer.renderMaxZ, (1.0D - renderer.renderMinY) * (1.0D - renderer.renderMaxZ), renderer.renderMinY * (1.0D - renderer.renderMaxZ), renderer.renderMinY * renderer.renderMaxZ);
			renderer.brightnessBottomLeft = renderer.mixAoBrightness(k1, l1, i2, j1, (1.0D - renderer.renderMinY) * renderer.renderMinZ, (1.0D - renderer.renderMinY) * (1.0D - renderer.renderMinZ), renderer.renderMinY * (1.0D - renderer.renderMinZ), renderer.renderMinY * renderer.renderMinZ);
			renderer.brightnessBottomRight = renderer.mixAoBrightness(k1, l1, i2, j1, (1.0D - renderer.renderMaxY) * renderer.renderMinZ, (1.0D - renderer.renderMaxY) * (1.0D - renderer.renderMinZ), renderer.renderMaxY * (1.0D - renderer.renderMinZ), renderer.renderMaxY * renderer.renderMinZ);
			renderer.brightnessTopRight = renderer.mixAoBrightness(k1, l1, i2, j1, (1.0D - renderer.renderMaxY) * renderer.renderMaxZ, (1.0D - renderer.renderMaxY) * (1.0D - renderer.renderMaxZ), renderer.renderMaxY * (1.0D - renderer.renderMaxZ), renderer.renderMaxY * renderer.renderMaxZ);

			if (flag1)
			{
				renderer.colorRedTopLeft = renderer.colorRedBottomLeft = renderer.colorRedBottomRight = renderer.colorRedTopRight = par5 * 0.6F;
				renderer.colorGreenTopLeft = renderer.colorGreenBottomLeft = renderer.colorGreenBottomRight = renderer.colorGreenTopRight = par6 * 0.6F;
				renderer.colorBlueTopLeft = renderer.colorBlueBottomLeft = renderer.colorBlueBottomRight = renderer.colorBlueTopRight = par7 * 0.6F;
			}
			else
			{
				renderer.colorRedTopLeft = renderer.colorRedBottomLeft = renderer.colorRedBottomRight = renderer.colorRedTopRight = 0.6F;
				renderer.colorGreenTopLeft = renderer.colorGreenBottomLeft = renderer.colorGreenBottomRight = renderer.colorGreenTopRight = 0.6F;
				renderer.colorBlueTopLeft = renderer.colorBlueBottomLeft = renderer.colorBlueBottomRight = renderer.colorBlueTopRight = 0.6F;
			}

			renderer.colorRedTopLeft *= f3;
			renderer.colorGreenTopLeft *= f3;
			renderer.colorBlueTopLeft *= f3;
			renderer.colorRedBottomLeft *= f4;
			renderer.colorGreenBottomLeft *= f4;
			renderer.colorBlueBottomLeft *= f4;
			renderer.colorRedBottomRight *= f5;
			renderer.colorGreenBottomRight *= f5;
			renderer.colorBlueBottomRight *= f5;
			renderer.colorRedTopRight *= f6;
			renderer.colorGreenTopRight *= f6;
			renderer.colorBlueTopRight *= f6;
			icon = renderer.getBlockIcon(block, renderer.blockAccess, par2, par3, par4, 5);
			//renderer.uvRotateSouth = 3;
			renderer.renderFaceXPos(block, par2, par3, par4, block.getIcon(5, meta));
			//renderer.uvRotateSouth = 0;
			flag = true;
		}

		renderer.enableAO = false;
		return flag;
	}

}
