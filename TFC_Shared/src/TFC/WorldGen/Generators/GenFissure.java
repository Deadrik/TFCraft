package TFC.WorldGen.Generators;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import TFC.TFCBlocks;
import TFC.API.Enums.TFCDirection;
import TFC.API.Util.ByteCoord;
import TFC.API.Util.CollapseData;
import TFC.API.Util.CollapseList;
import TFC.Core.TFC_Climate;
import TFC.Core.TFC_Core;

public class GenFissure
{
	Random rand;
	int poolDepthFromTop;
	int fissureDepthFromTop = 1;
	Block fillBlock;
	int startingDepth = 20;

	int minTunnel = 2;
	boolean checkStability = true;
	boolean underground = false;

	int rarity = 10;

	public int maxDepth = 15;
	public int maxStart = 90;

	//Basic constructor
	public GenFissure(Block b)
	{
		fillBlock = b;
	}

	public GenFissure(Block b, int minTunnel, boolean s, int rare)
	{
		this(b);
		this.minTunnel = minTunnel;
		checkStability = s;
		rarity = rare;
	}

	public GenFissure setUnderground(boolean i, int d)
	{
		underground = i;
		startingDepth = d;
		return this;
	}

	public void generate(World world, Random rand, int x, int y, int z)
	{
		fissureDepthFromTop = 1 + rand.nextInt(maxDepth);
		poolDepthFromTop = Math.min(1+rand.nextInt(maxDepth), fissureDepthFromTop-2);

		for(int d = 1; d <= poolDepthFromTop; d++)
			if(!world.isBlockNormalCube(x, y-d, z))
				return;

		if(world.getBlockMaterial(x, y+1, z) == Material.water)
			return;

		int stability = TFC_Climate.getStability(x, z);
		int id = world.getBlockId(x,y,z);

		if(Block.blocksList[id] != null && Block.blocksList[id].blockMaterial == Material.water)
			return;

		if(underground)
			y = startingDepth + rand.nextInt(maxStart-startingDepth);

		if(checkStability && stability == 0)
			return;

		if(stability == 1 && fillBlock != null && fillBlock.blockMaterial == Material.water)
			fillBlock = TFCBlocks.HotWaterStill;

		if(!TFC_Core.isGround(id))
			return;



		ArrayList<ByteCoord> map = getCollapseMap(world, x,y-fissureDepthFromTop,z);
		int[] rockLayer = TFC_Climate.getRockLayer(x, y, z, 2);
		boolean makeTunnel = map.size() > 10;
		if(rockLayer[0] == -1)
			return;
		//Take the 2d XZ map of the fissure that we've created and carve the ground
		for(ByteCoord b : map)
		{
			//converted these to i,j,k to make the code more legible
			int i = x+b.x, j = y-fissureDepthFromTop, k = z+b.z;

			world.setBlock(i, j, k, fillBlock != null ? fillBlock.blockID : 0);

			for(int d = 1; d <= fissureDepthFromTop - poolDepthFromTop; d++)
				fill(world, i, j+d, k, rockLayer[0], rockLayer[1], fillBlock != null ? fillBlock.blockID : 0);

			int rx = 0;
			int rz = 0;

			for(int d = 0; d <= fissureDepthFromTop; d++)
			{
				carve(world, i, j+d, k, rockLayer[0], rockLayer[1]);

				//Sometimes we carve the walls of the fissure slightly the make them less uniform
				if(rand.nextInt(3) == 0)
				{
					rx = -1 + rand.nextInt(3);
					rz = -1 + rand.nextInt(3);
					carve(world, i+rx, j+d, k+rz, rockLayer[0], rockLayer[1]);
				}
			}
			/*if(fillBlock != null)
				world.setBlock(i, j-1, k, rockLayer[0], rockLayer[1], 2);*/
		}

		if(makeTunnel)
			makeTunnel(rand, world, x, z, y, rockLayer);
	}

	private void carve(World world, int x, int y, int z, int id, int meta)
	{
		if(TFC_Core.isGround(world.getBlockId(x, y, z)))
			world.setBlockToAir(x, y, z);
		if(world.getBlockMaterial(x-1, y, z) != Material.air && (TFC_Core.isGround(world.getBlockId(x-1, y, z)) && !TFC_Core.isSoil(world.getBlockId(x-1, y, z))) || !world.isBlockNormalCube(x-1, y, z))
			world.setBlock(x-1, y, z, id, meta, 2);
		if(world.getBlockMaterial(x+1, y, z) != Material.air && (TFC_Core.isGround(world.getBlockId(x+1, y, z)) && !TFC_Core.isSoil(world.getBlockId(x+1, y, z))) || !world.isBlockNormalCube(x+1, y, z))
			world.setBlock(x+1, y, z, id, meta, 2);
		if(world.getBlockMaterial(x, y, z-1) != Material.air && (TFC_Core.isGround(world.getBlockId(x, y, z-1)) && !TFC_Core.isSoil(world.getBlockId(x, y, z-1))) || !world.isBlockNormalCube(x, y, z-1))
			world.setBlock(x, y, z-1, id, meta, 2);
		if(world.getBlockMaterial(x, y, z+1) != Material.air && (TFC_Core.isGround(world.getBlockId(x, y, z+1)) && !TFC_Core.isSoil(world.getBlockId(x, y, z+1))) || !world.isBlockNormalCube(x, y, z+1))
			world.setBlock(x, y, z+1, id, meta, 2);
	}

	private void fill(World world, int x, int y, int z, int id, int meta, int fill)
	{
		world.setBlock(x, y, z, fill);
		if(world.getBlockMaterial(x-1, y, z) == Material.air)
			world.setBlock(x-1, y, z, id, meta, 2);
		if(world.getBlockMaterial(x+1, y, z) == Material.air)
			world.setBlock(x+1, y, z, id, meta, 2);
		if(world.getBlockMaterial(x, y, z-1) == Material.air)
			world.setBlock(x, y, z-1, id, meta, 2);
		if(world.getBlockMaterial(x, y, z+1) == Material.air)
			world.setBlock(x, y, z+1, id, meta, 2);
		if(world.getBlockMaterial(x, y-1, z) == Material.air)
			world.setBlock(x, y-1, z, id, meta, 2);
	}

	private void makeTunnel(Random random, World world, int x, int z, int y,
			int[] rockLayer) {
		int xCoord = x, yCoord = y-poolDepthFromTop-1, zCoord = z;
		float downChance = 75;
		while(yCoord > minTunnel)
		{
			if(world.getBlockId(xCoord, yCoord, zCoord) == Block.bedrock.blockID)
				break;
			if(random.nextFloat() < downChance/100f)
				yCoord--;
			else
			{
				int dir = random.nextInt(3);
				switch(dir)
				{
				case 0: xCoord++; break;
				case 1: xCoord--; break;
				case 2: zCoord++; break;
				case 3: zCoord--; break;
				}
			}

			world.setBlock(xCoord, yCoord, zCoord, fillBlock != null ? fillBlock.blockID : 0);

			if(fillBlock != null && world.getBlockMaterial(xCoord+1, yCoord, zCoord) != fillBlock.blockMaterial)
				world.setBlock(xCoord+1, yCoord, zCoord, rockLayer[0], rockLayer[1], 2);
			if(fillBlock != null && world.getBlockMaterial(xCoord-1, yCoord, zCoord) != fillBlock.blockMaterial)
				world.setBlock(xCoord-1, yCoord, zCoord, rockLayer[0], rockLayer[1], 2);
			if(fillBlock != null && world.getBlockMaterial(xCoord, yCoord, zCoord+1) != fillBlock.blockMaterial)
				world.setBlock(xCoord, yCoord, zCoord+1, rockLayer[0], rockLayer[1], 2);
			if(fillBlock != null && world.getBlockMaterial(xCoord, yCoord, zCoord-1) != fillBlock.blockMaterial)
				world.setBlock(xCoord, yCoord, zCoord-1, rockLayer[0], rockLayer[1], 2);
		}
	}

	public ArrayList<ByteCoord> getCollapseMap(World world, int i, int j, int k)
	{
		int checks = 0;
		ArrayList<ByteCoord> map = new ArrayList<ByteCoord>();
		ArrayList<ByteCoord> checkedmap = new ArrayList<ByteCoord>();
		CollapseList<CollapseData> checkQueue = new CollapseList<CollapseData>();
		final float baseCollapse = 55f;
		final float incrementChance = 5f;
		final float incrementChanceDiag = 2.5f;

		int[] rockLayer = fillBlock != null && fillBlock.blockMaterial == Material.lava ? TFC_Climate.getRockLayer(i, j, k, 2) : 
			TFC_Climate.getRockLayer(i, j, k, TFC_Core.getRockLayerFromHeight(world, i, j, k));

		int worldX;
		int worldY;
		int worldZ;
		int localX;
		int localY;
		int localZ;
		int id;
		//Now we add each of the blocks around it so that the initial collapse tries to propogate in each direction
		checkQueue.add(new CollapseData(new ByteCoord(0,-1,0), 100, TFCDirection.NULL));

		while(checkQueue.peek() != null)
		{
			CollapseData block = checkQueue.peek();	
			worldX = block.coords.x + i;
			worldY = block.coords.y + j;
			worldZ = block.coords.z + k;
			localX = block.coords.x;
			localY = block.coords.y;
			localZ = block.coords.z;
			id = world.getBlockId(worldX, worldY, worldZ);

			if(!checkedmap.contains(block) && TFC_Core.isGround(id) && 
					world.rand.nextFloat() < block.collapseChance/100f)
			{
				checks++;
				//System.out.println("Number of block checks: " + checks + " | Queue Length: " + checkQueue.size());

				map.add(block.coords);

				if(checkQueue.size() < 500)
					switch(block.direction)
					{
					case NORTH:
					{
						checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX + 0, localY + 0, localZ - 1), block.collapseChance - incrementChance, TFCDirection.NORTH));
						checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX + 1, localY + 0, localZ + 0), block.collapseChance - incrementChance, TFCDirection.EAST));
						checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX - 1, localY + 0, localZ + 0), block.collapseChance - incrementChance, TFCDirection.WEST));
						break;
					}
					case SOUTH:
					{
						checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX + 0, localY + 0, localZ + 1), block.collapseChance - incrementChance, TFCDirection.SOUTH));
						checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX + 1, localY + 0, localZ + 0), block.collapseChance - incrementChance, TFCDirection.EAST));
						checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX - 1, localY + 0, localZ + 0), block.collapseChance - incrementChance, TFCDirection.WEST));
						break;
					}
					case EAST:
					{
						checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX + 0, localY + 0, localZ + 1), block.collapseChance - incrementChance, TFCDirection.SOUTH));
						checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX + 1, localY + 0, localZ + 0), block.collapseChance - incrementChance, TFCDirection.EAST));
						checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX + 0, localY + 0, localZ - 1), block.collapseChance - incrementChance, TFCDirection.NORTH));
						break;
					}
					case WEST:
					{
						checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX + 0, localY + 0, localZ + 1), block.collapseChance - incrementChance, TFCDirection.SOUTH));
						checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX - 1, localY + 0, localZ + 0), block.collapseChance - incrementChance, TFCDirection.WEST));
						checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX + 0, localY + 0, localZ - 1), block.collapseChance - incrementChance, TFCDirection.NORTH));
						break;
					}
					case SOUTHEAST:
					{
						checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX + 1, localY + 0, localZ - 1), block.collapseChance - incrementChanceDiag, TFCDirection.SOUTHEAST));
						checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX + 0, localY + 0, localZ + 1), block.collapseChance - incrementChance, TFCDirection.SOUTH));
						checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX + 1, localY + 0, localZ + 0), block.collapseChance - incrementChance, TFCDirection.EAST));
						break;
					}
					case SOUTHWEST:
					{
						checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX - 1, localY + 0, localZ - 1), block.collapseChance - incrementChanceDiag, TFCDirection.SOUTHWEST));
						checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX + 0, localY + 0, localZ + 1), block.collapseChance - incrementChance, TFCDirection.SOUTH));
						checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX - 1, localY + 0, localZ + 0), block.collapseChance - incrementChance, TFCDirection.WEST));
						break;
					}
					case NORTHEAST:
					{
						checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX + 1, localY + 0, localZ + 1), block.collapseChance - incrementChanceDiag, TFCDirection.NORTHEAST));
						checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX + 1, localY + 0, localZ + 0), block.collapseChance - incrementChance, TFCDirection.EAST));
						checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX + 0, localY + 0, localZ - 1), block.collapseChance - incrementChance, TFCDirection.NORTH));
						break;
					}
					case NORTHWEST:
					{
						checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX - 1, localY + 0, localZ + 1), block.collapseChance - incrementChanceDiag, TFCDirection.NORTHWEST));
						checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX + 0, localY + 0, localZ - 1), block.collapseChance - incrementChance, TFCDirection.NORTH));
						checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX - 1, localY + 0, localZ + 0), block.collapseChance - incrementChance, TFCDirection.WEST));
						break;
					}
					default:
					{
						checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX + 1, localY + 0, localZ + 0), baseCollapse - incrementChance, TFCDirection.EAST));
						checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX - 1, localY + 0, localZ + 0), baseCollapse - incrementChance, TFCDirection.WEST));
						checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX + 1, localY + 0, localZ + 1), baseCollapse - incrementChanceDiag, TFCDirection.NORTHEAST));
						checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX + 1, localY + 0, localZ - 1), baseCollapse - incrementChanceDiag, TFCDirection.SOUTHEAST));
						checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX - 1, localY + 0, localZ + 1), baseCollapse - incrementChanceDiag, TFCDirection.NORTHWEST));
						checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX - 1, localY + 0, localZ - 1), baseCollapse - incrementChanceDiag, TFCDirection.SOUTHWEST));
						checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX + 0, localY + 0, localZ + 1), baseCollapse - incrementChance, TFCDirection.SOUTH));
						checkQueue.add(checkedmap, new CollapseData(new ByteCoord(localX + 0, localY + 0, localZ - 1), baseCollapse - incrementChance, TFCDirection.NORTH));
					}
					}
			}
			else if(block.collapseChance < 100)
				for(int d = 0; d <= poolDepthFromTop; d++)
					if(TFC_Core.isGround(world.getBlockId(worldX, worldY-d, worldZ)) && rockLayer[0] != -1)
						world.setBlock(worldX, worldY-d, worldZ, rockLayer[0], rockLayer[1], 2);
			checkedmap.add(block.coords);
			checkQueue.removeFirst();
		}


		return map;
	}
}
