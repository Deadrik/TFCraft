package TFC.WorldGen.Generators;

import java.util.Random;

import TFC.*;
import TFC.Core.TFC_Core;
import TFC.WorldGen.TFCWorldChunkManager;
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

public class WorldGenCustomSand extends WorldGenerator
{
    /** Stores ID for WorldGenSand */
    private int sandID;

    /** The maximum radius used when generating a patch of blocks. */
    private int radius;

    public WorldGenCustomSand(int par1, int par2)
    {
        this.sandID = par2;
        this.radius = par1;
    }

    public boolean generate(World world, Random par2Random, int par3, int par4, int par5)
    {
        if (world.getBlockMaterial(par3, par4, par5) != Material.water)
        {
            return false;
        }
        else
        {
        	
        	int meta = sandID = ((TFCWorldChunkManager)world.provider.worldChunkMgr).getRockLayerAt(par3, par5, 0).data2;
        	sandID = TFC_Core.getTypeForSand(sandID);
            int var6 = par2Random.nextInt(this.radius - 2) + 2;
            byte var7 = 2;

            for (int var8 = par3 - var6; var8 <= par3 + var6; ++var8)
            {
                for (int var9 = par5 - var6; var9 <= par5 + var6; ++var9)
                {
                    int var10 = var8 - par3;
                    int var11 = var9 - par5;

                    if (var10 * var10 + var11 * var11 <= var6 * var6)
                    {
                        for (int var12 = par4 - var7; var12 <= par4 + var7; ++var12)
                        {
                            int var13 = world.getBlockId(var8, var12, var9);

                            boolean notCorrectSoil = !TFC_Core.isSoil(var13) && !TFC_Core.isSand(var13) ;
                            if (!notCorrectSoil)
                            {
                                world.setBlockAndMetadata(var8, var12, var9, sandID, meta);
                            }
                        }
                    }
                }
            }

            return true;
        }
    }
}
