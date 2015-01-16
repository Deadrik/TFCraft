package com.bioxx.tfc.Blocks.Vanilla;

import net.minecraft.block.BlockButtonWood;
import net.minecraft.util.IIcon;

import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.api.TFCBlocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCustomButtonWood extends BlockButtonWood
{
	public BlockCustomButtonWood()
	{
		super();
		this.setCreativeTab(TFCTabs.TFCDevices);
	}

	/**
	 * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
	 */
	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int par1, int par2)
	{
		return TFCBlocks.Planks.getBlockTextureFromSide(0);
	}
}
