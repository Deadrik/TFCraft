package com.bioxx.tfc.Blocks.Liquids;

import net.minecraft.block.material.Material;

import com.bioxx.tfc.Core.TFCFluid;

public class BlockLava extends BlockCustomLiquid
{
	public BlockLava()
	{
		super(TFCFluid.LAVA, Material.lava);
	}
}
