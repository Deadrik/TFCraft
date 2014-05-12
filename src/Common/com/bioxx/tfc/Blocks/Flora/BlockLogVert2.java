package com.bioxx.tfc.Blocks.Flora;

import net.minecraft.util.IIcon;

import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.api.Constant.Global;

public class BlockLogVert2 extends BlockLogVert
{
	public BlockLogVert2()
	{
		super();
		woodNames = new String[Global.WOOD_ALL.length - 16];
		System.arraycopy(Global.WOOD_ALL, 16, woodNames, 0, Global.WOOD_ALL.length - 16);
		//this.setCreativeTab(CreativeTabs.tabDecorations);
	}

	@Override
	public int damageDropped(int j) {
		return j+=16;
	}

	@Override
	public IIcon getIcon(int i, int j) 
	{
		if (i == 1)
			return BlockLogNatural2.innerIcons[j];
		if (i == 0)
			return BlockLogNatural2.innerIcons[j];
		return BlockLogNatural2.sideIcons[j];
	}
}
