package com.bioxx.tfc.Render;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.TileEntities.TEChest;
import com.bioxx.tfc.api.Constant.Global;

@SideOnly(Side.CLIENT)
public class TESRChestHelper
{
	public static TESRChestHelper instance = new TESRChestHelper();
	private TEChest[] chestTypes;

	public TESRChestHelper()
	{
		chestTypes = new TEChest[Global.WOOD_ALL.length*2];
		for(int i = 0; i < Global.WOOD_ALL.length; i++)
		{
			chestTypes[i] = new TEChest(i, false);
			chestTypes[Global.WOOD_ALL.length+i] = new TEChest(i, true);
		}
	}
	/**
	 * Renders a chest at 0,0,0 - used for item rendering
	 */
	public void renderChest(Block block, int meta, float modelID)
	{
		TileEntityRendererDispatcher.instance.renderTileEntityAt(chestTypes[meta], 0.0D, 0.0D, 0.0D, 0.0F);
	}
}
