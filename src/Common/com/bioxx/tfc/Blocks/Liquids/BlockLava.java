package com.bioxx.tfc.Blocks.Liquids;

import net.minecraft.block.material.Material;

import com.bioxx.tfc.api.TFCFluids;

public class BlockLava extends BlockCustomLiquid
{
	public BlockLava()
	{
		super(TFCFluids.LAVA, Material.lava);
	}
}
