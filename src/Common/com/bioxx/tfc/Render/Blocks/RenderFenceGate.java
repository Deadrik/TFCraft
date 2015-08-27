package com.bioxx.tfc.Render.Blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.TileEntities.TEFenceGate;
import com.bioxx.tfc.api.Interfaces.IMultipleBlock;

public class RenderFenceGate  implements ISimpleBlockRenderingHandler 
{
	/*private static float pixel3 = 3f / 16f;
	private static float pixel5 = 5f / 16f;
	private static float pixel12 = 12f / 16f;
	private static float pixel14 = 14f / 16f;*/

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int par2, int par3, int par4, Block block, int modelId, RenderBlocks renderer)
	{
		Block par1BlockFenceGate = ((IMultipleBlock)block).getBlockTypeForRender();
		boolean flag = true;
		int l = ((TEFenceGate)renderer.blockAccess.getTileEntity(par2, par3, par4)).getDirection();
		boolean flag1 = ((TEFenceGate)renderer.blockAccess.getTileEntity(par2, par3, par4)).getOpen();
		int i1 = BlockDirectional.getDirection(l);
		float f = 0.375F;
		float f1 = 0.5625F;
		float f2 = 0.75F;
		float f3 = 0.9375F;
		float f4 = 0.3125F;
		float f5 = 1.0F;

		if ((i1 == 2 || i1 == 0) && 
				renderer.blockAccess.getBlock(par2 - 1, par3, par4) instanceof com.bioxx.tfc.Blocks.Vanilla.BlockCustomWall &&
				renderer.blockAccess.getBlock(par2 + 1, par3, par4) instanceof com.bioxx.tfc.Blocks.Vanilla.BlockCustomWall ||
				(i1 == 3 || i1 == 1) &&
				renderer.blockAccess.getBlock(par2, par3, par4 - 1) instanceof com.bioxx.tfc.Blocks.Vanilla.BlockCustomWall &&
				renderer.blockAccess.getBlock(par2, par3, par4 + 1) instanceof com.bioxx.tfc.Blocks.Vanilla.BlockCustomWall)
		{
			f -= 0.1875F;
			f1 -= 0.1875F;
			f2 -= 0.1875F;
			f3 -= 0.1875F;
			f4 -= 0.1875F;
			f5 -= 0.1875F;
		}

		float f6;
		float f7;
		float f8;
		float f9;

		if (i1 != 3 && i1 != 1)
		{
			f6 = 0.0F;
			f8 = 0.125F;
			f7 = 0.4375F;
			f9 = 0.5625F;
			renderer.setRenderBounds(f6, f4, f7, f8, f5, f9);
			renderer.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
			f6 = 0.875F;
			f8 = 1.0F;
			renderer.setRenderBounds(f6, f4, f7, f8, f5, f9);
			renderer.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
		}
		else
		{
			renderer.uvRotateTop = 1;
			f6 = 0.4375F;
			f8 = 0.5625F;
			f7 = 0.0F;
			f9 = 0.125F;
			renderer.setRenderBounds(f6, f4, f7, f8, f5, f9);
			renderer.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
			f7 = 0.875F;
			f9 = 1.0F;
			renderer.setRenderBounds(f6, f4, f7, f8, f5, f9);
			renderer.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
			renderer.uvRotateTop = 0;
		}

		if (flag1)
		{
			if (i1 == 2 || i1 == 0)
				renderer.uvRotateTop = 1;

			//float f10;
			//float f11;
			//float f12;

			if (i1 == 3)
			{
				f6 = 0.0F;
				f8 = 0.125F;
				f7 = 0.875F;
				f9 = 1.0F;
				//f10 = 0.5625F;
				//f12 = 0.8125F;
				//f11 = 0.9375F;
				renderer.setRenderBounds(0.8125D, f, 0.0D, 0.9375D, f3, 0.125D);
				renderer.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
				renderer.setRenderBounds(0.8125D, f, 0.875D, 0.9375D, f3, 1.0D);
				renderer.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
				renderer.setRenderBounds(0.5625D, f, 0.0D, 0.8125D, f1, 0.125D);
				renderer.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
				renderer.setRenderBounds(0.5625D, f, 0.875D, 0.8125D, f1, 1.0D);
				renderer.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
				renderer.setRenderBounds(0.5625D, f2, 0.0D, 0.8125D, f3, 0.125D);
				renderer.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
				renderer.setRenderBounds(0.5625D, f2, 0.875D, 0.8125D, f3, 1.0D);
				renderer.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
			}
			else if (i1 == 1)
			{
				f6 = 0.0F;
				f8 = 0.125F;
				f7 = 0.875F;
				f9 = 1.0F;
				//f10 = 0.0625F;
				//f12 = 0.1875F;
				//f11 = 0.4375F;
				renderer.setRenderBounds(0.0625D, f, 0.0D, 0.1875D, f3, 0.125D);
				renderer.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
				renderer.setRenderBounds(0.0625D, f, 0.875D, 0.1875D, f3, 1.0D);
				renderer.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
				renderer.setRenderBounds(0.1875D, f, 0.0D, 0.4375D, f1, 0.125D);
				renderer.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
				renderer.setRenderBounds(0.1875D, f, 0.875D, 0.4375D, f1, 1.0D);
				renderer.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
				renderer.setRenderBounds(0.1875D, f2, 0.0D, 0.4375D, f3, 0.125D);
				renderer.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
				renderer.setRenderBounds(0.1875D, f2, 0.875D, 0.4375D, f3, 1.0D);
				renderer.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
			}
			else if (i1 == 0)
			{
				f6 = 0.0F;
				f8 = 0.125F;
				f7 = 0.875F;
				f9 = 1.0F;
				//f10 = 0.5625F;
				//f12 = 0.8125F;
				//f11 = 0.9375F;
				renderer.setRenderBounds(0.0D, f, 0.8125D, 0.125D, f3, 0.9375D);
				renderer.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
				renderer.setRenderBounds(0.875D, f, 0.8125D, 1.0D, f3, 0.9375D);
				renderer.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
				renderer.setRenderBounds(0.0D, f, 0.5625D, 0.125D, f1, 0.8125D);
				renderer.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
				renderer.setRenderBounds(0.875D, f, 0.5625D, 1.0D, f1, 0.8125D);
				renderer.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
				renderer.setRenderBounds(0.0D, f2, 0.5625D, 0.125D, f3, 0.8125D);
				renderer.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
				renderer.setRenderBounds(0.875D, f2, 0.5625D, 1.0D, f3, 0.8125D);
				renderer.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
			}
			else if (i1 == 2)
			{
				f6 = 0.0F;
				f8 = 0.125F;
				f7 = 0.875F;
				f9 = 1.0F;
				//f10 = 0.0625F;
				//f12 = 0.1875F;
				//f11 = 0.4375F;
				renderer.setRenderBounds(0.0D, f, 0.0625D, 0.125D, f3, 0.1875D);
				renderer.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
				renderer.setRenderBounds(0.875D, f, 0.0625D, 1.0D, f3, 0.1875D);
				renderer.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
				renderer.setRenderBounds(0.0D, f, 0.1875D, 0.125D, f1, 0.4375D);
				renderer.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
				renderer.setRenderBounds(0.875D, f, 0.1875D, 1.0D, f1, 0.4375D);
				renderer.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
				renderer.setRenderBounds(0.0D, f2, 0.1875D, 0.125D, f3, 0.4375D);
				renderer.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
				renderer.setRenderBounds(0.875D, f2, 0.1875D, 1.0D, f3, 0.4375D);
				renderer.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
			}
		}
		else if (i1 != 3 && i1 != 1)
		{
			f6 = 0.375F;
			f8 = 0.5F;
			f7 = 0.4375F;
			f9 = 0.5625F;
			renderer.setRenderBounds(f6, f, f7, f8, f3, f9);
			renderer.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
			f6 = 0.5F;
			f8 = 0.625F;
			renderer.setRenderBounds(f6, f, f7, f8, f3, f9);
			renderer.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
			f6 = 0.625F;
			f8 = 0.875F;
			renderer.setRenderBounds(f6, f, f7, f8, f1, f9);
			renderer.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
			renderer.setRenderBounds(f6, f2, f7, f8, f3, f9);
			renderer.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
			f6 = 0.125F;
			f8 = 0.375F;
			renderer.setRenderBounds(f6, f, f7, f8, f1, f9);
			renderer.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
			renderer.setRenderBounds(f6, f2, f7, f8, f3, f9);
			renderer.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
		}
		else
		{
			renderer.uvRotateTop = 1;
			f6 = 0.4375F;
			f8 = 0.5625F;
			f7 = 0.375F;
			f9 = 0.5F;
			renderer.setRenderBounds(f6, f, f7, f8, f3, f9);
			renderer.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
			f7 = 0.5F;
			f9 = 0.625F;
			renderer.setRenderBounds(f6, f, f7, f8, f3, f9);
			renderer.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
			f7 = 0.625F;
			f9 = 0.875F;
			renderer.setRenderBounds(f6, f, f7, f8, f1, f9);
			renderer.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
			renderer.setRenderBounds(f6, f2, f7, f8, f3, f9);
			renderer.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
			f7 = 0.125F;
			f9 = 0.375F;
			renderer.setRenderBounds(f6, f, f7, f8, f1, f9);
			renderer.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
			renderer.setRenderBounds(f6, f2, f7, f8, f3, f9);
			renderer.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
		}

		renderer.uvRotateTop = 0;
		renderer.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
		return flag;
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
	{
		int l = 1;
		//boolean flag1 = false;
		int i1 = BlockDirectional.getDirection(l);

		float f = 0.375F;
		float f1 = 0.5625F;
		float f2 = 0.75F;
		float f3 = 0.9375F;
		float f4 = 0.3125F;
		float f5 = 1.0F;
		float f6;
		float f7;
		float f8;
		float f9;

		if (i1 != 3 && i1 != 1)
		{
			f6 = 0.0F;
			f8 = 0.125F;
			f7 = 0.4375F;
			f9 = 0.5625F;
			renderer.setRenderBounds(f6, f4, f7, f8, f5, f9);
			renderInvBlock2(((IMultipleBlock)block).getBlockTypeForRender(), metadata, renderer);
			f6 = 0.875F;
			f8 = 1.0F;
			renderer.setRenderBounds(f6, f4, f7, f8, f5, f9);
			renderInvBlock2(((IMultipleBlock)block).getBlockTypeForRender(), metadata, renderer);
		}
		else
		{
			renderer.uvRotateTop = 1;
			f6 = 0.4375F;
			f8 = 0.5625F;
			f7 = 0.0F;
			f9 = 0.125F;
			renderer.setRenderBounds(f6, f4, f7, f8, f5, f9);
			renderInvBlock2(((IMultipleBlock)block).getBlockTypeForRender(), metadata, renderer);
			f7 = 0.875F;
			f9 = 1.0F;
			renderer.setRenderBounds(f6, f4, f7, f8, f5, f9);
			renderInvBlock2(((IMultipleBlock)block).getBlockTypeForRender(), metadata, renderer);
			renderer.uvRotateTop = 0;
		}

		renderer.uvRotateTop = 1;
		f6 = 0.4375F;
		f8 = 0.5625F;
		f7 = 0.375F;
		f9 = 0.5F;
		renderer.setRenderBounds(f6, f, f7, f8, f3, f9);
		renderInvBlock2(((IMultipleBlock)block).getBlockTypeForRender(), metadata, renderer);
		f7 = 0.5F;
		f9 = 0.625F;
		renderer.setRenderBounds(f6, f, f7, f8, f3, f9);
		renderInvBlock2(((IMultipleBlock)block).getBlockTypeForRender(), metadata, renderer);
		f7 = 0.625F;
		f9 = 0.875F;
		renderer.setRenderBounds(f6, f, f7, f8, f1, f9);
		renderInvBlock2(((IMultipleBlock)block).getBlockTypeForRender(), metadata, renderer);
		renderer.setRenderBounds(f6, f2, f7, f8, f3, f9);
		renderInvBlock2(((IMultipleBlock)block).getBlockTypeForRender(), metadata, renderer);
		f7 = 0.125F;
		f9 = 0.375F;
		renderer.setRenderBounds(f6, f, f7, f8, f1, f9);
		renderInvBlock2(((IMultipleBlock)block).getBlockTypeForRender(), metadata, renderer);
		renderer.setRenderBounds(f6, f2, f7, f8, f3, f9);
		renderInvBlock2(((IMultipleBlock)block).getBlockTypeForRender(), metadata, renderer);

		renderer.uvRotateTop = 0;
		renderer.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
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

	public static void renderInvBlock2(Block block, int m, RenderBlocks renderer)
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
}
