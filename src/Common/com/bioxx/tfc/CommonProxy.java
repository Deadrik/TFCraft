package com.bioxx.tfc;

import java.io.File;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;

import com.bioxx.tfc.Entities.EntityCustomMinecart;
import com.bioxx.tfc.Entities.EntityJavelin;
import com.bioxx.tfc.Entities.EntityProjectileTFC;
import com.bioxx.tfc.Entities.EntityStand;
import com.bioxx.tfc.Entities.Mobs.EntityBear;
import com.bioxx.tfc.Entities.Mobs.EntityBlazeTFC;
import com.bioxx.tfc.Entities.Mobs.EntityCaveSpiderTFC;
import com.bioxx.tfc.Entities.Mobs.EntityChickenTFC;
import com.bioxx.tfc.Entities.Mobs.EntityCowTFC;
import com.bioxx.tfc.Entities.Mobs.EntityCreeperTFC;
import com.bioxx.tfc.Entities.Mobs.EntityDeer;
import com.bioxx.tfc.Entities.Mobs.EntityEndermanTFC;
import com.bioxx.tfc.Entities.Mobs.EntityGhastTFC;
import com.bioxx.tfc.Entities.Mobs.EntityHorseTFC;
import com.bioxx.tfc.Entities.Mobs.EntityIronGolemTFC;
import com.bioxx.tfc.Entities.Mobs.EntityPheasantTFC;
import com.bioxx.tfc.Entities.Mobs.EntityPigTFC;
import com.bioxx.tfc.Entities.Mobs.EntityPigZombieTFC;
import com.bioxx.tfc.Entities.Mobs.EntitySheepTFC;
import com.bioxx.tfc.Entities.Mobs.EntitySilverfishTFC;
import com.bioxx.tfc.Entities.Mobs.EntitySkeletonTFC;
import com.bioxx.tfc.Entities.Mobs.EntitySlimeTFC;
import com.bioxx.tfc.Entities.Mobs.EntitySpiderTFC;
import com.bioxx.tfc.Entities.Mobs.EntitySquidTFC;
import com.bioxx.tfc.Entities.Mobs.EntityWolfTFC;
import com.bioxx.tfc.Entities.Mobs.EntityZombieTFC;
import com.bioxx.tfc.Handlers.GuiHandler;
import com.bioxx.tfc.Handlers.ServerTickHandler;
import com.bioxx.tfc.TileEntities.TEBarrel;
import com.bioxx.tfc.TileEntities.TEBerryBush;
import com.bioxx.tfc.TileEntities.TEBlastFurnace;
import com.bioxx.tfc.TileEntities.TECrop;
import com.bioxx.tfc.TileEntities.TECrucible;
import com.bioxx.tfc.TileEntities.TEFoodPrep;
import com.bioxx.tfc.TileEntities.TEForge;
import com.bioxx.tfc.TileEntities.TEFruitLeaves;
import com.bioxx.tfc.TileEntities.TEMetalSheet;
import com.bioxx.tfc.TileEntities.TENestBox;
import com.bioxx.tfc.TileEntities.TEOre;
import com.bioxx.tfc.TileEntities.TESeaWeed;
import com.bioxx.tfc.TileEntities.TEStand;
import com.bioxx.tfc.TileEntities.TEWorldItem;
import com.bioxx.tfc.TileEntities.TileEntityAnvil;
import com.bioxx.tfc.TileEntities.TileEntityBellows;
import com.bioxx.tfc.TileEntities.TileEntityBloom;
import com.bioxx.tfc.TileEntities.TileEntityChestTFC;
import com.bioxx.tfc.TileEntities.TileEntityDetailed;
import com.bioxx.tfc.TileEntities.TileEntityEarlyBloomery;
import com.bioxx.tfc.TileEntities.TileEntityFarmland;
import com.bioxx.tfc.TileEntities.TileEntityFenceGate;
import com.bioxx.tfc.TileEntities.TileEntityFirepit;
import com.bioxx.tfc.TileEntities.TileEntityFruitTreeWood;
import com.bioxx.tfc.TileEntities.TileEntityIngotPile;
import com.bioxx.tfc.TileEntities.TileEntityLogPile;
import com.bioxx.tfc.TileEntities.TileEntityPartial;
import com.bioxx.tfc.TileEntities.TileEntityPottery;
import com.bioxx.tfc.TileEntities.TileEntityQuern;
import com.bioxx.tfc.TileEntities.TileEntitySapling;
import com.bioxx.tfc.TileEntities.TileEntitySluice;
import com.bioxx.tfc.TileEntities.TileEntitySpawnMeter;
import com.bioxx.tfc.TileEntities.TileEntityToolRack;
import com.bioxx.tfc.TileEntities.TileEntityWoodConstruct;
import com.bioxx.tfc.TileEntities.TileEntityWorkbench;
import com.bioxx.tfc.WorldGen.TFCProvider;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy
{
	public void registerFluidIcons(Fluid f, Block b)
	{
		// NOOP on server
	}

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
		GameRegistry.registerTileEntity(TileEntityLogPile.class, TileEntityName.LogPile);
		GameRegistry.registerTileEntity(TileEntityWorkbench.class, TileEntityName.Workbench);
		GameRegistry.registerTileEntity(TEForge.class, TileEntityName.Forge);
		GameRegistry.registerTileEntity(TEBlastFurnace.class, TileEntityName.BlastFurnace);
		GameRegistry.registerTileEntity(TileEntityEarlyBloomery.class, TileEntityName.Bloomery);
		GameRegistry.registerTileEntity(TileEntitySluice.class, TileEntityName.Sluice);
		GameRegistry.registerTileEntity(TileEntityFarmland.class, TileEntityName.Farmland);
		GameRegistry.registerTileEntity(TECrop.class, TileEntityName.Crop);
		GameRegistry.registerTileEntity(TileEntityFruitTreeWood.class, TileEntityName.FruitTreeWood);
		GameRegistry.registerTileEntity(TileEntityPartial.class, TileEntityName.Partial);
		GameRegistry.registerTileEntity(TileEntityDetailed.class, TileEntityName.Detailed);
		GameRegistry.registerTileEntity(TileEntitySpawnMeter.class, TileEntityName.SpawnMeter);
		GameRegistry.registerTileEntity(TileEntitySapling.class, TileEntityName.Sapling);
		GameRegistry.registerTileEntity(TileEntityWoodConstruct.class, TileEntityName.WoodConstruct);
		GameRegistry.registerTileEntity(TEBarrel.class, TileEntityName.Barrel);
		GameRegistry.registerTileEntity(TileEntityFenceGate.class, TileEntityName.FenceGate);
		GameRegistry.registerTileEntity(TileEntityBloom.class, TileEntityName.Bloom);
		GameRegistry.registerTileEntity(TECrucible.class, TileEntityName.Crucible);
		GameRegistry.registerTileEntity(TENestBox.class, TileEntityName.NestBox);
		GameRegistry.registerTileEntity(TEStand.class, TileEntityName.Stand);
		GameRegistry.registerTileEntity(TEBerryBush.class, TileEntityName.BerryBush);
		GameRegistry.registerTileEntity(TEFruitLeaves.class, TileEntityName.FruitLeaves);
		GameRegistry.registerTileEntity(TEMetalSheet.class, TileEntityName.MetalSheet);
		GameRegistry.registerTileEntity(TEOre.class, TileEntityName.Ore);

		if(b)
		{
			GameRegistry.registerTileEntity(TileEntityFirepit.class, TileEntityName.Firepit);
			GameRegistry.registerTileEntity(TESeaWeed.class, TileEntityName.SeaWeed);
			GameRegistry.registerTileEntity(TileEntityIngotPile.class, TileEntityName.IngotPile);
			GameRegistry.registerTileEntity(TileEntityPottery.class, TileEntityName.Pottery);
			GameRegistry.registerTileEntity(TileEntityChestTFC.class, TileEntityName.Chest);
			GameRegistry.registerTileEntity(TEFoodPrep.class, TileEntityName.FoodPrep);
			GameRegistry.registerTileEntity(TileEntityBellows.class, TileEntityName.Bellows);
			GameRegistry.registerTileEntity(TileEntityToolRack.class, TileEntityName.ToolRack);
			GameRegistry.registerTileEntity(TileEntityAnvil.class, TileEntityName.Anvil);
			GameRegistry.registerTileEntity(TEWorldItem.class, TileEntityName.WorldItem);
			GameRegistry.registerTileEntity(TileEntityQuern.class, TileEntityName.Quern);
		}

		EntityRegistry.registerGlobalEntityID(EntitySquidTFC.class, "squidTFC", EntityRegistry.findGlobalUniqueEntityId(), 0xffffff, 0xbbbbbb);
		EntityRegistry.registerGlobalEntityID(EntityCowTFC.class, "cowTFC", EntityRegistry.findGlobalUniqueEntityId(), 0xffffff, 0xbbbbbb);
		EntityRegistry.registerGlobalEntityID(EntityWolfTFC.class, "wolfTFC", EntityRegistry.findGlobalUniqueEntityId(), 0xffffff, 0xaaaaaa);
		EntityRegistry.registerGlobalEntityID(EntityBear.class, "bearTFC", EntityRegistry.findGlobalUniqueEntityId(), 0xffffff, 0xbbbbbb);
		EntityRegistry.registerGlobalEntityID(EntityChickenTFC.class, "chickenTFC", EntityRegistry.findGlobalUniqueEntityId(), 0xffffff, 0xbbbbbb);
		EntityRegistry.registerGlobalEntityID(EntityPigTFC.class, "pigTFC", EntityRegistry.findGlobalUniqueEntityId(), 0xffffff, 0xbbbbbb);
		EntityRegistry.registerGlobalEntityID(EntityDeer.class, "deerTFC", EntityRegistry.findGlobalUniqueEntityId(), 0xffffff, 0x105510);

		EntityRegistry.registerGlobalEntityID(EntitySkeletonTFC.class, "skeletonTFC", EntityRegistry.findGlobalUniqueEntityId(), 0xffffff, 0x105510);
		EntityRegistry.registerGlobalEntityID(EntityZombieTFC.class, "zombieTFC", EntityRegistry.findGlobalUniqueEntityId(), 0xffffff, 0x105510);
		EntityRegistry.registerGlobalEntityID(EntitySpiderTFC.class, "spiderTFC", EntityRegistry.findGlobalUniqueEntityId(), 0xffffff, 0x105510);
		EntityRegistry.registerGlobalEntityID(EntitySlimeTFC.class, "slimeTFC", EntityRegistry.findGlobalUniqueEntityId(), 0xffffff, 0x105510);
		EntityRegistry.registerGlobalEntityID(EntitySilverfishTFC.class, "silverfishTFC", EntityRegistry.findGlobalUniqueEntityId(), 0xffffff, 0x105510);
		EntityRegistry.registerGlobalEntityID(EntityGhastTFC.class, "ghastTFC", EntityRegistry.findGlobalUniqueEntityId(), 0xffffff, 0x105510);
		EntityRegistry.registerGlobalEntityID(EntityCaveSpiderTFC.class, "caveSpiderTFC", EntityRegistry.findGlobalUniqueEntityId(), 0xffffff, 0x105510);
		EntityRegistry.registerGlobalEntityID(EntityBlazeTFC.class, "blazeTFC", EntityRegistry.findGlobalUniqueEntityId(), 0xffffff, 0x105510);
		EntityRegistry.registerGlobalEntityID(EntityEndermanTFC.class, "endermanTFC", EntityRegistry.findGlobalUniqueEntityId(), 0xffffff, 0x105510);
		EntityRegistry.registerGlobalEntityID(EntityPigZombieTFC.class, "pigZombieTFC", EntityRegistry.findGlobalUniqueEntityId(), 0xffffff, 0x105510);
		EntityRegistry.registerGlobalEntityID(EntityIronGolemTFC.class, "irongolemTFC", EntityRegistry.findGlobalUniqueEntityId(), 0xffffff, 0x105510);
		EntityRegistry.registerGlobalEntityID(EntityCreeperTFC.class, "creeperTFC", EntityRegistry.findGlobalUniqueEntityId(), 0xffffff, 0x105510);

		EntityRegistry.registerGlobalEntityID(EntitySheepTFC.class, "sheepTFC", EntityRegistry.findGlobalUniqueEntityId(), 0xffffff, 0xbbbbbb);
		EntityRegistry.registerGlobalEntityID(EntityPheasantTFC.class, "pheasantTFC", EntityRegistry.findGlobalUniqueEntityId(), 0xffffff, 0xbbbbbb);

		EntityRegistry.registerGlobalEntityID(EntityHorseTFC.class, "horseTFC", EntityRegistry.findGlobalUniqueEntityId(), 0xffffff, 0x105510);

		EntityRegistry.registerGlobalEntityID(EntityCustomMinecart.class, "minecartTFC", EntityRegistry.findGlobalUniqueEntityId());
		EntityRegistry.registerGlobalEntityID(EntityProjectileTFC.class, "arrowTFC", EntityRegistry.findGlobalUniqueEntityId());
		EntityRegistry.registerGlobalEntityID(EntityStand.class, "standTFC", EntityRegistry.findGlobalUniqueEntityId());

		EntityRegistry.registerModEntity(EntityJavelin.class, "javelin", 1, TerraFirmaCraft.instance, 160, 5, true);
		EntityRegistry.registerModEntity(EntitySquidTFC.class, "squidTFC", 2, TerraFirmaCraft.instance, 160, 5, true);
		EntityRegistry.registerModEntity(EntityCowTFC.class, "cowTFC", 6, TerraFirmaCraft.instance, 160, 5, true);
		EntityRegistry.registerModEntity(EntityWolfTFC.class, "wolfTFC", 7, TerraFirmaCraft.instance, 160, 5, true);
		EntityRegistry.registerModEntity(EntityBear.class, "bearTFC", 8, TerraFirmaCraft.instance, 160, 5, true);
		EntityRegistry.registerModEntity(EntityChickenTFC.class, "chickenTFC", 9, TerraFirmaCraft.instance, 160, 5, true);
		EntityRegistry.registerModEntity(EntityPigTFC.class, "pigTFC", 10, TerraFirmaCraft.instance, 160, 5, true);
		EntityRegistry.registerModEntity(EntityDeer.class, "deerTFC", 11, TerraFirmaCraft.instance, 160, 5, true);
		EntityRegistry.registerModEntity(EntityCustomMinecart.class, "minecartTFC", 12, TerraFirmaCraft.instance, 160, 5, true);
		EntityRegistry.registerModEntity(EntitySkeletonTFC.class, "skeletonTFC", 13, TerraFirmaCraft.instance, 160, 5, true);
		EntityRegistry.registerModEntity(EntityZombieTFC.class, "zombieTFC", 14, TerraFirmaCraft.instance, 160, 5, true);
		EntityRegistry.registerModEntity(EntitySpiderTFC.class, "spiderTFC", 15, TerraFirmaCraft.instance, 160, 5, true);
		EntityRegistry.registerModEntity(EntitySlimeTFC.class, "slimeTFC", 16, TerraFirmaCraft.instance, 160, 5, true);
		EntityRegistry.registerModEntity(EntitySilverfishTFC.class, "silverFishTFC", 17, TerraFirmaCraft.instance, 160, 5, true);
		EntityRegistry.registerModEntity(EntityGhastTFC.class, "ghastTFC", 18, TerraFirmaCraft.instance, 160, 5, true);
		EntityRegistry.registerModEntity(EntityCaveSpiderTFC.class, "caveSpiderTFC", 19, TerraFirmaCraft.instance, 160, 5, true);
		EntityRegistry.registerModEntity(EntityBlazeTFC.class, "blazeTFC", 20, TerraFirmaCraft.instance, 160, 5, true);
		EntityRegistry.registerModEntity(EntityEndermanTFC.class, "endermanTFC", 21, TerraFirmaCraft.instance, 160, 5, true);
		EntityRegistry.registerModEntity(EntityPigZombieTFC.class, "pigZombieTFC", 22, TerraFirmaCraft.instance, 160, 5, true);
		EntityRegistry.registerModEntity(EntityIronGolemTFC.class, "irongolemTFC", 23, TerraFirmaCraft.instance, 160, 5, true);
		EntityRegistry.registerModEntity(EntityCreeperTFC.class, "creeperTFC", 24, TerraFirmaCraft.instance, 160, 5, true);
		EntityRegistry.registerModEntity(EntityStand.class, "standTFC", 25, TerraFirmaCraft.instance, 160, 5, true);
		EntityRegistry.registerModEntity(EntityPheasantTFC.class, "PheasantTFC", 26, TerraFirmaCraft.instance, 160, 5, true);

		//EntityRegistry.registerModEntity(EntityArrowTFC.class, "arrowTFC", 27, TerraFirmaCraft.instance, 160, 5, true);
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
}
