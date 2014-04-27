package com.bioxx.tfc.Blocks.Terrain;

import com.bioxx.tfc.api.Constant.Global;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.IIcon;

public class BlockIgInSmooth extends BlockSmooth
{
	public BlockIgInSmooth()
	{
		super(Material.rock);
		this.setCreativeTab(CreativeTabs.tabBlock);
		names = Global.STONE_IGIN;
		icons = new IIcon[names.length];
	}
}
