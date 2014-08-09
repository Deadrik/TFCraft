package com.bioxx.tfc.Render.TESR;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Blocks.Liquids.BlockCustomLiquid;
import com.bioxx.tfc.Core.TFCFluid;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Render.Models.ModelCrossedSquares;
import com.bioxx.tfc.TileEntities.TEWaterPlant;

public class TESRWaterPlant extends TileEntitySpecialRenderer
{
	static ResourceLocation seaweed = new ResourceLocation(Reference.ModID, "textures/blocks/plants/seaweed.png");
	static ResourceLocation pondweed = new ResourceLocation(Reference.ModID, "textures/blocks/plants/pondweed.png");
	static ResourceLocation cattails = new ResourceLocation(Reference.ModID, "textures/blocks/plants/Cat Tails.png");

	private ModelCrossedSquares plantModelSmall = new ModelCrossedSquares(0,0,16,16,16,16,16);
	private ModelCrossedSquares plantModelLarge = new ModelCrossedSquares(0,0,32,32,32,32,32);

	public void renderTileEntitySeaWeedAt(TEWaterPlant te, double d, double d1, double d2, float f)
	{
		int var9;
		if (te.getWorldObj() == null)
		{
			var9 = 0;
		}
		else
		{
			boolean isSmall = true;
			Block blockDirectlyAbove = te.getWorldObj().getBlock(te.xCoord, te.yCoord + 1, te.zCoord);
			boolean hasAirTwoAbove = te.getWorldObj().getBlock(te.xCoord, te.yCoord + 2, te.zCoord).isAir(te.getWorldObj(),te.xCoord, te.yCoord + 2, te.zCoord);
			if(blockDirectlyAbove instanceof BlockCustomLiquid){
				Fluid fluidDirectlyAbove = ((BlockCustomLiquid) blockDirectlyAbove).getFluid();
				if(fluidDirectlyAbove.equals(TFCFluid.FRESHWATER)){
					if(hasAirTwoAbove){
						TFC_Core.bindTexture(cattails);
						isSmall = false;
					}
					else{
						TFC_Core.bindTexture(pondweed);
					}
				}
				else if(fluidDirectlyAbove.equals(TFCFluid.SALTWATER)){
					TFC_Core.bindTexture(seaweed);
				}
				GL11.glPushMatrix(); //start
				GL11.glTranslatef((float)d + 0.0F, (float)d1 + 1F, (float)d2 + 0.0F); //size

				
				//if(isSmall)
					//plantModelSmall.renderSquares();
				//else
					//plantModelLarge.renderSquares();

				GL11.glPopMatrix(); //end
			}
		}
	}

	@Override
	public void renderTileEntityAt(TileEntity te, double par2, double par4, double par6, float par8)
	{
		this.renderTileEntitySeaWeedAt((TEWaterPlant)te, par2, par4, par6, par8);
	}
}
