package TFC.Render.Blocks;

import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.Block;
import net.minecraft.src.ModLoader;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.Tessellator;
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
        int tex = Block.blocksList[type].getBlockTextureFromSideAndMetadata(0, meta);
        int over = renderblocks.overrideBlockTexture;
        renderblocks.overrideBlockTexture = tex;


        for(int subX = 0; subX < 10; subX++)
        {
        	for(int subZ = 0; subZ < 10; subZ++)
            {
        		for(int subY = 0; subY < 10; subY++)
                {
        			boolean subExists = isOpaque(te,subX, subY, subZ);
                	if(subExists)
                	{
                		renderblocks.setRenderMinMax(0.1f*subX, 0.1f*subY, 0.1f*subZ, 0.1f*subX+0.1f, 0.1f*subY+0.1f, 0.1f*subZ+0.1f);
                		
                		if(subY-1 == -1 || !isOpaque(te,subX, subY-1, subZ))
                		{
                			//Tessellator.instance.setBrightness(block.getMixedBrightnessForBlock(renderblocks.blockAccess, i, j - 1, k));
                			renderblocks.renderBottomFace(block, i, j, k, tex);
                		}
                		if(subY+1 == 10 || !isOpaque(te,subX, subY+1, subZ))
                		{
                			//Tessellator.instance.setBrightness(block.getMixedBrightnessForBlock(renderblocks.blockAccess, i, j + 1, k));
                			renderblocks.renderTopFace(block, i, j, k, tex);
                		}
                		if(subZ-1 == -1 || !isOpaque(te,subX, subY, subZ-1))
                		{
                			renderblocks.renderEastFace(block, i, j, k, tex);
                		}
                		if(subZ+1 == 10 || !isOpaque(te,subX, subY, subZ+1))
                		{
                			renderblocks.renderWestFace(block, i, j, k, tex);
                		}
                		if(subX+1 == 10 || !isOpaque(te,subX+1, subY, subZ))
                		{
                			renderblocks.renderSouthFace(block, i, j, k, tex);
                		}
                		if(subX-1 == -1 || !isOpaque(te,subX-1, subY, subZ))
                		{
                			renderblocks.renderNorthFace(block, i, j, k, tex);
                		}
                		

                	}
                }
            }
        }

        block.setBlockBounds(0,0,0,1,1,1);

        
//        if(over == -1 && (type == TFCBlocks.terraOre.blockID || type == TFCBlocks.terraOre2.blockID || type == TFCBlocks.terraOre3.blockID))
//        {
//            renderblocks.overrideBlockTexture = TFC_CoreRender.getRockTexture(ModLoader.getMinecraftInstance().theWorld, i, j, k);
//            renderblocks.renderStandardBlock(block, i, j, k);
//            renderblocks.overrideBlockTexture = over;
//        }
//
//
//        if(over == -1)
//            renderblocks.overrideBlockTexture = tex;
//
//        renderblocks.renderStandardBlock(block, i, j, k);
//
        renderblocks.overrideBlockTexture = over;



        return true;
    }
	
	public static boolean isOpaque(TileEntityDetailed te, int x, int y, int z)
	{
		return te.data.get((x * 10 + z)*10 + y);
	}
}
