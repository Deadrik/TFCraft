package TFC.Core;

import java.io.File;

import net.minecraft.src.mod_TFC;
import net.minecraft.src.forge.Configuration;
import net.minecraft.src.forge.Property;

public class TFCSettings 
{
	public static Configuration config;

	public static boolean enableVanillaDiamondRecipe;
	public static boolean enableVanillaIronRecipe;
	public static boolean enableVanillaGoldRecipe;
	public static boolean enableVanillaFurnaceRecipes;
	public static boolean enableVanillaRecipes;
	public static boolean enableBetterGrass;
	public static boolean enableInnerGrassFix;
	public static boolean enableDebugMode;
	public static boolean sendAllNBT;

	public static boolean BlacksmithModeHeatScale;

	public static int minimumRockLoad;
	public static int initialCollapseRatio;
	public static int propogateCollapseChance;
	
	public static int dayLength;
	public static int leavesLOD;

	static
	{
		try
		{
			config = new Configuration(new File(ServerClientProxy.getProxy().getMinecraftDir(), "/config/TFCOptions.cfg"));
			config.load();
		} catch (Exception e) {
			System.out.println(new StringBuilder().append("[TFC] Error while trying to access configuration!").toString());
			config = null;
		}
		/**Start setup here*/
		//General
		enableVanillaDiamondRecipe = getBooleanFor(config, "General","enableVanillaDiamondRecipe",false);
		enableVanillaIronRecipe = getBooleanFor(config,"General","enableVanillaIronRecipe",false);
		enableVanillaGoldRecipe = getBooleanFor(config,"General","enableVanillaGoldRecipe",false);
		enableBetterGrass = getBooleanFor(config,"General","enableBetterGrass", true);
		enableVanillaFurnaceRecipes = getBooleanFor(config,"General","enableVanillaFurnaceRecipes",false);
		enableVanillaRecipes = getBooleanFor(config,"General","enableVanillaRecipes",false, "Set this to true if you need recipes enabled for conversion from TFC to vanilla items.");
		enableInnerGrassFix = getBooleanFor(config,"General","enableInnerGrassFix",true, "Set this to false if your computer has to run in fast mode and you get lag. This setting forces the sides of grass to render when viewing from the inside.");
		enableDebugMode = getBooleanFor(config,"General","enableDebugMode",false, "Set this to true if you want to turn on debug mode which is useful for bug hunting");
		sendAllNBT = getBooleanFor(config,"General","sendAllNBT",true, "Setting this to false can cause alot of issues when used in SMP. Leave this set to true to prevent issues.");
		dayLength = getIntFor(config,"General","dayLength",24000, "This is how many ticks are in a minecraft day. 24000 is a standard MC cycle. Setting to 48000 will double the length of days.");
		leavesLOD  = getIntFor(config,"General","leavesLOD",20, "This is how far from the player that the dynamic leaf block switching should occur");
		//Metallurgy
		BlacksmithModeHeatScale = getBooleanFor(config,"Metallurgy","BlacksmithModeHeatScale",true, "Set this to false if you are having a hard time and would like the old color scale for heating metals.");
		//Caveins
		minimumRockLoad = getIntFor(config,"Cavein Options","minimumRockLoad",1, "This is the minimum number of solid blocks that must be over a section in order for it to collapse.");
		initialCollapseRatio = getIntFor(config,"Cavein Options","initialCollapseRatio",40, "This number is a 1 in X chance that when you mine a block, a collapse will occur.");
		propogateCollapseChance = getIntFor(config,"Cavein Options","propogateCollapseChance",35, "This number is the likelyhood for each block to propogate the collapse farther.");

		/**Always end with this*/
		if (config != null) {
			config.save();
		}
	}

	public static boolean getBooleanFor(Configuration config,String heading, String item, boolean value)
	{
		if (config == null)
		{
			return value;
		}
		try
		{
			Property prop = config.getOrCreateBooleanProperty(item, heading, value);
			return new Boolean(prop.value).booleanValue();
		}
		catch (Exception e)
		{
			System.out.println(new StringBuilder().append("[TFC] Error while trying to add Integer, config wasn't loaded properly!").toString());
		}return value;
	}

	public static boolean getBooleanFor(Configuration config,String heading, String item, boolean value, String comment)
	{
		if (config == null)
		{
			return value;
		}
		try
		{
			Property prop = config.getOrCreateBooleanProperty(item, heading, value);
			prop.comment = comment;
			return new Boolean(prop.value).booleanValue();
		}
		catch (Exception e)
		{
			System.out.println(new StringBuilder().append("[TFC] Error while trying to add Integer, config wasn't loaded properly!").toString());
		}return value;
	}

	public static int getIntFor(Configuration config, String heading, String item, int value)
	{
		if (config == null)
		{
			return value;
		}
		try
		{
			Property prop = config.getOrCreateIntProperty(item, heading, value);
			return new Integer(prop.value).intValue();
		}
		catch (Exception e)
		{
			System.out.println(new StringBuilder().append("[TFC] Error while trying to add Integer, config wasn't loaded properly!").toString());
		}return value;
	}

	public static int getIntFor(Configuration config,String heading, String item, int value, String comment)
	{
		if (config == null)
		{
			return value;
		}
		try
		{
			Property prop = config.getOrCreateIntProperty(item, heading, value);
			prop.comment = comment;
			return new Integer(prop.value).intValue();
		}
		catch (Exception e)
		{
			System.out.println(new StringBuilder().append("[TFC] Error while trying to add Integer, config wasn't loaded properly!").toString());
		}return value;
	}
}
