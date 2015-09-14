package com.bioxx.tfc.Blocks.Flora;

import net.minecraft.util.IIcon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.Constant.Global;

public class BlockLogHoriz2 extends BlockLogHoriz
{
	public BlockLogHoriz2(int off)
	{
		super(off);
		int size = Global.WOOD_ALL.length - 16 - off;
		if(size < 0) size = 0;
		woodNames = new String[size * 2];
		if(off < Global.WOOD_ALL.length - 16)
		{
			System.arraycopy(Global.WOOD_ALL, 16 + off, woodNames, 0, size);
			System.arraycopy(Global.WOOD_ALL, 16 + off, woodNames, size, size);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		int dir = meta >> 3;
		meta = (meta & 7) + offset; //NOPMD
		if (meta >= ((BlockLogNatural2) TFCBlocks.logNatural2).sideIcons.length)
			meta = 0;

		if(dir == 0)
		{
			if(side == 0 || side == 1)
				return ((BlockLogNatural2)TFCBlocks.logNatural2).sideIcons[meta];
			else if(side == 2 || side == 3)
				return ((BlockLogNatural2)TFCBlocks.logNatural2).innerIcons[meta];
			else
				return ((BlockLogNatural2)TFCBlocks.logNatural2).rotatedSideIcons[meta];
		}
		else
		{
			if(side == 0 || side == 1 || side == 2 || side == 3)
				return ((BlockLogNatural2)TFCBlocks.logNatural2).rotatedSideIcons[meta];
			else
				return ((BlockLogNatural2)TFCBlocks.logNatural2).innerIcons[meta];
		}
	}

	@Override
	public int damageDropped(int dmg)
	{
		return (dmg & 7) + offset + 16; //NOPMD
	}
}
