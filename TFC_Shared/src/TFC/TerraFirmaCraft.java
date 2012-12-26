//=======================================================
//Mod Client File
//=======================================================
package TFC;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayDeque;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;

import com.google.common.eventbus.Subscribe;

import cpw.mods.fml.common.*;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.Mod.ServerStarting;
import cpw.mods.fml.common.Mod.ServerStopping;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import cpw.mods.fml.common.modloader.BaseModProxy;
import cpw.mods.fml.common.network.IConnectionHandler;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

import TFC.*;
import TFC.Blocks.*;
import TFC.Commands.*;
import TFC.Core.*;
import TFC.Core.Player.PlayerTracker;
import TFC.Core.Player.TFC_PlayerClient;
import TFC.Core.Player.TFC_PlayerServer;
import TFC.Entities.*;
import TFC.Food.TFCPotion;
import TFC.Handlers.*;
import TFC.Items.*;
import TFC.TileEntities.*;
import TFC.WorldGen.TFCProvider;
import TFC.WorldGen.TFCProviderCOTE;
import TFC.WorldGen.TFCWorldType;
import TFC.WorldGen.Generators.*;

import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.crash.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.packet.*;
import net.minecraft.pathfinding.*;
import net.minecraft.potion.*;
import net.minecraft.server.*;
import net.minecraft.stats.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.village.*;
import net.minecraft.world.*;
import net.minecraft.world.chunk.*;
import net.minecraftforge.common.*;

@Mod(modid = "TerraFirmaCraft", name = "TerraFirmaCraft", version = "Build 73")
@NetworkMod(channels = { "TerraFirmaCraft" }, clientSideRequired = true, serverSideRequired = true, packetHandler = PacketHandler.class)
public class TerraFirmaCraft
{
	@Instance
	public static TerraFirmaCraft instance;

	@SidedProxy(clientSide = "TFC.ClientProxy", serverSide = "TFC.CommonProxy")
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
		DimensionManager.registerProviderType(0, TFCProvider.class, true);
		//DimensionManager.registerProviderType(-2, TFCProviderCOTE.class, false);

		//Add Item Name Localizations
		proxy.registerTranslations();

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

	@Init
	public void load(FMLInitializationEvent evt)
	{
		//Register all of the recipes
		Recipes.registerRecipes();	

		//Register the tool classes
		proxy.registerToolClasses();

		// Register Crafting Handler
		GameRegistry.registerCraftingHandler(new CraftingHandler());

		// Register the Entity Spawn Handler
		MinecraftForge.EVENT_BUS.register(new EntitySpawnHandler());
		
		// Register the Entity Hurt Handler
		MinecraftForge.EVENT_BUS.register(new EntityHurtHandler());

		// Register Gui Handler
		NetworkRegistry.instance().registerGuiHandler(this, proxy);

		// Register Packet Handler
		NetworkRegistry.instance().registerConnectionHandler(new PacketHandler());

		// Register all the render stuff for the client
		proxy.registerRenderInformation();
		
		// Register the Chunk Data Load/Save Handler
		MinecraftForge.EVENT_BUS.register(new ChunkDataEventHandler());
		
		// Register the Chunk Load/Save Handler
		MinecraftForge.EVENT_BUS.register(new ChunkEventHandler());

		//Register new Minecarts
		MinecartRegistry.registerMinecart(EntityCustomMinecart.class, 1, new ItemStack(TFCItems.minecartCrate));
		
		//Register our player tracker
		GameRegistry.registerPlayerTracker(new PlayerTracker());
		
		//Setup custom potion effects
		TFCPotion.Setup();
		
		TFC_ItemHeat.SetupItemHeat();

	}

	@PostInit
	public void modsLoaded(FMLPostInitializationEvent evt) 
	{
		this.proxy.RegisterPlayerApiClasses();
	}

	@ServerStarting
	public void serverStarting(FMLServerStartingEvent evt)
	{
		evt.registerServerCommand(new GetBioTempCommand());
		evt.registerServerCommand(new GetTreesCommand());
		evt.registerServerCommand(new GetRocksCommand());
		evt.registerServerCommand(new GetSpawnProtectionCommand());
	}	
}
