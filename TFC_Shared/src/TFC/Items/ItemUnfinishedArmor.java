package TFC.Items;

import TFC.Enums.EnumSize;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public class ItemUnfinishedArmor extends ItemTerra
{
	String texture;

	public ItemUnfinishedArmor(int id) 
	{
		super(id);
		this.hasSubtypes = true;
		this.setMaxDamage(0);
		this.setTabToDisplayOn(CreativeTabs.tabMisc);
	}
	public ItemUnfinishedArmor(int id, String tex) 
	{
		super(id);
		texture = tex;
	}

	@Override
	public String getTextureFile()
	{
		return texture;
	}

	public ItemUnfinishedArmor setTexturePath(String t)
	{
		texture = t;
		return this;
	}
	
	@Override
    public String getItemNameIS(ItemStack itemstack) 
    {
	    String s = "";
	    
	    if(itemstack.getItemDamage() == 1)
	        s = new StringBuilder().append(super.getItemName()).append("2").toString();
	    else
	        s = new StringBuilder().append(super.getItemName()).toString();
	    
        return s;
    }
	
	public int getItemStackLimit()
    {
    	if(canStack())
    		return this.getSize().stackSize;
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
		return true;
	}

}
