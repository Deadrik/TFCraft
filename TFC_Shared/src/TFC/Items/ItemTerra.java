package TFC.Items;

import java.util.List;

import TFC.Core.HeatIndex;
import TFC.Core.HeatManager;
import TFC.Core.TFC_ItemHeat;
import TFC.Core.TFC_Settings;
import TFC.Enums.EnumSize;
import TFC.Enums.EnumWeight;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Enchantment;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemFood;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.World;

public class ItemTerra extends Item implements ISize
{
    protected String texture;

    public ItemTerra(int id) 
    {
        super(id);
        this.setCreativeTab(CreativeTabs.tabMisc);
    }
    public ItemTerra(int id, String tex) 
    {
        this(id);
        texture = tex;
    }

    @Override
    public String getTextureFile()
    {
        return texture;
    }
    
    @Override
    public int getItemStackLimit()
    {
    	if(canStack())
    		return this.getSize().stackSize * getWeight().multiplier <= 64 ? this.getSize().stackSize * getWeight().multiplier : 64;
    	else
    		return 1;
    }

    public ItemTerra setTexturePath(String t)
    {
        texture = t;
        return this;
    }
    @Override
    public boolean getShareTag()
    {
        return true;
    }

    @Override
    public void onUpdate(ItemStack is, World world, Entity entity, int i, boolean isSelected) 
    {
        if (!world.isRemote && is.hasTagCompound())
        {
            NBTTagCompound stackTagCompound = is.getTagCompound();

            if(stackTagCompound.hasKey("temperature"))
            {
                TFC_ItemHeat.HandleItemHeat(is, (int)entity.posX, (int)entity.posY, (int)entity.posZ);
            }
        }
    }
    
    public static void addSizeInformation(ISize object, List arraylist)
    {
    	if(object.getSize()!= null && object.getWeight() != null)
    		arraylist.add("\u2696" + object.getWeight().getName() + " \u21F2" + object.getSize().getName());
    }
    @Override
    public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag) 
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

	@Override
	public boolean canStack() 
	{
		return true;
	}
	
	@Override
	public EnumSize getSize() 
	{
		return EnumSize.TINY;
	}
	@Override
	public EnumWeight getWeight() 
	{
		return EnumWeight.MEDIUM;
	}

}
