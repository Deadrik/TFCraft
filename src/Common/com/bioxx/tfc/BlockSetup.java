package com.bioxx.tfc;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import cpw.mods.fml.common.registry.GameRegistry;

import com.bioxx.tfc.Blocks.*;
import com.bioxx.tfc.Blocks.Devices.*;
import com.bioxx.tfc.Blocks.Flora.*;
import com.bioxx.tfc.Blocks.Liquids.*;
import com.bioxx.tfc.Blocks.Terrain.*;
import com.bioxx.tfc.Blocks.Vanilla.*;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Items.ItemBlocks.*;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCFluids;

public class BlockSetup extends TFCBlocks
{
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
		GameRegistry.registerBlock(WoodHoriz, com.bioxx.tfc.Items.ItemBlocks.ItemCustomWoodH.class, "WoodHoriz");
		GameRegistry.registerBlock(WoodHoriz2, com.bioxx.tfc.Items.ItemBlocks.ItemCustomWoodH2.class, "WoodHoriz2");
		GameRegistry.registerBlock(WoodHoriz3, com.bioxx.tfc.Items.ItemBlocks.ItemCustomWoodH3.class, "WoodHoriz3");
		GameRegistry.registerBlock(WoodHoriz4, "WoodHoriz4");

		GameRegistry.registerBlock(ToolRack, ItemToolRack.class, "ToolRack");
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
		GameRegistry.registerBlock(LitPumpkin, com.bioxx.tfc.Items.ItemBlocks.ItemTerraBlock.class, "LitPumpkin");
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
		GameRegistry.registerBlock(OilLamp, ItemOilLamp.class, "OilLamp");
		GameRegistry.registerBlock(Hopper, "Hopper");
		GameRegistry.registerBlock(FlowerPot, "FlowerPot");
	}

	public static void LoadBlocks()
	{
		TerraFirmaCraft.log.info(new StringBuilder().append("Loading Blocks").toString());

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
		Blocks.lit_pumpkin.setCreativeTab(null);
		Blocks.wooden_button.setCreativeTab(null);
		Blocks.ice.setCreativeTab(null);
		Blocks.vine.setCreativeTab(null);
		Blocks.flower_pot.setCreativeTab(null);

		Bookshelf = new BlockCustomBookshelf().setHardness(1.5F).setStepSound(Block.soundTypeWood).setBlockName("Bookshelf").setBlockTextureName("bookshelf");
		Torch = new BlockTorch().setHardness(0.0F).setStepSound(Block.soundTypeWood).setBlockName("Torch").setBlockTextureName("torch_on");
		Chest = new BlockChestTFC().setHardness(2.5F).setStepSound(Block.soundTypeWood).setBlockName("Chest");
		Workbench = new BlockWorkbench().setHardness(2.5F).setStepSound(Block.soundTypeWood).setBlockName("Workbench").setBlockTextureName("crafting_table");
		Cactus = new BlockCustomCactus().setHardness(0.4F).setStepSound(Block.soundTypeCloth).setBlockName("Cactus").setBlockTextureName("cactus");
		Reeds = new BlockCustomReed().setHardness(0.0F).setStepSound(Block.soundTypeGrass).setBlockName("Reeds").setBlockTextureName("reeds");
		Pumpkin = new BlockCustomPumpkin(false).setHardness(1.0F).setStepSound(Block.soundTypeWood).setBlockName("Pumpkin").setBlockTextureName("pumpkin");
		LitPumpkin = new BlockCustomPumpkin(true).setHardness(1.0F).setStepSound(Block.soundTypeWood).setLightLevel(1.0F).setBlockName("LitPumpkin").setBlockTextureName("pumpkin");
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
		StoneIgInSmooth = new BlockIgInSmooth().setHardness(16F).setBlockName("IgInRockSmooth");
		StoneIgInBrick = new BlockIgInBrick().setHardness(16F).setBlockName("IgInRockBrick");

		StoneSedCobble = new BlockSedCobble(Material.rock).setHardness(14F).setBlockName("SedRockCobble");
		StoneSed = new BlockSed(Material.rock).setHardness(7F).setBlockName("SedRock");
		StoneSedSmooth = new BlockSedSmooth().setHardness(14F).setBlockName("SedRockSmooth");
		StoneSedBrick = new BlockSedBrick().setHardness(14F).setBlockName("SedRockBrick");

		StoneIgExCobble = new BlockIgExCobble(Material.rock).setHardness(16F).setBlockName("IgExRockCobble");
		StoneIgEx = new BlockIgEx(Material.rock).setHardness(8F).setBlockName("IgExRock");
		StoneIgExSmooth = new BlockIgExSmooth().setHardness(16F).setBlockName("IgExRockSmooth");
		StoneIgExBrick = new BlockIgExBrick().setHardness(16F).setBlockName("IgExRockBrick");

		StoneMMCobble = new BlockMMCobble(Material.rock).setHardness(15F).setBlockName("MMRockCobble");
		StoneMM = new BlockMM(Material.rock).setHardness(8F).setBlockName("MMRock");
		StoneMMSmooth = new BlockMMSmooth().setHardness(15F).setBlockName("MMRockSmooth");
		StoneMMBrick = new BlockMMBrick().setHardness(15F).setBlockName("MMRockBrick");

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
		Leaves = (new BlockCustomLeaves()).setHardness(0.2F).setLightOpacity(1).setStepSound(Block.soundTypeGrass).setBlockName("leaves").setCreativeTab(TFCTabs.TFCDecoration);
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
		//Use 8 instead of 0 if Global.WOOD_ALL.length > 24
		WoodHoriz4 = new BlockLogHoriz2(/*8*/0).setBlockName("WoodHoriz4").setHardness(20).setResistance(15F).setStepSound(Block.soundTypeWood);

		ToolRack = new BlockToolRack().setHardness(3F).setBlockName("Toolrack");
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
		StrawHideBed = new BlockBed().setBlockName("StrawHideBed").setHardness(1).setCreativeTab(null);
		ArmourStand = new BlockStand().setBlockName("ArmourStand").setHardness(2);
		ArmourStand2 = new BlockStand2().setBlockName("ArmourStand").setHardness(2);

		BerryBush = new BlockBerryBush().setBlockName("BerryBush").setHardness(0.3f).setStepSound(Block.soundTypeGrass);
		Crops = new BlockCrop().setBlockName("crops").setHardness(0.3F).setStepSound(Block.soundTypeGrass);
		LilyPad = new BlockCustomLilyPad().setHardness(0.0F).setStepSound(Block.soundTypeGrass).setBlockName("LilyPad").setBlockTextureName("waterlily");
		Flowers = new BlockFlower().setHardness(0.0F).setStepSound(Block.soundTypeGrass).setBlockName("Flowers");
		Flowers2 = new BlockFlower2().setHardness(0.0F).setStepSound(Block.soundTypeGrass).setBlockName("Flowers2");
		Fungi = new BlockFungi().setHardness(0.0F).setStepSound(Block.soundTypeGrass).setBlockName("Fungi");

		SaltWater = (new BlockSaltWater(TFCFluids.SALTWATER)).setHardness(100.0F).setLightOpacity(3).setBlockName("SaltWater");
		SaltWaterStationary = (new BlockLiquidStatic(TFCFluids.SALTWATER, Material.water, SaltWater)).setHardness(100.0F).setLightOpacity(3).setBlockName("SaltWaterStationary");

		FreshWater = (new BlockFreshWater(TFCFluids.FRESHWATER)).setHardness(100.0F).setLightOpacity(3).setBlockName("FreshWater");
		FreshWaterStationary = (new BlockLiquidStatic(TFCFluids.FRESHWATER, Material.water, FreshWater)).setHardness(100.0F).setLightOpacity(3).setBlockName("FreshWaterStationary");

		HotWater = (new BlockHotWater(TFCFluids.HOTWATER)).setHardness(100.0F).setLightOpacity(3).setBlockName("HotWater");
		HotWaterStationary = (new BlockHotWaterStatic(TFCFluids.HOTWATER, Material.water, HotWater)).setHardness(100.0F).setLightOpacity(3).setBlockName("HotWaterStationary");

		Lava = (new BlockLava()).setHardness(0.0F).setLightLevel(1.0F).setLightOpacity(255).setBlockName("Lava");
		LavaStationary = (new BlockLiquidStatic(TFCFluids.LAVA, Material.lava, Lava)).setHardness(0.0F).setLightLevel(1.0F).setLightOpacity(255).setBlockName("LavaStationary");
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

		OilLamp = new BlockOilLamp().setHardness(1F).setBlockName("OilLamp");
		Hopper = new BlockHopper().setHardness(2F).setBlockName("Hopper");
		FlowerPot = new BlockCustomFlowerPot().setHardness(0.0F).setStepSound(Block.soundTypeStone).setBlockName("FlowerPot").setBlockTextureName("flower_pot");

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
		Blocks.fire.setFireInfo(LogNatural, 5, 5);
		Blocks.fire.setFireInfo(LogNatural2, 5, 5);
		Blocks.fire.setFireInfo(WoodSupportV, 5, 20);
		Blocks.fire.setFireInfo(WoodSupportV2, 5, 20);
		Blocks.fire.setFireInfo(WoodSupportH, 5, 20);
		Blocks.fire.setFireInfo(WoodSupportH2, 5, 20);
		Blocks.fire.setFireInfo(Leaves, 20, 20);
		Blocks.fire.setFireInfo(Leaves2, 20, 20);
		Blocks.fire.setFireInfo(fruitTreeWood, 5, 20);
		Blocks.fire.setFireInfo(fruitTreeLeaves, 20, 20);
		Blocks.fire.setFireInfo(fruitTreeLeaves2, 20, 20);
		Blocks.fire.setFireInfo(Fence, 5, 20);
		Blocks.fire.setFireInfo(Fence2, 5, 20);
		Blocks.fire.setFireInfo(FenceGate, 5, 20);
		Blocks.fire.setFireInfo(FenceGate2, 5, 20);
		Blocks.fire.setFireInfo(Chest, 5, 20);
		Blocks.fire.setFireInfo(StrawHideBed, 20, 20);
		Blocks.fire.setFireInfo(Thatch, 20, 20);
		Blocks.fire.setFireInfo(WoodVert, 5, 5);
		Blocks.fire.setFireInfo(WoodVert2, 5, 5);
		Blocks.fire.setFireInfo(WoodHoriz, 5, 5);
		Blocks.fire.setFireInfo(WoodHoriz2, 5, 5);
		Blocks.fire.setFireInfo(WoodHoriz3, 5, 5);
		Blocks.fire.setFireInfo(WoodHoriz4, 5, 5);
		Blocks.fire.setFireInfo(Planks, 5, 20);
		Blocks.fire.setFireInfo(Planks2, 5, 20);
		Blocks.fire.setFireInfo(WoodConstruct, 5, 20);
		Blocks.fire.setFireInfo(BerryBush, 20, 20);
		Blocks.fire.setFireInfo(Barrel, 5, 20);
		Blocks.fire.setFireInfo(Crops, 20, 20);
		Blocks.fire.setFireInfo(LogPile, 10, 10);
		//Blocks.fire.setFireInfo(Charcoal, 100, 20);
		for (int i=0; i < Global.WOOD_ALL.length; i++)
			Blocks.fire.setFireInfo(Doors[i], 5, 20);
	}
}
