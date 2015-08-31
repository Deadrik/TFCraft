package com.bioxx.tfc.Render.TESR;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.TileEntities.TEQuern;

public class TESRQuern extends TESRBase implements ISimpleBlockRenderingHandler
{
	private static final ResourceLocation BASE_TEXTURE = new ResourceLocation(Reference.MOD_ID + ":textures/blocks/devices/Quern Base.png");
	private static final ResourceLocation TOP1_TEXTURE = new ResourceLocation(Reference.MOD_ID + ":textures/blocks/devices/Quern Top 1.png");
	private static final ResourceLocation TOP2_TEXTURE = new ResourceLocation(Reference.MOD_ID + ":textures/blocks/devices/Quern Top 2.png");
	private static final ResourceLocation WOOD_TEXTURE = new ResourceLocation(Reference.MOD_ID + ":textures/blocks/wood/Oak Plank.png");

	@Override
	public void renderTileEntityAt(TileEntity te, double xDis, double yDis, double zDis, float f)
	{
		TEQuern teq = (TEQuern) te;

		Tessellator tess = Tessellator.instance;
		GL11.glPushMatrix();
		{
			GL11.glTranslatef((float)xDis, (float)yDis, (float)zDis);
			this.renderBase(tess);
			if(teq.hasQuern)
			{
				/**
				 * Both renderRoundTop and renderSquareTop can be used if you want the square block top + the round stone animation
				 * The last parameter is for rendering the round stone sides, no need to render if you can't see them :)
				 */
				this.renderRoundTop(tess, teq.rotatetimer, teq.getWorldObj().rand, 0.8, true); // Renders a round Quern top stone
				//this.renderSquareTop(tess); // Renders the top Quern box

				renderWoodHandle(tess, teq.rotatetimer, teq.getWorldObj().rand, 0.8); // Renders the wooden handle
				
				if(teq.storage[0] != null) renderItem(teq.storage[0]); // Renders the input slot item
			}
		}
		GL11.glPopMatrix();
	}

	private void renderItem(ItemStack is)
	{
		EntityItem customitem = new EntityItem(field_147501_a.field_147550_f);
		float blockScale = 0.7F;
		float timeD = (float) -(360.0 * (System.currentTimeMillis() & 0x3FFFL) / 0x3FFFL);
		
		bindTexture(TextureMap.locationItemsTexture);
		customitem.setEntityItemStack(is);
		customitem.hoverStart = 0f;
		
		GL11.glTranslatef(0.5f, 0.83f, 0.5f);
		GL11.glRotatef(timeD, 0.0f, 1.0F, 0.0F);
		GL11.glScalef(blockScale, blockScale, blockScale);
		itemRenderer.doRender(customitem, 0, 0, 0, 0, 0);
	}

	private void renderRoundTop(Tessellator t, int pos, Random rand, double angle, Boolean renderSides)
	{
		int sides = 4; // how many sides should the quern stone have
		double speed = pos * 4; // * 4 will make 2 turns, * 1 will make 1 turn, also look at TEQuern
		double i = 0.625; // where should top rendering start
		double j = i + 0.2; // thickness of the quern stone
		if(!renderSides) j = i + 0.201; // fixes the double render glitch when rendering the square top box
		//double k = j + 0.175; // height of the wooden handle
		double center = 0.5; // center
		double rad = 0.5; // radius of the quern stone

		// This gives a vibrating animation effect, it can be commented out if it becomes a performance issue
		if (pos > 0)
			center = 0.494 + (rand.nextDouble() * (0.003 - (-0.003))) + 0.003;

		for(int l = 0; l < sides; l++)
		{
			double a = ((l * (360 / sides) + speed + (4 * pos)) * Math.PI) / 180;
			double b = (((1 + l) * (360 / sides) + speed + (4 * pos)) * Math.PI) / 180;
			double x1 = Math.cos(a + angle) * rad + center;
			double y1 = Math.sin(a + angle) * rad + center;
			double x2 = Math.cos(b + angle) * rad + center;
			double y2 = Math.sin(b + angle) * rad + center;

			//This is needed for textures to stay static when rotating
			a = ((l * (360 / sides)) * Math.PI) / 180;
			b = (((1 + l) * (360 / sides)) * Math.PI) / 180;
			double xx1 = Math.cos(a + angle) * rad + center;
			double yy1 = Math.sin(a + angle) * rad + center;
			double xx2 = Math.cos(b + angle) * rad + center;
			double yy2 = Math.sin(b + angle) * rad + center;

			bindTexture(TOP2_TEXTURE);
			t.startDrawing(GL11.GL_TRIANGLES);
			t.addVertexWithUV(x1, j, y1, xx1, yy1);
			t.addVertexWithUV(center, j, center, center, center);
			t.addVertexWithUV(x2, j, y2, xx2, yy2);
			t.draw();

			if(renderSides)
			{
				bindTexture(BASE_TEXTURE);
				t.startDrawingQuads();
				t.addVertexWithUV(x1, i, y1, 1 - 0.27, 1 - j);
				t.addVertexWithUV(x1, j, y1, 1 - 0.27, 0);
				t.addVertexWithUV(x2, j, y2, 0, 0);
				t.addVertexWithUV(x2, i, y2, 0, 1 - j);
				t.draw();
			}
		}
	}

	/*private void renderSquareTop(Tessellator t)
	{
		double i = 0.625; // bottom square point
		double j = 0.825; // top square point
		bindTexture(BASE_TEXTURE);
		t.startDrawingQuads();
		//SOUTH
		t.addVertexWithUV(0, i, 0, 1, 1 - i);
		t.addVertexWithUV(0, j, 0, 1, 0);
		t.addVertexWithUV(1, j, 0, 0, 0);
		t.addVertexWithUV(1, i, 0, 0, 1 - i);
		//WEST
		t.addVertexWithUV(1, i, 0, 1, 1 - i);
		t.addVertexWithUV(1, j, 0, 1, 0);
		t.addVertexWithUV(1, j, 1, 0, 0);
		t.addVertexWithUV(1, i, 1, 0, 1 - i);
		//NORTH
		t.addVertexWithUV(1, i, 1, 1, 1 - i);
		t.addVertexWithUV(1, j, 1, 1, 0);
		t.addVertexWithUV(0, j, 1, 0, 0);
		t.addVertexWithUV(0, i, 1, 0, 1 - i);
		//EAST
		t.addVertexWithUV(0, i, 1, 1, 1 - i);
		t.addVertexWithUV(0, j, 1, 1, 0);
		t.addVertexWithUV(0, j, 0, 0, 0);
		t.addVertexWithUV(0, i, 0, 0, 1 - i);
		t.draw();
		//TOP
		bindTexture(TOP2_TEXTURE);
		t.startDrawingQuads();
		t.addVertexWithUV(0, j, 0, 0, 0);
		t.addVertexWithUV(0, j, 1, 0, 1);
		t.addVertexWithUV(1, j, 1, 1, 1);
		t.addVertexWithUV(1, j, 0, 1, 0);
		t.draw();
	}*/

	private void renderWoodHandle(Tessellator t, int pos, Random rand, double angle)
	{
		double speed = pos * 4; // * 4 will make 2 turns, * 1 will make 1 turn, also look at TEQuern
		double j = 0.825; // where should wood handle rendering start
		double k = j + 0.175; // height of the wooden handle
		double center = 0.5; // center
		double rad = 0.5; // radius of the quern stone

		//Draw wooden handle
		double a = ((pos * 4 - 5 + speed) * Math.PI) / 180;
		double a1 = ((pos * 4 - 5.7 + speed) * Math.PI) / 180;
		double b = ((pos * 4 + 5 + speed) * Math.PI) / 180;
		double b1 = ((pos * 4 + 5.7 + speed) * Math.PI) / 180;
		double x1 = Math.cos(a + angle) * (rad - 0.05) + center;
		double y1 = Math.sin(a + angle) * (rad - 0.05) + center;
		double xx1 = Math.cos(a1 + angle) * (rad - 0.125) + center;
		double yy1 = Math.sin(a1 + angle) * (rad - 0.125) + center;
		double x2 = Math.cos(b + angle) * (rad - 0.05) + center;
		double y2 = Math.sin(b + angle) * (rad - 0.05) + center;
		double xx2 = Math.cos(b1 + angle) * (rad - 0.125) + center;
		double yy2 = Math.sin(b1 + angle) * (rad - 0.125) + center;

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
