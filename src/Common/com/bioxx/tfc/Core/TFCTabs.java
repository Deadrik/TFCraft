package com.bioxx.tfc.Core;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TFCTabs extends CreativeTabs
{
	public static final CreativeTabs TFC_BUILDING = new TFCTabs("TFCBuilding");
	public static final CreativeTabs TFC_DECORATION = new TFCTabs("TFCDecoration");
	public static final CreativeTabs TFC_DEVICES = new TFCTabs("TFCDevices");
	public static final CreativeTabs TFC_POTTERY = new TFCTabs("TFCPottery");
	public static final CreativeTabs TFC_MISC = new TFCTabs("TFCMisc");
	public static final CreativeTabs TFC_FOODS = new TFCTabs("TFCFoods");
	public static final CreativeTabs TFC_TOOLS = new TFCTabs("TFCTools");
	public static final CreativeTabs TFC_WEAPONS = new TFCTabs("TFCWeapons");
	public static final CreativeTabs TFC_ARMOR = new TFCTabs("TFCArmor");
	public static final CreativeTabs TFC_MATERIALS = new TFCTabs("TFCMaterials");

	//private int itemIndex;
	private ItemStack is;

	public TFCTabs(String par2Str)
	{
		super(par2Str);
	}

	public TFCTabs(String par2Str, int icon)
	{
		super(par2Str);
		//itemIndex = icon;
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
		return TFC_Core.translate("itemGroup." + this.getTabLabel());
	}
}
