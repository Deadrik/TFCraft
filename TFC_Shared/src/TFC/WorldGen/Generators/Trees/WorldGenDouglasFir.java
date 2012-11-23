package TFC.WorldGen.Generators.Trees;

import java.util.Random;

import TFC.*;
import TFC.Core.TFC_Core;

import net.minecraft.src.Block;
import net.minecraft.src.World;
import net.minecraft.src.WorldGenerator;

public class WorldGenDouglasFir extends WorldGenerator
{
    private boolean Tall = false;
    
    private int metaID;

    public WorldGenDouglasFir(boolean par1, int m, boolean t)
    {
        super(par1);
        metaID = m;
        Tall = t;
    }

    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
    {
        int i = par2Random.nextInt(10) + 10;
        if(par2Random.nextInt(20)==0){
            Tall=true;
        }
        if(Tall){
            i += par2Random.nextInt(10);
        }
        boolean flag = true;

        if (par4 < 1 || par4 + i + 1 > 256)
        {
            return false;
        }

        for (int j = par4; j <= par4 + 1 + i; j++)
        {
            byte byte0 = 1;

            if (j == par4)
            {
                byte0 = 0;
            }

            if (j >= (par4 + 1 + i) - 2)
            {
                byte0 = 2;
            }

            for (int l = par3 - byte0; l <= par3 + byte0 && flag; l++)
            {
                for (int j1 = par5 - byte0; j1 <= par5 + byte0 && flag; j1++)
                {
                    if (j >= 0 && j < 256)
                    {
                        int j2 = par1World.getBlockId(l, j, j1);

                        if (j2 != 0 && j2 != Block.leaves.blockID && j2 != Block.grass.blockID && j2 != Block.dirt.blockID && j2 != Block.wood.blockID && j2 != Block.sapling.blockID)
                        {
                            flag = false;
                        }
                    }
                    else
                    {
                        flag = false;
                    }
                }
            }
        }

        if (!flag)
        {
            return false;
        }

        int k = par1World.getBlockId(par3, par4 - 1, par5);

        if (!TFC_Core.isSoil(k) || par4 >= 256 - i - 1)
        {
            return false;
        }

        byte byte1 = 3;
        int i1 = 0;

        for (int k1 = par4 + (i/3)-1; k1 <= par4 + i-1; k1++)
        {
            int k2 = k1 - (par4 + i);
            int j3 = 1 - k2 / 2;
            int z=i;
            if (i>20){
                z=20;
            }
            int x = z/10 +1;
            if (k1-par4>i/2||k1-par4-(i/3)+2<3){
                x--;
            }
            if(par4+i-k1<4){
                x=1;
            }
            for (int l3 = par3 -x; l3 <= par3 +x; l3++)
            {
                int j4 = l3 - par3;

                for (int l4 = par5-x; l4 <= par5 +x; l4++)
                {
                    int i5 = l4 - par5;
                    if ((Math.abs(j4) != 0 || Math.abs(i5) != 0 && k2 != 0)&&(Math.abs(j4)+Math.abs(i5)!=x*2||(k1-par4>i/2&&k1-par4<(4*i/5))||k1-par4-(i/3)+2==2)&&(par2Random.nextInt(12)!=0)&& !Block.opaqueCubeLookup[par1World.getBlockId(l3, k1, l4)])
                    {
                        setBlockAndMetadata(par1World, l3, k1, l4, Block.leaves.blockID, metaID);
                    }
                }
            }
        }
        setBlockAndMetadata(par1World, par3, par4+i, par5, Block.leaves.blockID, metaID);
        for (int l1 = 0; l1 < i; l1++)
        {
            int l2 = par1World.getBlockId(par3, par4 + l1, par5);

            if (l2 != 0 && l2 != Block.leaves.blockID)
            {
                continue;
            }

            setBlockAndMetadata(par1World, par3, par4 + l1, par5, Block.wood.blockID, metaID);

        }
        return true;
    }

}
