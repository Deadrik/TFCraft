package com.bioxx.tfc.Items;

import java.util.List;

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
	}

	@Override
	public void getSubItems(Item item, CreativeTabs tabs, List list)
	{
		for(int i = 0; i < 14; i++)
			list.add(new ItemStack(this, 1, i));
	}

	@Override
	public void registerIcons(IIconRegister registerer)
	{
		metaIcons = new IIcon[14];
		for(int i = 0; i < 14; i++)
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
		case 13: return (short) TFCOptions.smallOreUnits;
		case 35: 
		case 36:
		case 37:
		case 38:
		case 39:
		case 40:
		case 41:
		case 42:
		case 43:
		case 44:
		case 45:
		case 46:
		case 47:
		case 48: return 15;
		case 49: 
		case 50:
		case 51:
		case 52:
		case 53:
		case 54:
		case 55:
		case 56:
		case 57:
		case 58:
		case 59:
		case 60:
		case 61:
		case 62: return 5;
		}
		return 0;
	}
}
