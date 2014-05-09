package com.bioxx.tfc.Items;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

import com.bioxx.tfc.TFCItems;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.api.HeatRegistry;
import com.bioxx.tfc.api.ISmeltable;
import com.bioxx.tfc.api.Metal;
import com.bioxx.tfc.api.TFC_ItemHeat;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;

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
	public void addExtraInformation(ItemStack is, EntityPlayer player, List arraylist)
	{
		arraylist.add(is.getItemDamage()+"%");
	}

	@Override
	public void addItemInformation(ItemStack is, EntityPlayer player, List arraylist)
	{
		if(TFC_ItemHeat.HasTemp(is))
		{
			String s = "";
			if(HeatRegistry.getInstance().isTemperatureDanger(is))
			{
				s += EnumChatFormatting.WHITE + StatCollector.translateToLocal("gui.ingot.danger") + " | ";
			}

			if(HeatRegistry.getInstance().isTemperatureWeldable(is))
			{
				s += EnumChatFormatting.WHITE + StatCollector.translateToLocal("gui.ingot.weldable") + " | ";
			}

			if(HeatRegistry.getInstance().isTemperatureWorkable(is))
			{
				s += EnumChatFormatting.WHITE + StatCollector.translateToLocal("gui.ingot.workable");
			}

			if(!s.equals(""))
				arraylist.add(s);
		}
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
