package com.bioxx.tfc.Items;

import com.bioxx.tfc.Core.TFCTabs;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemWoolYarn extends ItemTerra
{
	public ItemWoolYarn()
	{
		super();
		this.hasSubtypes = false;
		this.setMaxDamage(0);
		this.setCreativeTab(TFCTabs.TFCMaterials);
	}

	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) 
	{
	}
}
