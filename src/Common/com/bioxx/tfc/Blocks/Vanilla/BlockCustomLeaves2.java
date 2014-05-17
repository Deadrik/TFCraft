package com.bioxx.tfc.Blocks.Vanilla;

import java.util.Random;

import net.minecraft.item.Item;
import net.minecraft.util.IIcon;

import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.api.Constant.Global;

public class BlockCustomLeaves2 extends BlockCustomLeaves
{
	public BlockCustomLeaves2()
	{
		super();
		woodNames = new String[Global.WOOD_ALL.length - 16];
		System.arraycopy(Global.WOOD_ALL, 16, woodNames, 0, Global.WOOD_ALL.length - 16);
		icons = new IIcon[woodNames.length];
		iconsOpaque = new IIcon[woodNames.length];
	}

	@Override
	public Item getItemDropped(int i, Random rand, int j)
	{
		return Item.getItemFromBlock(TFCBlocks.Sapling2);
	}
}
