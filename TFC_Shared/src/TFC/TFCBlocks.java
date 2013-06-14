package TFC;

import java.io.File;
import java.lang.reflect.Array;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import TFC.API.Constant.Global;
import TFC.Blocks.BlockCharcoal;
import TFC.Blocks.BlockCrop;
import TFC.Blocks.BlockDetailed;
import TFC.Blocks.BlockFiniteWater;
import TFC.Blocks.BlockFlora;
import TFC.Blocks.BlockFoodPrep;
import TFC.Blocks.BlockFruitLeaves;
import TFC.Blocks.BlockFruitWood;
import TFC.Blocks.BlockIngotPile;
import TFC.Blocks.BlockLogHoriz;
import TFC.Blocks.BlockLogPile;
import TFC.Blocks.BlockLogVert;
import TFC.Blocks.BlockLooseRock;
import TFC.Blocks.BlockMolten;
import TFC.Blocks.BlockSlab;
import TFC.Blocks.BlockStair;
import TFC.Blocks.BlockStalactite;
import TFC.Blocks.BlockSulfur;
import TFC.Blocks.BlockWoodSupport;
import TFC.Blocks.Devices.BlockBarrel;
import TFC.Blocks.Devices.BlockBellows;
import TFC.Blocks.Devices.BlockBloomery;
import TFC.Blocks.Devices.BlockChestTFC;
import TFC.Blocks.Devices.BlockFirepit;
import TFC.Blocks.Devices.BlockForge;
import TFC.Blocks.Devices.BlockMetallurgy;
import TFC.Blocks.Devices.BlockPottery;
import TFC.Blocks.Devices.BlockQuern;
import TFC.Blocks.Devices.BlockScribe;
import TFC.Blocks.Devices.BlockSluice;
import TFC.Blocks.Devices.BlockSpawnMeter;
import TFC.Blocks.Devices.BlockToolRack;
import TFC.Blocks.Terrain.BlockDryGrass;
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
import TFC.Blocks.Terrain.BlockOre;
import TFC.Blocks.Terrain.BlockOre2;
import TFC.Blocks.Terrain.BlockOre3;
import TFC.Blocks.Terrain.BlockSand2;
import TFC.Blocks.Terrain.BlockSed;
import TFC.Blocks.Terrain.BlockSedBrick;
import TFC.Blocks.Terrain.BlockSedCobble;
import TFC.Blocks.Terrain.BlockSedSmooth;
import TFC.Blocks.Vanilla.BlockCustomBookshelf;
import TFC.Blocks.Vanilla.BlockCustomCactus;
import TFC.Blocks.Vanilla.BlockCustomDoor;
import TFC.Blocks.Vanilla.BlockCustomFenceGate;
import TFC.Blocks.Vanilla.BlockCustomFlower;
import TFC.Blocks.Vanilla.BlockCustomFlowing;
import TFC.Blocks.Vanilla.BlockCustomIce;
import TFC.Blocks.Vanilla.BlockCustomLeaves;
import TFC.Blocks.Vanilla.BlockCustomMushroom;
import TFC.Blocks.Vanilla.BlockCustomReed;
import TFC.Blocks.Vanilla.BlockCustomSapling;
import TFC.Blocks.Vanilla.BlockCustomSnow;
import TFC.Blocks.Vanilla.BlockCustomStationary;
import TFC.Blocks.Vanilla.BlockCustomTallGrass;
import TFC.Blocks.Vanilla.BlockCustomVine;
import TFC.Blocks.Vanilla.BlockCustomWall;
import TFC.Core.TFC_Settings;
import TFC.Items.ItemBarrels;
import TFC.Items.ItemToolRack;
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
	public static int BellowsRenderId;
	public static int ScribeRenderId;
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
	public static Block Leaves;
	public static Block Sapling;
	public static Block WoodSupportV;
	public static Block WoodSupportH;
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
	public static Block finiteWater;
	public static Block Firepit;
	public static Block Bellows;
	public static Block Anvil;
	public static Block Anvil2;
	public static Block Scribe;
	public static Block Forge;
	public static Block Bloomery;
	public static Block MetalTable;
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
	public static Block SuperDetailed;
	public static Block WoodConstruct;
	public static Block WoodVert;
	public static Block WoodHoriz;
	public static Block WoodHoriz2;
	public static Block ToolRack;
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
	public static Block Pottery;
	public static Block Thatch;

	public static Block Flora;
	
	static Configuration config;
	
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
		GameRegistry.registerBlock(Grass, "Grass");
		GameRegistry.registerBlock(Grass2, "Grass2");
		GameRegistry.registerBlock(ClayGrass, "ClayGrass");
		GameRegistry.registerBlock(ClayGrass2, "ClayGrass2");
		GameRegistry.registerBlock(PeatGrass, "PeatGrass");
		GameRegistry.registerBlock(Peat, "Peat");
		GameRegistry.registerBlock(DryGrass, "DryGrass");
		GameRegistry.registerBlock(DryGrass2, "DryGrass2");
		GameRegistry.registerBlock(LooseRock, "LooseRock");
		GameRegistry.registerBlock(LogPile, "LogPile");
		GameRegistry.registerBlock(Charcoal, "Charcoal");
		GameRegistry.registerBlock(Detailed, "Detailed");
		
		GameRegistry.registerBlock(tilledSoil, "tilledSoil");
		GameRegistry.registerBlock(tilledSoil2, "tilledSoil2");

		GameRegistry.registerBlock(finiteWater, "finiteWater");

		GameRegistry.registerBlock(WoodSupportV, TFC.Items.ItemBlocks.ItemWoodSupport.class,"WoodSupportV");
		GameRegistry.registerBlock(WoodSupportH, TFC.Items.ItemBlocks.ItemWoodSupport.class, "WoodSupportH");
		GameRegistry.registerBlock(Sulfur, "Sulfur");
		GameRegistry.registerBlock(Block.wood, TFC.Items.ItemBlocks.ItemCustomWood.class, "wood");
		GameRegistry.registerBlock(Block.leaves, TFC.Items.ItemBlocks.ItemCustomLeaves.class, "leaves");
		GameRegistry.registerBlock(Block.sapling, TFC.Items.ItemBlocks.ItemSapling.class, "sapling");
		GameRegistry.registerBlock(Block.planks, TFC.Items.ItemBlocks.ItemPlankBlock.class, "planks");

		GameRegistry.registerBlock(Firepit, "Firepit");
		GameRegistry.registerBlock(Bellows, "Bellows");
		GameRegistry.registerBlock(Anvil, TFC.Items.ItemBlocks.ItemAnvil.class, "Anvil");
		GameRegistry.registerBlock(Anvil2, TFC.Items.ItemBlocks.ItemAnvil2.class, "Anvil2");
		GameRegistry.registerBlock(Scribe, TFC.Items.ItemBlocks.ItemTerraBlock.class, "Scribe");
		GameRegistry.registerBlock(Forge, "Forge");
		GameRegistry.registerBlock(MetalTable, TFC.Items.ItemBlocks.ItemTerraBlock.class, "MetalTable");
		GameRegistry.registerBlock(Molten, "Molten");
		GameRegistry.registerBlock(Bloomery, TFC.Items.ItemBlocks.ItemTerraBlock.class, "Bloomery");
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
		
		GameRegistry.registerBlock(ToolRack, ItemToolRack.class,"ToolRack");
		GameRegistry.registerBlock(SpawnMeter, "SpawnMeter");
		GameRegistry.registerBlock(FoodPrep, "FoodPrep");
		GameRegistry.registerBlock(Quern, "Quern");
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
		
		// Wooden Doors
		for (int i=0; i < Global.WOOD_ALL.length; i++) {
			GameRegistry.registerBlock(Doors[i], "Door"+Global.WOOD_ALL[i].replaceAll(" ", ""));
		}

		GameRegistry.registerBlock(IngotPile, "IngotPile");
		GameRegistry.registerBlock(Barrel, ItemBarrels.class,"Barrel");
		
		GameRegistry.registerBlock(Flora, "Flora");
		GameRegistry.registerBlock(Pottery, "ClayPottery");
		//GameRegistry.registerBlock(Thatch, "Thatch");
	}
	
	public static void LoadBlocks()
	{
		try
		{
			config = new Configuration(new File(TerraFirmaCraft.proxy.getMinecraftDir(), "/config/TFC.cfg"));
			config.load();
		} catch (Exception e) {
			System.out.println(new StringBuilder().append("[TFC] Error while trying to access block configuration: " + e.getLocalizedMessage()).toString());
			config = null;
		}

		System.out.println(new StringBuilder().append("[TFC] Loading Blocks").toString());		
		
		Block.blocksList[5] = null;
		Block.blocksList[6] = null;
		Block.blocksList[8] = null;
		Block.blocksList[9] = null;
		Block.blocksList[10] = null;
		Block.blocksList[17] = null;
		Block.blocksList[18] = null;
		Block.blocksList[31] = null;
		Block.blocksList[37] = null;
		Block.blocksList[38] = null;
		Block.blocksList[39] = null;
		Block.blocksList[40] = null;
		Block.blocksList[47] = null;
		Block.blocksList[53] = null;
		Block.blocksList[54] = null;
		Block.blocksList[58] = null;
		Block.blocksList[59] = null;
		Block.blocksList[78] = null;
		Block.blocksList[79] = null;
		Block.blocksList[81] = null;
		Block.blocksList[83] = null;
		Block.blocksList[106] = null;
		Block.blocksList[107] = null;
		Block.blocksList[125].setCreativeTab(null);
		Block.blocksList[126].setCreativeTab(null);
		Block.blocksList[134].setCreativeTab(null);
		Block.blocksList[135].setCreativeTab(null);
		Block.blocksList[136].setCreativeTab(null);

		Block.blocksList[5] = (new TFC.Blocks.BlockPlanks(5, Material.wood)).setHardness(4.0F).setResistance(5.0F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("wood");
		Block.blocksList[6] = (new BlockCustomSapling(6)).setHardness(0.0F).setStepSound(Block.soundGrassFootstep).setUnlocalizedName("sapling");
		Block.blocksList[8] = (new BlockCustomFlowing(8, Material.water)).setHardness(100.0F).setLightOpacity(3).setUnlocalizedName("water");
		Block.blocksList[9] = (new BlockCustomStationary(9, Material.water)).setHardness(100.0F).setLightOpacity(3).setUnlocalizedName("water");
		Block.blocksList[10] = (new BlockCustomFlowing(10, Material.lava)).setHardness(0.0F).setLightValue(1.0F).setLightOpacity(255).setUnlocalizedName("lava");
		Block.blocksList[17] = (new TFC.Blocks.BlockLogNatural(17)).setHardness(50.0F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("log");
		Block.blocksList[18] = (new BlockCustomLeaves(18)).setHardness(0.2F).setLightOpacity(1).setStepSound(Block.soundGrassFootstep).setUnlocalizedName("leaves");
		Block.blocksList[31] = (new BlockCustomTallGrass(31)).setHardness(0.0F).setStepSound(Block.soundGrassFootstep).setUnlocalizedName("tallgrass");
		Block.blocksList[37] = (new BlockCustomFlower(37)).setHardness(0.0F).setStepSound(Block.soundGrassFootstep).setUnlocalizedName("flower");
		Block.blocksList[38] = (new BlockCustomFlower(38)).setHardness(0.0F).setStepSound(Block.soundGrassFootstep).setUnlocalizedName("rose");
		Block.blocksList[39] = (new BlockCustomMushroom(39, "mushroom_brown")).setHardness(0.0F).setStepSound(Block.soundGrassFootstep).setLightValue(0.125F).setUnlocalizedName("mushroom");
		Block.blocksList[40] = (new BlockCustomMushroom(40, "mushroom_red")).setHardness(0.0F).setStepSound(Block.soundGrassFootstep).setUnlocalizedName("mushroom");
		Block.blocksList[47] = (new BlockCustomBookshelf(47)).setHardness(1.5F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("bookshelf");
		Block.blocksList[53] = (new BlockStair(53, Material.wood)).setUnlocalizedName("stairsWood");
		Block.blocksList[54] = (new BlockChestTFC(54, 0)).setHardness(2.5F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("chest");
		Block.blocksList[58] = (new TFC.Blocks.Devices.BlockWorkbench(58)).setHardness(2.5F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("workbench");
		Block.blocksList[59] = (new BlockCrop(59, 88)).setHardness(0.3F).setStepSound(Block.soundGrassFootstep).setUnlocalizedName("crops");
		Block.blocksList[78] = (new BlockCustomSnow(78)).setHardness(0.1F).setStepSound(Block.soundClothFootstep).setUnlocalizedName("snow").setLightOpacity(1);
		Block.blocksList[79] = (new BlockCustomIce(79)).setHardness(0.5F).setLightOpacity(3).setStepSound(Block.soundGlassFootstep).setUnlocalizedName("ice");
		Block.blocksList[81] = (new BlockCustomCactus(81)).setHardness(0.4F).setStepSound(Block.soundClothFootstep).setUnlocalizedName("cactus");
		Block.blocksList[83] = (new BlockCustomReed(83)).setHardness(0.0F).setStepSound(Block.soundGrassFootstep).setUnlocalizedName("reeds");
		Block.blocksList[106] = (new BlockCustomVine(106)).setHardness(0.2F).setStepSound(Block.soundGrassFootstep).setUnlocalizedName("vine");
		Block.blocksList[107] = (new BlockCustomFenceGate(107)).setHardness(2.0F).setResistance(5.0F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("fenceGate");


		TFCBlocks.Planks = Block.blocksList[5];
		TFCBlocks.Wood = Block.blocksList[17];
		TFCBlocks.Leaves = Block.blocksList[18];
		TFCBlocks.Sapling = Block.blocksList[6];

		TFCBlocks.StoneIgInCobble = new BlockIgInCobble(TFC_Settings.getIntFor(config,"block","StoneIgInCobble", 198), Material.rock).setHardness(13F).setResistance(10F).setUnlocalizedName("IgInRockCobble");
		TFCBlocks.StoneIgIn = new BlockIgIn(TFC_Settings.getIntFor(config,"block","StoneIgIn", 209), Material.rock, TFCBlocks.StoneIgInCobble.blockID).setHardness(13F).setResistance(10F).setUnlocalizedName("IgInRock");	
		TFCBlocks.StoneIgInSmooth = new BlockIgInSmooth(TFC_Settings.getIntFor(config,"block","StoneIgInSmooth", 182)).setHardness(13F).setResistance(20F).setUnlocalizedName("IgInRockSmooth");
		TFCBlocks.StoneIgInBrick = new BlockIgInBrick(TFC_Settings.getIntFor(config,"block","StoneIgInBrick", 186)).setHardness(13F).setResistance(15F).setUnlocalizedName("IgInRockBrick");

		TFCBlocks.StoneSedCobble = new BlockSedCobble(TFC_Settings.getIntFor(config,"block","StoneSedCobble", 199), Material.rock).setHardness(10F).setResistance(10F).setUnlocalizedName("SedRockCobble");
		TFCBlocks.StoneSed = new BlockSed(TFC_Settings.getIntFor(config,"block","StoneSed", 210), Material.rock, TFCBlocks.StoneSedCobble.blockID).setHardness(10F).setResistance(7F).setUnlocalizedName("SedRock");
		TFCBlocks.StoneSedSmooth = new BlockSedSmooth(TFC_Settings.getIntFor(config,"block","StoneSedSmooth", 183)).setHardness(10F).setResistance(20F).setUnlocalizedName("SedRockSmooth");
		TFCBlocks.StoneSedBrick = new BlockSedBrick(TFC_Settings.getIntFor(config,"block","StoneSedBrick", 187)).setHardness(10F).setResistance(15F).setUnlocalizedName("SedRockBrick");

		TFCBlocks.StoneIgExCobble = new BlockIgExCobble(TFC_Settings.getIntFor(config,"block","StoneIgExCobble", 200), Material.rock).setHardness(13F).setResistance(10F).setUnlocalizedName("IgExRockCobble");
		TFCBlocks.StoneIgEx = new BlockIgEx(TFC_Settings.getIntFor(config,"block","StoneIgEx", 211), Material.rock, TFCBlocks.StoneIgExCobble.blockID).setHardness(13F).setResistance(10F).setUnlocalizedName("IgExRock");
		TFCBlocks.StoneIgExSmooth = new BlockIgExSmooth(TFC_Settings.getIntFor(config,"block","StoneIgExSmooth", 184)).setHardness(13F).setResistance(20F).setUnlocalizedName("IgExRockSmooth");
		TFCBlocks.StoneIgExBrick = new BlockIgExBrick(TFC_Settings.getIntFor(config,"block","StoneIgExBrick", 188)).setHardness(13F).setResistance(15F).setUnlocalizedName("IgExRockBrick");

		TFCBlocks.StoneMMCobble = new BlockMMCobble(TFC_Settings.getIntFor(config,"block","StoneMMCobble", 201), Material.rock).setHardness(10F).setResistance(10F).setUnlocalizedName("MMRockCobble");
		TFCBlocks.StoneMM = new BlockMM(TFC_Settings.getIntFor(config,"block","StoneMM", 212), Material.rock, TFCBlocks.StoneMMCobble.blockID).setHardness(10F).setResistance(8F).setUnlocalizedName("MMRock");
		TFCBlocks.StoneMMSmooth = new BlockMMSmooth(TFC_Settings.getIntFor(config,"block","StoneMMSmooth", 185)).setHardness(10F).setResistance(20F).setUnlocalizedName("MMRockSmooth");
		TFCBlocks.StoneMMBrick = new BlockMMBrick(TFC_Settings.getIntFor(config,"block","StoneMMBrick", 189)).setHardness(10F).setResistance(15F).setUnlocalizedName("MMRockBrick");

		TFCBlocks.Dirt = (new TFC.Blocks.Terrain.BlockDirt(TFC_Settings.getIntFor(config,"block","Dirt", 190), 0, TFCBlocks.tilledSoil)).setHardness(2F).setStepSound(Block.soundGravelFootstep).setUnlocalizedName("dirt");
		TFCBlocks.Dirt2 = (new TFC.Blocks.Terrain.BlockDirt2(TFC_Settings.getIntFor(config,"block","Dirt2", 191), 16, TFCBlocks.tilledSoil2)).setHardness(2F).setStepSound(Block.soundGravelFootstep).setUnlocalizedName("dirt");
		TFCBlocks.Clay = (new TFC.Blocks.Terrain.BlockClay(TFC_Settings.getIntFor(config,"block","Clay", 192))).setHardness(3F).setStepSound(Block.soundGravelFootstep).setUnlocalizedName("clay");
		TFCBlocks.Clay2 = (new TFC.Blocks.Terrain.BlockClay2(TFC_Settings.getIntFor(config,"block","Clay2", 193))).setHardness(3F).setStepSound(Block.soundGravelFootstep).setUnlocalizedName("clay");
		TFCBlocks.ClayGrass = new TFC.Blocks.Terrain.BlockClayGrass(TFC_Settings.getIntFor(config,"block","ClayGrass", 194), 0).setHardness(3F).setStepSound(Block.soundGrassFootstep).setUnlocalizedName("ClayGrass"); 
		TFCBlocks.ClayGrass2 = new TFC.Blocks.Terrain.BlockClayGrass(TFC_Settings.getIntFor(config,"block","ClayGrass2", 195), 16).setHardness(3F).setStepSound(Block.soundGrassFootstep).setUnlocalizedName("ClayGrass"); 
		TFCBlocks.Grass = (new TFC.Blocks.Terrain.BlockGrass(TFC_Settings.getIntFor(config,"block","Grass", 196))).setHardness(3F).setStepSound(Block.soundGrassFootstep);
		TFCBlocks.Grass2 = new TFC.Blocks.Terrain.BlockGrass(TFC_Settings.getIntFor(config,"block","Grass2", 197), 16).setHardness(3F).setStepSound(Block.soundGrassFootstep);  
		TFCBlocks.Peat = new TFC.Blocks.Terrain.BlockPeat(TFC_Settings.getIntFor(config,"block","Peat", 180)).setHardness(3F).setStepSound(Block.soundGravelFootstep).setUnlocalizedName("Peat");
		TFCBlocks.PeatGrass = new TFC.Blocks.Terrain.BlockPeatGrass(TFC_Settings.getIntFor(config,"block","PeatGrass", 181)).setHardness(3F).setStepSound(Block.soundGrassFootstep).setUnlocalizedName("PeatGrass");
		TFCBlocks.DryGrass = new BlockDryGrass(TFC_Settings.getIntFor(config,"block","DryGrass", 218), 0).setHardness(3F).setStepSound(Block.soundGrassFootstep).setUnlocalizedName("Grass");
		TFCBlocks.DryGrass2 =new BlockDryGrass(TFC_Settings.getIntFor(config,"block","DryGrass2", 219), 16).setHardness(3F).setStepSound(Block.soundGrassFootstep).setUnlocalizedName("Grass");  

		TFCBlocks.Ore = new BlockOre(TFC_Settings.getIntFor(config,"block","Ore", 213), Material.rock).setHardness(10F).setResistance(10F).setUnlocalizedName("Ore");
		TFCBlocks.Ore2 = new BlockOre2(TFC_Settings.getIntFor(config,"block","Ore2", 214), Material.rock).setHardness(10F).setResistance(10F).setUnlocalizedName("Ore");
		TFCBlocks.Ore3 = new BlockOre3(TFC_Settings.getIntFor(config,"block","Ore3", 215), Material.rock).setHardness(10F).setResistance(10F).setUnlocalizedName("Ore");
		TFCBlocks.LooseRock = new BlockLooseRock(TFC_Settings.getIntFor(config,"block","LooseRock", 179)).setHardness(0.05F).setResistance(1F).setUnlocalizedName("LooseRock");
		TFCBlocks.LogPile = new BlockLogPile(TFC_Settings.getIntFor(config,"block","LogPile", 178)).setHardness(10F).setResistance(1F).setUnlocalizedName("LogPile");

		TFCBlocks.Sulfur = new BlockSulfur(TFC_Settings.getIntFor(config,"block","Sulfur", 208), Material.rock).setUnlocalizedName("Sulfur").setHardness(0.5F).setResistance(1F);
		TFCBlocks.WoodSupportV = new BlockWoodSupport(TFC_Settings.getIntFor(config,"block","WoodSupportV", 203), Material.wood).setUnlocalizedName("WoodSupportV").setHardness(0.5F).setResistance(1F);
		TFCBlocks.WoodSupportH = new BlockWoodSupport(TFC_Settings.getIntFor(config,"block","WoodSupportH", 202), Material.wood).setUnlocalizedName("WoodSupportH").setHardness(0.5F).setResistance(1F);

		TFCBlocks.tilledSoil = new TFC.Blocks.BlockFarmland(TFC_Settings.getIntFor(config,"block","tilledSoil", 177), TFCBlocks.Dirt.blockID, 0).setHardness(2F).setStepSound(Block.soundGravelFootstep).setUnlocalizedName("tilledSoil");
		TFCBlocks.tilledSoil2 = new TFC.Blocks.BlockFarmland(TFC_Settings.getIntFor(config,"block","tilledSoil2", 176), TFCBlocks.Dirt2.blockID, 16).setHardness(2F).setStepSound(Block.soundGravelFootstep).setUnlocalizedName("tilledSoil2");

		TFCBlocks.fruitTreeWood = new BlockFruitWood(TFC_Settings.getIntFor(config,"block","fruitTreeWood", 175)).setUnlocalizedName("fruitTreeWood").setHardness(5.5F).setResistance(2F);
		TFCBlocks.fruitTreeLeaves = new BlockFruitLeaves(TFC_Settings.getIntFor(config,"block","fruitTreeLeaves", 174), 0).setUnlocalizedName("fruitTreeLeaves").setHardness(0.5F).setResistance(1F).setStepSound(Block.soundGrassFootstep);
		TFCBlocks.fruitTreeLeaves2 = new BlockFruitLeaves(TFC_Settings.getIntFor(config,"block","fruitTreeLeaves2", 173), 8).setUnlocalizedName("fruitTreeLeaves2").setHardness(0.5F).setResistance(1F).setStepSound(Block.soundGrassFootstep);

		TFCBlocks.Sand = new TFC.Blocks.Terrain.BlockSand(TFC_Settings.getIntFor(config,"block","Sand", 216)).setHardness(0.5F).setStepSound(Block.soundSandFootstep).setUnlocalizedName("sand");
		TFCBlocks.Sand2 = new BlockSand2(TFC_Settings.getIntFor(config,"block","Sand2", 217)).setHardness(0.5F).setStepSound(Block.soundSandFootstep).setUnlocalizedName("sand");
		
		TFCBlocks.WoodConstruct = (new TFC.Blocks.BlockWoodConstruct(TFC_Settings.getIntFor(config,"block","WoodConstruct", 2200))).setHardness(4F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("WoodConstruct");

		TFCBlocks.finiteWater = new BlockFiniteWater(TFC_Settings.getIntFor(config,"block","bucketWater", 224)).setHardness(100.0F).setLightOpacity(3).setUnlocalizedName("bucketWater");

		TFCBlocks.Firepit = new BlockFirepit(TFC_Settings.getIntFor(config,"block","Firepit", 2015)).setUnlocalizedName("Firepit").setHardness(1).setLightValue(0F);
		TFCBlocks.Bellows = new BlockBellows(TFC_Settings.getIntFor(config,"block","Bellows", 2014),Material.wood).setUnlocalizedName("Bellows").setHardness(2);
		TFCBlocks.Forge= new BlockForge(TFC_Settings.getIntFor(config,"block","Forge", 2013)).setUnlocalizedName("Forge").setHardness(20).setLightValue(0F);
		TFCBlocks.Scribe = new BlockScribe(TFC_Settings.getIntFor(config,"block","Scribe", 2012)).setUnlocalizedName("Scribe").setHardness(2);
		TFCBlocks.Anvil = new TFC.Blocks.Devices.BlockAnvil(TFC_Settings.getIntFor(config,"block","Anvil", 2011)).setUnlocalizedName("Anvil").setHardness(3).setResistance(100F);
		TFCBlocks.Anvil2 = new TFC.Blocks.Devices.BlockAnvil(TFC_Settings.getIntFor(config,"block","Anvil2", 2010), 8).setUnlocalizedName("Anvil2").setHardness(3).setResistance(100F);

		TFCBlocks.MetalTable = new BlockMetallurgy(TFC_Settings.getIntFor(config,"block","Metallurgy", 2009)).setUnlocalizedName("Metallurgy").setHardness(3);
		TFCBlocks.Molten = new BlockMolten(TFC_Settings.getIntFor(config,"block","Molten", 2008)).setUnlocalizedName("Molten").setHardness(20);
		TFCBlocks.Bloomery = new BlockBloomery(TFC_Settings.getIntFor(config,"block","Bloomery", 2007)).setUnlocalizedName("Bloomery").setHardness(20).setLightValue(0F);
		TFCBlocks.Sluice = new BlockSluice(TFC_Settings.getIntFor(config,"block","Sluice", 2003)).setUnlocalizedName("Sluice").setHardness(2F).setResistance(20F);

		TFCBlocks.stoneStairs = new BlockStair(TFC_Settings.getIntFor(config,"block","stoneStairs", 2000), Material.rock).setUnlocalizedName("stoneStairs").setHardness(10).setResistance(15F);
		TFCBlocks.stoneSlabs = new BlockSlab(TFC_Settings.getIntFor(config,"block","stoneSlabs", 2001)).setUnlocalizedName("stoneSlabs").setHardness(10).setResistance(15F);
		TFCBlocks.stoneStalac = new BlockStalactite(TFC_Settings.getIntFor(config,"block","stoneStalac", 2002)).setUnlocalizedName("stoneStalac").setHardness(5);
		
		TFCBlocks.Charcoal = new BlockCharcoal(TFC_Settings.getIntFor(config,"block","Charcoal", 2016)).setHardness(3F).setResistance(10F).setUnlocalizedName("Charcoal");
		
		TFCBlocks.Detailed = new BlockDetailed(TFC_Settings.getIntFor(config,"block","StoneDetailed", 2017)).setUnlocalizedName("StoneDetailed").setHardness(10).setResistance(15F);
		
		TFCBlocks.WoodVert = new BlockLogVert(TFC_Settings.getIntFor(config,"block","WoodVert", 2018)).setUnlocalizedName("WoodVert").setHardness(40).setResistance(15F);
		TFCBlocks.WoodHoriz = new BlockLogHoriz(TFC_Settings.getIntFor(config,"block","WoodHoriz", 2019), 0).setUnlocalizedName("WoodHoriz").setHardness(40).setResistance(15F);
		TFCBlocks.WoodHoriz2 = new BlockLogHoriz(TFC_Settings.getIntFor(config,"block","WoodHoriz2", 2020), 8).setUnlocalizedName("WoodHoriz2").setHardness(40).setResistance(15F);
		
		TFCBlocks.ToolRack = new BlockToolRack(TFC_Settings.getIntFor(config,"block","ToolRack", 2021)).setHardness(3F).setUnlocalizedName("Toolrack");
		TFCBlocks.SpawnMeter = new BlockSpawnMeter(TFC_Settings.getIntFor(config,"block","SpawnMeter", 2022)).setHardness(3F).setUnlocalizedName("SpawnMeter");
		TFCBlocks.FoodPrep = new BlockFoodPrep(TFC_Settings.getIntFor(config,"block","FoodPrep", 2023)).setHardness(1F).setUnlocalizedName("FoodPrep");
		TFCBlocks.Quern = new BlockQuern(TFC_Settings.getIntFor(config,"block","Quern", 2024)).setHardness(3F).setUnlocalizedName("Quern");
		
		TFCBlocks.WallCobbleIgIn = new BlockCustomWall(TFC_Settings.getIntFor(config,"block","WallCobbleIgIn", 2025), StoneIgInCobble, 3).setUnlocalizedName("WallCobble");
		TFCBlocks.WallCobbleIgEx = new BlockCustomWall(TFC_Settings.getIntFor(config,"block","WallCobbleIgEx", 2026), StoneIgExCobble, 4).setUnlocalizedName("WallCobble");
		TFCBlocks.WallCobbleSed = new BlockCustomWall(TFC_Settings.getIntFor(config,"block","WallCobbleSed", 2027), StoneSedCobble, 10).setUnlocalizedName("WallCobble");
		TFCBlocks.WallCobbleMM = new BlockCustomWall(TFC_Settings.getIntFor(config,"block","WallCobbleMM", 2028), StoneMMCobble, 6).setUnlocalizedName("WallCobble");
		TFCBlocks.WallRawIgIn = new BlockCustomWall(TFC_Settings.getIntFor(config,"block","WallRawIgIn", 2029), StoneIgIn, 3).setUnlocalizedName("WallRaw");
		TFCBlocks.WallRawIgEx = new BlockCustomWall(TFC_Settings.getIntFor(config,"block","WallRawIgEx", 2030), StoneIgEx, 4).setUnlocalizedName("WallRaw");
		TFCBlocks.WallRawSed = new BlockCustomWall(TFC_Settings.getIntFor(config,"block","WallRawSed", 2031), StoneSed, 10).setUnlocalizedName("WallRaw");
		TFCBlocks.WallRawMM = new BlockCustomWall(TFC_Settings.getIntFor(config,"block","WallRawMM", 2032), StoneMM, 6).setUnlocalizedName("WallRaw");
		TFCBlocks.WallBrickIgIn = new BlockCustomWall(TFC_Settings.getIntFor(config,"block","WallBrickIgIn", 2033), StoneIgInBrick, 3).setUnlocalizedName("WallBrick");
		TFCBlocks.WallBrickIgEx = new BlockCustomWall(TFC_Settings.getIntFor(config,"block","WallBrickIgEx", 2034), StoneIgExBrick, 4).setUnlocalizedName("WallBrick");
		TFCBlocks.WallBrickSed = new BlockCustomWall(TFC_Settings.getIntFor(config,"block","WallBrickSed", 2035), StoneSedBrick, 10).setUnlocalizedName("WallBrick");
		TFCBlocks.WallBrickMM = new BlockCustomWall(TFC_Settings.getIntFor(config,"block","WallBrickMM", 2036), StoneMMBrick, 6).setUnlocalizedName("WallBrick");
		TFCBlocks.WallSmoothIgIn = new BlockCustomWall(TFC_Settings.getIntFor(config,"block","WallSmoothIgIn", 2037), StoneIgInSmooth, 3).setUnlocalizedName("WallSmooth");
		TFCBlocks.WallSmoothIgEx = new BlockCustomWall(TFC_Settings.getIntFor(config,"block","WallSmoothIgEx", 2038), StoneIgExSmooth, 4).setUnlocalizedName("WallSmooth");
		TFCBlocks.WallSmoothSed = new BlockCustomWall(TFC_Settings.getIntFor(config,"block","WallSmoothSed", 2039), StoneSedSmooth, 10).setUnlocalizedName("WallSmooth");
		TFCBlocks.WallSmoothMM = new BlockCustomWall(TFC_Settings.getIntFor(config,"block","WallSmoothMM", 2040), StoneMMSmooth, 6).setUnlocalizedName("WallSmooth");
		
		// Wooden Doors
		for (int i=0; i < Global.WOOD_ALL.length; i++) {
			TFCBlocks.Doors[i] = new BlockCustomDoor(TFC_Settings.getIntFor(config,"block","Door"+Global.WOOD_ALL[i].replaceAll(" ", ""), 2041+i), i).setUnlocalizedName("Door "+Global.WOOD_ALL[i]);
		}
		
		TFCBlocks.IngotPile =  new BlockIngotPile(TFC_Settings.getIntFor(config, "block", "IngotPile", 2060)).setUnlocalizedName("ingotpile").setHardness(3);
		int num = 2061;
		TFCBlocks.Barrel = new BlockBarrel(TFC_Settings.getIntFor(config, "block", "Barrel", num++)).setUnlocalizedName("Barrel").setHardness(2);
		
		TFCBlocks.Flora = new BlockFlora(TFC_Settings.getIntFor(config, "block", "Flora", num++)).setUnlocalizedName("Flora");
		TFCBlocks.Pottery = new BlockPottery(TFC_Settings.getIntFor(config, "block", "Pottery", num++)).setUnlocalizedName("Pottery");
		
		MinecraftForge.setBlockHarvestLevel(TFCBlocks.StoneIgIn, "pickaxe", 0);
		MinecraftForge.setBlockHarvestLevel(TFCBlocks.StoneIgEx, "pickaxe", 0);
		MinecraftForge.setBlockHarvestLevel(TFCBlocks.StoneSed, "pickaxe", 0);
		MinecraftForge.setBlockHarvestLevel(TFCBlocks.StoneMM, "pickaxe", 0);
		MinecraftForge.setBlockHarvestLevel(TFCBlocks.stoneStalac, "pickaxe", 0);
		MinecraftForge.setBlockHarvestLevel(TFCBlocks.Ore, "pickaxe", 1);
		MinecraftForge.setBlockHarvestLevel(TFCBlocks.Ore2, "pickaxe", 1);
		MinecraftForge.setBlockHarvestLevel(TFCBlocks.Ore3, "pickaxe", 1);
		MinecraftForge.setBlockHarvestLevel(TFCBlocks.Dirt, "shovel", 0);
		MinecraftForge.setBlockHarvestLevel(TFCBlocks.Dirt2, "shovel", 0);
		MinecraftForge.setBlockHarvestLevel(TFCBlocks.Grass, "shovel", 0);
		MinecraftForge.setBlockHarvestLevel(TFCBlocks.Grass2, "shovel", 0);
		MinecraftForge.setBlockHarvestLevel(TFCBlocks.DryGrass, "shovel", 0);
		MinecraftForge.setBlockHarvestLevel(TFCBlocks.DryGrass2, "shovel", 0);
		MinecraftForge.setBlockHarvestLevel(TFCBlocks.Clay, "shovel", 0);
		MinecraftForge.setBlockHarvestLevel(TFCBlocks.Clay2, "shovel", 0);
		MinecraftForge.setBlockHarvestLevel(TFCBlocks.ClayGrass, "shovel", 0);
		MinecraftForge.setBlockHarvestLevel(TFCBlocks.ClayGrass2, "shovel", 0);
		MinecraftForge.setBlockHarvestLevel(TFCBlocks.Wood, "axe", 1);
		MinecraftForge.setBlockHarvestLevel(TFCBlocks.WoodHoriz, "axe", 1);
		MinecraftForge.setBlockHarvestLevel(TFCBlocks.WoodVert, "axe", 1);
		MinecraftForge.setBlockHarvestLevel(TFCBlocks.Wood, "hammer", 1);
		MinecraftForge.setBlockHarvestLevel(TFCBlocks.WoodHoriz, "hammer", 1);
		MinecraftForge.setBlockHarvestLevel(TFCBlocks.WoodVert, "hammer", 1);
		MinecraftForge.setBlockHarvestLevel(TFCBlocks.Peat, "shovel", 0);
		MinecraftForge.setBlockHarvestLevel(TFCBlocks.PeatGrass, "shovel", 0);
		MinecraftForge.setBlockHarvestLevel(TFCBlocks.Sand, "shovel", 0);
		MinecraftForge.setBlockHarvestLevel(TFCBlocks.Sand2, "shovel", 0);
		MinecraftForge.setBlockHarvestLevel(TFCBlocks.Charcoal, "shovel", 0);
		MinecraftForge.setBlockHarvestLevel(TFCBlocks.WoodConstruct, "axe", 0);
		MinecraftForge.setBlockHarvestLevel(TFCBlocks.Detailed, "pickaxe", 0);
		MinecraftForge.setBlockHarvestLevel(TFCBlocks.SuperDetailed, "pickaxe", 0);
		MinecraftForge.setBlockHarvestLevel(TFCBlocks.Detailed, "axe", 0);
		MinecraftForge.setBlockHarvestLevel(TFCBlocks.SuperDetailed, "axe", 0);
		MinecraftForge.setBlockHarvestLevel(Block.blocksList[53], "axe", 0);

		if (config != null) {
			config.save();
		}
	}
	
}