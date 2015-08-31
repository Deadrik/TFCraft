package com.bioxx.tfc.WorldGen.Generators;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

import cpw.mods.fml.common.IWorldGenerator;

import com.bioxx.tfc.Core.TFC_Climate;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Food.CropIndex;
import com.bioxx.tfc.Food.CropManager;
import com.bioxx.tfc.TileEntities.TECrop;
import com.bioxx.tfc.api.TFCBlocks;

public class WorldGenGrowCrops implements IWorldGenerator
{
	/** The ID of the plant block used in this plant generator. */
	private final int cropBlockId;

	public WorldGenGrowCrops(int par1)
	{
		this.cropBlockId = par1;
	}

	public void generate(World world, Random rand, int x, int z, int numToGen)
	{
		int i = x, j = 150, k = z;
		CropIndex crop;
		TECrop te;

		for(int c = 0; c < numToGen; c++)
		{
			i = x - 8 + rand.nextInt(16);
			k = z - 8 + rand.nextInt(16);
			j = world.getTopSolidOrLiquidBlock(i, k);
			crop = CropManager.getInstance().getCropFromId(cropBlockId);

			if(crop != null)
			{
				float temp = TFC_Climate.getHeightAdjustedTempSpecificDay(world, TFC_Time.getTotalDays(), i, j, k);
				int month = TFC_Time.getSeasonAdjustedMonth(k);

				if(temp > crop.minAliveTemp && month > 0 && month <= 6)
				{
					Block b = world.getBlock(i, j, k);
					if (TFCBlocks.crops.canBlockStay(world, i, j, k) && (b.isAir(world, i, j, k) || b == TFCBlocks.tallGrass))
					{
						if(world.setBlock(i, j, k, TFCBlocks.crops, 0, 0x2))
						{
							te = (TECrop)world.getTileEntity(i, j, k);
							if(te != null)
							{
								te.cropId = cropBlockId;
								float gt = Math.max(crop.growthTime / TFC_Time.daysInMonth, 0.01f);
								float mg = Math.min(month / gt, 1.0f) * (0.75f + (rand.nextFloat() * 0.25f));
								float growth = Math.min(crop.numGrowthStages * mg, crop.numGrowthStages);
								te.growth = growth;
							}
						}
					}
				}
			}
		}
	}

	@Override
	public void generate(Random par2Random, int x, int z, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
	{
	}

}
