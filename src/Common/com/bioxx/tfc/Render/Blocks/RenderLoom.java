package com.bioxx.tfc.Render.Blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.TileEntities.TELoom;
import com.bioxx.tfc.api.TFCBlocks;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderLoom implements ISimpleBlockRenderingHandler
{
	static float minX = 0F;
	static float maxX = 1F;
	static float minY = 0F;
	static float maxY = 1F;
	static float minZ = 0F;
	static float maxZ = 1F;

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
	{
		TELoom te = (TELoom) world.getTileEntity(x, y, z);
		Block materialBlock;
		if(te.loomType < 16)
		{
			materialBlock = TFCBlocks.WoodSupportH;
		}
		else
		{
			materialBlock = TFCBlocks.WoodSupportH2;
		}
		renderer.renderAllFaces = true;
		GL11.glPushMatrix();



		//Arms
		this.setRotatedRenderBounds(renderer, te.rotation, minX+0.1F, minY, minZ+0.75F, maxX-0.8F, maxY, maxZ-0.15F);
		renderer.renderStandardBlock(materialBlock, x, y, z);

		this.setRotatedRenderBounds(renderer, te.rotation, minX+0.8F, minY, minZ+0.75F, maxX-0.1F, maxY, maxZ-0.15F);
		renderer.renderStandardBlock(materialBlock, x, y, z);

		//Arm holding sections
		//L
		this.setRotatedRenderBounds(renderer, te.rotation, minX+0.1F, minY+0.25F, minZ+0.5F, maxX-0.8F, maxY-0.7F, maxZ-0.25F);
		renderer.renderStandardBlock(materialBlock, x, y, z);

		this.setRotatedRenderBounds(renderer, te.rotation, minX+0.1F, minY+0.05F, minZ+0.5F, maxX-0.8F, maxY-0.9F, maxZ-0.25F);
		renderer.renderStandardBlock(materialBlock, x, y, z);

		//R
		this.setRotatedRenderBounds(renderer, te.rotation, minX+0.8F, minY+0.25F, minZ+0.5F, maxX-0.1F, maxY-0.7F, maxZ-0.25F);
		renderer.renderStandardBlock(materialBlock, x, y, z);

		this.setRotatedRenderBounds(renderer, te.rotation, minX+0.8F, minY+0.05F, minZ+0.5F, maxX-0.1F, maxY-0.9F, maxZ-0.25F);
		renderer.renderStandardBlock(materialBlock, x, y, z);

		//cross
		this.setRotatedRenderBounds(renderer, te.rotation, maxX-0.8F, minY+0.8F, minZ+0.75F, minX+0.8F, maxY-0.1F, maxZ-0.15F);
		renderer.renderStandardBlock(materialBlock, x, y, z);

		this.setRotatedRenderBounds(renderer, te.rotation, maxX-0.8F, 0F, minZ+0.75F, minX+0.8F, minY+0.1F, maxZ-0.15F);
		renderer.renderStandardBlock(materialBlock, x, y, z);

		rotate(renderer, 0);
		renderer.renderAllFaces = false;
		GL11.glPopMatrix();
		return true;
	}

	public void rotate(RenderBlocks renderer, int i)
	{
		renderer.uvRotateEast = i;
		renderer.uvRotateWest = i;
		renderer.uvRotateNorth = i;
		renderer.uvRotateSouth = i;
	}

	private void setRotatedRenderBounds(RenderBlocks renderer, byte rotation , float x, float y, float z, float X, float Y, float Z){
		switch(rotation){
		case 0: renderer.setRenderBounds(x,y,z,X,Y,Z); break;
		case 1: renderer.setRenderBounds(maxZ-Z,y,x,maxZ-z,Y,X); break;
		case 2: renderer.setRenderBounds(x,y,maxZ-Z,X,Y,maxZ-z); break;
		case 3: renderer.setRenderBounds(z,y,x,Z,Y,X); break;
		default: break;
		}
	}

	@Override
	public void renderInventoryBlock(Block block, int meta, int modelID, RenderBlocks renderer)
	{
		Block materialBlock;

		if(meta < 16)
		{
			materialBlock = TFCBlocks.WoodSupportH;
		}
		else
		{
			materialBlock = TFCBlocks.WoodSupportH2;
		}

		GL11.glPushMatrix();
		GL11.glRotatef(180, 0, 1, 0);
		//Arms
		renderer.setRenderBounds(minX+0.1F, minY, minZ+0.75F, maxX-0.8F, maxY, maxZ-0.15F);
		rotate(renderer, 1);
		renderInvBlock(materialBlock, meta, renderer);

		renderer.setRenderBounds(minX+0.8F, minY, minZ+0.75F, maxX-0.1F, maxY, maxZ-0.15F);
		rotate(renderer, 1);
		renderInvBlock(materialBlock, meta, renderer);

		//Arm holding sections
		//L
		renderer.setRenderBounds(minX+0.1F, minY+0.35F, minZ+0.6F, maxX-0.8F, maxY-0.6F, maxZ-0.25F);
		rotate(renderer, 1);
		renderInvBlock(materialBlock, meta, renderer);

		renderer.setRenderBounds(minX+0.1F, minY+0.15F, minZ+0.6F, maxX-0.8F, maxY-0.8F, maxZ-0.25F);
		rotate(renderer, 1);
		renderInvBlock(materialBlock, meta, renderer);

		//R
		renderer.setRenderBounds(minX+0.8F, minY+0.35F, minZ+0.6F, maxX-0.1F, maxY-0.6F, maxZ-0.25F);
		rotate(renderer, 1);
		renderInvBlock(materialBlock, meta, renderer);

		renderer.setRenderBounds(minX+0.8F, minY+0.15F, minZ+0.6F, maxX-0.1F, maxY-0.8F, maxZ-0.25F);
		rotate(renderer, 1);
		renderInvBlock(materialBlock, meta, renderer);

		//cross
		renderer.setRenderBounds(maxX-0.8F, minY+0.8F, minZ+0.75F, minX+0.8F, maxY-0.1F, maxZ-0.15F);
		rotate(renderer, 1);
		renderInvBlock(materialBlock, meta, renderer);

		renderer.setRenderBounds(maxX-0.8F, 0F, minZ+0.75F, minX+0.8F, minY+0.1F, maxZ-0.15F);
		rotate(renderer, 1);
		renderInvBlock(materialBlock, meta, renderer);

		rotate(renderer, 0);
		GL11.glPopMatrix();
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

	public static void renderInvBlockHoop(Block block, int m, RenderBlocks renderer)
	{
		Tessellator var14 = Tessellator.instance;
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		var14.startDrawingQuads();
		var14.setNormal(0.0F, -1.0F, 0.0F);
		renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(10, m));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(0.0F, 1.0F, 0.0F);
		renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(11, m));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(0.0F, 0.0F, -1.0F);
		renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(12, m));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(0.0F, 0.0F, 1.0F);
		renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(13, m));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(-1.0F, 0.0F, 0.0F);
		renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(14, m));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(1.0F, 0.0F, 0.0F);
		renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(15, m));
		var14.draw();
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
	}
}
