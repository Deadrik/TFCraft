package com.bioxx.tfc.WorldGen.Generators;

import java.util.Random;

import com.bioxx.tfc.Blocks.Flora.BlockWaterPlant;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.TileEntities.TEWaterPlant;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenWaterPlants extends WorldGenerator
{
	/** The ID of the plant block used in this plant generator. */
	private Block plantBlock;
	private boolean isSwamp;

	public WorldGenWaterPlants(Block par1,boolean isSwamp)
	{
		this.plantBlock = par1;
		this.isSwamp = isSwamp;
	}

	public boolean generate(World par1World, Random par2Random, int x, int y, int z)
	{
		int n = isSwamp?25:10;
		for (int var6 = 0; var6 < n; ++var6)
		{
			int var7 = x + (par2Random.nextInt(8) + par2Random.nextInt(4)) - (par2Random.nextInt(3) + par2Random.nextInt(2));
			int var8 = y + par2Random.nextInt(4) - par2Random.nextInt(4);
			int var9 = z + (par2Random.nextInt(8) + par2Random.nextInt(4)) - (par2Random.nextInt(3) + par2Random.nextInt(2));

			if (par1World.isAirBlock(var7, var8, var9))
			{
				//How far underwater are we going
				int depthCounter = 0;
				//Effectively makes sea grass grow less frequently as depth increases beyond 6 m.
				boolean randomTooDeepFlag = false;
				//travel down until a solid surface is reached
				while(var8 > 0 && TFC_Core.isWater(par1World.getBlock(var7, --var8, var9)) && !randomTooDeepFlag)
				{
					depthCounter++;
					if(depthCounter >= 6)
					{
						//If depthCounter reaches 11, automatically prevents plants from growing
						randomTooDeepFlag = (par2Random.nextInt(12 - depthCounter)==0);
					}
				}
				if(!randomTooDeepFlag && depthCounter >0)
				{
					int meta = par1World.getBlockMetadata(var7, var8, var9);
					Block oldBlock = par1World.getBlock(var7, var8, var9);
					par1World.setBlock(var7, var8, var9, this.plantBlock, meta, 1);
					TileEntity te = par1World.getTileEntity(var7, var8, var9);
					if(te instanceof TEWaterPlant){
						((TEWaterPlant)te).setBlock(oldBlock);
					}
					//Gravelly areas will spawn fewer plants
					if(TFC_Core.isGravel(oldBlock))
						n--;
				}
			}
		}
		return true;
	}
}
