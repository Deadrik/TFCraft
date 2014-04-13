package TFC.WorldGen.Generators;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import TFC.Blocks.BlockCrop;
import TFC.Core.TFC_Climate;
import TFC.Core.TFC_Time;
import TFC.Food.CropIndex;
import TFC.Food.CropManager;
import TFC.TileEntities.TECrop;
import cpw.mods.fml.common.IWorldGenerator;

public class WorldGenGrowCrops implements IWorldGenerator
{
	/** The ID of the plant block used in this plant generator. */
	private int cropBlockId;

	public WorldGenGrowCrops(int par1)
	{
		this.cropBlockId = par1;
	}

	public void generate(World world, Random rand, int x, int z, int numToGen)
	{
		int i = x,j = 150,k = z;
		CropIndex crop;
		TECrop te;
		for(int c = 0; c < numToGen; c++)
		{
			i = x-8+rand.nextInt(16);
			k = z-8+rand.nextInt(16);
			j = world.getTopSolidOrLiquidBlock(i, k);
			crop = CropManager.getInstance().getCropFromId(cropBlockId);
			if(crop != null)
			{
				float temp = TFC_Climate.getHeightAdjustedTempSpecificDay((int)TFC_Time.getTotalDays(), i, j, k);

				int month = TFC_Time.getSeasonAdjustedMonth(k);
				if(temp > crop.minAliveTemp && month > 0 && month <= 6)
				{
					int bid = world.getBlockId(i, j, k);
					if (((BlockCrop)Block.blocksList[Block.crops.blockID]).canBlockStay(world, i, j, k) && (bid == 0 || bid == Block.tallGrass.blockID))
					{
						if(world.setBlock(i, j, k, Block.crops.blockID, 0, 0x2))
						{
							te = (TECrop)world.getBlockTileEntity(i, j, k);
							te.cropId = cropBlockId;
							float gt = Math.max(crop.growthTime/TFC_Time.daysInMonth, 0.01f);
							float mg = Math.min(month/gt, 1.0f)*(0.75f+(rand.nextFloat()*0.25f));
							float growth =  Math.min(crop.numGrowthStages*mg, crop.numGrowthStages);
							te.growth = growth;
						}
					}
				}
			}
		}
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		// TODO Auto-generated method stub

	}
}
