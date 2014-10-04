package com.bioxx.tfc;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;

import com.bioxx.tfc.Blocks.BlockBloom;
import com.bioxx.tfc.Blocks.BlockCharcoal;
import com.bioxx.tfc.Blocks.BlockCrop;
import com.bioxx.tfc.Blocks.BlockDetailed;
import com.bioxx.tfc.Blocks.BlockFireBrick;
import com.bioxx.tfc.Blocks.BlockFoodPrep;
import com.bioxx.tfc.Blocks.BlockIngotPile;
import com.bioxx.tfc.Blocks.BlockLogPile;
import com.bioxx.tfc.Blocks.BlockMetalSheet;
import com.bioxx.tfc.Blocks.BlockMetalTrapDoor;
import com.bioxx.tfc.Blocks.BlockMolten;
import com.bioxx.tfc.Blocks.BlockPlanks;
import com.bioxx.tfc.Blocks.BlockSlab;
import com.bioxx.tfc.Blocks.BlockSmoke;
import com.bioxx.tfc.Blocks.BlockSmokeRack;
import com.bioxx.tfc.Blocks.BlockStair;
import com.bioxx.tfc.Blocks.BlockStalactite;
import com.bioxx.tfc.Blocks.BlockSulfur;
import com.bioxx.tfc.Blocks.BlockThatch;
import com.bioxx.tfc.Blocks.BlockWoodSupport;
import com.bioxx.tfc.Blocks.BlockWoodSupport2;
import com.bioxx.tfc.Blocks.BlockWorldItem;
import com.bioxx.tfc.Blocks.Devices.BlockBarrel;
import com.bioxx.tfc.Blocks.Devices.BlockBellows;
import com.bioxx.tfc.Blocks.Devices.BlockBlastFurnace;
import com.bioxx.tfc.Blocks.Devices.BlockChestTFC;
import com.bioxx.tfc.Blocks.Devices.BlockCrucible;
import com.bioxx.tfc.Blocks.Devices.BlockEarlyBloomery;
import com.bioxx.tfc.Blocks.Devices.BlockFirepit;
import com.bioxx.tfc.Blocks.Devices.BlockForge;
import com.bioxx.tfc.Blocks.Devices.BlockGrill;
import com.bioxx.tfc.Blocks.Devices.BlockLargeVessel;
import com.bioxx.tfc.Blocks.Devices.BlockLeatherRack;
import com.bioxx.tfc.Blocks.Devices.BlockLoom;
import com.bioxx.tfc.Blocks.Devices.BlockNestBox;
import com.bioxx.tfc.Blocks.Devices.BlockPottery;
import com.bioxx.tfc.Blocks.Devices.BlockQuern;
import com.bioxx.tfc.Blocks.Devices.BlockSluice;
import com.bioxx.tfc.Blocks.Devices.BlockSpawnMeter;
import com.bioxx.tfc.Blocks.Devices.BlockStand;
import com.bioxx.tfc.Blocks.Devices.BlockStand2;
import com.bioxx.tfc.Blocks.Devices.BlockToolRack;
import com.bioxx.tfc.Blocks.Devices.BlockToolRack2;
import com.bioxx.tfc.Blocks.Devices.BlockWorkbench;
import com.bioxx.tfc.Blocks.Flora.BlockBerryBush;
import com.bioxx.tfc.Blocks.Flora.BlockFlora;
import com.bioxx.tfc.Blocks.Flora.BlockFlower;
import com.bioxx.tfc.Blocks.Flora.BlockFlower2;
import com.bioxx.tfc.Blocks.Flora.BlockFruitLeaves;
import com.bioxx.tfc.Blocks.Flora.BlockFruitWood;
import com.bioxx.tfc.Blocks.Flora.BlockLogHoriz;
import com.bioxx.tfc.Blocks.Flora.BlockLogHoriz2;
import com.bioxx.tfc.Blocks.Flora.BlockLogVert;
import com.bioxx.tfc.Blocks.Flora.BlockLogVert2;
import com.bioxx.tfc.Blocks.Flora.BlockSapling;
import com.bioxx.tfc.Blocks.Flora.BlockSapling2;
import com.bioxx.tfc.Blocks.Flora.BlockWaterPlant;
import com.bioxx.tfc.Blocks.Liquids.BlockFreshWater;
import com.bioxx.tfc.Blocks.Liquids.BlockHotWater;
import com.bioxx.tfc.Blocks.Liquids.BlockLava;
import com.bioxx.tfc.Blocks.Liquids.BlockLiquidStatic;
import com.bioxx.tfc.Blocks.Liquids.BlockSaltWater;
import com.bioxx.tfc.Blocks.Terrain.BlockDryGrass;
import com.bioxx.tfc.Blocks.Terrain.BlockFungi;
import com.bioxx.tfc.Blocks.Terrain.BlockGravel;
import com.bioxx.tfc.Blocks.Terrain.BlockIgEx;
import com.bioxx.tfc.Blocks.Terrain.BlockIgExBrick;
import com.bioxx.tfc.Blocks.Terrain.BlockIgExCobble;
import com.bioxx.tfc.Blocks.Terrain.BlockIgExSmooth;
import com.bioxx.tfc.Blocks.Terrain.BlockIgIn;
import com.bioxx.tfc.Blocks.Terrain.BlockIgInBrick;
import com.bioxx.tfc.Blocks.Terrain.BlockIgInCobble;
import com.bioxx.tfc.Blocks.Terrain.BlockIgInSmooth;
import com.bioxx.tfc.Blocks.Terrain.BlockMM;
import com.bioxx.tfc.Blocks.Terrain.BlockMMBrick;
import com.bioxx.tfc.Blocks.Terrain.BlockMMCobble;
import com.bioxx.tfc.Blocks.Terrain.BlockMMSmooth;
import com.bioxx.tfc.Blocks.Terrain.BlockMoss;
import com.bioxx.tfc.Blocks.Terrain.BlockOre;
import com.bioxx.tfc.Blocks.Terrain.BlockOre2;
import com.bioxx.tfc.Blocks.Terrain.BlockOre3;
import com.bioxx.tfc.Blocks.Terrain.BlockSand;
import com.bioxx.tfc.Blocks.Terrain.BlockSed;
import com.bioxx.tfc.Blocks.Terrain.BlockSedBrick;
import com.bioxx.tfc.Blocks.Terrain.BlockSedCobble;
import com.bioxx.tfc.Blocks.Terrain.BlockSedSmooth;
import com.bioxx.tfc.Blocks.Vanilla.BlockBed;
import com.bioxx.tfc.Blocks.Vanilla.BlockCustomBookshelf;
import com.bioxx.tfc.Blocks.Vanilla.BlockCustomButtonWood;
import com.bioxx.tfc.Blocks.Vanilla.BlockCustomCactus;
import com.bioxx.tfc.Blocks.Vanilla.BlockCustomDoor;
import com.bioxx.tfc.Blocks.Vanilla.BlockCustomFenceGate;
import com.bioxx.tfc.Blocks.Vanilla.BlockCustomFenceGate2;
import com.bioxx.tfc.Blocks.Vanilla.BlockCustomIce;
import com.bioxx.tfc.Blocks.Vanilla.BlockCustomLeaves;
import com.bioxx.tfc.Blocks.Vanilla.BlockCustomLeaves2;
import com.bioxx.tfc.Blocks.Vanilla.BlockCustomLilyPad;
import com.bioxx.tfc.Blocks.Vanilla.BlockCustomPumpkin;
import com.bioxx.tfc.Blocks.Vanilla.BlockCustomReed;
import com.bioxx.tfc.Blocks.Vanilla.BlockCustomSnow;
import com.bioxx.tfc.Blocks.Vanilla.BlockCustomTallGrass;
import com.bioxx.tfc.Blocks.Vanilla.BlockCustomVine;
import com.bioxx.tfc.Blocks.Vanilla.BlockCustomWall;
import com.bioxx.tfc.Blocks.Vanilla.BlockTFCFence;
import com.bioxx.tfc.Blocks.Vanilla.BlockTFCFence2;
import com.bioxx.tfc.Blocks.Vanilla.BlockTorch;
import com.bioxx.tfc.Core.TFCFluid;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Items.ItemBlocks.ItemBarrels;
import com.bioxx.tfc.Items.ItemBlocks.ItemGrill;
import com.bioxx.tfc.Items.ItemBlocks.ItemLargeVessel;
import com.bioxx.tfc.Items.ItemBlocks.ItemLooms;
import com.bioxx.tfc.Items.ItemBlocks.ItemMetalTrapDoor;
import com.bioxx.tfc.Items.ItemBlocks.ItemSoil;
import com.bioxx.tfc.Items.ItemBlocks.ItemStone;
import com.bioxx.tfc.Items.ItemBlocks.ItemToolRack;
import com.bioxx.tfc.Items.ItemBlocks.ItemToolRack2;
import com.bioxx.tfc.Items.ItemBlocks.ItemTorch;
import com.bioxx.tfc.api.Constant.Global;

import cpw.mods.fml.common.registry.GameRegistry;

public class TFCBlocks 
{
	public static int clayGrassRenderId;
	public static int peatGrassRenderId;
	//////////////////////////////////////////////
	public static int sulfurRenderId;
	public static int woodFruitRenderId;
	public static int leavesFruitRenderId;
	public static int woodThickRenderId;
	public static int woodSupportRenderIdH;
	public static int woodSupportRenderIdV;
	public static int grassRenderId;
	public static int oreRenderId;
	public static int moltenRenderId;
	public static int looseRockRenderId;
	public static int snowRenderId;
	public static int FirepitRenderId;
	public static int AnvilRenderId;
	public static int barrelRenderId;
	public static int loomRenderId;
	public static int standRenderId;
	public static int FenceRenderId;
	public static int FenceGateRenderId;
	public static int NestBoxRenderId;
	public static int BellowsRenderId;
	public static int ForgeRenderId;
	public static int sluiceRenderId;
	public static int toolRackRenderId;
	public static int partialRenderId;
	public static int stairRenderId;
	public static int slabRenderId;
	public static int cropRenderId;
	public static int cookingPitRenderId;
	public static int leavesRenderId;
	public static int detailedRenderId;
	public static int foodPrepRenderId;
	public static int quernRenderId;
	public static int fluidRenderId;
	public static int woodConstructRenderId;
	public static int potteryRenderId;
	public static int tuyereRenderId;
	public static int crucibleRenderId;
	public static int berryRenderId;
	public static int pipeRenderId;
	public static int pipeValveRenderId;
	public static int waterPlantRenderId;
	public static int bloomeryRenderId;
	public static int metalsheetRenderId;
	public static int chestRenderId;
	public static int leatherRackRenderId;
	public static int grillRenderId;
	public static int metalTrapDoorRenderId;
	public static int vesselRenderId;
	public static int torchRenderId;
	public static int smokeRenderId;
	public static int smokeRackRenderId;

	public static Block StoneIgIn;
	public static Block StoneIgEx;
	public static Block StoneSed;
	public static Block StoneMM;
	public static Block StoneIgInCobble;
	public static Block StoneIgExCobble;
	public static Block StoneSedCobble;
	public static Block StoneMMCobble;
	public static Block StoneIgInBrick;
	public static Block StoneIgExBrick;
	public static Block StoneSedBrick;
	public static Block StoneMMBrick;
	public static Block StoneIgInSmooth;
	public static Block StoneIgExSmooth;
	public static Block StoneSedSmooth;
	public static Block StoneMMSmooth;
	public static Block Ore;
	public static Block Ore2;
	public static Block Ore3;
	public static Block Sulfur;
	public static Block Planks;
	public static Block Planks2;
	public static Block Leaves;
	public static Block Sapling;
	public static Block Leaves2;
	public static Block Sapling2;
	public static Block WoodSupportV;
	public static Block WoodSupportH;
	public static Block WoodSupportV2;
	public static Block WoodSupportH2;
	public static Block Grass;
	public static Block Grass2;
	public static Block Dirt;
	public static Block Dirt2;
	public static Block Clay;
	public static Block Clay2;
	public static Block ClayGrass;
	public static Block ClayGrass2;
	public static Block Peat;
	public static Block PeatGrass;
	public static Block worldItem;
	public static Block LogPile;
	public static Block tilledSoil;
	public static Block tilledSoil2;
	public static Block Firepit;
	public static Block Bellows;
	public static Block Anvil;
	public static Block Anvil2;
	public static Block Forge;
	public static Block BlastFurnace;
	public static Block Molten;
	public static Block Sluice;
	public static Block fruitTreeWood;
	public static Block fruitTreeLeaves;
	public static Block fruitTreeLeaves2;
	public static Block stoneStairs;
	public static Block stoneSlabs;
	public static Block stoneStalac;
	public static Block Sand;
	public static Block Sand2;
	public static Block DryGrass;
	public static Block DryGrass2;
	public static Block TallGrass;
	public static Block Charcoal;
	public static Block Detailed;

	public static Block WoodConstruct;
	public static Block WoodVert;
	public static Block WoodHoriz;
	public static Block WoodHoriz2;
	public static Block ToolRack;
	public static Block ToolRack2;
	public static Block SpawnMeter;
	public static Block FoodPrep;
	public static Block Quern;
	public static Block WallCobbleIgIn;
	public static Block WallCobbleIgEx;
	public static Block WallCobbleSed;
	public static Block WallCobbleMM;
	public static Block WallRawIgIn;
	public static Block WallRawIgEx;
	public static Block WallRawSed;
	public static Block WallRawMM;
	public static Block WallBrickIgIn;
	public static Block WallBrickIgEx;
	public static Block WallBrickSed;
	public static Block WallBrickMM;
	public static Block WallSmoothIgIn;
	public static Block WallSmoothIgEx;
	public static Block WallSmoothSed;
	public static Block WallSmoothMM;

	public static Block[] Doors = new Block[Global.WOOD_ALL.length];

	public static Block IngotPile;
	public static Block Barrel;
	public static Block Loom;
	public static Block Pottery;
	public static Block Thatch;
	public static Block Moss;
	public static Block BerryBush;
	public static Block Crops;
	public static Block LilyPad;
	public static Block Flowers;
	public static Block Flowers2;
	public static Block Fungi;
	public static Block Flora;
	public static Block EarlyBloomery;
	public static Block Bloom;
	public static Block Crucible;
	public static Block FireBrick;
	public static Block MetalSheet;

	public static Block NestBox;

	public static Block Fence;
	public static Block FenceGate;
	public static Block Fence2;
	public static Block FenceGate2;

	public static Block StrawHideBed;

	public static Block ArmourStand;
	public static Block ArmourStand2;

	public static Block LogNatural;
	public static Block LogNatural2;
	public static Block WoodHoriz3;
	public static Block WoodHoriz4;
	public static Block WoodVert2;

	public static Block SaltWater;
	public static Block SaltWaterStationary;
	public static Block FreshWater;
	public static Block FreshWaterStationary;
	public static Block HotWater;
	public static Block HotWaterStationary;
	public static Block Lava;
	public static Block LavaStationary;
	public static Block Ice;

	public static Block WaterPlant;

	public static Block Bookshelf;
	public static Block Torch;
	public static Block Chest;
	public static Block Workbench;
	public static Block Cactus;
	public static Block Reeds;
	public static Block Pumpkin;
	public static Block ButtonWood;
	public static Block Vine;
	public static Block LeatherRack;

	public static Block Gravel;
	public static Block Gravel2;

	public static Block Grill;
	public static Block MetalTrapDoor;
	public static Block Vessel;
	public static Block Smoke;
	public static Block SmokeRack;
	public static Block Snow;

	public static void RegisterBlocks()
	{
		GameRegistry.registerBlock(Ore, "Ore1");
		GameRegistry.registerBlock(Ore2, "Ore2");
		GameRegistry.registerBlock(Ore3, "Ore3");
		GameRegistry.registerBlock(StoneIgIn, ItemStone.class, "StoneIgIn");
		GameRegistry.registerBlock(StoneIgEx, ItemStone.class, "StoneIgEx");
		GameRegistry.registerBlock(StoneSed, ItemStone.class, "StoneSed");
		GameRegistry.registerBlock(StoneMM, ItemStone.class, "StoneMM");

		GameRegistry.registerBlock(StoneIgInCobble, ItemStone.class, "StoneIgInCobble");
		GameRegistry.registerBlock(StoneIgExCobble, ItemStone.class, "StoneIgExCobble");
		GameRegistry.registerBlock(StoneSedCobble, ItemStone.class, "StoneSedCobble");
		GameRegistry.registerBlock(StoneMMCobble, ItemStone.class, "StoneMMCobble");
		GameRegistry.registerBlock(StoneIgInSmooth, ItemStone.class, "StoneIgInSmooth");
		GameRegistry.registerBlock(StoneIgExSmooth, ItemStone.class, "StoneIgExSmooth");
		GameRegistry.registerBlock(StoneSedSmooth, ItemStone.class, "StoneSedSmooth");
		GameRegistry.registerBlock(StoneMMSmooth, ItemStone.class, "StoneMMSmooth");
		GameRegistry.registerBlock(StoneIgInBrick, ItemStone.class, "StoneIgInBrick");
		GameRegistry.registerBlock(StoneIgExBrick, ItemStone.class, "StoneIgExBrick");
		GameRegistry.registerBlock(StoneSedBrick, ItemStone.class, "StoneSedBrick");
		GameRegistry.registerBlock(StoneMMBrick, ItemStone.class, "StoneMMBrick");

		GameRegistry.registerBlock(Dirt, ItemSoil.class, "Dirt");
		GameRegistry.registerBlock(Dirt2, ItemSoil.class, "Dirt2");
		GameRegistry.registerBlock(Sand, ItemSoil.class, "Sand");
		GameRegistry.registerBlock(Sand2, ItemSoil.class, "Sand2");
		GameRegistry.registerBlock(Clay, ItemSoil.class,"Clay");
		GameRegistry.registerBlock(Clay2, ItemSoil.class,"Clay2");
		GameRegistry.registerBlock(Grass, ItemSoil.class, "Grass");
		GameRegistry.registerBlock(Grass2, ItemSoil.class, "Grass2");
		GameRegistry.registerBlock(ClayGrass, ItemSoil.class, "ClayGrass");
		GameRegistry.registerBlock(ClayGrass2, ItemSoil.class, "ClayGrass2");
		GameRegistry.registerBlock(PeatGrass, ItemSoil.class, "PeatGrass");
		GameRegistry.registerBlock(Peat, ItemSoil.class, "Peat");
		GameRegistry.registerBlock(DryGrass, ItemSoil.class, "DryGrass");
		GameRegistry.registerBlock(DryGrass2, ItemSoil.class, "DryGrass2");
		GameRegistry.registerBlock(TallGrass, com.bioxx.tfc.Items.ItemBlocks.ItemCustomTallGrass.class, "TallGrass");
		GameRegistry.registerBlock(worldItem, "LooseRock");
		GameRegistry.registerBlock(LogPile, "LogPile");
		GameRegistry.registerBlock(Charcoal, "Charcoal");
		GameRegistry.registerBlock(Detailed, "Detailed");

		GameRegistry.registerBlock(tilledSoil, ItemSoil.class, "tilledSoil");
		GameRegistry.registerBlock(tilledSoil2, ItemSoil.class, "tilledSoil2");

		GameRegistry.registerBlock(WoodSupportV, com.bioxx.tfc.Items.ItemBlocks.ItemWoodSupport.class,"WoodSupportV");
		GameRegistry.registerBlock(WoodSupportH, com.bioxx.tfc.Items.ItemBlocks.ItemWoodSupport.class, "WoodSupportH");
		GameRegistry.registerBlock(WoodSupportV2, com.bioxx.tfc.Items.ItemBlocks.ItemWoodSupport2.class,"WoodSupportV2");
		GameRegistry.registerBlock(WoodSupportH2, com.bioxx.tfc.Items.ItemBlocks.ItemWoodSupport2.class, "WoodSupportH2");
		GameRegistry.registerBlock(Sulfur, "Sulfur");
		GameRegistry.registerBlock(LogNatural, com.bioxx.tfc.Items.ItemBlocks.ItemCustomWood.class, "log");
		GameRegistry.registerBlock(LogNatural2, com.bioxx.tfc.Items.ItemBlocks.ItemCustomWood2.class, "log2");
		GameRegistry.registerBlock(Leaves, com.bioxx.tfc.Items.ItemBlocks.ItemCustomWood.class, "leaves");
		GameRegistry.registerBlock(Leaves2, com.bioxx.tfc.Items.ItemBlocks.ItemCustomWood2.class, "leaves2");
		GameRegistry.registerBlock(Sapling, com.bioxx.tfc.Items.ItemBlocks.ItemSapling.class, "sapling");
		GameRegistry.registerBlock(Sapling2, com.bioxx.tfc.Items.ItemBlocks.ItemSapling2.class, "sapling2");
		GameRegistry.registerBlock(Planks, com.bioxx.tfc.Items.ItemBlocks.ItemCustomWood.class, "planks");
		GameRegistry.registerBlock(Planks2, com.bioxx.tfc.Items.ItemBlocks.ItemCustomWood2.class, "planks2");

		GameRegistry.registerBlock(Firepit, "Firepit");
		GameRegistry.registerBlock(Bellows, com.bioxx.tfc.Items.ItemBlocks.ItemBellows.class, "Bellows");
		GameRegistry.registerBlock(Anvil, com.bioxx.tfc.Items.ItemBlocks.ItemAnvil1.class, "Anvil");
		GameRegistry.registerBlock(Anvil2, com.bioxx.tfc.Items.ItemBlocks.ItemAnvil2.class, "Anvil2");
		GameRegistry.registerBlock(Forge, "Forge");

		GameRegistry.registerBlock(Molten, "Molten");
		GameRegistry.registerBlock(BlastFurnace, com.bioxx.tfc.Items.ItemBlocks.ItemTerraBlock.class, "Bloomery");
		GameRegistry.registerBlock(EarlyBloomery, com.bioxx.tfc.Items.ItemBlocks.ItemTerraBlock.class, "EarlyBloomery");
		GameRegistry.registerBlock(Sluice, "Sluice");
		GameRegistry.registerBlock(Bloom, "Bloom");

		GameRegistry.registerBlock(fruitTreeWood, "fruitTreeWood");
		GameRegistry.registerBlock(fruitTreeLeaves, "fruitTreeLeaves");
		GameRegistry.registerBlock(fruitTreeLeaves2, "fruitTreeLeaves2");

		GameRegistry.registerBlock(stoneStairs, "stoneStairs");
		GameRegistry.registerBlock(stoneSlabs, "stoneSlabs");
		GameRegistry.registerBlock(stoneStalac, "stoneStalac");

		GameRegistry.registerBlock(WoodConstruct, "WoodConstruct");
		GameRegistry.registerBlock(WoodVert, com.bioxx.tfc.Items.ItemBlocks.ItemCustomWood.class, "WoodVert");
		GameRegistry.registerBlock(WoodVert2, com.bioxx.tfc.Items.ItemBlocks.ItemCustomWood2.class, "WoodVert2");
		GameRegistry.registerBlock(WoodHoriz, com.bioxx.tfc.Items.ItemBlocks.ItemCustomWood.class, "WoodHoriz");
		GameRegistry.registerBlock(WoodHoriz2, com.bioxx.tfc.Items.ItemBlocks.ItemCustomWood.class, "WoodHoriz2");
		GameRegistry.registerBlock(WoodHoriz3, com.bioxx.tfc.Items.ItemBlocks.ItemCustomWood2.class, "WoodHoriz3");
		GameRegistry.registerBlock(WoodHoriz4, com.bioxx.tfc.Items.ItemBlocks.ItemCustomWood2.class, "WoodHoriz4");

		GameRegistry.registerBlock(ToolRack, ItemToolRack.class, "ToolRack");
		GameRegistry.registerBlock(ToolRack2, ItemToolRack2.class, "ToolRack2");
		GameRegistry.registerBlock(SpawnMeter, com.bioxx.tfc.Items.ItemBlocks.ItemTerraBlock.class, "SpawnMeter");
		GameRegistry.registerBlock(FoodPrep, "FoodPrep");
		GameRegistry.registerBlock(Quern, com.bioxx.tfc.Items.ItemBlocks.ItemTerraBlock.class, "Quern");
		GameRegistry.registerBlock(WallCobbleIgIn, ItemStone.class, "WallCobbleIgIn");
		GameRegistry.registerBlock(WallCobbleIgEx, ItemStone.class, "WallCobbleIgEx");
		GameRegistry.registerBlock(WallCobbleSed, ItemStone.class, "WallCobbleSed");
		GameRegistry.registerBlock(WallCobbleMM, ItemStone.class, "WallCobbleMM");
		GameRegistry.registerBlock(WallRawIgIn, ItemStone.class, "WallRawIgIn");
		GameRegistry.registerBlock(WallRawIgEx, ItemStone.class, "WallRawIgEx");
		GameRegistry.registerBlock(WallRawSed, ItemStone.class, "WallRawSed");
		GameRegistry.registerBlock(WallRawMM, ItemStone.class, "WallRawMM");
		GameRegistry.registerBlock(WallBrickIgIn, ItemStone.class, "WallBrickIgIn");
		GameRegistry.registerBlock(WallBrickIgEx, ItemStone.class, "WallBrickIgEx");
		GameRegistry.registerBlock(WallBrickSed, ItemStone.class, "WallBrickSed");
		GameRegistry.registerBlock(WallBrickMM, ItemStone.class, "WallBrickMM");
		GameRegistry.registerBlock(WallSmoothIgIn, ItemStone.class, "WallSmoothIgIn");
		GameRegistry.registerBlock(WallSmoothIgEx, ItemStone.class, "WallSmoothIgEx");
		GameRegistry.registerBlock(WallSmoothSed, ItemStone.class, "WallSmoothSed");
		GameRegistry.registerBlock(WallSmoothMM, ItemStone.class, "WallSmoothMM");

		GameRegistry.registerBlock(SaltWater, "SaltWater");
		GameRegistry.registerBlock(SaltWaterStationary, "SaltWaterStationary");
		GameRegistry.registerBlock(FreshWater, "FreshWater");
		GameRegistry.registerBlock(FreshWaterStationary, "FreshWaterStationary");
		GameRegistry.registerBlock(HotWater, "HotWater");
		GameRegistry.registerBlock(HotWaterStationary, "HotWaterStationary");
		GameRegistry.registerBlock(Lava, "Lava");
		GameRegistry.registerBlock(LavaStationary, "LavaStationary");
		GameRegistry.registerBlock(Ice, "Ice");

		GameRegistry.registerBlock(WaterPlant, "SeaGrassStill");

		GameRegistry.registerBlock(FireBrick, "FireBrick");
		GameRegistry.registerBlock(MetalSheet, "MetalSheet");

		// Wooden Doors
		for (int i=0; i < Global.WOOD_ALL.length; i++)
			GameRegistry.registerBlock(Doors[i], "Door" + Global.WOOD_ALL[i].replaceAll(" ", ""));

		GameRegistry.registerBlock(IngotPile, "IngotPile");
		GameRegistry.registerBlock(Barrel, ItemBarrels.class, "Barrel");
		GameRegistry.registerBlock(Loom, ItemLooms.class, "Loom");
		GameRegistry.registerBlock(Moss, "Moss");

		GameRegistry.registerBlock(Flora, com.bioxx.tfc.Items.ItemBlocks.ItemFlora.class,"Flora");
		GameRegistry.registerBlock(Pottery, "ClayPottery");
		GameRegistry.registerBlock(Thatch, com.bioxx.tfc.Items.ItemBlocks.ItemTerraBlock.class, "Thatch");
		GameRegistry.registerBlock(Crucible, com.bioxx.tfc.Items.ItemBlocks.ItemCrucible.class, "Crucible");
		GameRegistry.registerBlock(NestBox, com.bioxx.tfc.Items.ItemBlocks.ItemTerraBlock.class, "NestBox");
		GameRegistry.registerBlock(Fence, com.bioxx.tfc.Items.ItemBlocks.ItemFence.class, "Fence");
		GameRegistry.registerBlock(Fence2, com.bioxx.tfc.Items.ItemBlocks.ItemFence2.class, "Fence2");
		GameRegistry.registerBlock(FenceGate, com.bioxx.tfc.Items.ItemBlocks.ItemFenceGate.class, "FenceGate");
		GameRegistry.registerBlock(FenceGate2, com.bioxx.tfc.Items.ItemBlocks.ItemFenceGate2.class, "FenceGate2");
		GameRegistry.registerBlock(StrawHideBed, "StrawHideBed");
		GameRegistry.registerBlock(ArmourStand, com.bioxx.tfc.Items.ItemBlocks.ItemArmourStand.class, "ArmourStand");
		GameRegistry.registerBlock(ArmourStand2, com.bioxx.tfc.Items.ItemBlocks.ItemArmourStand2.class, "ArmourStand2");
		GameRegistry.registerBlock(BerryBush, com.bioxx.tfc.Items.ItemBlocks.ItemBerryBush.class, "BerryBush");
		GameRegistry.registerBlock(Crops, "Crops");
		GameRegistry.registerBlock(LilyPad, com.bioxx.tfc.Items.ItemBlocks.ItemCustomLilyPad.class, "LilyPad");
		GameRegistry.registerBlock(Flowers, com.bioxx.tfc.Items.ItemBlocks.ItemFlowers.class, "Flowers");
		GameRegistry.registerBlock(Flowers2, com.bioxx.tfc.Items.ItemBlocks.ItemFlowers2.class, "Flowers2");
		GameRegistry.registerBlock(Fungi, com.bioxx.tfc.Items.ItemBlocks.ItemFungi.class, "Fungi");
		GameRegistry.registerBlock(Bookshelf, com.bioxx.tfc.Items.ItemBlocks.ItemTerraBlock.class, "Bookshelf");
		GameRegistry.registerBlock(Torch, ItemTorch.class, "Torch");
		GameRegistry.registerBlock(Chest, com.bioxx.tfc.Items.ItemBlocks.ItemChest.class, "Chest TFC");
		GameRegistry.registerBlock(Workbench, com.bioxx.tfc.Items.ItemBlocks.ItemTerraBlock.class, "Workbench");
		GameRegistry.registerBlock(Cactus, com.bioxx.tfc.Items.ItemBlocks.ItemTerraBlock.class, "Cactus");
		GameRegistry.registerBlock(Reeds, "Reeds");
		GameRegistry.registerBlock(Pumpkin, com.bioxx.tfc.Items.ItemBlocks.ItemTerraBlock.class, "Pumpkin");
		GameRegistry.registerBlock(ButtonWood, "ButtonWood");
		GameRegistry.registerBlock(Vine, com.bioxx.tfc.Items.ItemBlocks.ItemVine.class, "Vine");
		GameRegistry.registerBlock(LeatherRack, "LeatherRack");
		GameRegistry.registerBlock(Gravel, ItemSoil.class,"Gravel");
		GameRegistry.registerBlock(Gravel2, ItemSoil.class,"Gravel2");

		GameRegistry.registerBlock(Grill, ItemGrill.class, "Grill");
		GameRegistry.registerBlock(MetalTrapDoor, ItemMetalTrapDoor.class, "MetalTrapDoor");
		GameRegistry.registerBlock(Vessel, ItemLargeVessel.class, "Vessel");
		GameRegistry.registerBlock(Smoke, "Smoke");
		GameRegistry.registerBlock(SmokeRack, "SmokeRack");
		GameRegistry.registerBlock(Snow, "Snow");
	}

	public static void LoadBlocks()
	{
		System.out.println(new StringBuilder().append("[TFC] Loading Blocks").toString());

		// Remove Items from Creative Tabs
		Blocks.double_wooden_slab.setCreativeTab(null);
		Blocks.wooden_slab.setCreativeTab(null);
		Blocks.spruce_stairs.setCreativeTab(null);
		Blocks.birch_stairs.setCreativeTab(null);
		Blocks.jungle_stairs.setCreativeTab(null);
		Blocks.waterlily.setCreativeTab(null);
		Blocks.tallgrass.setCreativeTab(null);
		Blocks.yellow_flower.setCreativeTab(null);
		Blocks.red_flower.setCreativeTab(null);
		Blocks.brown_mushroom.setCreativeTab(null);
		Blocks.red_mushroom.setCreativeTab(null);
		Blocks.bookshelf.setCreativeTab(null);
		Blocks.torch.setCreativeTab(null);
		Blocks.chest.setCreativeTab(null);
		Blocks.planks.setCreativeTab(null);
		Blocks.crafting_table.setCreativeTab(null);
		Blocks.cactus.setCreativeTab(null);
		Blocks.reeds.setCreativeTab(null);
		Blocks.pumpkin.setCreativeTab(null);
		Blocks.wooden_button.setCreativeTab(null);
		Blocks.ice.setCreativeTab(null);
		Blocks.vine.setCreativeTab(null);

		Bookshelf = new BlockCustomBookshelf().setHardness(1.5F).setStepSound(Block.soundTypeWood).setBlockName("Bookshelf").setBlockTextureName("bookshelf");
		Torch = new BlockTorch().setHardness(0.0F).setStepSound(Block.soundTypeWood).setBlockName("Torch").setBlockTextureName("torch_on");
		Chest = new BlockChestTFC().setHardness(2.5F).setStepSound(Block.soundTypeWood).setBlockName("Chest");
		Workbench = new BlockWorkbench().setHardness(2.5F).setStepSound(Block.soundTypeWood).setBlockName("Workbench").setBlockTextureName("crafting_table");
		Cactus = new BlockCustomCactus().setHardness(0.4F).setStepSound(Block.soundTypeCloth).setBlockName("Cactus").setBlockTextureName("cactus");
		Reeds = new BlockCustomReed().setHardness(0.0F).setStepSound(Block.soundTypeGrass).setBlockName("Reeds").setBlockTextureName("reeds");
		Pumpkin = new BlockCustomPumpkin(false).setHardness(1.0F).setStepSound(Block.soundTypeWood).setBlockName("Pumpkin").setBlockTextureName("pumpkin");
		ButtonWood = new BlockCustomButtonWood().setHardness(0.5F).setStepSound(Block.soundTypeWood).setBlockName("ButtonWood");
		Vine = new BlockCustomVine().setHardness(0.2F).setStepSound(Block.soundTypeGrass).setBlockName("Vine").setBlockTextureName("vine");

		// This is not used anywhere
		//Block.blockRegistry.addObject(Block.getIdFromBlock(Blocks.oak_stairs), "oak_stairs", (new BlockStair(Material.wood)).setBlockName("stairsWood"));

		/*Block.blockRegistry.addObject(Block.getIdFromBlock(Blocks.snow_layer), "snow_layer",
				(new BlockCustomSnow()).setHardness(0.1F).setStepSound(Block.soundTypeSnow).setBlockName("snow").setLightOpacity(1).setBlockTextureName("snow"));*/
		//Snow = (Block)Block.blockRegistry.getObject("snow_layer");
		Snow = new BlockCustomSnow().setHardness(0.1F).setStepSound(Block.soundTypeSnow).setBlockName("snow").setLightOpacity(0).setBlockTextureName("snow");
		Blocks.snow_layer = Snow;
		StoneIgInCobble = new BlockIgInCobble(Material.rock).setHardness(16F).setBlockName("IgInRockCobble");
		StoneIgIn = new BlockIgIn(Material.rock).setHardness(8F).setBlockName("IgInRock");
		StoneIgInSmooth = new BlockIgInSmooth().setHardness(40F).setBlockName("IgInRockSmooth");
		StoneIgInBrick = new BlockIgInBrick().setHardness(40F).setBlockName("IgInRockBrick");

		StoneSedCobble = new BlockSedCobble(Material.rock).setHardness(14F).setBlockName("SedRockCobble");
		StoneSed = new BlockSed(Material.rock).setHardness(7F).setBlockName("SedRock");
		StoneSedSmooth = new BlockSedSmooth().setHardness(35F).setBlockName("SedRockSmooth");
		StoneSedBrick = new BlockSedBrick().setHardness(35F).setBlockName("SedRockBrick");

		StoneIgExCobble = new BlockIgExCobble(Material.rock).setHardness(16F).setBlockName("IgExRockCobble");
		StoneIgEx = new BlockIgEx(Material.rock).setHardness(8F).setBlockName("IgExRock");
		StoneIgExSmooth = new BlockIgExSmooth().setHardness(40F).setBlockName("IgExRockSmooth");
		StoneIgExBrick = new BlockIgExBrick().setHardness(40F).setBlockName("IgExRockBrick");

		StoneMMCobble = new BlockMMCobble(Material.rock).setHardness(15F).setBlockName("MMRockCobble");
		StoneMM = new BlockMM(Material.rock).setHardness(8F).setBlockName("MMRock");
		StoneMMSmooth = new BlockMMSmooth().setHardness(35F).setBlockName("MMRockSmooth");
		StoneMMBrick = new BlockMMBrick().setHardness(35F).setBlockName("MMRockBrick");

		Dirt = (new com.bioxx.tfc.Blocks.Terrain.BlockDirt(0)).setHardness(2F).setStepSound(Block.soundTypeGravel).setBlockName("dirt");

		Dirt2 = (new com.bioxx.tfc.Blocks.Terrain.BlockDirt(16)).setHardness(2F).setStepSound(Block.soundTypeGravel).setBlockName("dirt");
		Clay = (new com.bioxx.tfc.Blocks.Terrain.BlockClay(0)).setHardness(3F).setStepSound(Block.soundTypeGravel).setBlockName("clay");
		Clay2 = (new com.bioxx.tfc.Blocks.Terrain.BlockClay(16)).setHardness(3F).setStepSound(Block.soundTypeGravel).setBlockName("clay");
		ClayGrass = new com.bioxx.tfc.Blocks.Terrain.BlockClayGrass(0).setHardness(3F).setStepSound(Block.soundTypeGrass).setBlockName("ClayGrass");
		ClayGrass2 = new com.bioxx.tfc.Blocks.Terrain.BlockClayGrass(16).setHardness(3F).setStepSound(Block.soundTypeGrass).setBlockName("ClayGrass");
		Grass = (new com.bioxx.tfc.Blocks.Terrain.BlockGrass()).setHardness(3F).setStepSound(Block.soundTypeGrass).setBlockName("Grass");
		Grass2 = new com.bioxx.tfc.Blocks.Terrain.BlockGrass(16).setHardness(3F).setStepSound(Block.soundTypeGrass).setBlockName("Grass");
		Peat = new com.bioxx.tfc.Blocks.Terrain.BlockPeat().setHardness(3F).setStepSound(Block.soundTypeGravel).setBlockName("Peat");
		PeatGrass = new com.bioxx.tfc.Blocks.Terrain.BlockPeatGrass().setHardness(3F).setStepSound(Block.soundTypeGrass).setBlockName("PeatGrass");
		DryGrass = new BlockDryGrass(0).setHardness(3F).setStepSound(Block.soundTypeGrass).setBlockName("DryGrass");
		DryGrass2 =new BlockDryGrass(16).setHardness(3F).setStepSound(Block.soundTypeGrass).setBlockName("DryGrass");
		TallGrass = new BlockCustomTallGrass().setHardness(0.0F).setStepSound(Block.soundTypeGrass).setBlockName("TallGrass");
		Sand = new BlockSand(0).setHardness(0.5F).setStepSound(Block.soundTypeSand).setBlockName("sand");
		Sand2 = new BlockSand(16).setHardness(0.5F).setStepSound(Block.soundTypeSand).setBlockName("sand");

		Ore = new BlockOre(Material.rock).setHardness(10F).setResistance(10F).setBlockName("Ore");
		Ore2 = new BlockOre2(Material.rock).setHardness(10F).setResistance(10F).setBlockName("Ore");
		Ore3 = new BlockOre3(Material.rock).setHardness(10F).setResistance(10F).setBlockName("Ore");
		worldItem = new BlockWorldItem().setHardness(0.05F).setResistance(1F).setBlockName("LooseRock");
		Sulfur = new BlockSulfur(Material.rock).setBlockName("Sulfur").setHardness(0.5F).setResistance(1F);

		LogPile = new BlockLogPile().setHardness(10F).setResistance(1F).setBlockName("LogPile");
		WoodSupportV = new BlockWoodSupport(Material.wood).setBlockName("WoodSupportV").setHardness(0.5F).setResistance(1F);
		WoodSupportH = new BlockWoodSupport(Material.wood).setBlockName("WoodSupportH").setHardness(0.5F).setResistance(1F);
		WoodSupportV2 = new BlockWoodSupport2(Material.wood).setBlockName("WoodSupportV2").setHardness(0.5F).setResistance(1F);
		WoodSupportH2 = new BlockWoodSupport2(Material.wood).setBlockName("WoodSupportH2").setHardness(0.5F).setResistance(1F);

		tilledSoil = new com.bioxx.tfc.Blocks.BlockFarmland(TFCBlocks.Dirt, 0).setHardness(2F).setStepSound(Block.soundTypeGravel).setBlockName("tilledSoil");
		tilledSoil2 = new com.bioxx.tfc.Blocks.BlockFarmland(TFCBlocks.Dirt2, 16).setHardness(2F).setStepSound(Block.soundTypeGravel).setBlockName("tilledSoil");

		fruitTreeWood = new BlockFruitWood().setBlockName("fruitTreeWood").setHardness(5.5F).setResistance(2F);
		fruitTreeLeaves = new BlockFruitLeaves(0).setBlockName("fruitTreeLeaves").setHardness(0.5F).setResistance(1F).setStepSound(Block.soundTypeGrass);
		fruitTreeLeaves2 = new BlockFruitLeaves(8).setBlockName("fruitTreeLeaves2").setHardness(0.5F).setResistance(1F).setStepSound(Block.soundTypeGrass);

		WoodConstruct = (new com.bioxx.tfc.Blocks.BlockWoodConstruct()).setHardness(4F).setStepSound(Block.soundTypeWood).setBlockName("WoodConstruct");

		Firepit = new BlockFirepit().setBlockName("Firepit").setHardness(1).setLightLevel(0F);
		Bellows = new BlockBellows(Material.wood).setBlockName("Bellows").setHardness(2);
		Forge= new BlockForge().setBlockName("Forge").setHardness(20).setLightLevel(0F);
		Anvil = new com.bioxx.tfc.Blocks.Devices.BlockAnvil().setBlockName("Anvil").setHardness(3).setResistance(100F);
		Anvil2 = new com.bioxx.tfc.Blocks.Devices.BlockAnvil(8).setBlockName("Anvil2").setHardness(3).setResistance(100F);

		Molten = new BlockMolten().setBlockName("Molten").setHardness(20);
		BlastFurnace = new BlockBlastFurnace().setBlockName("BlastFurnace").setHardness(20).setLightLevel(0F);
		EarlyBloomery = new BlockEarlyBloomery().setBlockName("EarlyBloomery").setHardness(20).setLightLevel(0F);
		Bloom = new BlockBloom().setBlockName("Bloom").setHardness(20).setLightLevel(0F);
		Sluice = new BlockSluice().setBlockName("Sluice").setHardness(2F).setResistance(20F);

		stoneStairs = new BlockStair(Material.rock).setBlockName("stoneStairs").setHardness(10);
		stoneSlabs = new BlockSlab().setBlockName("stoneSlabs").setHardness(10);
		stoneStalac = new BlockStalactite().setBlockName("stoneStalac").setHardness(5);

		Charcoal = new BlockCharcoal().setHardness(3F).setResistance(10F).setBlockName("Charcoal");

		Detailed = new BlockDetailed().setBlockName("StoneDetailed").setHardness(10);

		Planks = (new BlockPlanks(Material.wood)).setHardness(4.0F).setResistance(5.0F).setStepSound(Block.soundTypeWood).setBlockName("wood");
		Planks2 = (new com.bioxx.tfc.Blocks.BlockPlanks2(Material.wood)).setHardness(4.0F).setResistance(5.0F).setStepSound(Block.soundTypeWood).setBlockName("wood2");
		Leaves = (new BlockCustomLeaves()).setHardness(0.2F).setLightOpacity(1).setStepSound(Block.soundTypeGrass).setBlockName("leaves");
		Leaves2 = (new BlockCustomLeaves2()).setHardness(0.2F).setLightOpacity(1).setStepSound(Block.soundTypeGrass).setBlockName("leaves2");
		Sapling = (new BlockSapling()).setHardness(0.0F).setStepSound(Block.soundTypeGrass).setBlockName("sapling");
		Sapling2 = (new BlockSapling2()).setHardness(0.0F).setStepSound(Block.soundTypeGrass).setBlockName("sapling2");

		LogNatural = new com.bioxx.tfc.Blocks.Flora.BlockLogNatural().setHardness(50.0F).setStepSound(Block.soundTypeWood).setBlockName("log");
		LogNatural2 = new com.bioxx.tfc.Blocks.Flora.BlockLogNatural2().setHardness(50.0F).setStepSound(Block.soundTypeWood).setBlockName("log2");
		WoodVert = new BlockLogVert().setBlockName("WoodVert").setHardness(20).setResistance(15F).setStepSound(Block.soundTypeWood);
		WoodVert2 = new BlockLogVert2().setBlockName("WoodVert2").setHardness(20).setResistance(15F).setStepSound(Block.soundTypeWood);
		WoodHoriz = new BlockLogHoriz(0).setBlockName("WoodHoriz").setHardness(20).setResistance(15F).setStepSound(Block.soundTypeWood);
		WoodHoriz2 = new BlockLogHoriz(8).setBlockName("WoodHoriz2").setHardness(20).setResistance(15F).setStepSound(Block.soundTypeWood);
		WoodHoriz3 = new BlockLogHoriz2(0).setBlockName("WoodHoriz3").setHardness(20).setResistance(15F).setStepSound(Block.soundTypeWood);
		WoodHoriz4 = new BlockLogHoriz2(8).setBlockName("WoodHoriz4").setHardness(20).setResistance(15F).setStepSound(Block.soundTypeWood);

		ToolRack = new BlockToolRack().setHardness(3F).setBlockName("Toolrack");
		ToolRack2 = new BlockToolRack2().setHardness(3F).setBlockName("Toolrack");
		SpawnMeter = new BlockSpawnMeter().setHardness(3F).setBlockName("SpawnMeter");
		FoodPrep = new BlockFoodPrep().setHardness(1F).setBlockName("FoodPrep");
		Quern = new BlockQuern().setHardness(3F).setBlockName("Quern");

		WallCobbleIgIn = new BlockCustomWall(StoneIgInCobble, 3).setBlockName("WallCobble");
		WallCobbleIgEx = new BlockCustomWall(StoneIgExCobble, 4).setBlockName("WallCobble");
		WallCobbleSed = new BlockCustomWall(StoneSedCobble, 8).setBlockName("WallCobble");
		WallCobbleMM = new BlockCustomWall(StoneMMCobble, 6).setBlockName("WallCobble");
		WallRawIgIn = new BlockCustomWall(StoneIgIn, 3).setBlockName("WallRaw");
		WallRawIgEx = new BlockCustomWall(StoneIgEx, 4).setBlockName("WallRaw");
		WallRawSed = new BlockCustomWall(StoneSed, 8).setBlockName("WallRaw");
		WallRawMM = new BlockCustomWall(StoneMM, 6).setBlockName("WallRaw");
		WallBrickIgIn = new BlockCustomWall(StoneIgInBrick, 3).setBlockName("WallBrick");
		WallBrickIgEx = new BlockCustomWall(StoneIgExBrick, 4).setBlockName("WallBrick");
		WallBrickSed = new BlockCustomWall(StoneSedBrick, 8).setBlockName("WallBrick");
		WallBrickMM = new BlockCustomWall(StoneMMBrick, 6).setBlockName("WallBrick");
		WallSmoothIgIn = new BlockCustomWall(StoneIgInSmooth, 3).setBlockName("WallSmooth");
		WallSmoothIgEx = new BlockCustomWall(StoneIgExSmooth, 4).setBlockName("WallSmooth");
		WallSmoothSed = new BlockCustomWall(StoneSedSmooth, 8).setBlockName("WallSmooth");
		WallSmoothMM = new BlockCustomWall(StoneMMSmooth, 6).setBlockName("WallSmooth");

		// Wooden Doors
		for (int i=0; i < Global.WOOD_ALL.length; i++)
			Doors[i] = new BlockCustomDoor(i*2).setBlockName("Door "+Global.WOOD_ALL[i]);

		IngotPile = new BlockIngotPile().setBlockName("ingotpile").setHardness(3);

		Barrel = new BlockBarrel().setBlockName("Barrel").setHardness(2);
		Loom = new BlockLoom().setBlockName("Loom").setHardness(2);
		Thatch = new BlockThatch().setBlockName("Thatch").setHardness(1).setStepSound(Block.soundTypeGrass).setCreativeTab(TFCTabs.TFCBuilding);
		Moss = new BlockMoss().setBlockName("Moss").setHardness(1).setStepSound(Block.soundTypeGrass);

		Flora = new BlockFlora().setBlockName("Flora").setHardness(0.1f).setStepSound(Block.soundTypeGrass);
		Pottery = new BlockPottery().setBlockName("Pottery").setHardness(1.0f);

		Crucible = new BlockCrucible().setBlockName("Crucible").setHardness(4.0f);

		NestBox = new BlockNestBox().setBlockName("NestBox").setHardness(1);

		Fence = new BlockTFCFence("Fence", Material.wood).setBlockName("FenceTFC").setHardness(2);
		FenceGate = new BlockCustomFenceGate().setBlockName("FenceGateTFC").setHardness(2);
		Fence2 = new BlockTFCFence2("Fence2", Material.wood).setBlockName("FenceTFC").setHardness(2);
		FenceGate2 = new BlockCustomFenceGate2().setBlockName("FenceGateTFC").setHardness(2);
		StrawHideBed = new BlockBed().setBlockName("StrawHideBed").setHardness(1);
		ArmourStand = new BlockStand().setBlockName("ArmourStand").setHardness(2);
		ArmourStand2 = new BlockStand2().setBlockName("ArmourStand").setHardness(2);

		BerryBush = new BlockBerryBush().setBlockName("BerryBush").setHardness(0.3f).setStepSound(Block.soundTypeGrass);
		Crops = new BlockCrop().setBlockName("crops").setHardness(0.3F).setStepSound(Block.soundTypeGrass);
		LilyPad = new BlockCustomLilyPad().setHardness(0.0F).setStepSound(Block.soundTypeGrass).setBlockName("LilyPad").setBlockTextureName("waterlily");
		Flowers = new BlockFlower().setHardness(0.0F).setStepSound(Block.soundTypeGrass).setBlockName("Flowers");
		Flowers2 = new BlockFlower2().setHardness(0.0F).setStepSound(Block.soundTypeGrass).setBlockName("Flowers2");
		Fungi = new BlockFungi().setHardness(0.0F).setStepSound(Block.soundTypeGrass).setBlockName("Fungi");

		SaltWater = (new BlockSaltWater(TFCFluid.SALTWATER)).setHardness(100.0F).setLightOpacity(3).setBlockName("SaltWater");
		SaltWaterStationary = (new BlockLiquidStatic(TFCFluid.SALTWATER, Material.water, SaltWater)).setHardness(100.0F).setLightOpacity(3).setBlockName("FreshWaterStationary");
		FreshWater = (new BlockFreshWater(TFCFluid.FRESHWATER)).setHardness(100.0F).setLightOpacity(3).setBlockName("FreshWater");
		FreshWaterStationary = (new BlockLiquidStatic(TFCFluid.FRESHWATER, Material.water, FreshWater)).setHardness(100.0F).setLightOpacity(3).setBlockName("FreshWaterStationary");
		HotWater = (new BlockHotWater(TFCFluid.HOTWATER)).setHardness(100.0F).setLightOpacity(3).setBlockName("HotWater");
		HotWaterStationary = (new BlockLiquidStatic(TFCFluid.HOTWATER, Material.water, HotWater)).setHardness(100.0F).setLightOpacity(3).setBlockName("HotWaterStationary");
		Lava = (new BlockLava()).setHardness(0.0F).setLightLevel(1.0F).setLightOpacity(255).setBlockName("Lava");
		LavaStationary = (new BlockLiquidStatic(TFCFluid.LAVA, Material.lava, Lava)).setHardness(0.0F).setLightLevel(1.0F).setLightOpacity(255).setBlockName("Lava");
		Ice = new BlockCustomIce().setHardness(0.5F).setLightOpacity(3).setStepSound(Block.soundTypeGlass).setBlockName("Ice").setBlockTextureName("ice");

		WaterPlant = new BlockWaterPlant(0).setBlockName("SeaGrassStill").setHardness(0.5f).setStepSound(Block.soundTypeGravel);

		FireBrick = new BlockFireBrick().setBlockName("FireBrick").setHardness(8);
		MetalSheet = new BlockMetalSheet().setBlockName("MetalSheet").setHardness(80);
		LeatherRack = new BlockLeatherRack().setBlockName("LeatherRack").setHardness(1);

		Gravel = (new BlockGravel(0)).setHardness(2F).setStepSound(Block.soundTypeGravel).setBlockName("gravel");
		Gravel2 = (new BlockGravel(16)).setHardness(2F).setStepSound(Block.soundTypeGravel).setBlockName("gravel");

		Grill = new BlockGrill().setHardness(2F).setBlockName("Grill");
		MetalTrapDoor = new BlockMetalTrapDoor().setHardness(2F).setBlockName("MetalTrapDoor");
		Vessel = new BlockLargeVessel().setHardness(1F).setBlockName("Vessel");
		Smoke = new BlockSmoke().setHardness(0F).setBlockName("Smoke");
		SmokeRack = new BlockSmokeRack().setHardness(0F).setBlockName("SmokeRack");

		StoneIgIn.setHarvestLevel("pickaxe", 0);
		StoneIgEx.setHarvestLevel("pickaxe", 0);
		StoneSed.setHarvestLevel("pickaxe", 0);
		StoneMM.setHarvestLevel("pickaxe", 0);
		stoneStalac.setHarvestLevel("pickaxe", 0);
		Detailed.setHarvestLevel("pickaxe", 0);
		Ore.setHarvestLevel("pickaxe", 1);
		Ore2.setHarvestLevel("pickaxe", 1);
		Ore3.setHarvestLevel("pickaxe", 1);

		Dirt.setHarvestLevel("shovel", 0);
		Dirt2.setHarvestLevel("shovel", 0);
		Grass.setHarvestLevel("shovel", 0);
		Grass2.setHarvestLevel("shovel", 0);
		DryGrass.setHarvestLevel("shovel", 0);
		DryGrass2.setHarvestLevel("shovel", 0);
		Clay.setHarvestLevel("shovel", 0);
		Clay2.setHarvestLevel("shovel", 0);
		ClayGrass.setHarvestLevel("shovel", 0);
		ClayGrass2.setHarvestLevel("shovel", 0);
		Peat.setHarvestLevel("shovel", 0);
		PeatGrass.setHarvestLevel("shovel", 0);
		Sand.setHarvestLevel("shovel", 0);
		Sand2.setHarvestLevel("shovel", 0);
		Charcoal.setHarvestLevel("shovel", 0);
		Gravel.setHarvestLevel("shovel", 0);
		Gravel2.setHarvestLevel("shovel", 0);
		WaterPlant.setHarvestLevel("shovel", 0);
		tilledSoil.setHarvestLevel("shovel", 0);
		tilledSoil2.setHarvestLevel("shovel", 0);

		Detailed.setHarvestLevel("axe", 0);
		Blocks.oak_stairs.setHarvestLevel("axe", 0);
		WoodConstruct.setHarvestLevel("axe", 0);
		LogNatural.setHarvestLevel("axe", 1);
		LogNatural2.setHarvestLevel("axe", 1);
		WoodHoriz.setHarvestLevel("axe", 1);
		WoodHoriz2.setHarvestLevel("axe", 1);
		WoodHoriz3.setHarvestLevel("axe", 1);
		WoodHoriz4.setHarvestLevel("axe", 1);
		WoodVert.setHarvestLevel("axe", 1);
		WoodVert2.setHarvestLevel("axe", 1);

		LogNatural.setHarvestLevel("hammer", 1);
		LogNatural2.setHarvestLevel("hammer", 1);
		WoodHoriz.setHarvestLevel("hammer", 1);
		WoodHoriz2.setHarvestLevel("hammer", 1);
		WoodHoriz3.setHarvestLevel("hammer", 1);
		WoodHoriz4.setHarvestLevel("hammer", 1);
		WoodVert.setHarvestLevel("hammer", 1);
		WoodVert2.setHarvestLevel("hammer", 1);
	}

	public static void setupFire()
	{
		Blocks.fire.setFireInfo(LogNatural, 5, 20);
		Blocks.fire.setFireInfo(LogNatural2, 5, 20);
		Blocks.fire.setFireInfo(WoodSupportV, 5, 20);
		Blocks.fire.setFireInfo(WoodSupportV2, 5, 20);
		Blocks.fire.setFireInfo(WoodSupportH, 5, 20);
		Blocks.fire.setFireInfo(WoodSupportH2, 5, 20);
		Blocks.fire.setFireInfo(Leaves, 5, 20);
		Blocks.fire.setFireInfo(Leaves2, 5, 20);
		Blocks.fire.setFireInfo(fruitTreeWood, 5, 20);
		Blocks.fire.setFireInfo(fruitTreeLeaves, 5, 20);
		Blocks.fire.setFireInfo(fruitTreeLeaves2, 5, 20);
		Blocks.fire.setFireInfo(Fence, 5, 20);
		Blocks.fire.setFireInfo(Fence2, 5, 20);
		Blocks.fire.setFireInfo(FenceGate, 5, 20);
		Blocks.fire.setFireInfo(FenceGate2, 5, 20);
		Blocks.fire.setFireInfo(Chest, 5, 20);
		Blocks.fire.setFireInfo(StrawHideBed, 5, 20);
		Blocks.fire.setFireInfo(Thatch, 5, 20);
		Blocks.fire.setFireInfo(WoodVert, 5, 20);
		Blocks.fire.setFireInfo(WoodVert2, 5, 20);
		Blocks.fire.setFireInfo(WoodHoriz, 5, 20);
		Blocks.fire.setFireInfo(WoodHoriz2, 5, 20);
		Blocks.fire.setFireInfo(WoodHoriz3, 5, 20);
		Blocks.fire.setFireInfo(WoodHoriz4, 5, 20);
		Blocks.fire.setFireInfo(Planks, 5, 20);
		Blocks.fire.setFireInfo(Planks2, 5, 20);
		Blocks.fire.setFireInfo(WoodConstruct, 5, 20);
		Blocks.fire.setFireInfo(BerryBush, 5, 20);
		Blocks.fire.setFireInfo(Barrel, 5, 20);
		Blocks.fire.setFireInfo(Crops, 20, 20);
		Blocks.fire.setFireInfo(LogPile, 20, 20);
		//Blocks.fire.setFireInfo(Charcoal, 100, 20);
		for (int i=0; i < Global.WOOD_ALL.length; i++)
			Blocks.fire.setFireInfo(Doors[i], 5, 20);
	}

	public static boolean isBlockVSupport(Block block)
	{
		return block == WoodSupportV || block == WoodSupportV2;
	}

	public static boolean isBlockHSupport(Block block)
	{
		return block == WoodSupportH || block == WoodSupportH2;
	}

	public static boolean isBlockAFence(Block block)
	{
		return block == Fence || block == Fence2 || BlockFence.func_149825_a(block);
	}

	public static boolean canFenceConnectTo(Block block)
	{
		return isBlockAFence(block) || block == FenceGate || block == FenceGate2;
	}

	public static boolean isArmourStand(Block block)
	{
		return block == ArmourStand || block == ArmourStand2;
	}
}