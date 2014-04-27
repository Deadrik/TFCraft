package com.bioxx.tfc.Containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ContainerSkills extends ContainerTFC
{
	public ContainerSkills(EntityPlayer player) 
	{
		this.player = player;
	}

	@Override
	public void putStackInSlot(int par1, ItemStack par2ItemStack)
	{
		this.player.inventoryContainer.getSlot(par1).putStack(par2ItemStack);
	}
}
