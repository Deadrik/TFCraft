package TFC.Render.TESR;

import java.util.Calendar;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.model.ModelLargeChest;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import TFC.Reference;
import TFC.Blocks.Devices.BlockChestTFC;
import TFC.Core.TFC_Core;
import TFC.Render.Models.ModelCrossedSquares;
import TFC.Render.Models.ModelIngotPile;
import TFC.TileEntities.TESeaWeed;
import TFC.TileEntities.TileEntityChestTFC;
import TFC.TileEntities.TileEntityIngotPile;
import cpw.mods.fml.common.FMLLog;

public class TESRSeaWeed extends TileEntitySpecialRenderer
{
	/** The normal small chest model. */
	private ModelCrossedSquares plantModel;
	/**
	 * Renders the TileEntity for the chest at a position.
	 */
	public void renderTileEntitySeaWeedAt(TESeaWeed te, double d, double d1, double d2, float f)
	{
		int var9;

		if (te.worldObj == null)
		{
			var9 = 0;
		}
		else
		{
			Block var10 = te.getBlockType();
			var9 = te.getBlockMetadata();
			plantModel = new ModelCrossedSquares(0,0,16,16,16,16,16);
			int type = te.getType();
			switch(type){
				case 0: TFC_Core.bindTexture(new ResourceLocation(Reference.ModID, "textures/blocks/plants/"+"seaweed"+".png"));break;//texture
				case 1: TFC_Core.bindTexture(new ResourceLocation(Reference.ModID, "textures/blocks/plants/"+"pondweed"+".png"));break;//temp texture
				case 2: TFC_Core.bindTexture(new ResourceLocation(Reference.ModID, "textures/blocks/plants/"+"Cat Tails"+".png"));
				plantModel = new ModelCrossedSquares(0,0,32,32,32,32,32);
				break;//temp texture
				default: TFC_Core.bindTexture(new ResourceLocation(Reference.ModID, "textures/blocks/plants/"+"seaweed"+".png")); //texture
			}
			GL11.glPushMatrix(); //start
			GL11.glTranslatef((float)d + 0.0F, (float)d1 + 0F, (float)d2 + 0.0F); //size

			plantModel.renderSquares();
			GL11.glPopMatrix(); //end

		}
	}


	@Override
	public void renderTileEntityAt(TileEntity te, double par2, double par4, double par6, float par8)
	{
		this.renderTileEntitySeaWeedAt((TESeaWeed)te, par2, par4, par6, par8);
	}
}
