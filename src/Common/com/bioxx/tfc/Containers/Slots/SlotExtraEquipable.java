package com.bioxx.tfc.Containers.Slots;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.bioxx.tfc.api.Interfaces.IEquipable;

public class SlotExtraEquipable extends Slot 
{
	public final IEquipable.EquipType armorType;
	public SlotExtraEquipable(IInventory inv, int index, int x, int y, IEquipable.EquipType armortype) 
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

	/*@Override
	public ItemStack getStack(){
		//TerraFirmaCraft.log.info("getting itemstack " + super.getStack());
		return super.getStack();
	}*/

	/**
	 * Leave as-is for now. In the future, modify it to allow non-armor items to be in here. Set up a method to register certain item
	 * requirements for each slot.
	 */
	@Override
	public boolean isItemValid(ItemStack is)
	{
		if(is != null && is.getItem() instanceof IEquipable)
			if(((IEquipable)is.getItem()).getEquipType(is) == armorType)
				return true;

		return false;
	}
}
