package TFC.Render.Blocks;

import net.minecraft.src.Block;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.RenderBlocks;
import TFC.Blocks.BlockTerraAnvil;
import TFC.Core.AnvilReq;
import TFC.Core.TFC_Climate;
import TFC.TileEntities.TileEntityTerraAnvil;
import TFC.WorldGen.DataLayer;

public class RenderAnvil {

	public static boolean renderAnvil(Block block, int i, int j, int k, RenderBlocks renderblocks)
	{
		IBlockAccess blockAccess = renderblocks.blockAccess;

		int meta = blockAccess.getBlockMetadata(i, j, k);
		int direction = ((BlockTerraAnvil)block).getDirectionFromMetadata(meta);

		if(((TileEntityTerraAnvil)blockAccess.getBlockTileEntity(i, j, k)).AnvilTier != AnvilReq.STONE.Tier)
		{
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
		}
		else
		{

			block.setBlockBounds(0.0F, 0.0F, 0.00F, 1.0F, 1.0F, 1.0F);
			renderblocks.renderStandardBlock(block, i, j, k);
		}
		return true;
	}
}
