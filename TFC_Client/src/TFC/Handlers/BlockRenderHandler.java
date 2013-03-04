package TFC.Handlers;

import TFC.TFCBlocks;
import TFC.Blocks.BlockIngotPile;
import TFC.Render.TFC_CoreRender;
import TFC.Render.Blocks.*;
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
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class BlockRenderHandler implements ISimpleBlockRenderingHandler 
{
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int i, int j, int k,
			Block block, int modelId, RenderBlocks renderer) 
	{
		if (modelId == TFCBlocks.sulfurRenderId)
        {
            return TFC_CoreRender.RenderSulfur(block, i, j, k, renderer);
        }
        else if (modelId == TFCBlocks.moltenRenderId)
        {
            return TFC_CoreRender.RenderMolten(block, i, j, k, renderer);
        }
        else if (modelId == TFCBlocks.woodSupportRenderIdH)
        {
            return TFC_CoreRender.RenderWoodSupportBeamH(block, i, j, k, renderer);
        }
        else if (modelId == TFCBlocks.woodSupportRenderIdV)
        {
            return TFC_CoreRender.RenderWoodSupportBeamV(block, i, j, k, renderer);
        }
        else if (modelId == TFCBlocks.grassRenderId)
        {
            int var5 = block.colorMultiplier(world, i, j, k);
            float var6 = (float)(var5 >> 16 & 255) / 255.0F;
            float var7 = (float)(var5 >> 8 & 255) / 255.0F;
            float var8 = (float)(var5 & 255) / 255.0F;
            return RenderGrass.Render(block, i, j, k, var6, var7, var8, renderer);
        }
        else if (modelId == TFCBlocks.oreRenderId)
        {
            int var5 = block.colorMultiplier(world, i, j, k);
            float var6 = (float)(var5 >> 16 & 255) / 255.0F;
            float var7 = (float)(var5 >> 8 & 255) / 255.0F;
            float var8 = (float)(var5 & 255) / 255.0F;
            return TFC_CoreRender.RenderOre(block, i, j, k,  var6, var7, var8, renderer, world);
        }
        else if (modelId == TFCBlocks.looseRockRenderId)
        {
            return TFC_CoreRender.RenderLooseRock(block, i, j, k, renderer);
        }
        else if (modelId == TFCBlocks.snowRenderId)
        {
            return TFC_CoreRender.RenderSnow(block, i, j, k, renderer);
        }
        else if (modelId == TFCBlocks.FirepitRenderId)
        {
            return TFC_CoreRender.renderFirepit(block, i, j, k, renderer);
        }
        else if (modelId == TFCBlocks.ForgeRenderId)
        {
            return TFC_CoreRender.renderForge(block, i, j, k, renderer);
        }
        else if (modelId == TFCBlocks.BellowsRenderId)
        {
            return TFC_CoreRender.renderBellows(block, i, j, k, renderer);
        }
        else if (modelId == TFCBlocks.AnvilRenderId)
        {
            return RenderAnvil.renderAnvil(block, i, j, k, renderer);
        }
       /* else if (modelId == TFCBlocks.IngotPileRenderId)
        {
        	if(((BlockIngotPile)block).stack < 10){
        		System.out.println(((BlockIngotPile)block).stack+" is the stack");
            return RenderIngotPile.renderIngotPile(block, i, j, k, renderer);
        	}
        	return TFC_CoreRender.RenderSluice(block, i, j, k, renderer);
        }*/
        else if (modelId == TFCBlocks.sluiceRenderId)
        {
            return TFC_CoreRender.RenderSluice(block, i, j, k, renderer);
        }
        else if (modelId == TFCBlocks.woodFruitRenderId)
        {
            return TFC_CoreRender.RenderWoodTrunk(block, i, j, k, renderer);
        }
        else if (modelId == TFCBlocks.leavesFruitRenderId)
        {
            int var5 = block.colorMultiplier(world, i, j, k);
            float var6 = (float)(var5 >> 16 & 255) / 255.0F;
            float var7 = (float)(var5 >> 8 & 255) / 255.0F;
            float var8 = (float)(var5 & 255) / 255.0F;
            return TFC_CoreRender.RenderFruitLeaves(block, i, j, k, var6, var7, var8, renderer);
        }
        else if (modelId == TFCBlocks.toolRackRenderId)
        {
            return RenderToolRack.renderToolRack(block, i, j, k, renderer);
        }
        else if (modelId == TFCBlocks.finiteWaterRenderId)
        {
            return TFC_CoreRender.RenderFiniteWater(block, i, j, k, renderer);
        }
        else if (modelId == TFCBlocks.stairRenderId)
        {
            return TFC_CoreRender.renderBlockStairs(block, i, j, k, renderer);
        }
        else if (modelId == TFCBlocks.slabRenderId)
        {
            return TFC_CoreRender.renderBlockSlab(block, i, j, k, renderer);
        }
        else if (modelId == TFCBlocks.cropRenderId)
        {
            return RenderCrop.render(block, i, j, k, renderer);
        }
        else if (modelId == TFCBlocks.leavesRenderId)
        {
            int var5 = block.colorMultiplier(world, i, j, k);
            float var6 = (float)(var5 >> 16 & 255) / 255.0F;
            float var7 = (float)(var5 >> 8 & 255) / 255.0F;
            float var8 = (float)(var5 & 255) / 255.0F;
            return RenderLeaves.renderLeaves(block, i, j, k, var6, var7, var8, (RenderBlocks)renderer, ModLoader.getMinecraftInstance().isFancyGraphicsEnabled(), true);
        }
        else if (modelId == TFCBlocks.detailedRenderId)
        {
            return RenderDetailed.renderBlockDetailed(block, i, j, k, renderer);
        }
        else if (modelId == TFCBlocks.foodPrepRenderId)
        {
            return RenderFoodPrep.render(block, i, j, k, renderer);
        }
        else if (modelId == TFCBlocks.fluidRenderId)
        {
            return RenderFluids.render(block, i, j, k, renderer);
        }
        else if (modelId == TFCBlocks.woodConstructRenderId)
        {
            return RenderWoodConstruct.render(block, i, j, k, renderer);
        }
        else if (modelId == TFCBlocks.superDetailedRenderId)
        {
            return RenderSuperDetailed.render(block, i, j, k, renderer);
        }
		return false;
	}

	@Override
	public boolean shouldRender3DInInventory() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getRenderId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID,
			RenderBlocks renderer) {
		// TODO Auto-generated method stub
		
	}

}
