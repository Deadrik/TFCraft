package TFC.WorldGen.Generators;

import java.util.Random;

import TFC.Core.TFC_Core;

import net.minecraft.src.*;

public class WorldGenCustomCactus extends WorldGenerator
{
    public boolean generate(World world, Random random, int i, int j, int k)
    {
        for (int var6 = 0; var6 < 10; ++var6)
        {
            int xCoord = i + random.nextInt(8) - random.nextInt(8);
            int yCoord = j + random.nextInt(4) - random.nextInt(4);
            int zCoord = k + random.nextInt(8) - random.nextInt(8);

            if (world.isAirBlock(xCoord, yCoord, zCoord))
            {
                int var10 = 1 + random.nextInt(random.nextInt(3) + 1);

                for (int var11 = 0; var11 < var10; ++var11)
                {
                    if (TFC_Core.isSand(world.getBlockId(xCoord, yCoord-1, zCoord)) || Block.cactus.canBlockStay(world, xCoord, yCoord + var11, zCoord))
                    {
                        world.setBlock(xCoord, yCoord + var11, zCoord, Block.cactus.blockID);
                    }
                }
            }
        }

        return true;
    }
}
