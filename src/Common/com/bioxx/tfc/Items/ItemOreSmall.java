package com.bioxx.tfc.Items;

import java.util.List;

import com.bioxx.tfc.api.Constant.Global;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.api.TFCOptions;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;

public class ItemOreSmall extends ItemOre
{
	public ItemOreSmall()
	{
		super();
		this.setWeight(EnumWeight.HEAVY);
		this.setSize(EnumSize.TINY);
		metaNames = Global.ORE_METAL_ALL;
	}

	@Override
	public void getSubItems(Item item, CreativeTabs tabs, List list)
	{
		for(int i = 0; i < Global.ORE_METAL_ALL.length; i++)
			list.add(new ItemStack(this, 1, i));
	}

	@Override
	public void registerIcons(IIconRegister registerer)
	{
		metaIcons = new IIcon[Global.ORE_METAL_ALL.length];
		for(int i = 0; i < Global.ORE_METAL_ALL.length; i++)
		{
			metaIcons[i] = registerer.registerIcon(Reference.MOD_ID + ":" + textureFolder+metaNames[i] + " Small Ore");
		}
	}

	@Override
	public short getMetalReturnAmount(ItemStack is)
	{
		int dam = is.getItemDamage();
		switch(dam)
		{
		case 0:
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
		case 6:
		case 7:
		case 8:
		case 9:
		case 10:
		case 11:
		case 12:
		case 13:
		case 14:
		case 15:
		case 16:
		case 17: return (short) TFCOptions.smallOreUnits;
		}
		return 0;
	}
}
