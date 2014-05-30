package com.bioxx.tfc.Blocks.Devices;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.TFCItems;
import com.bioxx.tfc.Items.ItemBarrels2;
import com.bioxx.tfc.api.IMultipleBlock;
import com.bioxx.tfc.api.IPipeConnectable;
import com.bioxx.tfc.api.Constant.Global;

public class BlockBarrel2 extends BlockBarrel implements IMultipleBlock, IPipeConnectable
{
	private final Random random = new Random();
	String[] woodNames;

	public BlockBarrel2()
	{
		super();
		woodNames = new String[Global.WOOD_ALL.length - 16];
		System.arraycopy(Global.WOOD_ALL, 16, woodNames, 0, Global.WOOD_ALL.length - 16);
	}

	@Override
	protected ItemStack createStackedBlock(int par1)
	{
		int j = 0;
		String s = this.getUnlocalizedName();
		for(int i = 0; i < woodNames.length;i++)
			j = s.substring(s.indexOf("l",s.length()))==((ItemBarrels2)(TFCItems.Barrel)).MetaNames[i]?i:0;
		return new ItemStack(TFCItems.Barrel, 1, j);
	}
	
	@Override
	public Block getBlockTypeForRender()
	{
		return TFCBlocks.Planks2;
	}
}
