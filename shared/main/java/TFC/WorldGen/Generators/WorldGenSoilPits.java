package TFC.WorldGen.Generators;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import TFC.TFCBlocks;
import TFC.Core.TFC_Climate;
import TFC.Core.TFC_Core;
import TFC.WorldGen.DataLayer;
import TFC.WorldGen.TFCWorldChunkManager;
import cpw.mods.fml.common.IWorldGenerator;

public class WorldGenSoilPits implements IWorldGenerator
{
	static WorldGenBerryBush cranberryGen = new WorldGenBerryBush(false, 6, 15, 1, 6, TFCBlocks.Peat.blockID);
	static WorldGenBerryBush cloudberryGen = new WorldGenBerryBush(false, 10, 12, 1, 6, TFCBlocks.Peat.blockID);

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
			if(random.nextInt(5) == 0) {
				if(!cranberryGen.generate(world, random, x, world.getTopSolidOrLiquidBlock(x, z)+1, z)) {
					cloudberryGen.generate(world, random, x, world.getTopSolidOrLiquidBlock(x, z)+1, z);
				}
			}
		}

	}

	public boolean generatePeat(World par1World, Random par2Random, int xCoord, int yCoord, int zCoord)
	{
		int var6 = par2Random.nextInt(16) + 8;
		byte var7 = 2;
		boolean flag = false;
		if(par2Random.nextInt(50) == 0 && yCoord <= 145)
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
							int blockID = par1World.getBlockId(x, y, z);

							if(TFC_Climate.isSwamp(x, y, z))
							{
								if (TFC_Core.isDirt(blockID) || TFC_Core.isClay(blockID) || TFC_Core.isPeat(blockID))
								{
									par1World.setBlock(x, y, z, TFCBlocks.Peat.blockID);
									flag = true;
								}
								else if(TFC_Core.isGrass(blockID))
								{
									par1World.setBlock(x, y, z, TFCBlocks.PeatGrass.blockID);
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

					if (x * x + z * z <= radius * radius && TFC_Climate.getRainfall(xCoord, 145, zCoord) >= 500)
					{
						for (int yCoord = j - depth; yCoord <= j + depth; ++yCoord)
						{
							int ID = world.getBlockId(xCoord, yCoord, zCoord);

							DataLayer rockLayer1 = ((TFCWorldChunkManager)world.getWorldChunkManager()).getRockLayerAt(xCoord, zCoord, 0);

							if (ID == TFCBlocks.Dirt.blockID || ID == TFCBlocks.Dirt2.blockID)
							{
								world.setBlock(xCoord, yCoord, zCoord, 
										TFC_Core.getTypeForClay(rockLayer1.data2), TFC_Core.getSoilMetaFromStone(rockLayer1.data1, rockLayer1.data2), 0x2);
								flag = true;
							}
							else if(ID == TFCBlocks.Grass.blockID || ID == TFCBlocks.Grass2.blockID)
							{
								world.setBlock(xCoord, yCoord, zCoord, 
										TFC_Core.getTypeForClayGrass(rockLayer1.data2), TFC_Core.getSoilMetaFromStone(rockLayer1.data1, rockLayer1.data2), 0x2);
								if(rand.nextInt(9) == 0 && world.getBlockId(xCoord, yCoord+1, zCoord) == 0) {
									world.setBlock(xCoord, yCoord+1, zCoord, TFCBlocks.Flora.blockID, 0, 2);
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
}
