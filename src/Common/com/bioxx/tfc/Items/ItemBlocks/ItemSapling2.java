package com.bioxx.tfc.Items.ItemBlocks;

import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Enums.EnumWeight;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;

public class ItemSapling2 extends ItemTerraBlock
{
	public ItemSapling2(Block par1)
	{
		super(par1);
		setMaxDamage(0);
		setHasSubtypes(true);
		MetaNames = Global.WOOD_ALL;
	}
	
	@Override
	public int getMetadata(int i)
	{
		return i;
	}

	@Override
	public void registerIcons(IIconRegister registerer)
	{
	}

	@Override
	public EnumWeight getWeight(ItemStack is)
	{
		return EnumWeight.MEDIUM;
	}
}
