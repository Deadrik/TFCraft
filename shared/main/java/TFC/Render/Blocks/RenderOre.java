package TFC.Render.Blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.src.ModLoader;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import TFC.API.TFCOptions;
import TFC.Core.TFC_Textures;
import TFC.WorldGen.DataLayer;
import TFC.WorldGen.TFCWorldChunkManager;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderOre implements ISimpleBlockRenderingHandler
{
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId,
			RenderBlocks renderer) 
	{

	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) 
	{
		boolean breaking = false;
		if(renderer.overrideBlockTexture != null)
		{
			breaking = true;
		}

		if(!breaking)
		{
			//render the background rock
			renderer.overrideBlockTexture = getRockTexture(ModLoader.getMinecraftInstance().theWorld, x, y, z);
			renderer.renderStandardBlock(block, x, y, z);
			renderer.clearOverrideBlockTexture();

			//render the ore overlay
			renderer.renderStandardBlock(block, x, y, z);
		}

		//renderblocks.renderStandardBlock(block, xCoord, yCoord, zCoord);

		return false;
	}

	public static Icon getRockTexture(World worldObj, int xCoord, int yCoord, int zCoord) 
	{
		Icon var27 = null;
		DataLayer rockLayer1 = ((TFCWorldChunkManager)worldObj.getWorldChunkManager()).getRockLayerAt(xCoord, zCoord, 0);
		DataLayer rockLayer2 = ((TFCWorldChunkManager)worldObj.getWorldChunkManager()).getRockLayerAt(xCoord, zCoord, 1);
		DataLayer rockLayer3 = ((TFCWorldChunkManager)worldObj.getWorldChunkManager()).getRockLayerAt(xCoord, zCoord, 2);

		try
		{
			if(yCoord <= TFCOptions.RockLayer3Height)
			{
				var27 = Block.blocksList[rockLayer3.data1].getIcon(5, rockLayer3.data2);
			} else if(yCoord <= TFCOptions.RockLayer2Height)
			{
				var27 = Block.blocksList[rockLayer2.data1].getIcon(5, rockLayer2.data2);
			} else
			{
				var27 = Block.blocksList[rockLayer1.data1].getIcon(5, rockLayer1.data2);
			}
		}
		catch(Exception ex)
		{
			System.out.println("Ore getRockTexture crash! " +
					"rock1: " + rockLayer1.data1 + "/" + rockLayer1.data2 +
					"rock2: " + rockLayer2.data1 + "/" + rockLayer2.data2 +
					"rock3: " + rockLayer3.data1 + "/" + rockLayer3.data2);
			var27 = TFC_Textures.InvisibleTexture;
		}
		return var27;
	}

	@Override
	public boolean shouldRender3DInInventory() {
		return false;
	}

	@Override
	public int getRenderId() {
		// TODO Auto-generated method stub
		return 0;
	}

	public static void renderInvBlock(Block block, int meta, RenderBlocks renderer)
	{
		Tessellator var14 = Tessellator.instance;
		var14.startDrawingQuads();
		var14.setNormal(0.0F, -1.0F, 0.0F);
		renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(0, meta));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(0.0F, 1.0F, 0.0F);
		renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(1, meta));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(0.0F, 0.0F, -1.0F);
		renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(2, meta));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(0.0F, 0.0F, 1.0F);
		renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(3, meta));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(-1.0F, 0.0F, 0.0F);
		renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(4, meta));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(1.0F, 0.0F, 0.0F);
		renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(5, meta));
		var14.draw();
	}
}

