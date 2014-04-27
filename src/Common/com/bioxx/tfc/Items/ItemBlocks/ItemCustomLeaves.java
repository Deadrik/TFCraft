package com.bioxx.tfc.Items.ItemBlocks;

import com.bioxx.tfc.api.Constant.Global;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;

public class ItemCustomLeaves extends ItemTerraBlock
{
	public ItemCustomLeaves(Block par1)
	{
		super(par1);
		setMaxDamage(0);
		setHasSubtypes(true);
		MetaNames = Global.WOOD_ALL;
	}
	
	@Override
	public void registerIcons(IIconRegister registerer)
    {

    }
}
