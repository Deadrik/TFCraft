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
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCFluids;
import com.bioxx.tfc.api.Constant.Global;

public class BlockSetup extends TFCBlocks
{
	public static void registerBlocks()
	{
		GameRegistry.registerBlock(ore, "Ore1");
		GameRegistry.registerBlock(ore2, "Ore2");
		GameRegistry.registerBlock(ore3, "Ore3");
		GameRegistry.registerBlock(stoneIgIn, ItemStone.class, "StoneIgIn");
		GameRegistry.registerBlock(stoneIgEx, ItemStone.class, "StoneIgEx");
		GameRegistry.registerBlock(stoneSed, ItemStone.class, "StoneSed");
		GameRegistry.registerBlock(stoneMM, ItemStone.class, "StoneMM");

		GameRegistry.registerBlock(stoneIgInCobble, ItemStone.class, "StoneIgInCobble");
		GameRegistry.registerBlock(stoneIgExCobble, ItemStone.class, "StoneIgExCobble");
		GameRegistry.registerBlock(stoneSedCobble, ItemStone.class, "StoneSedCobble");
		GameRegistry.registerBlock(stoneMMCobble, ItemStone.class, "StoneMMCobble");
		GameRegistry.registerBlock(stoneIgInSmooth, ItemStone.class, "StoneIgInSmooth");
		GameRegistry.registerBlock(stoneIgExSmooth, ItemStone.class, "StoneIgExSmooth");
		GameRegistry.registerBlock(stoneSedSmooth, ItemStone.class, "StoneSedSmooth");
		GameRegistry.registerBlock(stoneMMSmooth, ItemStone.class, "StoneMMSmooth");
		GameRegistry.registerBlock(stoneIgInBrick, ItemStone.class, "StoneIgInBrick");
		GameRegistry.registerBlock(stoneIgExBrick, ItemStone.class, "StoneIgExBrick");
		GameRegistry.registerBlock(stoneSedBrick, ItemStone.class, "StoneSedBrick");
		GameRegistry.registerBlock(stoneMMBrick, ItemStone.class, "StoneMMBrick");

		GameRegistry.registerBlock(dirt, ItemSoil.class, "Dirt");
		GameRegistry.registerBlock(dirt2, ItemSoil.class, "Dirt2");
		GameRegistry.registerBlock(sand, ItemSoil.class, "Sand");
		GameRegistry.registerBlock(sand2, ItemSoil.class, "Sand2");
		GameRegistry.registerBlock(clay, ItemSoil.class,"Clay");
		GameRegistry.registerBlock(clay2, ItemSoil.class,"Clay2");
		GameRegistry.registerBlock(grass, ItemSoil.class, "Grass");
		GameRegistry.registerBlock(grass2, ItemSoil.class, "Grass2");
		GameRegistry.registerBlock(clayGrass, ItemSoil.class, "ClayGrass");
		GameRegistry.registerBlock(clayGrass2, ItemSoil.class, "ClayGrass2");
		GameRegistry.registerBlock(peatGrass, ItemSoil.class, "PeatGrass");
		GameRegistry.registerBlock(peat, ItemSoil.class, "Peat");
		GameRegistry.registerBlock(dryGrass, ItemSoil.class, "DryGrass");
		GameRegistry.registerBlock(dryGrass2, ItemSoil.class, "DryGrass2");
		GameRegistry.registerBlock(tallGrass, ItemCustomTallGrass.class, "TallGrass");
		GameRegistry.registerBlock(worldItem, "LooseRock");
		GameRegistry.registerBlock(logPile, "LogPile");
		GameRegistry.registerBlock(charcoal, "Charcoal");
		GameRegistry.registerBlock(detailed, "Detailed");

		GameRegistry.registerBlock(tilledSoil, ItemSoil.class, "tilledSoil");
		GameRegistry.registerBlock(tilledSoil2, ItemSoil.class, "tilledSoil2");

		GameRegistry.registerBlock(woodSupportV, ItemWoodSupport.class, "WoodSupportV");
		GameRegistry.registerBlock(woodSupportH, ItemWoodSupport.class, "WoodSupportH");
		GameRegistry.registerBlock(woodSupportV2, ItemWoodSupport2.class, "WoodSupportV2");
		GameRegistry.registerBlock(woodSupportH2, ItemWoodSupport2.class, "WoodSupportH2");
		GameRegistry.registerBlock(sulfur, "Sulfur");
		GameRegistry.registerBlock(logNatural, ItemCustomWood.class, "log");
		GameRegistry.registerBlock(logNatural2, ItemCustomWood2.class, "log2");
		GameRegistry.registerBlock(leaves, ItemCustomWood.class, "leaves");
		GameRegistry.registerBlock(leaves2, ItemCustomWood2.class, "leaves2");
		GameRegistry.registerBlock(sapling, ItemSapling.class, "sapling");
		GameRegistry.registerBlock(sapling2, ItemSapling2.class, "sapling2");
		GameRegistry.registerBlock(planks, ItemCustomWood.class, "planks");
		GameRegistry.registerBlock(planks2, ItemCustomWood2.class, "planks2");

		GameRegistry.registerBlock(firepit, "Firepit");
		GameRegistry.registerBlock(bellows, ItemBellows.class, "Bellows");
		GameRegistry.registerBlock(anvil, ItemAnvil1.class, "Anvil");
		GameRegistry.registerBlock(anvil2, ItemAnvil2.class, "Anvil2");
		GameRegistry.registerBlock(forge, "Forge");

		GameRegistry.registerBlock(molten, "Molten");
		GameRegistry.registerBlock(blastFurnace, ItemTerraBlock.class, "Bloomery");
		GameRegistry.registerBlock(bloomery, ItemTerraBlock.class, "EarlyBloomery");
		GameRegistry.registerBlock(sluice, "Sluice");
		GameRegistry.registerBlock(bloom, "Bloom");

		GameRegistry.registerBlock(fruitTreeWood, "fruitTreeWood");
		GameRegistry.registerBlock(fruitTreeLeaves, "fruitTreeLeaves");
		GameRegistry.registerBlock(fruitTreeLeaves2, "fruitTreeLeaves2");

		GameRegistry.registerBlock(stoneStairs, "stoneStairs");
		GameRegistry.registerBlock(stoneSlabs, "stoneSlabs");
		GameRegistry.registerBlock(stoneStalac, "stoneStalac");

		GameRegistry.registerBlock(woodConstruct, "WoodConstruct");
		GameRegistry.registerBlock(woodVert, ItemCustomWood.class, "WoodVert");
		GameRegistry.registerBlock(woodVert2, ItemCustomWood2.class, "WoodVert2");
		GameRegistry.registerBlock(woodHoriz, ItemCustomWoodH.class, "WoodHoriz");
		GameRegistry.registerBlock(woodHoriz2, ItemCustomWoodH2.class, "WoodHoriz2");
		GameRegistry.registerBlock(woodHoriz3, ItemCustomWoodH3.class, "WoodHoriz3");
		GameRegistry.registerBlock(woodHoriz4, "WoodHoriz4");

		GameRegistry.registerBlock(toolRack, ItemToolRack.class, "ToolRack");
		GameRegistry.registerBlock(spawnMeter, ItemTerraBlock.class, "SpawnMeter");
		GameRegistry.registerBlock(foodPrep, "FoodPrep");
		GameRegistry.registerBlock(quern, ItemTerraBlock.class, "Quern");
		GameRegistry.registerBlock(wallCobbleIgIn, ItemStone.class, "WallCobbleIgIn");
		GameRegistry.registerBlock(wallCobbleIgEx, ItemStone.class, "WallCobbleIgEx");
		GameRegistry.registerBlock(wallCobbleSed, ItemStone.class, "WallCobbleSed");
		GameRegistry.registerBlock(wallCobbleMM, ItemStone.class, "WallCobbleMM");
		GameRegistry.registerBlock(wallRawIgIn, ItemStone.class, "WallRawIgIn");
		GameRegistry.registerBlock(wallRawIgEx, ItemStone.class, "WallRawIgEx");
		GameRegistry.registerBlock(wallRawSed, ItemStone.class, "WallRawSed");
		GameRegistry.registerBlock(wallRawMM, ItemStone.class, "WallRawMM");
		GameRegistry.registerBlock(wallBrickIgIn, ItemStone.class, "WallBrickIgIn");
		GameRegistry.registerBlock(wallBrickIgEx, ItemStone.class, "WallBrickIgEx");
		GameRegistry.registerBlock(wallBrickSed, ItemStone.class, "WallBrickSed");
		GameRegistry.registerBlock(wallBrickMM, ItemStone.class, "WallBrickMM");
		GameRegistry.registerBlock(wallSmoothIgIn, ItemStone.class, "WallSmoothIgIn");
		GameRegistry.registerBlock(wallSmoothIgEx, ItemStone.class, "WallSmoothIgEx");
		GameRegistry.registerBlock(wallSmoothSed, ItemStone.class, "WallSmoothSed");
		GameRegistry.registerBlock(wallSmoothMM, ItemStone.class, "WallSmoothMM");

		GameRegistry.registerBlock(saltWater, "SaltWater");
		GameRegistry.registerBlock(saltWaterStationary, "SaltWaterStationary");
		GameRegistry.registerBlock(freshWater, "FreshWater");
		GameRegistry.registerBlock(freshWaterStationary, "FreshWaterStationary");
		GameRegistry.registerBlock(hotWater, "HotWater");
		GameRegistry.registerBlock(hotWaterStationary, "HotWaterStationary");
		GameRegistry.registerBlock(lava, "Lava");
		GameRegistry.registerBlock(lavaStationary, "LavaStationary");
		GameRegistry.registerBlock(ice, "Ice");

		GameRegistry.registerBlock(waterPlant, "SeaGrassStill");

		GameRegistry.registerBlock(fireBrick, "FireBrick");
		GameRegistry.registerBlock(metalSheet, "MetalSheet");

		// Wooden Doors
		for (int i=0; i < Global.WOOD_ALL.length; i++)
			GameRegistry.registerBlock(doors[i], "Door" + Global.WOOD_ALL[i].replaceAll(" ", ""));

		GameRegistry.registerBlock(ingotPile, "IngotPile");
		GameRegistry.registerBlock(barrel, ItemBarrels.class, "Barrel");
		GameRegistry.registerBlock(loom, ItemLooms.class, "Loom");
		GameRegistry.registerBlock(moss, "Moss");

		GameRegistry.registerBlock(flora, ItemFlora.class, "Flora");
		GameRegistry.registerBlock(pottery, "ClayPottery");
		GameRegistry.registerBlock(thatch, ItemTerraBlock.class, "Thatch");
		GameRegistry.registerBlock(crucible, ItemCrucible.class, "Crucible");
		GameRegistry.registerBlock(nestBox, ItemTerraBlock.class, "NestBox");
		GameRegistry.registerBlock(fence, ItemFence.class, "Fence");
		GameRegistry.registerBlock(fence2, ItemFence2.class, "Fence2");
		GameRegistry.registerBlock(fenceGate, ItemFenceGate.class, "FenceGate");
		GameRegistry.registerBlock(fenceGate2, ItemFenceGate2.class, "FenceGate2");
		GameRegistry.registerBlock(strawHideBed, "StrawHideBed");
		GameRegistry.registerBlock(armorStand, ItemArmourStand.class, "ArmourStand");
		GameRegistry.registerBlock(armorStand2, ItemArmourStand2.class, "ArmourStand2");
		GameRegistry.registerBlock(berryBush, ItemBerryBush.class, "BerryBush");
		GameRegistry.registerBlock(crops, "Crops");
		GameRegistry.registerBlock(lilyPad, ItemCustomLilyPad.class, "LilyPad");
		GameRegistry.registerBlock(flowers, ItemFlowers.class, "Flowers");
		GameRegistry.registerBlock(flowers2, ItemFlowers2.class, "Flowers2");
		GameRegistry.registerBlock(fungi, ItemFungi.class, "Fungi");
		GameRegistry.registerBlock(bookshelf, ItemTerraBlock.class, "Bookshelf");
		GameRegistry.registerBlock(torch, ItemTorch.class, "Torch");
		GameRegistry.registerBlock(torchOff, "TorchOff");
		GameRegistry.registerBlock(chest, ItemChest.class, "Chest TFC");
		GameRegistry.registerBlock(workbench, ItemTerraBlock.class, "Workbench");
		GameRegistry.registerBlock(cactus, ItemTerraBlock.class, "Cactus");
		GameRegistry.registerBlock(reeds, "Reeds");
		GameRegistry.registerBlock(pumpkin, ItemTerraBlock.class, "Pumpkin");
		GameRegistry.registerBlock(litPumpkin, ItemTerraBlock.class, "LitPumpkin");
		GameRegistry.registerBlock(buttonWood, "ButtonWood");
		GameRegistry.registerBlock(vine, ItemVine.class, "Vine");
		GameRegistry.registerBlock(leatherRack, "LeatherRack");
		GameRegistry.registerBlock(gravel, ItemSoil.class,"Gravel");
		GameRegistry.registerBlock(gravel2, ItemSoil.class,"Gravel2");

		GameRegistry.registerBlock(grill, ItemGrill.class, "Grill");
		GameRegistry.registerBlock(metalTrapDoor, ItemMetalTrapDoor.class, "MetalTrapDoor");
		GameRegistry.registerBlock(vessel, ItemLargeVessel.class, "Vessel");
		GameRegistry.registerBlock(smoke, "Smoke");
		GameRegistry.registerBlock(smokeRack, "SmokeRack");
		GameRegistry.registerBlock(snow, "Snow");
		GameRegistry.registerBlock(oilLamp, ItemOilLamp.class, "OilLamp");
		GameRegistry.registerBlock(hopper, "Hopper");
		GameRegistry.registerBlock(flowerPot, "FlowerPot");
	}

	public static void loadBlocks()
	{
		TerraFirmaCraft.LOG.info(new StringBuilder().append("Loading Blocks").toString());

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

		bookshelf = new BlockCustomBookshelf().setHardness(1.5F).setStepSound(Block.soundTypeWood).setBlockName("Bookshelf").setBlockTextureName("bookshelf");
		torch = new BlockTorch().setHardness(0.0F).setStepSound(Block.soundTypeWood).setBlockName("Torch").setBlockTextureName("torch_on");
		torchOff = new BlockTorchOff().setHardness(0.0F).setStepSound(Block.soundTypeWood).setBlockName("TorchOff").setBlockTextureName("torch_on");
		chest = new BlockChestTFC().setHardness(2.5F).setStepSound(Block.soundTypeWood).setBlockName("Chest");
		workbench = new BlockWorkbench().setHardness(2.5F).setStepSound(Block.soundTypeWood).setBlockName("Workbench").setBlockTextureName("crafting_table");
		cactus = new BlockCustomCactus().setHardness(0.4F).setStepSound(Block.soundTypeCloth).setBlockName("Cactus").setBlockTextureName("cactus");
		reeds = new BlockCustomReed().setHardness(0.0F).setStepSound(Block.soundTypeGrass).setBlockName("Reeds").setBlockTextureName("reeds");
		pumpkin = new BlockCustomPumpkin(false).setHardness(1.0F).setStepSound(Block.soundTypeWood).setBlockName("Pumpkin").setBlockTextureName("pumpkin");
		litPumpkin = new BlockCustomPumpkin(true).setHardness(1.0F).setStepSound(Block.soundTypeWood).setLightLevel(1.0F).setBlockName("LitPumpkin").setBlockTextureName("pumpkin");
		buttonWood = new BlockCustomButtonWood().setHardness(0.5F).setStepSound(Block.soundTypeWood).setBlockName("ButtonWood");
		vine = new BlockCustomVine().setHardness(0.2F).setStepSound(Block.soundTypeGrass).setBlockName("Vine").setBlockTextureName("vine");

		// This is not used anywhere
		//Block.blockRegistry.addObject(Block.getIdFromBlock(Blocks.oak_stairs), "oak_stairs", (new BlockStair(Material.wood)).setBlockName("stairsWood"));

		/*Block.blockRegistry.addObject(Block.getIdFromBlock(Blocks.snow_layer), "snow_layer",
				(new BlockCustomSnow()).setHardness(0.1F).setStepSound(Block.soundTypeSnow).setBlockName("snow").setLightOpacity(1).setBlockTextureName("snow"));*/
		//Snow = (Block)Block.blockRegistry.getObject("snow_layer");
		snow = new BlockCustomSnow().setHardness(0.1F).setStepSound(Block.soundTypeSnow).setBlockName("snow").setLightOpacity(0).setBlockTextureName("snow");
		Blocks.snow_layer = snow;
		stoneIgInCobble = new BlockIgInCobble(Material.rock).setHardness(16F).setBlockName("IgInRockCobble");
		stoneIgIn = new BlockIgIn(Material.rock).setHardness(8F).setBlockName("IgInRock");
		stoneIgInSmooth = new BlockIgInSmooth().setHardness(16F).setBlockName("IgInRockSmooth");
		stoneIgInBrick = new BlockIgInBrick().setHardness(16F).setBlockName("IgInRockBrick");

		stoneSedCobble = new BlockSedCobble(Material.rock).setHardness(14F).setBlockName("SedRockCobble");
		stoneSed = new BlockSed(Material.rock).setHardness(7F).setBlockName("SedRock");
		stoneSedSmooth = new BlockSedSmooth().setHardness(14F).setBlockName("SedRockSmooth");
		stoneSedBrick = new BlockSedBrick().setHardness(14F).setBlockName("SedRockBrick");

		stoneIgExCobble = new BlockIgExCobble(Material.rock).setHardness(16F).setBlockName("IgExRockCobble");
		stoneIgEx = new BlockIgEx(Material.rock).setHardness(8F).setBlockName("IgExRock");
		stoneIgExSmooth = new BlockIgExSmooth().setHardness(16F).setBlockName("IgExRockSmooth");
		stoneIgExBrick = new BlockIgExBrick().setHardness(16F).setBlockName("IgExRockBrick");

		stoneMMCobble = new BlockMMCobble(Material.rock).setHardness(15F).setBlockName("MMRockCobble");
		stoneMM = new BlockMM(Material.rock).setHardness(8F).setBlockName("MMRock");
		stoneMMSmooth = new BlockMMSmooth().setHardness(15F).setBlockName("MMRockSmooth");
		stoneMMBrick = new BlockMMBrick().setHardness(15F).setBlockName("MMRockBrick");

		dirt = new BlockDirt(0).setHardness(2F).setStepSound(Block.soundTypeGravel).setBlockName("dirt");

		dirt2 = new BlockDirt(16).setHardness(2F).setStepSound(Block.soundTypeGravel).setBlockName("dirt");
		clay = new BlockClay(0).setHardness(3F).setStepSound(Block.soundTypeGravel).setBlockName("clay");
		clay2 = new BlockClay(16).setHardness(3F).setStepSound(Block.soundTypeGravel).setBlockName("clay");
		clayGrass = new BlockClayGrass(0).setHardness(3F).setStepSound(Block.soundTypeGrass).setBlockName("ClayGrass");
		clayGrass2 = new BlockClayGrass(16).setHardness(3F).setStepSound(Block.soundTypeGrass).setBlockName("ClayGrass");
		grass = new BlockGrass().setHardness(3F).setStepSound(Block.soundTypeGrass).setBlockName("Grass");
		grass2 = new BlockGrass(16).setHardness(3F).setStepSound(Block.soundTypeGrass).setBlockName("Grass");
		peat = new BlockPeat().setHardness(3F).setStepSound(Block.soundTypeGravel).setBlockName("Peat");
		peatGrass = new BlockPeatGrass().setHardness(3F).setStepSound(Block.soundTypeGrass).setBlockName("PeatGrass");
		dryGrass = new BlockDryGrass(0).setHardness(3F).setStepSound(Block.soundTypeGrass).setBlockName("DryGrass");
		dryGrass2 =new BlockDryGrass(16).setHardness(3F).setStepSound(Block.soundTypeGrass).setBlockName("DryGrass");
		tallGrass = new BlockCustomTallGrass().setHardness(0.0F).setStepSound(Block.soundTypeGrass).setBlockName("TallGrass");
		sand = new BlockSand(0).setHardness(0.5F).setStepSound(Block.soundTypeSand).setBlockName("sand");
		sand2 = new BlockSand(16).setHardness(0.5F).setStepSound(Block.soundTypeSand).setBlockName("sand");

		ore = new BlockOre(Material.rock).setHardness(10F).setResistance(10F).setBlockName("Ore");
		ore2 = new BlockOre2(Material.rock).setHardness(10F).setResistance(10F).setBlockName("Ore");
		ore3 = new BlockOre3(Material.rock).setHardness(10F).setResistance(10F).setBlockName("Ore");
		worldItem = new BlockWorldItem().setHardness(0.05F).setResistance(1F).setBlockName("LooseRock");
		sulfur = new BlockSulfur(Material.rock).setBlockName("Sulfur").setHardness(0.5F).setResistance(1F);

		logPile = new BlockLogPile().setHardness(10F).setResistance(1F).setBlockName("LogPile");
		woodSupportV = new BlockWoodSupport(Material.wood).setBlockName("WoodSupportV").setHardness(0.5F).setResistance(1F);
		woodSupportH = new BlockWoodSupport(Material.wood).setBlockName("WoodSupportH").setHardness(0.5F).setResistance(1F);
		woodSupportV2 = new BlockWoodSupport2(Material.wood).setBlockName("WoodSupportV2").setHardness(0.5F).setResistance(1F);
		woodSupportH2 = new BlockWoodSupport2(Material.wood).setBlockName("WoodSupportH2").setHardness(0.5F).setResistance(1F);

		tilledSoil = new BlockFarmland(TFCBlocks.dirt, 0).setHardness(2F).setStepSound(Block.soundTypeGravel).setBlockName("tilledSoil");
		tilledSoil2 = new BlockFarmland(TFCBlocks.dirt2, 16).setHardness(2F).setStepSound(Block.soundTypeGravel).setBlockName("tilledSoil");

		fruitTreeWood = new BlockFruitWood().setBlockName("fruitTreeWood").setHardness(5.5F).setResistance(2F);
		fruitTreeLeaves = new BlockFruitLeaves(0).setBlockName("fruitTreeLeaves").setHardness(0.5F).setResistance(1F).setStepSound(Block.soundTypeGrass);
		fruitTreeLeaves2 = new BlockFruitLeaves(8).setBlockName("fruitTreeLeaves2").setHardness(0.5F).setResistance(1F).setStepSound(Block.soundTypeGrass);

		woodConstruct = new BlockWoodConstruct().setHardness(4F).setStepSound(Block.soundTypeWood).setBlockName("WoodConstruct");

		firepit = new BlockFirepit().setBlockName("Firepit").setHardness(1).setLightLevel(0F);
		bellows = new BlockBellows(Material.wood).setBlockName("Bellows").setHardness(2);
		forge= new BlockForge().setBlockName("Forge").setHardness(20).setLightLevel(0F);
		anvil = new BlockAnvil().setBlockName("Anvil").setHardness(3).setResistance(100F);
		anvil2 = new BlockAnvil(8).setBlockName("Anvil2").setHardness(3).setResistance(100F);

		molten = new BlockMolten().setBlockName("Molten").setHardness(20);
		blastFurnace = new BlockBlastFurnace().setBlockName("BlastFurnace").setHardness(20).setLightLevel(0F);
		bloomery = new BlockEarlyBloomery().setBlockName("EarlyBloomery").setHardness(20).setLightLevel(0F);
		bloom = new BlockBloom().setBlockName("Bloom").setHardness(20).setLightLevel(0F);
		sluice = new BlockSluice().setBlockName("Sluice").setHardness(2F).setResistance(20F);

		stoneStairs = new BlockStair(Material.rock).setBlockName("stoneStairs").setHardness(10);
		stoneSlabs = new BlockSlab().setBlockName("stoneSlabs").setHardness(10);
		stoneStalac = new BlockStalactite().setBlockName("stoneStalac").setHardness(5);

		charcoal = new BlockCharcoal().setHardness(3F).setResistance(10F).setBlockName("Charcoal");

		detailed = new BlockDetailed().setBlockName("StoneDetailed").setHardness(10);

		planks = new BlockPlanks(Material.wood).setHardness(4.0F).setResistance(5.0F).setStepSound(Block.soundTypeWood).setBlockName("wood");
		planks2 = new BlockPlanks2(Material.wood).setHardness(4.0F).setResistance(5.0F).setStepSound(Block.soundTypeWood).setBlockName("wood2");
		leaves = new BlockCustomLeaves().setHardness(0.2F).setLightOpacity(1).setStepSound(Block.soundTypeGrass).setBlockName("leaves").setCreativeTab(TFCTabs.TFC_DECORATION);
		leaves2 = new BlockCustomLeaves2().setHardness(0.2F).setLightOpacity(1).setStepSound(Block.soundTypeGrass).setBlockName("leaves2");
		sapling = new BlockSapling().setHardness(0.0F).setStepSound(Block.soundTypeGrass).setBlockName("sapling");
		sapling2 = new BlockSapling2().setHardness(0.0F).setStepSound(Block.soundTypeGrass).setBlockName("sapling2");

		logNatural = new BlockLogNatural().setHardness(50.0F).setStepSound(Block.soundTypeWood).setBlockName("log");
		logNatural2 = new BlockLogNatural2().setHardness(50.0F).setStepSound(Block.soundTypeWood).setBlockName("log2");
		woodVert = new BlockLogVert().setBlockName("WoodVert").setHardness(20).setResistance(15F).setStepSound(Block.soundTypeWood);
		woodVert2 = new BlockLogVert2().setBlockName("WoodVert2").setHardness(20).setResistance(15F).setStepSound(Block.soundTypeWood);
		woodHoriz = new BlockLogHoriz(0).setBlockName("WoodHoriz").setHardness(20).setResistance(15F).setStepSound(Block.soundTypeWood);
		woodHoriz2 = new BlockLogHoriz(8).setBlockName("WoodHoriz2").setHardness(20).setResistance(15F).setStepSound(Block.soundTypeWood);
		woodHoriz3 = new BlockLogHoriz2(0).setBlockName("WoodHoriz3").setHardness(20).setResistance(15F).setStepSound(Block.soundTypeWood);
		//Use 8 instead of 0 if Global.WOOD_ALL.length > 24
		woodHoriz4 = new BlockLogHoriz2(/*8*/0).setBlockName("WoodHoriz4").setHardness(20).setResistance(15F).setStepSound(Block.soundTypeWood);

		toolRack = new BlockToolRack().setHardness(3F).setBlockName("Toolrack");
		spawnMeter = new BlockSpawnMeter().setHardness(3F).setBlockName("SpawnMeter");
		foodPrep = new BlockFoodPrep().setHardness(1F).setBlockName("FoodPrep");
		quern = new BlockQuern().setHardness(3F).setBlockName("Quern");

		wallCobbleIgIn = new BlockCustomWall(stoneIgInCobble, 3).setBlockName("WallCobble");
		wallCobbleIgEx = new BlockCustomWall(stoneIgExCobble, 4).setBlockName("WallCobble");
		wallCobbleSed = new BlockCustomWall(stoneSedCobble, 8).setBlockName("WallCobble");
		wallCobbleMM = new BlockCustomWall(stoneMMCobble, 6).setBlockName("WallCobble");
		wallRawIgIn = new BlockCustomWall(stoneIgIn, 3).setBlockName("WallRaw");
		wallRawIgEx = new BlockCustomWall(stoneIgEx, 4).setBlockName("WallRaw");
		wallRawSed = new BlockCustomWall(stoneSed, 8).setBlockName("WallRaw");
		wallRawMM = new BlockCustomWall(stoneMM, 6).setBlockName("WallRaw");
		wallBrickIgIn = new BlockCustomWall(stoneIgInBrick, 3).setBlockName("WallBrick");
		wallBrickIgEx = new BlockCustomWall(stoneIgExBrick, 4).setBlockName("WallBrick");
		wallBrickSed = new BlockCustomWall(stoneSedBrick, 8).setBlockName("WallBrick");
		wallBrickMM = new BlockCustomWall(stoneMMBrick, 6).setBlockName("WallBrick");
		wallSmoothIgIn = new BlockCustomWall(stoneIgInSmooth, 3).setBlockName("WallSmooth");
		wallSmoothIgEx = new BlockCustomWall(stoneIgExSmooth, 4).setBlockName("WallSmooth");
		wallSmoothSed = new BlockCustomWall(stoneSedSmooth, 8).setBlockName("WallSmooth");
		wallSmoothMM = new BlockCustomWall(stoneMMSmooth, 6).setBlockName("WallSmooth");

		// Wooden Doors
		for (int i=0; i < Global.WOOD_ALL.length; i++)
			doors[i] = new BlockCustomDoor(i*2).setBlockName("Door "+Global.WOOD_ALL[i]);

		ingotPile = new BlockIngotPile().setBlockName("ingotpile").setHardness(3);

		barrel = new BlockBarrel().setBlockName("Barrel").setHardness(2);
		loom = new BlockLoom().setBlockName("Loom").setHardness(2);
		thatch = new BlockThatch().setBlockName("Thatch").setHardness(1).setStepSound(Block.soundTypeGrass).setCreativeTab(TFCTabs.TFC_BUILDING);
		moss = new BlockMoss().setBlockName("Moss").setHardness(1).setStepSound(Block.soundTypeGrass);

		flora = new BlockFlora().setBlockName("Flora").setHardness(0.1f).setStepSound(Block.soundTypeGrass);
		pottery = new BlockPottery().setBlockName("Pottery").setHardness(1.0f);

		crucible = new BlockCrucible().setBlockName("Crucible").setHardness(4.0f);

		nestBox = new BlockNestBox().setBlockName("NestBox").setHardness(1);

		fence = new BlockTFCFence("Fence", Material.wood).setBlockName("FenceTFC").setHardness(2);
		fenceGate = new BlockCustomFenceGate().setBlockName("FenceGateTFC").setHardness(2);
		fence2 = new BlockTFCFence2("Fence2", Material.wood).setBlockName("FenceTFC").setHardness(2);
		fenceGate2 = new BlockCustomFenceGate2().setBlockName("FenceGateTFC").setHardness(2);
		strawHideBed = new BlockBed().setBlockName("StrawHideBed").setHardness(1).setCreativeTab(null);
		armorStand = new BlockStand().setBlockName("ArmourStand").setHardness(2);
		armorStand2 = new BlockStand2().setBlockName("ArmourStand").setHardness(2);

		berryBush = new BlockBerryBush().setBlockName("BerryBush").setHardness(0.3f).setStepSound(Block.soundTypeGrass);
		crops = new BlockCrop().setBlockName("crops").setHardness(0.3F).setStepSound(Block.soundTypeGrass);
		lilyPad = new BlockCustomLilyPad().setHardness(0.0F).setStepSound(Block.soundTypeGrass).setBlockName("LilyPad").setBlockTextureName("waterlily");
		flowers = new BlockFlower().setHardness(0.0F).setStepSound(Block.soundTypeGrass).setBlockName("Flowers");
		flowers2 = new BlockFlower2().setHardness(0.0F).setStepSound(Block.soundTypeGrass).setBlockName("Flowers2");
		fungi = new BlockFungi().setHardness(0.0F).setStepSound(Block.soundTypeGrass).setBlockName("Fungi");

		saltWater = new BlockSaltWater(TFCFluids.SALTWATER).setHardness(100.0F).setLightOpacity(3).setBlockName("SaltWater");
		saltWaterStationary = new BlockLiquidStatic(TFCFluids.SALTWATER, Material.water, saltWater).setHardness(100.0F).setLightOpacity(3).setBlockName("SaltWaterStationary");

		freshWater = new BlockFreshWater(TFCFluids.FRESHWATER).setHardness(100.0F).setLightOpacity(3).setBlockName("FreshWater");
		freshWaterStationary = new BlockLiquidStatic(TFCFluids.FRESHWATER, Material.water, freshWater).setHardness(100.0F).setLightOpacity(3).setBlockName("FreshWaterStationary");

		hotWater = new BlockHotWater(TFCFluids.HOTWATER).setHardness(100.0F).setLightOpacity(3).setBlockName("HotWater");
		hotWaterStationary = new BlockHotWaterStatic(TFCFluids.HOTWATER, Material.water, hotWater).setHardness(100.0F).setLightOpacity(3).setBlockName("HotWaterStationary");

		lava = new BlockLava().setHardness(0.0F).setLightLevel(1.0F).setLightOpacity(255).setBlockName("Lava");
		lavaStationary = new BlockLiquidStatic(TFCFluids.LAVA, Material.lava, lava).setHardness(0.0F).setLightLevel(1.0F).setLightOpacity(255).setBlockName("LavaStationary");
		ice = new BlockCustomIce().setHardness(0.5F).setLightOpacity(3).setStepSound(Block.soundTypeGlass).setBlockName("Ice").setBlockTextureName("ice");

		waterPlant = new BlockWaterPlant(0).setBlockName("SeaGrassStill").setHardness(0.5f).setStepSound(Block.soundTypeGravel);

		fireBrick = new BlockFireBrick().setBlockName("FireBrick").setHardness(8);
		metalSheet = new BlockMetalSheet().setBlockName("MetalSheet").setHardness(80);
		leatherRack = new BlockLeatherRack().setBlockName("LeatherRack").setHardness(1);

		gravel = new BlockGravel(0).setHardness(2F).setStepSound(Block.soundTypeGravel).setBlockName("gravel");
		gravel2 = new BlockGravel(16).setHardness(2F).setStepSound(Block.soundTypeGravel).setBlockName("gravel");

		grill = new BlockGrill().setHardness(2F).setBlockName("Grill");
		metalTrapDoor = new BlockMetalTrapDoor().setHardness(2F).setBlockName("MetalTrapDoor");
		vessel = new BlockLargeVessel().setHardness(1F).setBlockName("Vessel");
		smoke = new BlockSmoke().setHardness(0F).setBlockName("Smoke");
		smokeRack = new BlockSmokeRack().setHardness(0F).setBlockName("SmokeRack");

		oilLamp = new BlockOilLamp().setHardness(1F).setBlockName("OilLamp");
		hopper = new BlockHopper().setHardness(2F).setBlockName("Hopper");
		flowerPot = new BlockCustomFlowerPot().setHardness(0.0F).setStepSound(Block.soundTypeStone).setBlockName("FlowerPot").setBlockTextureName("flower_pot");

		stoneIgIn.setHarvestLevel("pickaxe", 0);
		stoneIgEx.setHarvestLevel("pickaxe", 0);
		stoneSed.setHarvestLevel("pickaxe", 0);
		stoneMM.setHarvestLevel("pickaxe", 0);
		stoneStalac.setHarvestLevel("pickaxe", 0);
		detailed.setHarvestLevel("pickaxe", 0);
		ore.setHarvestLevel("pickaxe", 1);
		ore2.setHarvestLevel("pickaxe", 1);
		ore3.setHarvestLevel("pickaxe", 1);

		dirt.setHarvestLevel("shovel", 0);
		dirt2.setHarvestLevel("shovel", 0);
		grass.setHarvestLevel("shovel", 0);
		grass2.setHarvestLevel("shovel", 0);
		dryGrass.setHarvestLevel("shovel", 0);
		dryGrass2.setHarvestLevel("shovel", 0);
		clay.setHarvestLevel("shovel", 0);
		clay2.setHarvestLevel("shovel", 0);
		clayGrass.setHarvestLevel("shovel", 0);
		clayGrass2.setHarvestLevel("shovel", 0);
		peat.setHarvestLevel("shovel", 0);
		peatGrass.setHarvestLevel("shovel", 0);
		sand.setHarvestLevel("shovel", 0);
		sand2.setHarvestLevel("shovel", 0);
		charcoal.setHarvestLevel("shovel", 0);
		gravel.setHarvestLevel("shovel", 0);
		gravel2.setHarvestLevel("shovel", 0);
		waterPlant.setHarvestLevel("shovel", 0);
		tilledSoil.setHarvestLevel("shovel", 0);
		tilledSoil2.setHarvestLevel("shovel", 0);

		detailed.setHarvestLevel("axe", 0);
		Blocks.oak_stairs.setHarvestLevel("axe", 0);
		woodConstruct.setHarvestLevel("axe", 0);
		logNatural.setHarvestLevel("axe", 1);
		logNatural2.setHarvestLevel("axe", 1);
		woodHoriz.setHarvestLevel("axe", 1);
		woodHoriz2.setHarvestLevel("axe", 1);
		woodHoriz3.setHarvestLevel("axe", 1);
		woodHoriz4.setHarvestLevel("axe", 1);
		woodVert.setHarvestLevel("axe", 1);
		woodVert2.setHarvestLevel("axe", 1);

		logNatural.setHarvestLevel("hammer", 1);
		logNatural2.setHarvestLevel("hammer", 1);
		woodHoriz.setHarvestLevel("hammer", 1);
		woodHoriz2.setHarvestLevel("hammer", 1);
		woodHoriz3.setHarvestLevel("hammer", 1);
		woodHoriz4.setHarvestLevel("hammer", 1);
		woodVert.setHarvestLevel("hammer", 1);
		woodVert2.setHarvestLevel("hammer", 1);
	}

	public static void setupFire()
	{
		Blocks.fire.setFireInfo(logNatural, 5, 5);
		Blocks.fire.setFireInfo(logNatural2, 5, 5);
		Blocks.fire.setFireInfo(woodSupportV, 5, 20);
		Blocks.fire.setFireInfo(woodSupportV2, 5, 20);
		Blocks.fire.setFireInfo(woodSupportH, 5, 20);
		Blocks.fire.setFireInfo(woodSupportH2, 5, 20);
		Blocks.fire.setFireInfo(leaves, 20, 20);
		Blocks.fire.setFireInfo(leaves2, 20, 20);
		Blocks.fire.setFireInfo(fruitTreeWood, 5, 20);
		Blocks.fire.setFireInfo(fruitTreeLeaves, 20, 20);
		Blocks.fire.setFireInfo(fruitTreeLeaves2, 20, 20);
		Blocks.fire.setFireInfo(fence, 5, 20);
		Blocks.fire.setFireInfo(fence2, 5, 20);
		Blocks.fire.setFireInfo(fenceGate, 5, 20);
		Blocks.fire.setFireInfo(fenceGate2, 5, 20);
		Blocks.fire.setFireInfo(chest, 5, 20);
		Blocks.fire.setFireInfo(strawHideBed, 20, 20);
		Blocks.fire.setFireInfo(thatch, 20, 20);
		Blocks.fire.setFireInfo(woodVert, 5, 5);
		Blocks.fire.setFireInfo(woodVert2, 5, 5);
		Blocks.fire.setFireInfo(woodHoriz, 5, 5);
		Blocks.fire.setFireInfo(woodHoriz2, 5, 5);
		Blocks.fire.setFireInfo(woodHoriz3, 5, 5);
		Blocks.fire.setFireInfo(woodHoriz4, 5, 5);
		Blocks.fire.setFireInfo(planks, 5, 20);
		Blocks.fire.setFireInfo(planks2, 5, 20);
		Blocks.fire.setFireInfo(woodConstruct, 5, 20);
		Blocks.fire.setFireInfo(berryBush, 20, 20);
		Blocks.fire.setFireInfo(barrel, 5, 20);
		Blocks.fire.setFireInfo(crops, 20, 20);
		Blocks.fire.setFireInfo(logPile, 10, 10);
		//Blocks.fire.setFireInfo(Charcoal, 100, 20);
		for (int i=0; i < Global.WOOD_ALL.length; i++)
			Blocks.fire.setFireInfo(doors[i], 5, 20);
	}
}
