package TFC.Items;

import java.util.List;

import TFC.Core.HeatIndex;
import TFC.Core.HeatManager;
import TFC.Core.TFCHeat;

import net.minecraft.src.Enchantment;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemFood;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.World;
import net.minecraft.src.forge.ITextureProvider;

public class ItemTerra extends Item  implements ITextureProvider
{
    protected String texture;

    public ItemTerra(int id) 
    {
        super(id);
    }
    public ItemTerra(int id, String tex) 
    {
        super(id);
        texture = tex;
    }

    @Override
    public String getTextureFile()
    {
        return texture;
    }

    public ItemTerra setTexturePath(String t)
    {
        texture = t;
        return this;
    }

    @Override
    public void onUpdate(ItemStack is, World world, Entity entity, int i, boolean isSelected) 
    {
        if (is.hasTagCompound())
        {
            NBTTagCompound stackTagCompound = is.getTagCompound();

            if(stackTagCompound.hasKey("temperature"))
            {
                TFCHeat.HandleContainerHeat(world, new ItemStack[]{is}, (int)entity.posX, (int)entity.posY, (int)entity.posZ);
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
                HeatIndex hi = HeatManager.getInstance().findMatchingIndex(is);
                if(hi != null)
                {
                    meltTemp = hi.meltTemp;
                }

                if(meltTemp != -1)
                {
                    if(is.getItem() instanceof ItemFood)
                        arraylist.add(TFCHeat.getHeatColorFood(temp, meltTemp));
                    else if(is.itemID == Item.stick.shiftedIndex)
                        arraylist.add(TFCHeat.getHeatColorTorch(temp, meltTemp));
                    else
                        arraylist.add(TFCHeat.getHeatColor(temp, meltTemp));
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
