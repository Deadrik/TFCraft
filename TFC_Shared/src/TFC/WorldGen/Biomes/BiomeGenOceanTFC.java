package TFC.WorldGen.Biomes;

import net.minecraft.world.biome.SpawnListEntry;
import TFC.Entities.Mobs.EntitySquidTFC;
import TFC.WorldGen.TFCBiome;

public class BiomeGenOceanTFC extends TFCBiome
{
	public BiomeGenOceanTFC(int i)
	{
		super(i);
		spawnableCreatureList.clear();
		setColor(ForestColor);
		this.spawnableWaterCreatureList.add(new SpawnListEntry(EntitySquidTFC.class, 6, 1, 3));
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
}
