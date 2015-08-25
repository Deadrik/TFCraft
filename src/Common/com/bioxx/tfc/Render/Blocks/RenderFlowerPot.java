package com.bioxx.tfc.Render.Blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFlowerPot;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderFlowerPot implements ISimpleBlockRenderingHandler
{

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
	{
		renderer.renderStandardBlock(block, x, y, z);
		Tessellator tessellator = Tessellator.instance;
		tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
		int colorMult = block.colorMultiplier(world, x, y, z);
		IIcon iicon = renderer.getBlockIconFromSide(block, 0);
		float r = (colorMult >> 16 & 255) / 255.0F;
		float g = (colorMult >> 8 & 255) / 255.0F;
		float b = (colorMult & 255) / 255.0F;
		float r2;

		if (EntityRenderer.anaglyphEnable)
		{
			r2 = (r * 30.0F + g * 59.0F + b * 11.0F) / 100.0F;
			float g2 = (r * 30.0F + g * 70.0F) / 100.0F;
			float b2 = (r * 30.0F + b * 70.0F) / 100.0F;
			r = r2;
			g = g2;
			b = b2;
		}

		tessellator.setColorOpaque_F(r, g, b);
		r2 = 0.1865F;
		renderer.renderFaceXPos(block, x - 0.5F + r2, y, z, iicon);
		renderer.renderFaceXNeg(block, x + 0.5F - r2, y, z, iicon);
		renderer.renderFaceZPos(block, x, y, z - 0.5F + r2, iicon);
		renderer.renderFaceZNeg(block, x, y, z + 0.5F - r2, iicon);
		renderer.renderFaceYPos(block, x, y - 0.5F + r2 + 0.1875F, z, renderer.getBlockIcon(Blocks.dirt));
		TileEntity tileentity = world.getTileEntity(x, y, z);

		if (tileentity instanceof TileEntityFlowerPot)
		{
			Item item = ((TileEntityFlowerPot) tileentity).getFlowerPotItem();
			int meta = ((TileEntityFlowerPot) tileentity).getFlowerPotData();

			if (item instanceof ItemBlock)
			{
				Block plantedBlock = Block.getBlockFromItem(item);
				int renderType = plantedBlock.getRenderType();
				float transX = 0.0F;
				float transY = 4.0F;
				float transZ = 0.0F;
				tessellator.addTranslation(transX / 16.0F, transY / 16.0F, transZ / 16.0F);
				colorMult = plantedBlock.colorMultiplier(world, x, y, z);

				if (colorMult != 16777215)
				{
					r = (colorMult >> 16 & 255) / 255.0F;
					g = (colorMult >> 8 & 255) / 255.0F;
					b = (colorMult & 255) / 255.0F;
					tessellator.setColorOpaque_F(r, g, b);
				}

				if (renderType == 1)
				{
					renderer.drawCrossedSquares(renderer.getBlockIconFromSideAndMetadata(plantedBlock, 0, meta), x, y, z, 0.75F);
				}
				else if (renderType == 13)
				{
					renderer.renderAllFaces = true;
					float f9 = 0.125F;
					renderer.setRenderBounds(0.5F - f9, 0.0D, 0.5F - f9, 0.5F + f9, 0.25D, 0.5F + f9);
					renderer.renderStandardBlock(plantedBlock, x, y, z);
					renderer.setRenderBounds(0.5F - f9, 0.25D, 0.5F - f9, 0.5F + f9, 0.5D, 0.5F + f9);
					renderer.renderStandardBlock(plantedBlock, x, y, z);
					renderer.setRenderBounds(0.5F - f9, 0.5D, 0.5F - f9, 0.5F + f9, 0.75D, 0.5F + f9);
					renderer.renderStandardBlock(plantedBlock, x, y, z);
					renderer.renderAllFaces = false;
					renderer.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
				}

				tessellator.addTranslation(-transX / 16.0F, -transY / 16.0F, -transZ / 16.0F);
			}
		}

		return true;
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer)
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
