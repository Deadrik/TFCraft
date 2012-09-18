package TFC.Items;

import java.util.List;

import TFC.Core.HeatIndex;
import TFC.Core.HeatManager;
import TFC.Core.TFC_ItemHeat;
import TFC.Core.TFC_Settings;
import TFC.Enums.EnumSize;
import TFC.Enums.EnumWeight;
import net.minecraft.src.*;

public class ItemTerraBlock extends ItemBlock implements ISize
{
	public String[] blockNames;
	
	public ItemTerraBlock(int par1) 
	{
		super(par1);
		setHasSubtypes(true);
		this.setTabToDisplayOn(CreativeTabs.tabBlock);
	}
	
	@Override
	public String getItemNameIS(ItemStack itemstack) 
	{
		if(blockNames != null)
		{
			String s = new StringBuilder().append(super.getItemName()).append(".").append(blockNames[itemstack.getItemDamage()]).toString();
			return s;
		}
		else
		{
			return itemstack.getItem().getItemName();
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
	
	@Override
	public int getMetadata(int i) 
	{		
		return i;
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
    
    public boolean getShareTag()
    {
        return true;
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
		// TODO Auto-generated method stub
		return EnumSize.VERYSMALL;
	}

	@Override
	public boolean canStack() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public EnumWeight getWeight() {
		// TODO Auto-generated method stub
		return EnumWeight.HEAVY;
	}

}