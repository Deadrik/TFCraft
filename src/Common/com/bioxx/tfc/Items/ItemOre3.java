package com.bioxx.tfc.Items;

import com.bioxx.tfc.Items.ItemBlocks.ItemTerraBlock;

import net.minecraft.block.Block;

public class ItemOre3 extends ItemTerraBlock
{
	public ItemOre3(Block par1) 
	{
		super(par1);
		setHasSubtypes(true);
		this.MetaNames = new String[]{"Borax", "Olivine", "Lapis Lazuli"};
		this.setFolder("ore/");
	}
}
