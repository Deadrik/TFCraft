package com.bioxx.tfc.WorldGen.Generators;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.Core.Schematic;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TreeRegistry;
import com.bioxx.tfc.TileEntities.TETreeLog;

public class WorldGenTrees extends WorldGenerator
{
	private int treeID;
	private int growthStage;
	private int baseX = 0;
	private int baseY = 0;
	private int baseZ = 0;

	public WorldGenTrees(Boolean flag, int id, int gs)
	{
		super(flag);
		treeID = id;
		growthStage = gs;
	}

	@Override
	public boolean generate(World world, Random rand, int x, int y, int z)
	{
		//Get a random tree schematic
		Schematic schem = TreeRegistry.instance.getRandomTreeSchematic(rand, treeID, growthStage);
		if(schem == null) return false;

		//This is for testing, so every tree generates at the coords you need
		//x = -85; y = 144; z = -4543;
		//System.out.println("---TREE: "+schem.getPath());

		Block b = world.getBlock(x, y - 1, z);
		if(growthStage <= 5 && canGrowHere(world, b, x, y - 1, z, 2) && world.isAirBlock(x, y, z))
		{
			return genTree(schem, treeID, world, x, y, z);
		}
		else if(growthStage == 6 && canGrowHere(world, b, x, y - 1, z, 3) && world.isAirBlock(x, y, z))
		{
			return genTree(schem, treeID, world, x, y, z);
		}

		return false;
	}

	private boolean genTree(Schematic schem, int meta, World world, int treeX, int treeY, int treeZ)
	{
		int rot = world.rand.nextInt(4);
		int index;
		int id;
		boolean doBase = false;

		this.baseX = treeX-1;
		this.baseY = treeY;
		this.baseZ = treeZ-1;

		for(int y = 0; y < schem.getSizeY(); y++)
		{
			for(int z = 0; z < schem.getSizeZ(); z++)
			{
				for(int x = 0; x < schem.getSizeX(); x++)
				{
					index = x + schem.getSizeX() * (z + schem.getSizeZ() * y);
					id = schem.getBlockArray()[index];
					doBase = (y == 0 && x == schem.getCenterX()-1 && z == schem.getCenterZ()-1);
					if(id != 0 || doBase)
						Process(world, baseX, baseY, baseZ, meta, schem, x+1, y, z+1, rot, doBase, Block.getBlockById(id));
				}
			}
		}

		return true;
	}

	private void Process(World world, int treeX, int treeY, int treeZ, int meta,
			Schematic schem, int schemX, int schemY, int schemZ, int rot, boolean doBase, Block b)
	{
		int localX = treeX + schem.getCenterX() - schemX;
		int localZ = treeZ + schem.getCenterZ() - schemZ;
		int localY = treeY + schemY;

		if(rot == 0)
		{
			localX = treeX - schem.getCenterX() + schemX;
			localZ = treeZ - schem.getCenterZ() + schemZ;
		}
		else if(rot == 1)
		{
			localX = treeX - schem.getCenterX() + schemX;
			localZ = treeZ + schem.getCenterZ() - schemZ;
		}
		else if(rot == 2)
		{
			localX = treeX + schem.getCenterX() - schemX;
			localZ = treeZ - schem.getCenterZ() + schemZ;
		}

		Block block = TFCBlocks.LogNatural;
		Block leaves = TFCBlocks.Leaves;
		int localMeta = meta;
		if(meta > 15)
		{
			block = TFCBlocks.LogNatural2;
			leaves = TFCBlocks.Leaves2;
			localMeta -= 16;
		}

		if(localX == treeX && schemY == 0 && localZ == treeZ || doBase)
		{
			world.setBlock(localX, localY, localZ, block, localMeta, 0x2);
			TETreeLog te = (TETreeLog) world.getTileEntity(localX, localY, localZ);
			te.isBase = true;
			te.schemIndex = (byte) schem.getIndex();
			te.treeID = (byte) meta;
			te.rotation = (byte) rot;
		}
		else
		{
			if(b.getMaterial() == Material.wood)
			{
				world.setBlock(localX, localY, localZ, block, localMeta, 0x2);
				TETreeLog te = (TETreeLog) world.getTileEntity(localX, localY, localZ);
				te.Setup(baseX, baseY, baseZ);
			}
			else
			{
				if(world.getBlock(localX, localY, localZ).canBeReplacedByLeaves(world, localX, localY, localZ))
				{
					world.setBlock(localX, localY, localZ, leaves, localMeta, 0x2);
					TETreeLog te = (TETreeLog) world.getTileEntity(localX, localY, localZ);
					te.Setup(baseX, baseY, baseZ);
				}
			}
		}
	}

	private boolean isBlockValid(Block block)
	{
		return TFC_Core.isGrass(block)
				|| TFC_Core.isDirt(block)
				|| TFC_Core.isClay(block);
	}

	private boolean canGrowHere(World world, Block block, int x, int y, int z, int rad)
	{
		boolean ret = true;
		Block ground;
		Block above;

		outerloop:
			for(int i = -rad; i <= rad; i++)
			{
				for(int k = -rad; k <= rad; k++)
				{
					ground = world.getBlock(x + i, y, z + k);
					above = world.getBlock(x + i, y + 1, z + k);
					if(above == TFCBlocks.LogNatural || above == TFCBlocks.LogNatural2)
					{
						ret = false;
						break outerloop;
					}
					if(!isBlockValid(ground))
					{
						ret = false;
						break outerloop;
					}
				}
			}

		return ret;
	}
}
