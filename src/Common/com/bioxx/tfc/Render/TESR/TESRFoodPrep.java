package com.bioxx.tfc.Render.TESR;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.TileEntities.TEFoodPrep;

public class TESRFoodPrep extends TESRBase
{

	public TESRFoodPrep()
	{
		super();
	}

	/**
	 * Renders the TileEntity for the chest at a position.
	 */
	public void renderAt(TEFoodPrep te, double d, double d1, double d2, float f)
	{
		if (te.getWorldObj() != null)
		{
			EntityItem customitem = new EntityItem(field_147501_a.field_147550_f); //tileEntityRenderer.worldObj
			customitem.hoverStart = 0f;
			float blockScale = 0.6F;
			float timeD = (float) (360.0 * (System.currentTimeMillis() & 0x3FFFL) / 0x3FFFL);

			if(RenderManager.instance.options.fancyGraphics)
			{
				if (te.getStackInSlot(0) != null)
				{
					GL11.glPushMatrix(); //start
					GL11.glTranslatef((float)d + 0.25F, (float)d1 + 0.0F, (float)d2 + 0.25F);
					GL11.glRotatef(timeD, 0.0F, 1.0F, 0.0F);
					GL11.glScalef(blockScale, blockScale, blockScale);
					customitem.setEntityItemStack(te.getStackInSlot(0));
					itemRenderer.doRender(customitem, 0, 0, 0, 0, 0);
					GL11.glPopMatrix(); //end
				}
				if (te.getStackInSlot(1) != null)
				{
					GL11.glPushMatrix(); //start
					GL11.glTranslatef((float)d + 0.75F, (float)d1 + 0.0F, (float)d2 + 0.25F);
					GL11.glRotatef(timeD, 0.0F, 1.0F, 0.0F);
					GL11.glScalef(blockScale, blockScale, blockScale);
					customitem.setEntityItemStack(te.getStackInSlot(1));
					itemRenderer.doRender(customitem, 0, 0, 0, 0, 0);
					GL11.glPopMatrix(); //end
				}
				if (te.getStackInSlot(2) != null)
				{
					GL11.glPushMatrix(); //start
					GL11.glTranslatef((float)d + 0.25F, (float)d1 + 0.0F, (float)d2 + 0.5F);
					GL11.glRotatef(timeD, 0.0F, 1.0F, 0.0F);
					GL11.glScalef(blockScale, blockScale, blockScale);
					customitem.setEntityItemStack(te.getStackInSlot(2));
					itemRenderer.doRender(customitem, 0, 0, 0, 0, 0);
					GL11.glPopMatrix(); //end
				}
				if (te.getStackInSlot(3) != null)
				{
					GL11.glPushMatrix(); //start
					GL11.glTranslatef((float)d + 0.75F, (float)d1 + 0.0F, (float)d2 + 0.5F);
					GL11.glRotatef(timeD, 0.0F, 1.0F, 0.0F);
					GL11.glScalef(blockScale, blockScale, blockScale);
					customitem.setEntityItemStack(te.getStackInSlot(3));
					itemRenderer.doRender(customitem, 0, 0, 0, 0, 0);
					GL11.glPopMatrix(); //end
				}
				if (te.getStackInSlot(4) != null)
				{
					GL11.glPushMatrix(); //start
					GL11.glTranslatef((float)d + 0.25F, (float)d1 + 0.0F, (float)d2 + 0.75F);
					GL11.glRotatef(timeD, 0.0F, 1.0F, 0.0F);
					GL11.glScalef(blockScale, blockScale, blockScale);
					customitem.setEntityItemStack(te.getStackInSlot(4));
					itemRenderer.doRender(customitem, 0, 0, 0, 0, 0);
					GL11.glPopMatrix(); //end
				}
				if (te.getStackInSlot(6) != null)
				{
					GL11.glPushMatrix(); //start
					GL11.glTranslatef((float)d + 0.75F, (float)d1 + 0.0F, (float)d2 + 0.75F);
					GL11.glRotatef(timeD, 0.0F, 1.0F, 0.0F);
					GL11.glScalef(blockScale, blockScale, blockScale);
					customitem.setEntityItemStack(te.getStackInSlot(6));
					itemRenderer.doRender(customitem, 0, 0, 0, 0, 0);
					GL11.glPopMatrix(); //end
				}
			}
			else
			{
				GL11.glPushMatrix(); //start
				GL11.glTranslated(d, d1+0.001, d2);
				drawItem(te, 0, 0.05, 0.35, 0.05, 0.35);
				drawItem(te, 1, 0.65, 0.95, 0.05, 0.35);
				drawItem(te, 2, 0.05, 0.35, 0.35, 0.65);
				drawItem(te, 3, 0.65, 0.95, 0.35, 0.65);
				drawItem(te, 4, 0.05, 0.35, 0.65, 0.95);
				drawItem(te, 6, 0.65, 0.95, 0.65, 0.95);
				GL11.glPopMatrix(); //end
			}
		}
	}

	public void drawItem(TEFoodPrep te, int index, double minX, double maxX, double minZ, double maxZ)
	{
		if (te.storage[index] != null && !(te.storage[index].getItem() instanceof ItemBlock))
		{
			TFC_Core.bindTexture(TextureMap.locationItemsTexture);
			float minU = te.storage[index].getIconIndex().getMinU();
			float maxU = te.storage[index].getIconIndex().getMaxU();
			float minV = te.storage[index].getIconIndex().getMinV();
			float maxV = te.storage[index].getIconIndex().getMaxV();
			Tessellator tessellator = Tessellator.instance;
			tessellator.startDrawingQuads();
			tessellator.setNormal(0.0F, 1.0F, 0.0F);
			tessellator.addVertexWithUV(minX, 0.0F, maxZ, minU, maxV);
			tessellator.addVertexWithUV(maxX, 0.0F, maxZ, maxU, maxV);
			tessellator.addVertexWithUV(maxX, 0.0F, minZ, maxU, minV);
			tessellator.addVertexWithUV(minX, 0.0F, minZ, minU, minV);
			tessellator.draw();
		}
	}

	@Override
	public void renderTileEntityAt(TileEntity par1TileEntity, double par2, double par4, double par6, float par8)
	{
		this.renderAt((TEFoodPrep)par1TileEntity, par2, par4, par6, par8);
	}
}
