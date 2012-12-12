package TFC.Render.Blocks;

import org.lwjgl.opengl.GL11;

import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.Block;
import net.minecraft.src.ModLoader;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.Tessellator;
import net.minecraftforge.client.ForgeHooksClient;
import TFC.TFCBlocks;
import TFC.Render.TFC_CoreRender;
import TFC.TileEntities.TileEntityDetailed;
import TFC.TileEntities.TileEntityPartial;

public class RenderDetailed 
{
	public static boolean renderBlockDetailed(Block block, int i, int j, int k, RenderBlocks renderblocks)
    {
        TileEntityDetailed te = (TileEntityDetailed) renderblocks.blockAccess.getBlockTileEntity(i, j, k);
        int md = renderblocks.blockAccess.getBlockMetadata(i, j, k);

        if(te.TypeID <= 0) return false;

        int type = te.TypeID;
        int meta = te.MetaID;

        int over = renderblocks.overrideBlockTexture;
        ForgeHooksClient.bindTexture(Block.blocksList[type].getTextureFile(), ModLoader.getMinecraftInstance().renderEngine.getTexture(Block.blocksList[type].getTextureFile()));


        for(int subX = 0; subX < 8; subX++)
        {
        	for(int subZ = 0; subZ < 8; subZ++)
            {
        		for(int subY = 0; subY < 8; subY++)
                {
        			boolean subExists = isOpaque(te,subX, subY, subZ);
                	if(subExists)
                	{
                		float minX = 0.125f*subX;
                		float maxX = minX + 0.125f;
                		float minY = 0.125f*subY;
                		float maxY = minY + 0.125f;
                		float minZ = 0.125f*subZ;
                		float maxZ = minZ + 0.125f;
                		
                		renderblocks.setRenderMinMax(minX, minY, minZ, maxX, maxY, maxZ);
                		renderStandardBlockWithColorMultiplier(Block.blocksList[type], renderblocks, i, j, k, 0.95f, 0.95f, 0.95f, meta);
                	}
                }
            }
        }

        renderblocks.overrideBlockTexture = over;

        return true;
    }
	
	public static boolean renderStandardBlockWithColorMultiplier(Block par1Block, RenderBlocks renderblocks, int par2, int par3, int par4, float par5, float par6, float par7, int meta)
    {
		renderblocks.enableAO = false;
        Tessellator var8 = Tessellator.instance;
        
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

        int var26 = par1Block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2, par3, par4);

            var8.setBrightness(renderblocks.field_83027_i > 0.0D ? var26 : par1Block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2, par3 - 1, par4));
            var8.setColorOpaque_F(var17, var20, var23);
            renderblocks.renderBottomFace(par1Block, (double)par2, (double)par3, (double)par4, par1Block.getBlockTextureFromSideAndMetadata(0, meta));


            var8.setBrightness(renderblocks.field_83024_j < 1.0D ? var26 : par1Block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2, par3 + 1, par4));
            var8.setColorOpaque_F(var14, var15, var16);
            renderblocks.renderTopFace(par1Block, (double)par2, (double)par3, (double)par4, par1Block.getBlockTextureFromSideAndMetadata(1, meta));


            int var28;

            var8.setBrightness(renderblocks.field_83025_k > 0.0D ? var26 : par1Block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2, par3, par4 - 1));
            var8.setColorOpaque_F(var18, var21, var24);
            var28 = par1Block.getBlockTextureFromSideAndMetadata(2, meta);
            
            renderblocks.renderEastFace(par1Block, (double)par2, (double)par3, (double)par4, var28);

            var8.setBrightness(renderblocks.field_83022_l < 1.0D ? var26 : par1Block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2, par3, par4 + 1));
            var8.setColorOpaque_F(var18, var21, var24);
            var28 = par1Block.getBlockTextureFromSideAndMetadata(3, meta);

            renderblocks.renderWestFace(par1Block, (double)par2, (double)par3, (double)par4, var28);


            var8.setBrightness(renderblocks.field_83021_g > 0.0D ? var26 : par1Block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2 - 1, par3, par4));
            var8.setColorOpaque_F(var19, var22, var25);
            var28 = par1Block.getBlockTextureFromSideAndMetadata(4, meta);

            renderblocks.renderNorthFace(par1Block, (double)par2, (double)par3, (double)par4, var28);

            
            var8.setBrightness(renderblocks.field_83026_h < 1.0D ? var26 : par1Block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2 + 1, par3, par4));
            var8.setColorOpaque_F(var19, var22, var25);
            var28 = par1Block.getBlockTextureFromSideAndMetadata(5, meta);

            renderblocks.renderSouthFace(par1Block, (double)par2, (double)par3, (double)par4, var28);

        return true;
    }
	
	public static boolean isOpaque(TileEntityDetailed te, int x, int y, int z)
	{
		return te.data.get((x * 8 + z)*8 + y);
	}

}
