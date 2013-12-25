package TFC.API.Constant;

import net.minecraftforge.common.Configuration;
import TFC.API.TFCOptions;

public class TFCBlockID
{
	public static int StoneIgInCobble			= 2068;
	public static int StoneIgIn					= 2069;
	public static int StoneIgInSmooth			= 2070;
	public static int StoneIgInBrick			= 2071;

	public static int StoneSedCobble			= 2072;
	public static int StoneSed					= 2073;
	public static int StoneSedSmooth			= 2074;
	public static int StoneSedBrick				= 2075;

	public static int StoneIgExCobble			= 2076;
	public static int StoneIgEx					= 2077;
	public static int StoneIgExSmooth			= 2078;
	public static int StoneIgExBrick			= 2079;

	public static int StoneMMCobble				= 2080;
	public static int StoneMM					= 2081;
	public static int StoneMMSmooth				= 2082;
	public static int StoneMMBrick				= 2083;

	public static int Dirt						= 2084;
	public static int Dirt2						= 2085;
	public static int Clay						= 2086;
	public static int Clay2						= 2087;
	public static int ClayGrass					= 2088;
	public static int ClayGrass2				= 2089;
	public static int Grass						= 2090;
	public static int Grass2					= 2091;
	public static int Peat						= 2092;
	public static int PeatGrass					= 2093;
	public static int DryGrass					= 2094;
	public static int DryGrass2					= 2095;

	public static int Ore						= 2096;
	public static int Ore2						= 2097;
	public static int Ore3						= 2098;
	public static int LooseRock					= 2099;
	public static int LogPile					= 2100;

	public static int Sulfur					= 2101;
	public static int WoodSupportV				= 2102;
	public static int WoodSupportH				= 2103;

	public static int tilledSoil				= 2104;
	public static int tilledSoil2				= 2105;

	public static int fruitTreeWood				= 2106;
	public static int fruitTreeLeaves			= 2107;
	public static int fruitTreeLeaves2			= 2108;

	public static int Sand						= 2109;
	public static int Sand2						= 2110;

	public static int WoodConstruct				= 2200;

	public static int bucketWater				= 2111;

	public static int Firepit					= 2015;
	public static int Bellows					= 2014;
	public static int Forge						= 2013;
	public static int Scribe					= 2012;
	public static int Anvil						= 2011;
	public static int Anvil2					= 2010;

	public static int Molten					= 2008;
	public static int BlastFurnace				= 2007;
	public static int EarlyBloomery				= 2006;
	public static int Bloom						= 2005;
	public static int Sluice					= 2003;

	public static int stoneStairs				= 2000;
	public static int stoneSlabs				= 2001;
	public static int stoneStalac				= 2002;

	public static int Charcoal					= 2016;

	public static int StoneDetailed				= 2017;

	public static int WoodVert					= 2018;
	public static int WoodHoriz					= 2019;
	public static int WoodHoriz2				= 2020;

	public static int ToolRack					= 2021;
	public static int SpawnMeter				= 2022;
	public static int FoodPrep					= 2023;
	public static int Quern						= 2024;

	public static int WallCobbleIgIn			= 2025;
	public static int WallCobbleIgEx			= 2026;
	public static int WallCobbleSed				= 2027;
	public static int WallCobbleMM				= 2028;
	public static int WallRawIgIn				= 2029;
	public static int WallRawIgEx				= 2030;
	public static int WallRawSed				= 2031;
	public static int WallRawMM					= 2032;
	public static int WallBrickIgIn				= 2033;
	public static int WallBrickIgEx				= 2034;
	public static int WallBrickSed				= 2035;
	public static int WallBrickMM				= 2036;
	public static int WallSmoothIgIn			= 2037;
	public static int WallSmoothIgEx			= 2038;
	public static int WallSmoothSed				= 2039;
	public static int WallSmoothMM				= 2040;

	public static int[] Doors = {2041, 2042, 2043, 2044, 2045, 2046, 2047, 2048,
		2049, 2050, 2051, 2052, 2053, 2054, 2055, 2056}; // 16 Doors

	public static int IngotPile					= 2060;
	public static int Barrel					= 2061;
	public static int Thatch					= 2062;
	public static int Moss						= 2063;
	public static int Flora						= 2064;
	public static int Pottery					= 2065;
	public static int Tuyere					= 2066;
	public static int Crucible					= 2067;


	static Configuration config;

	public static void Setup(Configuration config)
	{

		StoneIgInCobble	= TFCOptions.getIntFor(config,"block", "StoneIgInCobble", StoneIgInCobble);
		StoneIgIn = TFCOptions.getIntFor(config, "block", "StoneIgIn", StoneIgIn);
		StoneIgInSmooth = TFCOptions.getIntFor(config, "block", "StoneIgInSmooth", StoneIgInSmooth);
		StoneIgInBrick = TFCOptions.getIntFor(config, "block", "StoneIgInBrick", StoneIgInBrick);

		StoneSedCobble = TFCOptions.getIntFor(config, "block", "StoneSedCobble", StoneSedCobble);
		StoneSed = TFCOptions.getIntFor(config, "block", "StoneSed", StoneSed);
		StoneSedSmooth = TFCOptions.getIntFor(config, "block", "StoneSedSmooth", StoneSedSmooth);
		StoneSedBrick = TFCOptions.getIntFor(config, "block", "StoneSedBrick", StoneSedBrick);

		StoneIgExCobble = TFCOptions.getIntFor(config, "block", "StoneIgExCobble", StoneIgExCobble);
		StoneIgEx = TFCOptions.getIntFor(config, "block", "StoneIgEx", StoneIgEx);
		StoneIgExSmooth = TFCOptions.getIntFor(config, "block", "StoneIgExSmooth", StoneIgExSmooth);
		StoneIgExBrick = TFCOptions.getIntFor(config, "block", "StoneIgExBrick", StoneIgExBrick);

		StoneMMCobble = TFCOptions.getIntFor(config, "block", "StoneMMCobble", StoneMMCobble);
		StoneMM = TFCOptions.getIntFor(config, "block", "StoneMM", StoneMM);
		StoneMMSmooth = TFCOptions.getIntFor(config, "block", "StoneMMSmooth", StoneMMSmooth);
		StoneMMBrick = TFCOptions.getIntFor(config, "block", "StoneMMBrick", StoneMMBrick);

		Dirt = TFCOptions.getIntFor(config, "block", "Dirt", Dirt);
		Dirt2 = TFCOptions.getIntFor(config, "block", "Dirt2", Dirt2);
		Clay = TFCOptions.getIntFor(config, "block", "Clay", Clay);
		Clay2 = TFCOptions.getIntFor(config, "block", "Clay2", Clay2);
		ClayGrass = TFCOptions.getIntFor(config, "block", "ClayGrass", ClayGrass);
		ClayGrass2 = TFCOptions.getIntFor(config, "block", "ClayGrass2", ClayGrass2);
		Grass = TFCOptions.getIntFor(config, "block", "Grass", Grass);
		Grass2 = TFCOptions.getIntFor(config, "block", "Grass2", Grass2);
		Peat = TFCOptions.getIntFor(config, "block", "Peat", Peat);
		PeatGrass = TFCOptions.getIntFor(config, "block", "PeatGrass", PeatGrass);
		DryGrass = TFCOptions.getIntFor(config, "block", "DryGrass", DryGrass);
		DryGrass2 = TFCOptions.getIntFor(config, "block", "DryGrass2", DryGrass2);

		Ore = TFCOptions.getIntFor(config, "block", "Ore", Ore);
		Ore2 = TFCOptions.getIntFor(config, "block", "Ore2", Ore2);
		Ore3 = TFCOptions.getIntFor(config, "block", "Ore3", Ore3);
		LooseRock = TFCOptions.getIntFor(config, "block", "LooseRock", LooseRock);
		LogPile = TFCOptions.getIntFor(config, "block", "LogPile", LogPile);

		Sulfur = TFCOptions.getIntFor(config, "block", "Sulfur", Sulfur);
		WoodSupportV = TFCOptions.getIntFor(config, "block", "WoodSupportV", WoodSupportV);
		WoodSupportH = TFCOptions.getIntFor(config, "block", "WoodSupportH", WoodSupportH);

		tilledSoil = TFCOptions.getIntFor(config, "block", "tilledSoil", tilledSoil);
		tilledSoil2 = TFCOptions.getIntFor(config, "block", "tilledSoil2", tilledSoil2);

		fruitTreeWood = TFCOptions.getIntFor(config, "block", "fruitTreeWood", fruitTreeWood);
		fruitTreeLeaves = TFCOptions.getIntFor(config, "block", "fruitTreeLeaves", fruitTreeLeaves);
		fruitTreeLeaves2 = TFCOptions.getIntFor(config, "block", "fruitTreeLeaves2", fruitTreeLeaves2);

		Sand = TFCOptions.getIntFor(config, "block", "Sand", Sand);
		Sand2 = TFCOptions.getIntFor(config, "block", "Sand2", Sand2);

		WoodConstruct = TFCOptions.getIntFor(config, "block", "WoodConstruct", WoodConstruct);

		bucketWater = TFCOptions.getIntFor(config, "block", "bucketWater", bucketWater);

		Firepit = TFCOptions.getIntFor(config, "block", "Firepit", Firepit);
		Bellows = TFCOptions.getIntFor(config, "block", "Bellows", Bellows);
		Forge = TFCOptions.getIntFor(config, "block", "Forge", Forge);
		Scribe = TFCOptions.getIntFor(config, "block", "Scribe", Scribe);
		Anvil = TFCOptions.getIntFor(config, "block", "Anvil", Anvil);
		Anvil2 = TFCOptions.getIntFor(config, "block", "Anvil2", Anvil2);

		Molten = TFCOptions.getIntFor(config, "block", "Molten", Molten);
		BlastFurnace = TFCOptions.getIntFor(config, "block", "BlastFurnace", BlastFurnace);
		EarlyBloomery = TFCOptions.getIntFor(config, "block", "EarlyBloomery", EarlyBloomery);
		Bloom = TFCOptions.getIntFor(config, "block", "Bloom", Bloom);
		Sluice = TFCOptions.getIntFor(config, "block", "Sluice", Sluice);

		stoneStairs = TFCOptions.getIntFor(config, "block", "stoneStairs", stoneStairs);
		stoneSlabs = TFCOptions.getIntFor(config, "block", "stoneSlabs", stoneSlabs);
		stoneStalac = TFCOptions.getIntFor(config, "block", "stoneStalac", stoneStalac);

		Charcoal = TFCOptions.getIntFor(config, "block", "Charcoal", Charcoal);

		StoneDetailed = TFCOptions.getIntFor(config, "block", "StoneDetailed", StoneDetailed);

		WoodVert = TFCOptions.getIntFor(config, "block", "WoodVert", WoodVert);
		WoodHoriz = TFCOptions.getIntFor(config, "block", "WoodHoriz", WoodHoriz);
		WoodHoriz2 = TFCOptions.getIntFor(config, "block", "WoodHoriz2", WoodHoriz2);

		ToolRack = TFCOptions.getIntFor(config, "block", "ToolRack", ToolRack);
		SpawnMeter = TFCOptions.getIntFor(config, "block", "SpawnMeter", SpawnMeter);
		FoodPrep = TFCOptions.getIntFor(config, "block", "FoodPrep", FoodPrep);
		Quern = TFCOptions.getIntFor(config, "block", "Quern", Quern);

		WallCobbleIgIn = TFCOptions.getIntFor(config, "block", "WallCobbleIgIn", WallCobbleIgIn);
		WallCobbleIgEx = TFCOptions.getIntFor(config, "block", "WallCobbleIgEx", WallCobbleIgEx);
		WallCobbleSed = TFCOptions.getIntFor(config, "block", "WallCobbleSed", WallCobbleSed);
		WallCobbleMM = TFCOptions.getIntFor(config, "block", "WallCobbleMM", WallCobbleMM);
		WallRawIgIn = TFCOptions.getIntFor(config, "block", "WallRawIgIn", WallRawIgIn);
		WallRawIgEx = TFCOptions.getIntFor(config, "block", "WallRawIgEx", WallRawIgEx);
		WallRawSed = TFCOptions.getIntFor(config, "block", "WallRawSed", WallRawSed);
		WallRawMM = TFCOptions.getIntFor(config, "block", "WallRawMM", WallRawMM);
		WallBrickIgIn = TFCOptions.getIntFor(config, "block", "WallBrickIgIn", WallBrickIgIn);
		WallBrickIgEx = TFCOptions.getIntFor(config, "block", "WallBrickIgEx", WallBrickIgEx);
		WallBrickSed = TFCOptions.getIntFor(config, "block", "WallBrickSed", WallBrickSed);
		WallBrickMM = TFCOptions.getIntFor(config, "block", "WallBrickMM", WallBrickMM);
		WallSmoothIgIn = TFCOptions.getIntFor(config, "block", "WallSmoothIgIn", WallSmoothIgIn);
		WallSmoothIgEx = TFCOptions.getIntFor(config, "block", "WallSmoothIgEx", WallSmoothIgEx);
		WallSmoothSed = TFCOptions.getIntFor(config, "block", "WallSmoothSed", WallSmoothSed);
		WallSmoothMM = TFCOptions.getIntFor(config, "block", "WallSmoothMM", WallSmoothMM);

		for (int i=0; i < Global.WOOD_ALL.length; i++) {
			Doors[i] = TFCOptions.getIntFor(config,"block","Door"+Global.WOOD_ALL[i].replaceAll(" ", ""), Doors[i]);
		}

		IngotPile = TFCOptions.getIntFor(config, "block", "IngotPile", IngotPile);
		Barrel = TFCOptions.getIntFor(config, "block", "Barrel", Barrel);
		Thatch = TFCOptions.getIntFor(config, "block", "Thatch", Thatch);
		Moss = TFCOptions.getIntFor(config, "block", "Moss", Moss);
		Flora = TFCOptions.getIntFor(config, "block", "Flora", Flora);
		Pottery = TFCOptions.getIntFor(config, "block", "Pottery", Pottery);
		Tuyere = TFCOptions.getIntFor(config, "block", "Tuyere", Tuyere);
		Crucible = TFCOptions.getIntFor(config, "block", "Crucible", Crucible);

	}
}
