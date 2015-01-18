package com.bioxx.tfc.Render.TESR;

import java.util.Random;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.TileEntities.TEWorldItem;

public class TESRWorldItem extends TESRBase
{
	public static Random rand = new Random();
	public TESRWorldItem()
	{
	}

	/**
	 * Renders the TileEntity for the chest at a position.
	 */
	public void renderAt(TEWorldItem te, double d, double d1, double d2, float f)
	{
		if (te.getWorldObj() != null)
		{

			if(te.renderItem == null)
			{
				rand.setSeed((te.xCoord + te.zCoord)*te.xCoord);
				te.renderItem = new EntityItem(field_147501_a.field_147550_f); //tileEntityRenderer.worldObj
				te.renderItem.setAngles(rand.nextFloat()*360, 90);
				te.renderItem.hoverStart = 0f;
				te.renderItem.setEntityItemStack(te.storage[0]);
			}

			if (te.storage[0] != null)
			{
				float minU = te.storage[0].getIconIndex().getMinU();
				float maxU = te.storage[0].getIconIndex().getMaxU();
				float minV = te.storage[0].getIconIndex().getMinV();
				float maxV = te.storage[0].getIconIndex().getMaxV();
				//float f6 = 1.0F;
				//float f7 = 0.5F;
				//float f8 = 0.25F;

				if (te.storage[0].getItemSpriteNumber() == 0)
				{
					this.bindTexture(TextureMap.locationBlocksTexture);
				}
				else
				{
					this.bindTexture(TextureMap.locationItemsTexture);
				}

				boolean fancy = RenderManager.instance.options.fancyGraphics;
				//RenderManager.instance.options.fancyGraphics = true;
				GL11.glPushMatrix(); //start

				if(RenderManager.instance.options.fancyGraphics)
				{
					GL11.glTranslatef((float)d + 0.5f, (float)d1 + 0.021f, (float)d2 + 0.5f);
					GL11.glRotatef(90, 1.0f, 0.0F, 0.0F);
					//GL11.glRotatef(rand.nextFloat()*360, 0.0f, 0.0F, 1.0F);

					itemRenderer.doRender(te.renderItem, 0, 0, 0, 0, 0);
				}
				else
				{
					GL11.glTranslated(d, d1+0.001, d2);
					Tessellator tessellator = Tessellator.instance;
					tessellator.startDrawingQuads();
					tessellator.setNormal(0.0F, 1.0F, 0.0F);
					tessellator.addVertexWithUV(0.2, 0.0F, 0.8D, minU, maxV);
					tessellator.addVertexWithUV(0.8, 0.0F, 0.8D, maxU, maxV);
					tessellator.addVertexWithUV(0.8, 0.0F, 0.2D, maxU, minV);
					tessellator.addVertexWithUV(0.2, 0.0F, 0.2D, minU, minV);
					tessellator.draw();
				}
				GL11.glPopMatrix(); //end
				RenderManager.instance.options.fancyGraphics = fancy;
			}
		}
	}

	@Override
	public void renderTileEntityAt(TileEntity par1TileEntity, double par2, double par4, double par6, float par8)
	{
		this.renderAt((TEWorldItem)par1TileEntity, par2, par4, par6, par8);
	}
}
