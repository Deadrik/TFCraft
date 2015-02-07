package com.bioxx.tfc.api;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class TFCCrafting
{
	public static boolean appleConversion;
	public static boolean arrowConversion;
	public static boolean bowConversion;
	public static boolean coalConversion;
	public static boolean diamondConversion;
	public static boolean emeraldConversion;
	public static boolean fishConversion;
	public static boolean fishingRodConversion;
	public static boolean flintSteelConversion;
	public static boolean leatherArmorConversion;
	public static boolean leatherConversion;
	public static boolean stoneAxeConversion;
	public static boolean stoneHoeConversion;
	public static boolean stoneShovelConversion;
	public static boolean woodButtonConversion;
	public static boolean workbenchConversion;

	public static boolean anvilRecipe;
	public static boolean arrowsRecipe;
	public static boolean bedRecipe;
	public static boolean bonemealRecipe;
	public static boolean bowlRecipe;
	public static boolean brewingRecipe;
	public static boolean bucketRecipe;
	public static boolean cauldronRecipe;
	public static boolean chestRecipe;
	public static boolean clockRecipe;
	public static boolean compassRecipe;
	public static boolean dandelionYellowRecipe;
	public static boolean diamondArmorRecipe;
	public static boolean diamondBlockRecipe;
	public static boolean diamondToolsRecipe;
	public static boolean dispenserRecipe;
	public static boolean dropperRecipe;
	public static boolean enchantTableRecipe;
	public static boolean fenceGateRecipe;
	public static boolean fenceRecipe;
	public static boolean furnaceRecipe;
	public static boolean goldAppleRecipe;
	public static boolean goldArmorRecipe;
	public static boolean goldBlockRecipe;
	public static boolean goldNuggetRecipe;
	public static boolean goldPlateRecipe;
	public static boolean goldToolsRecipe;
	public static boolean hopperRecipe;
	public static boolean ironArmorRecipe;
	public static boolean ironBarsRecipe;
	public static boolean ironBlockRecipe;
	public static boolean ironDoorRecipe;
	public static boolean ironPlateRecipe;
	public static boolean ironToolsRecipe;
	public static boolean jukeboxRecipe;
	public static boolean leatherArmorRecipe;
	public static boolean leverRecipe;
	public static boolean minecartChestRecipe;
	public static boolean minecartRecipe;
	public static boolean pistonRecipe;
	public static boolean plankBlockRecipe;
	public static boolean poweredRailsRecipe;
	public static boolean railsRecipe;
	public static boolean repeaterRecipe;
	public static boolean roseRedRecipe;
	public static boolean shearsRecipe;
	public static boolean signRecipe;
	public static boolean stickRecipe;
	public static boolean stoneSlabsRecipe;
	public static boolean stoneStairsRecipe;
	public static boolean stoneToolsRecipe;
	public static boolean torchRecipe;
	public static boolean trapDoorRecipe;
	public static boolean tripwireRecipe;
	public static boolean woodDoorRecipe;
	public static boolean woodSlabsRecipe;
	public static boolean woodStairsRecipe;
	public static boolean woodToolsRecipe;
	public static boolean woolRecipe;

	public static boolean getBooleanFor(Configuration config,String heading, String item, boolean value)
	{
		if (config == null)
			return value;
		try
		{
			Property prop = config.get(heading, item, value);
			return prop.getBoolean(value);
		}
		catch (Exception e)
		{
			System.out.println(new StringBuilder().append("[TFC] Error while trying to add Integer, config wasn't loaded properly!").toString());
		}
		return value;
	}

	public static boolean getBooleanFor(Configuration config,String heading, String item, boolean value, String comment)
	{
		if (config == null)
			return value;
		try
		{
			Property prop = config.get(heading, item, value);
			prop.comment = comment;
			return prop.getBoolean(value);
		}
		catch (Exception e)
		{
			System.out.println(new StringBuilder().append("[TFC] Error while trying to add Integer, config wasn't loaded properly!").toString());
		}
		return value;
	}
}
