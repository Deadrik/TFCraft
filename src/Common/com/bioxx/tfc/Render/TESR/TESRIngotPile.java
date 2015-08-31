package com.bioxx.tfc.Render.TESR;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Blocks.BlockIngotPile;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Render.Models.ModelIngotPile;
import com.bioxx.tfc.TileEntities.TEIngotPile;
import com.bioxx.tfc.api.TFCBlocks;

public class TESRIngotPile extends TESRBase
{
	/** The normal small chest model. */
	private final ModelIngotPile ingotModel = new ModelIngotPile();

	public void renderTileEntityIngotPileAt(TEIngotPile tep, double d, double d1, double d2, float f)
	{
		Block block = tep.getBlockType();
		if (tep.getWorldObj() != null && tep.getStackInSlot(0) != null && block == TFCBlocks.ingotPile)
		{
			int i = ((BlockIngotPile) block).getStack(tep.getWorldObj(), tep);
			TFC_Core.bindTexture(new ResourceLocation(Reference.MOD_ID, "textures/blocks/metal/" + tep.type + ".png")); //texture

			GL11.glPushMatrix(); //start
			GL11.glTranslatef((float)d + 0.0F, (float)d1 + 0F, (float)d2 + 0.0F); //size
			ingotModel.renderIngots(i);
			GL11.glPopMatrix(); //end
		}
	}

	@Override
	public void renderTileEntityAt(TileEntity te, double par2, double par4, double par6, float par8)
	{
		this.renderTileEntityIngotPileAt((TEIngotPile) te, par2, par4, par6, par8);
	}
}
