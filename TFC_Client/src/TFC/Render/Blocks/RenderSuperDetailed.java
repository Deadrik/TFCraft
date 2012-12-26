package TFC.Render.Blocks;

import org.lwjgl.opengl.GL11;
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
