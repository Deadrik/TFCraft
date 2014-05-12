package TFC.Render.Blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;
import TFC.TFCBlocks;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderSupportBeam implements ISimpleBlockRenderingHandler
{
	public static boolean RenderWoodSupportBeamH(Block block, int i, int j, int k, RenderBlocks renderblocks)
	{
		IBlockAccess blockAccess = renderblocks.blockAccess;

		Boolean hasVerticalBeam = false;
		Boolean hasHorizontalBeamX = false;
		Boolean hasHorizontalBeamZ = false;

		//if the block directly beneath is a Vertical Support
		if(TFCBlocks.isIdVSupport(blockAccess.getBlockId(i, j-1, k)))
		{	
			renderblocks.setRenderBounds(0.25F, 0.0F, 0.25F, 0.75F, 1.0F, 0.75F);
			renderblocks.renderStandardBlock(block, i, j, k);
			hasVerticalBeam = true;
		}

		//X
		if(/*TFC_Core.isBlockAboveSolid(blockAccess, i, j, k) &&*/ TFCBlocks.isIdVSupport(blockAccess.getBlockId(i-1, j, k))|| TFCBlocks.isIdHSupport(blockAccess.getBlockId(i-1, j, k)))//if the block above is solid and the block at -x is a support beam
		{
			if(TFCBlocks.isIdVSupport(blockAccess.getBlockId(i+1, j, k)) || TFCBlocks.isIdHSupport(blockAccess.getBlockId(i+1, j, k)))//if the block above is solid and the block at +x is a support beam
			{
				if(hasVerticalBeam)//if the block does contain a vertical beam
				{
					renderblocks.setRenderBounds(0.0F, 0.50F, 0.25F, 0.25F, 1.0F, 0.75F);
					renderblocks.renderStandardBlock(block, i, j, k);
					renderblocks.setRenderBounds(0.75F, 0.50F, 0.25F, 1.0F, 1.0F, 0.75F);
					renderblocks.renderStandardBlock(block, i, j, k);
				}
				else if(!hasVerticalBeam)//if the block does not contain a vertical beam
				{
					renderblocks.setRenderBounds(0.0F, 0.50F, 0.25F, 1.0F, 1.0F, 0.75F);
					renderblocks.renderStandardBlock(block, i, j, k);
				}
				hasHorizontalBeamX = true;
			}
			else//if there is only a beam at the negative x and not the positive x
			{
				if(hasVerticalBeam)//if the block does contain a vertical beam
				{
					renderblocks.setRenderBounds(0.0F, 0.50F, 0.25F, 0.25F, 1.0F, 0.75F);
					renderblocks.renderStandardBlock(block, i, j, k);
				}
				else
				{
					renderblocks.setRenderBounds(0.0F, 0.50F, 0.25F, 0.75F, 1.0F, 0.75F);// 3/4 block
					renderblocks.renderStandardBlock(block, i, j, k);
				}
				hasHorizontalBeamX = true;
			}
		}
		else if(/*TFC_Core.isBlockAboveSolid(blockAccess, i, j, k) &&*/ TFCBlocks.isIdVSupport(blockAccess.getBlockId(i+1, j, k)) || TFCBlocks.isIdHSupport(blockAccess.getBlockId(i+1, j, k)))
		{
			if(hasVerticalBeam)//if the block does contain a vertical beam
			{
				renderblocks.setRenderBounds(0.75F, 0.50F, 0.25F, 1.0F, 1.0F, 0.75F);
				renderblocks.renderStandardBlock(block, i, j, k);
			}
			else
			{
				renderblocks.setRenderBounds(0.25F, 0.50F, 0.25F, 1.0F, 1.0F, 0.75F);// 3/4 block
				renderblocks.renderStandardBlock(block, i, j, k);
			}
			hasHorizontalBeamX = true;
		}
		//Z
		if(/*TFC_Core.isBlockAboveSolid(blockAccess, i, j, k) &&*/ TFCBlocks.isIdVSupport(blockAccess.getBlockId(i, j, k-1)) || TFCBlocks.isIdHSupport(blockAccess.getBlockId(i, j, k-1)))
		{
			if(TFCBlocks.isIdVSupport(blockAccess.getBlockId(i, j, k+1)) || TFCBlocks.isIdHSupport(blockAccess.getBlockId(i, j, k+1)))//if the block above is solid and the block at +x is a support beam
			{
				if(hasVerticalBeam)//if the block does contain a vertical beam
				{
					renderblocks.setRenderBounds(0.25F, 0.50F, 0.0F, 0.75F, 1.0F, 0.25F);
					renderblocks.renderStandardBlock(block, i, j, k);
					renderblocks.setRenderBounds(0.25F, 0.50F, 0.75F, 0.75F, 1.0F, 1.0F);
					renderblocks.renderStandardBlock(block, i, j, k);
				}
				else if(!hasVerticalBeam)//if the block does not contain a vertical beam
				{
					renderblocks.setRenderBounds(0.25F, 0.50F, 0.0F, 0.75F, 1.0F, 1.0F);
					renderblocks.renderStandardBlock(block, i, j, k);
				}
				hasHorizontalBeamZ = true;
			}
			else//if there is only a beam at the negative x and not the positive x
			{
				if(hasVerticalBeam)//if the block does contain a vertical beam
				{
					renderblocks.setRenderBounds(0.25F, 0.50F, 0.0F, 0.75F, 1.0F, 0.25F);
					renderblocks.renderStandardBlock(block, i, j, k);
				}
				else
				{
					renderblocks.setRenderBounds(0.25F, 0.50F, 0.0F, 0.75F, 1.0F, 0.75F);// 3/4 block
					renderblocks.renderStandardBlock(block, i, j, k);
				}
				hasHorizontalBeamZ = true;
			}
		}
		else if(/*TFC_Core.isBlockAboveSolid(blockAccess, i, j, k) && */TFCBlocks.isIdVSupport(blockAccess.getBlockId(i, j, k+1)) || TFCBlocks.isIdHSupport(blockAccess.getBlockId(i, j, k+1)))//Top is solid and positive Z is support
		{
			if(hasVerticalBeam)//if the block does contain a vertical beam
			{
				renderblocks.setRenderBounds(0.25F, 0.50F, 0.75F, 0.75F, 1.0F, 1.0F);
				renderblocks.renderStandardBlock(block, i, j, k);
			}
			else
			{
				renderblocks.setRenderBounds(0.25F, 0.50F, 0.25F, 0.75F, 1.0F, 1.0F);
				renderblocks.renderStandardBlock(block, i, j, k);
			}
			hasHorizontalBeamZ = true;
		}

		float minX = -1;
		float minY = -1;
		float minZ = -1;

		float maxX = -1;
		float maxY = -1;
		float maxZ = -1;

		if(hasHorizontalBeamX)
		{
			minX = 0F;
			maxX = 1F;
			minZ = 0.25F;
			maxZ = 0.75F;
		}
		if(hasHorizontalBeamZ)
		{
			if(maxX == -1)
			{
				minX = 0.25F;
				maxX = 0.75F;
			}

			minZ = 0F;
			maxZ = 1F;

		}
		if(hasVerticalBeam)
		{
			minY = 0F;
			maxY = 1F;
			if(maxX == -1)
			{
				minX = 0.25F;
				maxX = 0.75F;
			}
			if(maxZ == -1)
			{
				minZ = 0.25F;
				maxZ = 0.75F;
			}
		}
		else
		{
			minY = 0.5F;
			maxY = 1F;
		}

		renderblocks.setRenderBounds(minX,minY, minZ, maxX, maxY, maxZ);

		return true;
	}

	public static boolean RenderWoodSupportBeamV(Block block, int i, int j, int k, RenderBlocks renderblocks)
	{
		IBlockAccess blockAccess = renderblocks.blockAccess;

		Boolean hasVerticalBeam = false;
		Boolean hasHorizontalBeamX = false;
		Boolean hasHorizontalBeamZ = false;
		
		/*boolean inWater = isSurroundedByWater(blockAccess, i, j, k);
		
		if(inWater)
		{
			Tessellator tess = Tessellator.instance;
			renderblocks.setOverrideBlockTexture(Block.blocksList[Block.waterStill.blockID].getBlockTextureFromSide(0));
			
			tess.setColorRGBA_F(0, 0, 1, 0.5f);
			renderblocks.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
			renderblocks.renderStandardBlock(block, i, j, k);
			
			renderblocks.clearOverrideBlockTexture();
		}*/

		//if the block directly beneath is a Vertical Support or a solid block
		if((blockAccess.isBlockOpaqueCube(i, j-1, k) || TFCBlocks.isIdVSupport(blockAccess.getBlockId(i, j-1, k))) && TFCBlocks.isIdVSupport(block.blockID))
		{	
			renderblocks.setRenderBounds(0.25F, 0.0F, 0.25F, 0.75F, 1.0F, 0.75F);
			renderblocks.renderStandardBlock(block, i, j, k);
			hasVerticalBeam = true;
		}

		//X
		if(/*TFC_Core.isBlockAboveSolid(blockAccess, i, j, k) &&*/ TFCBlocks.isIdVSupport(blockAccess.getBlockId(i-1, j, k)) || TFCBlocks.isIdHSupport(blockAccess.getBlockId(i-1, j, k)))//if the block above is solid and the block at -x is a support beam
		{
			if(TFCBlocks.isIdVSupport(blockAccess.getBlockId(i+1, j, k)) || TFCBlocks.isIdHSupport(blockAccess.getBlockId(i+1, j, k)))//if the block above is solid and the block at +x is a support beam
			{
				if(hasVerticalBeam)//if the block does contain a vertical beam
				{
					renderblocks.setRenderBounds(0.0F, 0.50F, 0.25F, 0.25F, 1.0F, 0.75F);
					renderblocks.renderStandardBlock(block, i, j, k);
					renderblocks.setRenderBounds(0.75F, 0.50F, 0.25F, 1.0F, 1.0F, 0.75F);
					renderblocks.renderStandardBlock(block, i, j, k);
				}
				else if(!hasVerticalBeam)//if the block does not contain a vertical beam
				{
					renderblocks.setRenderBounds(0.0F, 0.50F, 0.25F, 1.0F, 1.0F, 0.75F);
					renderblocks.renderStandardBlock(block, i, j, k);
				}
				hasHorizontalBeamX = true;
			}
			else//if there is only a beam at the negative x and not the positive x
			{
				if(hasVerticalBeam)//if the block does contain a vertical beam
				{
					renderblocks.setRenderBounds(0.0F, 0.50F, 0.25F, 0.25F, 1.0F, 0.75F);
					renderblocks.renderStandardBlock(block, i, j, k);
				}
				else
				{
					renderblocks.setRenderBounds(0.0F, 0.50F, 0.25F, 0.75F, 1.0F, 0.75F);// 3/4 block
					renderblocks.renderStandardBlock(block, i, j, k);
				}
				hasHorizontalBeamX = true;
			}
		}
		else if(/*TFC_Core.isBlockAboveSolid(blockAccess, i, j, k) && */TFCBlocks.isIdVSupport(blockAccess.getBlockId(i+1, j, k)) || TFCBlocks.isIdHSupport(blockAccess.getBlockId(i+1, j, k)))
		{
			if(hasVerticalBeam)//if the block does contain a vertical beam
			{
				renderblocks.setRenderBounds(0.75F, 0.50F, 0.25F, 1.0F, 1.0F, 0.75F);
				renderblocks.renderStandardBlock(block, i, j, k);
			}
			else
			{
				renderblocks.setRenderBounds(0.25F, 0.50F, 0.25F, 1.0F, 1.0F, 0.75F);// 3/4 block
				renderblocks.renderStandardBlock(block, i, j, k);
			}
			hasHorizontalBeamX = true;
		}
		//Z
		if(/*TFC_Core.isBlockAboveSolid(blockAccess, i, j, k) && */TFCBlocks.isIdVSupport(blockAccess.getBlockId(i, j, k-1)) || TFCBlocks.isIdHSupport(blockAccess.getBlockId(i, j, k-1)))
		{
			if(TFCBlocks.isIdVSupport(blockAccess.getBlockId(i, j, k+1)) || TFCBlocks.isIdHSupport(blockAccess.getBlockId(i, j, k+1)))//if the block above is solid and the block at +x is a support beam
			{
				if(hasVerticalBeam)//if the block does contain a vertical beam
				{
					renderblocks.setRenderBounds(0.25F, 0.50F, 0.0F, 0.75F, 1.0F, 0.25F);
					renderblocks.renderStandardBlock(block, i, j, k);
					renderblocks.setRenderBounds(0.25F, 0.50F, 0.75F, 0.75F, 1.0F, 1.0F);
					renderblocks.renderStandardBlock(block, i, j, k);
				}
				else if(!hasVerticalBeam)//if the block does not contain a vertical beam
				{
					renderblocks.setRenderBounds(0.25F, 0.50F, 0.0F, 0.75F, 1.0F, 1.0F);
					renderblocks.renderStandardBlock(block, i, j, k);
				}
				hasHorizontalBeamZ = true;
			}
			else//if there is only a beam at the negative x and not the positive x
			{
				if(hasVerticalBeam)//if the block does contain a vertical beam
				{
					renderblocks.setRenderBounds(0.25F, 0.50F, 0.0F, 0.75F, 1.0F, 0.25F);
					renderblocks.renderStandardBlock(block, i, j, k);
				}
				else
				{
					renderblocks.setRenderBounds(0.25F, 0.50F, 0.0F, 0.75F, 1.0F, 0.75F);// 3/4 block
					renderblocks.renderStandardBlock(block, i, j, k);
				}
				hasHorizontalBeamZ = true;
			}
		}
		else if(/*TFC_Core.isBlockAboveSolid(blockAccess, i, j, k) && */TFCBlocks.isIdVSupport(blockAccess.getBlockId(i, j, k+1)) || TFCBlocks.isIdHSupport(blockAccess.getBlockId(i, j, k+1)))//Top is solid and positive Z is support
		{
			if(hasVerticalBeam)//if the block does contain a vertical beam
			{
				renderblocks.setRenderBounds(0.25F, 0.50F, 0.75F, 0.75F, 1.0F, 1.0F);
				renderblocks.renderStandardBlock(block, i, j, k);
			}
			else
			{
				renderblocks.setRenderBounds(0.25F, 0.50F, 0.25F, 0.75F, 1.0F, 1.0F);
				renderblocks.renderStandardBlock(block, i, j, k);
			}
			hasHorizontalBeamZ = true;
		}

		float minX = 0.25F;
		float minY = 0;
		float minZ = 0.25F;

		float maxX = 0.75F;
		float maxY = 1;
		float maxZ = 0.75F;


		renderblocks.setRenderBounds(minX,minY, minZ, maxX, maxY, maxZ);

		return true;
	}

	public static boolean isSurroundedByWater(IBlockAccess access, int i, int j, int k)
	{
		if(access.getBlockMaterial(i, j+1, k) == Material.water)
			return true;
		
		if(access.getBlockMaterial(i+1, j, k) == Material.water || access.getBlockMaterial(i-1, j, k) == Material.water ||
				access.getBlockMaterial(i, j, k+1) == Material.water || access.getBlockMaterial(i, j, k-1) == Material.water)
			return true;
		
		return false;
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID,
			RenderBlocks renderer) 
	{
		renderer.setOverrideBlockTexture(block.getIcon(0, metadata));
		if (modelID == TFCBlocks.woodSupportRenderIdH)
        {
			renderer.setRenderBounds(0.25f, 0.25f, 0f, 0.75f, 0.75f, 1f);
			renderInvBlock(block, renderer);
        }
        else if (modelID == TFCBlocks.woodSupportRenderIdV)
        {
        	renderer.setRenderBounds(0.25f, 0f, 0.25f, 0.75f, 1f, 0.75f);
			renderInvBlock(block, renderer);
        }
		renderer.clearOverrideBlockTexture();
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int i, int j, int k,
			Block block, int modelId, RenderBlocks renderer) 
	{
		if (modelId == TFCBlocks.woodSupportRenderIdH)
        {
            return RenderWoodSupportBeamH(block, i, j, k, renderer);
        }
        else if (modelId == TFCBlocks.woodSupportRenderIdV)
        {
            return RenderWoodSupportBeamV(block, i, j, k, renderer);
        }
		return false;
	}

	@Override
	public boolean shouldRender3DInInventory() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public int getRenderId() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public static void renderInvBlock(Block block, RenderBlocks renderer)
	{
		Tessellator var14 = Tessellator.instance;
		var14.startDrawingQuads();
		var14.setNormal(0.0F, -1.0F, 0.0F);
		renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(0, 0));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(0.0F, 1.0F, 0.0F);
		renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(1, 0));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(0.0F, 0.0F, -1.0F);
		renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(2, 0));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(0.0F, 0.0F, 1.0F);
		renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(3, 0));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(-1.0F, 0.0F, 0.0F);
		renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(4, 0));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(1.0F, 0.0F, 0.0F);
		renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(5, 0));
		var14.draw();
	}
}
