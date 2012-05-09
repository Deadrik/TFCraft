package net.minecraft.src.TFC_Core.Custom;

import java.util.Random;

import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.Block;
import net.minecraft.src.ChunkProviderGenerate;
import net.minecraft.src.MathHelper;
import net.minecraft.src.NoiseGeneratorOctaves;
import net.minecraft.src.World;

public class TFC_ChunkProviderGenerate
{
    /**This version performs all the intial block placement in the initial loop, eliminating
     * a major source of chunkloader lag.*/
    public static void replaceBlocksForBiomeHigh(int par1, int par2, byte[] blockArray, byte[] metaArray, BiomeGenBase[] par4ArrayOfBiomeGenBase, 
            double[] stoneNoise, NoiseGeneratorOctaves noiseGen4, Random rand)
    {
        int var5 = 24;
        double var6 = 0.03125D;
        stoneNoise = noiseGen4.generateNoiseOctaves(stoneNoise, par1 * 16, par2 * 16, 0, 16, 16, 1, var6 * 2.0D, var6 * 2.0D, var6 * 2.0D);

        for (int xCoord = 0; xCoord < 16; ++xCoord)
        {
            for (int zCoord = 0; zCoord < 16; ++zCoord)
            {
                BiomeGenBase biomegenbase = par4ArrayOfBiomeGenBase[zCoord + xCoord * 16];
                float var11 = biomegenbase.getFloatTemperature();
                int var12 = (int)(stoneNoise[xCoord + zCoord * 16] / 3.0D + 3.0D + rand.nextDouble() * 0.25D);
                int var13 = -1;
                int var14 = biomegenbase.GrassID;
                int var15 = biomegenbase.DirtID;

                for (int height = 127; height >= 0; --height)
                {
                    int var17 = ((zCoord * 16 + xCoord) * 128 + height);
                    metaArray[var17] = 0;
                    
                    {
                        int var18 = blockArray[var17];
                        if (var18 == 0)
                        {
                            var13 = -1;
                        }
                        else if (var18 == Block.stone.blockID)
                        {
                            if(height <= biomegenbase.Layer3)
                            {
                                blockArray[var17] = (byte) biomegenbase.Layer3Type; 
                                metaArray[var17] = (byte)  biomegenbase.Layer3Meta;
                                if(height == biomegenbase.Layer3)
                                {
                                    if(rand.nextBoolean())
                                    {
                                        blockArray[var17+1] = (byte) biomegenbase.Layer3Type; 
                                        metaArray[var17+1] = (byte)  biomegenbase.Layer3Meta;
                                        if(rand.nextBoolean())
                                        {
                                            blockArray[var17+2] = (byte) biomegenbase.Layer3Type; 
                                            metaArray[var17+2] = (byte)  biomegenbase.Layer3Meta;
                                            if(rand.nextBoolean())
                                            {
                                                blockArray[var17+3] = (byte) biomegenbase.Layer3Type; 
                                                metaArray[var17+3] = (byte)  biomegenbase.Layer3Meta;
                                            }
                                        }
                                    }
                                }
                            }
                            else if(height <= biomegenbase.Layer2 && height > biomegenbase.Layer3)
                            {
                                blockArray[var17] = (byte) biomegenbase.Layer2Type; 
                                metaArray[var17] = (byte)  biomegenbase.Layer2Meta;
                                if(height == biomegenbase.Layer2)
                                {
                                    if(rand.nextBoolean())
                                    {
                                        blockArray[var17+1] = (byte) biomegenbase.Layer2Type; 
                                        metaArray[var17+1] = (byte)  biomegenbase.Layer2Meta;
                                        if(rand.nextBoolean())
                                        {
                                            blockArray[var17+2] = (byte) biomegenbase.Layer2Type; 
                                            metaArray[var17+2] = (byte)  biomegenbase.Layer2Meta;
                                            if(rand.nextBoolean())
                                            {
                                                blockArray[var17+3] = (byte) biomegenbase.Layer2Type; 
                                                metaArray[var17+3] = (byte)  biomegenbase.Layer2Meta;
                                            }
                                        }
                                    }
                                }
                            }
                            else if(height <= biomegenbase.Layer1 && height > biomegenbase.Layer2)
                            {
                                blockArray[var17] = (byte) biomegenbase.Layer1Type; 
                                metaArray[var17] = (byte)  biomegenbase.Layer1Meta;    
                                if(height == biomegenbase.Layer3)
                                {
                                    if(rand.nextBoolean())
                                    {
                                        blockArray[var17+1] = (byte) biomegenbase.Layer1Type; 
                                        metaArray[var17+1] = (byte)  biomegenbase.Layer1Meta;
                                        if(rand.nextBoolean())
                                        {
                                            blockArray[var17+2] = (byte) biomegenbase.Layer1Type; 
                                            metaArray[var17+2] = (byte)  biomegenbase.Layer1Meta;
                                            if(rand.nextBoolean())
                                            {
                                                blockArray[var17+3] = (byte) biomegenbase.Layer1Type; 
                                                metaArray[var17+3] = (byte)  biomegenbase.Layer1Meta;
                                            }
                                        }
                                    }
                                }
                            }
                            else
                            {
                                blockArray[var17] = (byte) biomegenbase.SurfaceType; 
                                metaArray[var17] = (byte) biomegenbase.SurfaceMeta;
                            }
                            
                            if (var13 == -1)
                            {
                                if (var12 <= 0)
                                {
                                    var14 = 0;

                                }
                                else if (height >= var5 - 4 && height <= var5 + 1)
                                {
                                    var14 = biomegenbase.GrassID;
                                    var15 =  biomegenbase.DirtID;
                                }

                                if (height < var5 && var14 == 0)
                                {
                                    if (var11 < 0.15F)
                                    {
                                        var14 = Block.ice.blockID;
                                    }
                                    else
                                    {
                                        var14 = Block.waterStill.blockID;
                                    }
                                }

                                var13 = var12;

                                if (height >= var5 - 1)
                                {
                                    blockArray[var17] = (byte) var14;
                                }
                                else
                                {
                                    blockArray[var17] = (byte) var15;
                                    metaArray[var17] = (byte) biomegenbase.SurfaceMeta;
                                }
                            }
                            else if (var13 > 0)
                            {
                                --var13;
                                blockArray[var17] = (byte) var15;
                                metaArray[var17] = (byte) biomegenbase.SurfaceMeta;

                                if (var13 == 0 && var15 == Block.sand.blockID)
                                {
                                    var13 = rand.nextInt(4);
                                    var15 = Block.sandStone.blockID;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    public static void replaceBlocksForBiomeLow(int par1, int par2, byte[] blockArray, byte[] metaArray, BiomeGenBase[] par4ArrayOfBiomeGenBase, double[] stoneNoise, NoiseGeneratorOctaves noiseGen4, Random rand)
    {
        int var5 = 63;
        double var6 = 0.03125D;
        stoneNoise = noiseGen4.generateNoiseOctaves(stoneNoise, par1 * 16, par2 * 16, 0, 16, 16, 1, var6 * 2.0D, var6 * 2.0D, var6 * 2.0D);

        for (int xCoord = 0; xCoord < 16; ++xCoord)
        {
            for (int zCoord = 0; zCoord < 16; ++zCoord)
            {
                BiomeGenBase biomegenbase = par4ArrayOfBiomeGenBase[zCoord + xCoord * 16];
                float var11 = biomegenbase.getFloatTemperature();
                int var12 = (int)(stoneNoise[xCoord + zCoord * 16] / 3.0D + 3.0D + rand.nextDouble() * 0.25D);
                int var13 = -1;
                int var14 = biomegenbase.GrassID;
                int var15 = biomegenbase.DirtID;

                for (int height = 127; height >= 0; --height)
                {
                    int var17 = ((zCoord * 16 + xCoord) * 128 + height);

                    metaArray[var17] = 0;
                    
                    if(height <= 127)
                        blockArray[var17] = (byte) Block.stone.blockID;

                    if (height <= 1)
                    {
                        blockArray[var17] = (byte) Block.bedrock.blockID;
                    }
                    else
                    {
                        int var18 = blockArray[var17];
                        if (var18 == 0)
                        {
                            var13 = -1;
                        }
                        else if (var18 == Block.stone.blockID)
                        {
                            if(height <= biomegenbase.Layer3)
                            {
                                blockArray[var17] = (byte) biomegenbase.Layer3Type; 
                                metaArray[var17] = (byte)  biomegenbase.Layer3Meta;
                                if(height == biomegenbase.Layer3)
                                {
                                    if(rand.nextBoolean())
                                    {
                                        blockArray[var17+1] = (byte) biomegenbase.Layer3Type; 
                                        metaArray[var17+1] = (byte)  biomegenbase.Layer3Meta;
                                        if(rand.nextBoolean())
                                        {
                                            blockArray[var17+2] = (byte) biomegenbase.Layer3Type; 
                                            metaArray[var17+2] = (byte)  biomegenbase.Layer3Meta;
                                            if(rand.nextBoolean())
                                            {
                                                blockArray[var17+3] = (byte) biomegenbase.Layer3Type; 
                                                metaArray[var17+3] = (byte)  biomegenbase.Layer3Meta;
                                            }
                                        }
                                    }
                                }
                            }
                            else if(height <= biomegenbase.Layer2 && height > biomegenbase.Layer3)
                            {
                                blockArray[var17] = (byte) biomegenbase.Layer2Type; 
                                metaArray[var17] = (byte)  biomegenbase.Layer2Meta;
                                if(height == biomegenbase.Layer2)
                                {
                                    if(rand.nextBoolean())
                                    {
                                        blockArray[var17+1] = (byte) biomegenbase.Layer2Type; 
                                        metaArray[var17+1] = (byte)  biomegenbase.Layer2Meta;
                                        if(rand.nextBoolean())
                                        {
                                            blockArray[var17+2] = (byte) biomegenbase.Layer2Type; 
                                            metaArray[var17+2] = (byte)  biomegenbase.Layer2Meta;
                                            if(rand.nextBoolean())
                                            {
                                                blockArray[var17+3] = (byte) biomegenbase.Layer2Type; 
                                                metaArray[var17+3] = (byte)  biomegenbase.Layer2Meta;
                                            }
                                        }
                                    }
                                }
                            }
                            else if(height <= biomegenbase.Layer1 && height > biomegenbase.Layer2)
                            {
                                blockArray[var17] = (byte) biomegenbase.Layer1Type; 
                                metaArray[var17] = (byte)  biomegenbase.Layer1Meta;    
                                if(height == biomegenbase.Layer3)
                                {
                                    if(rand.nextBoolean())
                                    {
                                        blockArray[var17+1] = (byte) biomegenbase.Layer1Type; 
                                        metaArray[var17+1] = (byte)  biomegenbase.Layer1Meta;
                                        if(rand.nextBoolean())
                                        {
                                            blockArray[var17+2] = (byte) biomegenbase.Layer1Type; 
                                            metaArray[var17+2] = (byte)  biomegenbase.Layer1Meta;
                                            if(rand.nextBoolean())
                                            {
                                                blockArray[var17+3] = (byte) biomegenbase.Layer1Type; 
                                                metaArray[var17+3] = (byte)  biomegenbase.Layer1Meta;
                                            }
                                        }
                                    }
                                }
                            }
                            else
                            {
                                blockArray[var17] = (byte) biomegenbase.SurfaceType; 
                                metaArray[var17] = (byte) biomegenbase.SurfaceMeta;
                            }
                        }
                    }
                }
            }
        }
    }
    
   

    /**This version performs all the intial block placement in the initial loop, eliminating
     * a major source of chunkloader lag.*/
    public static void replaceBlocksForBiome(int par1, int par2, byte[] blockArray, byte[] metaArray, BiomeGenBase[] par4ArrayOfBiomeGenBase, 
            double[] stoneNoise, NoiseGeneratorOctaves noiseGen4, Random rand)
    {
        byte var5 = 63;
        double var6 = 0.03125D;
        stoneNoise = noiseGen4.generateNoiseOctaves(stoneNoise, par1 * 16, par2 * 16, 0, 16, 16, 1, var6 * 2.0D, var6 * 2.0D, var6 * 2.0D);

        for (int xCoord = 0; xCoord < 16; ++xCoord)
        {
            for (int zCoord = 0; zCoord < 16; ++zCoord)
            {
                BiomeGenBase biomegenbase = par4ArrayOfBiomeGenBase[zCoord + xCoord * 16];
                float var11 = biomegenbase.getFloatTemperature();
                int var12 = (int)(stoneNoise[xCoord + zCoord * 16] / 3.0D + 3.0D + rand.nextDouble() * 0.25D);
                int var13 = -1;
                int var14 = biomegenbase.GrassID;
                int var15 = biomegenbase.DirtID;

                for (int height = 127; height >= 0; --height)
                {
                    int var17 = (zCoord * 16 + xCoord) * 128 + height;

                    int var10 = height >> 4;
        metaArray[var17] = 0;

        if (height <= 1)
        {
            blockArray[var17] = (byte) Block.bedrock.blockID;
        }
        else
        {
            int var18 = blockArray[var17];
            if (var18 == 0)
            {
                var13 = -1;
            }
            else if (var18 == Block.stone.blockID)
            {
                if(height <= biomegenbase.Layer3)
                {
                    blockArray[var17] = (byte) biomegenbase.Layer3Type; 
                    metaArray[var17] = (byte)  biomegenbase.Layer3Meta;
                    if(height == biomegenbase.Layer3)
                    {
                        if(rand.nextBoolean())
                        {
                            blockArray[var17+1] = (byte) biomegenbase.Layer3Type; 
                            metaArray[var17+1] = (byte)  biomegenbase.Layer3Meta;
                            if(rand.nextBoolean())
                            {
                                blockArray[var17+2] = (byte) biomegenbase.Layer3Type; 
                                metaArray[var17+2] = (byte)  biomegenbase.Layer3Meta;
                                if(rand.nextBoolean())
                                {
                                    blockArray[var17+3] = (byte) biomegenbase.Layer3Type; 
                                    metaArray[var17+3] = (byte)  biomegenbase.Layer3Meta;
                                }
                            }
                        }
                    }
                }
                else if(height <= biomegenbase.Layer2 && height > biomegenbase.Layer3)
                {
                    blockArray[var17] = (byte) biomegenbase.Layer2Type; 
                    metaArray[var17] = (byte)  biomegenbase.Layer2Meta;
                    if(height == biomegenbase.Layer2)
                    {
                        if(rand.nextBoolean())
                        {
                            blockArray[var17+1] = (byte) biomegenbase.Layer2Type; 
                            metaArray[var17+1] = (byte)  biomegenbase.Layer2Meta;
                            if(rand.nextBoolean())
                            {
                                blockArray[var17+2] = (byte) biomegenbase.Layer2Type; 
                                metaArray[var17+2] = (byte)  biomegenbase.Layer2Meta;
                                if(rand.nextBoolean())
                                {
                                    blockArray[var17+3] = (byte) biomegenbase.Layer2Type; 
                                    metaArray[var17+3] = (byte)  biomegenbase.Layer2Meta;
                                }
                            }
                        }
                    }
                }
                else if(height <= biomegenbase.Layer1 && height > biomegenbase.Layer2)
                {
                    blockArray[var17] = (byte) biomegenbase.Layer1Type; 
                    metaArray[var17] = (byte)  biomegenbase.Layer1Meta;    
                    if(height == biomegenbase.Layer3)
                    {
                        if(rand.nextBoolean())
                        {
                            blockArray[var17+1] = (byte) biomegenbase.Layer1Type; 
                            metaArray[var17+1] = (byte)  biomegenbase.Layer1Meta;
                            if(rand.nextBoolean())
                            {
                                blockArray[var17+2] = (byte) biomegenbase.Layer1Type; 
                                metaArray[var17+2] = (byte)  biomegenbase.Layer1Meta;
                                if(rand.nextBoolean())
                                {
                                    blockArray[var17+3] = (byte) biomegenbase.Layer1Type; 
                                    metaArray[var17+3] = (byte)  biomegenbase.Layer1Meta;
                                }
                            }
                        }
                    }
                }
                else
                {
                    blockArray[var17] = (byte) biomegenbase.SurfaceType; 
                    metaArray[var17] = (byte) biomegenbase.SurfaceMeta;
                }
                if (var13 == -1)
                {
                    if (var12 <= 0)
                    {
                        var14 = 0;

                    }
                    else if (height >= var5 - 4 && height <= var5 + 1)
                    {
                        var14 = biomegenbase.GrassID;
                        var15 =  biomegenbase.DirtID;
                    }

                    if (height < var5 && var14 == 0)
                    {
                        if (var11 < 0.15F)
                        {
                            var14 = Block.ice.blockID;
                        }
                        else
                        {
                            var14 = Block.waterStill.blockID;
                        }
                    }

                    var13 = var12;

                    if (height >= var5 - 1)
                    {
                        blockArray[var17] = (byte) var14;
                    }
                    else
                    {
                        blockArray[var17] = (byte) var15;
                        metaArray[var17] = (byte) biomegenbase.SurfaceMeta;
                    }
                }
                else if (var13 > 0)
                {
                    --var13;
                    blockArray[var17] = (byte) var15;
                    metaArray[var17] = (byte) biomegenbase.SurfaceMeta;

                    if (var13 == 0 && var15 == Block.sand.blockID)
                    {
                        var13 = rand.nextInt(4);
                        var15 = Block.sandStone.blockID;
                    }
                }
            }
        }
                }
            }
        }
    }
}
