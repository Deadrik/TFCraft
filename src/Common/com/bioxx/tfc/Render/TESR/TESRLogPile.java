package com.bioxx.tfc.Render.TESR;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.TileEntities.TELogPile;
import com.bioxx.tfc.api.Constant.Global;

public class TESRLogPile extends TESRBase
{
	public void renderTileEntityLogPileAt(TELogPile tep, double d, double d1, double d2, float f)
	{
		Block block = tep.getBlockType();
		if (tep.getWorldObj() != null && tep.getNumberOfLogs() != 0 && block == TFCBlocks.LogPile)
		{
			int m = tep.getBlockMetadata();
			
			Tessellator t = Tessellator.instance;
			GL11.glPushMatrix(); //start
			
			GL11.glTranslatef((float)d + 0.0F, (float)d1 + 0.0F, (float)d2 + 0.0F); //size
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			
			//if(m == 1 || m == 3)
			//{
				GL11.glTranslatef(0.5f, 0.0f, 0.5f); //size
				GL11.glRotatef((m*90), 0.0F, 1.0F, 0.0F);
				GL11.glTranslatef(-0.5f, 0.0f, -0.5f); //size
			//}
			
			int logStack = 0;
			int logStackLog = 0;
			
			for(int l = 0; l < tep.getNumberOfLogs(); l++)
			{
				
				while(logStack < tep.storage.length && tep.storage[logStack] == null)
					logStack++;
				
				if(tep.storage[logStack] != null)
				{				
			
					int layer = ((l + 4) / 4);
					float x = (l % 4)*0.25f;
					float y = (layer - 1)*0.25f;
					float z = 0f;
					
					boolean[] renderFaces = new boolean[6];
					renderFaces[0] = true; //Back
					renderFaces[1] = true; //Front
					renderFaces[2] = (((tep.getNumberOfLogs()+4)/4)-layer < 1 || ((tep.getNumberOfLogs()%4 <= (l%4)) && (layer == ((tep.getNumberOfLogs()+4)/4) - 1))); //Top
					renderFaces[3] = (y==0); //Bottom
					renderFaces[4] = (l == (tep.getNumberOfLogs() - 1) || (l%4 == 3)); //Right
					renderFaces[5] = (l%4 == 0); //Left
						

					renderLog(t, x, y, z, renderFaces, Global.WOOD_ALL[tep.storage[logStack].getItemDamage()]);
					
					logStackLog++;
					if(logStackLog >= tep.storage[logStack].stackSize)
					{
						logStack++;
						logStackLog = 0;
					}
				}
			}			
			
			GL11.glPopMatrix(); //end
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		}
	}
	
	public void renderLog(Tessellator t, float originX, float originY, float originZ, boolean[] renderFaces, String woodType)
	{
		float maxX = originX + (0.25f);
        float maxY = originY + 0.25f;
        float maxZ = originZ + (1.0f);
        
        float a = 0.0f;
        float b = 0.25f;
        float c = 1.0f;
                
       	bindTexture(new ResourceLocation(Reference.ModID, "textures/blocks/wood/trees/" + woodType + " Log Top.png"));
        
		//back
       	if(renderFaces[0])
       	{
       		t.startDrawingQuads();
			t.setNormal(0, 0, 1.0f);
			t.addVertexWithUV(originX, maxY, originZ, c, a);
			t.addVertexWithUV(maxX, maxY, originZ, c, c);
			t.addVertexWithUV(maxX, originY, originZ, a, c);
			t.addVertexWithUV(originX, originY, originZ, a, a);
			t.draw();
       	}
		
		//front
       	if(renderFaces[1])
       	{
			t.startDrawingQuads();
			t.setNormal(0, 0, -1.0f);
			t.addVertexWithUV(originX, originY, maxZ, a, a);
			t.addVertexWithUV(maxX, originY, maxZ, a, c);
			t.addVertexWithUV(maxX, maxY, maxZ, c, c);
			t.addVertexWithUV(originX, maxY, maxZ, c, a);
			t.draw();
       	}
		
		bindTexture(new ResourceLocation(Reference.ModID, "textures/blocks/wood/trees/" + woodType + " Log Side.png"));

		// top
		if(renderFaces[2])
		{
			t.startDrawingQuads();
			t.setNormal(0, 1.0f, 0);
			t.addVertexWithUV(originX, maxY, originZ, a, a);
			t.addVertexWithUV(originX, maxY, maxZ, c, a);
			t.addVertexWithUV(maxX, maxY, maxZ, c, b);
			t.addVertexWithUV(maxX, maxY, originZ, a, b);
			t.draw();
		}
		
		//bottom
		if(renderFaces[3])
		{
			t.startDrawingQuads();
			t.setNormal(0, -1.0f, 0);
			t.addVertexWithUV(maxX, originY, originZ, b, c);
			t.addVertexWithUV(maxX, originY, maxZ, a, c);
			t.addVertexWithUV(originX, originY, maxZ, a, a);
			t.addVertexWithUV(originX, originY, originZ, b, a);
			t.draw();
		}

		//right
		if(renderFaces[4])
		{
			t.startDrawingQuads();
			t.setNormal(1.0f, 0, 0);
			t.addVertexWithUV(maxX, maxY, originZ, a, a);
			t.addVertexWithUV(maxX, maxY, maxZ, c, a);
			t.addVertexWithUV(maxX, originY, maxZ, c, b);
			t.addVertexWithUV(maxX, originY, originZ, a, b);
			t.draw();
		}
		
		//left
		if(renderFaces[5])
		{
			t.startDrawingQuads();
			t.setNormal(-1.0f, 0, 0);
			t.addVertexWithUV(originX, originY, originZ, a, b);
			t.addVertexWithUV(originX, originY, maxZ, c, b);
			t.addVertexWithUV(originX, maxY, maxZ, c, a);
			t.addVertexWithUV(originX, maxY, originZ, a, a);
			t.draw();
		}
	}
	
	@Override
	public void renderTileEntityAt(TileEntity te, double par2, double par4, double par6, float par8)
	{
		this.renderTileEntityLogPileAt((TELogPile) te, par2, par4, par6, par8);
	}
}
