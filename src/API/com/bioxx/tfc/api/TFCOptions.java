package com.bioxx.tfc.api;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class TFCOptions
{
	public static boolean enableVanillaDiamondRecipe;
	public static boolean enableVanillaIronRecipe;
	public static boolean enableVanillaGoldRecipe;
	public static boolean enableVanillaFurnaceRecipes;
	public static boolean enableVanillaRecipes;
	public static boolean enableBetterGrass;
	public static boolean enableInnerGrassFix;
	public static boolean enableDebugMode;
	public static boolean iDontLikeOnions;
	public static boolean enableOreTest;

	public static boolean enableCropsDie;

	public static int minimumRockLoad;
	public static int initialCollapseRatio;
	public static int propogateCollapseChance;

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
	public static byte[] cropFertilizerColor = {(byte) 255, (byte) 255, (byte) 0, (byte) 200};

	public static byte[] anvilRuleColor0 = {(byte) 237, (byte) 28, (byte) 36, (byte) 255};
	public static byte[] anvilRuleColor1 = {(byte) 242, (byte) 101, (byte) 34, (byte) 255};
	public static byte[] anvilRuleColor2 = {(byte) 247, (byte) 148, (byte) 49, (byte) 255};

	public static int pitKilnBurnTime = 8;
	public static boolean useDecayProtection = true;
	public static int decayProtectionDays = 18;
	public static float decayMultiplier = 1.0f;

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

	public static int getIntFor(Configuration config, String heading, String item, int value)
	{
		if (config == null)
			return value;
		try
		{
			Property prop = config.get(heading, item, value);
			return prop.getInt(value);
		}
		catch (Exception e)
		{
			System.out.println(new StringBuilder().append("[TFC] Error while trying to add Integer, config wasn't loaded properly!").toString());
		}
		return value;
	}

	public static int getIntFor(Configuration config,String heading, String item, int value, String comment)
	{
		if (config == null)
			return value;
		try
		{
			Property prop = config.get(heading, item, value);
			prop.comment = comment;
			return prop.getInt(value);
		}
		catch (Exception e)
		{
			System.out.println(new StringBuilder().append("[TFC] Error while trying to add Integer, config wasn't loaded properly!").toString());
		}
		return value;
	}

	public static double getDoubleFor(Configuration config,String heading, String item, double value, String comment)
	{
		if (config == null)
			return value;
		try
		{
			Property prop = config.get(heading, item, value);
			prop.comment = comment;
			return prop.getDouble(value);
		}
		catch (Exception e)
		{
			System.out.println(new StringBuilder().append("[TFC] Error while trying to add Double, config wasn't loaded properly!").toString());
		}
		return value;
	}
}
