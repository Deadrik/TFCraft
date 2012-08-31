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
import TFC.Core.*;
import TFC.Entities.*;
import TFC.Handlers.BlockRenderHandler;
import TFC.Handlers.CraftingHandler;
import TFC.Handlers.EntityLivingHandler;
import TFC.Handlers.PacketHandler;
import TFC.Items.*;
import TFC.TileEntities.*;
import TFC.WorldGen.TFCProvider;
import TFC.WorldGen.TFCWorldType;
import TFC.WorldGen.Generators.*;

import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.TFCItems;
import net.minecraftforge.common.*;

@Mod(modid = "TerraFirmaCraft", name = "TerraFirmaCraft", version = "B2 Build 49")
@NetworkMod(channels = { "TerraFirmaCraft" }, clientSideRequired = true, serverSideRequired = true, packetHandler = PacketHandler.class)
public class TerraFirmaCraft implements ITickHandler
{
	

	@Instance
	public static TerraFirmaCraft instance;

	@SidedProxy(clientSide = "net.minecraft.src.ClientProxy", serverSide = "net.minecraft.src.CommonProxy")
	public static CommonProxy proxy;

	//////////////////Features////////////////////
	public static int RockLayer2Height = 110;
	public static int RockLayer3Height = 55;
	
	public TerraFirmaCraft()
	{
		TickRegistry.registerTickHandler(this, Side.SERVER);
	}

	@PreInit
	public void preInit(FMLPreInitializationEvent event) 
	{
		instance = this;

		//Load Blocks
		TFCBlocks.LoadBlocks();
		
		//Load Items
		TFCItems.Setup();

		//Register Generators		
		GameRegistry.registerWorldGenerator(new WorldGenOreSurface(100,150));
		GameRegistry.registerWorldGenerator(new WorldGenOreSurface(130,200));
		GameRegistry.registerWorldGenerator(new WorldGenOre(5,96));
		GameRegistry.registerWorldGenerator(new WorldGenOre(60,130));
		GameRegistry.registerWorldGenerator(new WorldGenLooseRocks());
		GameRegistry.registerWorldGenerator(new WorldGenCaveDecor());
		GameRegistry.registerWorldGenerator(new WorldGenPlants());
		GameRegistry.registerWorldGenerator(new WorldGenSoilPits());

		//Add Item Name Localizations
		proxy.registerTranslations();

		//Register Key Bindings(Client only)
		proxy.registerKeys();

		//Register KeyBinding Handler (Client only)
		proxy.registerKeyBindingHandler();
		
		//Register Tile Entites
		proxy.registerTileEntities();
		
		//Register Sound Handler (Client only)
		proxy.registerSoundHandler();
		
		TFCWorldType.DEFAULT = new TFCWorldType(0, "TFC", 1);
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

	}
	
	@ServerStarting
    public void serverStarting(FMLServerStartingEvent evt)
    {
        evt.registerServerCommand(new GetBioTempCommand());
    }

	private static void RemoveRecipe(ItemStack resultItem) {
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

	private boolean doOnce = false;
	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData)
	{
		if (type.contains(TickType.WORLD))
		{
			World world;

			assert ((tickData[0] instanceof World));
			world = (World)tickData[0];


			TFC_Time.UpdateSeasons(world);
			for(Object p : world.playerEntities)
			{
				TFC_ItemHeat.HandleContainerHeat(world, ((EntityPlayer)p).inventory.mainInventory, (int)((EntityPlayer)p).posX, (int)((EntityPlayer)p).posY, (int)((EntityPlayer)p).posZ);
			}
		}

		if(type.contains(TickType.PLAYER))
		{
			EntityPlayer player = (EntityPlayer)tickData[0];
			if(!doOnce && PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(player) == null)
				PlayerManagerTFC.getInstance().Players.add(new PlayerInfo(player.username));
		}
		
		if(type.contains(TickType.WORLDLOAD))
		{
			World world = (World)tickData[0];
			if(world.provider.worldType == 0)
			{
				((TFCProvider)world.provider).createSpawnPosition();
			}
		}
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData)
	{


	}

	@Override
	public EnumSet ticks()
	{
		return EnumSet.of(TickType.WORLD, TickType.WORLDLOAD, TickType.CLIENT, TickType.PLAYER);
	}
	@Override
	public String getLabel()
	{
		return "TFC";
	}

}
