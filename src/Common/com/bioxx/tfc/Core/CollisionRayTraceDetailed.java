package com.bioxx.tfc.Core;

import java.util.BitSet;
import java.util.List;

import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;

public class CollisionRayTraceDetailed
{
	public static List<Object[]> rayTraceSubBlocks(Vec3 player, Vec3 view, int x, int y, int z, List<Object[]> returns, BitSet data, int d)
	{
		float div = 1f / d;

		for(int subX = 0; subX < d; subX++)
		{
			for(int subZ = 0; subZ < d; subZ++)
			{
				for(int subY = 0; subY < d; subY++)
				{
					if(data.get((subX * d + subZ)*d + subY))
					{
						float minX = subX * div;
						float maxX = minX + div;
						float minY = subY * div;
						float maxY = minY + div;
						float minZ = subZ * div;
						float maxZ = minZ + div;

						Object[] ret = rayTraceBound(AxisAlignedBB.getBoundingBox(minX, minY, minZ, maxX, maxY, maxZ), x, y, z, player, view);
						if (ret != null)
							returns.add(new Object[] {ret[0],ret[1],ret[2], subX, subY, subZ});
					}
				}
			}
		}
		return returns;
	}
	
	public static Object[] rayTraceBound(AxisAlignedBB bound, int x, int y, int z, Vec3 player, Vec3 view) {
		Vec3 minX = player.getIntermediateWithXValue(view, bound.minX);
		Vec3 maxX = player.getIntermediateWithXValue(view, bound.maxX);
		Vec3 minY = player.getIntermediateWithYValue(view, bound.minY);
		Vec3 maxY = player.getIntermediateWithYValue(view, bound.maxY);
		Vec3 minZ = player.getIntermediateWithZValue(view, bound.minZ);
		Vec3 maxZ = player.getIntermediateWithZValue(view, bound.maxZ);
		
		if (!isVecInsideYZBounds(bound, minX))
			minX = null;
		if (!isVecInsideYZBounds(bound, maxX))
			maxX = null;
		if (!isVecInsideXZBounds(bound, minY))
			minY = null;
		if (!isVecInsideXZBounds(bound, maxY))
			maxY = null;
		if (!isVecInsideXYBounds(bound, minZ))
			minZ = null;
		if (!isVecInsideXYBounds(bound, maxZ))
			maxZ = null;

		Vec3 tracedBound = null;
		if (minX != null && (tracedBound == null || player.distanceTo(minX) < player.distanceTo(tracedBound)))
			tracedBound = minX;
		if (maxX != null && (tracedBound == null || player.distanceTo(maxX) < player.distanceTo(tracedBound)))
			tracedBound = maxX;
		if (minY != null && (tracedBound == null || player.distanceTo(minY) < player.distanceTo(tracedBound)))
			tracedBound = minY;
		if (maxY != null && (tracedBound == null || player.distanceTo(maxY) < player.distanceTo(tracedBound)))
			tracedBound = maxY;
		if (minZ != null && (tracedBound == null || player.distanceTo(minZ) < player.distanceTo(tracedBound)))
			tracedBound = minZ;
		if (maxZ != null && (tracedBound == null || player.distanceTo(maxZ) < player.distanceTo(tracedBound)))
			tracedBound = maxZ;
		if (tracedBound == null)
			return null;

		byte side = -1;
		if (tracedBound == minX)
			side = 4;
		if (tracedBound == maxX)
			side = 5;
		if (tracedBound == minY)
			side = 0;
		if (tracedBound == maxY)
			side = 1;
		if (tracedBound == minZ)
			side = 2;
		if (tracedBound == maxZ)
			side = 3;

		return new Object[] { tracedBound, side, player.distanceTo(tracedBound) };
	}

	private static boolean isVecInsideYZBounds(AxisAlignedBB bound, Vec3 Vec3) {
		if (Vec3 == null)
			return false;
		else
			return Vec3.yCoord >= bound.minY && Vec3.yCoord <= bound.maxY && Vec3.zCoord >= bound.minZ && Vec3.zCoord <= bound.maxZ;
	}

	private static boolean isVecInsideXZBounds(AxisAlignedBB bound, Vec3 Vec3) {
		if (Vec3 == null)
			return false;
		else
			return Vec3.xCoord >= bound.minX && Vec3.xCoord <= bound.maxX && Vec3.zCoord >= bound.minZ && Vec3.zCoord <= bound.maxZ;
	}

	private static boolean isVecInsideXYBounds(AxisAlignedBB bound, Vec3 Vec3) {
		if (Vec3 == null)
			return false;
		else
			return Vec3.xCoord >= bound.minX && Vec3.xCoord <= bound.maxX && Vec3.yCoord >= bound.minY && Vec3.yCoord <= bound.maxY;
	}
}
