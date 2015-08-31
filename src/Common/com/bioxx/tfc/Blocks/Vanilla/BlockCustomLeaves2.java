package com.bioxx.tfc.Blocks.Vanilla;

import java.util.Random;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import com.bioxx.tfc.api.TFCBlocks;
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
		return Item.getItemFromBlock(TFCBlocks.sapling2);
	}

	@Override
	protected void dropSapling(World world, int x, int y, int z, int meta)
	{
		if (meta != 0)
			dropBlockAsItem(world, x, y, z, new ItemStack(this.getItemDropped(0, null, 0), 1, meta));
	}
}
