//=======================================================
//Mod Client File
//=======================================================
package com.bioxx.tfc;

import net.minecraft.world.WorldType;

import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.common.MinecraftForge;

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

import com.bioxx.tfc.Commands.*;
import com.bioxx.tfc.Core.*;
import com.bioxx.tfc.Core.Config.TFC_ConfigFiles;
import com.bioxx.tfc.Core.Player.PlayerTracker;
import com.bioxx.tfc.Food.TFCPotion;
import com.bioxx.tfc.Handlers.*;
import com.bioxx.tfc.Handlers.Network.PacketPipeline;
import com.bioxx.tfc.WorldGen.TFCProvider;
import com.bioxx.tfc.WorldGen.TFCProviderHell;
import com.bioxx.tfc.WorldGen.TFCWorldType;
import com.bioxx.tfc.WorldGen.Generators.*;
import com.bioxx.tfc.api.SkillsManager;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCOptions;
import com.bioxx.tfc.api.Constant.Global;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.MOD_VERSION, dependencies = Reference.MOD_DEPENDENCIES, guiFactory = Reference.GUI_FACTORY)
public class TerraFirmaCraft
{
	public static final Logger LOG = LogManager.getLogger(Reference.MOD_NAME);

	@Instance(Reference.MOD_ID)
	public static TerraFirmaCraft instance;

	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;

	// The packet pipeline
	public static final PacketPipeline PACKET_PIPELINE = new PacketPipeline();

	public TerraFirmaCraft()
	{
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		TFC_ConfigFiles.preInit(event.getModConfigurationDirectory());
		TFC_ConfigFiles.reloadGeneral(); // No special needs
		// No world gen here, other mods may need to load first!

		proxy.registerTickHandler();

		proxy.registerFluids();

		BlockSetup.loadBlocks();
		BlockSetup.registerBlocks();
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
		ItemSetup.setup();

		// Register Gui Handler
		proxy.registerGuiHandler();

		//Register Generators
		//Underground Lava
		GameRegistry.registerWorldGenerator(new WorldGenFissure(TFCBlocks.lava, 2, true, TFCOptions.lavaFissureRarity).setUnderground(true, 20).setSeed(1), 0);
		GameRegistry.registerWorldGenerator(new WorldGenFissure(TFCBlocks.freshWaterStationary, 2, false, TFCOptions.waterFissureRarity), 0);
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

	@EventHandler
	public void initialize(FMLInitializationEvent event)
	{
		// Register Packet Handler
		PACKET_PIPELINE.initalise();

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
		TFCPotion.setup();

		//Register all of the recipes
		TFC_OreDictionary.registerOreDict();
		Recipes.registerRecipes();

		ItemHeat.setupItemHeat();

		TFC_Climate.initCache();

		//Register TFC items with forge fuel handler.
		//This is used by vanilla furnice and many other mods.
		ItemSetup.registerFurnaceFuel();
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
		PACKET_PIPELINE.postInitialise();

		// Now that blocks are resisted, go ahead and do worldgen configs
		TFC_ConfigFiles.reloadOres();

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
		evt.registerServerCommand(new ClearChunkCommand());
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
		if (eventArgs.modID.equals(Reference.MOD_ID)) TFC_ConfigFiles.reloadAll();
	}
}
