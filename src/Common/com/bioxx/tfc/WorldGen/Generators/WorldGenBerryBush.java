package com.bioxx.tfc.WorldGen.Generators;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import com.bioxx.tfc.Blocks.Flora.BlockBerryBush;
import com.bioxx.tfc.Core.TFC_Climate;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Food.FloraIndex;
import com.bioxx.tfc.Food.FloraManager;
import com.bioxx.tfc.TileEntities.TEBerryBush;
import com.bioxx.tfc.api.TFCBlocks;

public class WorldGenBerryBush extends WorldGenerator
{
	private int meta;
	private int clusterSize;
	private int bushHeight;
	private int spawnRadius;
	private Block underBlock = Blocks.air;

	public WorldGenBerryBush(boolean flag, int m, int cluster, int height, int radius)
	{
		super(flag);
		meta = m;
		clusterSize = cluster;
		bushHeight = height;
		spawnRadius = radius;
	}

	public WorldGenBerryBush(boolean flag, int m, int cluster, int height, int radius, Block under)
	{
		this(flag, m, cluster, height, radius);
		underBlock = under;
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k)
	{
		float temp = TFC_Climate.getBioTemperatureHeight(world, i, j, k);
		float rain = TFC_Climate.getRainfall(world, i, j, k);
		float evt = TFC_Climate.getCacheManager(world).getEVTLayerAt(i, k).floatdata1;

		FloraIndex _fi = FloraManager.getInstance().findMatchingIndex(((BlockBerryBush)TFCBlocks.BerryBush).getType(meta));
		if(world.isAirBlock(i, j, k) && j < 250 && temp > _fi.minBioTemp && temp < _fi.maxBioTemp && 
				rain >= _fi.minRain && rain <= _fi.maxRain && evt >= _fi.minEVT && evt <= _fi.maxEVT)
		{
			int _cluster = clusterSize + random.nextInt(clusterSize)-(clusterSize/2);
			short count = 0;
			for(short realCount = 0; count < _cluster && realCount < (spawnRadius*spawnRadius); realCount++)
			{
				int x = random.nextInt(spawnRadius*2);
				int z = random.nextInt(spawnRadius*2);
				if(createBush(world, random, i - spawnRadius+x, world.getHeightValue(i - spawnRadius+x, k - spawnRadius+z), k - spawnRadius+z, _fi))
					count++;
			}
			//TerraFirmaCraft.log.info(_fi.type + ": " + count + " bushes spawned of " + _cluster + " expected. [" + i +"]" + "[" + j +"]" + "[" + k +"]");
		}
		return true;
	}

	public boolean createBush(World world, Random random, int i, int j, int k, FloraIndex fi)
	{
		Block id = world.getBlock(i, j-1, k);
		if ((world.canBlockSeeTheSky(i, j, k) || world.getBlockLightValue(i, j, k) > 8) &&
			(TFC_Core.isSoil(id) && underBlock == Blocks.air || id == underBlock))
		{
			for(short h = 0; h < bushHeight && random.nextBoolean(); h++) 
			{
				world.setBlock(i, j+h, k, TFCBlocks.BerryBush, meta, 2);
				if(TFC_Time.getSeasonAdjustedMonth(k) > fi.harvestStart && TFC_Time.getSeasonAdjustedMonth(k) < fi.harvestFinish+fi.fruitHangTime)
				{
					TEBerryBush te = (TEBerryBush) world.getTileEntity(i, j+h, k);
					te.hasFruit = true;
				}
			}
			return true;
		}
		return false;
	}
}
