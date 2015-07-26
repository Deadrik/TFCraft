package com.bioxx.tfc.api;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import com.bioxx.tfc.TerraFirmaCraft;

public class TFCOptions
{
	public static boolean enablePowderKegs = true;
	public static boolean enableBetterGrass;
	public static boolean enableDebugMode;
	public static boolean iDontLikeOnions;
	public static boolean enableOreTest;
	//public static boolean use2DGrill;
	public static boolean generateSmoke;
	public static boolean enableDetailedBlockSolidSide;
	public static int maxCountOfTranspSubBlocksOnSide;

	public static boolean enableCropsDie;

	public static int minimumRockLoad;
	public static int initialCollapseRatio;
	public static int propogateCollapseChance;
	public static boolean enableCaveIns;
	public static boolean enableCaveInsDestroyOre;
	
	public static int ravineRarity;
	public static int lavaFissureRarity;
	public static int waterFissureRarity;

	public static int yearLength;
	public static float pitKilnBurnTime = 8.0f;
	public static float bloomeryBurnTime = 14.4f;
	public static float charcoalPitBurnTime = 18.0f;
	public static int torchBurnTime = 48;

	public static float cropGrowthMultiplier = 1.0f;
	public static float saplingTimerMultiplier = 1.0f;
	public static float tempIncreaseMultiplier = 1.0f;
	public static float tempDecreaseMultiplier = 1.0f;
	public static float animalTimeMultiplier = 1.0f;

	public static float animalsSpawnMultiplier = 1.0f;

	public static int maxProtectionMonths;
	public static int protectionGain;
	public static int protectionBuffer;
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
	public static boolean useDecayProtection = true;
	public static int decayProtectionDays = 18;
	public static float decayMultiplier = 1.0f;
	public static int simSpeedNoPlayers = 1000;

	public static int smallOreUnits = 10;
	public static int poorOreUnits = 15;
	public static int normalOreUnits = 25;
	public static int richOreUnits = 35;

	public static String quiverHUDPosition = "bottomleft";

	public static int oilLampFuelMult = 4;
	
	public static boolean enableOverworkingChunks = true;
	public static int goldPanLimit = 50;
	public static int sluiceLimit = 300;

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
			TerraFirmaCraft.log.error(new StringBuilder().append("Error while trying to add Integer, config wasn't loaded properly!").toString());
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
			TerraFirmaCraft.log.error(new StringBuilder().append("Error while trying to add Integer, config wasn't loaded properly!").toString());
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
			TerraFirmaCraft.log.error(new StringBuilder().append("Error while trying to add Integer, config wasn't loaded properly!").toString());
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
			TerraFirmaCraft.log.error(new StringBuilder().append("Error while trying to add Integer, config wasn't loaded properly!").toString());
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
			TerraFirmaCraft.log.error(new StringBuilder().append("Error while trying to add Double, config wasn't loaded properly!").toString());
		}
		return value;
	}

	public static String getStringFor(Configuration config, String heading, String item, String value)
	{
		if (config == null)
			return value;
		try
		{
			Property prop = config.get(heading, item, value);
			return prop.getString();
		} catch (Exception e)
		{
			TerraFirmaCraft.log.error(new StringBuilder().append("Error while trying to add String, config wasn't loaded properly!").toString());
		}
		return value;
	}

	public static String getStringFor(Configuration config, String heading, String item, String value, String comment)
	{
		if (config == null)
			return value;
		try
		{
			Property prop = config.get(heading, item, value);
			prop.comment = comment;
			return prop.getString();
		} catch (Exception e)
		{
			TerraFirmaCraft.log.error(new StringBuilder().append("Error while trying to add String, config wasn't loaded properly!").toString());
		}
		return value;
	}
}
