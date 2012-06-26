// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

// Referenced classes of package net.minecraft.src:
//            BiomeGenBase, SpawnListEntry, EntityWolf, BiomeDecorator, 
//            WorldGenerator

public class BiomeGenPlainsTFC extends BiomeGenBase
{
	public BiomeGenPlainsTFC(int i)
	{
		super(i);
		biomeDecorator.treesPerChunk = -999;
		biomeDecorator.flowersPerChunk = 4;
		biomeDecorator.grassPerChunk = 20;
	}
}
