package com.bioxx.tfc;

import java.io.File;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import com.bioxx.tfc.Core.FluidBaseTFC;
import com.bioxx.tfc.Entities.EntityBarrel;
import com.bioxx.tfc.Entities.EntityCustomMinecart;
import com.bioxx.tfc.Entities.EntityFallingBlockTFC;
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
import com.bioxx.tfc.Entities.Mobs.EntityFishTFC;
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
import com.bioxx.tfc.Items.ItemBlocks.ItemOilLamp;
import com.bioxx.tfc.TileEntities.TEAnvil;
import com.bioxx.tfc.TileEntities.TEBarrel;
import com.bioxx.tfc.TileEntities.TEBellows;
import com.bioxx.tfc.TileEntities.TEBerryBush;
import com.bioxx.tfc.TileEntities.TEBlastFurnace;
import com.bioxx.tfc.TileEntities.TEBloomery;
import com.bioxx.tfc.TileEntities.TEChest;
import com.bioxx.tfc.TileEntities.TECrop;
import com.bioxx.tfc.TileEntities.TECrucible;
import com.bioxx.tfc.TileEntities.TEDetailed;
import com.bioxx.tfc.TileEntities.TEFarmland;
import com.bioxx.tfc.TileEntities.TEFirepit;
import com.bioxx.tfc.TileEntities.TEFoodPrep;
import com.bioxx.tfc.TileEntities.TEForge;
import com.bioxx.tfc.TileEntities.TEFruitLeaves;
import com.bioxx.tfc.TileEntities.TEGrill;
import com.bioxx.tfc.TileEntities.TEIngotPile;
import com.bioxx.tfc.TileEntities.TELeatherRack;
import com.bioxx.tfc.TileEntities.TELightEmitter;
import com.bioxx.tfc.TileEntities.TELogPile;
import com.bioxx.tfc.TileEntities.TELoom;
import com.bioxx.tfc.TileEntities.TEMetalSheet;
import com.bioxx.tfc.TileEntities.TEMetalTrapDoor;
import com.bioxx.tfc.TileEntities.TENestBox;
import com.bioxx.tfc.TileEntities.TEOilLamp;
import com.bioxx.tfc.TileEntities.TEOre;
import com.bioxx.tfc.TileEntities.TEPartial;
import com.bioxx.tfc.TileEntities.TEPottery;
import com.bioxx.tfc.TileEntities.TESluice;
import com.bioxx.tfc.TileEntities.TESmokeRack;
import com.bioxx.tfc.TileEntities.TEStand;
import com.bioxx.tfc.TileEntities.TEVessel;
import com.bioxx.tfc.TileEntities.TEWaterPlant;
import com.bioxx.tfc.TileEntities.TEWorldItem;
import com.bioxx.tfc.TileEntities.TileEntityBloom;
import com.bioxx.tfc.TileEntities.TileEntityFenceGate;
import com.bioxx.tfc.TileEntities.TileEntityFruitTreeWood;
import com.bioxx.tfc.TileEntities.TileEntityQuern;
import com.bioxx.tfc.TileEntities.TileEntitySapling;
import com.bioxx.tfc.TileEntities.TileEntitySpawnMeter;
import com.bioxx.tfc.TileEntities.TileEntityToolRack;
import com.bioxx.tfc.TileEntities.TileEntityWoodConstruct;
import com.bioxx.tfc.TileEntities.TileEntityWorkbench;
import com.bioxx.tfc.WorldGen.TFCProvider;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCFluids;
import com.bioxx.tfc.api.TFCItems;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy
{
	public void registerFluidIcons()
	{

	}

	public void registerRenderInformation()
	{
		// NOOP on server
	}

	public void registerBiomeEventHandler()
	{
		// NOOP on server
	}

	public void registerPlayerRenderEventHandler()
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
		GameRegistry.registerTileEntity(TELogPile.class, "TerraLogPile");
		GameRegistry.registerTileEntity(TileEntityWorkbench.class, "TerraWorkbench");
		GameRegistry.registerTileEntity(TEForge.class, "TerraForge");
		GameRegistry.registerTileEntity(TEBlastFurnace.class, "TerraBloomery");
		GameRegistry.registerTileEntity(TEBloomery.class, "TerraEarlyBloomery");
		GameRegistry.registerTileEntity(TESluice.class, "TerraSluice");
		GameRegistry.registerTileEntity(TEFarmland.class, "TileEntityFarmland");
		GameRegistry.registerTileEntity(TECrop.class, "TileEntityCrop");
		GameRegistry.registerTileEntity(TileEntityFruitTreeWood.class, "FruitTreeWood");
		GameRegistry.registerTileEntity(TEPartial.class, "Partial");
		GameRegistry.registerTileEntity(TEDetailed.class, "Detailed");
		GameRegistry.registerTileEntity(TileEntitySpawnMeter.class, "SpawnMeter");
		GameRegistry.registerTileEntity(TileEntitySapling.class, "Sapling");
		GameRegistry.registerTileEntity(TileEntityWoodConstruct.class, "WoodConstruct");
		GameRegistry.registerTileEntity(TEBarrel.class, "Barrel");
		GameRegistry.registerTileEntity(TileEntityFenceGate.class, "FenceGate");
		GameRegistry.registerTileEntity(TileEntityBloom.class, "IronBloom");
		GameRegistry.registerTileEntity(TECrucible.class, "Crucible");
		GameRegistry.registerTileEntity(TENestBox.class, "Nest Box");
		GameRegistry.registerTileEntity(TEStand.class, "Armour Stand");
		GameRegistry.registerTileEntity(TEBerryBush.class, "Berry Bush");
		GameRegistry.registerTileEntity(TEFruitLeaves.class, "Fruit Leaves");
		GameRegistry.registerTileEntity(TEMetalSheet.class, "Metal Sheet");
		GameRegistry.registerTileEntity(TEOre.class, "ore");
		GameRegistry.registerTileEntity(TELeatherRack.class, "leatherRack");
		GameRegistry.registerTileEntity(TEMetalTrapDoor.class, "MetalTrapDoor");
		GameRegistry.registerTileEntity(TEWaterPlant.class, "Sea Weed");
		GameRegistry.registerTileEntity(TEVessel.class, "Vessel");
		GameRegistry.registerTileEntity(TELightEmitter.class, "LightEmitter");
		GameRegistry.registerTileEntity(TESmokeRack.class, "Smoke Rack");
		GameRegistry.registerTileEntity(TEOilLamp.class, "Oil Lamp");
		if(b)
		{
			GameRegistry.registerTileEntity(TEFirepit.class, "TerraFirepit");
			GameRegistry.registerTileEntity(TEIngotPile.class, "ingotPile");
			GameRegistry.registerTileEntity(TEPottery.class, "Pottery");
			GameRegistry.registerTileEntity(TEChest.class, "chest");
			GameRegistry.registerTileEntity(TEFoodPrep.class, "FoodPrep");
			GameRegistry.registerTileEntity(TEBellows.class, "Bellows");
			GameRegistry.registerTileEntity(TileEntityToolRack.class, "ToolRack");
			GameRegistry.registerTileEntity(TEAnvil.class, "TerraAnvil");
			GameRegistry.registerTileEntity(TEWorldItem.class, "worldItem");
			GameRegistry.registerTileEntity(TileEntityQuern.class, "Quern");
			GameRegistry.registerTileEntity(TELoom.class, "Loom");
			GameRegistry.registerTileEntity(TEGrill.class, "grill");
		}

		EntityRegistry.registerGlobalEntityID(EntitySquidTFC.class, "squidTFC", EntityRegistry.findGlobalUniqueEntityId(), 0x3c5466, 0x260026);
		EntityRegistry.registerGlobalEntityID(EntityFishTFC.class, "fishTFC", EntityRegistry.findGlobalUniqueEntityId(), 0x535231, 0x260026);
		EntityRegistry.registerGlobalEntityID(EntityCowTFC.class, "cowTFC", EntityRegistry.findGlobalUniqueEntityId(), 0x3d2f23, 0x260026);
		EntityRegistry.registerGlobalEntityID(EntityWolfTFC.class, "wolfTFC", EntityRegistry.findGlobalUniqueEntityId(), 0x938f8c, 0x260026);
		EntityRegistry.registerGlobalEntityID(EntityBear.class, "bearTFC", EntityRegistry.findGlobalUniqueEntityId(), 0x5c4b3b, 0x260026);
		EntityRegistry.registerGlobalEntityID(EntityChickenTFC.class, "chickenTFC", EntityRegistry.findGlobalUniqueEntityId(), 0xf3f45e, 0x260026);
		EntityRegistry.registerGlobalEntityID(EntityPigTFC.class, "pigTFC", EntityRegistry.findGlobalUniqueEntityId(), 0xe78786, 0x260026);
		EntityRegistry.registerGlobalEntityID(EntityDeer.class, "deerTFC", EntityRegistry.findGlobalUniqueEntityId(), 0x7c624c, 0x260026);

		EntityRegistry.registerGlobalEntityID(EntitySkeletonTFC.class, "skeletonTFC", EntityRegistry.findGlobalUniqueEntityId(), 0x979797, 0x260026);
		EntityRegistry.registerGlobalEntityID(EntityZombieTFC.class, "zombieTFC", EntityRegistry.findGlobalUniqueEntityId(), 0x426a33, 0x260026);
		EntityRegistry.registerGlobalEntityID(EntitySpiderTFC.class, "spiderTFC", EntityRegistry.findGlobalUniqueEntityId(), 0x322b24, 0x260026);
		EntityRegistry.registerGlobalEntityID(EntitySlimeTFC.class, "slimeTFC", EntityRegistry.findGlobalUniqueEntityId(), 0x6eb35c, 0x260026);
		EntityRegistry.registerGlobalEntityID(EntitySilverfishTFC.class, "silverfishTFC", EntityRegistry.findGlobalUniqueEntityId(), 0x858887, 0x260026);
		EntityRegistry.registerGlobalEntityID(EntityGhastTFC.class, "ghastTFC", EntityRegistry.findGlobalUniqueEntityId(), 0xebebeb, 0x260026);
		EntityRegistry.registerGlobalEntityID(EntityCaveSpiderTFC.class, "caveSpiderTFC", EntityRegistry.findGlobalUniqueEntityId(), 0x123236, 0x260026);
		EntityRegistry.registerGlobalEntityID(EntityBlazeTFC.class, "blazeTFC", EntityRegistry.findGlobalUniqueEntityId(), 0xad6d0b, 0x260026);
		EntityRegistry.registerGlobalEntityID(EntityEndermanTFC.class, "endermanTFC", EntityRegistry.findGlobalUniqueEntityId(), 0x0d0d0d, 0x260026);
		EntityRegistry.registerGlobalEntityID(EntityPigZombieTFC.class, "pigZombieTFC", EntityRegistry.findGlobalUniqueEntityId(), 0xb6735f, 0x260026);
		EntityRegistry.registerGlobalEntityID(EntityIronGolemTFC.class, "irongolemTFC", EntityRegistry.findGlobalUniqueEntityId(), 0xbfb99a, 0x260026);
		EntityRegistry.registerGlobalEntityID(EntityCreeperTFC.class, "creeperTFC", EntityRegistry.findGlobalUniqueEntityId(), 0x66c55c, 0x260026);

		EntityRegistry.registerGlobalEntityID(EntitySheepTFC.class, "sheepTFC", EntityRegistry.findGlobalUniqueEntityId(), 0xcdbfb4, 0x260026);
		EntityRegistry.registerGlobalEntityID(EntityPheasantTFC.class, "pheasantTFC", EntityRegistry.findGlobalUniqueEntityId(), 0x822c1c, 0x260026);

		EntityRegistry.registerGlobalEntityID(EntityHorseTFC.class, "horseTFC", EntityRegistry.findGlobalUniqueEntityId(), 0x966936, 0x260026);

		EntityRegistry.registerGlobalEntityID(EntityCustomMinecart.class, "minecartTFC", EntityRegistry.findGlobalUniqueEntityId());
		EntityRegistry.registerGlobalEntityID(EntityProjectileTFC.class, "arrowTFC", EntityRegistry.findGlobalUniqueEntityId());
		EntityRegistry.registerGlobalEntityID(EntityStand.class, "standTFC", EntityRegistry.findGlobalUniqueEntityId());

		EntityRegistry.registerGlobalEntityID(EntityFallingBlockTFC.class, "fallingBlock", EntityRegistry.findGlobalUniqueEntityId());
		EntityRegistry.registerGlobalEntityID(EntityBarrel.class, "barrel", EntityRegistry.findGlobalUniqueEntityId());

		EntityRegistry.registerModEntity(EntityJavelin.class, "javelin", 1, TerraFirmaCraft.instance, 64, 5, true);
		EntityRegistry.registerModEntity(EntitySquidTFC.class, "squidTFC", 2, TerraFirmaCraft.instance, 64, 5, true);
		EntityRegistry.registerModEntity(EntityCowTFC.class, "cowTFC", 6, TerraFirmaCraft.instance, 160, 5, true);
		EntityRegistry.registerModEntity(EntityWolfTFC.class, "wolfTFC", 7, TerraFirmaCraft.instance, 160, 5, true);
		EntityRegistry.registerModEntity(EntityBear.class, "bearTFC", 8, TerraFirmaCraft.instance, 160, 5, true);
		EntityRegistry.registerModEntity(EntityChickenTFC.class, "chickenTFC", 9, TerraFirmaCraft.instance, 160, 5, true);
		EntityRegistry.registerModEntity(EntityPigTFC.class, "pigTFC", 10, TerraFirmaCraft.instance, 160, 5, true);
		EntityRegistry.registerModEntity(EntityDeer.class, "deerTFC", 11, TerraFirmaCraft.instance, 160, 5, true);
		EntityRegistry.registerModEntity(EntityCustomMinecart.class, "minecartTFC", 12, TerraFirmaCraft.instance, 80, 5, true);
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
		EntityRegistry.registerModEntity(EntityStand.class, "standTFC", 25, TerraFirmaCraft.instance, 64, 20, false);
		EntityRegistry.registerModEntity(EntityPheasantTFC.class, "PheasantTFC", 26, TerraFirmaCraft.instance, 160, 5, true);
		EntityRegistry.registerModEntity(EntityFishTFC.class, "fishTFC", 27, TerraFirmaCraft.instance, 64, 5, true);
		EntityRegistry.registerModEntity(EntityFallingBlockTFC.class, "fallingBlock", 28, TerraFirmaCraft.instance, 160, 20, true);
		EntityRegistry.registerModEntity(EntityBarrel.class, "barrel", 29, TerraFirmaCraft.instance, 64, 20, true);

		/*Function<EntitySpawnMessage, Entity> spawnFunction = new Function<EntitySpawnMessage, Entity>()
				{
			@Override
			public Entity apply(EntitySpawnMessage input) {
				return null;
			}};


			EntityRegistry.instance().lookupModSpawn(EntityFallingBlockTFC.class, false).setCustomSpawning(spawnFunction, false);
		 */
		//EntityRegistry.registerModEntity(EntityArrowTFC.class, "arrowTFC", 27, TerraFirmaCraft.instance, 160, 5, true);
	}

	public void registerFluids()
	{
		TFCFluids.SALTWATER = new FluidBaseTFC("saltwater").setBaseColor(0x354d35);
		TFCFluids.FRESHWATER = new FluidBaseTFC("freshwater").setBaseColor(0x354d35);
		TFCFluids.HOTWATER = new FluidBaseTFC("hotwater").setBaseColor(0x1f5099).setTemperature(372/*Kelvin*/);
		TFCFluids.LAVA = new FluidBaseTFC("lavatfc").setLuminosity(15).setDensity(3000).setViscosity(6000).setTemperature(1300).setUnlocalizedName(Blocks.lava.getUnlocalizedName());
		TFCFluids.RUM = new FluidBaseTFC("rum").setBaseColor(0x6e0123);
		TFCFluids.BEER = new FluidBaseTFC("beer").setBaseColor(0xc39e37);
		TFCFluids.RYEWHISKEY = new FluidBaseTFC("ryewhiskey").setBaseColor(0xc77d51);
		TFCFluids.WHISKEY = new FluidBaseTFC("whiskey").setBaseColor(0x583719);
		TFCFluids.CORNWHISKEY = new FluidBaseTFC("cornwhiskey").setBaseColor(0xd9c7b7);
		TFCFluids.SAKE = new FluidBaseTFC("sake").setBaseColor(0xb7d9bc);
		TFCFluids.VODKA = new FluidBaseTFC("vodka").setBaseColor(0xdcdcdc);
		TFCFluids.CIDER = new FluidBaseTFC("cider").setBaseColor(0xb0ae32);
		TFCFluids.TANNIN = new FluidBaseTFC("tannin").setBaseColor(0x63594e);
		TFCFluids.VINEGAR = new FluidBaseTFC("vinegar").setBaseColor(0xc7c2aa);
		TFCFluids.BRINE = new FluidBaseTFC("brine").setBaseColor(0xdcd3c9);
		TFCFluids.LIMEWATER = new FluidBaseTFC("limewater").setBaseColor(0xb4b4b4);
		TFCFluids.MILK = new FluidBaseTFC("milk").setBaseColor(0xffffff);
		TFCFluids.MILKCURDLED = new FluidBaseTFC("milkcurdled").setBaseColor(0xfffbe8);
		TFCFluids.MILKVINEGAR = new FluidBaseTFC("milkvinegar").setBaseColor(0xfffbe8);
		TFCFluids.OLIVEOIL = new FluidBaseTFC("oliveoil").setBaseColor(0x44B510);

		FluidRegistry.registerFluid(TFCFluids.LAVA);
		FluidRegistry.registerFluid(TFCFluids.SALTWATER);
		FluidRegistry.registerFluid(TFCFluids.FRESHWATER);
		FluidRegistry.registerFluid(TFCFluids.HOTWATER);
		FluidRegistry.registerFluid(TFCFluids.RUM);
		FluidRegistry.registerFluid(TFCFluids.BEER);
		FluidRegistry.registerFluid(TFCFluids.RYEWHISKEY);
		FluidRegistry.registerFluid(TFCFluids.CORNWHISKEY);
		FluidRegistry.registerFluid(TFCFluids.WHISKEY);
		FluidRegistry.registerFluid(TFCFluids.SAKE);
		FluidRegistry.registerFluid(TFCFluids.VODKA);
		FluidRegistry.registerFluid(TFCFluids.CIDER);
		FluidRegistry.registerFluid(TFCFluids.TANNIN);
		FluidRegistry.registerFluid(TFCFluids.VINEGAR);
		FluidRegistry.registerFluid(TFCFluids.BRINE);
		FluidRegistry.registerFluid(TFCFluids.LIMEWATER);
		FluidRegistry.registerFluid(TFCFluids.MILK);
		FluidRegistry.registerFluid(TFCFluids.MILKCURDLED);
		FluidRegistry.registerFluid(TFCFluids.MILKVINEGAR);
		FluidRegistry.registerFluid(TFCFluids.OLIVEOIL);
	}

	public void setupFluids()
	{
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluid(TFCFluids.LAVA.getName()), new ItemStack(TFCItems.BlueSteelBucketLava), new ItemStack(TFCItems.BlueSteelBucketEmpty));
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluid(TFCFluids.FRESHWATER.getName()), new ItemStack(TFCItems.RedSteelBucketWater), new ItemStack(TFCItems.RedSteelBucketEmpty));
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluid(TFCFluids.SALTWATER.getName()), new ItemStack(TFCItems.RedSteelBucketSaltWater), new ItemStack(TFCItems.RedSteelBucketEmpty));
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluid(TFCFluids.FRESHWATER.getName()), new ItemStack(TFCItems.WoodenBucketWater), new ItemStack(TFCItems.WoodenBucketEmpty));
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluid(TFCFluids.SALTWATER.getName()), new ItemStack(TFCItems.WoodenBucketSaltWater), new ItemStack(TFCItems.WoodenBucketEmpty));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCFluids.FRESHWATER, 1000), new ItemStack(TFCItems.PotteryJug, 1, 2), new ItemStack(TFCItems.PotteryJug,1, 1));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCFluids.RUM, 250), new ItemStack(TFCItems.Rum), new ItemStack(TFCItems.GlassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCFluids.BEER, 250), new ItemStack(TFCItems.Beer), new ItemStack(TFCItems.GlassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCFluids.RYEWHISKEY, 250), new ItemStack(TFCItems.RyeWhiskey), new ItemStack(TFCItems.GlassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCFluids.WHISKEY, 250), new ItemStack(TFCItems.Whiskey), new ItemStack(TFCItems.GlassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCFluids.CORNWHISKEY, 250), new ItemStack(TFCItems.CornWhiskey), new ItemStack(TFCItems.GlassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCFluids.SAKE, 250), new ItemStack(TFCItems.Sake), new ItemStack(TFCItems.GlassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCFluids.CIDER, 250), new ItemStack(TFCItems.Cider), new ItemStack(TFCItems.GlassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCFluids.VODKA, 250), new ItemStack(TFCItems.Vodka), new ItemStack(TFCItems.GlassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCFluids.MILK, 1000), new ItemStack(TFCItems.WoodenBucketMilk), new ItemStack(TFCItems.WoodenBucketEmpty));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCFluids.VINEGAR, 1000), new ItemStack(TFCItems.Vinegar), new ItemStack(TFCItems.WoodenBucketEmpty));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCFluids.OLIVEOIL, 1000), ItemOilLamp.GetFullLamp(0), new ItemStack(TFCBlocks.OilLamp, 1, 0));//Gold
		FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCFluids.OLIVEOIL, 1000), ItemOilLamp.GetFullLamp(1), new ItemStack(TFCBlocks.OilLamp, 1, 1));//Platinum
		FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCFluids.OLIVEOIL, 1000), ItemOilLamp.GetFullLamp(2), new ItemStack(TFCBlocks.OilLamp, 1, 2));//RoseGold
		FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCFluids.OLIVEOIL, 1000), ItemOilLamp.GetFullLamp(3), new ItemStack(TFCBlocks.OilLamp, 1, 3));//Silver
		FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCFluids.OLIVEOIL, 1000), ItemOilLamp.GetFullLamp(4), new ItemStack(TFCBlocks.OilLamp, 1, 4));//Sterling Silver
		FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCFluids.OLIVEOIL, 1000), ItemOilLamp.GetFullLamp(5), new ItemStack(TFCBlocks.OilLamp, 1, 5));//BlueSteel
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

	public void registerHandlers()
	{
	}

	public void registerSoundHandler()
	{
	}

	public void registerTickHandler()
	{
		FMLCommonHandler.instance().bus().register(new ServerTickHandler());
	}

	public void registerGuiHandler()
	{
		NetworkRegistry.INSTANCE.registerGuiHandler(TerraFirmaCraft.instance, new GuiHandler());
	}

	public void registerWailaClasses()
	{
		FMLInterModComms.sendMessage("Waila", "register", "com.bioxx.tfc.WAILA.WOre.callbackRegister");
		FMLInterModComms.sendMessage("Waila", "register", "com.bioxx.tfc.WAILA.WCrop.callbackRegister");
		FMLInterModComms.sendMessage("Waila", "register", "com.bioxx.tfc.WAILA.WBarrel.callbackRegister");
		FMLInterModComms.sendMessage("Waila", "register", "com.bioxx.tfc.WAILA.WAnvil.callbackRegister");
		FMLInterModComms.sendMessage("Waila", "register", "com.bioxx.tfc.WAILA.WBlastFurnace.callbackRegister");
		FMLInterModComms.sendMessage("Waila", "register", "com.bioxx.tfc.WAILA.WBerryBush.callbackRegister");
		FMLInterModComms.sendMessage("Waila", "register", "com.bioxx.tfc.WAILA.WBloomery.callbackRegister");
		FMLInterModComms.sendMessage("Waila", "register", "com.bioxx.tfc.WAILA.WBloom.callbackRegister");
		FMLInterModComms.sendMessage("Waila", "register", "com.bioxx.tfc.WAILA.WCrucible.callbackRegister");

		// I haven't decided if this is OP or not. Useful for debugging though, so uncomment when needing to check farmland nutrient %.
		//FMLInterModComms.sendMessage("Waila", "register", "com.bioxx.tfc.WAILA.WFarmland.callbackRegister");
	}
}
