package com.bioxx.tfc.Core;

import java.util.BitSet;
import java.util.List;

import com.bioxx.tfc.Blocks.BlockWoodConstruct;
import com.bioxx.tfc.TileEntities.TileEntityWoodConstruct;

import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class CollisionRayTracePlanks 
{
	public static List<Object[]> rayTracePlanks(BlockWoodConstruct construct, Vec3 player, Vec3 view, int i, int j, int k, List<Object[]> returns, BitSet data, TileEntityWoodConstruct te) {

		int d = te.PlankDetailLevel;
		int dd = (d * d);
		float div = 1f / d;

		for(int x = 0; x < dd; x++)
		{
			if(te.data.get(x))
			{
				float minX = 0;
				float maxX = 1;
				float minY = div * (x & 7);
				float maxY = minY + div;
				float minZ = div * (x >> 3);
				float maxZ = minZ + div;

				Object[] ret = construct.rayTraceBound(AxisAlignedBB.getBoundingBox(minX, minY, minZ, maxX, maxY, maxZ), i, j, k, player, view);
				if (ret != null)
					returns.add(new Object[] {ret[0],ret[1],ret[2], x});
			}
		}

		for(int y = 0; y < dd; y++)
		{
			if(te.data.get(y+dd))
			{
				float minX = div * (y & 7);
				float maxX = minX + div;
				float minY = 0;
				float maxY = 1;
				float minZ = div * (y >> 3);
				float maxZ = minZ + div;

				Object[] ret = construct.rayTraceBound(AxisAlignedBB.getBoundingBox(minX, minY, minZ, maxX, maxY, maxZ), i, j, k, player, view);
				if (ret != null)
					returns.add(new Object[] {ret[0],ret[1],ret[2], y + dd});
			}
		}

		for(int z = 0; z < dd; z++)
		{
			if(data.get(z+(dd*2)))
			{
				float minX = div * (z & 7);
				float maxX = minX + div;
				float minY = div * (z >> 3);
				float maxY = minY + div;
				float minZ = 0;
				float maxZ = 1;

				Object[] ret = construct.rayTraceBound( AxisAlignedBB.getBoundingBox(minX, minY, minZ, maxX, maxY, maxZ), i, j, k, player, view);
				if (ret != null)
					returns.add(new Object[] {ret[0],ret[1],ret[2], z + (dd*2)});
			}
		}
		return returns;
	}

	public static List<Object[]> collisionRayTracerX(BlockWoodConstruct block, World world, Vec3 player, Vec3 view, int i, int j, int k, List<Object[]> returns)
	{
		int d = TileEntityWoodConstruct.PlankDetailLevel;
		int dd = (d * d);
		float div = 1f / d;
		
			for (int index = 0; index < dd; index++) 
			{
				float minX = i + 0;
				float maxX = i + 1;
				float minY = j + div * (index & 7);
				float maxY = j + minX + div;
				float minZ = k + div * (index >> 3);
				float maxZ = k + minZ + div;
					
				Object[] ret = block.rayTraceBound(AxisAlignedBB.getBoundingBox(minX,minY,minZ,maxX,maxY,maxZ), i, j, k, player, view);
				if (ret != null)
					returns.add(new Object[] {ret[0], ret[1], ret[2], index });
			}
		return returns;
	}
	
	public static List<Object[]> collisionRayTracerY(BlockWoodConstruct block, World world, Vec3 player, Vec3 view, int i, int j, int k, List<Object[]> returns)
	{
		int d = TileEntityWoodConstruct.PlankDetailLevel;
		int dd = (d * d);
		float div = 1f / d;
		
			for (int index = dd; index < dd*2; index++) 
			{
				float minX = i + div * (index & 7);
				float maxX = i + minX + div;
				float minY = j + 0;
				float maxY = j + 1;
				float minZ = k + div * (index >> 3);
				float maxZ = k + minZ + div;
					
				Object[] ret = block.rayTraceBound(AxisAlignedBB.getBoundingBox(minX,minY,minZ,maxX,maxY,maxZ), i, j, k, player, view);
				if (ret != null)
					returns.add(new Object[] {ret[0], ret[1], ret[2], index });
			}
		return returns;
	}
	
	public static List<Object[]> collisionRayTracerZ(BlockWoodConstruct block, World world, Vec3 player, Vec3 view, int i, int j, int k, List<Object[]> returns)
	{
		int d = TileEntityWoodConstruct.PlankDetailLevel;
		int dd = (d * d);
		float div = 1f / d;
		
			for (int index = dd*2; index < dd*3; index++) 
			{
				float minX = i + div * (index & 7);
				float maxX = i + minX + div;
				float minY = j + div * (index >> 3);
				float maxY = j + minY + div;
				float minZ = k + 0;
				float maxZ = k + 1;
					
				Object[] ret = block.rayTraceBound(AxisAlignedBB.getBoundingBox(minX,minY,minZ,maxX,maxY,maxZ), i, j, k, player, view);
				if (ret != null) 
					returns.add(new Object[] {ret[0], ret[1], ret[2], index });
			}
		return returns;
	}

	public static List<Object[]> collisionRayTracer(BlockWoodConstruct construct, World world, Vec3 player, Vec3 view, int x, int y, int z, List<Object[]> returns)
	{
		/*
		 * UP
		 */
		returns = collisionRayTracerY(
				construct,
				world,
				player,
				view,
				x,
				y - 1,
				z,
				returns);
		/*
		 * DOWN
		 */
		returns = collisionRayTracerY(
				construct,
				world,
				player,
				view,
				x,
				y + 1,
				z,
				returns);
		/*
		 * -X
		 */
		returns = collisionRayTracerX(
				construct,
				world,
				player,
				view,
				x - 1,
				y,
				z,
				returns);
		/*
		 * +X
		 */
		returns = collisionRayTracerX(
				construct,
				world,
				player,
				view,
				x + 1,
				y,
				z,
				returns);
		/*
		 * -Z
		 */
		returns = collisionRayTracerZ(
				construct,
				world,
				player,
				view,
				x,
				y,
				z - 1,
				returns);
		/*
		 * +Z
		 */
		returns = collisionRayTracerZ(
				construct,
				world,
				player,
				view,
				x,
				y,
				z + 1,
				returns);
		return returns;
	}
}
