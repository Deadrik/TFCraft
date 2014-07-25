package com.bioxx.tfc.Blocks.Flora;

import net.minecraft.util.IIcon;

import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.api.Constant.Global;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockLogHoriz2 extends BlockLogHoriz
{
	public BlockLogHoriz2(int off)
	{
		super(off);
		woodNames = new String[Global.WOOD_ALL.length - 16];
		if(16 + off < Global.WOOD_ALL.length)
			System.arraycopy(Global.WOOD_ALL, 16 + off, woodNames, 0, Global.WOOD_ALL.length - 16 > off ? off : Global.WOOD_ALL.length - 16);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		int dir = meta >> 3;
		meta = (meta & 7) + offset;

		if(dir == 0)
		{
			if(side == 0 || side == 1)
				return ((BlockLogNatural2)TFCBlocks.LogNatural2).sideIcons[meta];
			else if(side == 2 || side == 3)
				return ((BlockLogNatural2)TFCBlocks.LogNatural2).innerIcons[meta];
			else
				return ((BlockLogNatural2)TFCBlocks.LogNatural2).rotatedSideIcons[meta];
		}
		else
		{
			if(side == 0 || side == 1 || side == 2 || side == 3)
				return ((BlockLogNatural2)TFCBlocks.LogNatural2).rotatedSideIcons[meta];
			else
				return ((BlockLogNatural2)TFCBlocks.LogNatural2).innerIcons[meta];
		}
	}

	@Override
	public int damageDropped(int dmg)
	{
		return (dmg & 7) + offset + 16;
	}
}
