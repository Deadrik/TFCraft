package com.bioxx.tfc.Blocks.Flora;

import net.minecraft.util.IIcon;

import com.bioxx.tfc.api.Constant.Global;

public class BlockLogVert2 extends BlockLogVert
{
	public BlockLogVert2()
	{
		super();
		woodNames = new String[Global.WOOD_ALL.length - 16];
		System.arraycopy(Global.WOOD_ALL, 16, woodNames, 0, Global.WOOD_ALL.length - 16);
	}

	@Override
	public int damageDropped(int dmg)
	{
		return dmg += 16;
	}

	@Override
	public IIcon getIcon(int side, int meta)
	{
		if (side == 1)
			return BlockLogNatural2.innerIcons[meta];
		if (side == 0)
			return BlockLogNatural2.innerIcons[meta];
		return BlockLogNatural2.sideIcons[meta];
	}
}
