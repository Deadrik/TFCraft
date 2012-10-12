package TFC.Render.Blocks;

import net.minecraft.client.Minecraft;
import net.minecraft.src.Block;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.ModLoader;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.Tessellator;
import TFC.Core.CropIndex;
import TFC.Core.CropManager;
import TFC.TileEntities.TileEntityCrop;

public class RenderCrop 
{
	
	public static boolean render(Block block, int i, int j, int k, RenderBlocks renderblocks)
    {
        IBlockAccess blockaccess = renderblocks.blockAccess;
        TileEntityCrop te = (TileEntityCrop)blockaccess.getBlockTileEntity(i, j, k);
        CropIndex crop = CropManager.getInstance().getCropFromId(te.cropId);
        Minecraft mc = ModLoader.getMinecraftInstance();
        
        int stage = (int) Math.floor(te.growth);
        if(stage > crop.numGrowthStages)
            stage = crop.numGrowthStages;
        
        int meta = blockaccess.getBlockMetadata(i, j, k);
        
        float est = te.getEstimatedGrowth(crop);
        float mult = 0.85f + (0.15f * (te.growth / est));
        if(mult > 1.15f) {mult = 1.15f;}
        
        if(renderblocks.overrideBlockTexture >= 0)
        {
        	renderblocks.clearOverrideBlockTexture();
        	mc.renderEngine.bindTexture(mc.renderEngine.getTexture(block.getTextureFile()));
        }
        
        switch(te.cropId)
        {
            case 0://Wheat
            case 1://Wild Wheat
            {
                byte index = (byte) (16 + stage);
                renderBlockCropsImpl(block, (double)i, (double)j, (double)k,renderblocks, index, mult, 1.0);
                break;
            }
            case 2://Corn
            case 3://Wild Corn
            {
                byte index = (byte) (96 + stage);
                drawCrossedSquares(block, i, j, k, index, mult, 2.0);
                break;
            }
            case 4://Tomato
            {                
                byte index = (byte) (240 + stage);
                drawCrossedSquares(block, i, j, k, index, 1.0f, 2.0);
                break;
            }
            case 5://Barley
            case 6://Wild Barley
            {
                byte index = (byte) (0 + stage);
                renderBlockCropsImpl(block, (double)i, (double)j, (double)k, renderblocks, index, mult, 1.0);
                break;
            }
            case 7://Rye
            case 8://Wild Rye
            {
                byte index = (byte) (32 + stage);
                renderBlockCropsImpl(block, (double)i, (double)j, (double)k, renderblocks, index, mult, 1.0);
                break;
            }
            case 9://Oat
            case 10://Wild Oat
            {
                byte index = (byte) (48 + stage);
                renderBlockCropsImpl(block, (double)i, (double)j, (double)k,renderblocks, index, mult, 1.0);
                break;
            }
            case 11://Rice
            case 12://Wild Rice
            {
                byte index = (byte) (64 + stage);
                renderBlockCropsImpl(block, (double)i, (double)j, (double)k,renderblocks, index, mult, 1.0);
                break;
            }
            case 13://Potato
            case 14://Wild Potato
            {                
                byte index = (byte) (112 + stage);
                drawCrossedSquares(block, i, j, k, index, mult, 1.0);
                break;
            }
            case 15://Onion
            {                
                byte index = (byte) (128 + stage);
                drawCrossedSquares(block, i, j, k, index, mult, 1.0);
                break;
            }
            case 16://Cabbage
            {                
                byte index = (byte) (144 + stage);
                drawCrossedSquares(block, i, j, k, index, mult, 1.0);
                break;
            }
            case 17://Garlic
            {                
                byte index = (byte) (213 + stage);
                drawCrossedSquares(block, i, j, k, index, mult, 1.0);
                break;
            }
            case 18://Carrots
            {                
                byte index = (byte) (208 + stage);
                drawCrossedSquares(block, i, j, k, index, mult, 1.0);
                break;
            }
            case 19://Sugarcane
            {                
                byte index = (byte) (218 + stage);
                drawCrossedSquares(block, i, j, k, index, mult, 1.0);
                break;
            }
            case 20://Hemp
            {                
                byte index = (byte) (192 + stage);
                drawCrossedSquares(block, i, j, k, index, mult, 3.0);
                break;
            }
            default:
            {
                renderblocks.renderBlockCrops(block, i, j, k);
                break;
            }
        }

        return true;
    }
	
	private static void renderBlockCropsImpl(Block par1Block, double par3, double par5, double par7, RenderBlocks renderblocks, byte index, float heightMult, double height)
    {
        Tessellator var9 = Tessellator.instance;

        int texX = (index & 15) << 4;
        int texY = (index+(16-(int)(16*height))) & 240;
        
        double var13 = (double)((float)texX / 256.0F);
        double var15 = (double)(((float)texX + 15.99F) / 256.0F);
        double var17 = (double)((float)texY / 256.0F);
        double var19 = (double)(((float)texY + 15.99F) / 256.0F);
        double var21 = par3 + 0.5D - 0.25D;
        double var23 = par3 + 0.5D + 0.25D;
        double var25 = par7 + 0.5D - 0.5D;
        double var27 = par7 + 0.5D + 0.5D;
        
        double y = par5;        
        
        var9.addVertexWithUV(var21, y + (height*heightMult), var25, var13, var17);
        var9.addVertexWithUV(var21, y + 0.0D, var25, var13, var19);
        var9.addVertexWithUV(var21, y + 0.0D, var27, var15, var19);
        var9.addVertexWithUV(var21, y + (height*heightMult), var27, var15, var17);
        var9.addVertexWithUV(var21, y + (height*heightMult), var27, var13, var17);
        var9.addVertexWithUV(var21, y + 0.0D, var27, var13, var19);
        var9.addVertexWithUV(var21, y + 0.0D, var25, var15, var19);
        var9.addVertexWithUV(var21, y + (height*heightMult), var25, var15, var17);
        var9.addVertexWithUV(var23, y + (height*heightMult), var27, var13, var17);
        var9.addVertexWithUV(var23, y + 0.0D, var27, var13, var19);
        var9.addVertexWithUV(var23, y + 0.0D, var25, var15, var19);
        var9.addVertexWithUV(var23, y + (height*heightMult), var25, var15, var17);
        var9.addVertexWithUV(var23, y + (height*heightMult), var25, var13, var17);
        var9.addVertexWithUV(var23, y + 0.0D, var25, var13, var19);
        var9.addVertexWithUV(var23, y + 0.0D, var27, var15, var19);
        var9.addVertexWithUV(var23, y + (height*heightMult), var27, var15, var17);
        var21 = par3 + 0.5D - 0.5D;
        var23 = par3 + 0.5D + 0.5D;
        var25 = par7 + 0.5D - 0.25D;
        var27 = par7 + 0.5D + 0.25D;
        var9.addVertexWithUV(var21, y + (height*heightMult), var25, var13, var17);
        var9.addVertexWithUV(var21, y + 0.0D, var25, var13, var19);
        var9.addVertexWithUV(var23, y + 0.0D, var25, var15, var19);
        var9.addVertexWithUV(var23, y + (height*heightMult), var25, var15, var17);
        var9.addVertexWithUV(var23, y + (height*heightMult), var25, var13, var17);
        var9.addVertexWithUV(var23, y + 0.0D, var25, var13, var19);
        var9.addVertexWithUV(var21, y + 0.0D, var25, var15, var19);
        var9.addVertexWithUV(var21, y + (height*heightMult), var25, var15, var17);
        var9.addVertexWithUV(var23, y + (height*heightMult), var27, var13, var17);
        var9.addVertexWithUV(var23, y + 0.0D, var27, var13, var19);
        var9.addVertexWithUV(var21, y + 0.0D, var27, var15, var19);
        var9.addVertexWithUV(var21, y + (height*heightMult), var27, var15, var17);
        var9.addVertexWithUV(var21, y + (height*heightMult), var27, var13, var17);
        var9.addVertexWithUV(var21, y + 0.0D, var27, var13, var19);
        var9.addVertexWithUV(var23, y + 0.0D, var27, var15, var19);
        var9.addVertexWithUV(var23, y + (height*heightMult), var27, var15, var17);
    }

    private static void drawCrossedSquares(Block par1Block, double par3, double par5, double par7, byte index, float heightMult, double height)
    {
        Tessellator var9 = Tessellator.instance;

        var9.setColorOpaque_F(1.0f, 1.0f, 1.0f);
        int texX = (index & 15) << 4;
        int texY = index & 240;
        int texY2 = (index+(16-(int)(16*height))) & 240;
        
        double minX = (double)((float)texX / 256.0F);
        double maxX = (double)(((float)texX + 15.99F) / 256.0F);
        double minY = (double)((float)texY2 / 256.0F);
        double maxY = (double)(((float)texY + 15.99F) / 256.0F);
        
        double var21 = par3 + 0.5D - 0.45D;
        double var23 = par3 + 0.5D + 0.45D;
        double var25 = par7 + 0.5D - 0.45D;
        double var27 = par7 + 0.5D + 0.45D;
        
        double y = par5;    
        
        var9.addVertexWithUV(var21, y + (height*heightMult), var25, minX, minY);
        var9.addVertexWithUV(var21, y + 0.0D, var25, minX, maxY);
        var9.addVertexWithUV(var23, y + 0.0D, var27, maxX, maxY);
        var9.addVertexWithUV(var23, y + (height*heightMult), var27, maxX, minY);
        
        var9.addVertexWithUV(var23, y + (height*heightMult), var27, minX, minY);
        var9.addVertexWithUV(var23, y + 0.0D, var27, minX, maxY);
        var9.addVertexWithUV(var21, y + 0.0D, var25, maxX, maxY);
        var9.addVertexWithUV(var21, y + (height*heightMult), var25, maxX, minY);
        
        var9.addVertexWithUV(var21, y + (height*heightMult), var27, minX, minY);
        var9.addVertexWithUV(var21, y + 0.0D, var27, minX, maxY);
        var9.addVertexWithUV(var23, y + 0.0D, var25, maxX, maxY);
        var9.addVertexWithUV(var23, y + (height*heightMult), var25, maxX, minY);
        
        var9.addVertexWithUV(var23, y + (height*heightMult), var25, minX, minY);
        var9.addVertexWithUV(var23, y + 0.0D, var25, minX, maxY);
        var9.addVertexWithUV(var21, y + 0.0D, var27, maxX, maxY);
        var9.addVertexWithUV(var21, y + (height*heightMult), var27, maxX, minY);
    }

}
