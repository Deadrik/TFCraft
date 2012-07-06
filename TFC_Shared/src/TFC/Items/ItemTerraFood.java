package TFC.Items;

import java.util.List;

import TFC.Core.TFCHeat;

import net.minecraft.src.Enchantment;
import net.minecraft.src.Item;
import net.minecraft.src.ItemFood;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.forge.ITextureProvider;

public class ItemTerraFood extends ItemFood  implements ITextureProvider
{
	String texture;

	public ItemTerraFood(int id, int healAmt) 
	{
		super(id, healAmt, true);
	}
	public ItemTerraFood(int id, int healAmt, String tex) 
	{
		this(id, healAmt);
		texture = tex;
	}
	
	public ItemTerraFood(int par1, int par2, float par3, boolean par4, String tex)
    {
	    super(par1, par2, par3, par4);
	    texture = tex;
    }

	@Override
	public String getTextureFile()
	{
		return texture;
	}

	public ItemTerraFood setTexturePath(String t)
	{
		texture = t;
		return this;
	}
	
	public void addInformation(ItemStack is, List arraylist) 
	{
	    if (is.hasTagCompound())
        {
            NBTTagCompound stackTagCompound = is.getTagCompound();

            if(stackTagCompound.hasKey("temperature"))
            {
                float temp = stackTagCompound.getFloat("temperature");
                float meltTemp = 0;
                if(stackTagCompound.hasKey("Item1Melted") && stackTagCompound.getBoolean("Item1Melted") == true)
                    meltTemp = TFCHeat.getMeltingPoint(is);
                else
                    meltTemp = TFCHeat.getMeltingPoint(is);

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
