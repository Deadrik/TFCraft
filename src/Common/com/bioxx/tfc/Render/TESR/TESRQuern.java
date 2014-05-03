package com.bioxx.tfc.Render.TESR;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.TileEntities.TileEntityQuern;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class TESRQuern extends TileEntitySpecialRenderer implements ISimpleBlockRenderingHandler
{
	private static final ResourceLocation BASE_TEXTURE = new ResourceLocation(Reference.ModID + ":textures/blocks/devices/Quern Base.png");
	private static final ResourceLocation TOP1_TEXTURE = new ResourceLocation(Reference.ModID + ":textures/blocks/devices/Quern Top 1.png");
	private static final ResourceLocation TOP2_TEXTURE = new ResourceLocation(Reference.ModID + ":textures/blocks/devices/Quern Top 2.png");
	private static final ResourceLocation WOOD_TEXTURE = new ResourceLocation(Reference.ModID + ":textures/blocks/wood/Oak Plank.png");

	@Override
	public void renderTileEntityAt(TileEntity te, double xDis, double yDis, double zDis, float f)
	{
		TileEntityQuern teq = (TileEntityQuern) te;
		
		Tessellator tess = Tessellator.instance;
		GL11.glPushMatrix();
		{
			GL11.glTranslatef((float)xDis, (float)yDis, (float)zDis);
			this.renderBase(tess);
			if(teq.hasQuern)
			{
				this.renderTop(tess, teq.rotatetimer, teq.getWorldObj().rand);
			}
		}
		GL11.glPopMatrix();
	}

	private void renderTop(Tessellator t, int pos, Random rand)
	{
		int sides = 15; // how many sides should the quern stone have
		double speed = pos * 4; // * 4 will make 2 turns, * 1 will make 1 turn
		double i = 0.625; // where should top rendering start
		double j = i + 0.2; // thickness of the quern stone
		double k = j + 0.175; // height of the wooden handle
		double C = 0.5; // center
		if(pos > 0) C = 0.5 + (-0.003 + (-0.003 - 0.003) * rand.nextDouble()); // this can be commented out if it becomes a performance issue
		double rad = 0.5; // radius of the quern stone

		for(int l = 0; l < sides; l++)
		{
			double a = ((l * (360 / sides) + speed + (4 * pos)) * Math.PI) / 180;
			double b = (((1 + l) * (360 / sides) + speed + (4 * pos)) * Math.PI) / 180;
			double x1 = Math.cos(a) * rad + C;
			double y1 = Math.sin(a) * rad + C;
			double x2 = Math.cos(b) * rad + C;
			double y2 = Math.sin(b) * rad + C;

			//This is needed for textures to stay static when rotating
			a = ((l * (360 / sides)) * Math.PI) / 180;
			b = (((1 + l) * (360 / sides)) * Math.PI) / 180;
			double xx1 = Math.cos(a) * rad + C;
			double yy1 = Math.sin(a) * rad + C;
			double xx2 = Math.cos(b) * rad + C;
			double yy2 = Math.sin(b) * rad + C;

			bindTexture(TOP1_TEXTURE);
			t.startDrawingQuads();
			t.addVertexWithUV(x1, i, y1, xx1, yy1);
			t.addVertexWithUV(x1, j, y1, xx1, yy2);
			t.addVertexWithUV(x2, j, y2, xx2, yy2);
			t.addVertexWithUV(x2, i, y2, xx2, yy1);
			t.draw();

			bindTexture(TOP2_TEXTURE);
			t.startDrawing(GL11.GL_TRIANGLES);
			t.addVertexWithUV(x1, j, y1, xx1, yy1);
			t.addVertexWithUV(C, j, C, C, C);
			t.addVertexWithUV(x2, j, y2, xx2, yy2);
			t.draw();
		}

		//Draw wooden handle
		double a = (((pos * 4) - 5 + speed) * Math.PI) / 180;
		double a1 = (((pos * 4) - 5.7 + speed) * Math.PI) / 180;
		double b = (((pos * 4) + 5 + speed) * Math.PI) / 180;
		double b1 = (((pos * 4) + 5.7 + speed) * Math.PI) / 180;
		double x1 = Math.cos(a) * (rad - 0.05) + C;
		double y1 = Math.sin(a) * (rad - 0.05) + C;
		double xx1 = Math.cos(a1) * (rad - 0.125) + C;
		double yy1 = Math.sin(a1) * (rad - 0.125) + C;
		double x2 = Math.cos(b) * (rad - 0.05) + C;
		double y2 = Math.sin(b) * (rad - 0.05) + C;
		double xx2 = Math.cos(b1) * (rad - 0.125) + C;
		double yy2 = Math.sin(b1) * (rad - 0.125) + C;

		bindTexture(WOOD_TEXTURE);
		//SOUTH
		t.startDrawingQuads();
		t.addVertexWithUV(x1, j, y1, 0, 0);
		t.addVertexWithUV(xx1, j, yy1, 0, 0.2);
		t.addVertexWithUV(xx1, k, yy1, 0.2, 0.2);
		t.addVertexWithUV(x1, k, y1, 0.2, 0);
		t.draw();
		//WEST
		t.startDrawingQuads();
		t.addVertexWithUV(xx1, j, yy1, 0, 0);
		t.addVertexWithUV(xx2, j, yy2, 0, 0.2);
		t.addVertexWithUV(xx2, k, yy2, 0.2, 0.2);
		t.addVertexWithUV(xx1, k, yy1, 0.2, 0);
		t.draw();
		//NORTH
		t.startDrawingQuads();
		t.addVertexWithUV(xx2, j, yy2, 0, 0);
		t.addVertexWithUV(x2, j, y2, 0, 0.2);
		t.addVertexWithUV(x2, k, y2, 0.2, 0.2);
		t.addVertexWithUV(xx2, k, yy2, 0.2, 0);
		t.draw();
		//EAST
		t.startDrawingQuads();
		t.addVertexWithUV(x2, j, y2, 0, 0);
		t.addVertexWithUV(x1, j, y1, 0, 0.2);
		t.addVertexWithUV(x1, k, y1, 0.2, 0.2);
		t.addVertexWithUV(x2, k, y2, 0.2, 0);
		t.draw();
		//TOP
		t.startDrawingQuads();
		t.addVertexWithUV(x1, k, y1, 0, 0);
		t.addVertexWithUV(xx1, k, yy1, 0, 0.2);
		t.addVertexWithUV(xx2, k, yy2, 0.2, 0.2);
		t.addVertexWithUV(x2, k, y2, 0.2, 0);
		t.draw();
	}

	private void renderBase(Tessellator t)
	{
		double i = 0.625;
		bindTexture(BASE_TEXTURE);
		t.startDrawingQuads();
		//SOUTH
		t.addVertexWithUV(0, 0, 0, 1, 1);
		t.addVertexWithUV(0, i, 0, 1, 1 - i);
		t.addVertexWithUV(1, i, 0, 0, 1 - i);
		t.addVertexWithUV(1, 0, 0, 0, 1);
		//WEST
		t.addVertexWithUV(1, 0, 0, 1, 1);
		t.addVertexWithUV(1, i, 0, 1, 1 - i);
		t.addVertexWithUV(1, i, 1, 0, 1 - i);
		t.addVertexWithUV(1, 0, 1, 0, 1);
		//NORTH
		t.addVertexWithUV(1, 0, 1, 1, 1);
		t.addVertexWithUV(1, i, 1, 1, 1 - i);
		t.addVertexWithUV(0, i, 1, 0, 1 - i);
		t.addVertexWithUV(0, 0, 1, 0, 1);
		//EAST
		t.addVertexWithUV(0, 0, 1, 1, 1);
		t.addVertexWithUV(0, i, 1, 1, 1 - i);
		t.addVertexWithUV(0, i, 0, 0, 1 - i);
		t.addVertexWithUV(0, 0, 0, 0, 1);
		t.draw();
		//TOP
		bindTexture(TOP2_TEXTURE);
		t.startDrawingQuads();
		t.addVertexWithUV(0, i, 0, 0, 0);
		t.addVertexWithUV(0, i, 1, 0, 1);
		t.addVertexWithUV(1, i, 1, 1, 1);
		t.addVertexWithUV(1, i, 0, 1, 0);
		t.draw();
		//BOTTOM
		bindTexture(TOP1_TEXTURE);
		t.startDrawingQuads();
		t.addVertexWithUV(0, 0, 0, 0, 0);
		t.addVertexWithUV(1, 0, 0, 0, 1);
		t.addVertexWithUV(1, 0, 1, 1, 1);
		t.addVertexWithUV(0, 0, 1, 1, 0);
		t.draw();
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int i, int j, int k, Block block, int modelId, RenderBlocks renderblocks)
	{
		return false;
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
	{
		renderer.setRenderBounds(0, 0, 0, 1, 0.625, 1);
		Tessellator var14 = Tessellator.instance;
		var14.startDrawingQuads();
		var14.setNormal(0.0F, 1.0F, 0.0F);
		renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(1, 1));
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
}
