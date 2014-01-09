package TFC.WorldGen.Generators;

import java.util.Random;

import TFC.Core.TFC_Core;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.crash.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.packet.*;
import net.minecraft.pathfinding.*;
import net.minecraft.potion.*;
import net.minecraft.server.*;
import net.minecraft.stats.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.village.*;
import net.minecraft.world.*;
import net.minecraft.world.biome.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.gen.feature.WorldGenerator;

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
                    if (TFC_Core.isSand(world.getBlockId(xCoord, yCoord-1, zCoord)) || Block.cactus.canBlockStay(world, xCoord, yCoord + var11, zCoord))
                    {
                        world.setBlock(xCoord, yCoord + var11, zCoord, Block.cactus.blockID, 0, 0x2);
                    }
                }
            }
        }

        return true;
    }
}
