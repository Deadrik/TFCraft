package com.bioxx.tfc.WorldGen.Generators;

import com.bioxx.tfc.Blocks.Terrain.BlockOre;
import com.bioxx.tfc.Blocks.Terrain.BlockOre2;
import com.bioxx.tfc.Core.TFC_Climate;
import com.bioxx.tfc.TileEntities.TEWorldItem;
import com.bioxx.tfc.WorldGen.DataLayer;
import com.bioxx.tfc.WorldGen.TFCBiome;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCItems;
import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

import java.util.ArrayList;
import java.util.Random;

public class WorldGenLooseRocks implements IWorldGenerator
{
	public WorldGenLooseRocks()
	{
	}

	public boolean generateRocks(World world, Random random, int i, int j, int k)
	{
		if ((world.isAirBlock(i, j + 1, k) || world.getBlock(i, j + 1, k) == Blocks.snow || world.getBlock(i, j + 1, k) == TFCBlocks.tallGrass) && 
				(world.getBlock(i, j, k).getMaterial() == Material.grass || world.getBlock(i, j, k).getMaterial() == Material.rock) && world.getBlock(i, j, k).isOpaqueCube())
		{
			if(world.setBlock(i, j+1, k, TFCBlocks.worldItem, 0, 2))
			{
				TEWorldItem te =(TEWorldItem)world.getTileEntity(i, j + 1, k);
				ItemStack sample = getCoreSample(world, i, j, k);
				if(world.rand.nextInt(3) == 0 && sample != null)
				{
					te.storage[0] = sample;
				}
				else
				{
					DataLayer dl = TFC_Climate.getRockLayer(world, i, j, k, 0);
					//BlockMeta rockLayer = new BlockMeta(dl.block, dl.data2);
					te.storage[0] = new ItemStack(TFCItems.looseRock, 1, dl.data1);
				}
			}
		}
		return true;
	}

	private ItemStack getCoreSample(World world, int xCoord, int yCoord, int zCoord)
	{
		ArrayList<Item> coreSample = new ArrayList<Item>();
		ArrayList<ItemStack> coreSampleStacks = new ArrayList<ItemStack>();
		for (int x = -15; x < 16; x++)
		{
			for (int z = -15; z < 16; z++)
			{
				for(int y = yCoord; y > yCoord-35; y--)
				{
					if (world.blockExists(xCoord + x, y, zCoord + z) &&
							(world.getBlock(xCoord + x, y, zCoord + z) == TFCBlocks.ore)) {
						int m = world.getBlockMetadata(xCoord + x, y, zCoord + z);
						if (!coreSample.contains(BlockOre.getDroppedItem(m))) {
							coreSample.add(BlockOre.getDroppedItem(m));
							coreSampleStacks.add(new ItemStack(BlockOre.getDroppedItem(m), 1, m));
						}
					}
					else if (world.blockExists(xCoord + x, y, zCoord + z) &&
							(world.getBlock(xCoord + x, y, zCoord +z) == TFCBlocks.ore2)) {
						int m = world.getBlockMetadata(xCoord + x, y, zCoord + z);
						if (!coreSample.contains(BlockOre2.getDroppedItem(m))) {
							coreSample.add(BlockOre2.getDroppedItem(m));
							coreSampleStacks.add(new ItemStack(BlockOre2.getDroppedItem(m + 16), 1, m + 16));
						}
					}
				}
			}
		}
		if (!coreSampleStacks.isEmpty())
			return coreSampleStacks.get(world.rand.nextInt(coreSampleStacks.size()));
		return null;
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) 
	{
		chunkX *= 16;
		chunkZ *= 16;

		if (world.getBiomeGenForCoords(chunkX, chunkZ) instanceof TFCBiome) // Fixes ClassCastException
		{
			TFCBiome biome = (TFCBiome) world.getBiomeGenForCoords(chunkX, chunkZ);
			if (biome == TFCBiome.OCEAN || biome == TFCBiome.DEEP_OCEAN)
				return;
		}

		//rocks/ore
		for (int itemCount = 0; itemCount < 8; itemCount++)
		{
			int xCoord = chunkX + random.nextInt(16) + 8;
			int zCoord = chunkZ + random.nextInt(16) + 8;
			generateRocks(world, random, xCoord, world.getTopSolidOrLiquidBlock(xCoord, zCoord) - 1, zCoord);
		}

		//sticks
		for (int stickCount = 0; stickCount < 5; stickCount++)
		{
			int xCoord = chunkX + random.nextInt(16) + 8;
			int zCoord = chunkZ + random.nextInt(16) + 8;
			generateSticks(world, random, xCoord, world.getTopSolidOrLiquidBlock(xCoord, zCoord) - 1, zCoord);
		}
	}

	public boolean generateSticks(World world, Random random, int i, int j, int k)
	{
		if ((world.isAirBlock(i, j + 1, k) || world.getBlock(i, j + 1, k) == Blocks.snow || world.getBlock(i, j + 1, k) == TFCBlocks.tallGrass) && 
				(world.getBlock(i, j, k).getMaterial() == Material.grass || world.getBlock(i, j, k).getMaterial() == Material.rock ||
				world.getBlock(i, j, k) .getMaterial() == Material.sand || world.getBlock(i, j, k).getMaterial() == Material.ground) && world.getBlock(i, j, k).isOpaqueCube())
		{
			if (world.getBiomeGenForCoords(i, k) instanceof TFCBiome) // Fixes ClassCastException
			{
				TFCBiome biome = (TFCBiome) world.getBiomeGenForCoords(i, k);
				if ((biome == TFCBiome.DEEP_OCEAN || biome == TFCBiome.BEACH || biome == TFCBiome.GRAVEL_BEACH || biome == TFCBiome.OCEAN || biome == TFCBiome.RIVER || isNearTree(world, i, j, k)) &&
						world.setBlock(i, j + 1, k, TFCBlocks.worldItem, 0, 2))
				{
					TEWorldItem te = (TEWorldItem) world.getTileEntity(i, j + 1, k);
					//BlockMeta rockLayer = TFC_Climate.getRockLayer(i, j, k, 0);
					te.storage[0] = new ItemStack(TFCItems.stick, 1);
				}
			}
		}
		return true;
	}

	private boolean isNearTree(World world, int i, int j, int k)
	{
		if(world.getBlock(i, j + 3, k).getMaterial() == Material.leaves ||
				world.getBlock(i + 5, j + 3, k).getMaterial() == Material.leaves ||
				world.getBlock(i - 5, j + 3, k).getMaterial() == Material.leaves ||
				world.getBlock(i, j + 3, k + 5).getMaterial() == Material.leaves ||
				world.getBlock(i, j + 3, k - 5).getMaterial() == Material.leaves)
			return true;
		else
			return world.getBlock(i, j + 6, k).getMaterial() == Material.leaves ||
				world.getBlock(i + 5, j + 6, k).getMaterial() == Material.leaves ||
				world.getBlock(i - 5, j + 6, k).getMaterial() == Material.leaves ||
				world.getBlock(i, j + 6, k + 5).getMaterial() == Material.leaves ||
				world.getBlock(i, j + 6, k - 5).getMaterial() == Material.leaves;
	}

	public static boolean rocksNearby(World world, int i, int j, int k)
	{
		if ((world.getBlock(i + 1, j + 1, k) != TFCBlocks.worldItem) || 
				(world.getBlock(i + 1, j + 1, k + 1) != TFCBlocks.worldItem) ||
				(world.getBlock(i, j + 1, k + 1) != TFCBlocks.worldItem) ||
				(world.getBlock(i - 1, j + 1, k) != TFCBlocks.worldItem) ||
				(world.getBlock(i - 1, j + 1, k + 1) != TFCBlocks.worldItem) ||
				(world.getBlock(i - 1, j + 1, k - 1) != TFCBlocks.worldItem) ||
				(world.getBlock(i, j + 1, k - 1) != TFCBlocks.worldItem) ||
				(world.getBlock(i + 1, j + 1, k) != TFCBlocks.worldItem))
			{
				return true;
			}
		
		return false;
	}
}
