package TFC.Render.Blocks;

import org.lwjgl.opengl.GL11;

import TFC.Blocks.BlockFoodPrep;
import TFC.Blocks.BlockSlab;
import TFC.Blocks.BlockToolRack;
import TFC.TileEntities.TileEntityFoodPrep;
import TFC.TileEntities.TileEntityPartial;
import TFC.TileEntities.TileEntityTerraAnvil;
import TFC.TileEntities.TileEntityToolRack;
import net.minecraft.src.Block;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Item;
import net.minecraft.src.ModLoader;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.TFCBlocks;
import net.minecraft.src.Tessellator;
import net.minecraftforge.client.ForgeHooksClient;

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
			double height = 0.05;
			int id = blockAccess.getBlockId(i, j, k);
			if(id == TFCBlocks.stoneSlabs.blockID)
			{

				height -= BlockSlab.getTopChiselLevel(((TileEntityPartial)blockAccess.getBlockTileEntity(i, j, k)).extraData)*0.1;
				//this.setBlockBounds(0F, -0.1F-(h*0.1F), 0F, 1F, 0F-(h*0.1F), 1F);
			}
			
			//First we render the rack itself.
			if(dir == 0)
			{
				ForgeHooksClient.bindTexture("/bioxx/FoodSprites.png", ModLoader.getMinecraftInstance().renderEngine.getTexture("/bioxx/FoodSprites.png"));
				Tessellator tessellator = Tessellator.instance;
				if(te.storage[0] != null)
				{
					renderblocks.overrideBlockTexture = Item.itemsList[te.storage[0].itemID].getIconIndex(te.storage[0]);

					int x = (renderblocks.overrideBlockTexture & 0xf) << 4;
					int z = renderblocks.overrideBlockTexture & 0xf0;

					double minX = ((double)x + 16	) / 256D;
					double maxX = ((double)x + 0	) / 256D;
					double minZ = ((double)z + 0	) / 256D;
					double maxZ = ((double)z + 16	) / 256D;

					tessellator.addTranslation(0f, 0.0f, 0.0f);

					tessellator.addVertexWithUV(i + 0.05, 	j + height, 	k + 0.05, 	minX, minZ);
					tessellator.addVertexWithUV(i + 0.05, 	j + height, 	k + 0.45, 	maxX, minZ);
					tessellator.addVertexWithUV(i + 0.45, 	j + height, 	k + 0.45, 	maxX, maxZ);
					tessellator.addVertexWithUV(i + 0.45, 	j + height, 	k + 0.05, 	minX, maxZ);

					tessellator.addTranslation(0f, 0.0f, 0f);
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

					tessellator.addTranslation(0f, 0.0f, 0.5f);

					tessellator.addVertexWithUV(i + 0.05, 	j + height, 	k + 0.05, 	minX, minZ);
					tessellator.addVertexWithUV(i + 0.05, 	j + height, 	k + 0.45, 	maxX, minZ);
					tessellator.addVertexWithUV(i + 0.45, 	j + height, 	k + 0.45, 	maxX, maxZ);
					tessellator.addVertexWithUV(i + 0.45, 	j + height, 	k + 0.05, 	minX, maxZ);

					tessellator.addTranslation(0f, 0.0f, -0.5f);;
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

					tessellator.addTranslation(0.5f, 0.0f, 0.0f);

					tessellator.addVertexWithUV(i + 0.05, 	j + height, 	k + 0.05, 	minX, minZ);
					tessellator.addVertexWithUV(i + 0.05, 	j + height, 	k + 0.45, 	maxX, minZ);
					tessellator.addVertexWithUV(i + 0.45, 	j + height, 	k + 0.45, 	maxX, maxZ);
					tessellator.addVertexWithUV(i + 0.45, 	j + height, 	k + 0.05, 	minX, maxZ);

					tessellator.addTranslation(-0.5f, 0.0f, 0f);
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

					tessellator.addTranslation(0.5f, 0.0f, 0.5f);

					tessellator.addVertexWithUV(i + 0.05, 	j + height, 	k + 0.05, 	minX, minZ);
					tessellator.addVertexWithUV(i + 0.05, 	j + height, 	k + 0.45, 	maxX, minZ);
					tessellator.addVertexWithUV(i + 0.45, 	j + height, 	k + 0.45, 	maxX, maxZ);
					tessellator.addVertexWithUV(i + 0.45, 	j + height, 	k + 0.05, 	minX, maxZ);

					tessellator.addTranslation(-0.5f, 0.0f, -0.5f);
				}
			}

			renderblocks.clearOverrideBlockTexture();

		}
		return true;
	}
}
