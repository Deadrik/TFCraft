package TFC.Render;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import TFC.Reference;
import TFC.TileEntities.TileEntityBellows;

public class TileEntityBellowsRenderer extends TileEntitySpecialRenderer
{
	private static final ResourceLocation FRONT_TEXTURE = new ResourceLocation(Reference.ModID + ":textures/blocks/devices/Bellows Front.png");
	private static final ResourceLocation BACK_TEXTURE = new ResourceLocation(Reference.ModID + ":textures/blocks/devices/Bellows Back.png");
	private static final ResourceLocation SIDE1_TEXTURE = new ResourceLocation(Reference.ModID + ":textures/blocks/devices/Bellows82.png");
	private static final ResourceLocation SIDE2_TEXTURE = new ResourceLocation(Reference.ModID + ":textures/blocks/devices/Bellows83.png");
	private static final ResourceLocation SIDE3_TEXTURE = new ResourceLocation(Reference.ModID + ":textures/blocks/devices/Bellows84.png");
	private static final ResourceLocation SIDE4_TEXTURE = new ResourceLocation(Reference.ModID + ":textures/blocks/devices/Bellows85.png");

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float f) {
		TileEntityBellows bellows = (TileEntityBellows) tileentity;
		if (bellows != null) {
			int meta = bellows.worldObj.getBlockMetadata(bellows.xCoord, bellows.yCoord, bellows.zCoord);
			float pos = bellows.blowTimer * 0.1F;
			if(pos < 0) { pos = 0; }

			Tessellator t = Tessellator.instance;
			GL11.glPushMatrix();
			GL11.glTranslatef((float) x, (float) y, (float) z);

			renderBack(t, meta, pos);
			renderBody(t, meta, pos);
			renderFront(t, meta);

			GL11.glPopMatrix();
		}
	}

	private void renderBack(Tessellator t, int meta, float pos)
	{
		float i;
		float j;

		if(meta == 0 || meta == 2)
		{
			if(meta == 0)
			{
				i = pos;
				j = 0.1F + pos;
			}
			else
			{
				i = 0.9F - pos;
				j = 1.0F - pos;
			}
			bindTexture(BACK_TEXTURE);
			t.startDrawingQuads();
			//back
			t.addVertexWithUV(0, 0, i, 0, 0);
			t.addVertexWithUV(0, 1, i, 0, 1);
			t.addVertexWithUV(1, 1, i, 1, 1);
			t.addVertexWithUV(1, 0, i, 1, 0);
			//front
			t.addVertexWithUV(0, 0, j, 0, 0);
			t.addVertexWithUV(1, 0, j, 0, 1);
			t.addVertexWithUV(1, 1, j, 1, 1);
			t.addVertexWithUV(0, 1, j, 1, 0);
			// top
			t.addVertexWithUV(0, 1, i, 0, 0);
			t.addVertexWithUV(0, 1, j, 0, 1);
			t.addVertexWithUV(1, 1, j, 1, 1);
			t.addVertexWithUV(1, 1, i, 1, 0);
			//right
			t.addVertexWithUV(0, 0, i, 0, 0);
			t.addVertexWithUV(0, 0, j, 0, 1);
			t.addVertexWithUV(0, 1, j, 1, 1);
			t.addVertexWithUV(0, 1, i, 1, 0);
			//left
			t.addVertexWithUV(1, 1, i, 0, 0);
			t.addVertexWithUV(1, 1, j, 0, 1);
			t.addVertexWithUV(1, 0, j, 1, 1);
			t.addVertexWithUV(1, 0, i, 1, 0);
			//bottom
			t.addVertexWithUV(0, 0, i, 0, 0);
			t.addVertexWithUV(1, 0, i, 0, 1);
			t.addVertexWithUV(1, 0, j, 1, 1);
			t.addVertexWithUV(0, 0, j, 1, 0);
			t.draw();
		}
		else if(meta == 1 || meta == 3)
		{
			if(meta == 3)
			{
				i = pos;
				j = 0.1F + pos;
			}
			else
			{
				i = 0.9F - pos;
				j = 1.0F - pos;
			}
			bindTexture(BACK_TEXTURE);
			t.startDrawingQuads();
			//front
			t.addVertexWithUV(i, 0, 0, 0, 0);
			t.addVertexWithUV(i, 0, 1, 0, 1);
			t.addVertexWithUV(i, 1, 1, 1, 1);
			t.addVertexWithUV(i, 1, 0, 1, 0);
			//back
			t.addVertexWithUV(j, 0, 0, 0, 0);
			t.addVertexWithUV(j, 1, 0, 0, 1);
			t.addVertexWithUV(j, 1, 1, 1, 1);
			t.addVertexWithUV(j, 0, 1, 1, 0);
			// top
			t.addVertexWithUV(i, 1, 0, 0, 0);
			t.addVertexWithUV(i, 1, 1, 0, 1);
			t.addVertexWithUV(j, 1, 1, 1, 1);
			t.addVertexWithUV(j, 1, 0, 1, 0);
			//right
			t.addVertexWithUV(i, 0, 0, 0, 0);
			t.addVertexWithUV(i, 1, 0, 0, 1);
			t.addVertexWithUV(j, 1, 0, 1, 1);
			t.addVertexWithUV(j, 0, 0, 1, 0);
			//bottom
			t.addVertexWithUV(i, 0, 1, 0, 0);
			t.addVertexWithUV(i, 0, 0, 0, 1);
			t.addVertexWithUV(j, 0, 0, 1, 1);
			t.addVertexWithUV(j, 0, 1, 1, 0);
			//left
			t.addVertexWithUV(i, 1, 1, 0, 0);
			t.addVertexWithUV(i, 0, 1, 0, 1);
			t.addVertexWithUV(j, 0, 1, 1, 1);
			t.addVertexWithUV(j, 1, 1, 1, 0);
			t.draw();
		}
	}

	private void renderBody(Tessellator t, int meta, float pos)
	{
		float i;
		float j;

		if(meta == 0 || meta == 2)
		{
			if(meta == 0)
			{
				bindTexture(SIDE1_TEXTURE);
				i = 0.05F + pos;
				j = 0.95F;
			}
			else
			{
				bindTexture(SIDE4_TEXTURE);
				i = 0.05F;
				j = 0.95F - pos;
			}
			t.startDrawingQuads();
			t.addVertexWithUV(0.9F, 0.9F, i, 0, 0);
			t.addVertexWithUV(0.9F, 0.9F, j, 0, 1);
			t.addVertexWithUV(0.9F, 0.1F, j, 1, 1);
			t.addVertexWithUV(0.9F, 0.1F, i, 1, 0);
			t.draw();

			t.startDrawingQuads();
			t.addVertexWithUV(0.1F, 0.9F, i, 0, 0);
			t.addVertexWithUV(0.1F, 0.9F, j, 0, 1);
			t.addVertexWithUV(0.9F, 0.9F, j, 1, 1);
			t.addVertexWithUV(0.9F, 0.9F, i, 1, 0);
			t.draw();

			t.startDrawingQuads();
			t.addVertexWithUV(0.1F, 0.1F, i, 0, 0);
			t.addVertexWithUV(0.1F, 0.1F, j, 0, 1);
			t.addVertexWithUV(0.1F, 0.9F, j, 1, 1);
			t.addVertexWithUV(0.1F, 0.9F, i, 1, 0);
			t.draw();

			t.startDrawingQuads();
			t.addVertexWithUV(0.9F, 0.1F, i, 0, 0);
			t.addVertexWithUV(0.9F, 0.1F, j, 0, 1);
			t.addVertexWithUV(0.1F, 0.1F, j, 1, 1);
			t.addVertexWithUV(0.1F, 0.1F, i, 1, 0);
			t.draw();
		}
		else if(meta == 1 || meta == 3)
		{
			if(meta == 3)
			{
				bindTexture(SIDE3_TEXTURE);
				i = 0.05F + pos;
				j = 0.95F;
			}
			else
			{
				bindTexture(SIDE2_TEXTURE);
				i = 0.05F;
				j = 0.95F - pos;
			}
			t.startDrawingQuads();
			t.addVertexWithUV(i, 0.1F, 0.1F, 0, 0);
			t.addVertexWithUV(i, 0.9F, 0.1F, 0, 1);
			t.addVertexWithUV(j, 0.9F, 0.1F, 1, 1);
			t.addVertexWithUV(j, 0.1F, 0.1F, 1, 0);
			t.draw();

			t.startDrawingQuads();
			t.addVertexWithUV(i, 0.1F, 0.9F, 0, 0);
			t.addVertexWithUV(i, 0.1F, 0.1F, 0, 1);
			t.addVertexWithUV(j, 0.1F, 0.1F, 1, 1);
			t.addVertexWithUV(j, 0.1F, 0.9F, 1, 0);
			t.draw();

			t.startDrawingQuads();
			t.addVertexWithUV(i, 0.9F, 0.9F, 0, 0);
			t.addVertexWithUV(i, 0.1F, 0.9F, 0, 1);
			t.addVertexWithUV(j, 0.1F, 0.9F, 1, 1);
			t.addVertexWithUV(j, 0.9F, 0.9F, 1, 0);
			t.draw();

			t.startDrawingQuads();
			t.addVertexWithUV(i, 0.9F, 0.1F, 0, 0);
			t.addVertexWithUV(i, 0.9F, 0.9F, 0, 1);
			t.addVertexWithUV(j, 0.9F, 0.9F, 1, 1);
			t.addVertexWithUV(j, 0.9F, 0.1F, 1, 0);
			t.draw();
		}
	}

	private void renderFront(Tessellator t, int meta)
	{
		if(meta == 0)
		{
			bindTexture(FRONT_TEXTURE);
			t.startDrawingQuads();
			//front
			t.addVertexWithUV(0, 0, 1, 0, 0);
			t.addVertexWithUV(1, 0, 1, 0, 1);
			t.addVertexWithUV(1, 1, 1, 1, 1);
			t.addVertexWithUV(0, 1, 1, 1, 0);
			t.draw();

			bindTexture(BACK_TEXTURE);
			t.startDrawingQuads();
			//back
			t.addVertexWithUV(0, 0, 0.9F, 0, 0);
			t.addVertexWithUV(0, 1, 0.9F, 0, 1);
			t.addVertexWithUV(1, 1, 0.9F, 1, 1);
			t.addVertexWithUV(1, 0, 0.9F, 1, 0);
			// top
			t.addVertexWithUV(0, 1, 0.9F, 0, 0);
			t.addVertexWithUV(0, 1, 1, 0, 1);
			t.addVertexWithUV(1, 1, 1, 1, 1);
			t.addVertexWithUV(1, 1, 0.9F, 1, 0);
			//right
			t.addVertexWithUV(0, 0, 0.9F, 0, 0);
			t.addVertexWithUV(0, 0, 1, 0, 1);
			t.addVertexWithUV(0, 1, 1, 1, 1);
			t.addVertexWithUV(0, 1, 0.9F, 1, 0);
			//left
			t.addVertexWithUV(1, 1, 0.9F, 0, 0);
			t.addVertexWithUV(1, 1, 1, 0, 1);
			t.addVertexWithUV(1, 0, 1, 1, 1);
			t.addVertexWithUV(1, 0, 0.9F, 1, 0);
			//bottom
			t.addVertexWithUV(0, 0, 0.9F, 0, 0);
			t.addVertexWithUV(1, 0, 0.9F, 0, 1);
			t.addVertexWithUV(1, 0, 1, 1, 1);
			t.addVertexWithUV(0, 0, 1, 1, 0);
			t.draw();
		}
		else if(meta == 1)
		{
			bindTexture(FRONT_TEXTURE);
			t.startDrawingQuads();
			//front
			t.addVertexWithUV(0, 0, 0, 0, 0);
			t.addVertexWithUV(0, 0, 1, 0, 1);
			t.addVertexWithUV(0, 1, 1, 1, 1);
			t.addVertexWithUV(0, 1, 0, 1, 0);
			t.draw();

			bindTexture(BACK_TEXTURE);
			t.startDrawingQuads();
			//back
			t.addVertexWithUV(0.1F, 0, 0, 0, 0);
			t.addVertexWithUV(0.1F, 1, 0, 0, 1);
			t.addVertexWithUV(0.1F, 1, 1, 1, 1);
			t.addVertexWithUV(0.1F, 0, 1, 1, 0);
			// top
			t.addVertexWithUV(0, 1, 0, 0, 0);
			t.addVertexWithUV(0, 1, 1, 0, 1);
			t.addVertexWithUV(0.1F, 1, 1, 1, 1);
			t.addVertexWithUV(0.1F, 1, 0, 1, 0);
			//right
			t.addVertexWithUV(0, 0, 0, 0, 0);
			t.addVertexWithUV(0, 1, 0, 0, 1);
			t.addVertexWithUV(0.1F, 1, 0, 1, 1);
			t.addVertexWithUV(0.1F, 0, 0, 1, 0);
			//bottom
			t.addVertexWithUV(0, 0, 1, 0, 0);
			t.addVertexWithUV(0, 0, 0, 0, 1);
			t.addVertexWithUV(0.1F, 0, 0, 1, 1);
			t.addVertexWithUV(0.1F, 0, 1, 1, 0);
			//left
			t.addVertexWithUV(0, 1, 1, 0, 0);
			t.addVertexWithUV(0, 0, 1, 0, 1);
			t.addVertexWithUV(0.1F, 0, 1, 1, 1);
			t.addVertexWithUV(0.1F, 1, 1, 1, 0);
			t.draw();
		}
		else if(meta == 2)
		{
			bindTexture(FRONT_TEXTURE);
			t.startDrawingQuads();
			//back
			t.addVertexWithUV(0, 0, 0, 0, 0);
			t.addVertexWithUV(0, 1, 0, 0, 1);
			t.addVertexWithUV(1, 1, 0, 1, 1);
			t.addVertexWithUV(1, 0, 0, 1, 0);
			t.draw();

			bindTexture(BACK_TEXTURE);
			t.startDrawingQuads();
			//front
			t.addVertexWithUV(0, 0, 0.1F, 0, 0);
			t.addVertexWithUV(1, 0, 0.1F, 0, 1);
			t.addVertexWithUV(1, 1, 0.1F, 1, 1);
			t.addVertexWithUV(0, 1, 0.1F, 1, 0);
			// top
			t.addVertexWithUV(0, 1, 0, 0, 0);
			t.addVertexWithUV(0, 1, 0.1F, 0, 1);
			t.addVertexWithUV(1, 1, 0.1F, 1, 1);
			t.addVertexWithUV(1, 1, 0, 1, 0);
			//right
			t.addVertexWithUV(0, 0, 0, 0, 0);
			t.addVertexWithUV(0, 0, 0.1F, 0, 1);
			t.addVertexWithUV(0, 1, 0.1F, 1, 1);
			t.addVertexWithUV(0, 1, 0, 1, 0);
			//left
			t.addVertexWithUV(1, 1, 0, 0, 0);
			t.addVertexWithUV(1, 1, 0.1F, 0, 1);
			t.addVertexWithUV(1, 0, 0.1F, 1, 1);
			t.addVertexWithUV(1, 0, 0, 1, 0);
			//bottom
			t.addVertexWithUV(0, 0, 0, 0, 0);
			t.addVertexWithUV(1, 0, 0, 0, 1);
			t.addVertexWithUV(1, 0, 0.1F, 1, 1);
			t.addVertexWithUV(0, 0, 0.1F, 1, 0);
			t.draw();
		}
		else if(meta == 3)
		{
			bindTexture(FRONT_TEXTURE);
			t.startDrawingQuads();
			//back
			t.addVertexWithUV(1.0F, 0, 0, 0, 0);
			t.addVertexWithUV(1.0F, 1, 0, 0, 1);
			t.addVertexWithUV(1.0F, 1, 1, 1, 1);
			t.addVertexWithUV(1.0F, 0, 1, 1, 0);
			t.draw();

			bindTexture(BACK_TEXTURE);
			t.startDrawingQuads();
			//front
			t.addVertexWithUV(0.9F, 0, 0, 0, 0);
			t.addVertexWithUV(0.9F, 0, 1, 0, 1);
			t.addVertexWithUV(0.9F, 1, 1, 1, 1);
			t.addVertexWithUV(0.9F, 1, 0, 1, 0);

			// top
			t.addVertexWithUV(0.9F, 1, 0, 0, 0);
			t.addVertexWithUV(0.9F, 1, 1, 0, 1);
			t.addVertexWithUV(1.0F, 1, 1, 1, 1);
			t.addVertexWithUV(1.0F, 1, 0, 1, 0);
			//right
			t.addVertexWithUV(0.9F, 0, 0, 0, 0);
			t.addVertexWithUV(0.9F, 1, 0, 0, 1);
			t.addVertexWithUV(1.0F, 1, 0, 1, 1);
			t.addVertexWithUV(1.0F, 0, 0, 1, 0);
			//bottom
			t.addVertexWithUV(0.9F, 0, 1, 0, 0);
			t.addVertexWithUV(0.9F, 0, 0, 0, 1);
			t.addVertexWithUV(1.0F, 0, 0, 1, 1);
			t.addVertexWithUV(1.0F, 0, 1, 1, 0);
			//left
			t.addVertexWithUV(0.9F, 1, 1, 0, 0);
			t.addVertexWithUV(0.9F, 0, 1, 0, 1);
			t.addVertexWithUV(1.0F, 0, 1, 1, 1);
			t.addVertexWithUV(1.0F, 1, 1, 1, 0);
			t.draw();
		}
	}
}
