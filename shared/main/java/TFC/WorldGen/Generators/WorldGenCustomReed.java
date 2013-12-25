package TFC.WorldGen.Generators;

import java.util.Random;

import net.minecraft.world.gen.feature.WorldGenerator;

import TFC.*;
import TFC.Blocks.Vanilla.BlockCustomReed;
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

public class WorldGenCustomReed extends WorldGenerator
{
    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
    {
        for (int var6 = 0; var6 < 20; ++var6)
        {
            int var7 = par3 + par2Random.nextInt(4) - par2Random.nextInt(4);
            int var8 = par4;
            int var9 = par5 + par2Random.nextInt(4) - par2Random.nextInt(4);

            if (par1World.isAirBlock(var7, par4, var9) && (par1World.getBlockMaterial(var7 - 1, par4 - 1, var9) == Material.water || par1World.getBlockMaterial(var7 + 1, par4 - 1, var9) == Material.water || par1World.getBlockMaterial(var7, par4 - 1, var9 - 1) == Material.water || par1World.getBlockMaterial(var7, par4 - 1, var9 + 1) == Material.water))
            {
                int var10 = 2 + par2Random.nextInt(par2Random.nextInt(3) + 1);

                for (int var11 = 0; var11 < var10; ++var11)
                {
                    if (((BlockCustomReed)Block.blocksList[Block.reed.blockID]).canBlockStay(par1World, var7, var8 + var11, var9))
                    {
                        par1World.setBlock(var7, var8 + var11, var9, Block.reed.blockID);
                    }
                }
            }
        }

        return true;
    }
}
