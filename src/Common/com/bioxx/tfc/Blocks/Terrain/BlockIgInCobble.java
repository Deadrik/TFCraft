package com.bioxx.tfc.Blocks.Terrain;

import com.bioxx.tfc.api.Constant.Global;

import net.minecraft.block.material.Material;
import net.minecraft.util.IIcon;

public class BlockIgInCobble extends BlockCobble
{
	public BlockIgInCobble(Material material)
	{
		super(material);
		fallInstantly = false;
		names = Global.STONE_IGIN;
		icons = new IIcon[names.length];
	}
}
