package com.bioxx.tfc.Items;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.api.Metal;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;
import com.bioxx.tfc.api.Interfaces.ISmeltable;

public class ItemBloom extends ItemTerra implements ISmeltable
{
	public ItemBloom()
	{
		super();
		setHasSubtypes(true);
		setCreativeTab(TFCTabs.TFC_MATERIALS);
		this.setWeight(EnumWeight.HEAVY);
		this.setSize(EnumSize.LARGE);

	}

	@Override
	public boolean canStack()
	{
		return false;
	}

	@Override
	public void addExtraInformation(ItemStack is, EntityPlayer player, List<String> arraylist)
	{
		arraylist.add(TFC_Core.translate("gui.units") + ": " + is.getItemDamage());
	}

	@Override
	public void getSubItems(Item item, CreativeTabs tabs, List list)
	{
		list.add(new ItemStack(this, 1, 100));
		list.add(new ItemStack(this, 1, 200));
		list.add(new ItemStack(this, 1, 300));
		list.add(new ItemStack(this, 1, 400));
	}

	@Override
	public Metal getMetalType(ItemStack is)
	{
		if (this == TFCItems.rawBloom)
			return Global.UNKNOWN;

		return Global.WROUGHTIRON;
	}

	@Override
	public short getMetalReturnAmount(ItemStack is)
	{
		return (short) is.getItemDamage();
	}

	@Override
	public boolean isSmeltable(ItemStack is)
	{
		return this == TFCItems.bloom;
	}

	@Override
	public EnumTier getSmeltTier(ItemStack is)
	{
		return EnumTier.TierIII;
	}

}
