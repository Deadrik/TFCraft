package com.bioxx.tfc.Items.ItemBlocks;

import net.minecraft.block.Block;
import net.minecraft.util.IIcon;

import com.bioxx.tfc.api.Constant.Global;

public class ItemSapling2 extends ItemSapling
{
	public ItemSapling2(Block b)
	{
		super(b);
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
		this.MetaNames = new String[Global.WOOD_ALL.length - 16];
		System.arraycopy(Global.WOOD_ALL, 16, MetaNames, 0, Global.WOOD_ALL.length - 16);
		this.icons = new IIcon[MetaNames.length];
	}
}
