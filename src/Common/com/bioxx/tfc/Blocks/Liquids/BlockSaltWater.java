package com.bioxx.tfc.Blocks.Liquids;

import net.minecraft.block.material.Material;

import net.minecraftforge.fluids.Fluid;

public class BlockSaltWater extends BlockCustomLiquid
{
	public BlockSaltWater(Fluid fluid)
	{
		super(fluid, Material.water);
	}
}
