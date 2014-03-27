package TFC.WorldGen.Generators;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import TFC.Blocks.BlockCrop;
import TFC.Core.TFC_Climate;
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

	public boolean generate(World world, Random par2Random, int x, int y, int z)
	{
		int i = x - 5 + par2Random.nextInt(10);
		int k = z - 5 + par2Random.nextInt(10);
		int j = world.getHeightValue(x, z)+1;

		CropIndex crop = CropManager.getInstance().getCropFromId(cropBlockId);
		if(crop != null)
		{
			float temp = TFC_Climate.getHeightAdjustedTemp(i, j, k);
			float growth =  Math.min(crop.numGrowthStages-par2Random.nextInt(3), crop.numGrowthStages);

			if(temp > crop.minAliveTemp)
			{
				if (world.isAirBlock(i, j, k) && Blocks.wheat.canBlockStay(world, i, j, k)) // ((BlockCrop)Blocks.wheat).canBlockStay(world, i, j, k))
				{
					world.setBlock(i, j, k, Blocks.wheat, 0, 0x2);
					TECrop te = (TECrop)world.getTileEntity(i, j, k);
					te.cropId = cropBlockId;
					te.growth = growth;
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		// TODO Auto-generated method stub

	}
}
