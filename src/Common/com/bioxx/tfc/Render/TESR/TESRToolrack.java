package com.bioxx.tfc.Render.TESR;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.TileEntities.TEToolRack;

public class TESRToolrack extends TESRBase
{
	public TESRToolrack()
	{
	}

	/**
	 * Renders the TileEntity for the chest at a position.
	 */
	public void renderAt(TEToolRack te, double d, double d1, double d2, float f)
	{
		if (te.getWorldObj() != null)
		{
			int dir = te.getWorldObj().getBlockMetadata(te.xCoord, te.yCoord, te.zCoord);

			EntityItem customitem = new EntityItem(field_147501_a.field_147550_f); //tileEntityRenderer.worldObj
			customitem.hoverStart = 0f;
			float blockScale = 1.0F;
			//float timeD = (float) (360.0 * (System.currentTimeMillis() & 0x3FFFL) / 0x3FFFL);
			for(int i = 0; i < 4; i++)
			{
				if (te.getStackInSlot(i) != null)
				{
					float[] loc = getLocation(dir, i);
					GL11.glPushMatrix(); //start
					GL11.glTranslatef((float)d + loc[0], (float)d1 + loc[1], (float)d2 + loc[2]);
					if (RenderManager.instance.options.fancyGraphics)
					{
						GL11.glRotatef(loc[3], 0.0F, 1.0F, 0.0F);
					}
					GL11.glScalef(blockScale, blockScale, blockScale);
					customitem.setEntityItemStack(te.getStackInSlot(i));
					itemRenderer.doRender(customitem, 0, 0, 0, 0, 0);
					GL11.glPopMatrix(); //end
				}
			}
		}
	}

	public float[] getLocation(int dir, int slot)
	{
		float[] out = new float[4];
		if(dir == 0)
		{
			out[3] = 0f;
			if(slot == 0)
			{
				out[0] = 0.25f; 
				out[1] = 0.5f; 
				out[2] = 0.94f;
			}
			else if(slot == 1)
			{
				out[0] = 0.75f; 
				out[1] = 0.5f; 
				out[2] = 0.94f;
			}
			else if(slot == 2)
			{
				out[0] = 0.25f;
				out[1] = 0.1f; 
				out[2] = 0.94f;
			}
			else if(slot == 3)
			{
				out[0] = 0.75f; 
				out[1] = 0.1f; 
				out[2] = 0.94f;
			}
		}
		else if(dir == 1)
		{
			out[3] = 270f;
			if(slot == 0)
			{
				out[0] = 0.06f; 
				out[1] = 0.5f; 
				out[2] = 0.25f;
			}
			else if(slot == 1)
			{
				out[0] = 0.06f; 
				out[1] = 0.5f; 
				out[2] = 0.75f;
			}
			else if(slot == 2)
			{
				out[0] = 0.06f;
				out[1] = 0.1f; 
				out[2] = 0.25f;
			}
			else if(slot == 3)
			{
				out[0] = 0.06f; 
				out[1] = 0.1f; 
				out[2] = 0.75f;
			}
		}
		else if(dir == 2)
		{
			out[3] = 180f;
			if(slot == 0)
			{
				out[0] = 0.25f; 
				out[1] = 0.5f; 
				out[2] = 0.06f;
			}
			else if(slot == 1)
			{
				out[0] = 0.75f; 
				out[1] = 0.5f; 
				out[2] = 0.06f;
			}
			else if(slot == 2)
			{
				out[0] = 0.25f;
				out[1] = 0.1f; 
				out[2] = 0.06f;
			}
			else if(slot == 3)
			{
				out[0] = 0.75f; 
				out[1] = 0.1f; 
				out[2] = 0.06f;
			}
		}
		else if(dir == 3)
		{
			out[3] = 90f;
			if(slot == 0)
			{
				out[0] = 0.94f; 
				out[1] = 0.5f; 
				out[2] = 0.25f;
			}
			else if(slot == 1)
			{
				out[0] = 0.94f; 
				out[1] = 0.5f; 
				out[2] = 0.75f;
			}
			else if(slot == 2)
			{
				out[0] = 0.94f;
				out[1] = 0.1f; 
				out[2] = 0.25f;
			}
			else if(slot == 3)
			{
				out[0] = 0.94f; 
				out[1] = 0.1f; 
				out[2] = 0.75f;
			}
		}
		return out;
	}

	@Override
	public void renderTileEntityAt(TileEntity par1TileEntity, double par2, double par4, double par6, float par8)
	{
		this.renderAt((TEToolRack)par1TileEntity, par2, par4, par6, par8);
	}
}
