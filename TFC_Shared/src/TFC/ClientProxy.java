package TFC;

import java.io.File;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelSlime;
import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.client.renderer.entity.RenderBlaze;
import net.minecraft.client.renderer.entity.RenderEnderman;
import net.minecraft.client.renderer.entity.RenderFallingSand;
import net.minecraft.client.renderer.entity.RenderGhast;
import net.minecraft.client.renderer.entity.RenderIronGolem;
import net.minecraft.client.renderer.entity.RenderMinecart;
import net.minecraft.client.renderer.entity.RenderSilverfish;
import net.minecraft.client.renderer.entity.RenderSlime;
import net.minecraft.client.renderer.entity.RenderSpider;
import net.minecraft.client.renderer.entity.RenderZombie;
import net.minecraft.client.resources.ReloadableResourceManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.src.ModLoader;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.common.MinecraftForge;
import TFC.API.Enums.EnumTree;
import TFC.API.Util.KeyBindings;
import TFC.Core.ColorizerFoliageTFC;
import TFC.Core.TFC_Climate;
import TFC.Core.TFC_Time;
import TFC.Core.Util.StringUtil;
import TFC.Entities.EntityCustomMinecart;
import TFC.Entities.EntityFallingStone;
import TFC.Entities.EntityJavelin;
import TFC.Entities.EntityProjectileTFC;
import TFC.Entities.EntityStand;
import TFC.Entities.Mobs.EntityBear;
import TFC.Entities.Mobs.EntityBlazeTFC;
import TFC.Entities.Mobs.EntityCaveSpiderTFC;
import TFC.Entities.Mobs.EntityChickenTFC;
import TFC.Entities.Mobs.EntityCowTFC;
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
import TFC.Handlers.BiomeEventHandler;
import TFC.Handlers.Client.ArmourStandHighlightHandler;
import TFC.Handlers.Client.BlockRenderHandler;
import TFC.Handlers.Client.ChiselHighlightHandler;
import TFC.Handlers.Client.FarmlandHighlightHandler;
import TFC.Handlers.Client.KeyBindingHandler;
import TFC.Handlers.Client.PlankHighlightHandler;
import TFC.Handlers.Client.RenderOverlayHandler;
import TFC.Handlers.Client.SoundHandler;
import TFC.Render.FoliageColorReloadListener;
import TFC.Render.GrassColorReloadListener;
import TFC.Render.RenderBear;
import TFC.Render.RenderChickenTFC;
import TFC.Render.RenderCowTFC;
import TFC.Render.RenderDeer;
import TFC.Render.RenderEntityStand;
import TFC.Render.RenderHorseTFC;
import TFC.Render.RenderPheasantTFC;
import TFC.Render.RenderPigTFC;
import TFC.Render.RenderSheepTFC;
import TFC.Render.RenderSkeletonTFC;
import TFC.Render.RenderSquidTFC;
import TFC.Render.RenderTerraJavelin;
import TFC.Render.RenderWolfTFC;
import TFC.Render.Blocks.RenderAnvil;
import TFC.Render.Blocks.RenderBarrel;
import TFC.Render.Blocks.RenderBellows;
import TFC.Render.Blocks.RenderCrucible;
import TFC.Render.Blocks.RenderFence;
import TFC.Render.Blocks.RenderFenceGate;
import TFC.Render.Blocks.RenderNestBox;
import TFC.Render.Blocks.RenderOre;
import TFC.Render.Blocks.RenderPipeBasic;
import TFC.Render.Blocks.RenderPottery;
import TFC.Render.Blocks.RenderQuern;
import TFC.Render.Blocks.RenderStand;
import TFC.Render.Blocks.RenderSupportBeam;
import TFC.Render.Blocks.RenderToolRack;
import TFC.Render.Blocks.RenderTuyere;
import TFC.Render.Blocks.RenderWoodConstruct;
import TFC.Render.Models.ModelBear;
import TFC.Render.Models.ModelChickenTFC;
import TFC.Render.Models.ModelCowTFC;
import TFC.Render.Models.ModelDeer;
import TFC.Render.Models.ModelHorseTFC;
import TFC.Render.Models.ModelPheasant;
import TFC.Render.Models.ModelPigTFC;
import TFC.Render.Models.ModelSheep1TFC;
import TFC.Render.Models.ModelSheep2TFC;
import TFC.Render.Models.ModelSquidTFC;
import TFC.Render.Models.ModelWolfTFC;
import TFC.Render.TESR.TESRAnvil;
import TFC.Render.TESR.TESRBellows;
import TFC.Render.TESR.TESRChest;
import TFC.Render.TESR.TESRFirepit;
import TFC.Render.TESR.TESRFoodPrep;
import TFC.Render.TESR.TESRIngotPile;
import TFC.Render.TESR.TESRPottery;
import TFC.Render.TESR.TESRSeaWeed;
import TFC.Render.TESR.TESRToolrack;
import TFC.TileEntities.TESeaWeed;
import TFC.TileEntities.TileEntityAnvil;
import TFC.TileEntities.TileEntityBellows;
import TFC.TileEntities.TileEntityChestTFC;
import TFC.TileEntities.TileEntityFirepit;
import TFC.TileEntities.TileEntityFoodPrep;
import TFC.TileEntities.TileEntityIngotPile;
import TFC.TileEntities.TileEntityPottery;
import TFC.TileEntities.TileEntityToolRack;
import TFC.WorldGen.TFCWorldChunkManager;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ClientProxy extends CommonProxy 
{	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerRenderInformation() 
	{
		((ReloadableResourceManager)Minecraft.getMinecraft().getResourceManager()).registerReloadListener(new GrassColorReloadListener());
		((ReloadableResourceManager)Minecraft.getMinecraft().getResourceManager()).registerReloadListener(new FoliageColorReloadListener());

		RenderingRegistry.registerEntityRenderingHandler(EntityJavelin.class, new RenderTerraJavelin());
		RenderingRegistry.registerEntityRenderingHandler(EntitySquidTFC.class, new RenderSquidTFC(new ModelSquidTFC(), 0.7F));
		RenderingRegistry.registerEntityRenderingHandler(EntityCowTFC.class, new RenderCowTFC(new ModelCowTFC(), 0.7F));
		RenderingRegistry.registerEntityRenderingHandler(EntitySheepTFC.class, new RenderSheepTFC(new ModelSheep2TFC(),new ModelSheep1TFC(), 0.4F));
		RenderingRegistry.registerEntityRenderingHandler(EntityWolfTFC.class, new RenderWolfTFC(new ModelWolfTFC(),new ModelWolfTFC(), 0.5F));
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
		//RenderingRegistry.registerEntityRenderingHandler(EntityPlayer.class, new RenderPlayerTFC());

		RenderingRegistry.registerEntityRenderingHandler(EntityProjectileTFC.class, new RenderArrow());
		RenderingRegistry.registerEntityRenderingHandler(EntityFallingStone.class, new RenderFallingSand());

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
		RenderingRegistry.registerBlockHandler(TFCBlocks.ScribeRenderId = RenderingRegistry.getNextAvailableRenderId(), new BlockRenderHandler());
		RenderingRegistry.registerBlockHandler(TFCBlocks.ForgeRenderId = RenderingRegistry.getNextAvailableRenderId(), new BlockRenderHandler());
		RenderingRegistry.registerBlockHandler(TFCBlocks.sluiceRenderId = RenderingRegistry.getNextAvailableRenderId(), new BlockRenderHandler());
		RenderingRegistry.registerBlockHandler(TFCBlocks.woodFruitRenderId = RenderingRegistry.getNextAvailableRenderId(), new BlockRenderHandler());
		RenderingRegistry.registerBlockHandler(TFCBlocks.leavesFruitRenderId = RenderingRegistry.getNextAvailableRenderId(), new BlockRenderHandler());
		RenderingRegistry.registerBlockHandler(TFCBlocks.finiteWaterRenderId = RenderingRegistry.getNextAvailableRenderId(), new BlockRenderHandler());
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
		RenderingRegistry.registerBlockHandler(TFCBlocks.quernRenderId = RenderingRegistry.getNextAvailableRenderId(), new RenderQuern());
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
		ClientRegistry.registerTileEntity(TileEntityChestTFC.class, "chest", new TESRChest());
		ClientRegistry.registerTileEntity(TileEntityIngotPile.class, "ingotPile2",new TESRIngotPile());
		ClientRegistry.registerTileEntity(TileEntityFirepit.class, "firepit",new TESRFirepit());
		ClientRegistry.registerTileEntity(TESeaWeed.class, "seaweed",new TESRSeaWeed());
		//ModLoader.registerTileEntity(TileEntityBarrel.class, "barrel", new TileEntityBarrelRendererTFC());
		ClientRegistry.registerTileEntity(TileEntityPottery.class, "Pottery", new TESRPottery());
		ClientRegistry.registerTileEntity(TileEntityFoodPrep.class, "FoodPrep", new TESRFoodPrep());
		ClientRegistry.registerTileEntity(TileEntityBellows.class, "Bellows", new TESRBellows());
		ClientRegistry.registerTileEntity(TileEntityToolRack.class, "ToolRack", new TESRToolrack());
		ClientRegistry.registerTileEntity(TileEntityAnvil.class, "Anvil", new TESRAnvil());
	}

	@Override
	public void onClientLogin()
	{

	}

	@Override
	public World getCurrentWorld() {
		return ModLoader.getMinecraftInstance().theWorld;
	}

	@Override
	public boolean isRemote() {
		return true;
	}

	@Override
	public File getMinecraftDir() {
		return ModLoader.getMinecraftInstance().mcDataDir;
	}

	private BiomeGenBase lastBiomeGen;
	private int waterColorMultiplier;

	@Override
	public int waterColorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		int var5 = 0;
		int var6 = 0;
		int var7 = 0;

		for (int var8 = -1; var8 <= 1; ++var8)
		{
			for (int var9 = -1; var9 <= 1; ++var9)
			{
				BiomeGenBase biome = par1IBlockAccess.getBiomeGenForCoords(par2 + var9, par4 + var8);

				if(biome != null)
				{
					if(lastBiomeGen != biome)
					{
						waterColorMultiplier = biome.getWaterColorMultiplier();
						lastBiomeGen = biome;
					}

					var5 += (waterColorMultiplier & 16711680) >> 16;
			var6 += (waterColorMultiplier & 65280) >> 8;
			var7 += waterColorMultiplier & 255;
				}
			}
		}
		return (var5 / 9 & 255) << 16 | (var6 / 9 & 255) << 8 | var7 / 9 & 255;
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
				int var10 = TFC_Climate.getGrassColor(getCurrentWorld(), i + x, j ,k + z);
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

		int[] rgb = {0,0,0};

		int meta = par1IBlockAccess.getBlockMetadata(i, j, k);
		if(par1IBlockAccess.getBlockId(i, j, k) == TFCBlocks.fruitTreeLeaves.blockID)
		{
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
						int var10 = TFC_Climate.getFoliageColor(getCurrentWorld(), i + var8, j ,k + var9);
						rgb = applyColor(var10, rgb);
					}
				}

				int x = (rgb[0] / 9 & 255) << 16 | (rgb[1] / 9 & 255) << 8 | rgb[2] / 9 & 255;
				return x;
			}
		}
		else if(par1IBlockAccess.getBlockId(i, j, k) == Block.vine.blockID)
		{
			if(TFC_Time.getSeason(k) >= 6 && TFC_Time.getSeason(k) < 9 && 
					!(((TFCWorldChunkManager)getCurrentWorld().provider.worldChunkMgr).getEVTLayerAt(i, k).floatdata1 < 0.8) && TFC_Climate.getHeightAdjustedTemp(i, j, k) < 30)
			{
				int color = 0;
				for (int var8 = -1; var8 <= 1; ++var8)
				{
					for (int var9 = -1; var9 <= 1; ++var9)
					{
						color = (TFC_Climate.getFoliageColor(getCurrentWorld(), i + var8, j ,k + var9));
						rgb = applyColor(color, rgb);
					}
				}

				int x = (rgb[0] / 9 & 255) << 16 | (rgb[1] / 9 & 255) << 8 | rgb[2] / 9 & 255;
				return x;
			}
			else if(TFC_Time.getSeason(k) >= 11 || TFC_Time.getSeason(k) <= 0 && 
					!(((TFCWorldChunkManager)getCurrentWorld().provider.worldChunkMgr).getEVTLayerAt(i, k).floatdata1 < 0.8) && TFC_Climate.getHeightAdjustedTemp(i, j, k) < 30)
			{
				for (int var8 = -1; var8 <= 1; ++var8)
				{
					for (int var9 = -1; var9 <= 1; ++var9)
					{
						int color = (TFC_Climate.getFoliageColor(getCurrentWorld(), i + var8, j ,k + var9));
						rgb = applyColor(color, rgb);
					}
				}

				int x = (rgb[0] / 9 & 255) << 16 | (rgb[1] / 9 & 255) << 8 | rgb[2] / 9 & 255;
				return x;

			}
			else if(TFC_Time.getSeason(k) >= 9 && 
					!(((TFCWorldChunkManager)getCurrentWorld().provider.worldChunkMgr).getEVTLayerAt(i, k).floatdata1 < 0.8) && TFC_Climate.getHeightAdjustedTemp(i, j, k) < 30)
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
						int color = (TFC_Climate.getFoliageColor(getCurrentWorld(), i + var8, j ,k + var9));
						rgb = applyColor(color, rgb);
					}
				}
				int x = (rgb[0] / 9 & 255) << 16 | (rgb[1] / 9 & 255) << 8 | rgb[2] / 9 & 255;
				return x;
			}
		}
		else
		{
			if(TFC_Time.getSeason(k) >= 6 && EnumTree.values()[meta].isEvergreen)
			{
				for (int var8 = -1; var8 <= 1; ++var8)
				{
					for (int var9 = -1; var9 <= 1; ++var9)
					{
						int var10 = TFC_Climate.getFoliageColor(getCurrentWorld(), i + var8, j ,k + var9);
						rgb = applyColor(var10, rgb);
					}
				}

				int x = (rgb[0] / 9 & 255) << 16 | (rgb[1] / 9 & 255) << 8 | rgb[2] / 9 & 255;
				return x;
			}
			else if(TFC_Time.getSeason(k) >= 6 && TFC_Time.getSeason(k) < 9 && (meta == 4 || meta == 7 || meta == 5 || meta == 14))
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
			else if(TFC_Time.getSeason(k) >= 6 && TFC_Time.getSeason(k) < 9 && (meta == 6))
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
			else if(TFC_Time.getSeason(k) >= 6 && TFC_Time.getSeason(k) < 9 && !(meta == 15))
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
			else if(TFC_Time.getSeason(k) >= 6 && !(meta == 15))
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
						int var10 = TFC_Climate.getFoliageColor(getCurrentWorld(), i + var8, j ,k + var9);
						rgb = applyColor(var10, rgb);
					}
				}

				int x = (rgb[0] / 9 & 255) << 16 | (rgb[1] / 9 & 255) << 8 | rgb[2] / 9 & 255;
				return x;
			}
		}
	}

	private float getTimeMult(long day, long start, long end)
	{
		float total = end - start;
		return total-(day-start)/total;
	}
	private int[] applyColor(int c, int[] rgb)
	{        
		rgb[0] += ((c & 16711680) >> 16);
		rgb[1] += ((c & 65280) >> 8);
		rgb[2] += (c & 255);

		return rgb;
	}

	@Override
	public void sendCustomPacket(Packet packet)
	{
		ModLoader.sendPacket(packet);     
	}

	@Override
	public int getArmorRenderID(String name)
	{
		return RenderingRegistry.addNewArmourRendererPrefix(name);
	}

	@Override
	public void registerKeys()
	{
		KeyBindings.addKeyBinding("Key_Calendar", 49);
		KeyBindings.addIsRepeating(false);
		KeyBindings.addKeyBinding("Key_ToolMode", 50);
		KeyBindings.addIsRepeating(false);
		KeyBindings.addKeyBinding("Key_LockTool", 38);
		KeyBindings.addIsRepeating(false);
	}

	@Override
	public void registerKeyBindingHandler()
	{
		KeyBindingRegistry.registerKeyBinding(new KeyBindingHandler());
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
	public void registerSoundHandler() {
		MinecraftForge.EVENT_BUS.register(new SoundHandler());
	}

	@Override
	public void registerGuiHandler() {
		NetworkRegistry.instance().registerGuiHandler(this, new TFC.Handlers.Client.GuiHandler());
		// Register Gui Event Handler
		MinecraftForge.EVENT_BUS.register(new TFC.Handlers.Client.GuiHandler());
	}

	@Override
	public String getCurrentLanguage()
	{
		return Minecraft.getMinecraft().getLanguageManager().getCurrentLanguage().getLanguageCode();
	}

	@Override
	public void registerTranslations() {
		LanguageRegistry LR = LanguageRegistry.instance();
		LR.addStringLocalization("entity.Bear.name", StringUtil.localize("entity.Bear"));
		LR.addStringLocalization("entity.Deer.name", StringUtil.localize("entity.Deer"));
		LR.addStringLocalization("entity.irongolem.name", StringUtil.localize("entity.irongolem"));
		LR.addStringLocalization("item.minecartChest.name", StringUtil.localize("item.minecartChest"));
		LR.addStringLocalization("Key_Calendar", StringUtil.localize("Key_Calendar"));
		LR.addStringLocalization("Key_ToolMode", StringUtil.localize("Key_ToolMode"));
		LR.addStringLocalization("Key_LockTool", StringUtil.localize("Key_LockTool"));
		LR.addStringLocalization("generator.DEFAULT", StringUtil.localize("generator.DEFAULT"));
		LR.addStringLocalization("effect.bleed",StringUtil.localize("effect.bleed"));

	}

	@Override
	public boolean getGraphicsLevel()
	{
		// TODO Auto-generated method stub
		return ModLoader.getMinecraftInstance().isFancyGraphicsEnabled();
	}

}
