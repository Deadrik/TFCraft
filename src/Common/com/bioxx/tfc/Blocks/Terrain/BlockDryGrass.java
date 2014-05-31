package com.bioxx.tfc.Blocks.Terrain;

import net.minecraft.client.renderer.texture.IIconRegister;

import com.bioxx.tfc.Reference;

public class BlockDryGrass extends BlockGrass
{
	public BlockDryGrass(int texOff)
	{
		super(texOff);
	}

	@Override
	public void registerBlockIcons(IIconRegister registerer)
	{
		super.registerBlockIcons(registerer);
		GrassTopTexture = registerer.registerIcon(Reference.ModID + ":" + "GrassSparseOverlay");
		iconGrassSideOverlay = registerer.registerIcon(Reference.ModID + ":" + "GrassSideSparse");
	}
}
