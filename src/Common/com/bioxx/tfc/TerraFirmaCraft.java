//=======================================================
//Mod Client File
//=======================================================
package com.bioxx.tfc;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import net.minecraft.world.WorldType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bioxx.tfc.Commands.CommandTime;
import com.bioxx.tfc.Commands.CommandTransferTamed;
import com.bioxx.tfc.Commands.DebugModeCommand;
import com.bioxx.tfc.Commands.GSPVisualCommand;
import com.bioxx.tfc.Commands.GenCommand;
import com.bioxx.tfc.Commands.GetBioTempCommand;
import com.bioxx.tfc.Commands.GetBodyTemp;
import com.bioxx.tfc.Commands.GetRocksCommand;
import com.bioxx.tfc.Commands.GetSpawnProtectionCommand;
import com.bioxx.tfc.Commands.GetTreesCommand;
import com.bioxx.tfc.Commands.GiveSkillCommand;
import com.bioxx.tfc.Commands.PrintImageMapCommand;
import com.bioxx.tfc.Commands.RemoveAreaCommand;
import com.bioxx.tfc.Commands.RemoveChunkCommand;
import com.bioxx.tfc.Commands.SetPlayerStatsCommand;
import com.bioxx.tfc.Commands.StripChunkCommand;
import com.bioxx.tfc.Core.ItemHeat;
import com.bioxx.tfc.Core.Recipes;
import com.bioxx.tfc.Core.TFC_Achievements;
import com.bioxx.tfc.Core.TFC_Climate;
import com.bioxx.tfc.Core.TFC_OreDictionary;
import com.bioxx.tfc.Core.Player.PlayerTracker;
import com.bioxx.tfc.Food.TFCPotion;
import com.bioxx.tfc.Handlers.AnvilCraftingHandler;
import com.bioxx.tfc.Handlers.ChatListenerTFC;
import com.bioxx.tfc.Handlers.ChunkEventHandler;
import com.bioxx.tfc.Handlers.CraftingHandler;
import com.bioxx.tfc.Handlers.EnteringChunkHandler;
import com.bioxx.tfc.Handlers.EntityDamageHandler;
import com.bioxx.tfc.Handlers.EntityLivingHandler;
import com.bioxx.tfc.Handlers.EntitySpawnHandler;
import com.bioxx.tfc.Handlers.FoodCraftingHandler;
import com.bioxx.tfc.Handlers.PlayerInteractHandler;
import com.bioxx.tfc.Handlers.TFCFuelHandler;
import com.bioxx.tfc.Handlers.Network.PacketPipeline;
import com.bioxx.tfc.WorldGen.TFCProvider;
import com.bioxx.tfc.WorldGen.TFCProviderHell;
import com.bioxx.tfc.WorldGen.TFCWorldType;
import com.bioxx.tfc.WorldGen.Generators.OreSpawnData;
import com.bioxx.tfc.WorldGen.Generators.WorldGenCaveDecor;
import com.bioxx.tfc.WorldGen.Generators.WorldGenFissure;
import com.bioxx.tfc.WorldGen.Generators.WorldGenFissureCluster;
import com.bioxx.tfc.WorldGen.Generators.WorldGenForests;
import com.bioxx.tfc.WorldGen.Generators.WorldGenLargeRock;
import com.bioxx.tfc.WorldGen.Generators.WorldGenLooseRocks;
import com.bioxx.tfc.WorldGen.Generators.WorldGenOre;
import com.bioxx.tfc.WorldGen.Generators.WorldGenPlants;
import com.bioxx.tfc.WorldGen.Generators.WorldGenSoilPits;
import com.bioxx.tfc.api.SkillsManager;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCCrafting;
import com.bioxx.tfc.api.TFCOptions;
import com.bioxx.tfc.api.Constant.Global;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = Reference.ModID, name = Reference.ModName, version = Reference.ModVersion, dependencies = Reference.ModDependencies, guiFactory = Reference.GUIFactory)
public class TerraFirmaCraft
{
	public static Logger log = LogManager.getLogger("TerraFirmaCraft");
	public static Configuration config;
	// Configuration Headers -- Must be entirely lowercase!
	public static final String GENERAL_HEADER = "general";
	public static final String TIME_HEADER = "time";
	public static final String FOOD_DECAY_HEADER = "food decay";
	public static final String CAVEIN_OPTIONS_HEADER = "cave-ins";
	public static final String WORLD_GEN_HEADER = "world generation";
	public static final String COLOR_HEADER = "colors";
	public static final String COLOR_NUTRIENT_A_HEADER = "color nutrient a";
	public static final String COLOR_NUTRIENT_B_HEADER = "color nutrient b";
	public static final String COLOR_NUTRIENT_C_HEADER = "color nutrient c";
	public static final String CROP_FERTILIZER_COLOR_HEADER = "color fertilizer";
	public static final String ANVIL_RULE_COLOR0_HEADER = "color anvil rule 0";
	public static final String ANVIL_RULE_COLOR1_HEADER = "color anvil rule 1";
	public static final String ANVIL_RULE_COLOR2_HEADER = "color anvil rule 2";
	public static final String CROPS_HEADER = "crops";
	public static final String PROTECTION_HEADER = "spawn protection";
	public static final String PLAYER_HEADER = "player";
	public static final String MATERIALS_HEADER = "materials";
	public static final String SERVER_HEADER = "server";
	public static final String OVERWORKED_HEADER = "overworked chunks";
	
	@Instance("TerraFirmaCraft")
	public static TerraFirmaCraft instance;

	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;

	// The packet pipeline
	public static final PacketPipeline packetPipeline = new PacketPipeline();

	public TerraFirmaCraft()
	{
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		instance = this;
		//Load our settings from the TFCOptions file
		try
		{
			config = new Configuration(new File(TerraFirmaCraft.proxy.getMinecraftDir(), "/config/TFCConfig.cfg"));
			config.load();
		} catch (Exception e)
		{
			TerraFirmaCraft.log.error(new StringBuilder().append("Error while trying to access settings configuration!").toString());
			config = null;
		}
		loadSettings();
		loadCraftingSettings();

		proxy.registerTickHandler();

		proxy.registerFluids();

		BlockSetup.LoadBlocks();
		BlockSetup.RegisterBlocks();
		BlockSetup.setupFire();


		//Register Key Bindings(Client only)
		proxy.registerKeys();
		//Register KeyBinding Handler (Client only)
		proxy.registerKeyBindingHandler();
		//Register Block Highlight Handler (Client only)
		proxy.registerHandlers();
		//Register Tile Entites
		proxy.registerTileEntities(true);
		//Register Sound Handler (Client only)
		proxy.registerSoundHandler();
		//Register Player Render Handler (Client only)
		proxy.registerPlayerRenderEventHandler();

		SkillsManager.instance.registerSkill(Global.SKILL_GENERAL_SMITHING, 250);
		SkillsManager.instance.registerSkill(Global.SKILL_TOOLSMITH, 100);
		SkillsManager.instance.registerSkill(Global.SKILL_ARMORSMITH, 100);
		SkillsManager.instance.registerSkill(Global.SKILL_WEAPONSMITH, 100);
		SkillsManager.instance.registerSkill(Global.SKILL_AGRICULTURE, 300);
		SkillsManager.instance.registerSkill(Global.SKILL_COOKING, 200);
		SkillsManager.instance.registerSkill(Global.SKILL_PROSPECTING, 1500);
		SkillsManager.instance.registerSkill(Global.SKILL_BUTCHERING, 100);

		//Load Items
		ItemSetup.Setup();



		// Register Gui Handler
		proxy.registerGuiHandler();

		if(true)
		{
			//Register Generators
			//Underground Lava
			GameRegistry.registerWorldGenerator(new WorldGenFissure(TFCBlocks.Lava, 2, true, TFCOptions.lavaFissureRarity).setUnderground(true, 20).setSeed(1), 0);
			GameRegistry.registerWorldGenerator(new WorldGenFissure(TFCBlocks.FreshWaterStationary, 2, false, TFCOptions.waterFissureRarity), 0);
			//Surface Hotsprings
			GameRegistry.registerWorldGenerator(new WorldGenFissureCluster(), 1);
			GameRegistry.registerWorldGenerator(new WorldGenOre(), 2);
			GameRegistry.registerWorldGenerator(new WorldGenCaveDecor(), 3);
			GameRegistry.registerWorldGenerator(new WorldGenForests(), 4);
			GameRegistry.registerWorldGenerator(new WorldGenLooseRocks(), 5);
			GameRegistry.registerWorldGenerator(new WorldGenSoilPits(), 6);
			GameRegistry.registerWorldGenerator(new WorldGenLargeRock(), 7);
			GameRegistry.registerWorldGenerator(new WorldGenPlants(), 8);

			WorldType.DEFAULT = new TFCWorldType(0, "TFCDefault");
			WorldType.FLAT = new TFCWorldType(1, "TFCFlat");
			WorldType.LARGE_BIOMES = new TFCWorldType(2, "TFCLargeBiomes");
			WorldType.AMPLIFIED = new TFCWorldType(3, "TFCAmplified");

			DimensionManager.unregisterDimension(-1);
			DimensionManager.unregisterDimension(0);
			DimensionManager.unregisterDimension(1);

			DimensionManager.unregisterProviderType(-1);
			DimensionManager.unregisterProviderType(0);
			DimensionManager.unregisterProviderType(1);
			DimensionManager.registerProviderType(-1, TFCProviderHell.class, false);
			DimensionManager.registerProviderType(0, TFCProvider.class, true);
			DimensionManager.registerProviderType(1, TFCProvider.class, false);

			DimensionManager.registerDimension(-1, -1);
			DimensionManager.registerDimension(0, 0);
			DimensionManager.registerDimension(1, 1);
		}
	}

	@EventHandler
	public void initialize(FMLInitializationEvent event)
	{
		// Register Packet Handler
		packetPipeline.initalise();

		//Register our player tracker
		FMLCommonHandler.instance().bus().register(new PlayerTracker());

		//Register the tool classes
		proxy.registerToolClasses();

		//Register Achievements
		TFC_Achievements.init();

		// Register Crafting Handler
		FMLCommonHandler.instance().bus().register(new CraftingHandler());
		FMLCommonHandler.instance().bus().register(new FoodCraftingHandler());
		FMLCommonHandler.instance().bus().register(instance);

		// Register Player Interact Handler - for drinking water & item pickups.
		MinecraftForge.EVENT_BUS.register(new PlayerInteractHandler());

		// Register the Entity Spawn Handler
		MinecraftForge.EVENT_BUS.register(new EntitySpawnHandler());

		// Register the Entity Hurt Handler
		MinecraftForge.EVENT_BUS.register(new EntityDamageHandler());

		// Register Chat Listener
		MinecraftForge.EVENT_BUS.register(new ChatListenerTFC());

		// Register the Chunk Load/Save Handler
		MinecraftForge.EVENT_BUS.register(new ChunkEventHandler());

		// Register the Chunk Load/Save Handler
		MinecraftForge.EVENT_BUS.register(new EnteringChunkHandler());

		// Register Anvil Crafting Handler
		MinecraftForge.EVENT_BUS.register(new AnvilCraftingHandler());

		// Register the Entity Living Update Handler
		MinecraftForge.EVENT_BUS.register(new EntityLivingHandler());

		// Register all the render stuff for the client
		proxy.registerRenderInformation();

		proxy.registerBiomeEventHandler();
		proxy.setupGuiIngameForge();

		// Register Liquids
		proxy.setupFluids();
		proxy.registerFluidIcons();

		//Setup custom potion effects
		TFCPotion.Setup();

		//Register all of the recipes
		TFC_OreDictionary.registerOreDict();
		Recipes.registerRecipes();

		ItemHeat.SetupItemHeat();

		TFC_Climate.initCache();

		//Register TFC items with forge fuel handler.
		//This is used by vanilla furnice and many other mods.
		ItemSetup.registerFurniceFuel();
		GameRegistry.registerFuelHandler(new TFCFuelHandler());

		//Register ChiselModes
		proxy.registerChiselModes();

		//WAILA stuff
		proxy.registerWailaClasses();
		proxy.hideNEIItems();
	}

	@EventHandler
	public void postInit (FMLPostInitializationEvent evt)
	{
		packetPipeline.postInitialise();
		loadOre();
	}

	@EventHandler
	public void modsLoaded(FMLPostInitializationEvent evt)
	{
		ForgeModContainer.zombieBabyChance = 0;
	}

	@EventHandler
	public void serverStarting(FMLServerStartingEvent evt)
	{
		evt.registerServerCommand(new GetBioTempCommand());
		evt.registerServerCommand(new GetTreesCommand());
		evt.registerServerCommand(new GetRocksCommand());
		evt.registerServerCommand(new GetSpawnProtectionCommand());
		evt.registerServerCommand(new SetPlayerStatsCommand());
		evt.registerServerCommand(new GetBodyTemp());
		evt.registerServerCommand(new RemoveChunkCommand());
		evt.registerServerCommand(new StripChunkCommand());
		evt.registerServerCommand(new GSPVisualCommand());
		evt.registerServerCommand(new RemoveAreaCommand());
		evt.registerServerCommand(new DebugModeCommand());
		evt.registerServerCommand(new CommandTime());
		evt.registerServerCommand(new GenCommand());
		evt.registerServerCommand(new PrintImageMapCommand());
		evt.registerServerCommand(new GiveSkillCommand());
		evt.registerServerCommand(new CommandTransferTamed());
	}	

	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs)
	{
		if (eventArgs.modID.equals(Reference.ModID))
			loadSettings();
	}

	public void loadSettings()
	{
		TerraFirmaCraft.log.info(new StringBuilder().append("Loading Settings").toString());
		/**Start setup here*/

		//General
		TFCOptions.enableBetterGrass = config.getBoolean("enableBetterGrass", GENERAL_HEADER, true, "If true, then the side of a grass block which has another grass block adjacent and one block lower than it will show as completely grass.");
		TFCOptions.enableDebugMode = config.getBoolean("enableDebugMode", GENERAL_HEADER, false, "Set this to true if you want to turn on debug mode which is useful for bug hunting.");
		TFCOptions.onionsAreGross = config.getBoolean("onionsAreGross", GENERAL_HEADER, false, "Set this to true if you don't like onions.");
		TFCOptions.quiverHUDPosition = config.getString("quiverHUDPosition", GENERAL_HEADER, "bottomleft", "Valid position strings are: bottomleft, left, topleft, bottomright, right, topright");
		TFCOptions.enableSolidDetailed = config.getBoolean("enableSolidDetailed", GENERAL_HEADER, true, "Should sides of detailed blocks be considered solid?");
		TFCOptions.maxRemovedSolidDetailed = config.getInt("maxRemovedSolidDetailed", GENERAL_HEADER, 12, 0, 64, "Maximum count of removed sub-blocks on one side for the detailed block side to still be solid.");
		TFCOptions.enablePowderKegs = config.getBoolean("enablePowderKegs", GENERAL_HEADER, true, "Set this to false to disable powder keg explosions.");

		//TFCOptions.generateSmoke = config.getBoolean("generateSmoke", GENERAL_HEADER, false, "Should forges generate smoke blocks? *Caution Unsupported*");
		//TFCOptions.use2DGrill = config.getBoolean("use2DGrill", GENERAL_HEADER, true);

		//Time
		TFCOptions.yearLength = config.getInt("yearLength", TIME_HEADER, 96, 0, 12000, "This is how many days are in a year. Keep this to multiples of 12.");
		TFCOptions.pitKilnBurnTime = config.getFloat("pitKilnBurnTime", TIME_HEADER, 8.0f, 1.0f, 2304.0f, "This is the number of hours that the pit kiln should burn before being completed.");
		TFCOptions.bloomeryBurnTime = config.getFloat("bloomeryBurnTime", TIME_HEADER, 14.4f, 1.0f, 2304.0f, "This is the number of hours that the bloomery should burn before being completed.");
		TFCOptions.charcoalPitBurnTime = config.getFloat("charcoalPitBurnTime", TIME_HEADER, 18.0f, 1.0f, 2304.0f, "This is the number of hours that the charcoal pit should burn before being completed.");
		TFCOptions.torchBurnTime = config.getInt("torchBurnTime", TIME_HEADER, 48, 0, 2304, "This is how many in-game hours torches will last before burning out. Set to 0 for infinitely burning torches.");
		TFCOptions.saplingTimerMultiplier = config.getFloat("saplingTimerMultiplier", TIME_HEADER, 1.0f, 0.01f, 100.0f, "This is a global multiplier for the number of days required before a sapling can grow into a tree. Decrease for faster sapling growth.");
		TFCOptions.tempIncreaseMultiplier = config.getFloat("tempIncreaseMultiplier", TIME_HEADER, 1.0f, 0.01f, 100.0f, "This is a global multiplier for the rate at which items heat up. Increase to make items heat up faster.");
		TFCOptions.tempDecreaseMultiplier = config.getFloat("tempDecreaseMultiplier", TIME_HEADER, 1.0f, 0.01f, 100.0f, "This is a global multiplier for the rate at which items cool down. Increase to make items cool down faster.");
		TFCOptions.oilLampFuelMult = config.getInt("oilLampFuelMult", TIME_HEADER, 8, 1, 50, "This determines how much fuel is used over time from oil lamps. Setting this higher will make fuel last longer. A mult of 1 = 250 hours, 4 = 1000 hours, 8 = 2000 hours.");
		TFCOptions.animalTimeMultiplier = config.getFloat("animalTimeMultiplier", TIME_HEADER, 1.0f, 0.01f, 100.0f, "This is a global multiplier for the gestation period of animals, as well as how long it takes for them to reach adulthood. Decrease for faster times.");

		//Food Decay
		Global.FOOD_DECAY_RATE = config.getFloat("foodDecayRate", FOOD_DECAY_HEADER, 1.0170378966055869517978300569768f, 1.0f, 2.0f, "This number causes base decay to equal 50% gain per day. If you wish to change, I recommend you look up a y-root calculator 1.0170378966055869517978300569768^24 = 1.5");
		TFCOptions.useDecayProtection = config.getBoolean("useDecayProtection", FOOD_DECAY_HEADER, true, "Set this to false if you want food to auto decay when a chunk is loaded instead of limiting decay when a chunk has been unloaded for a long period.");
		TFCOptions.decayProtectionDays = config.getInt("decayProtectionDays", FOOD_DECAY_HEADER, 24, 1, 12000, "If a food item has not been ticked for >= this number of days than when it is ticked for the first time, only a small amount of decay will occur.");
		TFCOptions.decayMultiplier = config.getFloat("foodDecayMultiplier", FOOD_DECAY_HEADER, 1.0f, 0.01f, 100.0f, "This is a global multiplier for food decay. Unlike FoodDecayRate which only modifies the base decay and not the environmental effect upon decay, this multiplier will multiply against the entire amount. Set to 0 to turn decay off.");

		//Cavein Options
		TFCOptions.minimumRockLoad = config.getInt("minimumRockLoad", CAVEIN_OPTIONS_HEADER, 1, 0, 256, "This is the minimum number of solid blocks that must be over a section in order for it to collapse.");
		TFCOptions.initialCollapseRatio = config.getInt("initialCollapseRatio", CAVEIN_OPTIONS_HEADER, 10, 1, 1000, "This number is a 1 in X chance that when you mine a block, a collapse will occur.");
		TFCOptions.propogateCollapseChance = config.getInt("propogateCollapseChance", CAVEIN_OPTIONS_HEADER, 55, 1, 100, "This number is the likelihood for each block to propagate the collapse farther.");
		TFCOptions.enableCaveIns = config.getBoolean("enableCaveIns", CAVEIN_OPTIONS_HEADER, true, "Set this to false to disable cave-ins.");
		TFCOptions.enableCaveInsDestroyOre = config.getBoolean("enableCaveInsDestroyOre", CAVEIN_OPTIONS_HEADER, true, "Set this to false to make cave-ins drop the ore item instead of destroy it.");
		
		// World Generation
		TFCOptions.ravineRarity = config.getInt("ravineRarity", WORLD_GEN_HEADER, 100, 0, 1000, "Controls the chance of a ravine generating, smaller value is higher chance, more ravines. Set to 0 to disable ravines.");
		TFCOptions.lavaFissureRarity = config.getInt("lavaFissureRarity", WORLD_GEN_HEADER, 25, 0, 1000, "Controls the chance of a lava fissure generating, smaller value is higher chance, more fissures. Set to 0 to disable lava fissures.");
		TFCOptions.waterFissureRarity = config.getInt("waterFissureRarity", WORLD_GEN_HEADER, 90, 0, 1000, "Controls the chance of a water fissure generating, smaller value is higher chance, more fissures. Set to 0 to disable water fissures.");

		TFCOptions.cropNutrientAColor[0] = (byte) config.getInt("Red", COLOR_NUTRIENT_A_HEADER, 237, 0, 255, "");
		TFCOptions.cropNutrientAColor[1] = (byte) config.getInt("Green", COLOR_NUTRIENT_A_HEADER, 28, 0, 255, "");
		TFCOptions.cropNutrientAColor[2] = (byte) config.getInt("Blue", COLOR_NUTRIENT_A_HEADER, 36, 0, 255, "");
		TFCOptions.cropNutrientAColor[3] = (byte) config.getInt("Alpha", COLOR_NUTRIENT_A_HEADER, 200, 0, 255, "");

		TFCOptions.cropNutrientBColor[0] = (byte) config.getInt("Red", COLOR_NUTRIENT_B_HEADER, 242, 0, 255, "");
		TFCOptions.cropNutrientBColor[1] = (byte) config.getInt("Green", COLOR_NUTRIENT_B_HEADER, 101, 0, 255, "");
		TFCOptions.cropNutrientBColor[2] = (byte) config.getInt("Blue", COLOR_NUTRIENT_B_HEADER, 34, 0, 255, "");
		TFCOptions.cropNutrientBColor[3] = (byte) config.getInt("Alpha", COLOR_NUTRIENT_B_HEADER, 200, 0, 255, "");

		TFCOptions.cropNutrientCColor[0] = (byte) config.getInt("Red", COLOR_NUTRIENT_C_HEADER, 247, 0, 255, "");
		TFCOptions.cropNutrientCColor[1] = (byte) config.getInt("Green", COLOR_NUTRIENT_C_HEADER, 148, 0, 255, "");
		TFCOptions.cropNutrientCColor[2] = (byte) config.getInt("Blue", COLOR_NUTRIENT_C_HEADER, 49, 0, 255, "");
		TFCOptions.cropNutrientCColor[3] = (byte) config.getInt("Alpha", COLOR_NUTRIENT_C_HEADER, 200, 0, 255, "");

		TFCOptions.cropFertilizerColor[0] = (byte) config.getInt("Red", CROP_FERTILIZER_COLOR_HEADER, 255, 0, 255, "");
		TFCOptions.cropFertilizerColor[1] = (byte) config.getInt("Green", CROP_FERTILIZER_COLOR_HEADER, 255, 0, 255, "");
		TFCOptions.cropFertilizerColor[2] = (byte) config.getInt("Blue", CROP_FERTILIZER_COLOR_HEADER, 0, 0, 255, "");
		TFCOptions.cropFertilizerColor[3] = (byte) config.getInt("Alpha", CROP_FERTILIZER_COLOR_HEADER, 200, 0, 255, "");

		TFCOptions.anvilRuleColor0[0] = (byte) config.getInt("Red", ANVIL_RULE_COLOR0_HEADER, 237, 0, 255, "");
		TFCOptions.anvilRuleColor0[1] = (byte) config.getInt("Green", ANVIL_RULE_COLOR0_HEADER, 28, 0, 255, "");
		TFCOptions.anvilRuleColor0[2] = (byte) config.getInt("Blue", ANVIL_RULE_COLOR0_HEADER, 36, 0, 255, "");

		TFCOptions.anvilRuleColor1[0] = (byte) config.getInt("Red", ANVIL_RULE_COLOR1_HEADER, 242, 0, 255, "");
		TFCOptions.anvilRuleColor1[1] = (byte) config.getInt("Green", ANVIL_RULE_COLOR1_HEADER, 101, 0, 255, "");
		TFCOptions.anvilRuleColor1[2] = (byte) config.getInt("Blue", ANVIL_RULE_COLOR1_HEADER, 34, 0, 255, "");

		TFCOptions.anvilRuleColor2[0] = (byte) config.getInt("Red", ANVIL_RULE_COLOR2_HEADER, 247, 0, 255, "");
		TFCOptions.anvilRuleColor2[1] = (byte) config.getInt("Green", ANVIL_RULE_COLOR2_HEADER, 148, 0, 255, "");
		TFCOptions.anvilRuleColor2[2] = (byte) config.getInt("Blue", ANVIL_RULE_COLOR2_HEADER, 49, 0, 255, "");

		//Crops
		TFCOptions.enableCropsDie = config.getBoolean("enableCropsDie", CROPS_HEADER, false, "Set to true to enable crop death from old age.");
		TFCOptions.cropGrowthMultiplier = config.getFloat("cropGrowthModifier", CROPS_HEADER, 1.0f, 0.01f, 100.0f, "This is a global multiplier for the rate at which crops will grow. Increase to make crops grow faster.");

		//Protection
		TFCOptions.maxProtectionMonths = config.getInt("maxProtectionMonths", PROTECTION_HEADER, 10, 0, 120, "The maximum number of months of spawn protection that can accumulate.");
		TFCOptions.protectionGain = config.getInt("protectionGain", PROTECTION_HEADER, 8, 0, 24, "The number of hours of protection gained in the 5x5 chunk area for spending 1 hour in that chunk.");
		TFCOptions.protectionBuffer = config.getInt("protectionBuffer", PROTECTION_HEADER, 24, 0, 2304, "The minimum number of hours of protection that must be accumulated in a chunk in order to bypass the buffer and prevent hostile mob spawning.");

		//Player
		TFCOptions.HealthGainRate = config.getInt("healthGainRate", PLAYER_HEADER, 20, 0, 100, "The rate of Health Gain per experience level. Set to 0 to turn off.");
		TFCOptions.HealthGainCap = config.getInt("healthGainCap", PLAYER_HEADER, 3000, 1000, 50000, "The maximum achievable health pool total.");

		//Materials
		TFCOptions.smallOreUnits = config.getInt("smallOreUnits", MATERIALS_HEADER, 10, 1, 100, "The metal units provided by a single piece of small ore or nugget.");
		TFCOptions.poorOreUnits = config.getInt("poorOreUnits", MATERIALS_HEADER, 15, 1, 150, "The metal units provided by a single piece of poor ore.");
		TFCOptions.normalOreUnits = config.getInt("normalOreUnits", MATERIALS_HEADER, 25, 1, 250, "The metal units provided by a single piece of normal ore.");
		TFCOptions.richOreUnits = config.getInt("richOreUnits", MATERIALS_HEADER, 35, 1, 350, "The metal units provided by a single piece of rich ore");

		TFCOptions.simSpeedNoPlayers = config.getInt("simSpeedNoPlayers", SERVER_HEADER, 100, 0, Integer.MAX_VALUE, "For every X number of ticks provided here, when there are no players online the server will only progress by 1 tick. (Default: 100) Time advances 100 times slower than normal. Setting this to 0 will turn this feature off.");

		// Overworked Chunks
		TFCOptions.enableOverworkingChunks = config.getBoolean("enableOverworkingChunks", OVERWORKED_HEADER, true, "Set this to false to disable the overworking of chunks when using a gold pan or sluice.");
		TFCOptions.goldPanLimit = config.getInt("goldPanLimit", OVERWORKED_HEADER, 50, 1, 500, "The overworked cap for filling a gold pan in a specific chunk. Both filling a gold pan or using a sluice in the chunk count towards this value.");
		TFCOptions.sluiceLimit = config.getInt("sluiceLimit", OVERWORKED_HEADER, 300, 1, 3000, "The overworked cap for a sluice scanning one soil unit in a specific chunk. Both filling a gold pan or using a sluice in the chunk count towards this value");

		validateSettings();
		/**Always end with this*/
		if (config.hasChanged())
			config.save();
	}

	public void validateSettings()
	{
		TerraFirmaCraft.log.info(new StringBuilder().append("Validating Settings").toString());
		ArrayList<String> configList = new ArrayList<String>();

		if (TFCOptions.maxRemovedSolidDetailed < 0 || TFCOptions.maxRemovedSolidDetailed > 64)
		{
			configList.add("maxRemovedSolidDetailed");
			TFCOptions.maxRemovedSolidDetailed = 12;
		}
		if (TFCOptions.yearLength < 12 || TFCOptions.yearLength % 12 != 0)
		{
			configList.add("yearLength");
			TFCOptions.yearLength = 96;
		}
		if (TFCOptions.pitKilnBurnTime <= 0f)
		{
			configList.add("pitKilnBurnTime");
			TFCOptions.pitKilnBurnTime = 8.0f;
		}
		if (TFCOptions.bloomeryBurnTime <= 0f)
		{
			configList.add("bloomeryBurnTime");
			TFCOptions.bloomeryBurnTime = 14.4f;
		}
		if (TFCOptions.charcoalPitBurnTime <= 0f)
		{
			configList.add("charcoalPitBurnTime");
			TFCOptions.charcoalPitBurnTime = 18.0f;
		}
		if (TFCOptions.torchBurnTime < 0)
		{
			configList.add("torchBurnTime");
			TFCOptions.torchBurnTime = 48;
		}
		if (TFCOptions.saplingTimerMultiplier <= 0)
		{
			configList.add("saplingTimerMultiplier");
			TFCOptions.saplingTimerMultiplier = 1.0f;
		}
		if (TFCOptions.tempIncreaseMultiplier <= 0)
		{
			configList.add("tempIncreaseMultiplier");
			TFCOptions.tempIncreaseMultiplier = 1.0f;
		}
		if (TFCOptions.tempDecreaseMultiplier <= 0)
		{
			configList.add("tempDecreaseMultiplier");
			TFCOptions.tempDecreaseMultiplier = 1.0f;
		}
		if (TFCOptions.animalTimeMultiplier <= 0)
		{
			configList.add("animalTimeMultiplier");
			TFCOptions.animalTimeMultiplier = 1.0f;
		}
		if (Global.FOOD_DECAY_RATE < 1)
		{
			configList.add("FoodDecayRate");
			Global.FOOD_DECAY_RATE = 1.0170378966055869517978300569768;
		}
		if (TFCOptions.decayProtectionDays <= 0)
		{
			configList.add("decayProtectionDays");
			TFCOptions.decayProtectionDays = 24;
		}
		if (TFCOptions.decayMultiplier < 0)
		{
			configList.add("FoodDecayMultiplier");
			TFCOptions.decayMultiplier = 1.0f;
		}
		if (TFCOptions.minimumRockLoad < 0)
		{
			configList.add("minimumRockLoad");
			TFCOptions.minimumRockLoad = 1;
		}
		if (TFCOptions.initialCollapseRatio <= 0)
		{
			configList.add("initialCollapseRatio");
			TFCOptions.initialCollapseRatio = 10;
		}
		if (TFCOptions.propogateCollapseChance <= 0)
		{
			configList.add("propogateCollapseChance");
			TFCOptions.propogateCollapseChance = 55;
		}
		if (TFCOptions.ravineRarity < 0)
		{
			configList.add("ravineRarity");
			TFCOptions.ravineRarity = 100;
		}
		if (TFCOptions.lavaFissureRarity < 0)
		{
			configList.add("lavaFissureRarity");
			TFCOptions.lavaFissureRarity = 25;
		}
		if (TFCOptions.waterFissureRarity < 0)
		{
			configList.add("waterFissureRarity");
			TFCOptions.waterFissureRarity = 90;
		}
		if (TFCOptions.cropGrowthMultiplier <= 0)
		{
			configList.add("cropGrowthMultiplier");
			TFCOptions.cropGrowthMultiplier = 1.0f;
		}
		if (TFCOptions.maxProtectionMonths <= 0)
		{
			configList.add("maxProtectionMonths");
			TFCOptions.maxProtectionMonths = 10;
		}
		if (TFCOptions.protectionGain < 0)
		{
			configList.add("protectionGain");
			TFCOptions.protectionGain = 8;
		}
		if (TFCOptions.protectionBuffer < 0)
		{
			configList.add("protectionBuffer");
			TFCOptions.protectionBuffer = 24;
		}
		if (TFCOptions.HealthGainRate < 0)
		{
			configList.add("HealthGainRate");
			TFCOptions.HealthGainRate = 20;
		}
		if (TFCOptions.HealthGainCap < 1000)
		{
			configList.add("HealthGainCap");
			TFCOptions.HealthGainCap = 3000;
		}
		if (TFCOptions.smallOreUnits <= 0)
		{
			configList.add("smallOreUnits");
			TFCOptions.smallOreUnits = 10;
		}
		if (TFCOptions.poorOreUnits <= 0)
		{
			configList.add("poorOreUnits");
			TFCOptions.poorOreUnits = 15;
		}
		if (TFCOptions.normalOreUnits <= 0)
		{
			configList.add("normalOreUnits");
			TFCOptions.normalOreUnits = 25;
		}
		if (TFCOptions.richOreUnits <= 0)
		{
			configList.add("richOreUnits");
			TFCOptions.richOreUnits = 35;
		}
		if (TFCOptions.simSpeedNoPlayers < 0)
		{
			configList.add("simSpeedNoPlayers");
			TFCOptions.simSpeedNoPlayers = 100;
		}
		if (TFCOptions.goldPanLimit < 0)
		{
			configList.add("goldPanLimit");
			TFCOptions.goldPanLimit = 50;
		}
		if (TFCOptions.sluiceLimit < 0)
		{
			configList.add("sluiceLimit");
			TFCOptions.sluiceLimit = 300;
		}

		if (!configList.isEmpty())
		{
			String badConfigs = "";
			for (String s : configList)
				badConfigs += s + " ";
			TerraFirmaCraft.log.warn("An invalid value has been entered for " + badConfigs + "in the config file. Using default value(s) instead.");
		}
	}

	public void loadCraftingSettings()
	{
		Configuration config;
		try
		{
			config = new Configuration(new File(TerraFirmaCraft.proxy.getMinecraftDir(), "/config/TFCCrafting.cfg"));
			config.load();
		} catch (Exception e)
		{
			TerraFirmaCraft.log.error(new StringBuilder().append("Error while trying to access crafting settings configuration!").toString());
			config = null;
		}
		TerraFirmaCraft.log.info(new StringBuilder().append("Loading Crafting Settings").toString());
		/**Start setup here*/
		TFCCrafting.enableNEIHiding = TFCCrafting.getBooleanFor(config, "NEI Hiding", "enableNEIHiding", true, "Set to false to show hidden items in NEI. Note that most of these items were hidden to prevent players from cheating them in and crashing their game.");

		//Conversions
		TFCCrafting.appleConversion = TFCCrafting.getBooleanFor(config, "Conversion", "appleConversion", false, "Conversions for food are irreversible.");
		TFCCrafting.arrowConversion = TFCCrafting.getBooleanFor(config, "Conversion", "arrowConversion", false);
		TFCCrafting.bowConversion = TFCCrafting.getBooleanFor(config, "Conversion", "bowConversion", false);
		TFCCrafting.coalConversion = TFCCrafting.getBooleanFor(config, "Conversion", "coalConversion", false);
		TFCCrafting.diamondConversion = TFCCrafting.getBooleanFor(config, "Conversion", "diamondConversion", false);
		TFCCrafting.emeraldConversion = TFCCrafting.getBooleanFor(config, "Conversion", "emeraldConversion", false);
		TFCCrafting.fishConversion = TFCCrafting.getBooleanFor(config, "Conversion", "fishConversion", false);
		TFCCrafting.fishingRodConversion = TFCCrafting.getBooleanFor(config, "Conversion", "fishingRodConversion", false);
		TFCCrafting.flintSteelConversion = TFCCrafting.getBooleanFor(config, "Conversion", "flintSteelConversion", false);
		TFCCrafting.leatherArmorConversion = TFCCrafting.getBooleanFor(config, "Conversion", "leatherArmorConversion", false);
		TFCCrafting.leatherConversion = TFCCrafting.getBooleanFor(config, "Conversion", "leatherConversion", false);
		TFCCrafting.stoneAxeConversion = TFCCrafting.getBooleanFor(config, "Conversion", "stoneAxeConversion", false);
		TFCCrafting.stoneHoeConversion = TFCCrafting.getBooleanFor(config, "Conversion", "stoneHoeConversion", false);
		TFCCrafting.stoneShovelConversion = TFCCrafting.getBooleanFor(config, "Conversion", "stoneShovelConversion", false);
		TFCCrafting.woodButtonConversion = TFCCrafting.getBooleanFor(config, "Conversion", "woodButtonConversion", false);
		TFCCrafting.workbenchConversion = TFCCrafting.getBooleanFor(config, "Conversion", "workbenchConversion", false);

		//Vanilla Recipes
		TFCCrafting.anvilRecipe = TFCCrafting.getBooleanFor(config, "Enable Vanilla Recipes", "anvilRecipe", false);
		TFCCrafting.arrowsRecipe = TFCCrafting.getBooleanFor(config, "Enable Vanilla Recipes", "arrowsRecipe", false);
		TFCCrafting.bedRecipe = TFCCrafting.getBooleanFor(config, "Enable Vanilla Recipes", "bedRecipe", false);
		TFCCrafting.bonemealRecipe = TFCCrafting.getBooleanFor(config, "Enable Vanilla Recipes", "bonemealRecipe", false);
		TFCCrafting.bowlRecipe = TFCCrafting.getBooleanFor(config, "Enable Vanilla Recipes", "bowlRecipe", false);
		TFCCrafting.brewingRecipe = TFCCrafting.getBooleanFor(config, "Enable Vanilla Recipes", "brewingRecipe", false);
		TFCCrafting.bucketRecipe = TFCCrafting.getBooleanFor(config, "Enable Vanilla Recipes", "bucketRecipe", false);
		TFCCrafting.cauldronRecipe = TFCCrafting.getBooleanFor(config, "Enable Vanilla Recipes", "cauldronRecipe", true);
		TFCCrafting.chestRecipe = TFCCrafting.getBooleanFor(config, "Enable Vanilla Recipes", "chestRecipe", false);
		TFCCrafting.clockRecipe = TFCCrafting.getBooleanFor(config, "Enable Vanilla Recipes", "clockRecipe", true);
		TFCCrafting.compassRecipe = TFCCrafting.getBooleanFor(config, "Enable Vanilla Recipes", "compassRecipe", true);
		TFCCrafting.dandelionYellowRecipe = TFCCrafting.getBooleanFor(config, "Enable Vanilla Recipes", "dandelionYellowRecipe", false);
		TFCCrafting.diamondArmorRecipe = TFCCrafting.getBooleanFor(config, "Enable Vanilla Recipes", "diamondArmorRecipe", false);
		TFCCrafting.diamondBlockRecipe = TFCCrafting.getBooleanFor(config, "Enable Vanilla Recipes", "diamondBlockRecipe", false);
		TFCCrafting.diamondToolsRecipe = TFCCrafting.getBooleanFor(config, "Enable Vanilla Recipes", "diamondToolsRecipe", false);
		TFCCrafting.dispenserRecipe = TFCCrafting.getBooleanFor(config, "Enable Vanilla Recipes", "dispenserRecipe", true);
		TFCCrafting.dropperRecipe = TFCCrafting.getBooleanFor(config, "Enable Vanilla Recipes", "dropperRecipe", true);
		TFCCrafting.enchantTableRecipe = TFCCrafting.getBooleanFor(config, "Enable Vanilla Recipes", "enchantTableRecipe", false);
		TFCCrafting.fenceGateRecipe = TFCCrafting.getBooleanFor(config, "Enable Vanilla Recipes", "fenceGateRecipe", false);
		TFCCrafting.fenceRecipe = TFCCrafting.getBooleanFor(config, "Enable Vanilla Recipes", "fenceRecipe", true);
		TFCCrafting.furnaceRecipe = TFCCrafting.getBooleanFor(config, "Enable Vanilla Recipes", "furnaceRecipe", false);
		TFCCrafting.goldAppleRecipe = TFCCrafting.getBooleanFor(config, "Enable Vanilla Recipes", "goldAppleRecipe", false);
		TFCCrafting.goldArmorRecipe = TFCCrafting.getBooleanFor(config, "Enable Vanilla Recipes", "goldArmorRecipe", false);
		TFCCrafting.goldBlockRecipe = TFCCrafting.getBooleanFor(config, "Enable Vanilla Recipes", "goldBlockRecipe", false);
		TFCCrafting.goldNuggetRecipe = TFCCrafting.getBooleanFor(config, "Enable Vanilla Recipes", "goldNuggetRecipe", false);
		TFCCrafting.goldPlateRecipe = TFCCrafting.getBooleanFor(config, "Enable Vanilla Recipes", "goldPlateRecipe", true);
		TFCCrafting.goldToolsRecipe = TFCCrafting.getBooleanFor(config, "Enable Vanilla Recipes", "goldToolsRecipe", false);
		TFCCrafting.hopperRecipe = TFCCrafting.getBooleanFor(config, "Enable Vanilla Recipes", "hopperRecipe", false);
		TFCCrafting.ironArmorRecipe = TFCCrafting.getBooleanFor(config, "Enable Vanilla Recipes", "ironArmorRecipe", false);
		TFCCrafting.ironBarsRecipe = TFCCrafting.getBooleanFor(config, "Enable Vanilla Recipes", "ironBarsRecipe", true);
		TFCCrafting.ironBlockRecipe = TFCCrafting.getBooleanFor(config, "Enable Vanilla Recipes", "ironBlockRecipe", false);
		TFCCrafting.ironDoorRecipe = TFCCrafting.getBooleanFor(config, "Enable Vanilla Recipes", "ironDoorRecipe", true);
		TFCCrafting.ironPlateRecipe = TFCCrafting.getBooleanFor(config, "Enable Vanilla Recipes", "ironPlateRecipe", true);
		TFCCrafting.ironToolsRecipe = TFCCrafting.getBooleanFor(config, "Enable Vanilla Recipes", "ironToolsRecipe", false);
		TFCCrafting.jukeboxRecipe = TFCCrafting.getBooleanFor(config, "Enable Vanilla Recipes", "jukeboxRecipe", false);
		TFCCrafting.leatherArmorRecipe = TFCCrafting.getBooleanFor(config, "Enable Vanilla Recipes", "leatherArmorRecipe", false);
		TFCCrafting.leverRecipe = TFCCrafting.getBooleanFor(config, "Enable Vanilla Recipes", "leverRecipe", false);
		TFCCrafting.minecartChestRecipe = TFCCrafting.getBooleanFor(config, "Enable Vanilla Recipes", "minecartChestRecipe", false);
		TFCCrafting.minecartRecipe = TFCCrafting.getBooleanFor(config, "Enable Vanilla Recipes", "minecartRecipe", false);
		TFCCrafting.pistonRecipe = TFCCrafting.getBooleanFor(config, "Enable Vanilla Recipes", "pistonRecipe", true);
		TFCCrafting.plankBlockRecipe = TFCCrafting.getBooleanFor(config, "Enable Vanilla Recipes", "plankBlockRecipe", false);
		TFCCrafting.poweredRailsRecipe = TFCCrafting.getBooleanFor(config, "Enable Vanilla Recipes", "poweredRailsRecipe", false);
		TFCCrafting.railsRecipe = TFCCrafting.getBooleanFor(config, "Enable Vanilla Recipes", "railsRecipe", false);
		TFCCrafting.repeaterRecipe = TFCCrafting.getBooleanFor(config, "Enable Vanilla Recipes", "repeaterRecipe", true);
		TFCCrafting.roseRedRecipe = TFCCrafting.getBooleanFor(config, "Enable Vanilla Recipes", "roseRedRecipe", false);
		TFCCrafting.shearsRecipe = TFCCrafting.getBooleanFor(config, "Enable Vanilla Recipes", "shearsRecipe", false);
		TFCCrafting.signRecipe = TFCCrafting.getBooleanFor(config, "Enable Vanilla Recipes", "signRecipe", false);
		TFCCrafting.stickRecipe = TFCCrafting.getBooleanFor(config, "Enable Vanilla Recipes", "stickRecipe", false);
		TFCCrafting.stoneSlabsRecipe = TFCCrafting.getBooleanFor(config, "Enable Vanilla Recipes", "stoneSlabsRecipe", false);
		TFCCrafting.stoneStairsRecipe = TFCCrafting.getBooleanFor(config, "Enable Vanilla Recipes", "stoneStairsRecipe", false);
		TFCCrafting.stoneToolsRecipe = TFCCrafting.getBooleanFor(config, "Enable Vanilla Recipes", "stoneToolsRecipe", false);
		TFCCrafting.torchRecipe = TFCCrafting.getBooleanFor(config, "Enable Vanilla Recipes", "torchRecipe", false);
		TFCCrafting.trapDoorRecipe = TFCCrafting.getBooleanFor(config, "Enable Vanilla Recipes", "trapDoorRecipe", false);
		TFCCrafting.tripwireRecipe = TFCCrafting.getBooleanFor(config, "Enable Vanilla Recipes", "tripwireRecipe", true);
		TFCCrafting.woodDoorRecipe = TFCCrafting.getBooleanFor(config, "Enable Vanilla Recipes", "woodDoorRecipe", true);
		TFCCrafting.woodSlabsRecipe = TFCCrafting.getBooleanFor(config, "Enable Vanilla Recipes", "woodSlabsRecipe", false);
		TFCCrafting.woodStairsRecipe = TFCCrafting.getBooleanFor(config, "Enable Vanilla Recipes", "woodStairsRecipe", false);
		TFCCrafting.woodToolsRecipe = TFCCrafting.getBooleanFor(config, "Enable Vanilla Recipes", "woodToolsRecipe", false);
		TFCCrafting.woolRecipe = TFCCrafting.getBooleanFor(config, "Enable Vanilla Recipes", "woolRecipe", false);

		/**Always end with this*/
		if (config != null)
			config.save();
	}

	public void loadOre()
	{
		Configuration config;
		try
		{
			config = new Configuration(new File(TerraFirmaCraft.proxy.getMinecraftDir(), "/config/TFCOre.cfg"));
			config.load();
		} catch (Exception e) {
			TerraFirmaCraft.log.error(new StringBuilder().append("Error while trying to access Ore configuration!").toString());
			config = null;
		}
		TerraFirmaCraft.log.info(new StringBuilder().append("Loading Ore").toString());

		WorldGenOre.OreList.put("Native Copper", getOreData(config, "Native Copper", "veins", "large", Reference.ModID + ":Ore1", 0, 120, new String[]{"igneous extrusive"}, 5, 128, 80, 60));
		WorldGenOre.OreList.put("Native Gold", getOreData(config, "Native Gold", "veins", "large", Reference.ModID + ":Ore1", 1, 120, new String[]{"igneous extrusive", "igneous intrusive"}, 5, 128, 80, 60));
		WorldGenOre.OreList.put("Platinum", getOreData(config, "Platinum", "veins", "small", Reference.ModID + ":Ore1", 2, 150, new String[]{"sedimentary"}, 5, 128, 40, 80));
		WorldGenOre.OreList.put("Hematite", getOreData(config, "Hematite", "veins", "medium", Reference.ModID + ":Ore1", 3, 125, new String[]{"igneous extrusive"}, 5, 128, 80, 60));
		WorldGenOre.OreList.put("Silver", getOreData(config, "Silver", "veins", "medium", Reference.ModID + ":Ore1", 4, 100, new String[]{"granite", "gneiss"}, 5, 128, 80, 60));
		WorldGenOre.OreList.put("Cassiterite", getOreData(config, "Cassiterite", "veins", "medium", Reference.ModID + ":Ore1", 5, 100, new String[]{"igneous intrusive"}, 5, 128, 80, 60));
		WorldGenOre.OreList.put("Galena", getOreData(config, "Galena", "veins", "medium", Reference.ModID + ":Ore1", 6, 100, new String[]{"igneous extrusive", "metamorphic", "granite", "limestone"}, 5, 128, 80, 60));
		WorldGenOre.OreList.put("Bismuthinite", getOreData(config, "Bismuthinite", "veins", "medium", Reference.ModID + ":Ore1", 7, 100, new String[]{"igneous extrusive", "sedimentary"}, 5, 128, 80, 60));
		WorldGenOre.OreList.put("Garnierite", getOreData(config, "Garnierite", "veins", "medium", Reference.ModID + ":Ore1", 8, 150, new String[]{"gabbro"}, 5, 128, 80, 60));
		WorldGenOre.OreList.put("Malachite", getOreData(config, "Malachite", "veins", "large", Reference.ModID + ":Ore1", 9, 100, new String[]{"limestone", "marble"}, 5, 128, 80, 60));
		WorldGenOre.OreList.put("Magnetite", getOreData(config, "Magnetite", "veins", "medium", Reference.ModID + ":Ore1", 10, 150, new String[]{"sedimentary"}, 5, 128, 80, 60));
		WorldGenOre.OreList.put("Limonite", getOreData(config, "Limonite", "veins", "medium", Reference.ModID + ":Ore1", 11, 150, new String[]{"sedimentary"}, 5, 128, 80, 60));
		WorldGenOre.OreList.put("Sphalerite", getOreData(config, "Sphalerite", "veins", "medium", Reference.ModID + ":Ore1", 12, 100, new String[]{"metamorphic"}, 5, 128, 80, 60));
		WorldGenOre.OreList.put("Tetrahedrite", getOreData(config, "Tetrahedrite", "veins", "medium", Reference.ModID + ":Ore1", 13, 120, new String[]{"metamorphic"}, 5, 128, 80, 60));
		WorldGenOre.OreList.put("Bituminous Coal", getOreData(config, "Bituminous Coal", "default", "large", Reference.ModID + ":Ore1", 14, 100, new String[]{"sedimentary"}, 5, 128, 90, 40));
		WorldGenOre.OreList.put("Lignite", getOreData(config, "Lignite", "default", "medium", Reference.ModID + ":Ore1", 15, 100, new String[]{"sedimentary"}, 5, 128, 90, 40));

		WorldGenOre.OreList.put("Kaolinite", getOreData(config, "Kaolinite", "default", "medium", Reference.ModID + ":Ore2", 0, 90, new String[]{"sedimentary"}, 5, 128, 80, 60));
		WorldGenOre.OreList.put("Gypsum", getOreData(config, "Gypsum", "veins", "large", Reference.ModID + ":Ore2", 1, 120, new String[]{"sedimentary"}, 5, 128, 80, 60));
		//WorldGenOre.OreList.put("Satinspar", getOreData(config, "Satinspar", "veins", "small", Reference.ModID + ":Ore2", 2, 150, new String[]{"sedimentary"}, 5, 128, 40, 80));
		//WorldGenOre.OreList.put("Selenite", getOreData(config, "Selenite", "veins", "medium", Reference.ModID + ":Ore2", 3, 125, new String[]{"igneous extrusive"}, 5, 128, 60, 60));
		WorldGenOre.OreList.put("Graphite", getOreData(config, "Graphite", "veins", "medium", Reference.ModID + ":Ore2", 4, 100, new String[]{"marble", "gneiss", "quartzite", "schist"}, 5, 128, 80, 60));
		WorldGenOre.OreList.put("Kimberlite", getOreData(config, "Kimberlite", "veins", "medium", Reference.ModID + ":Ore2", 5, 200, new String[]{"gabbro"}, 5, 128, 30, 80));
		//WorldGenOre.OreList.put("Petrified Wood", getOreData(config, "Petrified Wood", "veins", "medium", Reference.ModID + ":Ore2", 6, 100, new String[]{"igneous extrusive", "metamorphic", "granite", "limestone"}, 5, 128, 60, 60));
		//WorldGenOre.OreList.put("Sulfur", getOreData(config, "Sulfur", "veins", "medium", Reference.ModID + ":Ore2", 7, 100, new String[]{"igneous extrusive", "sedimentary"}, 5, 128, 60, 60));
		WorldGenOre.OreList.put("Jet", getOreData(config, "Jet", "veins", "large", Reference.ModID + ":Ore2", 8, 110, new String[]{"sedimentary"}, 5, 128, 80, 60));
		//WorldGenOre.OreList.put("Microcline", getOreData(config, "Microcline", "veins", "large", Reference.ModID + ":Ore2", 9, 100, new String[]{"limestone", "marble"}, 5, 128, 60, 60));
		WorldGenOre.OreList.put("Pitchblende", getOreData(config, "Pitchblende", "veins", "small", Reference.ModID + ":Ore2", 10, 150, new String[]{"granite"}, 5, 128, 80, 60));
		WorldGenOre.OreList.put("Cinnabar", getOreData(config, "Cinnabar", "veins", "small", Reference.ModID + ":Ore2", 11, 150, new String[]{"igneous extrusive", "shale", "quartzite"}, 5, 128, 30, 80));
		WorldGenOre.OreList.put("Cryolite", getOreData(config, "Cryolite", "veins", "small", Reference.ModID + ":Ore2", 12, 100, new String[]{"granite"}, 5, 128, 80, 60));
		WorldGenOre.OreList.put("Saltpeter", getOreData(config, "Saltpeter", "veins", "medium", Reference.ModID + ":Ore2", 13, 120, new String[]{"sedimentary"}, 5, 128, 80, 60));
		//WorldGenOre.OreList.put("Serpentine", getOreData(config, "Serpentine", "veins", "large", Reference.ModID + ":Ore2", 14, 100, new String[]{"sedimentary"}, 5, 128, 90, 40));
		WorldGenOre.OreList.put("Sylvite", getOreData(config, "Sylvite", "veins", "medium", Reference.ModID + ":Ore2", 15, 100, new String[]{"rock salt"}, 5, 128, 90, 40));

		WorldGenOre.OreList.put("Borax", getOreData(config, "Borax", "veins", "large", Reference.ModID + ":Ore3", 0, 120, new String[]{"rock salt"}, 5, 128, 80, 60));
		WorldGenOre.OreList.put("Lapis Lazuli", getOreData(config, "Lapis Lazuli", "veins", "large", Reference.ModID + ":Ore3", 2, 120, new String[]{"marble"}, 5, 128, 80, 60));
		//WorldGenOre.OreList.put("Olivine", getOreData(config, "Olivine", "veins", "small", Reference.ModID + ":Ore3", 1, 150, new String[]{"sedimentary"}, 5, 128, 40, 80));

		//Surface Ore
		WorldGenOre.OreList.put("Native Copper Surface", getOreData(config, "Native Copper Surface", "veins", "small", Reference.ModID + ":Ore1", 0, 35, new String[]{"igneous extrusive"}, 128, 240, 40, 40));
		WorldGenOre.OreList.put("Cassiterite Surface", getOreData(config, "Cassiterite Surface", "veins", "small", Reference.ModID + ":Ore1", 5, 35, new String[]{"granite"}, 128, 240, 40, 40));
		WorldGenOre.OreList.put("Bismuthinite Surface", getOreData(config, "Bismuthinite Surface", "veins", "small", Reference.ModID + ":Ore1", 7, 35, new String[]{"igneous extrusive", "sedimentary"}, 128, 240, 40, 40));
		WorldGenOre.OreList.put("Sphalerite Surface", getOreData(config, "Sphalerite Surface", "veins", "small", Reference.ModID + ":Ore1", 12, 35, new String[]{"metamorphic"}, 128, 240, 40, 40));
		WorldGenOre.OreList.put("Tetrahedrite Surface", getOreData(config, "Tetrahedrite Surface", "veins", "small", Reference.ModID + ":Ore1", 13, 35, new String[]{"metamorphic"}, 128, 240, 40, 40));

		/**Start setup here*/
		Set<String> names = config.getCategoryNames();
		Iterator<String> oreIter = names.iterator();

		while(oreIter.hasNext())
		{
			String s = oreIter.next();
			OreSpawnData data = new OreSpawnData(
					config.get(s, "type", "default").getString(),
					config.get(s, "size", "small").getString(),
					config.get(s, "oreName", "Ore").getString(),
					config.get(s, "oreMeta", 0).getInt(),
					config.get(s, "rarity", 100).getInt(),
					config.get(s, "baseRocks", "granite, basalt, sedimentary").getStringList(),
					config.get(s, "Minimum Height", 5).getInt(),
					config.get(s, "Maximum Height", 128).getInt(),
					config.get(s, "Vertical Density", 50).getInt(),
					config.get(s, "Horizontal Density", 50).getInt()
					);

			//If this is a new entry
			if(!WorldGenOre.OreList.containsKey(s))
				WorldGenOre.OreList.put(s, data);
			else //There is an entry that already exists for this name so we want to replace the default.
			{
				WorldGenOre.OreList.remove(s);
				WorldGenOre.OreList.put(s, data);
			}
		}

		/**Always end with this*/
		if (config != null)
			config.save();
	}

	private static OreSpawnData getOreData(Configuration config, String category, String type, String size, 
			String blockName, int meta, int rarity, String[] rocks, int min, int max, int v, int h)
	{
		return new OreSpawnData(
				config.get(category, "type", type).getString(),
				config.get(category, "size", size).getString(),
				config.get(category, "oreName", blockName).getString(),
				config.get(category, "oreMeta", meta).getInt(),
				config.get(category, "rarity", rarity).getInt(),
				config.get(category, "baseRocks", rocks).getStringList(),
				config.get(category, "Minimum Height", min).getInt(),
				config.get(category, "Maximum Height", max).getInt(),
				config.get(category, "Vertical Density", v).getInt(),
				config.get(category, "Horizontal Density", h).getInt()
				);
	}
}
