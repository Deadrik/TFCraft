package com.bioxx.tfc.Render.TESR;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Render.Models.ModelUrn;
import com.bioxx.tfc.TileEntities.TEPottery;
import com.bioxx.tfc.api.Constant.Global;

public class TESRPottery extends TESRBase
{
	private ModelUrn urnModel;

	public TESRPottery()
	{
	}
	
	public void renderAt(TEPottery te, double d, double d1, double d2, float f)
	{
		if (te.getWorldObj() == null)
		{
			return;
		}
		
		//When the straw reaches 8 the pottery is hidden, this just
		//looks like the straw has covered the contents rather than the
		//contents disappearing.
		if(te.straw < 8)
		{
			renderContents(te, d, d1, d2, f);
		}
		
		if(te.straw > 0)
		{
			renderStraw(te, (float)d, (float)d1, (float)d2);
		}
		
		if(te.wood > 0)
		{
			renderLogs(te, d, d1, d2, f);
		}
	}
	
	public void renderContents(TEPottery te, double d, double d1, double d2, float f)
	{
		EntityItem customitem = new EntityItem(field_147501_a.field_147550_f); //tileEntityRenderer.worldObj
		customitem.hoverStart = 0f;
		float blockScale = 1.0F;
		float timeD = (float) (360.0 * (System.currentTimeMillis() & 0x3FFFL) / 0x3FFFL);
		for(int i = 0; i < 4; i++)
		{
			float offsetX = 0.25f; 
			float offsetY = 0.1f; 
			float offsetZ = 0.25f;
			if(i == 1)
			{
				offsetX = 0.75f; 
				offsetZ = 0.25f;
			}
			else if(i == 2)
			{
				offsetX = 0.25f; 
				offsetZ = 0.75f;
			}
			else if(i == 3)
			{
				offsetX = 0.75f; 
				offsetZ = 0.75f;
			}

			if (te.getStackInSlot(i) != null)
			{
				if(te.getStackInSlot(i).getItem() instanceof ItemBlock)
				{
					blockScale = 2; offsetX = 0.5f; offsetZ = 0.5f;
				}
				GL11.glPushMatrix(); //start
				GL11.glTranslatef((float)d + offsetX, (float)d1 + offsetY, (float)d2 + offsetZ);
				if (RenderManager.instance.options.fancyGraphics)
				{
					GL11.glRotatef(timeD, 0.0F, 1.0F, 0.0F);
				}
				GL11.glScalef(blockScale, blockScale, blockScale);
				customitem.setEntityItemStack(te.getStackInSlot(i));
				itemRenderer.doRender(customitem, 0, 0, 0, 0, 0);
				GL11.glPopMatrix(); //end

			}
		}
	}
	
	public void renderStraw(TEPottery te, float d,  float d1,  float d2)
	{

		float originX = 0f;
		float originY = 0f;
		float originZ = 0f;
		float maxX = originX + 1f;
        float maxY = originY + (0.0625f*te.straw);
        float maxZ = originZ + (1.0f);
        
        float a = 0.0f;
        float b = maxY;
        float c = 1.0f;

		Tessellator t = Tessellator.instance;
		GL11.glPushMatrix(); //start
		
		GL11.glTranslatef(d, d1, d2); //size
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        
       	bindTexture(new ResourceLocation(Reference.ModID, "textures/blocks/plants/Thatch.png"));
        
		//All faces, other than the top, are always rendered because you can chisel through
       	//a block and the side will still be considered solid.
       	
		//back
       	t.startDrawingQuads();
		t.setNormal(0, 0, 1.0f);
		t.addVertexWithUV(originX, maxY, originZ, a, a);
		t.addVertexWithUV(maxX, maxY, originZ, c, a);
		t.addVertexWithUV(maxX, originY, originZ, c, b);
		t.addVertexWithUV(originX, originY, originZ, a, b);
		t.draw();
		
		//front
       	t.startDrawingQuads();
		t.setNormal(0, 0, -1.0f);
		t.addVertexWithUV(originX, originY, maxZ, a, b);
		t.addVertexWithUV(maxX, originY, maxZ, c, b);
		t.addVertexWithUV(maxX, maxY, maxZ, c, a);
		t.addVertexWithUV(originX, maxY, maxZ, a, a);
		t.draw();

       	// top
		if(te.wood < 4)
		{
			t.startDrawingQuads();
			t.setNormal(0, 1.0f, 0);
			t.addVertexWithUV(originX, maxY, originZ, a, a);
			t.addVertexWithUV(originX, maxY, maxZ, c, a);
			t.addVertexWithUV(maxX, maxY, maxZ, c, c);
			t.addVertexWithUV(maxX, maxY, originZ, a, c);
			t.draw();
		}
		
		//bottom
		t.startDrawingQuads();
		t.setNormal(0, -1.0f, 0);
		t.addVertexWithUV(maxX, originY, originZ, c, c);
		t.addVertexWithUV(maxX, originY, maxZ, a, c);
		t.addVertexWithUV(originX, originY, maxZ, a, a);
		t.addVertexWithUV(originX, originY, originZ, c, a);
		t.draw();

		//right
		t.startDrawingQuads();
		t.setNormal(1.0f, 0, 0);
		t.addVertexWithUV(maxX, maxY, originZ, a, a);
		t.addVertexWithUV(maxX, maxY, maxZ, c, a);
		t.addVertexWithUV(maxX, originY, maxZ, c, b);
		t.addVertexWithUV(maxX, originY, originZ, a, b);
		t.draw();
		
		//left
		t.startDrawingQuads();
		t.setNormal(-1.0f, 0, 0);
		t.addVertexWithUV(originX, originY, originZ, a, b);
		t.addVertexWithUV(originX, originY, maxZ, c, b);
		t.addVertexWithUV(originX, maxY, maxZ, c, a);
		t.addVertexWithUV(originX, maxY, originZ, a, a);
		t.draw();
		
		GL11.glPopMatrix(); //end
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}
	
	public void renderLogs(TEPottery te, double d, double d1, double d2, float f)
	{
		Tessellator t = Tessellator.instance;
		GL11.glPushMatrix(); //start
			
		GL11.glTranslatef((float)d + 0.0F, (float)d1 + 0.0F, (float)d2 + 0.0F); //size
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			
		for(int n = 4; n < te.wood + 4; n++)
		{
				
			int l = n - 4; //Convert to log number
				
			if(te.inventory[n] != null)
			{				
		
				int layer = ((l + 4) / 4);
				float x = (l % 4)*0.25f;
				float y = ((layer - 1)*0.25f) + 0.5f;
				float z = 0f;
				
				boolean[] renderFaces = new boolean[6];
				renderFaces[0] = true; //Back
				renderFaces[1] = true; //Front
				renderFaces[2] = (((te.wood+4)/4)-layer < 1 || ((te.wood%4 <= (l%4)) && (layer == ((te.wood+4)/4) - 1))); //Top
				renderFaces[3] = false; //Bottom
				renderFaces[4] = (x == 0.75 || l == te.wood - 1); //Right
				renderFaces[5] = x == 0; //Left
				
				renderLog(t, x, y, z, renderFaces, Global.WOOD_ALL[te.inventory[n].getItemDamage()]);
			}
		}			
		
		GL11.glPopMatrix(); //end
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
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
	public void renderTileEntityAt(TileEntity par1TileEntity, double par2, double par4, double par6, float par8)
	{
		this.renderAt((TEPottery)par1TileEntity, par2, par4, par6, par8);
	}
}
