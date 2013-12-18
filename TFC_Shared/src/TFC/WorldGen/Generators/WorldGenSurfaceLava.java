package TFC.WorldGen.Generators;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import TFC.API.Enums.TFCDirection;
import TFC.API.Util.ByteCoord;
import TFC.API.Util.CollapseData;
import TFC.API.Util.CollapseList;
import TFC.Core.TFC_Climate;
import TFC.Core.TFC_Core;
import cpw.mods.fml.common.IWorldGenerator;

public class WorldGenSurfaceLava implements IWorldGenerator
{
	Random rand;
	int poolDepth;
	int creviceDepth = 1;
	public WorldGenSurfaceLava()
	{

	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) 
	{
		rand = random;

		if(rand.nextInt(35) != 0)
			return;

		chunkX *= 16;
		chunkZ *= 16;

		int x = chunkX + random.nextInt(16) + 8;
		int z = chunkZ + random.nextInt(16) + 8;
		int y = world.getHeightValue(x, z);


		if(random.nextInt(100) < 50)
			creviceDepth += 4 + random.nextInt(15);
		poolDepth = 1+random.nextInt(2);

		ArrayList<ByteCoord> map = getCollapseMap(world, x,y-creviceDepth,z);
		int[] rockLayer = TFC_Climate.getRockLayer(x, y, z, 2);
		boolean makeTunnel = map.size() > 10;
		for(ByteCoord b : map)
		{
			world.setBlock(x+b.x, y+b.y, z+b.z, 0);
			for(int d = 1; d <= poolDepth; d++)
				world.setBlock(x+b.x, y+b.y-d, z+b.z, Block.lavaStill.blockID);
			for(int d = 0; d <= creviceDepth; d++)
			{
				world.setBlock(x+b.x, y+b.y+d, z+b.z, 0);
				if(world.getBlockMaterial(x+b.x+1, y+b.y+d, z+b.z) != Material.air)
					world.setBlock(x+b.x+1, y+b.y+d, z+b.z, rockLayer[0], rockLayer[1], 2);
				if(world.getBlockMaterial(x+b.x-1, y+b.y+d, z+b.z) != Material.air)
					world.setBlock(x+b.x-1, y+b.y+d, z+b.z, rockLayer[0], rockLayer[1], 2);
				if(world.getBlockMaterial(x+b.x, y+b.y+d, z+b.z+1) != Material.air)
					world.setBlock(x+b.x, y+b.y+d, z+b.z+1, rockLayer[0], rockLayer[1], 2);
				if(world.getBlockMaterial(x+b.x, y+b.y+d, z+b.z-1) != Material.air)
					world.setBlock(x+b.x, y+b.y+d, z+b.z-1, rockLayer[0], rockLayer[1], 2);
			}
			world.setBlock(x+b.x, y+b.y-poolDepth-1, z+b.z, rockLayer[0], rockLayer[1], 2);
		}

		if(makeTunnel)
		{
			int xCoord = x, yCoord = y-poolDepth-1, zCoord = z;
			float downChance = 75;
			while(yCoord > 1)
			{
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

				world.setBlock(xCoord, yCoord, zCoord, Block.lavaStill.blockID);

				if(world.getBlockMaterial(xCoord+1, yCoord, zCoord) != Material.lava)
					world.setBlock(xCoord+1, yCoord, zCoord, rockLayer[0], rockLayer[1], 2);
				if(world.getBlockMaterial(xCoord-1, yCoord, zCoord) != Material.lava)
					world.setBlock(xCoord-1, yCoord, zCoord, rockLayer[0], rockLayer[1], 2);
				if(world.getBlockMaterial(xCoord, yCoord, zCoord+1) != Material.lava)
					world.setBlock(xCoord, yCoord, zCoord+1, rockLayer[0], rockLayer[1], 2);
				if(world.getBlockMaterial(xCoord, yCoord, zCoord-1) != Material.lava)
					world.setBlock(xCoord, yCoord, zCoord-1, rockLayer[0], rockLayer[1], 2);
			}
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

		int[] rockLayer = TFC_Climate.getRockLayer(i, j, k, 2);

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

			if(!checkedmap.contains(block) && 
					(TFC_Core.isSoil(id) || TFC_Core.isRawStone(id)) && 
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
				for(int d = 0; d <= poolDepth; d++)
					world.setBlock(worldX, worldY-d, worldZ, rockLayer[0], rockLayer[1], 2);
			checkedmap.add(block.coords);
			checkQueue.removeFirst();
		}


		return map;
	}
}
