package com.bioxx.tfc.Blocks.Terrain;

import net.minecraft.client.renderer.texture.IIconRegister;

import com.bioxx.tfc.Reference;

public class BlockIgExBrick extends BlockIgExSmooth
{
	public BlockIgExBrick()
	{
		super();
	}

	@Override
	public void registerBlockIcons(IIconRegister iconRegisterer)
	{
		for(int i = 0; i < names.length; i++)
			icons[i] = iconRegisterer.registerIcon(Reference.ModID + ":" + "rocks/"+names[i]+" Brick");
	}
}
