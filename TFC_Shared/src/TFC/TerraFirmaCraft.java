//=======================================================
//Mod Client File
//=======================================================
package TFC;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.liquids.LiquidContainerData;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidDictionary;
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
import TFC.Handlers.BiomeEventHandler;
import TFC.Handlers.ChatListenerTFC;
import TFC.Handlers.ChunkDataEventHandler;
import TFC.Handlers.ChunkEventHandler;
import TFC.Handlers.CraftingHandler;
import TFC.Handlers.EnteringChunkHandler;
import TFC.Handlers.EntityDamageHandler;
import TFC.Handlers.EntityLivingHandler;
import TFC.Handlers.EntitySpawnHandler;
import TFC.Handlers.PacketHandler;
import TFC.Handlers.ServerTickHandler;
import TFC.Handlers.Client.ClientTickHandler;
import TFC.WorldGen.TFCProvider;
import TFC.WorldGen.TFCProviderHell;
import TFC.WorldGen.TFCWorldType;
import TFC.WorldGen.Generators.WorldGenCaveDecor;
import TFC.WorldGen.Generators.WorldGenOre;
import TFC.WorldGen.Generators.WorldGenOreSurface;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.Mod.ServerStarting;
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

	@PreInit
	public void preInit(FMLPreInitializationEvent event) 
	{
		instance = this;

		TickRegistry.registerTickHandler(new ServerTickHandler(), Side.SERVER);
		TickRegistry.registerTickHandler(new ClientTickHandler(), Side.CLIENT);

		//Load Blocks
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
		//proxy.registerSoundHandler();
	}

	@Init
	public void initialize(FMLInitializationEvent evt)
	{
		//Add Item Name Localizations
		Localization.addLocalization("/lang/", "en_US");
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

		// Register the Biome Event Handler
		MinecraftForge.EVENT_BUS.register(new BiomeEventHandler());

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

	@PostInit
	public void modsLoaded(FMLPostInitializationEvent evt) 
	{

	}

	@ServerStarting
	public void serverStarting(FMLServerStartingEvent evt)
	{
		evt.registerServerCommand(new GetBioTempCommand());
		evt.registerServerCommand(new GetTreesCommand());
		evt.registerServerCommand(new GetRocksCommand());
		evt.registerServerCommand(new GetSpawnProtectionCommand());
		evt.registerServerCommand(new SetPlayerStatsCommand());
		evt.registerServerCommand(new GetBodyTemp());
	}	
}
