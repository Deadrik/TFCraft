package com.bioxx.tfc.Blocks.Flora;

import net.minecraft.util.IIcon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.api.Constant.Global;

public class BlockLogNatural2 extends BlockLogNatural
{
	public BlockLogNatural2()
	{
		super();
		this.woodNames = new String[Global.WOOD_ALL.length - 16];
		System.arraycopy(Global.WOOD_ALL, 16, woodNames, 0, Global.WOOD_ALL.length - 16);
		this.sideIcons = new IIcon[woodNames.length];
		this.innerIcons = new IIcon[woodNames.length];
		this.rotatedSideIcons = new IIcon[woodNames.length];
	}

	@Override
	public int damageDropped(int dmg)
	{
		return dmg += 16;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		if (meta >= woodNames.length)
			meta = 0;
		if (side == 0 || side == 1)
			return innerIcons[meta];
		return sideIcons[meta];
	}
}
