package TFC;

import java.io.File;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import TFC.Entities.EntityCustomMinecart;
import TFC.Entities.EntityFallingDirt;
import TFC.Entities.EntityFallingStone;
import TFC.Entities.EntityJavelin;
import TFC.Entities.EntityProjectileTFC;
import TFC.Entities.EntityStand;
import TFC.Entities.Mobs.EntityBear;
import TFC.Entities.Mobs.EntityBlazeTFC;
import TFC.Entities.Mobs.EntityCaveSpiderTFC;
import TFC.Entities.Mobs.EntityChickenTFC;
import TFC.Entities.Mobs.EntityCowTFC;
import TFC.Entities.Mobs.EntityCreeperTFC;
import TFC.Entities.Mobs.EntityDeer;
import TFC.Entities.Mobs.EntityEndermanTFC;
import TFC.Entities.Mobs.EntityGhastTFC;
import TFC.Entities.Mobs.EntityHorseTFC;
import TFC.Entities.Mobs.EntityIronGolemTFC;
import TFC.Entities.Mobs.EntityPheasantTFC;
import TFC.Entities.Mobs.EntityPigTFC;
import TFC.Entities.Mobs.EntityPigZombieTFC;
import TFC.Entities.Mobs.EntitySheepTFC;
import TFC.Entities.Mobs.EntitySilverfishTFC;
import TFC.Entities.Mobs.EntitySkeletonTFC;
import TFC.Entities.Mobs.EntitySlimeTFC;
import TFC.Entities.Mobs.EntitySpiderTFC;
import TFC.Entities.Mobs.EntitySquidTFC;
import TFC.Entities.Mobs.EntityWolfTFC;
import TFC.Entities.Mobs.EntityZombieTFC;
import TFC.Handlers.GuiHandler;
import TFC.Handlers.ServerTickHandler;
import TFC.TileEntities.TEBerryBush;
import TFC.TileEntities.TEBlastFurnace;
import TFC.TileEntities.TECrop;
import TFC.TileEntities.TECrucible;
import TFC.TileEntities.TENestBox;
import TFC.TileEntities.TESeaWeed;
import TFC.TileEntities.TEStand;
import TFC.TileEntities.TileEntityAnvil;
import TFC.TileEntities.TileEntityBarrel;
import TFC.TileEntities.TileEntityBellows;
import TFC.TileEntities.TileEntityBloom;
import TFC.TileEntities.TileEntityChestTFC;
import TFC.TileEntities.TileEntityDetailed;
import TFC.TileEntities.TileEntityEarlyBloomery;
import TFC.TileEntities.TileEntityFarmland;
import TFC.TileEntities.TileEntityFenceGate;
import TFC.TileEntities.TileEntityFirepit;
import TFC.TileEntities.TileEntityFoodPrep;
import TFC.TileEntities.TileEntityForge;
import TFC.TileEntities.TileEntityFruitTreeWood;
import TFC.TileEntities.TileEntityIngotPile;
import TFC.TileEntities.TileEntityLogPile;
import TFC.TileEntities.TileEntityMetallurgy;
import TFC.TileEntities.TileEntityPartial;
import TFC.TileEntities.TileEntityPottery;
import TFC.TileEntities.TileEntityQuern;
import TFC.TileEntities.TileEntitySapling;
import TFC.TileEntities.TileEntityScribe;
import TFC.TileEntities.TileEntitySluice;
import TFC.TileEntities.TileEntitySpawnMeter;
import TFC.TileEntities.TileEntityToolRack;
import TFC.TileEntities.TileEntityWoodConstruct;
import TFC.TileEntities.TileEntityWorkbench;
import TFC.WorldGen.TFCProvider;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.ModClassLoader;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy
{
	public void registerRenderInformation()
	{
		// NOOP on server
	}

	public void registerBiomeEventHandler()
	{
		// NOOP on server
	}

	public void setupGuiIngameForge()
	{
		// NOOP on server
	}

	public String getCurrentLanguage()
	{
		return null;
	}

	public void registerTileEntities(boolean b)
	{
		GameRegistry.registerTileEntity(TileEntityLogPile.class, "TerraLogPile");

		GameRegistry.registerTileEntity(TileEntityWorkbench.class, "TerraWorkbench");
		GameRegistry.registerTileEntity(TileEntityFirepit.class, "TerraFirepit");

		GameRegistry.registerTileEntity(TileEntityScribe.class, "TerraScribe");
		GameRegistry.registerTileEntity(TileEntityForge.class, "TerraForge");
		GameRegistry.registerTileEntity(TileEntityMetallurgy.class, "TerraMetallurgy");
		GameRegistry.registerTileEntity(TEBlastFurnace.class, "TerraBloomery");
		GameRegistry.registerTileEntity(TileEntityEarlyBloomery.class, "TerraEarlyBloomery");
		GameRegistry.registerTileEntity(TileEntitySluice.class, "TerraSluice");
		GameRegistry.registerTileEntity(TileEntityFarmland.class, "TileEntityFarmland");
		GameRegistry.registerTileEntity(TECrop.class, "TileEntityCrop");

		GameRegistry.registerTileEntity(TileEntityFruitTreeWood.class, "FruitTreeWood");
		GameRegistry.registerTileEntity(TileEntityPartial.class, "Partial");
		GameRegistry.registerTileEntity(TileEntityDetailed.class, "Detailed");


		GameRegistry.registerTileEntity(TileEntitySpawnMeter.class, "SpawnMeter");

		GameRegistry.registerTileEntity(TileEntityQuern.class, "Quern");
		GameRegistry.registerTileEntity(TileEntitySapling.class, "Sapling");

		GameRegistry.registerTileEntity(TileEntityWoodConstruct.class, "Wood Construct");

		GameRegistry.registerTileEntity(TileEntityBarrel.class, "Barrel");
		GameRegistry.registerTileEntity(TileEntityFenceGate.class, "FenceGate");

		GameRegistry.registerTileEntity(TileEntityBloom.class, "IronBloom");
		GameRegistry.registerTileEntity(TECrucible.class, "Crucible");
		GameRegistry.registerTileEntity(TENestBox.class, "Nest Box");
		GameRegistry.registerTileEntity(TEStand.class, "Armour Stand");
		GameRegistry.registerTileEntity(TEBerryBush.class, "Berry Bush");
		GameRegistry.registerTileEntity(TESeaWeed.class, "Sea Weed");

		if(b)
		{
			GameRegistry.registerTileEntity(TileEntityIngotPile.class, "ingotPile");
			GameRegistry.registerTileEntity(TileEntityPottery.class, "Pottery");
			GameRegistry.registerTileEntity(TileEntityChestTFC.class, "chest");
			GameRegistry.registerTileEntity(TileEntityFoodPrep.class, "FoodPrep");
			GameRegistry.registerTileEntity(TileEntityBellows.class, "Bellows");
			GameRegistry.registerTileEntity(TileEntityToolRack.class, "ToolRack");
			GameRegistry.registerTileEntity(TileEntityAnvil.class, "TerraAnvil");
		}

//		EntityRegistry.registerGlobalEntityID(EntityWolfTFC.class, "Wolf", EntityRegistry.findGlobalUniqueEntityId(), 0xffffff, 0xaaaaaa);
//		EntityRegistry.registerGlobalEntityID(EntityCowTFC.class, "Cow", EntityRegistry.findGlobalUniqueEntityId(), 0xffffff, 0xbbbbbb);
//		EntityRegistry.registerGlobalEntityID(EntitySheepTFC.class, "Sheep", EntityRegistry.findGlobalUniqueEntityId(), 0xffffff, 0xbbbbbb);
		EntityRegistry.registerGlobalEntityID(EntityBear.class, "Bear", EntityRegistry.findGlobalUniqueEntityId(), 0xffffff, 0xbbbbbb);
//		EntityRegistry.registerGlobalEntityID(EntityChickenTFC.class, "Chicken", EntityRegistry.findGlobalUniqueEntityId(), 0xffffff, 0xbbbbbb);
		EntityRegistry.registerGlobalEntityID(EntityPheasantTFC.class, "Pheasant", EntityRegistry.findGlobalUniqueEntityId(), 0xffffff, 0xbbbbbb);
//		EntityRegistry.registerGlobalEntityID(EntityPigTFC.class, "Pig", EntityRegistry.findGlobalUniqueEntityId(), 0xffffff, 0xbbbbbb);
//		EntityRegistry.registerGlobalEntityID(EntitySquidTFC.class, "Squid", EntityRegistry.findGlobalUniqueEntityId(), 0xffffff, 0xbbbbbb);
		EntityRegistry.registerGlobalEntityID(EntityDeer.class, "Deer", EntityRegistry.findGlobalUniqueEntityId(), 0xffffff, 0x105510);
//		EntityRegistry.registerGlobalEntityID(EntityHorseTFC.class, "Horse", EntityRegistry.findGlobalUniqueEntityId(), 0xffffff, 0x105510);
		EntityRegistry.registerGlobalEntityID(EntityCustomMinecart.class, "minecart", EntityRegistry.findGlobalUniqueEntityId());
//		EntityRegistry.registerGlobalEntityID(EntitySkeletonTFC.class, "Skeleton", EntityRegistry.findGlobalUniqueEntityId(), 0xffffff, 0x105510);
//		EntityRegistry.registerGlobalEntityID(EntityZombieTFC.class, "Zombie", EntityRegistry.findGlobalUniqueEntityId(), 0xffffff, 0x105510);
//		EntityRegistry.registerGlobalEntityID(EntitySpiderTFC.class, "Spider", EntityRegistry.findGlobalUniqueEntityId(), 0xffffff, 0x105510);
//		EntityRegistry.registerGlobalEntityID(EntitySlimeTFC.class, "Slime", EntityRegistry.findGlobalUniqueEntityId(), 0xffffff, 0x105510);
//		EntityRegistry.registerGlobalEntityID(EntitySilverfishTFC.class, "Silverfish", EntityRegistry.findGlobalUniqueEntityId(), 0xffffff, 0x105510);
//		EntityRegistry.registerGlobalEntityID(EntityGhastTFC.class, "Ghast", EntityRegistry.findGlobalUniqueEntityId(), 0xffffff, 0x105510);
//		EntityRegistry.registerGlobalEntityID(EntityCaveSpiderTFC.class, "CaveSpider", EntityRegistry.findGlobalUniqueEntityId(), 0xffffff, 0x105510);
//		EntityRegistry.registerGlobalEntityID(EntityBlazeTFC.class, "Blaze", EntityRegistry.findGlobalUniqueEntityId(), 0xffffff, 0x105510);
//		EntityRegistry.registerGlobalEntityID(EntityEndermanTFC.class, "Enderman", EntityRegistry.findGlobalUniqueEntityId(), 0xffffff, 0x105510);
//		EntityRegistry.registerGlobalEntityID(EntityPigZombieTFC.class, "PigZombie", EntityRegistry.findGlobalUniqueEntityId(), 0xffffff, 0x105510);
		EntityRegistry.registerGlobalEntityID(EntityIronGolemTFC.class, "irongolem", EntityRegistry.findGlobalUniqueEntityId(), 0xffffff, 0x105510);
//		EntityRegistry.registerGlobalEntityID(EntityCreeperTFC.class, "Creeper", EntityRegistry.findGlobalUniqueEntityId(), 0xffffff, 0x105510);
		EntityRegistry.registerGlobalEntityID(EntityProjectileTFC.class, "arrow", EntityRegistry.findGlobalUniqueEntityId());
		EntityRegistry.registerGlobalEntityID(EntityStand.class, "stand", EntityRegistry.findGlobalUniqueEntityId());

		EntityRegistry.registerModEntity(EntityJavelin.class, "javelin", 1,TerraFirmaCraft.instance, 160, 5, true);
		EntityRegistry.registerModEntity(EntitySquidTFC.class, "squid", 2,TerraFirmaCraft.instance, 160, 5, true);
		EntityRegistry.registerModEntity(EntityFallingStone.class, "fallingstone", 3,TerraFirmaCraft.instance, 160, 5, true);
		EntityRegistry.registerModEntity(EntityFallingDirt.class, "fallingdirt", 4,TerraFirmaCraft.instance, 160, 5, true);
		EntityRegistry.registerModEntity(EntityCowTFC.class, "cowTFC", 6,TerraFirmaCraft.instance, 160, 5, true);
		EntityRegistry.registerModEntity(EntityWolfTFC.class, "wolfTFC", 7,TerraFirmaCraft.instance, 160, 5, true);
		EntityRegistry.registerModEntity(EntityBear.class, "bearTFC", 8,TerraFirmaCraft.instance, 160, 5, true);
		EntityRegistry.registerModEntity(EntityChickenTFC.class, "chickenTFC", 9,TerraFirmaCraft.instance, 160, 5, true);
		EntityRegistry.registerModEntity(EntityPigTFC.class, "pigTFC", 10,TerraFirmaCraft.instance, 160, 5, true);
		EntityRegistry.registerModEntity(EntityDeer.class, "deerTFC", 11,TerraFirmaCraft.instance, 160, 5, true);

		EntityRegistry.registerModEntity(EntityCustomMinecart.class, "TFC minecart", 12,TerraFirmaCraft.instance, 160, 5, true);

		EntityRegistry.registerModEntity(EntitySkeletonTFC.class, "skeletonTFC", 13,TerraFirmaCraft.instance, 160, 5, true);
		EntityRegistry.registerModEntity(EntityZombieTFC.class, "zombieTFC", 14,TerraFirmaCraft.instance, 160, 5, true);
		EntityRegistry.registerModEntity(EntitySpiderTFC.class, "spiderTFC", 15,TerraFirmaCraft.instance, 160, 5, true);
		EntityRegistry.registerModEntity(EntitySlimeTFC.class, "slimeTFC", 16,TerraFirmaCraft.instance, 160, 5, true);
		EntityRegistry.registerModEntity(EntitySilverfishTFC.class, "silverfishTFC", 17,TerraFirmaCraft.instance, 160, 5, true);
		EntityRegistry.registerModEntity(EntityGhastTFC.class, "ghastTFC", 18,TerraFirmaCraft.instance, 160, 5, true);
		EntityRegistry.registerModEntity(EntityCaveSpiderTFC.class, "cavespiderTFC", 19,TerraFirmaCraft.instance, 160, 5, true);
		EntityRegistry.registerModEntity(EntityBlazeTFC.class, "blazeTFC", 20,TerraFirmaCraft.instance, 160, 5, true);
		EntityRegistry.registerModEntity(EntityEndermanTFC.class, "endermanTFC", 21,TerraFirmaCraft.instance, 160, 5, true);
		EntityRegistry.registerModEntity(EntityPigZombieTFC.class, "pigzombieTFC", 22,TerraFirmaCraft.instance, 160, 5, true);
		EntityRegistry.registerModEntity(EntityIronGolemTFC.class, "irongolemTFC", 23,TerraFirmaCraft.instance, 160, 5, true);
		EntityRegistry.registerModEntity(EntityCreeperTFC.class, "creeperTFC", 24,TerraFirmaCraft.instance, 160, 5, true);

		EntityRegistry.registerModEntity(EntityStand.class, "standTFC", 25,TerraFirmaCraft.instance, 160, 5, true);

		//EntityRegistry.registerModEntity(EntityArrowTFC.class, "deerTFC", 25,TerraFirmaCraft.instance, 160, 5, true);
	}

	public void registerToolClasses()
	{
		//pickaxes
		TFCItems.BismuthBronzePick.setHarvestLevel("pickaxe", 2);
		TFCItems.BismuthBronzePick.setHarvestLevel("pickaxe", 2);
		TFCItems.BlackBronzePick.setHarvestLevel("pickaxe", 2);
		TFCItems.BlackSteelPick.setHarvestLevel("pickaxe", 5);
		TFCItems.BlueSteelPick.setHarvestLevel("pickaxe", 6);
		TFCItems.BronzePick.setHarvestLevel("pickaxe", 2);
		TFCItems.CopperPick.setHarvestLevel("pickaxe", 1);
		TFCItems.WroughtIronPick.setHarvestLevel("pickaxe", 3);
		TFCItems.RedSteelPick.setHarvestLevel("pickaxe", 6);
		TFCItems.SteelPick.setHarvestLevel("pickaxe", 4);
		//shovels
		TFCItems.IgInShovel.setHarvestLevel("shovel", 1);
		TFCItems.IgExShovel.setHarvestLevel("shovel", 1);
		TFCItems.SedShovel.setHarvestLevel("shovel", 1);
		TFCItems.MMShovel.setHarvestLevel("shovel", 1);
		TFCItems.BismuthBronzeShovel.setHarvestLevel("shovel", 2);
		TFCItems.BlackBronzeShovel.setHarvestLevel("shovel", 2);
		TFCItems.BlackSteelShovel.setHarvestLevel("shovel", 5);
		TFCItems.BlueSteelShovel.setHarvestLevel("shovel", 6);
		TFCItems.BronzeShovel.setHarvestLevel("shovel", 2);
		TFCItems.CopperShovel.setHarvestLevel("shovel", 1);
		TFCItems.WroughtIronShovel.setHarvestLevel("shovel", 3);
		TFCItems.RedSteelShovel.setHarvestLevel("shovel", 6);
		TFCItems.SteelShovel.setHarvestLevel("shovel", 4);
		//Axes
		TFCItems.IgInAxe.setHarvestLevel("axe", 1);
		TFCItems.IgExAxe.setHarvestLevel("axe", 1);
		TFCItems.SedAxe.setHarvestLevel("axe", 1);
		TFCItems.MMAxe.setHarvestLevel("axe", 1);
		TFCItems.BismuthBronzeAxe.setHarvestLevel("axe", 2);
		TFCItems.BlackBronzeAxe.setHarvestLevel("axe", 2);
		TFCItems.BlackSteelAxe.setHarvestLevel("axe", 5);
		TFCItems.BlueSteelAxe.setHarvestLevel("axe", 6);
		TFCItems.BronzeAxe.setHarvestLevel("axe", 2);
		TFCItems.CopperAxe.setHarvestLevel("axe", 1);
		TFCItems.WroughtIronAxe.setHarvestLevel("axe", 3);
		TFCItems.RedSteelAxe.setHarvestLevel("axe", 6);
		TFCItems.SteelAxe.setHarvestLevel("axe", 4);

		TFCItems.BismuthBronzeSaw.setHarvestLevel("axe", 2);
		TFCItems.BlackBronzeSaw.setHarvestLevel("axe", 2);
		TFCItems.BlackSteelSaw.setHarvestLevel("axe", 5);
		TFCItems.BlueSteelSaw.setHarvestLevel("axe", 6);
		TFCItems.BronzeSaw.setHarvestLevel("axe", 2);
		TFCItems.CopperSaw.setHarvestLevel("axe", 1);
		TFCItems.WroughtIronSaw.setHarvestLevel("axe", 3);
		TFCItems.RedSteelSaw.setHarvestLevel("axe", 6);
		TFCItems.SteelSaw.setHarvestLevel("axe", 4);

		TFCItems.StoneHammer.setHarvestLevel("hammer", 1);
		TFCItems.BismuthBronzeHammer.setHarvestLevel("hammer", 2);
		TFCItems.BlackBronzeHammer.setHarvestLevel("hammer", 2);
		TFCItems.BlackSteelHammer.setHarvestLevel("hammer", 5);
		TFCItems.BlueSteelHammer.setHarvestLevel("hammer", 6);
		TFCItems.BronzeHammer.setHarvestLevel("hammer", 2);
		TFCItems.CopperHammer.setHarvestLevel("hammer", 1);
		TFCItems.WroughtIronHammer.setHarvestLevel("hammer", 3);
		TFCItems.RedSteelHammer.setHarvestLevel("hammer", 6);
		TFCItems.SteelHammer.setHarvestLevel("hammer", 4);
	}

	public void onClientLogin()
	{
	}

	public void registerTranslations()
	{
	}

	public File getMinecraftDir()
	{
		return FMLCommonHandler.instance().getMinecraftServerInstance().getFile("");/*new File(".");*/
	}

	public void registerSkyProvider(TFCProvider P)
	{
	}

	public boolean isRemote()
	{
		return false;
	}

	public World getCurrentWorld()
	{
		return MinecraftServer.getServer().getEntityWorld();
	}

	public int waterColorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		return 0;
	}

	public int grassColorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		return 0;
	}

	public int foliageColorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		return 0;
	}

	public void takenFromCrafting(EntityPlayer entityplayer, ItemStack itemstack, IInventory iinventory)
	{
		FMLCommonHandler.instance().firePlayerCraftingEvent(entityplayer, itemstack, iinventory);
	}

	public int getArmorRenderID(String name)
	{
		return 0;
	}

	public boolean getGraphicsLevel()
	{
		return false;
	}

	public void registerKeys()
	{
	}

	public void registerKeyBindingHandler()
	{
	}
	
	public void uploadKeyBindingsToGame()
	{
	}

	public void registerHighlightHandler()
	{
	}

	public void registerSoundHandler()
	{
	}

	public void registerTickHandler()
	{
		FMLCommonHandler.instance().bus().register(new ServerTickHandler());
		new ServerTickHandler();
	}
	
	public void registerGuiHandler()
	{
		NetworkRegistry.INSTANCE.registerGuiHandler(TerraFirmaCraft.instance, new GuiHandler());
	}

//	public void sendCustomPacket(Packet packet)
//	{
////		FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().sendPacketToAllPlayers(packet);
//	}
//
//	public void sendCustomPacketToPlayer(EntityPlayerMP player, Packet packet)
//	{
////		player.playerNetServerHandler.sendPacket(packet);
//	}
//
//	public void sendCustomPacketToPlayersInRange(double X, double Y, double Z, Packet packet, double range)
//	{
////		FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().sendToAllNear(X, Y, Z, range, 0, packet);
//	}
}
