package TFC.Render.TESR;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import TFC.Reference;
import TFC.Core.TFC_Core;
import TFC.Render.Models.ModelLogPile;
import TFC.TileEntities.TileEntityLogPile;

public class TESRLogPile extends TESRBase
{
	/** The normal small chest model. */
	private final ModelLogPile logModel = new ModelLogPile();

	/**
	 * Renders the TileEntity for the chest at a position.
	 */
	public void renderTileEntityLogPileAt(TileEntityLogPile par1TileEntityPile, double d, double d1, double d2, float f)
	{
		if (par1TileEntityPile.worldObj != null)
		{
			Block var10 = par1TileEntityPile.getBlockType();
			int i = par1TileEntityPile.getNumberOfLogs();

			if (i > 0)
			{
				TFC_Core.bindTexture(new ResourceLocation(Reference.ModID, "textures/blocks/wood/trees/"+par1TileEntityPile.getType()+".png")); //texture
				GL11.glPushMatrix(); //start
				GL11.glTranslatef((float)d + 0.0F, (float)d1 + 0F, (float)d2 + 0.0F); //size

				logModel.renderLogs(i);
				GL11.glPopMatrix(); //end
			}
		}
	}

	@Override
	public void renderTileEntityAt(TileEntity par1TileEntity, double par2, double par4, double par6, float par8)
	{
		this.renderTileEntityLogPileAt((TileEntityLogPile)par1TileEntity, par2, par4, par6, par8);
	}
}
