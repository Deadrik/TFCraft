package TFC.WorldGen.Generators;

import java.util.Random;

import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import TFC.Core.TFC_Core;
import TFC.WorldGen.DataLayer;
import TFC.WorldGen.TFCWorldChunkManager;
import cpw.mods.fml.common.IWorldGenerator;

public class WorldGenLargeRock implements IWorldGenerator
{
	private int xWidth;
	private int xWidth2;
	private int zWidth;
	private int zWidth2;
	private int height = 3;

	public WorldGenLargeRock()
	{
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
			int i2,j2,k2;
			i2 = i+(rand.nextInt(2)+1)*(rand.nextBoolean()?1:-1);
			j2 = j+(rand.nextInt(2)+1)*(rand.nextBoolean()?1:-1);
			k2 = k+(rand.nextInt(2)+1)*(rand.nextBoolean()?1:-1);
			genFromPoint(world,rand,i,j,k,yOffset);
			genFromPoint(world,rand,i2,j2,k2,yOffset);
		}

		return true;
	}

	public void genFromPoint(World world, Random rand, int i, int j, int k,int yOffset){
		DataLayer rockLayer1 = ((TFCWorldChunkManager)world.getWorldChunkManager()).getRockLayerAt(i, k, 0);
		Vec3 center = Vec3.createVectorHelper(i, j + yOffset, k);
		xWidth = 3;
		xWidth2 = 3;
		zWidth = 3;
		zWidth2 = 3;
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

					if(xCoord < i && distance > xWidth * 4) {
						canPlaceX = false;
					}
					if(xCoord > i && distance > xWidth2 * 4) {
						canPlaceX = false;
					}
					if(zCoord < i && distance > zWidth * 4) {
						canPlaceZ = false;
					}
					if(zCoord > i && distance > zWidth2 * 4) {
						canPlaceZ = false;
					}

					if(rand.nextInt(10)+1 != 0 && canPlaceX && canPlaceZ)
					{
						world.setBlock(xCoord, yCoord, zCoord, rockLayer1.data1, rockLayer1.data2, 0x2);
					}
				}
			}
		}
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) 
	{
		chunkX *= 16;
		chunkZ *= 16;


		int xCoord = 0;
		int zCoord = 0;

		for (int count = 0; count < 1; ++count)
		{
			xCoord = chunkX + random.nextInt(16) + 8;
			zCoord = chunkZ + random.nextInt(16) + 8;
			int yCoord = world.getHeightValue(xCoord, zCoord)-1;

			xWidth = 2+random.nextInt(6);
			xWidth2 = 2+random.nextInt(6);
			zWidth = 2+random.nextInt(6);
			zWidth2 = 2+random.nextInt(6);

			if(random.nextInt(20) == 0 && TFC_Core.isSoil(world.getBlockId(xCoord, yCoord, zCoord))) 
			{
				generate(world, random, xCoord, world.getTopSolidOrLiquidBlock(xCoord, zCoord)-1, zCoord);
			}
		}


	}
}
