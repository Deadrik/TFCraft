package com.bioxx.tfc.WorldGen.Generators;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.Core.TFC_Climate;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.Util.BlockMeta;
import com.bioxx.tfc.api.Enums.TFCDirection;
import com.bioxx.tfc.api.Util.ByteCoord;
import com.bioxx.tfc.api.Util.CollapseData;
import com.bioxx.tfc.api.Util.CollapseList;

import cpw.mods.fml.common.IWorldGenerator;

public class WorldGenFissure implements IWorldGenerator
{
	Random rand;
	int poolDepth;
	int creviceDepth = 1;
	Block fillBlock;
	int depth = 20;
	int minTunnel = 1;
	public boolean checkStability = true;
	boolean underground = false;
	int seed = 0;
	int rarity = 10;

	//Basic constructor
	public WorldGenFissure(Block b)
	{
		fillBlock = b;
	}

	public WorldGenFissure(Block b, int minTunnel, boolean s, int rare)
	{
		this(b);
		this.minTunnel = minTunnel;
		checkStability = s;
		rarity = rare;
	}

	public WorldGenFissure setSeed(int i)
	{
		seed = i;
		return this;
	}

	public WorldGenFissure setUnderground(boolean i, int d)
	{
		underground = i;
		depth = d;
		return this;
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) 
	{
		rand = random;
		chunkX *= 16;
		chunkZ *= 16;

		int startX = chunkX + random.nextInt(16) + 8;
		int startZ = chunkZ + random.nextInt(16) + 8;
		int startY = world.getTopSolidOrLiquidBlock(startX, startZ)-1;

		if(rand.nextInt(rarity) != 0)
			return;

		if(underground)
			startY = depth + rand.nextInt(60);

		generate(world, rand, startX, startY, startZ);
	}

	public void generate(World world, Random rand, int x, int y, int z)
	{
		creviceDepth = 1;
		if(rand.nextInt(100) < 50)
			creviceDepth += 4 + rand.nextInt(15);
		poolDepth = 1+rand.nextInt(2);

		for(int d = 1; d <= poolDepth; d++)
		{
			if(!world.getBlock(x, y-d, z).isNormalCube())
				return;
		}

		int stability = TFC_Climate.getStability(world, x, z);
		Block block = world.getBlock(x,y,z);

		if(block != null && block.getMaterial() == Material.water)
			return;
		if(underground)
			y-= 20+rand.nextInt(depth);
		if(checkStability && stability == 0)
			return;
		if(stability == 1 && fillBlock != null && fillBlock.getMaterial() == Material.water)
			fillBlock = TFCBlocks.HotWater;
		if(!TFC_Core.isGround(block))
			return;

		ArrayList<ByteCoord> map = getCollapseMap(world, x,y-creviceDepth,z);
		BlockMeta rockLayer = fillBlock != null ? TFC_Climate.getRockLayer(world, x, y, z, 2) : new BlockMeta(Blocks.air, -1);
		boolean makeTunnel = map.size() > 10;
		if(rockLayer.block == null)
			return;
		for(ByteCoord b : map)
		{
			world.setBlockToAir(x + b.x, y + b.y, z + b.z);
			for(int d = 1; d <= poolDepth; d++)
				fill(world, x + b.x, y + b.y - d, z + b.z, rockLayer.block, rockLayer.meta, fillBlock != null ? fillBlock : Blocks.air);

			int rx = 0;
			int rz = 0;
			for(int d = 0; d <= creviceDepth; d++)
			{
				carve(world, x + b.x, y + b.y + d, z + b.z, rockLayer.block, rockLayer.meta);
				if(rand.nextInt(3) == 0)
				{
					rx = -1 + rand.nextInt(3);
					rz = -1 + rand.nextInt(3);
					carve(world, x + b.x + rx, y + b.y + d, z + b.z + rz, rockLayer.block, rockLayer.meta);
				}
			}
			if(fillBlock != null && fillBlock.getMaterial() == Material.lava)
				world.setBlock(x + b.x, y + b.y-poolDepth-1, z + b.z, rockLayer.block, rockLayer.meta, 2);
		}

		if(makeTunnel)
			makeTunnel(rand, world, x, z, y, rockLayer);
	}

	private void carve(World world, int x, int y, int z, Block block, int meta)
	{
		if(world.getBlock(x, y, z).getMaterial() != Material.air && TFC_Core.isGround(world.getBlock(x, y, z)))
			world.setBlockToAir(x, y, z);
		if(world.getBlock(x - 1, y, z).getMaterial() != Material.air && TFC_Core.isGround(world.getBlock(x - 1, y, z)) && !TFC_Core.isGrass(world.getBlock(x - 1, y, z)))
			world.setBlock(x - 1, y, z, block, meta, 2);
		if(world.getBlock(x + 1, y, z).getMaterial() != Material.air && TFC_Core.isGround(world.getBlock(x + 1, y, z)) && !TFC_Core.isGrass(world.getBlock(x + 1, y, z)))
			world.setBlock(x + 1, y, z, block, meta, 2);
		if(world.getBlock(x, y, z - 1).getMaterial() != Material.air && TFC_Core.isGround(world.getBlock(x, y, z - 1)) && !TFC_Core.isGrass(world.getBlock(x, y, z - 1)))
			world.setBlock(x, y, z - 1, block, meta, 2);
		if(world.getBlock(x, y, z + 1).getMaterial() != Material.air && TFC_Core.isGround(world.getBlock(x, y, z + 1)) && !TFC_Core.isGrass(world.getBlock(x, y, z + 1)))
			world.setBlock(x, y, z + 1, block, meta, 2);
	}

	private void fill(World world, int x, int y, int z, Block block, int meta, Block fill)
	{
		world.setBlock(x, y, z, fill, 0, 2);
		if(world.getBlock(x - 1, y, z).getMaterial() == Material.air)
			world.setBlock(x - 1, y, z, block, meta, 2);
		if(world.getBlock(x + 1, y, z).getMaterial() == Material.air)
			world.setBlock(x + 1, y, z, block, meta, 2);
		if(world.getBlock(x, y, z - 1).getMaterial() == Material.air)
			world.setBlock(x, y, z - 1, block, meta, 2);
		if(world.getBlock(x, y, z + 1).getMaterial() == Material.air)
			world.setBlock(x, y, z + 1, block, meta, 2);
		if(world.getBlock(x, y - 1, z).getMaterial() == Material.air)
			world.setBlock(x, y - 1, z, block, meta, 2);
	}

	private void makeTunnel(Random random, World world, int x, int z, int y, BlockMeta rockLayer)
	{
		int xCoord = x, yCoord = y-poolDepth-1, zCoord = z;
		float downChance = 75;
		while(yCoord > minTunnel)
		{
			if(world.getBlock(xCoord, yCoord, zCoord) == Blocks.bedrock)
				break;
			if(random.nextFloat() < downChance / 100f)
			{
				yCoord--;
			}
			else
			{
				int dir = random.nextInt(3);
				switch(dir)
				{
				case 0: xCoord++; break;
				case 1: xCoord--; break;
				case 2: zCoord++; break;
				case 3: zCoord--; break;
				}
			}

			world.setBlock(xCoord, yCoord, zCoord, fillBlock != null ? fillBlock : Blocks.air, 0, 0x2);

			if(fillBlock != null && world.getBlock(xCoord+1, yCoord, zCoord).getMaterial() != fillBlock.getMaterial())
				world.setBlock(xCoord+1, yCoord, zCoord, rockLayer.block, rockLayer.meta, 2);
			if(fillBlock != null && world.getBlock(xCoord-1, yCoord, zCoord).getMaterial() != fillBlock.getMaterial())
				world.setBlock(xCoord-1, yCoord, zCoord, rockLayer.block, rockLayer.meta, 2);
			if(fillBlock != null && world.getBlock(xCoord, yCoord, zCoord+1).getMaterial() != fillBlock.getMaterial())
				world.setBlock(xCoord, yCoord, zCoord+1, rockLayer.block, rockLayer.meta, 2);
			if(fillBlock != null && world.getBlock(xCoord, yCoord, zCoord-1).getMaterial() != fillBlock.getMaterial())
				world.setBlock(xCoord, yCoord, zCoord-1, rockLayer.block, rockLayer.meta, 2);
		}
	}

	public ArrayList<ByteCoord> getCollapseMap(World world, int i, int j, int k)
	{
		int checks = 0;
		ArrayList<ByteCoord> map = new ArrayList<ByteCoord>();
		ArrayList<ByteCoord> checkedmap = new ArrayList<ByteCoord>();
		CollapseList<CollapseData> checkQueue = new CollapseList<CollapseData>();
		final float baseCollapse = 55f;
		final float incrementChance = 5f;
		final float incrementChanceDiag = 2.5f;

		BlockMeta rockLayer = fillBlock != null && fillBlock.getMaterial() == Material.lava ? 
				TFC_Climate.getRockLayer(world, i, j, k, 2) : TFC_Climate.getRockLayer(world, i, j, k, TFC_Core.getRockLayerFromHeight(world, i, j, k));

				int worldX;
				int worldY;
				int worldZ;
				int localX;
				int localY;
				int localZ;
				Block id;
				//Now we add each of the blocks around it so that the initial collapse tries to propogate in each direction
				checkQueue.add(new CollapseData(new ByteCoord(0,-1,0), 100, TFCDirection.NULL));

				while(checkQueue.peek() != null)
				{
					CollapseData block = checkQueue.peek();
					worldX = block.coords.x + i;
					worldY = block.coords.y + j;
					worldZ = block.coords.z + k;
					localX = block.coords.x;
					localY = block.coords.y;
					localZ = block.coords.z;
					id = world.getBlock(worldX, worldY, worldZ);

					if(!checkedmap.contains(block) && TFC_Core.isGround(id) && 
							world.rand.nextFloat() < block.collapseChance/100f)
					{
						checks++;
						//System.out.println("Number of block checks: " + checks + " | Queue Length: " + checkQueue.size());

						map.add(block.coords);

						if(checkQueue.size() < 500)
							switch(block.direction)
							{
							case NORTH:
							{
								checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX + 0, localY + 0, localZ - 1), block.collapseChance - incrementChance, TFCDirection.NORTH));
								checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX + 1, localY + 0, localZ + 0), block.collapseChance - incrementChance, TFCDirection.EAST));
								checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX - 1, localY + 0, localZ + 0), block.collapseChance - incrementChance, TFCDirection.WEST));
								break;
							}
							case SOUTH:
							{
								checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX + 0, localY + 0, localZ + 1), block.collapseChance - incrementChance, TFCDirection.SOUTH));
								checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX + 1, localY + 0, localZ + 0), block.collapseChance - incrementChance, TFCDirection.EAST));
								checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX - 1, localY + 0, localZ + 0), block.collapseChance - incrementChance, TFCDirection.WEST));
								break;
							}
							case EAST:
							{
								checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX + 0, localY + 0, localZ + 1), block.collapseChance - incrementChance, TFCDirection.SOUTH));
								checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX + 1, localY + 0, localZ + 0), block.collapseChance - incrementChance, TFCDirection.EAST));
								checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX + 0, localY + 0, localZ - 1), block.collapseChance - incrementChance, TFCDirection.NORTH));
								break;
							}
							case WEST:
							{
								checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX + 0, localY + 0, localZ + 1), block.collapseChance - incrementChance, TFCDirection.SOUTH));
								checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX - 1, localY + 0, localZ + 0), block.collapseChance - incrementChance, TFCDirection.WEST));
								checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX + 0, localY + 0, localZ - 1), block.collapseChance - incrementChance, TFCDirection.NORTH));
								break;
							}
							case SOUTHEAST:
							{
								checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX + 1, localY + 0, localZ - 1), block.collapseChance - incrementChanceDiag, TFCDirection.SOUTHEAST));
								checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX + 0, localY + 0, localZ + 1), block.collapseChance - incrementChance, TFCDirection.SOUTH));
								checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX + 1, localY + 0, localZ + 0), block.collapseChance - incrementChance, TFCDirection.EAST));
								break;
							}
							case SOUTHWEST:
							{
								checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX - 1, localY + 0, localZ - 1), block.collapseChance - incrementChanceDiag, TFCDirection.SOUTHWEST));
								checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX + 0, localY + 0, localZ + 1), block.collapseChance - incrementChance, TFCDirection.SOUTH));
								checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX - 1, localY + 0, localZ + 0), block.collapseChance - incrementChance, TFCDirection.WEST));
								break;
							}
							case NORTHEAST:
							{
								checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX + 1, localY + 0, localZ + 1), block.collapseChance - incrementChanceDiag, TFCDirection.NORTHEAST));
								checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX + 1, localY + 0, localZ + 0), block.collapseChance - incrementChance, TFCDirection.EAST));
								checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX + 0, localY + 0, localZ - 1), block.collapseChance - incrementChance, TFCDirection.NORTH));
								break;
							}
							case NORTHWEST:
							{
								checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX - 1, localY + 0, localZ + 1), block.collapseChance - incrementChanceDiag, TFCDirection.NORTHWEST));
								checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX + 0, localY + 0, localZ - 1), block.collapseChance - incrementChance, TFCDirection.NORTH));
								checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX - 1, localY + 0, localZ + 0), block.collapseChance - incrementChance, TFCDirection.WEST));
								break;
							}
							default:
							{
								checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX + 1, localY + 0, localZ + 0), baseCollapse - incrementChance, TFCDirection.EAST));
								checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX - 1, localY + 0, localZ + 0), baseCollapse - incrementChance, TFCDirection.WEST));
								checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX + 1, localY + 0, localZ + 1), baseCollapse - incrementChanceDiag, TFCDirection.NORTHEAST));
								checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX + 1, localY + 0, localZ - 1), baseCollapse - incrementChanceDiag, TFCDirection.SOUTHEAST));
								checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX - 1, localY + 0, localZ + 1), baseCollapse - incrementChanceDiag, TFCDirection.NORTHWEST));
								checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX - 1, localY + 0, localZ - 1), baseCollapse - incrementChanceDiag, TFCDirection.SOUTHWEST));
								checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX + 0, localY + 0, localZ + 1), baseCollapse - incrementChance, TFCDirection.SOUTH));
								checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX + 0, localY + 0, localZ - 1), baseCollapse - incrementChance, TFCDirection.NORTH));
							}
							}
					}
					else if(block.collapseChance < 100)
					{
						for(int d = 0; d <= poolDepth; d++)
						{
							if(TFC_Core.isGround(world.getBlock(worldX, worldY-d, worldZ)))
								world.setBlock(worldX, worldY-d, worldZ, rockLayer.block, rockLayer.meta, 2);
						}
					}
					checkedmap.add(block.coords);
					checkQueue.removeFirst();
				}
				return map;
	}
}
