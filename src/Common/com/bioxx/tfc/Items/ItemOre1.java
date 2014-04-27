package com.bioxx.tfc.Items;

import com.bioxx.tfc.Items.ItemBlocks.ItemTerraBlock;

import net.minecraft.block.Block;

public class ItemOre1 extends ItemTerraBlock
{
	public ItemOre1(Block par1) 
	{
		super(par1);
		setHasSubtypes(true);
		this.MetaNames = new String[]{"Native Copper", "Native Gold", "Native Platinum", "Hematite", "Native Silver", "Cassiterite", "Galena", "Bismuthinite", "Garnierite", 
				"Malachite", "Magnetite", "Limonite", "Sphalerite", "Tetrahedrite", 
				"Bituminous Coal", "Lignite"};
		this.setFolder("ore/");
	}
}
