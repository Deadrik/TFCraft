package TFC.Items;

import java.util.List;

import TFC.*;
import TFC.Core.TFC_Settings;
import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EnumToolMaterial;
import net.minecraft.src.ItemStack;

public class ItemCustomScythe extends ItemTerraTool
{
    static Block[] blocks = {Block.blocksList[18]};
	private int weaponDamage;
	public ItemCustomScythe(int i, EnumToolMaterial e)
	{
		super(i, e.getDamageVsEntity(),e, blocks);
		this.setMaxDamage(e.getMaxUses()*3);
		this.weaponDamage = e.getDamageVsEntity();
		this.efficiencyOnProperMaterial = e.getEfficiencyOnProperMaterial();
	}

	@Override
	public int getDamageVsEntity(Entity par1Entity)
	{
		return this.weaponDamage;
	}

	@Override
	public boolean hitEntity(ItemStack par1ItemStack, EntityLiving par2EntityLiving, EntityLiving par3EntityLiving)
	{
		par1ItemStack.damageItem(1, par3EntityLiving);
		return true;
	}

}