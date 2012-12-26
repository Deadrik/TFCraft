package TFC.WorldGen.Biomes;

import java.util.Random;

import TFC.WorldGen.BiomeDecoratorTFC;
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

// Referenced classes of package net.minecraft.src:
//            BiomeGenBase, SpawnListEntry, EntityWolf, BiomeDecorator, 
//            WorldGenerator

public class BiomeGenSwampTFC extends TFCBiome
{
    int swampType;
    int treeCommon1 = -1;
    Boolean treeCommon1Size;
    int treeCommon2 = -1;
    Boolean treeCommon2Size;
    int treeUncommon = -1;
    Boolean treeUncommonSize;
    int treeRare = -1;
    Boolean treeRareSize;

    WorldGenerator[] HardwoodGenList = {worldGenWillowShortTrees,worldGenWillowShortTrees,worldGenAshShortTrees,worldGenWhiteElmShortTrees,
            worldGenOakShortTrees,worldGenBirchShortTrees,worldGenAshTallTrees,worldGenWhiteElmTallTrees,
            worldGenOakTallTrees,worldGenBirchTallTrees,worldGenBirchTallTrees,worldGenMapleShortTrees,worldGenAspenShortTrees,worldGenAspenTallTrees};

    WorldGenerator[] ConiferGenList = {worldGenWhiteCedarTallTrees,worldGenPineShortTrees,worldGenPineTallTrees,worldGenSpruceShortTrees,worldGenSpruceTallTrees,
            worldGenBirchShortTrees,worldGenAshTallTrees,worldGenWhiteElmTallTrees,worldGenWhiteElmShortTrees};

    public BiomeGenSwampTFC(int i)
    {
        super(i);
        ((BiomeDecoratorTFC)this.theBiomeDecorator).treesPerChunk = 4;
        ((BiomeDecoratorTFC)this.theBiomeDecorator).flowersPerChunk = -999;
        ((BiomeDecoratorTFC)this.theBiomeDecorator).deadBushPerChunk = 1;
        ((BiomeDecoratorTFC)this.theBiomeDecorator).mushroomsPerChunk = 8;
        ((BiomeDecoratorTFC)this.theBiomeDecorator).reedsPerChunk = 10;
        ((BiomeDecoratorTFC)this.theBiomeDecorator).clayPerChunk = 2;
        ((BiomeDecoratorTFC)this.theBiomeDecorator).waterlilyPerChunk = 4;
        swampType = -1;
        treeCommon1 = -1;
        treeCommon2 = -1;
        treeUncommon = -1;
        treeRare = -1;
    }

    @Override
    public WorldGenerator getRandomWorldGenForTrees(Random random, World world)
    {


        if(swampType == -1)
        {
            Random R = new Random(world.getSeed() + this.biomeID);
            swampType = R.nextInt(100);

        }

        if(this.temperature > 27)
        {
            int rand = random.nextInt(100);
            if(rand < 40) {
                return HardwoodGenList[treeCommon1];
            } else if(rand < 80) {
                return HardwoodGenList[treeCommon2];
            } else if(rand < 95) {
                return HardwoodGenList[treeUncommon];
            } else {
                return HardwoodGenList[treeRare];
            }
        }
        else
        {
            int rand = random.nextInt(100);
            if(rand < 40) {
                return ConiferGenList[treeCommon1];
            } else if(rand < 80) {
                return ConiferGenList[treeCommon2];
            } else if(rand < 95) {
                return ConiferGenList[treeUncommon];
            } else {
                return ConiferGenList[treeRare];
            }	
        }
    }

    public void SetupTrees(World world, Random R)
    {
        if(this.temperature > 27)
        {
            while(treeCommon1 == -1 && treeCommon2 == -1 && treeUncommon == -1 && treeRare == -1)
            {
                treeCommon1 = 0;
                treeCommon1Size = R.nextBoolean();
                treeCommon2 = R.nextInt(HardwoodGenList.length);
                treeCommon2Size = R.nextBoolean();
                treeUncommon = R.nextInt(HardwoodGenList.length);
                treeUncommonSize = R.nextBoolean();
                treeRare = R.nextInt(HardwoodGenList.length);
                treeRareSize = R.nextBoolean();
            }
        }
        else
        {
            while(treeCommon1 == -1 || treeCommon2 == -1 || treeUncommon == -1 || treeRare == -1)
            {
                ((BiomeDecoratorTFC)this.theBiomeDecorator).treesPerChunk = 6;
                treeCommon1 = R.nextInt(ConiferGenList.length);
                treeCommon1Size = R.nextBoolean();
                treeCommon2 = R.nextInt(ConiferGenList.length);
                treeCommon2Size = R.nextBoolean();
                treeUncommon = R.nextInt(ConiferGenList.length);
                treeUncommonSize = R.nextBoolean();
                treeRare = R.nextInt(ConiferGenList.length);
                treeRareSize = R.nextBoolean();
            }
        }

    }
    
    protected float getMonthTemp(int month)
    {
        switch(month)
        {
            case 11:
                return 0.1F;
            case 0:
                return 0.15F;
            case 1:
                return 0.25F;
            case 2:
                return 0.8F;
            case 3:
                return 0.75F; 
            case 4:
                return 1F;
            case 5:
                return 0.75F;
            case 6:
                return 0.5F;
            case 7:
                return 0.25F;
            case 8:
                return 0.15F;
            case 9:
                return 0.1F;
            case 10:
                return 0.05F;
            default:
                return 1F;
        }
    }

    /**
     * Provides the basic grass color based on the biome temperature and rainfall
     */
    //    public int getBiomeGrassColor()
    //    {
    //        double var1 = (double)this.getFloatTemperature();
    //        double var3 = (double)this.getIntRainfall();
    //        return ((ColorizerGrass.getGrassColor(var1, var3) & 16711422) + 5115470) / 2;
    //    }
    //
    //    /**
    //     * Provides the basic foliage color based on the biome temperature and rainfall
    //     */
    //    public int getBiomeFoliageColor()
    //    {
    //        double var1 = (double)this.getFloatTemperature();
    //        double var3 = (double)this.getIntRainfall();
    //        return ((ColorizerFoliage.getFoliageColor(var1, var3) & 16711422) + 5115470) / 2;
    //    }
}
