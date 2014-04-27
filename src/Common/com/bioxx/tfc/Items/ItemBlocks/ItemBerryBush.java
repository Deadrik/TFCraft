package com.bioxx.tfc.Items.ItemBlocks;

import com.bioxx.tfc.Blocks.Flora.BlockBerryBush;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;

public class ItemBerryBush extends ItemTerraBlock
{
	public ItemBerryBush(Block par1) 
	{
		super(par1);
		MetaNames = BlockBerryBush.MetaNames;
	}

	@Override
	public void registerIcons(IIconRegister registerer)
	{

	}
}
