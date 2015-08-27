package com.bioxx.tfc.Render.Blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Blocks.Vanilla.BlockTFCFence;

public class RenderFence  implements ISimpleBlockRenderingHandler 
{
	/*private static float pixel3 = 3f / 16f;
	private static float pixel5 = 5f / 16f;
	private static float pixel12 = 12f / 16f;
	private static float pixel14 = 14f / 16f;*/

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderblocks)
	{
		BlockTFCFence par1BlockFence = (BlockTFCFence)block;
		boolean flag = false;
		float f = 0.375F;
		float f1 = 0.625F;
		renderblocks.setRenderBounds(f, 0.0D, f, f1, 1.0D, f1);
		renderblocks.renderStandardBlock(par1BlockFence, x, y, z);
		flag = true;
		boolean flag1 = false;
		boolean flag2 = false;

		if (par1BlockFence.canConnectFenceTo(renderblocks.blockAccess, x - 1, y, z) || par1BlockFence.canConnectFenceTo(renderblocks.blockAccess, x + 1, y, z))
			flag1 = true;

		if (par1BlockFence.canConnectFenceTo(renderblocks.blockAccess, x, y, z - 1) || par1BlockFence.canConnectFenceTo(renderblocks.blockAccess, x, y, z + 1))
			flag2 = true;

		boolean flag3 = par1BlockFence.canConnectFenceTo(renderblocks.blockAccess, x - 1, y, z);
		boolean flag4 = par1BlockFence.canConnectFenceTo(renderblocks.blockAccess, x + 1, y, z);
		boolean flag5 = par1BlockFence.canConnectFenceTo(renderblocks.blockAccess, x, y, z - 1);
		boolean flag6 = par1BlockFence.canConnectFenceTo(renderblocks.blockAccess, x, y, z + 1);

		if (!flag1 && !flag2)
			flag1 = true;

		f = 0.4375F;
		f1 = 0.5625F;
		float f2 = 0.75F;
		float f3 = 0.9375F;
		float f4 = flag3 ? 0.0F : f;
		float f5 = flag4 ? 1.0F : f1;
		float f6 = flag5 ? 0.0F : f;
		float f7 = flag6 ? 1.0F : f1;

		if (flag1)
		{
			renderblocks.setRenderBounds(f4, f2+0.001, f+0.001, f5, f3+0.001, f1+0.001);
			renderblocks.renderStandardBlock(par1BlockFence, x, y, z);

			renderblocks.setRenderBounds(f5, f2, f1-0.001, f4, f3, f-0.001);
			renderblocks.renderStandardBlock(par1BlockFence, x, y, z);

			flag = true;
		}

		if (flag2)
		{
			renderblocks.setRenderBounds(f-0.001, f2+0.001, f6, f1-0.001, f3+0.001, f7);
			renderblocks.renderStandardBlock(par1BlockFence, x, y, z);

			renderblocks.setRenderBounds(f1+0.001, f2, f7, f+0.001, f3, f6);
			renderblocks.renderStandardBlock(par1BlockFence, x, y, z);
			flag = true;
		}

		f2 = 0.375F;
		f3 = 0.5625F;

		if (flag1)
		{
			renderblocks.setRenderBounds(f4, f2+0.001, f+0.001, f5, f3+0.001, f1+0.001);
			renderblocks.renderStandardBlock(par1BlockFence, x, y, z);

			renderblocks.setRenderBounds(f5, f2, f1-0.001, f4, f3, f-0.001);
			renderblocks.renderStandardBlock(par1BlockFence, x, y, z);

			flag = true;
		}

		if (flag2)
		{
			renderblocks.setRenderBounds(f-0.001, f2+0.001, f6, f1-0.001, f3+0.001, f7);
			renderblocks.renderStandardBlock(par1BlockFence, x, y, z);

			renderblocks.setRenderBounds(f1+0.001, f2, f7, f+0.001, f3, f6);
			renderblocks.renderStandardBlock(par1BlockFence, x, y, z);
			flag = true;
		}

		par1BlockFence.setBlockBoundsBasedOnState(renderblocks.blockAccess, x, y, z);
		return flag;
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
	{
		float f = 0.375F;
		float f1 = 0.625F;
		renderer.setRenderBounds(f, 0.0D, f, f1, 1.0D, f1);
		//rotate(renderer, 1);
		renderInvBlock(block, metadata, renderer);

		renderer.setRenderBounds(f, 0.0D, f, f1, 1.0D, f1);
		//rotate(renderer, 1);
		renderInvBlock2(block, metadata, renderer);

		f = 0.4375F;
		f1 = 0.5625F;
		float f2 = 0.75F;
		float f3 = 0.9375F;
		//float f4 = 0.0F;
		//float f5 = 1.0F;
		float f6 =  0.0F;
		float f7 = 1.0F;

		renderer.setRenderBounds(f-0.001, f2, f6, f1-0.001, f3, (double)f7/2);
		renderInvBlock(block, metadata, renderer);

		renderer.setRenderBounds(f1+0.001, f2, (double)f7/2, f+0.001, f3, f6);
		renderInvBlock(block, metadata, renderer);

		f2 = 0.375F;
		f3 = 0.5625F;

		renderer.setRenderBounds(f-0.001, f2, f6, f1-0.001, f3, (double)f7/2);
		renderInvBlock(block, metadata, renderer);

		renderer.setRenderBounds(f1+0.001, f2, (double)f7/2, f+0.001, f3, f6);
		renderInvBlock(block, metadata, renderer);
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
		GL11.glTranslatef(-0.5F, -0.5F, 0F);
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
