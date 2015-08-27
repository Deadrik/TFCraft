package com.bioxx.tfc.api.Interfaces;

import net.minecraft.item.ItemStack;

public interface IEquipable 
{
	EquipType getEquipType(ItemStack is);

	static enum EquipType
	{
		BACK, NULL;
	}

	void onEquippedRender();

	boolean getTooHeavyToCarry(ItemStack is);
}
