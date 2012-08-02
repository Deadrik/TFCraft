// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.util.Random;

// Referenced classes of package net.minecraft.src:
//            BiomeGenBase, SpawnListEntry, EntityWolf, BiomeDecorator, 
//            WorldGenerator

public class BiomeGenHillsEdgeTFC extends BiomeGenBase
{

    int treeCommon1 = -1;
    Boolean treeCommon1Size;
    int treeCommon2 = -1;
    Boolean treeCommon2Size;
    int treeUncommon = -1;
    Boolean treeUncommonSize;
    int treeRare = -1;
    Boolean treeRareSize;
    
	public BiomeGenHillsEdgeTFC(int i)
	{
		super(i);
		((BiomeDecoratorTFC)this.biomeDecorator).looseRocksPerChunk = 4;
		((BiomeDecoratorTFC)this.biomeDecorator).looseRocksChancePerChunk = 90;
		((BiomeDecoratorTFC)this.biomeDecorator).treesPerChunk = 2;
		((BiomeDecoratorTFC)this.biomeDecorator).grassPerChunk = 2;
	}
	
	public void SetupTrees(World world, Random R)
    {
        treeCommon1 = R.nextInt(13);
        treeCommon1Size = R.nextBoolean();
        treeCommon2 = R.nextInt(13);
        treeCommon2Size = R.nextBoolean();
        treeUncommon = R.nextInt(13);
        treeUncommonSize = R.nextBoolean();
        treeRare = R.nextInt(13);
        treeRareSize = R.nextBoolean();

        if(treeCommon1 == 15 || treeCommon1 == 9) {
            treeCommon1 = R.nextInt(8);
        }
        if(treeCommon2 == 15 || treeCommon2 == 9) {
            treeCommon2 = R.nextInt(8);
        }
        if(treeUncommon == 15) {
            treeUncommon = R.nextInt(13);
        }
        if(treeRare == 15) {
            treeRare = R.nextInt(13);
        }

    }
	
	protected float getMonthTemp(int month)
    {
        switch(month)
        {
            case 11:
                return 0F;
            case 0:
                return 0.33F;
            case 1:
                return 0.45F;
            case 2:
                return 0.60F;
            case 3:
                return 0.80F; 
            case 4:
                return 1F;
            case 5:
                return 0.80F;
            case 6:
                return 0.60F;
            case 7:
                return 0.45F;
            case 8:
                return 0.33F;
            case 9:
                return 0F;
            case 10:
                return -1F;
            default:
                return 1F;
        }
    }
}
