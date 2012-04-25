// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

// Referenced classes of package net.minecraft.src:
//            BiomeGenBase, SpawnListEntry, EntityWolf, BiomeDecorator, 
//            WorldGenerator

public class BiomeGenHillsTFC extends BiomeGenBase
{

	public BiomeGenHillsTFC(int i)
	{
		super(i);
		((BiomeDecoratorTFC)this.biomeDecorator).looseRocksPerChunk = 4;
		((BiomeDecoratorTFC)this.biomeDecorator).looseRocksChancePerChunk = 90;
	}
}
