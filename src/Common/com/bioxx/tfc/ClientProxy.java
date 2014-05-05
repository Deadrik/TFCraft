package com.bioxx.tfc;

import java.io.File;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelSlime;
import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.client.renderer.entity.RenderBlaze;
import net.minecraft.client.renderer.entity.RenderEnderman;
import net.minecraft.client.renderer.entity.RenderFish;
import net.minecraft.client.renderer.entity.RenderGhast;
import net.minecraft.client.renderer.entity.RenderIronGolem;
import net.minecraft.client.renderer.entity.RenderMinecart;
import net.minecraft.client.renderer.entity.RenderSilverfish;
import net.minecraft.client.renderer.entity.RenderSlime;
import net.minecraft.client.renderer.entity.RenderSpider;
import net.minecraft.client.renderer.entity.RenderZombie;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.Fluid;

import com.bioxx.tfc.Core.ColorizerFoliageTFC;
import com.bioxx.tfc.Core.TFC_Climate;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Entities.EntityCustomMinecart;
import com.bioxx.tfc.Entities.EntityFishHookTFC;
import com.bioxx.tfc.Entities.EntityJavelin;
import com.bioxx.tfc.Entities.EntityProjectileTFC;
import com.bioxx.tfc.Entities.EntityStand;
import com.bioxx.tfc.Entities.Mobs.EntityBear;
import com.bioxx.tfc.Entities.Mobs.EntityBlazeTFC;
import com.bioxx.tfc.Entities.Mobs.EntityCaveSpiderTFC;
import com.bioxx.tfc.Entities.Mobs.EntityChickenTFC;
import com.bioxx.tfc.Entities.Mobs.EntityCowTFC;
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
import com.bioxx.tfc.Handlers.BiomeEventHandler;
import com.bioxx.tfc.Handlers.Client.ArmourStandHighlightHandler;
import com.bioxx.tfc.Handlers.Client.BlockRenderHandler;
import com.bioxx.tfc.Handlers.Client.ChiselHighlightHandler;
import com.bioxx.tfc.Handlers.Client.ClientTickHandler;
import com.bioxx.tfc.Handlers.Client.FarmlandHighlightHandler;
import com.bioxx.tfc.Handlers.Client.KeyBindingHandler;
import com.bioxx.tfc.Handlers.Client.PlankHighlightHandler;
import com.bioxx.tfc.Handlers.Client.RenderOverlayHandler;
import com.bioxx.tfc.Handlers.Client.SoundHandler;
import com.bioxx.tfc.Render.FoliageColorReloadListener;
import com.bioxx.tfc.Render.GrassColorReloadListener;
import com.bioxx.tfc.Render.RenderBear;
import com.bioxx.tfc.Render.RenderChickenTFC;
import com.bioxx.tfc.Render.RenderCowTFC;
import com.bioxx.tfc.Render.RenderDeer;
import com.bioxx.tfc.Render.RenderEntityStand;
import com.bioxx.tfc.Render.RenderHorseTFC;
import com.bioxx.tfc.Render.RenderPheasantTFC;
import com.bioxx.tfc.Render.RenderPigTFC;
import com.bioxx.tfc.Render.RenderPlayerTFC;
import com.bioxx.tfc.Render.RenderSheepTFC;
import com.bioxx.tfc.Render.RenderSkeletonTFC;
import com.bioxx.tfc.Render.RenderSquidTFC;
import com.bioxx.tfc.Render.RenderTerraJavelin;
import com.bioxx.tfc.Render.RenderWolfTFC;
import com.bioxx.tfc.Render.Blocks.RenderAnvil;
import com.bioxx.tfc.Render.Blocks.RenderBarrel;
import com.bioxx.tfc.Render.Blocks.RenderBellows;
import com.bioxx.tfc.Render.Blocks.RenderBloomery;
import com.bioxx.tfc.Render.Blocks.RenderCrucible;
import com.bioxx.tfc.Render.Blocks.RenderFence;
import com.bioxx.tfc.Render.Blocks.RenderFenceGate;
import com.bioxx.tfc.Render.Blocks.RenderMetalSheet;
import com.bioxx.tfc.Render.Blocks.RenderNestBox;
import com.bioxx.tfc.Render.Blocks.RenderOre;
import com.bioxx.tfc.Render.Blocks.RenderPipeBasic;
import com.bioxx.tfc.Render.Blocks.RenderPottery;
import com.bioxx.tfc.Render.Blocks.RenderStand;
import com.bioxx.tfc.Render.Blocks.RenderSupportBeam;
import com.bioxx.tfc.Render.Blocks.RenderToolRack;
import com.bioxx.tfc.Render.Blocks.RenderTuyere;
import com.bioxx.tfc.Render.Blocks.RenderWoodConstruct;
import com.bioxx.tfc.Render.Models.ModelBear;
import com.bioxx.tfc.Render.Models.ModelChickenTFC;
import com.bioxx.tfc.Render.Models.ModelCowTFC;
import com.bioxx.tfc.Render.Models.ModelDeer;
import com.bioxx.tfc.Render.Models.ModelHorseTFC;
import com.bioxx.tfc.Render.Models.ModelPheasant;
import com.bioxx.tfc.Render.Models.ModelPigTFC;
import com.bioxx.tfc.Render.Models.ModelSheep1TFC;
import com.bioxx.tfc.Render.Models.ModelSheep2TFC;
import com.bioxx.tfc.Render.Models.ModelSquidTFC;
import com.bioxx.tfc.Render.Models.ModelWolfTFC;
import com.bioxx.tfc.Render.TESR.TESRAnvil;
import com.bioxx.tfc.Render.TESR.TESRBellows;
import com.bioxx.tfc.Render.TESR.TESRChest;
import com.bioxx.tfc.Render.TESR.TESRFirepit;
import com.bioxx.tfc.Render.TESR.TESRFoodPrep;
import com.bioxx.tfc.Render.TESR.TESRIngotPile;
import com.bioxx.tfc.Render.TESR.TESRPottery;
import com.bioxx.tfc.Render.TESR.TESRQuern;
import com.bioxx.tfc.Render.TESR.TESRSeaWeed;
import com.bioxx.tfc.Render.TESR.TESRToolrack;
import com.bioxx.tfc.Render.TESR.TESRWorldItem;
import com.bioxx.tfc.TileEntities.TEFoodPrep;
import com.bioxx.tfc.TileEntities.TESeaWeed;
import com.bioxx.tfc.TileEntities.TEWorldItem;
import com.bioxx.tfc.TileEntities.TileEntityAnvil;
import com.bioxx.tfc.TileEntities.TileEntityBellows;
import com.bioxx.tfc.TileEntities.TileEntityChestTFC;
import com.bioxx.tfc.TileEntities.TileEntityFirepit;
import com.bioxx.tfc.TileEntities.TileEntityIngotPile;
import com.bioxx.tfc.TileEntities.TileEntityPottery;
import com.bioxx.tfc.TileEntities.TileEntityQuern;
import com.bioxx.tfc.TileEntities.TileEntityToolRack;
import com.bioxx.tfc.WorldGen.TFCWorldChunkManager;
import com.bioxx.tfc.api.Enums.EnumTree;
import com.bioxx.tfc.api.Util.KeyBindings;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ClientProxy extends CommonProxy
{
	@Override
	public void registerFluidIcons(Fluid f, Block b)
	{
		f.setIcons(b.getIcon(0, 0), b.getIcon(2, 0));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerRenderInformation()
	{
		IReloadableResourceManager IRRM = (IReloadableResourceManager) Minecraft.getMinecraft().getResourceManager();
		IRRM.registerReloadListener(new GrassColorReloadListener());
		IRRM.registerReloadListener(new FoliageColorReloadListener());

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
		RenderingRegistry.registerEntityRenderingHandler(EntityPlayer.class, new RenderPlayerTFC());
		RenderingRegistry.registerEntityRenderingHandler(EntityProjectileTFC.class, new RenderArrow());
		RenderingRegistry.registerEntityRenderingHandler(EntityFishHookTFC.class, new RenderFish());

		RenderingRegistry.registerBlockHandler(TFCBlocks.clayGrassRenderId = RenderingRegistry.getNextAvailableRenderId(), new BlockRenderHandler());
		RenderingRegistry.registerBlockHandler(TFCBlocks.peatGrassRenderId = RenderingRegistry.getNextAvailableRenderId(), new BlockRenderHandler());
		RenderingRegistry.registerBlockHandler(TFCBlocks.sulfurRenderId = RenderingRegistry.getNextAvailableRenderId(), new BlockRenderHandler());
		RenderingRegistry.registerBlockHandler(TFCBlocks.woodSupportRenderIdH = RenderingRegistry.getNextAvailableRenderId(), new RenderSupportBeam());
		RenderingRegistry.registerBlockHandler(TFCBlocks.woodSupportRenderIdV = RenderingRegistry.getNextAvailableRenderId(), new RenderSupportBeam());
		RenderingRegistry.registerBlockHandler(TFCBlocks.grassRenderId = RenderingRegistry.getNextAvailableRenderId(), new BlockRenderHandler());
		RenderingRegistry.registerBlockHandler(TFCBlocks.oreRenderId = RenderingRegistry.getNextAvailableRenderId(), new RenderOre());
		RenderingRegistry.registerBlockHandler(TFCBlocks.moltenRenderId = RenderingRegistry.getNextAvailableRenderId(), new BlockRenderHandler());
		RenderingRegistry.registerBlockHandler(TFCBlocks.looseRockRenderId = RenderingRegistry.getNextAvailableRenderId(), new BlockRenderHandler());
		RenderingRegistry.registerBlockHandler(TFCBlocks.FirepitRenderId = RenderingRegistry.getNextAvailableRenderId(), new BlockRenderHandler());
		RenderingRegistry.registerBlockHandler(TFCBlocks.AnvilRenderId = RenderingRegistry.getNextAvailableRenderId(), new RenderAnvil());
		RenderingRegistry.registerBlockHandler(TFCBlocks.BellowsRenderId = RenderingRegistry.getNextAvailableRenderId(), new RenderBellows());
		RenderingRegistry.registerBlockHandler(TFCBlocks.ForgeRenderId = RenderingRegistry.getNextAvailableRenderId(), new BlockRenderHandler());
		RenderingRegistry.registerBlockHandler(TFCBlocks.sluiceRenderId = RenderingRegistry.getNextAvailableRenderId(), new BlockRenderHandler());
		RenderingRegistry.registerBlockHandler(TFCBlocks.woodFruitRenderId = RenderingRegistry.getNextAvailableRenderId(), new BlockRenderHandler());
		RenderingRegistry.registerBlockHandler(TFCBlocks.leavesFruitRenderId = RenderingRegistry.getNextAvailableRenderId(), new BlockRenderHandler());
		RenderingRegistry.registerBlockHandler(TFCBlocks.stairRenderId = RenderingRegistry.getNextAvailableRenderId(), new BlockRenderHandler());
		RenderingRegistry.registerBlockHandler(TFCBlocks.slabRenderId = RenderingRegistry.getNextAvailableRenderId(), new BlockRenderHandler());
		RenderingRegistry.registerBlockHandler(TFCBlocks.cropRenderId = RenderingRegistry.getNextAvailableRenderId(), new BlockRenderHandler());
		RenderingRegistry.registerBlockHandler(TFCBlocks.cookingPitRenderId = RenderingRegistry.getNextAvailableRenderId(), new BlockRenderHandler());
		RenderingRegistry.registerBlockHandler(TFCBlocks.leavesRenderId = RenderingRegistry.getNextAvailableRenderId(), new BlockRenderHandler());
		RenderingRegistry.registerBlockHandler(TFCBlocks.detailedRenderId = RenderingRegistry.getNextAvailableRenderId(), new BlockRenderHandler());
		RenderingRegistry.registerBlockHandler(TFCBlocks.FenceRenderId = RenderingRegistry.getNextAvailableRenderId(), new RenderFence());
		RenderingRegistry.registerBlockHandler(TFCBlocks.FenceGateRenderId = RenderingRegistry.getNextAvailableRenderId(), new RenderFenceGate());
		RenderingRegistry.registerBlockHandler(TFCBlocks.toolRackRenderId = RenderingRegistry.getNextAvailableRenderId(), new RenderToolRack());
		//RenderingRegistry.registerBlockHandler(TFCBlocks.foodPrepRenderId = RenderingRegistry.getNextAvailableRenderId(), new BlockRenderHandler());
		RenderingRegistry.registerBlockHandler(TFCBlocks.quernRenderId = RenderingRegistry.getNextAvailableRenderId(), new TESRQuern());
		RenderingRegistry.registerBlockHandler(TFCBlocks.fluidRenderId = RenderingRegistry.getNextAvailableRenderId(), new BlockRenderHandler());
		RenderingRegistry.registerBlockHandler(TFCBlocks.woodConstructRenderId = RenderingRegistry.getNextAvailableRenderId(), new RenderWoodConstruct());
		RenderingRegistry.registerBlockHandler(TFCBlocks.barrelRenderId = RenderingRegistry.getNextAvailableRenderId(), new RenderBarrel());
		RenderingRegistry.registerBlockHandler(TFCBlocks.standRenderId = RenderingRegistry.getNextAvailableRenderId(), new RenderStand());
		RenderingRegistry.registerBlockHandler(TFCBlocks.NestBoxRenderId = RenderingRegistry.getNextAvailableRenderId(), new RenderNestBox());
		RenderingRegistry.registerBlockHandler(TFCBlocks.potteryRenderId = RenderingRegistry.getNextAvailableRenderId(), new RenderPottery());
		RenderingRegistry.registerBlockHandler(TFCBlocks.tuyereRenderId = RenderingRegistry.getNextAvailableRenderId(), new RenderTuyere());
		RenderingRegistry.registerBlockHandler(TFCBlocks.crucibleRenderId = RenderingRegistry.getNextAvailableRenderId(), new RenderCrucible());
		RenderingRegistry.registerBlockHandler(TFCBlocks.pipeRenderId = RenderingRegistry.getNextAvailableRenderId(), new RenderPipeBasic());
		RenderingRegistry.registerBlockHandler(TFCBlocks.pipeValveRenderId = RenderingRegistry.getNextAvailableRenderId(), new RenderPipeBasic());
		RenderingRegistry.registerBlockHandler(TFCBlocks.seaWeedRenderId = RenderingRegistry.getNextAvailableRenderId(), new BlockRenderHandler());
		//RenderingRegistry.registerBlockHandler(TFCBlocks.berryRenderId = RenderingRegistry.getNextAvailableRenderId(), new RenderBerryBush());
		RenderingRegistry.registerBlockHandler(TFCBlocks.bloomeryRenderId = RenderingRegistry.getNextAvailableRenderId(), new RenderBloomery());
		RenderingRegistry.registerBlockHandler(TFCBlocks.metalsheetRenderId = RenderingRegistry.getNextAvailableRenderId(), new RenderMetalSheet());

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
	}

	@Override
	public void registerTileEntities(boolean b)
	{
		super.registerTileEntities(false);
		ClientRegistry.registerTileEntity(TileEntityChestTFC.class, TileEntityName.Chest, new TESRChest());
		ClientRegistry.registerTileEntity(TileEntityIngotPile.class, TileEntityName.IngotPile, new TESRIngotPile());
		ClientRegistry.registerTileEntity(TileEntityFirepit.class, TileEntityName.Firepit, new TESRFirepit());
		ClientRegistry.registerTileEntity(TESeaWeed.class, TileEntityName.SeaWeed, new TESRSeaWeed());
		//ModLoader.registerTileEntity(TileEntityBarrel.class, "barrel", new TileEntityBarrelRendererTFC());
		ClientRegistry.registerTileEntity(TileEntityPottery.class, TileEntityName.Pottery, new TESRPottery());
		ClientRegistry.registerTileEntity(TEFoodPrep.class, TileEntityName.FoodPrep, new TESRFoodPrep());
		ClientRegistry.registerTileEntity(TileEntityBellows.class, TileEntityName.Bellows, new TESRBellows());
		ClientRegistry.registerTileEntity(TileEntityToolRack.class, TileEntityName.ToolRack, new TESRToolrack());
		ClientRegistry.registerTileEntity(TileEntityAnvil.class, TileEntityName.Anvil, new TESRAnvil());
		ClientRegistry.registerTileEntity(TEWorldItem.class, TileEntityName.WorldItem, new TESRWorldItem());
		ClientRegistry.registerTileEntity(TileEntityQuern.class, TileEntityName.Quern, new TESRQuern());
	}

	@Override
	public void onClientLogin()
	{
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
	public File getMinecraftDir()
	{
		return Minecraft.getMinecraft().mcDataDir;
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
		int var5 = 0;
		int var6 = 0;
		int var7 = 0;
		int[] rgb = { 0, 0, 0 };

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
			int x = (rgb[0] / 9 & 255) << 16 | (rgb[1] / 9 & 255) << 8 | rgb[2] / 9 & 255;
			return x;
		}
		else if(par1IBlockAccess.getBlock(i, j, k) == Blocks.vine)
		{
			if (TFC_Time.getSeasonAdjustedMonth(k) >= 6 && TFC_Time.getSeasonAdjustedMonth(k) < 9
					&& !(((TFCWorldChunkManager) getCurrentWorld().provider.worldChunkMgr).getEVTLayerAt(i, k).floatdata1 < 0.8)
					&& TFC_Climate.getHeightAdjustedTemp(i, j, k) < 30)
			{
				int color = 0;
				for (int var8 = -1; var8 <= 1; ++var8)
				{
					for (int var9 = -1; var9 <= 1; ++var9)
					{
						color = (TFC_Climate.getFoliageColor(getCurrentWorld(), i + var8, j, k + var9));
						rgb = applyColor(color, rgb);
					}
				}
				int x = (rgb[0] / 9 & 255) << 16 | (rgb[1] / 9 & 255) << 8 | rgb[2] / 9 & 255;
				return x;
			}
			else if (TFC_Time.getSeasonAdjustedMonth(k) >= 11 || TFC_Time.getSeasonAdjustedMonth(k) <= 0
					&& !(((TFCWorldChunkManager) getCurrentWorld().provider.worldChunkMgr).getEVTLayerAt(i, k).floatdata1 < 0.8)
					&& TFC_Climate.getHeightAdjustedTemp(i, j, k) < 30)
			{
				for (int var8 = -1; var8 <= 1; ++var8)
				{
					for (int var9 = -1; var9 <= 1; ++var9)
					{
						int color = (TFC_Climate.getFoliageColor(getCurrentWorld(), i + var8, j, k + var9));
						rgb = applyColor(color, rgb);
					}
				}
				int x = (rgb[0] / 9 & 255) << 16 | (rgb[1] / 9 & 255) << 8 | rgb[2] / 9 & 255;
				return x;
			}
			else if (TFC_Time.getSeasonAdjustedMonth(k) >= 9 && !(((TFCWorldChunkManager) getCurrentWorld().provider.worldChunkMgr).getEVTLayerAt(i, k).floatdata1 < 0.8)
					&& TFC_Climate.getHeightAdjustedTemp(i, j, k) < 30)
			{
				for (int var8 = -1; var8 <= 1; ++var8)
				{
					for (int var9 = -1; var9 <= 1; ++var9)
					{
						int color = ColorizerFoliageTFC.getFoliageDead();
						rgb = applyColor(color, rgb);
					}
				}
				int x = (rgb[0] / 9 & 255) << 16 | (rgb[1] / 9 & 255) << 8 | rgb[2] / 9 & 255;
				return x;
			}
			else
			{
				for (int var8 = -1; var8 <= 1; ++var8)
				{
					for (int var9 = -1; var9 <= 1; ++var9)
					{
						int color = (TFC_Climate.getFoliageColor(getCurrentWorld(), i + var8, j, k + var9));
						rgb = applyColor(color, rgb);
					}
				}
				int x = (rgb[0] / 9 & 255) << 16 | (rgb[1] / 9 & 255) << 8 | rgb[2] / 9 & 255;
				return x;
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
			int x = (rgb[0] / 9 & 255) << 16 | (rgb[1] / 9 & 255) << 8 | rgb[2] / 9 & 255;
			return x;
		}
		else if (TFC_Time.getSeasonAdjustedMonth(k) >= 6 && TFC_Time.getSeasonAdjustedMonth(k) < 9 && (meta == 4 || meta == 7 || meta == 5 || meta == 14))
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
			int x = (rgb[0] / 9 & 255) << 16 | (rgb[1] / 9 & 255) << 8 | rgb[2] / 9 & 255;
			return x;
		}
		else if (TFC_Time.getSeasonAdjustedMonth(k) >= 6 && TFC_Time.getSeasonAdjustedMonth(k) < 9 && (meta == 6))
		{
			for (int var8 = -1; var8 <= 1; ++var8)
			{
				for (int var9 = -1; var9 <= 1; ++var9)
				{
					int var10 = ColorizerFoliageTFC.getFoliageRed();
					rgb = applyColor(var10, rgb);
				}
			}
			int x = (rgb[0] / 9 & 255) << 16 | (rgb[1] / 9 & 255) << 8 | rgb[2] / 9 & 255;
			return x;
		}
		else if (TFC_Time.getSeasonAdjustedMonth(k) >= 6 && TFC_Time.getSeasonAdjustedMonth(k) < 9 && !(meta == 15))
		{
			for (int var8 = -1; var8 <= 1; ++var8)
			{
				for (int var9 = -1; var9 <= 1; ++var9)
				{
					int var10 = ColorizerFoliageTFC.getFoliageOrange();
					rgb = applyColor(var10, rgb);
				}
			}
			int x = (rgb[0] / 9 & 255) << 16 | (rgb[1] / 9 & 255) << 8 | rgb[2] / 9 & 255;
			return x;
		}
		else if (TFC_Time.getSeasonAdjustedMonth(k) >= 6 && !(meta == 15))
		{
			for (int var8 = -1; var8 <= 1; ++var8)
			{
				for (int var9 = -1; var9 <= 1; ++var9)
				{
					int var10 = ColorizerFoliageTFC.getFoliageDead();
					rgb = applyColor(var10, rgb);
				}
			}
			int x = (rgb[0] / 9 & 255) << 16 | (rgb[1] / 9 & 255) << 8 | rgb[2] / 9 & 255;
			return x;
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
			int x = (rgb[0] / 9 & 255) << 16 | (rgb[1] / 9 & 255) << 8 | rgb[2] / 9 & 255;
			return x;
		}
	}

	private float getTimeMult(long day, long start, long end)
	{
		float total = end - start;
		return total - (day - start) / total;
	}

	private int[] applyColor(int c, int[] rgb)
	{
		rgb[0] += ((c & 16711680) >> 16);
		rgb[1] += ((c & 65280) >> 8);
		rgb[2] += (c & 255);
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
		KeyBindings.addKeyBinding(KeyBindingHandler.Key_ToolMode);
		KeyBindings.addIsRepeating(false);
		KeyBindings.addKeyBinding(KeyBindingHandler.Key_LockTool);
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
	public void registerHighlightHandler()
	{
		MinecraftForge.EVENT_BUS.register(new ChiselHighlightHandler());
		MinecraftForge.EVENT_BUS.register(new FarmlandHighlightHandler());
		MinecraftForge.EVENT_BUS.register(new PlankHighlightHandler());
		MinecraftForge.EVENT_BUS.register(new ArmourStandHighlightHandler());
	}

	@Override
	public void registerSoundHandler()
	{
		MinecraftForge.EVENT_BUS.register(new SoundHandler());
	}

	@Override
	public void registerTickHandler()
	{
		FMLCommonHandler.instance().bus().register(new ClientTickHandler());
		new ClientTickHandler();
	}

	@Override
	public void registerGuiHandler()
	{
		NetworkRegistry.INSTANCE.registerGuiHandler(TerraFirmaCraft.instance, new com.bioxx.tfc.Handlers.Client.GuiHandler());
		// Register Gui Event Handler
		MinecraftForge.EVENT_BUS.register(new com.bioxx.tfc.Handlers.Client.GuiHandler());
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
}
