package com.bioxx.tfc.Containers.Slots;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SlotForShowOnly extends Slot
{
	public SlotForShowOnly(IInventory iinventory, int i, int j, int k)
	{
		super(iinventory, i, j, k);
	}

	@Override
	public boolean canTakeStack(EntityPlayer par1EntityPlayer)
	{
		return false;
	}

	@Override
	public boolean isItemValid(ItemStack par1ItemStack)
	{
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean func_111238_b()
	{
		return false;
	}
}
