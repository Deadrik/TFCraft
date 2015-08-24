package com.bioxx.tfc.Containers.Slots;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.SlotCrafting;

public class SlotCraftingTFC extends SlotCrafting
{
	public SlotCraftingTFC(EntityPlayer par1EntityPlayer,
			IInventory par2iInventory, IInventory par3iInventory, int par4,
			int par5, int par6)
	{
		super(par1EntityPlayer, par2iInventory, par3iInventory, par4, par5, par6);
	}

	/*@Override
	public void onCrafting(ItemStack par1ItemStack)
	{
		super.onCrafting(par1ItemStack);
	}*/

}
