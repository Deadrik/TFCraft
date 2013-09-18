//=======================================================
//Mod Client File
//=======================================================
package TFC;

import java.io.File;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.liquids.LiquidContainerData;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidDictionary;
import TFC.API.TFCOptions;
import TFC.API.Constant.TFCBlockID;
import TFC.API.Constant.TFCItemID;
import TFC.Commands.GetBioTempCommand;
import TFC.Commands.GetBodyTemp;
import TFC.Commands.GetRocksCommand;
import TFC.Commands.GetSpawnProtectionCommand;
import TFC.Commands.GetTreesCommand;
import TFC.Commands.SetPlayerStatsCommand;
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
import TFC.Handlers.PacketHandler;
import TFC.Handlers.PlayerTossEventHandler;
import TFC.Handlers.ServerTickHandler;
import TFC.Handlers.Client.ClientTickHandler;
import TFC.WorldGen.TFCProvider;
import TFC.WorldGen.TFCProviderHell;
import TFC.WorldGen.TFCWorldType;
import TFC.WorldGen.Generators.WorldGenCaveDecor;
import TFC.WorldGen.Generators.WorldGenForests;
import TFC.WorldGen.Generators.WorldGenLargeRock;
import TFC.WorldGen.Generators.WorldGenLooseRocks;
import TFC.WorldGen.Generators.WorldGenOre;
import TFC.WorldGen.Generators.WorldGenOreSurface;
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
@NetworkMod(channels = { Reference.ModChannel }, clientSideRequired = true, serverSideRequired = true, packetHandler = PacketHandler.class)
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

		//Load Items
		TFCItems.Setup();

		//Register Generators
		GameRegistry.registerWorldGenerator(new WorldGenOreSurface(100,150));
		GameRegistry.registerWorldGenerator(new WorldGenOreSurface(130,200));
		GameRegistry.registerWorldGenerator(new WorldGenOre(5,96));
		GameRegistry.registerWorldGenerator(new WorldGenOre(60,130));
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

		// Register the Entity Spawn Handler
		MinecraftForge.EVENT_BUS.register(new EntitySpawnHandler());

		// Register the Entity Living Update Handler
		MinecraftForge.EVENT_BUS.register(new EntityLivingHandler());

		// Register the Entity Hurt Handler
		MinecraftForge.EVENT_BUS.register(new EntityDamageHandler());

		// Register the Player Toss Event Handler, workaround for a crash fix
		MinecraftForge.EVENT_BUS.register(new PlayerTossEventHandler());

		// Register Gui Handler
		NetworkRegistry.instance().registerGuiHandler(this, proxy);

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

		//Register our player tracker
		GameRegistry.registerPlayerTracker(new PlayerTracker());

		proxy.registerBiomeEventHandler();

		proxy.setupGuiIngameForge();

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
		//Caveins
		TFCOptions.minimumRockLoad = TFCOptions.getIntFor(config,"Cavein Options","minimumRockLoad",1, "This is the minimum number of solid blocks that must be over a section in order for it to collapse.");
		TFCOptions.initialCollapseRatio = TFCOptions.getIntFor(config,"Cavein Options","initialCollapseRatio",40, "This number is a 1 in X chance that when you mine a block, a collapse will occur.");
		TFCOptions.propogateCollapseChance = TFCOptions.getIntFor(config,"Cavein Options","propogateCollapseChance",35, "This number is the likelihood for each block to propagate the collapse farther.");

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
		if (config != null) {
			config.save();
		}
	}
}
