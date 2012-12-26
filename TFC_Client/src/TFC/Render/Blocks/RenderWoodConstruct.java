package TFC.Render.Blocks;

import java.util.BitSet;
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
import TFC.TileEntities.*;

public class RenderWoodConstruct 
{
	public static boolean render(Block block, int i, int j, int k, RenderBlocks renderblocks)
    {
        TileEntityWoodConstruct te = (TileEntityWoodConstruct) renderblocks.blockAccess.getBlockTileEntity(i, j, k);
        int md = renderblocks.blockAccess.getBlockMetadata(i, j, k);
        
        int d = te.PlankDetailLevel;
    	int dd = te.PlankDetailLevel * te.PlankDetailLevel;
    	int dd2 = dd*2;
    	
    	float div = 1f / d;
        
        boolean breaking = false;
        if(renderblocks.overrideBlockTexture >= 240)
        {
        	breaking = true;
        }
        
        for(int index = 0; index < dd; index++)
        {
        	if(te.data.get(index))
        	{        		
        		float minX = 0;
        		float maxX = 1;
        		float minY = div * (index & 7);
        		float maxY = minY + div;
        		float minZ = div * (index >> 3);
        		float maxZ = minZ + div;
        		
        		if(!breaking)
        			renderblocks.overrideBlockTexture = TFCBlocks.WoodConstruct.getBlockTextureFromSideAndMetadata(0, te.woodTypes[index]);
        		renderblocks.uvRotateTop = 3;
        		renderblocks.uvRotateBottom = 3;
        		renderblocks.setRenderMinMax(minX, minY, minZ, maxX, maxY, maxZ);
        		renderblocks.renderStandardBlockWithColorMultiplier(block, i, j, k, 1, 1, 1);
        	}
        }
        //Fix the rotations
        renderblocks.uvRotateTop = 0;
		renderblocks.uvRotateBottom = 0;
		
        for(int index = 0; index < dd; index++)
        {
        	if(te.data.get(index + dd))
        	{        		
        		float minX = div * (index & 7);
        		float maxX = minX + div;
        		float minY = 0;
        		float maxY = 1;
        		float minZ = div * (index >> 3);
        		float maxZ = minZ + div;
        		

        		if(!breaking)
        			renderblocks.overrideBlockTexture = TFCBlocks.WoodConstruct.getBlockTextureFromSideAndMetadata(0, te.woodTypes[index+dd]);
        		renderblocks.uvRotateNorth = 1;
        		renderblocks.uvRotateSouth = 1;
        		renderblocks.uvRotateEast = 1;
        		renderblocks.uvRotateWest = 1;
                renderblocks.setRenderMinMax(minX, minY, minZ, maxX, maxY, maxZ);
        		renderblocks.renderStandardBlockWithColorMultiplier(block, i, j, k, 1, 1, 1);
        	}
        }
        
        //Fix the rotations
        renderblocks.uvRotateNorth = 0;
		renderblocks.uvRotateSouth = 0;
		renderblocks.uvRotateEast = 0;
		renderblocks.uvRotateWest = 0;
		
        for(int z = 0; z < dd; z++)
        {
        	if(te.data.get(z+dd2))
        	{        		
        		float minX = div * (z & 7);
        		float maxX = minX + div;
        		float minY = div * (z >> 3);
        		float maxY = minY + div;
        		float minZ = 0;
        		float maxZ = 1;

        		if(!breaking)
        			renderblocks.overrideBlockTexture = TFCBlocks.WoodConstruct.getBlockTextureFromSideAndMetadata(0, te.woodTypes[z+dd2]);
        		renderblocks.uvRotateTop = 1;
        		renderblocks.uvRotateBottom = 1;
                renderblocks.setRenderMinMax(minX, minY, minZ, maxX, maxY, maxZ);
        		renderblocks.renderStandardBlockWithColorMultiplier(block, i, j, k, 1, 1, 1);
        	}
        }
        renderblocks.uvRotateTop = 0;
		renderblocks.uvRotateBottom = 0;
		renderblocks.clearOverrideBlockTexture();
        return true;
    }
}
