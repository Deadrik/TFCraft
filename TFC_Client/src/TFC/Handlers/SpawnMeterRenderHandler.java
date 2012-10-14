package TFC.Handlers;

import TFC.Render.TFC_CoreRender;
import TFC.Render.Blocks.*;
import net.minecraft.src.Block;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.ModLoader;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.TFCBlocks;
import net.minecraft.src.Tessellator;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class SpawnMeterRenderHandler implements ISimpleBlockRenderingHandler 
{
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int i, int j, int k,
			Block block, int modelId, RenderBlocks renderer) 
	{
		if (modelId == TFCBlocks.spawnMeterRenderId)
        {
            return RenderSpawnMeter.render(block, i, j, k, renderer);
        }
		return false;
	}

	@Override
	public boolean shouldRender3DInInventory() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public int getRenderId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId,
			RenderBlocks renderer) 
	{
		if (modelId == TFCBlocks.spawnMeterRenderId)
        {
            RenderSpawnMeter.renderItem(block, metadata, renderer);
        }
		
	}
}
