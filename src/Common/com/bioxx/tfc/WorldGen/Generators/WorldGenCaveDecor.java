package com.bioxx.tfc.WorldGen.Generators;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

import cpw.mods.fml.common.IWorldGenerator;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.api.TFCBlocks;

public class WorldGenCaveDecor implements IWorldGenerator
{
	public WorldGenCaveDecor()
	{
	}

	@Override
	public void generate(Random rand, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) 
	{
		chunkX *= 16;
		chunkZ *= 16;
		for (int xCoord = 0; xCoord < 16; ++xCoord)
		{
			for (int zCoord = 0; zCoord < 16; ++zCoord)
			{
				for (int y = 127; y >= 0; --y)
				{
					int x = chunkX + xCoord;
					int z = chunkZ + zCoord;
					if(rand.nextInt(35) == 0 && createStalactite(world, rand, x, y, z))
					{
						if(!createStalagmite(world, rand, x, y - 5, z))
							createStalagmite(world, rand, x, y - 6, z);
					}
					else if(rand.nextInt(35) == 0)
					{
						createStalagmite(world, rand, x, y, z);
					}
				}
			}
		}
	}

	private boolean createStalactite(World world, Random rand, int x, int y, int z)
	{
		if(!world.isRemote && y > 8 && world.isAirBlock(x, y, z) && TFC_Core.isRawStone(world, x, y + 1, z))
		{
			if(world.isAirBlock(x, y-1, z) && world.isAirBlock(x, y-2, z) && world.isAirBlock(x, y-3, z))
			{
				world.setBlock(x, y, z, TFCBlocks.stoneStalac, 0, 0x2);
				world.setBlock(x, y - 1, z, TFCBlocks.stoneStalac, 0, 0x2);
				world.setBlock(x, y - 2, z, TFCBlocks.stoneStalac, 0, 0x2);
				return true;
			}
		}
		return false;
	}

	private boolean createStalagmite(World world, Random rand, int x, int y, int z)
	{
		if(!world.isRemote && y <128 && world.isAirBlock(x, y, z) && TFC_Core.isRawStone(world, x, y-1, z) && !world.canBlockSeeTheSky(x, y, z))
		{
			if(world.isAirBlock(x, y + 1, z) && world.isAirBlock(x, y + 2, z) && world.isAirBlock(x, y + 3, z))
			{
				world.setBlock(x, y, z, TFCBlocks.stoneStalac, 8, 0x2);
				world.setBlock(x, y + 1, z, TFCBlocks.stoneStalac, 8, 0x2);
				world.setBlock(x, y + 2, z, TFCBlocks.stoneStalac, 8, 0x2);
				return true;
			}
		}
		return false;
	}

}
