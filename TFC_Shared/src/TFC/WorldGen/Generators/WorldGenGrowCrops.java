package TFC.WorldGen.Generators;

import java.util.Random;

import TFC.*;
import TFC.Blocks.BlockCrop;
import TFC.TileEntities.TileEntityCrop;

import net.minecraft.src.Block;
import net.minecraft.src.BlockFlower;
import net.minecraft.src.World;
import net.minecraft.src.WorldGenerator;

public class WorldGenGrowCrops extends WorldGenerator
{
	/** The ID of the plant block used in this plant generator. */
	private int cropBlockId;

	public WorldGenGrowCrops(int par1)
	{
		this.cropBlockId = par1;
	}
	
	@Override
	public boolean generate(World world, Random par2Random, int x, int y, int z)
	{
		int i = x + 8 + par2Random.nextInt(16);
		int j = y;
		int k = z+ 8 + par2Random.nextInt(16);

		if (world.isAirBlock(i, j, k) && ((BlockCrop)Block.blocksList[Block.crops.blockID]).canBlockStay(world, i, j, k))
		{
			world.setBlock(i, j, k, Block.crops.blockID);
			TileEntityCrop te = (TileEntityCrop)world.getBlockTileEntity(i, j, k);
			te.cropId = cropBlockId;
		}

		return true;
	}
}
