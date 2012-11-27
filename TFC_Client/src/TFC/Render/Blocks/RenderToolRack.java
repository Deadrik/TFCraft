package TFC.Render.Blocks;

import org.lwjgl.opengl.GL11;

import TFC.TFCBlocks;
import TFC.Blocks.BlockToolRack;
import TFC.TileEntities.TileEntityTerraAnvil;
import TFC.TileEntities.TileEntityToolRack;
import net.minecraft.src.Block;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Item;
import net.minecraft.src.ModLoader;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.Tessellator;
import net.minecraftforge.client.ForgeHooksClient;

public class RenderToolRack 
{
	static double[][] spritesOffsets = {{}, {}, {0.5, 0.3, 0.8, 0.06}, {}};

	public static boolean renderToolRack(Block block, int i, int j, int k, RenderBlocks renderblocks)
	{
		IBlockAccess blockAccess = renderblocks.blockAccess;
		TileEntityToolRack te = (TileEntityToolRack)blockAccess.getBlockTileEntity(i, j, k);
		renderblocks.renderAllFaces = true;
		int dir = blockAccess.getBlockMetadata(i, j, k);
		if(te != null)
		{
			renderblocks.overrideBlockTexture = ((BlockToolRack)block).getBlockTexture(te.woodType);

			//First we render the rack itself.
			if(dir == 0)
			{
				renderRackDir0(block, i, j, k, renderblocks, 0.7f);

				renderRackDir0(block, i, j, k, renderblocks, 0.3f);
				
				ForgeHooksClient.bindTexture("/bioxx/terratools.png", ModLoader.getMinecraftInstance().renderEngine.getTexture("/bioxx/terratools.png"));
				Tessellator tessellator = Tessellator.instance;
				tessellator.setBrightness(block.getMixedBrightnessForBlock(blockAccess, i, j, k));
				tessellator.setColorRGBA_F(1, 1, 1, 1);
				if(te.storage[0] != null)
				{
					renderblocks.overrideBlockTexture = Item.itemsList[te.storage[0].itemID].getIconIndex(te.storage[0]);

					int x = (renderblocks.overrideBlockTexture & 0xf) << 4;
					int z = renderblocks.overrideBlockTexture & 0xf0;

					double minX = ((double)x + 16	) / 256D;
					double maxX = ((double)x + 0	) / 256D;
					double minZ = ((double)z + 0	) / 256D;
					double maxZ = ((double)z + 16	) / 256D;

					tessellator.addTranslation(0f, 0.2f, 0.0f);
					
					tessellator.addVertexWithUV(i, 			j + 0.8, 	k + 0.94, 	minX, minZ);
					tessellator.addVertexWithUV(i + 0.5, 	j + 0.8, 	k + 0.94, 	maxX, minZ);
					tessellator.addVertexWithUV(i + 0.5, 	j + 0.3, 	k + 0.94, 	maxX, maxZ);
					tessellator.addVertexWithUV(i, 			j + 0.3, 	k + 0.94, 	minX, maxZ);
					
					tessellator.addTranslation(0f, -0.2f, 0f);
				}
				if(te.storage[1] != null)
				{
					renderblocks.overrideBlockTexture = Item.itemsList[te.storage[1].itemID].getIconIndex(te.storage[1]);

					int x = (renderblocks.overrideBlockTexture & 0xf) << 4;
					int z = renderblocks.overrideBlockTexture & 0xf0;

					double minX = ((double)x + 16	) / 256D;
					double maxX = ((double)x + 0	) / 256D;
					double minZ = ((double)z + 0	) / 256D;
					double maxZ = ((double)z + 16	) / 256D;

					tessellator.addTranslation(0.5f, 0.2f, 0.0f);

					tessellator.addVertexWithUV(i, 			j + 0.8, 	k + 0.94, 	minX, minZ);
					tessellator.addVertexWithUV(i + 0.5, 	j + 0.8, 	k + 0.94, 	maxX, minZ);
					tessellator.addVertexWithUV(i + 0.5, 	j + 0.3, 	k + 0.94, 	maxX, maxZ);
					tessellator.addVertexWithUV(i, 			j + 0.3, 	k + 0.94, 	minX, maxZ);

					tessellator.addTranslation(-0.5f, -0.2f, 0f);
				}
				if(te.storage[2] != null)
				{
					renderblocks.overrideBlockTexture = Item.itemsList[te.storage[2].itemID].getIconIndex(te.storage[2]);

					int x = (renderblocks.overrideBlockTexture & 0xf) << 4;
					int z = renderblocks.overrideBlockTexture & 0xf0;

					double minX = ((double)x + 16	) / 256D;
					double maxX = ((double)x + 0	) / 256D;
					double minZ = ((double)z + 0	) / 256D;
					double maxZ = ((double)z + 16	) / 256D;

					tessellator.addTranslation(0.0f, -0.2f, 0.0f);

					tessellator.addVertexWithUV(i, 			j + 0.8, 	k + 0.94, 	minX, minZ);
					tessellator.addVertexWithUV(i + 0.5, 	j + 0.8, 	k + 0.94, 	maxX, minZ);
					tessellator.addVertexWithUV(i + 0.5, 	j + 0.3, 	k + 0.94, 	maxX, maxZ);
					tessellator.addVertexWithUV(i, 			j + 0.3, 	k + 0.94, 	minX, maxZ);

					tessellator.addTranslation(-0.0f, 0.2f, 0f);
				}
				if(te.storage[3] != null)
				{
					renderblocks.overrideBlockTexture = Item.itemsList[te.storage[3].itemID].getIconIndex(te.storage[3]);

					int x = (renderblocks.overrideBlockTexture & 0xf) << 4;
					int z = renderblocks.overrideBlockTexture & 0xf0;

					double minX = ((double)x + 16	) / 256D;
					double maxX = ((double)x + 0	) / 256D;
					double minZ = ((double)z + 0	) / 256D;
					double maxZ = ((double)z + 16	) / 256D;

					tessellator.addTranslation(0.5f, -0.2f, 0.0f);

					tessellator.addVertexWithUV(i, 			j + 0.8, 	k + 0.94, 	minX, minZ);
					tessellator.addVertexWithUV(i + 0.5, 	j + 0.8, 	k + 0.94, 	maxX, minZ);
					tessellator.addVertexWithUV(i + 0.5, 	j + 0.3, 	k + 0.94, 	maxX, maxZ);
					tessellator.addVertexWithUV(i, 			j + 0.3, 	k + 0.94, 	minX, maxZ);

					tessellator.addTranslation(-0.5f, 0.2f, 0f);
				}
			}
			else if(dir == 1)
			{
				renderRackDir1(block, i, j, k, renderblocks, 0.7f);

				renderRackDir1(block, i, j, k, renderblocks, 0.3f);
				
				ForgeHooksClient.bindTexture("/bioxx/terratools.png", ModLoader.getMinecraftInstance().renderEngine.getTexture("/bioxx/terratools.png"));
				Tessellator tessellator = Tessellator.instance;
				tessellator.setBrightness(block.getMixedBrightnessForBlock(blockAccess, i, j, k));
				tessellator.setColorRGBA_F(1, 1, 1, 1);
				if(te.storage[0] != null)
				{
					renderblocks.overrideBlockTexture = Item.itemsList[te.storage[0].itemID].getIconIndex(te.storage[0]);

					int x = (renderblocks.overrideBlockTexture & 0xf) << 4;
					int z = renderblocks.overrideBlockTexture & 0xf0;

					double minX = ((double)x + 16	) / 256D;
					double maxX = ((double)x + 0	) / 256D;
					double minZ = ((double)z + 0	) / 256D;
					double maxZ = ((double)z + 16	) / 256D;

					tessellator.addTranslation(0f, 0.2f, 0.0f);
					
					tessellator.addVertexWithUV(i + 0.06, 	j + 0.8, 	k, 			minX, minZ);
					tessellator.addVertexWithUV(i + 0.06, 	j + 0.8, 	k + 0.5, 	maxX, minZ);
					tessellator.addVertexWithUV(i + 0.06, 	j + 0.3, 	k + 0.5, 	maxX, maxZ);
					tessellator.addVertexWithUV(i + 0.06, 	j + 0.3, 	k, 			minX, maxZ);
					
					tessellator.addTranslation(0f, -0.2f, 0f);
				}
				if(te.storage[1] != null)
				{
					renderblocks.overrideBlockTexture = Item.itemsList[te.storage[1].itemID].getIconIndex(te.storage[1]);

					int x = (renderblocks.overrideBlockTexture & 0xf) << 4;
					int z = renderblocks.overrideBlockTexture & 0xf0;

					double minX = ((double)x + 16	) / 256D;
					double maxX = ((double)x + 0	) / 256D;
					double minZ = ((double)z + 0	) / 256D;
					double maxZ = ((double)z + 16	) / 256D;

					tessellator.addTranslation(0.0f, 0.2f, 0.5f);

					tessellator.addVertexWithUV(i + 0.06, 	j + 0.8, 	k, 			minX, minZ);
					tessellator.addVertexWithUV(i + 0.06, 	j + 0.8, 	k + 0.5, 	maxX, minZ);
					tessellator.addVertexWithUV(i + 0.06, 	j + 0.3, 	k + 0.5, 	maxX, maxZ);
					tessellator.addVertexWithUV(i + 0.06, 	j + 0.3, 	k, 			minX, maxZ);

					tessellator.addTranslation(0.0f, -0.2f, -0.5f);
				}
				if(te.storage[2] != null)
				{
					renderblocks.overrideBlockTexture = Item.itemsList[te.storage[2].itemID].getIconIndex(te.storage[2]);

					int x = (renderblocks.overrideBlockTexture & 0xf) << 4;
					int z = renderblocks.overrideBlockTexture & 0xf0;

					double minX = ((double)x + 16	) / 256D;
					double maxX = ((double)x + 0	) / 256D;
					double minZ = ((double)z + 0	) / 256D;
					double maxZ = ((double)z + 16	) / 256D;

					tessellator.addTranslation(0.0f, -0.2f, 0.0f);

					tessellator.addVertexWithUV(i + 0.06, 	j + 0.8, 	k, 			minX, minZ);
					tessellator.addVertexWithUV(i + 0.06, 	j + 0.8, 	k + 0.5, 	maxX, minZ);
					tessellator.addVertexWithUV(i + 0.06, 	j + 0.3, 	k + 0.5, 	maxX, maxZ);
					tessellator.addVertexWithUV(i + 0.06, 	j + 0.3, 	k, 			minX, maxZ);

					tessellator.addTranslation(-0.0f, 0.2f, 0f);
				}
				if(te.storage[3] != null)
				{
					renderblocks.overrideBlockTexture = Item.itemsList[te.storage[3].itemID].getIconIndex(te.storage[3]);

					int x = (renderblocks.overrideBlockTexture & 0xf) << 4;
					int z = renderblocks.overrideBlockTexture & 0xf0;

					double minX = ((double)x + 16	) / 256D;
					double maxX = ((double)x + 0	) / 256D;
					double minZ = ((double)z + 0	) / 256D;
					double maxZ = ((double)z + 16	) / 256D;

					tessellator.addTranslation(0.0f, -0.2f, 0.5f);

					tessellator.addVertexWithUV(i + 0.06, 	j + 0.8, 	k, 			minX, minZ);
					tessellator.addVertexWithUV(i + 0.06, 	j + 0.8, 	k + 0.5, 	maxX, minZ);
					tessellator.addVertexWithUV(i + 0.06, 	j + 0.3, 	k + 0.5, 	maxX, maxZ);
					tessellator.addVertexWithUV(i + 0.06, 	j + 0.3, 	k, 			minX, maxZ);

					tessellator.addTranslation(0.0f, 0.2f, -0.5f);
				}
			}
			else if(dir == 2)
			{
				renderRackDir2(block, i, j, k, renderblocks, 0.7f);

				renderRackDir2(block, i, j, k, renderblocks, 0.3f);

				ForgeHooksClient.bindTexture("/bioxx/terratools.png", ModLoader.getMinecraftInstance().renderEngine.getTexture("/bioxx/terratools.png"));
				Tessellator tessellator = Tessellator.instance;
				tessellator.setBrightness(block.getMixedBrightnessForBlock(blockAccess, i, j, k));
				tessellator.setColorRGBA_F(1, 1, 1, 1);
				if(te.storage[0] != null)
				{
					renderblocks.overrideBlockTexture = Item.itemsList[te.storage[0].itemID].getIconIndex(te.storage[0]);

					int x = (renderblocks.overrideBlockTexture & 0xf) << 4;
					int z = renderblocks.overrideBlockTexture & 0xf0;

					double minX = ((double)x + 0	) / 256D;
					double maxX = ((double)x + 16	) / 256D;
					double minZ = ((double)z + 0	) / 256D;
					double maxZ = ((double)z + 16	) / 256D;

					tessellator.addTranslation(0f, 0.2f, 0.0f);

					tessellator.addVertexWithUV(i, 			j + 0.3, 	k + 0.06, 	minX, maxZ);
					tessellator.addVertexWithUV(i + 0.5, 	j + 0.3, 	k + 0.06, 	maxX, maxZ);
					tessellator.addVertexWithUV(i + 0.5, 	j + 0.8, 	k + 0.06, 	maxX, minZ);
					tessellator.addVertexWithUV(i, 			j + 0.8, 	k + 0.06, 	minX, minZ);

					tessellator.addTranslation(0f, -0.2f, 0f);
				}

				if(te.storage[1] != null)
				{
					renderblocks.overrideBlockTexture = Item.itemsList[te.storage[1].itemID].getIconIndex(te.storage[1]);

					int x = (renderblocks.overrideBlockTexture & 0xf) << 4;
					int z = renderblocks.overrideBlockTexture & 0xf0;

					double minX = ((double)x + 0	) / 256D;
					double maxX = ((double)x + 16	) / 256D;
					double minZ = ((double)z + 0	) / 256D;
					double maxZ = ((double)z + 16	) / 256D;

					tessellator.addTranslation(0.5f, 0.2f, 0.0f);

					tessellator.addVertexWithUV(i, 			j + 0.3, 	k + 0.06, 	minX, maxZ);
					tessellator.addVertexWithUV(i + 0.5, 	j + 0.3, 	k + 0.06, 	maxX, maxZ);
					tessellator.addVertexWithUV(i + 0.5, 	j + 0.8, 	k + 0.06, 	maxX, minZ);
					tessellator.addVertexWithUV(i, 			j + 0.8, 	k + 0.06, 	minX, minZ);

					tessellator.addTranslation(-0.5f, -0.2f, 0f);
				}
				
				if(te.storage[2] != null)
				{
					renderblocks.overrideBlockTexture = Item.itemsList[te.storage[2].itemID].getIconIndex(te.storage[2]);

					int x = (renderblocks.overrideBlockTexture & 0xf) << 4;
					int z = renderblocks.overrideBlockTexture & 0xf0;

					double minX = ((double)x + 0	) / 256D;
					double maxX = ((double)x + 16	) / 256D;
					double minZ = ((double)z + 0	) / 256D;
					double maxZ = ((double)z + 16	) / 256D;

					tessellator.addTranslation(0.0f, -0.2f, 0.0f);

					tessellator.addVertexWithUV(i, 			j + 0.3, 	k + 0.08, 	minX, maxZ);
					tessellator.addVertexWithUV(i + 0.5, 	j + 0.3, 	k + 0.08, 	maxX, maxZ);
					tessellator.addVertexWithUV(i + 0.5, 	j + 0.8, 	k + 0.08, 	maxX, minZ);
					tessellator.addVertexWithUV(i, 			j + 0.8, 	k + 0.08, 	minX, minZ);

					tessellator.addTranslation(-0.0f, 0.2f, 0f);
				}
				
				if(te.storage[3] != null)
				{
					renderblocks.overrideBlockTexture = Item.itemsList[te.storage[3].itemID].getIconIndex(te.storage[3]);

					int x = (renderblocks.overrideBlockTexture & 0xf) << 4;
					int z = renderblocks.overrideBlockTexture & 0xf0;

					double minX = ((double)x + 0	) / 256D;
					double maxX = ((double)x + 16	) / 256D;
					double minZ = ((double)z + 0	) / 256D;
					double maxZ = ((double)z + 16	) / 256D;

					tessellator.addTranslation(0.5f, -0.2f, 0.0f);

					tessellator.addVertexWithUV(i, 			j + 0.3, 	k + 0.08, 	minX, maxZ);
					tessellator.addVertexWithUV(i + 0.5, 	j + 0.3, 	k + 0.08, 	maxX, maxZ);
					tessellator.addVertexWithUV(i + 0.5, 	j + 0.8, 	k + 0.08, 	maxX, minZ);
					tessellator.addVertexWithUV(i, 			j + 0.8, 	k + 0.08, 	minX, minZ);

					tessellator.addTranslation(-0.5f, 0.2f, 0f);
				}
			}
			else if(dir == 3)
			{
				renderRackDir3(block, i, j, k, renderblocks, 0.7f);

				renderRackDir3(block, i, j, k, renderblocks, 0.3f);
				
				ForgeHooksClient.bindTexture("/bioxx/terratools.png", ModLoader.getMinecraftInstance().renderEngine.getTexture("/bioxx/terratools.png"));
				Tessellator tessellator = Tessellator.instance;
				tessellator.setBrightness(block.getMixedBrightnessForBlock(blockAccess, i, j, k));
				tessellator.setColorRGBA_F(1, 1, 1, 1);
				if(te.storage[0] != null)
				{
					renderblocks.overrideBlockTexture = Item.itemsList[te.storage[0].itemID].getIconIndex(te.storage[0]);

					int x = (renderblocks.overrideBlockTexture & 0xf) << 4;
					int z = renderblocks.overrideBlockTexture & 0xf0;

					double minX = ((double)x + 0	) / 256D;
					double maxX = ((double)x + 16	) / 256D;
					double minZ = ((double)z + 0	) / 256D;
					double maxZ = ((double)z + 16	) / 256D;

					tessellator.addTranslation(0f, 0.2f, 0.0f);
					
					tessellator.addVertexWithUV(i + 0.92, 	j + 0.3, 	k, 			minX, maxZ);
					tessellator.addVertexWithUV(i + 0.92, 	j + 0.3, 	k + 0.5, 	maxX, maxZ);
					tessellator.addVertexWithUV(i + 0.92, 	j + 0.8, 	k + 0.5, 	maxX, minZ);
					tessellator.addVertexWithUV(i + 0.92, 	j + 0.8, 	k, 			minX, minZ);
					
					tessellator.addTranslation(0f, -0.2f, 0f);
				}
				if(te.storage[1] != null)
				{
					renderblocks.overrideBlockTexture = Item.itemsList[te.storage[1].itemID].getIconIndex(te.storage[1]);

					int x = (renderblocks.overrideBlockTexture & 0xf) << 4;
					int z = renderblocks.overrideBlockTexture & 0xf0;

					double minX = ((double)x + 0	) / 256D;
					double maxX = ((double)x + 16	) / 256D;
					double minZ = ((double)z + 0	) / 256D;
					double maxZ = ((double)z + 16	) / 256D;

					tessellator.addTranslation(0.0f, 0.2f, 0.5f);

					tessellator.addVertexWithUV(i + 0.92, 	j + 0.3, 	k, 			minX, maxZ);
					tessellator.addVertexWithUV(i + 0.92, 	j + 0.3, 	k + 0.5, 	maxX, maxZ);
					tessellator.addVertexWithUV(i + 0.92, 	j + 0.8, 	k + 0.5, 	maxX, minZ);
					tessellator.addVertexWithUV(i + 0.92, 	j + 0.8, 	k, 			minX, minZ);

					tessellator.addTranslation(0.0f, -0.2f, -0.5f);
				}
				if(te.storage[2] != null)
				{
					renderblocks.overrideBlockTexture = Item.itemsList[te.storage[2].itemID].getIconIndex(te.storage[2]);

					int x = (renderblocks.overrideBlockTexture & 0xf) << 4;
					int z = renderblocks.overrideBlockTexture & 0xf0;

					double minX = ((double)x + 0	) / 256D;
					double maxX = ((double)x + 16	) / 256D;
					double minZ = ((double)z + 0	) / 256D;
					double maxZ = ((double)z + 16	) / 256D;

					tessellator.addTranslation(0.0f, -0.2f, 0.0f);

					tessellator.addVertexWithUV(i + 0.92, 	j + 0.3, 	k, 			minX, maxZ);
					tessellator.addVertexWithUV(i + 0.92, 	j + 0.3, 	k + 0.5, 	maxX, maxZ);
					tessellator.addVertexWithUV(i + 0.92, 	j + 0.8, 	k + 0.5, 	maxX, minZ);
					tessellator.addVertexWithUV(i + 0.92, 	j + 0.8, 	k, 			minX, minZ);

					tessellator.addTranslation(-0.0f, 0.2f, 0f);
				}
				if(te.storage[3] != null)
				{
					renderblocks.overrideBlockTexture = Item.itemsList[te.storage[3].itemID].getIconIndex(te.storage[3]);

					int x = (renderblocks.overrideBlockTexture & 0xf) << 4;
					int z = renderblocks.overrideBlockTexture & 0xf0;

					double minX = ((double)x + 0	) / 256D;
					double maxX = ((double)x + 16	) / 256D;
					double minZ = ((double)z + 0	) / 256D;
					double maxZ = ((double)z + 16	) / 256D;

					tessellator.addTranslation(0f, -0.2f, 0.5f);

					tessellator.addVertexWithUV(i + 0.92, 	j + 0.3, 	k, 			minX, maxZ);
					tessellator.addVertexWithUV(i + 0.92, 	j + 0.3, 	k + 0.5, 	maxX, maxZ);
					tessellator.addVertexWithUV(i + 0.92, 	j + 0.8, 	k + 0.5, 	maxX, minZ);
					tessellator.addVertexWithUV(i + 0.92, 	j + 0.8, 	k, 			minX, minZ);

					tessellator.addTranslation(0f, 0.2f, -0.5f);
				}
			}
		}

		renderblocks.clearOverrideBlockTexture();
		return true;	
	}
	
	private static void renderRackDir0(Block block, int i, int j, int k, RenderBlocks renderblocks, float yOffset) 
	{
		renderblocks.setRenderMinMax(0.0F, yOffset, 0.95F, 1F, yOffset + 0.05F, 1F);
		renderblocks.renderStandardBlock(block, i, j, k);

		renderblocks.setRenderMinMax(0.2F, yOffset, 0.9F, 0.22F, yOffset + 0.05F, 0.95F);
		renderblocks.renderStandardBlock(block, i, j, k);
		renderblocks.setRenderMinMax(0.3F, yOffset, 0.9F, 0.32F, yOffset + 0.05F, 0.95F);
		renderblocks.renderStandardBlock(block, i, j, k);

		renderblocks.setRenderMinMax(0.68F, yOffset, 0.9F, 0.70F, yOffset + 0.05F, 0.95F);
		renderblocks.renderStandardBlock(block, i, j, k);
		renderblocks.setRenderMinMax(0.78F, yOffset, 0.9F, 0.8F, yOffset + 0.05F, 0.95F);
		renderblocks.renderStandardBlock(block, i, j, k);
	}
	
	private static void renderRackDir1(Block block, int i, int j, int k, RenderBlocks renderblocks, float yOffset) 
	{
		renderblocks.setRenderMinMax(0.0F, yOffset, 0.0F, 0.05F, yOffset + 0.05F, 1F);
		renderblocks.renderStandardBlock(block, i, j, k);

		renderblocks.setRenderMinMax(0.05F, yOffset, 0.2F, 0.1F, yOffset + 0.05F, 0.22F);
		renderblocks.renderStandardBlock(block, i, j, k);
		renderblocks.setRenderMinMax(0.05F, yOffset, 0.3F, 0.1F, yOffset + 0.05F, 0.32F);
		renderblocks.renderStandardBlock(block, i, j, k);

		renderblocks.setRenderMinMax(0.05F, yOffset, 0.68F, 0.1F, yOffset + 0.05F, 0.7F);
		renderblocks.renderStandardBlock(block, i, j, k);
		renderblocks.setRenderMinMax(0.05F, yOffset, 0.78F, 0.1F, yOffset + 0.05F, 0.8F);
		renderblocks.renderStandardBlock(block, i, j, k);
	}

	private static void renderRackDir2(Block block, int i, int j, int k, RenderBlocks renderblocks, float yOffset) 
	{
		renderblocks.setRenderMinMax(0.0F, yOffset, 0.0F, 1F, yOffset + 0.05F, 0.05F);
		renderblocks.renderStandardBlock(block, i, j, k);

		renderblocks.setRenderMinMax(0.2F, yOffset, 0.05F, 0.22F, yOffset + 0.05F, 0.1F);
		renderblocks.renderStandardBlock(block, i, j, k);
		renderblocks.setRenderMinMax(0.3F, yOffset, 0.05F, 0.32F, yOffset + 0.05F, 0.1F);
		renderblocks.renderStandardBlock(block, i, j, k);

		renderblocks.setRenderMinMax(0.68F, yOffset, 0.05F, 0.70F, yOffset + 0.05F, 0.1F);
		renderblocks.renderStandardBlock(block, i, j, k);
		renderblocks.setRenderMinMax(0.78F, yOffset, 0.05F, 0.8F, yOffset + 0.05F, 0.1F);
		renderblocks.renderStandardBlock(block, i, j, k);
	}
	
	private static void renderRackDir3(Block block, int i, int j, int k, RenderBlocks renderblocks, float yOffset) 
	{
		renderblocks.setRenderMinMax(0.95F, 		yOffset, 	0.0F, 	1F, 	yOffset + 0.05F, 	1F);
		renderblocks.renderStandardBlock(block, i, j, k);

		renderblocks.setRenderMinMax(0.9F, 	yOffset, 	0.2F, 	0.95F, 	yOffset + 0.05F, 	0.22F);
		renderblocks.renderStandardBlock(block, i, j, k);
		renderblocks.setRenderMinMax(0.9F, 	yOffset, 	0.3F, 	0.95F, 	yOffset + 0.05F, 	0.32F);
		renderblocks.renderStandardBlock(block, i, j, k);

		renderblocks.setRenderMinMax(0.9F, 	yOffset, 	0.68F, 	0.95F, 	yOffset + 0.05F, 	0.7F);
		renderblocks.renderStandardBlock(block, i, j, k);
		renderblocks.setRenderMinMax(0.9F, 	yOffset, 	0.78F, 	0.95F, 	yOffset + 0.05F, 	0.8F);
		renderblocks.renderStandardBlock(block, i, j, k);
	}
}
