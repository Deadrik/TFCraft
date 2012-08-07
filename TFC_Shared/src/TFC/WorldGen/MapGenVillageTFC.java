package TFC.WorldGen;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import net.minecraft.src.BiomeGenBase;


public class MapGenVillageTFC extends MapGenStructureTFC
{
	/** A list of all the biomes villages can spawn in. */
	public static List villageSpawnBiomes = Arrays.asList(new TFCBiome[] {TFCBiome.plains, TFCBiome.desert,
	        TFCBiome.desert3,TFCBiome.desert4,TFCBiome.desert5,TFCBiome.desert6,
	        TFCBiome.desert7,TFCBiome.desert8, TFCBiome.desert2,
	        TFCBiome.plains2,TFCBiome.plains3,TFCBiome.plains4,
	        TFCBiome.plains8,TFCBiome.plains9,TFCBiome.plains10,
	        TFCBiome.plains5,TFCBiome.plains6,TFCBiome.plains7});

	/** World terrain type, 0 for normal, 1 for flat map */
	private final int terrainType;

	public MapGenVillageTFC(int par1)
	{
		this.terrainType = par1;
	}

	protected boolean canSpawnStructureAtCoords(int par1, int par2)
	{
		byte var3 = 32;
		byte var4 = 8;
		int var5 = par1;
		int var6 = par2;

		if (par1 < 0)
		{
			par1 -= var3 - 1;
		}

		if (par2 < 0)
		{
			par2 -= var3 - 1;
		}

		int var7 = par1 / var3;
		int var8 = par2 / var3;
		Random var9 = this.worldObj.setRandomSeed(var7, var8, 10387312);
		var7 *= var3;
		var8 *= var3;
		var7 += var9.nextInt(var3 - var4);
		var8 += var9.nextInt(var3 - var4);

		if (var5 == var7 && var6 == var8)
		{
			boolean var10 = this.worldObj.getWorldChunkManager().areBiomesViable(var5 * 16 + 8, var6 * 16 + 8, 0, villageSpawnBiomes);

			if (var10)
			{
				return true;
			}
		}

		return false;
	}

	protected StructureStartTFC getStructureStart(int par1, int par2)
	{
		return new StructureVillageStartTFC(this.worldObj, this.rand, par1, par2, this.terrainType);
	}
}
