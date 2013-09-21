package TFC;

import java.io.File;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelSlime;
import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.client.renderer.entity.RenderBlaze;
import net.minecraft.client.renderer.entity.RenderEnderman;
import net.minecraft.client.renderer.entity.RenderGhast;
import net.minecraft.client.renderer.entity.RenderIronGolem;
import net.minecraft.client.renderer.entity.RenderMinecart;
import net.minecraft.client.renderer.entity.RenderSilverfish;
import net.minecraft.client.renderer.entity.RenderSlime;
import net.minecraft.client.renderer.entity.RenderSpider;
import net.minecraft.client.renderer.entity.RenderZombie;
import net.minecraft.client.resources.ReloadableResourceManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet;
import net.minecraft.src.ModLoader;
import net.minecraft.tileentity.TileEntity;
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
import TFC.Core.Player.PlayerManagerTFC;
import TFC.Core.Util.StringUtil;
import TFC.Entities.EntityCustomMinecart;
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
import TFC.GUI.GuiAnvil;
import TFC.GUI.GuiBarrel;
import TFC.GUI.GuiBlastFurnace;
import TFC.GUI.GuiBlueprint;
import TFC.GUI.GuiCalendar;
import TFC.GUI.GuiChestTFC;
import TFC.GUI.GuiCrucible;
import TFC.GUI.GuiFirepit;
import TFC.GUI.GuiFoodPrep;
import TFC.GUI.GuiForge;
import TFC.GUI.GuiInventoryTFC;
import TFC.GUI.GuiKnapping;
import TFC.GUI.GuiLogPile;
import TFC.GUI.GuiMold;
import TFC.GUI.GuiQuern;
import TFC.GUI.GuiScribe;
import TFC.GUI.GuiSluice;
import TFC.GUI.GuiVessel;
import TFC.GUI.GuiVesselLiquid;
import TFC.GUI.GuiWorkbench;
import TFC.Handlers.BiomeEventHandler;
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
import TFC.Render.RenderPigTFC;
import TFC.Render.RenderPlayerTFC;
import TFC.Render.RenderSheepTFC;
import TFC.Render.RenderSkeletonTFC;
import TFC.Render.RenderSquidTFC;
import TFC.Render.RenderTerraJavelin;
import TFC.Render.RenderWolfTFC;
import TFC.Render.TileEntityBellowsRenderer;
import TFC.Render.TileEntityChestRendererTFC;
import TFC.Render.TileEntityFoodPrepRenderer;
import TFC.Render.TileEntityIngotPileRenderer;
import TFC.Render.TileEntityPotteryRenderer;
import TFC.Render.Blocks.RenderAnvil;
import TFC.Render.Blocks.RenderBarrel;
import TFC.Render.Blocks.RenderBellows;
import TFC.Render.Blocks.RenderCrucible;
import TFC.Render.Blocks.RenderOre;
import TFC.Render.Blocks.RenderPottery;
import TFC.Render.Blocks.RenderQuern;
import TFC.Render.Blocks.RenderSupportBeam;
import TFC.Render.Blocks.RenderToolRack;
import TFC.Render.Blocks.RenderTuyere;
import TFC.Render.Models.ModelBear;
import TFC.Render.Models.ModelChickenTFC;
import TFC.Render.Models.ModelCowTFC;
import TFC.Render.Models.ModelDeer;
import TFC.Render.Models.ModelPigTFC;
import TFC.Render.Models.ModelSheep1TFC;
import TFC.Render.Models.ModelSheep2TFC;
import TFC.Render.Models.ModelSquidTFC;
import TFC.Render.Models.ModelWolfTFC;
import TFC.TileEntities.TEBlastFurnace;
import TFC.TileEntities.TECrucible;
import TFC.TileEntities.TileEntityAnvil;
import TFC.TileEntities.TileEntityBarrel;
import TFC.TileEntities.TileEntityBellows;
import TFC.TileEntities.TileEntityChestTFC;
import TFC.TileEntities.TileEntityFirepit;
import TFC.TileEntities.TileEntityFoodPrep;
import TFC.TileEntities.TileEntityForge;
import TFC.TileEntities.TileEntityIngotPile;
import TFC.TileEntities.TileEntityLogPile;
import TFC.TileEntities.TileEntityPottery;
import TFC.TileEntities.TileEntityQuern;
import TFC.TileEntities.TileEntityScribe;
import TFC.TileEntities.TileEntitySluice;
import TFC.TileEntities.TileEntityWorkbench;
import TFC.WorldGen.TFCWorldChunkManager;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
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
		RenderingRegistry.registerEntityRenderingHandler(EntityChickenTFC.class, new RenderChickenTFC(new ModelChickenTFC(), 0.3F));
		RenderingRegistry.registerEntityRenderingHandler(EntityPigTFC.class, new RenderPigTFC(new ModelPigTFC(), new ModelPigTFC(0.5F), 0.7F));
		RenderingRegistry.registerEntityRenderingHandler(EntityDeer.class, new RenderDeer(new ModelDeer(), 0.9F));
		RenderingRegistry.registerEntityRenderingHandler(EntityCustomMinecart.class, new RenderMinecart());
		RenderingRegistry.registerEntityRenderingHandler(EntityStand.class,new RenderPlayerTFC());

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
		RenderingRegistry.registerBlockHandler(TFCBlocks.toolRackRenderId = RenderingRegistry.getNextAvailableRenderId(), new RenderToolRack());
		//RenderingRegistry.registerBlockHandler(TFCBlocks.foodPrepRenderId = RenderingRegistry.getNextAvailableRenderId(), new BlockRenderHandler());
		RenderingRegistry.registerBlockHandler(TFCBlocks.quernRenderId = RenderingRegistry.getNextAvailableRenderId(), new RenderQuern());
		RenderingRegistry.registerBlockHandler(TFCBlocks.fluidRenderId = RenderingRegistry.getNextAvailableRenderId(), new BlockRenderHandler());
		RenderingRegistry.registerBlockHandler(TFCBlocks.woodConstructRenderId = RenderingRegistry.getNextAvailableRenderId(), new BlockRenderHandler());
		RenderingRegistry.registerBlockHandler(TFCBlocks.barrelRenderId = RenderingRegistry.getNextAvailableRenderId(), new RenderBarrel());
		RenderingRegistry.registerBlockHandler(TFCBlocks.potteryRenderId = RenderingRegistry.getNextAvailableRenderId(), new RenderPottery());
		RenderingRegistry.registerBlockHandler(TFCBlocks.tuyereRenderId = RenderingRegistry.getNextAvailableRenderId(), new RenderTuyere());
		RenderingRegistry.registerBlockHandler(TFCBlocks.crucibleRenderId = RenderingRegistry.getNextAvailableRenderId(), new RenderCrucible());

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
		ClientRegistry.registerTileEntity(TileEntityChestTFC.class, "chest", new TileEntityChestRendererTFC());
		ClientRegistry.registerTileEntity(TileEntityIngotPile.class, "ingotPile2",new TileEntityIngotPileRenderer());
		//ModLoader.registerTileEntity(TileEntityBarrel.class, "barrel", new TileEntityBarrelRendererTFC());
		ClientRegistry.registerTileEntity(TileEntityPottery.class, "Pottery", new TileEntityPotteryRenderer());
		ClientRegistry.registerTileEntity(TileEntityFoodPrep.class, "FoodPrep", new TileEntityFoodPrepRenderer());
		ClientRegistry.registerTileEntity(TileEntityBellows.class, "Bellows", new TileEntityBellowsRenderer());
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
			return new GuiLogPile(player.inventory, (TileEntityLogPile) te, world, x, y, z);
		}
		case 1:
		{
			return new GuiWorkbench(player.inventory, (TileEntityWorkbench) te, world, x, y, z);
		}
		case 19:
		{
			return new GuiVesselLiquid(player.inventory, world, x, y, z);
		}
		case 20:
		{
			return new GuiFirepit(player.inventory, (TileEntityFirepit) te, world, x, y, z);
		}
		case 21:
		{
			return new GuiAnvil(player.inventory, (TileEntityAnvil) te, world, x, y, z);
		}
		case 22:
		{
			return new GuiScribe(player.inventory, (TileEntityScribe) te, world, x, y, z);
		}
		case 23:
		{
			return new GuiForge(player.inventory, (TileEntityForge) te, world, x, y, z);
		}
		case 24:
		{
			return null;//was metallurgy table
		}
		case 25:
		{
			return new GuiSluice(player.inventory, (TileEntitySluice) te, world, x, y, z);
		}
		case 26:
		{
			return new GuiBlastFurnace(player.inventory, (TEBlastFurnace) te, world, x, y, z);
		}
		case 27:
		{
			return new GuiCalendar(player, world, x, y, z);
		}
		case 28:
		{
			return new GuiKnapping(player.inventory, PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(player).specialCraftingType , world, x, y, z);
		}
		case 29:
		{
			return new GuiChestTFC(player.inventory, ((TileEntityChestTFC) te), world, x, y, z);
		}
		case 31:
		{
			return new GuiInventoryTFC(player);
		}
		case 32:
		{
			return new GuiFoodPrep(player.inventory, ((TileEntityFoodPrep) te), world, x, y, z);
		}
		case 33:
		{
			return new GuiQuern(player.inventory, ((TileEntityQuern) te), world, x, y, z);
		}
		case 34:
		{
			return new GuiBlueprint(player, world, x, y, z);
		}
		case 35:
		{
			return new GuiBarrel(player.inventory,((TileEntityBarrel)te),world,x,y,z);
		}
		case 36:
		{
			return null;
		}
		case 37:
		{
			return new GuiCrucible(player.inventory,((TECrucible)te), world, x, y, z);
		}
		case 38:
		{
			return new GuiMold(player.inventory, world, x, y, z);
		}
		case 39:
		{
			return new GuiVessel(player.inventory, world, x, y, z);
		}

		default:
		{
			return null;
		}
		}
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
			if(TFC_Time.currentMonth >= TFC_Time.September && TFC_Time.currentMonth < TFC_Time.December && 
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
			else if(TFC_Time.currentMonth >= TFC_Time.February || TFC_Time.currentMonth <= TFC_Time.March && 
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
			else if(TFC_Time.currentMonth >= 9 && 
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
			if(TFC_Time.currentMonth >= TFC_Time.September && EnumTree.values()[meta].isEvergreen)
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
			else if(TFC_Time.currentMonth >= TFC_Time.September && TFC_Time.currentMonth < TFC_Time.December && (meta == 4 || meta == 7 || meta == 5 || meta == 14))
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
			else if(TFC_Time.currentMonth >= TFC_Time.September && TFC_Time.currentMonth < TFC_Time.December && (meta == 6))
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
			else if(TFC_Time.currentMonth >= TFC_Time.September && TFC_Time.currentMonth < TFC_Time.December && !(meta == 15))
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
			else if(TFC_Time.currentMonth >= TFC_Time.September && !(meta == 15))
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
	}

	@Override
	public void registerSoundHandler() {
		MinecraftForge.EVENT_BUS.register(new SoundHandler());
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
