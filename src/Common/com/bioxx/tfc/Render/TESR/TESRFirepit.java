package com.bioxx.tfc.Render.TESR;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.TileEntities.TEFirepit;

public class TESRFirepit extends TESRBase
{
	/** The normal small chest model. */
	//private final ModelCookingPot potModel = new ModelCookingPot();
	/**
	 * Renders the TileEntity for the chest at a position.
	 */
	public void renderTileEntityFirepitAt(TEFirepit par1TileEntityPit, double d, double d1, double d2, float f)
	{
		//int var9;

		if (par1TileEntityPit.getWorldObj() != null)
		{
			if(par1TileEntityPit.hasCookingPot)
			{
				//Block var10 = par1TileEntityPit.getBlockType();
				//var9 = par1TileEntityPit.getBlockMetadata();
				TFC_Core.bindTexture(new ResourceLocation(Reference.MOD_ID, "textures/blocks/clay/Ceramic.png")); //texture
				GL11.glPushMatrix(); //start
				GL11.glTranslatef((float)d + 0.0F, (float)d1 + 0F, (float)d2 + 0.0F); //size

				//potModel.renderPot();
				GL11.glPopMatrix(); //end
			}
		}
	}

	@Override
	public void renderTileEntityAt(TileEntity par1TileEntity, double par2, double par4, double par6, float par8)
	{
		this.renderTileEntityFirepitAt((TEFirepit)par1TileEntity, par2, par4, par6, par8);
	}
}
