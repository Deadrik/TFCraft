package com.bioxx.tfc.Blocks.Flora;

import net.minecraft.util.IIcon;

import com.bioxx.tfc.api.Constant.Global;

public class BlockLogHoriz2 extends BlockLogHoriz
{
	public BlockLogHoriz2(int off)
	{
		super(off);
		woodNames = new String[Global.WOOD_ALL.length - 16];
		if(16+off < Global.WOOD_ALL.length)
			System.arraycopy(Global.WOOD_ALL, 16 + off, woodNames, 0, Global.WOOD_ALL.length-16>off?off:Global.WOOD_ALL.length-16);
	}

	@Override
	public IIcon getIcon(int i, int j) 
	{
		int meta = (j & 7) + offset;
		int dir = j >> 3;

		if(dir == 0)
		{
			if(i == 0) {
				return BlockLogNatural2.sideIcons[meta];
			} else if(i == 1) {
				return BlockLogNatural2.sideIcons[meta];
			} else if(i == 2) {
				return BlockLogNatural2.innerIcons[meta];
			} else if(i == 3) {
				return BlockLogNatural2.innerIcons[meta];
			} else if(i == 4) {
				return BlockLogNatural2.rotatedSideIcons[meta];
			} else {
				return BlockLogNatural2.rotatedSideIcons[meta];
			}
		}
		else
		{
			if(i == 0) {
				return BlockLogNatural2.rotatedSideIcons[meta];
			} else if(i == 1) {
				return BlockLogNatural2.rotatedSideIcons[meta];
			} else if(i == 2) {
				return BlockLogNatural2.rotatedSideIcons[meta];
			} else if(i == 3) {
				return BlockLogNatural2.rotatedSideIcons[meta];
			} else if(i == 4) {
				return BlockLogNatural2.innerIcons[meta];
			} else {
				return BlockLogNatural2.innerIcons[meta];
			}
		}
	}

	@Override
	public int damageDropped(int j)
	{
		return (j & 7) + offset + 16;
	}
}
