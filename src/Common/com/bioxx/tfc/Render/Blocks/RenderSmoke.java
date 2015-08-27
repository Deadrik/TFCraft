package com.bioxx.tfc.Render.Blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderSmoke  implements ISimpleBlockRenderingHandler 
{
	/*private static float pixel3 = 3f / 16f;
	private static float pixel5 = 5f / 16f;
	private static float pixel12 = 12f / 16f;
	private static float pixel14 = 14f / 16f;*/

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
	{
		//IBlockAccess blockAccess = renderer.blockAccess;
		renderer.enableAO = false;
		Tessellator tessellator = Tessellator.instance;
		boolean flag = false;
		float f3 = 0.5F;
		float f4 = 1.0F;
		float f5 = 0.8F;
		float f6 = 0.6F;
		float f7 = f4 * 1;
		float f8 = f4 * 1;
		float f9 = f4 * 1;
		float f10 = f3;
		float f11 = f5;
		float f12 = f6;
		float f13 = f3;
		float f14 = f5;
		float f15 = f6;
		float f16 = f3;
		float f17 = f5;
		float f18 = f6;
		float alpha = 0.8f;
		IIcon iicon;


		int l = block.getMixedBrightnessForBlock(world, x, y, z);

		if (renderer.renderAllFaces || block.shouldSideBeRendered(renderer.blockAccess, x, y - 1, z, 0))
		{
			tessellator.setBrightness(renderer.renderMinY > 0.0D ? l : block.getMixedBrightnessForBlock(renderer.blockAccess, x, y - 1, z));
			tessellator.setColorRGBA_F(f10, f13, f16, alpha);
			renderer.renderFaceYNeg(block, x, y, z, renderer.getBlockIcon(block, renderer.blockAccess, x, y, z, 0));
			flag = true;
		}

		if (renderer.renderAllFaces || block.shouldSideBeRendered(renderer.blockAccess, x, y + 1, z, 1))
		{
			tessellator.setBrightness(renderer.renderMaxY < 1.0D ? l : block.getMixedBrightnessForBlock(renderer.blockAccess, x, y + 1, z));
			tessellator.setColorRGBA_F(f7, f8, f9, alpha);
			renderer.renderFaceYPos(block, x, y, z, renderer.getBlockIcon(block, renderer.blockAccess, x, y, z, 1));
			flag = true;
		}


		if (renderer.renderAllFaces || block.shouldSideBeRendered(renderer.blockAccess, x, y, z - 1, 2))
		{
			tessellator.setBrightness(renderer.renderMinZ > 0.0D ? l : block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z - 1));
			tessellator.setColorRGBA_F(f11, f14, f17, alpha);
			iicon = renderer.getBlockIcon(block, renderer.blockAccess, x, y, z, 2);
			renderer.renderFaceZNeg(block, x, y, z, iicon);
			flag = true;
		}

		if (renderer.renderAllFaces || block.shouldSideBeRendered(renderer.blockAccess, x, y, z + 1, 3))
		{
			tessellator.setBrightness(renderer.renderMaxZ < 1.0D ? l : block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z + 1));
			tessellator.setColorRGBA_F(f11, f14, f17, alpha);
			iicon = renderer.getBlockIcon(block, renderer.blockAccess, x, y, z, 3);
			renderer.renderFaceZPos(block, x, y, z, iicon);
			flag = true;
		}

		if (renderer.renderAllFaces || block.shouldSideBeRendered(renderer.blockAccess, x - 1, y, z, 4))
		{
			tessellator.setBrightness(renderer.renderMinX > 0.0D ? l : block.getMixedBrightnessForBlock(renderer.blockAccess, x - 1, y, z));
			tessellator.setColorRGBA_F(f12, f15, f18, alpha);
			iicon = renderer.getBlockIcon(block, renderer.blockAccess, x, y, z, 4);
			renderer.renderFaceXNeg(block, x, y, z, iicon);
			flag = true;
		}

		if (renderer.renderAllFaces || block.shouldSideBeRendered(renderer.blockAccess, x + 1, y, z, 5))
		{
			tessellator.setBrightness(renderer.renderMaxX < 1.0D ? l : block.getMixedBrightnessForBlock(renderer.blockAccess, x + 1, y, z));
			tessellator.setColorRGBA_F(f12, f15, f18, alpha);
			iicon = renderer.getBlockIcon(block, renderer.blockAccess, x, y, z, 5);
			renderer.renderFaceXPos(block, x, y, z, iicon);
			flag = true;
		}
		return flag;
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
	{

	}

	@Override
	public boolean shouldRender3DInInventory(int modelId)
	{
		return true;
	}

	@Override
	public int getRenderId()
	{
		return 0;
	}
}
