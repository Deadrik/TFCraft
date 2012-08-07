package TFC.WorldGen;

import java.util.Random;

import net.minecraft.src.*;

public class TFC_ChunkProviderGenerate
{
    /**This version performs all the intial block placement in the initial loop, eliminating
     * a major source of chunkloader lag.*/
    public static void replaceBlocksForBiomeHigh(int par1, int par2, byte[] blockArray, byte[] metaArray, BiomeGenBase[] par4ArrayOfBiomeGenBase, 
            double[] stoneNoise, NoiseGeneratorOctaves noiseGen4, Random rand, byte[] blockArrayBig, byte[] metaArrayBig)
    {
        int var5 = 16;
        double var6 = 0.03125D;
        stoneNoise = noiseGen4.generateNoiseOctaves(stoneNoise, par1 * 16, par2 * 16, 0, 16, 16, 1, var6 * 2.0D, var6 * 2.0D, var6 * 2.0D);

        for (int xCoord = 0; xCoord < 16; ++xCoord)
        {
            for (int zCoord = 0; zCoord < 16; ++zCoord)
            {
                TFCBiome biomegenbase = (TFCBiome) par4ArrayOfBiomeGenBase[zCoord + xCoord * 16];
                float var11 = biomegenbase.getFloatTemperature();
                int var12 = (int)(stoneNoise[xCoord + zCoord * 16] / 3.0D + 3.0D + rand.nextDouble() * 0.25D);  
                int var13 = -1;
                int var14 = biomegenbase.GrassID;
                int var15 = biomegenbase.DirtID;

                for (int height = 127; height >= 0; --height)
                {
                    int indexBig = ((zCoord * 16 + xCoord) * 256 + height + 128);
                    int index = ((zCoord * 16 + xCoord) * 128 + height);
                    metaArrayBig[indexBig] = 0;

                    int var18 = blockArray[index];
                    blockArrayBig[indexBig] = blockArray[index];
                    metaArrayBig[indexBig] = metaArray[index];

                    if (var18 == 0)
                    {
                        var13 = -1;
                    }
                    else if (var18 == Block.stone.blockID)
                    {
                        if(height+128 <= biomegenbase.Layer3)
                        {
                            blockArrayBig[indexBig] = (byte) biomegenbase.Layer3Type; 
                            metaArrayBig[indexBig] = (byte)  biomegenbase.Layer3Meta;
                            if(height == biomegenbase.Layer3)
                            {
                                if(rand.nextBoolean())
                                {
                                    blockArrayBig[indexBig+1] = (byte) biomegenbase.Layer3Type; 
                                    metaArrayBig[indexBig+1] = (byte)  biomegenbase.Layer3Meta;
                                    if(rand.nextBoolean())
                                    {
                                        blockArrayBig[indexBig+2] = (byte) biomegenbase.Layer3Type; 
                                        metaArrayBig[indexBig+2] = (byte)  biomegenbase.Layer3Meta;
                                        if(rand.nextBoolean())
                                        {
                                            blockArrayBig[indexBig+3] = (byte) biomegenbase.Layer3Type; 
                                            metaArrayBig[indexBig+3] = (byte)  biomegenbase.Layer3Meta;
                                        }
                                    }
                                }
                            }
                        }
                        else if(height+128 <= biomegenbase.Layer2 && height +128> biomegenbase.Layer3)
                        {
                            blockArrayBig[indexBig] = (byte) biomegenbase.Layer2Type; 
                            metaArrayBig[indexBig] = (byte)  biomegenbase.Layer2Meta;
                            if(height == biomegenbase.Layer2)
                            {
                                if(rand.nextBoolean())
                                {
                                    blockArrayBig[indexBig+1] = (byte) biomegenbase.Layer2Type; 
                                    metaArrayBig[indexBig+1] = (byte)  biomegenbase.Layer2Meta;
                                    if(rand.nextBoolean())
                                    {
                                        blockArrayBig[indexBig+2] = (byte) biomegenbase.Layer2Type; 
                                        metaArrayBig[indexBig+2] = (byte)  biomegenbase.Layer2Meta;
                                        if(rand.nextBoolean())
                                        {
                                            blockArrayBig[indexBig+3] = (byte) biomegenbase.Layer2Type; 
                                            metaArrayBig[indexBig+3] = (byte)  biomegenbase.Layer2Meta;
                                        }
                                    }
                                }
                            }
                        }
                        else if(height+128 <= biomegenbase.Layer1 && height+128 > biomegenbase.Layer2)
                        {
                            blockArrayBig[indexBig] = (byte) biomegenbase.Layer1Type; 
                            metaArrayBig[indexBig] = (byte)  biomegenbase.Layer1Meta;    
                            if(height == biomegenbase.Layer3)
                            {
                                if(rand.nextBoolean())
                                {
                                    blockArrayBig[indexBig+1] = (byte) biomegenbase.Layer1Type; 
                                    metaArrayBig[indexBig+1] = (byte)  biomegenbase.Layer1Meta;
                                    if(rand.nextBoolean())
                                    {
                                        blockArrayBig[indexBig+2] = (byte) biomegenbase.Layer1Type; 
                                        metaArrayBig[indexBig+2] = (byte)  biomegenbase.Layer1Meta;
                                        if(rand.nextBoolean())
                                        {
                                            blockArrayBig[indexBig+3] = (byte) biomegenbase.Layer1Type; 
                                            metaArrayBig[indexBig+3] = (byte)  biomegenbase.Layer1Meta;
                                        }
                                    }
                                }
                            }
                        }
                        else
                        {
                            blockArrayBig[indexBig] = (byte) biomegenbase.SurfaceType; 
                            metaArrayBig[indexBig] = (byte) biomegenbase.SurfaceMeta;
                        }

                        if (var13 == -1)
                        {
                            if (var12 <= 0)
                            {
                                var14 = 0;
                            }

                            if (height < var5 && var14 == 0)
                            {
                                if (var11 < 0.15F)
                                {
                                    var14 = Block.ice.blockID;
                                }
                                else if (biomegenbase.getFloatRainfall() == 0)
                                {
                                    var14 = 0;
                                }
                                else
                                {
                                    var14 = Block.waterStill.blockID;
                                }
                            }

                            var13 = var12;

                            if (height >= var5 - 1 && index+1 < blockArray.length && blockArray[index+1] != Block.waterStill.blockID)
                            {
                                blockArrayBig[indexBig] = (byte) var14;
                                metaArrayBig[indexBig] = (byte) biomegenbase.TopSoilMetaID;
                            }
                            else
                            {
                                blockArrayBig[indexBig] = (byte) var15;
                                metaArrayBig[indexBig] = (byte) biomegenbase.TopSoilMetaID;
                            }
                        }
                        else if (var13 > 0)
                        {
                            --var13;
                            blockArrayBig[indexBig] = (byte) var15;
                            metaArrayBig[indexBig] = (byte) biomegenbase.TopSoilMetaID;

                            if (var13 == 0 && var15 == Block.sand.blockID)
                            {
                                var13 = rand.nextInt(4);
                                var15 = Block.sandStone.blockID;
                            }
                        }

                        if(biomegenbase.biomeID == 0)
                        {
                            if(((height > var5-2 && height <= var5+1) || (height < var5 && blockArray[index+2] == Block.waterStill.blockID)))//If its an ocean give it a sandy bottom
                            {

                                blockArrayBig[indexBig] = (byte) Block.sand.blockID;
                                metaArrayBig[indexBig] = 0;
                            }
                        }
                        else if(!(biomegenbase instanceof BiomeGenSwampTFC))
                        {
                            if(((height > var5-2 && height < var5 && blockArray[index+1] == Block.waterStill.blockID) || (height < var5 && blockArray[index+1] == Block.waterStill.blockID)))//If its an ocean give it a sandy bottom
                            {
                                if(blockArrayBig[indexBig] != Block.sand.blockID && rand.nextInt(3) != 0)
                                {
                                    blockArrayBig[indexBig] = (byte) Block.gravel.blockID;
                                    metaArrayBig[indexBig] = 0;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static void replaceBlocksForBiomeLow(int par1, int par2, byte[] blockArray, byte[] metaArray, BiomeGenBase[] par4ArrayOfBiomeGenBase, 
            double[] stoneNoise, NoiseGeneratorOctaves noiseGen4, Random rand, byte[] blockArrayBig, byte[] metaArrayBig)
    {
        int var5 = 63;
        double var6 = 0.03125D;
        stoneNoise = noiseGen4.generateNoiseOctaves(stoneNoise, par1 * 16, par2 * 16, 0, 16, 16, 1, var6 * 2.0D, var6 * 2.0D, var6 * 2.0D);

        for (int xCoord = 0; xCoord < 16; ++xCoord)
        {
            for (int zCoord = 0; zCoord < 16; ++zCoord)
            {
                TFCBiome biomegenbase = (TFCBiome) par4ArrayOfBiomeGenBase[zCoord + xCoord * 16];
                float var11 = biomegenbase.getFloatTemperature();
                int var12 = (int)(stoneNoise[xCoord + zCoord * 16] / 3.0D + 3.0D + rand.nextDouble() * 0.25D);
                int var13 = -1;
                int var14 = biomegenbase.GrassID;
                int var15 = biomegenbase.DirtID;

                for (int height = 127; height >= 0; --height)
                {
                    int index = ((zCoord * 16 + xCoord) * 128 + height);
                    int indexBig = ((zCoord * 16 + xCoord) * 256 + height);

                    metaArrayBig[indexBig] = 0;

                    if (height >= 2 && height <= 6)
                    {
                        blockArrayBig[indexBig] = (byte) Block.lavaStill.blockID;
                        metaArrayBig[indexBig] = 0; 

                        if(blockArrayBig[indexBig+1] != (byte) Block.lavaStill.blockID && rand.nextBoolean())
                        {
                            blockArrayBig[indexBig+1] = (byte) Block.lavaStill.blockID;
                            metaArrayBig[indexBig+1] = 0; 
                        }
                    }
                    else if (height <= 1)
                    {
                        blockArrayBig[indexBig] = (byte) Block.bedrock.blockID;
                    }
                    else
                    {
                        if(height <= biomegenbase.Layer3)
                        {
                            blockArrayBig[indexBig] = (byte) biomegenbase.Layer3Type; 
                            metaArrayBig[indexBig] = (byte)  biomegenbase.Layer3Meta;
                            if(height == biomegenbase.Layer3)
                            {
                                if(rand.nextBoolean())
                                {
                                    blockArrayBig[indexBig+1] = (byte) biomegenbase.Layer3Type; 
                                    metaArrayBig[indexBig+1] = (byte)  biomegenbase.Layer3Meta;
                                    if(rand.nextBoolean())
                                    {
                                        blockArrayBig[indexBig+2] = (byte) biomegenbase.Layer3Type; 
                                        metaArrayBig[indexBig+2] = (byte)  biomegenbase.Layer3Meta;
                                        if(rand.nextBoolean())
                                        {
                                            blockArrayBig[indexBig+3] = (byte) biomegenbase.Layer3Type; 
                                            metaArrayBig[indexBig+3] = (byte)  biomegenbase.Layer3Meta;
                                        }
                                    }
                                }
                            }
                        }
                        else if(height <= biomegenbase.Layer2 && height > biomegenbase.Layer3)
                        {
                            blockArrayBig[indexBig] = (byte) biomegenbase.Layer2Type; 
                            metaArrayBig[indexBig] = (byte)  biomegenbase.Layer2Meta;
                            if(height == biomegenbase.Layer2)
                            {
                                if(rand.nextBoolean())
                                {
                                    blockArrayBig[indexBig+1] = (byte) biomegenbase.Layer2Type; 
                                    metaArrayBig[indexBig+1] = (byte)  biomegenbase.Layer2Meta;
                                    if(rand.nextBoolean())
                                    {
                                        blockArrayBig[indexBig+2] = (byte) biomegenbase.Layer2Type; 
                                        metaArrayBig[indexBig+2] = (byte)  biomegenbase.Layer2Meta;
                                        if(rand.nextBoolean())
                                        {
                                            blockArrayBig[indexBig+3] = (byte) biomegenbase.Layer2Type; 
                                            metaArrayBig[indexBig+3] = (byte)  biomegenbase.Layer2Meta;
                                        }
                                    }
                                }
                            }
                        }
                        else if(height > biomegenbase.Layer2)
                        {
                            blockArrayBig[indexBig] = (byte) biomegenbase.Layer1Type; 
                            metaArrayBig[indexBig] = (byte)  biomegenbase.Layer1Meta;    
                            if(height == biomegenbase.Layer3)
                            {
                                if(rand.nextBoolean())
                                {
                                    blockArrayBig[indexBig+1] = (byte) biomegenbase.Layer1Type; 
                                    metaArrayBig[indexBig+1] = (byte)  biomegenbase.Layer1Meta;
                                    if(rand.nextBoolean())
                                    {
                                        blockArrayBig[indexBig+2] = (byte) biomegenbase.Layer1Type; 
                                        metaArrayBig[indexBig+2] = (byte)  biomegenbase.Layer1Meta;
                                        if(rand.nextBoolean())
                                        {
                                            blockArrayBig[indexBig+3] = (byte) biomegenbase.Layer1Type; 
                                            metaArrayBig[indexBig+3] = (byte)  biomegenbase.Layer1Meta;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    public static void replaceBlocksForBiome256(int par1, int par2, byte[] blockArray, byte[] metaArray, BiomeGenBase[] par4ArrayOfBiomeGenBase, 
            double[] stoneNoise, NoiseGeneratorOctaves noiseGen4, Random rand)
    {
        int seaLevel = 145;
        double var6 = 0.03125D;
        stoneNoise = noiseGen4.generateNoiseOctaves(stoneNoise, par1 * 16, par2 * 16, 0, 16, 16, 1, var6 * 2.0D, var6 * 2.0D, var6 * 2.0D);

        for (int var8 = 0; var8 < 16; ++var8)
        {
            for (int var9 = 0; var9 < 16; ++var9)
            {
                TFCBiome biomegenbase = (TFCBiome) par4ArrayOfBiomeGenBase[var9 + var8 * 16];
                float var11 = biomegenbase.getFloatTemperature();
                int var12 = (int)(stoneNoise[var8 + var9 * 16] / 3.0D + 3.0D + rand.nextDouble() * 0.25D);
                int blockDensity = -1;
                byte var14 = (byte) biomegenbase.GrassID;
                byte var15 = (byte) biomegenbase.DirtID;

                for (int height = 255; height >= 0; --height)
                {
                    int var17 = (var9 * 16 + var8) * 256 + height;

                    metaArray[var17] = 0;

                    if (height >= 2 && height <= 6)
                    {
                        blockArray[var17] = (byte) Block.lavaStill.blockID;
                        metaArray[var17] = 0; 

                        if(blockArray[var17+1] != (byte) Block.lavaStill.blockID && rand.nextBoolean())
                        {
                            blockArray[var17+1] = (byte) Block.lavaStill.blockID;
                            metaArray[var17+1] = 0; 
                        }
                    }
                    else if (height <= 1)
                    {
                        blockArray[var17] = (byte) Block.bedrock.blockID;
                    }
                    else
                    {
                        int var18 = blockArray[var17];
                        if (var18 == 0)
                        {
                            blockDensity = -1;
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

                            //                            if(var17 + 1 < blockArray.length && blockArray[var17 + 1] == 0)
                            //                            {
                            //                                var14 = (byte) biomegenbase.GrassID;
                            //                                var15 =  (byte) biomegenbase.DirtID;
                            //
                            //                                blockArray[var17] = var14;
                            //                                metaArray[var17] = (byte) biomegenbase.TopSoilMetaID;
                            //
                            //                                if(blockArray[var17 - 1] != 0)
                            //                                {
                            //                                    blockArray[var17-1] = var15;
                            //                                    metaArray[var17-1] = (byte) biomegenbase.TopSoilMetaID;
                            //                                }
                            //                                if(blockArray[var17 - 2] != 0)
                            //                                {
                            //                                    blockArray[var17-2] = var15;
                            //                                    metaArray[var17-2] = (byte) biomegenbase.TopSoilMetaID;
                            //                                }
                            //                                if(blockArray[var17 - 3] != 0)
                            //                                {
                            //                                    blockArray[var17-3] = var15;
                            //                                    metaArray[var17-3] = (byte) biomegenbase.TopSoilMetaID;
                            //                                }
                            //                                if(blockArray[var17 - 4] != 0)
                            //                                {
                            //                                    blockArray[var17-4] = var15;
                            //                                    metaArray[var17-4] = (byte) biomegenbase.TopSoilMetaID;
                            //                                }
                            //                            }

                            if (blockDensity == -1)
                            {
                                if (var12 <= 0)
                                {
                                    var14 = 0;
                                    var15 = 0;
                                }
                                else if (height >= seaLevel - 4 && height <= seaLevel + 1)
                                {
                                    var14 = (byte) biomegenbase.GrassID;
                                    var15 =  (byte) biomegenbase.DirtID;
                                }

                                if (height < seaLevel && var14 == 0)
                                {
                                    if (var11 < 0.15F)
                                    {
                                        var14 = (byte) Block.ice.blockID;
                                    }
                                    else if (biomegenbase.getFloatRainfall() == 0)
                                    {
                                        var14 = 0;
                                    }
                                    else
                                    {
                                        var14 = (byte) Block.waterStill.blockID;
                                    }
                                }

                                blockDensity = var12;

                                if (height >= seaLevel - 1 && var17+1 < blockArray.length && blockArray[var17+1] == 0)
                                {
                                    blockArray[var17] = (byte) var14;
                                    metaArray[var17] = (byte) biomegenbase.TopSoilMetaID;
                                }
                                else
                                {
                                    blockArray[var17] = (byte) var15;
                                    metaArray[var17] = (byte) biomegenbase.TopSoilMetaID;
                                }
                            }
                            else if (blockDensity > 0)
                            {
                                --blockDensity;
                                blockArray[var17] = (byte) var15;
                                metaArray[var17] = (byte) biomegenbase.TopSoilMetaID;

                                if (blockDensity == 0 && var15 == Block.sand.blockID)
                                {
                                    blockDensity = rand.nextInt(4);
                                    var15 = (byte) Block.sandStone.blockID;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
