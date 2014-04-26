package TFC.Render.TESR;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import TFC.Reference;
import TFC.Core.TFC_Core;
import TFC.Render.Models.ModelCrossedSquares;
import TFC.TileEntities.TESeaWeed;

public class TESRSeaWeed extends TileEntitySpecialRenderer
{
	static ResourceLocation seaweed = new ResourceLocation(Reference.ModID, "textures/blocks/plants/seaweed.png");
	static ResourceLocation pondweed = new ResourceLocation(Reference.ModID, "textures/blocks/plants/pondweed.png");
	static ResourceLocation cattails = new ResourceLocation(Reference.ModID, "textures/blocks/plants/Cat Tails.png");

	private ModelCrossedSquares plantModelSmall = new ModelCrossedSquares(0,0,16,16,16,16,16);
	private ModelCrossedSquares plantModelLarge = new ModelCrossedSquares(0,0,32,32,32,32,32);

	public void renderTileEntitySeaWeedAt(TESeaWeed te, double d, double d1, double d2, float f)
	{
		int var9;
		if (te.getWorldObj() == null)
		{
			var9 = 0;
		}
		else
		{
			boolean isSmall = true;
			switch(te.getType())
			{
			case 0: TFC_Core.bindTexture(seaweed);break;//texture
			case 1: TFC_Core.bindTexture(pondweed);break;//temp texture
			case 2: TFC_Core.bindTexture(cattails);isSmall = false;break;//temp texture
			default: TFC_Core.bindTexture(seaweed); //texture
			}
			GL11.glPushMatrix(); //start
			GL11.glTranslatef((float)d + 0.0F, (float)d1 + 0F, (float)d2 + 0.0F); //size

			if(isSmall)
				plantModelSmall.renderSquares();
			else
				plantModelLarge.renderSquares();

			GL11.glPopMatrix(); //end
		}
	}

	@Override
	public void renderTileEntityAt(TileEntity te, double par2, double par4, double par6, float par8)
	{
		this.renderTileEntitySeaWeedAt((TESeaWeed)te, par2, par4, par6, par8);
	}
}
