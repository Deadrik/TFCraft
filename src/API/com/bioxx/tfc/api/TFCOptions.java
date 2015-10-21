package com.bioxx.tfc.api;

/**
 * Values in here are default, for bounds look in:
 * @see com.bioxx.tfc.Core.Config.TFC_ConfigFiles
 *
 * The order of fields is the same as there appearance in the config file.
 */
public class TFCOptions
{
	// General
	public static boolean enableNEIHiding = true;
	public static boolean enablePowderKegs = true;
	public static boolean enableBetterGrass = true;
	public static boolean enableSaplingDrops = true;
	public static boolean enableSeedDrops = true;
	public static boolean enableDebugMode;
	public static boolean enableFiniteWater;
	public static boolean onionsAreGross;
	public static boolean generateSmoke;
	public static String quiverHUDPosition = "bottomleft";
	public static boolean enableSolidDetailed = true;
	public static int maxRemovedSolidDetailed = 12;
	public static int hammerBreakSpeed = 90;

	// Time
	public static int yearLength = 96;
	public static float pitKilnBurnTime = 8.0f;
	public static float bloomeryBurnTime = 14.4f;
	public static float charcoalPitBurnTime = 18.0f;
	public static int torchBurnTime = 48;
	public static float saplingTimerMultiplier = 1.0f;
	public static float tempIncreaseMultiplier = 1.0f;
	public static float tempDecreaseMultiplier = 1.0f;
	public static int oilLampFuelMult = 8;
	public static float animalTimeMultiplier = 1.0f;

	// Food decay
	/**
	 * This is the nth root of 1.5 where the root is 24. This means that, excluding
	 * environmental factors, food will decay at 50% per 24 hours.
	 * Easy calculator here: http://www.basic-mathematics.com/nth-root-calculator.html
	 *
	 * Made into a float because the config readout will round it to a float anyways.
	 */
	public static float foodDecayRate = 1.0170378966055869517978300569768f;
	public static boolean useDecayProtection = true;
	public static int decayProtectionDays = 24;
	public static float decayMultiplier = 1.0f;

	// Cave-in
	public static int minimumRockLoad = 1;
	public static int initialCollapseRatio = 10;
	public static int propogateCollapseChance = 55;
	public static boolean enableCaveIns = true;
	public static boolean enableCaveInsDestroyOre = true;

	// World gen
	public static int ravineRarity = 100;
	public static int lavaFissureRarity = 25;
	public static int waterFissureRarity = 90;

	// Crops
	public static boolean enableCropsDie;
	public static float cropGrowthMultiplier = 1.0f;

	// Protection
	public static int maxProtectionMonths = 10;
	public static int protectionGain = 8;
	public static int protectionBuffer = 24;

	// Player
	public static int healthGainRate = 20;
	public static int healthGainCap = 3000;

	// Materials
	public static int smallOreUnits = 10;
	public static int poorOreUnits = 15;
	public static int normalOreUnits = 25;
	public static int richOreUnits = 35;

	// Server
	public static int simSpeedNoPlayers = 100;

	// Overworked
	public static boolean enableOverworkingChunks = true;
	public static int goldPanLimit = 50;
	public static int sluiceLimit = 300;

	// Colors
	public static byte[] cropNutrientAColor = {(byte) 237, (byte) 28, (byte) 36, (byte) 200};
	public static byte[] cropNutrientBColor = {(byte) 242, (byte) 101, (byte) 34, (byte) 200};
	public static byte[] cropNutrientCColor = {(byte) 247, (byte) 148, (byte) 49, (byte) 200};
	public static byte[] cropFertilizerColor = {(byte) 255, (byte) 255, (byte) 0, (byte) 200};
	public static byte[] anvilRuleColor0 = {(byte) 237, (byte) 28, (byte) 36, (byte) 255};
	public static byte[] anvilRuleColor1 = {(byte) 242, (byte) 101, (byte) 34, (byte) 255};
	public static byte[] anvilRuleColor2 = {(byte) 247, (byte) 148, (byte) 49, (byte) 255};

	// Not actual options, could be moved to Global perhaps?
	public static int rockLayer2Height = 110;
	public static int rockLayer3Height = 55;
}
