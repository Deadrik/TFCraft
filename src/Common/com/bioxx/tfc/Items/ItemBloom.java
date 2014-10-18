package com.bioxx.tfc.Items;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.bioxx.tfc.TFCItems;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.api.Metal;
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
		setCreativeTab(TFCTabs.TFCMaterials);
		this.setWeight(EnumWeight.HEAVY);
		this.setSize(EnumSize.LARGE);

	}

	@Override
	public boolean canStack()
	{
		return false;
	}

	@Override
	public void addExtraInformation(ItemStack is, EntityPlayer player, List arraylist)
	{
		arraylist.add(is.getItemDamage() + "%");
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
	public Metal GetMetalType(ItemStack is)
	{
		return Global.WROUGHTIRON;
	}

	@Override
	public short GetMetalReturnAmount(ItemStack is)
	{
		return (short) is.getItemDamage();
	}

	@Override
	public boolean isSmeltable(ItemStack is)
	{
		if(this == TFCItems.Bloom)
			return true;
		return false;
	}

	@Override
	public EnumTier GetSmeltTier(ItemStack is)
	{
		return EnumTier.TierIII;
	}

}
