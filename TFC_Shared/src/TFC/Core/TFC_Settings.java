package TFC.Core;

import java.io.File;

import TFC.TerraFirmaCraft;

import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;

public class TFC_Settings 
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

	public static int minimumRockLoad;
	public static int initialCollapseRatio;
	public static int propogateCollapseChance;
	
	public static int dayLength;
	public static int yearLength;
	
	//////////////////Features////////////////////
	public static int RockLayer2Height = 110;
	public static int RockLayer3Height = 55;
	
	public static byte[] cropNutrientAColor = {(byte) 237, (byte) 28, (byte) 36, (byte) 200};
	public static byte[] cropNutrientBColor = {(byte) 242, (byte) 101, (byte) 34, (byte) 200};
	public static byte[] cropNutrientCColor = {(byte) 247, (byte) 148, (byte) 49, (byte) 200};

	static
	{
		try
		{
			config = new Configuration(new File(TerraFirmaCraft.proxy.getMinecraftDir(), "/config/TFCOptions.cfg"));
			config.load();
		} catch (Exception e) {
			System.out.println(new StringBuilder().append("[TFC] Error while trying to access settings configuration!").toString());
			config = null;
		}
		System.out.println(new StringBuilder().append("[TFC] Loading Settings").toString());
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
		yearLength = getIntFor(config,"General","yearLength",96, "This is how many days are in a year. Keep this to multiples of 12.");
		//Caveins
		minimumRockLoad = getIntFor(config,"Cavein Options","minimumRockLoad",1, "This is the minimum number of solid blocks that must be over a section in order for it to collapse.");
		initialCollapseRatio = getIntFor(config,"Cavein Options","initialCollapseRatio",40, "This number is a 1 in X chance that when you mine a block, a collapse will occur.");
		propogateCollapseChance = getIntFor(config,"Cavein Options","propogateCollapseChance",35, "This number is the likelyhood for each block to propogate the collapse farther.");

		cropNutrientAColor[0] = getByteFor(config,"ColorNutrientA","Red", (byte) 237);
		cropNutrientAColor[1] = getByteFor(config,"ColorNutrientA","Green", (byte) 28);
		cropNutrientAColor[2] = getByteFor(config,"ColorNutrientA","Blue", (byte) 36);
		cropNutrientAColor[3] = getByteFor(config,"ColorNutrientA","Alpha", (byte) 200);
		
		cropNutrientBColor[0] = getByteFor(config,"ColorNutrientB","Red", (byte) 242);
		cropNutrientBColor[1] = getByteFor(config,"ColorNutrientB","Green", (byte) 101);
		cropNutrientBColor[2] = getByteFor(config,"ColorNutrientB","Blue", (byte) 34);
		cropNutrientBColor[3] = getByteFor(config,"ColorNutrientB","Alpha", (byte) 200);
		
		cropNutrientCColor[0] = getByteFor(config,"ColorNutrientC","Red", (byte) 247);
		cropNutrientCColor[1] = getByteFor(config,"ColorNutrientC","Green", (byte) 148);
		cropNutrientCColor[2] = getByteFor(config,"ColorNutrientC","Blue", (byte) 49);
		cropNutrientCColor[3] = getByteFor(config,"ColorNutrientC","Alpha", (byte) 200);
		
		
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
			Property prop = config.get(heading, item, value);
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
			Property prop = config.get(heading, item, value);
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
			Property prop = config.get(heading, item, value);
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
			Property prop = config.get(heading, item, value);
			prop.comment = comment;
			return new Integer(prop.value).intValue();
		}
		catch (Exception e)
		{
			System.out.println(new StringBuilder().append("[TFC] Error while trying to add Integer, config wasn't loaded properly!").toString());
		}return value;
	}
	
	public static byte getByteFor(Configuration config, String heading, String item, byte value)
	{
		if (config == null)
		{
			return value;
		}
		try
		{
			Property prop = config.get(heading, item, value);
			return new Byte(prop.value).byteValue();
		}
		catch (Exception e)
		{
			System.out.println(new StringBuilder().append("[TFC] Error while trying to add Byte, config wasn't loaded properly!").toString());
		}return value;
	}
}
