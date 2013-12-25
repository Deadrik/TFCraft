package TFC.WorldGen.Generators.Trees;

import java.util.Random;

import TFC.*;
import TFC.Core.TFC_Core;
import TFC.WorldGen.TFCBiome;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
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
import net.minecraft.world.gen.feature.*;

public class WorldGenPineShort extends WorldGenerator
{
	final int	blockLeaf, metaLeaf, blockWood, metaWood;
    public WorldGenPineShort(boolean par1, int id)
    {
        super(par1);
        metaLeaf = id;
		blockLeaf = TFCBlocks.Leaves.blockID;
		metaWood = id;
		blockWood = TFCBlocks.Wood.blockID;
    }

    public boolean generate(World world, Random par2Random, int x, int y, int z)
    {
        int var6 = par2Random.nextInt(4) + 6;
        int var7 = 1 + par2Random.nextInt(2);
        int var8 = var6 - var7;
        int var9 = 2 + par2Random.nextInt(2);
        boolean var10 = true;

        if (y >= 1 && y + var6 + 1 <= 256)
        {
            int var11;
            int var13;
            int var15;
            int var21;

            for (var11 = y; var11 <= y + 1 + var6 && var10; ++var11)
            {
                boolean var12 = true;

                if (var11 - y < var7)
                {
                    var21 = 0;
                }
                else
                {
                    var21 = var9;
                }

                for (var13 = x - var21; var13 <= x + var21 && var10; ++var13)
                {
                    for (int var14 = z - var21; var14 <= z + var21 && var10; ++var14)
                    {
                        if (var11 >= 0 && var11 < 256)
                        {
                            var15 = world.getBlockId(var13, var11, var14);

                            Block block = Block.blocksList[var15];

                            if (var15 != 0 && block != null && !block.isLeaves(world, var13, var11, var14))
                            {
                                var10 = false;
                            }
                        }
                        else
                        {
                            var10 = false;
                        }
                    }
                }
            }

            if (!var10)
            {
                return false;
            }
            else
            {
                var11 = world.getBlockId(x, y - 1, z);

                int meta = TFCBiome.getSurfaceRockLayer(world, x, z);
        		int dirtID =  TFC_Core.getTypeForDirt(meta);
        		int dirtMeta =  TFC_Core.getSoilMetaFromStone(dirtID, meta);

                if (TFC_Core.isSoil(var11) && y < world.getActualHeight() - var6 - 1)
                {
                    this.setBlockAndMetadata(world, x, y - 1, z, dirtID, dirtMeta);
                    var21 = par2Random.nextInt(2);
                    var13 = 1;
                    byte var22 = 0;
                    int var17;
                    int var16;

                    for (var15 = 0; var15 <= var8; ++var15)
                    {
                        var16 = y + var6 - var15;

                        for (var17 = x - var21; var17 <= x + var21; ++var17)
                        {
                            int var18 = var17 - x;

                            for (int var19 = z - var21; var19 <= z + var21; ++var19)
                            {
                                int var20 = var19 - z;

                                Block block = Block.blocksList[world.getBlockId(var17, var16, var19)];

                                if ((Math.abs(var18) != var21 || Math.abs(var20) != var21 || var21 <= 0) && 
                                    (block == null || block.canBeReplacedByLeaves(world, var17, var16, var19)))
                                {
                                    this.setBlockAndMetadata(world, var17, var16, var19, blockLeaf, metaLeaf);
                                }
                            }
                        }

                        if (var21 >= var13)
                        {
                            var21 = var22;
                            var22 = 1;
                            ++var13;

                            if (var13 > var9)
                            {
                                var13 = var9;
                            }
                        }
                        else
                        {
                            ++var21;
                        }
                    }

                    var15 = par2Random.nextInt(3);

                    for (var16 = 0; var16 < var6 - var15; ++var16)
                    {
                        var17 = world.getBlockId(x, y + var16, z);

                        Block block = Block.blocksList[var17];

                        if (var17 == 0 || block == null || block.isLeaves(world, x, y + var16, z) || 
                        		block.canBeReplacedByLeaves(world, x, y + var16, z))
                        {
                            this.setBlockAndMetadata(world, x, y + var16, z, blockWood, metaWood);
                        }
                    }

                    return true;
                }
                else
                {
                    return false;
                }
            }
        }
        else
        {
            return false;
        }
    }
}
