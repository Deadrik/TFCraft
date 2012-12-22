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
import TFC.TileEntities.TileEntitySuperDetailed;

public class RenderSuperDetailed 
{
	public static boolean render(Block block, int i, int j, int k, RenderBlocks renderblocks)
    {
        TileEntitySuperDetailed te = (TileEntitySuperDetailed) renderblocks.blockAccess.getBlockTileEntity(i, j, k);
        int md = renderblocks.blockAccess.getBlockMetadata(i, j, k);

        boolean breaking = false;
        if(renderblocks.overrideBlockTexture >= 240)
        {
        	breaking = true;
        }
        

        for(int subX = 0; subX < 8; subX++)
        {
        	for(int subZ = 0; subZ < 8; subZ++)
            {
        		for(int subY = 0; subY < 8; subY++)
                {
        			boolean subExists = RenderDetailed.isOpaque(te,subX, subY, subZ);
                	if(subExists)
                	{
                		float minX = 0.125f*subX;
                		float maxX = minX + 0.125f;
                		float minY = 0.125f*subY;
                		float maxY = minY + 0.125f;
                		float minZ = 0.125f*subZ;
                		float maxZ = minZ + 0.125f;
                		
                		int blockIndex = te.getIndex(subX, subY, subZ);
                		
                		int type = te.getIdFromIndex(blockIndex);
                        int meta = te.getMetaFromIndex(blockIndex);
                        
                		if(type > 0)
                		{
                			int index = te.blockIndex[type];
                			if(!breaking)
                            	ForgeHooksClient.bindTexture(Block.blocksList[index].getTextureFile(), ModLoader.getMinecraftInstance().renderEngine.getTexture(Block.blocksList[index].getTextureFile()));
                			
                			renderblocks.setRenderMinMax(minX, minY, minZ, maxX, maxY, maxZ);
                			RenderDetailed.renderStandardBlockWithColorMultiplier(Block.blocksList[type], renderblocks, i, j, k, 0.95f, 0.95f, 0.95f, meta);
                		}
                	}
                }
            }
        }

        return true;
    }
}
