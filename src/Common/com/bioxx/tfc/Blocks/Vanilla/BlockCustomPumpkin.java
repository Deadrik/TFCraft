package com.bioxx.tfc.Blocks.Vanilla;

import net.minecraft.block.BlockPumpkin;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCustomPumpkin extends BlockPumpkin
{
	public BlockCustomPumpkin(boolean par2)
	{
		super(par2);
	}

	@Override
	@SideOnly(Side.CLIENT)
	/**
	 * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
	 */
	public IIcon getIcon(int par1, int par2)
	{
		return super.getIcon(par1, par2);
	}

}
