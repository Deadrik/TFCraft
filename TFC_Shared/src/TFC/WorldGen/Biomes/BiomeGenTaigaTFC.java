package TFC.WorldGen.Biomes;

import net.minecraft.world.biome.SpawnListEntry;
import TFC.Entities.Mobs.EntityBear;
import TFC.Entities.Mobs.EntityDeer;
import TFC.Entities.Mobs.EntityWolfTFC;
import TFC.WorldGen.BiomeDecoratorTFC;
import TFC.WorldGen.TFCBiome;


// Referenced classes of package net.minecraft.src:
//            BiomeGenBase, SpawnListEntry, EntityWolf, BiomeDecorator, 
//            WorldGenerator

public class BiomeGenTaigaTFC extends TFCBiome
{

	public BiomeGenTaigaTFC(int i)
	{
		super(i);
		spawnableCreatureList.add(new SpawnListEntry(EntityWolfTFC.class, 2, 2, 4));
		spawnableCreatureList.add(new SpawnListEntry(EntityBear.class, 2, 1, 2));
		spawnableCreatureList.add(new SpawnListEntry(EntityDeer.class, 1, 2, 5));
		((BiomeDecoratorTFC)this.theBiomeDecorator).treesPerChunk = 10;
		((BiomeDecoratorTFC)this.theBiomeDecorator).grassPerChunk = 1;
		setMinMaxHeight(0.2F, 0.4F);
	}
	@Override
	protected float getMonthTemp(int month)
	{
		switch(month)
		{
		case 11:
			return -1F;
		case 0:
			return 0F;
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
			return 0F;
		case 9:
			return -1F;
		case 10:
			return -2F;
		default:
			return 1F;
		}
	}

}
