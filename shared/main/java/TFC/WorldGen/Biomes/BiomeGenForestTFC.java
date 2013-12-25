package TFC.WorldGen.Biomes;

import net.minecraft.world.biome.SpawnListEntry;
import TFC.Entities.Mobs.EntityBear;
import TFC.Entities.Mobs.EntityChickenTFC;
import TFC.Entities.Mobs.EntityDeer;
import TFC.Entities.Mobs.EntityPigTFC;
import TFC.Entities.Mobs.EntityWolfTFC;
import TFC.WorldGen.BiomeDecoratorTFC;
import TFC.WorldGen.TFCBiome;

public class BiomeGenForestTFC extends TFCBiome
{

	public BiomeGenForestTFC(int i)
	{
		super(i);
		spawnableCreatureList.add(new SpawnListEntry(EntityWolfTFC.class, 1, 1, 3));
		this.spawnableCreatureList.add(new SpawnListEntry(EntityPigTFC.class, 2, 2, 3));
		this.spawnableCreatureList.add(new SpawnListEntry(EntityChickenTFC.class, 1, 1, 1));
		spawnableCreatureList.add(new SpawnListEntry(EntityBear.class, 1, 1, 1));
		spawnableCreatureList.add(new SpawnListEntry(EntityDeer.class, 2, 1, 3));

		((BiomeDecoratorTFC)this.theBiomeDecorator).treesPerChunk = 10;
		((BiomeDecoratorTFC)this.theBiomeDecorator).grassPerChunk = 2;
	}

	@Override
	protected float getMonthTemp(int month)
	{
		switch(month)
		{
		case 11:
			return -0.1F;
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
			return 0.85F;
		case 6:
			return 0.6F;
		case 7:
			return 0.3F;
		case 8:
			return -0.1F;
		case 9:
			return -0.2F;
		case 10:
			return -0.3F;
		default:
			return 1F;
		}
	}
}
