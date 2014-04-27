package com.bioxx.tfc.Items.Pottery;

import java.util.List;

import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ItemPotteryPot extends ItemPotteryBase
{

	public ItemPotteryPot() 
	{
		super();
		this.MetaNames = new String[]{"Clay Pot", "Ceramic Pot"};
		this.setWeight(EnumWeight.LIGHT);
		this.setSize(EnumSize.SMALL);
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag) 
	{
		super.addInformation(is, player, arraylist, flag);
		if(is.hasTagCompound() && is.stackTagCompound.hasKey("LiquidType"))
		{
			arraylist.add(is.stackTagCompound.getString("LiquidType"));
		}
	}
}
