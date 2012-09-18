package TFC.Items;

import TFC.Enums.EnumSize;
import net.minecraft.src.*;

public class ItemCustomSword extends ItemWeapon
{
	public ItemCustomSword(int par1, EnumToolMaterial par2EnumToolMaterial)
	{
		super(par1, par2EnumToolMaterial);

	}

	public String getTextureFile() 
	{
		return "/bioxx/terratools.png";
	}
}
