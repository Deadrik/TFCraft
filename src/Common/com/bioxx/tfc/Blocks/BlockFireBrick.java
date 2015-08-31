package com.bioxx.tfc.Blocks;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Core.TFCTabs;

public class BlockFireBrick extends BlockTerra
{
	public BlockFireBrick()
	{
		super(Material.rock);
		this.setCreativeTab(TFCTabs.TFC_BUILDING);
	}

	@Override
	public void registerBlockIcons(IIconRegister iconRegisterer)
	{
		this.blockIcon = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "rocks/Fire Brick");
	}
}
