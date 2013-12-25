package TFC.Render.Blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.world.IBlockAccess;
import TFC.Core.TFC_Core;
import TFC.TileEntities.TileEntityFoodPrep;

public class RenderFoodPrep 
{
	static double[][] spritesOffsets = {{}, {}, {0.5, 0.3, 0.8, 0.06}, {}};

	public static boolean render(Block block, int i, int j, int k, RenderBlocks renderblocks)
	{
		IBlockAccess blockAccess = renderblocks.blockAccess;
		TileEntityFoodPrep te = (TileEntityFoodPrep)blockAccess.getBlockTileEntity(i, j, k);
		int dir = blockAccess.getBlockMetadata(i, j, k);
		if(te != null)
		{
			double height = 0.005;


			Tessellator tessellator = Tessellator.instance;

			int state = tessellator.drawMode;
			tessellator.draw();
			tessellator.startDrawingQuads();

			TFC_Core.bindTexture(TextureMap.locationItemsTexture);

			tessellator.setBrightness(block.getMixedBrightnessForBlock(blockAccess, i, j, k));
			tessellator.setColorRGBA_F(1, 1, 1, 1);
			if(te.storage[5] != null)
			{
				renderblocks.overrideBlockTexture = te.storage[5].getIconIndex();

				double minX = renderblocks.overrideBlockTexture.getMinU();
				double maxX = renderblocks.overrideBlockTexture.getMaxU();
				double minZ = renderblocks.overrideBlockTexture.getMinV();
				double maxZ = renderblocks.overrideBlockTexture.getMaxV();

				tessellator.addTranslation(0f, 0.0f, 0.0f);

				tessellator.addVertexWithUV(i + 0.30, 	j + height, 	k + 0.30, 	minX, minZ);
				tessellator.addVertexWithUV(i + 0.30, 	j + height, 	k + 0.70, 	maxX, minZ);
				tessellator.addVertexWithUV(i + 0.70, 	j + height, 	k + 0.70, 	maxX, maxZ);
				tessellator.addVertexWithUV(i + 0.70, 	j + height, 	k + 0.30, 	minX, maxZ);

				tessellator.addTranslation(0f, 0.0f, 0f);
			}
			if(te.storage[0] != null)
			{
				renderblocks.overrideBlockTexture = te.storage[0].getIconIndex();

				double minX = renderblocks.overrideBlockTexture.getMinU();
				double maxX = renderblocks.overrideBlockTexture.getMaxU();
				double minZ = renderblocks.overrideBlockTexture.getMinV();
				double maxZ = renderblocks.overrideBlockTexture.getMaxV();

				tessellator.addTranslation(0f, 0.0f, 0.0f);

				tessellator.addVertexWithUV(i + 0.05, 	j + height, 	k + 0.05, 	minX, minZ);
				tessellator.addVertexWithUV(i + 0.05, 	j + height, 	k + 0.45, 	maxX, minZ);
				tessellator.addVertexWithUV(i + 0.45, 	j + height, 	k + 0.45, 	maxX, maxZ);
				tessellator.addVertexWithUV(i + 0.45, 	j + height, 	k + 0.05, 	minX, maxZ);

				tessellator.addTranslation(0f, 0.0f, 0f);
			}
			if(te.storage[1] != null)
			{
				renderblocks.overrideBlockTexture = te.storage[1].getIconIndex();

				double minX = renderblocks.overrideBlockTexture.getMinU();
				double maxX = renderblocks.overrideBlockTexture.getMaxU();
				double minZ = renderblocks.overrideBlockTexture.getMinV();
				double maxZ = renderblocks.overrideBlockTexture.getMaxV();

				tessellator.addTranslation(0f, 0.0f, 0.5f);

				tessellator.addVertexWithUV(i + 0.05, 	j + height, 	k + 0.05, 	minX, minZ);
				tessellator.addVertexWithUV(i + 0.05, 	j + height, 	k + 0.45, 	maxX, minZ);
				tessellator.addVertexWithUV(i + 0.45, 	j + height, 	k + 0.45, 	maxX, maxZ);
				tessellator.addVertexWithUV(i + 0.45, 	j + height, 	k + 0.05, 	minX, maxZ);

				tessellator.addTranslation(0f, 0.0f, -0.5f);;
			}
			if(te.storage[2] != null)
			{
				renderblocks.overrideBlockTexture = te.storage[2].getIconIndex();

				double minX = renderblocks.overrideBlockTexture.getMinU();
				double maxX = renderblocks.overrideBlockTexture.getMaxU();
				double minZ = renderblocks.overrideBlockTexture.getMinV();
				double maxZ = renderblocks.overrideBlockTexture.getMaxV();

				tessellator.addTranslation(0.5f, 0.0f, 0.0f);

				tessellator.addVertexWithUV(i + 0.05, 	j + height, 	k + 0.05, 	minX, minZ);
				tessellator.addVertexWithUV(i + 0.05, 	j + height, 	k + 0.45, 	maxX, minZ);
				tessellator.addVertexWithUV(i + 0.45, 	j + height, 	k + 0.45, 	maxX, maxZ);
				tessellator.addVertexWithUV(i + 0.45, 	j + height, 	k + 0.05, 	minX, maxZ);

				tessellator.addTranslation(-0.5f, 0.0f, 0f);
			}
			if(te.storage[3] != null)
			{
				renderblocks.overrideBlockTexture = te.storage[3].getIconIndex();

				double minX = renderblocks.overrideBlockTexture.getMinU();
				double maxX = renderblocks.overrideBlockTexture.getMaxU();
				double minZ = renderblocks.overrideBlockTexture.getMinV();
				double maxZ = renderblocks.overrideBlockTexture.getMaxV();

				tessellator.addTranslation(0.5f, 0.0f, 0.5f);

				tessellator.addVertexWithUV(i + 0.05, 	j + height, 	k + 0.05, 	minX, minZ);
				tessellator.addVertexWithUV(i + 0.05, 	j + height, 	k + 0.45, 	maxX, minZ);
				tessellator.addVertexWithUV(i + 0.45, 	j + height, 	k + 0.45, 	maxX, maxZ);
				tessellator.addVertexWithUV(i + 0.45, 	j + height, 	k + 0.05, 	minX, maxZ);

				tessellator.addTranslation(-0.5f, 0.0f, -0.5f);
			}
			tessellator.draw();				
			tessellator.startDrawing(state);
			TFC_Core.bindTexture(TextureMap.locationBlocksTexture);
		}

		renderblocks.clearOverrideBlockTexture();
		return true;
	}
}
