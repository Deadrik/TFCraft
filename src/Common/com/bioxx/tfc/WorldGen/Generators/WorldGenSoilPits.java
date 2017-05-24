package com.bioxx.tfc.WorldGen.Generators;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

import cpw.mods.fml.common.IWorldGenerator;

import com.bioxx.tfc.Core.TFC_Climate;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.WorldGen.DataLayer;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.Constant.Global;

public class WorldGenSoilPits implements IWorldGenerator
{
	private static WorldGenBerryBush cranberryGen = new WorldGenBerryBush(false, 6, 15, 1, 6, TFCBlocks.peatGrass);
	private static WorldGenBerryBush cloudberryGen = new WorldGenBerryBush(false, 10, 12, 1, 6, TFCBlocks.peatGrass);

	public WorldGenSoilPits()
	{
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) 
	{
		chunkX *= 16;
		chunkZ *= 16;

		int x = chunkX + random.nextInt(16) + 8;
		int z = chunkZ + random.nextInt(16) + 8;
		generateClay(world, random, x, world.getTopSolidOrLiquidBlock(x, z), z);

		x = chunkX + random.nextInt(16) + 8;
		z = chunkZ + random.nextInt(16) + 8;
		if(generatePeat(world, random, x, world.getTopSolidOrLiquidBlock(x, z), z))
		{
			if(random.nextInt(5) == 0)
			{
				if (!cloudberryGen.generate(world, random, x, world.getTopSolidOrLiquidBlock(x, z) + 1, z))
					cranberryGen.generate(world, random, x, world.getTopSolidOrLiquidBlock(x, z) + 1, z);
			}
		}
	}

	public boolean generatePeat(World world, Random random, int xCoord, int yCoord, int zCoord)
	{
		int var6 = random.nextInt(16) + 8;
		byte var7 = 2;
		boolean flag = false;

		if(random.nextInt(50) == 0 && yCoord <= Global.SEALEVEL)
		{
			for (int x = xCoord - var6; x <= xCoord + var6; ++x)
			{
				for (int z = zCoord - var6; z <= zCoord + var6; ++z)
				{
					int var10 = x - xCoord;
					int var11 = z - zCoord;
					if (var10 * var10 + var11 * var11 <= var6 * var6)
					{
						for (int y = yCoord - var7; y <= yCoord + var7; ++y)
						{
							Block block = world.getBlock(x, y, z);
							if(TFC_Climate.isSwamp(world, x, y, z))
							{
								if (TFC_Core.isDirt(block) || TFC_Core.isClay(block) || TFC_Core.isPeat(block))
								{
									world.setBlock(x, y, z, TFCBlocks.peat, 0, 2);
									flag = true;
								}
								else if(TFC_Core.isGrass(block))
								{
									world.setBlock(x, y, z, TFCBlocks.peatGrass, 0, 2);
									flag = true;
								}
							}
						}
					}
				}
			}
		}
		return flag;
	}

	public boolean generateClay(World world, Random rand, int i, int j, int k)
	{
		int radius = rand.nextInt(14) + 2;
		byte depth = (byte) (rand.nextInt(3) + 1);
		boolean flag = false;
		if(rand.nextInt(30) == 0 && j <= 150)
		{
			for (int xCoord = i - radius; xCoord <= i + radius; ++xCoord)
			{
				for (int zCoord = k - radius; zCoord <= k + radius; ++zCoord)
				{
					int x = xCoord - i;
					int z = zCoord - k;
					if (x * x + z * z <= radius * radius && TFC_Climate.getRainfall(world, xCoord, Global.SEALEVEL, zCoord) >= 500)
					{
						flag = false;
						for (int yCoord = j - depth; yCoord <= j + depth; ++yCoord)
						{
							Block block = world.getBlock(xCoord, yCoord, zCoord);
							DataLayer rockLayer1 = TFC_Climate.getCacheManager(world).getRockLayerAt(xCoord, zCoord, 0);
							if (block == TFCBlocks.dirt || block == TFCBlocks.dirt2)
							{
								world.setBlock(xCoord, yCoord, zCoord, TFC_Core.getTypeForClay(block), TFC_Core.getSoilMetaFromStone(rockLayer1.block, rockLayer1.data2), 0x2);
								flag = true;
							}
							else if(block == TFCBlocks.grass || block == TFCBlocks.grass2)
							{
								world.setBlock(xCoord, yCoord, zCoord, TFC_Core.getTypeForClayGrass(block), TFC_Core.getSoilMetaFromStone(rockLayer1.block, rockLayer1.data2), 0x2);
								flag = true;
							}
						}
						if(flag && rand.nextInt(15) == 0)
						{
							int y = world.getTopSolidOrLiquidBlock(xCoord, zCoord);
							if(world.isAirBlock(xCoord, y, zCoord) && TFC_Core.isSoil(world.getBlock(xCoord, y-1, zCoord)))
								world.setBlock(xCoord, y, zCoord, TFCBlocks.flora, 0, 2);
						}
					}
				}
			}
		}
		return flag;
	}
}
