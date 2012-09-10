package TFC.Items;

import java.util.List;

import TFC.Core.HeatIndex;
import TFC.Core.HeatManager;
import TFC.Core.TFC_ItemHeat;
import TFC.Enums.EnumSize;
import net.minecraft.src.*;

public class ItemTerraBlock extends ItemBlock
{
	public String[] blockNames;
	public EnumSize size;
	
	public ItemTerraBlock(int par1) 
	{
		super(par1);
		size = EnumSize.MEDIUM;
		setHasSubtypes(true);
		this.setTabToDisplayOn(CreativeTabs.tabBlock);
	}
	
	@Override
	public String getItemNameIS(ItemStack itemstack) 
	{
		String s = new StringBuilder().append(super.getItemName()).append(".").append(blockNames[itemstack.getItemDamage()]).toString();
		return s;
	}
	
	@Override
	public int getMetadata(int i) 
	{		
		return i;
	}

    public void addInformation(ItemStack is, List arraylist) 
    {
    	if(size!= null)
    		arraylist.add("Size: " + size.name());
    	
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
    
    public boolean getShareTag()
    {
        return true;
    }

}