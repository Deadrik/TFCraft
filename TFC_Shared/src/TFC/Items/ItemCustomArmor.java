package TFC.Items;

import net.minecraft.src.EnumArmorMaterial;
import net.minecraft.src.Item;
import net.minecraft.src.ItemArmor;

public class ItemCustomArmor extends ItemArmor
{

    public ItemCustomArmor(int par1, EnumArmorMaterial par2EnumArmorMaterial, int par3, int par4)
    {
        super(par1,par2EnumArmorMaterial,par3,par4);
    }
    
    public String getTextureFile() 
    {
        return "/bioxx/terraarmor1.png";
    }
}

