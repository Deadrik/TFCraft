package com.bioxx.tfc.Items.ItemBlocks;

import com.bioxx.tfc.api.Constant.Global;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;

public class ItemSed extends ItemTerraBlock
{
	public ItemSed(Block par1) 
	{
		super(par1);
		MetaNames = Global.STONE_SED;
	}
	@Override
	public void registerIcons(IIconRegister registerer)
    {

    }
}
