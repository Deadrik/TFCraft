package TFC.Core;

import java.io.File;
import java.util.Map;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.Packet;
import net.minecraft.src.TerraFirmaCraft;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraftforge.common.MinecraftForge;

import TFC.Commands.GetBioTempCommand;
import TFC.Commands.GetTreesCommand;
import TFC.Containers.*;
import TFC.Core.*;
import TFC.Entities.*;
import TFC.GUI.GuiCalendar;
import TFC.GUI.GuiChestTFC;
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

	public void registerTileEntities()
	{
		ModLoader.registerTileEntity(TileEntityTerraLogPile.class, "TerraLogPile");
		ModLoader.registerTileEntity(TileEntityTerraWorkbench.class, "TerraWorkbench");
		ModLoader.registerTileEntity(TileEntityTerraFirepit.class, "TerraFirepit");
		ModLoader.registerTileEntity(TileEntityTerraAnvil.class, "TerraAnvil");
		ModLoader.registerTileEntity(TileEntityTerraScribe.class, "TerraScribe");
		ModLoader.registerTileEntity(TileEntityTerraForge.class, "TerraForge");
		ModLoader.registerTileEntity(TileEntityTerraMetallurgy.class, "TerraMetallurgy");
		ModLoader.registerTileEntity(TileEntityTerraBloomery.class, "TerraBloomery");
		ModLoader.registerTileEntity(TileEntityTerraSluice.class, "TerraSluice");
		ModLoader.registerTileEntity(TileEntityFarmland.class, "TileEntityFarmland");
		ModLoader.registerTileEntity(TileEntityCrop.class, "TileEntityCrop");

		ModLoader.registerTileEntity(TileEntityFruitTreeWood.class, "FruitTreeWood");
		ModLoader.registerTileEntity(TileEntityPartial.class, "Partial");
		ModLoader.registerTileEntity(TileEntityChestTFC.class, "chest", new TileEntityChestRendererTFC());

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

	public void registerTranslations() {
	}

	public File getMinecraftDir() {
		return ModLoader.getMinecraftInstance().getMinecraftDir();/*new File(".");*/
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

	public void sendCustomPacket(Packet packet)
	{
		ModLoader.getMinecraftServerInstance().getConfigurationManager().sendPacketToAllPlayers(packet);
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


	public void registerSoundHandler() {
	}

	public void sendCustomPacketToPlayer(String player, Packet packet)
	{
		ModLoader.getMinecraftServerInstance().getConfigurationManager().sendPacketToAllPlayers(packet);     
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
			return new ContainerTerraBloomery(player.inventory, (TileEntityTerraBloomery) te, world, x, y, z);
		}
		case 28:
		{
			return new ContainerKnapping(player.inventory, PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(player).knappingRockType, world, x, y, z);
		}
		case 29:
		{
			return new ContainerChestTFC(player.inventory, (TileEntityChestTFC) te, world, x, y, z);
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
		TileEntity te;
		try
		{
			te= world.getBlockTileEntity(x, y, z);
		}
		catch(Exception e)
		{
			te = null;
		}

		switch(ID)
		{
		case 0:
		{
			return new GuiTerraLogPile(player.inventory, (TileEntityTerraLogPile) te, world, x, y, z);
		}
		case 1:
		{
			return new GuiTerraWorkbench(player.inventory, (TileEntityTerraWorkbench) te, world, x, y, z);
		}
		case 20:
		{
			return new GuiTerraFirepit(player.inventory, (TileEntityTerraFirepit) te, world, x, y, z);
		}
		case 21:
		{
			return new GuiTerraAnvil(player.inventory, (TileEntityTerraAnvil) te, world, x, y, z);
		}
		case 22:
		{
			return new GuiTerraScribe(player.inventory, (TileEntityTerraScribe) te, world, x, y, z);
		}
		case 23:
		{
			return new GuiTerraForge(player.inventory, (TileEntityTerraForge) te, world, x, y, z);
		}
		case 24:
		{
			return new GuiTerraMetallurgy(player.inventory, (TileEntityTerraMetallurgy) te, world, x, y, z);
		}
		case 25:
		{
			return new GuiTerraSluice(player.inventory, (TileEntityTerraSluice) te, world, x, y, z);
		}
		case 26:
		{
			return new GuiTerraBloomery(player.inventory, (TileEntityTerraBloomery) te, world, x, y, z);
		}
		case 27:
		{
			return new GuiCalendar(player, world, x, y, z);
		}
		case 28:
		{
			return new GuiKnapping(player.inventory, PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(player).knappingRockType , world, x, y, z);
		}
		case 29:
		{
			return new GuiChestTFC(player.inventory, ((TileEntityChestTFC) te), world, x, y, z);
		}

		}
		return null;
	}

}
