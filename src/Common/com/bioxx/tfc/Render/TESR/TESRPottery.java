package com.bioxx.tfc.Render.TESR;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.TileEntities.TEPottery;

public class TESRPottery extends TESRBase
{
	//private ModelUrn urnModel;

	public TESRPottery()
	{
	}

	/**
	 * Renders the TileEntity for the chest at a position.
	 */
	public void renderAt(TEPottery te, double d, double d1, double d2, float f)
	{
		if (te.getWorldObj() != null)
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
	}

	@Override
	public void renderTileEntityAt(TileEntity par1TileEntity, double par2, double par4, double par6, float par8)
	{
		this.renderAt((TEPottery)par1TileEntity, par2, par4, par6, par8);
	}
}
