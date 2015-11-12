package com.bioxx.tfc.WorldGen.Generators.Trees;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import com.bioxx.tfc.Core.TFC_Climate;
import com.bioxx.tfc.TileEntities.TEFruitTreeWood;
import com.bioxx.tfc.api.TFCBlocks;

public class WorldGenCustomFruitTree extends WorldGenerator
{
	private final Block leavesBlock;
	private final int metaId;

	public WorldGenCustomFruitTree(boolean flag, Block block, int meta)
	{
		super(flag);
		leavesBlock = block;
		metaId = meta;
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k)
	{
		float temp = TFC_Climate.getBioTemperatureHeight(world, i, j, k);
		float rain = TFC_Climate.getRainfall(world, i, j, k);
		if(world.isAirBlock(i, j, k) && j < 250 && temp > 10 && temp < 25 && rain >= 500)
			gen(world, random, i, j, k);
		return true;
	}

	public void gen(World world, Random random, int i, int j, int k)
	{
		world.setBlock(i, j, k, TFCBlocks.fruitTreeWood, metaId, 0x2);
		((TEFruitTreeWood)world.getTileEntity(i, j, k)).setTrunk(true);
		((TEFruitTreeWood)world.getTileEntity(i, j, k)).setHeight(0);
		((TEFruitTreeWood)world.getTileEntity(i, j, k)).initBirth();

		if(world.isAirBlock(i, j+1, k))
		{
			world.setBlock(i, j+1, k, TFCBlocks.fruitTreeWood, metaId, 0x2);
			((TEFruitTreeWood)world.getTileEntity(i, j+1, k)).setTrunk(true);
			((TEFruitTreeWood)world.getTileEntity(i, j+1, k)).setHeight(1);
			((TEFruitTreeWood)world.getTileEntity(i, j+1, k)).initBirth();

			if(world.isAirBlock(i, j+2, k))
			{
				world.setBlock(i, j+2, k, TFCBlocks.fruitTreeWood, metaId, 0x2);
				((TEFruitTreeWood)world.getTileEntity(i, j+2, k)).setTrunk(true);
				((TEFruitTreeWood)world.getTileEntity(i, j+2, k)).setHeight(2);
				((TEFruitTreeWood)world.getTileEntity(i, j+2, k)).initBirth();
				surroundWithLeaves(world, i, j + 2, k);

				if(world.isAirBlock(i+1, j+2, k) || world.getBlock(i+1, j+2, k) == leavesBlock)
				{
					world.setBlock(i+1, j+2, k, TFCBlocks.fruitTreeWood, metaId, 0x2);
				}
				if(world.isAirBlock(i-1, j+2, k) || world.getBlock(i-1, j+2, k-1) == leavesBlock)
				{
					world.setBlock(i-1, j+2, k, TFCBlocks.fruitTreeWood, metaId, 0x2);
				}
				if(world.isAirBlock(i, j+2, k+1) || world.getBlock(i, j+2, k+1) == leavesBlock)
				{
					world.setBlock(i, j+2, k+1, TFCBlocks.fruitTreeWood, metaId, 0x2);
				}
				if(world.isAirBlock(i, j+2, k-1) || world.getBlock(i, j+2, k-1) == leavesBlock)
				{
					world.setBlock(i, j+2, k-1, TFCBlocks.fruitTreeWood, metaId, 0x2);
				}

				if(world.isAirBlock(i, j+3, k) || world.getBlock(i, j+3, k) == leavesBlock)
				{
					world.setBlock(i, j+3, k, TFCBlocks.fruitTreeWood, metaId, 0x2);
					((TEFruitTreeWood)world.getTileEntity(i, j+3, k)).setTrunk(true);
					((TEFruitTreeWood)world.getTileEntity(i, j+3, k)).setHeight(3);
					((TEFruitTreeWood)world.getTileEntity(i, j+3, k)).initBirth();
					if (world.isAirBlock(i, j + 4, k))
						world.setBlock(i, j + 4, k, leavesBlock, metaId & 7, 0x2);
				}
			}
		}
	}

	public void surroundWithLeaves(World world, int i, int j, int k)
	{
		for (int y = 1; y >= 0; y--)
		{
			for (int x = 1; x >= -1; x--)
			{
				for (int z = 1; z >= -1; z--)
				{
					if(world.isAirBlock(i+x, j+y, k+z))
						world.setBlock(i+x, j+y, k+z, leavesBlock, metaId & 7, 0x2);
				}
			}
		}
	}

}
