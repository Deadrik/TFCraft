package TFC.WorldGen.Generators;

import java.util.Random;

import net.minecraft.src.*;

public class WorldGenDouglasFir extends WorldGenerator
{
    private final int field_48202_a;
    private final boolean field_48200_b;
    private final int field_48201_c;
    private final int field_48199_d;
    private boolean Tall = false;
    
    private int metaID;

    public WorldGenDouglasFir(boolean par1, int m)
    {
        this(par1, 4, 0, 0, false);
        metaID = m;
    }

    public WorldGenDouglasFir(boolean par1,boolean tall, int par2, int par3, int par4, boolean par5)
    {
        super(par1);
        Tall = tall;
        field_48202_a = par2;
        field_48201_c = par3;
        field_48199_d = par4;
        field_48200_b = par5;
    }
    public WorldGenDouglasFir(boolean par1, int par2, int par3, int par4, boolean par5)
    {
        super(par1);
        field_48202_a = par2;
        field_48201_c = par3;
        field_48199_d = par4;
        field_48200_b = par5;
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

                        if (j2 != 0 && j2 != Block.leaves.blockID && j2 != Block.grass.blockID && j2 != Block.dirt.blockID && j2 != Block.wood.blockID)
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

        if (k != Block.grass.blockID && k != Block.dirt.blockID || par4 >= 256 - i - 1)
        {
            return false;
        }

        setBlock(par1World, par3, par4 - 1, par5, Block.dirt.blockID);
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

            if (!field_48200_b || l1 <= 0)
            {
                continue;
            }

            if (par2Random.nextInt(3) > 0 && par1World.isAirBlock(par3 - 1, par4 + l1, par5))
            {
                setBlockAndMetadata(par1World, par3 - 1, par4 + l1, par5, Block.vine.blockID, 8);
            }

            if (par2Random.nextInt(3) > 0 && par1World.isAirBlock(par3 + 1, par4 + l1, par5))
            {
                setBlockAndMetadata(par1World, par3 + 1, par4 + l1, par5, Block.vine.blockID, 2);
            }

            if (par2Random.nextInt(3) > 0 && par1World.isAirBlock(par3, par4 + l1, par5 - 1))
            {
                setBlockAndMetadata(par1World, par3, par4 + l1, par5 - 1, Block.vine.blockID, 1);
            }

            if (par2Random.nextInt(3) > 0 && par1World.isAirBlock(par3, par4 + l1, par5 + 1))
            {
                setBlockAndMetadata(par1World, par3, par4 + l1, par5 + 1, Block.vine.blockID, 4);
            }
        }

        if (field_48200_b)
        {
            for (int i2 = (par4 - 3) + i; i2 <= par4 + i; i2++)
            {
                int i3 = i2 - (par4 + i);
                int k3 = 2 - i3 / 2;

                for (int i4 = par3 - k3; i4 <= par3 + k3; i4++)
                {
                    for (int k4 = par5 - k3; k4 <= par5 + k3; k4++)
                    {
                        if (par1World.getBlockId(i4, i2, k4) != Block.leaves.blockID)
                        {
                            continue;
                        }

                        if (par2Random.nextInt(4) == 0 && par1World.getBlockId(i4 - 1, i2, k4) == 0)
                        {
                            func_48198_a(par1World, i4 - 1, i2, k4, 8);
                        }

                        if (par2Random.nextInt(4) == 0 && par1World.getBlockId(i4 + 1, i2, k4) == 0)
                        {
                            func_48198_a(par1World, i4 + 1, i2, k4, 2);
                        }

                        if (par2Random.nextInt(4) == 0 && par1World.getBlockId(i4, i2, k4 - 1) == 0)
                        {
                            func_48198_a(par1World, i4, i2, k4 - 1, 1);
                        }

                        if (par2Random.nextInt(4) == 0 && par1World.getBlockId(i4, i2, k4 + 1) == 0)
                        {
                            func_48198_a(par1World, i4, i2, k4 + 1, 4);
                        }
                    }
                }
            }
        }

        return true;
    }

    private void func_48198_a(World par1World, int par2, int par3, int par4, int par5)
    {
        setBlockAndMetadata(par1World, par2, par3, par4, Block.vine.blockID, par5);

        for (int i = 4; par1World.getBlockId(par2, --par3, par4) == 0 && i > 0; i--)
        {
            setBlockAndMetadata(par1World, par2, par3, par4, Block.vine.blockID, par5);
        }
    }
}
