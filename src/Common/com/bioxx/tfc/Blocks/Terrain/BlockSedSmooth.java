package com.bioxx.tfc.Blocks.Terrain;

import com.bioxx.tfc.api.Constant.Global;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.IIcon;

public class BlockSedSmooth extends BlockSmooth
{
	public BlockSedSmooth()
	{
		super(Material.rock);
		this.setCreativeTab(CreativeTabs.tabBlock);
		names = Global.STONE_SED;
		icons = new IIcon[names.length];
	}
}