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
				"Native Copper", "Native Gold", "Native Platinum", "Hematite", "Native Silver", "Cassiterite", "Galena", "Bismuthinite", "Garnierite", 
				"Malachite", "Magnetite", "Limonite", "Sphalerite", "Tetrahedrite", 
				"Bituminous Coal", "Lignite", "Kaolinite", "Gypsum", "Satinspar", "Selenite", "Graphite", "Kimberlite", 
				/*22*/"Petrified Wood", "Sulfur", "Jet", "Microcline", "Pitchblende", "Cinnabar", "Cryolite", "Saltpeter", "Serpentine", "Sylvite", 
				/*32*/"Borax", "Olivine", "Lapis Lazuli", "Rich Native Copper", "Rich Native Gold", "Rich Native Platinum", "Rich Hematite", 
				"Rich Native Silver", "Rich Cassiterite", "Rich Galena", "Rich Bismuthinite", "Rich Garnierite", "Rich Malachite", 
				"Rich Magnetite", "Rich Limonite", "Rich Sphalerite", "Rich Tetrahedrite", 
				"Poor Native Copper", "Poor Native Gold", "Poor Native Platinum", "Poor Hematite", 
				"Poor Native Silver", "Poor Cassiterite", "Poor Galena", "Poor Bismuthinite", "Poor Garnierite", "Poor Malachite", 
				"Poor Magnetite", "Poor Limonite", "Poor Sphalerite", "Poor Tetrahedrite"};
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
		//Rich Ores
		case 35: return Global.COPPER;
		case 36: return Global.GOLD;
		case 37: return Global.PLATINUM;
		case 38: return Global.PIGIRON;
		case 39: return Global.SILVER;
		case 40: return Global.TIN;
		case 41: return Global.LEAD;
		case 42: return Global.BISMUTH;
		case 43: return Global.NICKEL;
		case 44: return Global.COPPER;
		case 45: return Global.PIGIRON;
		case 46: return Global.PIGIRON;
		case 47: return Global.ZINC;
		case 48: return Global.COPPER;
		//Poor Ores
		case 49: return Global.COPPER;
		case 50: return Global.GOLD;
		case 51: return Global.PLATINUM;
		case 52: return Global.PIGIRON;
		case 53: return Global.SILVER;
		case 54: return Global.TIN;
		case 55: return Global.LEAD;
		case 56: return Global.BISMUTH;
		case 57: return Global.NICKEL;
		case 58: return Global.COPPER;
		case 59: return Global.PIGIRON;
		case 60: return Global.PIGIRON;
		case 61: return Global.ZINC;
		case 62: return Global.COPPER;
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
		case 13: return (short) TFCOptions.normalOreUnits;
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
		case 48: return (short) TFCOptions.richOreUnits;
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
		case 62: return (short) TFCOptions.poorOreUnits;
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
		case 54:
		case 55:
		case 56:
		case 57:
		case 58:
		case 59:
		case 60:
		case 61:
		case 62:
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
		//Roch Ores
		case 35: return EnumTier.TierI;
		case 36: return EnumTier.TierI;
		case 37: return EnumTier.TierIV;
		case 38: return EnumTier.TierIII;
		case 39: return EnumTier.TierI;
		case 40: return EnumTier.TierI;
		case 41: return EnumTier.TierI;
		case 42: return EnumTier.TierI;
		case 43: return EnumTier.TierIII;
		case 44: return EnumTier.TierI;
		case 45: return EnumTier.TierIII;
		case 46: return EnumTier.TierIII;
		case 47: return EnumTier.TierI;
		case 48: return EnumTier.TierI;
		//Poor Ores
		case 49: return EnumTier.TierI;
		case 50: return EnumTier.TierI;
		case 51: return EnumTier.TierIV;
		case 52: return EnumTier.TierIII;
		case 53: return EnumTier.TierI;
		case 54: return EnumTier.TierI;
		case 55: return EnumTier.TierI;
		case 56: return EnumTier.TierI;
		case 57: return EnumTier.TierIII;
		case 58: return EnumTier.TierI;
		case 59: return EnumTier.TierIII;
		case 60: return EnumTier.TierIII;
		case 61: return EnumTier.TierI;
		case 62: return EnumTier.TierI;
		}
		return EnumTier.TierX;
	}

}
