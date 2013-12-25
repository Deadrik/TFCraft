package TFC;

import java.io.File;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.ModLoader;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
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
import TFC.TileEntities.TEBerryBush;
import TFC.TileEntities.TEBlastFurnace;
import TFC.TileEntities.TECrucible;
import TFC.TileEntities.TENestBox;
import TFC.TileEntities.TEStand;
import TFC.TileEntities.TileEntityAnvil;
import TFC.TileEntities.TileEntityBarrel;
import TFC.TileEntities.TileEntityBellows;
import TFC.TileEntities.TileEntityBloom;
import TFC.TileEntities.TileEntityChestTFC;
import TFC.TileEntities.TileEntityCrop;
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
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy
{

	public void registerRenderInformation() {
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
		GameRegistry.registerTileEntity(TileEntityCrop.class, "TileEntityCrop");

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

		EntityRegistry.registerGlobalEntityID(EntityWolfTFC.class, "Wolf", ModLoader.getUniqueEntityId(), 0xffffff, 0xaaaaaa);
		EntityRegistry.registerGlobalEntityID(EntityCowTFC.class, "Cow", ModLoader.getUniqueEntityId(), 0xffffff, 0xbbbbbb);
		EntityRegistry.registerGlobalEntityID(EntitySheepTFC.class, "Sheep", ModLoader.getUniqueEntityId(), 0xffffff, 0xbbbbbb);
		EntityRegistry.registerGlobalEntityID(EntityBear.class, "Bear", ModLoader.getUniqueEntityId(), 0xffffff, 0xbbbbbb);
		EntityRegistry.registerGlobalEntityID(EntityChickenTFC.class, "Chicken", ModLoader.getUniqueEntityId(), 0xffffff, 0xbbbbbb);
		EntityRegistry.registerGlobalEntityID(EntityPigTFC.class, "Pig", ModLoader.getUniqueEntityId(), 0xffffff, 0xbbbbbb);
		EntityRegistry.registerGlobalEntityID(EntitySquidTFC.class, "Squid", ModLoader.getUniqueEntityId(), 0xffffff, 0xbbbbbb);
		EntityRegistry.registerGlobalEntityID(EntityDeer.class, "Deer", ModLoader.getUniqueEntityId(), 0xffffff, 0x105510);
		EntityRegistry.registerGlobalEntityID(EntityHorseTFC.class, "Horse", ModLoader.getUniqueEntityId(), 0xffffff, 0x105510);
		EntityRegistry.registerGlobalEntityID(EntityCustomMinecart.class, "minecart", ModLoader.getUniqueEntityId());
		EntityRegistry.registerGlobalEntityID(EntitySkeletonTFC.class, "Skeleton", ModLoader.getUniqueEntityId(), 0xffffff, 0x105510);
		EntityRegistry.registerGlobalEntityID(EntityZombieTFC.class, "Zombie", ModLoader.getUniqueEntityId(), 0xffffff, 0x105510);
		EntityRegistry.registerGlobalEntityID(EntitySpiderTFC.class, "Spider", ModLoader.getUniqueEntityId(), 0xffffff, 0x105510);
		EntityRegistry.registerGlobalEntityID(EntitySlimeTFC.class, "Slime", ModLoader.getUniqueEntityId(), 0xffffff, 0x105510);
		EntityRegistry.registerGlobalEntityID(EntitySilverfishTFC.class, "Silverfish", ModLoader.getUniqueEntityId(), 0xffffff, 0x105510);
		EntityRegistry.registerGlobalEntityID(EntityGhastTFC.class, "Ghast", ModLoader.getUniqueEntityId(), 0xffffff, 0x105510);
		EntityRegistry.registerGlobalEntityID(EntityCaveSpiderTFC.class, "CaveSpider", ModLoader.getUniqueEntityId(), 0xffffff, 0x105510);
		EntityRegistry.registerGlobalEntityID(EntityBlazeTFC.class, "Blaze", ModLoader.getUniqueEntityId(), 0xffffff, 0x105510);
		EntityRegistry.registerGlobalEntityID(EntityEndermanTFC.class, "Enderman", ModLoader.getUniqueEntityId(), 0xffffff, 0x105510);
		EntityRegistry.registerGlobalEntityID(EntityPigZombieTFC.class, "PigZombie", ModLoader.getUniqueEntityId(), 0xffffff, 0x105510);
		EntityRegistry.registerGlobalEntityID(EntityIronGolemTFC.class, "irongolem", ModLoader.getUniqueEntityId(), 0xffffff, 0x105510);
		EntityRegistry.registerGlobalEntityID(EntityCreeperTFC.class, "Creeper", ModLoader.getUniqueEntityId(), 0xffffff, 0x105510);
		EntityRegistry.registerGlobalEntityID(EntityProjectileTFC.class, "arrow", ModLoader.getUniqueEntityId());
		EntityRegistry.registerGlobalEntityID(EntityStand.class, "stand", ModLoader.getUniqueEntityId());


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

	public void registerToolClasses() {
		//pickaxes
		MinecraftForge.setToolClass(TFCItems.BismuthBronzePick, "pickaxe", 2);
		MinecraftForge.setToolClass(TFCItems.BlackBronzePick, "pickaxe", 2);
		MinecraftForge.setToolClass(TFCItems.BlackSteelPick, "pickaxe", 5);
		MinecraftForge.setToolClass(TFCItems.BlueSteelPick, "pickaxe", 6);
		MinecraftForge.setToolClass(TFCItems.BronzePick, "pickaxe", 2);
		MinecraftForge.setToolClass(TFCItems.CopperPick, "pickaxe", 1);
		MinecraftForge.setToolClass(TFCItems.WroughtIronPick, "pickaxe", 3);
		MinecraftForge.setToolClass(TFCItems.RedSteelPick, "pickaxe", 6);
		MinecraftForge.setToolClass(TFCItems.SteelPick, "pickaxe", 4);
		//shovels
		MinecraftForge.setToolClass(TFCItems.IgInShovel, "shovel", 1);
		MinecraftForge.setToolClass(TFCItems.IgExShovel, "shovel", 1);
		MinecraftForge.setToolClass(TFCItems.SedShovel, "shovel", 1);
		MinecraftForge.setToolClass(TFCItems.MMShovel, "shovel", 1);
		MinecraftForge.setToolClass(TFCItems.BismuthBronzeShovel, "shovel", 2);
		MinecraftForge.setToolClass(TFCItems.BlackBronzeShovel, "shovel", 2);
		MinecraftForge.setToolClass(TFCItems.BlackSteelShovel, "shovel", 5);
		MinecraftForge.setToolClass(TFCItems.BlueSteelShovel, "shovel", 6);
		MinecraftForge.setToolClass(TFCItems.BronzeShovel, "shovel", 2);
		MinecraftForge.setToolClass(TFCItems.CopperShovel, "shovel", 1);
		MinecraftForge.setToolClass(TFCItems.WroughtIronShovel, "shovel", 3);
		MinecraftForge.setToolClass(TFCItems.RedSteelShovel, "shovel", 6);
		MinecraftForge.setToolClass(TFCItems.SteelShovel, "shovel", 4);
		//Axes
		MinecraftForge.setToolClass(TFCItems.IgInAxe, "axe", 1);
		MinecraftForge.setToolClass(TFCItems.IgExAxe, "axe", 1);
		MinecraftForge.setToolClass(TFCItems.SedAxe, "axe", 1);
		MinecraftForge.setToolClass(TFCItems.MMAxe, "axe", 1);
		MinecraftForge.setToolClass(TFCItems.BismuthBronzeAxe, "axe", 2);
		MinecraftForge.setToolClass(TFCItems.BlackBronzeAxe, "axe", 2);
		MinecraftForge.setToolClass(TFCItems.BlackSteelAxe, "axe", 5);
		MinecraftForge.setToolClass(TFCItems.BlueSteelAxe, "axe", 6);
		MinecraftForge.setToolClass(TFCItems.BronzeAxe, "axe", 2);
		MinecraftForge.setToolClass(TFCItems.CopperAxe, "axe", 1);
		MinecraftForge.setToolClass(TFCItems.WroughtIronAxe, "axe", 3);
		MinecraftForge.setToolClass(TFCItems.RedSteelAxe, "axe", 6);
		MinecraftForge.setToolClass(TFCItems.SteelAxe, "axe", 4);

		MinecraftForge.setToolClass(TFCItems.BismuthBronzeSaw, "axe", 2);
		MinecraftForge.setToolClass(TFCItems.BlackBronzeSaw, "axe", 2);
		MinecraftForge.setToolClass(TFCItems.BlackSteelSaw, "axe", 5);
		MinecraftForge.setToolClass(TFCItems.BlueSteelSaw, "axe", 6);
		MinecraftForge.setToolClass(TFCItems.BronzeSaw, "axe", 2);
		MinecraftForge.setToolClass(TFCItems.CopperSaw, "axe", 1);
		MinecraftForge.setToolClass(TFCItems.WroughtIronSaw, "axe", 3);
		MinecraftForge.setToolClass(TFCItems.RedSteelSaw, "axe", 6);
		MinecraftForge.setToolClass(TFCItems.SteelSaw, "axe", 4);

		MinecraftForge.setToolClass(TFCItems.StoneHammer, "hammer", 1);
		MinecraftForge.setToolClass(TFCItems.BismuthBronzeHammer, "hammer", 2);
		MinecraftForge.setToolClass(TFCItems.BlackBronzeHammer, "hammer", 2);
		MinecraftForge.setToolClass(TFCItems.BlackSteelHammer, "hammer", 5);
		MinecraftForge.setToolClass(TFCItems.BlueSteelHammer, "hammer", 6);
		MinecraftForge.setToolClass(TFCItems.BronzeHammer, "hammer", 2);
		MinecraftForge.setToolClass(TFCItems.CopperHammer, "hammer", 1);
		MinecraftForge.setToolClass(TFCItems.WroughtIronHammer, "hammer", 3);
		MinecraftForge.setToolClass(TFCItems.RedSteelHammer, "hammer", 6);
		MinecraftForge.setToolClass(TFCItems.SteelHammer, "hammer", 4);
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
		return MinecraftServer.getServer().getEntityWorld();
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

	public int getArmorRenderID(String name)
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

	public void registerGuiHandler() {
		NetworkRegistry.instance().registerGuiHandler(this, new GuiHandler());
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
}
