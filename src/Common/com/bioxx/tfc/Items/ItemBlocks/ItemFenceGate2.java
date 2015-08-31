package com.bioxx.tfc.Items.ItemBlocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;

import com.bioxx.tfc.api.Constant.Global;

public class ItemFenceGate2 extends ItemTerraBlock
{
	public ItemFenceGate2(Block par1) 
	{
		super(par1);
		metaNames = new String[Global.WOOD_ALL.length - 16];
		System.arraycopy(Global.WOOD_ALL, 16, metaNames, 0, Global.WOOD_ALL.length - 16);
	}
	
	@Override
	public void registerIcons(IIconRegister registerer)
	{

	}
}