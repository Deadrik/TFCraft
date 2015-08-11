package com.bioxx.tfc.Items;

import net.minecraft.client.renderer.texture.IIconRegister;

import com.bioxx.tfc.api.Enums.EnumAmmo;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;
import com.bioxx.tfc.api.Interfaces.IQuiverAmmo;

public class ItemArrow extends ItemTerra implements IQuiverAmmo
{
	public ItemArrow()
	{
		super();
		this.setSize(EnumSize.LARGE);
		this.setWeight(EnumWeight.LIGHT);
	}

	@Override
	public void registerIcons(IIconRegister registerer)
	{
		this.itemIcon = registerer.registerIcon("minecraft:arrow");
	}

	@Override
	public EnumAmmo getAmmoType() 
	{
		return EnumAmmo.ARROW;
	}
}
