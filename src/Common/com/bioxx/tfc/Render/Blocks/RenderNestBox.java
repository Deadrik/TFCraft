package com.bioxx.tfc.Render.Blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.api.TFCBlocks;

public class RenderNestBox  implements ISimpleBlockRenderingHandler 
{
	/*private static float pixel3 = 3f / 16f;
	private static float pixel5 = 5f / 16f;
	private static float pixel12 = 12f / 16f;
	private static float pixel14 = 14f / 16f;*/

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int i, int j, int k, Block block, int modelId, RenderBlocks renderer)
	{
		//IBlockAccess blockAccess = renderer.blockAccess;
		renderer.renderAllFaces = true;
		renderer.setRenderBounds(0.15F, 0.1F, 0.15F, 0.85F, 0.1F, 0.85F);
		renderer.renderStandardBlock(TFCBlocks.planks, i, j, k);

		renderer.setRenderBounds(0.1F, 0F, 0.15F, 0.15F, 0.4F, 0.85F);
		rotate(renderer, 1);
		renderer.renderStandardBlock(TFCBlocks.planks, i, j, k);
		rotate(renderer, 0);
		renderer.renderStandardBlock(block, i, j, k);

		renderer.setRenderBounds(0.85F, 0F, 0.15F, 0.9F, 0.4F, 0.85F);
		rotate(renderer, 1);
		renderer.renderStandardBlock(TFCBlocks.planks, i, j, k);
		rotate(renderer, 0);
		renderer.renderStandardBlock(block, i, j, k);

		renderer.setRenderBounds(0.1F, 0F, 0.1F, 0.9F, 0.4F, 0.15F);
		rotate(renderer, 1);
		renderer.renderStandardBlock(TFCBlocks.planks, i, j, k);
		rotate(renderer, 0);
		renderer.renderStandardBlock(block, i, j, k);

		renderer.setRenderBounds(0.1F, 0F, 0.85F, 0.9F, 0.4F, 0.9F);
		rotate(renderer, 1);
		renderer.renderStandardBlock(TFCBlocks.planks, i, j, k);
		rotate(renderer, 0);
		renderer.renderStandardBlock(block, i, j, k);
		renderer.renderAllFaces = false;
		return true;
	}

	public void rotate(RenderBlocks renderer, int i)
	{
		renderer.uvRotateEast = i;
		renderer.uvRotateWest = i;
		renderer.uvRotateNorth = i;
		renderer.uvRotateSouth = i;
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
	{
		renderer.setRenderBounds(0.15F, 0.2F, 0.15F, 0.85F, 0.1F, 0.85F);
		rotate(renderer, 1);
		renderInvBlock(TFCBlocks.planks, metadata, renderer);

		renderer.setRenderBounds(0.1F, 0F, 0.15F, 0.15F, 0.4F, 0.85F);
		rotate(renderer, 1);
		renderInvBlock(TFCBlocks.planks, metadata, renderer);
		rotate(renderer, 0);
		renderInvBlock(block, metadata, renderer);

		renderer.setRenderBounds(0.85F, 0F, 0.15F, 0.9F, 0.4F, 0.85F);
		rotate(renderer, 1);
		renderInvBlock(TFCBlocks.planks, metadata, renderer);
		rotate(renderer, 0);
		renderInvBlock(block, metadata, renderer);

		renderer.setRenderBounds(0.1F, 0F, 0.1F, 0.9F, 0.4F, 0.15F);
		rotate(renderer, 1);
		renderInvBlock(TFCBlocks.planks, metadata, renderer);
		rotate(renderer, 0);
		renderInvBlock(block, metadata, renderer);

		renderer.setRenderBounds(0.1F, 0F, 0.85F, 0.9F, 0.4F, 0.9F);
		rotate(renderer, 1);
		renderInvBlock(TFCBlocks.planks, metadata, renderer);
		rotate(renderer, 0);
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
}
