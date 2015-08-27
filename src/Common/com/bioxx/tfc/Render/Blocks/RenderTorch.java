package com.bioxx.tfc.Render.Blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderTorch  implements ISimpleBlockRenderingHandler 
{
	/*private static float pixel3 = 3f / 16f;
	private static float pixel5 = 5f / 16f;
	private static float pixel12 = 12f / 16f;
	private static float pixel14 = 14f / 16f;*/

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
	{
		int meta = world.getBlockMetadata(x, y, z);
		Tessellator tessellator = Tessellator.instance;
		tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
		tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
		double d0 = 0.4000000059604645D;
		double d1 = 0.5D - d0;
		double d2 = 0.20000000298023224D;

		if ((meta & 7) == 1)
		{
			renderer.renderTorchAtAngle(block, x - d1, y + d2, z, -d0, 0.0D, meta);
		}
		else if ((meta & 7) == 2)
		{
			renderer.renderTorchAtAngle(block, x + d1, y + d2, z, d0, 0.0D, meta);
		}
		else if ((meta & 7) == 3)
		{
			renderer.renderTorchAtAngle(block, x, y + d2, z - d1, 0.0D, -d0, meta);
		}
		else if ((meta & 7) == 4)
		{
			renderer.renderTorchAtAngle(block, x, y + d2, z + d1, 0.0D, d0, meta);
		}
		else
		{
			renderer.renderTorchAtAngle(block, x, y, z, 0.0D, 0.0D, meta);
		}

		return true;
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
	{
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
