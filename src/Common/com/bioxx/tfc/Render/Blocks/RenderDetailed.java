package com.bioxx.tfc.Render.Blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.util.IIcon;

import com.bioxx.tfc.Render.RenderBlocksLightCache;
import com.bioxx.tfc.TileEntities.TEDetailed;

public class RenderDetailed 
{
	private static RenderBlocksLightCache renderer;

	public static boolean renderBlockDetailed(Block block, int i, int j, int k, RenderBlocks renderblocks)
	{
		TEDetailed te = (TEDetailed) renderblocks.blockAccess.getTileEntity(i, j, k);

		if(renderer == null)
			renderer = new RenderBlocksLightCache(renderblocks);
		else
			renderer.update(renderblocks);

		// Capture full block lighting data...
		renderer.disableRender();
		renderer.setRenderBounds(0,0,0,1,1,1);
		renderer.renderStandardBlock(block, i, j, k);
		renderer.enableRender();
		
		if(te.typeID <= 0)
			return false;

		int type = te.typeID;
		int meta = te.metaID;

		IIcon myTexture = renderblocks.overrideBlockTexture == null ? Block.getBlockById(te.typeID).getIcon(0, te.metaID) : renderblocks.overrideBlockTexture;
		
		for(int subX = 0; subX < 2; subX++)
		{
			for(int subZ = 0; subZ < 2; subZ++)
			{
				for(int subY = 0; subY < 2; subY++)
				{
					if(!te.isQuadSolid(subX, subY, subZ))
					{
						renderMiniBlock(block, i, j, k, subX, subY, subZ, te, type, meta, myTexture);
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
						renderer.renderCachedBlock(block, i, j, k, myTexture);
					}
				}
			}
		}
		renderer.clearOverrideBlockTexture();
		return true;
	}

	private static void renderMiniBlock(Block block, int i, int j, int k, int x, int y, int z, TEDetailed te, int type, int meta, IIcon myTexture)
	{
		for(int subX = x*4; subX < 4+x*4; subX++)
		{
			for(int subZ = z*4; subZ < 4+z*4; subZ++)
			{
				for(int subY = y*4; subY < 4+y*4; subY++)
				{
					boolean subExists = isOpaque(te,subX, subY, subZ);
					if ( subExists )
					{
						boolean isVisible = isTransparent(te,subX-1, subY, subZ) || 
								isTransparent(te,subX+1, subY, subZ) ||
								isTransparent(te,subX, subY-1, subZ) ||
								isTransparent(te,subX, subY+1, subZ) ||
								isTransparent(te,subX, subY, subZ-1) ||
								isTransparent(te,subX, subY, subZ+1);
						
						if ( isVisible )
						{
							float minX = 0.125f*subX;
							float maxX = minX + 0.125f;
							float minY = 0.125f*subY;
							float maxY = minY + 0.125f;
							float minZ = 0.125f*subZ;
							float maxZ = minZ + 0.125f;
	
							renderer.setRenderBounds(minX, minY, minZ, maxX, maxY, maxZ);
							renderer.renderCachedBlock(block, i, j, k, myTexture);
						}
					}
				}
			}
		}
	}

	public static boolean isOpaque(TEDetailed te, int x, int y, int z)
	{
		return te.data.get((x * 8 + z)*8 + y);
	}
	
	public static boolean isTransparent(TEDetailed te, int x, int y, int z)
	{
		if ( x < 0 || x >= 8 || y < 0 || y >= 8 || z < 0 || z >= 8 )
			return true;
		
		return ! te.data.get((x * 8 + z)*8 + y);
	}
}
