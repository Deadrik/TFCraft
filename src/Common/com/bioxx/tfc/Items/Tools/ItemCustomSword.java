package com.bioxx.tfc.Items.Tools;

import net.minecraft.item.ItemStack;

import com.bioxx.tfc.api.Enums.EnumDamageType;
import com.bioxx.tfc.api.Enums.EnumItemReach;

public class ItemCustomSword extends ItemWeapon
{
	public ItemCustomSword(ToolMaterial par2EnumToolMaterial, float damage, EnumDamageType dt)
	{
		super(par2EnumToolMaterial, damage);
		//this.weaponDamage = 150 + par2EnumToolMaterial.getDamageVsEntity();
		this.damageType = dt;
	}

	public ItemCustomSword(ToolMaterial par2EnumToolMaterial, float damage)
	{
		super(par2EnumToolMaterial, damage);
		//this.weaponDamage = 150 + par2EnumToolMaterial.getDamageVsEntity();
		this.damageType = EnumDamageType.SLASHING;
	}

	@Override
	public EnumItemReach getReach(ItemStack is)
	{
		return EnumItemReach.MEDIUM;
	}
}
