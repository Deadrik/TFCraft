package TFC.Items.Tools;

import TFC.API.Enums.EnumDamageType;

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
}
