package TFC;

import java.io.File;
import java.util.Map;

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
import net.minecraft.src.ModLoader;
import net.minecraft.stats.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.village.*;
import net.minecraft.world.*;
import net.minecraft.world.chunk.*;
import net.minecraftforge.common.MinecraftForge;

import TFC.Commands.GetBioTempCommand;
import TFC.Commands.GetTreesCommand;
import TFC.Containers.*;
import TFC.Core.*;
import TFC.Core.Player.PlayerManagerTFC;
import TFC.Core.Player.TFC_PlayerServer;
import TFC.Entities.*;
import TFC.Entities.Mobs.*;
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
		ModLoader.registerTileEntity(TileEntitySluice.class, "TerraSluice");
		ModLoader.registerTileEntity(TileEntityFarmland.class, "TileEntityFarmland");
		ModLoader.registerTileEntity(TileEntityCrop.class, "TileEntityCrop");

		ModLoader.registerTileEntity(TileEntityFruitTreeWood.class, "FruitTreeWood");
		ModLoader.registerTileEntity(TileEntityPartial.class, "Partial");
		ModLoader.registerTileEntity(TileEntityDetailed.class, "Detailed");
		
		ModLoader.registerTileEntity(TileEntityToolRack.class, "ToolRack");
		ModLoader.registerTileEntity(TileEntitySpawnMeter.class, "SpawnMeter");
		ModLoader.registerTileEntity(TileEntityFoodPrep.class, "FoodPrep");
		ModLoader.registerTileEntity(TileEntityQuern.class, "Quern");
		ModLoader.registerTileEntity(TileEntitySapling.class, "Sapling");
		
		ModLoader.registerTileEntity(TileEntityWoodConstruct.class, "Wood Construct");
		ModLoader.registerTileEntity(TileEntitySuperDetailed.class, "Super Detailed");
		
		if(b)
			ModLoader.registerTileEntity(TileEntityChestTFC.class, "chest");

		EntityRegistry.registerGlobalEntityID(EntityCowTFC.class, "cow", ModLoader.getUniqueEntityId(), 0xffffff, 0xbbbbbb);
		EntityRegistry.registerGlobalEntityID(EntitySheepTFC.class, "sheep", ModLoader.getUniqueEntityId(), 0xffffff, 0xbbbbbb);
		EntityRegistry.registerGlobalEntityID(EntityBear.class, "bear", ModLoader.getUniqueEntityId(), 0xd1d003, 0x101010);
		EntityRegistry.registerGlobalEntityID(EntityChickenTFC.class, "chicken", ModLoader.getUniqueEntityId(), 0xffffff, 0xbbbbbb);
		EntityRegistry.registerGlobalEntityID(EntityPigTFC.class, "pig", ModLoader.getUniqueEntityId(), 0xffffff, 0xbbbbbb);
		EntityRegistry.registerGlobalEntityID(EntitySquidTFC.class, "squid", ModLoader.getUniqueEntityId(), 0xffffff, 0xbbbbbb);
		EntityRegistry.registerGlobalEntityID(EntityDeer.class, "deer", ModLoader.getUniqueEntityId(), 0xffffff, 0x105510);
		EntityRegistry.registerGlobalEntityID(EntityCustomMinecart.class, "minecart", ModLoader.getUniqueEntityId(), 0xffffff, 0x105510);
		EntityRegistry.registerGlobalEntityID(EntitySkeletonTFC.class, "skeleton", ModLoader.getUniqueEntityId(), 0xffffff, 0x105510);
		EntityRegistry.registerGlobalEntityID(EntityZombieTFC.class, "zombie", ModLoader.getUniqueEntityId(), 0xffffff, 0x105510);
		EntityRegistry.registerGlobalEntityID(EntitySpiderTFC.class, "spider", ModLoader.getUniqueEntityId(), 0xffffff, 0x105510);
		EntityRegistry.registerGlobalEntityID(EntitySlimeTFC.class, "slime", ModLoader.getUniqueEntityId(), 0xffffff, 0x105510);
		EntityRegistry.registerGlobalEntityID(EntitySilverfishTFC.class, "silverfish", ModLoader.getUniqueEntityId(), 0xffffff, 0x105510);
		EntityRegistry.registerGlobalEntityID(EntityGhastTFC.class, "ghast", ModLoader.getUniqueEntityId(), 0xffffff, 0x105510);
		EntityRegistry.registerGlobalEntityID(EntityCaveSpiderTFC.class, "cavespider", ModLoader.getUniqueEntityId(), 0xffffff, 0x105510);
		EntityRegistry.registerGlobalEntityID(EntityBlazeTFC.class, "blaze", ModLoader.getUniqueEntityId(), 0xffffff, 0x105510);
		EntityRegistry.registerGlobalEntityID(EntityEndermanTFC.class, "enderman", ModLoader.getUniqueEntityId(), 0xffffff, 0x105510);
		EntityRegistry.registerGlobalEntityID(EntityPigZombieTFC.class, "pigzombie", ModLoader.getUniqueEntityId(), 0xffffff, 0x105510);
		EntityRegistry.registerGlobalEntityID(EntityIronGolemTFC.class, "irongolem", ModLoader.getUniqueEntityId(), 0xffffff, 0x105510);
		EntityRegistry.registerGlobalEntityID(EntityCreeperTFC.class, "creeper", ModLoader.getUniqueEntityId(), 0xffffff, 0x105510);
		EntityRegistry.registerGlobalEntityID(EntityArrowTFC.class, "arrow", ModLoader.getUniqueEntityId());

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
		
		EntityRegistry.registerModEntity(EntityArrowTFC.class, "deerTFC", 25,TerraFirmaCraft.instance, 160, 5, true);
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
		
		MinecraftForge.setToolClass(TFCItems.StoneHammer, "hammer", 1);
		MinecraftForge.setToolClass(TFCItems.BismuthHammer, "hammer", 1);
		MinecraftForge.setToolClass(TFCItems.BismuthBronzeHammer, "hammer", 2);
		MinecraftForge.setToolClass(TFCItems.BlackBronzeHammer, "hammer", 2);
		MinecraftForge.setToolClass(TFCItems.BlackSteelHammer, "hammer", 5);
		MinecraftForge.setToolClass(TFCItems.BlueSteelHammer, "hammer", 6);
		MinecraftForge.setToolClass(TFCItems.BronzeHammer, "hammer", 2);
		MinecraftForge.setToolClass(TFCItems.CopperHammer, "hammer", 1);
		MinecraftForge.setToolClass(TFCItems.WroughtIronHammer, "hammer", 3);
		MinecraftForge.setToolClass(TFCItems.RedSteelHammer, "hammer", 6);
		MinecraftForge.setToolClass(TFCItems.RoseGoldHammer, "hammer", 2);
		MinecraftForge.setToolClass(TFCItems.SteelHammer, "hammer", 4);
		MinecraftForge.setToolClass(TFCItems.TinHammer, "hammer", 1);
		MinecraftForge.setToolClass(TFCItems.ZincHammer, "hammer", 1);
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
			return new ContainerTerraSluice(player.inventory, (TileEntitySluice) te, world, x, y, z);
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
			return new ContainerPlayer(player.inventory, false, player);
		}
		case 32:
		{
			return new ContainerFoodPrep(player.inventory, (TileEntityFoodPrep) te, world, x, y, z);
		}
		case 33:
		{
			return new ContainerQuern(player.inventory, (TileEntityQuern) te, world, x, y, z);
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
