package com.bioxx.tfc.Blocks.Liquids;

import net.minecraft.block.material.Material;

import net.minecraftforge.fluids.Fluid;

public class BlockFreshWater extends BlockCustomLiquid
{
	public BlockFreshWater(Fluid fluid)
	{
		super(fluid, Material.water);
	}
}
