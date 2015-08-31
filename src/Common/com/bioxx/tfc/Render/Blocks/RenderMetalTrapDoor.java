package com.bioxx.tfc.Render.Blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

import com.bioxx.tfc.Blocks.BlockMetalSheet;
import com.bioxx.tfc.Blocks.BlockMetalTrapDoor;
import com.bioxx.tfc.TileEntities.TEMetalTrapDoor;
import com.bioxx.tfc.api.TFCBlocks;

public class RenderMetalTrapDoor implements ISimpleBlockRenderingHandler
{
	public static boolean render(Block block, int i, int j, int k, RenderBlocks renderer)
	{
		IBlockAccess access = renderer.blockAccess;
		TEMetalTrapDoor te = (TEMetalTrapDoor)access.getTileEntity(i, j, k);
		int side = te.data & 7;
		int hinge = te.data >> 4;
		float f = 0.0625f;
		//float f3 = f/2;
		//float f2 = 0.125f;

		float fx = 0;
		float fy = 0;
		float fz = 0;
		float fx2 = 1;
		float fy2 = 1;
		float fz2 = 1;
		renderer.renderAllFaces = true;
		if (BlockMetalTrapDoor.isTrapdoorOpen(access.getBlockMetadata(i, j, k)))
		{
			if(hinge == 0)
			{
				switch(side)
				{
				case 0:
				{
					fx2 = f;
					fy2 = 1-f;
					break;
				}
				case 1:
				{
					fy = f;
					fx2 = f;
					break;
				}
				case 2:
				{
					fx2 = f;
					fz2 = 1-f;
					break;
				}
				case 3:
				{
					fz = f;
					fx2 = f;
					break;
				}
				case 4:
				{
					fy2 = f;
					fx2 = 1-f;
					break;
				}
				case 5:
				{
					fy2 = f;
					fx = f;
					break;
				}
				default:
					fx2 = f;
					break;
				}
			}
			else if(hinge == 1)
			{
				switch(side)
				{
				case 0:
				{
					fz2 = f;
					fy2 = 1-f;
					break;
				}
				case 1:
				{
					fy = f;
					fz2 = f;
					break;
				}
				case 2:
				{
					fy2 = f;
					fz2 = 1-f;
					break;
				}
				case 3:
				{
					fy2 = f;
					fz = f;
					break;
				}
				case 4:
				{
					fx2 = 1-f;
					fz2 = f;
					break;
				}
				case 5:
				{
					fx = f;
					fz2 = f;
					break;
				}
				default:
					fz2 = f;
					break;
				}
			}
			else if(hinge == 2)
			{
				switch(side)
				{
				case 0:
				{
					fx = 1-f;
					fy2 = 1-f;
					break;
				}
				case 1:
				{
					fx = 1-f;
					fy = f;
					break;
				}
				case 2:
				{
					fx = 1-f;
					fz2 = 1-f;
					break;
				}
				case 3:
				{
					fx = 1-f;
					fz = f;
					break;
				}
				case 4:
				{
					fx2 = 1-f;
					fy = 1-f;
					break;
				}
				case 5:
				{
					fy = 1-f;
					fx = f;
					break;
				}
				default:
					fx = 1-f;
					break;

				}
			}
			else if(hinge == 3)
			{
				switch(side)
				{
				case 0:
				{
					fy2 = 1-f;
					fz = 1-f;
					break;
				}
				case 1:
				{
					fy = f;
					fz = 1-f;
					break;
				}
				case 2:
				{
					fy = 1-f;
					fz2 = 1-f;
					break;
				}
				case 3:
				{
					fy = 1-f;
					fz = f;
					break;
				}
				case 4:
				{
					fx2 = 1-f;
					fz = 1-f;
					break;
				}
				case 5:
				{
					fx = f;
					fz = 1-f;
					break;
				}
				default:
					fz = 1-f;
					break;
				}
			}
			renderer.setRenderBounds(fx+0.0001f, fy+0.0001f, fz+0.0001f, fx2-0.0001f, fy2-0.0001f, fz2-0.0001f);
			renderer.renderStandardBlock(block, i, j, k);
		}
		else
		{
			if(side == 0)
			{
				fy = 1-f;
				switch(hinge)
				{
				case 0:
					fx = f;
					break;
				case 1:
					fz = f;
					break;
				case 2:
					fx2 = 1-f;
					break;
				case 3:
					fz2 = 1-f;
					break;
				}
			}
			else if(side == 1)
			{
				fy2 = f;
				switch(hinge)
				{
				case 0:
					fx = f;
					break;
				case 1:
					fz = f;
					break;
				case 2:
					fx2 = 1-f;
					break;
				case 3:
					fz2 = 1-f;
					break;
				}
			}
			else if(side == 2)
			{
				fz = 1-f;
				switch(hinge)
				{
				case 0:
					fx = f;
					break;
				case 1:
					fy = f;
					break;
				case 2:
					fx2 = 1-f;
					break;
				case 3:
					fy2 = 1-f;
					break;
				}
			}
			else if(side == 3)
			{
				fz2 = f;
				switch(hinge)
				{
				case 0:
					fx = f;
					break;
				case 1:
					fy = f;
					break;
				case 2:
					fx2 = 1-f;
					break;
				case 3:
					fy2 = 1-f;
					break;
				}
			}
			else if(side == 4)
			{
				fx = 1-f;
				switch(hinge)
				{
				case 0:
					fy = f;
					break;
				case 1:
					fz = f;
					break;
				case 2:
					fy2 = 1-f;
					break;
				case 3:
					fz2 = 1-f;
					break;
				}
			}
			else if(side == 5)
			{
				fx2 = f;
				switch(hinge)
				{
				case 0:
					fy = f;
					break;
				case 1:
					fz = f;
					break;
				case 2:
					fy2 = 1-f;
					break;
				case 3:
					fz2 = 1-f;
					break;
				}
			}
			renderer.setRenderBounds(fx, fy, fz, fx2, fy2, fz2);
			renderer.renderStandardBlock(block, i, j, k);
		}

		int hingeID = te.sheetStack != null ? Math.min(((BlockMetalSheet)TFCBlocks.metalSheet).icons.length-1, te.sheetStack.getItemDamage() >> 5) : 0;
		
		boolean breaking = renderer.overrideBlockTexture != null;
		
		if ( ! breaking )
			renderer.setOverrideBlockTexture(((BlockMetalSheet)TFCBlocks.metalSheet).icons[hingeID]);

		drawHinges(block, i, j, k, renderer, side, hinge);
		
		if ( ! breaking )
			renderer.clearOverrideBlockTexture();
		
		renderer.renderAllFaces = false;
		return true;
	}

	private static void drawHinges(Block block, int i, int j, int k,
			RenderBlocks renderer, int side, int hinge) 
	{
		float f = 0.0625f;
		float f3 = f/2;
		//float f2 = 0.125f;
		float hingeMin = 0;
		float hingeMin2 = f+f3;
		float hingeMax = 1-f-f3;
		float hingeMax2 = 1;

		if(hinge == 0)
		{
			switch(side)
			{
			case 0:
			{
				renderer.setRenderBounds(hingeMin, hingeMax, 0.1, hingeMin2, hingeMax2, 0.4);
				renderer.renderStandardBlock(block, i, j, k);
				renderer.setRenderBounds(hingeMin, hingeMax, 0.6, hingeMin2, hingeMax2, 0.9);
				renderer.renderStandardBlock(block, i, j, k);
				break;
			}
			case 1:
			{
				renderer.setRenderBounds(hingeMin, hingeMin, 0.1, hingeMin2, hingeMin2, 0.4);
				renderer.renderStandardBlock(block, i, j, k);
				renderer.setRenderBounds(hingeMin, hingeMin, 0.6, hingeMin2, hingeMin2, 0.9);
				renderer.renderStandardBlock(block, i, j, k);
				break;
			}
			case 2:
			{
				renderer.setRenderBounds(hingeMin, 0.1, hingeMax, hingeMin2, 0.4, hingeMax2);
				renderer.renderStandardBlock(block, i, j, k);
				renderer.setRenderBounds(hingeMin, 0.6, hingeMax, hingeMin2, 0.9, hingeMax2);
				renderer.renderStandardBlock(block, i, j, k);
				break;
			}
			case 3:
			{
				renderer.setRenderBounds(hingeMin, 0.1, hingeMin, hingeMin2, 0.4, hingeMin2);
				renderer.renderStandardBlock(block, i, j, k);
				renderer.setRenderBounds(hingeMin, 0.6, hingeMin, hingeMin2, 0.9, hingeMin2);
				renderer.renderStandardBlock(block, i, j, k);
				break;
			}
			case 4:
			{
				renderer.setRenderBounds(hingeMax, hingeMin, 0.1, hingeMax2, hingeMin2, 0.4);
				renderer.renderStandardBlock(block, i, j, k);
				renderer.setRenderBounds(hingeMax, hingeMin, 0.6, hingeMax2, hingeMin2, 0.9);
				renderer.renderStandardBlock(block, i, j, k);
				break;
			}
			case 5:
			{
				renderer.setRenderBounds(hingeMin, hingeMin, 0.1, hingeMin2, hingeMin2, 0.4);
				renderer.renderStandardBlock(block, i, j, k);
				renderer.setRenderBounds(hingeMin, hingeMin, 0.6, hingeMin2, hingeMin2, 0.9);
				renderer.renderStandardBlock(block, i, j, k);
				break;
			}
			}
		}
		else if(hinge == 1)
		{
			switch(side)
			{
			case 0:
			{
				renderer.setRenderBounds(0.1, hingeMax, hingeMin, 0.4, hingeMax2, hingeMin2);
				renderer.renderStandardBlock(block, i, j, k);
				renderer.setRenderBounds(0.6, hingeMax, hingeMin, 0.9, hingeMax2, hingeMin2);
				renderer.renderStandardBlock(block, i, j, k);
				break;
			}
			case 1:
			{
				renderer.setRenderBounds(0.1, hingeMin, hingeMin, 0.4, hingeMin2, hingeMin2);
				renderer.renderStandardBlock(block, i, j, k);
				renderer.setRenderBounds(0.6, hingeMin, hingeMin, 0.9, hingeMin2, hingeMin2);
				renderer.renderStandardBlock(block, i, j, k);
				break;
			}
			case 2:
			{
				renderer.setRenderBounds(0.1, hingeMin, hingeMax, 0.4, hingeMin2, hingeMax2);
				renderer.renderStandardBlock(block, i, j, k);
				renderer.setRenderBounds(0.6, hingeMin, hingeMax, 0.9, hingeMin2, hingeMax2);
				renderer.renderStandardBlock(block, i, j, k);
				break;
			}
			case 3:
			{
				renderer.setRenderBounds(0.1, hingeMin, hingeMin, 0.4, hingeMin2, hingeMin2);
				renderer.renderStandardBlock(block, i, j, k);
				renderer.setRenderBounds(0.6, hingeMin, hingeMin, 0.9, hingeMin2, hingeMin2);
				renderer.renderStandardBlock(block, i, j, k);
				break;
			}
			case 4:
			{
				renderer.setRenderBounds(hingeMax, 0.1, hingeMin, hingeMax2, 0.4, hingeMin2);
				renderer.renderStandardBlock(block, i, j, k);
				renderer.setRenderBounds(hingeMax, 0.6, hingeMin, hingeMax2, 0.9, hingeMin2);
				renderer.renderStandardBlock(block, i, j, k);
				break;
			}
			case 5:
			{
				renderer.setRenderBounds(hingeMin, 0.1, hingeMin, hingeMin2, 0.4, hingeMin2);
				renderer.renderStandardBlock(block, i, j, k);
				renderer.setRenderBounds(hingeMin, 0.6, hingeMin, hingeMin2, 0.9, hingeMin2);
				renderer.renderStandardBlock(block, i, j, k);
				break;
			}
			}
		}
		else if(hinge == 2)
		{
			switch(side)
			{
			case 0:
			{
				renderer.setRenderBounds(hingeMax, hingeMax, 0.1, hingeMax2, hingeMax2, 0.4);
				renderer.renderStandardBlock(block, i, j, k);
				renderer.setRenderBounds(hingeMax, hingeMax, 0.6, hingeMax2, hingeMax2, 0.9);
				renderer.renderStandardBlock(block, i, j, k);
				break;
			}
			case 1:
			{
				renderer.setRenderBounds(hingeMax, hingeMin, 0.1, hingeMax2, hingeMin2, 0.4);
				renderer.renderStandardBlock(block, i, j, k);
				renderer.setRenderBounds(hingeMax, hingeMin, 0.6, hingeMax2, hingeMin2, 0.9);
				renderer.renderStandardBlock(block, i, j, k);
				break;
			}
			case 2:
			{
				renderer.setRenderBounds(hingeMax, 0.1, hingeMax, hingeMax2, 0.4, hingeMax2);
				renderer.renderStandardBlock(block, i, j, k);
				renderer.setRenderBounds(hingeMax, 0.6, hingeMax, hingeMax2, 0.9, hingeMax2);
				renderer.renderStandardBlock(block, i, j, k);
				break;
			}
			case 3:
			{
				renderer.setRenderBounds(hingeMax, 0.1, hingeMin, hingeMax2, 0.4, hingeMin2);
				renderer.renderStandardBlock(block, i, j, k);
				renderer.setRenderBounds(hingeMax, 0.6, hingeMin, hingeMax2, 0.9, hingeMin2);
				renderer.renderStandardBlock(block, i, j, k);
				break;
			}
			case 4:
			{
				renderer.setRenderBounds(hingeMax, hingeMax, 0.1, hingeMax2, hingeMax2, 0.4);
				renderer.renderStandardBlock(block, i, j, k);
				renderer.setRenderBounds(hingeMax, hingeMax, 0.6, hingeMax2, hingeMax2, 0.9);
				renderer.renderStandardBlock(block, i, j, k);
				break;
			}
			case 5:
			{
				renderer.setRenderBounds(hingeMin, hingeMax, 0.1, hingeMin2, hingeMax2, 0.4);
				renderer.renderStandardBlock(block, i, j, k);
				renderer.setRenderBounds(hingeMin, hingeMax, 0.6, hingeMin2, hingeMax2, 0.9);
				renderer.renderStandardBlock(block, i, j, k);
				break;
			}
			}
		}
		else if(hinge == 3)
		{
			switch(side)
			{
			case 0:
			{
				renderer.setRenderBounds(0.1, hingeMax, hingeMax, 0.4, hingeMax2, hingeMax2);
				renderer.renderStandardBlock(block, i, j, k);
				renderer.setRenderBounds(0.6, hingeMax, hingeMax, 0.9, hingeMax2, hingeMax2);
				renderer.renderStandardBlock(block, i, j, k);
				break;
			}
			case 1:
			{
				renderer.setRenderBounds(0.1, hingeMin, hingeMax, 0.4, hingeMin2, hingeMax2);
				renderer.renderStandardBlock(block, i, j, k);
				renderer.setRenderBounds(0.6, hingeMin, hingeMax, 0.9, hingeMin2, hingeMax2);
				renderer.renderStandardBlock(block, i, j, k);
				break;
			}
			case 2:
			{
				renderer.setRenderBounds(0.1, hingeMax, hingeMax, 0.4, hingeMax2, hingeMax2);
				renderer.renderStandardBlock(block, i, j, k);
				renderer.setRenderBounds(0.6, hingeMax, hingeMax, 0.9, hingeMax2, hingeMax2);
				renderer.renderStandardBlock(block, i, j, k);
				break;
			}
			case 3:
			{
				renderer.setRenderBounds(0.1, hingeMax, hingeMin, 0.4, hingeMax2, hingeMin2);
				renderer.renderStandardBlock(block, i, j, k);
				renderer.setRenderBounds(0.6, hingeMax, hingeMin, 0.9, hingeMax2, hingeMin2);
				renderer.renderStandardBlock(block, i, j, k);
				break;
			}
			case 4:
			{
				renderer.setRenderBounds(hingeMax, 0.1, hingeMax, hingeMax2, 0.4, hingeMax2);
				renderer.renderStandardBlock(block, i, j, k);
				renderer.setRenderBounds(hingeMax, 0.6, hingeMax, hingeMax2, 0.9, hingeMax2);
				renderer.renderStandardBlock(block, i, j, k);
				break;
			}
			case 5:
			{
				renderer.setRenderBounds(hingeMin, 0.1, hingeMax, hingeMin2, 0.4, hingeMax2);
				renderer.renderStandardBlock(block, i, j, k);
				renderer.setRenderBounds(hingeMin, 0.6, hingeMax, hingeMin2, 0.9, hingeMax2);
				renderer.renderStandardBlock(block, i, j, k);
				break;
			}
			}
		}
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer)
	{
		renderer.setRenderBounds(0.125F, 0.4F, 0F, 1F, 0.475F, 1f);
		renderInvBlock(block, metadata&255, renderer);
		renderer.setRenderBounds(0.0F, 0.4F, 0.1F, 0.125F, 0.525F, 0.4f);
		int index = Math.min(((BlockMetalSheet)TFCBlocks.metalSheet).icons.length-1, metadata >> 5);
		renderInvBlock(block, ((BlockMetalSheet)TFCBlocks.metalSheet).icons[index], renderer);
		renderer.setRenderBounds(0.0F, 0.4F, 0.6F, 0.125F, 0.525F, 0.9f);
		renderInvBlock(block, ((BlockMetalSheet)TFCBlocks.metalSheet).icons[index], renderer);
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
	{
		return render(block, x, y, z, renderer);
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

	public static void renderInvBlock(Block block, int meta, RenderBlocks renderer)
	{
		Tessellator var14 = Tessellator.instance;
		var14.startDrawingQuads();
		var14.setNormal(0.0F, -1.0F, 0.0F);
		renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(0, meta));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(0.0F, 1.0F, 0.0F);
		renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(1, meta));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(0.0F, 0.0F, -1.0F);
		renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(2, meta));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(0.0F, 0.0F, 1.0F);
		renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(3, meta));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(-1.0F, 0.0F, 0.0F);
		renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(4, meta));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(1.0F, 0.0F, 0.0F);
		renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(5, meta));
		var14.draw();
	}

	public static void renderInvBlock(Block block, IIcon ico, RenderBlocks renderer)
	{
		Tessellator var14 = Tessellator.instance;
		var14.startDrawingQuads();
		var14.setNormal(0.0F, -1.0F, 0.0F);
		renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, ico);
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(0.0F, 1.0F, 0.0F);
		renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, ico);
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(0.0F, 0.0F, -1.0F);
		renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, ico);
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(0.0F, 0.0F, 1.0F);
		renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, ico);
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(-1.0F, 0.0F, 0.0F);
		renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, ico);
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(1.0F, 0.0F, 0.0F);
		renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, ico);
		var14.draw();
	}

}
