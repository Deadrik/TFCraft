package com.bioxx.tfc.Render.Blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;

import com.bioxx.tfc.api.TFCBlocks;

public class RenderGrass
{
	public static boolean render(Block block, int x, int y, int z, RenderBlocks renderer)
	{
		float red = 1F;
		float green = 1F;
		float blue = 1F;

		if(block == TFCBlocks.grass || block == TFCBlocks.dryGrass)
			renderer.renderStandardBlockWithAmbientOcclusion(TFCBlocks.dirt, x, y, z, red, blue, green);
		else if(block == TFCBlocks.grass2 || block == TFCBlocks.dryGrass2)
			renderer.renderStandardBlockWithAmbientOcclusion(TFCBlocks.dirt2, x, y, z, red, blue, green);

		renderer.renderStandardBlock(block, x, y, z);

		return true;
	}

	public static boolean renderClay(Block block, int x, int y, int z, RenderBlocks renderer)
	{
		float red = 1F;
		float green = 1F;
		float blue = 1F;

		if(block == TFCBlocks.clayGrass)
			renderer.renderStandardBlockWithAmbientOcclusion(TFCBlocks.clay, x, y, z, red, blue, green);
		else if(block == TFCBlocks.clayGrass2)
			renderer.renderStandardBlockWithAmbientOcclusion(TFCBlocks.clay2, x, y, z, red, blue, green);

		renderer.renderStandardBlock(block, x, y, z);

		return true;
	}

	public static boolean renderPeat(Block block, int x, int y, int z, RenderBlocks renderer)
	{
		float red = 1F;
		float green = 1F;
		float blue = 1F;

		renderer.renderStandardBlockWithAmbientOcclusion(TFCBlocks.peat, x, y, z, red, blue, green);

		renderer.renderStandardBlock(block, x, y, z);

		return true;
	}
}
