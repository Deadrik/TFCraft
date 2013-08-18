package TFC.API.Constant;

import java.io.File;

import net.minecraftforge.common.Configuration;
import TFC.TerraFirmaCraft;
import TFC.Core.TFC_Settings;

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
	
	public static void Setup()
	{
		try
		{
			config = new net.minecraftforge.common.Configuration(
					new File(TerraFirmaCraft.proxy.getMinecraftDir(), "/config/TFC.cfg"));
			config.load();
		} catch (Exception e) {
			System.out.println(new StringBuilder().append("[TFC] Error while trying to access item configuration!").toString());
			config = null;
		}
		
		StoneIgInCobble	= TFC_Settings.getIntFor(config,"block", "StoneIgInCobble", StoneIgInCobble);
		StoneIgIn = TFC_Settings.getIntFor(config, "block", "StoneIgIn", StoneIgIn);
		StoneIgInSmooth = TFC_Settings.getIntFor(config, "block", "StoneIgInSmooth", StoneIgInSmooth);
		StoneIgInBrick = TFC_Settings.getIntFor(config, "block", "StoneIgInBrick", StoneIgInBrick);

		StoneSedCobble = TFC_Settings.getIntFor(config, "block", "StoneSedCobble", StoneSedCobble);
		StoneSed = TFC_Settings.getIntFor(config, "block", "StoneSed", StoneSed);
		StoneSedSmooth = TFC_Settings.getIntFor(config, "block", "StoneSedSmooth", StoneSedSmooth);
		StoneSedBrick = TFC_Settings.getIntFor(config, "block", "StoneSedBrick", StoneSedBrick);

		StoneIgExCobble = TFC_Settings.getIntFor(config, "block", "StoneIgExCobble", StoneIgExCobble);
		StoneIgEx = TFC_Settings.getIntFor(config, "block", "StoneIgEx", StoneIgEx);
		StoneIgExSmooth = TFC_Settings.getIntFor(config, "block", "StoneIgExSmooth", StoneIgExSmooth);
		StoneIgExBrick = TFC_Settings.getIntFor(config, "block", "StoneIgExBrick", StoneIgExBrick);

		StoneMMCobble = TFC_Settings.getIntFor(config, "block", "StoneMMCobble", StoneMMCobble);
		StoneMM = TFC_Settings.getIntFor(config, "block", "StoneMM", StoneMM);
		StoneMMSmooth = TFC_Settings.getIntFor(config, "block", "StoneMMSmooth", StoneMMSmooth);
		StoneMMBrick = TFC_Settings.getIntFor(config, "block", "StoneMMBrick", StoneMMBrick);

		Dirt = TFC_Settings.getIntFor(config, "block", "Dirt", Dirt);
		Dirt2 = TFC_Settings.getIntFor(config, "block", "Dirt2", Dirt2);
		Clay = TFC_Settings.getIntFor(config, "block", "Clay", Clay);
		Clay2 = TFC_Settings.getIntFor(config, "block", "Clay2", Clay2);
		ClayGrass = TFC_Settings.getIntFor(config, "block", "ClayGrass", ClayGrass);
		ClayGrass2 = TFC_Settings.getIntFor(config, "block", "ClayGrass2", ClayGrass2);
		Grass = TFC_Settings.getIntFor(config, "block", "Grass", Grass);
		Grass2 = TFC_Settings.getIntFor(config, "block", "Grass2", Grass2);
		Peat = TFC_Settings.getIntFor(config, "block", "Peat", Peat);
		PeatGrass = TFC_Settings.getIntFor(config, "block", "PeatGrass", PeatGrass);
		DryGrass = TFC_Settings.getIntFor(config, "block", "DryGrass", DryGrass);
		DryGrass2 = TFC_Settings.getIntFor(config, "block", "DryGrass2", DryGrass2);

		Ore = TFC_Settings.getIntFor(config, "block", "Ore", Ore);
		Ore2 = TFC_Settings.getIntFor(config, "block", "Ore2", Ore2);
		Ore3 = TFC_Settings.getIntFor(config, "block", "Ore3", Ore3);
		LooseRock = TFC_Settings.getIntFor(config, "block", "LooseRock", LooseRock);
		LogPile = TFC_Settings.getIntFor(config, "block", "LogPile", LogPile);

		Sulfur = TFC_Settings.getIntFor(config, "block", "Sulfur", Sulfur);
		WoodSupportV = TFC_Settings.getIntFor(config, "block", "WoodSupportV", WoodSupportV);
		WoodSupportH = TFC_Settings.getIntFor(config, "block", "WoodSupportH", WoodSupportH);

		tilledSoil = TFC_Settings.getIntFor(config, "block", "tilledSoil", tilledSoil);
		tilledSoil2 = TFC_Settings.getIntFor(config, "block", "tilledSoil2", tilledSoil2);

		fruitTreeWood = TFC_Settings.getIntFor(config, "block", "fruitTreeWood", fruitTreeWood);
		fruitTreeLeaves = TFC_Settings.getIntFor(config, "block", "fruitTreeLeaves", fruitTreeLeaves);
		fruitTreeLeaves2 = TFC_Settings.getIntFor(config, "block", "fruitTreeLeaves2", fruitTreeLeaves2);

		Sand = TFC_Settings.getIntFor(config, "block", "Sand", Sand);
		Sand2 = TFC_Settings.getIntFor(config, "block", "Sand2", Sand2);

		WoodConstruct = TFC_Settings.getIntFor(config, "block", "WoodConstruct", WoodConstruct);

		bucketWater = TFC_Settings.getIntFor(config, "block", "bucketWater", bucketWater);

		Firepit = TFC_Settings.getIntFor(config, "block", "Firepit", Firepit);
		Bellows = TFC_Settings.getIntFor(config, "block", "Bellows", Bellows);
		Forge = TFC_Settings.getIntFor(config, "block", "Forge", Forge);
		Scribe = TFC_Settings.getIntFor(config, "block", "Scribe", Scribe);
		Anvil = TFC_Settings.getIntFor(config, "block", "Anvil", Anvil);
		Anvil2 = TFC_Settings.getIntFor(config, "block", "Anvil2", Anvil2);

		Molten = TFC_Settings.getIntFor(config, "block", "Molten", Molten);
		BlastFurnace = TFC_Settings.getIntFor(config, "block", "BlastFurnace", BlastFurnace);
		EarlyBloomery = TFC_Settings.getIntFor(config, "block", "EarlyBloomery", EarlyBloomery);
		Bloom = TFC_Settings.getIntFor(config, "block", "Bloom", Bloom);
		Sluice = TFC_Settings.getIntFor(config, "block", "Sluice", Sluice);

		stoneStairs = TFC_Settings.getIntFor(config, "block", "stoneStairs", stoneStairs);
		stoneSlabs = TFC_Settings.getIntFor(config, "block", "stoneSlabs", stoneSlabs);
		stoneStalac = TFC_Settings.getIntFor(config, "block", "stoneStalac", stoneStalac);

		Charcoal = TFC_Settings.getIntFor(config, "block", "Charcoal", Charcoal);

		StoneDetailed = TFC_Settings.getIntFor(config, "block", "StoneDetailed", StoneDetailed);

		WoodVert = TFC_Settings.getIntFor(config, "block", "WoodVert", WoodVert);
		WoodHoriz = TFC_Settings.getIntFor(config, "block", "WoodHoriz", WoodHoriz);
		WoodHoriz2 = TFC_Settings.getIntFor(config, "block", "WoodHoriz2", WoodHoriz2);

		ToolRack = TFC_Settings.getIntFor(config, "block", "ToolRack", ToolRack);
		SpawnMeter = TFC_Settings.getIntFor(config, "block", "SpawnMeter", SpawnMeter);
		FoodPrep = TFC_Settings.getIntFor(config, "block", "FoodPrep", FoodPrep);
		Quern = TFC_Settings.getIntFor(config, "block", "Quern", Quern);

		WallCobbleIgIn = TFC_Settings.getIntFor(config, "block", "WallCobbleIgIn", WallCobbleIgIn);
		WallCobbleIgEx = TFC_Settings.getIntFor(config, "block", "WallCobbleIgEx", WallCobbleIgEx);
		WallCobbleSed = TFC_Settings.getIntFor(config, "block", "WallCobbleSed", WallCobbleSed);
		WallCobbleMM = TFC_Settings.getIntFor(config, "block", "WallCobbleMM", WallCobbleMM);
		WallRawIgIn = TFC_Settings.getIntFor(config, "block", "WallRawIgIn", WallRawIgIn);
		WallRawIgEx = TFC_Settings.getIntFor(config, "block", "WallRawIgEx", WallRawIgEx);
		WallRawSed = TFC_Settings.getIntFor(config, "block", "WallRawSed", WallRawSed);
		WallRawMM = TFC_Settings.getIntFor(config, "block", "WallRawMM", WallRawMM);
		WallBrickIgIn = TFC_Settings.getIntFor(config, "block", "WallBrickIgIn", WallBrickIgIn);
		WallBrickIgEx = TFC_Settings.getIntFor(config, "block", "WallBrickIgEx", WallBrickIgEx);
		WallBrickSed = TFC_Settings.getIntFor(config, "block", "WallBrickSed", WallBrickSed);
		WallBrickMM = TFC_Settings.getIntFor(config, "block", "WallBrickMM", WallBrickMM);
		WallSmoothIgIn = TFC_Settings.getIntFor(config, "block", "WallSmoothIgIn", WallSmoothIgIn);
		WallSmoothIgEx = TFC_Settings.getIntFor(config, "block", "WallSmoothIgEx", WallSmoothIgEx);
		WallSmoothSed = TFC_Settings.getIntFor(config, "block", "WallSmoothSed", WallSmoothSed);
		WallSmoothMM = TFC_Settings.getIntFor(config, "block", "WallSmoothMM", WallSmoothMM);

		for (int i=0; i < Global.WOOD_ALL.length; i++) {
			Doors[i] = TFC_Settings.getIntFor(config,"block","Door"+Global.WOOD_ALL[i].replaceAll(" ", ""), Doors[i]);
		}
		
		IngotPile = TFC_Settings.getIntFor(config, "block", "IngotPile", IngotPile);
		Barrel = TFC_Settings.getIntFor(config, "block", "Barrel", Barrel);
		Thatch = TFC_Settings.getIntFor(config, "block", "Thatch", Thatch);
		Moss = TFC_Settings.getIntFor(config, "block", "Moss", Moss);
		Flora = TFC_Settings.getIntFor(config, "block", "Flora", Flora);
		Pottery = TFC_Settings.getIntFor(config, "block", "Pottery", Pottery);
		Tuyere = TFC_Settings.getIntFor(config, "block", "Tuyere", Tuyere);
		Crucible = TFC_Settings.getIntFor(config, "block", "Crucible", Crucible);


		if (config != null) {
			config.save();
		}
	}
}
