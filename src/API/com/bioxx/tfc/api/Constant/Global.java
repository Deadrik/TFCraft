package com.bioxx.tfc.api.Constant;

import java.util.Arrays;

import com.bioxx.tfc.api.Metal;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.TFCOptions;
import com.google.common.collect.ObjectArrays;

public class Global
{
	/* FruitTree Meta Names, also used for fruit items and FloraManager */
	public static final String[] FRUIT_META_NAMES = new String[] {
		"Red Apple", "Banana", "Orange", "Green Apple", "Lemon", "Olive", "Cherry", "Peach", "Plum"
	};

	/* Flower Meta Names
	 * The first 10 flowers are from vanilla */
	public static final String[] FLOWER_META_NAMES = new String[] {
		"flower_rose", "flower_blue_orchid", "flower_allium", "flower_houstonia",
		"flower_tulip_red", "flower_tulip_orange", "flower_tulip_white", "flower_tulip_pink", "flower_oxeye_daisy",
		"flower_dandelion","flower_nasturtium", "flower_meads_milkweed", "flower_tropical_milkweed", "flower_butterfly_milkweed", "flower_calendula"
	};

	/* Fungi Meta Names
	 * The first 2 are vanilla mushrooms */
	public static final String[] FUNGI_META_NAMES = new String[] {
		"mushroom_brown", "mushroom_red"
	};

	/* Powder */
	public static final String[] POWDER = {
		"Flux", "Kaolinite Powder", "Graphite Powder", "Sulfur Powder", "Saltpeter Powder",
		"Hematite Powder", "Lapis Lazuli Powder", "Limonite Powder", "Malachite Powder", "Salt"
	};
	
	/* Dyes, used for carpets and small vessels */
	/* Colors MUST be in the same order as in EntitySheep.fleeceColorTable! */
	public static final String[] DYE_NAMES = { 
		"dyeWhite", "dyeOrange", "dyeMagenta", "dyeLightBlue", "dyeYellow",
		"dyeLime", "dyePink", "dyeGray", "dyeLightGray", "dyeCyan", 
		"dyePurple", "dyeBlue", "dyeBrown", "dyeGreen", "dyeRed", "dyeBlack" };


	/* Stone Types */
	public static final String[] STONE_IGIN = {"Granite", "Diorite", "Gabbro"};
	public static final String[] STONE_SED  = {"Shale", "Claystone", "Rock Salt", "Limestone", "Conglomerate", "Dolomite", "Chert", "Chalk"};
	public static final String[] STONE_IGEX = {"Rhyolite", "Basalt", "Andesite", "Dacite"};
	public static final String[] STONE_MM   = {"Quartzite", "Slate", "Phyllite", "Schist", "Gneiss", "Marble"};

	// Used for loose rocks and other places where the stone list is combined
	public static final int STONE_IGIN_START = 0;
	public static final int STONE_SED_START = STONE_IGIN_START + STONE_IGIN.length;
	public static final int STONE_IGEX_START = STONE_SED_START + STONE_SED.length;
	public static final int STONE_MM_START = STONE_IGEX_START + STONE_IGEX.length;
	public static final String[] STONE_ALL  = ObjectArrays.concat(ObjectArrays.concat(STONE_IGIN, STONE_SED, String.class), ObjectArrays.concat(STONE_IGEX, STONE_MM, String.class), String.class);

	// Stones that can be turned into flux
	public static final int[] STONE_FLUXINDEX = {
		Arrays.asList(STONE_ALL).indexOf("Limestone"),
		Arrays.asList(STONE_ALL).indexOf("Dolomite"),
		Arrays.asList(STONE_ALL).indexOf("Chalk"),
		Arrays.asList(STONE_ALL).indexOf("Marble")
	};

	/* Ore Types */
	public static final String[] ORE_METAL = {
		"Native Copper", "Native Gold", "Native Platinum", "Hematite",
		"Native Silver", "Cassiterite", "Galena", "Bismuthinite",
		"Garnierite", "Malachite", "Magnetite", "Limonite",
		"Sphalerite", "Tetrahedrite", "Bituminous Coal", "Lignite"
	};
	public static final String[] ORE_MINERAL = {
		"Kaolinite", "Gypsum", "Satinspar", "Selenite",
		"Graphite", "Kimberlite", "Petrified Wood", "Sulfur",
		"Jet", "Microcline", "Pitchblende", "Cinnabar",
		"Cryolite", "Saltpeter", "Serpentine", "Sylvite"
	};
	public static final String[] ORE_MINERAL2 = {"Borax", "Olivine", "Lapis Lazuli"};

	public static final String[] WOOD_ALL = {
		"Oak","Aspen","Birch","Chestnut",
		"Douglas Fir","Hickory","Maple","Ash",
		"Pine","Sequoia","Spruce","Sycamore",
		"White Cedar","White Elm","Willow","Kapok","Acacia"
	};

	public static final String SKILL_GENERAL_SMITHING = "skill.gensmith";
	public static final String SKILL_TOOLSMITH = "skill.toolsmith";
	public static final String SKILL_WEAPONSMITH = "skill.weaponsmith";
	public static final String SKILL_ARMORSMITH = "skill.armorsmith";
	public static final String SKILL_AGRICULTURE = "skill.agriculture";
	public static final String SKILL_COOKING = "skill.cooking";
	public static final String SKILL_PROSPECTING = "skill.prospecting";
	public static final String SKILL_BUTCHERING = "skill.butchering";


	public static final Metal BISMUTH = new Metal("Bismuth", TFCItems.bismuthUnshaped, TFCItems.bismuthIngot);
	public static final Metal BISMUTHBRONZE = new Metal("Bismuth Bronze", TFCItems.bismuthBronzeUnshaped, TFCItems.bismuthBronzeIngot);
	public static final Metal BLACKBRONZE = new Metal("Black Bronze", TFCItems.blackBronzeUnshaped, TFCItems.blackBronzeIngot);
	public static final Metal BLACKSTEEL = new Metal("Black Steel", TFCItems.blackSteelUnshaped, TFCItems.blackSteelIngot);
	public static final Metal BLUESTEEL = new Metal("Blue Steel", TFCItems.blueSteelUnshaped, TFCItems.blueSteelIngot);
	public static final Metal BRASS = new Metal("Brass", TFCItems.brassUnshaped, TFCItems.brassIngot);
	public static final Metal BRONZE = new Metal("Bronze", TFCItems.bronzeUnshaped, TFCItems.bronzeIngot);
	public static final Metal COPPER = new Metal("Copper", TFCItems.copperUnshaped, TFCItems.copperIngot);
	public static final Metal GOLD = new Metal("Gold", TFCItems.goldUnshaped, TFCItems.goldIngot);
	public static final Metal WROUGHTIRON = new Metal("Wrought Iron", TFCItems.wroughtIronUnshaped, TFCItems.wroughtIronIngot);
	public static final Metal LEAD = new Metal("Lead", TFCItems.leadUnshaped, TFCItems.leadIngot);
	public static final Metal NICKEL = new Metal("Nickel", TFCItems.nickelUnshaped, TFCItems.nickelIngot);
	public static final Metal PIGIRON = new Metal("Pig Iron", TFCItems.pigIronUnshaped, TFCItems.pigIronIngot);
	public static final Metal PLATINUM = new Metal("Platinum", TFCItems.platinumUnshaped, TFCItems.platinumIngot);
	public static final Metal REDSTEEL = new Metal("Red Steel", TFCItems.redSteelUnshaped, TFCItems.redSteelIngot);
	public static final Metal ROSEGOLD = new Metal("Rose Gold", TFCItems.roseGoldUnshaped, TFCItems.roseGoldIngot);
	public static final Metal SILVER = new Metal("Silver", TFCItems.silverUnshaped, TFCItems.silverIngot);
	public static final Metal STEEL = new Metal("Steel", TFCItems.steelUnshaped, TFCItems.steelIngot);
	public static final Metal STERLINGSILVER = new Metal("Sterling Silver", TFCItems.sterlingSilverUnshaped, TFCItems.sterlingSilverIngot);
	public static final Metal TIN = new Metal("Tin", TFCItems.tinUnshaped, TFCItems.tinIngot);
	public static final Metal ZINC = new Metal("Zinc", TFCItems.zincUnshaped, TFCItems.zincIngot);
	public static final Metal WEAKSTEEL = new Metal("Weak Steel", TFCItems.weakSteelUnshaped, TFCItems.weakSteelIngot);
	public static final Metal HCBLACKSTEEL = new Metal("HC Black Steel", TFCItems.highCarbonBlackSteelUnshaped, TFCItems.highCarbonBlackSteelIngot);
	public static final Metal WEAKREDSTEEL = new Metal("Weak Red Steel", TFCItems.weakRedSteelUnshaped, TFCItems.weakRedSteelIngot);
	public static final Metal HCREDSTEEL = new Metal("HC Red Steel", TFCItems.highCarbonRedSteelUnshaped, TFCItems.highCarbonRedSteelIngot);
	public static final Metal WEAKBLUESTEEL = new Metal("Weak Blue Steel", TFCItems.weakBlueSteelUnshaped, TFCItems.weakBlueSteelIngot);
	public static final Metal HCBLUESTEEL = new Metal("HC Blue Steel", TFCItems.highCarbonBlueSteelUnshaped, TFCItems.highCarbonBlueSteelIngot);
	public static final Metal UNKNOWN = new Metal("Unknown", TFCItems.unknownUnshaped, TFCItems.unknownIngot, false);

	/**
	 * Switch to TFCOptions.foodDecayRate
	 */
	@Deprecated
	public static double foodDecayRate = TFCOptions.foodDecayRate;

	public static final float FOOD_MAX_WEIGHT = 160;
	public static final float FOOD_MIN_DROP_WEIGHT = 0.1f;

	public static final int SEALEVEL = 144;
}
