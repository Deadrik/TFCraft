package TFC.Items;

import java.util.List;

import TFC.Core.TFC_Settings;
import TFC.Enums.EnumSize;
import net.minecraft.src.*;

public class ItemCustomKnife extends ItemWeapon
{
	private int weaponDamage = 1;
	public ItemCustomKnife(int i, EnumToolMaterial e)
	{
		super(i, e);
		this.setMaxDamage(e.getMaxUses());
		this.weaponDamage = 1 + e.getDamageVsEntity();
	}

	public String getTextureFile() {
		return "/bioxx/terratools.png";
	}
	
	public EnumSize getSize()
	{
		return EnumSize.SMALL;
	}
	
	@Override
	public boolean canStack() {
		// TODO Auto-generated method stub
		return false;
	}

}