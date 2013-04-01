package TFC.Render.Blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import TFC.Food.CropIndex;
import TFC.Food.CropManager;
import TFC.TileEntities.TileEntityCrop;

public class RenderCrop 
{
	
	public static boolean render(Block block, int i, int j, int k, RenderBlocks renderblocks)
    {
		IBlockAccess blockaccess = renderblocks.blockAccess;
		TileEntityCrop te = (TileEntityCrop)blockaccess.getBlockTileEntity(i, j, k);
        CropIndex crop = CropManager.getInstance().getCropFromId(te.cropId);
        
        switch(te.cropId)
        {
        case 0://Wheat
        case 1://Wild Wheat
        {
            renderBlockCropsImpl(block, i, j, k, renderblocks, 0.5, 1.0);
            break;
        }
        case 2://Corn
        case 3://Wild Corn
        {
            renderBlockCropsImpl(block, i, j, k, renderblocks, 1.0, 2.0);
            break;
        }
        case 4://Tomatoes
        {
        	drawCrossedSquares(block, i, j, k, renderblocks, 0.9, 2.0);
            break;
        }
        case 5://Barley
        case 6://Wild Barley
        {
        	renderBlockCropsImpl(block, i, j, k, renderblocks, 0.5, 1.0);
            break;
        }
        case 7://Rye
        case 8://Wild Rye
        {
        	renderBlockCropsImpl(block, i, j, k, renderblocks, 0.5, 1.0);
            break;
        }
        case 9://Oat
        case 10://Wild Oat
        {
        	renderBlockCropsImpl(block, i, j, k, renderblocks, 0.5, 1.0);
            break;
        }
        case 11://Rice
        case 12://Wild Rice
        {
        	renderBlockCropsImpl(block, i, j, k, renderblocks, 0.5, 1.0);
            break;
        }
        case 13://Potato
        case 14://Wild Potato
        {                
        	drawCrossedSquares(block, i, j, k, renderblocks, 0.45, 1.0);
            break;
        }
        case 15://Onion
        {                
        	drawCrossedSquares(block, i, j, k, renderblocks, 0.45, 1.0);
            break;
        }
        case 16://Cabbage
        {                
        	drawCrossedSquares(block, i, j, k, renderblocks, 0.45, 1.0);
            break;
        }
        case 17://Garlic
        {                
        	drawCrossedSquares(block, i, j, k, renderblocks, 0.45, 1.0);
            break;
        }
        case 18://Carrots
        {                
        	drawCrossedSquares(block, i, j, k, renderblocks, 0.45, 1.0);
            break;
        }
        case 19://Yellow Bell
        {                
        	drawCrossedSquares(block, i, j, k, renderblocks, 0.45, 1.0);
            break;
        }
        case 20://Red Bell
        {                
        	drawCrossedSquares(block, i, j, k, renderblocks, 0.45, 1.0);
            break;
        }
        case 21://Soybean
        {                
        	drawCrossedSquares(block, i, j, k, renderblocks, 0.45, 1.0);
            break;
        }
        case 22://Greenbean
        {                
        	drawCrossedSquares(block, i, j, k, renderblocks, 0.45, 1.0);
            break;
        }
        case 23://Squash
        {                
        	drawCrossedSquares(block, i, j, k, renderblocks, 0.45, 1.0);
            break;
        }
        default:
        {
            renderblocks.renderBlockCrops(block, i, j, k);
            break;
        }
        }
		
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
	
	private static void renderBlockCropsImpl(Block block, double i, double j, double k, RenderBlocks renderblocks, double width, double height)
    {
        Tessellator tess = Tessellator.instance;
        GL11.glColor3f(1, 1, 1);
        int brightness = block.getMixedBrightnessForBlock(renderblocks.blockAccess, (int)i, (int)j, (int)k);
        tess.setBrightness(brightness);
        tess.setColorRGBA_F(1, 1, 1, 1);
        
        Icon icon = block.getBlockTexture(renderblocks.blockAccess, (int)i, (int)j, (int)k, renderblocks.blockAccess.getBlockMetadata((int)i, (int)j, (int)k));

        if (renderblocks.hasOverrideBlockTexture())
        {
            icon = renderblocks.overrideBlockTexture;
        }
        
        double minU = icon.getMinU();
        double maxU = icon.getMaxU();
        double minV = icon.getMinV();
        double maxV = icon.getMaxV();
        double minX = i + 0.5D - 0.25D;
        double maxX = i + 0.5D + 0.25D;
        double minZ = k + 0.5D - width;
        double maxZ = k + 0.5D + width;
        
        double y = j;        
        
        tess.addVertexWithUV(minX, y+height, minZ, minU, minV);
        tess.addVertexWithUV(minX, y, minZ, minU, maxV);
        tess.addVertexWithUV(minX, y, maxZ, maxU, maxV);
        tess.addVertexWithUV(minX, y+height, maxZ, maxU, minV);
        tess.addVertexWithUV(minX, y+height, maxZ, minU, minV);
        tess.addVertexWithUV(minX, y, maxZ, minU, maxV);
        tess.addVertexWithUV(minX, y, minZ, maxU, maxV);
        tess.addVertexWithUV(minX, y+height, minZ, maxU, minV);
        tess.addVertexWithUV(maxX, y+height, maxZ, minU, minV);
        tess.addVertexWithUV(maxX, y, maxZ, minU, maxV);
        tess.addVertexWithUV(maxX, y, minZ, maxU, maxV);
        tess.addVertexWithUV(maxX, y+height, minZ, maxU, minV);
        tess.addVertexWithUV(maxX, y+height, minZ, minU, minV);
        tess.addVertexWithUV(maxX, y, minZ, minU, maxV);
        tess.addVertexWithUV(maxX, y, maxZ, maxU, maxV);
        tess.addVertexWithUV(maxX, y+height, maxZ, maxU, minV);
        minX = i + 0.5D - width;
        maxX = i + 0.5D + width;
        minZ = k + 0.5D - 0.25D;
        maxZ = k + 0.5D + 0.25D;
        tess.addVertexWithUV(minX, y+height, minZ, minU, minV);
        tess.addVertexWithUV(minX, y, minZ, minU, maxV);
        tess.addVertexWithUV(maxX, y, minZ, maxU, maxV);
        tess.addVertexWithUV(maxX, y+height, minZ, maxU, minV);
        tess.addVertexWithUV(maxX, y+height, minZ, minU, minV);
        tess.addVertexWithUV(maxX, y, minZ, minU, maxV);
        tess.addVertexWithUV(minX, y, minZ, maxU, maxV);
        tess.addVertexWithUV(minX, y+height, minZ, maxU, minV);
        tess.addVertexWithUV(maxX, y+height, maxZ, minU, minV);
        tess.addVertexWithUV(maxX, y, maxZ, minU, maxV);
        tess.addVertexWithUV(minX, y, maxZ, maxU, maxV);
        tess.addVertexWithUV(minX, y+height, maxZ, maxU, minV);
        tess.addVertexWithUV(minX, y+height, maxZ, minU, minV);
        tess.addVertexWithUV(minX, y, maxZ, minU, maxV);
        tess.addVertexWithUV(maxX, y, maxZ, maxU, maxV);
        tess.addVertexWithUV(maxX, y+height, maxZ, maxU, minV);
    }

    private static void drawCrossedSquares(Block block, double x, double y, double z, RenderBlocks renderblocks, double width, double height)
    {
        Tessellator var9 = Tessellator.instance;

        var9.setColorOpaque_F(1.0f, 1.0f, 1.0f);
        GL11.glColor3f(1, 1, 1);
        
        Icon icon = block.getBlockTexture(renderblocks.blockAccess, (int)x, (int)y, (int)z, renderblocks.blockAccess.getBlockMetadata((int)x, (int)y, (int)z));

        if (renderblocks.hasOverrideBlockTexture())
        {
            icon = renderblocks.overrideBlockTexture;
        }
        
        double minU = icon.getMinU();
        double maxU = icon.getMaxU();
        double minV = icon.getMinV();
        double maxV = icon.getMaxV();
        
        double minX = x + 0.5D - width;
        double maxX = x + 0.5D + width;
        double minZ = z + 0.5D - width;
        double maxZ = z + 0.5D + width;
          
        
        var9.addVertexWithUV(minX, y + height, minZ, minU, minV);
        var9.addVertexWithUV(minX, y + 0.0D, minZ, minU, maxV);
        var9.addVertexWithUV(maxX, y + 0.0D, maxZ, maxU, maxV);
        var9.addVertexWithUV(maxX, y + height, maxZ, maxU, minV);
        
        var9.addVertexWithUV(maxX, y + height, maxZ, minU, minV);
        var9.addVertexWithUV(maxX, y + 0.0D, maxZ, minU, maxV);
        var9.addVertexWithUV(minX, y + 0.0D, minZ, maxU, maxV);
        var9.addVertexWithUV(minX, y + height, minZ, maxU, minV);
        
        var9.addVertexWithUV(minX, y + height, maxZ, minU, minV);
        var9.addVertexWithUV(minX, y + 0.0D, maxZ, minU, maxV);
        var9.addVertexWithUV(maxX, y + 0.0D, minZ, maxU, maxV);
        var9.addVertexWithUV(maxX, y + height, minZ, maxU, minV);
        
        var9.addVertexWithUV(maxX, y + height, minZ, minU, minV);
        var9.addVertexWithUV(maxX, y + 0.0D, minZ, minU, maxV);
        var9.addVertexWithUV(minX, y + 0.0D, maxZ, maxU, maxV);
        var9.addVertexWithUV(minX, y + height, maxZ, maxU, minV);
    }

}
