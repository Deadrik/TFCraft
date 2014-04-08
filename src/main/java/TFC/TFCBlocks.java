package TFC;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import TFC.API.Constant.Global;
import TFC.Blocks.BlockBloom;
import TFC.Blocks.BlockCharcoal;
import TFC.Blocks.BlockCrop;
import TFC.Blocks.BlockDetailed;
import TFC.Blocks.BlockFireBrick;
import TFC.Blocks.BlockFoodPrep;
import TFC.Blocks.BlockIngotPile;
import TFC.Blocks.BlockLogPile;
import TFC.Blocks.BlockLooseRock;
import TFC.Blocks.BlockMetalSheet;
import TFC.Blocks.BlockMolten;
import TFC.Blocks.BlockPipeBasic;
import TFC.Blocks.BlockPipeValve;
import TFC.Blocks.BlockPlanks;
import TFC.Blocks.BlockSlab;
import TFC.Blocks.BlockStair;
import TFC.Blocks.BlockStalactite;
import TFC.Blocks.BlockSulfur;
import TFC.Blocks.BlockThatch;
import TFC.Blocks.BlockTuyere;
import TFC.Blocks.BlockWoodSupport;
import TFC.Blocks.BlockWoodSupport2;
import TFC.Blocks.Devices.BlockBarrel;
import TFC.Blocks.Devices.BlockBarrel2;
import TFC.Blocks.Devices.BlockBellows;
import TFC.Blocks.Devices.BlockBlastFurnace;
import TFC.Blocks.Devices.BlockChestTFC;
import TFC.Blocks.Devices.BlockCrucible;
import TFC.Blocks.Devices.BlockEarlyBloomery;
import TFC.Blocks.Devices.BlockFirepit;
import TFC.Blocks.Devices.BlockForge;
import TFC.Blocks.Devices.BlockNestBox;
import TFC.Blocks.Devices.BlockPottery;
import TFC.Blocks.Devices.BlockQuern;
import TFC.Blocks.Devices.BlockSluice;
import TFC.Blocks.Devices.BlockSpawnMeter;
import TFC.Blocks.Devices.BlockStand;
import TFC.Blocks.Devices.BlockStand2;
import TFC.Blocks.Devices.BlockToolRack;
import TFC.Blocks.Devices.BlockToolRack2;
import TFC.Blocks.Devices.BlockWorkbench;
import TFC.Blocks.Flora.BlockBerryBush;
import TFC.Blocks.Flora.BlockFlora;
import TFC.Blocks.Flora.BlockFrozenSeaGrass;
import TFC.Blocks.Flora.BlockFruitLeaves;
import TFC.Blocks.Flora.BlockFruitWood;
import TFC.Blocks.Flora.BlockLogHoriz;
import TFC.Blocks.Flora.BlockLogHoriz2;
import TFC.Blocks.Flora.BlockLogVert;
import TFC.Blocks.Flora.BlockLogVert2;
import TFC.Blocks.Flora.BlockTallSeaGrassFlowing;
import TFC.Blocks.Flora.BlockTallSeaGrassStill;
import TFC.Blocks.Terrain.BlockDryGrass;
import TFC.Blocks.Terrain.BlockFreshWaterFlowing;
import TFC.Blocks.Terrain.BlockFreshWaterStill;
import TFC.Blocks.Terrain.BlockHotWaterFlowing;
import TFC.Blocks.Terrain.BlockHotWaterStill;
import TFC.Blocks.Terrain.BlockIgEx;
import TFC.Blocks.Terrain.BlockIgExBrick;
import TFC.Blocks.Terrain.BlockIgExCobble;
import TFC.Blocks.Terrain.BlockIgExSmooth;
import TFC.Blocks.Terrain.BlockIgIn;
import TFC.Blocks.Terrain.BlockIgInBrick;
import TFC.Blocks.Terrain.BlockIgInCobble;
import TFC.Blocks.Terrain.BlockIgInSmooth;
import TFC.Blocks.Terrain.BlockMM;
import TFC.Blocks.Terrain.BlockMMBrick;
import TFC.Blocks.Terrain.BlockMMCobble;
import TFC.Blocks.Terrain.BlockMMSmooth;
import TFC.Blocks.Terrain.BlockMoss;
import TFC.Blocks.Terrain.BlockOre;
import TFC.Blocks.Terrain.BlockOre2;
import TFC.Blocks.Terrain.BlockOre3;
import TFC.Blocks.Terrain.BlockSand2;
import TFC.Blocks.Terrain.BlockSed;
import TFC.Blocks.Terrain.BlockSedBrick;
import TFC.Blocks.Terrain.BlockSedCobble;
import TFC.Blocks.Terrain.BlockSedSmooth;
import TFC.Blocks.Vanilla.BlockCustomBed;
import TFC.Blocks.Vanilla.BlockCustomBookshelf;
import TFC.Blocks.Vanilla.BlockCustomButtonWood;
import TFC.Blocks.Vanilla.BlockCustomCactus;
import TFC.Blocks.Vanilla.BlockCustomDoor;
import TFC.Blocks.Vanilla.BlockCustomFence;
import TFC.Blocks.Vanilla.BlockCustomFence2;
import TFC.Blocks.Vanilla.BlockCustomFenceGate;
import TFC.Blocks.Vanilla.BlockCustomFenceGate2;
import TFC.Blocks.Vanilla.BlockCustomFlower;
import TFC.Blocks.Vanilla.BlockCustomFlowing;
import TFC.Blocks.Vanilla.BlockCustomIce;
import TFC.Blocks.Vanilla.BlockCustomLeaves;
import TFC.Blocks.Vanilla.BlockCustomLeaves2;
import TFC.Blocks.Vanilla.BlockCustomLilyPad;
import TFC.Blocks.Vanilla.BlockCustomMushroom;
import TFC.Blocks.Vanilla.BlockCustomPumpkin;
import TFC.Blocks.Vanilla.BlockCustomReed;
import TFC.Blocks.Vanilla.BlockCustomSapling;
import TFC.Blocks.Vanilla.BlockCustomSapling2;
import TFC.Blocks.Vanilla.BlockCustomSnow;
import TFC.Blocks.Vanilla.BlockCustomStationary;
import TFC.Blocks.Vanilla.BlockCustomTallGrass;
import TFC.Blocks.Vanilla.BlockCustomVine;
import TFC.Blocks.Vanilla.BlockCustomWall;
import TFC.Blocks.Vanilla.BlockTorch;
import TFC.Items.ItemBarrels;
import TFC.Items.ItemBarrels2;
import TFC.Items.ItemBlocks.ItemCustomWood2;
import TFC.Items.ItemBlocks.ItemToolRack;
import TFC.Items.ItemBlocks.ItemToolRack2;
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
	public static int finiteWaterRenderId;
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
	public static Block Wood;
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
	public static Block LooseRock;
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

	public static Block LogNatural2;
	public static Block WoodHoriz3;
	public static Block WoodHoriz4;
	public static Block WoodVert2;

	public static Block SteamPipe;
	public static Block SteamPipeValve;

	public static Block FreshWaterStill;
	public static Block FreshWaterFlowing;
	public static Block HotWaterStill;
	public static Block HotWaterFlowing;
	
	public static Block SeaGrassStill;
	public static Block SeaGrassFrozen;
	public static Block SeaGrassFlowing;

	public static void RegisterBlocks()
	{
		GameRegistry.registerBlock(Ore, TFC.Items.ItemOre1.class, "Ore1");
		GameRegistry.registerBlock(Ore2, TFC.Items.ItemOre2.class, "Ore2");
		GameRegistry.registerBlock(Ore3, TFC.Items.ItemOre3.class, "Ore3");
		GameRegistry.registerBlock(StoneIgIn, TFC.Items.ItemBlocks.ItemIgIn.class, "StoneIgIn");
		GameRegistry.registerBlock(StoneIgEx, TFC.Items.ItemBlocks.ItemIgEx.class, "StoneIgEx");
		GameRegistry.registerBlock(StoneSed, TFC.Items.ItemBlocks.ItemSed.class, "StoneSed");
		GameRegistry.registerBlock(StoneMM, TFC.Items.ItemBlocks.ItemMM.class, "StoneMM");

		GameRegistry.registerBlock(StoneIgInCobble, TFC.Items.ItemBlocks.ItemIgIn.class, "StoneIgInCobble");
		GameRegistry.registerBlock(StoneIgExCobble, TFC.Items.ItemBlocks.ItemIgEx.class, "StoneIgExCobble");
		GameRegistry.registerBlock(StoneSedCobble, TFC.Items.ItemBlocks.ItemSed.class, "StoneSedCobble");
		GameRegistry.registerBlock(StoneMMCobble, TFC.Items.ItemBlocks.ItemMM.class, "StoneMMCobble");
		GameRegistry.registerBlock(StoneIgInSmooth, TFC.Items.ItemBlocks.ItemIgIn.class, "StoneIgInSmooth");
		GameRegistry.registerBlock(StoneIgExSmooth, TFC.Items.ItemBlocks.ItemIgEx.class, "StoneIgExSmooth");
		GameRegistry.registerBlock(StoneSedSmooth, TFC.Items.ItemBlocks.ItemSed.class, "StoneSedSmooth");
		GameRegistry.registerBlock(StoneMMSmooth, TFC.Items.ItemBlocks.ItemMM.class, "StoneMMSmooth");
		GameRegistry.registerBlock(StoneIgInBrick, TFC.Items.ItemBlocks.ItemIgIn.class, "StoneIgInBrick");
		GameRegistry.registerBlock(StoneIgExBrick, TFC.Items.ItemBlocks.ItemIgEx.class, "StoneIgExBrick");
		GameRegistry.registerBlock(StoneSedBrick, TFC.Items.ItemBlocks.ItemSed.class, "StoneSedBrick");
		GameRegistry.registerBlock(StoneMMBrick, TFC.Items.ItemBlocks.ItemMM.class, "StoneMMBrick");

		GameRegistry.registerBlock(Dirt, TFC.Items.ItemBlocks.ItemDirt.class, "Dirt");
		GameRegistry.registerBlock(Dirt2, TFC.Items.ItemBlocks.ItemDirt.class, "Dirt2");
		GameRegistry.registerBlock(Sand, TFC.Items.ItemBlocks.ItemSand.class, "Sand");
		GameRegistry.registerBlock(Sand2, TFC.Items.ItemBlocks.ItemSand.class, "Sand2");
		GameRegistry.registerBlock(Clay, "Clay");
		GameRegistry.registerBlock(Clay2, "Clay2");
		GameRegistry.registerBlock(Grass, TFC.Items.ItemBlocks.ItemTerraBlock.class, "Grass");
		GameRegistry.registerBlock(Grass2, TFC.Items.ItemBlocks.ItemTerraBlock.class, "Grass2");
		GameRegistry.registerBlock(ClayGrass, TFC.Items.ItemBlocks.ItemTerraBlock.class, "ClayGrass");
		GameRegistry.registerBlock(ClayGrass2, TFC.Items.ItemBlocks.ItemTerraBlock.class, "ClayGrass2");
		GameRegistry.registerBlock(PeatGrass, TFC.Items.ItemBlocks.ItemTerraBlock.class, "PeatGrass");
		GameRegistry.registerBlock(Peat, TFC.Items.ItemBlocks.ItemTerraBlock.class, "Peat");
		GameRegistry.registerBlock(DryGrass, TFC.Items.ItemBlocks.ItemTerraBlock.class, "DryGrass");
		GameRegistry.registerBlock(DryGrass2, TFC.Items.ItemBlocks.ItemTerraBlock.class, "DryGrass2");
		GameRegistry.registerBlock(LooseRock, "LooseRock");
		GameRegistry.registerBlock(LogPile, "LogPile");
		GameRegistry.registerBlock(Charcoal, "Charcoal");
		GameRegistry.registerBlock(Detailed, "Detailed");

		GameRegistry.registerBlock(tilledSoil, "tilledSoil");
		GameRegistry.registerBlock(tilledSoil2, "tilledSoil2");

		GameRegistry.registerBlock(WoodSupportV, TFC.Items.ItemBlocks.ItemWoodSupport.class,"WoodSupportV");
		GameRegistry.registerBlock(WoodSupportH, TFC.Items.ItemBlocks.ItemWoodSupport.class, "WoodSupportH");
		GameRegistry.registerBlock(WoodSupportV2, TFC.Items.ItemBlocks.ItemWoodSupport2.class,"WoodSupportV2");
		GameRegistry.registerBlock(WoodSupportH2, TFC.Items.ItemBlocks.ItemWoodSupport2.class, "WoodSupportH2");
		GameRegistry.registerBlock(Sulfur, "Sulfur");
//		GameRegistry.registerBlock(Blocks.log, TFC.Items.ItemBlocks.ItemCustomWood.class, "log");
//		GameRegistry.registerBlock(Blocks.leaves, TFC.Items.ItemBlocks.ItemCustomLeaves.class, "leaves");
//		GameRegistry.registerBlock(Blocks.sapling, TFC.Items.ItemBlocks.ItemSapling.class, "sapling");
		GameRegistry.registerBlock(Leaves2, TFC.Items.ItemBlocks.ItemCustomLeaves2.class, "leaves2");
		GameRegistry.registerBlock(Sapling2, TFC.Items.ItemBlocks.ItemSapling2.class, "sapling2");
//		GameRegistry.registerBlock(Blocks.planks, TFC.Items.ItemBlocks.ItemPlankBlock.class, "planks");
		GameRegistry.registerBlock(Planks2, TFC.Items.ItemBlocks.ItemPlankBlock2.class, "planks2");
//		GameRegistry.registerBlock(Blocks.brown_mushroom, TFC.Items.ItemBlocks.ItemFoodBlock.class, "mushroom");
//		GameRegistry.registerBlock(Blocks.pumpkin, TFC.Items.ItemBlocks.ItemFoodBlock.class, "pumpkin");
//		GameRegistry.registerBlock(Blocks.melon_block, TFC.Items.ItemBlocks.ItemFoodBlock.class, "melon");

		GameRegistry.registerBlock(Firepit, "Firepit");
		GameRegistry.registerBlock(Bellows, TFC.Items.ItemBlocks.ItemBellows.class, "Bellows");
		GameRegistry.registerBlock(Anvil, TFC.Items.ItemBlocks.ItemAnvil1.class, "Anvil");
		GameRegistry.registerBlock(Anvil2, TFC.Items.ItemBlocks.ItemAnvil2.class, "Anvil2");
		GameRegistry.registerBlock(Forge, "Forge");

		GameRegistry.registerBlock(Molten, "Molten");
		GameRegistry.registerBlock(BlastFurnace, TFC.Items.ItemBlocks.ItemTerraBlock.class, "Bloomery");
		GameRegistry.registerBlock(EarlyBloomery, TFC.Items.ItemBlocks.ItemTerraBlock.class, "EarlyBloomery");
		GameRegistry.registerBlock(Sluice, "Sluice");

		GameRegistry.registerBlock(fruitTreeWood, "fruitTreeWood");
		GameRegistry.registerBlock(fruitTreeLeaves, "fruitTreeLeaves");
		GameRegistry.registerBlock(fruitTreeLeaves2, "fruitTreeLeaves2");

		GameRegistry.registerBlock(stoneStairs, "stoneStairs");
		GameRegistry.registerBlock(stoneSlabs, "stoneSlabs");
		GameRegistry.registerBlock(stoneStalac, "stoneStalac");

		GameRegistry.registerBlock(WoodConstruct, "WoodConstruct");
		GameRegistry.registerBlock(WoodVert, "WoodVert");
		GameRegistry.registerBlock(WoodHoriz, "WoodHoriz");
		GameRegistry.registerBlock(WoodHoriz2, "WoodHoriz2");

		GameRegistry.registerBlock(LogNatural2,ItemCustomWood2.class,"LogNatural2");
		GameRegistry.registerBlock(WoodVert2, "WoodVert2");
		GameRegistry.registerBlock(WoodHoriz3, "WoodHoriz3");
		GameRegistry.registerBlock(WoodHoriz4, "WoodHoriz4");

		GameRegistry.registerBlock(ToolRack, ItemToolRack.class,"ToolRack");
		GameRegistry.registerBlock(ToolRack2, ItemToolRack2.class,"ToolRack2");
		GameRegistry.registerBlock(SpawnMeter, TFC.Items.ItemBlocks.ItemTerraBlock.class, "SpawnMeter");
		GameRegistry.registerBlock(FoodPrep, "FoodPrep");
		GameRegistry.registerBlock(Quern, TFC.Items.ItemBlocks.ItemTerraBlock.class, "Quern");
		GameRegistry.registerBlock(WallCobbleIgIn, TFC.Items.ItemBlocks.ItemIgIn.class, "WallCobbleIgIn");
		GameRegistry.registerBlock(WallCobbleIgEx, TFC.Items.ItemBlocks.ItemIgEx.class, "WallCobbleIgEx");
		GameRegistry.registerBlock(WallCobbleSed, TFC.Items.ItemBlocks.ItemSed.class, "WallCobbleSed");
		GameRegistry.registerBlock(WallCobbleMM, TFC.Items.ItemBlocks.ItemMM.class, "WallCobbleMM");
		GameRegistry.registerBlock(WallRawIgIn, TFC.Items.ItemBlocks.ItemIgIn.class, "WallRawIgIn");
		GameRegistry.registerBlock(WallRawIgEx, TFC.Items.ItemBlocks.ItemIgEx.class, "WallRawIgEx");
		GameRegistry.registerBlock(WallRawSed, TFC.Items.ItemBlocks.ItemSed.class, "WallRawSed");
		GameRegistry.registerBlock(WallRawMM, TFC.Items.ItemBlocks.ItemMM.class, "WallRawMM");
		GameRegistry.registerBlock(WallBrickIgIn, TFC.Items.ItemBlocks.ItemIgIn.class, "WallBrickIgIn");
		GameRegistry.registerBlock(WallBrickIgEx, TFC.Items.ItemBlocks.ItemIgEx.class, "WallBrickIgEx");
		GameRegistry.registerBlock(WallBrickSed, TFC.Items.ItemBlocks.ItemSed.class, "WallBrickSed");
		GameRegistry.registerBlock(WallBrickMM, TFC.Items.ItemBlocks.ItemMM.class, "WallBrickMM");
		GameRegistry.registerBlock(WallSmoothIgIn, TFC.Items.ItemBlocks.ItemIgIn.class, "WallSmoothIgIn");
		GameRegistry.registerBlock(WallSmoothIgEx, TFC.Items.ItemBlocks.ItemIgEx.class, "WallSmoothIgEx");
		GameRegistry.registerBlock(WallSmoothSed, TFC.Items.ItemBlocks.ItemSed.class, "WallSmoothSed");
		GameRegistry.registerBlock(WallSmoothMM, TFC.Items.ItemBlocks.ItemMM.class, "WallSmoothMM");
		GameRegistry.registerBlock(SteamPipe, TFC.Items.ItemBlocks.ItemPipe.class, "BasicPipe");
		GameRegistry.registerBlock(SteamPipeValve, TFC.Items.ItemBlocks.ItemPipeValve.class, "ValvePipe");

		GameRegistry.registerBlock(FreshWaterStill,"FreshWaterStill");
		GameRegistry.registerBlock(FreshWaterFlowing,"FreshWaterFlowing");
		GameRegistry.registerBlock(HotWaterStill,"HotWaterStill");
		GameRegistry.registerBlock(HotWaterFlowing,"HotWaterFlowing");
		
		GameRegistry.registerBlock(SeaGrassStill,"SeaGrassStill");
		GameRegistry.registerBlock(SeaGrassFrozen,"SeaGrassFrozen");
		GameRegistry.registerBlock(SeaGrassFlowing,"SeaGrassFlowing");

		GameRegistry.registerBlock(FireBrick, "FireBrick");
		GameRegistry.registerBlock(MetalSheet, "MetalSheet");

		// Wooden Doors
		for (int i=0; i < Global.WOOD_ALL.length; i++)
			GameRegistry.registerBlock(Doors[i], "Door"+Global.WOOD_ALL[i].replaceAll(" ", ""));

		GameRegistry.registerBlock(IngotPile, "IngotPile");
		GameRegistry.registerBlock(Barrel, ItemBarrels.class,"Barrel");
		GameRegistry.registerBlock(Barrel2, ItemBarrels2.class,"Barrel2");
		GameRegistry.registerBlock(Moss, "Moss");

		GameRegistry.registerBlock(Flora, TFC.Items.ItemBlocks.ItemFlora.class,"Flora");
		GameRegistry.registerBlock(Pottery, "ClayPottery");
		GameRegistry.registerBlock(Thatch, TFC.Items.ItemBlocks.ItemTerraBlock.class, "Thatch");
		GameRegistry.registerBlock(Tuyere, "Tuyere");
		GameRegistry.registerBlock(Crucible, TFC.Items.ItemBlocks.ItemCrucible.class, "Crucible");
		GameRegistry.registerBlock(NestBox, TFC.Items.ItemBlocks.ItemTerraBlock.class, "NestBox");
		GameRegistry.registerBlock(Fence,TFC.Items.ItemBlocks.ItemFence.class,"Fence");
		GameRegistry.registerBlock(Fence2,TFC.Items.ItemBlocks.ItemFence2.class,"Fence2");
//		GameRegistry.registerBlock(Blocks.fence_gate,TFC.Items.ItemBlocks.ItemFenceGate.class,"FenceGate");
		GameRegistry.registerBlock(FenceGate2,TFC.Items.ItemBlocks.ItemFenceGate2.class,"FenceGate2");
		GameRegistry.registerBlock(StrawHideBed,"StrawHideBed");
		GameRegistry.registerBlock(ArmourStand,TFC.Items.ItemBlocks.ItemArmourStand.class,"ArmourStand");
		GameRegistry.registerBlock(ArmourStand2,TFC.Items.ItemBlocks.ItemArmourStand2.class,"ArmourStand2");
		GameRegistry.registerBlock(BerryBush,TFC.Items.ItemBlocks.ItemBerryBush.class,"BerryBush");
		//FluidRegistry.registerFluid(new Fluid("freshWater").setBlockID(FreshWaterStill.blockID).setBlockName(FreshWaterStill.getUnlocalizedName()));
		//FluidRegistry.registerFluid(new Fluid("hotWater").setBlockID(HotWaterStill.blockID).setBlockName(HotWaterStill.getUnlocalizedName()));
	}

	public static void LoadBlocks()
	{
		System.out.println(new StringBuilder().append("[TFC] Loading Blocks").toString());

		Blocks.double_wooden_slab.setCreativeTab(null);
		Blocks.wooden_slab.setCreativeTab(null);
		Blocks.spruce_stairs.setCreativeTab(null);
		Blocks.birch_stairs.setCreativeTab(null);
		Blocks.jungle_stairs.setCreativeTab(null);

		Block.blockRegistry.addObject(Block.getIdFromBlock(Blocks.planks), "planks",
				(new BlockPlanks(Material.wood)).setHardness(4.0F).setResistance(5.0F).setStepSound(Block.soundTypeWood).setBlockName("wood").setBlockTextureName("planks"));
		Block.blockRegistry.addObject(Block.getIdFromBlock(Blocks.sapling), "sapling",
				(new BlockCustomSapling()).setHardness(0.0F).setStepSound(Block.soundTypeGrass).setBlockName("sapling"));
		Block.blockRegistry.addObject(Block.getIdFromBlock(Blocks.flowing_water), "flowing_water",
				(new BlockCustomFlowing(Material.water)).setHardness(100.0F).setLightOpacity(3).setBlockName("water_flow"));
		Block.blockRegistry.addObject(Block.getIdFromBlock(Blocks.water), "water",
				(new BlockCustomStationary(Material.water)).setHardness(100.0F).setLightOpacity(3).setBlockName("water_still"));
		Block.blockRegistry.addObject(Block.getIdFromBlock(Blocks.flowing_lava), "flowing_lava",
				(new BlockCustomFlowing(Material.lava)).setHardness(0.0F).setLightLevel(1.0F).setLightOpacity(255).setBlockName("lava_flow"));
		Block.blockRegistry.addObject(Block.getIdFromBlock(Blocks.flowing_water), "log",
				(new TFC.Blocks.Flora.BlockLogNatural()).setHardness(50.0F).setStepSound(Block.soundTypeWood).setBlockName("log"));
		Block.blockRegistry.addObject(Block.getIdFromBlock(Blocks.leaves), "leaves",
				(new BlockCustomLeaves()).setHardness(0.2F).setLightOpacity(1).setStepSound(Block.soundTypeGrass).setBlockName("leaves"));
		Block.blockRegistry.addObject(Block.getIdFromBlock(Blocks.tallgrass), "tallgrass",
				(new BlockCustomTallGrass()).setHardness(0.0F).setStepSound(Block.soundTypeGrass).setBlockName("tallgrass"));
		Block.blockRegistry.addObject(Block.getIdFromBlock(Blocks.yellow_flower), "yellow_flower",
				(new BlockCustomFlower(0)).setHardness(0.0F).setStepSound(Block.soundTypeGrass).setBlockName("flower1").setBlockTextureName("flower_dandelion"));
		Block.blockRegistry.addObject(Block.getIdFromBlock(Blocks.red_flower), "red_flower",
				(new BlockCustomFlower(1)).setHardness(0.0F).setStepSound(Block.soundTypeGrass).setBlockName("flower2").setBlockTextureName("flower_rose"));
		Block.blockRegistry.addObject(Block.getIdFromBlock(Blocks.brown_mushroom), "brown_mushroom",
				(new BlockCustomMushroom("mushroom_brown")).setHardness(0.0F).setStepSound(Block.soundTypeGrass).setLightLevel(0.125F).setBlockName("mushroom").setBlockTextureName("mushroom_brown"));
		Block.blockRegistry.addObject(Block.getIdFromBlock(Blocks.red_mushroom), "red_mushroom",
				(new BlockCustomMushroom("mushroom_red")).setHardness(0.0F).setStepSound(Block.soundTypeGrass).setBlockName("mushroom").setBlockTextureName("mushroom_red"));
		Block.blockRegistry.addObject(Block.getIdFromBlock(Blocks.bookshelf), "bookshelf",
				(new BlockCustomBookshelf()).setHardness(1.5F).setStepSound(Block.soundTypeWood).setBlockName("bookshelf").setBlockTextureName("bookshelf"));
		Block.blockRegistry.addObject(Block.getIdFromBlock(Blocks.torch), "torch",
				(new BlockTorch()).setHardness(0.0F).setLightLevel(0.9375F).setStepSound(Block.soundTypeWood).setBlockName("torch").setBlockTextureName("torch_on"));
		Block.blockRegistry.addObject(Block.getIdFromBlock(Blocks.oak_stairs), "oak_stairs",
				(new BlockStair(Material.wood)).setBlockName("stairsWood"));
		Block.blockRegistry.addObject(Block.getIdFromBlock(Blocks.chest), "chest",
				(new BlockChestTFC(0)).setHardness(2.5F).setStepSound(Block.soundTypeWood).setBlockName("chest"));
		Block.blockRegistry.addObject(Block.getIdFromBlock(Blocks.crafting_table), "crafting_table",
				(new BlockWorkbench()).setHardness(2.5F).setStepSound(Block.soundTypeWood).setBlockName("workbench").setBlockTextureName("crafting_table"));
		Block.blockRegistry.addObject(Block.getIdFromBlock(Blocks.wheat), "wheat",
				(new BlockCrop()).setHardness(0.3F).setStepSound(Block.soundTypeGrass).setBlockName("crops").setBlockTextureName("wheat"));
		Block.blockRegistry.addObject(Block.getIdFromBlock(Blocks.snow_layer), "snow_layer",
				(new BlockCustomSnow()).setHardness(0.1F).setStepSound(Block.soundTypeSnow).setBlockName("snow").setLightOpacity(1).setBlockTextureName("snow"));
		Block.blockRegistry.addObject(Block.getIdFromBlock(Blocks.ice), "ice",
				(new BlockCustomIce()).setHardness(0.5F).setLightOpacity(3).setStepSound(Block.soundTypeGlass).setBlockName("ice").setBlockTextureName("ice"));
		Block.blockRegistry.addObject(Block.getIdFromBlock(Blocks.cactus), "cactus",
				(new BlockCustomCactus()).setHardness(0.4F).setStepSound(Block.soundTypeCloth).setBlockName("cactus").setBlockTextureName("cactus"));
		Block.blockRegistry.addObject(Block.getIdFromBlock(Blocks.reeds), "reeds",
				(new BlockCustomReed()).setHardness(0.0F).setStepSound(Block.soundTypeGrass).setBlockName("reeds").setBlockTextureName("reeds"));
		Block.blockRegistry.addObject(Block.getIdFromBlock(Blocks.pumpkin), "pumpkin",
				(new BlockCustomPumpkin(false)).setHardness(1.0F).setStepSound(Block.soundTypeWood).setBlockName("pumpkin").setBlockTextureName("pumpkin"));
		Block.blockRegistry.addObject(Block.getIdFromBlock(Blocks.vine), "vine",
				(new BlockCustomVine()).setHardness(0.2F).setStepSound(Block.soundTypeGrass).setBlockName("vine").setBlockTextureName("vine"));
		Block.blockRegistry.addObject(Block.getIdFromBlock(Blocks.fence_gate), "FenceGateTFC",
				(new BlockCustomFenceGate()).setBlockName("FenceGateTFC").setHardness(2));
		Block.blockRegistry.addObject(Block.getIdFromBlock(Blocks.waterlily), "waterlily",
				(new BlockCustomLilyPad()).setHardness(0.0F).setStepSound(Block.soundTypeGrass).setBlockName("waterlily").setBlockTextureName("waterlily"));
		Block.blockRegistry.addObject(Block.getIdFromBlock(Blocks.wooden_button), "wooden_button",
				(new BlockCustomButtonWood()).setHardness(0.5F).setStepSound(Block.soundTypeWood).setBlockName("button"));


		Planks = Blocks.planks;
		Planks2 = (new TFC.Blocks.BlockPlanks2(Material.wood)).setHardness(4.0F).setResistance(5.0F).setStepSound(Block.soundTypeWood).setBlockName("wood2").setBlockTextureName("planks");
		Wood = Blocks.log;
		Leaves = Blocks.leaves;
		Leaves2 = (new BlockCustomLeaves2()).setHardness(0.2F).setLightOpacity(1).setStepSound(Block.soundTypeGrass).setBlockName("leaves2");
		Sapling = Blocks.sapling;
		Sapling2 = (new BlockCustomSapling2()).setHardness(0.0F).setStepSound(Block.soundTypeGrass).setBlockName("sapling2");

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

		Dirt = (new TFC.Blocks.Terrain.BlockDirt(0, tilledSoil)).setHardness(2F).setStepSound(Block.soundTypeGravel).setBlockName("dirt");
		Dirt2 = (new TFC.Blocks.Terrain.BlockDirt2(16, tilledSoil2)).setHardness(2F).setStepSound(Block.soundTypeGravel).setBlockName("dirt");
		Clay = (new TFC.Blocks.Terrain.BlockClay()).setHardness(3F).setStepSound(Block.soundTypeGravel).setBlockName("clay");
		Clay2 = (new TFC.Blocks.Terrain.BlockClay2()).setHardness(3F).setStepSound(Block.soundTypeGravel).setBlockName("clay");
		ClayGrass = new TFC.Blocks.Terrain.BlockClayGrass(0).setHardness(3F).setStepSound(Block.soundTypeGrass).setBlockName("ClayGrass");
		ClayGrass2 = new TFC.Blocks.Terrain.BlockClayGrass(16).setHardness(3F).setStepSound(Block.soundTypeGrass).setBlockName("ClayGrass");
		Grass = (new TFC.Blocks.Terrain.BlockGrass()).setHardness(3F).setStepSound(Block.soundTypeGrass).setBlockName("Grass");
		Grass2 = new TFC.Blocks.Terrain.BlockGrass(16).setHardness(3F).setStepSound(Block.soundTypeGrass).setBlockName("Grass");
		Peat = new TFC.Blocks.Terrain.BlockPeat().setHardness(3F).setStepSound(Block.soundTypeGravel).setBlockName("Peat");
		PeatGrass = new TFC.Blocks.Terrain.BlockPeatGrass().setHardness(3F).setStepSound(Block.soundTypeGrass).setBlockName("PeatGrass");
		DryGrass = new BlockDryGrass(0).setHardness(3F).setStepSound(Block.soundTypeGrass).setBlockName("DryGrass");
		DryGrass2 =new BlockDryGrass(16).setHardness(3F).setStepSound(Block.soundTypeGrass).setBlockName("DryGrass");

		Ore = new BlockOre(Material.rock).setHardness(10F).setResistance(10F).setBlockName("Ore");
		Ore2 = new BlockOre2(Material.rock).setHardness(10F).setResistance(10F).setBlockName("Ore");
		Ore3 = new BlockOre3(Material.rock).setHardness(10F).setResistance(10F).setBlockName("Ore");
		LooseRock = new BlockLooseRock().setHardness(0.05F).setResistance(1F).setBlockName("LooseRock");
		LogPile = new BlockLogPile().setHardness(10F).setResistance(1F).setBlockName("LogPile");

		Sulfur = new BlockSulfur(Material.rock).setBlockName("Sulfur").setHardness(0.5F).setResistance(1F);
		WoodSupportV = new BlockWoodSupport(Material.wood).setBlockName("WoodSupportV").setHardness(0.5F).setResistance(1F);
		WoodSupportH = new BlockWoodSupport(Material.wood).setBlockName("WoodSupportH").setHardness(0.5F).setResistance(1F);
		WoodSupportV2 = new BlockWoodSupport2(Material.wood).setBlockName("WoodSupportV2").setHardness(0.5F).setResistance(1F);
		WoodSupportH2 = new BlockWoodSupport2(Material.wood).setBlockName("WoodSupportH2").setHardness(0.5F).setResistance(1F);

		tilledSoil = new TFC.Blocks.BlockFarmland(TFCBlocks.Dirt, 0).setHardness(2F).setStepSound(Block.soundTypeGravel).setBlockName("tilledSoil");
		tilledSoil2 = new TFC.Blocks.BlockFarmland(TFCBlocks.Dirt2, 16).setHardness(2F).setStepSound(Block.soundTypeGravel).setBlockName("tilledSoil2");

		fruitTreeWood = new BlockFruitWood().setBlockName("fruitTreeWood").setHardness(5.5F).setResistance(2F);
		fruitTreeLeaves = new BlockFruitLeaves(0).setBlockName("fruitTreeLeaves").setHardness(0.5F).setResistance(1F).setStepSound(Block.soundTypeGrass);
		fruitTreeLeaves2 = new BlockFruitLeaves(8).setBlockName("fruitTreeLeaves2").setHardness(0.5F).setResistance(1F).setStepSound(Block.soundTypeGrass);

		Sand = new TFC.Blocks.Terrain.BlockSand(0).setHardness(0.5F).setStepSound(Block.soundTypeSand).setBlockName("sand");
		Sand2 = new BlockSand2(16).setHardness(0.5F).setStepSound(Block.soundTypeSand).setBlockName("sand");

		WoodConstruct = (new TFC.Blocks.BlockWoodConstruct()).setHardness(4F).setStepSound(Block.soundTypeWood).setBlockName("WoodConstruct");

		Firepit = new BlockFirepit().setBlockName("Firepit").setHardness(1).setLightLevel(0F);
		Bellows = new BlockBellows(Material.wood).setBlockName("Bellows").setHardness(2);
		Forge= new BlockForge().setBlockName("Forge").setHardness(20).setLightLevel(0F);
		Anvil = new TFC.Blocks.Devices.BlockAnvil().setBlockName("Anvil").setHardness(3).setResistance(100F);
		Anvil2 = new TFC.Blocks.Devices.BlockAnvil(8).setBlockName("Anvil2").setHardness(3).setResistance(100F);

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

		WoodVert = new BlockLogVert().setBlockName("WoodVert").setHardness(40).setResistance(15F);
		WoodHoriz = new BlockLogHoriz(0).setBlockName("WoodHoriz").setHardness(40).setResistance(15F);
		WoodHoriz2 = new BlockLogHoriz(8).setBlockName("WoodHoriz2").setHardness(40).setResistance(15F);

		LogNatural2 = new TFC.Blocks.Flora.BlockLogNatural2().setHardness(50.0F).setStepSound(Block.soundTypeWood).setBlockName("log2");
		WoodVert2 = new BlockLogVert2().setBlockName("WoodVert2").setHardness(40).setResistance(15F);
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

		IngotPile =  new BlockIngotPile().setBlockName("ingotpile").setHardness(3);

		Barrel = new BlockBarrel().setBlockName("Barrel").setHardness(2);
		Barrel2 = new BlockBarrel2().setBlockName("Barrel").setHardness(2);
		Thatch =  new BlockThatch().setBlockName("Thatch").setHardness(1).setStepSound(Block.soundTypeGrass).setCreativeTab(CreativeTabs.tabBlock);
		Moss =  new BlockMoss().setBlockName("Moss").setHardness(1).setStepSound(Block.soundTypeGrass);

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

		FreshWaterFlowing = (new BlockFreshWaterFlowing()).setHardness(100.0F).setLightOpacity(3).setBlockName("water").setCreativeTab(CreativeTabs.tabDecorations);;
		FreshWaterStill  = (new BlockFreshWaterStill()).setHardness(100.0F).setLightOpacity(3).setBlockName("water").setCreativeTab(CreativeTabs.tabDecorations);;
		HotWaterFlowing = (new BlockHotWaterFlowing()).setHardness(100.0F).setLightOpacity(3).setBlockName("water");
		HotWaterStill  = (new BlockHotWaterStill()).setHardness(100.0F).setLightOpacity(3).setBlockName("water");
		
		SeaGrassStill = new BlockTallSeaGrassStill().setBlockName("SeaGrassStill").setHardness(0.3f).setCreativeTab(CreativeTabs.tabDecorations);
		SeaGrassFrozen = (new BlockFrozenSeaGrass()).setHardness(0.5F).setLightOpacity(3).setStepSound(Block.soundTypeGlass).setBlockName("seagrassice");
		SeaGrassFlowing = new BlockTallSeaGrassFlowing().setBlockName("SeaGrassFlowing").setHardness(0.3f).setCreativeTab(CreativeTabs.tabDecorations);

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
		Wood.setHarvestLevel("axe", 1);
		WoodHoriz.setHarvestLevel("axe", 1);
		WoodVert.setHarvestLevel("axe", 1);

		Wood.setHarvestLevel("hammer", 1);
		WoodHoriz.setHarvestLevel("hammer", 1);
		WoodVert.setHarvestLevel("hammer", 1);
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