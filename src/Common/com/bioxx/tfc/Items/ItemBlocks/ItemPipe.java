package com.bioxx.tfc.Items.ItemBlocks;

import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;

public class ItemPipe extends ItemTerraBlock
{
	public ItemPipe(Block par1) 
	{
		super(par1);
	}
	
	@Override
	public void registerIcons(IIconRegister registerer)
	{

	}
	
	@Override
	public EnumSize getSize(ItemStack is) {
		return EnumSize.MEDIUM;
	}

	@Override
	public EnumWeight getWeight(ItemStack is) {
		return EnumWeight.MEDIUM;
	}
}