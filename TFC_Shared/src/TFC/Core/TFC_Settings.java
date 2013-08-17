package TFC.Core;

import java.io.File;

import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;
import TFC.TerraFirmaCraft;

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
	
	public static boolean enableCropsDie;

	public static int minimumRockLoad;
	public static int initialCollapseRatio;
	public static int propogateCollapseChance;
	
	public static int dayLength;
	public static int yearLength;
	
	public static int maxProtectionMonths;
	public static int protectionGain;
	//////////////////Features////////////////////
	public static int RockLayer2Height = 110;
	public static int RockLayer3Height = 55;
	
	public static int HealthGainRate = 20;
	public static int HealthGainCap = 3000;
	
	public static byte[] cropNutrientAColor = {(byte) 237, (byte) 28, (byte) 36, (byte) 200};
	public static byte[] cropNutrientBColor = {(byte) 242, (byte) 101, (byte) 34, (byte) 200};
	public static byte[] cropNutrientCColor = {(byte) 247, (byte) 148, (byte) 49, (byte) 200};
	
	public static byte[] anvilRuleColor0 = {(byte) 237, (byte) 28, (byte) 36, (byte) 255};
	public static byte[] anvilRuleColor1 = {(byte) 242, (byte) 101, (byte) 34, (byte) 255};
	public static byte[] anvilRuleColor2 = {(byte) 247, (byte) 148, (byte) 49, (byte) 255};
	
	public static int pitKilnBurnTime = 8;

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
		dayLength = getIntFor(config,"General","dayLength",24000, "This is how many ticks are in a minecraft day. 24000 is a standard MC cycle. Setting to 48000 will double the length of days.");
		yearLength = getIntFor(config,"General","yearLength",96, "This is how many days are in a year. Keep this to multiples of 12.");
		//Caveins
		minimumRockLoad = getIntFor(config,"Cavein Options","minimumRockLoad",1, "This is the minimum number of solid blocks that must be over a section in order for it to collapse.");
		initialCollapseRatio = getIntFor(config,"Cavein Options","initialCollapseRatio",40, "This number is a 1 in X chance that when you mine a block, a collapse will occur.");
		propogateCollapseChance = getIntFor(config,"Cavein Options","propogateCollapseChance",35, "This number is the likelihood for each block to propagate the collapse farther.");

		cropNutrientAColor[0] = (byte)getIntFor(config,"ColorNutrientA","Red", 237);
		cropNutrientAColor[1] = (byte)getIntFor(config,"ColorNutrientA","Green", 28);
		cropNutrientAColor[2] = (byte)getIntFor(config,"ColorNutrientA","Blue", 36);
		cropNutrientAColor[3] = (byte)getIntFor(config,"ColorNutrientA","Alpha", 200);
		
		cropNutrientBColor[0] = (byte)getIntFor(config,"ColorNutrientB","Red", 242);
		cropNutrientBColor[1] = (byte)getIntFor(config,"ColorNutrientB","Green", 101);
		cropNutrientBColor[2] = (byte)getIntFor(config,"ColorNutrientB","Blue", 34);
		cropNutrientBColor[3] = (byte)getIntFor(config,"ColorNutrientB","Alpha", 200);
		
		cropNutrientCColor[0] = (byte)getIntFor(config,"ColorNutrientC","Red", 247);
		cropNutrientCColor[1] = (byte)getIntFor(config,"ColorNutrientC","Green", 148);
		cropNutrientCColor[2] = (byte)getIntFor(config,"ColorNutrientC","Blue", 49);
		cropNutrientCColor[3] = (byte)getIntFor(config,"ColorNutrientC","Alpha", 200);
		
		anvilRuleColor0[0] = (byte)getIntFor(config,"anvilRuleColor0","Red", 237);
		anvilRuleColor0[1] = (byte)getIntFor(config,"anvilRuleColor0","Green", 28);
		anvilRuleColor0[2] = (byte)getIntFor(config,"anvilRuleColor0","Blue", 36);
		
		anvilRuleColor1[0] = (byte)getIntFor(config,"anvilRuleColor1","Red", 242);
		anvilRuleColor1[1] = (byte)getIntFor(config,"anvilRuleColor1","Green", 101);
		anvilRuleColor1[2] = (byte)getIntFor(config,"anvilRuleColor1","Blue", 34);
		
		anvilRuleColor2[0] = (byte)getIntFor(config,"anvilRuleColor2","Red", 247);
		anvilRuleColor2[1] = (byte)getIntFor(config,"anvilRuleColor2","Green", 148);
		anvilRuleColor2[2] = (byte)getIntFor(config,"anvilRuleColor2","Blue", 49);
		
		enableCropsDie = getBooleanFor(config, "Crops","enableCropsDie",false);
		
		pitKilnBurnTime = getIntFor(config,"General","pitKilnBurnTime", 8, "This is the number of hours that the pit kiln should burn before being completed. Longer than 8 hours will require players to feed extra logs to the fire beyond the initial 16 in the full log pile. Logs burn for 30 minutes each.");
		maxProtectionMonths = getIntFor(config,"Protection","maxProtectionMonths", 10, "The maximum number of months of spawn protection that can accumulate.");
		protectionGain = getIntFor(config,"Protection","protectionGain", 8, "The number of hours of protection gained in the 3x3 chunk area for spending 1 hour in that chunk.");
		
		HealthGainRate = getIntFor(config,"Player","HealthGainRate", 20, "The rate of Health Gain per experience level. Set to 0 to turn off.");
		HealthGainCap = getIntFor(config,"Player","HealthGainCap", 3000, "The maximum achievable health pool total.");
		
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
			return prop.getBoolean(value);
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
			return prop.getBoolean(value);
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
			return prop.getInt(value);
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
			return prop.getInt(value);
		}
		catch (Exception e)
		{
			System.out.println(new StringBuilder().append("[TFC] Error while trying to add Integer, config wasn't loaded properly!").toString());
		}return value;
	}
}
