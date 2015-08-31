package com.bioxx.tfc.Blocks.Terrain;

import net.minecraft.client.renderer.texture.IIconRegister;

import com.bioxx.tfc.Reference;

public class BlockMMBrick extends BlockMMSmooth
{
	public BlockMMBrick()
	{
		super();
	}

	@Override
	public void registerBlockIcons(IIconRegister iconRegisterer)
	{
		for(int i = 0; i < names.length; i++)
			icons[i] = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "rocks/"+names[i]+" Brick");
	}
}
