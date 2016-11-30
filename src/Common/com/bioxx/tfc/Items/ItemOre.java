package com.bioxx.tfc.Items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.api.Metal;
import com.bioxx.tfc.api.TFCOptions;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;
import com.bioxx.tfc.api.Interfaces.ISmeltable;

public class ItemOre extends ItemTerra implements ISmeltable
{
	public ItemOre()
	{
		super();
		setMaxDamage(0);
		setHasSubtypes(true);
		metaNames = new String[]{
				"Native Copper", "Native Gold", "Native Platinum", "Hematite", "Native Silver", "Cassiterite", "Galena",
				"Bismuthinite", "Garnierite", "Malachite", "Magnetite", "Limonite", "Sphalerite", "Tetrahedrite",
				"Native Osmium", "Bauxite", "Scheelite", "Wolframite",

				"Rich Native Copper", "Rich Native Gold", "Rich Native Platinum", "Rich Hematite", "Rich Native Silver",
				"Rich Cassiterite", "Rich Galena", "Rich Bismuthinite", "Rich Garnierite", "Rich Malachite",
				"Rich Magnetite", "Rich Limonite", "Rich Sphalerite", "Rich Tetrahedrite", "Rich Native Osmium",
				"Rich Bauxite", "Rich Scheelite", "Rich Wolframite",

				"Poor Native Copper", "Poor Native Gold", "Poor Native Platinum", "Poor Hematite", "Poor Native Silver",
				"Poor Cassiterite", "Poor Galena", "Poor Bismuthinite", "Poor Garnierite", "Poor Malachite",
				"Poor Magnetite", "Poor Limonite", "Poor Sphalerite", "Poor Tetrahedrite", "Poor Native Osmium",
				"Poor Bauxite", "Poor Scheelite", "Poor Wolframite"
		};
		setFolder("ore/");
		setCreativeTab(TFCTabs.TFC_MATERIALS);
	}

	@Override
	public EnumSize getSize(ItemStack is)
	{
		return EnumSize.SMALL;
	}

	@Override
	public EnumWeight getWeight(ItemStack is)
	{
		return EnumWeight.HEAVY;
	}

	@Override
	public void registerIcons(IIconRegister registerer)
	{
		metaIcons = new IIcon[metaNames.length];
		for(int i = 0; i < metaNames.length; i++)
		{
			metaIcons[i] = registerer.registerIcon(Reference.MOD_ID + ":" + textureFolder + metaNames[i] + " Ore");
		}
	}

	@Override
	public void addExtraInformation(ItemStack is, EntityPlayer player, List<String> arraylist)
	{
		if(getMetalType(is) != null)
		{
			if (TFC_Core.showShiftInformation())
			{
				arraylist.add(TFC_Core.translate("gui.units") + ": " + getMetalReturnAmount(is));
			}
			else
			{
				arraylist.add(TFC_Core.translate("gui.ShowHelp"));
			}
		}
	}

	@Override
	public Metal getMetalType(ItemStack is)
	{
		int dam = is.getItemDamage();
		switch(dam)
		{
		case 0: return Global.COPPER;
		case 1: return Global.GOLD;
		case 2: return Global.PLATINUM;
		case 3: return Global.PIGIRON;
		case 4: return Global.SILVER;
		case 5: return Global.TIN;
		case 6: return Global.LEAD;
		case 7: return Global.BISMUTH;
		case 8: return Global.NICKEL;
		case 9: return Global.COPPER;
		case 10: return Global.PIGIRON;
		case 11: return Global.PIGIRON;
		case 12: return Global.ZINC;
		case 13: return Global.COPPER;
		case 14: return Global.OSMIUM;
		case 15: return Global.ALUMINUM;
		case 16: return Global.TUNGSTEN;
		case 17: return Global.TUNGSTEN;
		//Rich Ores
		case 18: return Global.COPPER;
		case 19: return Global.GOLD;
		case 20: return Global.PLATINUM;
		case 21: return Global.PIGIRON;
		case 22: return Global.SILVER;
		case 23: return Global.TIN;
		case 24: return Global.LEAD;
		case 25: return Global.BISMUTH;
		case 26: return Global.NICKEL;
		case 27: return Global.COPPER;
		case 28: return Global.PIGIRON;
		case 29: return Global.PIGIRON;
		case 30: return Global.ZINC;
		case 31: return Global.COPPER;
		case 32: return Global.OSMIUM;
		case 33: return Global.ALUMINUM;
		case 34: return Global.TUNGSTEN;
		case 35: return Global.TUNGSTEN;
		//Poor Ores
		case 36: return Global.COPPER;
		case 37: return Global.GOLD;
		case 38: return Global.PLATINUM;
		case 39: return Global.PIGIRON;
		case 40: return Global.SILVER;
		case 41: return Global.TIN;
		case 42: return Global.LEAD;
		case 43: return Global.BISMUTH;
		case 44: return Global.NICKEL;
		case 45: return Global.COPPER;
		case 46: return Global.PIGIRON;
		case 47: return Global.PIGIRON;
		case 48: return Global.ZINC;
		case 49: return Global.COPPER;
		case 50: return Global.OSMIUM;
		case 51: return Global.ALUMINUM;
		case 52: return Global.TUNGSTEN;
		case 53: return Global.TUNGSTEN;
		}
		return null;
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
		case 17: return (short) TFCOptions.normalOreUnits;
		case 18:
		case 19:
		case 20:
		case 21:
		case 22:
		case 23:
		case 24:
		case 25:
		case 26:
		case 27:
		case 28:
		case 29:
		case 30:
		case 31:
		case 32:
		case 33:
		case 34:
		case 35: return (short) TFCOptions.richOreUnits;
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
		case 48:
		case 49:
		case 50:
		case 51:
		case 52:
		case 53: return (short) TFCOptions.poorOreUnits;
		}
		return 0;
	}

	@Override
	public boolean isSmeltable(ItemStack is)
	{
		switch(is.getItemDamage())
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
			case 17:
			case 18:
			case 19:
			case 20:
			case 21:
			case 22:
			case 23:
			case 24:
			case 25:
			case 26:
			case 27:
			case 28:
			case 29:
			case 30:
			case 31:
			case 32:
			case 33:
			case 34:
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
			case 48:
			case 49:
			case 50:
			case 51:
			case 52:
			case 53:
			return true;
		default:
			return false;
		}
	}

	@Override
	public EnumTier getSmeltTier(ItemStack is)
	{
		int dam = is.getItemDamage();
		switch(dam)
		{
		case 0: return EnumTier.TierI;
		case 1: return EnumTier.TierI;
		case 2: return EnumTier.TierIV;
		case 3: return EnumTier.TierIII;
		case 4: return EnumTier.TierI;
		case 5: return EnumTier.TierI;
		case 6: return EnumTier.TierI;
		case 7: return EnumTier.TierI;
		case 8: return EnumTier.TierIII;
		case 9: return EnumTier.TierI;
		case 10: return EnumTier.TierIII;
		case 11: return EnumTier.TierIII;
		case 12: return EnumTier.TierI;
		case 13: return EnumTier.TierI;
		case 14: return EnumTier.TierIV;
		case 15: return EnumTier.TierIII;
		case 16: return EnumTier.TierIV;
		case 17: return EnumTier.TierIV;
		//Roch Ores
		case 18: return EnumTier.TierI;
		case 19: return EnumTier.TierI;
		case 20: return EnumTier.TierIV;
		case 21: return EnumTier.TierIII;
		case 22: return EnumTier.TierI;
		case 23: return EnumTier.TierI;
		case 24: return EnumTier.TierI;
		case 25: return EnumTier.TierI;
		case 26: return EnumTier.TierIII;
		case 27: return EnumTier.TierI;
		case 28: return EnumTier.TierIII;
		case 29: return EnumTier.TierIII;
		case 30: return EnumTier.TierI;
		case 31: return EnumTier.TierI;
		case 32: return EnumTier.TierIV;
		case 33: return EnumTier.TierIII;
		case 34: return EnumTier.TierIV;
		case 35: return EnumTier.TierIV;
		//Poor Ores
		case 36: return EnumTier.TierI;
		case 37: return EnumTier.TierI;
		case 38: return EnumTier.TierIV;
		case 39: return EnumTier.TierIII;
		case 40: return EnumTier.TierI;
		case 41: return EnumTier.TierI;
		case 42: return EnumTier.TierI;
		case 43: return EnumTier.TierI;
		case 44: return EnumTier.TierIII;
		case 45: return EnumTier.TierI;
		case 46: return EnumTier.TierIII;
		case 47: return EnumTier.TierIII;
		case 48: return EnumTier.TierI;
		case 49: return EnumTier.TierI;
		case 50: return EnumTier.TierIV;
		case 51: return EnumTier.TierIII;
		case 52: return EnumTier.TierIV;
		case 53: return EnumTier.TierIV;
		}
		return EnumTier.TierX;
	}

}
