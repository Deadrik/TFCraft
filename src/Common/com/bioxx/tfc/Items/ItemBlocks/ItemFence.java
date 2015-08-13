package com.bioxx.tfc.Items.ItemBlocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;

import com.bioxx.tfc.api.Constant.Global;

public class ItemFence extends ItemTerraBlock
{
	public ItemFence(Block par1) 
	{
		super(par1);
		MetaNames = new String[16];
		System.arraycopy(Global.WOOD_ALL, 0, MetaNames, 0, 16);
	}
	
	@Override
	public void registerIcons(IIconRegister registerer)
	{

	}
}