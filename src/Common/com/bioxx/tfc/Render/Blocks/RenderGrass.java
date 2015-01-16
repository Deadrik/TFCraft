package com.bioxx.tfc.Render.Blocks;

import com.bioxx.tfc.api.TFCBlocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;

public class RenderGrass
{
	public static boolean render(Block block, int x, int y, int z, RenderBlocks renderer)
	{
		float red = 1F;
		float green = 1F;
		float blue = 1F;

		if(block == TFCBlocks.Grass || block == TFCBlocks.DryGrass)
			renderer.renderStandardBlockWithAmbientOcclusion(TFCBlocks.Dirt, x, y, z, red, blue, green);
		else if(block == TFCBlocks.Grass2 || block == TFCBlocks.DryGrass2)
			renderer.renderStandardBlockWithAmbientOcclusion(TFCBlocks.Dirt2, x, y, z, red, blue, green);

		renderer.renderStandardBlock(block, x, y, z);

		return true;
	}

	public static boolean renderClay(Block block, int x, int y, int z, RenderBlocks renderer)
	{
		float red = 1F;
		float green = 1F;
		float blue = 1F;

		if(block == TFCBlocks.ClayGrass)
			renderer.renderStandardBlockWithAmbientOcclusion(TFCBlocks.Clay, x, y, z, red, blue, green);
		else if(block == TFCBlocks.ClayGrass2)
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
