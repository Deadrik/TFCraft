package com.bioxx.tfc.api.Interfaces;

import net.minecraft.item.ItemStack;

public interface IEquipable 
{
	public EquipType getEquipType(ItemStack is);

	public static enum EquipType
	{
		BACK, NULL;
	}
	
	public void onEquippedRender();
}
