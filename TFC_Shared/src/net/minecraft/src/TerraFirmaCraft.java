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

import cpw.mods.fml.common.*;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
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
import TFC.Core.*;
import TFC.Entities.*;
import TFC.Handlers.BlockRenderHandler;
import TFC.Handlers.CraftingHandler;
import TFC.Handlers.EntityLivingHandler;
import TFC.Handlers.PacketHandler;
import TFC.Items.*;
import TFC.TileEntities.*;
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
//	public static int logPileGuiId = 0;
//	public static int workbenchGuiId = 1;
//	public static int toolRackGuiId = 2;
//	public static int terraFirepitGuiId = 20;
//	public static int terraAnvilGuiId = 21;
//	public static int terraScribeGuiId = 22;
//	public static int terraForgeGuiId = 23;
//	public static int terraMetallurgyGuiId = 24;
//	public static int terraSluiceGuiId = 25;
	//public static int terraBloomeryGuiId = 26;
	
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

		//Register Blocks
		
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
	}

	@Init
	public void load(FMLInitializationEvent evt)
	{

		TFC_Core.RegisterRecipes();	
		
		TFC_Game.RegisterToolRecipes();
		
		RegisterToolClasses();
		
		// Register Crafting Handler
        GameRegistry.registerCraftingHandler(new CraftingHandler());
        
        // Register the EntityLiving Handler
        MinecraftForge.EVENT_BUS.register(new EntityLivingHandler());

        // Register Gui Handler
		NetworkRegistry.instance().registerGuiHandler(this, proxy);
		
		// Register Packet Handler
		NetworkRegistry.instance().registerConnectionHandler(new PacketHandler());
		
		proxy.registerRenderInformation();


		if(TFCSettings.enableVanillaRecipes == false)
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
		MinecartRegistry.registerMinecart(EntityCustomMinecart.class, 0, new ItemStack(TFCItems.minecartEmpty));
		MinecartRegistry.registerMinecart(EntityCustomMinecart.class, 1, new ItemStack(TFCItems.minecartCrate));
	}

	@PostInit
	public void modsLoaded(FMLPostInitializationEvent evt) 
	{

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

	

	private static void RegisterToolClasses() 
	{
		//pickaxes
		MinecraftForge.setToolClass(TFCItems.terraIgInPick, "pickaxe", 1);
		MinecraftForge.setToolClass(TFCItems.terraIgExPick, "pickaxe", 1);
		MinecraftForge.setToolClass(TFCItems.terraSedPick, "pickaxe", 1);
		MinecraftForge.setToolClass(TFCItems.terraMMPick, "pickaxe", 1);
		MinecraftForge.setToolClass(TFCItems.boneIgInPick, "pickaxe", 1);
		MinecraftForge.setToolClass(TFCItems.boneIgExPick, "pickaxe", 1);
		MinecraftForge.setToolClass(TFCItems.boneSedPick, "pickaxe", 1);
		MinecraftForge.setToolClass(TFCItems.boneMMPick, "pickaxe", 1);
		MinecraftForge.setToolClass(TFCItems.terraBismuthPick, "pickaxe", 1);
		MinecraftForge.setToolClass(TFCItems.terraBismuthBronzePick, "pickaxe", 2);
		MinecraftForge.setToolClass(TFCItems.terraBlackBronzePick, "pickaxe", 2);
		MinecraftForge.setToolClass(TFCItems.terraBlackSteelPick, "pickaxe", 5);
		MinecraftForge.setToolClass(TFCItems.terraBlueSteelPick, "pickaxe", 6);
		MinecraftForge.setToolClass(TFCItems.terraBronzePick, "pickaxe", 2);
		MinecraftForge.setToolClass(TFCItems.terraCopperPick, "pickaxe", 1);
		MinecraftForge.setToolClass(TFCItems.terraWroughtIronPick, "pickaxe", 3);
		MinecraftForge.setToolClass(TFCItems.terraRedSteelPick, "pickaxe", 6);
		MinecraftForge.setToolClass(TFCItems.terraRoseGoldPick, "pickaxe", 2);
		MinecraftForge.setToolClass(TFCItems.terraSteelPick, "pickaxe", 4);
		MinecraftForge.setToolClass(TFCItems.terraTinPick, "pickaxe", 1);
		MinecraftForge.setToolClass(TFCItems.terraZincPick, "pickaxe", 1);
		//shovels
		MinecraftForge.setToolClass(TFCItems.terraIgInShovel, "shovel", 1);
		MinecraftForge.setToolClass(TFCItems.terraIgExShovel, "shovel", 1);
		MinecraftForge.setToolClass(TFCItems.terraSedShovel, "shovel", 1);
		MinecraftForge.setToolClass(TFCItems.terraMMShovel, "shovel", 1);
		MinecraftForge.setToolClass(TFCItems.boneIgInShovel, "shovel", 1);
		MinecraftForge.setToolClass(TFCItems.boneIgExShovel, "shovel", 1);
		MinecraftForge.setToolClass(TFCItems.boneSedShovel, "shovel", 1);
		MinecraftForge.setToolClass(TFCItems.boneMMShovel, "shovel", 1);
		MinecraftForge.setToolClass(TFCItems.terraBismuthShovel, "shovel", 1);
		MinecraftForge.setToolClass(TFCItems.terraBismuthBronzeShovel, "shovel", 2);
		MinecraftForge.setToolClass(TFCItems.terraBlackBronzeShovel, "shovel", 2);
		MinecraftForge.setToolClass(TFCItems.terraBlackSteelShovel, "shovel", 5);
		MinecraftForge.setToolClass(TFCItems.terraBlueSteelShovel, "shovel", 6);
		MinecraftForge.setToolClass(TFCItems.terraBronzeShovel, "shovel", 2);
		MinecraftForge.setToolClass(TFCItems.terraCopperShovel, "shovel", 1);
		MinecraftForge.setToolClass(TFCItems.terraWroughtIronShovel, "shovel", 3);
		MinecraftForge.setToolClass(TFCItems.terraRedSteelShovel, "shovel", 6);
		MinecraftForge.setToolClass(TFCItems.terraRoseGoldShovel, "shovel", 2);
		MinecraftForge.setToolClass(TFCItems.terraSteelShovel, "shovel", 4);
		MinecraftForge.setToolClass(TFCItems.terraTinShovel, "shovel", 1);
		MinecraftForge.setToolClass(TFCItems.terraZincShovel, "shovel", 1);
		//Axes
		MinecraftForge.setToolClass(TFCItems.terraIgInAxe, "axe", 1);
		MinecraftForge.setToolClass(TFCItems.terraIgExAxe, "axe", 1);
		MinecraftForge.setToolClass(TFCItems.terraSedAxe, "axe", 1);
		MinecraftForge.setToolClass(TFCItems.terraMMAxe, "axe", 1);
		MinecraftForge.setToolClass(TFCItems.boneIgInAxe, "axe", 1);
		MinecraftForge.setToolClass(TFCItems.boneIgExAxe, "axe", 1);
		MinecraftForge.setToolClass(TFCItems.boneSedAxe, "axe", 1);
		MinecraftForge.setToolClass(TFCItems.boneMMAxe, "axe", 1);
		MinecraftForge.setToolClass(TFCItems.terraBismuthAxe, "axe", 1);
		MinecraftForge.setToolClass(TFCItems.terraBismuthBronzeAxe, "axe", 2);
		MinecraftForge.setToolClass(TFCItems.terraBlackBronzeAxe, "axe", 2);
		MinecraftForge.setToolClass(TFCItems.terraBlackSteelAxe, "axe", 5);
		MinecraftForge.setToolClass(TFCItems.terraBlueSteelAxe, "axe", 6);
		MinecraftForge.setToolClass(TFCItems.terraBronzeAxe, "axe", 2);
		MinecraftForge.setToolClass(TFCItems.terraCopperAxe, "axe", 1);
		MinecraftForge.setToolClass(TFCItems.terraWroughtIronAxe, "axe", 3);
		MinecraftForge.setToolClass(TFCItems.terraRedSteelAxe, "axe", 6);
		MinecraftForge.setToolClass(TFCItems.terraRoseGoldAxe, "axe", 2);
		MinecraftForge.setToolClass(TFCItems.terraSteelAxe, "axe", 4);
		MinecraftForge.setToolClass(TFCItems.terraTinAxe, "axe", 1);
		MinecraftForge.setToolClass(TFCItems.terraZincAxe, "axe", 1);

		MinecraftForge.setToolClass(TFCItems.BismuthSaw, "axe", 1);
		MinecraftForge.setToolClass(TFCItems.BismuthBronzeSaw, "axe", 2);
		MinecraftForge.setToolClass(TFCItems.BlackBronzeSaw, "axe", 2);
		MinecraftForge.setToolClass(TFCItems.BlackSteelSaw, "axe", 5);
		MinecraftForge.setToolClass(TFCItems.BlueSteelSaw, "axe", 6);
		MinecraftForge.setToolClass(TFCItems.BronzeSaw, "axe", 2);
		MinecraftForge.setToolClass(TFCItems.CopperSaw, "axe", 1);
		MinecraftForge.setToolClass(TFCItems.WroughtIronSaw, "axe", 3);
		MinecraftForge.setToolClass(TFCItems.RedSteelSaw, "axe", 6);
		MinecraftForge.setToolClass(TFCItems.RoseGoldSaw, "axe", 2);
		MinecraftForge.setToolClass(TFCItems.SteelSaw, "axe", 4);
		MinecraftForge.setToolClass(TFCItems.TinSaw, "axe", 1);
		MinecraftForge.setToolClass(TFCItems.ZincSaw, "axe", 1);
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


			TFCSeasons.UpdateSeasons(world);
			for(Object p : world.playerEntities)
			{
				TFCHeat.HandleContainerHeat(world, ((EntityPlayer)p).inventory.mainInventory, (int)((EntityPlayer)p).posX, (int)((EntityPlayer)p).posY, (int)((EntityPlayer)p).posZ);
			}
		}

		if(type.contains(TickType.PLAYER))
		{
			EntityPlayer player = (EntityPlayer)tickData[0];
			if(!doOnce && PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(player) == null)
				PlayerManagerTFC.getInstance().Players.add(new PlayerInfo(player.username));
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
