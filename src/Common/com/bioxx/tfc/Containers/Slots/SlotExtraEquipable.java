package com.bioxx.tfc.Containers.Slots;

import com.bioxx.tfc.Containers.ContainerPlayerTFC;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SlotExtraEquipable extends Slot 
{
	public final int armorType;
	public SlotExtraEquipable(IInventory inv, int index, int x, int y, int armortype) 
	{
		super(inv, index, x, y);
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
	
	@Override
	public ItemStack getStack(){
		//System.out.println("getting itemstack " + super.getStack());
		return super.getStack();
	}
	
	/**
	 * Leave as-is for now. In the future, modify it to allow non-armor items to be in here. Set up a method to register certain item
	 * requirements for each slot.
	 */
	@Override
	public boolean isItemValid(ItemStack par1ItemStack)
	{
		Item item = (par1ItemStack == null ? null : par1ItemStack.getItem());
		return item != null /*&& (item instanceof ItemArmor)?((ItemArmor))*/; //modify to allow non-quiver items in.
	}
}
