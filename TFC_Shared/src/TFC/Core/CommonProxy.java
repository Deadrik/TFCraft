package TFC.Core;

import java.io.File;
import java.util.Map;

import net.minecraft.src.Container;
import net.minecraft.src.ContainerPlayer;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.Packet;
import net.minecraft.src.ServerPlayerAPI;
import net.minecraft.src.TerraFirmaCraft;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraftforge.common.MinecraftForge;

import TFC.Commands.GetBioTempCommand;
import TFC.Commands.GetTreesCommand;
import TFC.Containers.*;
import TFC.Core.*;
import TFC.Core.Player.PlayerManagerTFC;
import TFC.Core.Player.TFC_PlayerServer;
import TFC.Entities.*;
import TFC.GUI.GuiCalendar;
import TFC.GUI.GuiChestTFC;
import TFC.GUI.GuiHUD;
import TFC.GUI.GuiInventoryTFC;
import TFC.GUI.GuiKnapping;
import TFC.GUI.GuiTerraAnvil;
import TFC.GUI.GuiTerraBloomery;
import TFC.GUI.GuiTerraFirepit;
import TFC.GUI.GuiTerraForge;
import TFC.GUI.GuiTerraLogPile;
import TFC.GUI.GuiTerraMetallurgy;
import TFC.GUI.GuiTerraScribe;
import TFC.GUI.GuiTerraSluice;
import TFC.GUI.GuiTerraWorkbench;
import TFC.Render.TileEntityChestRendererTFC;
import TFC.TileEntities.*;
import TFC.WorldGen.TFCProvider;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.server.FMLServerHandler;

public class CommonProxy implements IGuiHandler
{

	public void registerRenderInformation() {
		// NOOP on server
	}

	public void registerTileEntities(boolean b)
	{
		ModLoader.registerTileEntity(TileEntityTerraLogPile.class, "TerraLogPile");
		ModLoader.registerTileEntity(TileEntityTerraWorkbench.class, "TerraWorkbench");
		ModLoader.registerTileEntity(TileEntityTerraFirepit.class, "TerraFirepit");
		ModLoader.registerTileEntity(TileEntityTerraAnvil.class, "TerraAnvil");
		ModLoader.registerTileEntity(TileEntityTerraScribe.class, "TerraScribe");
		ModLoader.registerTileEntity(TileEntityTerraForge.class, "TerraForge");
		ModLoader.registerTileEntity(TileEntityTerraMetallurgy.class, "TerraMetallurgy");
		ModLoader.registerTileEntity(TileEntityBloomery.class, "TerraBloomery");
		ModLoader.registerTileEntity(TileEntityTerraSluice.class, "TerraSluice");
		ModLoader.registerTileEntity(TileEntityFarmland.class, "TileEntityFarmland");
		ModLoader.registerTileEntity(TileEntityCrop.class, "TileEntityCrop");

		ModLoader.registerTileEntity(TileEntityFruitTreeWood.class, "FruitTreeWood");
		ModLoader.registerTileEntity(TileEntityPartial.class, "Partial");
		ModLoader.registerTileEntity(TileEntityDetailed.class, "Detailed");
		
		ModLoader.registerTileEntity(TileEntityToolRack.class, "ToolRack");
		ModLoader.registerTileEntity(TileEntitySpawnMeter.class, "SpawnMeter");
		
		if(b)
			ModLoader.registerTileEntity(TileEntityChestTFC.class, "chest");

		EntityRegistry.registerGlobalEntityID(EntityCowTFC.class, "cow", ModLoader.getUniqueEntityId(), 0xffffff, 0xbbbbbb);
		EntityRegistry.registerGlobalEntityID(EntitySheepTFC.class, "sheep", ModLoader.getUniqueEntityId(), 0xffffff, 0xbbbbbb);
		EntityRegistry.registerGlobalEntityID(EntityBear.class, "bear", ModLoader.getUniqueEntityId(), 0xd1d003, 0x101010);
		EntityRegistry.registerGlobalEntityID(EntityChickenTFC.class, "chicken", ModLoader.getUniqueEntityId(), 0xffffff, 0xbbbbbb);
		EntityRegistry.registerGlobalEntityID(EntityPigTFC.class, "pig", ModLoader.getUniqueEntityId(), 0xffffff, 0xbbbbbb);
		EntityRegistry.registerGlobalEntityID(EntitySquidTFC.class, "squid", ModLoader.getUniqueEntityId(), 0xffffff, 0xbbbbbb);
		EntityRegistry.registerGlobalEntityID(EntityDeer.class, "deer", ModLoader.getUniqueEntityId(), 0xffffff, 0x105510);
		EntityRegistry.registerGlobalEntityID(EntityCustomMinecart.class, "minecart", ModLoader.getUniqueEntityId());
		//EntityRegistry.registerGlobalEntityID(EntityCustomMinecartCrate.class, "minecartCrate", 108);

		EntityRegistry.registerModEntity(EntityTerraJavelin.class, "javelin", 1,TerraFirmaCraft.instance, 160, 5, true);
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
		//EntityRegistry.registerModEntity(EntityCustomMinecartCrate.class, "TFC minecartCrate", 13,TerraFirmaCraft.instance, 160, 5, true);
	}

	public void registerToolClasses() {
		//pickaxes
		MinecraftForge.setToolClass(TFCItems.IgInPick, "pickaxe", 1);
		MinecraftForge.setToolClass(TFCItems.IgExPick, "pickaxe", 1);
		MinecraftForge.setToolClass(TFCItems.SedPick, "pickaxe", 1);
		MinecraftForge.setToolClass(TFCItems.MMPick, "pickaxe", 1);
		MinecraftForge.setToolClass(TFCItems.boneIgInPick, "pickaxe", 1);
		MinecraftForge.setToolClass(TFCItems.boneIgExPick, "pickaxe", 1);
		MinecraftForge.setToolClass(TFCItems.boneSedPick, "pickaxe", 1);
		MinecraftForge.setToolClass(TFCItems.boneMMPick, "pickaxe", 1);
		MinecraftForge.setToolClass(TFCItems.BismuthPick, "pickaxe", 1);
		MinecraftForge.setToolClass(TFCItems.BismuthBronzePick, "pickaxe", 2);
		MinecraftForge.setToolClass(TFCItems.BlackBronzePick, "pickaxe", 2);
		MinecraftForge.setToolClass(TFCItems.BlackSteelPick, "pickaxe", 5);
		MinecraftForge.setToolClass(TFCItems.BlueSteelPick, "pickaxe", 6);
		MinecraftForge.setToolClass(TFCItems.BronzePick, "pickaxe", 2);
		MinecraftForge.setToolClass(TFCItems.CopperPick, "pickaxe", 1);
		MinecraftForge.setToolClass(TFCItems.WroughtIronPick, "pickaxe", 3);
		MinecraftForge.setToolClass(TFCItems.RedSteelPick, "pickaxe", 6);
		MinecraftForge.setToolClass(TFCItems.RoseGoldPick, "pickaxe", 2);
		MinecraftForge.setToolClass(TFCItems.SteelPick, "pickaxe", 4);
		MinecraftForge.setToolClass(TFCItems.TinPick, "pickaxe", 1);
		MinecraftForge.setToolClass(TFCItems.ZincPick, "pickaxe", 1);
		//shovels
		MinecraftForge.setToolClass(TFCItems.IgInShovel, "shovel", 1);
		MinecraftForge.setToolClass(TFCItems.IgExShovel, "shovel", 1);
		MinecraftForge.setToolClass(TFCItems.SedShovel, "shovel", 1);
		MinecraftForge.setToolClass(TFCItems.MMShovel, "shovel", 1);
		MinecraftForge.setToolClass(TFCItems.boneIgInShovel, "shovel", 1);
		MinecraftForge.setToolClass(TFCItems.boneIgExShovel, "shovel", 1);
		MinecraftForge.setToolClass(TFCItems.boneSedShovel, "shovel", 1);
		MinecraftForge.setToolClass(TFCItems.boneMMShovel, "shovel", 1);
		MinecraftForge.setToolClass(TFCItems.BismuthShovel, "shovel", 1);
		MinecraftForge.setToolClass(TFCItems.BismuthBronzeShovel, "shovel", 2);
		MinecraftForge.setToolClass(TFCItems.BlackBronzeShovel, "shovel", 2);
		MinecraftForge.setToolClass(TFCItems.BlackSteelShovel, "shovel", 5);
		MinecraftForge.setToolClass(TFCItems.BlueSteelShovel, "shovel", 6);
		MinecraftForge.setToolClass(TFCItems.BronzeShovel, "shovel", 2);
		MinecraftForge.setToolClass(TFCItems.CopperShovel, "shovel", 1);
		MinecraftForge.setToolClass(TFCItems.WroughtIronShovel, "shovel", 3);
		MinecraftForge.setToolClass(TFCItems.RedSteelShovel, "shovel", 6);
		MinecraftForge.setToolClass(TFCItems.RoseGoldShovel, "shovel", 2);
		MinecraftForge.setToolClass(TFCItems.SteelShovel, "shovel", 4);
		MinecraftForge.setToolClass(TFCItems.TinShovel, "shovel", 1);
		MinecraftForge.setToolClass(TFCItems.ZincShovel, "shovel", 1);
		//Axes
		MinecraftForge.setToolClass(TFCItems.IgInAxe, "axe", 1);
		MinecraftForge.setToolClass(TFCItems.IgExAxe, "axe", 1);
		MinecraftForge.setToolClass(TFCItems.SedAxe, "axe", 1);
		MinecraftForge.setToolClass(TFCItems.MMAxe, "axe", 1);
		MinecraftForge.setToolClass(TFCItems.boneIgInAxe, "axe", 1);
		MinecraftForge.setToolClass(TFCItems.boneIgExAxe, "axe", 1);
		MinecraftForge.setToolClass(TFCItems.boneSedAxe, "axe", 1);
		MinecraftForge.setToolClass(TFCItems.boneMMAxe, "axe", 1);
		MinecraftForge.setToolClass(TFCItems.BismuthAxe, "axe", 1);
		MinecraftForge.setToolClass(TFCItems.BismuthBronzeAxe, "axe", 2);
		MinecraftForge.setToolClass(TFCItems.BlackBronzeAxe, "axe", 2);
		MinecraftForge.setToolClass(TFCItems.BlackSteelAxe, "axe", 5);
		MinecraftForge.setToolClass(TFCItems.BlueSteelAxe, "axe", 6);
		MinecraftForge.setToolClass(TFCItems.BronzeAxe, "axe", 2);
		MinecraftForge.setToolClass(TFCItems.CopperAxe, "axe", 1);
		MinecraftForge.setToolClass(TFCItems.WroughtIronAxe, "axe", 3);
		MinecraftForge.setToolClass(TFCItems.RedSteelAxe, "axe", 6);
		MinecraftForge.setToolClass(TFCItems.RoseGoldAxe, "axe", 2);
		MinecraftForge.setToolClass(TFCItems.SteelAxe, "axe", 4);
		MinecraftForge.setToolClass(TFCItems.TinAxe, "axe", 1);
		MinecraftForge.setToolClass(TFCItems.ZincAxe, "axe", 1);

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

	public void RegisterPlayerApiClasses()
	{
		ServerPlayerAPI.register("TFC Player Server", TFC_PlayerServer.class);
	}
	
	public void onClientLogin()
	{
		
	}
	
	public void registerTranslations() {
	}

	public File getMinecraftDir() {
		return ModLoader.getMinecraftServerInstance().getFile("");/*new File(".");*/
	}
	
	public void registerSkyProvider(TFCProvider P)
	{
		
	}

	public boolean isRemote() {
		return false;
	}

	public World getCurrentWorld() {
		return null;
	}

	public int waterColorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		return 0;
	}

	public int grassColorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int foliageColorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
		// TODO Auto-generated method stub
		return 0;
	}

	public void takenFromCrafting(EntityPlayer entityplayer,
			ItemStack itemstack, IInventory iinventory)
	{
		GameRegistry.onItemCrafted(entityplayer, itemstack, iinventory);  
	}

	public int getArmorRenderID(int i)
	{
		return 0;
	}

	public boolean getGraphicsLevel()
	{
		// TODO Auto-generated method stub
		return false;
	}

	public void registerKeys(){
	}

	public void registerKeyBindingHandler(){ 
	}
	
	public void registerHighlightHandler(){ 
	}

	public void registerSoundHandler() {
	}

	public void sendCustomPacket(Packet packet)
	{
		ModLoader.getMinecraftServerInstance().getConfigurationManager().sendPacketToAllPlayers(packet);
	}
	
	public void sendCustomPacketToPlayer(EntityPlayerMP player, Packet packet)
	{ 
		player.playerNetServerHandler.sendPacketToPlayer(packet);
	}
	
	public void sendCustomPacketToPlayersInRange(double X, double Y, double Z, Packet packet, double range)
	{ 
		ModLoader.getMinecraftServerInstance().getConfigurationManager().sendToAllNear(X, Y, Z, range, 0, packet);
	}
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
	{
		TileEntity te = world.getBlockTileEntity(x, y, z);
		switch(ID)
		{
		case 0:
		{
			return new ContainerTerraLogPile(player.inventory, (TileEntityTerraLogPile) te, world, x, y, z);
		}
		case 1:
		{
			return new ContainerTerraWorkbench(player.inventory, (TileEntityTerraWorkbench) te, world, x, y, z);
		}
		case 20:
		{
			return new ContainerTerraFirepit(player.inventory, (TileEntityTerraFirepit) te, world, x, y, z);
		}
		case 21:
		{
			return new ContainerTerraAnvil(player.inventory, (TileEntityTerraAnvil) te, world, x, y, z);
		}
		case 22:
		{
			return new ContainerTerraScribe(player.inventory, (TileEntityTerraScribe) te, world, x, y, z);
		}
		case 23:
		{
			return new ContainerTerraForge(player.inventory, (TileEntityTerraForge) te, world, x, y, z);
		}
		case 24:
		{
			return new ContainerTerraMetallurgy(player.inventory, (TileEntityTerraMetallurgy) te, world, x, y, z);
		}
		case 25:
		{
			return new ContainerTerraSluice(player.inventory, (TileEntityTerraSluice) te, world, x, y, z);
		}
		case 26:
		{
			return new ContainerTerraBloomery(player.inventory, (TileEntityBloomery) te, world, x, y, z);
		}
		case 28:
		{
			return new ContainerKnapping(player.inventory, PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(player).knappingRockType, world, x, y, z);
		}
		case 29:
		{
			return new ContainerChestTFC(player.inventory, (TileEntityChestTFC) te, world, x, y, z);
		}
		case 31:
		{
			return new ContainerPlayer(player.inventory);
		}
		default:
		{
			return null;
		}
		}
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) 
	{
		return null;
	}

}
