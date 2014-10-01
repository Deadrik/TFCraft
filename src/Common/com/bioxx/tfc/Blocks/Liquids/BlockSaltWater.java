package com.bioxx.tfc.Blocks.Liquids;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.Fluid;

import com.bioxx.tfc.TFCBlocks;

public class BlockSaltWater extends BlockCustomLiquid
{
	public BlockSaltWater(Fluid fluid)
	{
		super(fluid, Material.water);
	}

	@Override
	protected Block getInverseBlock() 
	{
		return TFCBlocks.SaltWaterStationary;
	}
}
