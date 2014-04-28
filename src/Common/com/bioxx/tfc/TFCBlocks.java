package com.bioxx.tfc;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
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
import com.bioxx.tfc.Blocks.BlockMolten;
import com.bioxx.tfc.Blocks.BlockPipeBasic;
import com.bioxx.tfc.Blocks.BlockPipeValve;
import com.bioxx.tfc.Blocks.BlockPlanks;
import com.bioxx.tfc.Blocks.BlockSlab;
import com.bioxx.tfc.Blocks.BlockStair;
import com.bioxx.tfc.Blocks.BlockStalactite;
import com.bioxx.tfc.Blocks.BlockSulfur;
import com.bioxx.tfc.Blocks.BlockThatch;
import com.bioxx.tfc.Blocks.BlockTuyere;
import com.bioxx.tfc.Blocks.BlockWoodSupport;
import com.bioxx.tfc.Blocks.BlockWoodSupport2;
import com.bioxx.tfc.Blocks.BlockWorldItem;
import com.bioxx.tfc.Blocks.Devices.BlockBarrel;
import com.bioxx.tfc.Blocks.Devices.BlockBarrel2;
import com.bioxx.tfc.Blocks.Devices.BlockBellows;
import com.bioxx.tfc.Blocks.Devices.BlockBlastFurnace;
import com.bioxx.tfc.Blocks.Devices.BlockChestTFC;
import com.bioxx.tfc.Blocks.Devices.BlockCrucible;
import com.bioxx.tfc.Blocks.Devices.BlockEarlyBloomery;
import com.bioxx.tfc.Blocks.Devices.BlockFirepit;
import com.bioxx.tfc.Blocks.Devices.BlockForge;
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
import com.bioxx.tfc.Blocks.Flora.BlockFrozenSeaGrass;
import com.bioxx.tfc.Blocks.Flora.BlockFruitLeaves;
import com.bioxx.tfc.Blocks.Flora.BlockFruitWood;
import com.bioxx.tfc.Blocks.Flora.BlockLogHoriz;
import com.bioxx.tfc.Blocks.Flora.BlockLogHoriz2;
import com.bioxx.tfc.Blocks.Flora.BlockLogVert;
import com.bioxx.tfc.Blocks.Flora.BlockLogVert2;
import com.bioxx.tfc.Blocks.Flora.BlockTallSeaGrassStill;
import com.bioxx.tfc.Blocks.Terrain.BlockDryGrass;
import com.bioxx.tfc.Blocks.Terrain.BlockFlowers;
import com.bioxx.tfc.Blocks.Terrain.BlockFreshWater;
import com.bioxx.tfc.Blocks.Terrain.BlockFungi;
import com.bioxx.tfc.Blocks.Terrain.BlockHotWater;
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
import com.bioxx.tfc.Blocks.Terrain.BlockSand2;
import com.bioxx.tfc.Blocks.Terrain.BlockSed;
import com.bioxx.tfc.Blocks.Terrain.BlockSedBrick;
import com.bioxx.tfc.Blocks.Terrain.BlockSedCobble;
import com.bioxx.tfc.Blocks.Terrain.BlockSedSmooth;
import com.bioxx.tfc.Blocks.Terrain.TFC_Fluids;
import com.bioxx.tfc.Blocks.Vanilla.BlockCustomBed;
import com.bioxx.tfc.Blocks.Vanilla.BlockCustomBookshelf;
import com.bioxx.tfc.Blocks.Vanilla.BlockCustomButtonWood;
import com.bioxx.tfc.Blocks.Vanilla.BlockCustomCactus;
import com.bioxx.tfc.Blocks.Vanilla.BlockCustomDoor;
import com.bioxx.tfc.Blocks.Vanilla.BlockCustomFence;
import com.bioxx.tfc.Blocks.Vanilla.BlockCustomFence2;
import com.bioxx.tfc.Blocks.Vanilla.BlockCustomFenceGate;
import com.bioxx.tfc.Blocks.Vanilla.BlockCustomFenceGate2;
import com.bioxx.tfc.Blocks.Vanilla.BlockCustomIce;
import com.bioxx.tfc.Blocks.Vanilla.BlockCustomLeaves;
import com.bioxx.tfc.Blocks.Vanilla.BlockCustomLeaves2;
import com.bioxx.tfc.Blocks.Vanilla.BlockCustomLilyPad;
import com.bioxx.tfc.Blocks.Vanilla.BlockCustomLiquid;
import com.bioxx.tfc.Blocks.Vanilla.BlockCustomPumpkin;
import com.bioxx.tfc.Blocks.Vanilla.BlockCustomReed;
import com.bioxx.tfc.Blocks.Vanilla.BlockCustomSapling;
import com.bioxx.tfc.Blocks.Vanilla.BlockCustomSapling2;
import com.bioxx.tfc.Blocks.Vanilla.BlockCustomSnow;
import com.bioxx.tfc.Blocks.Vanilla.BlockCustomTallGrass;
import com.bioxx.tfc.Blocks.Vanilla.BlockCustomVine;
import com.bioxx.tfc.Blocks.Vanilla.BlockCustomWall;
import com.bioxx.tfc.Blocks.Vanilla.BlockTorch;
import com.bioxx.tfc.Items.ItemBarrels;
import com.bioxx.tfc.Items.ItemBarrels2;
import com.bioxx.tfc.Items.ItemBlocks.ItemTerraBlock;
import com.bioxx.tfc.Items.ItemBlocks.ItemToolRack;
import com.bioxx.tfc.Items.ItemBlocks.ItemToolRack2;
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
	public static int seaWeedRenderId;
	public static int bloomeryRenderId;
	public static int metalsheetRenderId;

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
	public static Block Barrel2;
	public static Block Pottery;
	public static Block Thatch;
	public static Block Moss;
	public static Block BerryBush;
	public static Block Crops;
	public static Block LilyPad;
	public static Block Flowers;
	public static Block Fungi;
	public static Block Flora;
	public static Block Tuyere;
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

	public static Block SteamPipe;
	public static Block SteamPipeValve;

	public static Block SaltWater;
	public static Block FreshWater;
	public static Block HotWater;
	public static Block Lava;

	public static Block SeaGrassStill;
	public static Block SeaGrassFrozen;

	public static Block Bookshelf;
	public static Block Torch;
	public static Block Chest;
	public static Block Workbench;
	public static Block Cactus;
	public static Block Reeds;
	public static Block Pumpkin;
	public static Block ButtonWood;

	public static void RegisterBlocks()
	{
		GameRegistry.registerBlock(Ore, com.bioxx.tfc.Items.ItemOre1.class, "Ore1");
		GameRegistry.registerBlock(Ore2, com.bioxx.tfc.Items.ItemOre2.class, "Ore2");
		GameRegistry.registerBlock(Ore3, com.bioxx.tfc.Items.ItemOre3.class, "Ore3");
		GameRegistry.registerBlock(StoneIgIn, com.bioxx.tfc.Items.ItemBlocks.ItemIgIn.class, "StoneIgIn");
		GameRegistry.registerBlock(StoneIgEx, com.bioxx.tfc.Items.ItemBlocks.ItemIgEx.class, "StoneIgEx");
		GameRegistry.registerBlock(StoneSed, com.bioxx.tfc.Items.ItemBlocks.ItemSed.class, "StoneSed");
		GameRegistry.registerBlock(StoneMM, com.bioxx.tfc.Items.ItemBlocks.ItemMM.class, "StoneMM");

		GameRegistry.registerBlock(StoneIgInCobble, com.bioxx.tfc.Items.ItemBlocks.ItemIgIn.class, "StoneIgInCobble");
		GameRegistry.registerBlock(StoneIgExCobble, com.bioxx.tfc.Items.ItemBlocks.ItemIgEx.class, "StoneIgExCobble");
		GameRegistry.registerBlock(StoneSedCobble, com.bioxx.tfc.Items.ItemBlocks.ItemSed.class, "StoneSedCobble");
		GameRegistry.registerBlock(StoneMMCobble, com.bioxx.tfc.Items.ItemBlocks.ItemMM.class, "StoneMMCobble");
		GameRegistry.registerBlock(StoneIgInSmooth, com.bioxx.tfc.Items.ItemBlocks.ItemIgIn.class, "StoneIgInSmooth");
		GameRegistry.registerBlock(StoneIgExSmooth, com.bioxx.tfc.Items.ItemBlocks.ItemIgEx.class, "StoneIgExSmooth");
		GameRegistry.registerBlock(StoneSedSmooth, com.bioxx.tfc.Items.ItemBlocks.ItemSed.class, "StoneSedSmooth");
		GameRegistry.registerBlock(StoneMMSmooth, com.bioxx.tfc.Items.ItemBlocks.ItemMM.class, "StoneMMSmooth");
		GameRegistry.registerBlock(StoneIgInBrick, com.bioxx.tfc.Items.ItemBlocks.ItemIgIn.class, "StoneIgInBrick");
		GameRegistry.registerBlock(StoneIgExBrick, com.bioxx.tfc.Items.ItemBlocks.ItemIgEx.class, "StoneIgExBrick");
		GameRegistry.registerBlock(StoneSedBrick, com.bioxx.tfc.Items.ItemBlocks.ItemSed.class, "StoneSedBrick");
		GameRegistry.registerBlock(StoneMMBrick, com.bioxx.tfc.Items.ItemBlocks.ItemMM.class, "StoneMMBrick");

		GameRegistry.registerBlock(Dirt, com.bioxx.tfc.Items.ItemBlocks.ItemDirt.class, "Dirt");
		GameRegistry.registerBlock(Dirt2, com.bioxx.tfc.Items.ItemBlocks.ItemDirt.class, "Dirt2");
		GameRegistry.registerBlock(Sand, com.bioxx.tfc.Items.ItemBlocks.ItemSand.class, "Sand");
		GameRegistry.registerBlock(Sand2, com.bioxx.tfc.Items.ItemBlocks.ItemSand.class, "Sand2");
		GameRegistry.registerBlock(Clay, "Clay");
		GameRegistry.registerBlock(Clay2, "Clay2");
		GameRegistry.registerBlock(Grass, ItemTerraBlock.class, "Grass");
		GameRegistry.registerBlock(Grass2, com.bioxx.tfc.Items.ItemBlocks.ItemTerraBlock.class, "Grass2");
		GameRegistry.registerBlock(ClayGrass, com.bioxx.tfc.Items.ItemBlocks.ItemTerraBlock.class, "ClayGrass");
		GameRegistry.registerBlock(ClayGrass2, com.bioxx.tfc.Items.ItemBlocks.ItemTerraBlock.class, "ClayGrass2");
		GameRegistry.registerBlock(PeatGrass, com.bioxx.tfc.Items.ItemBlocks.ItemTerraBlock.class, "PeatGrass");
		GameRegistry.registerBlock(Peat, com.bioxx.tfc.Items.ItemBlocks.ItemTerraBlock.class, "Peat");
		GameRegistry.registerBlock(DryGrass, com.bioxx.tfc.Items.ItemBlocks.ItemTerraBlock.class, "DryGrass");
		GameRegistry.registerBlock(DryGrass2, com.bioxx.tfc.Items.ItemBlocks.ItemTerraBlock.class, "DryGrass2");
		GameRegistry.registerBlock(TallGrass, com.bioxx.tfc.Items.ItemBlocks.ItemCustomTallGrass.class, "TallGrass");
		GameRegistry.registerBlock(worldItem, "LooseRock");
		GameRegistry.registerBlock(LogPile, "LogPile");
		GameRegistry.registerBlock(Charcoal, "Charcoal");
		GameRegistry.registerBlock(Detailed, "Detailed");

		GameRegistry.registerBlock(tilledSoil, "tilledSoil");
		GameRegistry.registerBlock(tilledSoil2, "tilledSoil2");

		GameRegistry.registerBlock(WoodSupportV, com.bioxx.tfc.Items.ItemBlocks.ItemWoodSupport.class,"WoodSupportV");
		GameRegistry.registerBlock(WoodSupportH, com.bioxx.tfc.Items.ItemBlocks.ItemWoodSupport.class, "WoodSupportH");
		GameRegistry.registerBlock(WoodSupportV2, com.bioxx.tfc.Items.ItemBlocks.ItemWoodSupport2.class,"WoodSupportV2");
		GameRegistry.registerBlock(WoodSupportH2, com.bioxx.tfc.Items.ItemBlocks.ItemWoodSupport2.class, "WoodSupportH2");
		GameRegistry.registerBlock(Sulfur, "Sulfur");
		GameRegistry.registerBlock(LogNatural, com.bioxx.tfc.Items.ItemBlocks.ItemCustomWood.class, "log");
		GameRegistry.registerBlock(LogNatural2, com.bioxx.tfc.Items.ItemBlocks.ItemCustomWood2.class, "log2");
		GameRegistry.registerBlock(Leaves, com.bioxx.tfc.Items.ItemBlocks.ItemCustomLeaves.class, "leaves");
		GameRegistry.registerBlock(Leaves2, com.bioxx.tfc.Items.ItemBlocks.ItemCustomLeaves2.class, "leaves2");
		GameRegistry.registerBlock(Sapling, com.bioxx.tfc.Items.ItemBlocks.ItemSapling.class, "sapling");
		GameRegistry.registerBlock(Sapling2, com.bioxx.tfc.Items.ItemBlocks.ItemSapling2.class, "sapling2");
		GameRegistry.registerBlock(Planks, com.bioxx.tfc.Items.ItemBlocks.ItemPlankBlock.class, "planks");
		GameRegistry.registerBlock(Planks2, com.bioxx.tfc.Items.ItemBlocks.ItemPlankBlock2.class, "planks2");

		GameRegistry.registerBlock(Firepit, "Firepit");
		GameRegistry.registerBlock(Bellows, com.bioxx.tfc.Items.ItemBlocks.ItemBellows.class, "Bellows");
		GameRegistry.registerBlock(Anvil, com.bioxx.tfc.Items.ItemBlocks.ItemAnvil1.class, "Anvil");
		GameRegistry.registerBlock(Anvil2, com.bioxx.tfc.Items.ItemBlocks.ItemAnvil2.class, "Anvil2");
		GameRegistry.registerBlock(Forge, "Forge");

		GameRegistry.registerBlock(Molten, "Molten");
		GameRegistry.registerBlock(BlastFurnace, com.bioxx.tfc.Items.ItemBlocks.ItemTerraBlock.class, "Bloomery");
		GameRegistry.registerBlock(EarlyBloomery, com.bioxx.tfc.Items.ItemBlocks.ItemTerraBlock.class, "EarlyBloomery");
		GameRegistry.registerBlock(Sluice, "Sluice");

		GameRegistry.registerBlock(fruitTreeWood, "fruitTreeWood");
		GameRegistry.registerBlock(fruitTreeLeaves, "fruitTreeLeaves");
		GameRegistry.registerBlock(fruitTreeLeaves2, "fruitTreeLeaves2");

		GameRegistry.registerBlock(stoneStairs, "stoneStairs");
		GameRegistry.registerBlock(stoneSlabs, "stoneSlabs");
		GameRegistry.registerBlock(stoneStalac, "stoneStalac");

		GameRegistry.registerBlock(WoodConstruct, "WoodConstruct");
		GameRegistry.registerBlock(WoodVert, "WoodVert");
		GameRegistry.registerBlock(WoodVert2, "WoodVert2");
		GameRegistry.registerBlock(WoodHoriz, "WoodHoriz");
		GameRegistry.registerBlock(WoodHoriz2, "WoodHoriz2");
		GameRegistry.registerBlock(WoodHoriz3, "WoodHoriz3");
		GameRegistry.registerBlock(WoodHoriz4, "WoodHoriz4");

		GameRegistry.registerBlock(ToolRack, ItemToolRack.class, "ToolRack");
		GameRegistry.registerBlock(ToolRack2, ItemToolRack2.class, "ToolRack2");
		GameRegistry.registerBlock(SpawnMeter, com.bioxx.tfc.Items.ItemBlocks.ItemTerraBlock.class, "SpawnMeter");
		GameRegistry.registerBlock(FoodPrep, "FoodPrep");
		GameRegistry.registerBlock(Quern, com.bioxx.tfc.Items.ItemBlocks.ItemTerraBlock.class, "Quern");
		GameRegistry.registerBlock(WallCobbleIgIn, com.bioxx.tfc.Items.ItemBlocks.ItemIgIn.class, "WallCobbleIgIn");
		GameRegistry.registerBlock(WallCobbleIgEx, com.bioxx.tfc.Items.ItemBlocks.ItemIgEx.class, "WallCobbleIgEx");
		GameRegistry.registerBlock(WallCobbleSed, com.bioxx.tfc.Items.ItemBlocks.ItemSed.class, "WallCobbleSed");
		GameRegistry.registerBlock(WallCobbleMM, com.bioxx.tfc.Items.ItemBlocks.ItemMM.class, "WallCobbleMM");
		GameRegistry.registerBlock(WallRawIgIn, com.bioxx.tfc.Items.ItemBlocks.ItemIgIn.class, "WallRawIgIn");
		GameRegistry.registerBlock(WallRawIgEx, com.bioxx.tfc.Items.ItemBlocks.ItemIgEx.class, "WallRawIgEx");
		GameRegistry.registerBlock(WallRawSed, com.bioxx.tfc.Items.ItemBlocks.ItemSed.class, "WallRawSed");
		GameRegistry.registerBlock(WallRawMM, com.bioxx.tfc.Items.ItemBlocks.ItemMM.class, "WallRawMM");
		GameRegistry.registerBlock(WallBrickIgIn, com.bioxx.tfc.Items.ItemBlocks.ItemIgIn.class, "WallBrickIgIn");
		GameRegistry.registerBlock(WallBrickIgEx, com.bioxx.tfc.Items.ItemBlocks.ItemIgEx.class, "WallBrickIgEx");
		GameRegistry.registerBlock(WallBrickSed, com.bioxx.tfc.Items.ItemBlocks.ItemSed.class, "WallBrickSed");
		GameRegistry.registerBlock(WallBrickMM, com.bioxx.tfc.Items.ItemBlocks.ItemMM.class, "WallBrickMM");
		GameRegistry.registerBlock(WallSmoothIgIn, com.bioxx.tfc.Items.ItemBlocks.ItemIgIn.class, "WallSmoothIgIn");
		GameRegistry.registerBlock(WallSmoothIgEx, com.bioxx.tfc.Items.ItemBlocks.ItemIgEx.class, "WallSmoothIgEx");
		GameRegistry.registerBlock(WallSmoothSed, com.bioxx.tfc.Items.ItemBlocks.ItemSed.class, "WallSmoothSed");
		GameRegistry.registerBlock(WallSmoothMM, com.bioxx.tfc.Items.ItemBlocks.ItemMM.class, "WallSmoothMM");
		GameRegistry.registerBlock(SteamPipe, com.bioxx.tfc.Items.ItemBlocks.ItemPipe.class, "BasicPipe");
		GameRegistry.registerBlock(SteamPipeValve, com.bioxx.tfc.Items.ItemBlocks.ItemPipeValve.class, "ValvePipe");

		GameRegistry.registerBlock(SaltWater, "SaltWater");
		GameRegistry.registerBlock(FreshWater, "FreshWater");
		GameRegistry.registerBlock(HotWater, "HotWater");

		GameRegistry.registerBlock(Lava, "Lava");

		GameRegistry.registerBlock(SeaGrassStill, "SeaGrassStill");
		GameRegistry.registerBlock(SeaGrassFrozen, "SeaGrassFrozen");

		GameRegistry.registerBlock(FireBrick, "FireBrick");
		GameRegistry.registerBlock(MetalSheet, "MetalSheet");

		// Wooden Doors
		for (int i=0; i < Global.WOOD_ALL.length; i++)
			GameRegistry.registerBlock(Doors[i], "Door" + Global.WOOD_ALL[i].replaceAll(" ", ""));

		GameRegistry.registerBlock(IngotPile, "IngotPile");
		GameRegistry.registerBlock(Barrel, ItemBarrels.class, "Barrel");
		GameRegistry.registerBlock(Barrel2, ItemBarrels2.class, "Barrel2");
		GameRegistry.registerBlock(Moss, "Moss");

		GameRegistry.registerBlock(Flora, com.bioxx.tfc.Items.ItemBlocks.ItemFlora.class,"Flora");
		GameRegistry.registerBlock(Pottery, "ClayPottery");
		GameRegistry.registerBlock(Thatch, com.bioxx.tfc.Items.ItemBlocks.ItemTerraBlock.class, "Thatch");
		GameRegistry.registerBlock(Tuyere, "Tuyere");
		GameRegistry.registerBlock(Crucible, com.bioxx.tfc.Items.ItemBlocks.ItemCrucible.class, "Crucible");
		GameRegistry.registerBlock(NestBox, com.bioxx.tfc.Items.ItemBlocks.ItemTerraBlock.class, "NestBox");
		GameRegistry.registerBlock(Fence, com.bioxx.tfc.Items.ItemBlocks.ItemFence.class, "Fence");
		GameRegistry.registerBlock(Fence2, com.bioxx.tfc.Items.ItemBlocks.ItemFence2.class, "Fence2");
		//		GameRegistry.registerBlock(Blocks.fence_gate, TFC.Items.ItemBlocks.ItemFenceGate.class, "FenceGate");
		GameRegistry.registerBlock(FenceGate2, com.bioxx.tfc.Items.ItemBlocks.ItemFenceGate2.class, "FenceGate2");
		GameRegistry.registerBlock(StrawHideBed, "StrawHideBed");
		GameRegistry.registerBlock(ArmourStand, com.bioxx.tfc.Items.ItemBlocks.ItemArmourStand.class, "ArmourStand");
		GameRegistry.registerBlock(ArmourStand2, com.bioxx.tfc.Items.ItemBlocks.ItemArmourStand2.class, "ArmourStand2");
		GameRegistry.registerBlock(BerryBush, com.bioxx.tfc.Items.ItemBlocks.ItemBerryBush.class, "BerryBush");
		GameRegistry.registerBlock(LilyPad, com.bioxx.tfc.Items.ItemBlocks.ItemCustomLilyPad.class, "LilyPad");
		GameRegistry.registerBlock(Flowers, com.bioxx.tfc.Items.ItemBlocks.ItemFlowers.class, "Flowers");
		GameRegistry.registerBlock(Fungi, com.bioxx.tfc.Items.ItemBlocks.ItemFungi.class, "Fungi");
		GameRegistry.registerBlock(Bookshelf, com.bioxx.tfc.Items.ItemBlocks.ItemTerraBlock.class, "Bookshelf");
		GameRegistry.registerBlock(Torch, com.bioxx.tfc.Items.ItemBlocks.ItemTerraBlock.class, "Torch");
		GameRegistry.registerBlock(Chest, com.bioxx.tfc.Items.ItemBlocks.ItemTerraBlock.class, "Chest");
		GameRegistry.registerBlock(Workbench, com.bioxx.tfc.Items.ItemBlocks.ItemTerraBlock.class, "Workbench");
		GameRegistry.registerBlock(Cactus, com.bioxx.tfc.Items.ItemBlocks.ItemTerraBlock.class, "Cactus");
		GameRegistry.registerBlock(Reeds, "Reeds");
		GameRegistry.registerBlock(Pumpkin, com.bioxx.tfc.Items.ItemBlocks.ItemTerraBlock.class, "Pumpkin");
		GameRegistry.registerBlock(ButtonWood, "ButtonWood");
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


		Bookshelf = new BlockCustomBookshelf().setHardness(1.5F).setStepSound(Block.soundTypeWood).setBlockName("Bookshelf").setBlockTextureName("bookshelf");
		Torch = new BlockTorch().setHardness(0.0F).setLightLevel(0.9375F).setStepSound(Block.soundTypeWood).setBlockName("Torch").setBlockTextureName("torch_on");
		Chest = new BlockChestTFC(0).setHardness(2.5F).setStepSound(Block.soundTypeWood).setBlockName("Chest");
		Workbench = new BlockWorkbench().setHardness(2.5F).setStepSound(Block.soundTypeWood).setBlockName("Workbench").setBlockTextureName("crafting_table");
		Cactus = new BlockCustomCactus().setHardness(0.4F).setStepSound(Block.soundTypeCloth).setBlockName("Cactus").setBlockTextureName("cactus");
		Reeds = new BlockCustomReed().setHardness(0.0F).setStepSound(Block.soundTypeGrass).setBlockName("Reeds").setBlockTextureName("reeds");
		Pumpkin = new BlockCustomPumpkin(false).setHardness(1.0F).setStepSound(Block.soundTypeWood).setBlockName("Pumpkin").setBlockTextureName("pumpkin");
		ButtonWood = new BlockCustomButtonWood().setHardness(0.5F).setStepSound(Block.soundTypeWood).setBlockName("ButtonWood");

		// This is not used anywhere
		//Block.blockRegistry.addObject(Block.getIdFromBlock(Blocks.oak_stairs), "oak_stairs", (new BlockStair(Material.wood)).setBlockName("stairsWood"));

		Block.blockRegistry.addObject(Block.getIdFromBlock(Blocks.snow_layer), "snow_layer",
				(new BlockCustomSnow()).setHardness(0.1F).setStepSound(Block.soundTypeSnow).setBlockName("snow").setLightOpacity(1).setBlockTextureName("snow"));
		Block.blockRegistry.addObject(Block.getIdFromBlock(Blocks.ice), "ice",
				(new BlockCustomIce()).setHardness(0.5F).setLightOpacity(3).setStepSound(Block.soundTypeGlass).setBlockName("ice").setBlockTextureName("ice"));
		Block.blockRegistry.addObject(Block.getIdFromBlock(Blocks.vine), "vine",
				(new BlockCustomVine()).setHardness(0.2F).setStepSound(Block.soundTypeGrass).setBlockName("vine").setBlockTextureName("vine"));
		Block.blockRegistry.addObject(Block.getIdFromBlock(Blocks.fence_gate), "FenceGateTFC",
				(new BlockCustomFenceGate()).setBlockName("FenceGateTFC").setHardness(2));


		StoneIgInCobble = new BlockIgInCobble(Material.rock).setHardness(13F).setResistance(10F).setBlockName("IgInRockCobble");
		StoneIgIn = new BlockIgIn(Material.rock).setHardness(13F).setResistance(10F).setBlockName("IgInRock");
		StoneIgInSmooth = new BlockIgInSmooth().setHardness(13F).setResistance(20F).setBlockName("IgInRockSmooth");
		StoneIgInBrick = new BlockIgInBrick().setHardness(13F).setResistance(15F).setBlockName("IgInRockBrick");

		StoneSedCobble = new BlockSedCobble(Material.rock).setHardness(10F).setResistance(10F).setBlockName("SedRockCobble");
		StoneSed = new BlockSed(Material.rock).setHardness(10F).setResistance(7F).setBlockName("SedRock");
		StoneSedSmooth = new BlockSedSmooth().setHardness(10F).setResistance(20F).setBlockName("SedRockSmooth");
		StoneSedBrick = new BlockSedBrick().setHardness(10F).setResistance(15F).setBlockName("SedRockBrick");

		StoneIgExCobble = new BlockIgExCobble(Material.rock).setHardness(13F).setResistance(10F).setBlockName("IgExRockCobble");
		StoneIgEx = new BlockIgEx(Material.rock).setHardness(13F).setResistance(10F).setBlockName("IgExRock");
		StoneIgExSmooth = new BlockIgExSmooth().setHardness(13F).setResistance(20F).setBlockName("IgExRockSmooth");
		StoneIgExBrick = new BlockIgExBrick().setHardness(13F).setResistance(15F).setBlockName("IgExRockBrick");

		StoneMMCobble = new BlockMMCobble(Material.rock).setHardness(10F).setResistance(10F).setBlockName("MMRockCobble");
		StoneMM = new BlockMM(Material.rock).setHardness(10F).setResistance(8F).setBlockName("MMRock");
		StoneMMSmooth = new BlockMMSmooth().setHardness(10F).setResistance(20F).setBlockName("MMRockSmooth");
		StoneMMBrick = new BlockMMBrick().setHardness(10F).setResistance(15F).setBlockName("MMRockBrick");

		Dirt = (new com.bioxx.tfc.Blocks.Terrain.BlockDirt(0, tilledSoil)).setHardness(2F).setStepSound(Block.soundTypeGravel).setBlockName("dirt");
		Dirt2 = (new com.bioxx.tfc.Blocks.Terrain.BlockDirt2(16, tilledSoil2)).setHardness(2F).setStepSound(Block.soundTypeGravel).setBlockName("dirt");
		Clay = (new com.bioxx.tfc.Blocks.Terrain.BlockClay()).setHardness(3F).setStepSound(Block.soundTypeGravel).setBlockName("clay");
		Clay2 = (new com.bioxx.tfc.Blocks.Terrain.BlockClay2()).setHardness(3F).setStepSound(Block.soundTypeGravel).setBlockName("clay");
		ClayGrass = new com.bioxx.tfc.Blocks.Terrain.BlockClayGrass(0).setHardness(3F).setStepSound(Block.soundTypeGrass).setBlockName("ClayGrass");
		ClayGrass2 = new com.bioxx.tfc.Blocks.Terrain.BlockClayGrass(16).setHardness(3F).setStepSound(Block.soundTypeGrass).setBlockName("ClayGrass");
		Grass = (new com.bioxx.tfc.Blocks.Terrain.BlockGrass()).setHardness(3F).setStepSound(Block.soundTypeGrass).setBlockName("Grass");
		Grass2 = new com.bioxx.tfc.Blocks.Terrain.BlockGrass(16).setHardness(3F).setStepSound(Block.soundTypeGrass).setBlockName("Grass");
		Peat = new com.bioxx.tfc.Blocks.Terrain.BlockPeat().setHardness(3F).setStepSound(Block.soundTypeGravel).setBlockName("Peat");
		PeatGrass = new com.bioxx.tfc.Blocks.Terrain.BlockPeatGrass().setHardness(3F).setStepSound(Block.soundTypeGrass).setBlockName("PeatGrass");
		DryGrass = new BlockDryGrass(0).setHardness(3F).setStepSound(Block.soundTypeGrass).setBlockName("DryGrass");
		DryGrass2 =new BlockDryGrass(16).setHardness(3F).setStepSound(Block.soundTypeGrass).setBlockName("DryGrass");
		TallGrass = new BlockCustomTallGrass().setHardness(0.0F).setStepSound(Block.soundTypeGrass).setBlockName("TallGrass");
		Sand = new com.bioxx.tfc.Blocks.Terrain.BlockSand(0).setHardness(0.5F).setStepSound(Block.soundTypeSand).setBlockName("sand");
		Sand2 = new BlockSand2(16).setHardness(0.5F).setStepSound(Block.soundTypeSand).setBlockName("sand");

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
		tilledSoil2 = new com.bioxx.tfc.Blocks.BlockFarmland(TFCBlocks.Dirt2, 16).setHardness(2F).setStepSound(Block.soundTypeGravel).setBlockName("tilledSoil2");

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

		stoneStairs = new BlockStair(Material.rock).setBlockName("stoneStairs").setHardness(10).setResistance(15F);
		stoneSlabs = new BlockSlab().setBlockName("stoneSlabs").setHardness(10).setResistance(15F);
		stoneStalac = new BlockStalactite().setBlockName("stoneStalac").setHardness(5);

		Charcoal = new BlockCharcoal().setHardness(3F).setResistance(10F).setBlockName("Charcoal");

		Detailed = new BlockDetailed().setBlockName("StoneDetailed").setHardness(10).setResistance(15F);

		Planks = (new BlockPlanks(Material.wood)).setHardness(4.0F).setResistance(5.0F).setStepSound(Block.soundTypeWood).setBlockName("wood");
		Planks2 = (new com.bioxx.tfc.Blocks.BlockPlanks2(Material.wood)).setHardness(4.0F).setResistance(5.0F).setStepSound(Block.soundTypeWood).setBlockName("wood2");
		Leaves = (new BlockCustomLeaves()).setHardness(0.2F).setLightOpacity(1).setStepSound(Block.soundTypeGrass).setBlockName("leaves");
		Leaves2 = (new BlockCustomLeaves2()).setHardness(0.2F).setLightOpacity(1).setStepSound(Block.soundTypeGrass).setBlockName("leaves2");
		Sapling = (new BlockCustomSapling()).setHardness(0.0F).setStepSound(Block.soundTypeGrass).setBlockName("sapling");
		Sapling2 = (new BlockCustomSapling2()).setHardness(0.0F).setStepSound(Block.soundTypeGrass).setBlockName("sapling2");

		LogNatural = new com.bioxx.tfc.Blocks.Flora.BlockLogNatural().setHardness(50.0F).setStepSound(Block.soundTypeWood).setBlockName("log");
		LogNatural2 = new com.bioxx.tfc.Blocks.Flora.BlockLogNatural2().setHardness(50.0F).setStepSound(Block.soundTypeWood).setBlockName("log2");
		WoodVert = new BlockLogVert().setBlockName("WoodVert").setHardness(40).setResistance(15F);
		WoodVert2 = new BlockLogVert2().setBlockName("WoodVert2").setHardness(40).setResistance(15F);
		WoodHoriz = new BlockLogHoriz(0).setBlockName("WoodHoriz").setHardness(40).setResistance(15F);
		WoodHoriz2 = new BlockLogHoriz(8).setBlockName("WoodHoriz2").setHardness(40).setResistance(15F);
		WoodHoriz3 = new BlockLogHoriz2(0).setBlockName("WoodHoriz3").setHardness(40).setResistance(15F);
		WoodHoriz4 = new BlockLogHoriz2(8).setBlockName("WoodHoriz4").setHardness(40).setResistance(15F);

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
		Barrel2 = new BlockBarrel2().setBlockName("Barrel").setHardness(2);
		Thatch = new BlockThatch().setBlockName("Thatch").setHardness(1).setStepSound(Block.soundTypeGrass).setCreativeTab(CreativeTabs.tabBlock);
		Moss = new BlockMoss().setBlockName("Moss").setHardness(1).setStepSound(Block.soundTypeGrass);

		Flora = new BlockFlora().setBlockName("Flora").setHardness(0.1f).setStepSound(Block.soundTypeGrass);
		Pottery = new BlockPottery().setBlockName("Pottery").setHardness(1.0f);

		Tuyere = new BlockTuyere().setBlockName("Tuyere");
		Crucible = new BlockCrucible().setBlockName("Crucible").setHardness(4.0f);

		NestBox = new BlockNestBox().setBlockName("NestBox").setHardness(1);

		Fence = new BlockCustomFence("Fence",Material.wood).setBlockName("FenceTFC").setHardness(2);
		FenceGate = Blocks.fence_gate;//new BlockCustomFenceGate().setBlockName("FenceGateTFC").setHardness(2);
		Fence2 = new BlockCustomFence2("Fence2",Material.wood).setBlockName("FenceTFC").setHardness(2);
		FenceGate2 = new BlockCustomFenceGate2().setBlockName("FenceGateTFC").setHardness(2);
		StrawHideBed = new BlockCustomBed().setBlockName("StrawHideBed").setHardness(1);
		ArmourStand = new BlockStand().setBlockName("ArmourStand").setHardness(2);
		ArmourStand2 = new BlockStand2().setBlockName("ArmourStand").setHardness(2);

		SteamPipe = new BlockPipeBasic(Material.iron).setBlockName("BasicPipe").setHardness(2);
		SteamPipeValve = new BlockPipeValve(Material.iron).setBlockName("ValvePipe").setHardness(2);

		BerryBush = new BlockBerryBush().setBlockName("BerryBush").setHardness(0.3f).setStepSound(Block.soundTypeGrass);
		Crops = new BlockCrop().setBlockName("crops").setHardness(0.3F).setStepSound(Block.soundTypeGrass);
		LilyPad = new BlockCustomLilyPad().setHardness(0.0F).setStepSound(Block.soundTypeGrass).setBlockName("LilyPad").setBlockTextureName("waterlily");
		Flowers = new BlockFlowers().setHardness(0.0F).setStepSound(Block.soundTypeGrass).setBlockName("Flowers");
		Fungi = new BlockFungi().setHardness(0.0F).setStepSound(Block.soundTypeGrass).setBlockName("Fungi");//.setCreativeTab(CreativeTabs.tabFood);

		SaltWater = (new BlockCustomLiquid(TFC_Fluids.SALTWATER, Material.water)).setHardness(100.0F).setLightOpacity(3).setBlockName("SaltWater");
		FreshWater = (new BlockFreshWater(TFC_Fluids.FRESHWATER)).setHardness(100.0F).setLightOpacity(3).setBlockName("FreshWater");
		HotWater = (new BlockHotWater(TFC_Fluids.HOTWATER)).setHardness(100.0F).setLightOpacity(3).setBlockName("HotWater");
		Lava = (new BlockCustomLiquid(TFC_Fluids.LAVA, Material.lava)).setHardness(0.0F).setLightLevel(1.0F).setLightOpacity(255).setBlockName("Lava");

		SeaGrassStill = new BlockTallSeaGrassStill().setBlockName("SeaGrassStill").setHardness(0.3f).setCreativeTab(CreativeTabs.tabDecorations);
		SeaGrassFrozen = (new BlockFrozenSeaGrass()).setHardness(0.5F).setLightOpacity(3).setStepSound(Block.soundTypeGlass).setBlockName("SeaGrassIce");

		FireBrick = new BlockFireBrick().setBlockName("FireBrick").setHardness(8);
		MetalSheet = new BlockMetalSheet().setBlockName("MetalSheet").setHardness(8).setResistance(20f);

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