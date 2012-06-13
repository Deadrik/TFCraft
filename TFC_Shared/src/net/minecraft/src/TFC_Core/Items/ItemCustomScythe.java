package net.minecraft.src.TFC_Core.Items;

import net.minecraft.src.*;
import net.minecraft.src.forge.ITextureProvider;

public class ItemCustomScythe extends ItemTool
implements ITextureProvider
{
    static Block[] blocks = {Block.blocksList[18]};
	private int weaponDamage;
	public ItemCustomScythe(int i, EnumToolMaterial e)
	{
		super(i, e.getDamageVsEntity(),e, blocks);
		this.setMaxDamage(e.getMaxUses());
		this.weaponDamage = e.getDamageVsEntity();
		this.efficiencyOnProperMaterial = e.getEfficiencyOnProperMaterial();
	}

	public int getDamageVsEntity(Entity par1Entity)
	{
		return this.weaponDamage;
	}

	public String getTextureFile() {
		return "/bioxx/terratools.png";
	}

	public boolean hitEntity(ItemStack par1ItemStack, EntityLiving par2EntityLiving, EntityLiving par3EntityLiving)
	{
		par1ItemStack.damageItem(1, par3EntityLiving);
		return true;
	}
}