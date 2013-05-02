package TFC.Render.Blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import TFC.Items.Pottery.ItemPotteryJug;
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
		
		for(int i = 0; i < 4; i++)
		{
			ItemStack is = te.inventory[i];
			float offsetX = 0.25f; 
			float offsetY = 0; 
			float offsetZ = 0.25f;
			if(i == 1)
			{
				offsetX = 0.75f; 
				offsetZ = 0.25f;
			}
			else if(i == 2)
			{
				offsetX = 0.25f; 
				offsetZ = 0.75f;
			}
			else if(i == 3)
			{
				offsetX = 0.75f; 
				offsetZ = 0.75f;
			}
			if(is != null)
			{
				renderer.setOverrideBlockTexture(block.getIcon(0, is.getItemDamage()));
				if(is.getItem() instanceof ItemPotteryJug)
				{
					//renderJug(x, y, z,offsetX, offsetY, offsetZ, block, renderer);
				}
			}
			
		}
		renderer.clearOverrideBlockTexture();
		return true;
	}
	
	public void renderJug(int x, int y, int z, float offsetX, float offsetY, float offsetZ, Block block, RenderBlocks renderer)
	{
		//Base
		renderer.setRenderBounds(-0.15 + offsetX, 0 + offsetY, -0.15 + offsetZ, 0.15 + offsetX, 0.4 + offsetY, 0.15 + offsetZ);
		renderer.renderStandardBlock(block, x, y, z);
		renderer.setRenderBounds(-0.14 + offsetX, 0.4 + offsetY, -0.14 + offsetZ, 0.14 + offsetX, 0.42 + offsetY, 0.14 + offsetZ);
		renderer.renderStandardBlock(block, x, y, z);
		renderer.setRenderBounds(-0.13 + offsetX, 0.4 + offsetY, -0.13 + offsetZ, 0.13 + offsetX, 0.44 + offsetY, 0.13 + offsetZ);
		renderer.renderStandardBlock(block, x, y, z);
		renderer.setRenderBounds(-0.12 + offsetX, 0.4 + offsetY, -0.12 + offsetZ, 0.12 + offsetX, 0.46 + offsetY, 0.12 + offsetZ);
		renderer.renderStandardBlock(block, x, y, z);
		renderer.setRenderBounds(-0.11 + offsetX, 0.4 + offsetY, -0.11 + offsetZ, 0.11 + offsetX, 0.48 + offsetY, 0.11 + offsetZ);
		renderer.renderStandardBlock(block, x, y, z);
		renderer.setRenderBounds(-0.10 + offsetX, 0.4 + offsetY, -0.10 + offsetZ, 0.10 + offsetX, 0.5 + offsetY, 0.10 + offsetZ);
		renderer.renderStandardBlock(block, x, y, z);
		//Mouth
		renderer.setRenderBounds(-0.03 + offsetX, 0.5 + offsetY, -0.03 + offsetZ, -0.02 + offsetX, 0.55 + offsetY, 0.03 + offsetZ);
		renderer.renderStandardBlock(block, x, y, z);
		renderer.setRenderBounds(0.02 + offsetX, 0.5 + offsetY, -0.03 + offsetZ, 0.03 + offsetX, 0.55 + offsetY, 0.03 + offsetZ);
		renderer.renderStandardBlock(block, x, y, z);
		renderer.setRenderBounds(-0.03 + offsetX, 0.5 + offsetY, -0.03 + offsetZ, 0.03 + offsetX, 0.55 + offsetY, -0.02 + offsetZ);
		renderer.renderStandardBlock(block, x, y, z);
		renderer.setRenderBounds(-0.03 + offsetX, 0.5 + offsetY, 0.02 + offsetZ, 0.03 + offsetX, 0.55 + offsetY, 0.03 + offsetZ);
		renderer.renderStandardBlock(block, x, y, z);
		//Handle
		/*renderer.setRenderBounds(-0.03 + offsetX, 0.5 + offsetY, -0.03 + offsetZ, 0.03 + offsetX, 0.55 + offsetY, 0.03 + offsetZ);
		renderer.renderStandardBlock(block, x, y, z);*/
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

