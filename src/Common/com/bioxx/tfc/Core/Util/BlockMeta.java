package com.bioxx.tfc.Core.Util;

import net.minecraft.block.Block;

public class BlockMeta
{
	public Block block;
	public int meta;
	
	public BlockMeta(Block b, int m)
	{
		block = b;
		meta = m;
	}
}
