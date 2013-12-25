package TFC.WorldGen.Biomes;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.biome.SpawnListEntry;
import net.minecraft.world.gen.feature.WorldGenVines;
import net.minecraft.world.gen.feature.WorldGenerator;
import TFC.Entities.Mobs.EntityChickenTFC;
import TFC.Entities.Mobs.EntityPigTFC;
import TFC.WorldGen.BiomeDecoratorTFC;
import TFC.WorldGen.TFCBiome;
import TFC.WorldGen.Generators.WorldGenCustomTallGrass;

public class BiomeGenJungleTFC extends TFCBiome
{
	private WorldGenVines vinesGen;
	private WorldGenCustomTallGrass grass1Gen;
	private WorldGenCustomTallGrass grass2Gen;

	public BiomeGenJungleTFC(int id)
	{
		super(id);
		((BiomeDecoratorTFC)this.theBiomeDecorator).treesPerChunk = 50;
		((BiomeDecoratorTFC)this.theBiomeDecorator).grassPerChunk = 25;
		((BiomeDecoratorTFC)this.theBiomeDecorator).flowersPerChunk = 4;
		((BiomeDecoratorTFC)this.theBiomeDecorator).waterlilyPerChunk = 4;
		//this.spawnableMonsterList.add(new SpawnListEntry(EntityOcelot.class, 2, 1, 1));
		this.spawnableCreatureList.add(new SpawnListEntry(EntityChickenTFC.class, 3, 1, 3));
		this.spawnableCreatureList.add(new SpawnListEntry(EntityPigTFC.class, 2, 1, 3));
		this.vinesGen = new WorldGenVines();
		this.grass1Gen = new WorldGenCustomTallGrass(Block.tallGrass.blockID, 1);
		this.grass2Gen = new WorldGenCustomTallGrass(Block.tallGrass.blockID, 2);
	}

	@Override
	public void decorate(World par1World, Random par2Random, int par3, int par4)
	{
		super.decorate(par1World, par2Random, par3, par4);

		for (int var6 = 0; var6 < 50; ++var6)
		{
			int var7 = par3 + par2Random.nextInt(16) + 8;
			byte var8 = 64;
			int var9 = par4 + par2Random.nextInt(16) + 8;
			vinesGen.generate(par1World, par2Random, var7, var8, var9);
		}
	}

	public WorldGenerator func_48410_b(Random par1Random)
	{
		return par1Random.nextInt(4) == 0 ? grass2Gen : grass1Gen;
	}

	@Override
	protected float getMonthTemp(int month)
	{
		switch(month)
		{
		case 11:
			return 0.9F;
		case 0:
			return 0.92F;
		case 1:
			return 0.94F;
		case 2:
			return 0.96F;
		case 3:
			return 0.98F; 
		case 4:
			return 1F;
		case 5:
			return 0.98F;
		case 6:
			return 0.96F;
		case 7:
			return 0.94F;
		case 8:
			return 0.92F;
		case 9:
			return 0.90F;
		case 10:
			return 0.88F;
		default:
			return 1F;
		}
	}
}
