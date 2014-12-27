package com.bioxx.tfc.Blocks.Vanilla;

import net.minecraft.block.BlockPumpkin;
import net.minecraft.util.IIcon;

import com.bioxx.tfc.Core.TFCTabs;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCustomPumpkin extends BlockPumpkin
{
	public BlockCustomPumpkin(boolean par2)
	{
		super(par2);
		this.setCreativeTab(TFCTabs.TFCBuilding);
	}

	@Override
	@SideOnly(Side.CLIENT)
	/**
	 * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
	 */
	public IIcon getIcon(int par1, int par2)
	{
		return par1 == 1 ? super.getIcon(par1, par2) : (par1 == 0 ? super.getIcon(par1, par2) : this.blockIcon); //Removes face from unlit pumpkins.
	}

}
