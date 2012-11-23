package TFC.WorldGen.Generators.Trees;

import java.util.Random;

import TFC.*;
import TFC.Core.TFC_Core;
import TFC.WorldGen.TFCBiome;

import net.minecraft.src.Block;
import net.minecraft.src.World;
import net.minecraft.src.WorldGenerator;

public class WorldGenPineTall extends WorldGenerator
{
	final int	blockLeaf, metaLeaf, blockWood, metaWood;
	public WorldGenPineTall(int id)
	{
		metaLeaf = id;
		blockLeaf = TFCBlocks.Leaves.blockID;
		metaWood = id;
		blockWood = TFCBlocks.Wood.blockID;
	}
	
    public boolean generate(World world, Random par2Random, int i, int j, int k)
    {
        int var6 = par2Random.nextInt(5) + 7;
        int var7 = var6 - par2Random.nextInt(2) - 3;
        int var8 = var6 - var7;
        int var9 = 1 + par2Random.nextInt(var8 + 1);
        boolean var10 = true;

        if (j >= 1 && j + var6 + 1 <= 128)
        {
            int var11;
            int var13;
            int var14;
            int var15;
            int var18;

            for (var11 = j; var11 <= j + 1 + var6 && var10; ++var11)
            {
                boolean var12 = true;

                if (var11 - j < var7)
                {
                    var18 = 0;
                }
                else
                {
                    var18 = var9;
                }

                for (var13 = i - var18; var13 <= i + var18 && var10; ++var13)
                {
                    for (var14 = k - var18; var14 <= k + var18 && var10; ++var14)
                    {
                        if (var11 >= 0 && var11 < 128)
                        {
                            var15 = world.getBlockId(var13, var11, var14);

                            Block block = Block.blocksList[var15];

                            if (var15 != 0 && (block == null || !block.isLeaves(world, var13, var11, var14)))
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
                var11 = world.getBlockId(i, j - 1, k);
                int meta = TFCBiome.getSurfaceRockLayer(world, i, k);
        		int dirtID =  TFC_Core.getTypeForDirt(meta);
        		int dirtMeta =  TFC_Core.getSoilMetaFromStone(dirtID, meta);

                if (TFC_Core.isGrass(var11) && j < world.getActualHeight() - var6 - 1)
                {
                    this.setBlockAndMetadata(world, i, j - 1, k, dirtID, dirtMeta);
                    var18 = 0;

                    for (var13 = j + var6; var13 >= j + var7; --var13)
                    {
                        for (var14 = i - var18; var14 <= i + var18; ++var14)
                        {
                            var15 = var14 - i;

                            for (int var16 = k - var18; var16 <= k + var18; ++var16)
                            {
                                int var17 = var16 - k;

                                Block block = Block.blocksList[world.getBlockId(var14, var13, var16)];

                                if ((Math.abs(var15) != var18 || Math.abs(var17) != var18 || var18 <= 0) && 
                                    (block == null || block.canBeReplacedByLeaves(world, var14, var13, var16)))
                                {
                                    this.setBlockAndMetadata(world, var14, var13, var16, blockLeaf, metaLeaf);
                                }
                            }
                        }

                        if (var18 >= 1 && var13 == j + var7 + 1)
                        {
                            --var18;
                        }
                        else if (var18 < var9)
                        {
                            ++var18;
                        }
                    }

                    for (var13 = 0; var13 < var6 - 1; ++var13)
                    {
                        var14 = world.getBlockId(i, j + var13, k);

                        Block block = Block.blocksList[var14];

                        if (var14 == 0 || block == null || block.isLeaves(world, i, j + var13, k) || 
                        		block.canBeReplacedByLeaves(world, i, j + var14, k))
                        {
                            this.setBlockAndMetadata(world, i, j + var13, k, blockWood, metaWood);
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
