package TFC.Render.Blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import TFC.TileEntities.TileEntityPottery;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderPottery implements ISimpleBlockRenderingHandler
{

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer)
	{
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
	{
		IBlockAccess blockAccess = renderer.blockAccess;
		TileEntityPottery te = (TileEntityPottery) blockAccess.getTileEntity(x, y, z);
		
		float meta = world.getBlockMetadata(x, y, z);
		if(meta > 0)
		{
			renderer.setRenderBounds(0, 0, 0, 1, meta/15, 1);
			renderer.renderStandardBlock(block, x, y, z);
		}
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId)
	{
		return false;
	}

	@Override
	public int getRenderId()
	{
		return 0;
	}
}

