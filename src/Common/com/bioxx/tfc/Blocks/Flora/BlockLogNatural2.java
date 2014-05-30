package com.bioxx.tfc.Blocks.Flora;

import net.minecraft.util.IIcon;

import com.bioxx.tfc.api.Constant.Global;

public class BlockLogNatural2 extends BlockLogNatural
{
	public BlockLogNatural2()
	{
		super();
		System.arraycopy(Global.WOOD_ALL, 16, woodNames, 0, Global.WOOD_ALL.length - 16);
		sideIcons = new IIcon[woodNames.length];
		innerIcons = new IIcon[woodNames.length];
		rotatedSideIcons = new IIcon[woodNames.length];
	}

	@Override
	public int damageDropped(int dmg)
	{
		return dmg += 16;
	}

	public int getItemDamage(int dmg)
	{
		return dmg += 16;
	}
}
