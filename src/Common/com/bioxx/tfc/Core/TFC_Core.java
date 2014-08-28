package com.bioxx.tfc.Core;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldInfo;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.TFCItems;
import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Blocks.BlockSlab;
import com.bioxx.tfc.Chunkdata.ChunkData;
import com.bioxx.tfc.Chunkdata.ChunkDataManager;
import com.bioxx.tfc.Core.Player.BodyTempStats;
import com.bioxx.tfc.Core.Player.FoodStatsTFC;
import com.bioxx.tfc.Core.Player.InventoryPlayerTFC;
import com.bioxx.tfc.Core.Player.SkillStats;
import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.Items.ItemOre;
import com.bioxx.tfc.Items.ItemTerra;
import com.bioxx.tfc.Items.ItemBlocks.ItemTerraBlock;
import com.bioxx.tfc.TileEntities.TEMetalSheet;
import com.bioxx.tfc.TileEntities.TEPartial;
import com.bioxx.tfc.WorldGen.TFCBiome;
import com.bioxx.tfc.api.TFCOptions;
import com.bioxx.tfc.api.TFC_ItemHeat;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Entities.IAnimal;
import com.bioxx.tfc.api.Enums.EnumFuelMaterial;
import com.bioxx.tfc.api.Interfaces.IFood;
import com.bioxx.tfc.api.Util.Helper;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TFC_Core
{
	public enum Direction
	{
		PosX, PosZ, NegX, NegZ, None, PosXPosZ, PosXNegZ, NegXPosZ, NegXNegZ, NegY, PosY
	}

	public static boolean PreventEntityDataUpdate = false;

	@SideOnly(Side.CLIENT)
	public static int getMouseX()
	{
		ScaledResolution scaledresolution = new ScaledResolution(Minecraft.getMinecraft().gameSettings, Minecraft.getMinecraft().displayWidth,
				Minecraft.getMinecraft().displayHeight);
		int i = scaledresolution.getScaledWidth();
		int k = Mouse.getX() * i / Minecraft.getMinecraft().displayWidth;

		return k;
	}

	@SideOnly(Side.CLIENT)
	public static int getMouseY()
	{
		ScaledResolution scaledresolution = new ScaledResolution(Minecraft.getMinecraft().gameSettings, Minecraft.getMinecraft().displayWidth,
				Minecraft.getMinecraft().displayHeight);
		int j = scaledresolution.getScaledHeight();
		int l = j - Mouse.getY() * j / Minecraft.getMinecraft().displayHeight - 1;

		return l;
	}

	static Boolean isBlockAboveSolid(IBlockAccess blockAccess, int i, int j, int k)
	{
		if(TerraFirmaCraft.proxy.getCurrentWorld().getBlock(i, j + 1, k).isOpaqueCube())
			return true;
		return false;
	}

	public static int getExtraEquipInventorySize(){
		//Just the back
		return 1;
	}

	public static InventoryPlayer getNewInventory(EntityPlayer player)
	{
		InventoryPlayer ip = player.inventory;
		NBTTagList nbt = new NBTTagList();
		nbt = player.inventory.writeToNBT(nbt);
		ip = new InventoryPlayerTFC(player);
		ip.readFromNBT(nbt);
		return ip;
	}

	public static ItemStack RandomGem(Random random, int rockType)
	{
		ItemStack is = null;
		if (random.nextInt(500) == 0)
		{
			ArrayList items = new ArrayList<ItemStack>();
			items.add(new ItemStack(TFCItems.GemAgate, 1, 0));
			items.add(new ItemStack(TFCItems.GemAmethyst, 1, 0));
			items.add(new ItemStack(TFCItems.GemBeryl, 1, 0));
			items.add(new ItemStack(TFCItems.GemEmerald, 1, 0));
			items.add(new ItemStack(TFCItems.GemGarnet, 1, 0));
			items.add(new ItemStack(TFCItems.GemJade, 1, 0));
			items.add(new ItemStack(TFCItems.GemJasper, 1, 0));
			items.add(new ItemStack(TFCItems.GemOpal, 1, 0));
			items.add(new ItemStack(TFCItems.GemRuby, 1, 0));
			items.add(new ItemStack(TFCItems.GemSapphire, 1, 0));
			items.add(new ItemStack(TFCItems.GemTourmaline, 1, 0));
			items.add(new ItemStack(TFCItems.GemTopaz, 1, 0));

			is = (ItemStack) items.toArray()[random.nextInt(items.toArray().length)];
		}
		else if (random.nextInt(1000) == 0)
		{
			ArrayList items = new ArrayList<ItemStack>();
			items.add(new ItemStack(TFCItems.GemAgate, 1, 1));
			items.add(new ItemStack(TFCItems.GemAmethyst, 1, 1));
			items.add(new ItemStack(TFCItems.GemBeryl, 1, 1));
			items.add(new ItemStack(TFCItems.GemEmerald, 1, 1));
			items.add(new ItemStack(TFCItems.GemGarnet, 1, 1));
			items.add(new ItemStack(TFCItems.GemJade, 1, 1));
			items.add(new ItemStack(TFCItems.GemJasper, 1, 1));
			items.add(new ItemStack(TFCItems.GemOpal, 1, 1));
			items.add(new ItemStack(TFCItems.GemRuby, 1, 1));
			items.add(new ItemStack(TFCItems.GemSapphire, 1, 1));
			items.add(new ItemStack(TFCItems.GemTourmaline, 1, 1));
			items.add(new ItemStack(TFCItems.GemTopaz, 1, 1));

			is = (ItemStack) items.toArray()[random.nextInt(items.toArray().length)];
		}
		else if (random.nextInt(2000) == 0)
		{
			ArrayList items = new ArrayList<ItemStack>();
			items.add(new ItemStack(TFCItems.GemAgate, 1, 2));
			items.add(new ItemStack(TFCItems.GemAmethyst, 1, 2));
			items.add(new ItemStack(TFCItems.GemBeryl, 1, 2));
			items.add(new ItemStack(TFCItems.GemEmerald, 1, 2));
			items.add(new ItemStack(TFCItems.GemGarnet, 1, 2));
			items.add(new ItemStack(TFCItems.GemJade, 1, 2));
			items.add(new ItemStack(TFCItems.GemJasper, 1, 2));
			items.add(new ItemStack(TFCItems.GemOpal, 1, 2));
			items.add(new ItemStack(TFCItems.GemRuby, 1, 2));
			items.add(new ItemStack(TFCItems.GemSapphire, 1, 2));
			items.add(new ItemStack(TFCItems.GemTourmaline, 1, 2));
			items.add(new ItemStack(TFCItems.GemTopaz, 1, 2));

			is = (ItemStack) items.toArray()[random.nextInt(items.toArray().length)];
		}
		else if (random.nextInt(4000) == 0)
		{
			ArrayList items = new ArrayList<ItemStack>();
			items.add(new ItemStack(TFCItems.GemAgate, 1, 3));
			items.add(new ItemStack(TFCItems.GemAmethyst, 1, 3));
			items.add(new ItemStack(TFCItems.GemBeryl, 1, 3));
			items.add(new ItemStack(TFCItems.GemEmerald, 1, 3));
			items.add(new ItemStack(TFCItems.GemGarnet, 1, 3));
			items.add(new ItemStack(TFCItems.GemJade, 1, 3));
			items.add(new ItemStack(TFCItems.GemJasper, 1, 3));
			items.add(new ItemStack(TFCItems.GemOpal, 1, 3));
			items.add(new ItemStack(TFCItems.GemRuby, 1, 3));
			items.add(new ItemStack(TFCItems.GemSapphire, 1, 3));
			items.add(new ItemStack(TFCItems.GemTourmaline, 1, 3));
			items.add(new ItemStack(TFCItems.GemTopaz, 1, 3));

			is = (ItemStack) items.toArray()[random.nextInt(items.toArray().length)];
		}
		else if (random.nextInt(8000) == 0)
		{
			ArrayList items = new ArrayList<ItemStack>();
			items.add(new ItemStack(TFCItems.GemAgate, 1, 4));
			items.add(new ItemStack(TFCItems.GemAmethyst, 1, 4));
			items.add(new ItemStack(TFCItems.GemBeryl, 1, 4));
			items.add(new ItemStack(TFCItems.GemEmerald, 1, 4));
			items.add(new ItemStack(TFCItems.GemGarnet, 1, 4));
			items.add(new ItemStack(TFCItems.GemJade, 1, 4));
			items.add(new ItemStack(TFCItems.GemJasper, 1, 4));
			items.add(new ItemStack(TFCItems.GemOpal, 1, 4));
			items.add(new ItemStack(TFCItems.GemRuby, 1, 4));
			items.add(new ItemStack(TFCItems.GemSapphire, 1, 4));
			items.add(new ItemStack(TFCItems.GemTourmaline, 1, 4));
			items.add(new ItemStack(TFCItems.GemTopaz, 1, 4));

			is = (ItemStack) items.toArray()[random.nextInt(items.toArray().length)];
		}
		return is;
	}

	public static void SurroundWithLeaves(World world, int i, int j, int k, int meta, Random R)
	{
		for (int y = 2; y >= -2; y--)
		{
			for (int x = 2; x >= -2; x--)
			{
				for (int z = 2; z >= -2; z--)
				{
					if(world.isAirBlock(i + x, j + y, k + z))
						world.setBlock(i + x, j + y, k + z, TFCBlocks.Leaves, meta, 2);
				}
			}
		}
	}

	public static void SetupWorld(World world)
	{
		long seed = world.getSeed();
		Random R = new Random(seed);
		world.provider.registerWorld(world);
		Recipes.registerAnvilRecipes(R, world);
		// TerraFirmaCraft.proxy.registerSkyProvider(world.provider);
	}

	public static void SetupWorld(World w, long seed)
	{
		World world = w;
		try
		{
			// ReflectionHelper.setPrivateValue(WorldInfo.class,
			// w.getWorldInfo(), "randomSeed", seed);
			ReflectionHelper.setPrivateValue(WorldInfo.class, w.getWorldInfo(), seed, 0);
			SetupWorld(w);
		}
		catch (Exception ex)
		{
		}
	}

	public static boolean isRawStone(World world, int x, int y, int z)
	{
		Block block = world.getBlock(x, y, z);
		return (block == TFCBlocks.StoneIgEx
				|| block == TFCBlocks.StoneIgIn
				|| block == TFCBlocks.StoneSed
				|| block == TFCBlocks.StoneMM
				);
	}

	public static boolean isSmoothStone(World world, int x, int y, int z)
	{
		Block block = world.getBlock(x, y, z);
		return block == TFCBlocks.StoneIgExSmooth
				|| block == TFCBlocks.StoneIgInSmooth
				|| block == TFCBlocks.StoneSedSmooth
				|| block == TFCBlocks.StoneMMSmooth;
	}

	public static boolean isSmoothStone(Block block)
	{
		return block == TFCBlocks.StoneIgExSmooth
				|| block == TFCBlocks.StoneIgInSmooth
				|| block == TFCBlocks.StoneSedSmooth
				|| block == TFCBlocks.StoneMMSmooth;
	}

	public static boolean isBrickStone(Block block)
	{
		return block == TFCBlocks.StoneIgExBrick
				|| block == TFCBlocks.StoneIgInBrick
				|| block == TFCBlocks.StoneSedBrick
				|| block == TFCBlocks.StoneMMBrick;
	}

	public static boolean isRawStone(Block block)
	{
		return block == TFCBlocks.StoneIgEx
				|| block == TFCBlocks.StoneIgIn
				|| block == TFCBlocks.StoneSed
				|| block == TFCBlocks.StoneMM;
	}

	public static boolean isCobbleStone(Block block)
	{
		return block == TFCBlocks.StoneIgExCobble
				|| block == TFCBlocks.StoneIgInCobble
				|| block == TFCBlocks.StoneSedCobble
				|| block == TFCBlocks.StoneMMCobble;
	}

	public static boolean isStoneIgEx(Block block)
	{
		return block == TFCBlocks.StoneIgEx
				|| block == TFCBlocks.StoneIgExCobble
				|| block == TFCBlocks.StoneIgExSmooth
				|| block == TFCBlocks.StoneIgExBrick
				|| block == TFCBlocks.WallRawIgEx
				|| block == TFCBlocks.WallCobbleIgEx
				|| block == TFCBlocks.WallBrickIgEx
				|| block == TFCBlocks.WallSmoothIgEx;
	}

	public static boolean isStoneIgIn(Block block)
	{
		return block == TFCBlocks.StoneIgIn
				|| block == TFCBlocks.StoneIgInCobble
				|| block == TFCBlocks.StoneIgInSmooth
				|| block == TFCBlocks.StoneIgInBrick
				|| block == TFCBlocks.WallRawIgIn
				|| block == TFCBlocks.WallCobbleIgIn
				|| block == TFCBlocks.WallBrickIgIn
				|| block == TFCBlocks.WallSmoothIgIn;
	}

	public static boolean isStoneSed(Block block)
	{
		return block == TFCBlocks.StoneSed
				|| block == TFCBlocks.StoneSedCobble
				|| block == TFCBlocks.StoneSedSmooth
				|| block == TFCBlocks.StoneSedBrick
				|| block == TFCBlocks.WallRawSed
				|| block == TFCBlocks.WallCobbleSed
				|| block == TFCBlocks.WallBrickSed
				|| block == TFCBlocks.WallSmoothSed;
	}

	public static boolean isStoneMM(Block block)
	{
		return block == TFCBlocks.StoneMM
				|| block == TFCBlocks.StoneMMCobble
				|| block == TFCBlocks.StoneMMSmooth
				|| block == TFCBlocks.StoneMMBrick
				|| block == TFCBlocks.WallRawMM
				|| block == TFCBlocks.WallCobbleMM
				|| block == TFCBlocks.WallBrickMM
				|| block == TFCBlocks.WallSmoothMM;
	}

	public static boolean isDirt(Block block)
	{
		return block == TFCBlocks.Dirt
				|| block == TFCBlocks.Dirt2;
	}

	public static boolean isFarmland(Block block)
	{
		return block == TFCBlocks.tilledSoil
				|| block == TFCBlocks.tilledSoil2;
	}

	public static boolean isGrass(Block block)
	{
		return block == TFCBlocks.Grass
				|| block == TFCBlocks.Grass2
				|| block == TFCBlocks.ClayGrass
				|| block == TFCBlocks.ClayGrass2
				|| block == TFCBlocks.PeatGrass
				|| block == TFCBlocks.DryGrass
				|| block == TFCBlocks.DryGrass2;
	}

	public static boolean isLushGrass(Block block)
	{
		return block == TFCBlocks.Grass
				|| block == TFCBlocks.Grass2
				|| block == TFCBlocks.ClayGrass
				|| block == TFCBlocks.ClayGrass2
				|| block == TFCBlocks.PeatGrass;
	}

	public static boolean isClayGrass(Block block)
	{
		return block == TFCBlocks.ClayGrass
				|| block == TFCBlocks.ClayGrass2;
	}

	public static boolean isPeatGrass(Block block)
	{
		return block == TFCBlocks.PeatGrass;
	}

	public static boolean isDryGrass(Block block)
	{
		return block == TFCBlocks.DryGrass
				|| block == TFCBlocks.DryGrass2;
	}

	public static boolean isGrassType1(Block block)
	{
		return block == TFCBlocks.Grass
				|| block == TFCBlocks.ClayGrass
				|| block == TFCBlocks.DryGrass;
	}

	public static boolean isGrassType2(Block block)
	{
		return block == TFCBlocks.Grass2
				|| block == TFCBlocks.ClayGrass2
				|| block == TFCBlocks.DryGrass2;
	}

	public static boolean isClay(Block block)
	{
		if(block == TFCBlocks.Clay || block == TFCBlocks.Clay2)
			return true;
		return false;
	}

	public static boolean isSand(Block block)
	{
		return block == TFCBlocks.Sand
				|| block == TFCBlocks.Sand2;
	}

	public static boolean isPeat(Block block)
	{
		return block == TFCBlocks.Peat;
	}

	public static boolean isNotWater(Block block)
	{
		return !isSaltWater(block)
				|| !isFreshWater(block)
				|| !isHotWater(block);
	}

	public static boolean isHotWater(Block block)
	{
		return block == TFCBlocks.HotWater;
	}

	public static boolean isWater(Block block)
	{
		return isSaltWater(block)
				|| isFreshWater(block);
	}

	public static boolean isSaltWater(Block block)
	{
		return block == TFCBlocks.SaltWater;
	}

	public static boolean isSaltWaterIncludeIce(Block block, int meta, Material mat)
	{
		return block == TFCBlocks.SaltWater
				|| (mat == Material.ice && meta == 0);
	}

	public static boolean isFreshWater(Block block)
	{
		return block == TFCBlocks.FreshWater;
	}

	public static boolean isFreshWaterIncludeIce(Block block, int meta)
	{
		return block == TFCBlocks.FreshWater
				|| (block == TFCBlocks.Ice && meta != 0);
	}

	public static boolean isFreshWaterIncludeIce(Block block, int meta, Material mat)
	{
		return block == TFCBlocks.FreshWater
				|| (mat == Material.ice && meta != 0);
	}

	public static boolean isSoil(Block block)
	{
		return isGrass(block)
				|| isDirt(block)
				|| isClay(block)
				|| isPeat(block);
	}

	public static boolean isGravel(Block block)
	{
		return block == TFCBlocks.Gravel || block == TFCBlocks.Gravel2;
	}

	public static boolean isGround(Block block)
	{
		return 	   isSoil(block)
				|| isRawStone(block)
				|| isSand(block) 
				|| isGravel(block);
	}

	public static int getSoilMetaFromStone(Block inBlock, int inMeta)
	{
		if(inBlock == TFCBlocks.StoneIgIn)
			return inMeta;
		else if(inBlock == TFCBlocks.StoneSed)
			return inMeta+3;
		else if(inBlock == TFCBlocks.StoneIgEx)
			return inMeta+11;
		else
		{
			if (inMeta == 0)
				return inMeta + 15;
			return inMeta-1;
		}
	}

	public static int getSoilMeta( int inMeta)
	{
		return inMeta & 15;
	}

	public static int getItemMetaFromStone(Block inBlock, int inMeta)
	{
		if(inBlock == TFCBlocks.StoneIgIn)
			return inMeta;
		else if(inBlock == TFCBlocks.StoneSed)
			return inMeta+3;
		else if(inBlock == TFCBlocks.StoneIgEx)
			return inMeta+11;
		else if(inBlock == TFCBlocks.StoneMM)
			return inMeta+15;
		else
			return 0;
	}

	public static Block getTypeForGrassWithRain(int inMeta, float rain)
	{
		if (rain >= 500)
			return getTypeForGrass(inMeta);
		return getTypeForDryGrass(inMeta);
	}

	public static Block getTypeForGrassWithRainByBlock(Block block, float rain)
	{
		if(rain >= 500)
			return getTypeForGrassFromSoil(block);
		return getTypeForDryGrassFromSoil(block);
	}

	public static Block getTypeForGrass(int inMeta)
	{
		if(inMeta < 16)
			return TFCBlocks.Grass;
		return TFCBlocks.Grass2;
	}

	public static Block getTypeForGrassFromDirt(Block block)
	{
		if(block == TFCBlocks.Dirt)
			return TFCBlocks.Grass;
		return TFCBlocks.Grass2;
	}

	public static Block getTypeForDryGrass(int inMeta)
	{
		if(inMeta < 16)
			return TFCBlocks.DryGrass;
		return TFCBlocks.DryGrass2;
	}

	public static Block getTypeForDryGrassFromSoil(Block block)
	{
		if(block == TFCBlocks.Grass)
			return TFCBlocks.DryGrass;
		else if(block == TFCBlocks.Dirt)
			return TFCBlocks.DryGrass;
		return TFCBlocks.DryGrass2;
	}

	public static Block getTypeForGrassFromSoil(Block block)
	{
		if(block == TFCBlocks.DryGrass)
			return TFCBlocks.Grass;
		else if(block == TFCBlocks.DryGrass2)
			return TFCBlocks.Grass2;
		else if(block == TFCBlocks.Dirt)
			return TFCBlocks.Grass;
		return TFCBlocks.Grass2;
	}

	public static Block getTypeForClayGrass(int inMeta)
	{
		if(inMeta < 16)
			return TFCBlocks.ClayGrass;
		return TFCBlocks.ClayGrass2;
	}

	public static Block getTypeForDirt(int inMeta)
	{
		if(inMeta < 16)
			return TFCBlocks.Dirt;
		return TFCBlocks.Dirt2;
	}

	public static Block getTypeForDirtFromGrass(Block block)
	{
		if (block == TFCBlocks.Grass || block == TFCBlocks.DryGrass)
			return TFCBlocks.Dirt;
		return TFCBlocks.Dirt2;
	}

	public static Block getTypeForClay(int inMeta)
	{
		if(inMeta < 16)
			return TFCBlocks.Clay;
		return TFCBlocks.Clay2;
	}

	public static Block getTypeForSand(int inMeta)
	{
		if(inMeta < 16)
			return TFCBlocks.Sand;
		return TFCBlocks.Sand2;
	}

	public static Block getTypeForGravel(int inMeta)
	{
		if(inMeta < 16)
			return TFCBlocks.Gravel;
		return TFCBlocks.Gravel2;
	}

	public static int getRockLayerFromHeight(World world, int x, int y, int z)
	{
		ChunkData cd = ChunkDataManager.getData(x >> 4, z >> 4);
		if (cd != null)
		{
			int[] hm = cd.heightmap;
			int localX = x & 15;
			int localZ = z & 15;
			int localY = localX + localZ * 16;
			if (y <= TFCOptions.RockLayer3Height + hm[localY])
				return 2;
			else if (y <= TFCOptions.RockLayer2Height + hm[localY])
				return 1;
			else
				return 0;
		}
		return 0;
	}

	public static boolean convertGrassToDirt(World world, int i, int j, int k)
	{
		Block block = world.getBlock(i, j, k);
		int meta = world.getBlockMetadata(i, j, k);
		if(TFC_Core.isGrass(block))
		{
			if(TFC_Core.isGrassType1(block))
			{
				world.setBlock(i, j, k, TFCBlocks.Dirt, meta, 2);
				return true;
			}
			else if(TFC_Core.isGrassType2(block))
			{
				world.setBlock(i, j, k, TFCBlocks.Dirt2, meta, 2);
				return true;
			}
		}
		return false;
	}

	public static EnumFuelMaterial getFuelMaterial(ItemStack is)
	{
		if(is.getItem() == Item.getItemFromBlock(TFCBlocks.Peat))
			return EnumFuelMaterial.PEAT;
		if(is.getItem() == TFCItems.Coal && is.getItemDamage() == 0)
			return EnumFuelMaterial.COAL;
		else if(is.getItem() == TFCItems.Coal && is.getItemDamage() == 1)
			return EnumFuelMaterial.CHARCOAL;
		if(is.getItemDamage() == 0)
			return EnumFuelMaterial.ASH;
		else if (is.getItemDamage() == 1)
			return EnumFuelMaterial.ASPEN;
		else if (is.getItemDamage() == 2)
			return EnumFuelMaterial.BIRCH;
		else if (is.getItemDamage() == 3)
			return EnumFuelMaterial.CHESTNUT;
		else if (is.getItemDamage() == 4)
			return EnumFuelMaterial.DOUGLASFIR;
		else if (is.getItemDamage() == 5)
			return EnumFuelMaterial.HICKORY;
		else if (is.getItemDamage() == 6)
			return EnumFuelMaterial.MAPLE;
		else if (is.getItemDamage() == 7)
			return EnumFuelMaterial.OAK;
		else if (is.getItemDamage() == 8)
			return EnumFuelMaterial.PINE;
		else if (is.getItemDamage() == 9)
			return EnumFuelMaterial.REDWOOD;
		else if (is.getItemDamage() == 10)
			return EnumFuelMaterial.SPRUCE;
		else if (is.getItemDamage() == 11)
			return EnumFuelMaterial.SYCAMORE;
		else if (is.getItemDamage() == 12)
			return EnumFuelMaterial.WHITECEDAR;
		else if (is.getItemDamage() == 13)
			return EnumFuelMaterial.WHITEELM;
		else if (is.getItemDamage() == 14)
			return EnumFuelMaterial.WILLOW;
		else if (is.getItemDamage() == 15)
			return EnumFuelMaterial.KAPOK;
		else if(is.getItemDamage() == 16)
			return EnumFuelMaterial.ACACIA;
		return EnumFuelMaterial.ASPEN;
	}

	public static boolean showShiftInformation()
	{
		if ((FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) && (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)))
			return true;
		return false;
	}

	public static boolean showCtrlInformation()
	{
		if ((FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) && (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)))
			return true;
		return false;
	}

	public static FoodStatsTFC getPlayerFoodStats(EntityPlayer player)
	{
		FoodStatsTFC foodstats = new FoodStatsTFC(player);
		foodstats.readNBT(player.getEntityData());
		return foodstats;
	}

	public static void setPlayerFoodStats(EntityPlayer player, FoodStatsTFC foodstats)
	{
		foodstats.writeNBT(player.getEntityData());
	}

	public static BodyTempStats getBodyTempStats(EntityPlayer player)
	{
		BodyTempStats body = new BodyTempStats();
		body.readNBT(player.getEntityData());
		return body;
	}

	public static void setBodyTempStats(EntityPlayer player, BodyTempStats tempStats)
	{
		tempStats.writeNBT(player.getEntityData());
	}

	public static SkillStats getSkillStats(EntityPlayer player)
	{
		SkillStats skills = new SkillStats(player);
		skills.readNBT(player.getEntityData());
		return skills;
	}

	public static void setSkillStats(EntityPlayer player, SkillStats skills)
	{
		skills.writeNBT(player.getEntityData());
	}

	public static boolean isTopFaceSolid(World world, int x, int y, int z)
	{
		if(world.getBlock(x, y, z).isNormalCube())
			return true;
		else if(world.getBlock(x, y, z) == TFCBlocks.stoneSlabs)
		{
			TEPartial te = (TEPartial) world.getTileEntity(x, y, z);
			if(BlockSlab.getTopChiselLevel(te.extraData) != 0)
				return false;
			return true;
		}
		else if(world.getBlock(x, y, z) == TFCBlocks.MetalSheet)
		{
			TEMetalSheet te = (TEMetalSheet) world.getTileEntity(x, y, z);
			if(te.TopExists())
				return true;
		}
		return false;
	}

	public static boolean isBottomFaceSolid(World world, int x, int y, int z)
	{
		if(world.getBlock(x, y, z).isNormalCube())
			return true;
		else if(world.getBlock(x, y, z) == TFCBlocks.stoneSlabs)
		{
			TEPartial te = (TEPartial) world.getTileEntity(x, y, z);
			if(BlockSlab.getBottomChiselLevel(te.extraData) != 0)
				return false;
			return true;
		}
		else if(world.getBlock(x, y, z) == TFCBlocks.MetalSheet)
		{
			TEMetalSheet te = (TEMetalSheet) world.getTileEntity(x, y, z);
			if(te.BottomExists())
				return true;
		}
		return false;
	}

	public static boolean isNorthFaceSolid(World world, int x, int y, int z)
	{
		Block bid = world.getBlock(x, y, z);
		if(bid.isNormalCube())
			return true;
		else if(bid == TFCBlocks.stoneSlabs)
		{
			TEPartial te = (TEPartial) world.getTileEntity(x, y, z);
			if(BlockSlab.getNorthChiselLevel(te.extraData) != 0)
				return false;
			return true;
		}
		else if(bid == TFCBlocks.MetalSheet)
		{
			TEMetalSheet te = (TEMetalSheet) world.getTileEntity(x, y, z);
			if(te.NorthExists())
				return true;
		}
		return false;
	}

	public static boolean isSouthFaceSolid(World world, int x, int y, int z)
	{
		if(world.getBlock(x, y, z).isNormalCube())
			return true;
		else if(world.getBlock(x, y, z) == TFCBlocks.stoneSlabs)
		{
			TEPartial te = (TEPartial) world.getTileEntity(x, y, z);
			if(BlockSlab.getSouthChiselLevel(te.extraData) != 0)
				return false;
			return true;
		}
		else if(world.getBlock(x, y, z) == TFCBlocks.MetalSheet)
		{
			TEMetalSheet te = (TEMetalSheet) world.getTileEntity(x, y, z);
			if(te.SouthExists())
				return true;
		}
		return false;
	}

	public static boolean isEastFaceSolid(World world, int x, int y, int z)
	{
		if(world.getBlock(x, y, z).isNormalCube())
			return true;
		else if(world.getBlock(x, y, z) == TFCBlocks.stoneSlabs)
		{
			TEPartial te = (TEPartial) world.getTileEntity(x, y, z);
			if(BlockSlab.getEastChiselLevel(te.extraData) != 0)
				return true;
		}
		else if(world.getBlock(x, y, z) == TFCBlocks.MetalSheet)
		{
			TEMetalSheet te = (TEMetalSheet) world.getTileEntity(x, y, z);
			if(te.EastExists())
				return true;
		}
		return false;
	}

	public static boolean isWestFaceSolid(World world, int x, int y, int z)
	{
		if(world.getBlock(x, y, z).isNormalCube())
			return true;
		else if(world.getBlock(x, y, z) == TFCBlocks.stoneSlabs)
		{
			TEPartial te = (TEPartial) world.getTileEntity(x, y, z);
			if(BlockSlab.getWestChiselLevel(te.extraData) != 0)
				return false;
			return true;
		}
		else if(world.getBlock(x, y, z) == TFCBlocks.MetalSheet)
		{
			TEMetalSheet te = (TEMetalSheet) world.getTileEntity(x, y, z);
			if(te.WestExists())
				return true;
		}
		return false;
	}

	public static boolean isOreIron(ItemStack is)
	{
		if (is.getItem() instanceof ItemOre && ((ItemOre) is.getItem()).GetMetalType(is) == Global.PIGIRON)
			return true;
		return false;
	}

	public static float getEntityMaxHealth(EntityLivingBase entity)
	{
		return (float) entity.getEntityAttribute(SharedMonsterAttributes.maxHealth).getAttributeValue();
	}

	public static float getPercentGrown(IAnimal animal)
	{
		float birth = animal.getBirthDay();
		float time = (int) TFC_Time.getTotalDays();
		float percent = (time - birth) / animal.getNumberOfDaysToAdult();
		return Math.min(percent, 1f);
	}

	public static void bindTexture(ResourceLocation texture)
	{
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
	}

	public static boolean isPlayerInDebugMode(EntityPlayer player)
	{
		if (TFCOptions.enableDebugMode)
			return true;
		return false;
	}

	/**
	 * Adds exhaustion to the player. 0.001 is a standard amount.
	 */
	public static void addPlayerExhaustion(EntityPlayer player, float exhaustion)
	{
		FoodStatsTFC foodstats = TFC_Core.getPlayerFoodStats(player);
		foodstats.addFoodExhaustion(exhaustion);
		foodstats.addWaterExhaustion(exhaustion);
		TFC_Core.setPlayerFoodStats(player, foodstats);
	}

	public static float getEnvironmentalDecay(float temp)
	{
		if (temp > 0)
		{
			float tempFactor = 1f - (15f / (15f + temp));
			return tempFactor * 2;
		}
		else
			return 0;
	}

	/**
	 * This is the default item ticking method for use by all containers. Call
	 * this if you don't want to do custom environmental decay math.
	 */
	public static void handleItemTicking(IInventory iinv, World world, int x, int y, int z)
	{
		handleItemTicking(iinv, world, x, y, z, 1);
	}

	/**
	 * This is the default item ticking method for use by all containers. Call
	 * this if you don't want to do custom environmental decay math.
	 */
	public static void handleItemTicking(ItemStack[] iinv, World world, int x, int y, int z)
	{
		handleItemTicking(iinv, world, x, y, z, 1);
	}

	/**
	 * This version of the method assumes that the environmental decay modifier
	 * has already been calculated.
	 */
	public static void handleItemTicking(IInventory iinv, World world, int x, int y, int z, float environmentalDecayFactor)
	{
		for (int i = 0; !world.isRemote && i < iinv.getSizeInventory(); i++)
		{
			ItemStack is = iinv.getStackInSlot(i);
			if (is != null && iinv.getStackInSlot(i).stackSize <= 0)
				iinv.setInventorySlotContents(i, null);

			if (is != null)
			{
				if ((is.getItem() instanceof ItemTerra && ((ItemTerra) is.getItem()).onUpdate(is, world, x, y, z)))
					continue;
				else if (is.getItem() instanceof ItemTerraBlock && ((ItemTerraBlock) is.getItem()).onUpdate(is, world, x, y, z))
					continue;
				is = tickDecay(is, world, x, y, z, environmentalDecayFactor);
				if(is != null)
					TFC_ItemHeat.HandleItemHeat(is);
			}
			iinv.setInventorySlotContents(i, is);
		}
	}

	/**
	 * This version of the method assumes that the environmental decay modifier
	 * has already been calculated.
	 */
	public static void handleItemTicking(ItemStack[] iinv, World world, int x, int y, int z, float environmentalDecayFactor)
	{
		for (int i = 0; !world.isRemote && i < iinv.length; i++)
		{
			ItemStack is = iinv[i];
			if (is != null && iinv[i].stackSize <= 0)
				iinv[i] = null;

			if (is != null)
			{
				if ((is.getItem() instanceof ItemTerra && ((ItemTerra) is.getItem()).onUpdate(is, world, x, y, z)))
					continue;
				else if (is.getItem() instanceof ItemTerraBlock && ((ItemTerraBlock) is.getItem()).onUpdate(is, world, x, y, z))
					continue;
				is = tickDecay(is, world, x, y, z, environmentalDecayFactor);
				if(is != null)
					TFC_ItemHeat.HandleItemHeat(is);
			}
			iinv[i] = is;
		}
	}

	/**
	 * @param is
	 * @param nbt
	 */
	private static ItemStack tickDecay(ItemStack is, World world, int x, int y, int z, float environmentalDecayFactor)
	{
		NBTTagCompound nbt = is.getTagCompound();
		if (nbt == null || !nbt.hasKey("foodWeight") || !nbt.hasKey("foodDecay"))
			return is;

		// if the tick timer is up then we cause decay.
		if (nbt.getInteger("decayTimer") < TFC_Time.getTotalHours())
		{
			int timeDiff = (int) (TFC_Time.getTotalHours() - nbt.getInteger("decayTimer"));
			float protMult = 1;

			if(TFCOptions.useDecayProtection)
			{
				if(timeDiff > TFCOptions.decayProtectionDays * 24)
				{
					nbt.setInteger("decayTimer", (int)TFC_Time.getTotalHours() - 24);
				}
				else if(timeDiff > 24)
				{
					protMult = 1-(timeDiff/(TFCOptions.decayProtectionDays * 24));
				}
			}

			float decay = nbt.getFloat("foodDecay");
			float thisDecayRate = 1.0f;
			// Get the base food decay rate
			if (is.getItem() instanceof IFood)
				thisDecayRate = ((IFood) is.getItem()).getDecayRate();
			// check if the food has a specially applied decay rate in its nbt
			// for some reason
			if (nbt.hasKey("decayRate"))
				thisDecayRate = nbt.getFloat("decayRate");
			// if the food is salted then we cut the decay rate in half
			if (nbt.hasKey("isSalted"))
				thisDecayRate *= 0.5f;

			/*
			 * Here we calculate the decayRate based on the environment. We do
			 * this before everything else so that its only done once per
			 * inventory
			 */
			int day = TFC_Time.getDayOfYearFromDays(TFC_Time.getDayFromTotalHours(nbt.getInteger("decayTimer")));
			float temp = TFC_Climate.getHeightAdjustedTempSpecificDay(world,day,nbt.getInteger("decayTimer"), x, y, z);
			float environmentalDecay = getEnvironmentalDecay(temp) * environmentalDecayFactor;

			if (decay < 0)
			{
				float d = 1 * (thisDecayRate * environmentalDecay);
				if (decay + d < 0)
					decay += d;
				else
					decay = 0;
			}
			else if (decay == 0)
			{
				decay = (nbt.getFloat("foodWeight") * (world.rand.nextFloat() * 0.005f))*TFCOptions.decayMultiplier;
			}
			else
			{
				double fdr = Global.FOOD_DECAY_RATE - 1;
				fdr *= thisDecayRate * environmentalDecay * protMult * TFCOptions.decayMultiplier;
				decay *= 1 + fdr;
			}
			nbt.setInteger("decayTimer", nbt.getInteger("decayTimer") + 1);
			nbt.setFloat("foodDecay", decay);
		}

		if (nbt.getFloat("foodDecay") / nbt.getFloat("foodWeight") > 0.9f)
		{
			if(is.getItem() instanceof IFood)
				is = ((IFood)is.getItem()).onDecayed(is, world, x, y, z);
			else
				is.stackSize = 0;
		}
		else
			is.setTagCompound(nbt);

		return is;
	}

	public static void animalDropMeat(Entity e, Item i, float foodWeight)
	{
		while (foodWeight > 0)
		{
			float fw = Helper.roundNumber(Math.min(Global.FOOD_MAX_WEIGHT, foodWeight), 10);
			if (fw < Global.FOOD_MAX_WEIGHT)
				foodWeight = 0;
			foodWeight -= fw;
			e.entityDropItem(ItemFoodTFC.createTag(new ItemStack(i, 1), fw), 0);
		}
	}

	public static Vec3 getEntityPos(Entity e)
	{
		return Vec3.createVectorHelper(e.posX, e.posY, e.posZ);
	}

	public static void giveItemToPlayer(ItemStack is, EntityPlayer player)
	{
		if(player.worldObj.isRemote)
			return;
		EntityItem ei = player.entityDropItem(is, 1);
		ei.delayBeforeCanPickup = 0;
	}

	public static boolean isFence(Block b)
	{
		return (b == TFCBlocks.Fence || b == TFCBlocks.Fence2);
	}

	public static boolean isVertSupport(Block b)
	{
		return (b == TFCBlocks.WoodSupportV || b == TFCBlocks.WoodSupportV2);
	}

	public static boolean isHorizSupport(Block b)
	{
		return (b == TFCBlocks.WoodSupportH || b == TFCBlocks.WoodSupportH2);
	}

	public static boolean isOceanicBiome(int id)
	{
		if(id == TFCBiome.ocean.biomeID || id == TFCBiome.DeepOcean.biomeID)
			return true;
		return false;
	}

	public static boolean isMountainBiome(int id)
	{
		if(id == TFCBiome.Mountains.biomeID || id == TFCBiome.MountainsEdge.biomeID)
			return true;
		return false;
	}

	public static boolean isBeachBiome(int id)
	{
		if(id == TFCBiome.beach.biomeID || id == TFCBiome.gravelbeach.biomeID)
			return true;
		return false;
	}

	public static boolean isValidCharcoalPitCover(Block block)
	{
		if(Blocks.fire.getFlammability(block) > 0 && block != TFCBlocks.LogPile) return false;

		return block == TFCBlocks.Firepit
				|| block == TFCBlocks.LogPile
				|| isRawStone(block)
				|| isCobbleStone(block)
				|| isBrickStone(block)
				|| isSmoothStone(block)
				|| isDirt(block)
				|| isSand(block)
				|| isGrassType1(block)
				|| isGrassType2(block)
				|| isGravel(block)
				|| block == Blocks.glass;
	}

	public static MovingObjectPosition getTargetBlock(EntityPlayer P)
	{
		float v1 = 1.0F;
		double v2 = P.prevPosX + (P.posX - P.prevPosX) * v1;
		double v3 = P.prevPosY + (P.posY - P.prevPosY) * v1 + 1.62D - P.yOffset;
		double v4 = P.prevPosZ + (P.posZ - P.prevPosZ) * v1;
		Vec3 v5 = Vec3.createVectorHelper(v2, v3, v4);

		float v6 = P.prevRotationYaw + (P.rotationYaw - P.prevRotationYaw) * v1;
		float v7 = P.prevRotationPitch + (P.rotationPitch - P.prevRotationPitch) * v1;

		float v8 = MathHelper.cos(-v6 * 0.017453292F - (float)Math.PI);
		float v9 = MathHelper.sin(-v6 * 0.017453292F - (float)Math.PI);
		float v10 = -MathHelper.cos(-v7 * 0.017453292F);
		float v11 = MathHelper.sin(-v7 * 0.017453292F);
		float v12 = v8 * v10;
		float v13 = v9 * v10;
		double v14 = 5.0D; 
		Vec3 v15 = v5.addVector(v13 * v14, v11 * v14, v12 * v14);

		return P.worldObj.rayTraceBlocks(v5, v15, true);
	}

	public static void setSweetMod(ItemStack is, int i)
	{
		if(is != null)
		{
			NBTTagCompound nbt = is.getTagCompound();
			nbt.setInteger("tasteSweetMod", nbt.getInteger("tasteSweetMod") + i);
		}
	}

	public static void setSourMod(ItemStack is, int i)
	{
		if(is != null)
		{
			NBTTagCompound nbt = is.getTagCompound();
			nbt.setInteger("tasteSourMod", nbt.getInteger("tasteSourMod") + i);
		}
	}

	public static void setSaltyMod(ItemStack is, int i)
	{
		if(is != null)
		{
			NBTTagCompound nbt = is.getTagCompound();
			nbt.setInteger("tasteSaltyMod", nbt.getInteger("tasteSaltyMod") + i);
		}
	}

	public static void setBitterMod(ItemStack is, int i)
	{
		if(is != null)
		{
			NBTTagCompound nbt = is.getTagCompound();
			nbt.setInteger("tasteBitterMod", nbt.getInteger("tasteBitterMod") + i);
		}
	}

	public static void setSavoryMod(ItemStack is, int i)
	{
		if(is != null)
		{
			NBTTagCompound nbt = is.getTagCompound();
			nbt.setInteger("tasteUmamiMod", nbt.getInteger("tasteUmamiMod") + i);
		}
	}
}
