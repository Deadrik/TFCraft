package TFC.Items;

import java.util.List;

import TFC.Core.HeatIndex;
import TFC.Core.HeatManager;
import TFC.Core.TFC_ItemHeat;
import TFC.Core.TFC_Settings;
import TFC.Enums.EnumSize;
import TFC.Enums.EnumWeight;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.crash.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.packet.*;
import net.minecraft.pathfinding.*;
import net.minecraft.potion.*;
import net.minecraft.server.*;
import net.minecraft.stats.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.village.*;
import net.minecraft.world.*;
import net.minecraft.world.chunk.*;

public class ItemTerra extends Item implements ISize
{
    protected boolean stackable = true;
    public String[] MetaNames;
    public String textureFolder;

    public ItemTerra(int id) 
    {
        super(id);
        this.setCreativeTab(CreativeTabs.tabMisc);
        textureFolder = "";
    }
    
    @Override
    public int getItemStackLimit()
    {
    	if(canStack())
    		return this.getSize().stackSize * getWeight().multiplier <= 64 ? this.getSize().stackSize * getWeight().multiplier : 64;
    	else
    		return 1;
    }
    
    public ItemTerra setFolder(String s)
    {
    	this.textureFolder = s;
    	return this;
    }
    
	public void registerIcon(IconRegister registerer)
    {
		this.iconIndex = registerer.func_94245_a(textureFolder+this.getUnlocalizedName().replace("item.", ""));
    }
    
    @Override
    public void func_94581_a(IconRegister registerer)
    {
    	//super.func_94581_a(registerer);
    	registerIcon(registerer);
    }
    
    @Override
    public String getUnlocalizedName(ItemStack itemstack)
    {
    	if(MetaNames != null)
    		return getUnlocalizedName().concat("."+ MetaNames[itemstack.getItemDamage()]);
    	return super.getUnlocalizedName(itemstack);
    }
    
   /* @Override
	public String getItemDisplayName(ItemStack itemstack) 
	{
    	if(MetaNames != null)
    		return new StringBuilder().append(super.getItemDisplayName(itemstack)).append(".").append(MetaNames[itemstack.getItemDamage()]).toString();
		return super.getItemDisplayName(itemstack);
	}*/

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
                    if(is.itemID == Item.stick.itemID)
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
		return stackable;
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
