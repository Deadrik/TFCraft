package net.minecraft.src;

import java.util.Random;

import TFC.Entities.EntityChickenTFC;
import TFC.WorldGen.WorldGenCustomHugeTrees;
import TFC.WorldGen.WorldGenCustomShortTrees;
import TFC.WorldGen.WorldGenCustomShrub;
import TFC.WorldGen.WorldGenCustomTallGrass;

public class BiomeGenJungleTFC extends BiomeGenBase
{
	public BiomeGenJungleTFC(int id)
	{
		super(id);
		this.biomeDecorator.treesPerChunk = 50;
		this.biomeDecorator.grassPerChunk = 25;
		this.biomeDecorator.flowersPerChunk = 4;
		this.spawnableMonsterList.add(new SpawnListEntry(EntityOcelot.class, 2, 1, 1));
		this.spawnableCreatureList.add(new SpawnListEntry(EntityChickenTFC.class, 10, 4, 4));

	}

	public void decorate(World par1World, Random par2Random, int par3, int par4)
	{
		super.decorate(par1World, par2Random, par3, par4);
		WorldGenVines var5 = new WorldGenVines();

		for (int var6 = 0; var6 < 50; ++var6)
		{
			int var7 = par3 + par2Random.nextInt(16) + 8;
			byte var8 = 64;
			int var9 = par4 + par2Random.nextInt(16) + 8;
			var5.generate(par1World, par2Random, var7, var8, var9);
		}
	}

	public WorldGenerator func_48410_b(Random par1Random)
	{
		return par1Random.nextInt(4) == 0 ? new WorldGenCustomTallGrass(Block.tallGrass.blockID, 2) : new WorldGenCustomTallGrass(Block.tallGrass.blockID, 1);
	}

	/**
	 * Gets a WorldGen appropriate for this biome.
	 */
	public WorldGenerator getRandomWorldGenForTrees(Random par1Random, World world)
	{
		return (WorldGenerator)(par1Random.nextInt(10) == 0 ? new WorldGenCustomShortTrees(false,15) : par1Random.nextInt(2) == 0 ? new WorldGenCustomShrub(15, 15) : par1Random.nextInt(3) == 0 ? new WorldGenCustomHugeTrees(false, 10 + par1Random.nextInt(20), 15, 15) : new WorldGenCustomShortTrees(false, 15));
	}
	
	protected float getMonthTemp(int month)
    {
        switch(month)
        {
            case 11:
                return 0.78F;
            case 0:
                return 0.78F;
            case 1:
                return 0.78F;
            case 2:
                return 0.79F;
            case 3:
                return 0.79F; 
            case 4:
                return 0.79F;
            case 5:
                return 0.78F;
            case 6:
                return 0.78F;
            case 7:
                return 0.78F;
            case 8:
                return 0.77F;
            case 9:
                return 0.77F;
            case 10:
                return 0.77F;
            default:
                return 1F;
        }
    }
}
