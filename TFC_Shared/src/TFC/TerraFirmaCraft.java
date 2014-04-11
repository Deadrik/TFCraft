//=======================================================
//Mod Client File
//=======================================================
package TFC;

import java.io.File;
import java.util.Iterator;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.liquids.LiquidContainerData;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidDictionary;
import TFC.API.SkillsManager;
import TFC.API.TFCOptions;
import TFC.API.Constant.Global;
import TFC.API.Constant.TFCBlockID;
import TFC.API.Constant.TFCItemID;
import TFC.Commands.CommandTime;
import TFC.Commands.DebugModeCommand;
import TFC.Commands.GSPVisualCommand;
import TFC.Commands.GetBioTempCommand;
import TFC.Commands.GetBodyTemp;
import TFC.Commands.GetRocksCommand;
import TFC.Commands.GetSpawnProtectionCommand;
import TFC.Commands.GetTreesCommand;
import TFC.Commands.RemoveAreaCommand;
import TFC.Commands.RemoveChunkCommand;
import TFC.Commands.SetPlayerStatsCommand;
import TFC.Commands.StripChunkCommand;
import TFC.Core.Recipes;
import TFC.Core.TFC_Achievements;
import TFC.Core.TFC_Climate;
import TFC.Core.TFC_ItemHeat;
import TFC.Core.Player.PlayerTracker;
import TFC.Core.Util.Localization;
import TFC.Food.TFCPotion;
import TFC.Handlers.AnvilCraftingHandler;
import TFC.Handlers.ChatListenerTFC;
import TFC.Handlers.ChunkDataEventHandler;
import TFC.Handlers.ChunkEventHandler;
import TFC.Handlers.CraftingHandler;
import TFC.Handlers.EnteringChunkHandler;
import TFC.Handlers.EntityDamageHandler;
import TFC.Handlers.EntityLivingHandler;
import TFC.Handlers.EntitySpawnHandler;
import TFC.Handlers.FoodCraftingHandler;
import TFC.Handlers.PacketHandler;
import TFC.Handlers.PickupHandler;
import TFC.Handlers.PlayerSkillEventHandler;
import TFC.Handlers.PlayerTossEventHandler;
import TFC.Handlers.ServerTickHandler;
import TFC.Handlers.Client.ClientTickHandler;
import TFC.WorldGen.TFCProvider;
import TFC.WorldGen.TFCProviderHell;
import TFC.WorldGen.TFCWorldType;
import TFC.WorldGen.Generators.OreSpawnData;
import TFC.WorldGen.Generators.WorldGenCaveDecor;
import TFC.WorldGen.Generators.WorldGenFissure;
import TFC.WorldGen.Generators.WorldGenFissureCluster;
import TFC.WorldGen.Generators.WorldGenForests;
import TFC.WorldGen.Generators.WorldGenLargeRock;
import TFC.WorldGen.Generators.WorldGenLooseRocks;
import TFC.WorldGen.Generators.WorldGenOre;
import TFC.WorldGen.Generators.WorldGenPlants;
import TFC.WorldGen.Generators.WorldGenSoilPits;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

@Mod(modid = Reference.ModID, name = Reference.ModName, version = Reference.ModVersion, dependencies = Reference.ModDependencies)
@NetworkMod(channels = { Reference.ModChannel }, clientSideRequired = false, serverSideRequired = false, packetHandler = PacketHandler.class)
public class TerraFirmaCraft
{
	@Instance("TerraFirmaCraft")
	public static TerraFirmaCraft instance;

	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;

	public TerraFirmaCraft()
	{

	}


	@EventHandler
	public void preInit(FMLPreInitializationEvent event) 
	{
		instance = this;
		//Load our settings from the TFCOptions file
		loadSettings();

		TickRegistry.registerTickHandler(new ServerTickHandler(), Side.SERVER);
		TickRegistry.registerTickHandler(new ClientTickHandler(), Side.CLIENT);

		//Load IDs
		Configuration config;
		try
		{
			config = new net.minecraftforge.common.Configuration(
					new File(TerraFirmaCraft.proxy.getMinecraftDir(), "/config/TFC.cfg"));
			config.load();
			TFCBlockID.Setup(config);
			TFCItemID.Setup(config);
			config.save();
		} 
		catch (Exception e) 
		{
			System.out.println(new StringBuilder().append("[TFC] Error while trying to access item configuration!").toString());
		}

		TFCBlocks.LoadBlocks();
		TFCBlocks.RegisterBlocks();

		loadOre();

		//Load Items
		TFCItems.Setup();

		//Register Generators

		//Underground Lava
		GameRegistry.registerWorldGenerator(new WorldGenFissure(Block.lavaStill,2, true, 25).setUnderground(true, 20).setSeed(1));
		//Surface Hotsprings
		GameRegistry.registerWorldGenerator(new WorldGenFissureCluster());

		GameRegistry.registerWorldGenerator(new WorldGenOre());
		GameRegistry.registerWorldGenerator(new WorldGenCaveDecor());
		GameRegistry.registerWorldGenerator(new WorldGenForests());
		GameRegistry.registerWorldGenerator(new WorldGenLooseRocks());
		GameRegistry.registerWorldGenerator(new WorldGenSoilPits());
		GameRegistry.registerWorldGenerator(new WorldGenLargeRock());
		GameRegistry.registerWorldGenerator(new WorldGenPlants());


		TFCWorldType.DEFAULT = new TFCWorldType(0, "DEFAULT", 1);
		TFCWorldType.FLAT = new TFCWorldType(1, "FLAT");
		DimensionManager.unregisterProviderType(-1);
		DimensionManager.registerProviderType(-1, TFCProviderHell.class, true);
		DimensionManager.unregisterProviderType(0);
		DimensionManager.registerProviderType(0, TFCProvider.class, true);
		DimensionManager.unregisterProviderType(1);
		DimensionManager.registerProviderType(1, TFCProvider.class, true);


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

		SkillsManager.instance.registerSkill(Global.SKILL_GENERAL_SMITHING);
		SkillsManager.instance.registerSkill(Global.SKILL_TOOLSMITH);
		SkillsManager.instance.registerSkill(Global.SKILL_ARMORSMITH);
		SkillsManager.instance.registerSkill(Global.SKILL_WEAPONSMITH);
		SkillsManager.instance.registerSkill(Global.SKILL_AGRICULTURE);
		SkillsManager.instance.registerSkill(Global.SKILL_COOKING);
	}

	@EventHandler
	public void load(FMLInitializationEvent event)
	{
		//Register Achievements
		MinecraftForge.EVENT_BUS.register(new TFC_Achievements());
	}

	@EventHandler
	public void initialize(FMLInitializationEvent evt)
	{
		//Add Item Name Localizations
		Localization.addLocalization("/assets/terrafirmacraft/lang/", "en_US");
		//LanguageRegistry.instance().loadLocalization("assets/terrafirmacraft/lang/", "en_US", false);
		proxy.registerTranslations();

		//Register all of the recipes
		Recipes.registerRecipes();	

		//Register the tool classes
		proxy.registerToolClasses();

		// Register Crafting Handler
		GameRegistry.registerCraftingHandler(new CraftingHandler());
		GameRegistry.registerCraftingHandler(new FoodCraftingHandler());

		// Register the Entity Spawn Handler
		MinecraftForge.EVENT_BUS.register(new EntitySpawnHandler());

		// Register the Entity Living Update Handler
		MinecraftForge.EVENT_BUS.register(new EntityLivingHandler());

		// Register the Entity Hurt Handler
		MinecraftForge.EVENT_BUS.register(new EntityDamageHandler());

		// Register the Player Toss Event Handler, workaround for a crash fix
		MinecraftForge.EVENT_BUS.register(new PlayerTossEventHandler());

		// Register Gui Handler
		proxy.registerGuiHandler();

		// Register Chat Listener
		NetworkRegistry.instance().registerChatListener(new ChatListenerTFC());

		// Register Packet Handler
		NetworkRegistry.instance().registerConnectionHandler(new PacketHandler());

		// Register all the render stuff for the client
		proxy.registerRenderInformation();

		// Register the Chunk Data Load/Save Handler
		MinecraftForge.EVENT_BUS.register(new ChunkDataEventHandler());

		// Register the Chunk Load/Save Handler
		MinecraftForge.EVENT_BUS.register(new ChunkEventHandler());

		// Register the Chunk Load/Save Handler
		MinecraftForge.EVENT_BUS.register(new EnteringChunkHandler());

		// Register Anvil Crafting Handler
		MinecraftForge.EVENT_BUS.register(new AnvilCraftingHandler());

		MinecraftForge.EVENT_BUS.register(new PlayerSkillEventHandler());

		//Register our player tracker
		GameRegistry.registerPlayerTracker(new PlayerTracker());

		proxy.registerBiomeEventHandler();

		proxy.setupGuiIngameForge();

		GameRegistry.registerPickupHandler(new PickupHandler());

		//Setup custom potion effects
		TFCPotion.Setup();

		TFC_ItemHeat.SetupItemHeat();

		LiquidContainerRegistry.registerLiquid(new LiquidContainerData(
				LiquidDictionary.getLiquid("Lava", LiquidContainerRegistry.BUCKET_VOLUME), 
				new ItemStack(TFCItems.BlueSteelBucketLava), new ItemStack(TFCItems.BlueSteelBucketEmpty)));
		LiquidContainerRegistry.registerLiquid(new LiquidContainerData(
				LiquidDictionary.getLiquid("Water", LiquidContainerRegistry.BUCKET_VOLUME), 
				new ItemStack(TFCItems.RedSteelBucketWater), new ItemStack(TFCItems.RedSteelBucketEmpty)));
		LiquidContainerRegistry.registerLiquid(new LiquidContainerData(
				LiquidDictionary.getLiquid("Water", LiquidContainerRegistry.BUCKET_VOLUME), 
				new ItemStack(TFCItems.WoodenBucketWater), new ItemStack(TFCItems.WoodenBucketEmpty)));

		TFC_Climate.initCache();
	}
	@EventHandler
	public void modsLoaded(FMLPostInitializationEvent evt) 
	{
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
		TFCOptions.enableVanillaDiamondRecipe = TFCOptions.getBooleanFor(config, "General","enableVanillaDiamondRecipe",false);
		TFCOptions.enableVanillaIronRecipe = TFCOptions.getBooleanFor(config,"General","enableVanillaIronRecipe",false);
		TFCOptions.enableVanillaGoldRecipe = TFCOptions.getBooleanFor(config,"General","enableVanillaGoldRecipe",false);
		TFCOptions.enableBetterGrass = TFCOptions.getBooleanFor(config,"General","enableBetterGrass", true);
		TFCOptions.enableVanillaFurnaceRecipes = TFCOptions.getBooleanFor(config,"General","enableVanillaFurnaceRecipes",false);
		TFCOptions.enableVanillaRecipes = TFCOptions.getBooleanFor(config,"General","enableVanillaRecipes",false, "Set this to true if you need recipes enabled for conversion from TFC to vanilla items.");
		TFCOptions.enableInnerGrassFix = TFCOptions.getBooleanFor(config,"General","enableInnerGrassFix",true, "Set this to false if your computer has to run in fast mode and you get lag. This setting forces the sides of grass to render when viewing from the inside.");
		TFCOptions.enableDebugMode = TFCOptions.getBooleanFor(config,"General","enableDebugMode",false, "Set this to true if you want to turn on debug mode which is useful for bug hunting");
		TFCOptions.yearLength = TFCOptions.getIntFor(config,"General","yearLength",96, "This is how many days are in a year. Keep this to multiples of 12.");
		TFCOptions.iDontLikeOnions = TFCOptions.getBooleanFor(config, "General", "enableNotOnions", false,"Set this to true if you don't like onions.");
		TFCOptions.enableOreTest = TFCOptions.getBooleanFor(config, "General","enableOreTest", false, "This will generate only ore in your world with nothing else. *Caution Unsupported*");
		//Caveins
		TFCOptions.minimumRockLoad = TFCOptions.getIntFor(config,"Cavein Options","minimumRockLoad",1, "This is the minimum number of solid blocks that must be over a section in order for it to collapse.");
		TFCOptions.initialCollapseRatio = TFCOptions.getIntFor(config,"Cavein Options","initialCollapseRatio",50, "This number is a 1 in X chance that when you mine a block, a collapse will occur.");
		TFCOptions.propogateCollapseChance = TFCOptions.getIntFor(config,"Cavein Options","propogateCollapseChance",55, "This number is the likelihood for each block to propagate the collapse farther.");

		TFCOptions.cropNutrientAColor[0] = (byte)TFCOptions.getIntFor(config,"ColorNutrientA","Red", 237);
		TFCOptions.cropNutrientAColor[1] = (byte)TFCOptions.getIntFor(config,"ColorNutrientA","Green", 28);
		TFCOptions.cropNutrientAColor[2] = (byte)TFCOptions.getIntFor(config,"ColorNutrientA","Blue", 36);
		TFCOptions.cropNutrientAColor[3] = (byte)TFCOptions.getIntFor(config,"ColorNutrientA","Alpha", 200);

		TFCOptions.cropNutrientBColor[0] = (byte)TFCOptions.getIntFor(config,"ColorNutrientB","Red", 242);
		TFCOptions.cropNutrientBColor[1] = (byte)TFCOptions.getIntFor(config,"ColorNutrientB","Green", 101);
		TFCOptions.cropNutrientBColor[2] = (byte)TFCOptions.getIntFor(config,"ColorNutrientB","Blue", 34);
		TFCOptions.cropNutrientBColor[3] = (byte)TFCOptions.getIntFor(config,"ColorNutrientB","Alpha", 200);

		TFCOptions.cropNutrientCColor[0] = (byte)TFCOptions.getIntFor(config,"ColorNutrientC","Red", 247);
		TFCOptions.cropNutrientCColor[1] = (byte)TFCOptions.getIntFor(config,"ColorNutrientC","Green", 148);
		TFCOptions.cropNutrientCColor[2] = (byte)TFCOptions.getIntFor(config,"ColorNutrientC","Blue", 49);
		TFCOptions.cropNutrientCColor[3] = (byte)TFCOptions.getIntFor(config,"ColorNutrientC","Alpha", 200);

		TFCOptions.cropFertilizerColor[0] = (byte)TFCOptions.getIntFor(config,"cropFertilizerColor","Red", 255);
		TFCOptions.cropFertilizerColor[1] = (byte)TFCOptions.getIntFor(config,"cropFertilizerColor","Green", 255);
		TFCOptions.cropFertilizerColor[2] = (byte)TFCOptions.getIntFor(config,"cropFertilizerColor","Blue", 0);
		TFCOptions.cropFertilizerColor[3] = (byte)TFCOptions.getIntFor(config,"cropFertilizerColor","Alpha", 200);

		TFCOptions.anvilRuleColor0[0] = (byte)TFCOptions.getIntFor(config,"anvilRuleColor0","Red", 237);
		TFCOptions.anvilRuleColor0[1] = (byte)TFCOptions.getIntFor(config,"anvilRuleColor0","Green", 28);
		TFCOptions.anvilRuleColor0[2] = (byte)TFCOptions.getIntFor(config,"anvilRuleColor0","Blue", 36);

		TFCOptions.anvilRuleColor1[0] = (byte)TFCOptions.getIntFor(config,"anvilRuleColor1","Red", 242);
		TFCOptions.anvilRuleColor1[1] = (byte)TFCOptions.getIntFor(config,"anvilRuleColor1","Green", 101);
		TFCOptions.anvilRuleColor1[2] = (byte)TFCOptions.getIntFor(config,"anvilRuleColor1","Blue", 34);

		TFCOptions.anvilRuleColor2[0] = (byte)TFCOptions.getIntFor(config,"anvilRuleColor2","Red", 247);
		TFCOptions.anvilRuleColor2[1] = (byte)TFCOptions.getIntFor(config,"anvilRuleColor2","Green", 148);
		TFCOptions.anvilRuleColor2[2] = (byte)TFCOptions.getIntFor(config,"anvilRuleColor2","Blue", 49);

		TFCOptions.enableCropsDie = TFCOptions.getBooleanFor(config, "Crops","enableCropsDie",false);

		TFCOptions.pitKilnBurnTime = TFCOptions.getIntFor(config,"General","pitKilnBurnTime", 8, "This is the number of hours that the pit kiln should burn before being completed. Longer than 8 hours will require players to feed extra logs to the fire beyond the initial 16 in the full log pile. Logs burn for 30 minutes each.");
		TFCOptions.maxProtectionMonths = TFCOptions.getIntFor(config,"Protection","maxProtectionMonths", 10, "The maximum number of months of spawn protection that can accumulate.");
		TFCOptions.protectionGain = TFCOptions.getIntFor(config,"Protection","protectionGain", 8, "The number of hours of protection gained in the 3x3 chunk area for spending 1 hour in that chunk.");

		TFCOptions.HealthGainRate = TFCOptions.getIntFor(config,"Player","HealthGainRate", 20, "The rate of Health Gain per experience level. Set to 0 to turn off.");
		TFCOptions.HealthGainCap = TFCOptions.getIntFor(config,"Player","HealthGainCap", 3000, "The maximum achievable health pool total.");

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

		WorldGenOre.OreList.put("Native Copper", getOreData(config, "Native Copper", "veins", "large", TFCBlocks.Ore.blockID, 0, 120, new String[]{"igneous extrusive"}, 5, 128, 80, 60));
		WorldGenOre.OreList.put("Native Gold", getOreData(config, "Native Gold", "veins", "large", TFCBlocks.Ore.blockID, 1, 120, new String[]{"igneous extrusive", "igneous intrusive"}, 5, 128, 80, 60));
		WorldGenOre.OreList.put("Platinum", getOreData(config, "Platinum", "veins", "small", TFCBlocks.Ore.blockID, 2, 150, new String[]{"sedimentary"}, 5, 128, 40, 80));
		WorldGenOre.OreList.put("Hematite", getOreData(config, "Hematite", "veins", "medium", TFCBlocks.Ore.blockID, 3, 125, new String[]{"igneous extrusive"}, 5, 128, 80, 60));
		WorldGenOre.OreList.put("Silver", getOreData(config, "Silver", "veins", "medium", TFCBlocks.Ore.blockID, 4, 100, new String[]{"granite", "gneiss"}, 5, 128, 80, 60));
		WorldGenOre.OreList.put("Cassiterite", getOreData(config, "Cassiterite", "veins", "medium", TFCBlocks.Ore.blockID, 5, 100, new String[]{"igneous intrusive"}, 5, 128, 80, 60));
		WorldGenOre.OreList.put("Galena", getOreData(config, "Galena", "veins", "medium", TFCBlocks.Ore.blockID, 6, 100, new String[]{"igneous extrusive", "metamorphic", "granite", "limestone"}, 5, 128, 80, 60));
		WorldGenOre.OreList.put("Bismuthinite", getOreData(config, "Bismuthinite", "veins", "medium", TFCBlocks.Ore.blockID, 7, 100, new String[]{"igneous extrusive", "sedimentary"}, 5, 128, 80, 60));
		WorldGenOre.OreList.put("Garnierite", getOreData(config, "Garnierite", "veins", "medium", TFCBlocks.Ore.blockID, 8, 150, new String[]{"gabbro"}, 5, 128, 80, 60));
		WorldGenOre.OreList.put("Malachite", getOreData(config, "Malachite", "veins", "large", TFCBlocks.Ore.blockID, 9, 100, new String[]{"limestone", "marble"}, 5, 128, 80, 60));
		WorldGenOre.OreList.put("Magnetite", getOreData(config, "Magnetite", "veins", "medium", TFCBlocks.Ore.blockID, 10, 150, new String[]{"sedimentary"}, 5, 128, 80, 60));
		WorldGenOre.OreList.put("Limonite", getOreData(config, "Limonite", "veins", "medium", TFCBlocks.Ore.blockID, 11, 150, new String[]{"sedimentary"}, 5, 128, 80, 60));
		WorldGenOre.OreList.put("Sphalerite", getOreData(config, "Sphalerite", "veins", "medium", TFCBlocks.Ore.blockID, 12, 100, new String[]{"metamorphic"}, 5, 128, 80, 60));
		WorldGenOre.OreList.put("Tetrahedrite", getOreData(config, "Tetrahedrite", "veins", "medium", TFCBlocks.Ore.blockID, 13, 120, new String[]{"metamorphic"}, 5, 128, 80, 60));
		WorldGenOre.OreList.put("Bituminous Coal", getOreData(config, "Bituminous Coal", "default", "large", TFCBlocks.Ore.blockID, 14, 100, new String[]{"sedimentary"}, 5, 128, 90, 40));
		WorldGenOre.OreList.put("Lignite", getOreData(config, "Lignite", "default", "medium", TFCBlocks.Ore.blockID, 15, 100, new String[]{"sedimentary"}, 5, 128, 90, 40));

		WorldGenOre.OreList.put("Kaolinite", getOreData(config, "Kaolinite", "default", "medium", TFCBlocks.Ore2.blockID, 0, 90, new String[]{"sedimentary"}, 5, 128, 80, 60));
		WorldGenOre.OreList.put("Gypsum", getOreData(config, "Gypsum", "veins", "large", TFCBlocks.Ore2.blockID, 1, 120, new String[]{"sedimentary"}, 5, 128, 80, 60));
		//WorldGenOre.OreList.put("Satinspar", getOreData(config, "Satinspar", "veins", "small", TFCBlocks.Ore2.blockID, 2, 150, new String[]{"sedimentary"}, 5, 128, 40, 80));
		//WorldGenOre.OreList.put("Selenite", getOreData(config, "Selenite", "veins", "medium", TFCBlocks.Ore2.blockID, 3, 125, new String[]{"igneous extrusive"}, 5, 128, 60, 60));
		WorldGenOre.OreList.put("Graphite", getOreData(config, "Graphite", "veins", "medium", TFCBlocks.Ore2.blockID, 4, 100, new String[]{"marble", "gneiss", "quartzite", "schist"}, 5, 128, 80, 60));
		WorldGenOre.OreList.put("Kimberlite", getOreData(config, "Kimberlite", "veins", "medium", TFCBlocks.Ore2.blockID, 5, 200, new String[]{"gabbro"}, 5, 128, 30, 80));
		//WorldGenOre.OreList.put("Petrified Wood", getOreData(config, "Petrified Wood", "veins", "medium", TFCBlocks.Ore2.blockID, 6, 100, new String[]{"igneous extrusive", "metamorphic", "granite", "limestone"}, 5, 128, 60, 60));
		//WorldGenOre.OreList.put("Sulfur", getOreData(config, "Sulfur", "veins", "medium", TFCBlocks.Ore2.blockID, 7, 100, new String[]{"igneous extrusive", "sedimentary"}, 5, 128, 60, 60));
		WorldGenOre.OreList.put("Jet", getOreData(config, "Jet", "veins", "large", TFCBlocks.Ore2.blockID, 8, 110, new String[]{"sedimentary"}, 5, 128, 80, 60));
		//WorldGenOre.OreList.put("Microcline", getOreData(config, "Microcline", "veins", "large", TFCBlocks.Ore2.blockID, 9, 100, new String[]{"limestone", "marble"}, 5, 128, 60, 60));
		WorldGenOre.OreList.put("Pitchblende", getOreData(config, "Pitchblende", "veins", "small", TFCBlocks.Ore2.blockID, 10, 150, new String[]{"granite"}, 5, 128, 80, 60));
		WorldGenOre.OreList.put("Cinnabar", getOreData(config, "Cinnabar", "veins", "small", TFCBlocks.Ore2.blockID, 11, 150, new String[]{"igneous extrusive", "shale", "quartzite"}, 5, 128, 30, 80));
		WorldGenOre.OreList.put("Cryolite", getOreData(config, "Cryolite", "veins", "small", TFCBlocks.Ore2.blockID, 12, 100, new String[]{"granite"}, 5, 128, 80, 60));
		WorldGenOre.OreList.put("Saltpeter", getOreData(config, "Saltpeter", "veins", "medium", TFCBlocks.Ore2.blockID, 13, 120, new String[]{"sedimentary"}, 5, 128, 80, 60));
		//WorldGenOre.OreList.put("Serpentine", getOreData(config, "Serpentine", "veins", "large", TFCBlocks.Ore2.blockID, 14, 100, new String[]{"sedimentary"}, 5, 128, 90, 40));
		WorldGenOre.OreList.put("Sylvite", getOreData(config, "Sylvite", "veins", "medium", TFCBlocks.Ore2.blockID, 15, 100, new String[]{"rock salt"}, 5, 128, 90, 40));

		WorldGenOre.OreList.put("Borax", getOreData(config, "Borax", "veins", "large", TFCBlocks.Ore3.blockID, 0, 120, new String[]{"rock salt"}, 5, 128, 80, 60));
		WorldGenOre.OreList.put("Lapis Lazuli", getOreData(config, "Lapis Lazuli", "veins", "large", TFCBlocks.Ore3.blockID, 2, 120, new String[]{"marble"}, 5, 128, 80, 60));
		//WorldGenOre.OreList.put("Olivine", getOreData(config, "Olivine", "veins", "small", TFCBlocks.Ore3.blockID, 1, 150, new String[]{"sedimentary"}, 5, 128, 40, 80));

		//Surface Ore
		WorldGenOre.OreList.put("Native Copper Surface", getOreData(config, "Native Copper Surface", "veins", "small", TFCBlocks.Ore.blockID, 0, 35, new String[]{"igneous extrusive"}, 128, 240, 40, 40));
		WorldGenOre.OreList.put("Cassiterite Surface", getOreData(config, "Cassiterite Surface", "veins", "small", TFCBlocks.Ore.blockID, 5, 35, new String[]{"granite"}, 128, 240, 40, 40));
		WorldGenOre.OreList.put("Bismuthinite Surface", getOreData(config, "Bismuthinite Surface", "veins", "small", TFCBlocks.Ore.blockID, 7, 35, new String[]{"igneous intrusive", "sedimentary"}, 128, 240, 40, 40));
		WorldGenOre.OreList.put("Sphalerite Surface", getOreData(config, "Sphalerite Surface", "veins", "small", TFCBlocks.Ore.blockID, 12, 35, new String[]{"metamorphic"}, 128, 240, 40, 40));
		WorldGenOre.OreList.put("Tetrahedrite Surface", getOreData(config, "Tetrahedrite Surface", "veins", "small", TFCBlocks.Ore.blockID, 13, 35, new String[]{"metamorphic"}, 128, 240, 40, 40));


		/**Start setup here*/
		Set<String> names = config.getCategoryNames();
		Iterator oreIter = names.iterator();

		while(oreIter.hasNext())
		{
			String s = (String)oreIter.next();
			OreSpawnData data = new OreSpawnData(
					config.get(s, "type", "default").getString(),
					config.get(s, "size", "small").getString(),
					config.get(s, "oreId", 0).getInt(),
					config.get(s, "oreMeta", 0).getInt(),
					config.get(s, "rarity", 100).getInt(),
					config.get(s, "baseRocks", "granite, basalt, sedimentary").getStringList(),
					config.get(s, "Minimum Height", 5).getInt(),
					config.get(s, "Maximum Height", 128).getInt(),
					config.get(s, "Vertical Density", 50).getInt(),
					config.get(s, "Horizontal Density", 50).getInt());

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
			int id, int meta, int rarity, String[] rocks, int min, int max, int v, int h)
	{
		return new OreSpawnData(
				config.get(category, "type", type).getString(),
				config.get(category, "size", size).getString(),
				config.get(category, "oreId", id).getInt(),
				config.get(category, "oreMeta", meta).getInt(),
				config.get(category, "rarity", rarity).getInt(),
				config.get(category, "baseRocks", rocks).getStringList(),
				config.get(category, "Minimum Height", min).getInt(),
				config.get(category, "Maximum Height", max).getInt(),
				config.get(category, "Vertical Density", v).getInt(),
				config.get(category, "Horizontal Density", h).getInt());
	}
}
