package TFC.Render.Blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import TFC.TFCBlocks;

public class RenderGrass
{
	public static boolean render(Block block, int x, int y, int z, RenderBlocks renderer)
	{
		float red = 1F;
        float green = 1F;
        float blue = 1F;
        
        if(block.blockID == TFCBlocks.Grass.blockID)
        	renderer.renderStandardBlockWithAmbientOcclusion(TFCBlocks.Dirt, x, y, z, red, blue, green);
        else if(block.blockID == TFCBlocks.Grass2.blockID)
        	renderer.renderStandardBlockWithAmbientOcclusion(TFCBlocks.Dirt2, x, y, z, red, blue, green);
        
        renderer.renderStandardBlock(block, x, y, z);
        
		return true;
	}
}
