package TFC.Core;

import java.util.BitSet;
import java.util.List;

import TFC.Blocks.BlockDetailed;
import TFC.Blocks.BlockWoodConstruct;
import TFC.TileEntities.TileEntityDetailed;
import TFC.TileEntities.TileEntityWoodConstruct;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.Vec3;
import net.minecraft.src.World;

public class CollisionRayTraceDetailed 
{

	public static List<Object[]> rayTraceSubBlocks(BlockDetailed construct, Vec3 player, Vec3 view, int i, int j, int k, 
			List<Object[]> returns, BitSet data, TileEntityDetailed te) {

		int d = 8;
		int dd = (d * d);

		float div = 1f / d;

		for(int subX = 0; subX < 8; subX++)
		{
			for(int subZ = 0; subZ < 8; subZ++)
			{
				for(int subY = 0; subY < 8; subY++)
				{
					if(te.data.get((subX * 8 + subZ)*8 + subY))
					{        		
						float minX = subX * div;
						float maxX = minX + div;
						float minY = subY * div;
						float maxY = minY + div;
						float minZ = subZ * div;
						float maxZ = minZ + div;

						Object[] ret = construct.rayTraceBound(
								AxisAlignedBB.getBoundingBox(minX, minY, minZ, maxX, maxY, maxZ),
								i,
								j,
								k,
								player,
								view);

						if (ret != null) 
						{
							returns.add(new Object[] {ret[0],ret[1],ret[2], subX, subY, subZ});
						}
					}
				}
			}
		}

		return returns;
	}

	public static List<Object[]> collisionRayTracerX(BlockDetailed block, World world, Vec3 player, Vec3 view, int i, int j, int k, int subX, List<Object[]> returns) 
	{
		int d = 8;
		int dd = (d * d);

		float div = 1f / d;

		for(int subZ = 0; subZ < 8; subZ++)
		{
			for(int subY = 0; subY < 8; subY++)
			{     		
				float minX = subX * div;
				float maxX = minX + div;
				float minY = subY * div;
				float maxY = minY + div;
				float minZ = subZ * div;
				float maxZ = minZ + div;

				Object[] ret = block.rayTraceBound(
						AxisAlignedBB.getBoundingBox(minX,minY,minZ,maxX,maxY,maxZ), i, j, k, player, view);

				if (ret != null) 
				{
					returns.add(new Object[] {
							ret[0], ret[1], ret[2], subX, subY, subZ});
				}
			}
		}
		return returns;
	}

	public static List<Object[]> collisionRayTracerY(BlockDetailed block, World world, Vec3 player, Vec3 view, int i, int j, int k, int subY, List<Object[]> returns) 
	{
		int d = 8;
		int dd = (d * d);

		float div = 1f / d;

		for(int subX = 0; subX < 8; subX++)
		{
			for(int subZ = 0; subZ < 8; subZ++)
			{     		
				float minX = subX * div;
				float maxX = minX + div;
				float minY = subY * div;
				float maxY = minY + div;
				float minZ = subZ * div;
				float maxZ = minZ + div;

				Object[] ret = block.rayTraceBound(
						AxisAlignedBB.getBoundingBox(minX,minY,minZ,maxX,maxY,maxZ), i, j, k, player, view);

				if (ret != null) 
				{
					returns.add(new Object[] {
							ret[0], ret[1], ret[2], subX, subY, subZ});
				}
			}
		}
		return returns;
	}

	public static List<Object[]> collisionRayTracerZ(BlockDetailed block, World world, Vec3 player, Vec3 view, int i, int j, int k, int subZ, List<Object[]> returns) 
	{
		int d = 8;
		int dd = (d * d);

		float div = 1f / d;

		for(int subX = 0; subX < 8; subX++)
		{
			for(int subY = 0; subY < 8; subY++)
			{     		
				float minX = subX * div;
				float maxX = minX + div;
				float minY = subY * div;
				float maxY = minY + div;
				float minZ = subZ * div;
				float maxZ = minZ + div;

				Object[] ret = block.rayTraceBound(
						AxisAlignedBB.getBoundingBox(minX,minY,minZ,maxX,maxY,maxZ), i, j, k, player, view);

				if (ret != null) 
				{
					returns.add(new Object[] {
							ret[0], ret[1], ret[2], subX, subY, subZ});
				}
			}
		}
		return returns;
	}

	public static List<Object[]> collisionRayTracer(BlockDetailed blockDetailed, World world, Vec3 player, Vec3 view, int x, int y, int z, List<Object[]> returns) 
	{
		/*
		 * UP
		 */
		returns = collisionRayTracerY(
				blockDetailed,
				world,
				player,
				view,
				x,
				y - 1,
				z,
				0,
				returns);
		/*
		 * DOWN
		 */
		returns = collisionRayTracerY(
				blockDetailed,
				world,
				player,
				view,
				x,
				y + 1,
				z,
				7,
				returns);
		/*
		 * -X
		 */
		returns = collisionRayTracerX(
				blockDetailed,
				world,
				player,
				view,
				x - 1,
				y,
				z,
				0,
				returns);
		/*
		 * +X
		 */
		returns = collisionRayTracerX(
				blockDetailed,
				world,
				player,
				view,
				x + 1,
				y,
				z,
				7,
				returns);
		/*
		 * -Z
		 */
		returns = collisionRayTracerZ(
				blockDetailed,
				world,
				player,
				view,
				x,
				y,
				z - 1,
				0,
				returns);
		/*
		 * +Z
		 */
		returns = collisionRayTracerZ(
				blockDetailed,
				world,
				player,
				view,
				x,
				y,
				z + 1,
				7,
				returns);
		return returns;
	}
}
