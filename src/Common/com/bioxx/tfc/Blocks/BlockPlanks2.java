package com.bioxx.tfc.Blocks;

import net.minecraft.block.material.Material;
import net.minecraft.util.IIcon;

import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.api.Constant.Global;

public class BlockPlanks2 extends BlockPlanks
{
	public BlockPlanks2(Material material)
	{
		super(Material.wood);
		this.setCreativeTab(TFCTabs.TFC_BUILDING);
		woodNames = new String[Global.WOOD_ALL.length-16];
		System.arraycopy(Global.WOOD_ALL, 16, woodNames, 0, Global.WOOD_ALL.length-16);
		icons = new IIcon[woodNames.length];
	}
}
