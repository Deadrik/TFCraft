package com.bioxx.tfc;

import net.minecraft.block.Block;

import com.bioxx.tfc.Core.TFC_Core;

public class ServerOverrides
{
	public static int isValidSurface(Block b)
	{
		if(TFC_Core.isFence(b) || (b != null && b.getRenderType() == 11))
			return 11;
		return b.getRenderType();
	}
}
