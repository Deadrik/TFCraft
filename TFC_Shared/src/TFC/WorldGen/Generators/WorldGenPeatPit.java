package TFC.WorldGen.Generators;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import TFC.TFCBlocks;
import TFC.Core.TFC_Climate;
import TFC.Core.TFC_Core;
import cpw.mods.fml.common.IWorldGenerator;

public class WorldGenPeatPit implements IWorldGenerator
{
	/** The block ID for clay. */
	private int clayBlockId;

	/** The number of blocks to generate. */
	private int numberOfBlocks;

	public WorldGenPeatPit(int par1)
	{
		this.numberOfBlocks = par1;
	}

	public boolean generate(World par1World, Random par2Random, int xCoord, int yCoord, int zCoord)
	{
		int var6 = par2Random.nextInt(this.numberOfBlocks - 8) + 8;
		byte var7 = 2;

		if(par2Random.nextInt(30) == 0 && yCoord <= 146)
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
								}
								else if(TFC_Core.isGrass(blockID))
								{
									par1World.setBlock(x, y, z, TFCBlocks.PeatGrass.blockID);
								}
							}
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
