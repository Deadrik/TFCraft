package TFC.WorldGen.Generators;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import TFC.TFCBlocks;
import TFC.TFCItems;
import TFC.Blocks.Terrain.BlockOre;
import TFC.Core.TFC_Climate;
import TFC.Core.TFC_Core;
import TFC.TileEntities.TEWorldItem;
import TFC.WorldGen.TFCBiome;
import cpw.mods.fml.common.IWorldGenerator;

public class WorldGenLooseRocks implements IWorldGenerator
{


	public WorldGenLooseRocks()
	{

	}

	private boolean generateRocks(World world, Random random, int i, int j, int k)
	{
		if ((world.isAirBlock(i, j+1, k) || world.getBlockId(i, j+1, k) == Block.snow.blockID || 
				world.getBlockId(i, j+1, k) == Block.tallGrass.blockID) && 
				(world.getBlockMaterial(i, j, k) == Material.grass || 
				world.getBlockMaterial(i, j, k) == Material.rock) && world.isBlockOpaqueCube(i, j, k))
		{
			if(world.setBlock(i, j+1, k, TFCBlocks.worldItem.blockID, 0, 2))
			{
				TEWorldItem te =(TEWorldItem)world.getBlockTileEntity(i, j+1, k);
				ItemStack sample = getCoreSample(world,i,j,k);
				if(world.rand.nextInt(3) == 0 && sample != null)
				{
					te.storage[0] = sample;
				}
				else
				{
					int[] rockLayer = TFC_Climate.getRockLayer(i, j, k, 0);
					te.storage[0] = new ItemStack(TFCItems.LooseRock, 1, TFC_Core.getItemMetaFromStone(rockLayer[0], rockLayer[1]));
				}
			}
		}

		return true;
	}

	private ItemStack getCoreSample(World world, int xCoord, int yCoord, int zCoord)
	{
		ArrayList coreSample = new ArrayList<Item>();
		ArrayList coreSampleStacks = new ArrayList<ItemStack>();
		int x1 = (xCoord >> 4) << 4;
		int z1 = (zCoord >> 4) << 4;
		for(int x = 0; x <= 15; x++)
		{
			for(int z = 0; z <= 15; z++)
			{
				for(int y = yCoord; y > yCoord-35; y--)
				{
					if(world.getBlockId(x1+x, y, z1+z) == TFCBlocks.Ore.blockID)
					{
						int m = world.getBlockMetadata(x1+x, y, z1+z);
						if(!coreSample.contains(BlockOre.getDroppedItem(m)))
						{
							if(m!= 14 && m != 15)
							{
								coreSample.add(BlockOre.getDroppedItem(m));
								coreSampleStacks.add(new ItemStack(BlockOre.getDroppedItem(m), 1, m));
							}
						}
					}
				}
			}
		}
		if(coreSampleStacks.size() > 0)
		{
			return (ItemStack)coreSampleStacks.get(world.rand.nextInt(coreSampleStacks.size()));
		}
		return null;
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) 
	{
		chunkX *= 16;
		chunkZ *= 16;
		BiomeGenBase biome = world.getBiomeGenForCoords(chunkX, chunkZ);
		if(biome == TFCBiome.ocean) {
			return;
		}

		//rocks/ore
		for (int var2 = 0; var2 < 8; var2++)
		{
			int var7 = chunkX + random.nextInt(16) + 8;
			int var3 = chunkZ + random.nextInt(16) + 8;

			generateRocks(world, random, var7, world.getTopSolidOrLiquidBlock(var7, var3)-1, var3);
		}

		//sticks
		for (int var2 = 0; var2 < 5; var2++)
		{
			int var7 = chunkX + random.nextInt(16) + 8;
			int var3 = chunkZ + random.nextInt(16) + 8;

			generateSticks(world, random, var7, world.getTopSolidOrLiquidBlock(var7, var3)-1, var3);
		}
	}

	private boolean generateSticks(World world, Random random, int i, int j, int k)
	{
		if ((world.isAirBlock(i, j+1, k) || world.getBlockId(i, j+1, k) == Block.snow.blockID || 
				world.getBlockId(i, j+1, k) == Block.tallGrass.blockID) && 
				(world.getBlockMaterial(i, j, k) == Material.grass || 
				world.getBlockMaterial(i, j, k) == Material.rock) && world.isBlockOpaqueCube(i, j, k))
		{
			BiomeGenBase biome = world.getBiomeGenForCoords(i, k);
			if((biome == TFCBiome.beach || biome == TFCBiome.ocean || biome == TFCBiome.river || isNearTree(world, i, j, k)) && 
					world.setBlock(i, j+1, k, TFCBlocks.worldItem.blockID, 0, 2))
			{
				TEWorldItem te =(TEWorldItem)world.getBlockTileEntity(i, j+1, k);

				int[] rockLayer = TFC_Climate.getRockLayer(i, j, k, 0);
				te.storage[0] = new ItemStack(Item.stick, 1);
			}
		}

		return true;
	}

	private boolean isNearTree(World world, int i, int j, int k)
	{
		if(world.getBlockMaterial(i,j+3,k) == Material.leaves || world.getBlockMaterial(i+5,j+3,k) == Material.leaves || 
				world.getBlockMaterial(i-5,j+3,k) == Material.leaves || world.getBlockMaterial(i,j+3,k+5) == Material.leaves || 
				world.getBlockMaterial(i,j+3,k-5) == Material.leaves)
			return true;

		if(world.getBlockMaterial(i,j+6,k) == Material.leaves || world.getBlockMaterial(i+5,j+6,k) == Material.leaves || 
				world.getBlockMaterial(i-5,j+6,k) == Material.leaves || world.getBlockMaterial(i,j+6,k+5) == Material.leaves || 
				world.getBlockMaterial(i,j+6,k-5) == Material.leaves)
			return true;

		return false;
	}
}
