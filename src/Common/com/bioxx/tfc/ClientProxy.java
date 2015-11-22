package com.bioxx.tfc;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelSlime;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.common.MinecraftForge;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.Core.ColorizerFoliageTFC;
import com.bioxx.tfc.Core.TFC_Climate;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Entities.*;
import com.bioxx.tfc.Entities.Mobs.*;
import com.bioxx.tfc.Handlers.BiomeEventHandler;
import com.bioxx.tfc.Handlers.Client.*;
import com.bioxx.tfc.Render.*;
import com.bioxx.tfc.Render.RenderFallingBlock;
import com.bioxx.tfc.Render.Blocks.*;
import com.bioxx.tfc.Render.Models.*;
import com.bioxx.tfc.Render.TESR.*;
import com.bioxx.tfc.TileEntities.*;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCFluids;
import com.bioxx.tfc.api.Enums.EnumTree;
import com.bioxx.tfc.api.Util.KeyBindings;

public class ClientProxy extends CommonProxy
{

	@Override
	public void registerFluidIcons()
	{
		//Only bother adding fluids here if you don't want to use the default HotWater icons
		TFCFluids.LAVA.setIcons(TFCBlocks.lava.getIcon(0, 0), TFCBlocks.lava.getIcon(2, 0));
		TFCFluids.SALTWATER.setIcons(TFCBlocks.saltWater.getIcon(0, 0), TFCBlocks.saltWater.getIcon(2, 0));
		TFCFluids.FRESHWATER.setIcons(TFCBlocks.freshWater.getIcon(0, 0), TFCBlocks.freshWater.getIcon(2, 0));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerRenderInformation()
	{
		Minecraft.getMinecraft().entityRenderer = new EntityRendererTFC(Minecraft.getMinecraft(),Minecraft.getMinecraft().getResourceManager());
		IReloadableResourceManager iRRM = (IReloadableResourceManager) Minecraft.getMinecraft().getResourceManager();
		iRRM.registerReloadListener(new GrassColorReloadListener());
		iRRM.registerReloadListener(new FoliageColorReloadListener());		
		iRRM.registerReloadListener(Minecraft.getMinecraft().entityRenderer);

		RenderingRegistry.registerEntityRenderingHandler(EntityJavelin.class, new RenderTerraJavelin());
		RenderingRegistry.registerEntityRenderingHandler(EntitySquidTFC.class, new RenderSquidTFC(new ModelSquidTFC(), 0.7F));
		RenderingRegistry.registerEntityRenderingHandler(EntityCowTFC.class, new RenderCowTFC(new ModelCowTFC(), 0.7F));
		RenderingRegistry.registerEntityRenderingHandler(EntitySheepTFC.class, new RenderSheepTFC(new ModelSheep2TFC(), new ModelSheep1TFC(), 0.4F));
		RenderingRegistry.registerEntityRenderingHandler(EntityWolfTFC.class, new RenderWolfTFC(new ModelWolfTFC(), new ModelWolfTFC(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(EntityBear.class, new RenderBear(new ModelBear(), 0.9F));
		RenderingRegistry.registerEntityRenderingHandler(EntityPheasantTFC.class, new RenderPheasantTFC(new ModelPheasant(), 0.3F));
		RenderingRegistry.registerEntityRenderingHandler(EntityChickenTFC.class, new RenderChickenTFC(new ModelChickenTFC(), 0.3F));
		RenderingRegistry.registerEntityRenderingHandler(EntityPigTFC.class, new RenderPigTFC(new ModelPigTFC(), new ModelPigTFC(0.5F), 0.7F));
		RenderingRegistry.registerEntityRenderingHandler(EntityDeer.class, new RenderDeer(new ModelDeer(), 0.9F));
		RenderingRegistry.registerEntityRenderingHandler(EntityHorseTFC.class, new RenderHorseTFC(new ModelHorseTFC(), 0.9F));
		RenderingRegistry.registerEntityRenderingHandler(EntityCustomMinecart.class, new RenderMinecart());
		RenderingRegistry.registerEntityRenderingHandler(EntityStand.class, new RenderEntityStand());
		RenderingRegistry.registerEntityRenderingHandler(EntityFishTFC.class, new RenderFishTFC(new ModelBass(), 0.7F));
		RenderingRegistry.registerEntityRenderingHandler(EntitySkeletonTFC.class, new RenderSkeletonTFC());
		RenderingRegistry.registerEntityRenderingHandler(EntityZombieTFC.class, new RenderZombie());
		RenderingRegistry.registerEntityRenderingHandler(EntitySpiderTFC.class, new RenderSpider());
		RenderingRegistry.registerEntityRenderingHandler(EntitySlimeTFC.class, new RenderSlime(new ModelSlime(16), new ModelSlime(0), 0.25F));
		RenderingRegistry.registerEntityRenderingHandler(EntitySilverfishTFC.class, new RenderSilverfish());
		RenderingRegistry.registerEntityRenderingHandler(EntityGhastTFC.class, new RenderGhast());
		RenderingRegistry.registerEntityRenderingHandler(EntityCaveSpiderTFC.class, new RenderSpider());
		RenderingRegistry.registerEntityRenderingHandler(EntityBlazeTFC.class, new RenderBlaze());
		RenderingRegistry.registerEntityRenderingHandler(EntityEndermanTFC.class, new RenderEnderman());
		RenderingRegistry.registerEntityRenderingHandler(EntityPigZombieTFC.class, new RenderZombie());
		RenderingRegistry.registerEntityRenderingHandler(EntityIronGolemTFC.class, new RenderIronGolem());
		//RenderingRegistry.registerEntityRenderingHandler(EntityPlayer.class, new RenderPlayerTFC());
		RenderingRegistry.registerEntityRenderingHandler(EntityProjectileTFC.class, new RenderArrow());
		RenderingRegistry.registerEntityRenderingHandler(EntityFishHookTFC.class, new RenderFish());
		RenderingRegistry.registerEntityRenderingHandler(EntityBarrel.class, new RenderBarrelEntity());
		RenderingRegistry.registerEntityRenderingHandler(EntityFallingBlockTFC.class, new RenderFallingBlock());

		RenderingRegistry.registerBlockHandler(TFCBlocks.chestRenderId = RenderingRegistry.getNextAvailableRenderId(), new RenderChest());
		RenderingRegistry.registerBlockHandler(TFCBlocks.clayGrassRenderId = RenderingRegistry.getNextAvailableRenderId(), new BlockRenderHandler());
		RenderingRegistry.registerBlockHandler(TFCBlocks.peatGrassRenderId = RenderingRegistry.getNextAvailableRenderId(), new BlockRenderHandler());
		RenderingRegistry.registerBlockHandler(TFCBlocks.sulfurRenderId = RenderingRegistry.getNextAvailableRenderId(), new BlockRenderHandler());
		RenderingRegistry.registerBlockHandler(TFCBlocks.woodSupportRenderIdH = RenderingRegistry.getNextAvailableRenderId(), new RenderSupportBeam());
		RenderingRegistry.registerBlockHandler(TFCBlocks.woodSupportRenderIdV = RenderingRegistry.getNextAvailableRenderId(), new RenderSupportBeam());
		RenderingRegistry.registerBlockHandler(TFCBlocks.grassRenderId = RenderingRegistry.getNextAvailableRenderId(), new BlockRenderHandler());
		RenderingRegistry.registerBlockHandler(TFCBlocks.oreRenderId = RenderingRegistry.getNextAvailableRenderId(), new RenderOre());
		RenderingRegistry.registerBlockHandler(TFCBlocks.moltenRenderId = RenderingRegistry.getNextAvailableRenderId(), new BlockRenderHandler());
		RenderingRegistry.registerBlockHandler(TFCBlocks.looseRockRenderId = RenderingRegistry.getNextAvailableRenderId(), new BlockRenderHandler());
		RenderingRegistry.registerBlockHandler(TFCBlocks.firepitRenderId = RenderingRegistry.getNextAvailableRenderId(), new BlockRenderHandler());
		RenderingRegistry.registerBlockHandler(TFCBlocks.anvilRenderId = RenderingRegistry.getNextAvailableRenderId(), new RenderAnvil());
		RenderingRegistry.registerBlockHandler(TFCBlocks.bellowsRenderId = RenderingRegistry.getNextAvailableRenderId(), new RenderBellows());
		RenderingRegistry.registerBlockHandler(TFCBlocks.forgeRenderId = RenderingRegistry.getNextAvailableRenderId(), new BlockRenderHandler());
		RenderingRegistry.registerBlockHandler(TFCBlocks.sluiceRenderId = RenderingRegistry.getNextAvailableRenderId(), new BlockRenderHandler());
		RenderingRegistry.registerBlockHandler(TFCBlocks.woodFruitRenderId = RenderingRegistry.getNextAvailableRenderId(), new BlockRenderHandler());
		RenderingRegistry.registerBlockHandler(TFCBlocks.leavesFruitRenderId = RenderingRegistry.getNextAvailableRenderId(), new BlockRenderHandler());
		RenderingRegistry.registerBlockHandler(TFCBlocks.stairRenderId = RenderingRegistry.getNextAvailableRenderId(), new BlockRenderHandler());
		RenderingRegistry.registerBlockHandler(TFCBlocks.slabRenderId = RenderingRegistry.getNextAvailableRenderId(), new BlockRenderHandler());
		RenderingRegistry.registerBlockHandler(TFCBlocks.cropRenderId = RenderingRegistry.getNextAvailableRenderId(), new BlockRenderHandler());
		RenderingRegistry.registerBlockHandler(TFCBlocks.cookingPitRenderId = RenderingRegistry.getNextAvailableRenderId(), new BlockRenderHandler());
		RenderingRegistry.registerBlockHandler(TFCBlocks.leavesRenderId = RenderingRegistry.getNextAvailableRenderId(), new BlockRenderHandler());
		RenderingRegistry.registerBlockHandler(TFCBlocks.detailedRenderId = RenderingRegistry.getNextAvailableRenderId(), new BlockRenderHandler());
		RenderingRegistry.registerBlockHandler(TFCBlocks.wallRenderId = RenderingRegistry.getNextAvailableRenderId(), new RenderWall());
		RenderingRegistry.registerBlockHandler(TFCBlocks.fenceRenderId = RenderingRegistry.getNextAvailableRenderId(), new RenderFence());
		RenderingRegistry.registerBlockHandler(TFCBlocks.fenceGateRenderId = RenderingRegistry.getNextAvailableRenderId(), new RenderFenceGate());
		RenderingRegistry.registerBlockHandler(TFCBlocks.toolRackRenderId = RenderingRegistry.getNextAvailableRenderId(), new RenderToolRack());
		//RenderingRegistry.registerBlockHandler(TFCBlocks.foodPrepRenderId = RenderingRegistry.getNextAvailableRenderId(), new BlockRenderHandler());
		RenderingRegistry.registerBlockHandler(TFCBlocks.quernRenderId = RenderingRegistry.getNextAvailableRenderId(), new TESRQuern());
		RenderingRegistry.registerBlockHandler(TFCBlocks.woodConstructRenderId = RenderingRegistry.getNextAvailableRenderId(), new RenderWoodConstruct());
		RenderingRegistry.registerBlockHandler(TFCBlocks.barrelRenderId = RenderingRegistry.getNextAvailableRenderId(), new RenderBarrel());
		RenderingRegistry.registerBlockHandler(TFCBlocks.loomRenderId = RenderingRegistry.getNextAvailableRenderId(), new RenderLoom());
		RenderingRegistry.registerBlockHandler(TFCBlocks.standRenderId = RenderingRegistry.getNextAvailableRenderId(), new RenderStand());
		RenderingRegistry.registerBlockHandler(TFCBlocks.nestBoxRenderId = RenderingRegistry.getNextAvailableRenderId(), new RenderNestBox());
		RenderingRegistry.registerBlockHandler(TFCBlocks.potteryRenderId = RenderingRegistry.getNextAvailableRenderId(), new RenderPottery());
		RenderingRegistry.registerBlockHandler(TFCBlocks.tuyereRenderId = RenderingRegistry.getNextAvailableRenderId(), new RenderTuyere());
		RenderingRegistry.registerBlockHandler(TFCBlocks.crucibleRenderId = RenderingRegistry.getNextAvailableRenderId(), new RenderCrucible());
		RenderingRegistry.registerBlockHandler(TFCBlocks.waterPlantRenderId = RenderingRegistry.getNextAvailableRenderId(), new BlockRenderHandler());
		//RenderingRegistry.registerBlockHandler(TFCBlocks.berryRenderId = RenderingRegistry.getNextAvailableRenderId(), new RenderBerryBush());
		RenderingRegistry.registerBlockHandler(TFCBlocks.bloomeryRenderId = RenderingRegistry.getNextAvailableRenderId(), new RenderBloomery());
		RenderingRegistry.registerBlockHandler(TFCBlocks.metalsheetRenderId = RenderingRegistry.getNextAvailableRenderId(), new RenderMetalSheet());
		RenderingRegistry.registerBlockHandler(TFCBlocks.leatherRackRenderId = RenderingRegistry.getNextAvailableRenderId(), new RenderLeatherRack());
		RenderingRegistry.registerBlockHandler(TFCBlocks.grillRenderId = RenderingRegistry.getNextAvailableRenderId(), new RenderGrill());
		RenderingRegistry.registerBlockHandler(TFCBlocks.metalTrapDoorRenderId = RenderingRegistry.getNextAvailableRenderId(), new RenderMetalTrapDoor());
		RenderingRegistry.registerBlockHandler(TFCBlocks.vesselRenderId = RenderingRegistry.getNextAvailableRenderId(), new RenderVessel());
		RenderingRegistry.registerBlockHandler(TFCBlocks.torchRenderId = RenderingRegistry.getNextAvailableRenderId(), new RenderTorch());
		RenderingRegistry.registerBlockHandler(TFCBlocks.smokeRenderId = RenderingRegistry.getNextAvailableRenderId(), new RenderSmoke());
		RenderingRegistry.registerBlockHandler(TFCBlocks.smokeRackRenderId = RenderingRegistry.getNextAvailableRenderId(), new RenderSmokeRack());
		RenderingRegistry.registerBlockHandler(TFCBlocks.oilLampRenderId = RenderingRegistry.getNextAvailableRenderId(), new RenderOilLamp());
		RenderingRegistry.registerBlockHandler(TFCBlocks.hopperRenderId = RenderingRegistry.getNextAvailableRenderId(), new RenderHopper());
		RenderingRegistry.registerBlockHandler(TFCBlocks.flowerPotRenderId = RenderingRegistry.getNextAvailableRenderId(), new RenderFlowerPot());
		//Register our overlay changes
		MinecraftForge.EVENT_BUS.register(new RenderOverlayHandler());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBiomeEventHandler()
	{
		// Register the Biome Event Handler
		MinecraftForge.EVENT_BUS.register(new BiomeEventHandler());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void setupGuiIngameForge()
	{
		GuiIngameForge.renderHealth = false;
		GuiIngameForge.renderArmor = false;
		GuiIngameForge.renderExperiance = false;
		//GuiIngameForge.renderHealthMount = false;		//Forge re-enables it every tick
	}

	@Override
	public void registerTileEntities(boolean b)
	{
		super.registerTileEntities(false);
		ClientRegistry.registerTileEntity(TEChest.class, "chest", new TESRChest());
		ClientRegistry.registerTileEntity(TEIngotPile.class, "ingotPile", new TESRIngotPile());
		ClientRegistry.registerTileEntity(TEFirepit.class, "TerraFirepit", new TESRFirepit());
		ClientRegistry.registerTileEntity(TELoom.class, "Loom", new TESRLoom());
		//ModLoader.registerTileEntity(TileEntityBarrel.class, "barrel", new TileEntityBarrelRendererTFC());
		ClientRegistry.registerTileEntity(TEPottery.class, "Pottery", new TESRPottery());
		ClientRegistry.registerTileEntity(TEFoodPrep.class, "FoodPrep", new TESRFoodPrep());
		ClientRegistry.registerTileEntity(TEBellows.class, "Bellows", new TESRBellows());
		ClientRegistry.registerTileEntity(TEToolRack.class, "ToolRack", new TESRToolrack());
		ClientRegistry.registerTileEntity(TEAnvil.class, "TerraAnvil", new TESRAnvil());
		ClientRegistry.registerTileEntity(TEWorldItem.class, "worldItem", new TESRWorldItem());
		ClientRegistry.registerTileEntity(TEQuern.class, "Quern", new TESRQuern());
		ClientRegistry.registerTileEntity(TEGrill.class, "GrillTESR", new TESRGrill());
		ClientRegistry.registerTileEntity(TESmokeRack.class, "SmokeRackTESR", new TESRSmokeRack());
		ClientRegistry.registerTileEntity(TEHopper.class, "HopperTESR", new TESRHopper());
	}

	@Override
	public void onClientLogin()
	{
		/*PlayerManagerTFC.getInstance().Players.add(new PlayerInfo(
				Minecraft.getMinecraft().thePlayer.getDisplayName(),
				Minecraft.getMinecraft().thePlayer.getUniqueID()));*/
	}

	@Override
	public World getCurrentWorld()
	{
		return Minecraft.getMinecraft().theWorld;
	}

	@Override
	public boolean isRemote()
	{
		return true;
	}

	@Override
	public int waterColorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		return 0x354d35;
	}

	@Override
	public int grassColorMultiplier(IBlockAccess par1IBlockAccess, int i, int j, int k)
	{
		int var5 = 0;
		int var6 = 0;
		int var7 = 0;

		for (int z = -1; z <= 1; ++z)
		{
			for (int x = -1; x <= 1; ++x)
			{
				int var10 = TFC_Climate.getGrassColor(getCurrentWorld(), i + x, j, k + z);
				var5 += (var10 & 16711680) >> 16;
			var6 += (var10 & 65280) >> 8;
			var7 += var10 & 255;
			}
		}
		return (var5 / 9 & 255) << 16 | (var6 / 9 & 255) << 8 | var7 / 9 & 255;
	}

	@Override
	public int foliageColorMultiplier(IBlockAccess par1IBlockAccess, int i, int j, int k)
	{
		//int var5 = 0;
		//int var6 = 0;
		//int var7 = 0;
		int[] rgb = { 0, 0, 0 };
		float temperature = TFC_Climate.getHeightAdjustedTempSpecificDay(getCurrentWorld(),TFC_Time.getDayOfYear(),i,j,k);
		//float rainfall = TFC_Climate.getRainfall(getCurrentWorld(),i,j,k);

		int meta = par1IBlockAccess.getBlockMetadata(i, j, k);
		if(par1IBlockAccess.getBlock(i, j, k) == TFCBlocks.fruitTreeLeaves)
			//			if(TFC_Time.currentMonth >= TFC_Time.September && TFC_Time.currentMonth < TFC_Time.December)
			//			{
			//				int var10 = ColorizerFoliageTFC.getFoliageYellow();
			//				rgb = applyColor(var10, rgb);
			//
			//				int x = (rgb[0] / 9 & 255) << 16 | (rgb[1] / 9 & 255) << 8 | rgb[2] / 9 & 255;
			//				return x;
			//			}
			//			else
		{
			for (int var8 = -1; var8 <= 1; ++var8)
			{
				for (int var9 = -1; var9 <= 1; ++var9)
				{
					int var10 = TFC_Climate.getFoliageColor(getCurrentWorld(), i + var8, j, k + var9);
					rgb = applyColor(var10, rgb);
				}
			}
			return (rgb[0] / 9 & 255) << 16 | (rgb[1] / 9 & 255) << 8 | rgb[2] / 9 & 255;
		}
		else if(par1IBlockAccess.getBlock(i, j, k) == TFCBlocks.vine)
		{
			if (TFC_Time.getSeasonAdjustedMonth(k) >= 6 &&TFC_Time.getSeasonAdjustedMonth(k) < 9 &&
				TFC_Climate.getCacheManager(getCurrentWorld()).getEVTLayerAt(i, k).floatdata1 >= 0.8
					&& TFC_Climate.getHeightAdjustedTemp(getCurrentWorld(), i, j, k) < 30)
			{
				int color = 0;
				for (int var8 = -1; var8 <= 1; ++var8)
				{
					for (int var9 = -1; var9 <= 1; ++var9)
					{
						color = TFC_Climate.getFoliageColor(getCurrentWorld(), i + var8, j, k + var9);
						rgb = applyColor(color, rgb);
					}
				}
				return (rgb[0] / 9 & 255) << 16 | (rgb[1] / 9 & 255) << 8 | rgb[2] / 9 & 255;
			}
			else if (TFC_Time.getSeasonAdjustedMonth(k) >= 11 || TFC_Time.getSeasonAdjustedMonth(k) <= 0 &&
					TFC_Climate.getCacheManager(getCurrentWorld()).getEVTLayerAt(i, k).floatdata1 >= 0.8
					&& TFC_Climate.getHeightAdjustedTemp(getCurrentWorld(), i, j, k) < 30)
			{
				for (int var8 = -1; var8 <= 1; ++var8)
				{
					for (int var9 = -1; var9 <= 1; ++var9)
					{
						int color = TFC_Climate.getFoliageColor(getCurrentWorld(), i + var8, j, k + var9);
						rgb = applyColor(color, rgb);
					}
				}
				return (rgb[0] / 9 & 255) << 16 | (rgb[1] / 9 & 255) << 8 | rgb[2] / 9 & 255;
			}
			else if (TFC_Time.getSeasonAdjustedMonth(k) >= 9 &&
					TFC_Climate.getCacheManager(getCurrentWorld()).getEVTLayerAt(i, k).floatdata1 >= 0.8
					&& TFC_Climate.getHeightAdjustedTemp(getCurrentWorld(), i, j, k) < 30)
			{
				for (int var8 = -1; var8 <= 1; ++var8)
				{
					for (int var9 = -1; var9 <= 1; ++var9)
					{
						int color = ColorizerFoliageTFC.getFoliageDead();
						rgb = applyColor(color, rgb);
					}
				}
				return (rgb[0] / 9 & 255) << 16 | (rgb[1] / 9 & 255) << 8 | rgb[2] / 9 & 255;
			}
			else
			{
				for (int var8 = -1; var8 <= 1; ++var8)
				{
					for (int var9 = -1; var9 <= 1; ++var9)
					{
						int color = TFC_Climate.getFoliageColor(getCurrentWorld(), i + var8, j, k + var9);
						rgb = applyColor(color, rgb);
					}
				}
				return (rgb[0] / 9 & 255) << 16 | (rgb[1] / 9 & 255) << 8 | rgb[2] / 9 & 255;
			}
		}
		else if (TFC_Time.getSeasonAdjustedMonth(k) >= 6 && EnumTree.values()[meta].isEvergreen)
		{
			for (int var8 = -1; var8 <= 1; ++var8)
			{
				for (int var9 = -1; var9 <= 1; ++var9)
				{
					int var10 = TFC_Climate.getFoliageColorEvergreen(getCurrentWorld(), i + var8, j, k + var9);
					rgb = applyColor(var10, rgb);
				}
			}
			return (rgb[0] / 9 & 255) << 16 | (rgb[1] / 9 & 255) << 8 | rgb[2] / 9 & 255;
		}
		else if (temperature <= 10 && TFC_Time.getSeasonAdjustedMonth(k) >= 6 && TFC_Time.getSeasonAdjustedMonth(k) < 9 && (meta == 4 || meta == 7 || meta == 5 || meta == 14))
		{
			int color = 0;
			//Get the fade multiplie
			for (int var8 = -1; var8 <= 1; ++var8)
			{
				for (int var9 = -1; var9 <= 1; ++var9)
				{
					color = ColorizerFoliageTFC.getFoliageYellow();
					rgb = applyColor(color, rgb);
				}
			}
			return (rgb[0] / 9 & 255) << 16 | (rgb[1] / 9 & 255) << 8 | rgb[2] / 9 & 255;
		}
		else if (temperature <= 10 && TFC_Time.getSeasonAdjustedMonth(k) >= 6 && TFC_Time.getSeasonAdjustedMonth(k) < 9 && meta == 6)
		{
			for (int var8 = -1; var8 <= 1; ++var8)
			{
				for (int var9 = -1; var9 <= 1; ++var9)
				{
					int var10 = ColorizerFoliageTFC.getFoliageRed();
					rgb = applyColor(var10, rgb);
				}
			}
			return (rgb[0] / 9 & 255) << 16 | (rgb[1] / 9 & 255) << 8 | rgb[2] / 9 & 255;
		}
		else if (temperature <= 10 && TFC_Time.getSeasonAdjustedMonth(k) >= 6 && TFC_Time.getSeasonAdjustedMonth(k) < 9 && meta != 15)
		{
			for (int var8 = -1; var8 <= 1; ++var8)
			{
				for (int var9 = -1; var9 <= 1; ++var9)
				{
					int var10 = ColorizerFoliageTFC.getFoliageOrange();
					rgb = applyColor(var10, rgb);
				}
			}
			return (rgb[0] / 9 & 255) << 16 | (rgb[1] / 9 & 255) << 8 | rgb[2] / 9 & 255;
		}
		else if (temperature <= 8 && TFC_Time.getSeasonAdjustedMonth(k) >= 6 && meta != 15)
		{
			for (int var8 = -1; var8 <= 1; ++var8)
			{
				for (int var9 = -1; var9 <= 1; ++var9)
				{
					int var10 = ColorizerFoliageTFC.getFoliageDead();
					rgb = applyColor(var10, rgb);
				}
			}
			return (rgb[0] / 9 & 255) << 16 | (rgb[1] / 9 & 255) << 8 | rgb[2] / 9 & 255;
		}
		else
		{
			for (int var8 = -1; var8 <= 1; ++var8)
			{
				for (int var9 = -1; var9 <= 1; ++var9)
				{
					int var10 = TFC_Climate.getFoliageColor(getCurrentWorld(), i + var8, j, k + var9);
					rgb = applyColor(var10, rgb);
				}
			}
			return (rgb[0] / 9 & 255) << 16 | (rgb[1] / 9 & 255) << 8 | rgb[2] / 9 & 255;
		}
	}

	/*private float getTimeMult(long day, long start, long end)
	{
		float total = end - start;
		return total - (day - start) / total;
	}*/

	private int[] applyColor(int c, int[] rgb)
	{
		rgb[0] += (c & 16711680) >> 16;
		rgb[1] += (c & 65280) >> 8;
		rgb[2] += c & 255;
		return rgb;
	}

	@Override
	public int getArmorRenderID(String name)
	{
		return RenderingRegistry.addNewArmourRendererPrefix(name);
	}

	@Override
	public void registerKeys()
	{
		KeyBindings.addKeyBinding(KeyBindingHandler.keyToolMode);
		KeyBindings.addIsRepeating(false);
		KeyBindings.addKeyBinding(KeyBindingHandler.keyLockTool);
		KeyBindings.addIsRepeating(false);
		//ClientRegistry.registerKeyBinding(KeyBindingHandler.Key_ToolMode);
		//ClientRegistry.registerKeyBinding(KeyBindingHandler.Key_LockTool);
		uploadKeyBindingsToGame();
	}

	@Override
	public void registerKeyBindingHandler()
	{
		FMLCommonHandler.instance().bus().register(new KeyBindingHandler());
	}

	@Override
	public void uploadKeyBindingsToGame()
	{
		GameSettings settings = Minecraft.getMinecraft().gameSettings;
		KeyBinding[] tfcKeyBindings = KeyBindings.gatherKeyBindings();
		KeyBinding[] allKeys = new KeyBinding[settings.keyBindings.length + tfcKeyBindings.length];
		System.arraycopy(settings.keyBindings, 0, allKeys, 0, settings.keyBindings.length);
		System.arraycopy(tfcKeyBindings, 0, allKeys, settings.keyBindings.length, tfcKeyBindings.length);
		settings.keyBindings = allKeys;
		settings.loadOptions();
	}

	@Override
	public void registerHandlers()
	{
		MinecraftForge.EVENT_BUS.register(new ChiselHighlightHandler());
		MinecraftForge.EVENT_BUS.register(new FarmlandHighlightHandler());
		MinecraftForge.EVENT_BUS.register(new PlankHighlightHandler());
		MinecraftForge.EVENT_BUS.register(new ArmourStandHighlightHandler());
		MinecraftForge.EVENT_BUS.register(new FamiliarityHighlightHandler());
		MinecraftForge.EVENT_BUS.register(new FogHandler());

	}

	@Override
	public void registerPlayerRenderEventHandler()
	{
		PlayerRenderHandler pRHandler = new PlayerRenderHandler();
		MinecraftForge.EVENT_BUS.register(pRHandler);
		FMLCommonHandler.instance().bus().register(pRHandler);
	}

	@Override
	public void registerSoundHandler()
	{
		MinecraftForge.EVENT_BUS.register(new SoundHandler());
	}

	@Override
	public void registerTickHandler()
	{
		super.registerTickHandler();
		FMLCommonHandler.instance().bus().register(new ClientTickHandler());
		FMLCommonHandler.instance().bus().register(new FMLClientEventHandler());
	}

	@Override
	public void registerGuiHandler()
	{
		NetworkRegistry.INSTANCE.registerGuiHandler(TerraFirmaCraft.instance, new GuiHandler());
		// Register Gui Event Handler
		MinecraftForge.EVENT_BUS.register(new GuiHandler());
	}

	@Override
	public String getCurrentLanguage()
	{
		return Minecraft.getMinecraft().getLanguageManager().getCurrentLanguage().getLanguageCode();
	}

	@Override
	public boolean getGraphicsLevel()
	{
		Minecraft.getMinecraft();
		return Minecraft.isFancyGraphicsEnabled();
	}

	@Override
	public void hideNEIItems()
	{
		if (Loader.isModLoaded("NotEnoughItems")) NEIIntegration.hideNEIItems();
	}
}
