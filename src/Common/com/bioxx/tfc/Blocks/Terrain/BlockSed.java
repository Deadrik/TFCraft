package com.bioxx.tfc.Blocks.Terrain;

import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.Constant.Global;

import net.minecraft.block.material.Material;
import net.minecraft.util.IIcon;

public class BlockSed extends BlockStone
{
	public BlockSed(Material material)
	{
		super(material);
		this.dropBlock = TFCBlocks.StoneSedCobble;
		names = Global.STONE_SED;
		icons = new IIcon[names.length];
		looseStart = Global.STONE_SED_START;
		gemChance = 1;
	}
}