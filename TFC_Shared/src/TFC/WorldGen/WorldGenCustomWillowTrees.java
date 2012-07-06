package TFC.WorldGen;

import java.util.Random;

import net.minecraft.src.*;

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
		if(world.getBlockId(i, j, k) == 0) {
			world.setBlockAndMetadataWithNotify (i, j, k, Block.vine.blockID, l);
		}
		for (int i1 = 4 ; world.getBlockId (i, --j, k) == 0 && i1 > 0 ; i1--)
		{
			if(world.getBlockId(i, j, k) == 0) {
				world.setBlockAndMetadataWithNotify (i, j, k, Block.vine.blockID, l);
			}
		}
	}


	public boolean generate (World world, Random random, int i, int j, int k)
	{
		int height = random.nextInt (2) + 3;
		for (; world.getBlockMaterial (i, j - 1, k) == Material.water ; j--)
		{
		}
		boolean flag = true;
		if (j < 1 || j + height + 5 > world.getHeight())
		{
			return false;
		}
		for (int i1 = j ; i1 <= j + 1 + height ; i1++)
		{
			byte byte0 = 1;
			if (i1 == j)
			{
				byte0 = 0;
			}
			if (i1 >= j + 1 + height - 2)
			{
				byte0 = 3;
			}
			for (int j2 = i - byte0 ; j2 <= i + byte0 && flag ; j2++)
			{
				for (int j3 = k - byte0 ; j3 <= k + byte0 && flag ; j3++)
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
							if (i1 > j)
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
		int var3 = world.getBlockId (i, j - 1, k);
		if (!(var3 == mod_TFC_Core.terraDirt.blockID || var3 == mod_TFC_Core.terraDirt2.blockID || var3 == mod_TFC_Core.terraGrass.blockID || var3 == mod_TFC_Core.terraGrass2.blockID ||
				var3 == mod_TFC_Core.terraClayGrass.blockID || var3 == mod_TFC_Core.terraClayGrass2.blockID)|| j >= world.getHeight() - height - 1 || 
				world.getBlockMaterial (i, j, k) == Material.water)
		{
			return false;
		}
		if(world.getBlockId(i, j - 1, k) == 0) {
			world.setBlockAndMetadata(i, j - 1, k, world.getBiomeGenForCoords(i, k).DirtID,world.getBiomeGenForCoords(i, k).SurfaceMeta);
		}
		int z1, x1, y,x,z;
		y = height+j;
		x = i;
		z = k;
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
		createLeafGroup (i, j + height + 1, k, random, world);
		for (int l1 = 0 ; l1 < height ; l1++)
		{
			int l2 = world.getBlockId (i, j + l1, k);
			if (l2 == 0 || l2 == Block.leaves.blockID || l2 == Block.waterMoving.blockID || l2 == Block.waterStill.blockID)
			{
				world.setBlockAndMetadata (i, j + l1, k, Block.wood.blockID, treeId);
			}
		}

		for (int i2 = j + height ; i2 <= j + height + 5 ; i2++)
		{
			for (int k4 = i - 6 ; k4 <= i + 6 ; k4++)
			{
				for (int i5 = k - 6 ; i5 <= k + 6 ; i5++)
				{
					if (world.getBlockId (k4, i2, i5) != Block.leaves.blockID)
					{
						continue;
					}
					if (random.nextInt (4) == 0 && world.getBlockId (k4 - 1, i2, i5) == 0)
					{
						func_35265_a (world, k4 - 1, i2, i5, 8);
					}
					if (random.nextInt (4) == 0 && world.getBlockId (k4 + 1, i2, i5) == 0)
					{
						func_35265_a (world, k4 + 1, i2, i5, 2);
					}
					if (random.nextInt (4) == 0 && world.getBlockId (k4, i2, i5 - 1) == 0)
					{
						func_35265_a (world, k4, i2, i5 - 1, 1);
					}
					if (random.nextInt (4) == 0 && world.getBlockId (k4, i2, i5 + 1) == 0)
					{
						func_35265_a (world, k4, i2, i5 + 1, 4);
					}
				}
			}
		}

		return true;
	}
}