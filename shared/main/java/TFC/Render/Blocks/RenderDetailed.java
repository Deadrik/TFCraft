package TFC.Render.Blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Icon;
import TFC.Render.RenderBlocksFixUV;
import TFC.TileEntities.TileEntityDetailed;

public class RenderDetailed 
{
	private static RenderBlocksFixUV renderer;

	public static boolean renderBlockDetailed(Block block, int i, int j, int k, RenderBlocks renderblocks)
	{
		TileEntityDetailed te = (TileEntityDetailed) renderblocks.blockAccess.getBlockTileEntity(i, j, k);
		int md = renderblocks.blockAccess.getBlockMetadata(i, j, k);

		if(renderer == null)
		{
			renderer = new RenderBlocksFixUV(renderblocks);
		}
		else
		{
			renderer.update(renderblocks);
		}

		if(te.TypeID <= 0)
		{
			return false;
		}

		int type = te.TypeID;
		int meta = te.MetaID;

		boolean breaking = false;
		if(renderer.overrideBlockTexture != null)
		{
			breaking = true;
		}

		for(int subX = 0; subX < 2; subX++)
		{
			for(int subZ = 0; subZ < 2; subZ++)
			{
				for(int subY = 0; subY < 2; subY++)
				{
					if(!te.isQuadSolid(subX, subY, subZ))
					{
						renderMiniBlock(i, j, k, subX, subY, subZ, renderer, te, type, meta);
					} else
					{
						float minX = 0.5f * subX;
						float maxX = minX + 0.5f;
						float minY = 0.5f * subY;
						float maxY = minY + 0.5f;
						float minZ = 0.5f * subZ;
						float maxZ = minZ + 0.5f;

						renderer.setRenderBounds(minX, minY, minZ, maxX, maxY, maxZ);
						renderStandardBlockWithColorMultiplier(Block.blocksList[type], renderer, i, j, k, 1f, 1f, 1f, meta);
					}
				}
			}
		}

		return true;
	}

	private static void renderMiniBlock(int i, int j, int k, int x, int y, int z,
			RenderBlocks renderblocks, TileEntityDetailed te, int type, int meta) {
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
						renderStandardBlockWithColorMultiplier(Block.blocksList[type], renderblocks, i, j, k, 1f, 1f, 1f, meta);
					}
				}
			}
		}
	}

	public static boolean renderStandardBlockWithColorMultiplier(Block par1Block, RenderBlocks renderblocks, int par2, int par3, int par4, float par5, float par6, float par7, int meta)
	{
		renderblocks.enableAO = false;
		Tessellator var8 = Tessellator.instance;

		float var10 = 0.5F;
		float var11 = 1.0F;
		float var12 = 0.8F;
		float var13 = 0.6F;
		float var14 = var11 * par5;
		float var15 = var11 * par6;
		float var16 = var11 * par7;
		float var17 = var10;
		float var18 = var12;
		float var19 = var13;
		float var20 = var10;
		float var21 = var12;
		float var22 = var13;
		float var23 = var10;
		float var24 = var12;
		float var25 = var13;

		if (par1Block != Block.grass)
		{
			var17 = var10 * par5;
			var18 = var12 * par5;
			var19 = var13 * par5;
			var20 = var10 * par6;
			var21 = var12 * par6;
			var22 = var13 * par6;
			var23 = var10 * par7;
			var24 = var12 * par7;
			var25 = var13 * par7;
		}

		int var26 = par1Block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2, par3, par4);

		var8.setBrightness(renderblocks.renderMinY > 0.0D ? var26 : par1Block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2, par3 - 1, par4));
		var8.setColorOpaque_F(var17, var20, var23);
		renderblocks.renderFaceYNeg(par1Block, par2, par3, par4, par1Block.getIcon(0, meta));


		var8.setBrightness(renderblocks.renderMaxY < 1.0D ? var26 : par1Block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2, par3 + 1, par4));
		var8.setColorOpaque_F(var14, var15, var16);
		renderblocks.renderFaceYPos(par1Block, par2, par3, par4, par1Block.getIcon(1, meta));


		Icon var28;

		var8.setBrightness(renderblocks.renderMaxX > 0.0D ? var26 : par1Block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2, par3, par4 - 1));
		var8.setColorOpaque_F(var18, var21, var24);
		var28 = par1Block.getIcon(2, meta);

		renderblocks.renderFaceXNeg(par1Block, par2, par3, par4, var28);

		var8.setBrightness(renderblocks.renderMinX < 1.0D ? var26 : par1Block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2, par3, par4 + 1));
		var8.setColorOpaque_F(var18, var21, var24);
		var28 = par1Block.getIcon(3, meta);

		renderblocks.renderFaceXPos(par1Block, par2, par3, par4, var28);


		var8.setBrightness(renderblocks.renderMinZ > 0.0D ? var26 : par1Block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2 - 1, par3, par4));
		var8.setColorOpaque_F(var19, var22, var25);
		var28 = par1Block.getIcon(4, meta);

		renderblocks.renderFaceZNeg(par1Block, par2, par3, par4, var28);


		var8.setBrightness(renderblocks.renderMaxZ < 1.0D ? var26 : par1Block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2 + 1, par3, par4));
		var8.setColorOpaque_F(var19, var22, var25);
		var28 = par1Block.getIcon(5, meta);

		renderblocks.renderFaceZPos(par1Block, par2, par3, par4, var28);

		return true;
	}

	public static boolean isOpaque(TileEntityDetailed te, int x, int y, int z)
	{
		return te.data.get((x * 8 + z)*8 + y);
	}

}
