package com.bioxx.tfc.Render.Blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.api.TFCBlocks;

public class RenderStand  implements ISimpleBlockRenderingHandler
{
	/*private static float pixel3 = 3f / 16f;
	private static float pixel5 = 5f / 16f;
	private static float pixel12 = 12f / 16f;
	private static float pixel14 = 14f / 16f;*/

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int i, int j, int k, Block block, int modelId, RenderBlocks renderer)
	{
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
		Block blockToRender = block == TFCBlocks.armorStand ? TFCBlocks.planks : TFCBlocks.planks2;
		Block woodblock = block == TFCBlocks.armorStand ? TFCBlocks.woodSupportH : TFCBlocks.woodSupportH2;

		float yScale = 0.7f;
		float blockScale = 0.5f;

		//Arms of the Stand
		renderer.setRenderBounds(0.44F * blockScale,1.45F * yScale * blockScale, 0.2F * blockScale ,0.56F * blockScale ,1.55F * yScale * blockScale,0.8F * blockScale);
		renderInvBlock(woodblock, metadata, renderer);

		renderer.setRenderBounds(0.45F * blockScale, 0.201F * yScale * blockScale, 0.35F * blockScale, 0.55F * blockScale, 1.45F * yScale * blockScale, 0.45F * blockScale);
		renderInvBlock(woodblock, metadata, renderer);

		renderer.setRenderBounds(0.45F * blockScale, 0.201F * yScale * blockScale, 0.55F * blockScale, 0.55F * blockScale, 1.45F * yScale * blockScale, 0.65F * blockScale);
		renderInvBlock(woodblock, metadata, renderer);

		//Base of the stand
		renderer.setRenderBounds(0.2F, 0F, 0.2F, 0.8F, 0.2*yScale, 0.8F);
		renderInvBlock(blockToRender, metadata, renderer, false);

		//Main post of the stand
		renderer.setRenderBounds(0.45F * blockScale, 1.45F * yScale * blockScale, 0.45F * blockScale, 0.55F * blockScale, 1.9F * yScale * blockScale, 0.55F * blockScale);
		renderInvBlock(woodblock, metadata, renderer);
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
		renderInvBlock(block, m, renderer, true);
	}

	public static void renderInvBlock(Block block, int m, RenderBlocks renderer, boolean b)
	{
		Tessellator var14 = Tessellator.instance;
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		if(b) GL11.glScalef(2, 2, 2);
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
		if(b) GL11.glScalef(0.5F, 0.5F, 0.5F);
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
	}
}
