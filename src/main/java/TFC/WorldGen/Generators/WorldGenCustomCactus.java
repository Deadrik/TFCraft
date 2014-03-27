package TFC.WorldGen.Generators;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import TFC.Core.TFC_Core;

public class WorldGenCustomCactus extends WorldGenerator
{
    public boolean generate(World world, Random random, int i, int j, int k)
    {
        for (int var6 = 0; var6 < random.nextInt(2)+1; ++var6)
        {
            int xCoord = i + random.nextInt(8) - random.nextInt(8);
            int yCoord = j + random.nextInt(4) - random.nextInt(4);
            int zCoord = k + random.nextInt(8) - random.nextInt(8);

            if (world.isAirBlock(xCoord, yCoord, zCoord))
            {
                int var10 = 1 + random.nextInt(random.nextInt(3) + 1);

                for (int var11 = 0; var11 < var10; ++var11)
                {
                    if (TFC_Core.isSand(world.getBlock(xCoord, yCoord-1, zCoord)) || Blocks.cactus.canBlockStay(world, xCoord, yCoord + var11, zCoord))
                    {
                        world.setBlock(xCoord, yCoord + var11, zCoord, Blocks.cactus, 0, 0x2);
                    }
                }
            }
        }

        return true;
    }
}
