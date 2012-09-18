package TFC.Items;

import java.util.List;

import TFC.Core.TFC_ItemHeat;
import TFC.Core.TFC_Settings;

import net.minecraft.src.Enchantment;
import net.minecraft.src.Entity;
import net.minecraft.src.Item;
import net.minecraft.src.ItemFood;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.World;

public class ItemTerraFood extends ItemFood
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
                
                meltTemp = TFC_ItemHeat.getMeltingPoint(is);

                if(meltTemp != -1)
                {
                    if(is.getItem() instanceof ItemFood)
                        arraylist.add(TFC_ItemHeat.getHeatColorFood(temp, meltTemp));
                }
            }
        }
	}
	
	@Override
    public void onUpdate(ItemStack is, World world, Entity entity, int i, boolean isSelected) 
    {
        if (!world.isRemote && is.hasTagCompound() && TFC_Settings.enableInventoryHeat)
        {
            NBTTagCompound stackTagCompound = is.getTagCompound();

            if(stackTagCompound.hasKey("temperature"))
            {
            	TFC_ItemHeat.HandleItemHeat(is, (int)entity.posX, (int)entity.posY, (int)entity.posZ);
            }
        }
    }
	
	public boolean getShareTag()
    {
        return true;
    }
}
