package com.bioxx.tfc.Blocks.Terrain;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class BlockDirt2 extends BlockDirt
{
	public BlockDirt2(int texOff, Block Farm)
	{
		super(texOff, Farm);
		this.setCreativeTab(CreativeTabs.tabBlock);
	}

	@Override
	public int damageDropped(int i)
	{
		return i;
	}

	@Override
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		for(int i = 0; i < 5; i++)
			par3List.add(new ItemStack(par1, 1, i));
	}
}
