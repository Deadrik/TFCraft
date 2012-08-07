// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package TFC.WorldGen;

import net.minecraft.src.BiomeGenBase;

// Referenced classes of package net.minecraft.src:
//            BiomeGenBase, SpawnListEntry, EntityWolf, BiomeDecorator, 
//            WorldGenerator

public class BiomeGenPlainsTFC extends TFCBiome
{
	public BiomeGenPlainsTFC(int i)
	{
		super(i);
		((BiomeDecoratorTFC)this.biomeDecorator).treesPerChunk = -999;
		((BiomeDecoratorTFC)this.biomeDecorator).flowersPerChunk = 4;
		((BiomeDecoratorTFC)this.biomeDecorator).grassPerChunk = 20;
	}
}
