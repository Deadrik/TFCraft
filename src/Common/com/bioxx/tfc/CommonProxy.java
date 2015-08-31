package com.bioxx.tfc;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

import com.bioxx.tfc.Entities.*;
import com.bioxx.tfc.Entities.Mobs.*;
import com.bioxx.tfc.Handlers.GuiHandler;
import com.bioxx.tfc.Handlers.ServerTickHandler;
import com.bioxx.tfc.TileEntities.*;
import com.bioxx.tfc.Tools.ChiselMode_Detailed;
import com.bioxx.tfc.Tools.ChiselMode_Slab;
import com.bioxx.tfc.Tools.ChiselMode_Smooth;
import com.bioxx.tfc.Tools.ChiselMode_Stair;
import com.bioxx.tfc.WorldGen.TFCProvider;
import com.bioxx.tfc.api.TFCFluids;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.Tools.ChiselManager;

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
		GameRegistry.registerTileEntity(TEWorkbench.class, "TerraWorkbench");
		GameRegistry.registerTileEntity(TEForge.class, "TerraForge");
		GameRegistry.registerTileEntity(TEBlastFurnace.class, "TerraBloomery");
		GameRegistry.registerTileEntity(TEBloomery.class, "TerraEarlyBloomery");
		GameRegistry.registerTileEntity(TESluice.class, "TerraSluice");
		GameRegistry.registerTileEntity(TEFarmland.class, "TileEntityFarmland");
		GameRegistry.registerTileEntity(TECrop.class, "TileEntityCrop");
		GameRegistry.registerTileEntity(TEFruitTreeWood.class, "FruitTreeWood");
		GameRegistry.registerTileEntity(TEPartial.class, "Partial");
		GameRegistry.registerTileEntity(TEDetailed.class, "Detailed");
		GameRegistry.registerTileEntity(TESpawnMeter.class, "SpawnMeter");
		GameRegistry.registerTileEntity(TESapling.class, "Sapling");
		GameRegistry.registerTileEntity(TEWoodConstruct.class, "WoodConstruct");
		GameRegistry.registerTileEntity(TEBarrel.class, "Barrel");
		GameRegistry.registerTileEntity(TEFenceGate.class, "FenceGate");
		GameRegistry.registerTileEntity(TEBloom.class, "IronBloom");
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
			GameRegistry.registerTileEntity(TEToolRack.class, "ToolRack");
			GameRegistry.registerTileEntity(TEAnvil.class, "TerraAnvil");
			GameRegistry.registerTileEntity(TEWorldItem.class, "worldItem");
			GameRegistry.registerTileEntity(TEQuern.class, "Quern");
			GameRegistry.registerTileEntity(TELoom.class, "Loom");
			GameRegistry.registerTileEntity(TEGrill.class, "grill");
			GameRegistry.registerTileEntity(TEHopper.class, "HopperTFC");
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
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluid(TFCFluids.LAVA.getName()), new ItemStack(TFCItems.blueSteelBucketLava), new ItemStack(TFCItems.blueSteelBucketEmpty));
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluid(TFCFluids.FRESHWATER.getName()), new ItemStack(TFCItems.redSteelBucketWater), new ItemStack(TFCItems.redSteelBucketEmpty));
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluid(TFCFluids.SALTWATER.getName()), new ItemStack(TFCItems.redSteelBucketSaltWater), new ItemStack(TFCItems.redSteelBucketEmpty));
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluid(TFCFluids.FRESHWATER.getName()), new ItemStack(TFCItems.woodenBucketWater), new ItemStack(TFCItems.woodenBucketEmpty));
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluid(TFCFluids.SALTWATER.getName()), new ItemStack(TFCItems.woodenBucketSaltWater), new ItemStack(TFCItems.woodenBucketEmpty));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCFluids.FRESHWATER, 1000), new ItemStack(TFCItems.potteryJug, 1, 2), new ItemStack(TFCItems.potteryJug,1, 1));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCFluids.RUM, 250), new ItemStack(TFCItems.rum), new ItemStack(TFCItems.glassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCFluids.BEER, 250), new ItemStack(TFCItems.beer), new ItemStack(TFCItems.glassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCFluids.RYEWHISKEY, 250), new ItemStack(TFCItems.ryeWhiskey), new ItemStack(TFCItems.glassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCFluids.WHISKEY, 250), new ItemStack(TFCItems.whiskey), new ItemStack(TFCItems.glassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCFluids.CORNWHISKEY, 250), new ItemStack(TFCItems.cornWhiskey), new ItemStack(TFCItems.glassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCFluids.SAKE, 250), new ItemStack(TFCItems.sake), new ItemStack(TFCItems.glassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCFluids.CIDER, 250), new ItemStack(TFCItems.cider), new ItemStack(TFCItems.glassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCFluids.VODKA, 250), new ItemStack(TFCItems.vodka), new ItemStack(TFCItems.glassBottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCFluids.MILK, 1000), new ItemStack(TFCItems.woodenBucketMilk), new ItemStack(TFCItems.woodenBucketEmpty));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCFluids.VINEGAR, 1000), new ItemStack(TFCItems.vinegar), new ItemStack(TFCItems.woodenBucketEmpty));
		//FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCFluids.OLIVEOIL, 250), ItemOilLamp.GetFullLamp(0), new ItemStack(TFCBlocks.OilLamp, 1, 0));//Gold
		//FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCFluids.OLIVEOIL, 250), ItemOilLamp.GetFullLamp(1), new ItemStack(TFCBlocks.OilLamp, 1, 1));//Platinum
		//FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCFluids.OLIVEOIL, 250), ItemOilLamp.GetFullLamp(2), new ItemStack(TFCBlocks.OilLamp, 1, 2));//RoseGold
		//FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCFluids.OLIVEOIL, 250), ItemOilLamp.GetFullLamp(3), new ItemStack(TFCBlocks.OilLamp, 1, 3));//Silver
		//FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCFluids.OLIVEOIL, 250), ItemOilLamp.GetFullLamp(4), new ItemStack(TFCBlocks.OilLamp, 1, 4));//Sterling Silver
		//FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCFluids.OLIVEOIL, 250), ItemOilLamp.GetFullLamp(5), new ItemStack(TFCBlocks.OilLamp, 1, 5));//BlueSteel
		//FluidContainerRegistry.registerFluidContainer(new FluidStack(TFCFluids.LAVA, 250), ItemOilLamp.GetFullLamp(5), new ItemStack(TFCBlocks.OilLamp, 1, 5));//BlueSteel
	}

	public void registerToolClasses()
	{
		//pickaxes
		TFCItems.bismuthBronzePick.setHarvestLevel("pickaxe", 2);
		TFCItems.bismuthBronzePick.setHarvestLevel("pickaxe", 2);
		TFCItems.blackBronzePick.setHarvestLevel("pickaxe", 2);
		TFCItems.blackSteelPick.setHarvestLevel("pickaxe", 5);
		TFCItems.blueSteelPick.setHarvestLevel("pickaxe", 6);
		TFCItems.bronzePick.setHarvestLevel("pickaxe", 2);
		TFCItems.copperPick.setHarvestLevel("pickaxe", 1);
		TFCItems.wroughtIronPick.setHarvestLevel("pickaxe", 3);
		TFCItems.redSteelPick.setHarvestLevel("pickaxe", 6);
		TFCItems.steelPick.setHarvestLevel("pickaxe", 4);
		//shovels
		TFCItems.igInShovel.setHarvestLevel("shovel", 1);
		TFCItems.igExShovel.setHarvestLevel("shovel", 1);
		TFCItems.sedShovel.setHarvestLevel("shovel", 1);
		TFCItems.mMShovel.setHarvestLevel("shovel", 1);
		TFCItems.bismuthBronzeShovel.setHarvestLevel("shovel", 2);
		TFCItems.blackBronzeShovel.setHarvestLevel("shovel", 2);
		TFCItems.blackSteelShovel.setHarvestLevel("shovel", 5);
		TFCItems.blueSteelShovel.setHarvestLevel("shovel", 6);
		TFCItems.bronzeShovel.setHarvestLevel("shovel", 2);
		TFCItems.copperShovel.setHarvestLevel("shovel", 1);
		TFCItems.wroughtIronShovel.setHarvestLevel("shovel", 3);
		TFCItems.redSteelShovel.setHarvestLevel("shovel", 6);
		TFCItems.steelShovel.setHarvestLevel("shovel", 4);
		//Axes
		TFCItems.igInAxe.setHarvestLevel("axe", 1);
		TFCItems.igExAxe.setHarvestLevel("axe", 1);
		TFCItems.sedAxe.setHarvestLevel("axe", 1);
		TFCItems.mMAxe.setHarvestLevel("axe", 1);
		TFCItems.bismuthBronzeAxe.setHarvestLevel("axe", 2);
		TFCItems.blackBronzeAxe.setHarvestLevel("axe", 2);
		TFCItems.blackSteelAxe.setHarvestLevel("axe", 5);
		TFCItems.blueSteelAxe.setHarvestLevel("axe", 6);
		TFCItems.bronzeAxe.setHarvestLevel("axe", 2);
		TFCItems.copperAxe.setHarvestLevel("axe", 1);
		TFCItems.wroughtIronAxe.setHarvestLevel("axe", 3);
		TFCItems.redSteelAxe.setHarvestLevel("axe", 6);
		TFCItems.steelAxe.setHarvestLevel("axe", 4);

		TFCItems.bismuthBronzeSaw.setHarvestLevel("axe", 2);
		TFCItems.blackBronzeSaw.setHarvestLevel("axe", 2);
		TFCItems.blackSteelSaw.setHarvestLevel("axe", 5);
		TFCItems.blueSteelSaw.setHarvestLevel("axe", 6);
		TFCItems.bronzeSaw.setHarvestLevel("axe", 2);
		TFCItems.copperSaw.setHarvestLevel("axe", 1);
		TFCItems.wroughtIronSaw.setHarvestLevel("axe", 3);
		TFCItems.redSteelSaw.setHarvestLevel("axe", 6);
		TFCItems.steelSaw.setHarvestLevel("axe", 4);

		TFCItems.stoneHammer.setHarvestLevel("hammer", 1);
		TFCItems.bismuthBronzeHammer.setHarvestLevel("hammer", 2);
		TFCItems.blackBronzeHammer.setHarvestLevel("hammer", 2);
		TFCItems.blackSteelHammer.setHarvestLevel("hammer", 5);
		TFCItems.blueSteelHammer.setHarvestLevel("hammer", 6);
		TFCItems.bronzeHammer.setHarvestLevel("hammer", 2);
		TFCItems.copperHammer.setHarvestLevel("hammer", 1);
		TFCItems.wroughtIronHammer.setHarvestLevel("hammer", 3);
		TFCItems.redSteelHammer.setHarvestLevel("hammer", 6);
		TFCItems.steelHammer.setHarvestLevel("hammer", 4);
	}

	public void onClientLogin()
	{
	}

	public void registerSkyProvider(TFCProvider p)
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
		FMLInterModComms.sendMessage("Waila", "register", "com.bioxx.tfc.WAILA.WAILAData.callbackRegister"); // Blocks
		FMLInterModComms.sendMessage("Waila", "register", "com.bioxx.tfc.WAILA.WMobs.callbackRegister"); // Entities
		FMLInterModComms.sendMessage("Waila", "register", "com.bioxx.tfc.WAILA.WCrucible.callbackRegister"); // Crucible has its own file due to extra calculations.
	}

	public void registerChiselModes()
	{
		ChiselManager.getInstance().addChiselMode(new ChiselMode_Smooth("Smooth"));
		ChiselManager.getInstance().addChiselMode(new ChiselMode_Stair("Stairs"));
		ChiselManager.getInstance().addChiselMode(new ChiselMode_Slab("Slabs"));
		ChiselManager.getInstance().addChiselMode(new ChiselMode_Detailed("Detailed"));
	}

	public void hideNEIItems() {}
}
