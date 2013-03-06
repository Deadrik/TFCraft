package TFC.Render;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.model.ModelLargeChest;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.crash.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.packet.*;
import net.minecraft.pathfinding.*;
import net.minecraft.potion.*;
import net.minecraft.server.*;
import net.minecraft.stats.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.village.*;
import net.minecraft.world.*;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import TFC.*;
import TFC.Core.TFC_Textures;
import TFC.TileEntities.TileEntityChestTFC;
import TFC.TileEntities.TileEntityIngotPile;

public class TileEntityIngotPileRenderer extends TileEntitySpecialRenderer
{
	/** The normal small chest model. */
	private ModelIngotPile ingotModel = new ModelIngotPile();

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
				bindTextureByName(TFC_Textures.BlockSheet); //texture
				GL11.glPushMatrix(); //start
				GL11.glTranslatef((float)d + 0.0F, (float)d1 + 0F, (float)d2 + 0.0F); //size

				ingotModel.renderIngots(i,j);
				GL11.glPopMatrix(); //end
			}
		}
	}

	public void renderTileEntityAt(TileEntity par1TileEntity, double par2, double par4, double par6, float par8)
	{
		this.renderTileEntityIngotPileAt((TileEntityIngotPile)par1TileEntity, par2, par4, par6, par8);
	}
}
