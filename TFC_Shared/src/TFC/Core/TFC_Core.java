package TFC.Core;

import java.util.ArrayList;
import java.util.Random;

import cpw.mods.fml.common.ReflectionHelper;

import TFC.TileEntities.TileEntityPartial;
import TFC.WorldGen.WorldGenClayPit;
import TFC.WorldGen.WorldGenCustomFlowers;
import TFC.WorldGen.WorldGenCustomFruitTree;
import TFC.WorldGen.WorldGenCustomFruitTree2;
import TFC.WorldGen.WorldGenCustomTallGrass;
import TFC.WorldGen.WorldGenLooseRocks;
import TFC.WorldGen.WorldGenMinableTFC;
import TFC.WorldGen.WorldGenPeatPit;
import net.minecraft.src.*;
import net.minecraft.src.forge.MinecraftForge;

public class TFC_Core
{
    public enum Direction{PosX,PosZ,NegX,NegZ,None,PosXPosZ,PosXNegZ,NegXPosZ,NegXNegZ,NegY,PosY}

    public static Item[] Axes;

    public static Item[] Chisels;

    public static Item[] Saws;

    public static Item[] Knives;

    private static void createOre(int i, int j, int[] Layers, int rarity, int veinSize, 
            int veinAmount, int height, int diameter, int vDensity, int hDensity,World world, Random rand, int chunkX, int chunkZ, int min, int max)
    {
        for(int n = 0; n < Layers.length/2;)
        {
            BiomeGenBase biome = world.getBiomeGenForCoords(chunkX, chunkZ);
            if((biome.SurfaceType == Layers[n] && (biome.SurfaceMeta == Layers[n+1] || Layers[n+1] == -1)) || 
                    (biome.Layer1Type == Layers[n] && (biome.Layer1Meta == Layers[n+1] || Layers[n+1] == -1)) ||
                    (biome.Layer2Type == Layers[n] && (biome.Layer2Meta == Layers[n+1] || Layers[n+1] == -1)) ||
                    (biome.Layer3Type == Layers[n] && (biome.Layer3Meta == Layers[n+1] || Layers[n+1] == -1)))
            {
                new WorldGenMinableTFC(i, j,Layers[n],Layers[n+1],rarity,veinSize,veinAmount,height,diameter,vDensity,hDensity).generate(
                        world, rand, chunkX, chunkZ, min, max);
            }
            n+=2;
        }
    }

    private static void createOreVein(int i, int j, int[] Layers, int rarity, int veinSize, 
            int veinAmount, int height, int diameter, int vDensity, int hDensity,World world, Random rand, int chunkX, int chunkZ, int min, int max)
    {
        for(int n = 0; n < Layers.length/2;)
        {
            BiomeGenBase biome = world.getBiomeGenForCoords(chunkX, chunkZ);
            if((biome.SurfaceType == Layers[n] && (biome.SurfaceMeta == Layers[n+1] || Layers[n+1] == -1)) || 
                    (biome.Layer1Type == Layers[n] && (biome.Layer1Meta == Layers[n+1] || Layers[n+1] == -1)) ||
                    (biome.Layer2Type == Layers[n] && (biome.Layer2Meta == Layers[n+1] || Layers[n+1] == -1)) ||
                    (biome.Layer3Type == Layers[n] && (biome.Layer3Meta == Layers[n+1] || Layers[n+1] == -1)))
            {
                new WorldGenMinableTFC(i, j,Layers[n],Layers[n+1],rarity,veinSize,veinAmount,height,diameter,vDensity,hDensity).generateVein(
                        world, rand, chunkX, chunkZ, min, max);
            }
            n+=2;
        }
    }
    public static void CreateTreeLimb(World world, int i, int j, int k, int meta, Direction dir, Random R)
    {
        if(dir == Direction.PosX)
        {
            if(world.getBlockId(i+1, j, k) == Block.leaves.blockID)
            {
                world.setBlockAndMetadata(i+1, j, k, Block.wood.blockID, meta);
                SurroundWithLeaves(world,i+1,j,k,meta,R);
                if(world.getBlockId(i+2, j, k) == Block.leaves.blockID)
                {
                    world.setBlockAndMetadata(i+2, j, k, Block.wood.blockID, meta);
                    SurroundWithLeaves(world,i+2,j,k,meta,R);
                    if(world.getBlockId(i+3, j, k) == Block.leaves.blockID && R.nextInt(10) == 0)
                    {
                        world.setBlockAndMetadata(i+3, j, k, Block.wood.blockID, meta);
                        SurroundWithLeaves(world,i+3,j,k,meta,R);
                        if(world.getBlockId(i+4, j-1, k) != Block.leaves.blockID && R.nextInt(10) == 0)
                        {
                            world.setBlockAndMetadata(i+4, j-1, k, Block.wood.blockID, meta);
                            SurroundWithLeaves(world,i+4,j-1,k,meta,R);
                        }
                    }
                }
            }
        }
        if(dir == Direction.NegX)
        {
            if(world.getBlockId(i-1, j, k) == Block.leaves.blockID)
            {
                world.setBlockAndMetadata(i-1, j, k, Block.wood.blockID, meta);
                SurroundWithLeaves(world,i-1,j,k,meta,R);
                if(world.getBlockId(i-2, j, k) == Block.leaves.blockID)
                {
                    world.setBlockAndMetadata(i-2, j, k, Block.wood.blockID, meta);
                    SurroundWithLeaves(world,i-2,j,k,meta,R);
                    if(world.getBlockId(i-3, j, k) == Block.leaves.blockID && R.nextInt(10) == 0)
                    {
                        world.setBlockAndMetadata(i-3, j, k, Block.wood.blockID, meta);
                        SurroundWithLeaves(world,i-3,j,k,meta,R);
                        if(world.getBlockId(i-4, j-1, k) == Block.leaves.blockID && R.nextInt(10) == 0)
                        {
                            world.setBlockAndMetadata(i-4, j-1, k, Block.wood.blockID, meta);
                            SurroundWithLeaves(world,i-4,j-1,k,meta,R);
                        }
                    }
                }
            }
        }
        if(dir == Direction.PosZ)
        {
            if(world.getBlockId(i, j, k+1) == Block.leaves.blockID)
            {
                world.setBlockAndMetadata(i, j, k+1, Block.wood.blockID, meta);
                SurroundWithLeaves(world,i,j,k+1,meta,R);
                if(world.getBlockId(i, j, k+2) == Block.leaves.blockID)
                {
                    world.setBlockAndMetadata(i, j, k+2, Block.wood.blockID, meta);
                    SurroundWithLeaves(world,i,j,k+2,meta,R);
                    if(world.getBlockId(i, j, k+3) == Block.leaves.blockID && R.nextInt(10) == 0)
                    {
                        world.setBlockAndMetadata(i, j, k+3, Block.wood.blockID, meta);
                        SurroundWithLeaves(world,i,j,k+3,meta,R);
                        if(world.getBlockId(i, j-1, k+4) == Block.leaves.blockID && R.nextInt(10) == 0)
                        {
                            world.setBlockAndMetadata(i, j-1, k+4, Block.wood.blockID, meta);
                            SurroundWithLeaves(world,i,j-1,k+4,meta,R);
                        }
                    }
                }
            }


        }
        if(dir == Direction.NegZ)
        {
            if(world.getBlockId(i, j, k-1) == Block.leaves.blockID)
            {
                world.setBlockAndMetadata(i, j, k-1, Block.wood.blockID, meta);
                SurroundWithLeaves(world,i,j,k-1,meta,R);
                if(world.getBlockId(i, j, k-2) == Block.leaves.blockID)
                {
                    world.setBlockAndMetadata(i, j, k-2, Block.wood.blockID, meta);
                    SurroundWithLeaves(world,i,j,k-2,meta,R);
                    if(world.getBlockId(i, j, k-3) == Block.leaves.blockID && R.nextInt(10) == 0)
                    {
                        world.setBlockAndMetadata(i, j, k-3, Block.wood.blockID, meta);
                        SurroundWithLeaves(world,i,j,k-3,meta,R);
                        if(world.getBlockId(i, j-1, k-4) == Block.leaves.blockID && R.nextInt(10) == 0)
                        {
                            world.setBlockAndMetadata(i, j-1, k-4, Block.wood.blockID, meta);
                            SurroundWithLeaves(world,i,j-1,k-4,meta,R);
                        }
                    }
                }
            }	
        }
    }

    public static void GenerateHigh(World world, Random rand, int chunkX, int chunkZ, int min, int max)
    {
        int height = max-min;
        //============Copper
        createOre(mod_TFC_Core.terraOre.blockID, 0,new int[]{mod_TFC_Core.terraStoneIgEx.blockID,-1,Block.sandStone.blockID,-1},//IgEx and Sandstone, veins
                /*rarity*/35,/*veinSize*/20,/*veinAmt*/15,/*height*/height,/*diameter*/40,/*vDensity*/30,/*hDensity*/40,         world, rand, chunkX, chunkZ, min, max);

        //============Cassiterite
        createOre(mod_TFC_Core.terraOre.blockID, 5,new int[]{mod_TFC_Core.terraStoneIgIn.blockID,0},//Granite Veins
                /*rarity*/20,/*veinSize*/15,/*veinAmt*/20,/*height*/height,/*diameter*/40,/*vDensity*/30,/*hDensity*/50,         world, rand, chunkX, chunkZ, min, max);

        //============Cassiterite2
        createOre(mod_TFC_Core.terraOre.blockID, 5,new int[]{mod_TFC_Core.terraStoneIgEx.blockID,-1},//IgEx Veins
                /*rarity*/20,/*veinSize*/10,/*veinAmt*/15,/*height*/height,/*diameter*/40,/*vDensity*/30,/*hDensity*/60,         world, rand, chunkX, chunkZ, min, max);

        //============Bismuthinite
        createOre(mod_TFC_Core.terraOre.blockID, 7,new int[]{mod_TFC_Core.terraStoneIgIn.blockID,-1,mod_TFC_Core.terraStoneSed.blockID,-1},//Granite Veins
                /*rarity*/25,/*veinSize*/10,/*veinAmt*/25,/*height*/height,/*diameter*/40,/*vDensity*/30,/*hDensity*/60,         world, rand, chunkX, chunkZ, min, max);

        //============Sphalerite
        createOre(mod_TFC_Core.terraOre.blockID, 12,new int[]{mod_TFC_Core.terraStoneMM.blockID,-1},//mm, veins
                /*rarity*/20,/*veinSize*/10,/*veinAmt*/18,/*height*/height,/*diameter*/40,/*vDensity*/60,/*hDensity*/40,         world, rand, chunkX, chunkZ, min, max);
    }
    public static void Generate(World world, Random rand, int chunkX, int chunkZ, int min, int max)
    {
        int height = max-min;
        //============Copper
        createOreVein(mod_TFC_Core.terraOre.blockID, 0,new int[]{mod_TFC_Core.terraStoneIgEx.blockID,-1,Block.sandStone.blockID,-1},//IgEx and Sandstone, veins
                /*rarity*/100,/*veinSize*/80,/*veinAmt*/55,/*height*/height,/*diameter*/100,/*vDensity*/50,/*hDensity*/40,         world, rand, chunkX, chunkZ, min, max);

        //============Gold
        createOreVein(mod_TFC_Core.terraOre.blockID, 1,new int[]{mod_TFC_Core.terraStoneIgEx.blockID,-1,mod_TFC_Core.terraStoneIgIn.blockID,-1},//Ig veins
                /*rarity*/130,/*veinSize*/35,/*veinAmt*/45,/*height*/height,/*diameter*/100,/*vDensity*/50,/*hDensity*/20,         world, rand, chunkX, chunkZ, min, max);

        //============Hematite
        createOreVein(mod_TFC_Core.terraOre.blockID, 3,new int[]{mod_TFC_Core.terraStoneIgEx.blockID,-1},//IgEx veins
                /*rarity*/100,/*veinSize*/80,/*veinAmt*/42,/*height*/height,/*diameter*/100,/*vDensity*/40,/*hDensity*/30,         world, rand, chunkX, chunkZ, min, max);

        //============Silver
        createOreVein(mod_TFC_Core.terraOre.blockID, 4,new int[]{mod_TFC_Core.terraStoneIgIn.blockID,0,mod_TFC_Core.terraStoneMM.blockID,4},//granite and gneiss, veins
                /*rarity*/100,/*veinSize*/80,/*veinAmt*/45,/*height*/height,/*diameter*/100,/*vDensity*/50,/*hDensity*/30,         world, rand, chunkX, chunkZ, min, max);

        //============Cassiterite
        createOreVein(mod_TFC_Core.terraOre.blockID, 5,new int[]{mod_TFC_Core.terraStoneIgIn.blockID,0},//Granite Veins
                /*rarity*/100,/*veinSize*/85,/*veinAmt*/55,/*height*/height,/*diameter*/100,/*vDensity*/50,/*hDensity*/50,         world, rand, chunkX, chunkZ, min, max);

        //============Cassiterite2
        createOreVein(mod_TFC_Core.terraOre.blockID, 5,new int[]{mod_TFC_Core.terraStoneIgEx.blockID,-1},//IgEx Veins
                /*rarity*/140,/*veinSize*/80,/*veinAmt*/45,/*height*/height,/*diameter*/100,/*vDensity*/50,/*hDensity*/60,         world, rand, chunkX, chunkZ, min, max);

        //============Galena
        createOreVein(mod_TFC_Core.terraOre.blockID, 6,new int[]{mod_TFC_Core.terraStoneIgEx.blockID,-1,mod_TFC_Core.terraStoneMM.blockID,-1,
                mod_TFC_Core.terraStoneIgIn.blockID,0,mod_TFC_Core.terraStoneSed.blockID,5},//igex, mm, granite, limestone as veins
                /*rarity*/120,/*veinSize*/80,/*veinAmt*/55,/*height*/height,/*diameter*/100,/*vDensity*/50,/*hDensity*/60,         world, rand, chunkX, chunkZ, min, max);

        //============Bismuthinite
        createOreVein(mod_TFC_Core.terraOre.blockID, 7,new int[]{mod_TFC_Core.terraStoneIgIn.blockID,-1,mod_TFC_Core.terraStoneSed.blockID,-1},//Granite Veins
                /*rarity*/120,/*veinSize*/80,/*veinAmt*/45,/*height*/height,/*diameter*/100,/*vDensity*/50,/*hDensity*/60,         world, rand, chunkX, chunkZ, min, max);

        //============Garnierite
        createOreVein(mod_TFC_Core.terraOre.blockID, 8,new int[]{mod_TFC_Core.terraStoneIgIn.blockID,2},//Gabbro Veins
                /*rarity*/160,/*veinSize*/40,/*veinAmt*/35,/*height*/height,/*diameter*/100,/*vDensity*/50,/*hDensity*/10,         world, rand, chunkX, chunkZ, min, max);

        //============Malachite
        createOreVein(mod_TFC_Core.terraOre.blockID, 9,new int[]{mod_TFC_Core.terraStoneSed.blockID,5,mod_TFC_Core.terraStoneMM.blockID,5},//limestone and marble veins
                /*rarity*/140,/*veinSize*/80,/*veinAmt*/45,/*height*/height,/*diameter*/100,/*vDensity*/50,/*hDensity*/20,         world, rand, chunkX, chunkZ, min, max);

        //============Magnetite
        createOreVein(mod_TFC_Core.terraOre.blockID, 10,new int[]{mod_TFC_Core.terraStoneSed.blockID,-1},//Sedimentary, Large Cluster
                /*rarity*/180,/*veinSize*/80,/*veinAmt*/36,/*height*/height,/*diameter*/100,/*vDensity*/50,/*hDensity*/40,         world, rand, chunkX, chunkZ, min, max);

        //============Limonite
        createOreVein(mod_TFC_Core.terraOre.blockID, 11,new int[]{mod_TFC_Core.terraStoneSed.blockID,-1},//Sedimentary, Large Cluster
                /*rarity*/180,/*veinSize*/85,/*veinAmt*/40,/*height*/height,/*diameter*/100,/*vDensity*/50,/*hDensity*/40,         world, rand, chunkX, chunkZ, min, max);

        //============Sphalerite
        createOreVein(mod_TFC_Core.terraOre.blockID, 12,new int[]{mod_TFC_Core.terraStoneMM.blockID,-1},//mm, veins
                /*rarity*/140,/*veinSize*/80,/*veinAmt*/38,/*height*/height,/*diameter*/100,/*vDensity*/60,/*hDensity*/40,         world, rand, chunkX, chunkZ, min, max);

        //============Tetrahedrite
        createOreVein(mod_TFC_Core.terraOre.blockID, 13,new int[]{mod_TFC_Core.terraStoneIgEx.blockID,-1,mod_TFC_Core.terraStoneMM.blockID,-1,
                mod_TFC_Core.terraStoneIgIn.blockID,-1,mod_TFC_Core.terraStoneSed.blockID,-1},//everything, veins
                /*rarity*/120,/*veinSize*/85,/*veinAmt*/45,/*height*/height,/*diameter*/100,/*vDensity*/50,/*hDensity*/30,         world, rand, chunkX, chunkZ, min, max);

        //============Bituminous Coal
        createOre(mod_TFC_Core.terraOre.blockID, 14,new int[]{mod_TFC_Core.terraStoneSed.blockID,-1},//sedimentary, veins
                /*rarity*/80,/*veinSize*/80,/*veinAmt*/60,/*height*/height,/*diameter*/200,/*vDensity*/60,/*hDensity*/80,         world, rand, chunkX, chunkZ, min, max);

        //============Lignite
        createOre(mod_TFC_Core.terraOre.blockID, 15,new int[]{mod_TFC_Core.terraStoneSed.blockID,-1},//sedimentary, veins
                /*rarity*/80,/*veinSize*/80,/*veinAmt*/60,/*height*/height,/*diameter*/200,/*vDensity*/10,/*hDensity*/80,         world, rand, chunkX, chunkZ, min, max);

        //        //============Kaolinite
        //        createOre(mod_TFC_Core.terraOre2.blockID, 0,new int[]{mod_TFC_Core.terraStoneSed.blockID,-1},//sedimentary, large clusters
        //                /*rarity*/60,/*veinSize*/40,/*veinAmt*/2,/*height*/height,/*diameter*/40,/*vDensity*/50,/*hDensity*/90,         world, rand, chunkX, chunkZ, min, max);

        //============Gypsum
        createOre(mod_TFC_Core.terraOre2.blockID, 1,new int[]{mod_TFC_Core.terraStoneSed.blockID,-1},//sedimentary, large clusters
                /*rarity*/110,/*veinSize*/40,/*veinAmt*/20,/*height*/height,/*diameter*/40,/*vDensity*/50,/*hDensity*/90,         world, rand, chunkX, chunkZ, min, max);

        //============Satinspar
        createOreVein(mod_TFC_Core.terraOre2.blockID, 2,new int[]{mod_TFC_Core.terraOre2.blockID,8},//gypsum, small clusters
                /*rarity*/2,/*veinSize*/6,/*veinAmt*/20,/*height*/height,/*diameter*/40,/*vDensity*/40,/*hDensity*/40,         world, rand, chunkX, chunkZ, min, max);

        //============Selenite
        createOreVein(mod_TFC_Core.terraOre2.blockID, 3,new int[]{mod_TFC_Core.terraOre2.blockID,8},//gypsum, small clusters
                /*rarity*/2,/*veinSize*/6,/*veinAmt*/20,/*height*/height,/*diameter*/40,/*vDensity*/40,/*hDensity*/40,         world, rand, chunkX, chunkZ, min, max);

        //============Graphite
        createOreVein(mod_TFC_Core.terraOre2.blockID, 4,new int[]{mod_TFC_Core.terraStoneMM.blockID,4,mod_TFC_Core.terraStoneMM.blockID,0,
                mod_TFC_Core.terraStoneMM.blockID,5, mod_TFC_Core.terraStoneMM.blockID,3},//gneiss, quartzite, marble, schist, small clusters
                /*rarity*/80,/*veinSize*/6,/*veinAmt*/24,/*height*/height,/*diameter*/40,/*vDensity*/40,/*hDensity*/40,         world, rand, chunkX, chunkZ, min, max);

        //============Kimberlite
        createOreVein(mod_TFC_Core.terraOre2.blockID, 5,new int[]{mod_TFC_Core.terraStoneIgIn.blockID,2},//Gabbro, large clusters
                /*rarity*/200,/*veinSize*/40,/*veinAmt*/20,/*height*/height,/*diameter*/40,/*vDensity*/40,/*hDensity*/90,         world, rand, chunkX, chunkZ, min, max);

        //============Petrified Wood
        createOre(mod_TFC_Core.terraOre2.blockID, 6,new int[]{mod_TFC_Core.terraStoneSed.blockID,-1},//Sedimentary, small clusters 
                /*rarity*/200,/*veinSize*/10,/*veinAmt*/5,/*height*/height,/*diameter*/20,/*vDensity*/10,/*hDensity*/40,         world, rand, chunkX, chunkZ, min, max);

        //============Sulfur
        //      createOre(mod_TFCraft.terraOre.blockID, 14,new int[]{mod_TFCraft.terraStoneIgEx.blockID,-1,mod_TFCraft.terraOre2.blockID,8},//igex, gypsum small clusters
        //              /*rarity*/4,/*veinSize*/6,/*veinAmt*/10,/*height*/128,/*diameter*/40,/*vDensity*/40,/*hDensity*/40,         world, rand, chunkX, chunkZ);

        //============Jet
        createOre(mod_TFC_Core.terraOre2.blockID, 8,new int[]{mod_TFC_Core.terraStoneSed.blockID,-1},//Sedimentary, med clusters 
                /*rarity*/100,/*veinSize*/30,/*veinAmt*/10,/*height*/height,/*diameter*/40,/*vDensity*/60,/*hDensity*/60,         world, rand, chunkX, chunkZ, min, max);

        //============Microcline
        //        createOre(mod_TFC_Core.terraOre2.blockID, 9,new int[]{mod_TFC_Core.terraStoneIgIn.blockID,0},//granite, large clusters 
        //                /*rarity*/45,/*veinSize*/64,/*veinAmt*/2,/*height*/height,/*diameter*/50,/*vDensity*/10,/*hDensity*/40,         world, rand, chunkX, chunkZ, min, max);

        //============Pitchblende
        createOre(mod_TFC_Core.terraOre2.blockID, 10,new int[]{mod_TFC_Core.terraStoneIgIn.blockID,0},//granite, small clusters 
                /*rarity*/100,/*veinSize*/10,/*veinAmt*/10,/*height*/height,/*diameter*/50,/*vDensity*/10,/*hDensity*/40,         world, rand, chunkX, chunkZ, min, max);

        //============Cinnabar
        createOreVein(mod_TFC_Core.terraOre2.blockID, 11,new int[]{mod_TFC_Core.terraStoneIgEx.blockID,-1,mod_TFC_Core.terraStoneSed.blockID,2,
                mod_TFC_Core.terraStoneMM.blockID,0},//igex, shale, quartzite small clusters
                /*rarity*/60,/*veinSize*/35,/*veinAmt*/30,/*height*/height,/*diameter*/50,/*vDensity*/40,/*hDensity*/40,         world, rand, chunkX, chunkZ, min, max);

        //============Cryolite
        createOre(mod_TFC_Core.terraOre2.blockID, 12,new int[]{mod_TFC_Core.terraStoneIgIn.blockID,0},//granite, small clusters 
                /*rarity*/100,/*veinSize*/10,/*veinAmt*/20,/*height*/height,/*diameter*/50,/*vDensity*/10,/*hDensity*/40,         world, rand, chunkX, chunkZ, min, max);

        //============Saltpeter
        createOre(mod_TFC_Core.terraOre2.blockID, 13,new int[]{mod_TFC_Core.terraStoneSed.blockID,-1},//sed, small clusters 
                /*rarity*/100,/*veinSize*/10,/*veinAmt*/20,/*height*/height,/*diameter*/50,/*vDensity*/10,/*hDensity*/40,         world, rand, chunkX, chunkZ, min, max);

        //============Olivine(Out of Order) must come before serpentine
        createOre(mod_TFC_Core.terraOre3.blockID, 1,new int[]{mod_TFC_Core.terraStoneIgIn.blockID,2},//gabbro, large clusters 
                /*rarity*/80,/*veinSize*/30,/*veinAmt*/14,/*height*/height,/*diameter*/50,/*vDensity*/10,/*hDensity*/60,         world, rand, chunkX, chunkZ, min, max);

        //============Serpentine
        createOre(mod_TFC_Core.terraOre2.blockID, 14,new int[]{mod_TFC_Core.terraOre3.blockID,8},//Olivine, small clusters 
                /*rarity*/2,/*veinSize*/10,/*veinAmt*/8,/*height*/height,/*diameter*/50,/*vDensity*/10,/*hDensity*/60,         world, rand, chunkX, chunkZ, min, max);

        //============Sylvite
        createOre(mod_TFC_Core.terraOre2.blockID, 15,new int[]{mod_TFC_Core.terraStoneSed.blockID,4},//Rock Salt, large clusters 
                /*rarity*/80,/*veinSize*/40,/*veinAmt*/14,/*height*/height,/*diameter*/50,/*vDensity*/10,/*hDensity*/60,         world, rand, chunkX, chunkZ, min, max);

        //============Borax
        createOre(mod_TFC_Core.terraOre3.blockID, 0,new int[]{mod_TFC_Core.terraStoneSed.blockID,4},//Rock Salt, large clusters 
                /*rarity*/50,/*veinSize*/50,/*veinAmt*/24,/*height*/height,/*diameter*/200,/*vDensity*/50,/*hDensity*/60,         world, rand, chunkX, chunkZ, min, max);
        createOre(mod_TFC_Core.terraOre3.blockID, 0,new int[]{mod_TFC_Core.terraOre2.blockID,8},//Gypsum, small clusters 
                /*rarity*/3,/*veinSize*/12,/*veinAmt*/22,/*height*/height,/*diameter*/200,/*vDensity*/40,/*hDensity*/40,         world, rand, chunkX, chunkZ, min, max);
        //============Lapis Lazuli
        createOre(mod_TFC_Core.terraOre3.blockID, 2,new int[]{mod_TFC_Core.terraStoneMM.blockID,5},//Marble, small clusters 
                /*rarity*/90,/*veinSize*/20,/*veinAmt*/26,/*height*/height,/*diameter*/60,/*vDensity*/40,/*hDensity*/40,         world, rand, chunkX, chunkZ, min, max);

        //============Platinum -- (out of order) must follow magnetite and olivine
        createOre(mod_TFC_Core.terraOre.blockID, 2,new int[]{mod_TFC_Core.terraOre.blockID,1,mod_TFC_Core.terraOre3.blockID,8},//magnetite, veins
                /*rarity*/10,/*veinSize*/8,/*veinAmt*/10,/*height*/height,/*diameter*/25,/*vDensity*/60,/*hDensity*/40,         world, rand, chunkX, chunkZ, min, max);

        //============Gravel
        createOre(Block.gravel.blockID, 0,new int[]{mod_TFC_Core.terraDirt.blockID,-1,
                mod_TFC_Core.terraDirt2.blockID,-1,mod_TFC_Core.terraGrass.blockID,-1,mod_TFC_Core.terraGrass2.blockID,-1},//Everywhere, Clusters
                /*rarity*/12,/*veinSize*/40,/*veinAmt*/5,/*height*/height,/*diameter*/40,/*vDensity*/10,/*hDensity*/60,         world, rand, chunkX, chunkZ, min, max);
    }

    public static void GenerateLooseRocks(World currentWorld, Random randomGenerator, int chunk_X, int chunk_Z)
    {
        BiomeGenBase biome = currentWorld.getBiomeGenForCoords(chunk_X, chunk_Z);


        for (int var2 = 0; var2 < ((BiomeDecoratorTFC)biome.biomeDecorator).looseRocksPerChunk; var2++)
        {
            int var7 = chunk_X + randomGenerator.nextInt(16) + 8;
            int var3 = chunk_Z + randomGenerator.nextInt(16) + 8;

            new WorldGenLooseRocks().generate(currentWorld, randomGenerator, var7, currentWorld.getTopSolidOrLiquidBlock(var7, var3)-1, var3);
        }

    }

    public static void GeneratePits(World world, Random rand, int chunkX, int chunkZ)
    {
        for (int var1 = 0; var1 < 1; ++var1)
        {
            int var2 = chunkX + rand.nextInt(16) + 8;
            int var3 = chunkZ + rand.nextInt(16) + 8;
            new WorldGenClayPit(16, world.getBiomeGenForCoords(var2, var3)).generate(world, rand, var2, world.getTopSolidOrLiquidBlock(var2, var3), var3);
        }

        for (int var1 = 0; var1 < 1; ++var1)
        {
            int var2 = chunkX + rand.nextInt(16) + 8;
            int var3 = chunkZ + rand.nextInt(16) + 8;
            new WorldGenPeatPit(24, world.getBiomeGenForCoords(var2, var3)).generate(world, rand, var2, world.getTopSolidOrLiquidBlock(var2, var3), var3);
        }
    }

    public static void GeneratePlants(World world, Random rand, int chunk_X, int chunk_Z)
    {
        BiomeGenBase biome = world.getBiomeGenForCoords(chunk_X, chunk_Z);
        WorldGenCustomFlowers plantYellowGen = new WorldGenCustomFlowers(Block.plantYellow.blockID);
        WorldGenCustomFlowers plantRedGen = new WorldGenCustomFlowers(Block.plantRed.blockID);
        WorldGenCustomFlowers mushroomBrownGen = new WorldGenCustomFlowers(Block.mushroomBrown.blockID);
        WorldGenCustomFlowers mushroomRedGen = new WorldGenCustomFlowers(Block.mushroomRed.blockID);

        WorldGenCustomFruitTree appleTree = new WorldGenCustomFruitTree(false, mod_TFC_Core.fruitTreeLeaves.blockID, 0);
        WorldGenCustomFruitTree bananaTree = new WorldGenCustomFruitTree(false, mod_TFC_Core.fruitTreeLeaves.blockID, 1);
        WorldGenCustomFruitTree orangeTree = new WorldGenCustomFruitTree(false, mod_TFC_Core.fruitTreeLeaves.blockID, 2);
        WorldGenCustomFruitTree grappleTree = new WorldGenCustomFruitTree(false, mod_TFC_Core.fruitTreeLeaves.blockID, 3);
        WorldGenCustomFruitTree lemonTree = new WorldGenCustomFruitTree(false, mod_TFC_Core.fruitTreeLeaves.blockID, 4);
        WorldGenCustomFruitTree oliveTree = new WorldGenCustomFruitTree(false, mod_TFC_Core.fruitTreeLeaves.blockID, 5);
        WorldGenCustomFruitTree cherryTree = new WorldGenCustomFruitTree(false, mod_TFC_Core.fruitTreeLeaves.blockID, 6);
        WorldGenCustomFruitTree peachTree = new WorldGenCustomFruitTree(false, mod_TFC_Core.fruitTreeLeaves.blockID, 7);

        WorldGenCustomFruitTree2 plumTree = new WorldGenCustomFruitTree2(false, mod_TFC_Core.fruitTreeLeaves2.blockID, 0);
        WorldGenCustomFruitTree2 cacaoTree = new WorldGenCustomFruitTree2(false, mod_TFC_Core.fruitTreeLeaves2.blockID, 1);

        int var2;
        int var3;
        int var4;
        int var7;

        for (var2 = 0; var2 < ((BiomeDecoratorTFC)biome.biomeDecorator).flowersPerChunk; ++var2)
        {
            var3 = chunk_X + rand.nextInt(16) + 8;
            var4 = rand.nextInt(256);
            var7 = chunk_Z + rand.nextInt(16) + 8;

            plantYellowGen.generate(world, rand, var3, var4, var7);

            if (rand.nextInt(4) == 0)
            {
                var3 = chunk_X + rand.nextInt(16) + 8;
                var4 = rand.nextInt(256);
                var7 = chunk_Z + rand.nextInt(16) + 8;
                plantRedGen.generate(world, rand, var3, var4, var7);
            }
        }

        for (var2 = 0; var2 < ((BiomeDecoratorTFC)biome.biomeDecorator).grassPerChunk; ++var2)
        {
            var3 = chunk_X + rand.nextInt(16) + 8;
            var4 = rand.nextInt(256);
            var7 = chunk_Z + rand.nextInt(16) + 8;
            WorldGenerator var6 = new WorldGenCustomTallGrass(Block.tallGrass.blockID, 1);
            var6.generate(world, rand, var3, var4, var7);
        }

        for (var2 = 0; var2 < ((BiomeDecoratorTFC)biome.biomeDecorator).mushroomsPerChunk; ++var2)
        {
            if (rand.nextInt(4) == 0)
            {
                var3 = chunk_X + rand.nextInt(16) + 8;
                var4 = chunk_Z + rand.nextInt(16) + 8;
                var7 = world.getHeightValue(var3, var4);
                mushroomBrownGen.generate(world, rand, var3, var7, var4);
            }

            if (rand.nextInt(8) == 0)
            {
                var3 = chunk_X + rand.nextInt(16) + 8;
                var4 = chunk_Z + rand.nextInt(16) + 8;
                var7 = rand.nextInt(128);
                mushroomRedGen.generate(world, rand, var3, var7, var4);
            }
        }

        if (rand.nextInt(4) == 0)
        {
            var2 = chunk_X + rand.nextInt(16) + 8;
            var3 = rand.nextInt(256);
            var4 = chunk_Z + rand.nextInt(16) + 8;
            mushroomBrownGen.generate(world, rand, var2, var3, var4);
        }

        if (rand.nextInt(8) == 0)
        {
            var2 = chunk_X + rand.nextInt(16) + 8;
            var3 = rand.nextInt(256);
            var4 = chunk_Z + rand.nextInt(16) + 8;
            mushroomRedGen.generate(world, rand, var2, var3, var4);
        }

        if (rand.nextInt(50) == 0 && biome.temperature >= 18)
        {
            var2 = chunk_X + rand.nextInt(16) + 8;

            var4 = chunk_Z + rand.nextInt(16) + 8;

            var3 = world.getTopSolidOrLiquidBlock(var2, var4);
            switch(rand.nextInt(9))
            {
                default:
                {
                    if(world.getBlockId(var2, var3, var4) == 0 && (world.getBlockId(var2, var3-1, var4) == mod_TFC_Core.terraGrass.blockID || world.getBlockId(var2, var3-1, var4) == mod_TFC_Core.terraGrass2.blockID))
                        appleTree.generate(world, rand, var2, var3, var4);
                    break;
                }
                case 1:
                {
                    if(world.getBlockId(var2, var3, var4) == 0 && (world.getBlockId(var2, var3-1, var4) == mod_TFC_Core.terraGrass.blockID || world.getBlockId(var2, var3-1, var4) == mod_TFC_Core.terraGrass2.blockID))
                        bananaTree.generate(world, rand, var2, var3, var4);
                    break;
                }
                case 2:
                {
                    if(world.getBlockId(var2, var3, var4) == 0 && (world.getBlockId(var2, var3-1, var4) == mod_TFC_Core.terraGrass.blockID || world.getBlockId(var2, var3-1, var4) == mod_TFC_Core.terraGrass2.blockID))
                        orangeTree.generate(world, rand, var2, var3, var4);
                    break;
                }
                case 3:
                {
                    if(world.getBlockId(var2, var3, var4) == 0 && (world.getBlockId(var2, var3-1, var4) == mod_TFC_Core.terraGrass.blockID || world.getBlockId(var2, var3-1, var4) == mod_TFC_Core.terraGrass2.blockID))
                        grappleTree.generate(world, rand, var2, var3, var4);
                    break;
                }
                case 4:
                {
                    if(world.getBlockId(var2, var3, var4) == 0 && (world.getBlockId(var2, var3-1, var4) == mod_TFC_Core.terraGrass.blockID || world.getBlockId(var2, var3-1, var4) == mod_TFC_Core.terraGrass2.blockID))
                        lemonTree.generate(world, rand, var2, var3, var4);
                    break;
                }
                case 5:
                {
                    if(world.getBlockId(var2, var3, var4) == 0 && (world.getBlockId(var2, var3-1, var4) == mod_TFC_Core.terraGrass.blockID || world.getBlockId(var2, var3-1, var4) == mod_TFC_Core.terraGrass2.blockID))
                        oliveTree.generate(world, rand, var2, var3, var4);
                    break;
                }
                case 6:
                {
                    if(world.getBlockId(var2, var3, var4) == 0 && (world.getBlockId(var2, var3-1, var4) == mod_TFC_Core.terraGrass.blockID || world.getBlockId(var2, var3-1, var4) == mod_TFC_Core.terraGrass2.blockID))
                        cherryTree.generate(world, rand, var2, var3, var4);
                    break;
                }
                case 7:
                {
                    if(world.getBlockId(var2, var3, var4) == 0 && (world.getBlockId(var2, var3-1, var4) == mod_TFC_Core.terraGrass.blockID || world.getBlockId(var2, var3-1, var4) == mod_TFC_Core.terraGrass2.blockID))
                        peachTree.generate(world, rand, var2, var3, var4);
                    break;
                }
                case 8:
                {
                    if(world.getBlockId(var2, var3, var4) == 0 && (world.getBlockId(var2, var3-1, var4) == mod_TFC_Core.terraGrass.blockID || world.getBlockId(var2, var3-1, var4) == mod_TFC_Core.terraGrass2.blockID))
                        plumTree.generate(world, rand, var2, var3, var4);
                    break;
                }
                //                case 9:
                //                {
                //                    if(world.getBlockId(var2, var3, var4) == 0 && (world.getBlockId(var2, var3-1, var4) == mod_TFC_Core.terraGrass.blockID || world.getBlockId(var2, var3-1, var4) == mod_TFC_Core.terraGrass2.blockID))
                //                        cacaoTree.generate(world, rand, var2, var3, var4);
                //                    break;
                //                }
            }
        }
    }

    static Boolean isBlockAboveSolid(IBlockAccess blockAccess, int i, int j, int k)
    {
        if(ServerClientProxy.getProxy().getCurrentWorld().isBlockOpaqueCube(i, j+1, k)) {
            return true;
        }

        return false;
    }

    public static ItemStack RandomGem(Random random, int rockType)
    {
        ItemStack is = null;
        if(random.nextInt(500) == 0)
        {
            ArrayList items = new ArrayList<ItemStack>();
            items.add(new ItemStack(TFCItems.terraGemAgate,1,0));
            items.add(new ItemStack(TFCItems.terraGemAmethyst,1,0));
            items.add(new ItemStack(TFCItems.terraGemBeryl,1,0));
            items.add(new ItemStack(TFCItems.terraGemEmerald,1,0));
            items.add(new ItemStack(TFCItems.terraGemGarnet,1,0));
            items.add(new ItemStack(TFCItems.terraGemJade,1,0));
            items.add(new ItemStack(TFCItems.terraGemJasper,1,0));
            items.add(new ItemStack(TFCItems.terraGemOpal,1,0));
            items.add(new ItemStack(TFCItems.terraGemRuby,1,0));
            items.add(new ItemStack(TFCItems.terraGemSapphire,1,0));
            items.add(new ItemStack(TFCItems.terraGemTourmaline,1,0));
            items.add(new ItemStack(TFCItems.terraGemTopaz,1,0));

            is = (ItemStack)items.toArray()[random.nextInt(items.toArray().length)];

        }
        else if(random.nextInt(1000) == 0)
        {
            ArrayList items = new ArrayList<ItemStack>();
            items.add(new ItemStack(TFCItems.terraGemAgate,1,1));
            items.add(new ItemStack(TFCItems.terraGemAmethyst,1,1));
            items.add(new ItemStack(TFCItems.terraGemBeryl,1,1));
            items.add(new ItemStack(TFCItems.terraGemEmerald,1,1));
            items.add(new ItemStack(TFCItems.terraGemGarnet,1,1));
            items.add(new ItemStack(TFCItems.terraGemJade,1,1));
            items.add(new ItemStack(TFCItems.terraGemJasper,1,1));
            items.add(new ItemStack(TFCItems.terraGemOpal,1,1));
            items.add(new ItemStack(TFCItems.terraGemRuby,1,1));
            items.add(new ItemStack(TFCItems.terraGemSapphire,1,1));
            items.add(new ItemStack(TFCItems.terraGemTourmaline,1,1));
            items.add(new ItemStack(TFCItems.terraGemTopaz,1,1));

            is = (ItemStack)items.toArray()[random.nextInt(items.toArray().length)];
        }
        else if(random.nextInt(2000) == 0)
        {
            ArrayList items = new ArrayList<ItemStack>();
            items.add(new ItemStack(TFCItems.terraGemAgate,1,2));
            items.add(new ItemStack(TFCItems.terraGemAmethyst,1,2));
            items.add(new ItemStack(TFCItems.terraGemBeryl,1,2));
            items.add(new ItemStack(TFCItems.terraGemEmerald,1,2));
            items.add(new ItemStack(TFCItems.terraGemGarnet,1,2));
            items.add(new ItemStack(TFCItems.terraGemJade,1,2));
            items.add(new ItemStack(TFCItems.terraGemJasper,1,2));
            items.add(new ItemStack(TFCItems.terraGemOpal,1,2));
            items.add(new ItemStack(TFCItems.terraGemRuby,1,2));
            items.add(new ItemStack(TFCItems.terraGemSapphire,1,2));
            items.add(new ItemStack(TFCItems.terraGemTourmaline,1,2));
            items.add(new ItemStack(TFCItems.terraGemTopaz,1,2));

            is = (ItemStack)items.toArray()[random.nextInt(items.toArray().length)];
        }
        else if(random.nextInt(4000) == 0)
        {
            ArrayList items = new ArrayList<ItemStack>();
            items.add(new ItemStack(TFCItems.terraGemAgate,1,3));
            items.add(new ItemStack(TFCItems.terraGemAmethyst,1,3));
            items.add(new ItemStack(TFCItems.terraGemBeryl,1,3));
            items.add(new ItemStack(TFCItems.terraGemEmerald,1,3));
            items.add(new ItemStack(TFCItems.terraGemGarnet,1,3));
            items.add(new ItemStack(TFCItems.terraGemJade,1,3));
            items.add(new ItemStack(TFCItems.terraGemJasper,1,3));
            items.add(new ItemStack(TFCItems.terraGemOpal,1,3));
            items.add(new ItemStack(TFCItems.terraGemRuby,1,3));
            items.add(new ItemStack(TFCItems.terraGemSapphire,1,3));
            items.add(new ItemStack(TFCItems.terraGemTourmaline,1,3));
            items.add(new ItemStack(TFCItems.terraGemTopaz,1,3));

            is = (ItemStack)items.toArray()[random.nextInt(items.toArray().length)];
        }
        else if(random.nextInt(8000) == 0)
        {
            ArrayList items = new ArrayList<ItemStack>();
            items.add(new ItemStack(TFCItems.terraGemAgate,1,4));
            items.add(new ItemStack(TFCItems.terraGemAmethyst,1,4));
            items.add(new ItemStack(TFCItems.terraGemBeryl,1,4));
            items.add(new ItemStack(TFCItems.terraGemEmerald,1,4));
            items.add(new ItemStack(TFCItems.terraGemGarnet,1,4));
            items.add(new ItemStack(TFCItems.terraGemJade,1,4));
            items.add(new ItemStack(TFCItems.terraGemJasper,1,4));
            items.add(new ItemStack(TFCItems.terraGemOpal,1,4));
            items.add(new ItemStack(TFCItems.terraGemRuby,1,4));
            items.add(new ItemStack(TFCItems.terraGemSapphire,1,4));
            items.add(new ItemStack(TFCItems.terraGemTourmaline,1,4));
            items.add(new ItemStack(TFCItems.terraGemTopaz,1,4));

            is = (ItemStack)items.toArray()[random.nextInt(items.toArray().length)];

        }
        return is;
    }

    public static void RegisterRecipes()
    {
        Item[] Ingots = {TFCItems.BismuthIngot, TFCItems.BismuthBronzeIngot,TFCItems.BlackBronzeIngot,
                TFCItems.BlackSteelIngot,TFCItems.BlueSteelIngot,TFCItems.BrassIngot,TFCItems.BronzeIngot,
                TFCItems.BronzeIngot,TFCItems.CopperIngot,TFCItems.GoldIngot,TFCItems.WroughtIronIngot,TFCItems.LeadIngot
                ,TFCItems.NickelIngot,TFCItems.PigIronIngot,TFCItems.PlatinumIngot,TFCItems.RedSteelIngot,
                TFCItems.RoseGoldIngot,TFCItems.SilverIngot,TFCItems.SteelIngot,TFCItems.SterlingSilverIngot
                ,TFCItems.TinIngot,TFCItems.ZincIngot};


        //jimmynator's javelin
        ModLoader.addRecipe(new ItemStack(TFCItems.Javelin, 1), new Object[] { 
            "1","2", Character.valueOf('1'), new ItemStack(TFCItems.LooseRock, 1, -1),Character.valueOf('2'), new ItemStack(Item.stick,1,-1)});
        ModLoader.addShapelessRecipe(new ItemStack(Item.arrow, 8), new Object[] { 
             new ItemStack(TFCItems.LooseRock, 1, -1), new ItemStack(Item.stick,1,-1),
             new ItemStack(Item.feather,1,-1)});

        //stone picks

        ModLoader.addRecipe(new ItemStack(TFCItems.terraIgInPick, 1, 0), new Object[] { 
            "1","2", Character.valueOf('1'), TFCItems.IgInStonePickaxeHead,Character.valueOf('2'), new ItemStack(Item.stick,1,-1)});
        ModLoader.addRecipe(new ItemStack(TFCItems.terraSedPick, 1, 0), new Object[] { 
            "1","2", Character.valueOf('1'), TFCItems.SedStonePickaxeHead,Character.valueOf('2'), new ItemStack(Item.stick,1,-1)});
        ModLoader.addRecipe(new ItemStack(TFCItems.terraIgExPick, 1, 0), new Object[] { 
            "1","2", Character.valueOf('1'), TFCItems.IgExStonePickaxeHead,Character.valueOf('2'), new ItemStack(Item.stick,1,-1)});
        ModLoader.addRecipe(new ItemStack(TFCItems.terraMMPick, 1, 0), new Object[] { 
            "1","2", Character.valueOf('1'), TFCItems.MMStonePickaxeHead,Character.valueOf('2'), new ItemStack(Item.stick,1,-1)});
        //stone shovels
        ModLoader.addRecipe(new ItemStack(TFCItems.terraIgInShovel, 1, 0), new Object[] { 
            "1","2", Character.valueOf('1'), TFCItems.IgInStoneShovelHead,Character.valueOf('2'), new ItemStack(Item.stick,1,-1)});
        ModLoader.addRecipe(new ItemStack(TFCItems.terraSedShovel, 1, 0), new Object[] { 
            "1","2", Character.valueOf('1'), TFCItems.SedStoneShovelHead,Character.valueOf('2'), new ItemStack(Item.stick,1,-1)});
        ModLoader.addRecipe(new ItemStack(TFCItems.terraIgExShovel, 1, 0), new Object[] { 
            "1","2", Character.valueOf('1'), TFCItems.IgExStoneShovelHead,Character.valueOf('2'), new ItemStack(Item.stick,1,-1)});
        ModLoader.addRecipe(new ItemStack(TFCItems.terraMMShovel, 1, 0), new Object[] { 
            "1","2", Character.valueOf('1'), TFCItems.MMStoneShovelHead,Character.valueOf('2'), new ItemStack(Item.stick,1,-1)});
        //stone axes
        ModLoader.addRecipe(new ItemStack(TFCItems.terraIgInAxe, 1, 0), new Object[] { 
            "1","2", Character.valueOf('1'), TFCItems.IgInStoneAxeHead,Character.valueOf('2'), new ItemStack(Item.stick,1,-1)});
        ModLoader.addRecipe(new ItemStack(TFCItems.terraSedAxe, 1, 0), new Object[] { 
            "1","2", Character.valueOf('1'), TFCItems.SedStoneAxeHead,Character.valueOf('2'), new ItemStack(Item.stick,1,-1)});
        ModLoader.addRecipe(new ItemStack(TFCItems.terraIgExAxe, 1, 0), new Object[] { 
            "1","2", Character.valueOf('1'), TFCItems.IgExStoneAxeHead,Character.valueOf('2'), new ItemStack(Item.stick,1,-1)});
        ModLoader.addRecipe(new ItemStack(TFCItems.terraMMAxe, 1, 0), new Object[] { 
            "1","2", Character.valueOf('1'), TFCItems.MMStoneAxeHead,Character.valueOf('2'), new ItemStack(Item.stick,1,-1)});
        //stone hoes
        ModLoader.addRecipe(new ItemStack(TFCItems.terraIgInHoe, 1, 0), new Object[] { 
            "1","2", Character.valueOf('1'), TFCItems.IgInStoneHoeHead,Character.valueOf('2'), new ItemStack(Item.stick,1,-1)});
        ModLoader.addRecipe(new ItemStack(TFCItems.terraSedHoe, 1, 0), new Object[] { 
            "1","2", Character.valueOf('1'), TFCItems.SedStoneHoeHead,Character.valueOf('2'), new ItemStack(Item.stick,1,-1)});
        ModLoader.addRecipe(new ItemStack(TFCItems.terraIgExHoe, 1, 0), new Object[] { 
            "1","2", Character.valueOf('1'), TFCItems.IgExStoneHoeHead,Character.valueOf('2'), new ItemStack(Item.stick,1,-1)});
        ModLoader.addRecipe(new ItemStack(TFCItems.terraMMHoe, 1, 0), new Object[] { 
            "1","2", Character.valueOf('1'), TFCItems.MMStoneHoeHead,Character.valueOf('2'), new ItemStack(Item.stick,1,-1)});
        //bone pick
        ModLoader.addRecipe(new ItemStack(TFCItems.terraIgInPick, 1, 0), new Object[] { 
            "1","2", Character.valueOf('1'), TFCItems.IgInStonePickaxeHead,Character.valueOf('2'), new ItemStack(Item.bone,1,-1)});
        ModLoader.addRecipe(new ItemStack(TFCItems.terraSedPick, 1, 0), new Object[] { 
            "1","2", Character.valueOf('1'), TFCItems.SedStonePickaxeHead,Character.valueOf('2'), new ItemStack(Item.bone,1,-1)});
        ModLoader.addRecipe(new ItemStack(TFCItems.terraIgExPick, 1, 0), new Object[] { 
            "1","2", Character.valueOf('1'), TFCItems.IgExStonePickaxeHead,Character.valueOf('2'), new ItemStack(Item.bone,1,-1)});
        ModLoader.addRecipe(new ItemStack(TFCItems.terraMMPick, 1, 0), new Object[] { 
            "1","2", Character.valueOf('1'), TFCItems.MMStonePickaxeHead,Character.valueOf('2'), new ItemStack(Item.bone,1,-1)});
        //bone shovels
        ModLoader.addRecipe(new ItemStack(TFCItems.terraIgInShovel, 1, 0), new Object[] { 
            "1","2", Character.valueOf('1'), TFCItems.IgInStoneShovelHead,Character.valueOf('2'), new ItemStack(Item.bone,1,-1)});
        ModLoader.addRecipe(new ItemStack(TFCItems.terraSedShovel, 1, 0), new Object[] { 
            "1","2", Character.valueOf('1'), TFCItems.SedStoneShovelHead,Character.valueOf('2'), new ItemStack(Item.bone,1,-1)});
        ModLoader.addRecipe(new ItemStack(TFCItems.terraIgExShovel, 1, 0), new Object[] { 
            "1","2", Character.valueOf('1'), TFCItems.IgExStoneShovelHead,Character.valueOf('2'), new ItemStack(Item.bone,1,-1)});
        ModLoader.addRecipe(new ItemStack(TFCItems.terraMMShovel, 1, 0), new Object[] { 
            "1","2", Character.valueOf('1'), TFCItems.MMStoneShovelHead,Character.valueOf('2'), new ItemStack(Item.bone,1,-1)});
        //bone axes
        ModLoader.addRecipe(new ItemStack(TFCItems.terraIgInAxe, 1, 0), new Object[] { 
            "1","2", Character.valueOf('1'), TFCItems.IgInStoneAxeHead,Character.valueOf('2'), new ItemStack(Item.bone,1,-1)});
        ModLoader.addRecipe(new ItemStack(TFCItems.terraSedAxe, 1, 0), new Object[] { 
            "1","2", Character.valueOf('1'), TFCItems.SedStoneAxeHead,Character.valueOf('2'), new ItemStack(Item.bone,1,-1)});
        ModLoader.addRecipe(new ItemStack(TFCItems.terraIgExAxe, 1, 0), new Object[] { 
            "1","2", Character.valueOf('1'), TFCItems.IgExStoneAxeHead,Character.valueOf('2'), new ItemStack(Item.bone,1,-1)});
        ModLoader.addRecipe(new ItemStack(TFCItems.terraMMAxe, 1, 0), new Object[] { 
            "1","2", Character.valueOf('1'), TFCItems.MMStoneAxeHead,Character.valueOf('2'), new ItemStack(Item.bone,1,-1)});
        //bone hoes
        ModLoader.addRecipe(new ItemStack(TFCItems.terraIgInHoe, 1, 0), new Object[] { 
            "1","2", Character.valueOf('1'), TFCItems.IgInStoneHoeHead,Character.valueOf('2'), new ItemStack(Item.bone,1,-1)});
        ModLoader.addRecipe(new ItemStack(TFCItems.terraSedHoe, 1, 0), new Object[] { 
            "1","2", Character.valueOf('1'), TFCItems.SedStoneHoeHead,Character.valueOf('2'), new ItemStack(Item.bone,1,-1)});
        ModLoader.addRecipe(new ItemStack(TFCItems.terraIgExHoe, 1, 0), new Object[] { 
            "1","2", Character.valueOf('1'), TFCItems.IgExStoneHoeHead,Character.valueOf('2'), new ItemStack(Item.bone,1,-1)});
        ModLoader.addRecipe(new ItemStack(TFCItems.terraMMHoe, 1, 0), new Object[] { 
            "1","2", Character.valueOf('1'), TFCItems.MMStoneHoeHead,Character.valueOf('2'), new ItemStack(Item.bone,1,-1)});

        ModLoader.addRecipe(new ItemStack(TFCItems.StoneHammer, 1, 0), new Object[] { 
            "1","2", Character.valueOf('1'), TFCItems.StoneHammerHead,Character.valueOf('2'), new ItemStack(Item.stick,1,-1)});

        ModLoader.addRecipe(new ItemStack(TFCItems.StoneKnife, 1, 0), new Object[] { 
            "1","2", Character.valueOf('1'), TFCItems.StoneKnifeHead,Character.valueOf('2'), new ItemStack(Item.stick,1,-1)});

        //        ModLoader.addRecipe(new ItemStack(TFCItems.terraProPickStone, 1, 0), new Object[] { 
        //            "1","2", Character.valueOf('1'), TFCItems.StoneProPickHead,Character.valueOf('2'), new ItemStack(Item.stick,1,-1)});

//        ModLoader.addRecipe(new ItemStack(TFCItems.FlintPaxel, 1, 0), new Object[] { 
//            "1","2", Character.valueOf('1'), Item.flint,Character.valueOf('2'), new ItemStack(Item.stick,1,-1)});

        ModLoader.addRecipe(new ItemStack(TFCItems.WoodenBucketEmpty, 1), new Object[] { "w w","w w"," w ", Character.valueOf('w'), new ItemStack(TFCItems.SinglePlank, 1, -1) });

        /** Axe Recipes */
        for(int i = 0; i < 16; i++)
        {
            for(int j = 0; j < Axes.length; j++)
            {
                ModLoader.addShapelessRecipe(new ItemStack(TFCItems.SinglePlank, 4, i), new Object[] {new ItemStack(TFCItems.Logs, 1, i), new ItemStack(Axes[j], 1, -1)});
                ModLoader.addRecipe(new ItemStack(TFCItems.terraWoodSupportItemV, 8, i), new Object[] { "A2"," 2", Character.valueOf('2'), new ItemStack(TFCItems.Logs,1,i), Character.valueOf('A'), new ItemStack(Axes[j], 1, -1)});
                ModLoader.addRecipe(new ItemStack(TFCItems.terraWoodSupportItemH, 8, i), new Object[] { "A ","22", Character.valueOf('2'), new ItemStack(TFCItems.Logs,1,i), Character.valueOf('A'), new ItemStack(Axes[j], 1, -1)});
            }
            for(int j = 0; j < Saws.length; j++)
            {
                ModLoader.addShapelessRecipe(new ItemStack(TFCItems.SinglePlank, 12, i), new Object[] {new ItemStack(TFCItems.Logs, 1, i), new ItemStack(Saws[j], 1, -1)});
            }
            for(int j = 0; j < Knives.length; j++)
            {
                ModLoader.addRecipe(new ItemStack(Item.bowlEmpty, 4, 0), new Object[] { 
                    "2","1", Character.valueOf('1'),new ItemStack(TFCItems.Logs,1,-1), Character.valueOf('2'),new ItemStack(Knives[j], 1, -1)});
            }
            ModLoader.addShapelessRecipe(new ItemStack(TFCItems.SinglePlank, 4, i), new Object[] {new ItemStack(TFCItems.Logs, 1, i), new ItemStack(TFCItems.FlintPaxel, 1, -1)});
            ModLoader.addRecipe(new ItemStack(Block.planks.blockID, 1, i), new Object[] {"11","11", Character.valueOf('1'), new ItemStack(TFCItems.SinglePlank, 1, i)});
        }

        //Chest
        ModLoader.addRecipe(new ItemStack(Block.chest, 1), new Object[] { "###","# #","###", Character.valueOf('#'), new ItemStack(TFCItems.SinglePlank, 1, -1)});

        //Red Stone		
        ModLoader.addRecipe(new ItemStack(Item.redstone, 8), new Object[] { 
            "1", Character.valueOf('1'),new ItemStack(TFCItems.OreChunk, 1, 28)});
        //Lapis Lazuli	
        ModLoader.addShapelessRecipe(new ItemStack(Item.dyePowder, 4,4), new Object[] {new ItemStack(TFCItems.OreChunk, 1, 34)});

        //knapping
        for(int i = 0; i < 23; i++)
        {
            ModLoader.addShapelessRecipe(new ItemStack(TFCItems.LooseRock, 1, i), new Object[] {new ItemStack(TFCItems.LooseRock, 1, i), new ItemStack(TFCItems.LooseRock, 1, i)});
        }

        for(int i = 0; i < 13; i++)
        {			
            for(int j = 0; j < 3; j++)
            {
                ModLoader.addShapelessRecipe(new ItemStack(mod_TFC_Core.terraStoneIgInBrick,1,j), 
                        new Object[] {new ItemStack(TFCItems.LooseRock,1,j),new ItemStack(Chisels[i],1,-1)});
            }

            for(int j = 3; j < 13; j++)
            {
                ModLoader.addShapelessRecipe(new ItemStack(mod_TFC_Core.terraStoneSedBrick,1,j-3), 
                        new Object[] {new ItemStack(TFCItems.LooseRock,1,j),new ItemStack(Chisels[i],1,-1)});
            }

            for(int j = 13; j < 17; j++)
            {
                ModLoader.addShapelessRecipe(new ItemStack(mod_TFC_Core.terraStoneIgExBrick,1,j-13), 
                        new Object[] {new ItemStack(TFCItems.LooseRock,1,j),new ItemStack(Chisels[i],1,-1)});
            }

            for(int j = 17; j < 23; j++)
            {
                ModLoader.addShapelessRecipe(new ItemStack(mod_TFC_Core.terraStoneMMBrick,1,j-17), 
                        new Object[] {new ItemStack(TFCItems.LooseRock,1,j),new ItemStack(Chisels[i],1,-1)});
            }
        }

        //Gold Pan
        ModLoader.addRecipe(new ItemStack(TFCItems.terraGoldPan, 1, 0), new Object[] { 
            "1", Character.valueOf('1'),Item.bowlEmpty});
        //Sluice
        ModLoader.addRecipe(new ItemStack(TFCItems.terraSluiceItem, 1), new Object[] { 
            "  1"," 12","122", Character.valueOf('1'),new ItemStack(Item.stick,1,-1), Character.valueOf('2'),new ItemStack(TFCItems.SinglePlank,1,-1)});
        
        for(int j = 0; j < TFCItems.Hammers.length; j++)
        {
            ModLoader.addShapelessRecipe(new ItemStack(TFCItems.Flux, 2), new Object[] {new ItemStack(TFCItems.LooseRock, 1, 8), new ItemStack(TFCItems.Hammers[j], 1, -1)});
            ModLoader.addShapelessRecipe(new ItemStack(TFCItems.Flux, 2), new Object[] {new ItemStack(TFCItems.LooseRock, 1, 12), new ItemStack(TFCItems.Hammers[j], 1, -1)});
            ModLoader.addShapelessRecipe(new ItemStack(TFCItems.Flux, 2), new Object[] {new ItemStack(TFCItems.LooseRock, 1, 22), new ItemStack(TFCItems.Hammers[j], 1, -1)});
            ModLoader.addShapelessRecipe(new ItemStack(TFCItems.Flux, 2), new Object[] {new ItemStack(TFCItems.LooseRock, 1, 10), new ItemStack(TFCItems.Hammers[j], 1, -1)});
            ModLoader.addShapelessRecipe(new ItemStack(TFCItems.Flux, 6), new Object[] {new ItemStack(TFCItems.OreChunk, 1, 32), new ItemStack(TFCItems.Hammers[j], 1, -1)});
        }
        
        ModLoader.addShapelessRecipe(new ItemStack(TFCItems.Flux, 4), new Object[] {new ItemStack(TFCItems.OreChunk, 1, 32), new ItemStack(TFCItems.StoneHammer, 1, -1)});

        ModLoader.addRecipe(new ItemStack(Item.redstone, 8, 0), new Object[] { "2", Character.valueOf('2'), new ItemStack(TFCItems.OreChunk,1,27)});
        ModLoader.addRecipe(new ItemStack(TFCItems.terraInk, 16, 0), new Object[] { "2", Character.valueOf('2'), new ItemStack(Item.dyePowder,1,0)});
        ModLoader.addRecipe(new ItemStack(TFCItems.terraFireStarter, 1, 0), new Object[] { "2 "," 2", Character.valueOf('2'), new ItemStack(Item.stick,1,-1)});
        ModLoader.addRecipe(new ItemStack(TFCItems.terraBellowsItem, 1, 0), new Object[] { "###","???","###", Character.valueOf('#'), new ItemStack(TFCItems.SinglePlank,1,-1), Character.valueOf('?'), Item.leather});
        //ModLoader.addRecipe(new ItemStack(TFCItems.terraStoneAnvilItem, 1, 0), new Object[] { "###"," # ","###", Character.valueOf('#'), mod_TFC_Core.terraStoneIgIn});
        //ModLoader.addRecipe(new ItemStack(TFCItems.terraStoneAnvilItem, 1, 0), new Object[] { "###"," # ","###", Character.valueOf('#'), mod_TFC_Core.terraStoneIgEx});
        ModLoader.addRecipe(new ItemStack(TFCItems.terraCopperAnvilItem, 1, 0), new Object[] { "###"," # ","###", Character.valueOf('#'), TFCItems.CopperIngot2x});
        ModLoader.addRecipe(new ItemStack(TFCItems.terraBronzeAnvilItem, 1, 0), new Object[] { "###"," # ","###", Character.valueOf('#'), TFCItems.BronzeIngot2x});
        ModLoader.addRecipe(new ItemStack(TFCItems.terraWroughtIronAnvilItem, 1, 0), new Object[] { "###"," # ","###", Character.valueOf('#'), TFCItems.WroughtIronIngot2x});
        ModLoader.addRecipe(new ItemStack(TFCItems.terraSteelAnvilItem, 1, 0), new Object[] { "###"," # ","###", Character.valueOf('#'), TFCItems.SteelIngot2x});
        ModLoader.addRecipe(new ItemStack(TFCItems.terraBlackSteelAnvilItem, 1, 0), new Object[] { "###"," # ","###", Character.valueOf('#'), TFCItems.BlackSteelIngot2x});
        ModLoader.addRecipe(new ItemStack(TFCItems.terraBlueSteelAnvilItem, 1, 0), new Object[] { "###"," # ","###", Character.valueOf('#'), TFCItems.BlueSteelIngot2x});
        ModLoader.addRecipe(new ItemStack(TFCItems.terraRedSteelAnvilItem, 1, 0), new Object[] { "###"," # ","###", Character.valueOf('#'), TFCItems.RedSteelIngot2x});
        ModLoader.addRecipe(new ItemStack(TFCItems.terraRoseGoldAnvilItem, 1, 0), new Object[] { "###"," # ","###", Character.valueOf('#'), TFCItems.RoseGoldIngot2x});
        ModLoader.addRecipe(new ItemStack(TFCItems.terraBismuthBronzeAnvilItem, 1, 0), new Object[] { "###"," # ","###", Character.valueOf('#'), TFCItems.BismuthBronzeIngot2x});
        ModLoader.addRecipe(new ItemStack(TFCItems.terraBlackBronzeAnvilItem, 1, 0), new Object[] { "###"," # ","###", Character.valueOf('#'), TFCItems.BlackBronzeIngot2x});

        ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraScribe, 1), new Object[] { " L ","#P#","###", Character.valueOf('#'), new ItemStack(TFCItems.SinglePlank,1,-1),
            Character.valueOf('P'), Item.paper,Character.valueOf('L'), Item.feather});
        ModLoader.addRecipe(new ItemStack(TFCItems.terraClayMold, 4), new Object[] { "# #","###", Character.valueOf('#'), Item.clay});

        ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraMetalTable, 1), new Object[] { "P P","PPP","PPP", Character.valueOf('P'), mod_TFC_Core.terraStoneIgEx});
        ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraMetalTable, 1), new Object[] { "P P","PPP","PPP", Character.valueOf('P'), mod_TFC_Core.terraStoneIgIn});
        ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraMetalTable, 1), new Object[] { "P P","PPP","PPP", Character.valueOf('P'), mod_TFC_Core.terraStoneSed});
        ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraMetalTable, 1), new Object[] { "P P","PPP","PPP", Character.valueOf('P'), mod_TFC_Core.terraStoneMM});

        ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBloomery, 1), new Object[] { "PPP","PKP","PPP", Character.valueOf('P'), mod_TFC_Core.terraStoneIgExBrick, Character.valueOf('K'), new ItemStack(Item.coal,1,1)});
        ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBloomery, 1), new Object[] { "PPP","PKP","PPP", Character.valueOf('P'), mod_TFC_Core.terraStoneIgInBrick, Character.valueOf('K'), new ItemStack(Item.coal,1,1)});
        ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBloomery, 1), new Object[] { "PPP","PKP","PPP", Character.valueOf('P'), mod_TFC_Core.terraStoneSedBrick, Character.valueOf('K'), new ItemStack(Item.coal,1,1)});
        ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBloomery, 1), new Object[] { "PPP","PKP","PPP", Character.valueOf('P'), mod_TFC_Core.terraStoneMMBrick, Character.valueOf('K'), new ItemStack(Item.coal,1,1)});

        ModLoader.addRecipe(new ItemStack(Block.rail, 64), new Object[] { "PsP","PsP", Character.valueOf('P'), TFCItems.WroughtIronIngot, Character.valueOf('s'), new ItemStack(Item.stick,1,-1)});
        ModLoader.addRecipe(new ItemStack(Block.railPowered, 64), new Object[] { " r ","PsP","PsP", Character.valueOf('P'), TFCItems.GoldIngot, Character.valueOf('s'), new ItemStack(Item.stick,1,-1), Character.valueOf('r'), Item.redstone});
        ModLoader.addRecipe(new ItemStack(Item.minecartEmpty, 1), new Object[] { "P P","PPP", Character.valueOf('P'), TFCItems.WroughtIronSheet});
        ModLoader.addRecipe(new ItemStack(Item.shears, 1), new Object[] { "P "," P", Character.valueOf('P'), TFCItems.WroughtIronIngot});
        ModLoader.addRecipe(new ItemStack(Block.lever, 1), new Object[] { "P","H", Character.valueOf('P'), new ItemStack(Item.stick,1,-1), Character.valueOf('H'), new ItemStack(TFCItems.LooseRock,1,-1)});
        ModLoader.addRecipe(new ItemStack(Item.doorWood, 1, 0), new Object[] { "##","##","##", Character.valueOf('#'), new ItemStack(TFCItems.SinglePlank,1,-1)});
        ModLoader.addRecipe(new ItemStack(Block.trapdoor, 1, 0), new Object[] { "###","###", Character.valueOf('#'), new ItemStack(TFCItems.SinglePlank,1,-1)});
        ModLoader.addRecipe(new ItemStack(Block.signPost, 1, 0), new Object[] { "###","###"," 1 ", Character.valueOf('#'), new ItemStack(TFCItems.SinglePlank,1,-1),Character.valueOf('1'), new ItemStack(Item.stick,1,-1)});

        VanillaRecipes();
    }

    private static void VanillaRecipes()
    {
        if(TFCSettings.enableVanillaDiamondRecipe == true)
        {
            ModLoader.addRecipe(new ItemStack(Item.diamond, 1), new Object[] {"1", Character.valueOf('1'),new ItemStack(TFCItems.terraGemDiamond,1,2)});
            ModLoader.addRecipe(new ItemStack(Item.diamond, 2), new Object[] {"1", Character.valueOf('1'),new ItemStack(TFCItems.terraGemDiamond,1,3)});
            ModLoader.addRecipe(new ItemStack(Item.diamond, 3), new Object[] {"1", Character.valueOf('1'),new ItemStack(TFCItems.terraGemDiamond,1,4)});
        }
        if(TFCSettings.enableVanillaIronRecipe == true)
        {
            ModLoader.addRecipe(new ItemStack(Item.ingotIron, 1), new Object[] {"1", Character.valueOf('1'),new ItemStack(TFCItems.WroughtIronIngot,1)});

        }
        if(TFCSettings.enableVanillaGoldRecipe == true)
        {
            ModLoader.addRecipe(new ItemStack(Item.ingotGold, 1), new Object[] {"1", Character.valueOf('1'),new ItemStack(TFCItems.GoldIngot,1)});
        }
        if(TFCSettings.enableVanillaRecipes == true)
        {
            //Terrastone to Cobblestone
            ModLoader.addRecipe(new ItemStack(Block.cobblestone, 1), new Object[] {"1", Character.valueOf('1'),mod_TFC_Core.terraStoneSedCobble});
            ModLoader.addRecipe(new ItemStack(Block.cobblestone, 1), new Object[] {"1", Character.valueOf('1'),mod_TFC_Core.terraStoneIgInCobble});
            ModLoader.addRecipe(new ItemStack(Block.cobblestone, 1), new Object[] {"1", Character.valueOf('1'),mod_TFC_Core.terraStoneIgExCobble});
            ModLoader.addRecipe(new ItemStack(Block.cobblestone, 1), new Object[] {"1", Character.valueOf('1'),mod_TFC_Core.terraStoneMMCobble});
        }

        if(TFCSettings.enableVanillaFurnaceRecipes  == true)
        {
            FurnaceRecipes.smelting().addSmelting(mod_TFC_Core.terraStoneIgIn.blockID, new ItemStack(Block.stone));
            FurnaceRecipes.smelting().addSmelting(mod_TFC_Core.terraStoneIgEx.blockID, new ItemStack(Block.stone));
            FurnaceRecipes.smelting().addSmelting(mod_TFC_Core.terraStoneSed.blockID,  new ItemStack(Block.stone));
            FurnaceRecipes.smelting().addSmelting(mod_TFC_Core.terraStoneMM.blockID,   new ItemStack(Block.stone));

            FurnaceRecipes.smelting().addSmelting(mod_TFC_Core.terraStoneIgInCobble.blockID, new ItemStack(mod_TFC_Core.terraStoneIgIn));
            FurnaceRecipes.smelting().addSmelting(mod_TFC_Core.terraStoneIgExCobble.blockID, new ItemStack(mod_TFC_Core.terraStoneIgEx));
            FurnaceRecipes.smelting().addSmelting(mod_TFC_Core.terraStoneSedCobble.blockID,  new ItemStack(mod_TFC_Core.terraStoneSed));
            FurnaceRecipes.smelting().addSmelting(mod_TFC_Core.terraStoneMMCobble.blockID,   new ItemStack(mod_TFC_Core.terraStoneMM));

            FurnaceRecipes.smelting().addSmelting(mod_TFC_Core.terraOre.blockID, 23,new ItemStack(TFCItems.CopperIngot));
            FurnaceRecipes.smelting().addSmelting(mod_TFC_Core.terraOre.blockID, 32,new ItemStack(TFCItems.CopperIngot));
            FurnaceRecipes.smelting().addSmelting(mod_TFC_Core.terraOre.blockID, 36,new ItemStack(TFCItems.CopperIngot));
            FurnaceRecipes.smelting().addSmelting(mod_TFC_Core.terraOre.blockID, 35,new ItemStack(TFCItems.ZincIngot));
            FurnaceRecipes.smelting().addSmelting(mod_TFC_Core.terraOre.blockID, 34,new ItemStack(TFCItems.WroughtIronIngot));
            FurnaceRecipes.smelting().addSmelting(mod_TFC_Core.terraOre.blockID, 33,new ItemStack(TFCItems.WroughtIronIngot));
            FurnaceRecipes.smelting().addSmelting(mod_TFC_Core.terraOre.blockID, 26,new ItemStack(TFCItems.WroughtIronIngot));
            FurnaceRecipes.smelting().addSmelting(mod_TFC_Core.terraOre.blockID, 25,new ItemStack(TFCItems.PlatinumIngot));
            FurnaceRecipes.smelting().addSmelting(mod_TFC_Core.terraOre.blockID, 24,new ItemStack(TFCItems.GoldIngot));
            FurnaceRecipes.smelting().addSmelting(mod_TFC_Core.terraOre.blockID, 31,new ItemStack(TFCItems.NickelIngot));
            FurnaceRecipes.smelting().addSmelting(mod_TFC_Core.terraOre.blockID, 29,new ItemStack(TFCItems.SilverIngot));
            FurnaceRecipes.smelting().addSmelting(mod_TFC_Core.terraOre.blockID, 27,new ItemStack(TFCItems.SilverIngot));
            FurnaceRecipes.smelting().addSmelting(mod_TFC_Core.terraOre.blockID, 30,new ItemStack(TFCItems.BismuthIngot));
            FurnaceRecipes.smelting().addSmelting(mod_TFC_Core.terraOre.blockID, 28,new ItemStack(TFCItems.TinIngot));
        }

        if(TFCSettings.enableVanillaRecipes == true)
        {
            //Conversion to vanilla tools for recipes in other mods
            ModLoader.addRecipe(new ItemStack(Item.pickaxeStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), TFCItems.terraIgInPick});
            ModLoader.addRecipe(new ItemStack(Item.pickaxeStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), TFCItems.terraIgExPick});
            ModLoader.addRecipe(new ItemStack(Item.pickaxeStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), TFCItems.terraSedPick});
            ModLoader.addRecipe(new ItemStack(Item.pickaxeStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), TFCItems.terraMMPick});
            ModLoader.addRecipe(new ItemStack(Item.shovelStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), TFCItems.terraIgInShovel});
            ModLoader.addRecipe(new ItemStack(Item.shovelStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), TFCItems.terraIgExShovel});
            ModLoader.addRecipe(new ItemStack(Item.shovelStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), TFCItems.terraSedShovel});
            ModLoader.addRecipe(new ItemStack(Item.shovelStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), TFCItems.terraMMShovel});
            ModLoader.addRecipe(new ItemStack(Item.hoeStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), TFCItems.terraIgInHoe});
            ModLoader.addRecipe(new ItemStack(Item.hoeStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), TFCItems.terraIgExHoe});
            ModLoader.addRecipe(new ItemStack(Item.hoeStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), TFCItems.terraSedHoe});
            ModLoader.addRecipe(new ItemStack(Item.hoeStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), TFCItems.terraMMHoe});
            ModLoader.addRecipe(new ItemStack(Item.axeStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), TFCItems.terraIgInAxe});
            ModLoader.addRecipe(new ItemStack(Item.axeStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), TFCItems.terraIgExAxe});
            ModLoader.addRecipe(new ItemStack(Item.axeStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), TFCItems.terraSedAxe});
            ModLoader.addRecipe(new ItemStack(Item.axeStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), TFCItems.terraMMAxe});
        }
    }

    public static void SurroundWithLeaves(World world, int i, int j, int k, int meta, Random R)
    {
        for (int y = 2; y >= -2; y--)
        {
            for (int x = 2; x >= -2; x--)
            {
                for (int z = 2; z >= -2; z--)
                {
                    if(world.getBlockId(i+x, j+y, k+z) == 0) {
                        world.setBlockAndMetadata(i+x, j+y, k+z, Block.leaves.blockID, meta);
                    }
                }
            }
        }
    }

    public static boolean isClient()
    {
        boolean b = false;
        try 
        {
            Class.forName("net.minecraft.client.MinecraftApplet", false, MinecraftForge.class.getClassLoader());
            b = true;
        } 
        catch (ClassNotFoundException e) 
        {
            b = false;
        }  
        return b;
    }

    public static void generateCaveDecor(World world, Random R, int chunkX, int chunkZ)
    {
        for (int xCoord = 0; xCoord < 16; ++xCoord)
        {
            for (int zCoord = 0; zCoord < 16; ++zCoord)
            {
                for (int y = 127; y >= 0; --y)
                {
                    int x = chunkX + xCoord;
                    int z = chunkZ + zCoord;

                    int id = world.getBlockId(x, y, z);

                    if(y > 8 && id == 0 && (world.getBlockId(x, y+1, z) == mod_TFC_Core.terraStoneIgEx.blockID || world.getBlockId(x, y+1, z) == mod_TFC_Core.terraStoneIgIn.blockID || 
                            world.getBlockId(x, y+1, z) == mod_TFC_Core.terraStoneSed.blockID || world.getBlockId(x, y+1, z) == mod_TFC_Core.terraStoneMM.blockID))
                    {
                        if(world.getBlockId(x, y-1, z) == 0 && world.getBlockId(x, y-2, z) == 0 && world.getBlockId(x, y-3, z) == 0)
                        {

                            if(R.nextInt(25) == 0)
                            {
                                int type = R.nextInt(4);

                                switch(type)
                                {
                                    case 0:
                                    {
                                        world.setBlock(x, y, z, mod_TFC_Core.stoneStalac.blockID);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y, z)).extraData = 1052929 + 16777216L; 
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y, z)).TypeID = (short) world.getBlockId(x, y+1, z);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y, z)).MetaID =  (byte) world.getBlockMetadata(x, y+1, z);
                                        world.setBlock(x, y-1, z, mod_TFC_Core.stoneStalac.blockID);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y-1, z)).extraData = 2105858 + 16777216L;
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y-1, z)).TypeID = (short) world.getBlockId(x, y+1, z);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y-1, z)).MetaID =  (byte) world.getBlockMetadata(x, y+1, z);
                                        world.setBlock(x, y-2, z, mod_TFC_Core.stoneStalac.blockID);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y-2, z)).extraData = 3158787 + 16777216L;
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y-2, z)).TypeID = (short) world.getBlockId(x, y+1, z);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y-2, z)).MetaID =  (byte) world.getBlockMetadata(x, y+1, z);
                                    }
                                    case 1:
                                    {
                                        world.setBlock(x, y, z, mod_TFC_Core.stoneStalac.blockID);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y, z)).extraData = 1052929 + 16777216L;
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y, z)).TypeID = (short) world.getBlockId(x, y+1, z);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y, z)).MetaID =  (byte) world.getBlockMetadata(x, y+1, z);
                                        world.setBlock(x, y-1, z, mod_TFC_Core.stoneStalac.blockID);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y-1, z)).extraData = 3158851 + 16777216L;
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y-1, z)).TypeID = (short) world.getBlockId(x, y+1, z);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y-1, z)).MetaID =  (byte) world.getBlockMetadata(x, y+1, z);
                                    }
                                    case 2:
                                    {
                                        world.setBlock(x, y, z, mod_TFC_Core.stoneStalac.blockID);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y, z)).extraData = 3158785 + 16777216L;
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y, z)).TypeID = (short) world.getBlockId(x, y+1, z);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y, z)).MetaID =  (byte) world.getBlockMetadata(x, y+1, z);
                                        world.setBlock(x, y-1, z, mod_TFC_Core.stoneStalac.blockID);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y-1, z)).extraData = 4211779 + 16777216L;
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y-1, z)).TypeID = (short) world.getBlockId(x, y+1, z);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y-1, z)).MetaID =  (byte) world.getBlockMetadata(x, y+1, z);
                                    }
                                    case 3:
                                    {
                                        world.setBlock(x, y, z, mod_TFC_Core.stoneStalac.blockID);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y, z)).extraData = 2101505 + 16777216L;
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y, z)).TypeID = (short) world.getBlockId(x, y+1, z);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y, z)).MetaID =  (byte) world.getBlockMetadata(x, y+1, z);
                                        world.setBlock(x, y-1, z, mod_TFC_Core.stoneStalac.blockID);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y-1, z)).extraData = 4203010 + 16777216L;
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y-1, z)).TypeID = (short) world.getBlockId(x, y+1, z);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y-1, z)).MetaID =  (byte) world.getBlockMetadata(x, y+1, z);
                                        world.setBlock(x, y-2, z, mod_TFC_Core.stoneStalac.blockID);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y-2, z)).extraData = 5255683 + 16777216L;
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y-2, z)).TypeID = (short) world.getBlockId(x, y+1, z);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y-2, z)).MetaID =  (byte) world.getBlockMetadata(x, y+1, z);
                                    }
                                    case 4:
                                    {
                                        world.setBlock(x, y, z, mod_TFC_Core.stoneStalac.blockID);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y, z)).extraData = 3158785 + 16777216L;
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y, z)).TypeID = (short) world.getBlockId(x, y+1, z);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y, z)).MetaID =  (byte) world.getBlockMetadata(x, y+1, z);
                                        world.setBlock(x, y-2, z, mod_TFC_Core.stoneStalac.blockID);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y-2, z)).extraData = 5255683 + 16777216L;
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y-2, z)).TypeID = (short) world.getBlockId(x, y+1, z);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y-2, z)).MetaID =  (byte) world.getBlockMetadata(x, y+1, z);
                                    }
                                    case 5:
                                    {
                                        world.setBlock(x, y, z, mod_TFC_Core.stoneStalac.blockID);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y, z)).extraData = 1057026 + 16777216L;
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y, z)).TypeID = (short) world.getBlockId(x, y+1, z);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y, z)).MetaID =  (byte) world.getBlockMetadata(x, y+1, z);
                                        world.setBlock(x, y-1, z, mod_TFC_Core.stoneStalac.blockID);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y-1, z)).extraData = 3158531L + 16777216L;
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y-1, z)).TypeID = (short) world.getBlockId(x, y+1, z);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y-1, z)).MetaID =  (byte) world.getBlockMetadata(x, y+1, z);
                                    }
                                }
                            }
                        }
                    }
                    else if(y <128 && id == 0 && world.getBlockLightValue(x, y-1, z) < 10 && (world.getBlockId(x, y-1, z) == mod_TFC_Core.terraStoneIgEx.blockID || world.getBlockId(x, y-1, z) == mod_TFC_Core.terraStoneIgIn.blockID || 
                            world.getBlockId(x, y-1, z) == mod_TFC_Core.terraStoneSed.blockID || world.getBlockId(x, y-1, z) == mod_TFC_Core.terraStoneMM.blockID))
                    {
                        if(world.getBlockId(x, y+1, z) == 0 && world.getBlockId(x, y+2, z) == 0 && world.getBlockId(x, y+3, z) == 0)
                        {
                            if(R.nextInt(25) == 0)
                            {
                                int type = R.nextInt(3);

                                switch(type)
                                {
                                    case 0:
                                    {
                                        world.setBlock(x, y, z, mod_TFC_Core.stoneSlabs.blockID);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y, z)).extraData = 1057026; 
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y, z)).TypeID = (short) world.getBlockId(x, y-1, z);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y, z)).MetaID =  (byte) world.getBlockMetadata(x, y-1, z);
                                        world.setBlock(x, y+1, z, mod_TFC_Core.stoneSlabs.blockID);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y+1, z)).extraData = 3158531;
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y+1, z)).TypeID = (short) world.getBlockId(x, y-1, z);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y+1, z)).MetaID =  (byte) world.getBlockMetadata(x, y-1, z);
                                        world.setBlock(x, y+2, z, mod_TFC_Core.stoneSlabs.blockID);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y+2, z)).extraData = 4207363;
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y+2, z)).TypeID = (short) world.getBlockId(x, y-1, z);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y+2, z)).MetaID =  (byte) world.getBlockMetadata(x, y-1, z);
                                    }
                                    case 1:
                                    {
                                        world.setBlock(x, y, z, mod_TFC_Core.stoneSlabs.blockID);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y, z)).extraData = 1052930; 
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y, z)).TypeID = (short) world.getBlockId(x, y-1, z);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y, z)).MetaID =  (byte) world.getBlockMetadata(x, y-1, z);
                                        world.setBlock(x, y+1, z, mod_TFC_Core.stoneSlabs.blockID);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y+1, z)).extraData = 3158274;
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y+1, z)).TypeID = (short) world.getBlockId(x, y-1, z);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y+1, z)).MetaID =  (byte) world.getBlockMetadata(x, y-1, z);
                                        world.setBlock(x, y+2, z, mod_TFC_Core.stoneSlabs.blockID);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y+2, z)).extraData = 4407811;
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y+2, z)).TypeID = (short) world.getBlockId(x, y-1, z);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y+2, z)).MetaID =  (byte) world.getBlockMetadata(x, y-1, z);
                                    }
                                    case 2:
                                    {
                                        world.setBlock(x, y, z, mod_TFC_Core.stoneSlabs.blockID);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y, z)).extraData = 1052929; 
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y, z)).TypeID = (short) world.getBlockId(x, y-1, z);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y, z)).MetaID =  (byte) world.getBlockMetadata(x, y-1, z);
                                        world.setBlock(x, y+1, z, mod_TFC_Core.stoneSlabs.blockID);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y+1, z)).extraData = 2105858;
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y+1, z)).TypeID = (short) world.getBlockId(x, y-1, z);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y+1, z)).MetaID =  (byte) world.getBlockMetadata(x, y-1, z);
                                        world.setBlock(x, y+2, z, mod_TFC_Core.stoneSlabs.blockID);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y+2, z)).extraData = 3552003;
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y+2, z)).TypeID = (short) world.getBlockId(x, y-1, z);
                                        ((TileEntityPartial)world.getBlockTileEntity(x, y+2, z)).MetaID =  (byte) world.getBlockMetadata(x, y-1, z);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static void SetupWorld(World world)
    {
        Random R = new Random(world.getSeed());
        System.out.println(world.getSeed());
        int count = 0;

            for(int i = 0; i < BiomeGenBase.biomeList.length; i++)
            {
                if(BiomeGenBase.biomeList[i] != null && !(BiomeGenBase.biomeList[i] instanceof BiomeGenHillsEdgeTFC) && !(BiomeGenBase.biomeList[i] instanceof BiomeGenRiverTFC))
                {
                    BiomeGenBase.biomeList[i].SetupStone(world, R);
                }
                else if(BiomeGenBase.biomeList[i] instanceof BiomeGenHillsEdgeTFC)
                {
                    BiomeGenBase.biomeList[i].copyBiomeRocks(BiomeGenBase.biomeList[i].biomeName.replace(" Edge", "").toLowerCase());
                }
                else if(BiomeGenBase.biomeList[i] instanceof BiomeGenRiverTFC)
                {
                    BiomeGenBase.biomeList[i].copyBiomeRocks(BiomeGenBase.biomeList[i].biomeName.replace("River ", "").toLowerCase());
                }
                if(BiomeGenBase.biomeList[i] != null)
                {
                    BiomeGenBase.biomeList[i].SetupTrees(world, R);
                }
            }

//            for(int i = 0; i < BiomeGenBase.deck.length; i++)
//            {
//                if(BiomeGenBase.deck[i] == true)
//                {
//                    count++;
//                }
//            }

        TFC_Game.registerAnvilRecipes(R, world);
    }

    public static void SetupWorld(World w, long seed)
    {
        World world = w;
        try
        {
            //ReflectionHelper.setPrivateValue(WorldInfo.class, w.getWorldInfo(), "randomSeed", seed);
            ReflectionHelper.setPrivateValue(WorldInfo.class, w.getWorldInfo(), 0, seed);
            SetupWorld(w);
        }
        catch(Exception ex)
        {

        }

    }

    public static boolean isRawStone(World world,int x, int y, int z)
    {
        int id = world.getBlockId(x, y, z);
        return id == mod_TFC_Core.terraStoneIgEx.blockID || id == mod_TFC_Core.terraStoneIgIn.blockID || 
                id == mod_TFC_Core.terraStoneSed.blockID || id == mod_TFC_Core.terraStoneMM.blockID;
    }
}
