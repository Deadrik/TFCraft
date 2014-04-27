package com.bioxx.tfc.Items;

import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemBackpack extends ItemTerra
{
	public ItemBackpack() 
	{
		super();
		this.setWeight(EnumWeight.HEAVY);
		this.setSize(EnumSize.HUGE);
	}

	@Override
	public void onCreated(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) 
	{
		//Add the itemstacks for the inventory.
	}
}
