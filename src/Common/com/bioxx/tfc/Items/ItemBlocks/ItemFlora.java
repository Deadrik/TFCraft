package com.bioxx.tfc.Items.ItemBlocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;

public class ItemFlora extends ItemTerraBlock
{
	public ItemFlora(Block par1) 
	{
		super(par1);
		metaNames = new String[]{"Golden Rod", "Cat Tails"};
	}

	@Override
	public void registerIcons(IIconRegister registerer)
	{

	}
}
