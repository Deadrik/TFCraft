package TFC.Items;

import java.util.List;

import TFC.Core.HeatIndex;
import TFC.Core.HeatManager;
import TFC.Core.TFC_ItemHeat;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Entity;
import net.minecraft.src.Item;
import net.minecraft.src.ItemBlock;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.World;

public class ItemDirt extends ItemBlock
{
	public ItemDirt(int i) 
	{
		super(i);
		setHasSubtypes(true);
		this.setMaxDamage(0);
		this.setTabToDisplayOn(CreativeTabs.tabBlock);
	}

	@Override
	public int getMetadata(int i) 
	{		
		return i;
	}

	@Override
	public String getTextureFile()
	{
		return "/bioxx/terrablocks2.png";
	}
	
    @Override
    public void onUpdate(ItemStack is, World world, Entity entity, int i, boolean isSelected) 
    {
        if (is.hasTagCompound())
        {
            NBTTagCompound stackTagCompound = is.getTagCompound();

            if(stackTagCompound.hasKey("temperature"))
            {
                TFC_ItemHeat.HandleContainerHeat(world, new ItemStack[]{is}, (int)entity.posX, (int)entity.posY, (int)entity.posZ);
            }
        }
    }

    public void addInformation(ItemStack is, List arraylist) 
    {
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
            if(stackTagCompound.hasKey("itemCraftingValue") && stackTagCompound.getByte("itemCraftingValue") != 0)
            {
                arraylist.add("This Item Has Been Worked");
                if(stackTagCompound.hasKey("itemCraftingRule1"))
                    arraylist.add("  \u2022" + getRuleFromId(stackTagCompound.getByte("itemCraftingRule1")));
                if(stackTagCompound.hasKey("itemCraftingRule2"))
                    arraylist.add("  \u2022" + getRuleFromId(stackTagCompound.getByte("itemCraftingRule2")));
                if(stackTagCompound.hasKey("itemCraftingRule3"))
                    arraylist.add("  \u2022" + getRuleFromId(stackTagCompound.getByte("itemCraftingRule3")));
            }
        }
    }

    public static String getRuleFromId(int id)
    {
        switch(id)
        {
            case 0:
                return "Hit";
            case 1:
                return "Draw";
            case 2:
                return "Quench";
            case 3:
                return "Punch";
            case 4:
                return "Bend";
            case 5:
                return "Upset";
            case 6:
                return "Shrink";
            case 7:
                return "Weld";
            default:
                return "";
        }
    }
}