package TFC.WorldGen.Generators.Trees;

import java.util.Random;

import TFC.*;
import TFC.Core.TFC_Core;
import TFC.WorldGen.DataLayer;
import TFC.WorldGen.TFCBiome;
import TFC.WorldGen.TFCWorldChunkManager;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.crash.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.packet.*;
import net.minecraft.pathfinding.*;
import net.minecraft.potion.*;
import net.minecraft.server.*;
import net.minecraft.stats.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.village.*;
import net.minecraft.world.*;
import net.minecraft.world.biome.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.gen.feature.*;

public class WorldGenCustomWillowTrees extends WorldGenerator
{
	private int treeId;

	public WorldGenCustomWillowTrees(boolean flag, int id)
	{
		super(flag);
		treeId=id;
	}
	void addBranch (int x, int y, int z, int x1, int z1, Random random, World world)
	{
		if (random.nextInt (3) == 0)
		{
			int X = (random.nextInt (8) - 4) * z1;
			X += (random.nextInt (2) + 3) * x1;
			int Z = (random.nextInt (8) - 4) * x1;
			Z += (random.nextInt (2) + 3) * z1;
			int Y = random.nextInt (3) + random.nextInt (1);
			int length = (int) Math.sqrt (Math.pow (X, 2) + Math.pow (Y, 2) + Math.pow (Z, 2));
			for (int a = 0 ; a < length ; a++)
			{
				if(world.getBlockId(X * a / length + x, Y * a / length + y, Z * a / length + z) == 0) {
					world.setBlockAndMetadata (X * a / length + x, Y * a / length + y, Z * a / length + z, Block.wood.blockID, treeId);
				}
			}
			createLeafGroup (X + x, Y + y, Z + z, random, world);
		}
	}


	void createLeafGroup (int x, int y, int z, Random random, World world)
	{
		for (int y1 = 0 ; y1 < 2 ; y1++)
		{
			for (int x1 = x - 2 + y1 ; x1 < x + 2 - y1 ; x1++)
			{
				for (int z1 = z - 2 + y1 ; z1 < z + 2 - y1 ; z1++)
				{
					if (!Block.opaqueCubeLookup [world.getBlockId (x1, y1 + y, z1)])
					{
						world.setBlockAndMetadata (x1, y1 + y, z1, Block.leaves.blockID, treeId);

						for (int a = 0 ; a < random.nextInt (2) + 2 ; a++)
						{
							int id = world.getBlockId (x1, y1 - 1 - a + y, z1);
							if (!Block.opaqueCubeLookup [id] && id == 0)
							{
								world.setBlockAndMetadata (x1, y1 - 1 - a + y, z1, Block.leaves.blockID, treeId);
							}
						}
					}
				}
			}
		}
	}


	private void func_35265_a (World world, int i, int j, int k, int l)
	{
		if(world.getBlockId(i, j, k) == 0 && Block.vine.canBlockStay(world, i, j, k)) {
			world.setBlockAndMetadataWithNotify (i, j, k, Block.vine.blockID, l);
		}
		for (int i1 = 4 ; world.getBlockId (i, --j, k) == 0 && i1 > 0 ; i1--)
		{
			if(world.getBlockId(i, j, k) == 0 && Block.vine.canBlockStay(world, i, j, k)) {
				world.setBlockAndMetadataWithNotify (i, j, k, Block.vine.blockID, l);
			}
		}
	}


	public boolean generate (World world, Random random, int xCoord, int yCoord, int zCoord)
	{
		int height = random.nextInt (2) + 3;
		for (; world.getBlockMaterial (xCoord, yCoord - 1, zCoord) == Material.water ; yCoord--)
		{
		}
		boolean flag = true;
		if (yCoord < 1 || yCoord + height + 5 > world.getHeight())
		{
			return false;
		}
		for (int i1 = yCoord ; i1 <= yCoord + 1 + height ; i1++)
		{
			byte byte0 = 1;
			if (i1 == yCoord)
			{
				byte0 = 0;
			}
			if (i1 >= yCoord + 1 + height - 2)
			{
				byte0 = 3;
			}
			for (int j2 = xCoord - byte0 ; j2 <= xCoord + byte0 && flag ; j2++)
			{
				for (int j3 = zCoord - byte0 ; j3 <= zCoord + byte0 && flag ; j3++)
				{
					if (i1 >= 0 && i1 < world.getHeight())
					{
						int i4 = world.getBlockId (j2, i1, j3);
						if (i4 == 0 || i4 == Block.leaves.blockID)
						{
							continue;
						}
						if (i4 == Block.waterStill.blockID || i4 == Block.waterMoving.blockID)
						{
							if (i1 > yCoord)
							{
								flag = false;
							}
						}
						else
						{
							flag = false;
						}
					}
					else
					{
						flag = false;
					}
				}
			}
		}

		if (!flag)
		{
			return false;
		}
		int var3 = world.getBlockId (xCoord, yCoord - 1, zCoord);
		if (!(TFC_Core.isSoil(var3))|| yCoord >= world.getHeight() - height - 1 || 
				world.getBlockMaterial (xCoord, yCoord, zCoord) == Material.water)
		{
			return false;
		}
		if(world.getBlockId(xCoord, yCoord - 1, zCoord) == 0) {
			DataLayer rockLayer1 = ((TFCWorldChunkManager)world.getWorldChunkManager()).getRockLayerAt(xCoord, zCoord, 0);
			//set the block below the tree to dirt.
			world.setBlockAndMetadata(xCoord, yCoord - 1, zCoord, TFC_Core.getTypeForGrass(rockLayer1.data2), TFC_Core.getSoilMetaFromStone(rockLayer1.data1, rockLayer1.data2));
		}
		int z1, x1, y,x,z;
		y = height+yCoord;
		x = xCoord;
		z = zCoord;
		for (int n = 0 ; n < 2 ; n++)
		{
			x1 = 1 - n * (random.nextInt (1) - random.nextInt (1));
			z1 = 0 - n * (random.nextInt (1) - random.nextInt (1));
			int X = (random.nextInt (6) - 3) * z1;
			X += (random.nextInt (1) + 2) * x1;
			int Z = (random.nextInt (6) - 3) * x1;
			Z += (random.nextInt (1) + 2) * z1;
			int Y = random.nextInt (2) + 3;
			int length = (int) Math.sqrt (Math.pow (X, 2) + Math.pow (Y, 2) + Math.pow (Z, 2));
			for (int a = 0 ; a < length ; a++)
			{
				if(world.getBlockId(X * a / length + x, Y * a / length + y, Z * a / length + z) == 0) {
					world.setBlockAndMetadata (X * a / length + x, Y * a / length + y, Z * a / length + z, Block.wood.blockID, treeId);
				}
				addBranch (X * a / length + x, Y * a / length + y, Z * a / length + z, -1, 0, random, world);
				addBranch (X * a / length + x, Y * a / length + y, Z * a / length + z, 0, -1, random, world);
				addBranch (X * a / length + x, Y * a / length + y, Z * a / length + z, 1, 0, random, world);
				addBranch (X * a / length + x, Y * a / length + y, Z * a / length + z, 0, 1, random, world);
			}
			createLeafGroup (X + x, Y + y, Z + z, random, world);
		}
		createLeafGroup (xCoord, yCoord + height + 1, zCoord, random, world);
		for (int l1 = 0 ; l1 < height ; l1++)
		{
			int l2 = world.getBlockId (xCoord, yCoord + l1, zCoord);
			if (l2 == 0 || l2 == Block.leaves.blockID || l2 == Block.waterMoving.blockID || l2 == Block.waterStill.blockID  || 
					Block.blocksList[l2].canBeReplacedByLeaves(world, xCoord, yCoord + l1, zCoord))
			{
				world.setBlockAndMetadata (xCoord, yCoord + l1, zCoord, Block.wood.blockID, treeId);
			}
		}

//		for (int i2 = yCoord + height ; i2 <= yCoord + height + 5 ; i2++)
//		{
//			for (int k4 = xCoord - 6 ; k4 <= xCoord + 6 ; k4++)
//			{
//				for (int i5 = zCoord - 6 ; i5 <= zCoord + 6 ; i5++)
//				{
//					if (world.getBlockId (k4, i2, i5) != Block.leaves.blockID)
//					{
//						continue;
//					}
//					if (random.nextInt (4) == 0 && world.getBlockId (k4 - 1, i2, i5) == 0)
//					{
//						func_35265_a (world, k4 - 1, i2, i5, 8);
//					}
//					if (random.nextInt (4) == 0 && world.getBlockId (k4 + 1, i2, i5) == 0)
//					{
//						func_35265_a (world, k4 + 1, i2, i5, 2);
//					}
//					if (random.nextInt (4) == 0 && world.getBlockId (k4, i2, i5 - 1) == 0)
//					{
//						func_35265_a (world, k4, i2, i5 - 1, 1);
//					}
//					if (random.nextInt (4) == 0 && world.getBlockId (k4, i2, i5 + 1) == 0)
//					{
//						func_35265_a (world, k4, i2, i5 + 1, 4);
//					}
//				}
//			}
//		}

		return true;
	}
}