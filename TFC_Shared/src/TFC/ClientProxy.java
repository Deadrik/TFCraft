package TFC;

import java.io.File;

import net.minecraft.block.Block;
import net.minecraft.client.model.ModelSlime;
import net.minecraft.client.model.ModelSquid;
import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.client.renderer.entity.RenderBlaze;
import net.minecraft.client.renderer.entity.RenderEnderman;
import net.minecraft.client.renderer.entity.RenderGhast;
import net.minecraft.client.renderer.entity.RenderIronGolem;
import net.minecraft.client.renderer.entity.RenderSilverfish;
import net.minecraft.client.renderer.entity.RenderSkeleton;
import net.minecraft.client.renderer.entity.RenderSlime;
import net.minecraft.client.renderer.entity.RenderSpider;
import net.minecraft.client.renderer.entity.RenderSquid;
import net.minecraft.client.renderer.entity.RenderZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet;
import net.minecraft.src.ModLoader;
import net.minecraft.src.PlayerAPI;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import TFC.Core.ColorizerFoliageTFC;
import TFC.Core.ColorizerGrassTFC;
import TFC.Core.KeyBindings;
import TFC.Core.TFC_Climate;
import TFC.Core.TFC_Time;
import TFC.Core.Player.PlayerManagerTFC;
import TFC.Core.Player.TFC_PlayerClient;
import TFC.Entities.EntityArrowTFC;
import TFC.Entities.EntityCustomMinecart;
import TFC.Entities.EntityStand;
import TFC.Entities.EntityTerraJavelin;
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
import TFC.Enums.EnumTree;
import TFC.GUI.GuiBarrel;
import TFC.GUI.GuiBlueprint;
import TFC.GUI.GuiCalendar;
import TFC.GUI.GuiChestTFC;
import TFC.GUI.GuiFoodPrep;
import TFC.GUI.GuiHUD;
import TFC.GUI.GuiInventoryTFC;
import TFC.GUI.GuiKnapping;
import TFC.GUI.GuiLeatherWorking;
import TFC.GUI.GuiQuern;
import TFC.GUI.GuiTerraAnvil;
import TFC.GUI.GuiTerraBloomery;
import TFC.GUI.GuiTerraFirepit;
import TFC.GUI.GuiTerraForge;
import TFC.GUI.GuiTerraLogPile;
import TFC.GUI.GuiTerraMetallurgy;
import TFC.GUI.GuiTerraScribe;
import TFC.GUI.GuiTerraSluice;
import TFC.GUI.GuiTerraWorkbench;
import TFC.Handlers.BlockRenderHandler;
import TFC.Handlers.ChiselHighlightHandler;
import TFC.Handlers.FarmlandHighlightHandler;
import TFC.Handlers.KeyBindingHandler;
import TFC.Handlers.PlankHighlightHandler;
import TFC.Handlers.SoundHandler;
import TFC.Render.ModelBear;
import TFC.Render.ModelChickenTFC;
import TFC.Render.ModelCowTFC;
import TFC.Render.ModelDeer;
import TFC.Render.ModelPigTFC;
import TFC.Render.ModelSheep1TFC;
import TFC.Render.ModelSheep2TFC;
import TFC.Render.ModelWolfTFC;
import TFC.Render.RenderBear;
import TFC.Render.RenderChickenTFC;
import TFC.Render.RenderCowTFC;
import TFC.Render.RenderCustomMinecart;
import TFC.Render.RenderDeer;
import TFC.Render.RenderPigTFC;
import TFC.Render.RenderPlayerTFC;
import TFC.Render.RenderSheepTFC;
import TFC.Render.RenderTerraJavelin;
import TFC.Render.RenderWolfTFC;
import TFC.Render.TileEntityChestRendererTFC;
import TFC.Render.TileEntityIngotPileRenderer;
import TFC.Render.Blocks.RenderAnvil;
import TFC.Render.Blocks.RenderBarrel;
import TFC.Render.Blocks.RenderCrucible;
import TFC.Render.Blocks.RenderOre;
import TFC.Render.Blocks.RenderQuern;
import TFC.Render.Blocks.RenderSupportBeam;
import TFC.Render.Blocks.RenderToolRack;
import TFC.TileEntities.TileEntityAnvil;
import TFC.TileEntities.TileEntityBarrel;
import TFC.TileEntities.TileEntityBloomery;
import TFC.TileEntities.TileEntityChestTFC;
import TFC.TileEntities.TileEntityFirepit;
import TFC.TileEntities.TileEntityFoodPrep;
import TFC.TileEntities.TileEntityForge;
import TFC.TileEntities.TileEntityIngotPile;
import TFC.TileEntities.TileEntityLogPile;
import TFC.TileEntities.TileEntityMetallurgy;
import TFC.TileEntities.TileEntityQuern;
import TFC.TileEntities.TileEntityScribe;
import TFC.TileEntities.TileEntitySluice;
import TFC.TileEntities.TileEntityWorkbench;
import TFC.WorldGen.TFCBiome;
import TFC.WorldGen.TFCWorldChunkManager;
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

		ColorizerFoliageTFC.getFoilageBiomeColorizer(ModLoader.getMinecraftInstance().renderEngine.getTextureContents("/misc/foliagecolor.png"));
		ColorizerGrassTFC.setGrassBiomeColorizer(ModLoader.getMinecraftInstance().renderEngine.getTextureContents("/misc/grasscolor.png"));

		RenderingRegistry.registerEntityRenderingHandler(EntityTerraJavelin.class, new RenderTerraJavelin());
		RenderingRegistry.registerEntityRenderingHandler(EntitySquidTFC.class, new RenderSquid(new ModelSquid(), 0.7F));
		RenderingRegistry.registerEntityRenderingHandler(EntityCowTFC.class, new RenderCowTFC(new ModelCowTFC(), 0.7F));
		RenderingRegistry.registerEntityRenderingHandler(EntitySheepTFC.class, new RenderSheepTFC(new ModelSheep2TFC(),new ModelSheep1TFC(), 0.7F));
		RenderingRegistry.registerEntityRenderingHandler(EntityWolfTFC.class, new RenderWolfTFC(new ModelWolfTFC(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(EntityBear.class, new RenderBear(new ModelBear(), 0.9F));
		RenderingRegistry.registerEntityRenderingHandler(EntityChickenTFC.class, new RenderChickenTFC(new ModelChickenTFC(), 0.3F));
		RenderingRegistry.registerEntityRenderingHandler(EntityPigTFC.class, new RenderPigTFC(new ModelPigTFC(), new ModelPigTFC(0.5F), 0.7F));
		RenderingRegistry.registerEntityRenderingHandler(EntityDeer.class, new RenderDeer(new ModelDeer(), 0.9F));
		RenderingRegistry.registerEntityRenderingHandler(EntityCustomMinecart.class, new RenderCustomMinecart());
		RenderingRegistry.registerEntityRenderingHandler(EntityStand.class,new RenderPlayerTFC());

		RenderingRegistry.registerEntityRenderingHandler(EntitySkeletonTFC.class, new RenderSkeleton());
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

		RenderingRegistry.registerEntityRenderingHandler(EntityArrowTFC.class, new RenderArrow());

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
		RenderingRegistry.registerBlockHandler(TFCBlocks.BellowsRenderId = RenderingRegistry.getNextAvailableRenderId(), new BlockRenderHandler());
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
		RenderingRegistry.registerBlockHandler(TFCBlocks.foodPrepRenderId = RenderingRegistry.getNextAvailableRenderId(), new BlockRenderHandler());
		RenderingRegistry.registerBlockHandler(TFCBlocks.quernRenderId = RenderingRegistry.getNextAvailableRenderId(), new RenderQuern());
		RenderingRegistry.registerBlockHandler(TFCBlocks.fluidRenderId = RenderingRegistry.getNextAvailableRenderId(), new BlockRenderHandler());
		RenderingRegistry.registerBlockHandler(TFCBlocks.woodConstructRenderId = RenderingRegistry.getNextAvailableRenderId(), new BlockRenderHandler());
		RenderingRegistry.registerBlockHandler(TFCBlocks.superDetailedRenderId = RenderingRegistry.getNextAvailableRenderId(), new BlockRenderHandler());
		RenderingRegistry.registerBlockHandler(TFCBlocks.crucibleRenderId = RenderingRegistry.getNextAvailableRenderId(), new RenderCrucible());
		RenderingRegistry.registerBlockHandler(TFCBlocks.barrelRenderId = RenderingRegistry.getNextAvailableRenderId(), new RenderBarrel());
	}

	@Override
	public void registerTileEntities(boolean b)
	{
		super.registerTileEntities(false);
		ModLoader.registerTileEntity(TileEntityChestTFC.class, "chest", new TileEntityChestRendererTFC());
		ModLoader.registerTileEntity(TileEntityIngotPile.class, "ingotPile2",new TileEntityIngotPileRenderer());
		//ModLoader.registerTileEntity(TileEntityBarrel.class, "barrel", new TileEntityBarrelRendererTFC());
	}

	@Override
	public void onClientLogin()
	{
		ModLoader.getMinecraftInstance().ingameGUI = new GuiHUD(ModLoader.getMinecraftInstance());
	}

	@Override
	public void RegisterPlayerApiClasses()
	{
		super.RegisterPlayerApiClasses();
		PlayerAPI.register("TFC Player Client", TFC_PlayerClient.class);
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
		return ModLoader.getMinecraftInstance().getMinecraftDir();
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
			return new GuiTerraLogPile(player.inventory, (TileEntityLogPile) te, world, x, y, z);
		}
		case 1:
		{
			return new GuiTerraWorkbench(player.inventory, (TileEntityWorkbench) te, world, x, y, z);
		}
		case 20:
		{
			return new GuiTerraFirepit(player.inventory, (TileEntityFirepit) te, world, x, y, z);
		}
		case 21:
		{
			return new GuiTerraAnvil(player.inventory, (TileEntityAnvil) te, world, x, y, z);
		}
		case 22:
		{
			return new GuiTerraScribe(player.inventory, (TileEntityScribe) te, world, x, y, z);
		}
		case 23:
		{
			return new GuiTerraForge(player.inventory, (TileEntityForge) te, world, x, y, z);
		}
		case 24:
		{
			return new GuiTerraMetallurgy(player.inventory, (TileEntityMetallurgy) te, world, x, y, z);
		}
		case 25:
		{
			return new GuiTerraSluice(player.inventory, (TileEntitySluice) te, world, x, y, z);
		}
		case 26:
		{
			return new GuiTerraBloomery(player.inventory, (TileEntityBloomery) te, world, x, y, z);
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
		case 30:
		{
			return new GuiHUD(ModLoader.getMinecraftInstance());
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
			return new GuiLeatherWorking(player.inventory, new ItemStack(TFCItems.FlatLeather,1) , world, x, y, z);
		}
		case 37:
		{
			//return new GuiScreenBookTFC(player.inventory, world, x, y, z);
		}

		}
		return null;
	}

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
				int var10 = ((TFCBiome)par1IBlockAccess.getBiomeGenForCoords(par2 + var9, par4 + var8)).waterColorMultiplier;
				var5 += (var10 & 16711680) >> 16;
			var6 += (var10 & 65280) >> 8;
			var7 += var10 & 255;
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
	public int getArmorRenderID(int i)
	{
		String[] Names = {"bismuth", "bismuthbronze", "blackbronze", "blacksteel", "bluesteel", "bronze", "copper", "wroughtiron", "redsteel", "rosegold", "steel", "tin", "zinc"};
		return ModLoader.addArmor(Names[i]);
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
	public void registerTranslations() 
	{
		LanguageRegistry LR = LanguageRegistry.instance();

		LR.addStringLocalization("Key_Calendar", "Open Calendar");
		LR.addStringLocalization("Key_ToolMode", "Cycle Tool Mode");
		LR.addStringLocalization("Key_LockTool", "Lock Tool Location");

		LR.addStringLocalization("generator.DEFAULT", "TFC Default");
		//Gems
		String[] GemNames = {"Ruby","Emerald","Topaz","Sapphire","Opal","Agate",
				"Jade","Garnet","Amethyst","Beryl","Jasper","Tourmaline","Diamond"};

		for(int i= 0; i < GemNames.length; i++)
		{
			LR.addStringLocalization("item."+GemNames[i]+".Chipped.name", "Chipped "+GemNames[i]);
			LR.addStringLocalization("item."+GemNames[i]+".Flawed.name", "Flawed "+GemNames[i]);
			LR.addStringLocalization("item."+GemNames[i]+".Normal.name",  GemNames[i]);
			LR.addStringLocalization("item."+GemNames[i]+".Flawless.name", "Flawless "+GemNames[i]);
			LR.addStringLocalization("item."+GemNames[i]+".Exquisite.name", "Exquisite "+GemNames[i]);
		}

		LR.addStringLocalization("item.coal.coal.name", "Coal");
		LR.addStringLocalization("item.coal.charcoal.name", "Charcoal");
		LR.addStringLocalization("item.javelin.name", "Javelin");

		LR.addStringLocalization("item.Wooden Bucket Empty.name", "Wooden Bucket (Empty)");
		LR.addStringLocalization("item.Wooden Bucket Water.name", "Wooden Bucket (Water)");
		LR.addStringLocalization("item.Wooden Bucket Milk.name", "Wooden Bucket (Milk)");

		LR.addStringLocalization("item.Red Steel Bucket Empty.name", "Red Steel Bucket (Empty)");
		LR.addStringLocalization("item.Red Steel Bucket Water.name", "Red Steel Bucket (Water)");

		LR.addStringLocalization("item.Blue Steel Bucket Empty.name", "Blue Steel Bucket (Empty)");
		LR.addStringLocalization("item.Blue Steel Bucket Lava.name", "Blue Steel Bucket (Lava)");

		LR.addStringLocalization("item.SluiceItem.name", "Sluice");

		LR.addStringLocalization("item.GoldPan.name", "Gold Pan");
		LR.addStringLocalization("item.GoldPan.Sand.name", "Gold Pan - Sand");
		LR.addStringLocalization("item.GoldPan.Gravel.name", "Gold Pan - Gravel");
		LR.addStringLocalization("item.GoldPan.Clay.name", "Gold Pan - Clay");
		LR.addStringLocalization("item.GoldPan.Dirt.name", "Gold Pan - Dirt");

		LR.addStringLocalization("item.Firestarter.name", "Firestarter");


		LR.addStringLocalization("item.Bellows.name", "Bellows");
		LR.addStringLocalization("tile.Bellows.name", "Bellows");
		LR.addStringLocalization("tile.Scribe.name", "Scribing Table");
		LR.addStringLocalization("item.Ink.name", "Marking");
		LR.addStringLocalization("item.Clay Mold.name", "Clay Mold");
		LR.addStringLocalization("item.Ceramic Mold.name", "Ceramic Mold");
		LR.addStringLocalization("tile.Forge.name", "Forge");
		LR.addStringLocalization("tile.Bloomery.name", "Bloomery");
		LR.addStringLocalization("tile.Metallurgy.name", "Metallurgy Table");

		LR.addStringLocalization("item.PickaxeHeadPlan.name", "Plan: Pickaxe Head");
		LR.addStringLocalization("item.ShovelHeadPlan.name", "Plan: Shovel Head");
		LR.addStringLocalization("item.HoeHeadPlan.name", "Plan: Hoe Head");
		LR.addStringLocalization("item.AxeHeadPlan.name", "Plan: Axe Head");
		LR.addStringLocalization("item.HammerHeadPlan.name", "Plan: Hammer Head");
		LR.addStringLocalization("item.ChiselHeadPlan.name", "Plan: Chisel Head");
		LR.addStringLocalization("item.SwordBladePlan.name", "Plan: Sword Blade");
		LR.addStringLocalization("item.MaceHeadPlan.name", "Plan: Mace Head");
		LR.addStringLocalization("item.SawBladePlan.name", "Plan: Saw Head");
		LR.addStringLocalization("item.ProPickHeadPlan.name", "Plan: Prospecter's Pick Head");
		LR.addStringLocalization("item.HelmetPlan.name", "Plan: Plate Helmet");
		LR.addStringLocalization("item.ChestplatePlan.name", "Plan: Chestplate");
		LR.addStringLocalization("item.GreavesPlan.name", "Plan: Plate Greaves");
		LR.addStringLocalization("item.BootsPlan.name", "Plan: Plate Boots");
		LR.addStringLocalization("item.ScythePlan.name", "Plan: Scythe Blade");
		LR.addStringLocalization("item.KnifePlan.name", "Plan: Knife Blade");
		LR.addStringLocalization("item.BucketPlan.name", "Plan: Bucket");

		LR.addStringLocalization("item.Coke.name", "Coke");
		LR.addStringLocalization("item.Flux.name", "Flux");

		LR.addStringLocalization("tile.SpawnMeter.name", "Protection Meter");
		LR.addStringLocalization("effect.bleed","Bleeding");
		LR.addStringLocalization("tile.Quern.name", "Quern Base");
		LR.addStringLocalization("item.Quern.name", "Handstone");

		LR.addStringLocalization("item.Blueprint.name", "Blueprint");
		LR.addStringLocalization("item.Spindle.name", "Spindle");
		LR.addStringLocalization("item.Clay Spindle.name", "Clay Spindle");
		LR.addStringLocalization("item.Spindle Head.name", "Spindle Head");
		LR.addStringLocalization("item.WoolYarn.name", "Wool Yarn");
		LR.addStringLocalization("item.Wool.name", "Wool");
		LR.addStringLocalization("item.WoolCloth.name", "Wool Cloth");
		LR.addStringLocalization("item.Mortar.name", "Mortar");
		LR.addStringLocalization("item.Lime Water.name", "Limewater");
		LR.addStringLocalization("item.Hide.name","Raw Hide");
		LR.addStringLocalization("item.Soaked Hide.name","Soaked Hide");
		LR.addStringLocalization("item.Scraped Hide.name","Scraped Hide");
		LR.addStringLocalization("item.Prep Hide.name","Prepared Hide");
		LR.addStringLocalization("item.TFC Leather.name","Leather");
		LR.addStringLocalization("item.Sheep Skin.name","Sheepskin");

		LR.addStringLocalization("itemGroup.TFCTools", "(TFC) Tools");
		LR.addStringLocalization("itemGroup.TFCMaterials", "(TFC) Materials");
		LR.addStringLocalization("itemGroup.TFCUnfinished", "(TFC) Unfinished Items");
		LR.addStringLocalization("itemGroup.TFCArmor", "(TFC) Armor");

		RegisterTerrain();
		RegisterMetal();
		RegisterFood();
		RegisterWood();
	}

	private void RegisterFood()
	{
		LanguageRegistry LR = LanguageRegistry.instance();
		String[] FruitTreeNames = {"Red Apple","Banana", "Orange", "Green Apple","Lemon","Olive","Cherry","Peach","Plum","Cacao"};
		for(int i= 0; i < FruitTreeNames.length; i++)
		{
			LR.addStringLocalization("item.FruitSapling1."+FruitTreeNames[i]+".name", FruitTreeNames[i] + " Tree Sapling");
			LR.addStringLocalization("item.FruitSapling2."+FruitTreeNames[i]+".name", FruitTreeNames[i] + " Tree Sapling");
			LR.addStringLocalization("item."+FruitTreeNames[i]+".name", FruitTreeNames[i]);
		}

		LR.addStringLocalization("item.Seeds Wheat.name", "Wheat Seeds");
		LR.addStringLocalization("item.Seeds Maize.name", "Maize Seeds");
		LR.addStringLocalization("item.Seeds Tomato.name", "Tomato Seeds");
		LR.addStringLocalization("item.Seeds Barley.name", "Barley Seeds");
		LR.addStringLocalization("item.Seeds Rye.name", "Rye Seeds");
		LR.addStringLocalization("item.Seeds Oat.name", "Oat Seeds");
		LR.addStringLocalization("item.Seeds Rice.name", "Rice Seeds");
		LR.addStringLocalization("item.Seeds Potato.name", "Potato Seeds");
		LR.addStringLocalization("item.Seeds Onion.name", "Onion Seeds");
		LR.addStringLocalization("item.Seeds Cabbage.name", "Cabbage Seeds");
		LR.addStringLocalization("item.Seeds Garlic.name", "Garlic Seeds");
		LR.addStringLocalization("item.Seeds Carrot.name", "Carrot Seeds");
		LR.addStringLocalization("item.Seeds Sugarcane.name", "Sugarcane Seeds");
		LR.addStringLocalization("item.Seeds Hemp.name", "Hemp Seeds");
		LR.addStringLocalization("item.Seeds Soybean.name", "Soybean Seeds");
		LR.addStringLocalization("item.Seeds Greenbean.name", "Greenbean Seeds");
		LR.addStringLocalization("item.Seeds Yam.name", "Yam Seeds");
		LR.addStringLocalization("item.Seeds Yellow Bell Pepper.name", "Yellow Bell Pepper Seeds");
		LR.addStringLocalization("item.Seeds Red Bell Pepper.name", "Red Bell Pepper Seeds");
		LR.addStringLocalization("item.Seeds Squash.name", "Squash Seeds");
		
		LR.addStringLocalization("item.Mutton Raw.name", "Raw Mutton");
		LR.addStringLocalization("item.Mutton Cooked.name", "Cooked Mutton");
		LR.addStringLocalization("item.Flat Leather.name","Leather");

		LR.addStringLocalization("item.Egg Cooked.name", "Cooked Egg");

		LR.addStringLocalization("item.Wheat Grain.name", "Wheat Grain");
		LR.addStringLocalization("item.Wheat Whole.name", "Wheat");
		LR.addStringLocalization("item.Wheat Ground.name", "Wheat Flour");
		LR.addStringLocalization("item.Wheat Dough.name", "Wheat Dough");
		LR.addStringLocalization("item.Barley Grain.name", "Barley Grain");
		LR.addStringLocalization("item.Barley Whole.name", "Barley");
		LR.addStringLocalization("item.Barley Ground.name", "Barley Flour");
		LR.addStringLocalization("item.Barley Dough.name", "Barley Dough");
		LR.addStringLocalization("item.Barley Bread.name", "Barley Bread");
		LR.addStringLocalization("item.Oat Grain.name", "Oat Grain");
		LR.addStringLocalization("item.Oat Whole.name", "Oat");
		LR.addStringLocalization("item.Oat Dough.name", "Oat Dough");
		LR.addStringLocalization("item.Oat Ground.name", "Oat Flour");
		LR.addStringLocalization("item.Oat Bread.name", "Oat Bread");
		LR.addStringLocalization("item.Rye Grain.name", "Rye Grain");
		LR.addStringLocalization("item.Rye Whole.name", "Rye");
		LR.addStringLocalization("item.Rye Ground.name", "Rye Flour");
		LR.addStringLocalization("item.Rye Dough.name", "Rye Dough");
		LR.addStringLocalization("item.Rye Bread.name", "Rye Bread");
		LR.addStringLocalization("item.Rice Grain.name", "Rice Grain");
		LR.addStringLocalization("item.Rice Whole.name", "Rice");
		LR.addStringLocalization("item.Rice Ground.name", "Rice Flour");
		LR.addStringLocalization("item.Rice Dough.name", "Rice Dough");
		LR.addStringLocalization("item.Rice Bread.name", "Rice Bread");
		LR.addStringLocalization("item.Cornmeal Ground.name", "Cornmeal");
		LR.addStringLocalization("item.Cornmeal Dough.name", "Cornmeal Dough");
		LR.addStringLocalization("item.Corn Bread.name", "Corn Bread");

		LR.addStringLocalization("item.Maize Ear.name", "Maize Ear");
		LR.addStringLocalization("item.Tomato.name", "Tomato");
		LR.addStringLocalization("item.Potato.name", "Potato");

		LR.addStringLocalization("item.Onion.name", "Onion");
		LR.addStringLocalization("item.Cabbage.name", "Cabbage");
		LR.addStringLocalization("item.Garlic.name", "Garlic");
		LR.addStringLocalization("item.Carrot.name", "Carrot");
		LR.addStringLocalization("item.Sugarcane.name", "Sugarcane");
		LR.addStringLocalization("item.Hemp.name", "Hemp");
		LR.addStringLocalization("item.Soybeans.name", "Soybeans");
		LR.addStringLocalization("item.Greenbeans.name", "Greenbeans");
		LR.addStringLocalization("item.Yam.name", "Yam");
		LR.addStringLocalization("item.Green Bell Pepper.name", "Green Bell Pepper");
		LR.addStringLocalization("item.Yellow Bell Pepper.name", "Yellow Bell Pepper");
		LR.addStringLocalization("item.Red Bell Pepper.name", "Red Bell Pepper");
		LR.addStringLocalization("item.Squash.name", "Squash");

		LR.addStringLocalization("item.MealGeneric.name", "Meal");

	}

	private void RegisterTerrain()
	{
		LanguageRegistry LR = LanguageRegistry.instance();
		String[] rockNames = {"Granite", "Diorite", "Gabbro", 
				"Siltstone", "Mudstone", "Shale", "Claystone", "Rock Salt", "Limestone", "Conglomerate", "Dolomite", "Chert", "Chalk", 
				"Rhyolite", "Basalt", "Andesite", "Dacite", 
				"Quartzite", "Slate", "Phyllite", "Schist", "Gneiss", "Marble"};

		for(int i= 0; i < rockNames.length; i++)
		{
			LR.addStringLocalization("item.LooseRock."+rockNames[i]+".name", rockNames[i] + " Rock");
			LR.addStringLocalization("item.ItemStoneBrick."+rockNames[i]+".name", rockNames[i] + " Brick");
			LR.addStringLocalization("item.FlatRock."+rockNames[i]+".name", rockNames[i] + " Rock");
			LR.addStringLocalization("tile.WallCobble."+rockNames[i]+".name", rockNames[i] + " Cobblestone Wall");
			LR.addStringLocalization("tile.WallRaw."+rockNames[i]+".name", rockNames[i] + " Wall");
			LR.addStringLocalization("tile.WallBrick."+rockNames[i]+".name", rockNames[i] + " Brick Wall");
			LR.addStringLocalization("tile.WallSmooth."+rockNames[i]+".name", rockNames[i] + " Smooth Wall");
		}

		LR.addStringLocalization("tile.IgInRock.Granite.name", "Granite");
		LR.addStringLocalization("tile.IgInRock.Diorite.name", "Diorite");
		LR.addStringLocalization("tile.IgInRock.Gabbro.name", "Gabbro");
		LR.addStringLocalization("tile.IgExRock.Rhyolite.name", "Rhyolite");
		LR.addStringLocalization("tile.IgExRock.Basalt.name", "Basalt");
		LR.addStringLocalization("tile.IgExRock.Andesite.name", "Andesite");
		LR.addStringLocalization("tile.IgExRock.Dacite.name", "Dacite");
		LR.addStringLocalization("tile.SedRock.Siltstone.name", "Siltstone");
		LR.addStringLocalization("tile.SedRock.Mudstone.name", "Mudstone");
		LR.addStringLocalization("tile.SedRock.Shale.name", "Shale");
		LR.addStringLocalization("tile.SedRock.Claystone.name", "Claystone");
		LR.addStringLocalization("tile.SedRock.Rock Salt.name", "Rock Salt");
		LR.addStringLocalization("tile.SedRock.Limestone.name", "Limestone");
		LR.addStringLocalization("tile.SedRock.Conglomerate.name", "Conglomerate");
		LR.addStringLocalization("tile.SedRock.Dolomite.name", "Dolomite");
		LR.addStringLocalization("tile.SedRock.Chert.name", "Chert");
		LR.addStringLocalization("tile.SedRock.Chalk.name", "Chalk");
		LR.addStringLocalization("tile.MMRock.Quartzite.name", "Quartzite");
		LR.addStringLocalization("tile.MMRock.Slate.name", "Slate");
		LR.addStringLocalization("tile.MMRock.Phyllite.name", "Phyllite");
		LR.addStringLocalization("tile.MMRock.Schist.name", "Schist");
		LR.addStringLocalization("tile.MMRock.Gneiss.name", "Gneiss");
		LR.addStringLocalization("tile.MMRock.Marble.name", "Marble");
		LR.addStringLocalization("tile.Ore.Native Copper.name", "Native Copper");
		LR.addStringLocalization("tile.Ore.Native Gold.name", "Native Gold");
		LR.addStringLocalization("tile.Ore.Native Platinum.name", "Native Platinum");
		LR.addStringLocalization("tile.Ore.Hematite.name", "Hematite");
		LR.addStringLocalization("tile.Ore.Native Silver.name", "Native Silver");
		LR.addStringLocalization("tile.Ore.Cassiterite.name", "Cassiterite");
		LR.addStringLocalization("tile.Ore.Galena.name", "Galena");
		LR.addStringLocalization("tile.Ore.Bismuthinite.name", "Bismuthinite");
		LR.addStringLocalization("tile.Ore.Garnierite.name", "Garnierite");
		LR.addStringLocalization("tile.Ore.Malachite.name", "Malachite");
		LR.addStringLocalization("tile.Ore.Magnetite.name", "Magnetite");
		LR.addStringLocalization("tile.Ore.Limonite.name", "Limonite");
		LR.addStringLocalization("tile.Ore.Sphalerite.name", "Sphalerite");
		LR.addStringLocalization("tile.Ore.Tetrahedrite.name", "Tetrahedrite");
		LR.addStringLocalization("tile.Ore.Bituminous Coal.name", "Bituminous Coal");
		LR.addStringLocalization("tile.Ore.Lignite.name", "Lignite");
		LR.addStringLocalization("tile.Ore.Kaolinite.name", "Kaolinite");
		LR.addStringLocalization("tile.Ore.Gypsum.name", "Gypsum");
		LR.addStringLocalization("tile.Ore.Satinspar.name", "Satinspar");
		LR.addStringLocalization("tile.Ore.Selenite.name", "Selenite");
		LR.addStringLocalization("tile.Ore.Graphite.name", "Graphite");
		LR.addStringLocalization("tile.Ore.Kimberlite.name", "Kimberlite");
		LR.addStringLocalization("tile.Ore.Petrified Wood.name", "Petrified Wood");
		LR.addStringLocalization("tile.Ore.Sulfur.name", "Sulfur");
		LR.addStringLocalization("tile.Ore.Jet.name", "Jet");
		LR.addStringLocalization("tile.Ore.Microcline.name", "Microcline");
		LR.addStringLocalization("tile.Ore.Pitchblende.name", "Pitchblende");
		LR.addStringLocalization("tile.Ore.Cinnabar.name", "Cinnabar");
		LR.addStringLocalization("tile.Ore.Cryolite.name", "Cryolite");
		LR.addStringLocalization("tile.Ore.Saltpeter.name", "Saltpeter");
		LR.addStringLocalization("tile.Ore.Serpentine.name", "Serpentine");
		LR.addStringLocalization("tile.Ore.Sylvite.name", "Sylvite");
		LR.addStringLocalization("tile.Ore.Borax.name", "Borax");
		LR.addStringLocalization("tile.Ore.Olivine.name", "Olivine");
		LR.addStringLocalization("tile.Ore.LapisLazuli.name", "Lapis Lazuli");

		LR.addStringLocalization("item.Ore.Native Copper.name", "Native Copper");
		LR.addStringLocalization("item.Ore.Native Gold.name", "Native Gold");
		LR.addStringLocalization("item.Ore.Native Platinum.name", "Native Platinum");
		LR.addStringLocalization("item.Ore.Hematite.name", "Hematite");
		LR.addStringLocalization("item.Ore.Native Silver.name", "Native Silver");
		LR.addStringLocalization("item.Ore.Cassiterite.name", "Cassiterite");
		LR.addStringLocalization("item.Ore.Galena.name", "Galena");
		LR.addStringLocalization("item.Ore.Bismuthinite.name", "Bismuthinite");
		LR.addStringLocalization("item.Ore.Garnierite.name", "Garnierite");
		LR.addStringLocalization("item.Ore.Malachite.name", "Malachite");
		LR.addStringLocalization("item.Ore.Magnetite.name", "Magnetite");
		LR.addStringLocalization("item.Ore.Limonite.name", "Limonite");
		LR.addStringLocalization("item.Ore.Sphalerite.name", "Sphalerite");
		LR.addStringLocalization("item.Ore.Tetrahedrite.name", "Tetrahedrite");
		LR.addStringLocalization("item.Ore.Bituminous Coal.name", "Bituminous Coal");
		LR.addStringLocalization("item.Ore.Lignite.name", "Lignite");
		LR.addStringLocalization("item.Ore.Kaolinite.name", "Kaolinite");
		LR.addStringLocalization("item.Ore.Gypsum.name", "Gypsum");
		LR.addStringLocalization("item.Ore.Satinspar.name", "Satinspar");
		LR.addStringLocalization("item.Ore.Selenite.name", "Selenite");
		LR.addStringLocalization("item.Ore.Graphite.name", "Graphite");
		LR.addStringLocalization("item.Ore.Kimberlite.name", "Kimberlite");
		LR.addStringLocalization("item.Ore.Petrified Wood.name", "Petrified Wood");
		LR.addStringLocalization("item.Ore.Sulfur.name", "Sulfur");
		LR.addStringLocalization("item.Ore.Jet.name", "Jet");
		LR.addStringLocalization("item.Ore.Microcline.name", "Microcline");
		LR.addStringLocalization("item.Ore.Pitchblende.name", "Pitchblende");
		LR.addStringLocalization("item.Ore.Cinnabar.name", "Cinnabar");
		LR.addStringLocalization("item.Ore.Cryolite.name", "Cryolite");
		LR.addStringLocalization("item.Ore.Saltpeter.name", "Saltpeter");
		LR.addStringLocalization("item.Ore.Serpentine.name", "Serpentine");
		LR.addStringLocalization("item.Ore.Sylvite.name", "Sylvite");
		LR.addStringLocalization("item.Ore.Borax.name", "Borax");
		LR.addStringLocalization("item.Ore.Olivine.name", "Olivine");
		LR.addStringLocalization("item.Ore.Lapis Lazuli.name", "Lapis Lazuli");
		LR.addStringLocalization("item.Ore.GalenaPartial.name", "Partially Smelted Galena");
		LR.addStringLocalization("item.Ore.TetrahedritePartial.name", "Partially Smelted Tetrahedrite");
		LR.addStringLocalization("item.Ore.MagnetitePartial.name", "Partially Smelted Magnetite");

		LR.addStringLocalization("item.Small Ore.Native Copper.name", "Native Copper Nugget");
		LR.addStringLocalization("item.Small Ore.Native Gold.name", "Native Gold Nugget");
		LR.addStringLocalization("item.Small Ore.Native Platinum.name", "Native Platinum Nugget");
		LR.addStringLocalization("item.Small Ore.Hematite.name", "Small Hematite");
		LR.addStringLocalization("item.Small Ore.Native Silver.name", "Native Silver Nugget");
		LR.addStringLocalization("item.Small Ore.Cassiterite.name", "Small Cassiterite");
		LR.addStringLocalization("item.Small Ore.Galena.name", "Small Galena");
		LR.addStringLocalization("item.Small Ore.Bismuthinite.name", "Small Bismuthinite");
		LR.addStringLocalization("item.Small Ore.Garnierite.name", "Small Garnierite");
		LR.addStringLocalization("item.Small Ore.Malachite.name", "Small Malachite");
		LR.addStringLocalization("item.Small Ore.Magnetite.name", "Small Magnetite");
		LR.addStringLocalization("item.Small Ore.Limonite.name", "Small Limonite");
		LR.addStringLocalization("item.Small Ore.Sphalerite.name", "Small Sphalerite");
		LR.addStringLocalization("item.Small Ore.Tetrahedrite.name", "Small Tetrahedrite");
		LR.addStringLocalization("item.Small Ore.GalenaPartial.name", "Small Partially Smelted Galena");
		LR.addStringLocalization("item.Small Ore.TetrahedritePartial.name", "Small Partially Smelted Tetrahedrite");
		LR.addStringLocalization("item.Small Ore.MagnetitePartial.name", "Small Partially Smelted Magnetite");

		//Cobble
		LR.addStringLocalization("tile.IgInRockCobble.Granite.name", "Granite Cobblestone");
		LR.addStringLocalization("tile.IgInRockCobble.Diorite.name", "Diorite Cobblestone");
		LR.addStringLocalization("tile.IgInRockCobble.Gabbro.name", "Gabbro Cobblestone");
		LR.addStringLocalization("tile.IgExRockCobble.Rhyolite.name", "Rhyolite Cobblestone");
		LR.addStringLocalization("tile.IgExRockCobble.Basalt.name", "Basalt Cobblestone");
		LR.addStringLocalization("tile.IgExRockCobble.Andesite.name", "Andesite Cobblestone");
		LR.addStringLocalization("tile.IgExRockCobble.Dacite.name", "Dacite Cobblestone");
		LR.addStringLocalization("tile.SedRockCobble.Siltstone.name", "Siltstone Cobblestone");
		LR.addStringLocalization("tile.SedRockCobble.Mudstone.name", "Mudstone Cobblestone");
		LR.addStringLocalization("tile.SedRockCobble.Shale.name", "Shale Cobblestone");
		LR.addStringLocalization("tile.SedRockCobble.Claystone.name", "Claystone Cobblestone");
		LR.addStringLocalization("tile.SedRockCobble.Rock Salt.name", "Rock Salt Cobblestone");
		LR.addStringLocalization("tile.SedRockCobble.Limestone.name", "Limestone Cobblestone");
		LR.addStringLocalization("tile.SedRockCobble.Conglomerate.name", "Conglomerate Cobblestone");
		LR.addStringLocalization("tile.SedRockCobble.Dolomite.name", "Dolomite Cobblestone");
		LR.addStringLocalization("tile.SedRockCobble.Chert.name", "Chert Cobblestone");
		LR.addStringLocalization("tile.SedRockCobble.Chalk.name", "Chalk Cobblestone");
		LR.addStringLocalization("tile.MMRockCobble.Quartzite.name", "Quartzite Cobblestone");
		LR.addStringLocalization("tile.MMRockCobble.Slate.name", "Slate Cobblestone");
		LR.addStringLocalization("tile.MMRockCobble.Phyllite.name", "Phyllite Cobblestone");
		LR.addStringLocalization("tile.MMRockCobble.Schist.name", "Schist Cobblestone");
		LR.addStringLocalization("tile.MMRockCobble.Gneiss.name", "Gneiss Cobblestone");
		LR.addStringLocalization("tile.MMRockCobble.Marble.name", "Marble Cobblestone");
		//Smooth Stone
		LR.addStringLocalization("tile.IgInRockSmooth.Granite.name", "Smooth Granite");
		LR.addStringLocalization("tile.IgInRockSmooth.Diorite.name", "Smooth Diorite");
		LR.addStringLocalization("tile.IgInRockSmooth.Gabbro.name", "Smooth Gabbro");
		LR.addStringLocalization("tile.IgExRockSmooth.Rhyolite.name", "Smooth Rhyolite");
		LR.addStringLocalization("tile.IgExRockSmooth.Basalt.name", "Smooth Basalt");
		LR.addStringLocalization("tile.IgExRockSmooth.Andesite.name", "Smooth Andesite");
		LR.addStringLocalization("tile.IgExRockSmooth.Dacite.name", "Smooth Dacite");
		LR.addStringLocalization("tile.SedRockSmooth.Siltstone.name", "Smooth Siltstone");
		LR.addStringLocalization("tile.SedRockSmooth.Mudstone.name", "Smooth Mudstone");
		LR.addStringLocalization("tile.SedRockSmooth.Shale.name", "Smooth Shale");
		LR.addStringLocalization("tile.SedRockSmooth.Claystone.name", "Smooth Claystone");
		LR.addStringLocalization("tile.SedRockSmooth.Rock Salt.name", "Smooth Rock Salt");
		LR.addStringLocalization("tile.SedRockSmooth.Limestone.name", "Smooth Limestone");
		LR.addStringLocalization("tile.SedRockSmooth.Conglomerate.name", "Smooth Conglomerate");
		LR.addStringLocalization("tile.SedRockSmooth.Dolomite.name", "Smooth Dolomite");
		LR.addStringLocalization("tile.SedRockSmooth.Chert.name", "Smooth Chert");
		LR.addStringLocalization("tile.SedRockSmooth.Chalk.name", "Smooth Chalk");
		LR.addStringLocalization("tile.MMRockSmooth.Quartzite.name", "Smooth Quartzite");
		LR.addStringLocalization("tile.MMRockSmooth.Slate.name", "Smooth Slate");
		LR.addStringLocalization("tile.MMRockSmooth.Phyllite.name", "Smooth Phyllite");
		LR.addStringLocalization("tile.MMRockSmooth.Schist.name", "Smooth Schist");
		LR.addStringLocalization("tile.MMRockSmooth.Gneiss.name", "Smooth Gneiss");
		LR.addStringLocalization("tile.MMRockSmooth.Marble.name", "Smooth Marble");
		//Brick
		LR.addStringLocalization("tile.IgInRockBrick.Granite.name", "Granite Bricks");
		LR.addStringLocalization("tile.IgInRockBrick.Diorite.name", "Diorite Bricks");
		LR.addStringLocalization("tile.IgInRockBrick.Gabbro.name", "Gabbro Bricks");
		LR.addStringLocalization("tile.IgExRockBrick.Rhyolite.name", "Rhyolite Bricks");
		LR.addStringLocalization("tile.IgExRockBrick.Basalt.name", "Basalt Bricks");
		LR.addStringLocalization("tile.IgExRockBrick.Andesite.name", "Andesite Bricks");
		LR.addStringLocalization("tile.IgExRockBrick.Dacite.name", "Dacite Bricks");
		LR.addStringLocalization("tile.SedRockBrick.Siltstone.name", "Siltstone Bricks");
		LR.addStringLocalization("tile.SedRockBrick.Mudstone.name", "Mudstone Bricks");
		LR.addStringLocalization("tile.SedRockBrick.Shale.name", "Shale Bricks");
		LR.addStringLocalization("tile.SedRockBrick.Claystone.name", "Claystone Bricks");
		LR.addStringLocalization("tile.SedRockBrick.Rock Salt.name", "Rock Salt Bricks");
		LR.addStringLocalization("tile.SedRockBrick.Limestone.name", "Limestone Bricks");
		LR.addStringLocalization("tile.SedRockBrick.Conglomerate.name", "Conglomerate Bricks");
		LR.addStringLocalization("tile.SedRockBrick.Dolomite.name", "Dolomite Bricks");
		LR.addStringLocalization("tile.SedRockBrick.Chert.name", "Chert Bricks");
		LR.addStringLocalization("tile.SedRockBrick.Chalk.name", "Chalk Bricks");
		LR.addStringLocalization("tile.MMRockBrick.Quartzite.name", "Quartzite Bricks");
		LR.addStringLocalization("tile.MMRockBrick.Slate.name", "Slate Bricks");
		LR.addStringLocalization("tile.MMRockBrick.Phyllite.name", "Phyllite Bricks");
		LR.addStringLocalization("tile.MMRockBrick.Schist.name", "Schist Bricks");
		LR.addStringLocalization("tile.MMRockBrick.Gneiss.name", "Gneiss Bricks");
		LR.addStringLocalization("tile.MMRockBrick.Marble.name", "Marble Bricks");
		//Cobble Stairs
		LR.addStringLocalization("tile.IgInRockCobbleStairs.Granite.name", "Granite Cobblestone Stairs");
		LR.addStringLocalization("tile.IgInRockCobbleStairs.Diorite.name", "Diorite Cobblestone Stairs");
		LR.addStringLocalization("tile.IgInRockCobbleStairs.Gabbro.name", "Gabbro Cobblestone Stairs");
		LR.addStringLocalization("tile.IgExRockCobbleStairs.Rhyolite.name", "Rhyolite Cobblestone Stairs");
		LR.addStringLocalization("tile.IgExRockCobbleStairs.Basalt.name", "Basalt Cobblestone Stairs");
		LR.addStringLocalization("tile.IgExRockCobbleStairs.Andesite.name", "Andesite Cobblestone Stairs");
		LR.addStringLocalization("tile.IgExRockCobbleStairs.Dacite.name", "Dacite Cobblestone Stairs");
		LR.addStringLocalization("tile.SedRockCobbleStairs.Siltstone.name", "Siltstone Cobblestone Stairs");
		LR.addStringLocalization("tile.SedRockCobbleStairs.Mudstone.name", "Mudstone Cobblestone Stairs");
		LR.addStringLocalization("tile.SedRockCobbleStairs.Shale.name", "Shale Cobblestone Stairs");
		LR.addStringLocalization("tile.SedRockCobbleStairs.Claystone.name", "Claystone Cobblestone Stairs");
		LR.addStringLocalization("tile.SedRockCobbleStairs.Rock Salt.name", "Rock Salt Cobblestone Stairs");
		LR.addStringLocalization("tile.SedRockCobbleStairs.Limestone.name", "Limestone Cobblestone Stairs");
		LR.addStringLocalization("tile.SedRockCobbleStairs.Conglomerate.name", "Conglomerate Cobblestone Stairs");
		LR.addStringLocalization("tile.SedRockCobbleStairs.Dolomite.name", "Dolomite Cobblestone Stairs");
		LR.addStringLocalization("tile.SedRockCobbleStairs.Chert.name", "Chert Cobblestone Stairs");
		LR.addStringLocalization("tile.SedRockCobbleStairs.Chalk.name", "Chalk Cobblestone Stairs");
		LR.addStringLocalization("tile.MMRockCobbleStairs.Quartzite.name", "Quartzite Cobblestone Stairs");
		LR.addStringLocalization("tile.MMRockCobbleStairs.Slate.name", "Slate Cobblestone Stairs");
		LR.addStringLocalization("tile.MMRockCobbleStairs.Phyllite.name", "Phyllite Cobblestone Stairs");
		LR.addStringLocalization("tile.MMRockCobbleStairs.Schist.name", "Schist Cobblestone Stairs");
		LR.addStringLocalization("tile.MMRockCobbleStairs.Gneiss.name", "Gneiss Cobblestone Stairs");
		LR.addStringLocalization("tile.MMRockCobbleStairs.Marble.name", "Marble Cobblestone Stairs");

		//Dirt
		LR.addStringLocalization("tile.dirt.Granite.name", "Dirt");
		LR.addStringLocalization("tile.dirt.Diorite.name", "Dirt");
		LR.addStringLocalization("tile.dirt.Gabbro.name", "Dirt");
		LR.addStringLocalization("tile.dirt.Rhyolite.name", "Dirt");
		LR.addStringLocalization("tile.dirt.Basalt.name", "Dirt");
		LR.addStringLocalization("tile.dirt.Andesite.name", "Dirt");
		LR.addStringLocalization("tile.dirt.Dacite.name", "Dirt");
		LR.addStringLocalization("tile.dirt.Siltstone.name", "Dirt");
		LR.addStringLocalization("tile.dirt.Mudstone.name", "Dirt");
		LR.addStringLocalization("tile.dirt.Shale.name", "Dirt");
		LR.addStringLocalization("tile.dirt.Claystone.name", "Dirt");
		LR.addStringLocalization("tile.dirt.Rock Salt.name", "Dirt");
		LR.addStringLocalization("tile.dirt.Limestone.name", "Dirt");
		LR.addStringLocalization("tile.dirt.Conglomerate.name", "Dirt");
		LR.addStringLocalization("tile.dirt.Dolomite.name", "Dirt");
		LR.addStringLocalization("tile.dirt.Chert.name", "Dirt");
		LR.addStringLocalization("tile.dirt.Chalk.name", "Dirt");
		LR.addStringLocalization("tile.dirt.Quartzite.name", "Dirt");
		LR.addStringLocalization("tile.dirt.Slate.name", "Dirt");
		LR.addStringLocalization("tile.dirt.Phyllite.name", "Dirt");
		LR.addStringLocalization("tile.dirt.Schist.name", "Dirt");
		LR.addStringLocalization("tile.dirt.Gneiss.name", "Dirt");
		LR.addStringLocalization("tile.dirt.Marble.name", "Dirt");
		//Clay
		LR.addStringLocalization("tile.clay.Granite.name", "Clay");
		LR.addStringLocalization("tile.clay.Diorite.name", "Clay");
		LR.addStringLocalization("tile.clay.Gabbro.name", "Clay");
		LR.addStringLocalization("tile.clay.Rhyolite.name", "Clay");
		LR.addStringLocalization("tile.clay.Basalt.name", "Clay");
		LR.addStringLocalization("tile.clay.Andesite.name", "Clay");
		LR.addStringLocalization("tile.clay.Dacite.name", "Clay");
		LR.addStringLocalization("tile.clay.Siltstone.name", "Clay");
		LR.addStringLocalization("tile.clay.Mudstone.name", "Clay");
		LR.addStringLocalization("tile.clay.Shale.name", "Clay");
		LR.addStringLocalization("tile.clay.Claystone.name", "Clay");
		LR.addStringLocalization("tile.clay.Rock Salt.name", "Clay");
		LR.addStringLocalization("tile.clay.Limestone.name", "Clay");
		LR.addStringLocalization("tile.clay.Conglomerate.name", "Clay");
		LR.addStringLocalization("tile.clay.Dolomite.name", "Clay");
		LR.addStringLocalization("tile.clay.Chert.name", "Clay");
		LR.addStringLocalization("tile.clay.Chalk.name", "Clay");
		LR.addStringLocalization("tile.clay.Quartzite.name", "Clay");
		LR.addStringLocalization("tile.clay.Slate.name", "Clay");
		LR.addStringLocalization("tile.clay.Phyllite.name", "Clay");
		LR.addStringLocalization("tile.clay.Schist.name", "Clay");
		LR.addStringLocalization("tile.clay.Gneiss.name", "Clay");
		LR.addStringLocalization("tile.clay.Marble.name", "Clay");
		//Grass
		LR.addStringLocalization("tile.Grass.Granite.name", "Grass");
		LR.addStringLocalization("tile.Grass.Diorite.name", "Grass");
		LR.addStringLocalization("tile.Grass.Gabbro.name", "Grass");
		LR.addStringLocalization("tile.Grass.Rhyolite.name", "Grass");
		LR.addStringLocalization("tile.Grass.Basalt.name", "Grass");
		LR.addStringLocalization("tile.Grass.Andesite.name", "Grass");
		LR.addStringLocalization("tile.Grass.Dacite.name", "Grass");
		LR.addStringLocalization("tile.Grass.Siltstone.name", "Grass");
		LR.addStringLocalization("tile.Grass.Mudstone.name", "Grass");
		LR.addStringLocalization("tile.Grass.Shale.name", "Grass");
		LR.addStringLocalization("tile.Grass.Claystone.name", "Grass");
		LR.addStringLocalization("tile.Grass.Rock Salt.name", "Grass");
		LR.addStringLocalization("tile.Grass.Limestone.name", "Grass");
		LR.addStringLocalization("tile.Grass.Conglomerate.name", "Grass");
		LR.addStringLocalization("tile.Grass.Dolomite.name", "Grass");
		LR.addStringLocalization("tile.Grass.Chert.name", "Grass");
		LR.addStringLocalization("tile.Grass.Chalk.name", "Grass");
		LR.addStringLocalization("tile.Grass.Quartzite.name", "Grass");
		LR.addStringLocalization("tile.Grass.Slate.name", "Grass");
		LR.addStringLocalization("tile.Grass.Phyllite.name", "Grass");
		LR.addStringLocalization("tile.Grass.Schist.name", "Grass");
		LR.addStringLocalization("tile.Grass.Gneiss.name", "Grass");
		LR.addStringLocalization("tile.Grass.Marble.name", "Grass");
		//clay grass
		LR.addStringLocalization("tile.ClayGrass.Granite.name", "Clay Grass");
		LR.addStringLocalization("tile.ClayGrass.Diorite.name", "Clay Grass");
		LR.addStringLocalization("tile.ClayGrass.Gabbro.name", "Clay Grass");
		LR.addStringLocalization("tile.ClayGrass.Rhyolite.name", "Clay Grass");
		LR.addStringLocalization("tile.ClayGrass.Basalt.name", "Clay Grass");
		LR.addStringLocalization("tile.ClayGrass.Andesite.name", "Clay Grass");
		LR.addStringLocalization("tile.ClayGrass.Dacite.name", "Clay Grass");
		LR.addStringLocalization("tile.ClayGrass.Siltstone.name", "Clay Grass");
		LR.addStringLocalization("tile.ClayGrass.Mudstone.name", "Clay Grass");
		LR.addStringLocalization("tile.ClayGrass.Shale.name", "Clay Grass");
		LR.addStringLocalization("tile.ClayGrass.Claystone.name", "Clay Grass");
		LR.addStringLocalization("tile.ClayGrass.Rock Salt.name", "Clay Grass");
		LR.addStringLocalization("tile.ClayGrass.Limestone.name", "Clay Grass");
		LR.addStringLocalization("tile.ClayGrass.Conglomerate.name", "Clay Grass");
		LR.addStringLocalization("tile.ClayGrass.Dolomite.name", "Clay Grass");
		LR.addStringLocalization("tile.ClayGrass.Chert.name", "Clay Grass");
		LR.addStringLocalization("tile.ClayGrass.Chalk.name", "Clay Grass");
		LR.addStringLocalization("tile.ClayGrass.Quartzite.name", "Clay Grass");
		LR.addStringLocalization("tile.ClayGrass.Slate.name", "Clay Grass");
		LR.addStringLocalization("tile.ClayGrass.Phyllite.name", "Clay Grass");
		LR.addStringLocalization("tile.ClayGrass.Schist.name", "Clay Grass");
		LR.addStringLocalization("tile.ClayGrass.Gneiss.name", "Clay Grass");
		LR.addStringLocalization("tile.ClayGrass.Marble.name", "Clay Grass");
		//peat
		LR.addStringLocalization("tile.Peat.name", "Peat");
		LR.addStringLocalization("tile.PeatGrass.name", "Peat Grass");
		LR.addStringLocalization("item.Sulfur Powder.name", "Sulfur Powder");
		LR.addStringLocalization("item.Saltpeter Powder.name", "Saltpeter");

		LR.addStringLocalization("item.IgIn Stone Pick.name", "Stone Pickaxe");
		LR.addStringLocalization("item.IgIn Stone Shovel.name", "Stone Shovel");
		LR.addStringLocalization("item.IgIn Stone Axe.name", "Stone Axe");
		LR.addStringLocalization("item.IgIn Stone Hoe.name", "Stone Hoe");
		LR.addStringLocalization("item.IgIn Stone Sword.name", "Stone Sword");
		LR.addStringLocalization("item.IgIn Stone Mace.name", "Stone Mace");
		LR.addStringLocalization("item.Sed Stone Pick.name", "Stone Pickaxe");
		LR.addStringLocalization("item.Sed Stone Shovel.name", "Stone Shovel");
		LR.addStringLocalization("item.Sed Stone Axe.name", "Stone Axe");
		LR.addStringLocalization("item.Sed Stone Hoe.name", "Stone Hoe");
		LR.addStringLocalization("item.Sed Stone Sword.name", "Stone Sword");
		LR.addStringLocalization("item.Sed Stone Mace.name", "Stone Mace");
		LR.addStringLocalization("item.IgEx Stone Pick.name", "Stone Pickaxe");
		LR.addStringLocalization("item.IgEx Stone Shovel.name", "Stone Shovel");
		LR.addStringLocalization("item.IgEx Stone Axe.name", "Stone Axe");
		LR.addStringLocalization("item.IgEx Stone Hoe.name", "Stone Hoe");
		LR.addStringLocalization("item.IgEx Stone Sword.name", "Stone Sword");
		LR.addStringLocalization("item.IgEx Stone Mace.name", "Stone Mace");
		LR.addStringLocalization("item.MM Stone Pick.name", "Stone Pickaxe");
		LR.addStringLocalization("item.MM Stone Shovel.name", "Stone Shovel");
		LR.addStringLocalization("item.MM Stone Axe.name", "Stone Axe");
		LR.addStringLocalization("item.MM Stone Hoe.name", "Stone Hoe");
		LR.addStringLocalization("item.MM Stone Sword.name", "Stone Sword");
		LR.addStringLocalization("item.MM Stone Mace.name", "Stone Mace");

		LR.addStringLocalization("item.Bone IgIn Stone Pick.name", "Stone Pickaxe");
		LR.addStringLocalization("item.Bone IgIn Stone Shovel.name", "Stone Shovel");
		LR.addStringLocalization("item.Bone IgIn Stone Axe.name", "Stone Axe");
		LR.addStringLocalization("item.Bone IgIn Stone Hoe.name", "Stone Hoe");

		LR.addStringLocalization("item.Bone Sed Stone Pick.name", "Stone Pickaxe");
		LR.addStringLocalization("item.Bone Sed Stone Shovel.name", "Stone Shovel");
		LR.addStringLocalization("item.Bone Sed Stone Axe.name", "Stone Axe");
		LR.addStringLocalization("item.Bone Sed Stone Hoe.name", "Stone Hoe");

		LR.addStringLocalization("item.Bone IgEx Stone Pick.name", "Stone Pickaxe");
		LR.addStringLocalization("item.Bone IgEx Stone Shovel.name", "Stone Shovel");
		LR.addStringLocalization("item.Bone IgEx Stone Axe.name", "Stone Axe");
		LR.addStringLocalization("item.Bone IgEx Stone Hoe.name", "Stone Hoe");

		LR.addStringLocalization("item.Bone MM Stone Pick.name", "Stone Pickaxe");
		LR.addStringLocalization("item.Bone MM Stone Shovel.name", "Stone Shovel");
		LR.addStringLocalization("item.Bone MM Stone Axe.name", "Stone Axe");
		LR.addStringLocalization("item.Bone MM Stone Hoe.name", "Stone Hoe");

		LR.addStringLocalization("item.Stone Hammer.name", "Stone Hammer");
	}

	private void RegisterWood()
	{
		LanguageRegistry LR = LanguageRegistry.instance();
		String[] WoodNames = {"Oak","Aspen","Birch","Chestnut","Douglas Fir","Hickory","Maple","Ash","Pine",
				"Sequoia","Spruce","Sycamore","White Cedar","White Elm","Willow","Kapok"};

		for(int i= 0; i < WoodNames.length; i++)
		{
			LR.addStringLocalization("tile.log."+WoodNames[i]+".name", WoodNames[i]);
			LR.addStringLocalization("tile.leaves."+WoodNames[i]+".name", WoodNames[i] + " Leaves");
			LR.addStringLocalization("tile.Barrel"+WoodNames[i]+".name", WoodNames[i] + " Barrel");
			LR.addStringLocalization("tile.sapling."+WoodNames[i]+".name", WoodNames[i] + " Sapling");
			LR.addStringLocalization("tile.wood."+WoodNames[i]+".name", WoodNames[i] + " Planks");
			LR.addStringLocalization("tile.WoodSupportV."+WoodNames[i]+".name","V. " + WoodNames[i] + " Support Beam");
			LR.addStringLocalization("tile.WoodSupportH."+WoodNames[i]+".name","H. " + WoodNames[i] + " Support Beam");
			LR.addStringLocalization("item.Log."+WoodNames[i]+".name", WoodNames[i]);
			LR.addStringLocalization("tile.Barrel."+WoodNames[i]+".name", WoodNames[i] +" Barrel");
			LR.addStringLocalization("item.SinglePlank."+WoodNames[i]+".name", WoodNames[i] + " Plank");
			LR.addStringLocalization("tile.Toolrack."+WoodNames[i]+".name", WoodNames[i] + " Tool Rack");
			LR.addStringLocalization("tile."+WoodNames[i]+" Door.name", WoodNames[i] + " Door");
			LR.addStringLocalization("item."+WoodNames[i]+" Door.name", WoodNames[i] + " Door");

		}
		LR.addStringLocalization("item.Stick.name", "Stick");
	}

	private void RegisterMetal()
	{
		LanguageRegistry LR = LanguageRegistry.instance();
		String[] ToolNames = {"Stone","Bismuth","Bismuth Bronze","Black Bronze","Black Steel","Blue Steel", "Bronze", "Copper"
				,"Wrought Iron","Red Steel","Rose Gold", "Steel", "Tin", "Zinc"};

		String[] Names = {"Bismuth","Bismuth Bronze","Black Bronze","Black Steel","Blue Steel","Brass","Bronze","Copper","Gold"
				,"Wrought Iron","Lead","Nickel","Pig Iron","Platinum","Red Steel","Rose Gold","Silver", "Steel", "Sterling Silver",
				"Tin", "Zinc"};

		LR.addStringLocalization("tile.Anvil.Stone.name", "Stone Anvil");
		
		for(int i= 0; i < ToolNames.length; i++)
		{
			LR.addStringLocalization("tile.Anvil."+ToolNames[i]+".name", ToolNames[i]+" Anvil");
			LR.addStringLocalization("tile.Anvil2."+ToolNames[i]+".name", ToolNames[i]+" Anvil");
			LR.addStringLocalization("item."+ToolNames[i]+" Pick.name", ToolNames[i] + " Pickaxe");
			LR.addStringLocalization("item."+ToolNames[i]+" Shovel.name", ToolNames[i] + " Shovel");
			LR.addStringLocalization("item."+ToolNames[i]+" Axe.name", ToolNames[i] + " Axe");
			LR.addStringLocalization("item."+ToolNames[i]+" Hoe.name", ToolNames[i] + " Hoe");
			LR.addStringLocalization("item."+ToolNames[i]+" Hammer.name", ToolNames[i] + " Hammer");
			LR.addStringLocalization("item."+ToolNames[i]+" Sword.name", ToolNames[i] + " Sword");
			LR.addStringLocalization("item."+ToolNames[i]+" Mace.name", ToolNames[i] + " Mace");
			LR.addStringLocalization("item."+ToolNames[i]+" Chisel.name", ToolNames[i] + " Chisel");
			LR.addStringLocalization("item."+ToolNames[i]+" Saw.name", ToolNames[i] + " Saw");
			LR.addStringLocalization("item."+ToolNames[i]+" Scythe.name", ToolNames[i] + " Scythe");
			LR.addStringLocalization("item."+ToolNames[i]+" Knife.name", ToolNames[i] + " Knife");
			LR.addStringLocalization("item."+ToolNames[i]+" ProPick.name", ToolNames[i] + " Prospector's Pick");
			LR.addStringLocalization("item."+ToolNames[i]+" Pick Head.name", ToolNames[i] + " Pickaxe Head");
			LR.addStringLocalization("item."+ToolNames[i]+" Shovel Head.name", ToolNames[i] + " Shovel Head");
			LR.addStringLocalization("item."+ToolNames[i]+" Axe Head.name", ToolNames[i] + " Axe Head");
			LR.addStringLocalization("item."+ToolNames[i]+" Hoe Head.name", ToolNames[i] + " Hoe Head");
			LR.addStringLocalization("item."+ToolNames[i]+" Hammer Head.name", ToolNames[i] + " Hammer Head");
			LR.addStringLocalization("item."+ToolNames[i]+" Sword Blade.name", ToolNames[i] + " Sword Blade");
			LR.addStringLocalization("item."+ToolNames[i]+" Mace Head.name", ToolNames[i] + " Mace Head");
			LR.addStringLocalization("item."+ToolNames[i]+" Chisel Head.name", ToolNames[i] + " Chisel Head");
			LR.addStringLocalization("item."+ToolNames[i]+" Saw Blade.name", ToolNames[i] + " Saw Blade");
			LR.addStringLocalization("item."+ToolNames[i]+" ProPick Head.name", ToolNames[i] + " Prospector's Pick Head");
			LR.addStringLocalization("item."+ToolNames[i]+" Scythe Blade.name", ToolNames[i] + " Scythe Blade");
			LR.addStringLocalization("item."+ToolNames[i]+" Knife Blade.name", ToolNames[i] + " Knife Blade");
			/**
			 * Armor Related
			 * */

			LR.addStringLocalization("item."+ToolNames[i]+" Unfinished Helmet.name", "Unfinished " + ToolNames[i] + " Helmet Stage 1");
			LR.addStringLocalization("item."+ToolNames[i]+" Unfinished Helmet 2.name", "Unfinished " + ToolNames[i] + " Helmet Stage 2");
			LR.addStringLocalization("item."+ToolNames[i]+" Helmet.name", ToolNames[i] + " Helmet");
			LR.addStringLocalization("item."+ToolNames[i]+" Unfinished Chestplate.name", "Unfinished " + ToolNames[i] + " Chestplate Stage 1");
			LR.addStringLocalization("item."+ToolNames[i]+" Unfinished Chestplate 2.name", "Unfinished " + ToolNames[i] + " Chestplate Stage 2");
			LR.addStringLocalization("item."+ToolNames[i]+" Chestplate.name", ToolNames[i] + " Chestplate");
			LR.addStringLocalization("item."+ToolNames[i]+" Unfinished Greaves.name", "Unfinished " + ToolNames[i] + " Greaves Stage 1");
			LR.addStringLocalization("item."+ToolNames[i]+" Unfinished Greaves 2.name", "Unfinished " + ToolNames[i] + " Greaves Stage 2");
			LR.addStringLocalization("item."+ToolNames[i]+" Greaves.name", ToolNames[i] + " Greaves");
			LR.addStringLocalization("item."+ToolNames[i]+" Unfinished Boots.name", "Unfinished " + ToolNames[i] + " Boots Stage 1");
			LR.addStringLocalization("item."+ToolNames[i]+" Unfinished Boots 2.name", "Unfinished " + ToolNames[i] + " Boots Stage 2");
			LR.addStringLocalization("item."+ToolNames[i]+" Boots.name", ToolNames[i] + " Boots");
		}

		//meltedmetal
		for(int i= 0; i < Names.length; i++)
		{
			LR.addStringLocalization("item."+Names[i]+" Unshaped.name", Names[i] + " Unshaped");
			LR.addStringLocalization("item."+Names[i]+" Ingot.name", Names[i] + " Ingot");
			LR.addStringLocalization("item."+Names[i]+" Double Ingot.name", Names[i] + " Double Ingot");
			LR.addStringLocalization("item."+Names[i]+" Sheet.name", Names[i] + " Sheet");
			LR.addStringLocalization("item."+Names[i]+" Double Sheet.name", Names[i] + " Double Sheet");
		}

		LR.addStringLocalization("item.HC Black Steel Unshaped.name", "Unshaped High Carbon Black Steel");
		LR.addStringLocalization("item.HC Blue Steel Unshaped.name", "Unshaped High Carbon Blue Steel");
		LR.addStringLocalization("item.HC Red Steel Unshaped.name", "Unshaped High Carbon Red Steel");
		LR.addStringLocalization("item.HC Steel Unshaped.name", "Unshaped High Carbon Steel");
		LR.addStringLocalization("item.Weak Steel Unshaped.name", "Unshaped Weak Steel");
		LR.addStringLocalization("item.Weak Blue Steel Unshaped.name", "Unshaped Weak Blue Steel");
		LR.addStringLocalization("item.Weak Red Steel Unshaped.name", "Unshaped Weak Red Steel");

		LR.addStringLocalization("item.HC Black Steel Ingot.name", "High Carbon Black Steel Ingot");
		LR.addStringLocalization("item.HC Blue Steel Ingot.name", "High Carbon Blue Steel Ingot");
		LR.addStringLocalization("item.HC Red Steel Ingot.name", "High Carbon Red Steel Ingot");
		LR.addStringLocalization("item.HC Steel Ingot.name", "High Carbon Steel Ingot");
		LR.addStringLocalization("item.Weak Steel Ingot.name", "Weak Steel Ingot");
		LR.addStringLocalization("item.Weak Blue Steel Ingot.name", "Weak Blue Steel Ingot");
		LR.addStringLocalization("item.Weak Red Steel Ingot.name", "Weak Red Steel Ingot");
	}

	@Override
	public boolean getGraphicsLevel()
	{
		// TODO Auto-generated method stub
		return ModLoader.getMinecraftInstance().isFancyGraphicsEnabled();
	}

}
