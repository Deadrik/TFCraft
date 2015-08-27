package com.bioxx.tfc.Containers.Slots;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.Containers.ContainerPlayerTFC;

public class SlotArmorTFC extends Slot 
{
	public final int armorType;
	private final ContainerPlayerTFC parent;
	public SlotArmorTFC(ContainerPlayerTFC cont, IInventory inv, int index, int x, int y, int armortype) 
	{
		super(inv, index, x, y);
		this.parent = cont;
		armorType = armortype;
	}


	/**
	 * Returns the maximum stack size for a given slot (usually the same as getInventoryStackLimit(), but 1 in the case
	 * of armor slots)
	 */
	@Override
	public int getSlotStackLimit()
	{
		return 1;
	}

	/**
	 * Check if the stack is a valid item for this slot. Always true beside for the armor slots.
	 */
	@Override
	public boolean isItemValid(ItemStack par1ItemStack)
	{
		Item item = (par1ItemStack == null ? null : par1ItemStack.getItem());
		return item != null && item.isValidArmor(par1ItemStack, armorType, parent.getPlayer());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getBackgroundIconIndex()
	{
		return ItemArmor.func_94602_b(this.armorType);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean func_111238_b()
	{
		return true;
	}

}
