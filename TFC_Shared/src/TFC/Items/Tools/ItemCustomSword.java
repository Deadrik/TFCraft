package TFC.Items.Tools;

import net.minecraft.item.EnumToolMaterial;
import TFC.API.Enums.EnumDamageType;

public class ItemCustomSword extends ItemWeapon
{
	public ItemCustomSword(int par1, EnumToolMaterial par2EnumToolMaterial, float damage, EnumDamageType dt)
	{
		super(par1, par2EnumToolMaterial, damage);
		//this.weaponDamage = 150 + par2EnumToolMaterial.getDamageVsEntity();
		this.damageType = dt;
	}

	public ItemCustomSword(int par1, EnumToolMaterial par2EnumToolMaterial, float damage)
	{
		super(par1, par2EnumToolMaterial, damage);
		//this.weaponDamage = 150 + par2EnumToolMaterial.getDamageVsEntity();
		this.damageType = EnumDamageType.SLASHING;
	}
}
