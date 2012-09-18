package TFC.Items;

import java.util.List;

import TFC.Core.HeatIndex;
import TFC.Core.HeatManager;
import TFC.Core.TFC_ItemHeat;
import TFC.Enums.EnumSize;
import TFC.Enums.EnumWeight;
import net.minecraft.src.EnumArmorMaterial;
import net.minecraft.src.Item;
import net.minecraft.src.ItemArmor;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;

public class ItemCustomArmor extends ItemArmor implements ISize
{

    public ItemCustomArmor(int par1, EnumArmorMaterial par2EnumArmorMaterial, int par3, int par4)
    {
        super(par1,par2EnumArmorMaterial,par3,par4);
    }
    
    public String getTextureFile() 
    {
        return "/bioxx/terraarmor1.png";
    }
    
    public int getItemStackLimit()
    {
    	if(canStack())
    		return this.getSize().stackSize * getWeight().multiplier;
    	else
    		return 1;
    }

	@Override
	public EnumSize getSize() {
		return EnumSize.LARGE;
	}
	
	@Override
	public boolean canStack() 
	{
		return false;
	}
	
	public void addInformation(ItemStack is, List arraylist) 
    {
		ItemTerra.addSizeInformation(this, arraylist);
    	
        if (is.hasTagCompound())
        {
            NBTTagCompound stackTagCompound = is.getTagCompound();

            if(stackTagCompound.hasKey("temperature"))
            {
                float temp = stackTagCompound.getFloat("temperature");
                float meltTemp = -1;
                float boilTemp = 10000;
                HeatIndex hi = HeatManager.getInstance().findMatchingIndex(is);
                if(hi != null)
                {
                    meltTemp = hi.meltTemp;
                    boilTemp = hi.boilTemp;
                }

                if(meltTemp != -1)
                {
                    if(is.itemID == Item.stick.shiftedIndex)
                        arraylist.add(TFC_ItemHeat.getHeatColorTorch(temp, meltTemp));
                    else
                        arraylist.add(TFC_ItemHeat.getHeatColor(temp, meltTemp, boilTemp));
                }
            }
        }
    }

	@Override
	public EnumWeight getWeight() {
		// TODO Auto-generated method stub
		return EnumWeight.HEAVY;
	}
}

