package com.bioxx.tfc.WorldGen.Generators;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TreeRegistry;
import com.bioxx.tfc.Core.TreeSchematic;

public class WorldGenTrees extends WorldGenerator
{
	private int meta;
	private boolean large;

	public WorldGenTrees(Boolean flag, int m, boolean l)
	{
		super(flag);
		meta = m;
		large = l;
	}

	@Override
	public boolean generate(World world, Random rand, int x, int y, int z)
	{
		//Get a random tree schematic
		TreeSchematic schem = TreeRegistry.instance.getTreeSchematic(meta, large);

		Block b = world.getBlock(x, y - 1, z);
		//System.out.println("---TREE: "+b.getLocalizedName());
		//System.out.println("---TREE: x:"+x+"_y:"+y+"_z:"+z+".."+schem.getPath());
		if(large && canGrowLarge(world, b, x, y - 1, z))
		{
			/*boolean ret;
			long tim = System.currentTimeMillis();
			ret = genTree(schem, meta, world, x, y, z);
			tim = System.currentTimeMillis() - tim;
			System.out.println("---NEW TREE on "+b.getLocalizedName()+": "+schem.getPath()+" in "+tim+"ms");
			return ret;*/
			return genTree(schem, meta, world, x, y, z);
		}
		else if(!large && canGrowSmall(world, b, x, y - 1, z))
		{
			/*boolean ret;
			long tim = System.currentTimeMillis();
			ret = genTree(schem, meta, world, x, y, z);
			tim = System.currentTimeMillis() - tim;
			System.out.println("---NEW TREE on "+b.getLocalizedName()+": "+schem.getPath()+" in "+tim+"ms");
			return ret;*/
			return genTree(schem, meta, world, x, y, z);
		}

		return false;
	}

	private static boolean genTree(TreeSchematic schem, int meta, World world, int i, int j, int k)
	{
		if(schem == null) return false;

		byte rot = (byte)world.rand.nextInt(4);
		for(int y = 0; y < schem.getSizeY(); y++)
		{
			for(int z = 0; z < schem.getSizeZ(); z++)
			{
				for(int x = 0; x < schem.getSizeX(); x++)
				{
					if(y == 0)
						Process(world, i, j, k, meta, schem, y, z, x, rot, true);
					else
						Process(world, i, j, k, meta, schem, y, z, x, rot, false);
				}
			}
		}

		return true;
	}

	private static void Process(World world, int i, int j, int k, int meta, TreeSchematic schem, int y, int z, int x, int rot, boolean doTrunk)
	{
		int localX = i - schem.getCenterX() - x + schem.getSizeX();
		int localZ = k - schem.getCenterZ() - z + schem.getSizeZ();
		int localY = j + y;

		if(rot == 0)
		{
			localX = i - schem.getCenterX() + x;
			localZ = k - schem.getCenterZ() + z;
		}
		else if(rot == 1)
		{
			localX = i - schem.getCenterX() + x;
			localZ = k - schem.getCenterZ() - z + schem.getSizeZ();
		}
		else if(rot == 2)
		{
			localX = i - schem.getCenterX() - x + schem.getSizeX();
			localZ = k - schem.getCenterZ() + z;
		}

		int index = x + schem.getSizeX() * (z + schem.getSizeZ() * y);
		int id = schem.getBlockArray()[index];

		Block block = TFCBlocks.LogNatural;
		Block leaves = TFCBlocks.Leaves;
		if(meta > 15)
		{
			block = TFCBlocks.LogNatural2;
			leaves = TFCBlocks.Leaves2;
			meta -= 15;
		}

		if(id != 0)
		{
			if(doTrunk)
			{
				if(Block.getBlockById(id).isWood(world, i, j, k))
					world.setBlock(localX, localY, localZ, block, meta, 0x2);
			}
			else
			{
				if(Block.getBlockById(id).isWood(world, i, j, k))
				{
					world.setBlock(localX, localY, localZ, block, meta, 0x2);
				}
				else
				{
					if(world.getBlock(localX, localY, localZ).canBeReplacedByLeaves(world, localX, localY, localZ))
						world.setBlock(localX, localY, localZ, leaves, meta, 0x2);
				}
			}
		}
	}

	private boolean canGrow(Block block)
	{
		return TFC_Core.isGrass(block)
				|| TFC_Core.isDirt(block)
				|| TFC_Core.isClay(block);
	}

	private boolean canGrowSmall(World world, Block block, int x, int y, int z)
	{
		boolean ret = true;

		outerloop:
		for(int i = -1; i <= 1; i++)
		{
			for(int k = -1; k <= 1; k++)
			{
				if(!canGrow(world.getBlock(x + i, y, z + k)))
				{
					ret = false;
					break outerloop;
				}
			}
		}

		return ret;
	}

	private boolean canGrowLarge(World world, Block block, int x, int y, int z)
	{
		boolean ret = true;

		outerloop:
		for(int i = -2; i <= 2; i++)
		{
			for(int k = -2; k <= 2; k++)
			{
				if(!canGrow(world.getBlock(x + i, y, z + k)))
				{
					ret = false;
					break outerloop;
				}
			}
		}

		return ret;
	}
}
