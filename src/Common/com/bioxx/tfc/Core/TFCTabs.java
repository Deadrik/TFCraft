package com.bioxx.tfc.Core;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TFCTabs extends CreativeTabs
{
	public static CreativeTabs TFCMisc = new TFCTabs("TFCMisc");
	public static CreativeTabs TFCTools = new TFCTabs("TFCTools");
	public static CreativeTabs TFCWeapons = new TFCTabs("TFCWeapons");
	public static CreativeTabs TFCUnfinished = new TFCTabs("TFCUnfinished");
	public static CreativeTabs TFCMaterials = new TFCTabs("TFCMaterials");
	public static CreativeTabs TFCArmor = new TFCTabs("TFCArmor");
	public static CreativeTabs TFCPottery = new TFCTabs("TFCPottery");
	public static CreativeTabs TFCDevices = new TFCTabs("TFCDevices");
	public static CreativeTabs TFCFoods = new TFCTabs("TFCFoods");

	private int itemIndex;
	private ItemStack is;

	public TFCTabs(String par2Str)
	{
		super(par2Str);
	}

	public TFCTabs(String par2Str, int icon)
	{
		super(par2Str);
		itemIndex = icon;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public Item getTabIconItem()
	{
		return is.getItem();
	}

	public void setTabIconItem(Item i)
	{
		is = new ItemStack(i);
	}

	@Override
	public ItemStack getIconItemStack()
	{
		return is;
	}

	public void setTabIconItemStack(ItemStack stack)
	{
		is = stack;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getTranslatedTabLabel()
	{
		return StatCollector.translateToLocal("itemGroup." + this.getTabLabel());
	}
}
