package TFC.Render.Blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import TFC.TileEntities.TileEntityPottery;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderPottery implements ISimpleBlockRenderingHandler
{

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId,
			RenderBlocks renderer) 
	{
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) 
	{
		IBlockAccess blockAccess = renderer.blockAccess;
		TileEntityPottery te = (TileEntityPottery) blockAccess.getBlockTileEntity(x, y, z);
		
		for(int i = 0; i < 16; i++)
		{
			ItemStack is = te.inventory[i];
			if(is != null)
			{
				if(is.getItemDamage() < 5)
				{
					
				}
				else
				{
					
				}
			}
		}
		
		return true;
	}
	
	public void renderJug(int x, int y, int z, float offsetX, float offsetY, float offsetZ, Block block, RenderBlocks renderer)
	{
		renderer.setRenderBounds(0, 0, 0, 1, 1, 1);
		renderer.renderStandardBlock(block, x, y, z);
	}

	@Override
	public boolean shouldRender3DInInventory() {
		return false;
	}

	@Override
	public int getRenderId() {
		// TODO Auto-generated method stub
		return 0;
	}
}

