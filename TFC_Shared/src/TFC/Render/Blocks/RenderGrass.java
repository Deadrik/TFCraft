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
        
        if(block.blockID == TFCBlocks.Grass.blockID || block.blockID == TFCBlocks.DryGrass.blockID)
        	renderer.renderStandardBlockWithAmbientOcclusion(TFCBlocks.Dirt, x, y, z, red, blue, green);
        else if(block.blockID == TFCBlocks.Grass2.blockID || block.blockID == TFCBlocks.DryGrass2.blockID)
        	renderer.renderStandardBlockWithAmbientOcclusion(TFCBlocks.Dirt2, x, y, z, red, blue, green);
        
        renderer.renderStandardBlock(block, x, y, z);
        
		return true;
	}
	
	public static boolean renderClay(Block block, int x, int y, int z, RenderBlocks renderer)
	{
		float red = 1F;
        float green = 1F;
        float blue = 1F;
        
        if(block.blockID == TFCBlocks.ClayGrass.blockID)
        	renderer.renderStandardBlockWithAmbientOcclusion(TFCBlocks.Clay, x, y, z, red, blue, green);
        else if(block.blockID == TFCBlocks.ClayGrass2.blockID)
        	renderer.renderStandardBlockWithAmbientOcclusion(TFCBlocks.Clay2, x, y, z, red, blue, green);
        
        renderer.renderStandardBlock(block, x, y, z);
        
		return true;
	}
	
	public static boolean renderPeat(Block block, int x, int y, int z, RenderBlocks renderer)
	{
		float red = 1F;
        float green = 1F;
        float blue = 1F;
        
        renderer.renderStandardBlockWithAmbientOcclusion(TFCBlocks.Peat, x, y, z, red, blue, green);

        renderer.renderStandardBlock(block, x, y, z);
        
		return true;
	}
}
