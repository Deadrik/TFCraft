package TFC.WorldGen.Generators;

import java.util.Random;

import cpw.mods.fml.common.IWorldGenerator;

import TFC.*;
import TFC.Core.TFC_Climate;
import TFC.Core.TFC_Core;
import TFC.WorldGen.BiomeDecoratorTFC;
import TFC.WorldGen.DataLayer;
import TFC.WorldGen.TFCBiome;
import TFC.WorldGen.TFCWorldChunkManager;

import net.minecraft.src.IChunkProvider;
import net.minecraft.src.Vec3;
import net.minecraft.src.World;

public class WorldGenLargeRock implements IWorldGenerator
{
	private int xWidth;
	private int xWidth2;
	private int zWidth;
	private int zWidth2;
	private int height;

	public WorldGenLargeRock(int par1, int par2)
	{
		xWidth = par1;
		xWidth2 = par1;
		zWidth = par1;
		zWidth2 = par1;
		height = par2;
	}
	
	public WorldGenLargeRock(int xw1, int xw2, int zw1, int zw2, int h)
	{
		xWidth = xw1;
		xWidth2 = xw2;
		zWidth = zw1;
		zWidth2 = zw2;
		height = h;
	}

	public boolean generate(World world, Random rand, int i, int j, int k)
	{
		int yOffset = 0;

		boolean isFlatEnough = false;

		for(; yOffset > -2 && !isFlatEnough; yOffset--)
		{
			if(world.isBlockNormalCube(i, j+yOffset, k))
			{
				if(world.isBlockNormalCube(i+1, j+yOffset, k) && world.isBlockNormalCube(i-1, j+yOffset, k) && 
						world.isBlockNormalCube(i-1, j+yOffset, k) && world.isBlockNormalCube(i, j+yOffset, k+1))
				{
					isFlatEnough = true;
				}
			}
		}

		if(j <= 155)
		{
			DataLayer rockLayer1 = ((TFCWorldChunkManager)world.getWorldChunkManager()).getRockLayerAt(i, k, 0);
			Vec3 center = Vec3.createVectorHelper(i, j + yOffset, k);
			for (int xCoord = i - xWidth; xCoord <= i + xWidth2; ++xCoord)
			{
				for (int zCoord = k - zWidth; zCoord <= k + zWidth2; ++zCoord)
				{
					for (int yCoord = j + yOffset - height; yCoord <= j + yOffset + height; ++yCoord)
					{
						Vec3 point = Vec3.createVectorHelper(xCoord, yCoord, zCoord);
						double distance = center.squareDistanceTo(point);
						
						boolean canPlaceX = true;
						boolean canPlaceZ = true;
						
						if(xCoord < i && distance > xWidth * 3)
							canPlaceX = false;
						if(xCoord > i && distance > xWidth2 * 3)
							canPlaceX = false;
						if(zCoord < i && distance > zWidth * 3)
							canPlaceZ = false;
						if(zCoord > i && distance > zWidth2 * 3)
							canPlaceZ = false;
						
						if(rand.nextInt(10) != 0 && canPlaceX && canPlaceZ)
						{
							world.setBlockAndMetadata(xCoord, yCoord, zCoord, rockLayer1.data1, rockLayer1.data2);
						}
					}
				}
			}
		}

		return true;
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) 
	{
		chunkX *= 16;
		chunkZ *= 16;


		int var7 = chunkX + random.nextInt(16) + 8;
		int var3 = chunkZ + random.nextInt(16) + 8;

		generate(world, random, var7, world.getTopSolidOrLiquidBlock(var7, var3)-1, var3);
	}
}
