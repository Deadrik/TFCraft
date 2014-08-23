package com.bioxx.tfc.WorldGen.Generators;

import net.minecraft.block.Block;
import net.minecraft.world.World;

import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.Core.TreeSchematic;

public class WorldGenTree
{
	static boolean createdTrunk = false;
	static int trunkX = 0;
	static int trunkY = 0;
	static int trunkZ = 0;

	public static boolean generate(TreeSchematic schem, byte meta, World world, int i, int j, int k)
	{
		if(schem == null)
			return false;

		createdTrunk = false;
		trunkX = 0;
		trunkY = 0;
		trunkZ = 0;

		byte rot = (byte)world.rand.nextInt(4);

		for(int y = 0; y < schem.getSizeY(); y++)
		{
			for(int z = 0; z < schem.getSizeZ(); z++)
			{
				for(int x = 0; x < schem.getSizeX(); x++)
				{
					Process(world, i, j, k, meta, schem, y, z, x, rot);
				}
			}
		}

		return true;
	}

	private static void Process(World world, int i, int j, int k, byte meta, TreeSchematic schem, int y, int z, int x, int rot)
	{
		int localX = i - schem.getCenterX() - x;
		int localZ = k - schem.getCenterZ() - z;
		int localY = j + y + 1;

		if(rot == 0)
		{
			localX = i - schem.getCenterX() + x;
			localZ = k - schem.getCenterZ() + z;
		}
		else if(rot == 1)
		{
			localX = i - schem.getCenterX() + x;
			localZ = k - schem.getCenterZ() - z;
		}
		else if(rot == 2)
		{
			localX = i - schem.getCenterX() - x;
			localZ = k - schem.getCenterZ() + z;
		}

		int index = x + schem.getSizeX() * (z + schem.getSizeZ() * y);
		int id = schem.getBlockArray()[index];

		//Block trunk = TFCBlocks.LogNaturalTrunk;
		Block block = TFCBlocks.LogNatural;
		Block leaves = TFCBlocks.Leaves;
		if(meta > 15)
		{
			block = TFCBlocks.LogNatural2;
			leaves = TFCBlocks.Leaves2;
			meta -= 15;
		}

		if(world.isAirBlock(localX, localY, localZ) && id != 0)
		{
			if(Block.getBlockById(id).isWood(world, i, j, k))
			{
				if(!createdTrunk)
				{
					world.setBlock(localX, localY, localZ, block, meta, 0x2);
					createdTrunk = true;
					trunkX = localX;
					trunkY = localY;
					trunkZ = localZ;

					/*TELogNaturalTrunk te = (TELogNaturalTrunk)world.getTileEntity(trunkX, trunkY, trunkZ);
					if(te != null)
						te.Setup(schem.getIndex(), (byte)0, trunkX, trunkY, trunkZ, (byte)x, (byte)z);
					else
						return;*/
				}
				else
				{
					world.setBlock(localX, localY, localZ, block, meta, 0x2);
					/*TELogNatural te = (TELogNatural)world.getTileEntity(localX, localY, localZ);
					if(te != null)
						te.Setup(trunkX, trunkY, trunkZ);*/
				}
			}
			else
			{
				world.setBlock(localX, localY, localZ, leaves, meta, 0x2);
				/*TELeaves te = (TELeaves)world.getTileEntity(localX, localY, localZ);
				if(te != null)
					te.Setup(trunkX, trunkY, trunkZ);*/
			}
		}
	}
}
