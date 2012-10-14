package TFC.Render.Blocks;

import net.minecraft.src.Block;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Item;
import net.minecraft.src.ModLoader;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.Tessellator;
import net.minecraftforge.client.ForgeHooksClient;
import TFC.Blocks.BlockAnvil;
import TFC.Chunkdata.ChunkDataManager;
import TFC.Core.AnvilReq;
import TFC.Core.TFC_Time;
import TFC.Render.TFC_CoreRender;
import TFC.TileEntities.TileEntityTerraAnvil;

public class RenderSpawnMeter {

	public static boolean render(Block block, int i, int j, int k, RenderBlocks renderblocks)
	{
		IBlockAccess blockAccess = renderblocks.blockAccess;

		//Render Top
		block.setBlockBounds(0.0F, 0.75F, 0.0F, 1.0F, 1.0F, 1.0F);
		renderblocks.renderStandardBlock(block, i, j, k);
		//Render Base
		block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.25F, 1.0F);
		renderblocks.renderStandardBlock(block, i, j, k);	
		
		//Render Core
		block.setBlockBounds(0.0625F, 0F, 0.0625F, 0.9375F, 1F, 0.9375F);
		renderblocks.renderStandardBlock(block, i, j, k);

		block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);

		return true;
	}

	public static void renderItem(Block block, int meta, RenderBlocks renderer)
	{
		//Render Top
		block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F);
		renderStandardBlockWithColorMultiplier(block, 0, 0, 0, 255, 255, 255, renderer);
		//Render Base
		block.setBlockBounds(0.0F, 0.9375F, 0.0F, 1.0F, 1.0F, 1.0F);
		renderStandardBlockWithColorMultiplier(block, 0, 0, 0, 255, 255, 255, renderer);
		//Render Core
		block.setBlockBounds(0.0625F, 0.0F, 0.0F, 1.0F, 0.9375F, 1.0F);
		renderStandardBlockWithColorMultiplier(block, 0, 0, 0, 255, 255, 255, renderer);
	}
	
	public static boolean renderStandardBlockWithColorMultiplier(Block par1Block, int par2, int par3, int par4, float par5, float par6, float par7, RenderBlocks renderer)
    {
    	boolean renderAllFaces = true;
    	
        Tessellator var8 = Tessellator.instance;
        boolean var9 = false;
        float var10 = 0.5F;
        float var11 = 1.0F;
        float var12 = 0.8F;
        float var13 = 0.6F;
        float var14 = var11 * par5;
        float var15 = var11 * par6;
        float var16 = var11 * par7;
        float var17 = var10;
        float var18 = var12;
        float var19 = var13;
        float var20 = var10;
        float var21 = var12;
        float var22 = var13;
        float var23 = var10;
        float var24 = var12;
        float var25 = var13;

        if (par1Block != Block.grass)
        {
            var17 = var10 * par5;
            var18 = var12 * par5;
            var19 = var13 * par5;
            var20 = var10 * par6;
            var21 = var12 * par6;
            var22 = var13 * par6;
            var23 = var10 * par7;
            var24 = var12 * par7;
            var25 = var13 * par7;
        }

        int var26 = 1;

        if (renderAllFaces)
        {
            var8.setBrightness(1);
            var8.setColorOpaque_F(var17, var20, var23);
            TFC_CoreRender.renderBottomFace(par1Block, (double)par2, (double)par3, (double)par4, par1Block.getBlockTextureFromSideAndMetadata(0, 0));
            var9 = true;
        }

        if (renderAllFaces)
        {
        	var8.setBrightness(1);
            var8.setColorOpaque_F(var14, var15, var16);
            TFC_CoreRender.renderTopFace(par1Block, (double)par2, (double)par3, (double)par4, par1Block.getBlockTextureFromSideAndMetadata(1, 0));
            var9 = true;
        }

        int var28;

        if (renderAllFaces)
        {
        	var8.setBrightness(1);
            var8.setColorOpaque_F(var18, var21, var24);
            var28 = par1Block.getBlockTextureFromSideAndMetadata(2, 0);
            TFC_CoreRender.renderEastFace(par1Block, (double)par2, (double)par3, (double)par4, var28);

            var9 = true;
        }

        if (renderAllFaces)
        {
        	var8.setBrightness(1);
            var8.setColorOpaque_F(var18, var21, var24);
            var28 = par1Block.getBlockTextureFromSideAndMetadata(3, 0);
            TFC_CoreRender.renderWestFace(par1Block, (double)par2, (double)par3, (double)par4, var28);

            var9 = true;
        }

        if (renderAllFaces)
        {
        	var8.setBrightness(1);
            var8.setColorOpaque_F(var19, var22, var25);
            var28 = par1Block.getBlockTextureFromSideAndMetadata(4, 0);
            TFC_CoreRender.renderNorthFace(par1Block, (double)par2, (double)par3, (double)par4, var28);

            var9 = true;
        }

        if (renderAllFaces)
        {
        	var8.setBrightness(1);
            var8.setColorOpaque_F(var19, var22, var25);
            var28 = par1Block.getBlockTextureFromSideAndMetadata(5, 0);
            TFC_CoreRender.renderSouthFace(par1Block, (double)par2, (double)par3, (double)par4, var28);

            var9 = true;
        }

        return var9;
    }
}
