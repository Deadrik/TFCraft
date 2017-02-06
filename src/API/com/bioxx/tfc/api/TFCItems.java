package com.bioxx.tfc.api;

import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;

import cpw.mods.fml.common.registry.GameRegistry;

import com.bioxx.tfc.TerraFirmaCraft;

public class TFCItems
{
	public static Item gemRuby;
	public static Item gemSapphire;
	public static Item gemEmerald;
	public static Item gemTopaz;
	public static Item gemGarnet;
	public static Item gemOpal;
	public static Item gemAmethyst;
	public static Item gemJasper;
	public static Item gemBeryl;
	public static Item gemTourmaline;
	public static Item gemJade;

	public static Item gemAgate;
	public static Item gemDiamond;

	public static Item smallMetalChunk;
	public static Item metalDust;

	public static Item bismuthIngot;
	public static Item bismuthBronzeIngot;
	public static Item blackBronzeIngot;
	public static Item blackSteelIngot;
	public static Item highCarbonBlackSteelIngot;
	public static Item blueSteelIngot;
	public static Item weakBlueSteelIngot;
	public static Item highCarbonBlueSteelIngot;
	public static Item brassIngot;
	public static Item bronzeIngot;
	public static Item copperIngot;
	public static Item goldIngot;
	public static Item wroughtIronIngot;
	public static Item leadIngot;
	public static Item nickelIngot;
	public static Item pigIronIngot;
	public static Item platinumIngot;
	public static Item redSteelIngot;
	public static Item weakRedSteelIngot;
	public static Item highCarbonRedSteelIngot;
	public static Item roseGoldIngot;
	public static Item silverIngot;
	public static Item steelIngot;
	public static Item weakSteelIngot;
	public static Item highCarbonSteelIngot;
	public static Item sterlingSilverIngot;
	public static Item tinIngot;
	public static Item zincIngot;
	public static Item electrumIngot;
	public static Item cupronickelIngot;
	public static Item osmiumIngot;
	public static Item aluminumIngot;
	public static Item tungstenIngot;

	public static Item bismuthIngot2x;
	public static Item bismuthBronzeIngot2x;
	public static Item blackBronzeIngot2x;
	public static Item blackSteelIngot2x;
	public static Item blueSteelIngot2x;
	public static Item brassIngot2x;
	public static Item bronzeIngot2x;
	public static Item copperIngot2x;
	public static Item goldIngot2x;
	public static Item wroughtIronIngot2x;
	public static Item leadIngot2x;
	public static Item nickelIngot2x;
	public static Item pigIronIngot2x;
	public static Item platinumIngot2x;
	public static Item redSteelIngot2x;
	public static Item roseGoldIngot2x;
	public static Item silverIngot2x;
	public static Item steelIngot2x;
	public static Item sterlingSilverIngot2x;
	public static Item tinIngot2x;
	public static Item zincIngot2x;
	public static Item electrumIngot2x;
	public static Item cupronickelIngot2x;
	public static Item osmiumIngot2x;
	public static Item aluminumIngot2x;
	public static Item tungstenIngot2x;

	public static Item igInShovel;
	public static Item igInAxe;
	public static Item igInHoe;

	public static Item sedShovel;
	public static Item sedAxe;
	public static Item sedHoe;

	public static Item igExShovel;
	public static Item igExAxe;
	public static Item igExHoe;

	public static Item mMShovel;
	public static Item mMAxe;
	public static Item mMHoe;

	public static Item bismuthBronzePick;
	public static Item bismuthBronzeShovel;
	public static Item bismuthBronzeAxe;
	public static Item bismuthBronzeHoe;
	public static Item blackBronzePick;
	public static Item blackBronzeShovel;
	public static Item blackBronzeAxe;
	public static Item blackBronzeHoe;
	public static Item blackSteelPick;
	public static Item blackSteelShovel;
	public static Item blackSteelAxe;
	public static Item blackSteelHoe;
	public static Item blueSteelPick;
	public static Item blueSteelShovel;
	public static Item blueSteelAxe;
	public static Item blueSteelHoe;
	public static Item bronzePick;
	public static Item bronzeShovel;
	public static Item bronzeAxe;
	public static Item bronzeHoe;
	public static Item copperPick;
	public static Item copperShovel;
	public static Item copperAxe;
	public static Item copperHoe;
	public static Item wroughtIronPick;
	public static Item wroughtIronShovel;
	public static Item wroughtIronAxe;
	public static Item wroughtIronHoe;
	public static Item redSteelPick;
	public static Item redSteelShovel;
	public static Item redSteelAxe;
	public static Item redSteelHoe;
	public static Item steelPick;
	public static Item steelShovel;
	public static Item steelAxe;
	public static Item steelHoe;


	public static Item bismuthBronzeChisel;
	public static Item blackBronzeChisel;
	public static Item blackSteelChisel;
	public static Item blueSteelChisel;
	public static Item bronzeChisel;
	public static Item copperChisel;
	public static Item wroughtIronChisel;
	public static Item redSteelChisel;
	public static Item steelChisel;

	public static Item bismuthBronzeSword;
	public static Item blackBronzeSword;
	public static Item blackSteelSword;
	public static Item blueSteelSword;
	public static Item bronzeSword;
	public static Item copperSword;
	public static Item wroughtIronSword;
	public static Item redSteelSword;
	public static Item steelSword;

	public static Item bismuthBronzeMace;
	public static Item blackBronzeMace;
	public static Item blackSteelMace;
	public static Item blueSteelMace;
	public static Item bronzeMace;
	public static Item copperMace;
	public static Item wroughtIronMace;
	public static Item redSteelMace;
	public static Item steelMace;

	public static Item bismuthBronzeSaw;
	public static Item blackBronzeSaw;
	public static Item blackSteelSaw;
	public static Item blueSteelSaw;
	public static Item bronzeSaw;
	public static Item copperSaw;
	public static Item wroughtIronSaw;
	public static Item redSteelSaw;
	public static Item steelSaw;

	public static Item coal;
	public static Item oreChunk;
	public static Item oreMineralChunk;
	public static Item smallOreChunk;
	public static Item orePile;
	public static Item logs;
	public static Item barrel;
	public static Item loom;

	// javelins
	public static Item igInStoneJavelin;
	public static Item sedStoneJavelin;
	public static Item igExStoneJavelin;
	public static Item mMStoneJavelin;
	public static Item copperJavelin;
	public static Item bismuthBronzeJavelin;
	public static Item bronzeJavelin;
	public static Item blackBronzeJavelin;
	public static Item wroughtIronJavelin;
	public static Item steelJavelin;
	public static Item blackSteelJavelin;
	public static Item blueSteelJavelin;
	public static Item redSteelJavelin;

	// javelin heads
	public static Item igInStoneJavelinHead;
	public static Item sedStoneJavelinHead;
	public static Item igExStoneJavelinHead;
	public static Item mMStoneJavelinHead;
	public static Item copperJavelinHead;
	public static Item bismuthBronzeJavelinHead;
	public static Item bronzeJavelinHead;
	public static Item blackBronzeJavelinHead;
	public static Item wroughtIronJavelinHead;
	public static Item steelJavelinHead;
	public static Item blackSteelJavelinHead;
	public static Item blueSteelJavelinHead;
	public static Item redSteelJavelinHead;

	public static Item bismuthBronzeScythe;
	public static Item blackBronzeScythe;
	public static Item blackSteelScythe;
	public static Item blueSteelScythe;
	public static Item bronzeScythe;
	public static Item copperScythe;
	public static Item wroughtIronScythe;
	public static Item redSteelScythe;
	public static Item steelScythe;

	public static Item bismuthBronzeKnife;
	public static Item blackBronzeKnife;
	public static Item blackSteelKnife;
	public static Item blueSteelKnife;
	public static Item bronzeKnife;
	public static Item copperKnife;
	public static Item wroughtIronKnife;
	public static Item redSteelKnife;
	public static Item steelKnife;

	public static Item fireStarter;
	public static Item fishingRod;
	public static Item bow;

	public static Item stoneHammer;
	public static Item bismuthBronzeHammer;
	public static Item blackBronzeHammer;
	public static Item blackSteelHammer;
	public static Item blueSteelHammer;
	public static Item bronzeHammer;
	public static Item copperHammer;
	public static Item wroughtIronHammer;
	public static Item redSteelHammer;
	public static Item steelHammer;

	public static Item bismuthUnshaped;
	public static Item bismuthBronzeUnshaped;
	public static Item blackBronzeUnshaped;
	public static Item blackSteelUnshaped;
	public static Item highCarbonBlackSteelUnshaped;
	public static Item blueSteelUnshaped;
	public static Item weakBlueSteelUnshaped;
	public static Item highCarbonBlueSteelUnshaped;
	public static Item brassUnshaped;
	public static Item bronzeUnshaped;
	public static Item copperUnshaped;
	public static Item goldUnshaped;
	public static Item wroughtIronUnshaped;
	public static Item leadUnshaped;
	public static Item nickelUnshaped;
	public static Item pigIronUnshaped;
	public static Item platinumUnshaped;
	public static Item redSteelUnshaped;
	public static Item weakRedSteelUnshaped;
	public static Item highCarbonRedSteelUnshaped;
	public static Item roseGoldUnshaped;
	public static Item silverUnshaped;
	public static Item steelUnshaped;
	public static Item weakSteelUnshaped;
	public static Item highCarbonSteelUnshaped;
	public static Item sterlingSilverUnshaped;
	public static Item tinUnshaped;
	public static Item zincUnshaped;
	public static Item electrumUnshaped;
	public static Item cupronickelUnshaped;
	public static Item osmiumUnshaped;
	public static Item aluminumUnshaped;
	public static Item tungstenUnshaped;
	public static Item ceramicMold;
	public static Item ink;

	//Tool Heads
	public static Item bismuthBronzePickaxeHead;
	public static Item blackBronzePickaxeHead;
	public static Item blackSteelPickaxeHead;
	public static Item blueSteelPickaxeHead;
	public static Item bronzePickaxeHead;
	public static Item copperPickaxeHead;
	public static Item wroughtIronPickaxeHead;
	public static Item redSteelPickaxeHead;
	public static Item steelPickaxeHead;

	public static Item bismuthBronzeShovelHead;
	public static Item blackBronzeShovelHead;
	public static Item blackSteelShovelHead;
	public static Item blueSteelShovelHead;
	public static Item bronzeShovelHead;
	public static Item copperShovelHead;
	public static Item wroughtIronShovelHead;
	public static Item redSteelShovelHead;
	public static Item silverShovelHead;
	public static Item steelShovelHead;

	public static Item bismuthBronzeHoeHead;
	public static Item blackBronzeHoeHead;
	public static Item blackSteelHoeHead;
	public static Item blueSteelHoeHead;
	public static Item bronzeHoeHead;
	public static Item copperHoeHead;
	public static Item wroughtIronHoeHead;
	public static Item redSteelHoeHead;
	public static Item steelHoeHead;

	public static Item bismuthBronzeAxeHead;
	public static Item blackBronzeAxeHead;
	public static Item blackSteelAxeHead;
	public static Item blueSteelAxeHead;
	public static Item bronzeAxeHead;
	public static Item copperAxeHead;
	public static Item wroughtIronAxeHead;
	public static Item redSteelAxeHead;
	public static Item steelAxeHead;

	public static Item bismuthBronzeHammerHead;
	public static Item blackBronzeHammerHead;
	public static Item blackSteelHammerHead;
	public static Item blueSteelHammerHead;
	public static Item bronzeHammerHead;
	public static Item copperHammerHead;
	public static Item wroughtIronHammerHead;
	public static Item redSteelHammerHead;
	public static Item steelHammerHead;

	public static Item bismuthBronzeChiselHead;
	public static Item blackBronzeChiselHead;
	public static Item blackSteelChiselHead;
	public static Item blueSteelChiselHead;
	public static Item bronzeChiselHead;
	public static Item copperChiselHead;
	public static Item wroughtIronChiselHead;
	public static Item redSteelChiselHead;
	public static Item steelChiselHead;

	public static Item bismuthBronzeSwordHead;
	public static Item blackBronzeSwordHead;
	public static Item blackSteelSwordHead;
	public static Item blueSteelSwordHead;
	public static Item bronzeSwordHead;
	public static Item copperSwordHead;
	public static Item wroughtIronSwordHead;
	public static Item redSteelSwordHead;
	public static Item steelSwordHead;

	public static Item bismuthBronzeMaceHead;
	public static Item blackBronzeMaceHead;
	public static Item blackSteelMaceHead;
	public static Item blueSteelMaceHead;
	public static Item bronzeMaceHead;
	public static Item copperMaceHead;
	public static Item wroughtIronMaceHead;
	public static Item redSteelMaceHead;
	public static Item steelMaceHead;

	public static Item bismuthBronzeSawHead;
	public static Item blackBronzeSawHead;
	public static Item blackSteelSawHead;
	public static Item blueSteelSawHead;
	public static Item bronzeSawHead;
	public static Item copperSawHead;
	public static Item wroughtIronSawHead;
	public static Item redSteelSawHead;
	public static Item steelSawHead;

	public static Item bismuthBronzeProPickHead;
	public static Item blackBronzeProPickHead;
	public static Item blackSteelProPickHead;
	public static Item blueSteelProPickHead;
	public static Item bronzeProPickHead;
	public static Item copperProPickHead;
	public static Item wroughtIronProPickHead;
	public static Item redSteelProPickHead;
	public static Item steelProPickHead;

	public static Item bismuthBronzeScytheHead;
	public static Item blackBronzeScytheHead;
	public static Item blackSteelScytheHead;
	public static Item blueSteelScytheHead;
	public static Item bronzeScytheHead;
	public static Item copperScytheHead;
	public static Item wroughtIronScytheHead;
	public static Item redSteelScytheHead;
	public static Item steelScytheHead;

	public static Item bismuthBronzeKnifeHead;
	public static Item blackBronzeKnifeHead;
	public static Item blackSteelKnifeHead;
	public static Item blueSteelKnifeHead;
	public static Item bronzeKnifeHead;
	public static Item copperKnifeHead;
	public static Item wroughtIronKnifeHead;
	public static Item redSteelKnifeHead;
	public static Item steelKnifeHead;

	//public static Item Coke;
	public static Item powder;
	public static Item dye;

	//Formerly TFC_Mining
	public static Item goldPan;
	public static Item sluiceItem;

	public static Item proPickBismuthBronze;
	public static Item proPickBlackBronze;
	public static Item proPickBlackSteel;
	public static Item proPickBlueSteel;
	public static Item proPickBronze;
	public static Item proPickCopper;
	public static Item proPickIron;
	public static Item proPickRedSteel;
	public static Item proPickSteel;

	/**Armor Crafting related items*/
	public static Item bismuthSheet;
	public static Item bismuthBronzeSheet;
	public static Item blackBronzeSheet;
	public static Item blackSteelSheet;
	public static Item blueSteelSheet;
	public static Item bronzeSheet;
	public static Item copperSheet;
	public static Item wroughtIronSheet;
	public static Item redSteelSheet;
	public static Item roseGoldSheet;
	public static Item steelSheet;
	public static Item tinSheet;
	public static Item zincSheet;
	public static Item electrumSheet;
	public static Item cupronickelSheet;
	public static Item osmiumSheet;
	public static Item aluminumSheet;
	public static Item tungstenSheet;

	public static Item brassSheet;
	public static Item goldSheet;
	public static Item leadSheet;
	public static Item nickelSheet;
	public static Item pigIronSheet;
	public static Item platinumSheet;
	public static Item silverSheet;
	public static Item sterlingSilverSheet;

	public static Item bismuthSheet2x;
	public static Item bismuthBronzeSheet2x;
	public static Item blackBronzeSheet2x;
	public static Item blackSteelSheet2x;
	public static Item blueSteelSheet2x;
	public static Item bronzeSheet2x;
	public static Item copperSheet2x;
	public static Item wroughtIronSheet2x;
	public static Item redSteelSheet2x;
	public static Item roseGoldSheet2x;
	public static Item steelSheet2x;
	public static Item tinSheet2x;
	public static Item zincSheet2x;
	public static Item electrumSheet2x;
	public static Item cupronickelSheet2x;
	public static Item osmiumSheet2x;
	public static Item aluminumSheet2x;
	public static Item tungstenSheet2x;

	public static Item brassSheet2x;
	public static Item goldSheet2x;
	public static Item leadSheet2x;
	public static Item nickelSheet2x;
	public static Item pigIronSheet2x;
	public static Item platinumSheet2x;
	public static Item silverSheet2x;
	public static Item sterlingSilverSheet2x;

	public static Item bismuthBronzeUnfinishedChestplate;
	public static Item blackBronzeUnfinishedChestplate;
	public static Item blackSteelUnfinishedChestplate;
	public static Item blueSteelUnfinishedChestplate;
	public static Item bronzeUnfinishedChestplate;
	public static Item copperUnfinishedChestplate;
	public static Item wroughtIronUnfinishedChestplate;
	public static Item redSteelUnfinishedChestplate;
	public static Item steelUnfinishedChestplate;

	public static Item bismuthBronzeUnfinishedGreaves;
	public static Item blackBronzeUnfinishedGreaves;
	public static Item blackSteelUnfinishedGreaves;
	public static Item blueSteelUnfinishedGreaves;
	public static Item bronzeUnfinishedGreaves;
	public static Item copperUnfinishedGreaves;
	public static Item wroughtIronUnfinishedGreaves;
	public static Item redSteelUnfinishedGreaves;
	public static Item steelUnfinishedGreaves;

	public static Item bismuthBronzeUnfinishedBoots;
	public static Item blackBronzeUnfinishedBoots;
	public static Item blackSteelUnfinishedBoots;
	public static Item blueSteelUnfinishedBoots;
	public static Item bronzeUnfinishedBoots;
	public static Item copperUnfinishedBoots;
	public static Item wroughtIronUnfinishedBoots;
	public static Item redSteelUnfinishedBoots;
	public static Item steelUnfinishedBoots;

	public static Item bismuthBronzeUnfinishedHelmet;
	public static Item blackBronzeUnfinishedHelmet;
	public static Item blackSteelUnfinishedHelmet;
	public static Item blueSteelUnfinishedHelmet;
	public static Item bronzeUnfinishedHelmet;
	public static Item copperUnfinishedHelmet;
	public static Item wroughtIronUnfinishedHelmet;
	public static Item redSteelUnfinishedHelmet;
	public static Item steelUnfinishedHelmet;

	public static Item leatherChestplate;
	public static Item bismuthBronzeChestplate;
	public static Item blackBronzeChestplate;
	public static Item blackSteelChestplate;
	public static Item blueSteelChestplate;
	public static Item bronzeChestplate;
	public static Item copperChestplate;
	public static Item wroughtIronChestplate;
	public static Item redSteelChestplate;
	public static Item steelChestplate;

	public static Item leatherLeggings;
	public static Item bismuthBronzeGreaves;
	public static Item blackBronzeGreaves;
	public static Item blackSteelGreaves;
	public static Item blueSteelGreaves;
	public static Item bronzeGreaves;
	public static Item copperGreaves;
	public static Item wroughtIronGreaves;
	public static Item redSteelGreaves;
	public static Item steelGreaves;

	public static Item leatherBoots;
	public static Item bismuthBronzeBoots;
	public static Item blackBronzeBoots;
	public static Item blackSteelBoots;
	public static Item blueSteelBoots;
	public static Item bronzeBoots;
	public static Item copperBoots;
	public static Item wroughtIronBoots;
	public static Item redSteelBoots;
	public static Item steelBoots;

	public static Item leatherHelmet;
	public static Item bismuthBronzeHelmet;
	public static Item blackBronzeHelmet;
	public static Item blackSteelHelmet;
	public static Item blueSteelHelmet;
	public static Item bronzeHelmet;
	public static Item copperHelmet;
	public static Item wroughtIronHelmet;
	public static Item redSteelHelmet;
	public static Item steelHelmet;

	public static Item woodenBucketEmpty;
	public static Item woodenBucketWater;
	public static Item woodenBucketSaltWater;
	public static Item woodenBucketMilk;
	public static Item bottleMilk;

	/**Food Related Items and Blocks*/
	public static Item seedsWheat;
	public static Item seedsMaize;
	public static Item seedsTomato;
	public static Item seedsBarley;
	public static Item seedsRye;
	public static Item seedsOat;
	public static Item seedsRice;
	public static Item seedsPotato;
	public static Item seedsOnion;
	public static Item seedsCabbage;
	public static Item seedsGarlic;
	public static Item seedsCarrot;
	public static Item seedsSugarcane;
	public static Item seedsYellowBellPepper;
	public static Item seedsRedBellPepper;
	public static Item seedsSoybean;
	public static Item seedsGreenbean;
	public static Item seedsSquash;
	public static Item seedsJute;

	//public static Item fruitTreeSapling;

	public static Item redApple;
	public static Item greenApple;
	public static Item banana;
	public static Item orange;
	public static Item lemon;
	public static Item olive;
	public static Item cherry;
	public static Item peach;
	public static Item plum;
	public static Item egg;
	public static Item eggCooked;
	public static Item cheese;

	public static Item wheatGrain;
	public static Item barleyGrain;
	public static Item oatGrain;
	public static Item ryeGrain;
	public static Item riceGrain;
	public static Item maizeEar;
	public static Item tomato;
	public static Item potato;
	public static Item onion;
	public static Item cabbage;
	public static Item garlic;
	public static Item carrot;
	public static Item sugarcane;
	//public static Item Hemp;
	public static Item soybean;
	public static Item greenbeans;
	public static Item greenBellPepper;
	public static Item yellowBellPepper;
	public static Item redBellPepper;
	public static Item squash;
	public static Item seaWeed;
	public static Item sugar;

	public static Item wheatGround;
	public static Item barleyGround;
	public static Item oatGround;
	public static Item ryeGround;
	public static Item riceGround;
	public static Item cornmealGround;

	public static Item wheatDough;
	public static Item barleyDough;
	public static Item oatDough;
	public static Item ryeDough;
	public static Item riceDough;
	public static Item cornmealDough;

	public static Item wheatBread;
	public static Item barleyBread;
	public static Item oatBread;
	public static Item ryeBread;
	public static Item riceBread;
	public static Item cornBread;

	public static Item wheatWhole;
	public static Item barleyWhole;
	public static Item oatWhole;
	public static Item ryeWhole;
	public static Item riceWhole;

	public static Item muttonRaw;
	public static Item calamariRaw;
	public static Item venisonRaw;
	public static Item horseMeatRaw;
	public static Item porkchopRaw;
	public static Item fishRaw;
	public static Item beefRaw;
	public static Item bearRaw;
	public static Item chickenRaw;

	public static Item looseRock;
	public static Item flatRock;

	public static Item igInStoneShovelHead;
	public static Item sedStoneShovelHead;
	public static Item igExStoneShovelHead;
	public static Item mMStoneShovelHead;
	public static Item igInStoneAxeHead;
	public static Item sedStoneAxeHead;
	public static Item igExStoneAxeHead;
	public static Item mMStoneAxeHead;
	public static Item igInStoneHoeHead;
	public static Item sedStoneHoeHead;
	public static Item igExStoneHoeHead;
	public static Item mMStoneHoeHead;

	public static Item stoneKnife;
	public static Item stoneKnifeHead;
	public static Item stoneHammerHead;
	public static Item singlePlank;

	public static Item minecartEmpty;
	public static Item minecartCrate;

	public static Item redSteelBucketEmpty;
	public static Item redSteelBucketWater;
	public static Item redSteelBucketSaltWater;

	public static Item blueSteelBucketEmpty;
	public static Item blueSteelBucketLava;

	public static Item quern;
	public static Item flintSteel;

	public static Item doorOak;
	public static Item doorAspen;
	public static Item doorBirch;
	public static Item doorChestnut;
	public static Item doorDouglasFir;
	public static Item doorHickory;
	public static Item doorMaple;
	public static Item doorAsh;
	public static Item doorPine;
	public static Item doorSequoia;
	public static Item doorSpruce;
	public static Item doorSycamore;
	public static Item doorWhiteCedar;
	public static Item doorWhiteElm;
	public static Item doorWillow;
	public static Item doorKapok;
	public static Item doorAcacia;

	public static Item blueprint;
	public static Item nametag;
	//public static Item writabeBookTFC;
	public static Item woolYarn;
	public static Item wool;
	public static Item woolCloth;
	public static Item silkCloth;
	public static Item burlapCloth;
	public static Item spindle;

	public static Item spindleHead;
	public static Item stoneBrick;
	public static Item mortar;
	public static Item vinegar;
	public static Item brine;
	public static Item oliveOil;
	public static Item limewater;
	public static Item tannin;
	public static Item hide;
	public static Item soakedHide;
	public static Item scrapedHide;
	public static Item prepHide;
	public static Item sheepSkin;
	public static Item pbearSkin;
	public static Item leather;
	public static Item flatLeather;

	public static Item beer;
	public static Item cider;
	public static Item rum;
	public static Item ryeWhiskey;
	public static Item sake;
	public static Item vodka;
	public static Item whiskey;
	public static Item cornWhiskey;

	public static Item glassBottle;
	public static Item potion;

	public static Item clayBall;
	public static Item potteryJug;
	//public static Item PotteryPot;
	public static Item potterySmallVessel;
	public static Item potteryBowl;
	//public static Item KilnRack;
	public static Item straw;
	public static Item flatClay;
	public static Item fireBrick;
	public static Item stick;
	public static Item arrow;
	public static Item rope;

	public static Item clayMoldAxe;
	public static Item clayMoldChisel;
	public static Item clayMoldHammer;
	public static Item clayMoldHoe;
	public static Item clayMoldKnife;
	public static Item clayMoldMace;
	public static Item clayMoldPick;
	public static Item clayMoldProPick;
	public static Item clayMoldSaw;
	public static Item clayMoldScythe;
	public static Item clayMoldShovel;
	public static Item clayMoldSword;
	public static Item clayMoldJavelin;

	public static Item tuyereCopper;
	public static Item tuyereBronze;
	public static Item tuyereBlackBronze;
	public static Item tuyereBismuthBronze;
	public static Item tuyereWroughtIron;
	public static Item tuyereSteel;
	public static Item tuyereBlackSteel;
	public static Item tuyereBlueSteel;
	public static Item tuyereRedSteel;

	public static Item bloom;
	public static Item rawBloom;
	public static Item unknownIngot;
	public static Item unknownUnshaped;

	public static Item quiver;

	public static Item jute;
	public static Item juteFiber;
	public static Item reeds;

	public static Item wintergreenBerry;
	public static Item blueberry;
	public static Item raspberry;
	public static Item strawberry;
	public static Item blackberry;
	public static Item bunchberry;
	public static Item cranberry;
	public static Item snowberry;
	public static Item elderberry;
	public static Item gooseberry;
	public static Item cloudberry;
	/*public static Item WintergreenLeaf;
	public static Item BlueberryLeaf;
	public static Item RaspberryLeaf;
	public static Item StrawberryLeaf;
	public static Item BlackberryLeaf;
	public static Item BunchberryLeaf;
	public static Item CranberryLeaf;
	public static Item SnowberryLeaf;
	public static Item ElderberryLeaf;
	public static Item GooseberryLeaf;
	public static Item CloudberryLeaf;*/
	public static Item fertilizer;
	//public static Item MetalLock;
	//public static Item MudBrick;
	public static Item sandwich;
	//public static Item Soup;
	//public static Item Stew;
	public static Item salad;
	public static Item shears;
	public static Item shearsBlackSteel;

	/**
	 * Item Uses Setup
	 * */
	public static int igInStoneUses = 60;
	public static int igExStoneUses = 70;
	public static int sedStoneUses = 50;
	public static int mMStoneUses = 55;
	//Tier 1
	public static int copperUses = 600;
	//Tier 2
	public static int bronzeUses = 1300;
	public static int bismuthBronzeUses = 1200;
	public static int blackBronzeUses = 1460;
	//Tier 3
	public static int wroughtIronUses = 2200;
	//Tier 4
	public static int steelUses = 3300;
	//Tier 5
	public static int blackSteelUses = 4200;
	//Tier 6
	public static int blueSteelUses = 6500;
	public static int redSteelUses = 6500;

	//Tier 0
	public static float igInStoneEff = 7.0F;
	public static float igExStoneEff = 7.0F;
	public static float sedStoneEff = 6.0F;
	public static float mMStoneEff = 6.5F;
	//Tier 1
	public static float copperEff = 8.0F;
	//Tier 2
	public static float bronzeEff = 11.0F;
	public static float bismuthBronzeEff = 10.0F;
	public static float blackBronzeEff = 9.0F;
	//Tier 3
	public static float wroughtIronEff = 12.0F;
	//Tier 4
	public static float steelEff = 14.0F;
	//Tier 5
	public static float blackSteelEff = 16.0F;
	//Tier 6
	public static float blueSteelEff = 18.0F;
	public static float redSteelEff = 18.0F;

	public static ToolMaterial igInToolMaterial;
	public static ToolMaterial sedToolMaterial;
	public static ToolMaterial igExToolMaterial;
	public static ToolMaterial mMToolMaterial;

	public static ToolMaterial bismuthBronzeToolMaterial;
	public static ToolMaterial blackBronzeToolMaterial;
	public static ToolMaterial blackSteelToolMaterial;
	public static ToolMaterial blueSteelToolMaterial;
	public static ToolMaterial bronzeToolMaterial;
	public static ToolMaterial copperToolMaterial;
	public static ToolMaterial ironToolMaterial;
	public static ToolMaterial redSteelToolMaterial;
	public static ToolMaterial steelToolMaterial;

	public static List<Item> foodList;

	public static void registerItems()
	{
		TerraFirmaCraft.LOG.info("Registering Items");

		GameRegistry.registerItem(goldPan, goldPan.getUnlocalizedName());
		GameRegistry.registerItem(sluiceItem, sluiceItem.getUnlocalizedName());

		GameRegistry.registerItem(proPickBismuthBronze, proPickBismuthBronze.getUnlocalizedName());
		GameRegistry.registerItem(proPickBlackBronze, proPickBlackBronze.getUnlocalizedName());
		GameRegistry.registerItem(proPickBlackSteel, proPickBlackSteel.getUnlocalizedName());
		GameRegistry.registerItem(proPickBlueSteel, proPickBlueSteel.getUnlocalizedName());
		GameRegistry.registerItem(proPickBronze, proPickBronze.getUnlocalizedName());
		GameRegistry.registerItem(proPickCopper, proPickCopper.getUnlocalizedName());
		GameRegistry.registerItem(proPickIron, proPickIron.getUnlocalizedName());
		GameRegistry.registerItem(proPickRedSteel, proPickRedSteel.getUnlocalizedName());
		GameRegistry.registerItem(proPickSteel, proPickSteel.getUnlocalizedName());

		GameRegistry.registerItem(bismuthIngot, bismuthIngot.getUnlocalizedName());
		GameRegistry.registerItem(bismuthBronzeIngot, bismuthBronzeIngot.getUnlocalizedName());
		GameRegistry.registerItem(blackBronzeIngot, blackBronzeIngot.getUnlocalizedName());
		GameRegistry.registerItem(blackSteelIngot, blackSteelIngot.getUnlocalizedName());
		GameRegistry.registerItem(blueSteelIngot, blueSteelIngot.getUnlocalizedName());
		GameRegistry.registerItem(brassIngot, brassIngot.getUnlocalizedName());
		GameRegistry.registerItem(bronzeIngot, bronzeIngot.getUnlocalizedName());
		GameRegistry.registerItem(copperIngot, copperIngot.getUnlocalizedName());
		GameRegistry.registerItem(goldIngot, goldIngot.getUnlocalizedName());
		GameRegistry.registerItem(wroughtIronIngot, wroughtIronIngot.getUnlocalizedName());
		GameRegistry.registerItem(leadIngot, leadIngot.getUnlocalizedName());
		GameRegistry.registerItem(nickelIngot, nickelIngot.getUnlocalizedName());
		GameRegistry.registerItem(pigIronIngot, pigIronIngot.getUnlocalizedName());
		GameRegistry.registerItem(platinumIngot, platinumIngot.getUnlocalizedName());
		GameRegistry.registerItem(redSteelIngot, redSteelIngot.getUnlocalizedName());
		GameRegistry.registerItem(roseGoldIngot, roseGoldIngot.getUnlocalizedName());
		GameRegistry.registerItem(silverIngot, silverIngot.getUnlocalizedName());
		GameRegistry.registerItem(steelIngot, steelIngot.getUnlocalizedName());
		GameRegistry.registerItem(sterlingSilverIngot, sterlingSilverIngot.getUnlocalizedName());
		GameRegistry.registerItem(tinIngot, tinIngot.getUnlocalizedName());
		GameRegistry.registerItem(zincIngot, zincIngot.getUnlocalizedName());
		GameRegistry.registerItem(electrumIngot, electrumIngot.getUnlocalizedName());
		GameRegistry.registerItem(cupronickelIngot, cupronickelIngot.getUnlocalizedName());
		GameRegistry.registerItem(osmiumIngot, osmiumIngot.getUnlocalizedName());
		GameRegistry.registerItem(aluminumIngot, aluminumIngot.getUnlocalizedName());
		GameRegistry.registerItem(tungstenIngot, tungstenIngot.getUnlocalizedName());

		GameRegistry.registerItem(bismuthIngot2x, bismuthIngot2x.getUnlocalizedName());
		GameRegistry.registerItem(bismuthBronzeIngot2x, bismuthBronzeIngot2x.getUnlocalizedName());
		GameRegistry.registerItem(blackBronzeIngot2x, blackBronzeIngot2x.getUnlocalizedName());
		GameRegistry.registerItem(blackSteelIngot2x, blackSteelIngot2x.getUnlocalizedName());
		GameRegistry.registerItem(blueSteelIngot2x, blueSteelIngot2x.getUnlocalizedName());
		GameRegistry.registerItem(brassIngot2x, brassIngot2x.getUnlocalizedName());
		GameRegistry.registerItem(bronzeIngot2x, bronzeIngot2x.getUnlocalizedName());
		GameRegistry.registerItem(copperIngot2x, copperIngot2x.getUnlocalizedName());
		GameRegistry.registerItem(goldIngot2x, goldIngot2x.getUnlocalizedName());
		GameRegistry.registerItem(wroughtIronIngot2x, wroughtIronIngot2x.getUnlocalizedName());
		GameRegistry.registerItem(leadIngot2x, leadIngot2x.getUnlocalizedName());
		GameRegistry.registerItem(nickelIngot2x, nickelIngot2x.getUnlocalizedName());
		GameRegistry.registerItem(pigIronIngot2x, pigIronIngot2x.getUnlocalizedName());
		GameRegistry.registerItem(platinumIngot2x, platinumIngot2x.getUnlocalizedName());
		GameRegistry.registerItem(redSteelIngot2x, redSteelIngot2x.getUnlocalizedName());
		GameRegistry.registerItem(roseGoldIngot2x, roseGoldIngot2x.getUnlocalizedName());
		GameRegistry.registerItem(silverIngot2x, silverIngot2x.getUnlocalizedName());
		GameRegistry.registerItem(steelIngot2x, steelIngot2x.getUnlocalizedName());
		GameRegistry.registerItem(sterlingSilverIngot2x, sterlingSilverIngot2x.getUnlocalizedName());
		GameRegistry.registerItem(tinIngot2x, tinIngot2x.getUnlocalizedName());
		GameRegistry.registerItem(zincIngot2x, zincIngot2x.getUnlocalizedName());
		GameRegistry.registerItem(electrumIngot2x, electrumIngot2x.getUnlocalizedName());
		GameRegistry.registerItem(cupronickelIngot2x, cupronickelIngot2x.getUnlocalizedName());
		GameRegistry.registerItem(osmiumIngot2x, osmiumIngot2x.getUnlocalizedName());
		GameRegistry.registerItem(aluminumIngot2x, aluminumIngot2x.getUnlocalizedName());
		GameRegistry.registerItem(tungstenIngot2x, tungstenIngot2x.getUnlocalizedName());

		GameRegistry.registerItem(gemRuby, gemRuby.getUnlocalizedName());
		GameRegistry.registerItem(gemSapphire, gemSapphire.getUnlocalizedName());
		GameRegistry.registerItem(gemEmerald, gemEmerald.getUnlocalizedName());
		GameRegistry.registerItem(gemTopaz, gemTopaz.getUnlocalizedName());
		GameRegistry.registerItem(gemTourmaline, gemTourmaline.getUnlocalizedName());
		GameRegistry.registerItem(gemJade, gemJade.getUnlocalizedName());
		GameRegistry.registerItem(gemBeryl, gemBeryl.getUnlocalizedName());
		GameRegistry.registerItem(gemAgate, gemAgate.getUnlocalizedName());
		GameRegistry.registerItem(gemOpal, gemOpal.getUnlocalizedName());
		GameRegistry.registerItem(gemGarnet, gemGarnet.getUnlocalizedName());
		GameRegistry.registerItem(gemJasper, gemJasper.getUnlocalizedName());
		GameRegistry.registerItem(gemAmethyst, gemAmethyst.getUnlocalizedName());
		GameRegistry.registerItem(gemDiamond, gemDiamond.getUnlocalizedName());

		GameRegistry.registerItem(igInShovel, igInShovel.getUnlocalizedName());
		GameRegistry.registerItem(igInAxe, igInAxe.getUnlocalizedName());
		GameRegistry.registerItem(igInHoe, igInHoe.getUnlocalizedName());
		GameRegistry.registerItem(sedShovel, sedShovel.getUnlocalizedName());
		GameRegistry.registerItem(sedAxe, sedAxe.getUnlocalizedName());
		GameRegistry.registerItem(sedHoe, sedHoe.getUnlocalizedName());
		GameRegistry.registerItem(igExShovel, igExShovel.getUnlocalizedName());
		GameRegistry.registerItem(igExAxe, igExAxe.getUnlocalizedName());
		GameRegistry.registerItem(igExHoe, igExHoe.getUnlocalizedName());
		GameRegistry.registerItem(mMShovel, mMShovel.getUnlocalizedName());
		GameRegistry.registerItem(mMAxe, mMAxe.getUnlocalizedName());
		GameRegistry.registerItem(mMHoe, mMHoe.getUnlocalizedName());

		GameRegistry.registerItem(bismuthBronzePick, bismuthBronzePick.getUnlocalizedName());
		GameRegistry.registerItem(bismuthBronzeShovel, bismuthBronzeShovel.getUnlocalizedName());
		GameRegistry.registerItem(bismuthBronzeAxe, bismuthBronzeAxe.getUnlocalizedName());
		GameRegistry.registerItem(bismuthBronzeHoe, bismuthBronzeHoe.getUnlocalizedName());

		GameRegistry.registerItem(blackBronzePick, blackBronzePick.getUnlocalizedName());
		GameRegistry.registerItem(blackBronzeShovel, blackBronzeShovel.getUnlocalizedName());
		GameRegistry.registerItem(blackBronzeAxe, blackBronzeAxe.getUnlocalizedName());
		GameRegistry.registerItem(blackBronzeHoe, blackBronzeHoe.getUnlocalizedName());

		GameRegistry.registerItem(blackSteelPick, blackSteelPick.getUnlocalizedName());
		GameRegistry.registerItem(blackSteelShovel, blackSteelShovel.getUnlocalizedName());
		GameRegistry.registerItem(blackSteelAxe, blackSteelAxe.getUnlocalizedName());
		GameRegistry.registerItem(blackSteelHoe, blackSteelHoe.getUnlocalizedName());

		GameRegistry.registerItem(blueSteelPick, blueSteelPick.getUnlocalizedName());
		GameRegistry.registerItem(blueSteelShovel, blueSteelShovel.getUnlocalizedName());
		GameRegistry.registerItem(blueSteelAxe, blueSteelAxe.getUnlocalizedName());
		GameRegistry.registerItem(blueSteelHoe, blueSteelHoe.getUnlocalizedName());

		GameRegistry.registerItem(bronzePick, bronzePick.getUnlocalizedName());
		GameRegistry.registerItem(bronzeShovel, bronzeShovel.getUnlocalizedName());
		GameRegistry.registerItem(bronzeAxe, bronzeAxe.getUnlocalizedName());
		GameRegistry.registerItem(bronzeHoe, bronzeHoe.getUnlocalizedName());

		GameRegistry.registerItem(copperPick, copperPick.getUnlocalizedName());
		GameRegistry.registerItem(copperShovel, copperShovel.getUnlocalizedName());
		GameRegistry.registerItem(copperAxe, copperAxe.getUnlocalizedName());
		GameRegistry.registerItem(copperHoe, copperHoe.getUnlocalizedName());

		GameRegistry.registerItem(wroughtIronPick, wroughtIronPick.getUnlocalizedName());
		GameRegistry.registerItem(wroughtIronShovel, wroughtIronShovel.getUnlocalizedName());
		GameRegistry.registerItem(wroughtIronAxe, wroughtIronAxe.getUnlocalizedName());
		GameRegistry.registerItem(wroughtIronHoe, wroughtIronHoe.getUnlocalizedName());

		GameRegistry.registerItem(redSteelPick, redSteelPick.getUnlocalizedName());
		GameRegistry.registerItem(redSteelShovel, redSteelShovel.getUnlocalizedName());
		GameRegistry.registerItem(redSteelAxe, redSteelAxe.getUnlocalizedName());
		GameRegistry.registerItem(redSteelHoe, redSteelHoe.getUnlocalizedName());

		GameRegistry.registerItem(steelPick, steelPick.getUnlocalizedName());
		GameRegistry.registerItem(steelShovel, steelShovel.getUnlocalizedName());
		GameRegistry.registerItem(steelAxe, steelAxe.getUnlocalizedName());
		GameRegistry.registerItem(steelHoe, steelHoe.getUnlocalizedName());

		GameRegistry.registerItem(bismuthBronzeChisel, bismuthBronzeChisel.getUnlocalizedName());
		GameRegistry.registerItem(blackBronzeChisel, blackBronzeChisel.getUnlocalizedName());
		GameRegistry.registerItem(blackSteelChisel, blackSteelChisel.getUnlocalizedName());
		GameRegistry.registerItem(blueSteelChisel, blueSteelChisel.getUnlocalizedName());
		GameRegistry.registerItem(bronzeChisel, bronzeChisel.getUnlocalizedName());
		GameRegistry.registerItem(copperChisel, copperChisel.getUnlocalizedName());
		GameRegistry.registerItem(wroughtIronChisel, wroughtIronChisel.getUnlocalizedName());
		GameRegistry.registerItem(redSteelChisel, redSteelChisel.getUnlocalizedName());
		GameRegistry.registerItem(steelChisel, steelChisel.getUnlocalizedName());

		GameRegistry.registerItem(bismuthBronzeSword, bismuthBronzeSword.getUnlocalizedName());
		GameRegistry.registerItem(blackBronzeSword, blackBronzeSword.getUnlocalizedName());
		GameRegistry.registerItem(blackSteelSword, blackSteelSword.getUnlocalizedName());
		GameRegistry.registerItem(blueSteelSword, blueSteelSword.getUnlocalizedName());
		GameRegistry.registerItem(bronzeSword, bronzeSword.getUnlocalizedName());
		GameRegistry.registerItem(copperSword, copperSword.getUnlocalizedName());
		GameRegistry.registerItem(wroughtIronSword, wroughtIronSword.getUnlocalizedName());
		GameRegistry.registerItem(redSteelSword, redSteelSword.getUnlocalizedName());
		GameRegistry.registerItem(steelSword, steelSword.getUnlocalizedName());

		GameRegistry.registerItem(bismuthBronzeMace, bismuthBronzeMace.getUnlocalizedName());
		GameRegistry.registerItem(blackBronzeMace, blackBronzeMace.getUnlocalizedName());
		GameRegistry.registerItem(blackSteelMace, blackSteelMace.getUnlocalizedName());
		GameRegistry.registerItem(blueSteelMace, blueSteelMace.getUnlocalizedName());
		GameRegistry.registerItem(bronzeMace, bronzeMace.getUnlocalizedName());
		GameRegistry.registerItem(copperMace, copperMace.getUnlocalizedName());
		GameRegistry.registerItem(wroughtIronMace, wroughtIronMace.getUnlocalizedName());
		GameRegistry.registerItem(redSteelMace, redSteelMace.getUnlocalizedName());
		GameRegistry.registerItem(steelMace, steelMace.getUnlocalizedName());

		GameRegistry.registerItem(bismuthBronzeSaw, bismuthBronzeSaw.getUnlocalizedName());
		GameRegistry.registerItem(blackBronzeSaw, blackBronzeSaw.getUnlocalizedName());
		GameRegistry.registerItem(blackSteelSaw, blackSteelSaw.getUnlocalizedName());
		GameRegistry.registerItem(blueSteelSaw, blueSteelSaw.getUnlocalizedName());
		GameRegistry.registerItem(bronzeSaw, bronzeSaw.getUnlocalizedName());
		GameRegistry.registerItem(copperSaw, copperSaw.getUnlocalizedName());
		GameRegistry.registerItem(wroughtIronSaw, wroughtIronSaw.getUnlocalizedName());
		GameRegistry.registerItem(redSteelSaw, redSteelSaw.getUnlocalizedName());
		GameRegistry.registerItem(steelSaw, steelSaw.getUnlocalizedName());

		GameRegistry.registerItem(highCarbonBlackSteelIngot, highCarbonBlackSteelIngot.getUnlocalizedName());
		GameRegistry.registerItem(weakBlueSteelIngot, weakBlueSteelIngot.getUnlocalizedName());
		GameRegistry.registerItem(weakRedSteelIngot, weakRedSteelIngot.getUnlocalizedName());
		GameRegistry.registerItem(weakSteelIngot, weakSteelIngot.getUnlocalizedName());
		GameRegistry.registerItem(highCarbonBlueSteelIngot, highCarbonBlueSteelIngot.getUnlocalizedName());
		GameRegistry.registerItem(highCarbonRedSteelIngot, highCarbonRedSteelIngot.getUnlocalizedName());
		GameRegistry.registerItem(highCarbonSteelIngot, highCarbonSteelIngot.getUnlocalizedName());

		GameRegistry.registerItem(smallMetalChunk, smallMetalChunk.getUnlocalizedName());
		GameRegistry.registerItem(metalDust, metalDust.getUnlocalizedName());

		GameRegistry.registerItem(oreChunk, oreChunk.getUnlocalizedName());
		GameRegistry.registerItem(oreMineralChunk, oreMineralChunk.getUnlocalizedName());
		GameRegistry.registerItem(smallOreChunk, smallOreChunk.getUnlocalizedName());
		GameRegistry.registerItem(orePile, orePile.getUnlocalizedName());
		GameRegistry.registerItem(logs, logs.getUnlocalizedName());

		GameRegistry.registerItem(igInStoneJavelin, igInStoneJavelin.getUnlocalizedName());
		GameRegistry.registerItem(sedStoneJavelin, sedStoneJavelin.getUnlocalizedName());
		GameRegistry.registerItem(igExStoneJavelin, igExStoneJavelin.getUnlocalizedName());
		GameRegistry.registerItem(mMStoneJavelin, mMStoneJavelin.getUnlocalizedName());
		GameRegistry.registerItem(copperJavelin, copperJavelin.getUnlocalizedName());
		GameRegistry.registerItem(bismuthBronzeJavelin, bismuthBronzeJavelin.getUnlocalizedName());
		GameRegistry.registerItem(bronzeJavelin, bronzeJavelin.getUnlocalizedName());
		GameRegistry.registerItem(blackBronzeJavelin, blackBronzeJavelin.getUnlocalizedName());
		GameRegistry.registerItem(wroughtIronJavelin, wroughtIronJavelin.getUnlocalizedName());
		GameRegistry.registerItem(steelJavelin, steelJavelin.getUnlocalizedName());
		GameRegistry.registerItem(blackSteelJavelin, blackSteelJavelin.getUnlocalizedName());
		GameRegistry.registerItem(blueSteelJavelin, blueSteelJavelin.getUnlocalizedName());
		GameRegistry.registerItem(redSteelJavelin, redSteelJavelin.getUnlocalizedName());

		GameRegistry.registerItem(igInStoneJavelinHead, igInStoneJavelinHead.getUnlocalizedName());
		GameRegistry.registerItem(sedStoneJavelinHead, sedStoneJavelinHead.getUnlocalizedName());
		GameRegistry.registerItem(igExStoneJavelinHead, igExStoneJavelinHead.getUnlocalizedName());
		GameRegistry.registerItem(mMStoneJavelinHead, mMStoneJavelinHead.getUnlocalizedName());
		GameRegistry.registerItem(copperJavelinHead, copperJavelinHead.getUnlocalizedName());
		GameRegistry.registerItem(bismuthBronzeJavelinHead, bismuthBronzeJavelinHead.getUnlocalizedName());
		GameRegistry.registerItem(bronzeJavelinHead, bronzeJavelinHead.getUnlocalizedName());
		GameRegistry.registerItem(blackBronzeJavelinHead, blackBronzeJavelinHead.getUnlocalizedName());
		GameRegistry.registerItem(wroughtIronJavelinHead, wroughtIronJavelinHead.getUnlocalizedName());
		GameRegistry.registerItem(steelJavelinHead, steelJavelinHead.getUnlocalizedName());
		GameRegistry.registerItem(blackSteelJavelinHead, blackSteelJavelinHead.getUnlocalizedName());
		GameRegistry.registerItem(blueSteelJavelinHead, blueSteelJavelinHead.getUnlocalizedName());
		GameRegistry.registerItem(redSteelJavelinHead, redSteelJavelinHead.getUnlocalizedName());

		GameRegistry.registerItem(bismuthUnshaped, bismuthUnshaped.getUnlocalizedName());
		GameRegistry.registerItem(bismuthBronzeUnshaped, bismuthBronzeUnshaped.getUnlocalizedName());
		GameRegistry.registerItem(blackBronzeUnshaped, blackBronzeUnshaped.getUnlocalizedName());
		GameRegistry.registerItem(blackSteelUnshaped, blackSteelUnshaped.getUnlocalizedName());
		GameRegistry.registerItem(blueSteelUnshaped, blueSteelUnshaped.getUnlocalizedName());
		GameRegistry.registerItem(brassUnshaped, brassUnshaped.getUnlocalizedName());
		GameRegistry.registerItem(bronzeUnshaped, bronzeUnshaped.getUnlocalizedName());
		GameRegistry.registerItem(copperUnshaped, copperUnshaped.getUnlocalizedName());
		GameRegistry.registerItem(goldUnshaped, goldUnshaped.getUnlocalizedName());
		GameRegistry.registerItem(wroughtIronUnshaped, wroughtIronUnshaped.getUnlocalizedName());
		GameRegistry.registerItem(leadUnshaped, leadUnshaped.getUnlocalizedName());
		GameRegistry.registerItem(nickelUnshaped, nickelUnshaped.getUnlocalizedName());
		GameRegistry.registerItem(pigIronUnshaped, pigIronUnshaped.getUnlocalizedName());
		GameRegistry.registerItem(platinumUnshaped, platinumUnshaped.getUnlocalizedName());
		GameRegistry.registerItem(redSteelUnshaped, redSteelUnshaped.getUnlocalizedName());
		GameRegistry.registerItem(roseGoldUnshaped, roseGoldUnshaped.getUnlocalizedName());
		GameRegistry.registerItem(silverUnshaped, silverUnshaped.getUnlocalizedName());
		GameRegistry.registerItem(steelUnshaped, steelUnshaped.getUnlocalizedName());
		GameRegistry.registerItem(sterlingSilverUnshaped, sterlingSilverUnshaped.getUnlocalizedName());
		GameRegistry.registerItem(tinUnshaped, tinUnshaped.getUnlocalizedName());
		GameRegistry.registerItem(zincUnshaped, zincUnshaped.getUnlocalizedName());
		GameRegistry.registerItem(electrumUnshaped, electrumUnshaped.getUnlocalizedName());
		GameRegistry.registerItem(cupronickelUnshaped, cupronickelUnshaped.getUnlocalizedName());
		GameRegistry.registerItem(osmiumUnshaped, osmiumUnshaped.getUnlocalizedName());
		GameRegistry.registerItem(aluminumUnshaped, aluminumUnshaped.getUnlocalizedName());
		GameRegistry.registerItem(tungstenUnshaped, tungstenUnshaped.getUnlocalizedName());

		GameRegistry.registerItem(stoneHammer, stoneHammer.getUnlocalizedName());
		GameRegistry.registerItem(bismuthBronzeHammer, bismuthBronzeHammer.getUnlocalizedName());
		GameRegistry.registerItem(blackBronzeHammer, blackBronzeHammer.getUnlocalizedName());
		GameRegistry.registerItem(blackSteelHammer, blackSteelHammer.getUnlocalizedName());
		GameRegistry.registerItem(blueSteelHammer, blueSteelHammer.getUnlocalizedName());
		GameRegistry.registerItem(bronzeHammer, bronzeHammer.getUnlocalizedName());
		GameRegistry.registerItem(copperHammer, copperHammer.getUnlocalizedName());
		GameRegistry.registerItem(wroughtIronHammer, wroughtIronHammer.getUnlocalizedName());
		GameRegistry.registerItem(redSteelHammer, redSteelHammer.getUnlocalizedName());
		GameRegistry.registerItem(steelHammer, steelHammer.getUnlocalizedName());

		GameRegistry.registerItem(ink, ink.getUnlocalizedName());
		GameRegistry.registerItem(fireStarter, fireStarter.getUnlocalizedName());

		GameRegistry.registerItem(bismuthBronzePickaxeHead, bismuthBronzePickaxeHead.getUnlocalizedName());
		GameRegistry.registerItem(blackBronzePickaxeHead, blackBronzePickaxeHead.getUnlocalizedName());
		GameRegistry.registerItem(blackSteelPickaxeHead, blackSteelPickaxeHead.getUnlocalizedName());
		GameRegistry.registerItem(blueSteelPickaxeHead, blueSteelPickaxeHead.getUnlocalizedName());
		GameRegistry.registerItem(bronzePickaxeHead, bronzePickaxeHead.getUnlocalizedName());
		GameRegistry.registerItem(copperPickaxeHead, copperPickaxeHead.getUnlocalizedName());
		GameRegistry.registerItem(wroughtIronPickaxeHead, wroughtIronPickaxeHead.getUnlocalizedName());
		GameRegistry.registerItem(redSteelPickaxeHead, redSteelPickaxeHead.getUnlocalizedName());
		GameRegistry.registerItem(steelPickaxeHead, steelPickaxeHead.getUnlocalizedName());

		GameRegistry.registerItem(bismuthBronzeShovelHead, bismuthBronzeShovelHead.getUnlocalizedName());
		GameRegistry.registerItem(blackBronzeShovelHead, blackBronzeShovelHead.getUnlocalizedName());
		GameRegistry.registerItem(blackSteelShovelHead, blackSteelShovelHead.getUnlocalizedName());
		GameRegistry.registerItem(blueSteelShovelHead, blueSteelShovelHead.getUnlocalizedName());
		GameRegistry.registerItem(bronzeShovelHead, bronzeShovelHead.getUnlocalizedName());
		GameRegistry.registerItem(copperShovelHead, copperShovelHead.getUnlocalizedName());
		GameRegistry.registerItem(wroughtIronShovelHead, wroughtIronShovelHead.getUnlocalizedName());
		GameRegistry.registerItem(redSteelShovelHead, redSteelShovelHead.getUnlocalizedName());
		GameRegistry.registerItem(steelShovelHead, steelShovelHead.getUnlocalizedName());

		GameRegistry.registerItem(bismuthBronzeHoeHead, bismuthBronzeHoeHead.getUnlocalizedName());
		GameRegistry.registerItem(blackBronzeHoeHead, blackBronzeHoeHead.getUnlocalizedName());
		GameRegistry.registerItem(blackSteelHoeHead, blackSteelHoeHead.getUnlocalizedName());
		GameRegistry.registerItem(blueSteelHoeHead, blueSteelHoeHead.getUnlocalizedName());
		GameRegistry.registerItem(bronzeHoeHead, bronzeHoeHead.getUnlocalizedName());
		GameRegistry.registerItem(copperHoeHead, copperHoeHead.getUnlocalizedName());
		GameRegistry.registerItem(wroughtIronHoeHead, wroughtIronHoeHead.getUnlocalizedName());
		GameRegistry.registerItem(redSteelHoeHead, redSteelHoeHead.getUnlocalizedName());
		GameRegistry.registerItem(steelHoeHead, steelHoeHead.getUnlocalizedName());

		GameRegistry.registerItem(bismuthBronzeAxeHead, bismuthBronzeAxeHead.getUnlocalizedName());
		GameRegistry.registerItem(blackBronzeAxeHead, blackBronzeAxeHead.getUnlocalizedName());
		GameRegistry.registerItem(blackSteelAxeHead, blackSteelAxeHead.getUnlocalizedName());
		GameRegistry.registerItem(blueSteelAxeHead, blueSteelAxeHead.getUnlocalizedName());
		GameRegistry.registerItem(bronzeAxeHead, bronzeAxeHead.getUnlocalizedName());
		GameRegistry.registerItem(copperAxeHead, copperAxeHead.getUnlocalizedName());
		GameRegistry.registerItem(wroughtIronAxeHead, wroughtIronAxeHead.getUnlocalizedName());
		GameRegistry.registerItem(redSteelAxeHead, redSteelAxeHead.getUnlocalizedName());
		GameRegistry.registerItem(steelAxeHead, steelAxeHead.getUnlocalizedName());

		GameRegistry.registerItem(bismuthBronzeHammerHead, bismuthBronzeHammerHead.getUnlocalizedName());
		GameRegistry.registerItem(blackBronzeHammerHead, blackBronzeHammerHead.getUnlocalizedName());
		GameRegistry.registerItem(blackSteelHammerHead, blackSteelHammerHead.getUnlocalizedName());
		GameRegistry.registerItem(blueSteelHammerHead, blueSteelHammerHead.getUnlocalizedName());
		GameRegistry.registerItem(bronzeHammerHead, bronzeHammerHead.getUnlocalizedName());
		GameRegistry.registerItem(copperHammerHead, copperHammerHead.getUnlocalizedName());
		GameRegistry.registerItem(wroughtIronHammerHead, wroughtIronHammerHead.getUnlocalizedName());
		GameRegistry.registerItem(redSteelHammerHead, redSteelHammerHead.getUnlocalizedName());
		GameRegistry.registerItem(steelHammerHead, steelHammerHead.getUnlocalizedName());

		GameRegistry.registerItem(bismuthBronzeChiselHead, bismuthBronzeChiselHead.getUnlocalizedName());
		GameRegistry.registerItem(blackBronzeChiselHead, blackBronzeChiselHead.getUnlocalizedName());
		GameRegistry.registerItem(blackSteelChiselHead, blackSteelChiselHead.getUnlocalizedName());
		GameRegistry.registerItem(blueSteelChiselHead, blueSteelChiselHead.getUnlocalizedName());
		GameRegistry.registerItem(bronzeChiselHead, bronzeChiselHead.getUnlocalizedName());
		GameRegistry.registerItem(copperChiselHead, copperChiselHead.getUnlocalizedName());
		GameRegistry.registerItem(wroughtIronChiselHead, wroughtIronChiselHead.getUnlocalizedName());
		GameRegistry.registerItem(redSteelChiselHead, redSteelChiselHead.getUnlocalizedName());
		GameRegistry.registerItem(steelChiselHead, steelChiselHead.getUnlocalizedName());

		GameRegistry.registerItem(bismuthBronzeSwordHead, bismuthBronzeSwordHead.getUnlocalizedName());
		GameRegistry.registerItem(blackBronzeSwordHead, blackBronzeSwordHead.getUnlocalizedName());
		GameRegistry.registerItem(blackSteelSwordHead, blackSteelSwordHead.getUnlocalizedName());
		GameRegistry.registerItem(blueSteelSwordHead, blueSteelSwordHead.getUnlocalizedName());
		GameRegistry.registerItem(bronzeSwordHead, bronzeSwordHead.getUnlocalizedName());
		GameRegistry.registerItem(copperSwordHead, copperSwordHead.getUnlocalizedName());
		GameRegistry.registerItem(wroughtIronSwordHead, wroughtIronSwordHead.getUnlocalizedName());
		GameRegistry.registerItem(redSteelSwordHead, redSteelSwordHead.getUnlocalizedName());
		GameRegistry.registerItem(steelSwordHead, steelSwordHead.getUnlocalizedName());

		GameRegistry.registerItem(bismuthBronzeMaceHead, bismuthBronzeMaceHead.getUnlocalizedName());
		GameRegistry.registerItem(blackBronzeMaceHead, blackBronzeMaceHead.getUnlocalizedName());
		GameRegistry.registerItem(blackSteelMaceHead, blackSteelMaceHead.getUnlocalizedName());
		GameRegistry.registerItem(blueSteelMaceHead, blueSteelMaceHead.getUnlocalizedName());
		GameRegistry.registerItem(bronzeMaceHead, bronzeMaceHead.getUnlocalizedName());
		GameRegistry.registerItem(copperMaceHead, copperMaceHead.getUnlocalizedName());
		GameRegistry.registerItem(wroughtIronMaceHead, wroughtIronMaceHead.getUnlocalizedName());
		GameRegistry.registerItem(redSteelMaceHead, redSteelMaceHead.getUnlocalizedName());
		GameRegistry.registerItem(steelMaceHead, steelMaceHead.getUnlocalizedName());

		GameRegistry.registerItem(bismuthBronzeSawHead, bismuthBronzeSawHead.getUnlocalizedName());
		GameRegistry.registerItem(blackBronzeSawHead, blackBronzeSawHead.getUnlocalizedName());
		GameRegistry.registerItem(blackSteelSawHead, blackSteelSawHead.getUnlocalizedName());
		GameRegistry.registerItem(blueSteelSawHead, blueSteelSawHead.getUnlocalizedName());
		GameRegistry.registerItem(bronzeSawHead, bronzeSawHead.getUnlocalizedName());
		GameRegistry.registerItem(copperSawHead, copperSawHead.getUnlocalizedName());
		GameRegistry.registerItem(wroughtIronSawHead, wroughtIronSawHead.getUnlocalizedName());
		GameRegistry.registerItem(redSteelSawHead, redSteelSawHead.getUnlocalizedName());
		GameRegistry.registerItem(steelSawHead, steelSawHead.getUnlocalizedName());

		GameRegistry.registerItem(highCarbonBlackSteelUnshaped, highCarbonBlackSteelUnshaped.getUnlocalizedName());
		GameRegistry.registerItem(weakBlueSteelUnshaped, weakBlueSteelUnshaped.getUnlocalizedName());
		GameRegistry.registerItem(highCarbonBlueSteelUnshaped, highCarbonBlueSteelUnshaped.getUnlocalizedName());
		GameRegistry.registerItem(weakRedSteelUnshaped, weakRedSteelUnshaped.getUnlocalizedName());
		GameRegistry.registerItem(highCarbonRedSteelUnshaped, highCarbonRedSteelUnshaped.getUnlocalizedName());
		GameRegistry.registerItem(weakSteelUnshaped, weakSteelUnshaped.getUnlocalizedName());
		GameRegistry.registerItem(highCarbonSteelUnshaped, highCarbonSteelUnshaped.getUnlocalizedName());

		//GameRegistry.registerItem(Coke, Coke.getUnlocalizedName());

		GameRegistry.registerItem(bismuthBronzeProPickHead, bismuthBronzeProPickHead.getUnlocalizedName());
		GameRegistry.registerItem(blackBronzeProPickHead, blackBronzeProPickHead.getUnlocalizedName());
		GameRegistry.registerItem(blackSteelProPickHead, blackSteelProPickHead.getUnlocalizedName());
		GameRegistry.registerItem(blueSteelProPickHead, blueSteelProPickHead.getUnlocalizedName());
		GameRegistry.registerItem(bronzeProPickHead, bronzeProPickHead.getUnlocalizedName());
		GameRegistry.registerItem(copperProPickHead, copperProPickHead.getUnlocalizedName());
		GameRegistry.registerItem(wroughtIronProPickHead, wroughtIronProPickHead.getUnlocalizedName());
		GameRegistry.registerItem(redSteelProPickHead, redSteelProPickHead.getUnlocalizedName());
		GameRegistry.registerItem(steelProPickHead, steelProPickHead.getUnlocalizedName());

		GameRegistry.registerItem(bismuthBronzeScythe, bismuthBronzeScythe.getUnlocalizedName());
		GameRegistry.registerItem(blackBronzeScythe, blackBronzeScythe.getUnlocalizedName());
		GameRegistry.registerItem(blackSteelScythe, blackSteelScythe.getUnlocalizedName());
		GameRegistry.registerItem(blueSteelScythe, blueSteelScythe.getUnlocalizedName());
		GameRegistry.registerItem(bronzeScythe, bronzeScythe.getUnlocalizedName());
		GameRegistry.registerItem(copperScythe, copperScythe.getUnlocalizedName());
		GameRegistry.registerItem(wroughtIronScythe, wroughtIronScythe.getUnlocalizedName());
		GameRegistry.registerItem(redSteelScythe, redSteelScythe.getUnlocalizedName());
		GameRegistry.registerItem(steelScythe, steelScythe.getUnlocalizedName());

		GameRegistry.registerItem(bismuthBronzeScytheHead, bismuthBronzeScytheHead.getUnlocalizedName());
		GameRegistry.registerItem(blackBronzeScytheHead, blackBronzeScytheHead.getUnlocalizedName());
		GameRegistry.registerItem(blackSteelScytheHead, blackSteelScytheHead.getUnlocalizedName());
		GameRegistry.registerItem(blueSteelScytheHead, blueSteelScytheHead.getUnlocalizedName());
		GameRegistry.registerItem(bronzeScytheHead, bronzeScytheHead.getUnlocalizedName());
		GameRegistry.registerItem(copperScytheHead, copperScytheHead.getUnlocalizedName());
		GameRegistry.registerItem(wroughtIronScytheHead, wroughtIronScytheHead.getUnlocalizedName());
		GameRegistry.registerItem(redSteelScytheHead, redSteelScytheHead.getUnlocalizedName());
		GameRegistry.registerItem(steelScytheHead, steelScytheHead.getUnlocalizedName());

		GameRegistry.registerItem(woodenBucketEmpty, woodenBucketEmpty.getUnlocalizedName());
		GameRegistry.registerItem(woodenBucketWater, woodenBucketWater.getUnlocalizedName());
		GameRegistry.registerItem(woodenBucketSaltWater, woodenBucketSaltWater.getUnlocalizedName());
		GameRegistry.registerItem(woodenBucketMilk, woodenBucketMilk.getUnlocalizedName());
		GameRegistry.registerItem(bottleMilk, bottleMilk.getUnlocalizedName());

		GameRegistry.registerItem(bismuthBronzeKnifeHead, bismuthBronzeKnifeHead.getUnlocalizedName());
		GameRegistry.registerItem(blackBronzeKnifeHead, blackBronzeKnifeHead.getUnlocalizedName());
		GameRegistry.registerItem(blackSteelKnifeHead, blackSteelKnifeHead.getUnlocalizedName());
		GameRegistry.registerItem(blueSteelKnifeHead, blueSteelKnifeHead.getUnlocalizedName());
		GameRegistry.registerItem(bronzeKnifeHead, bronzeKnifeHead.getUnlocalizedName());
		GameRegistry.registerItem(copperKnifeHead, copperKnifeHead.getUnlocalizedName());
		GameRegistry.registerItem(wroughtIronKnifeHead, wroughtIronKnifeHead.getUnlocalizedName());
		GameRegistry.registerItem(redSteelKnifeHead, redSteelKnifeHead.getUnlocalizedName());
		GameRegistry.registerItem(steelKnifeHead, steelKnifeHead.getUnlocalizedName());

		GameRegistry.registerItem(bismuthBronzeKnife, bismuthBronzeKnife.getUnlocalizedName());
		GameRegistry.registerItem(blackBronzeKnife, blackBronzeKnife.getUnlocalizedName());
		GameRegistry.registerItem(blackSteelKnife, blackSteelKnife.getUnlocalizedName());
		GameRegistry.registerItem(blueSteelKnife, blueSteelKnife.getUnlocalizedName());
		GameRegistry.registerItem(bronzeKnife, bronzeKnife.getUnlocalizedName());
		GameRegistry.registerItem(copperKnife, copperKnife.getUnlocalizedName());
		GameRegistry.registerItem(wroughtIronKnife, wroughtIronKnife.getUnlocalizedName());
		GameRegistry.registerItem(redSteelKnife, redSteelKnife.getUnlocalizedName());
		GameRegistry.registerItem(steelKnife, steelKnife.getUnlocalizedName());

		GameRegistry.registerItem(flatRock, flatRock.getUnlocalizedName());
		GameRegistry.registerItem(looseRock, looseRock.getUnlocalizedName());

		GameRegistry.registerItem(igInStoneShovelHead, igInStoneShovelHead.getUnlocalizedName());
		GameRegistry.registerItem(sedStoneShovelHead, sedStoneShovelHead.getUnlocalizedName());
		GameRegistry.registerItem(igExStoneShovelHead, igExStoneShovelHead.getUnlocalizedName());
		GameRegistry.registerItem(mMStoneShovelHead, mMStoneShovelHead.getUnlocalizedName());

		GameRegistry.registerItem(igInStoneAxeHead, igInStoneAxeHead.getUnlocalizedName());
		GameRegistry.registerItem(sedStoneAxeHead, sedStoneAxeHead.getUnlocalizedName());
		GameRegistry.registerItem(igExStoneAxeHead, igExStoneAxeHead.getUnlocalizedName());
		GameRegistry.registerItem(mMStoneAxeHead, mMStoneAxeHead.getUnlocalizedName());

		GameRegistry.registerItem(igInStoneHoeHead, igInStoneHoeHead.getUnlocalizedName());
		GameRegistry.registerItem(sedStoneHoeHead, sedStoneHoeHead.getUnlocalizedName());
		GameRegistry.registerItem(igExStoneHoeHead, igExStoneHoeHead.getUnlocalizedName());
		GameRegistry.registerItem(mMStoneHoeHead, mMStoneHoeHead.getUnlocalizedName());

		GameRegistry.registerItem(stoneKnifeHead, stoneKnifeHead.getUnlocalizedName());
		GameRegistry.registerItem(stoneHammerHead, stoneHammerHead.getUnlocalizedName());
		GameRegistry.registerItem(stoneKnife, stoneKnife.getUnlocalizedName());

		GameRegistry.registerItem(singlePlank, singlePlank.getUnlocalizedName());

		GameRegistry.registerItem(redSteelBucketEmpty, redSteelBucketEmpty.getUnlocalizedName());
		GameRegistry.registerItem(redSteelBucketWater, redSteelBucketWater.getUnlocalizedName());
		GameRegistry.registerItem(redSteelBucketSaltWater, redSteelBucketSaltWater.getUnlocalizedName());
		GameRegistry.registerItem(blueSteelBucketEmpty, blueSteelBucketEmpty.getUnlocalizedName());
		GameRegistry.registerItem(blueSteelBucketLava, blueSteelBucketLava.getUnlocalizedName());

		GameRegistry.registerItem(quern, quern.getUnlocalizedName());
		GameRegistry.registerItem(flintSteel, flintSteel.getUnlocalizedName());

		GameRegistry.registerItem(doorOak, doorOak.getUnlocalizedName());
		GameRegistry.registerItem(doorAspen, doorAspen.getUnlocalizedName());
		GameRegistry.registerItem(doorBirch, doorBirch.getUnlocalizedName());
		GameRegistry.registerItem(doorChestnut, doorChestnut.getUnlocalizedName());
		GameRegistry.registerItem(doorDouglasFir, doorDouglasFir.getUnlocalizedName());
		GameRegistry.registerItem(doorHickory, doorHickory.getUnlocalizedName());
		GameRegistry.registerItem(doorMaple, doorMaple.getUnlocalizedName());
		GameRegistry.registerItem(doorAsh, doorAsh.getUnlocalizedName());
		GameRegistry.registerItem(doorPine, doorPine.getUnlocalizedName());
		GameRegistry.registerItem(doorSequoia, doorSequoia.getUnlocalizedName());
		GameRegistry.registerItem(doorSpruce, doorSpruce.getUnlocalizedName());
		GameRegistry.registerItem(doorSycamore, doorSycamore.getUnlocalizedName());
		GameRegistry.registerItem(doorWhiteCedar, doorWhiteCedar.getUnlocalizedName());
		GameRegistry.registerItem(doorWhiteElm, doorWhiteElm.getUnlocalizedName());
		GameRegistry.registerItem(doorWillow, doorWillow.getUnlocalizedName());
		GameRegistry.registerItem(doorKapok, doorKapok.getUnlocalizedName());
		GameRegistry.registerItem(doorAcacia, doorAcacia.getUnlocalizedName());

		//GameRegistry.registerItem(glassBottle, glassBottle.getUnlocalizedName());
		GameRegistry.registerItem(beer, beer.getUnlocalizedName());
		GameRegistry.registerItem(cider, cider.getUnlocalizedName());
		GameRegistry.registerItem(rum, rum.getUnlocalizedName());
		GameRegistry.registerItem(ryeWhiskey, ryeWhiskey.getUnlocalizedName());
		GameRegistry.registerItem(sake, sake.getUnlocalizedName());
		GameRegistry.registerItem(vodka, vodka.getUnlocalizedName());
		GameRegistry.registerItem(whiskey, whiskey.getUnlocalizedName());
		GameRegistry.registerItem(cornWhiskey, cornWhiskey.getUnlocalizedName());

		GameRegistry.registerItem(blueprint, blueprint.getUnlocalizedName());
		GameRegistry.registerItem(nametag, nametag.getUnlocalizedName());
		//GameRegistry.registerItem(writabeBookTFC, writabeBookTFC.getUnlocalizedName());
		GameRegistry.registerItem(woolYarn, woolYarn.getUnlocalizedName());
		GameRegistry.registerItem(wool, wool.getUnlocalizedName());
		GameRegistry.registerItem(woolCloth, woolCloth.getUnlocalizedName());
		GameRegistry.registerItem(silkCloth, silkCloth.getUnlocalizedName());
		GameRegistry.registerItem(burlapCloth, burlapCloth.getUnlocalizedName());
		GameRegistry.registerItem(spindle, spindle.getUnlocalizedName());
		GameRegistry.registerItem(spindleHead, spindleHead.getUnlocalizedName());
		GameRegistry.registerItem(stoneBrick , stoneBrick.getUnlocalizedName());

		GameRegistry.registerItem(mortar , mortar.getUnlocalizedName());
		GameRegistry.registerItem(vinegar , vinegar.getUnlocalizedName());
		GameRegistry.registerItem(brine , brine.getUnlocalizedName());
		GameRegistry.registerItem(oliveOil , oliveOil.getUnlocalizedName());
		GameRegistry.registerItem(limewater , limewater.getUnlocalizedName());
		GameRegistry.registerItem(tannin , tannin.getUnlocalizedName());

		GameRegistry.registerItem(hide , hide.getUnlocalizedName());
		GameRegistry.registerItem(soakedHide , soakedHide.getUnlocalizedName());
		GameRegistry.registerItem(scrapedHide , scrapedHide.getUnlocalizedName());
		GameRegistry.registerItem(prepHide , prepHide.getUnlocalizedName());
		GameRegistry.registerItem(sheepSkin , sheepSkin.getUnlocalizedName());
		GameRegistry.registerItem(pbearSkin , pbearSkin.getUnlocalizedName());
		GameRegistry.registerItem(flatLeather , flatLeather.getUnlocalizedName());
		GameRegistry.registerItem(leather , leather.getUnlocalizedName());
		GameRegistry.registerItem(straw , straw.getUnlocalizedName());
		GameRegistry.registerItem(flatClay , flatClay.getUnlocalizedName());

		GameRegistry.registerItem(potteryJug , potteryJug.getUnlocalizedName());
		GameRegistry.registerItem(potterySmallVessel , potterySmallVessel.getUnlocalizedName());
		GameRegistry.registerItem(potteryBowl, potteryBowl.getUnlocalizedName());
		//GameRegistry.registerItem(PotteryPot , PotteryPot.getUnlocalizedName());

		GameRegistry.registerItem(ceramicMold , ceramicMold.getUnlocalizedName());
		GameRegistry.registerItem(fireBrick , fireBrick.getUnlocalizedName());

		GameRegistry.registerItem(clayMoldAxe , clayMoldAxe.getUnlocalizedName());
		GameRegistry.registerItem(clayMoldChisel , clayMoldChisel.getUnlocalizedName());
		GameRegistry.registerItem(clayMoldHammer , clayMoldHammer.getUnlocalizedName());
		GameRegistry.registerItem(clayMoldHoe , clayMoldHoe.getUnlocalizedName());
		GameRegistry.registerItem(clayMoldKnife , clayMoldKnife.getUnlocalizedName());
		GameRegistry.registerItem(clayMoldMace , clayMoldMace.getUnlocalizedName());
		GameRegistry.registerItem(clayMoldPick , clayMoldPick.getUnlocalizedName());
		GameRegistry.registerItem(clayMoldProPick , clayMoldProPick.getUnlocalizedName());
		GameRegistry.registerItem(clayMoldSaw , clayMoldSaw.getUnlocalizedName());
		GameRegistry.registerItem(clayMoldScythe , clayMoldScythe.getUnlocalizedName());
		GameRegistry.registerItem(clayMoldShovel , clayMoldShovel.getUnlocalizedName());
		GameRegistry.registerItem(clayMoldSword , clayMoldSword.getUnlocalizedName());
		GameRegistry.registerItem(clayMoldJavelin , clayMoldJavelin.getUnlocalizedName());

		GameRegistry.registerItem(tuyereCopper , tuyereCopper.getUnlocalizedName());
		GameRegistry.registerItem(tuyereBronze , tuyereBronze.getUnlocalizedName());
		GameRegistry.registerItem(tuyereBlackBronze , tuyereBlackBronze.getUnlocalizedName());
		GameRegistry.registerItem(tuyereBismuthBronze , tuyereBismuthBronze.getUnlocalizedName());
		GameRegistry.registerItem(tuyereWroughtIron , tuyereWroughtIron.getUnlocalizedName());
		GameRegistry.registerItem(tuyereSteel , tuyereSteel.getUnlocalizedName());
		GameRegistry.registerItem(tuyereBlackSteel , tuyereBlackSteel.getUnlocalizedName());
		GameRegistry.registerItem(tuyereRedSteel , tuyereRedSteel.getUnlocalizedName());
		GameRegistry.registerItem(tuyereBlueSteel , tuyereBlueSteel.getUnlocalizedName());

		GameRegistry.registerItem(bloom , bloom.getUnlocalizedName());
		GameRegistry.registerItem(rawBloom , rawBloom.getUnlocalizedName());

		GameRegistry.registerItem(unknownIngot , unknownIngot.getUnlocalizedName());
		GameRegistry.registerItem(unknownUnshaped , unknownUnshaped.getUnlocalizedName());

		GameRegistry.registerItem(jute , jute.getUnlocalizedName());
		GameRegistry.registerItem(juteFiber , juteFiber.getUnlocalizedName());
		GameRegistry.registerItem(reeds , reeds.getUnlocalizedName());

		GameRegistry.registerItem(fishingRod, fishingRod.getUnlocalizedName());
		GameRegistry.registerItem(coal, coal.getUnlocalizedName());
		GameRegistry.registerItem(stick, stick.getUnlocalizedName());
		GameRegistry.registerItem(bow, bow.getUnlocalizedName());
		GameRegistry.registerItem(arrow, arrow.getUnlocalizedName());
		GameRegistry.registerItem(dye, dye.getUnlocalizedName());
		GameRegistry.registerItem(rope, rope.getUnlocalizedName());
		GameRegistry.registerItem(clayBall, clayBall.getUnlocalizedName());
		GameRegistry.registerItem(powder, powder.getUnlocalizedName());
		GameRegistry.registerItem(fertilizer, fertilizer.getUnlocalizedName());


		TerraFirmaCraft.LOG.info("Registering Food");
		//GameRegistry.registerItem(fruitTreeSapling, fruitTreeSapling.getUnlocalizedName());
		GameRegistry.registerItem(redApple, redApple.getUnlocalizedName());
		GameRegistry.registerItem(banana, banana.getUnlocalizedName());
		GameRegistry.registerItem(orange, orange.getUnlocalizedName());
		GameRegistry.registerItem(greenApple, greenApple.getUnlocalizedName());
		GameRegistry.registerItem(lemon, lemon.getUnlocalizedName());
		GameRegistry.registerItem(olive, olive.getUnlocalizedName());
		GameRegistry.registerItem(cherry, cherry.getUnlocalizedName());
		GameRegistry.registerItem(peach, peach.getUnlocalizedName());
		GameRegistry.registerItem(plum, plum.getUnlocalizedName());
		GameRegistry.registerItem(egg, egg.getUnlocalizedName());
		GameRegistry.registerItem(eggCooked, eggCooked.getUnlocalizedName());
		GameRegistry.registerItem(wheatGrain, wheatGrain.getUnlocalizedName());
		GameRegistry.registerItem(barleyGrain, barleyGrain.getUnlocalizedName());
		GameRegistry.registerItem(oatGrain, oatGrain.getUnlocalizedName());
		GameRegistry.registerItem(ryeGrain, ryeGrain.getUnlocalizedName());
		GameRegistry.registerItem(riceGrain, riceGrain.getUnlocalizedName());
		GameRegistry.registerItem(maizeEar, maizeEar.getUnlocalizedName());
		GameRegistry.registerItem(tomato, tomato.getUnlocalizedName());
		GameRegistry.registerItem(potato, potato.getUnlocalizedName());
		GameRegistry.registerItem(onion, onion.getUnlocalizedName());
		GameRegistry.registerItem(cabbage, cabbage.getUnlocalizedName());
		GameRegistry.registerItem(garlic, garlic.getUnlocalizedName());
		GameRegistry.registerItem(carrot, carrot.getUnlocalizedName());
		GameRegistry.registerItem(sugarcane, sugarcane.getUnlocalizedName());
		//GameRegistry.registerItem(Hemp, Hemp.getUnlocalizedName());
		GameRegistry.registerItem(soybean, soybean.getUnlocalizedName());
		GameRegistry.registerItem(greenbeans, greenbeans.getUnlocalizedName());
		GameRegistry.registerItem(greenBellPepper, greenBellPepper.getUnlocalizedName());
		GameRegistry.registerItem(yellowBellPepper, yellowBellPepper.getUnlocalizedName());
		GameRegistry.registerItem(redBellPepper, redBellPepper.getUnlocalizedName());
		GameRegistry.registerItem(squash, squash.getUnlocalizedName());
		GameRegistry.registerItem(cheese, cheese.getUnlocalizedName());
		GameRegistry.registerItem(wheatWhole, wheatWhole.getUnlocalizedName());
		GameRegistry.registerItem(barleyWhole, barleyWhole.getUnlocalizedName());
		GameRegistry.registerItem(oatWhole, oatWhole.getUnlocalizedName());
		GameRegistry.registerItem(ryeWhole, ryeWhole.getUnlocalizedName());
		GameRegistry.registerItem(riceWhole, riceWhole.getUnlocalizedName());
		GameRegistry.registerItem(wheatGround, wheatGround.getUnlocalizedName());
		GameRegistry.registerItem(barleyGround, barleyGround.getUnlocalizedName());
		GameRegistry.registerItem(oatGround, oatGround.getUnlocalizedName());
		GameRegistry.registerItem(ryeGround, ryeGround.getUnlocalizedName());
		GameRegistry.registerItem(riceGround, riceGround.getUnlocalizedName());
		GameRegistry.registerItem(cornmealGround, cornmealGround.getUnlocalizedName());
		GameRegistry.registerItem(wheatDough, wheatDough.getUnlocalizedName());
		GameRegistry.registerItem(barleyDough, barleyDough.getUnlocalizedName());
		GameRegistry.registerItem(oatDough, oatDough.getUnlocalizedName());
		GameRegistry.registerItem(ryeDough, ryeDough.getUnlocalizedName());
		GameRegistry.registerItem(riceDough, riceDough.getUnlocalizedName());
		GameRegistry.registerItem(cornmealDough, cornmealDough.getUnlocalizedName());
		GameRegistry.registerItem(wheatBread, wheatBread.getUnlocalizedName());
		GameRegistry.registerItem(barleyBread, barleyBread.getUnlocalizedName());
		GameRegistry.registerItem(oatBread, oatBread.getUnlocalizedName());
		GameRegistry.registerItem(ryeBread, ryeBread.getUnlocalizedName());
		GameRegistry.registerItem(riceBread, riceBread.getUnlocalizedName());
		GameRegistry.registerItem(cornBread, cornBread.getUnlocalizedName());
		GameRegistry.registerItem(calamariRaw, calamariRaw.getUnlocalizedName());
		GameRegistry.registerItem(seedsWheat, seedsWheat.getUnlocalizedName());
		GameRegistry.registerItem(seedsBarley, seedsBarley.getUnlocalizedName());
		GameRegistry.registerItem(seedsRye, seedsRye.getUnlocalizedName());
		GameRegistry.registerItem(seedsOat, seedsOat.getUnlocalizedName());
		GameRegistry.registerItem(seedsRice, seedsRice.getUnlocalizedName());
		GameRegistry.registerItem(seedsMaize, seedsMaize.getUnlocalizedName());
		GameRegistry.registerItem(seedsPotato, seedsPotato.getUnlocalizedName());
		GameRegistry.registerItem(seedsOnion, seedsOnion.getUnlocalizedName());
		GameRegistry.registerItem(seedsCabbage, seedsCabbage.getUnlocalizedName());
		GameRegistry.registerItem(seedsGarlic, seedsGarlic.getUnlocalizedName());
		GameRegistry.registerItem(seedsCarrot, seedsCarrot.getUnlocalizedName());
		GameRegistry.registerItem(seedsSugarcane, seedsSugarcane.getUnlocalizedName());
		//GameRegistry.registerItem(SeedsHemp, SeedsHemp.getUnlocalizedName());
		GameRegistry.registerItem(seedsTomato, seedsTomato.getUnlocalizedName());
		GameRegistry.registerItem(seedsYellowBellPepper, seedsYellowBellPepper.getUnlocalizedName());
		GameRegistry.registerItem(seedsRedBellPepper, seedsRedBellPepper.getUnlocalizedName());
		GameRegistry.registerItem(seedsSoybean, seedsSoybean.getUnlocalizedName());
		GameRegistry.registerItem(seedsGreenbean, seedsGreenbean.getUnlocalizedName());
		GameRegistry.registerItem(seedsSquash, seedsSquash.getUnlocalizedName());
		GameRegistry.registerItem(seedsJute, seedsJute.getUnlocalizedName());
		GameRegistry.registerItem(muttonRaw, muttonRaw.getUnlocalizedName());
		GameRegistry.registerItem(venisonRaw, venisonRaw.getUnlocalizedName());
		GameRegistry.registerItem(horseMeatRaw, horseMeatRaw.getUnlocalizedName());
		GameRegistry.registerItem(porkchopRaw, porkchopRaw.getUnlocalizedName());
		GameRegistry.registerItem(fishRaw, fishRaw.getUnlocalizedName());
		GameRegistry.registerItem(beefRaw, beefRaw.getUnlocalizedName());
		GameRegistry.registerItem(bearRaw, bearRaw.getUnlocalizedName());
		GameRegistry.registerItem(chickenRaw, chickenRaw.getUnlocalizedName());
		GameRegistry.registerItem(seaWeed, seaWeed.getUnlocalizedName());

		GameRegistry.registerItem(wintergreenBerry, wintergreenBerry.getUnlocalizedName());
		GameRegistry.registerItem(blueberry, blueberry.getUnlocalizedName());
		GameRegistry.registerItem(raspberry, raspberry.getUnlocalizedName());
		GameRegistry.registerItem(strawberry, strawberry.getUnlocalizedName());
		GameRegistry.registerItem(blackberry, blackberry.getUnlocalizedName());
		GameRegistry.registerItem(bunchberry, bunchberry.getUnlocalizedName());
		GameRegistry.registerItem(cranberry, cranberry.getUnlocalizedName());
		GameRegistry.registerItem(snowberry, snowberry.getUnlocalizedName());
		GameRegistry.registerItem(elderberry, elderberry.getUnlocalizedName());
		GameRegistry.registerItem(gooseberry, gooseberry.getUnlocalizedName());
		GameRegistry.registerItem(cloudberry, cloudberry.getUnlocalizedName());
		/*GameRegistry.registerItem(WintergreenLeaf, WintergreenLeaf.getUnlocalizedName());
		GameRegistry.registerItem(BlueberryLeaf, BlueberryLeaf.getUnlocalizedName());
		GameRegistry.registerItem(RaspberryLeaf, RaspberryLeaf.getUnlocalizedName());
		GameRegistry.registerItem(StrawberryLeaf, StrawberryLeaf.getUnlocalizedName());
		GameRegistry.registerItem(BlackberryLeaf, BlackberryLeaf.getUnlocalizedName());
		GameRegistry.registerItem(BunchberryLeaf, BunchberryLeaf.getUnlocalizedName());
		GameRegistry.registerItem(CranberryLeaf, CranberryLeaf.getUnlocalizedName());
		GameRegistry.registerItem(SnowberryLeaf, SnowberryLeaf.getUnlocalizedName());
		GameRegistry.registerItem(ElderberryLeaf, ElderberryLeaf.getUnlocalizedName());
		GameRegistry.registerItem(GooseberryLeaf, GooseberryLeaf.getUnlocalizedName());
		GameRegistry.registerItem(CloudberryLeaf, CloudberryLeaf.getUnlocalizedName());*/

		TerraFirmaCraft.LOG.info("Registering Armor");
		GameRegistry.registerItem(bismuthSheet, bismuthSheet.getUnlocalizedName());
		GameRegistry.registerItem(bismuthBronzeSheet, bismuthBronzeSheet.getUnlocalizedName());
		GameRegistry.registerItem(blackBronzeSheet, blackBronzeSheet.getUnlocalizedName());
		GameRegistry.registerItem(blackSteelSheet, blackSteelSheet.getUnlocalizedName());
		GameRegistry.registerItem(blueSteelSheet, blueSteelSheet.getUnlocalizedName());
		GameRegistry.registerItem(bronzeSheet, bronzeSheet.getUnlocalizedName());
		GameRegistry.registerItem(copperSheet, copperSheet.getUnlocalizedName());
		GameRegistry.registerItem(wroughtIronSheet, wroughtIronSheet.getUnlocalizedName());
		GameRegistry.registerItem(redSteelSheet, redSteelSheet.getUnlocalizedName());
		GameRegistry.registerItem(roseGoldSheet, roseGoldSheet.getUnlocalizedName());
		GameRegistry.registerItem(steelSheet, steelSheet.getUnlocalizedName());
		GameRegistry.registerItem(tinSheet, tinSheet.getUnlocalizedName());
		GameRegistry.registerItem(zincSheet, zincSheet.getUnlocalizedName());
		GameRegistry.registerItem(electrumSheet, electrumSheet.getUnlocalizedName());
		GameRegistry.registerItem(cupronickelSheet, cupronickelSheet.getUnlocalizedName());
		GameRegistry.registerItem(osmiumSheet, osmiumSheet.getUnlocalizedName());
		GameRegistry.registerItem(aluminumSheet, aluminumSheet.getUnlocalizedName());
		GameRegistry.registerItem(tungstenSheet, tungstenSheet.getUnlocalizedName());

		GameRegistry.registerItem(bismuthSheet2x, bismuthSheet2x.getUnlocalizedName());
		GameRegistry.registerItem(bismuthBronzeSheet2x, bismuthBronzeSheet2x.getUnlocalizedName());
		GameRegistry.registerItem(blackBronzeSheet2x, blackBronzeSheet2x.getUnlocalizedName());
		GameRegistry.registerItem(blackSteelSheet2x, blackSteelSheet2x.getUnlocalizedName());
		GameRegistry.registerItem(blueSteelSheet2x, blueSteelSheet2x.getUnlocalizedName());
		GameRegistry.registerItem(bronzeSheet2x, bronzeSheet2x.getUnlocalizedName());
		GameRegistry.registerItem(copperSheet2x, copperSheet2x.getUnlocalizedName());
		GameRegistry.registerItem(wroughtIronSheet2x, wroughtIronSheet2x.getUnlocalizedName());
		GameRegistry.registerItem(redSteelSheet2x, redSteelSheet2x.getUnlocalizedName());
		GameRegistry.registerItem(roseGoldSheet2x, roseGoldSheet2x.getUnlocalizedName());
		GameRegistry.registerItem(steelSheet2x, steelSheet2x.getUnlocalizedName());
		GameRegistry.registerItem(tinSheet2x, tinSheet2x.getUnlocalizedName());
		GameRegistry.registerItem(zincSheet2x, zincSheet2x.getUnlocalizedName());
		GameRegistry.registerItem(electrumSheet2x, electrumSheet2x.getUnlocalizedName());
		GameRegistry.registerItem(cupronickelSheet2x, cupronickelSheet2x.getUnlocalizedName());
		GameRegistry.registerItem(osmiumSheet2x, osmiumSheet2x.getUnlocalizedName());
		GameRegistry.registerItem(aluminumSheet2x, aluminumSheet2x.getUnlocalizedName());
		GameRegistry.registerItem(tungstenSheet2x, tungstenSheet2x.getUnlocalizedName());

		GameRegistry.registerItem(brassSheet, brassSheet.getUnlocalizedName());
		GameRegistry.registerItem(goldSheet, goldSheet.getUnlocalizedName());
		GameRegistry.registerItem(leadSheet, leadSheet.getUnlocalizedName());
		GameRegistry.registerItem(nickelSheet, nickelSheet.getUnlocalizedName());
		GameRegistry.registerItem(pigIronSheet, pigIronSheet.getUnlocalizedName());
		GameRegistry.registerItem(platinumSheet, platinumSheet.getUnlocalizedName());
		GameRegistry.registerItem(silverSheet, silverSheet.getUnlocalizedName());
		GameRegistry.registerItem(sterlingSilverSheet, sterlingSilverSheet.getUnlocalizedName());
		GameRegistry.registerItem(brassSheet2x, brassSheet2x.getUnlocalizedName());
		GameRegistry.registerItem(goldSheet2x, goldSheet2x.getUnlocalizedName());
		GameRegistry.registerItem(leadSheet2x, leadSheet2x.getUnlocalizedName());
		GameRegistry.registerItem(nickelSheet2x, nickelSheet2x.getUnlocalizedName());
		GameRegistry.registerItem(pigIronSheet2x, pigIronSheet2x.getUnlocalizedName());
		GameRegistry.registerItem(platinumSheet2x, platinumSheet2x.getUnlocalizedName());
		GameRegistry.registerItem(silverSheet2x, silverSheet2x.getUnlocalizedName());
		GameRegistry.registerItem(sterlingSilverSheet2x, sterlingSilverSheet2x.getUnlocalizedName());
		GameRegistry.registerItem(bismuthBronzeUnfinishedBoots, bismuthBronzeUnfinishedBoots.getUnlocalizedName());
		GameRegistry.registerItem(blackBronzeUnfinishedBoots, blackBronzeUnfinishedBoots.getUnlocalizedName());
		GameRegistry.registerItem(blackSteelUnfinishedBoots, blackSteelUnfinishedBoots.getUnlocalizedName());
		GameRegistry.registerItem(blueSteelUnfinishedBoots, blueSteelUnfinishedBoots.getUnlocalizedName());
		GameRegistry.registerItem(bronzeUnfinishedBoots, bronzeUnfinishedBoots.getUnlocalizedName());
		GameRegistry.registerItem(copperUnfinishedBoots, copperUnfinishedBoots.getUnlocalizedName());
		GameRegistry.registerItem(wroughtIronUnfinishedBoots, wroughtIronUnfinishedBoots.getUnlocalizedName());
		GameRegistry.registerItem(redSteelUnfinishedBoots, redSteelUnfinishedBoots.getUnlocalizedName());
		GameRegistry.registerItem(steelUnfinishedBoots, steelUnfinishedBoots.getUnlocalizedName());
		GameRegistry.registerItem(bismuthBronzeBoots, bismuthBronzeBoots.getUnlocalizedName());
		GameRegistry.registerItem(blackBronzeBoots, blackBronzeBoots.getUnlocalizedName());
		GameRegistry.registerItem(blackSteelBoots, blackSteelBoots.getUnlocalizedName());
		GameRegistry.registerItem(blueSteelBoots, blueSteelBoots.getUnlocalizedName());
		GameRegistry.registerItem(bronzeBoots, bronzeBoots.getUnlocalizedName());
		GameRegistry.registerItem(copperBoots, copperBoots.getUnlocalizedName());
		GameRegistry.registerItem(wroughtIronBoots, wroughtIronBoots.getUnlocalizedName());
		GameRegistry.registerItem(redSteelBoots, redSteelBoots.getUnlocalizedName());
		GameRegistry.registerItem(steelBoots, steelBoots.getUnlocalizedName());
		GameRegistry.registerItem(bismuthBronzeUnfinishedGreaves, bismuthBronzeUnfinishedGreaves.getUnlocalizedName());
		GameRegistry.registerItem(blackBronzeUnfinishedGreaves, blackBronzeUnfinishedGreaves.getUnlocalizedName());
		GameRegistry.registerItem(blackSteelUnfinishedGreaves, blackSteelUnfinishedGreaves.getUnlocalizedName());
		GameRegistry.registerItem(blueSteelUnfinishedGreaves, blueSteelUnfinishedGreaves.getUnlocalizedName());
		GameRegistry.registerItem(bronzeUnfinishedGreaves, bronzeUnfinishedGreaves.getUnlocalizedName());
		GameRegistry.registerItem(copperUnfinishedGreaves, copperUnfinishedGreaves.getUnlocalizedName());
		GameRegistry.registerItem(wroughtIronUnfinishedGreaves, wroughtIronUnfinishedGreaves.getUnlocalizedName());
		GameRegistry.registerItem(redSteelUnfinishedGreaves, redSteelUnfinishedGreaves.getUnlocalizedName());
		GameRegistry.registerItem(steelUnfinishedGreaves, steelUnfinishedGreaves.getUnlocalizedName());
		GameRegistry.registerItem(bismuthBronzeGreaves, bismuthBronzeGreaves.getUnlocalizedName());
		GameRegistry.registerItem(blackBronzeGreaves, blackBronzeGreaves.getUnlocalizedName());
		GameRegistry.registerItem(blackSteelGreaves, blackSteelGreaves.getUnlocalizedName());
		GameRegistry.registerItem(blueSteelGreaves, blueSteelGreaves.getUnlocalizedName());
		GameRegistry.registerItem(bronzeGreaves, bronzeGreaves.getUnlocalizedName());
		GameRegistry.registerItem(copperGreaves, copperGreaves.getUnlocalizedName());
		GameRegistry.registerItem(wroughtIronGreaves, wroughtIronGreaves.getUnlocalizedName());
		GameRegistry.registerItem(redSteelGreaves, redSteelGreaves.getUnlocalizedName());
		GameRegistry.registerItem(steelGreaves, steelGreaves.getUnlocalizedName());
		GameRegistry.registerItem(bismuthBronzeUnfinishedChestplate, bismuthBronzeUnfinishedChestplate.getUnlocalizedName());
		GameRegistry.registerItem(blackBronzeUnfinishedChestplate, blackBronzeUnfinishedChestplate.getUnlocalizedName());
		GameRegistry.registerItem(blackSteelUnfinishedChestplate, blackSteelUnfinishedChestplate.getUnlocalizedName());
		GameRegistry.registerItem(blueSteelUnfinishedChestplate, blueSteelUnfinishedChestplate.getUnlocalizedName());
		GameRegistry.registerItem(bronzeUnfinishedChestplate, bronzeUnfinishedChestplate.getUnlocalizedName());
		GameRegistry.registerItem(copperUnfinishedChestplate, copperUnfinishedChestplate.getUnlocalizedName());
		GameRegistry.registerItem(wroughtIronUnfinishedChestplate, wroughtIronUnfinishedChestplate.getUnlocalizedName());
		GameRegistry.registerItem(redSteelUnfinishedChestplate, redSteelUnfinishedChestplate.getUnlocalizedName());
		GameRegistry.registerItem(steelUnfinishedChestplate, steelUnfinishedChestplate.getUnlocalizedName());
		GameRegistry.registerItem(bismuthBronzeChestplate, bismuthBronzeChestplate.getUnlocalizedName());
		GameRegistry.registerItem(blackBronzeChestplate, blackBronzeChestplate.getUnlocalizedName());
		GameRegistry.registerItem(blackSteelChestplate, blackSteelChestplate.getUnlocalizedName());
		GameRegistry.registerItem(blueSteelChestplate, blueSteelChestplate.getUnlocalizedName());
		GameRegistry.registerItem(bronzeChestplate, bronzeChestplate.getUnlocalizedName());
		GameRegistry.registerItem(copperChestplate, copperChestplate.getUnlocalizedName());
		GameRegistry.registerItem(wroughtIronChestplate, wroughtIronChestplate.getUnlocalizedName());
		GameRegistry.registerItem(redSteelChestplate, redSteelChestplate.getUnlocalizedName());
		GameRegistry.registerItem(steelChestplate, steelChestplate.getUnlocalizedName());
		GameRegistry.registerItem(bismuthBronzeUnfinishedHelmet, bismuthBronzeUnfinishedHelmet.getUnlocalizedName());
		GameRegistry.registerItem(blackBronzeUnfinishedHelmet, blackBronzeUnfinishedHelmet.getUnlocalizedName());
		GameRegistry.registerItem(blackSteelUnfinishedHelmet, blackSteelUnfinishedHelmet.getUnlocalizedName());
		GameRegistry.registerItem(blueSteelUnfinishedHelmet, blueSteelUnfinishedHelmet.getUnlocalizedName());
		GameRegistry.registerItem(bronzeUnfinishedHelmet, bronzeUnfinishedHelmet.getUnlocalizedName());
		GameRegistry.registerItem(copperUnfinishedHelmet, copperUnfinishedHelmet.getUnlocalizedName());
		GameRegistry.registerItem(wroughtIronUnfinishedHelmet, wroughtIronUnfinishedHelmet.getUnlocalizedName());
		GameRegistry.registerItem(redSteelUnfinishedHelmet, redSteelUnfinishedHelmet.getUnlocalizedName());
		GameRegistry.registerItem(steelUnfinishedHelmet, steelUnfinishedHelmet.getUnlocalizedName());
		GameRegistry.registerItem(bismuthBronzeHelmet, bismuthBronzeHelmet.getUnlocalizedName());
		GameRegistry.registerItem(blackBronzeHelmet, blackBronzeHelmet.getUnlocalizedName());
		GameRegistry.registerItem(blackSteelHelmet, blackSteelHelmet.getUnlocalizedName());
		GameRegistry.registerItem(blueSteelHelmet, blueSteelHelmet.getUnlocalizedName());
		GameRegistry.registerItem(bronzeHelmet, bronzeHelmet.getUnlocalizedName());
		GameRegistry.registerItem(copperHelmet, copperHelmet.getUnlocalizedName());
		GameRegistry.registerItem(wroughtIronHelmet, wroughtIronHelmet.getUnlocalizedName());
		GameRegistry.registerItem(redSteelHelmet, redSteelHelmet.getUnlocalizedName());
		GameRegistry.registerItem(steelHelmet, steelHelmet.getUnlocalizedName());

		GameRegistry.registerItem(leatherHelmet, leatherHelmet.getUnlocalizedName());
		GameRegistry.registerItem(leatherChestplate, leatherChestplate.getUnlocalizedName());
		GameRegistry.registerItem(leatherLeggings, leatherLeggings.getUnlocalizedName());
		GameRegistry.registerItem(leatherBoots, leatherBoots.getUnlocalizedName());

		GameRegistry.registerItem(quiver, quiver.getUnlocalizedName());
		//GameRegistry.registerItem(MudBrick, MudBrick.getUnlocalizedName());
		GameRegistry.registerItem(sandwich, sandwich.getUnlocalizedName());
		GameRegistry.registerItem(salad, salad.getUnlocalizedName());
		//GameRegistry.registerItem(Soup, Soup.getUnlocalizedName());

		GameRegistry.registerItem(sugar, sugar.getUnlocalizedName());

		GameRegistry.registerItem(shears, shears.getUnlocalizedName());
		GameRegistry.registerItem(shearsBlackSteel, shearsBlackSteel.getUnlocalizedName());

		TerraFirmaCraft.LOG.info("All Items Registered");
	}


}
