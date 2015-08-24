package com.bioxx.tfc.Render.TESR;

import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Render.Models.ModelLoom;
import com.bioxx.tfc.TileEntities.TELoom;

public class TESRLoom extends TESRBase
{
	/**
	 * Renders the TileEntity for the chest at a position.
	 */
	public void renderTileEntityLoomAt(TELoom te, double d, double d1, double d2, float f)
	{
		if (te.getWorldObj() != null)
		{
			int stringCount = te.getStringCount();
			int reqStringCount = te.getRequiredStringCount();

			if (te.getModel() == null)
			{
				te.setModel(new ModelLoom());
			}
			TFC_Core.bindTexture(te.getWoodResource()); //texture
			GL11.glPushMatrix(); //start
			GL11.glTranslatef((float)d + 0.0F, (float)d1 + 0F, (float)d2 + 0.0F); //size
			if (stringCount < reqStringCount)
			{
				te.getModel().updateCloth(te.getCloth());
			}
			if (te.getIsWeaving() || te.getModel().stillWeaving)
			{
				if (TFC_Time.getTotalTicks() > te.getModel().tempTime)
				{
					te.getModel().tempNum = te.getModel().tempNum + (int) (TFC_Time.getTotalTicks() - te.getModel().tempTime);
					te.getModel().tempTime = TFC_Time.getTotalTicks();
					if (te.getModel().tempNum >= (te.getModel().lastClothIncrease + (te.getModel().mod / 2)))
					{
						te.getModel().clothIncrease = true;
						te.getModel().lastClothIncrease = (te.getModel().lastClothIncrease + (te.getModel().mod / 2)) % te.getModel().mod;
						
						te.finishWeaveCycle();
						te.getModel().stillWeaving = te.getIsWeaving();
					}
					te.getModel().tempNum = te.getModel().tempNum % te.getModel().mod;
				}
			}
			else{
				te.getModel().tempTime = TFC_Time.getTotalTicks();
			}
			
			if (te.getStackInSlot(1) != null) // Render completed cloth
				te.getModel().render(reqStringCount, reqStringCount, te.getModel().tempNum, false, te.getModel().mod, te.getStringResource(), false, false, te);
			else // Render based on string, or empty loom
				te.getModel().render(stringCount, reqStringCount, te.getModel().tempNum, te.getModel().clothIncrease, te.getModel().mod, te.getStringResource(), te.getIsWeaving(), te.getModel().stillWeaving, te);

			te.getModel().clothIncrease = false;
			GL11.glPopMatrix(); //end
		}
	}

	@Override
	public void renderTileEntityAt(TileEntity par1TileEntity, double par2, double par4, double par6, float par8)
	{
		this.renderTileEntityLoomAt((TELoom)par1TileEntity, par2, par4, par6, par8);
	}
}
