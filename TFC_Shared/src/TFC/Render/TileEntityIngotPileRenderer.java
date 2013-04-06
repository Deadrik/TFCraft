package TFC.Render;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import TFC.TileEntities.TileEntityIngotPile;

public class TileEntityIngotPileRenderer extends TileEntitySpecialRenderer
{
	/** The normal small chest model. */
	private final ModelIngotPile ingotModel = new ModelIngotPile();
	private static String[] metalTypes =  new String[]{"Bismuth", "Bismuth Bronze", "Black Bronze", "Black Steel", "Blue Steel", "Brass", 
		"Bronze", "Copper", "Gold", "Wrought Iron", "Lead", "Nickel", "Pig Iron", "Platinum", "Red Steel", "Rose Gold", "Silver", "Steel",
		"Sterling Silver", "Tin", "Zinc" };
	/**
	 * Renders the TileEntity for the chest at a position.
	 */
	public void renderTileEntityIngotPileAt(TileEntityIngotPile par1TileEntityPile, double d, double d1, double d2, float f)
	{
		int var9;

		if (par1TileEntityPile.worldObj == null)
		{
			var9 = 0;
		}
		else
		{
			Block var10 = par1TileEntityPile.getBlockType();
			var9 = par1TileEntityPile.getBlockMetadata();



			if (par1TileEntityPile.getStackInSlot(0)!=null)
			{
				par1TileEntityPile.validate();
				int i = ((TFC.Blocks.BlockIngotPile)var10).getStack(par1TileEntityPile.worldObj,par1TileEntityPile);
				int j = par1TileEntityPile.getType() != -1 ? par1TileEntityPile.getType() : 0;
				bindTextureByName("/textures/blocks/metal/"+metalTypes[par1TileEntityPile.type]+".png"); //texture
				GL11.glPushMatrix(); //start
				GL11.glTranslatef((float)d + 0.0F, (float)d1 + 0F, (float)d2 + 0.0F); //size

				ingotModel.renderIngots(i,j);
				GL11.glPopMatrix(); //end
			}
		}
	}

	@Override
	public void renderTileEntityAt(TileEntity par1TileEntity, double par2, double par4, double par6, float par8)
	{
		this.renderTileEntityIngotPileAt((TileEntityIngotPile)par1TileEntity, par2, par4, par6, par8);
	}
}
