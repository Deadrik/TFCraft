//=======================================================
//Mod Client File
//=======================================================
package com.bioxx.tfc;

import java.io.File;
import java.util.Iterator;
import java.util.Set;

import net.minecraft.init.Blocks;
import net.minecraft.world.WorldType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

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
import com.bioxx.tfc.Core.TFCFluid;
import com.bioxx.tfc.Core.TFC_Achievements;
import com.bioxx.tfc.Core.TFC_Climate;
import com.bioxx.tfc.Core.TreeRegistry;
import com.bioxx.tfc.Core.Player.PlayerTracker;
import com.bioxx.tfc.Food.TFCPotion;
import com.bioxx.tfc.Handlers.AnvilCraftingHandler;
import com.bioxx.tfc.Handlers.ChatListenerTFC;
import com.bioxx.tfc.Handlers.ChunkDataEventHandler;
import com.bioxx.tfc.Handlers.ChunkEventHandler;
import com.bioxx.tfc.Handlers.CraftingHandler;
import com.bioxx.tfc.Handlers.EnteringChunkHandler;
import com.bioxx.tfc.Handlers.EntityDamageHandler;
import com.bioxx.tfc.Handlers.EntityLivingHandler;
import com.bioxx.tfc.Handlers.EntitySpawnHandler;
import com.bioxx.tfc.Handlers.FoodCraftingHandler;
import com.bioxx.tfc.Handlers.PlayerSkillEventHandler;
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
import com.bioxx.tfc.api.TFCCrafting;
import com.bioxx.tfc.api.TFCOptions;
import com.bioxx.tfc.api.Constant.Global;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = Reference.ModID, name = Reference.ModName, version = Reference.ModVersion, dependencies = Reference.ModDependencies)
public class TerraFirmaCraft
{
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
		loadSettings();
		loadCraftingSettings();

		proxy.registerTickHandler();

		// Register Liquids
		TFCFluid.register();

		TFCBlocks.LoadBlocks();
		TFCBlocks.RegisterBlocks();
		TFCBlocks.setupFire();
		loadOre();

		//Register Key Bindings(Client only)
		proxy.registerKeys();
		//Register KeyBinding Handler (Client only)
		proxy.registerKeyBindingHandler();
		//Register Block Highlight Handler (Client only)
		proxy.registerHighlightHandler();
		//Register Tile Entites
		proxy.registerTileEntities(true);
		//Register Sound Handler (Client only)
		proxy.registerSoundHandler();
		//Register Player Render Handler (Client only)
		proxy.registerPlayerRenderEventHandler();

		SkillsManager.instance.registerSkill(Global.SKILL_GENERAL_SMITHING);
		SkillsManager.instance.registerSkill(Global.SKILL_TOOLSMITH);
		SkillsManager.instance.registerSkill(Global.SKILL_ARMORSMITH);
		SkillsManager.instance.registerSkill(Global.SKILL_WEAPONSMITH);
		SkillsManager.instance.registerSkill(Global.SKILL_AGRICULTURE);
		SkillsManager.instance.registerSkill(Global.SKILL_COOKING);

		//Load Items
		TFCItems.Setup();

		// Register Gui Handler
		proxy.registerGuiHandler();

		//Register tree types and load tree schematics
		TreeRegistry.instance.LoadTreeTypes();
		TreeRegistry.instance.LoadTrees();

		if(true)
		{
			//Register Generators
			//Underground Lava
			GameRegistry.registerWorldGenerator(new WorldGenFissure(Blocks.lava, 2, true, 25).setUnderground(true, 20).setSeed(1), 0);
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

			DimensionManager.unregisterDimension(-1);
			DimensionManager.unregisterDimension(0);
			DimensionManager.unregisterDimension(1);

			DimensionManager.unregisterProviderType(-1);
			DimensionManager.unregisterProviderType(0);
			DimensionManager.unregisterProviderType(1);
			DimensionManager.registerProviderType(-1, TFCProviderHell.class, true);
			DimensionManager.registerProviderType(0, TFCProvider.class, true);
			DimensionManager.registerProviderType(1, TFCProvider.class, true);

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

		// Register the Entity Spawn Handler
		MinecraftForge.EVENT_BUS.register(new EntitySpawnHandler());

		// Register the Entity Hurt Handler
		MinecraftForge.EVENT_BUS.register(new EntityDamageHandler());

		// Register Chat Listener
		MinecraftForge.EVENT_BUS.register(new ChatListenerTFC());

		// Register the Chunk Load/Save Handler
		MinecraftForge.EVENT_BUS.register(new ChunkEventHandler());

		// Register the Chunk Data Load/Save Handler
		MinecraftForge.EVENT_BUS.register(new ChunkDataEventHandler());
		// Register the Chunk Load/Save Handler
		MinecraftForge.EVENT_BUS.register(new EnteringChunkHandler());

		// Register Anvil Crafting Handler
		MinecraftForge.EVENT_BUS.register(new AnvilCraftingHandler());

		MinecraftForge.EVENT_BUS.register(new PlayerSkillEventHandler());

		// Register the Entity Living Update Handler
		MinecraftForge.EVENT_BUS.register(new EntityLivingHandler());

		// Register all the render stuff for the client
		proxy.registerRenderInformation();

		proxy.registerBiomeEventHandler();
		proxy.setupGuiIngameForge();

		// Register Liquids
		TFCFluid.registerFluidContainers();
		proxy.registerFluidIcons();

		//Setup custom potion effects
		TFCPotion.Setup();

		//Register all of the recipes
		Recipes.registerRecipes();

		ItemHeat.SetupItemHeat();

		TFC_Climate.initCache();
	}

	@EventHandler
	public void postInit (FMLPostInitializationEvent evt)
	{
		packetPipeline.postInitialise();
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

	public void loadSettings()
	{
		Configuration config;
		try
		{
			config = new Configuration(new File(TerraFirmaCraft.proxy.getMinecraftDir(), "/config/TFCOptions.cfg"));
			config.load();
		} catch (Exception e) {
			System.out.println(new StringBuilder().append("[TFC] Error while trying to access settings configuration!").toString());
			config = null;
		}
		System.out.println(new StringBuilder().append("[TFC] Loading Settings").toString());
		/**Start setup here*/

		//General
		TFCOptions.enableBetterGrass = TFCOptions.getBooleanFor(config, "General", "enableBetterGrass", true);
		TFCOptions.use2DGrill = TFCOptions.getBooleanFor(config, "General", "use2DGrill", true);
		TFCOptions.enableInnerGrassFix = TFCOptions.getBooleanFor(config, "General", "enableInnerGrassFix", true, "Set this to false if your computer has to run in fast mode and you get lag. This setting forces the sides of grass to render when viewing from the inside.");
		TFCOptions.enableDebugMode = TFCOptions.getBooleanFor(config,"General","enableDebugMode",false, "Set this to true if you want to turn on debug mode which is useful for bug hunting");
		TFCOptions.iDontLikeOnions = TFCOptions.getBooleanFor(config, "General", "enableNotOnions", false,"Set this to true if you don't like onions.");
		TFCOptions.enableOreTest = TFCOptions.getBooleanFor(config, "General","enableOreTest", false, "This will generate only ore in your world with nothing else. *Caution Unsupported*");
		TFCOptions.quiverHUDPosition = TFCOptions.getStringFor(config, "General", "quiverHUDPosition", "bottomleft", "Valid position strings are: bottomleft, left, topleft, bottomright, right, topright");
		TFCOptions.generateSmoke = TFCOptions.getBooleanFor(config, "General", "generateSmoke", false, "Should forges generate smoke blocks?");

		//Time
		TFCOptions.yearLength = TFCOptions.getIntFor(config, "Time", "yearLength", 96, "This is how many days are in a year. Keep this to multiples of 12.");
		TFCOptions.pitKilnBurnTime = (float) TFCOptions.getDoubleFor(config, "Time", "pitKilnBurnTime", 8.0, "This is the number of hours that the pit kiln should burn before being completed.");
		TFCOptions.bloomeryBurnTime = (float) TFCOptions.getDoubleFor(config, "Time", "bloomeryBurnTime", 14.4, "This is the number of hours that the bloomery should burn before being completed.");
		TFCOptions.charcoalPitBurnTime = (float) TFCOptions.getDoubleFor(config, "Time", "charcoalPitBurnTime", 18.0, "This is the number of hours that the charcoal pit should burn before being completed.");
		TFCOptions.torchBurnTime = TFCOptions.getIntFor(config, "Time", "torchBurnTime", 48, "This is how many in-game hours torches will last before burning out. Set to 0 for infinitely burning torches.");
		TFCOptions.saplingTimerMultiplier = (float) TFCOptions.getDoubleFor(config, "Time", "saplingTimerMultiplier", 1.0, "This is a global multiplier for the number of days required before a sapling can grow into a tree. Decrease for faster sapling growth.");
		TFCOptions.tempIncreaseMultiplier = (float) TFCOptions.getDoubleFor(config, "Time", "tempIncreaseMultiplier", 1.0, "This is a global multiplier for the rate at which items heat up. Increase to make items heat up faster.");
		TFCOptions.tempDecreaseMultiplier = (float) TFCOptions.getDoubleFor(config, "Time", "tempDecreaseMultiplier", 1.0, "This is a global multiplier for the rate at which items cool down. Increase to make items cool down faster.");

		//Food Decay
		Global.FOOD_DECAY_RATE = TFCOptions.getDoubleFor(config,"Food Decay","FoodDecayRate", 1.0170378966055869517978300569768, "This number causes base decay to equal 50% gain per day. If you wish to change, I recommend you look up a y-root calculator 1.0170378966055869517978300569768^24 = 1.5");
		TFCOptions.useDecayProtection = TFCOptions.getBooleanFor(config, "Food Decay", "useDecayProtection", true,"Set this to false if you want food to auto decay when a chunk is loaded instead of limiting decay when a chunk has been unloaded for a long period.");
		TFCOptions.decayProtectionDays = TFCOptions.getIntFor(config,"Food Decay","decayProtectionDays",24, "If a food item has not been ticked for >= this number of days than when it is ticked for the first time, only a small amount of decay will occur.");
		TFCOptions.decayMultiplier = (float)TFCOptions.getDoubleFor(config,"Food Decay","FoodDecayMultiplier", 1.0, "This is a global multiplier for food decay. Unlike FoodDecayRate which only modifies the base decay and not the environmental effect upon decay, this multiplier will multiply against the entire amount. Set to 0 to turn decay off.");

		//Cavein Options
		TFCOptions.minimumRockLoad = TFCOptions.getIntFor(config,"Cavein Options","minimumRockLoad",1, "This is the minimum number of solid blocks that must be over a section in order for it to collapse.");
		TFCOptions.initialCollapseRatio = TFCOptions.getIntFor(config,"Cavein Options","initialCollapseRatio",20, "This number is a 1 in X chance that when you mine a block, a collapse will occur.");
		TFCOptions.propogateCollapseChance = TFCOptions.getIntFor(config,"Cavein Options","propogateCollapseChance",55, "This number is the likelihood for each block to propagate the collapse farther.");

		TFCOptions.cropNutrientAColor[0] = (byte) TFCOptions.getIntFor(config, "ColorNutrientA", "Red", 237);
		TFCOptions.cropNutrientAColor[1] = (byte) TFCOptions.getIntFor(config, "ColorNutrientA", "Green", 28);
		TFCOptions.cropNutrientAColor[2] = (byte) TFCOptions.getIntFor(config, "ColorNutrientA", "Blue", 36);
		TFCOptions.cropNutrientAColor[3] = (byte) TFCOptions.getIntFor(config, "ColorNutrientA", "Alpha", 200);

		TFCOptions.cropNutrientBColor[0] = (byte) TFCOptions.getIntFor(config, "ColorNutrientB", "Red", 242);
		TFCOptions.cropNutrientBColor[1] = (byte) TFCOptions.getIntFor(config, "ColorNutrientB", "Green", 101);
		TFCOptions.cropNutrientBColor[2] = (byte) TFCOptions.getIntFor(config, "ColorNutrientB", "Blue", 34);
		TFCOptions.cropNutrientBColor[3] = (byte) TFCOptions.getIntFor(config, "ColorNutrientB", "Alpha", 200);

		TFCOptions.cropNutrientCColor[0] = (byte) TFCOptions.getIntFor(config, "ColorNutrientC", "Red", 247);
		TFCOptions.cropNutrientCColor[1] = (byte) TFCOptions.getIntFor(config, "ColorNutrientC", "Green", 148);
		TFCOptions.cropNutrientCColor[2] = (byte) TFCOptions.getIntFor(config, "ColorNutrientC", "Blue", 49);
		TFCOptions.cropNutrientCColor[3] = (byte) TFCOptions.getIntFor(config, "ColorNutrientC", "Alpha", 200);

		TFCOptions.cropFertilizerColor[0] = (byte) TFCOptions.getIntFor(config, "cropFertilizerColor", "Red", 255);
		TFCOptions.cropFertilizerColor[1] = (byte) TFCOptions.getIntFor(config, "cropFertilizerColor", "Green", 255);
		TFCOptions.cropFertilizerColor[2] = (byte) TFCOptions.getIntFor(config, "cropFertilizerColor", "Blue", 0);
		TFCOptions.cropFertilizerColor[3] = (byte) TFCOptions.getIntFor(config, "cropFertilizerColor", "Alpha", 200);

		TFCOptions.anvilRuleColor0[0] = (byte) TFCOptions.getIntFor(config, "anvilRuleColor0", "Red", 237);
		TFCOptions.anvilRuleColor0[1] = (byte) TFCOptions.getIntFor(config, "anvilRuleColor0", "Green", 28);
		TFCOptions.anvilRuleColor0[2] = (byte) TFCOptions.getIntFor(config, "anvilRuleColor0", "Blue", 36);

		TFCOptions.anvilRuleColor1[0] = (byte) TFCOptions.getIntFor(config, "anvilRuleColor1", "Red", 242);
		TFCOptions.anvilRuleColor1[1] = (byte) TFCOptions.getIntFor(config, "anvilRuleColor1", "Green", 101);
		TFCOptions.anvilRuleColor1[2] = (byte) TFCOptions.getIntFor(config, "anvilRuleColor1", "Blue", 34);

		TFCOptions.anvilRuleColor2[0] = (byte) TFCOptions.getIntFor(config, "anvilRuleColor2", "Red", 247);
		TFCOptions.anvilRuleColor2[1] = (byte) TFCOptions.getIntFor(config, "anvilRuleColor2", "Green", 148);
		TFCOptions.anvilRuleColor2[2] = (byte) TFCOptions.getIntFor(config, "anvilRuleColor2", "Blue", 49);

		//Crops
		TFCOptions.enableCropsDie = TFCOptions.getBooleanFor(config, "Crops", "enableCropsDie", false, "Whether or not crops will die of old age.");
		TFCOptions.cropGrowthMultiplier = (float) TFCOptions.getDoubleFor(config, "Crops", "cropGrowthModifier", 1.0, "This is a global multiplier for the rate at which crops will grow. Increase to make crops grow faster.");

		//Protection
		TFCOptions.maxProtectionMonths = TFCOptions.getIntFor(config,"Protection","maxProtectionMonths", 10, "The maximum number of months of spawn protection that can accumulate.");
		TFCOptions.protectionGain = TFCOptions.getIntFor(config, "Protection", "protectionGain", 8, "The number of hours of protection gained in the 5x5 chunk area for spending 1 hour in that chunk.");

		//Player
		TFCOptions.HealthGainRate = TFCOptions.getIntFor(config,"Player","HealthGainRate", 20, "The rate of Health Gain per experience level. Set to 0 to turn off.");
		TFCOptions.HealthGainCap = TFCOptions.getIntFor(config,"Player","HealthGainCap", 3000, "The maximum achievable health pool total.");

		//Materials
		TFCOptions.smallOreUnits = TFCOptions.getIntFor(config, "Materials", "smallOreUnits", 10, "The metal units provided by a single piece of small ore or nugget.");
		TFCOptions.poorOreUnits = TFCOptions.getIntFor(config, "Materials", "poorOreUnits", 15, "The metal units provided by a single piece of poor ore.");
		TFCOptions.normalOreUnits = TFCOptions.getIntFor(config, "Materials", "normalOreUnits", 25, "The metal units provided by a single piece of normal ore.");
		TFCOptions.richOreUnits = TFCOptions.getIntFor(config, "Materials", "richOreUnits", 35, "The metal units provided by a single piece of rich ore");

		TFCOptions.simSpeedNoPlayers = TFCOptions.getIntFor(config, "Server", "simSpeedNoPlayers", 100, "For every X number of ticks provided here, when there are no players online the server will only progress by 1 tick. (Default: 100) Time advances 100 times slower than normal. Setting this to less than 1 will turn this feature off.");

		/**Always end with this*/
		if (config != null)
			config.save();
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
			System.out.println(new StringBuilder().append("[TFC] Error while trying to access crafting settings configuration!").toString());
			config = null;
		}
		System.out.println(new StringBuilder().append("[TFC] Loading Crafting Settings").toString());
		/**Start setup here*/

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
			System.out.println(new StringBuilder().append("[TFC] Error while trying to access Ore configuration!").toString());
			config = null;
		}
		System.out.println(new StringBuilder().append("[TFC] Loading Ore").toString());

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
		WorldGenOre.OreList.put("Bismuthinite Surface", getOreData(config, "Bismuthinite Surface", "veins", "small", Reference.ModID + ":Ore1", 7, 35, new String[]{"igneous intrusive", "sedimentary"}, 128, 240, 40, 40));
		WorldGenOre.OreList.put("Sphalerite Surface", getOreData(config, "Sphalerite Surface", "veins", "small", Reference.ModID + ":Ore1", 12, 35, new String[]{"metamorphic"}, 128, 240, 40, 40));
		WorldGenOre.OreList.put("Tetrahedrite Surface", getOreData(config, "Tetrahedrite Surface", "veins", "small", Reference.ModID + ":Ore1", 13, 35, new String[]{"metamorphic"}, 128, 240, 40, 40));

		/**Start setup here*/
		Set<String> names = config.getCategoryNames();
		Iterator oreIter = names.iterator();

		while(oreIter.hasNext())
		{
			String s = (String)oreIter.next();
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
