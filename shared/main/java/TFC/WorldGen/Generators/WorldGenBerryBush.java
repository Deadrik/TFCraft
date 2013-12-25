package TFC.WorldGen.Generators;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import TFC.TFCBlocks;
import TFC.Blocks.Flora.BlockBerryBush;
import TFC.Core.TFC_Climate;
import TFC.Core.TFC_Core;
import TFC.Core.TFC_Time;
import TFC.Food.FloraIndex;
import TFC.Food.FloraManager;
import TFC.TileEntities.TEBerryBush;

public class WorldGenBerryBush extends WorldGenerator
{
	private int meta;
	private int clusterSize;
	private int bushHeight;
	private int spawnRadius;
	private int underBlock = -1;

	public WorldGenBerryBush(boolean flag, int m, int cluster, int height, int radius)
	{
		super(flag);
		meta = m;
		clusterSize = cluster;
		bushHeight = height;
		spawnRadius = radius;
	}

	public WorldGenBerryBush(boolean flag, int m, int cluster, int height, int radius, int under)
	{
		this(flag, m, cluster, height, radius);
		underBlock = under;
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k)
	{
		float temp = TFC_Climate.getBioTemperatureHeight(i, j, k);
		float rain = TFC_Climate.getRainfall(i, j, k);
		float evt = TFC_Climate.manager.getEVTLayerAt(i, k).floatdata1;

		FloraIndex _fi = FloraManager.getInstance().findMatchingIndex(((BlockBerryBush)TFCBlocks.BerryBush).getType(meta));

		if(world.getBlockId(i, j, k) == 0 && j < 250 && temp > _fi.minBioTemp && temp < _fi.maxBioTemp && 
				rain >= _fi.minRain && rain <= _fi.maxRain && evt >= _fi.minEVT && evt <= _fi.maxEVT)
		{
			int _cluster = clusterSize + random.nextInt(clusterSize)-(clusterSize/2);
			short count = 0;
			for(short realCount = 0; count < _cluster && realCount < (spawnRadius*spawnRadius); realCount++)
			{
				int x = random.nextInt(spawnRadius*2);
				int z = random.nextInt(spawnRadius*2);
				if(createBush(world, random, i - spawnRadius+x, world.getHeightValue(i - spawnRadius+x, k - spawnRadius+z), k - spawnRadius+z, _fi)) {
					count++;
				}
			}
			//System.out.println(_fi.type + ": " + count + " bushes spawned of " + _cluster + " expected. [" + i +"]" + "[" + j +"]" + "[" + k +"]");
		}
		return true;
	}

	public boolean createBush(World world, Random random, int i, int j, int k, FloraIndex fi)
	{
		int id = world.getBlockId(i, j-1, k);
		if((world.canBlockSeeTheSky(i, j, k) || world.getBlockLightValue(i, j, k) > 8) && ((TFC_Core.isSoil(id) && underBlock == -1) || id == underBlock))
		{
			for(short h = 0; h < bushHeight && random.nextBoolean(); h++) 
			{
				world.setBlock(i, j+h, k, TFCBlocks.BerryBush.blockID, meta, 2);
				if(TFC_Time.currentMonth > fi.harvestStart && TFC_Time.currentMonth < fi.harvestFinish+fi.fruitHangTime)
				{
					TEBerryBush te = (TEBerryBush) world.getBlockTileEntity(i, j+h, k);
					te.hasFruit = true;
				}
			}
			return true;
		}
		return false;
	}
}
