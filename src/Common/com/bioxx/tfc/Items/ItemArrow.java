package com.bioxx.tfc.Items;

import com.bioxx.tfc.api.IQuiverAmmo;
import com.bioxx.tfc.api.Enums.EnumAmmo;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;

import net.minecraft.client.renderer.texture.IIconRegister;

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
