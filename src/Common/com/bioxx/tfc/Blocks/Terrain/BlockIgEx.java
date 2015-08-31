package com.bioxx.tfc.Blocks.Terrain;

import net.minecraft.block.material.Material;
import net.minecraft.util.IIcon;

import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.Constant.Global;

public class BlockIgEx extends BlockStone
{
	public BlockIgEx(Material material)
	{
		super(material);
		this.dropBlock = TFCBlocks.stoneIgExCobble;
		names = Global.STONE_IGEX;
		icons = new IIcon[names.length];
		looseStart = Global.STONE_IGEX_START;
		gemChance = 0;
	}
}
