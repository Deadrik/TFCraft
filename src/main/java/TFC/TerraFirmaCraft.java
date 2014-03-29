//=======================================================
//Mod Client File
//=======================================================
package TFC;

import java.io.File;
import java.util.Iterator;
import java.util.Set;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import TFC.API.SkillsManager;
import TFC.API.TFCOptions;
import TFC.API.Constant.Global;
import TFC.Commands.CommandTime;
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
import TFC.Handlers.Network.PacketPipeline;
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

		proxy.registerTickHandler();

		TFCBlocks.LoadBlocks();
		TFCBlocks.RegisterBlocks();
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

		SkillsManager.instance.registerSkill(Global.SKILL_GENERAL_SMITHING);
		SkillsManager.instance.registerSkill(Global.SKILL_TOOLSMITH);
		SkillsManager.instance.registerSkill(Global.SKILL_ARMORSMITH);
		SkillsManager.instance.registerSkill(Global.SKILL_WEAPONSMITH);
		
		//Load Items
		TFCItems.Setup();

		// Register Gui Handler
		proxy.registerGuiHandler();

		//Register Generators
		//Underground Lava
		GameRegistry.registerWorldGenerator(new WorldGenFissure(Blocks.lava,2, true, 25).setUnderground(true, 20).setSeed(1), 0);
		//Surface Hotsprings
		GameRegistry.registerWorldGenerator(new WorldGenFissureCluster(), 1);

		GameRegistry.registerWorldGenerator(new WorldGenOre(), 2);
		GameRegistry.registerWorldGenerator(new WorldGenCaveDecor(), 3);
		GameRegistry.registerWorldGenerator(new WorldGenForests(), 4);
		GameRegistry.registerWorldGenerator(new WorldGenLooseRocks(), 5);
		GameRegistry.registerWorldGenerator(new WorldGenSoilPits(), 6);
		GameRegistry.registerWorldGenerator(new WorldGenLargeRock(), 7);
		GameRegistry.registerWorldGenerator(new WorldGenPlants(), 8);

		TFCWorldType.DEFAULT = new TFCWorldType("DEFAULT");
		TFCWorldType.FLAT = new TFCWorldType("FLAT");
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

	@EventHandler
	public void init(FMLInitializationEvent evt)
	{
		//Add Item Name Localizations
		//Localization.addLocalization("/assets/terrafirmacraft/lang/", "en_US");
		//LanguageRegistry.instance().loadLocalization("assets/terrafirmacraft/lang/", "en_US", false);
		//proxy.registerTranslations();

		// Register Packet Handler
		//NetworkRegistry.INSTANCE.registerConnectionHandler(new PacketHandler());
		packetPipeline.initalise();
		//Register all of the recipes
		Recipes.registerRecipes();
		//Register the tool classes
		proxy.registerToolClasses();
		// Register Crafting Handler
		MinecraftForge.EVENT_BUS.register(new CraftingHandler());
		// Register the Entity Spawn Handler
		MinecraftForge.EVENT_BUS.register(new EntitySpawnHandler());
		// Register the Entity Living Update Handler
		MinecraftForge.EVENT_BUS.register(new EntityLivingHandler());
		// Register the Entity Hurt Handler
		MinecraftForge.EVENT_BUS.register(new EntityDamageHandler());
		// Register Chat Listener
		MinecraftForge.EVENT_BUS.register(new ChatListenerTFC());
		// Register the Chunk Data Load/Save Handler
		MinecraftForge.EVENT_BUS.register(new ChunkDataEventHandler());
		// Register the Chunk Load/Save Handler
		MinecraftForge.EVENT_BUS.register(new ChunkEventHandler());
		// Register the Chunk Load/Save Handler
		MinecraftForge.EVENT_BUS.register(new EnteringChunkHandler());
		// Register Anvil Crafting Handler
		MinecraftForge.EVENT_BUS.register(new AnvilCraftingHandler());
		//Register our player tracker
		MinecraftForge.EVENT_BUS.register(new PlayerTracker());
		// Register all the render stuff for the client
		proxy.registerRenderInformation();

		proxy.registerBiomeEventHandler();
		proxy.setupGuiIngameForge();

		//Setup custom potion effects
		TFCPotion.Setup();

		TFC_ItemHeat.SetupItemHeat();

		FluidContainerRegistry.registerFluidContainer(FluidRegistry.LAVA, new ItemStack(TFCItems.BlueSteelBucketLava), new ItemStack(TFCItems.BlueSteelBucketEmpty));
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.WATER, new ItemStack(TFCItems.RedSteelBucketWater), new ItemStack(TFCItems.RedSteelBucketEmpty));
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.WATER, new ItemStack(TFCItems.WoodenBucketWater), new ItemStack(TFCItems.WoodenBucketEmpty));

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
		//CommandHandler ch = (CommandHandler) evt.getServer().getCommandManager();
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

		WorldGenOre.OreList.put("Native Copper", getOreData(config, "Native Copper", "veins", "large", Global.ORE_METAL[0], 0, 120, new String[]{"igneous extrusive"}, 5, 128, 80, 60));
		WorldGenOre.OreList.put("Native Gold", getOreData(config, "Native Gold", "veins", "large", Global.ORE_METAL[1], 1, 120, new String[]{"igneous extrusive", "igneous intrusive"}, 5, 128, 80, 60));
		WorldGenOre.OreList.put("Platinum", getOreData(config, "Platinum", "veins", "small", Global.ORE_METAL[2], 2, 150, new String[]{"sedimentary"}, 5, 128, 40, 80));
		WorldGenOre.OreList.put("Hematite", getOreData(config, "Hematite", "veins", "medium", Global.ORE_METAL[3], 3, 125, new String[]{"igneous extrusive"}, 5, 128, 80, 60));
		WorldGenOre.OreList.put("Silver", getOreData(config, "Silver", "veins", "medium", Global.ORE_METAL[4], 4, 100, new String[]{"granite", "gneiss"}, 5, 128, 80, 60));
		WorldGenOre.OreList.put("Cassiterite", getOreData(config, "Cassiterite", "veins", "medium", Global.ORE_METAL[5], 5, 100, new String[]{"igneous intrusive"}, 5, 128, 80, 60));
		WorldGenOre.OreList.put("Galena", getOreData(config, "Galena", "veins", "medium", Global.ORE_METAL[6], 6, 100, new String[]{"igneous extrusive", "metamorphic", "granite", "limestone"}, 5, 128, 80, 60));
		WorldGenOre.OreList.put("Bismuthinite", getOreData(config, "Bismuthinite", "veins", "medium", Global.ORE_METAL[7], 7, 100, new String[]{"igneous extrusive", "sedimentary"}, 5, 128, 80, 60));
		WorldGenOre.OreList.put("Garnierite", getOreData(config, "Garnierite", "veins", "large", Global.ORE_METAL[8], 8, 110, new String[]{"gabbro"}, 5, 128, 80, 60));
		WorldGenOre.OreList.put("Malachite", getOreData(config, "Malachite", "veins", "large", Global.ORE_METAL[9], 9, 100, new String[]{"limestone", "marble"}, 5, 128, 80, 60));
		WorldGenOre.OreList.put("Magnetite", getOreData(config, "Magnetite", "veins", "medium", Global.ORE_METAL[10], 10, 150, new String[]{"sedimentary"}, 5, 128, 80, 60));
		WorldGenOre.OreList.put("Limonite", getOreData(config, "Limonite", "veins", "medium", Global.ORE_METAL[11], 11, 150, new String[]{"sedimentary"}, 5, 128, 80, 60));
		WorldGenOre.OreList.put("Sphalerite", getOreData(config, "Sphalerite", "veins", "medium", Global.ORE_METAL[12], 12, 100, new String[]{"metamorphic"}, 5, 128, 80, 60));
		WorldGenOre.OreList.put("Tetrahedrite", getOreData(config, "Tetrahedrite", "veins", "medium", Global.ORE_METAL[13], 13, 120, new String[]{"metamorphic"}, 5, 128, 80, 60));
		WorldGenOre.OreList.put("Bituminous Coal", getOreData(config, "Bituminous Coal", "default", "large", Global.ORE_METAL[14], 14, 100, new String[]{"sedimentary"}, 5, 128, 90, 40));
		WorldGenOre.OreList.put("Lignite", getOreData(config, "Lignite", "default", "medium", Global.ORE_METAL[15], 15, 100, new String[]{"sedimentary"}, 5, 128, 90, 40));

		WorldGenOre.OreList.put("Kaolinite", getOreData(config, "Kaolinite", "default", "medium", Global.ORE_MINERAL[0], 0, 90, new String[]{"sedimentary"}, 5, 128, 80, 60));
		WorldGenOre.OreList.put("Gypsum", getOreData(config, "Gypsum", "veins", "large", Global.ORE_MINERAL[1], 1, 120, new String[]{"sedimentary"}, 5, 128, 80, 60));
		//WorldGenOre.OreList.put("Satinspar", getOreData(config, "Satinspar", "veins", "small", Global.ORE_MINERAL[2], 2, 150, new String[]{"sedimentary"}, 5, 128, 40, 80));
		//WorldGenOre.OreList.put("Selenite", getOreData(config, "Selenite", "veins", "medium", Global.ORE_MINERAL[3], 3, 125, new String[]{"igneous extrusive"}, 5, 128, 60, 60));
		WorldGenOre.OreList.put("Graphite", getOreData(config, "Graphite", "veins", "medium", Global.ORE_MINERAL[4], 4, 100, new String[]{"marble", "gneiss", "quartzite", "schist"}, 5, 128, 80, 60));
		WorldGenOre.OreList.put("Kimberlite", getOreData(config, "Kimberlite", "veins", "medium", Global.ORE_MINERAL[5], 5, 200, new String[]{"gabbro"}, 5, 128, 30, 80));
		//WorldGenOre.OreList.put("Petrified Wood", getOreData(config, "Petrified Wood", "veins", "medium", Global.ORE_MINERAL[6], 6, 100, new String[]{"igneous extrusive", "metamorphic", "granite", "limestone"}, 5, 128, 60, 60));
		//WorldGenOre.OreList.put("Sulfur", getOreData(config, "Sulfur", "veins", "medium", Global.ORE_MINERAL[7], 7, 100, new String[]{"igneous extrusive", "sedimentary"}, 5, 128, 60, 60));
		WorldGenOre.OreList.put("Jet", getOreData(config, "Jet", "veins", "large", Global.ORE_MINERAL[8], 8, 110, new String[]{"sedimentary"}, 5, 128, 80, 60));
		//WorldGenOre.OreList.put("Microcline", getOreData(config, "Microcline", "veins", "large", Global.ORE_MINERAL[9], 9, 100, new String[]{"limestone", "marble"}, 5, 128, 60, 60));
		WorldGenOre.OreList.put("Pitchblende", getOreData(config, "Pitchblende", "veins", "small", Global.ORE_MINERAL[10], 10, 150, new String[]{"granite"}, 5, 128, 80, 60));
		WorldGenOre.OreList.put("Cinnabar", getOreData(config, "Cinnabar", "veins", "small", Global.ORE_MINERAL[11], 11, 150, new String[]{"igneous extrusive", "shale", "quartzite"}, 5, 128, 30, 80));
		WorldGenOre.OreList.put("Cryolite", getOreData(config, "Cryolite", "veins", "small", Global.ORE_MINERAL[12], 12, 100, new String[]{"granite"}, 5, 128, 80, 60));
		WorldGenOre.OreList.put("Saltpeter", getOreData(config, "Saltpeter", "veins", "medium", Global.ORE_MINERAL[13], 13, 120, new String[]{"sedimentary"}, 5, 128, 80, 60));
		//WorldGenOre.OreList.put("Serpentine", getOreData(config, "Serpentine", "veins", "large", Global.ORE_MINERAL[14], 14, 100, new String[]{"sedimentary"}, 5, 128, 90, 40));
		WorldGenOre.OreList.put("Sylvite", getOreData(config, "Sylvite", "veins", "medium", Global.ORE_MINERAL[15], 15, 100, new String[]{"rock salt"}, 5, 128, 90, 40));

		WorldGenOre.OreList.put("Borax", getOreData(config, "Borax", "veins", "large", Global.ORE_MINERAL2[0], 0, 120, new String[]{"rock salt"}, 5, 128, 80, 60));
		WorldGenOre.OreList.put("Lapis Lazuli", getOreData(config, "Lapis Lazuli", "veins", "large", Global.ORE_MINERAL2[1], 1, 120, new String[]{"marble"}, 5, 128, 80, 60));
		//WorldGenOre.OreList.put("Olivine", getOreData(config, "Olivine", "veins", "small", Global.ORE_MINERAL2[2], 2, 150, new String[]{"sedimentary"}, 5, 128, 40, 80));

		//Surface Ore
		WorldGenOre.OreList.put("Native Copper Surface", getOreData(config, "Native Copper Surface", "veins", "small", Global.ORE_METAL[0], 0, 35, new String[]{"igneous extrusive"}, 128, 240, 40, 40));
		WorldGenOre.OreList.put("Cassiterite Surface", getOreData(config, "Cassiterite Surface", "veins", "small", Global.ORE_METAL[5], 5, 20, new String[]{"granite"}, 128, 240, 40, 40));
		WorldGenOre.OreList.put("Bismuthinite Surface", getOreData(config, "Bismuthinite Surface", "veins", "small", Global.ORE_METAL[7], 7, 25, new String[]{"igneous intrusive", "sedimentary"}, 128, 240, 40, 40));
		WorldGenOre.OreList.put("Sphalerite Surface", getOreData(config, "Sphalerite Surface", "veins", "small", Global.ORE_METAL[12], 12, 25, new String[]{"metamorphic"}, 128, 240, 40, 40));
		WorldGenOre.OreList.put("Tetrahedrite Surface", getOreData(config, "Tetrahedrite Surface", "veins", "small", Global.ORE_METAL[13], 13, 25, new String[]{"metamorphic"}, 128, 240, 40, 40));


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
