package TFC;

import java.io.File;

import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraft.src.Block;
import net.minecraft.src.BlockCactus;
import net.minecraft.src.BlockFlower;
import net.minecraft.src.BlockGrass;
import net.minecraft.src.BlockTallGrass;
import net.minecraft.src.Material;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import TFC.Blocks.*;
import TFC.Core.TFC_Settings;
import TFC.TileEntities.*;

public class TFCBlocks 
{
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
	public static int terraFirepitRenderId;
	public static int terraAnvilRenderId;
	public static int terraBellowsRenderId;
	public static int terraScribeRenderId;
	public static int terraForgeRenderId;
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
	public static int nestboxRenderId;
	public static int woodConstructRenderId;
	
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
	public static Block Leaves;
	public static Block Sapling;
	public static Block WoodSupportV;
	public static Block WoodSupportH;
	public static BlockGrass Grass;
	public static BlockGrass Grass2;
	public static Block Dirt;
	public static Block Dirt2;
	public static Block Clay;
	public static Block Clay2;
	public static BlockClayGrass ClayGrass;
	public static BlockClayGrass ClayGrass2;
	public static Block Peat;
	public static BlockPeatGrass PeatGrass;
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
	public static Block StoneDetailed;
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
	
	public static Block DoorOak;
	public static Block DoorAspen;
	public static Block DoorBirch;
	public static Block DoorChestnut;
	public static Block DoorDouglasFir;
	public static Block DoorHickory;
	public static Block DoorMaple;
	public static Block DoorAsh;
	public static Block DoorPine;
	public static Block DoorSequoia;
	public static Block DoorSpruce;
	public static Block DoorSycamore;
	public static Block DoorWhiteCedar;
	public static Block DoorWhiteElm;
	public static Block DoorWillow;
	public static Block DoorKapok;
	
	public static Block Nestbox;
	
	static Configuration config;
	
	public static void RegisterBlocks()
	{
		GameRegistry.registerBlock(Ore, TFC.Items.ItemOre1.class);
		GameRegistry.registerBlock(Ore2, TFC.Items.ItemOre2.class);
		GameRegistry.registerBlock(Ore3, TFC.Items.ItemOre3.class);
		GameRegistry.registerBlock(StoneIgIn, TFC.Items.ItemIgIn.class);
		GameRegistry.registerBlock(StoneIgEx, TFC.Items.ItemIgEx.class);
		GameRegistry.registerBlock(StoneSed, TFC.Items.ItemSed.class);
		GameRegistry.registerBlock(StoneMM, TFC.Items.ItemMM.class);

		GameRegistry.registerBlock(StoneIgInCobble, TFC.Items.ItemIgIn.class);
		GameRegistry.registerBlock(StoneIgExCobble, TFC.Items.ItemIgEx.class);
		GameRegistry.registerBlock(StoneSedCobble, TFC.Items.ItemSed.class);
		GameRegistry.registerBlock(StoneMMCobble, TFC.Items.ItemMM.class);
		GameRegistry.registerBlock(StoneIgInSmooth, TFC.Items.ItemIgIn.class);
		GameRegistry.registerBlock(StoneIgExSmooth, TFC.Items.ItemIgEx.class);
		GameRegistry.registerBlock(StoneSedSmooth, TFC.Items.ItemSed.class);
		GameRegistry.registerBlock(StoneMMSmooth, TFC.Items.ItemMM.class);
		GameRegistry.registerBlock(StoneIgInBrick, TFC.Items.ItemIgIn.class);
		GameRegistry.registerBlock(StoneIgExBrick, TFC.Items.ItemIgEx.class);
		GameRegistry.registerBlock(StoneSedBrick, TFC.Items.ItemSed.class);
		GameRegistry.registerBlock(StoneMMBrick, TFC.Items.ItemMM.class);

		GameRegistry.registerBlock(Dirt, TFC.Items.ItemDirt.class);
		GameRegistry.registerBlock(Dirt2, TFC.Items.ItemDirt.class);
		GameRegistry.registerBlock(Sand, TFC.Items.ItemSand.class);
		GameRegistry.registerBlock(Sand2, TFC.Items.ItemSand.class);
		GameRegistry.registerBlock(Clay);
		GameRegistry.registerBlock(Clay2);
		GameRegistry.registerBlock(Grass);
		GameRegistry.registerBlock(Grass2);
		GameRegistry.registerBlock(ClayGrass);
		GameRegistry.registerBlock(ClayGrass2);
		GameRegistry.registerBlock(PeatGrass);
		GameRegistry.registerBlock(Peat);
		GameRegistry.registerBlock(DryGrass);
		GameRegistry.registerBlock(DryGrass2);
		GameRegistry.registerBlock(LooseRock);
		GameRegistry.registerBlock(LogPile);
		GameRegistry.registerBlock(Charcoal);
		GameRegistry.registerBlock(StoneDetailed);
		
		GameRegistry.registerBlock(tilledSoil);
		GameRegistry.registerBlock(tilledSoil2);

		GameRegistry.registerBlock(finiteWater);

		GameRegistry.registerBlock(WoodSupportV);
		GameRegistry.registerBlock(WoodSupportH);
		GameRegistry.registerBlock(Sulfur);
		GameRegistry.registerBlock(Block.wood, TFC.Items.ItemCustomWood.class);
		GameRegistry.registerBlock(Block.leaves, TFC.Items.ItemCustomLeaves.class);
		GameRegistry.registerBlock(Block.sapling, TFC.Items.ItemSapling.class);
		GameRegistry.registerBlock(Block.planks, TFC.Items.ItemPlankBlock.class);

		GameRegistry.registerBlock(Firepit);
		GameRegistry.registerBlock(Bellows);
		GameRegistry.registerBlock(Anvil);
		GameRegistry.registerBlock(Scribe, TFC.Items.ItemTerraBlock.class);
		GameRegistry.registerBlock(Forge);
		GameRegistry.registerBlock(MetalTable, TFC.Items.ItemTerraBlock.class);
		GameRegistry.registerBlock(Molten);
		GameRegistry.registerBlock(Bloomery, TFC.Items.ItemTerraBlock.class);
		GameRegistry.registerBlock(Sluice);

		GameRegistry.registerBlock(fruitTreeWood);
		GameRegistry.registerBlock(fruitTreeLeaves);
		GameRegistry.registerBlock(fruitTreeLeaves2);

		GameRegistry.registerBlock(stoneStairs);
		GameRegistry.registerBlock(stoneSlabs);
		GameRegistry.registerBlock(stoneStalac);
		
		GameRegistry.registerBlock(WoodConstruct);
		GameRegistry.registerBlock(WoodVert);
		GameRegistry.registerBlock(WoodHoriz);
		GameRegistry.registerBlock(WoodHoriz2);
		
		GameRegistry.registerBlock(ToolRack, TFC.Items.ItemToolRack.class);
		GameRegistry.registerBlock(SpawnMeter);
		GameRegistry.registerBlock(FoodPrep);
		GameRegistry.registerBlock(Quern);
		GameRegistry.registerBlock(WallCobbleIgIn, TFC.Items.ItemIgIn.class);
		GameRegistry.registerBlock(WallCobbleIgEx, TFC.Items.ItemIgEx.class);
		GameRegistry.registerBlock(WallCobbleSed, TFC.Items.ItemSed.class);
		GameRegistry.registerBlock(WallCobbleMM, TFC.Items.ItemMM.class);
		GameRegistry.registerBlock(WallRawIgIn, TFC.Items.ItemIgIn.class);
		GameRegistry.registerBlock(WallRawIgEx, TFC.Items.ItemIgEx.class);
		GameRegistry.registerBlock(WallRawSed, TFC.Items.ItemSed.class);
		GameRegistry.registerBlock(WallRawMM, TFC.Items.ItemMM.class);
		GameRegistry.registerBlock(WallBrickIgIn, TFC.Items.ItemIgIn.class);
		GameRegistry.registerBlock(WallBrickIgEx, TFC.Items.ItemIgEx.class);
		GameRegistry.registerBlock(WallBrickSed, TFC.Items.ItemSed.class);
		GameRegistry.registerBlock(WallBrickMM, TFC.Items.ItemMM.class);
		GameRegistry.registerBlock(WallSmoothIgIn, TFC.Items.ItemIgIn.class);
		GameRegistry.registerBlock(WallSmoothIgEx, TFC.Items.ItemIgEx.class);
		GameRegistry.registerBlock(WallSmoothSed, TFC.Items.ItemSed.class);
		GameRegistry.registerBlock(WallSmoothMM, TFC.Items.ItemMM.class);
		GameRegistry.registerBlock(DoorOak);
		GameRegistry.registerBlock(DoorAspen);
		GameRegistry.registerBlock(DoorBirch);
		GameRegistry.registerBlock(DoorChestnut);
		GameRegistry.registerBlock(DoorDouglasFir);
		GameRegistry.registerBlock(DoorHickory);
		GameRegistry.registerBlock(DoorMaple);
		GameRegistry.registerBlock(DoorAsh);
		GameRegistry.registerBlock(DoorPine);
		GameRegistry.registerBlock(DoorSequoia);
		GameRegistry.registerBlock(DoorSpruce);
		GameRegistry.registerBlock(DoorSycamore);
		GameRegistry.registerBlock(DoorWhiteCedar);
		GameRegistry.registerBlock(DoorWhiteElm);
		GameRegistry.registerBlock(DoorWillow);
		GameRegistry.registerBlock(DoorKapok);
		GameRegistry.registerBlock(Nestbox);
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

		TFCBlocks.StoneIgInCobble = new BlockIgInCobble(TFC_Settings.getIntFor(config,"block","StoneIgInCobble", 198), Material.rock).setHardness(13F).setResistance(10F).setBlockName("IgInRockCobble");
		TFCBlocks.StoneIgIn = new BlockIgIn(TFC_Settings.getIntFor(config,"block","StoneIgIn", 209), Material.rock, TFCBlocks.StoneIgInCobble.blockID).setHardness(13F).setResistance(10F).setBlockName("IgInRock");	
		TFCBlocks.StoneIgInSmooth = new BlockIgInSmooth(TFC_Settings.getIntFor(config,"block","StoneIgInSmooth", 182), 48).setHardness(13F).setResistance(20F).setBlockName("IgInRockSmooth");
		TFCBlocks.StoneIgInBrick = new BlockIgInSmooth(TFC_Settings.getIntFor(config,"block","StoneIgInBrick", 186), 32).setHardness(13F).setResistance(15F).setBlockName("IgInRockBrick");

		TFCBlocks.StoneSedCobble = new BlockSedCobble(TFC_Settings.getIntFor(config,"block","StoneSedCobble", 199), Material.rock).setHardness(10F).setResistance(10F).setBlockName("SedRockCobble");
		TFCBlocks.StoneSed = new BlockSed(TFC_Settings.getIntFor(config,"block","StoneSed", 210), Material.rock, TFCBlocks.StoneSedCobble.blockID).setHardness(10F).setResistance(7F).setBlockName("SedRock");
		TFCBlocks.StoneSedSmooth = new BlockSedSmooth(TFC_Settings.getIntFor(config,"block","StoneSedSmooth", 183), 112).setHardness(10F).setResistance(20F).setBlockName("SedRockSmooth");
		TFCBlocks.StoneSedBrick = new BlockSedSmooth(TFC_Settings.getIntFor(config,"block","StoneSedBrick", 187), 96).setHardness(10F).setResistance(15F).setBlockName("SedRockBrick");

		TFCBlocks.StoneIgExCobble = new BlockIgExCobble(TFC_Settings.getIntFor(config,"block","StoneIgExCobble", 200), Material.rock).setHardness(13F).setResistance(10F).setBlockName("IgExRockCobble");
		TFCBlocks.StoneIgEx = new BlockIgEx(TFC_Settings.getIntFor(config,"block","StoneIgEx", 211), Material.rock, TFCBlocks.StoneIgExCobble.blockID).setHardness(13F).setResistance(10F).setBlockName("IgExRock");
		TFCBlocks.StoneIgExSmooth = new BlockIgExSmooth(TFC_Settings.getIntFor(config,"block","StoneIgExSmooth", 184), 51).setHardness(13F).setResistance(20F).setBlockName("IgExRockSmooth");
		TFCBlocks.StoneIgExBrick = new BlockIgExSmooth(TFC_Settings.getIntFor(config,"block","StoneIgExBrick", 188), 35).setHardness(13F).setResistance(15F).setBlockName("IgExRockBrick");

		TFCBlocks.StoneMMCobble = new BlockMMCobble(TFC_Settings.getIntFor(config,"block","StoneMMCobble", 201), Material.rock).setHardness(10F).setResistance(10F).setBlockName("MMRockCobble");
		TFCBlocks.StoneMM = new BlockMM(TFC_Settings.getIntFor(config,"block","StoneMM", 212), Material.rock, TFCBlocks.StoneMMCobble.blockID).setHardness(10F).setResistance(8F).setBlockName("MMRock");
		TFCBlocks.StoneMMSmooth = new BlockMMSmooth(TFC_Settings.getIntFor(config,"block","StoneMMSmooth", 185), 122).setHardness(10F).setResistance(20F).setBlockName("MMRockSmooth");
		TFCBlocks.StoneMMBrick = new BlockMMSmooth(TFC_Settings.getIntFor(config,"block","StoneMMBrick", 189), 106).setHardness(10F).setResistance(15F).setBlockName("MMRockBrick");

		TFCBlocks.Dirt = (new TFC.Blocks.BlockDirt(TFC_Settings.getIntFor(config,"block","Dirt", 190), 112,TFCBlocks.tilledSoil)).setHardness(2F).setStepSound(Block.soundGravelFootstep).setBlockName("dirt");
		TFCBlocks.Dirt2 = (new TFC.Blocks.BlockDirt2(TFC_Settings.getIntFor(config,"block","Dirt2", 191), 128,TFCBlocks.tilledSoil2)).setHardness(2F).setStepSound(Block.soundGravelFootstep).setBlockName("dirt");
		TFCBlocks.Clay = (new TFC.Blocks.BlockClay(TFC_Settings.getIntFor(config,"block","Clay", 192), 144)).setHardness(3F).setStepSound(Block.soundGravelFootstep).setBlockName("clay");
		TFCBlocks.Clay2 = (new TFC.Blocks.BlockClay2(TFC_Settings.getIntFor(config,"block","Clay2", 193), 160)).setHardness(3F).setStepSound(Block.soundGravelFootstep).setBlockName("clay");
		TFCBlocks.ClayGrass = (BlockClayGrass) (new TFC.Blocks.BlockClayGrass(TFC_Settings.getIntFor(config,"block","ClayGrass", 194), 144)).setHardness(3F).setStepSound(Block.soundGrassFootstep).setBlockName("ClayGrass"); 
		TFCBlocks.ClayGrass2 = (BlockClayGrass) (new TFC.Blocks.BlockClayGrass(TFC_Settings.getIntFor(config,"block","ClayGrass2", 195), 160)).setHardness(3F).setStepSound(Block.soundGrassFootstep).setBlockName("ClayGrass"); 
		TFCBlocks.Grass = (TFC.Blocks.BlockGrass)(new TFC.Blocks.BlockGrass(TFC_Settings.getIntFor(config,"block","Grass", 196), 112)).setHardness(2F).setStepSound(Block.soundGrassFootstep).setBlockName("Grass");
		TFCBlocks.Grass2 = (TFC.Blocks.BlockGrass)(new TFC.Blocks.BlockGrass(TFC_Settings.getIntFor(config,"block","Grass2", 197), 128)).setHardness(2F).setStepSound(Block.soundGrassFootstep).setBlockName("Grass");  
		TFCBlocks.Peat = (new TFC.Blocks.BlockPeat(TFC_Settings.getIntFor(config,"block","Peat", 180), 135)).setHardness(3F).setStepSound(Block.soundGravelFootstep).setBlockName("peat");
		TFCBlocks.PeatGrass = (BlockPeatGrass)(new TFC.Blocks.BlockPeatGrass(TFC_Settings.getIntFor(config,"block","PeatGrass", 181), 135)).setHardness(3F).setStepSound(Block.soundGrassFootstep).setBlockName("PeatGrass");
		TFCBlocks.DryGrass = (new BlockDryGrass(TFC_Settings.getIntFor(config,"block","DryGrass", 218), 112)).setHardness(3F).setStepSound(Block.soundGrassFootstep).setBlockName("Grass");
		TFCBlocks.DryGrass2 =(new BlockDryGrass(TFC_Settings.getIntFor(config,"block","DryGrass2", 219), 128)).setHardness(3F).setStepSound(Block.soundGrassFootstep).setBlockName("Grass");  

		TFCBlocks.Ore = new TFC.Blocks.BlockOre(TFC_Settings.getIntFor(config,"block","Ore", 213), Material.rock).setHardness(10F).setResistance(10F).setBlockName("Ore");
		TFCBlocks.Ore2 = new BlockOre2(TFC_Settings.getIntFor(config,"block","Ore2", 214), Material.rock).setHardness(10F).setResistance(10F).setBlockName("Ore");
		TFCBlocks.Ore3 = new BlockOre3(TFC_Settings.getIntFor(config,"block","Ore3", 215), Material.rock).setHardness(10F).setResistance(10F).setBlockName("Ore");
		TFCBlocks.LooseRock = new BlockLooseRock(TFC_Settings.getIntFor(config,"block","LooseRock", 179)).setHardness(0.05F).setResistance(1F).setBlockName("LooseRock");
		TFCBlocks.LogPile = new BlockLogPile(TFC_Settings.getIntFor(config,"block","LogPile", 178), TileEntityTerraLogPile.class).setHardness(10F).setResistance(1F).setBlockName("LogPile");

		TFCBlocks.Sulfur = new BlockSulfur(TFC_Settings.getIntFor(config,"block","Sulfur", 208), Material.rock).setBlockName("Sulfur").setHardness(0.5F).setResistance(1F);
		TFCBlocks.WoodSupportV = new BlockWoodSupport(TFC_Settings.getIntFor(config,"block","WoodSupportV", 203), Material.wood).setBlockName("WoodSupportV").setHardness(0.5F).setResistance(1F);
		TFCBlocks.WoodSupportH = new BlockWoodSupport(TFC_Settings.getIntFor(config,"block","WoodSupportH", 202), Material.wood).setBlockName("WoodSupportH").setHardness(0.5F).setResistance(1F);

		TFCBlocks.tilledSoil = new TFC.Blocks.BlockFarmland(TFC_Settings.getIntFor(config,"block","tilledSoil", 177), TFCBlocks.Dirt.blockID, 176).setHardness(2F).setStepSound(Block.soundGravelFootstep).setBlockName("tilledSoil");
		TFCBlocks.tilledSoil2 = new TFC.Blocks.BlockFarmland(TFC_Settings.getIntFor(config,"block","tilledSoil2", 176), TFCBlocks.Dirt2.blockID, 192).setHardness(2F).setStepSound(Block.soundGravelFootstep).setBlockName("tilledSoil2");

		TFCBlocks.fruitTreeWood = new BlockFruitWood(TFC_Settings.getIntFor(config,"block","fruitTreeWood", 175), 0, TileEntityFruitTreeWood.class).setBlockName("fruitTreeWood").setHardness(5.5F).setResistance(2F);
		TFCBlocks.fruitTreeLeaves = new BlockFruitLeaves(TFC_Settings.getIntFor(config,"block","fruitTreeLeaves", 174), 48).setBlockName("fruitTreeLeaves").setHardness(0.5F).setResistance(1F).setStepSound(Block.soundGrassFootstep);
		TFCBlocks.fruitTreeLeaves2 = new BlockFruitLeaves(TFC_Settings.getIntFor(config,"block","fruitTreeLeaves2", 173), 56).setBlockName("fruitTreeLeaves2").setHardness(0.5F).setResistance(1F).setStepSound(Block.soundGrassFootstep);

		TFCBlocks.Sand = new TFC.Blocks.BlockSand(TFC_Settings.getIntFor(config,"block","Sand", 216), 208).setHardness(0.5F).setStepSound(Block.soundSandFootstep).setBlockName("sand");
		TFCBlocks.Sand2 = new BlockSand2(TFC_Settings.getIntFor(config,"block","Sand2", 217), 224).setHardness(0.5F).setStepSound(Block.soundSandFootstep).setBlockName("sand");
		
		TFCBlocks.WoodConstruct = (new TFC.Blocks.BlockWoodConstruct(TFC_Settings.getIntFor(config,"block","WoodConstruct", 2200))).setHardness(4F).setStepSound(Block.soundWoodFootstep).setBlockName("WoodConstruct");
		
		
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
		Block.blocksList[53] = null;
		Block.blocksList[54] = null;
		Block.blocksList[58] = null;
		Block.blocksList[59] = null;
		Block.blocksList[78] = null;
		Block.blocksList[79] = null;
		Block.blocksList[81] = null;
		Block.blocksList[83] = null;
		Block.blocksList[106] = null;

		Block.blocksList[5] = (new TFC.Blocks.BlockPlanks(5, Material.wood)).setHardness(4.0F).setResistance(5.0F).setStepSound(Block.soundWoodFootstep).setBlockName("wood").setRequiresSelfNotify();
		Block.blocksList[6] = (new BlockCustomSapling(6, 160)).setHardness(0.0F).setStepSound(Block.soundGrassFootstep).setBlockName("sapling").setRequiresSelfNotify();
		Block.blocksList[8] = (new BlockCustomFlowing(8, Material.water)).setHardness(100.0F).setLightOpacity(3).setBlockName("water").setRequiresSelfNotify();
		Block.blocksList[9] = (new BlockCustomStationary(9, Material.water)).setHardness(100.0F).setLightOpacity(3).setBlockName("water").setRequiresSelfNotify();
		Block.blocksList[10] = (new BlockCustomFlowing(10, Material.lava)).setHardness(0.0F).setLightValue(1.0F).setLightOpacity(255).setBlockName("lava").setRequiresSelfNotify();
		Block.blocksList[17] = (new TFC.Blocks.BlockLogNatural(17)).setHardness(50.0F).setStepSound(Block.soundWoodFootstep).setBlockName("log").setRequiresSelfNotify();
		Block.blocksList[18] = (new BlockCustomLeaves(18, 96)).setHardness(0.2F).setLightOpacity(1).setStepSound(Block.soundGrassFootstep).setBlockName("leaves").setRequiresSelfNotify();
		Block.blocksList[31] = (BlockTallGrass)(new BlockCustomTallGrass(31, 39)).setHardness(0.0F).setStepSound(Block.soundGrassFootstep).setBlockName("tallgrass");
		Block.blocksList[37] = (BlockFlower)(new BlockCustomFlower(37, 13)).setHardness(0.0F).setStepSound(Block.soundGrassFootstep).setBlockName("flower");
		Block.blocksList[38] = (BlockFlower)(new BlockCustomFlower(38, 12)).setHardness(0.0F).setStepSound(Block.soundGrassFootstep).setBlockName("rose");
		Block.blocksList[39] = (BlockFlower)(new BlockCustomMushroom(39, 29)).setHardness(0.0F).setStepSound(Block.soundGrassFootstep).setLightValue(0.125F).setBlockName("mushroom");
		Block.blocksList[40] = (BlockFlower)(new BlockCustomMushroom(40, 28)).setHardness(0.0F).setStepSound(Block.soundGrassFootstep).setBlockName("mushroom");
		Block.blocksList[53] = (new BlockStair(53, Material.wood)).setBlockName("stairsWood").setRequiresSelfNotify();
		Block.blocksList[54] = (new BlockChestTFC(54)).setHardness(2.5F).setStepSound(Block.soundWoodFootstep).setBlockName("chest").setRequiresSelfNotify();
		Block.blocksList[58] = (new TFC.Blocks.BlockWorkbench(58)).setHardness(2.5F).setStepSound(Block.soundWoodFootstep).setBlockName("workbench");
		Block.blocksList[59] = (new BlockCrop(59, 88)).setHardness(0.3F).setStepSound(Block.soundGrassFootstep).setBlockName("crops").setRequiresSelfNotify();
		Block.blocksList[78] = (new BlockCustomSnow(78, 66)).setHardness(0.1F).setStepSound(Block.soundClothFootstep).setBlockName("snow").setLightOpacity(1);
		Block.blocksList[79] = (new BlockCustomIce(79, 67)).setHardness(0.5F).setLightOpacity(3).setStepSound(Block.soundGlassFootstep).setBlockName("ice");
		Block.blocksList[81] = (new BlockCustomCactus(81, 70)).setHardness(0.4F).setStepSound(Block.soundClothFootstep).setBlockName("cactus");
		Block.blocksList[83] = (new BlockCustomReed(83, 73)).setHardness(0.0F).setStepSound(Block.soundGrassFootstep).setBlockName("reeds");
		Block.blocksList[106] = (new BlockCustomVine(106)).setHardness(0.2F).setStepSound(Block.soundGrassFootstep).setBlockName("vine").setRequiresSelfNotify();


		TFCBlocks.Wood = Block.blocksList[17];
		TFCBlocks.Leaves = Block.blocksList[18];
		TFCBlocks.Sapling = Block.blocksList[6];

		TFCBlocks.finiteWater = new BlockFiniteWater(TFC_Settings.getIntFor(config,"block","bucketWater", 224)).setHardness(100.0F).setLightOpacity(3).setRequiresSelfNotify().setBlockName("bucketWater");

		TFCBlocks.Firepit = new BlockFirepit(TFC_Settings.getIntFor(config,"block","Firepit", 2015), 80).setBlockName("Firepit").setHardness(1).setLightValue(0F);
		TFCBlocks.Bellows = new BlockBellows(TFC_Settings.getIntFor(config,"block","Bellows", 2014),Material.wood).setBlockName("Bellows").setHardness(2);
		TFCBlocks.Forge= new BlockForge(TFC_Settings.getIntFor(config,"block","Forge", 2013), 90).setBlockName("Forge").setHardness(20).setLightValue(0F);
		TFCBlocks.Scribe = new BlockScribe(TFC_Settings.getIntFor(config,"block","Scribe", 2012)).setBlockName("Scribe").setHardness(2);
		TFCBlocks.Anvil = new TFC.Blocks.BlockAnvil(TFC_Settings.getIntFor(config,"block","Anvil", 2011),192).setBlockName("Anvil").setHardness(3);
		TFCBlocks.Anvil2 = new TFC.Blocks.BlockAnvil(TFC_Settings.getIntFor(config,"block","Anvil2", 2010),208).setBlockName("Anvil2").setHardness(3);

		TFCBlocks.MetalTable = new BlockMetallurgy(TFC_Settings.getIntFor(config,"block","Metallurgy", 2009)).setBlockName("Metallurgy").setHardness(3);
		TFCBlocks.Molten = new BlockMolten(TFC_Settings.getIntFor(config,"block","Molten", 2008)).setBlockName("Molten").setHardness(20);
		TFCBlocks.Bloomery = new BlockBloomery(TFC_Settings.getIntFor(config,"block","Bloomery", 2007), 65).setBlockName("Bloomery").setHardness(20).setLightValue(0F);
		TFCBlocks.Sluice = new BlockSluice(TFC_Settings.getIntFor(config,"block","Sluice", 2003)).setBlockName("Sluice").setHardness(2F).setResistance(20F);

		TFCBlocks.stoneStairs = new BlockStair(TFC_Settings.getIntFor(config,"block","stoneStairs", 2000), Material.rock).setBlockName("stoneStairs").setRequiresSelfNotify().setHardness(10).setResistance(15F);
		TFCBlocks.stoneSlabs = new BlockSlab(TFC_Settings.getIntFor(config,"block","stoneSlabs", 2001)).setBlockName("stoneSlabs").setRequiresSelfNotify().setHardness(10).setResistance(15F);
		TFCBlocks.stoneStalac = new BlockStalactite(TFC_Settings.getIntFor(config,"block","stoneStalac", 2002)).setBlockName("stoneStalac").setRequiresSelfNotify().setHardness(5);
		
		Charcoal = new BlockCharcoal(TFC_Settings.getIntFor(config,"block","Charcoal", 2016)).setHardness(3F).setResistance(10F).setBlockName("Charcoal");
		
		StoneDetailed = new BlockDetailed(TFC_Settings.getIntFor(config,"block","StoneDetailed", 2017)).setBlockName("StoneDetailed").setHardness(10).setResistance(15F);
		
		WoodVert = new BlockLogVert(TFC_Settings.getIntFor(config,"block","WoodVert", 2018)).setBlockName("WoodVert").setHardness(40).setResistance(15F);
		WoodHoriz = new BlockLogHoriz(TFC_Settings.getIntFor(config,"block","WoodHoriz", 2019), 0).setBlockName("WoodHoriz").setHardness(40).setResistance(15F);
		WoodHoriz2 = new BlockLogHoriz(TFC_Settings.getIntFor(config,"block","WoodHoriz2", 2020), 8).setBlockName("WoodHoriz2").setHardness(40).setResistance(15F);
		
		ToolRack = new BlockToolRack(TFC_Settings.getIntFor(config,"block","ToolRack", 2021)).setHardness(3F).setBlockName("ToolRack");
		SpawnMeter = new BlockSpawnMeter(TFC_Settings.getIntFor(config,"block","SpawnMeter", 2022)).setHardness(3F).setBlockName("SpawnMeter");
		FoodPrep = new BlockFoodPrep(TFC_Settings.getIntFor(config,"block","FoodPrep", 2023)).setHardness(1F).setBlockName("FoodPrep");
		Quern = new BlockQuern(TFC_Settings.getIntFor(config,"block","Quern", 2024)).setHardness(3F).setBlockName("Quern");
		
		WallCobbleIgIn = new BlockCustomWall(TFC_Settings.getIntFor(config,"block","WallCobbleIgIn", 2025), StoneIgInCobble, 3).setBlockName("WallCobble");
		WallCobbleIgEx = new BlockCustomWall(TFC_Settings.getIntFor(config,"block","WallCobbleIgEx", 2026), StoneIgExCobble, 4).setBlockName("WallCobble");
		WallCobbleSed = new BlockCustomWall(TFC_Settings.getIntFor(config,"block","WallCobbleSed", 2027), StoneSedCobble, 10).setBlockName("WallCobble");
		WallCobbleMM = new BlockCustomWall(TFC_Settings.getIntFor(config,"block","WallCobbleMM", 2028), StoneMMCobble, 6).setBlockName("WallCobble");
		WallRawIgIn = new BlockCustomWall(TFC_Settings.getIntFor(config,"block","WallRawIgIn", 2029), StoneIgIn, 3).setBlockName("WallRaw");
		WallRawIgEx = new BlockCustomWall(TFC_Settings.getIntFor(config,"block","WallRawIgEx", 2030), StoneIgEx, 4).setBlockName("WallRaw");
		WallRawSed = new BlockCustomWall(TFC_Settings.getIntFor(config,"block","WallRawSed", 2031), StoneSed, 10).setBlockName("WallRaw");
		WallRawMM = new BlockCustomWall(TFC_Settings.getIntFor(config,"block","WallRawMM", 2032), StoneMM, 6).setBlockName("WallRaw");
		WallBrickIgIn = new BlockCustomWall(TFC_Settings.getIntFor(config,"block","WallBrickIgIn", 2033), StoneIgInBrick, 3).setBlockName("WallBrick");
		WallBrickIgEx = new BlockCustomWall(TFC_Settings.getIntFor(config,"block","WallBrickIgEx", 2034), StoneIgExBrick, 4).setBlockName("WallBrick");
		WallBrickSed = new BlockCustomWall(TFC_Settings.getIntFor(config,"block","WallBrickSed", 2035), StoneSedBrick, 10).setBlockName("WallBrick");
		WallBrickMM = new BlockCustomWall(TFC_Settings.getIntFor(config,"block","WallBrickMM", 2036), StoneMMBrick, 6).setBlockName("WallBrick");
		WallSmoothIgIn = new BlockCustomWall(TFC_Settings.getIntFor(config,"block","WallSmoothIgIn", 2037), StoneIgInSmooth, 3).setBlockName("WallSmooth");
		WallSmoothIgEx = new BlockCustomWall(TFC_Settings.getIntFor(config,"block","WallSmoothIgEx", 2038), StoneIgExSmooth, 4).setBlockName("WallSmooth");
		WallSmoothSed = new BlockCustomWall(TFC_Settings.getIntFor(config,"block","WallSmoothSed", 2039), StoneSedSmooth, 10).setBlockName("WallSmooth");
		WallSmoothMM = new BlockCustomWall(TFC_Settings.getIntFor(config,"block","WallSmoothMM", 2040), StoneMMSmooth, 6).setBlockName("WallSmooth");
		
		DoorOak = new BlockCustomDoor(TFC_Settings.getIntFor(config,"block","DoorOak", 2041), Material.wood, 0).setBlockName("Door Oak");
		DoorAspen = new BlockCustomDoor(TFC_Settings.getIntFor(config,"block","DoorAspen", 2042), Material.wood, 1).setBlockName("Door Aspen");
		DoorBirch = new BlockCustomDoor(TFC_Settings.getIntFor(config,"block","DoorBirch", 2043), Material.wood, 2).setBlockName("Door Birch");
		DoorChestnut = new BlockCustomDoor(TFC_Settings.getIntFor(config,"block","DoorChestnut", 2044), Material.wood, 3).setBlockName("Door Chestnut");
		DoorDouglasFir = new BlockCustomDoor(TFC_Settings.getIntFor(config,"block","DoorDouglasFir", 2045), Material.wood, 4).setBlockName("Door Douglas Fir");
		DoorHickory = new BlockCustomDoor(TFC_Settings.getIntFor(config,"block","DoorHickory", 2046), Material.wood, 5).setBlockName("Door Hickory");
		DoorMaple = new BlockCustomDoor(TFC_Settings.getIntFor(config,"block","DoorMaple", 2047), Material.wood, 6).setBlockName("Door Maple");
		DoorAsh = new BlockCustomDoor(TFC_Settings.getIntFor(config,"block","DoorAsh", 2048), Material.wood, 7).setBlockName("Door Ash");
		DoorPine = new BlockCustomDoor(TFC_Settings.getIntFor(config,"block","DoorPine", 2049), Material.wood, 8).setBlockName("Door Pine");
		DoorSequoia = new BlockCustomDoor(TFC_Settings.getIntFor(config,"block","DoorSequoia", 2050), Material.wood, 9).setBlockName("Door Sequoia");
		DoorSpruce = new BlockCustomDoor(TFC_Settings.getIntFor(config,"block","DoorSpruce", 2051), Material.wood, 10).setBlockName("Door Spruce");
		DoorSycamore = new BlockCustomDoor(TFC_Settings.getIntFor(config,"block","DoorSycamore", 2052), Material.wood, 11).setBlockName("Door Sycamore");
		DoorWhiteCedar = new BlockCustomDoor(TFC_Settings.getIntFor(config,"block","DoorWhiteCedar", 2053), Material.wood, 12).setBlockName("Door White Cedar");
		DoorWhiteElm = new BlockCustomDoor(TFC_Settings.getIntFor(config,"block","DoorWhiteElm", 2054), Material.wood, 13).setBlockName("Door White Elm");
		DoorWillow = new BlockCustomDoor(TFC_Settings.getIntFor(config,"block","DoorWillow", 2055), Material.wood, 14).setBlockName("Door Willow");
		DoorKapok = new BlockCustomDoor(TFC_Settings.getIntFor(config,"block","DoorKapok", 2056), Material.wood, 15).setBlockName("Door Kapok");
		
		Nestbox = new BlockNestBox(TFC_Settings.getIntFor(config,"block","NestBox", 2057)).setBlockName("NestBox");
		

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
		MinecraftForge.setBlockHarvestLevel(Charcoal, "shovel", 0);
		MinecraftForge.setBlockHarvestLevel(TFCBlocks.WoodConstruct, "axe", 0);

		if (config != null) {
			config.save();
		}
	}
	
}