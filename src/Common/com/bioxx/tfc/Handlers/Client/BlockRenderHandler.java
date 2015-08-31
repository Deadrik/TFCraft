package com.bioxx.tfc.Handlers.Client;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Render.TFC_CoreRender;
import com.bioxx.tfc.Render.Blocks.RenderAnvil;
import com.bioxx.tfc.Render.Blocks.RenderCrop;
import com.bioxx.tfc.Render.Blocks.RenderDetailed;
import com.bioxx.tfc.Render.Blocks.RenderGrass;
import com.bioxx.tfc.api.TFCBlocks;

public class BlockRenderHandler implements ISimpleBlockRenderingHandler
{
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int i, int j, int k, Block block, int modelId, RenderBlocks renderer)
	{
		if (modelId == TFCBlocks.sulfurRenderId)
		{
			return TFC_CoreRender.renderSulfur(block, i, j, k, renderer);
		}
		else if (modelId == TFCBlocks.moltenRenderId)
		{
			return TFC_CoreRender.renderMolten(block, i, j, k, renderer);
		}
		else if (modelId == TFCBlocks.grassRenderId)
		{
			return RenderGrass.render(block, i, j, k, renderer);
		}
		else if (modelId == TFCBlocks.clayGrassRenderId)
		{
			return RenderGrass.renderClay(block, i, j, k, renderer);
		}
		else if (modelId == TFCBlocks.peatGrassRenderId)
		{
			return RenderGrass.renderPeat(block, i, j, k, renderer);
		}
		else if (modelId == TFCBlocks.looseRockRenderId)
		{
			return TFC_CoreRender.renderLooseRock(block, i, j, k, renderer);
		}
		else if (modelId == TFCBlocks.snowRenderId)
		{
			return TFC_CoreRender.renderSnow(block, i, j, k, renderer);
		}
		else if (modelId == TFCBlocks.firepitRenderId)
		{
			return TFC_CoreRender.renderFirepit(block, i, j, k, renderer);
		}
		else if (modelId == TFCBlocks.forgeRenderId)
		{
			return TFC_CoreRender.renderForge(block, i, j, k, renderer);
		}
		else if (modelId == TFCBlocks.anvilRenderId)
		{
			return RenderAnvil.renderAnvil(block, i, j, k, renderer);
		}
		/* else if (modelId == TFCBlocks.IngotPileRenderId)
		{
			if(((BlockIngotPile)block).stack < 10){
				TerraFirmaCraft.log.info(((BlockIngotPile)block).stack+" is the stack");
		    return RenderIngotPile.renderIngotPile(block, i, j, k, renderer);
			}
			return TFC_CoreRender.RenderSluice(block, i, j, k, renderer);
		}*/
		else if (modelId == TFCBlocks.sluiceRenderId)
		{
			return TFC_CoreRender.renderSluice(block, i, j, k, renderer);
		}
		else if (modelId == TFCBlocks.woodFruitRenderId)
		{
			return TFC_CoreRender.renderWoodTrunk(block, i, j, k, renderer);
		}
		else if (modelId == TFCBlocks.leavesFruitRenderId)
		{
			return TFC_CoreRender.renderFruitLeaves(block, i, j, k, renderer);
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
		/*else if (modelId == TFCBlocks.leavesRenderId)
        {
            int var5 = block.colorMultiplier(world, i, j, k);
            float var6 = (float)(var5 >> 16 & 255) / 255.0F;
            float var7 = (float)(var5 >> 8 & 255) / 255.0F;
            float var8 = (float)(var5 & 255) / 255.0F;
            return RenderLeaves.renderLeaves(block, i, j, k, var6, var7, var8, (RenderBlocks)renderer, ModLoader.getMinecraftInstance().isFancyGraphicsEnabled(), true);
        }*/
		else if (modelId == TFCBlocks.detailedRenderId)
		{
			return RenderDetailed.renderBlockDetailed(block, i, j, k, renderer);
		}
		else if (modelId == TFCBlocks.waterPlantRenderId)
		{
			return TFC_CoreRender.renderSeaPlant(block, i, j, k, renderer);
		}
		return false;
	}

	@Override
	public boolean shouldRender3DInInventory(int i)
	{
		return true;
	}

	@Override
	public int getRenderId()
	{
		return 0;
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
	{
		IIcon[] icons = new IIcon[6];

		if (modelID == TFCBlocks.peatGrassRenderId)
		{
			for(int i = 0; i < 6; i++)
				icons[i] = TFCBlocks.peat.getBlockTextureFromSide(i);
			renderInvBlock(block, renderer, icons);
		}
		else if (modelID == TFCBlocks.grassRenderId)
		{
			for(int i = 0; i < 6; i++)
			{
				if (block == TFCBlocks.dirt)
					icons[i] = TFCBlocks.dirt.getBlockTextureFromSide(i);
				else
					icons[i] = TFCBlocks.dirt2.getBlockTextureFromSide(i);
			}
			renderInvBlock(block, renderer, icons);
		}
		else if (modelID == TFCBlocks.clayGrassRenderId)
		{
			for(int i = 0; i < 6; i++)
			{
				if (block == TFCBlocks.clay)
					icons[i] = TFCBlocks.clay.getBlockTextureFromSide(i);
				else
					icons[i] = TFCBlocks.clay2.getBlockTextureFromSide(i);
			}
			renderInvBlock(block, renderer, icons);
		}
	}

	private void renderInvBlock(Block block, RenderBlocks renderer, IIcon[] icons)
	{
		Tessellator tessellator = Tessellator.instance;
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, -1F, 0.0F);
		renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, icons[0]);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, icons[1]);
		renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(1));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, -1F);
		renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, icons[2]);
		renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(2));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, 1.0F);
		renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, icons[3]);
		renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(3));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(-1F, 0.0F, 0.0F);
		renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, icons[4]);
		renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(4));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(1.0F, 0.0F, 0.0F);
		renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, icons[5]);
		renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(5));
		tessellator.draw();
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
	}
}
