package com.bioxx.tfc.WorldGen.Generators;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.TileEntities.TEWaterPlant;

public class WorldGenWaterPlants extends WorldGenerator
{
	/** The ID of the plant block used in this plant generator. */
	private final Block plantBlock;

	public WorldGenWaterPlants(Block par1)
	{
		this.plantBlock = par1;
	}

	@Override
	public boolean generate(World world, Random rand, int x, int y, int z)
	{
		if (TFC_Core.isWater(world.getBlock(x, y, z)) && world.isAirBlock(x, y+1, z))
		{
			//How far underwater are we going
			int depthCounter = 1;
			//Effectively makes sea grass grow less frequently as depth increases beyond 6 m.
			boolean isTooDeep = false;
			boolean isFreshWater = TFC_Core.isFreshWater(world.getBlock(x, y, z));
			int maxDepth = !isFreshWater ? 10 : 4;

			//travel down until a solid surface is reached
			while(y > 0 && TFC_Core.isWater(world.getBlock(x, --y, z)) && !isTooDeep)
			{
				depthCounter++;
				if(depthCounter > maxDepth)
				{
					//If depthCounter reaches 11, automatically prevents plants from growing
					isTooDeep = true;
				}
			}
			if(!isTooDeep && depthCounter > 0)
			{
				int meta = world.getBlockMetadata(x, y, z);
				Block oldBlock = world.getBlock(x, y, z);
				if (TFC_Core.isSoilOrGravel(oldBlock) || TFC_Core.isSand(oldBlock))
				{
					world.setBlock(x, y, z, this.plantBlock, meta, 2);
					TileEntity te = world.getTileEntity(x, y, z);
					if(te instanceof TEWaterPlant){
						((TEWaterPlant)te).setBlock(oldBlock);
					}
				}
			}
		}
		return true;
	}
}
