// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package TFC.WorldGen.Biomes;

import java.util.Random;

import TFC.Core.TFC_Time;
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

public class BiomeGenDesertTFC extends TFCBiome
{

	public BiomeGenDesertTFC(int i)
	{
		super(i);
		spawnableCreatureList.clear();
		topBlock = (byte)Block.sand.blockID;
		fillerBlock = (byte)Block.sand.blockID;
		((BiomeDecoratorTFC)this.theBiomeDecorator).treesPerChunk = -999;
		((BiomeDecoratorTFC)this.theBiomeDecorator).deadBushPerChunk = 1;
		((BiomeDecoratorTFC)this.theBiomeDecorator).reedsPerChunk = 3;
		((BiomeDecoratorTFC)this.theBiomeDecorator).cactiPerChunk = 2;
		setMinMaxHeight(0.2F, 0.3F);
		setColor(DesertColor);
	}

	public void decorate(World par1World, Random par2Random, int par3, int par4)
	{
		super.decorate(par1World, par2Random, par3, par4);

		if (par2Random.nextInt(1000) == 0)
		{
			int var5 = par3 + par2Random.nextInt(16) + 8;
			int var6 = par4 + par2Random.nextInt(16) + 8;
			WorldGenDesertWells var7 = new WorldGenDesertWells();
			var7.generate(par1World, par2Random, var5, par1World.getHeightValue(var5, var6) + 1, var6);
		}
	}
	
	protected float getMonthTemp(int month)
    {
        switch(month)
        {
            case 11:
                return 0.75F;
            case 0:
                return 0.80F;
            case 1:
                return 0.85F;
            case 2:
                return 0.90F;
            case 3:
                return 0.95F; 
            case 4:
                return 1F;
            case 5:
                return 0.95F;
            case 6:
                return 0.90F;
            case 7:
                return 0.85F;
            case 8:
                return 0.80F;
            case 9:
                return 0.75F;
            case 10:
                return 0.70F;
            default:
                return 1F;
        }
    }
	
	public float getFloatRain()
    {
        return this.rainfall;
    }
}
