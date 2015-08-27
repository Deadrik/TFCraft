package com.bioxx.tfc.Render.Blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Blocks.Devices.BlockGrill;

public class RenderGrill  implements ISimpleBlockRenderingHandler 
{
	/*private static float pixel3 = 3f / 16f;
	private static float pixel5 = 5f / 16f;
	private static float pixel12 = 12f / 16f;
	private static float pixel14 = 14f / 16f;*/

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int i, int j, int k, Block block, int modelId, RenderBlocks renderer)
	{
		//IBlockAccess blockAccess = renderer.blockAccess;
		/*if(!TFCOptions.use2DGrill)
		{
			renderer.renderAllFaces = true;
			renderer.setRenderBounds(0.0F, -0.05F, 0.0F, 0.05F, 0.0F, 1.0F);//minX edge
			renderer.renderStandardBlock(block, i, j, k);
			renderer.setRenderBounds(0.95F, -0.05F, 0.0F, 1F, 0.0F, 1.0F);//maxX edge
			renderer.renderStandardBlock(block, i, j, k);
			renderer.setRenderBounds(0.05F, -0.05F, 0.0F, 0.95F, 0.0F, 0.05F);
			renderer.renderStandardBlock(block, i, j, k);
			renderer.setRenderBounds(0.05F, -0.05F, 0.1F, 0.95F, 0.0F, 0.15F);
			renderer.renderStandardBlock(block, i, j, k);
			renderer.setRenderBounds(0.05F, -0.05F, 0.2F, 0.95F, 0.0F, 0.25F);
			renderer.renderStandardBlock(block, i, j, k);
			renderer.setRenderBounds(0.05F, -0.05F, 0.3F, 0.95F, 0.0F, 0.35F);
			renderer.renderStandardBlock(block, i, j, k);
			renderer.setRenderBounds(0.05F, -0.05F, 0.4F, 0.95F, 0.0F, 0.475F);
			renderer.renderStandardBlock(block, i, j, k);
			renderer.setRenderBounds(0.05F, -0.05F, 0.525F, 0.95F, 0.0F, 0.6F);
			renderer.renderStandardBlock(block, i, j, k);
			renderer.setRenderBounds(0.05F, -0.05F, 0.65F, 0.95F, 0.0F, 0.7F);
			renderer.renderStandardBlock(block, i, j, k);
			renderer.setRenderBounds(0.05F, -0.05F, 0.75F, 0.95F, 0.0F, 0.8F);
			renderer.renderStandardBlock(block, i, j, k);
			renderer.setRenderBounds(0.05F, -0.05F, 0.85F, 0.95F, 0.0F, 0.9F);
			renderer.renderStandardBlock(block, i, j, k);
			renderer.setRenderBounds(0.05F, -0.05F, 0.95F, 0.95F, 0.0F, 1.0F);
			renderer.renderStandardBlock(block, i, j, k);
			renderer.renderAllFaces = false;
		}
		else
		{ */
			BlockGrill grill = (BlockGrill) block;
			int meta = world.getBlockMetadata(i, j, k);

			if (!grill.isGrillOpen(meta))
				renderer.setRenderBounds(0.0F, -0.05F, 0.0F, 1F, 0.0F, 1.0F);

			renderer.renderStandardBlock(block, i, j, k);
		//}
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
		/*if(!TFCOptions.use2DGrill)
		{
			renderer.setRenderBounds(0.0F, 0.5F, 0.0F, 0.05F, 0.55F, 1.0F);//minX edge
			renderInvBlock(block, metadata, renderer);
			renderer.setRenderBounds(0.95F, 0.5F, 0.0F, 1F, 0.55F, 1.0F);//maxX edge
			renderInvBlock(block, metadata, renderer);
			renderer.setRenderBounds(0.05F, 0.5F, 0.0F, 0.95F, 0.55F, 0.05F);
			renderInvBlock(block, metadata, renderer);
			renderer.setRenderBounds(0.05F, 0.5F, 0.1F, 0.95F, 0.55F, 0.15F);
			renderInvBlock(block, metadata, renderer);
			renderer.setRenderBounds(0.05F, 0.5F, 0.2F, 0.95F, 0.55F, 0.25F);
			renderInvBlock(block, metadata, renderer);
			renderer.setRenderBounds(0.05F, 0.5F, 0.3F, 0.95F, 0.55F, 0.35F);
			renderInvBlock(block, metadata, renderer);
			renderer.setRenderBounds(0.05F, 0.5F, 0.4F, 0.95F, 0.55F, 0.475F);
			renderInvBlock(block, metadata, renderer);
			renderer.setRenderBounds(0.05F, 0.5F, 0.525F, 0.95F, 0.55F, 0.6F);
			renderInvBlock(block, metadata, renderer);
			renderer.setRenderBounds(0.05F, 0.5F, 0.65F, 0.95F, 0.55F, 0.7F);
			renderInvBlock(block, metadata, renderer);
			renderer.setRenderBounds(0.05F, 0.5F, 0.75F, 0.95F, 0.55F, 0.8F);
			renderInvBlock(block, metadata, renderer);
			renderer.setRenderBounds(0.05F, 0.5F, 0.85F, 0.95F, 0.55F, 0.9F);
			renderInvBlock(block, metadata, renderer);
			renderer.setRenderBounds(0.05F, 0.5F, 0.95F, 0.95F, 0.55F, 1.0F);
			renderInvBlock(block, metadata, renderer);
		}
		else 
		{*/
			renderer.setRenderBounds(0.0F, 0.5F, 0.0F, 1F, 0.55F, 1.0F);
			renderInvBlock(block, metadata, renderer);
		//};
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
