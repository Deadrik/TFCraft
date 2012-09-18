//=======================================================
//Mod Client File
//=======================================================
package net.minecraft.src;

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
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.modloader.BaseModProxy;
import cpw.mods.fml.common.network.IConnectionHandler;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.TickRegistry;

import TFC.*;
import TFC.Blocks.*;
import TFC.Commands.GetBioTempCommand;
import TFC.Commands.GetRocksCommand;
import TFC.Commands.GetTreesCommand;
import TFC.Containers.ContainerTFC;
import TFC.Core.*;
import TFC.Entities.*;
import TFC.Handlers.BlockRenderHandler;
import TFC.Handlers.ChunkDataEventHandler;
import TFC.Handlers.ChunkEventHandler;
import TFC.Handlers.CraftingHandler;
import TFC.Handlers.EntityLivingHandler;
import TFC.Handlers.PacketHandler;
import TFC.Handlers.ClientTickHandler;
import TFC.Handlers.ServerTickHandler;
import TFC.Items.*;
import TFC.TileEntities.*;
import TFC.WorldGen.TFCProvider;
import TFC.WorldGen.TFCWorldType;
import TFC.WorldGen.Generators.*;

import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.*;

@Mod(modid = "TerraFirmaCraft", name = "TerraFirmaCraft", version = "B2 Build 50")
@NetworkMod(channels = { "TerraFirmaCraft" }, clientSideRequired = true, serverSideRequired = true, packetHandler = PacketHandler.class)
public class TerraFirmaCraft
{
	@Instance
	public static TerraFirmaCraft instance;

	@SidedProxy(clientSide = "TFC.Core.ClientProxy", serverSide = "TFC.Core.CommonProxy")
	public static CommonProxy proxy;

	//////////////////Features////////////////////
	public static int RockLayer2Height = 110;
	public static int RockLayer3Height = 55;

	public TerraFirmaCraft()
	{
		TickRegistry.registerTickHandler(new ServerTickHandler(), Side.SERVER);
		TickRegistry.registerTickHandler(new ClientTickHandler(), Side.CLIENT);
	}

	@PreInit
	public void preInit(FMLPreInitializationEvent event) 
	{
		instance = this;

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

		TFCWorldType.DEFAULT = new TFCWorldType(0, "DEFAULT", 1);
		DimensionManager.registerProviderType(0, TFCProvider.class, true);
	}

	@Init
	public void load(FMLInitializationEvent evt)
	{
		TFC_Core.RegisterRecipes();	

		TFC_Game.RegisterToolRecipes();

		proxy.registerToolClasses();

		// Register Crafting Handler
		GameRegistry.registerCraftingHandler(new CraftingHandler());

		// Register the EntityLiving Handler
		MinecraftForge.EVENT_BUS.register(new EntityLivingHandler());

		// Register Gui Handler
		NetworkRegistry.instance().registerGuiHandler(this, proxy);

		// Register Packet Handler
		NetworkRegistry.instance().registerConnectionHandler(new PacketHandler());

		proxy.registerRenderInformation();
		
		// Register the Chunk Data Load/Save Handler
		MinecraftForge.EVENT_BUS.register(new ChunkDataEventHandler());
		
		// Register the Chunk Load/Save Handler
		MinecraftForge.EVENT_BUS.register(new ChunkEventHandler());


		if(TFC_Settings.enableVanillaRecipes == false)
		{
			RemoveRecipe(new ItemStack(Item.pickaxeWood,1));
			RemoveRecipe(new ItemStack(Item.axeWood,1));
			RemoveRecipe(new ItemStack(Item.shovelWood,1));
			RemoveRecipe(new ItemStack(Item.hoeWood,1));
			RemoveRecipe(new ItemStack(Item.swordWood,1));
			RemoveRecipe(new ItemStack(Block.stoneOvenIdle,1));
			RemoveRecipe(new ItemStack(Block.torchWood,4));
			RemoveRecipe(new ItemStack(Item.stick,4));
			RemoveRecipe(new ItemStack(Block.planks,4));
		}

		//Register new Minecarts
		MinecartRegistry.registerMinecart(EntityCustomMinecart.class, 1, new ItemStack(TFCItems.minecartCrate));

	}

	@PostInit
	public void modsLoaded(FMLPostInitializationEvent evt) 
	{
		ServerPlayerAPI.register("TFC Player", TFC_Player.class);
	}

	@ServerStarting
	public void serverStarting(FMLServerStartingEvent evt)
	{
		evt.registerServerCommand(new GetBioTempCommand());
		evt.registerServerCommand(new GetTreesCommand());
		evt.registerServerCommand(new GetRocksCommand());
	}

	public static void RemoveRecipe(ItemStack resultItem) {
		List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();
		for (int i = 0; i < recipes.size(); i++)
		{
			IRecipe tmpRecipe = recipes.get(i);
			if (tmpRecipe instanceof ShapedRecipes) {
				ShapedRecipes recipe = (ShapedRecipes)tmpRecipe;
				ItemStack recipeResult = recipe.getRecipeOutput();

				if (ItemStack.areItemStacksEqual(resultItem, recipeResult)) {
					recipes.remove(i--);
				}
			}
		}
	}
}
