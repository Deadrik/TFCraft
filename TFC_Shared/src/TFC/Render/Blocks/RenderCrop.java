package TFC.Render.Blocks;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.crash.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.packet.*;
import net.minecraft.pathfinding.*;
import net.minecraft.potion.*;
import net.minecraft.server.*;
import net.minecraft.src.ModLoader;
import net.minecraft.stats.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.village.*;
import net.minecraft.world.*;
import TFC.Food.CropIndex;
import TFC.Food.CropManager;
import TFC.TileEntities.TileEntityCrop;

public class RenderCrop 
{
	
	public static boolean render(Block block, int i, int j, int k, RenderBlocks renderblocks)
    {
        /*IBlockAccess blockaccess = renderblocks.blockAccess;
        TileEntityCrop te = (TileEntityCrop)blockaccess.getBlockTileEntity(i, j, k);
        CropIndex crop = CropManager.getInstance().getCropFromId(te.cropId);
        Minecraft mc = ModLoader.getMinecraftInstance();
        
        boolean breaking = false;
        if(renderblocks.overrideBlockTexture >= 240)
        {
        	breaking = true;
        }
        
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
        
        Tessellator var9 = Tessellator.instance;
        var9.setBrightness(block.getMixedBrightnessForBlock(blockaccess, i, j, k));
        
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
            case 19://Yellow Bell
            {                
                byte index = (byte) (137 + stage);
                drawCrossedSquares(block, i, j, k, index, mult, 1.0);
                break;
            }
            case 20://Red Bell
            {                
                byte index = (byte) (153 + stage);
                drawCrossedSquares(block, i, j, k, index, mult, 1.0);
                break;
            }
            case 21://Soybean
            {                
                byte index = (byte) (121 + stage);
                drawCrossedSquares(block, i, j, k, index, mult, 1.0);
                break;
            }
            case 22://Greenbean
            {                
                byte index = (byte) (105 + stage);
                drawCrossedSquares(block, i, j, k, index, mult, 1.0);
                break;
            }
            case 23://Squash
            {                
                byte index = (byte) (89 + stage);
                drawCrossedSquares(block, i, j, k, index, mult, 1.0);
                break;
            }
            default:
            {
                renderblocks.renderBlockCrops(block, i, j, k);
                break;
            }
        }
*/
        return true;
    }
	
	private static void renderBlockCropsImpl(Block par1Block, double par3, double par5, double par7, RenderBlocks renderblocks, byte index, float heightMult, double height)
    {
        Tessellator var9 = Tessellator.instance;
        GL11.glColor3f(1, 1, 1);
        int brightness = par1Block.getMixedBrightnessForBlock(renderblocks.blockAccess, (int)par3, (int)par5, (int)par7);
        var9.setBrightness(brightness);
        var9.setColorRGBA_F(1, 1, 1, 1);
        
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
        GL11.glColor3f(1, 1, 1);
        
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
