package com.bioxx.tfc.Items.ItemBlocks;

import com.bioxx.tfc.api.Constant.Global;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;

public class ItemFence2 extends ItemTerraBlock
{
	public ItemFence2(Block par1) 
	{
		super(par1);
		MetaNames = new String[Global.WOOD_ALL.length - 16];
		System.arraycopy(Global.WOOD_ALL, 16, MetaNames, 0, Global.WOOD_ALL.length - 16);
	}
	
	@Override
	public void registerIcons(IIconRegister registerer)
	{

	}
}