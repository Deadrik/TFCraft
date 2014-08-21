package com.bioxx.tfc.Food;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;

public class ItemSandwich extends ItemMeal
{

	public ItemSandwich()
	{
		super();
		this.hasSubtypes = true;
		this.MetaNames = new String[]{"Sandwich0","Sandwich1","Sandwich2","Sandwich3"};
		this.MetaIcons = new IIcon[4];
		this.setFolder("food/");
	}

	@Override
	protected void addFGInformation(ItemStack is, List arraylist)
	{
		if (is.hasTagCompound())
		{
			NBTTagCompound nbt = is.getTagCompound();
			if(nbt.hasKey("FG"))
			{
				int[] fg = nbt.getIntArray("FG");
				for(int i = 0; i < fg.length; i++)
				{
					if(i == 5 && fg[5] == fg[0])
						return;
					if(fg[i] != -1)
						arraylist.add(localize(fg[i]));
				}
			}
		}
	}
}
