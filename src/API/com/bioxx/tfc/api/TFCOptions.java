package com.bioxx.tfc.api;


public class TFCOptions
{
	public static boolean enablePowderKegs = true;
	public static boolean enableBetterGrass;
	public static boolean enableDebugMode;
	public static boolean onionsAreGross;
	//public static boolean use2DGrill;
	public static boolean generateSmoke = false;
	public static boolean enableSolidDetailed;
	public static int maxRemovedSolidDetailed;

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
}
