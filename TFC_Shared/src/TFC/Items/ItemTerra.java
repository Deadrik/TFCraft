package TFC.Items;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import TFC.Reference;
import TFC.API.ISize;
import TFC.API.Enums.EnumSize;
import TFC.API.Enums.EnumWeight;
import TFC.Core.HeatIndex;
import TFC.Core.HeatManager;
import TFC.Core.TFC_ItemHeat;
import TFC.Core.TFC_Settings;
import TFC.Core.Util.StringUtil;

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
    
    public ItemTerra setMetaNames(String[] metanames)
    {
    	MetaNames = metanames;
    	return this;
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
    
    @Override
	public void registerIcons(IconRegister registerer)
    {
		this.itemIcon = registerer.registerIcon(Reference.ModID + ":" + textureFolder+this.getUnlocalizedName().replace("item.", ""));
    }
    
    @Override
    public String getUnlocalizedName(ItemStack itemstack)
    {
    	if(MetaNames != null)
    		return getUnlocalizedName().concat("."+ MetaNames[itemstack.getItemDamage()]);
    	return super.getUnlocalizedName(itemstack);
    }
    
    @Override
	public String getItemDisplayName(ItemStack itemstack) 
	{
		return StringUtil.localize(getUnlocalizedName(itemstack).replace(" ", ""));
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
    	Minecraft.getMinecraft().gameSettings.advancedItemTooltips = false;
    	ItemTerra.addSizeInformation(this, arraylist);
    	
    	if(TFC_Settings.enableDebugMode)
			arraylist.add(getUnlocalizedName(is));
    	
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
                /*if(stackTagCompound.hasKey("itemCraftingRule1"))
                    arraylist.add("  \u2022" + getRuleFromId(stackTagCompound.getByte("itemCraftingRule1")));
                if(stackTagCompound.hasKey("itemCraftingRule2"))
                    arraylist.add("  \u2022" + getRuleFromId(stackTagCompound.getByte("itemCraftingRule2")));
                if(stackTagCompound.hasKey("itemCraftingRule3"))
                    arraylist.add("  \u2022" + getRuleFromId(stackTagCompound.getByte("itemCraftingRule3")));*/
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
