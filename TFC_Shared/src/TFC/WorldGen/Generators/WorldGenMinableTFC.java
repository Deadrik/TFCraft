package TFC.WorldGen.Generators;

import java.util.Random;

import TFC.*;
import TFC.Chunkdata.ChunkData;
import TFC.Chunkdata.ChunkDataManager;
import TFC.Core.TFC_Settings;
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

public class WorldGenMinableTFC extends WorldGenerator
{
    private int minableBlockId;
    private int minableBlockMeta;
    private int numberOfBlocks;
    private int LayerID;
    private int LayerMeta;
    private int BiomeId;

    private int MPChunk_X;
    private int MPChunk_Z;
    public static int MPBlockID;
    public static int MPBlockMeta;
    public static int MPLayerID;
    public static int MPLayerMeta;
    public static int MPPrevX;
    public static int MPPrevZ;
    public static int MPPrevID;
    public static int MPPrevMeta;
    public static int MPPrevLayerID;
    public static int MPPrevLayerMeta;
    private World worldObj;

    private int rarity = 2;
    private int veinSi = 2;
    private int veinAm = 2;
    private int height = 2;
    private int diameter = 2;
    private int vDens = 2;
    private int hDens = 2;
    
    private ChunkData chunkdata;
    private String name;

    public WorldGenMinableTFC(int i, int j)
    {
        int emptyHolder = 0;
        minableBlockId = i;
        minableBlockMeta = 0;
        emptyHolder = j;
        BiomeId = -1;
    }

    public WorldGenMinableTFC(int i, int j, int layerId, int layerMeta, int rarity, int veinSize, 
            int veinAmount, int height, int diameter, int vDensity, int hDensity)
    {
        int emptyHolder = 0;
        minableBlockId = i;
        minableBlockMeta = j;
        emptyHolder = j;

        LayerID= layerId;
        LayerMeta = layerMeta;

        this.rarity = rarity;
        this.veinSi = veinSize;
        this.veinAm = veinAmount;
        this.height = height;
        this.diameter = diameter;
        this.vDens = vDensity;
        this.hDens = hDensity;
    }

    public boolean BetterOreDistribution(int xChunk, int zChunk, int MPMinableBlockId, int MPMinableBlockMeta, int min, int max, Random rand)//======================================================================================
    {
        if (rand.nextInt(rarity) == 0)
        {
            for(int loopCount = 0; loopCount < veinAm; loopCount++)
            {
                int temp1 = mPCalculateDensity(rand, diameter, hDens);
                int temp2 = mPCalculateDensityVert(rand, height, vDens, min, max);
                int temp3 = mPCalculateDensity(rand, diameter, hDens);
                int l5 = xChunk*16 + temp1;
                int i9 = temp2;
                int k13 = zChunk*16 + temp3;
                BODgenerate(worldObj, rand, l5, i9, k13, veinSi);

            }
        }
        return true;
    }
    
    public boolean BetterOreDistributionVein(int xChunk, int zChunk, int MPMinableBlockId, int MPMinableBlockMeta, int min, int max, Random rand)//======================================================================================
    {
        if (rand.nextInt(rarity) == 0)
        {
            for(int loopCount = 0; loopCount < veinAm; loopCount++)
            {
                int temp1 = mPCalculateDensity(rand, diameter, hDens);
                int temp2 = mPCalculateDensityVert(rand, height, vDens, min, max);
                int temp3 = mPCalculateDensity(rand, diameter, hDens);
                int l5 = xChunk*16 + temp1;
                int i9 = temp2;
                int k13 = zChunk*16 + temp3;
                BODgenerateVein(worldObj, rand, l5, i9, k13, veinSi);

            }
        }
        return true;
    }

    public boolean BODgenerateVein(World world, Random rand, int parX, int parY, int parZ, int xyz)
    {
    	boolean doOnce = true;
    	
        //==========================================mp mod
        int posX = parX;
        int posY = parY;
        int posZ = parZ;
        int tempPosX =0;
        int tempPosY =0;
        int tempPosZ =0;
        int posX2 = 0;
        int posY2 = 0;
        int posZ2 = 0;
        int directionX =0;
        int directionY =0;
        int directionZ =0;
        int directionX2 = 0;
        int directionY2 = 0;
        int directionZ2 = 0;
        int directionX3 =0;
        int directionY3 =0;
        int directionZ3 =0;
        int directionChange =0;
        int directionChange2 =0;
        int blocksToUse = xyz;//input number of blocks per vein
        int blocksToUse2 =0; 
        for(int  blocksMade=0; blocksMade <= blocksToUse;) // make veins
        {
            blocksToUse2 = 1 + (blocksToUse/30);
            directionChange = rand.nextInt(6);
            directionX = rand.nextInt(2);
            directionY = rand.nextInt(2);
            directionZ = rand.nextInt(2);

            for(int blocksMade1 = 0; blocksMade1 <= blocksToUse2; ) // make branch
            {   
                if(directionX == 0  && directionChange != 1){posX = posX + rand.nextInt(2);}
                if(directionX == 1  && directionChange != 1){posX = posX - rand.nextInt(2);}
                if(directionY == 0  && directionChange != 2){posY = posY + rand.nextInt(2);}
                if(directionY == 1  && directionChange != 2){posY = posY - rand.nextInt(2);}
                if(directionZ == 0  && directionChange != 3){posZ = posZ + rand.nextInt(2);}
                if(directionZ == 1  && directionChange != 3){posZ = posZ - rand.nextInt(2);}
                if(rand.nextInt(4) == 0){
                    posX2 = posX2 + rand.nextInt(2);
                    posY2 = posY2 + rand.nextInt(2);
                    posZ2 = posZ2 + rand.nextInt(2);
                    posX2 = posX2 - rand.nextInt(2);
                    posY2 = posY2 - rand.nextInt(2);
                    posZ2 = posZ2 - rand.nextInt(2);
                }
                if(rand.nextInt(3) == 0) // make sub-branch
                {   
                    posX2 = posX;
                    posY2 = posY;
                    posZ2 = posZ;

                    directionX2 = rand.nextInt(2);
                    directionY2 = rand.nextInt(2);
                    directionZ2 = rand.nextInt(2);
                    directionChange2 = rand.nextInt(6);
                    if(directionX2 == 0 && directionChange2 != 0){posX2 = posX2 + rand.nextInt(2);}
                    if(directionY2 == 0 && directionChange2 != 1){posY2 = posY2 + rand.nextInt(2);}
                    if(directionZ2 == 0 && directionChange2 != 2){posZ2 = posZ2 + rand.nextInt(2);}
                    if(directionX2 == 1 && directionChange2 != 0){posX2 = posX2 - rand.nextInt(2);}
                    if(directionY2 == 1 && directionChange2 != 1){posY2 = posY2 - rand.nextInt(2);}
                    if(directionZ2 == 1 && directionChange2 != 2){posZ2 = posZ2 - rand.nextInt(2);}



                    for(int blocksMade2 = 0; blocksMade2 <= (1 +(blocksToUse2/5)); )
                    {   

                        if(directionX2 == 0 && directionChange2 != 0){posX2 = posX2 + rand.nextInt(2);}
                        if(directionY2 == 0 && directionChange2 != 1){posY2 = posY2 + rand.nextInt(2);}
                        if(directionZ2 == 0 && directionChange2 != 2){posZ2 = posZ2 + rand.nextInt(2);}
                        if(directionX2 == 1 && directionChange2 != 0){posX2 = posX2 - rand.nextInt(2);}
                        if(directionY2 == 1 && directionChange2 != 1){posY2 = posY2 - rand.nextInt(2);}
                        if(directionZ2 == 1 && directionChange2 != 2){posZ2 = posZ2 - rand.nextInt(2);}


                        int m = world.getBlockMetadata(posX, posY, posZ);
                        boolean isCorrectRockType = world.getBlockId(posX, posY, posZ) == LayerID;
                        boolean isCorrectMeta = (m == LayerMeta || LayerMeta == -1);

                        if(isCorrectRockType && isCorrectMeta)
                        {
                            world.setBlockAndMetadataWithNotify(posX, posY, posZ, MPBlockID, MPBlockMeta, 0);
                            if(doOnce)
                            {
                            	if(posY >= TFC_Settings.RockLayer2Height && !chunkdata.oreList1.contains(name))
                            	{
                            		chunkdata.oreList1.add(name);
                            	}
                            	else if(posY >= TFC_Settings.RockLayer3Height && !chunkdata.oreList2.contains(name))
                            	{
                            		chunkdata.oreList2.add(name);
                            	}
                            	else if (!chunkdata.oreList3.contains(name))
                            	{
                            		chunkdata.oreList3.add(name);
                            		
                            	}
                            	doOnce = false;
                            }
                        }

                        blocksMade++;
                        blocksMade1++;
                        blocksMade2++;
                    }
                }

                int m = world.getBlockMetadata(posX, posY, posZ);
                boolean isCorrectRockType = world.getBlockId(posX, posY, posZ) == LayerID;
                boolean isCorrectMeta = (m == LayerMeta || LayerMeta == -1);

                if(isCorrectRockType && isCorrectMeta)
                {
                    world.setBlockAndMetadataWithNotify(posX, posY, posZ, MPBlockID, MPBlockMeta, 0);
                    if(doOnce)
                    {
                    	if(posY >= TFC_Settings.RockLayer2Height && !chunkdata.oreList1.contains(name))
                    	{
                    		chunkdata.oreList1.add(name);
                    	}
                    	else if(posY >= TFC_Settings.RockLayer3Height && !chunkdata.oreList2.contains(name))
                    	{
                    		chunkdata.oreList2.add(name);
                    	}
                    	else if (!chunkdata.oreList3.contains(name))
                    	{
                    		chunkdata.oreList3.add(name);
                    	}
                    	doOnce = false;
                    }
                }                      

                blocksMade++;
                blocksMade1++;

            }

            parX = parX + (rand.nextInt(3) - 1);
            parY = parY + (rand.nextInt(3) - 1);
            parZ = parZ + (rand.nextInt(3) - 1);
            posX = parX;
            posY = parY;
            posZ = parZ;

        }


        return true;
    }

    public boolean BODgenerate(World world, Random rand, int x, int y, int z, int xyz)
    {
    	boolean doOnce = true;
    	
        numberOfBlocks = xyz; 

        float f = rand.nextFloat() * (float)Math.PI;
        double d = (float)(x + 8) + MathHelper.sin(f) * (float)numberOfBlocks / 8F;
        double d1 = (float)(x + 8) - MathHelper.sin(f) * (float)numberOfBlocks / 8F;
        double d2 = (float)(z + 8) + MathHelper.cos(f) * (float)numberOfBlocks / 8F;
        double d3 = (float)(z + 8) - MathHelper.cos(f) * (float)numberOfBlocks / 8F;
        double d4 = y + rand.nextInt(3) - 2;
        double d5 = y + rand.nextInt(3) - 2;
        for(int l = 0; l <= numberOfBlocks; l++)
        {
            double d6 = d + (d1 - d) * (double)l / (double)numberOfBlocks;
            double d7 = d4 + (d5 - d4) * (double)l / (double)numberOfBlocks;
            double d8 = d2 + (d3 - d2) * (double)l / (double)numberOfBlocks;
            double d9 = rand.nextDouble() * (double)numberOfBlocks / 16D;
            double d10 = (double)(MathHelper.sin((float)l * (float)Math.PI / (float)numberOfBlocks) + 1.0F) * d9 + 1.0D;
            double d11 = (double)(MathHelper.sin((float)l * (float)Math.PI / (float)numberOfBlocks) + 1.0F) * d9 + 1.0D;
            int i1 = MathHelper.floor_double(d6 - d10 / 2D);
            int j1 = MathHelper.floor_double(d7 - d11 / 2D);
            int k1 = MathHelper.floor_double(d8 - d10 / 2D);
            int l1 = MathHelper.floor_double(d6 + d10 / 2D);
            int i2 = MathHelper.floor_double(d7 + d11 / 2D);
            int j2 = MathHelper.floor_double(d8 + d10 / 2D);
            for(int xCoord = i1; xCoord <= l1; xCoord++)
            {
                double d12 = ((double)xCoord + 0.5D - d6) / (d10 / 2D);
                if(d12 * d12 >= 1.0D)
                {
                    continue;
                }
                for(int yCoord = j1; yCoord <= i2; yCoord++)
                {
                    double d13 = ((double)yCoord + 0.5D - d7) / (d11 / 2D);
                    if(d12 * d12 + d13 * d13 >= 1.0D)
                    {
                        continue;
                    }
                    for(int zCoord = k1; zCoord <= j2; zCoord++)
                    {
                        double d14 = ((double)zCoord + 0.5D - d8) / (d10 / 2D);
                        int m = world.getBlockMetadata(xCoord, yCoord, zCoord);
                        boolean isCorrectRockType = world.getBlockId(xCoord, yCoord, zCoord) == LayerID;
                        boolean isCorrectMeta = (m == LayerMeta || LayerMeta == -1);

                        if(d12 * d12 + d13 * d13 + d14 * d14 < 1.0D && isCorrectRockType && 
                                (m == LayerMeta || LayerMeta == -1))
                        {
                            world.setBlockAndMetadataWithNotify(xCoord, yCoord, zCoord, MPBlockID, MPBlockMeta, 0);
                            if(doOnce)
                            {
                            	if(yCoord >= TFC_Settings.RockLayer2Height && !chunkdata.oreList1.contains(name))
                            	{
                            		chunkdata.oreList1.add(name);
                            	}
                            	else if(yCoord >= TFC_Settings.RockLayer3Height && !chunkdata.oreList2.contains(name))
                            	{
                            		chunkdata.oreList2.add(name);
                            	}
                            	else if (!chunkdata.oreList3.contains(name))
                            	{
                            		chunkdata.oreList3.add(name);
                            	}
                            	doOnce = false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    public boolean generate(World world, Random random, int x, int z, int min, int max, String n)//absorb default system
    {
        MPChunk_X = x >> 4;// set output chunk x
        MPChunk_Z = z >> 4;// set output chunk z
        
        chunkdata = ChunkDataManager.getData(MPChunk_X, MPChunk_Z);
        
        MPBlockID = minableBlockId;// set output block ID
        MPBlockMeta = minableBlockMeta;
        worldObj = world;
        //rand = random;
        if(MPChunk_X != MPPrevX || MPChunk_Z != MPPrevZ || MPBlockID != MPPrevID || 
                MPBlockID == MPPrevID && MPBlockMeta != MPPrevMeta || MPLayerID != MPPrevLayerID || MPLayerMeta != MPPrevLayerMeta)// if it is a new x or y chunk or is a new ore, then generate
        { 

            MPPrevX = MPChunk_X;
            MPPrevZ = MPChunk_Z;
            MPPrevID = MPBlockID;
            MPPrevMeta = MPBlockMeta;
            this.name = n;
            BetterOreDistribution(MPChunk_X, MPChunk_Z, MPBlockID, MPBlockMeta, min, max,random);
        }
        return true;
    }
    
    public boolean generateVein(World world, Random random, int x, int z, int min, int max, String n)//absorb default system
    {
        MPChunk_X = x >> 4;// set output chunk x
        MPChunk_Z = z >> 4;// set output chunk z
            
        chunkdata = ChunkDataManager.getData(MPChunk_X, MPChunk_Z);
            
        MPBlockID = minableBlockId;// set output block ID
        MPBlockMeta = minableBlockMeta;
        worldObj = world;
        //rand = random;
        if(MPChunk_X != MPPrevX || MPChunk_Z != MPPrevZ || MPBlockID != MPPrevID || 
                MPBlockID == MPPrevID && MPBlockMeta != MPPrevMeta || MPLayerID != MPPrevLayerID || MPLayerMeta != MPPrevLayerMeta)// if it is a new x or y chunk or is a new ore, then generate
        { 

            MPPrevX = MPChunk_X;
            MPPrevZ = MPChunk_Z;
            MPPrevID = MPBlockID;
            MPPrevMeta = MPBlockMeta;
            this.name = n;
            BetterOreDistributionVein(MPChunk_X, MPChunk_Z, MPBlockID, MPBlockMeta, min, max,random);
        }
        return true;
    }
    
    
    public boolean generate(World world, Random random, int i, int j, int k)
    {
        return true;
    }
    private boolean isRock(int i)
    {
        if(i == TFCBlocks.StoneIgIn.blockID || i == TFCBlocks.StoneIgEx.blockID || 
                i == TFCBlocks.StoneMM.blockID || i == TFCBlocks.StoneSed.blockID ||
                i == TFCBlocks.Ore.blockID || i == TFCBlocks.Ore2.blockID || 
                i == TFCBlocks.Ore3.blockID)
        {
            return true;
        }

        return false;
    }

    //======================================================================================
    public int mPCalculateDensity(Random rand, int oreDist, float oreDens) // returns the density value
    {
        int lpCnt = 0;
        int dValPassInr = 0;
        int dValPass = 0;
        oreDens = oreDens * .01F;
        oreDens = oreDens * (oreDist / 2) + 1F;// establishes number of times to loop
        lpCnt = (int)oreDens; //stores number of times to loop
        dValPassInr = (int)(oreDist/oreDens+.5F); // distance devided by number of times it will loop, establishes the number for randomization
        dValPass = 0;
        while (lpCnt > 0) // loops to acumulate random values
        {
            dValPass = dValPass + rand.nextInt(dValPassInr); // acumulate randoms
            lpCnt--;// decriment loop
        }
        if (dValPass < 5){dValPass = 5;}
        if (dValPass > 128) {dValPass = 128;}
        return dValPass; // return proccesed random value
    }
    //======================================================================================
    public int mPCalculateDensityVert(Random rand, int oreDist, float oreDens, int min, int max) // returns the density value
    {
        int lpCnt = 0;
        int dValPassInr = 0;
        int dValPass = 0;
        oreDens = oreDens * .01F;
        oreDens = oreDens * (oreDist / 2) + 1F;// establishes number of times to loop
        lpCnt = (int)oreDens; //stores number of times to loop
        dValPassInr = (int)(oreDist/oreDens+.5F); // distance devided by number of times it will loop, establishes the number for randomization
        dValPass = min;
        while (lpCnt > 0) // loops to acumulate random values
        {
            dValPass = dValPass + rand.nextInt(dValPassInr); // acumulate randoms
            lpCnt--;// decriment loop
        }
        if (dValPass < min){dValPass = min;}
        if (dValPass > max) {dValPass = max;}
        return dValPass; // return proccesed random value
    }
}
