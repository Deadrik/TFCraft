package com.bioxx.tfc.Blocks.Liquids;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.Core.TFCFluid;

public class BlockLava extends BlockCustomLiquid
{
	public BlockLava()
	{
		super(TFCFluid.LAVA, Material.lava);
	}

	@Override
	protected Block getInverseBlock() 
	{
		return TFCBlocks.Lava;
	}
}
