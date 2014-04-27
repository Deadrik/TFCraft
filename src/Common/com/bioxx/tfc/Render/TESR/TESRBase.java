package com.bioxx.tfc.Render.TESR;

import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import com.google.common.primitives.SignedBytes;

public class TESRBase extends TileEntitySpecialRenderer
{
	protected static RenderBlocks renderBlocks = new RenderBlocks();
	protected static RenderItem itemRenderer;

	{
		itemRenderer = new RenderItem()
		{
			@Override
			public byte getMiniBlockCount(ItemStack stack, byte original)
			{
				return SignedBytes.saturatedCast(Math.min(stack.stackSize / 32, 15) + 1);
			}

			@Override
			public byte getMiniItemCount(ItemStack stack, byte original)
			{
				return SignedBytes.saturatedCast(Math.min(stack.stackSize / 32, 7) + 1);
			}

			@Override
			public boolean shouldBob()
			{
				return true;
			}

			@Override
			public boolean shouldSpreadItems()
			{
				return false;
			}
		};
		itemRenderer.setRenderManager(RenderManager.instance);
	}

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d0, double d1, double d2, float f)
	{
	}
}
