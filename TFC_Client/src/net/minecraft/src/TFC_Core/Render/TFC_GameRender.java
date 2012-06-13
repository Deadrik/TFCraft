package net.minecraft.src.TFC_Core.Render;

import java.util.Random;

import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.Block;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Material;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.Tessellator;
import net.minecraft.src.World;
import net.minecraft.src.mod_TFC_Core;
import net.minecraft.src.TFC_Core.Blocks.BlockTerraAnvil;
import net.minecraft.src.TFC_Core.Blocks.BlockTerraBellows;

public class TFC_GameRender 
{
	public static boolean renderFirepit(Block block, int i, int j, int k, RenderBlocks renderblocks)
	{
		IBlockAccess blockAccess = renderblocks.blockAccess;

		block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.02F, 1.0F);
		renderblocks.renderStandardBlock(block, i, j, k);

		block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.02F, 1.0F);
		return true;
	}
	
	public static boolean renderForge(Block block, int i, int j, int k, RenderBlocks renderblocks)
	{
		IBlockAccess blockAccess = renderblocks.blockAccess;

		block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.9F, 1.0F);
		renderblocks.renderStandardBlock(block, i, j, k);


		block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.9F, 1.0F);
		return true;
	}
	
	public static boolean renderBellows(Block block, int i, int j, int k, RenderBlocks renderblocks)
	{
		IBlockAccess blockAccess = renderblocks.blockAccess;
		
		int meta = blockAccess.getBlockMetadata(i, j, k);
		int direction = ((BlockTerraBellows)block).getDirectionFromMetadata(meta);
		
		if(direction == 0)
		{
			//forward
			renderblocks.overrideBlockTexture = 86;
			block.setBlockBounds(0.0F, 0.0F, 0.9F, 1.0F, 1.0F, 1.0F);
			renderblocks.renderStandardBlock(block, i, j, k);
			//mid
			renderblocks.overrideBlockTexture = -1;
			block.setBlockBounds(0.1F, 0.1F, 0.05F, 0.9F, 0.9F, 0.95F);
			renderblocks.renderStandardBlock(block, i, j, k);
			//back
			renderblocks.overrideBlockTexture = 87;
			block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.1F);
			renderblocks.renderStandardBlock(block, i, j, k);
			renderblocks.overrideBlockTexture = -1;
		}
		else if(direction == 1)
		{
			//forward
			renderblocks.overrideBlockTexture = 86;
			block.setBlockBounds(0.0F, 0.0F, 0.0F, 0.1F, 1.0F, 1.0F);
			renderblocks.renderStandardBlock(block, i, j, k);
			
			//mid
			renderblocks.overrideBlockTexture = -1;
			block.setBlockBounds(0.1F, 0.1F, 0.1F, 0.9F, 0.9F, 0.9F);
			renderblocks.renderStandardBlock(block, i, j, k);

			//back
			renderblocks.overrideBlockTexture = 87;
			block.setBlockBounds(0.9F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
			renderblocks.renderStandardBlock(block, i, j, k);
			renderblocks.overrideBlockTexture = -1;
			
		}
		else if(direction == 2)
		{
			//forward
			renderblocks.overrideBlockTexture = 86;
			block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.1F);
			renderblocks.renderStandardBlock(block, i, j, k);
			//mid
			renderblocks.overrideBlockTexture = -1;
			block.setBlockBounds(0.1F, 0.1F, 0.05F, 0.9F, 0.9F, 0.95F);
			renderblocks.renderStandardBlock(block, i, j, k);
			//back
			renderblocks.overrideBlockTexture = 87;
			block.setBlockBounds(0.0F, 0.0F, 0.9F, 1.0F, 1.0F, 1.0F);
			renderblocks.renderStandardBlock(block, i, j, k);
			renderblocks.overrideBlockTexture = -1;
		}
		else if(direction == 3)
		{
			//forward
			renderblocks.overrideBlockTexture = 86;
			block.setBlockBounds(0.9F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
			renderblocks.renderStandardBlock(block, i, j, k);
			
			//mid
			renderblocks.overrideBlockTexture = -1;
			block.setBlockBounds(0.1F, 0.1F, 0.1F, 0.9F, 0.9F, 0.9F);
			renderblocks.renderStandardBlock(block, i, j, k);

			//back
			renderblocks.overrideBlockTexture = 87;
			block.setBlockBounds(0.0F, 0.0F, 0.0F, 0.1F, 1.0F, 1.0F);
			renderblocks.renderStandardBlock(block, i, j, k);
			renderblocks.overrideBlockTexture = -1;
		}

		block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		return true;
	}

	public static boolean renderAnvil(Block block, int i, int j, int k, RenderBlocks renderblocks)
	{
		IBlockAccess blockAccess = renderblocks.blockAccess;
		
		int meta = blockAccess.getBlockMetadata(i, j, k);
		int direction = ((BlockTerraAnvil)block).getDirectionFromMetadata(meta);
		
		if(direction == 0)//x
		{
			//top
			block.setBlockBounds(0.3F, 0.4F, 0.1F, 0.7F, 0.6F, 0.9F);
			renderblocks.renderStandardBlock(block, i, j, k);

			//core
			block.setBlockBounds(0.35F, 0.0F, 0.15F, 0.65F, 0.4F, 0.85F);
			renderblocks.renderStandardBlock(block, i, j, k);
			
			//feet
			block.setBlockBounds(0.25F, 0.0F, 0.1F, 0.75F, 0.2F, 0.90F);
			renderblocks.renderStandardBlock(block, i, j, k);
			block.setBlockBounds(0.20F, 0.0F, 0.0F, 0.80F, 0.1F, 1.0F);
			renderblocks.renderStandardBlock(block, i, j, k);
			
			block.setBlockBounds(0.2F, 0.0F, 0.0F, 0.80F, 0.6F, 1.0F);
		}
		else if(direction == 1)//z
		{
			//top
			block.setBlockBounds(0.1F, 0.4F, 0.3F, 0.9F, 0.6F, 0.7F);
			renderblocks.renderStandardBlock(block, i, j, k);

			//core
			block.setBlockBounds(0.15F, 0.0F, 0.35F, 0.85F, 0.4F, 0.65F);
			renderblocks.renderStandardBlock(block, i, j, k);
			
			//feet
			block.setBlockBounds(0.1F, 0.0F, 0.25F, 0.90F, 0.2F, 0.75F);
			renderblocks.renderStandardBlock(block, i, j, k);
			block.setBlockBounds(0.0F, 0.0F, 0.20F, 1.00F, 0.1F, 0.80F);
			renderblocks.renderStandardBlock(block, i, j, k);
			
			block.setBlockBounds(0.0F, 0.0F, 0.20F, 1.0F, 0.6F, 0.8F);
			
		}

		
		return true;
	}
}
