package com.bioxx.tfc;

import net.minecraft.block.Block;

import com.bioxx.tfc.Core.TFC_Core;

public class ServerOverrides
{
	public static boolean isValidSurface(Block b)
	{
		return TFC_Core.isFence(b) || (b != null && b.getRenderType() == 11);
	}
}
