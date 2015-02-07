package com.bioxx.tfc.Blocks.Terrain;

import net.minecraft.block.material.Material;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import com.bioxx.tfc.api.Constant.Global;

public class BlockSedCobble extends BlockCobble
{
	public BlockSedCobble(Material material) {
		super(material);
		names = Global.STONE_SED;
		icons = new IIcon[names.length];
	}

	@Override
	public int tickRate(World world)
	{
		return 3;
	}
}
