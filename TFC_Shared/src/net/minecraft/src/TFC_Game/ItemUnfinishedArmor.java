package net.minecraft.src.TFC_Game;

import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TFC_Core.ItemTerra;
import net.minecraft.src.forge.ITextureProvider;

public class ItemUnfinishedArmor extends ItemTerra  implements ITextureProvider
{
	String texture;

	public ItemUnfinishedArmor(int id) 
	{
		super(id);
		this.hasSubtypes = true;
		this.setMaxDamage(0);
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

}
