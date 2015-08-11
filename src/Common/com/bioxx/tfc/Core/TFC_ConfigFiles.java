package com.bioxx.tfc.Core;

import java.io.File;

import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;

import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.WorldGen.Generators.OreSpawnData;
import com.bioxx.tfc.api.Constant.Global;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ObjectArrays;

import static com.bioxx.tfc.Reference.ModID;
import static com.bioxx.tfc.WorldGen.Generators.WorldGenOre.OreList;
import static com.bioxx.tfc.api.TFCCrafting.*;
import static com.bioxx.tfc.api.TFCOptions.*;

/**
 * @author Dries007
 */
public class TFC_ConfigFiles
{
	// General
	public static final String GENERAL = "general";
	public static final String TIME = "time";
	public static final String FOOD_DECAY = "food decay";
	public static final String CAVEIN_OPTIONS = "cave-ins";
	public static final String WORLD_GEN = "world generation";
	public static final String CROPS = "crops";
	public static final String PROTECTION = "spawn protection";
	public static final String PLAYER = "player";
	public static final String MATERIALS = "materials";
	public static final String SERVER = "server";
	public static final String OVERWORKED = "overworked chunks";
	public static final String COLORS = "colors";
	public static final String COLOR_NUTRIENT_A = "color nutrient a";
	public static final String COLOR_NUTRIENT_B = "color nutrient b";
	public static final String COLOR_NUTRIENT_C = "color nutrient c";
	public static final String CROP_FERTILIZER_COLOR = "color fertilizer";
	public static final String ANVIL_RULE_COLOR0 = "color anvil rule 0";
	public static final String ANVIL_RULE_COLOR1 = "color anvil rule 1";
	public static final String ANVIL_RULE_COLOR2 = "color anvil rule 2";
	// Crafting
	public static final String CONVERSION = "Conversion";
	public static final String ENABLE_VANILLA_RECIPES = "Enable Vanilla Recipes";

	// Used internally to move from top to colors.<name>
	private static final String[] COLOR_CATEGORIES = {COLOR_NUTRIENT_A, COLOR_NUTRIENT_B, COLOR_NUTRIENT_C, CROP_FERTILIZER_COLOR, ANVIL_RULE_COLOR0, ANVIL_RULE_COLOR1, ANVIL_RULE_COLOR2};

	// Used as allowed values for Ore
	private static final String[] ALLOWED_TYPES = new String[] {"default", "veins"};
	private static final String[] ALLOWED_SIZES = new String[] {"small", "medium", "large"};
	private static final String[] ALLOWED_BASE_ROCKS = ObjectArrays.concat(Global.STONE_ALL, new String[] {"igneous intrusive", "igneous extrusive", "sedimentary", "metamorphic"}, String.class);

	private static Configuration generalConfig;
	private static Configuration craftingConfig;
	private static Configuration oresConfig;

	public static Configuration getGeneralConfig()
	{
		return generalConfig;
	}

	public static Configuration getCraftingConfig()
	{
		return craftingConfig;
	}

	public static Configuration getOresConfig()
	{
		return oresConfig;
	}

	public static void preInit(File configFolder)
	{
		if (generalConfig != null) throw new IllegalStateException("Preinit can't be called twice.");

		File generalConfigFile = new File(configFolder, "TFCConfig.cfg");
		if (!generalConfigFile.exists()) // Handle old config file
		{
			File oldConfigFile = new File(generalConfigFile, "cfg");
			if (oldConfigFile.exists() && !oldConfigFile.isDirectory())
			{
				//noinspection ResultOfMethodCallIgnored
				oldConfigFile.delete();
			}
		}
		generalConfig = new Configuration(generalConfigFile);
		craftingConfig = new Configuration(new File(configFolder, "TFCCrafting.cfg"));
		oresConfig = new Configuration(new File(configFolder, "TFCOre.cfg"));
	}

	/**
	 * Reloads all configs. While doing inits, don't call this.
	 * Call the individual config reload methods, at there appropriate times.
	 */
	public static void reloadAll()
	{
		reloadGeneral(); // No special needs
		reloadCrafting(); // Require sync from server
		reloadOres(); // World gen voo-doo
	}

	public static void reloadOres()
	{
		if (oresConfig == null) throw new IllegalStateException("Config reload attempt before preinit.");
		TerraFirmaCraft.log.info("(re)loading the crafting config (TFCCrafting) settings...");

		OreList.put("Native Copper", getOreData("Native Copper", "veins", "large", ModID + ":Ore1", 0, 120, new String[]{"igneous extrusive"}, 5, 128, 80, 60));
		OreList.put("Native Gold", getOreData("Native Gold", "veins", "large", ModID + ":Ore1", 1, 120, new String[]{"igneous extrusive", "igneous intrusive"}, 5, 128, 80, 60));
		OreList.put("Platinum", getOreData("Platinum", "veins", "small", ModID + ":Ore1", 2, 150, new String[]{"sedimentary"}, 5, 128, 40, 80));
		OreList.put("Hematite", getOreData("Hematite", "veins", "medium", ModID + ":Ore1", 3, 125, new String[]{"igneous extrusive"}, 5, 128, 80, 60));
		OreList.put("Silver", getOreData("Silver", "veins", "medium", ModID + ":Ore1", 4, 100, new String[]{"granite", "gneiss"}, 5, 128, 80, 60));
		OreList.put("Cassiterite", getOreData("Cassiterite", "veins", "medium", ModID + ":Ore1", 5, 100, new String[]{"igneous intrusive"}, 5, 128, 80, 60));
		OreList.put("Galena", getOreData("Galena", "veins", "medium", ModID + ":Ore1", 6, 100, new String[]{"igneous extrusive", "metamorphic", "granite", "limestone"}, 5, 128, 80, 60));
		OreList.put("Bismuthinite", getOreData("Bismuthinite", "veins", "medium", ModID + ":Ore1", 7, 100, new String[]{"igneous extrusive", "sedimentary"}, 5, 128, 80, 60));
		OreList.put("Garnierite", getOreData("Garnierite", "veins", "medium", ModID + ":Ore1", 8, 150, new String[]{"gabbro"}, 5, 128, 80, 60));
		OreList.put("Malachite", getOreData("Malachite", "veins", "large", ModID + ":Ore1", 9, 100, new String[]{"limestone", "marble"}, 5, 128, 80, 60));
		OreList.put("Magnetite", getOreData("Magnetite", "veins", "medium", ModID + ":Ore1", 10, 150, new String[]{"sedimentary"}, 5, 128, 80, 60));
		OreList.put("Limonite", getOreData("Limonite", "veins", "medium", ModID + ":Ore1", 11, 150, new String[]{"sedimentary"}, 5, 128, 80, 60));
		OreList.put("Sphalerite", getOreData("Sphalerite", "veins", "medium", ModID + ":Ore1", 12, 100, new String[]{"metamorphic"}, 5, 128, 80, 60));
		OreList.put("Tetrahedrite", getOreData("Tetrahedrite", "veins", "medium", ModID + ":Ore1", 13, 120, new String[]{"metamorphic"}, 5, 128, 80, 60));
		OreList.put("Bituminous Coal", getOreData("Bituminous Coal", "default", "large", ModID + ":Ore1", 14, 100, new String[]{"sedimentary"}, 5, 128, 90, 40));
		OreList.put("Lignite", getOreData("Lignite", "default", "medium", ModID + ":Ore1", 15, 100, new String[]{"sedimentary"}, 5, 128, 90, 40));

		OreList.put("Kaolinite", getOreData("Kaolinite", "default", "medium", ModID + ":Ore2", 0, 90, new String[]{"sedimentary"}, 5, 128, 80, 60));
		OreList.put("Gypsum", getOreData("Gypsum", "veins", "large", ModID + ":Ore2", 1, 120, new String[]{"sedimentary"}, 5, 128, 80, 60));
		//WorldGenOre.OreList.put("Satinspar", getOreData("Satinspar", "veins", "small", Reference.ModID + ":Ore2", 2, 150, new String[]{"sedimentary"}, 5, 128, 40, 80));
		//WorldGenOre.OreList.put("Selenite", getOreData("Selenite", "veins", "medium", Reference.ModID + ":Ore2", 3, 125, new String[]{"igneous extrusive"}, 5, 128, 60, 60));
		OreList.put("Graphite", getOreData("Graphite", "veins", "medium", ModID + ":Ore2", 4, 100, new String[]{"marble", "gneiss", "quartzite", "schist"}, 5, 128, 80, 60));
		OreList.put("Kimberlite", getOreData("Kimberlite", "veins", "medium", ModID + ":Ore2", 5, 200, new String[]{"gabbro"}, 5, 128, 30, 80));
		//WorldGenOre.OreList.put("Petrified Wood", getOreData("Petrified Wood", "veins", "medium", Reference.ModID + ":Ore2", 6, 100, new String[]{"igneous extrusive", "metamorphic", "granite", "limestone"}, 5, 128, 60, 60));
		//WorldGenOre.OreList.put("Sulfur", getOreData("Sulfur", "veins", "medium", Reference.ModID + ":Ore2", 7, 100, new String[]{"igneous extrusive", "sedimentary"}, 5, 128, 60, 60));
		OreList.put("Jet", getOreData("Jet", "veins", "large", ModID + ":Ore2", 8, 110, new String[]{"sedimentary"}, 5, 128, 80, 60));
		//WorldGenOre.OreList.put("Microcline", getOreData("Microcline", "veins", "large", Reference.ModID + ":Ore2", 9, 100, new String[]{"limestone", "marble"}, 5, 128, 60, 60));
		OreList.put("Pitchblende", getOreData("Pitchblende", "veins", "small", ModID + ":Ore2", 10, 150, new String[]{"granite"}, 5, 128, 80, 60));
		OreList.put("Cinnabar", getOreData("Cinnabar", "veins", "small", ModID + ":Ore2", 11, 150, new String[]{"igneous extrusive", "shale", "quartzite"}, 5, 128, 30, 80));
		OreList.put("Cryolite", getOreData("Cryolite", "veins", "small", ModID + ":Ore2", 12, 100, new String[]{"granite"}, 5, 128, 80, 60));
		OreList.put("Saltpeter", getOreData("Saltpeter", "veins", "medium", ModID + ":Ore2", 13, 120, new String[]{"sedimentary"}, 5, 128, 80, 60));
		//WorldGenOre.OreList.put("Serpentine", getOreData("Serpentine", "veins", "large", Reference.ModID + ":Ore2", 14, 100, new String[]{"sedimentary"}, 5, 128, 90, 40));
		OreList.put("Sylvite", getOreData("Sylvite", "veins", "medium", ModID + ":Ore2", 15, 100, new String[]{"rock salt"}, 5, 128, 90, 40));

		OreList.put("Borax", getOreData("Borax", "veins", "large", ModID + ":Ore3", 0, 120, new String[]{"rock salt"}, 5, 128, 80, 60));
		OreList.put("Lapis Lazuli", getOreData("Lapis Lazuli", "veins", "large", ModID + ":Ore3", 2, 120, new String[]{"marble"}, 5, 128, 80, 60));
		//WorldGenOre.OreList.put("Olivine", getOreData("Olivine", "veins", "small", Reference.ModID + ":Ore3", 1, 150, new String[]{"sedimentary"}, 5, 128, 40, 80));

		//Surface Ore
		OreList.put("Native Copper Surface", getOreData("Native Copper Surface", "veins", "small", ModID + ":Ore1", 0, 35, new String[]{"igneous extrusive"}, 128, 240, 40, 40));
		OreList.put("Cassiterite Surface", getOreData("Cassiterite Surface", "veins", "small", ModID + ":Ore1", 5, 35, new String[]{"granite"}, 128, 240, 40, 40));
		OreList.put("Bismuthinite Surface", getOreData("Bismuthinite Surface", "veins", "small", ModID + ":Ore1", 7, 35, new String[]{"igneous extrusive", "sedimentary"}, 128, 240, 40, 40));
		OreList.put("Sphalerite Surface", getOreData("Sphalerite Surface", "veins", "small", ModID + ":Ore1", 12, 35, new String[]{"metamorphic"}, 128, 240, 40, 40));
		OreList.put("Tetrahedrite Surface", getOreData("Tetrahedrite Surface", "veins", "small", ModID + ":Ore1", 13, 35, new String[]{"metamorphic"}, 128, 240, 40, 40));

		// getCategoryNames returns an ImmutableSet
		for (String s : oresConfig.getCategoryNames())
		{
			// If this is a new entry, otherwise it has already been added by the previous bit of code.
			if (!OreList.containsKey(s)) OreList.put(s, getOreData(s, "default", "small", "Ore", 0, 100, new String[] {"granite", "basalt", "sedimentary"}, 5, 128, 50, 50));
		}

		if (oresConfig.hasChanged()) oresConfig.save();
	}

	private static OreSpawnData getOreData(String category, String type, String size, String blockName, int meta, int rarity, String[] rocks, int min, int max, int v, int h)
	{
		return new OreSpawnData(
				oresConfig.get(category, "type", type).setValidValues(ALLOWED_TYPES).getString(),
				oresConfig.get(category, "size", size).setValidValues(ALLOWED_SIZES).getString(),
				oresConfig.get(category, "oreName", blockName).getString(),
				oresConfig.get(category, "oreMeta", meta).getInt(),
				oresConfig.get(category, "rarity", rarity).getInt(),
				oresConfig.get(category, "baseRocks", rocks).setValidValues(ALLOWED_BASE_ROCKS).getStringList(),
				oresConfig.get(category, "Minimum Height", min).getInt(),
				oresConfig.get(category, "Maximum Height", max).getInt(),
				oresConfig.get(category, "Vertical Density", v).getInt(),
				oresConfig.get(category, "Horizontal Density", h).getInt()
		);
	}

	public static void reloadCrafting()
	{
		if (craftingConfig == null) throw new IllegalStateException("Config reload attempt before preinit.");
		TerraFirmaCraft.log.info("(re)loading the crafting config (TFCCrafting) settings...");

		//todo: implement syncing. For now cats are just marked as need reboot.

		// Replaced by 'tfc.disableNEIhiding' system property, mainly because of server client syncing. Enable by adding -Dtfc.disableNEIhiding to your startup line.
		if (craftingConfig.hasCategory("nei hiding")) craftingConfig.removeCategory(craftingConfig.getCategory("nei hiding"));

		craftingConfig.addCustomCategoryComment(CONVERSION, "Conversions for food are irreversible! The server overrides the client on this.");
		craftingConfig.setCategoryLanguageKey(CONVERSION, "config.gui.TFCCrafting.conversion");

		appleConversion = craftingConfig.getBoolean("appleConversion", CONVERSION, appleConversion, "");
		arrowConversion = craftingConfig.getBoolean("arrowConversion", CONVERSION, arrowConversion, "");
		bowConversion = craftingConfig.getBoolean("bowConversion", CONVERSION, bowConversion, "");
		coalConversion = craftingConfig.getBoolean("coalConversion", CONVERSION, coalConversion, "");
		diamondConversion = craftingConfig.getBoolean("diamondConversion", CONVERSION, diamondConversion, "");
		emeraldConversion = craftingConfig.getBoolean("emeraldConversion", CONVERSION, emeraldConversion, "");
		fishConversion = craftingConfig.getBoolean("fishConversion", CONVERSION, fishConversion, "");
		fishingRodConversion = craftingConfig.getBoolean("fishingRodConversion", CONVERSION, fishingRodConversion, "");
		flintSteelConversion = craftingConfig.getBoolean("flintSteelConversion", CONVERSION, flintSteelConversion, "");
		leatherArmorConversion = craftingConfig.getBoolean("leatherArmorConversion", CONVERSION, leatherArmorConversion, "");
		leatherConversion = craftingConfig.getBoolean("leatherConversion", CONVERSION, leatherConversion, "");
		stoneAxeConversion = craftingConfig.getBoolean("stoneAxeConversion", CONVERSION, stoneAxeConversion, "");
		stoneHoeConversion = craftingConfig.getBoolean("stoneHoeConversion", CONVERSION, stoneHoeConversion, "");
		stoneShovelConversion = craftingConfig.getBoolean("stoneShovelConversion", CONVERSION, stoneShovelConversion, "");
		woodButtonConversion = craftingConfig.getBoolean("woodButtonConversion", CONVERSION, woodButtonConversion, "");
		workbenchConversion = craftingConfig.getBoolean("workbenchConversion", CONVERSION, workbenchConversion, "");

		craftingConfig.addCustomCategoryComment(ENABLE_VANILLA_RECIPES, "Enable Vanilla recipes, the server overrides the client on this.");
		craftingConfig.setCategoryLanguageKey(ENABLE_VANILLA_RECIPES, "config.gui.TFCCrafting.vanilla");

		anvilRecipe = craftingConfig.getBoolean("anvilRecipe", ENABLE_VANILLA_RECIPES, anvilRecipe, "");
		arrowsRecipe = craftingConfig.getBoolean("arrowsRecipe", ENABLE_VANILLA_RECIPES, arrowsRecipe, "");
		bedRecipe = craftingConfig.getBoolean("bedRecipe", ENABLE_VANILLA_RECIPES, bedRecipe, "");
		bonemealRecipe = craftingConfig.getBoolean("bonemealRecipe", ENABLE_VANILLA_RECIPES, bonemealRecipe, "");
		bowlRecipe = craftingConfig.getBoolean("bowlRecipe", ENABLE_VANILLA_RECIPES, bowlRecipe, "");
		brewingRecipe = craftingConfig.getBoolean("brewingRecipe", ENABLE_VANILLA_RECIPES, brewingRecipe, "");
		bucketRecipe = craftingConfig.getBoolean("bucketRecipe", ENABLE_VANILLA_RECIPES, bucketRecipe, "");
		cauldronRecipe = craftingConfig.getBoolean("cauldronRecipe", ENABLE_VANILLA_RECIPES, cauldronRecipe, "");
		chestRecipe = craftingConfig.getBoolean("chestRecipe", ENABLE_VANILLA_RECIPES, chestRecipe, "");
		clockRecipe = craftingConfig.getBoolean("clockRecipe", ENABLE_VANILLA_RECIPES, clockRecipe, "");
		compassRecipe = craftingConfig.getBoolean("compassRecipe", ENABLE_VANILLA_RECIPES, compassRecipe, "");
		dandelionYellowRecipe = craftingConfig.getBoolean("dandelionYellowRecipe", ENABLE_VANILLA_RECIPES, dandelionYellowRecipe, "");
		diamondArmorRecipe = craftingConfig.getBoolean("diamondArmorRecipe", ENABLE_VANILLA_RECIPES, diamondArmorRecipe, "");
		diamondBlockRecipe = craftingConfig.getBoolean("diamondBlockRecipe", ENABLE_VANILLA_RECIPES, diamondBlockRecipe, "");
		diamondToolsRecipe = craftingConfig.getBoolean("diamondToolsRecipe", ENABLE_VANILLA_RECIPES, diamondToolsRecipe, "");
		dispenserRecipe = craftingConfig.getBoolean("dispenserRecipe", ENABLE_VANILLA_RECIPES, dispenserRecipe, "");
		dropperRecipe = craftingConfig.getBoolean("dropperRecipe", ENABLE_VANILLA_RECIPES, dropperRecipe, "");
		enchantTableRecipe = craftingConfig.getBoolean("enchantTableRecipe", ENABLE_VANILLA_RECIPES, enchantTableRecipe, "");
		fenceGateRecipe = craftingConfig.getBoolean("fenceGateRecipe", ENABLE_VANILLA_RECIPES, fenceGateRecipe, "");
		fenceRecipe = craftingConfig.getBoolean("fenceRecipe", ENABLE_VANILLA_RECIPES, fenceRecipe, "");
		furnaceRecipe = craftingConfig.getBoolean("furnaceRecipe", ENABLE_VANILLA_RECIPES, furnaceRecipe, "");
		goldAppleRecipe = craftingConfig.getBoolean("goldAppleRecipe", ENABLE_VANILLA_RECIPES, goldAppleRecipe, "");
		goldArmorRecipe = craftingConfig.getBoolean("goldArmorRecipe", ENABLE_VANILLA_RECIPES, goldArmorRecipe, "");
		goldBlockRecipe = craftingConfig.getBoolean("goldBlockRecipe", ENABLE_VANILLA_RECIPES, goldBlockRecipe, "");
		goldNuggetRecipe = craftingConfig.getBoolean("goldNuggetRecipe", ENABLE_VANILLA_RECIPES, goldNuggetRecipe, "");
		goldPlateRecipe = craftingConfig.getBoolean("goldPlateRecipe", ENABLE_VANILLA_RECIPES, goldPlateRecipe, "");
		goldToolsRecipe = craftingConfig.getBoolean("goldToolsRecipe", ENABLE_VANILLA_RECIPES, goldToolsRecipe, "");
		hopperRecipe = craftingConfig.getBoolean("hopperRecipe", ENABLE_VANILLA_RECIPES, hopperRecipe, "");
		ironArmorRecipe = craftingConfig.getBoolean("ironArmorRecipe", ENABLE_VANILLA_RECIPES, ironArmorRecipe, "");
		ironBarsRecipe = craftingConfig.getBoolean("ironBarsRecipe", ENABLE_VANILLA_RECIPES, ironBarsRecipe, "");
		ironBlockRecipe = craftingConfig.getBoolean("ironBlockRecipe", ENABLE_VANILLA_RECIPES, ironBlockRecipe, "");
		ironDoorRecipe = craftingConfig.getBoolean("ironDoorRecipe", ENABLE_VANILLA_RECIPES, ironDoorRecipe, "");
		ironPlateRecipe = craftingConfig.getBoolean("ironPlateRecipe", ENABLE_VANILLA_RECIPES, ironPlateRecipe, "");
		ironToolsRecipe = craftingConfig.getBoolean("ironToolsRecipe", ENABLE_VANILLA_RECIPES, ironToolsRecipe, "");
		jukeboxRecipe = craftingConfig.getBoolean("jukeboxRecipe", ENABLE_VANILLA_RECIPES, jukeboxRecipe, "");
		leatherArmorRecipe = craftingConfig.getBoolean("leatherArmorRecipe", ENABLE_VANILLA_RECIPES, leatherArmorRecipe, "");
		leverRecipe = craftingConfig.getBoolean("leverRecipe", ENABLE_VANILLA_RECIPES, leverRecipe, "");
		minecartChestRecipe = craftingConfig.getBoolean("minecartChestRecipe", ENABLE_VANILLA_RECIPES, minecartChestRecipe, "");
		minecartRecipe = craftingConfig.getBoolean("minecartRecipe", ENABLE_VANILLA_RECIPES, minecartRecipe, "");
		pistonRecipe = craftingConfig.getBoolean("pistonRecipe", ENABLE_VANILLA_RECIPES, pistonRecipe, "");
		plankBlockRecipe = craftingConfig.getBoolean("plankBlockRecipe", ENABLE_VANILLA_RECIPES, plankBlockRecipe, "");
		poweredRailsRecipe = craftingConfig.getBoolean("poweredRailsRecipe", ENABLE_VANILLA_RECIPES, poweredRailsRecipe, "");
		railsRecipe = craftingConfig.getBoolean("railsRecipe", ENABLE_VANILLA_RECIPES, railsRecipe, "");
		repeaterRecipe = craftingConfig.getBoolean("repeaterRecipe", ENABLE_VANILLA_RECIPES, repeaterRecipe, "");
		roseRedRecipe = craftingConfig.getBoolean("roseRedRecipe", ENABLE_VANILLA_RECIPES, roseRedRecipe, "");
		shearsRecipe = craftingConfig.getBoolean("shearsRecipe", ENABLE_VANILLA_RECIPES, shearsRecipe, "");
		signRecipe = craftingConfig.getBoolean("signRecipe", ENABLE_VANILLA_RECIPES, signRecipe, "");
		stickRecipe = craftingConfig.getBoolean("stickRecipe", ENABLE_VANILLA_RECIPES, stickRecipe, "");
		stoneSlabsRecipe = craftingConfig.getBoolean("stoneSlabsRecipe", ENABLE_VANILLA_RECIPES, stoneSlabsRecipe, "");
		stoneStairsRecipe = craftingConfig.getBoolean("stoneStairsRecipe", ENABLE_VANILLA_RECIPES, stoneStairsRecipe, "");
		stoneToolsRecipe = craftingConfig.getBoolean("stoneToolsRecipe", ENABLE_VANILLA_RECIPES, stoneToolsRecipe, "");
		torchRecipe = craftingConfig.getBoolean("torchRecipe", ENABLE_VANILLA_RECIPES, torchRecipe, "");
		trapDoorRecipe = craftingConfig.getBoolean("trapDoorRecipe", ENABLE_VANILLA_RECIPES, trapDoorRecipe, "");
		tripwireRecipe = craftingConfig.getBoolean("tripwireRecipe", ENABLE_VANILLA_RECIPES, tripwireRecipe, "");
		woodDoorRecipe = craftingConfig.getBoolean("woodDoorRecipe", ENABLE_VANILLA_RECIPES, woodDoorRecipe, "");
		woodSlabsRecipe = craftingConfig.getBoolean("woodSlabsRecipe", ENABLE_VANILLA_RECIPES, woodSlabsRecipe, "");
		woodStairsRecipe = craftingConfig.getBoolean("woodStairsRecipe", ENABLE_VANILLA_RECIPES, woodStairsRecipe, "");
		woodToolsRecipe = craftingConfig.getBoolean("woodToolsRecipe", ENABLE_VANILLA_RECIPES, woodToolsRecipe, "");
		woolRecipe = craftingConfig.getBoolean("woolRecipe", ENABLE_VANILLA_RECIPES, woolRecipe, "");

		if (craftingConfig.hasChanged()) craftingConfig.save();
	}

	/**
	 * Ordered the same as in TFCOptions!
	 */
	public static void reloadGeneral()
	{
		if (generalConfig == null) throw new IllegalStateException("Config reload attempt before preinit.");
		TerraFirmaCraft.log.info("(re)loading the general config (TFCConfig) settings...");

		generalConfig.addCustomCategoryComment(GENERAL, "Miscellaneous options");
		generalConfig.setCategoryLanguageKey(GENERAL, "config.gui.TFCConfig.general");

		enablePowderKegs = generalConfig.getBoolean("enablePowderKegs", GENERAL, enablePowderKegs, "Set this to false to disable powder keg explosions.", "config.gui.TFCConfig.general.enablePowderKegs");
		enableBetterGrass = generalConfig.getBoolean("enableBetterGrass", GENERAL, enableBetterGrass, "If true, then the side of a grass block which has another grass block adjacent and one block lower than it will show as completely grass.", "config.gui.TFCConfig.general.enableBetterGrass");
		enableDebugMode = generalConfig.getBoolean("enableDebugMode", GENERAL, enableDebugMode, "Set this to true if you want to turn on debug mode which is useful for bug hunting.", "config.gui.TFCConfig.general.enableDebugMode");
		onionsAreGross = generalConfig.getBoolean("onionsAreGross", GENERAL, onionsAreGross, "Set this to true if you don't like onions.", "config.gui.TFCConfig.general.onionsAreGross");
		quiverHUDPosition = generalConfig.getString("quiverHUDPosition", GENERAL, quiverHUDPosition, "Valid position strings are: bottomleft, left, topleft, bottomright, right, topright", new String[]{"bottomleft", "left", "topleft", "bottomright", "right", "topright"}, "config.gui.TFCConfig.general.quiverHUDPosition");
		enableSolidDetailed = generalConfig.getBoolean("enableSolidDetailed", GENERAL, enableSolidDetailed, "Should sides of detailed blocks be considered solid?", "config.gui.TFCConfig.general.enableSolidDetailed");
		maxRemovedSolidDetailed = generalConfig.getInt("maxRemovedSolidDetailed", GENERAL, maxRemovedSolidDetailed, 0, 64, "Maximum count of removed sub-blocks on one side for the detailed block side to still be solid.", "config.gui.TFCConfig.general.maxRemovedSolidDetailed");

		generalConfig.addCustomCategoryComment(TIME, "Time and Calendar related options");
		generalConfig.setCategoryLanguageKey(TIME, "config.gui.TFCConfig.time");

		yearLength = generalConfig.getInt("yearLength", TIME, yearLength, 12, 12000, "This is how many days are in a year. Keep this to multiples of 12.", "config.gui.TFCConfig.time.yearLength");
		if (yearLength % 12 != 0) yearLength = 12 + (12 * (yearLength / 12)); // Extra validation, because we need multiples of 12. Rounds up so it can never be 0.
		pitKilnBurnTime = generalConfig.getFloat("pitKilnBurnTime", TIME, pitKilnBurnTime, 1.0f, 2304.0f, "This is the number of hours that the pit kiln should burn before being completed.", "config.gui.TFCConfig.time.pitKilnBurnTime");
		bloomeryBurnTime = generalConfig.getFloat("bloomeryBurnTime", TIME, bloomeryBurnTime, 1.0f, 2304.0f, "This is the number of hours that the bloomery should burn before being completed.", "config.gui.TFCConfig.time.bloomeryBurnTime");
		charcoalPitBurnTime = generalConfig.getFloat("charcoalPitBurnTime", TIME, charcoalPitBurnTime, 1.0f, 2304.0f, "This is the number of hours that the charcoal pit should burn before being completed.", "config.gui.TFCConfig.time.charcoalPitBurnTime");
		torchBurnTime = generalConfig.getInt("torchBurnTime", TIME, torchBurnTime, 0, 2304, "This is how many in-game hours torches will last before burning out. Set to 0 for infinitely burning torches.", "config.gui.TFCConfig.time.torchBurnTime");
		saplingTimerMultiplier = generalConfig.getFloat("saplingTimerMultiplier", TIME, saplingTimerMultiplier, 0.01f, 100.0f, "This is a global multiplier for the number of days required before a sapling can grow into a tree. Decrease for faster sapling growth.", "config.gui.TFCConfig.time.saplingTimerMultiplier");
		tempIncreaseMultiplier = generalConfig.getFloat("tempIncreaseMultiplier", TIME, tempIncreaseMultiplier, 0.01f, 100.0f, "This is a global multiplier for the rate at which items heat up. Increase to make items heat up faster.", "config.gui.TFCConfig.time.tempIncreaseMultiplier");
		tempDecreaseMultiplier = generalConfig.getFloat("tempDecreaseMultiplier", TIME, tempDecreaseMultiplier, 0.01f, 100.0f, "This is a global multiplier for the rate at which items cool down. Increase to make items cool down faster.", "config.gui.TFCConfig.time.tempDecreaseMultiplier");
		oilLampFuelMult = generalConfig.getInt("oilLampFuelMult", TIME, oilLampFuelMult, 1, 50, "This determines how much fuel is used over time from oil lamps. Setting this higher will make fuel last longer. A mult of 1 = 250 hours, 4 = 1000 hours, 8 = 2000 hours.", "config.gui.TFCConfig.time.oilLampFuelMult");
		animalTimeMultiplier = generalConfig.getFloat("animalTimeMultiplier", TIME, animalTimeMultiplier, 0.01f, 100.0f, "This is a global multiplier for the gestation period of animals, as well as how long it takes for them to reach adulthood. Decrease for faster times.", "config.gui.TFCConfig.time.animalTimeMultiplier");

		generalConfig.addCustomCategoryComment(FOOD_DECAY, "Food decay related options");
		generalConfig.setCategoryLanguageKey(FOOD_DECAY, "config.gui.TFCConfig.fooddecay");

		foodDecayRate = generalConfig.getFloat("foodDecayRate", FOOD_DECAY, foodDecayRate, 1.0f, 2.0f, "This number causes base decay to equal 50% gain per day. If you wish to change, I recommend you look up a y-root calculator 1.0170378966055869517978300569768^24 = 1.5", "config.gui.TFCConfig.fooddecay.foodDecayRate");
		useDecayProtection = generalConfig.getBoolean("useDecayProtection", FOOD_DECAY, useDecayProtection, "Set this to false if you want food to auto decay when a chunk is loaded instead of limiting decay when a chunk has been unloaded for a long period.", "config.gui.TFCConfig.fooddecay.useDecayProtection");
		decayProtectionDays = generalConfig.getInt("decayProtectionDays", FOOD_DECAY, decayProtectionDays, 1, 12000, "If a food item has not been ticked for >= this number of days than when it is ticked for the first time, only a small amount of decay will occur.", "config.gui.TFCConfig.fooddecay.decayProtectionDays");
		decayMultiplier = generalConfig.getFloat("foodDecayMultiplier", FOOD_DECAY, decayMultiplier, 0.01f, 100.0f, "This is a global multiplier for food decay. Unlike FoodDecayRate which only modifies the base decay and not the environmental effect upon decay, this multiplier will multiply against the entire amount. Set to 0 to turn decay off.", "config.gui.TFCConfig.fooddecay.foodDecayMultiplier");

		generalConfig.addCustomCategoryComment(CAVEIN_OPTIONS, "Cave-in related options");
		generalConfig.setCategoryLanguageKey(CAVEIN_OPTIONS, "config.gui.TFCConfig.caveins");

		minimumRockLoad = generalConfig.getInt("minimumRockLoad", CAVEIN_OPTIONS, minimumRockLoad, 0, 256, "This is the minimum number of solid blocks that must be over a section in order for it to collapse.", "config.gui.TFCConfig.caveins.minimumRockLoad");
		initialCollapseRatio = generalConfig.getInt("initialCollapseRatio", CAVEIN_OPTIONS, initialCollapseRatio, 1, 1000, "This number is a 1 in X chance that when you mine a block, a collapse will occur.", "config.gui.TFCConfig.caveins.initialCollapseRatio");
		propogateCollapseChance = generalConfig.getInt("propogateCollapseChance", CAVEIN_OPTIONS, propogateCollapseChance, 1, 100, "This number is the likelihood for each block to propagate the collapse farther.", "config.gui.TFCConfig.caveins.propogateCollapseChance");
		enableCaveIns = generalConfig.getBoolean("enableCaveIns", CAVEIN_OPTIONS, enableCaveIns, "Set this to false to disable cave-ins.", "config.gui.TFCConfig.caveins.enableCaveIns");
		enableCaveInsDestroyOre = generalConfig.getBoolean("enableCaveInsDestroyOre", CAVEIN_OPTIONS, enableCaveInsDestroyOre, "Set this to false to make cave-ins drop the ore item instead of destroy it.", "config.gui.TFCConfig.caveins.enableCaveInsDestroyOre");

		generalConfig.addCustomCategoryComment(WORLD_GEN, "World gen options");
		generalConfig.setCategoryLanguageKey(WORLD_GEN, "config.gui.TFCConfig.worldgen");

		ravineRarity = generalConfig.getInt("ravineRarity", WORLD_GEN, ravineRarity, 0, 1000, "Controls the chance of a ravine generating, smaller value is higher chance, more ravines. Set to 0 to disable ravines.", "config.gui.TFCConfig.worldgen.ravineRarity");
		lavaFissureRarity = generalConfig.getInt("lavaFissureRarity", WORLD_GEN, lavaFissureRarity, 0, 1000, "Controls the chance of a lava fissure generating, smaller value is higher chance, more fissures. Set to 0 to disable lava fissures.", "config.gui.TFCConfig.worldgen.lavaFissureRarity");
		waterFissureRarity = generalConfig.getInt("waterFissureRarity", WORLD_GEN, waterFissureRarity, 0, 1000, "Controls the chance of a water fissure generating, smaller value is higher chance, more fissures. Set to 0 to disable water fissures.", "config.gui.TFCConfig.worldgen.waterFissureRarity");

		generalConfig.addCustomCategoryComment(CROPS, "Crop related options");
		generalConfig.setCategoryLanguageKey(CROPS, "config.gui.TFCConfig.crops");

		enableCropsDie = generalConfig.getBoolean("enableCropsDie", CROPS, enableCropsDie, "Set to true to enable crop death from old age.", "config.gui.TFCConfig.crops.enableCropsDie");
		cropGrowthMultiplier = generalConfig.getFloat("cropGrowthModifier", CROPS, cropGrowthMultiplier, 0.01f, 100.0f, "This is a global multiplier for the rate at which crops will grow. Increase to make crops grow faster.", "config.gui.TFCConfig.crops.cropGrowthModifier");

		generalConfig.addCustomCategoryComment(PROTECTION, "Spawn protection related options");
		generalConfig.setCategoryLanguageKey(PROTECTION, "config.gui.TFCConfig.protection");

		maxProtectionMonths = generalConfig.getInt("maxProtectionMonths", PROTECTION, maxProtectionMonths, 0, 120, "The maximum number of months of spawn protection that can accumulate.", "config.gui.TFCConfig.protection.maxProtectionMonths");
		protectionGain = generalConfig.getInt("protectionGain", PROTECTION, protectionGain, 0, 24, "The number of hours of protection gained in the 5x5 chunk area for spending 1 hour in that chunk.", "config.gui.TFCConfig.protection.protectionGain");
		protectionBuffer = generalConfig.getInt("protectionBuffer", PROTECTION, protectionBuffer, 0, 2304, "The minimum number of hours of protection that must be accumulated in a chunk in order to bypass the buffer and prevent hostile mob spawning.", "config.gui.TFCConfig.protection.protectionBuffer");

		generalConfig.addCustomCategoryComment(PLAYER, "Options related to 'the player'");
		generalConfig.setCategoryLanguageKey(PLAYER, "config.gui.TFCConfig.player");

		HealthGainRate = generalConfig.getInt("healthGainRate", PLAYER, HealthGainRate, 0, 100, "The rate of Health Gain per experience level. Set to 0 to turn off.", "config.gui.TFCConfig.player.healthGainRate");
		HealthGainCap = generalConfig.getInt("healthGainCap", PLAYER, HealthGainCap, 1000, 50000, "The maximum achievable health pool total.", "config.gui.TFCConfig.player.healthGainCap");

		generalConfig.addCustomCategoryComment(MATERIALS, "Material related options");
		generalConfig.setCategoryLanguageKey(MATERIALS, "config.gui.TFCConfig.materials");

		smallOreUnits = generalConfig.getInt("smallOreUnits", MATERIALS, smallOreUnits, 1, 100, "The metal units provided by a single piece of small ore or nugget.", "config.gui.TFCConfig.materials.smallOreUnits");
		poorOreUnits = generalConfig.getInt("poorOreUnits", MATERIALS, poorOreUnits, 1, 150, "The metal units provided by a single piece of poor ore.", "config.gui.TFCConfig.materials.poorOreUnits");
		normalOreUnits = generalConfig.getInt("normalOreUnits", MATERIALS, normalOreUnits, 1, 250, "The metal units provided by a single piece of normal ore.", "config.gui.TFCConfig.materials.normalOreUnits");
		richOreUnits = generalConfig.getInt("richOreUnits", MATERIALS, richOreUnits, 1, 350, "The metal units provided by a single piece of rich ore", "config.gui.TFCConfig.materials.richOreUnits");

		generalConfig.addCustomCategoryComment(SERVER, "Server only options");
		generalConfig.setCategoryLanguageKey(SERVER, "config.gui.TFCConfig.server");

		simSpeedNoPlayers = generalConfig.getInt("simSpeedNoPlayers", SERVER, simSpeedNoPlayers, 0, Integer.MAX_VALUE, "For every X number of ticks provided here, when there are no players online the server will only progress by 1 tick. Time advances 100 times slower than normal. Setting this to 0 will turn this feature off.", "config.gui.TFCConfig.server.simSpeedNoPlayers");

		generalConfig.addCustomCategoryComment(OVERWORKED, "Overworked chunks options");
		generalConfig.setCategoryLanguageKey(OVERWORKED, "config.gui.TFCConfig.overworked");

		enableOverworkingChunks = generalConfig.getBoolean("enableOverworkingChunks", OVERWORKED, enableOverworkingChunks, "Set this to false to disable the overworking of chunks when using a gold pan or sluice.", "config.gui.TFCConfig.overworked.enableOverworkingChunks");
		goldPanLimit = generalConfig.getInt("goldPanLimit", OVERWORKED, goldPanLimit, 1, 500, "The overworked cap for filling a gold pan in a specific chunk. Both filling a gold pan or using a sluice in the chunk count towards this value.", "config.gui.TFCConfig.overworked.goldPanLimit");
		sluiceLimit = generalConfig.getInt("sluiceLimit", OVERWORKED, sluiceLimit, 1, 3000, "The overworked cap for a sluice scanning one soil unit in a specific chunk. Both filling a gold pan or using a sluice in the chunk count towards this value", "config.gui.TFCConfig.overworked.sluiceLimit");

		if (!generalConfig.hasCategory(COLORS)) // Migrate old colors to there new homes
		{
			for (String catName : COLOR_CATEGORIES)
			{
				ConfigCategory cat = generalConfig.getCategory(catName);
				for (String propName : ImmutableSet.copyOf(cat.keySet()))
				{
					generalConfig.moveProperty(catName, propName, COLORS + '.' + catName);
				}
				generalConfig.removeCategory(cat);
			}
		}

		generalConfig.addCustomCategoryComment(COLORS, "Ingame color overlay things");
		generalConfig.setCategoryLanguageKey(COLORS, "config.gui.TFCConfig.colors");

		getColor(generalConfig, COLOR_NUTRIENT_A, cropNutrientAColor, "config.gui.TFCConfig.colors.nutrient_a");
		getColor(generalConfig, COLOR_NUTRIENT_B, cropNutrientBColor, "config.gui.TFCConfig.colors.nutrient_b");
		getColor(generalConfig, COLOR_NUTRIENT_C, cropNutrientCColor, "config.gui.TFCConfig.colors.nutrient_c");
		getColor(generalConfig, CROP_FERTILIZER_COLOR, cropFertilizerColor, "config.gui.TFCConfig.colors.fertilizer");
		getColor(generalConfig, ANVIL_RULE_COLOR0, anvilRuleColor0, "config.gui.TFCConfig.colors.anvil.0");
		getColor(generalConfig, ANVIL_RULE_COLOR1, anvilRuleColor1, "config.gui.TFCConfig.colors.anvil.1");
		getColor(generalConfig, ANVIL_RULE_COLOR2, anvilRuleColor2, "config.gui.TFCConfig.colors.anvil.2");

		//noinspection deprecation
		Global.FOOD_DECAY_RATE = foodDecayRate; // keep deprecated value up to date

		if (generalConfig.hasChanged()) generalConfig.save();
	}

	/**
	 * Does not return a new byte array, it modifies the default instance, which it returns for convenience.
	 * To deal with the fact that bytes are signed in java, and we like to use 0 - 255 in the config, values are being "& 0xFF".
	 */
	private static byte[] getColor(Configuration cfg, String subcat, byte[] def, String langKey)
	{
		final String cat = COLORS + '.' + subcat;
		cfg.setCategoryLanguageKey(cat, langKey);
		cfg.setCategoryPropertyOrder(cat, ImmutableList.of("Red", "Green", "Blue", "Alpha"));

		def[0] = (byte) cfg.getInt("Red", cat, def[0] & 0xFF, 0, 255, "", "config.gui.TFCConfig.colors.r");
		def[1] = (byte) cfg.getInt("Green", cat, def[1] & 0xFF, 0, 255, "", "config.gui.TFCConfig.colors.g");
		def[2] = (byte) cfg.getInt("Blue", cat, def[2] & 0xFF, 0, 255, "", "config.gui.TFCConfig.colors.b");
		if (def.length == 4) def[3] = (byte) cfg.getInt("Alpha", cat, def[3] & 0xFF, 0, 255, "", "config.gui.TFCConfig.colors.a");
		return def;
	}
}
