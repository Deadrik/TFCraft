package com.bioxx.tfc.Food;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;

import com.bioxx.tfc.Core.TFC_Time;

public class ItemSalad extends ItemMeal
{

	public ItemSalad()
	{
		super();
		this.hasSubtypes = true;
		this.MetaNames = new String[]{"Salad0","Salad1","Salad2","Salad3"};
		this.MetaIcons = new IIcon[4];
		this.setFolder("food/");
	}

	@Override
	public void getSubItems(Item item, CreativeTabs tabs, List list)
	{
		list.add(createTag(new ItemStack(this, 1)));
	}

	//Creates empty food to prevent NBT errors when food is loaded in NEI
	public static ItemStack createTag(ItemStack is)
	{
		NBTTagCompound nbt = is.getTagCompound();
		if (nbt == null)
			nbt = new NBTTagCompound();

		int[] FG = new int[] { -1, -1, -1, -1 };
		nbt.setIntArray("FG", FG);
		nbt.setFloat("foodWeight", 0);
		nbt.setFloat("foodDecay", 0);
		nbt.setInteger("decayTimer", (int) TFC_Time.getTotalHours() + 1);

		is.setTagCompound(nbt);
		return is;
	}
}
