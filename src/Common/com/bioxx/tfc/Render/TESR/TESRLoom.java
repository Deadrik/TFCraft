package com.bioxx.tfc.Render.TESR;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Render.Models.ModelLoom;
import com.bioxx.tfc.TileEntities.TELoom;

public class TESRLoom extends TESRBase
{
	/** The normal small chest model. */
	private ModelLoom loomModel;
	private int tempNum = 0;
	private long tempTime = 0;
	private boolean clothIncrease = false;
	private int mod = 40;
	private int lastClothIncrease = 0;
	private boolean stillWeaving = false;
	/**
	 * Renders the TileEntity for the chest at a position.
	 */
	public void renderTileEntityLoomAt(TELoom te, double d, double d1, double d2, float f)
	{
		if (te.getWorldObj() == null)
		{
		}
		else
		{
			if(te.getModel() ==null){
				te.setModel(new ModelLoom());
			}
			loomModel = te.getModel();
			TFC_Core.bindTexture(te.getWoodResource()); //texture
			GL11.glPushMatrix(); //start
			GL11.glTranslatef((float)d + 0.0F, (float)d1 + 0F, (float)d2 + 0.0F); //size
			if(te.getStringCount()<16){
				loomModel.updateCloth(te.getCloth());
			}
			if(te.getIsWeaving() || stillWeaving){
				if(TFC_Time.getTotalTicks() > tempTime){
					tempNum = (tempNum+(int)(TFC_Time.getTotalTicks()-tempTime));
					tempTime = TFC_Time.getTotalTicks();
					if(tempNum >= (lastClothIncrease + (mod/2))){
						clothIncrease = true;
						lastClothIncrease = (lastClothIncrease + (mod/2))%mod;
						
						te.finishWeaveCycle();
						stillWeaving = te.getIsWeaving();
					}
					tempNum = tempNum%mod;
				}
			}
			else{
				tempTime = TFC_Time.getTotalTicks();
			}
			loomModel.render(te.getStringCount(),16,tempNum,clothIncrease,mod,te.getStringResource(), te.getIsWeaving(), stillWeaving, te);
			clothIncrease = false;
			GL11.glPopMatrix(); //end

		}
	}

	@Override
	public void renderTileEntityAt(TileEntity par1TileEntity, double par2, double par4, double par6, float par8)
	{
		this.renderTileEntityLoomAt((TELoom)par1TileEntity, par2, par4, par6, par8);
	}
}
