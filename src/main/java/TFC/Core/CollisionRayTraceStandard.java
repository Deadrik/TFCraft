package TFC.Core;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import TFC.API.ICustomCollision;

public class CollisionRayTraceStandard
{

	public static MovingObjectPosition collisionRayTrace(ICustomCollision b, World world, int x, int y, int z, Vec3 player, Vec3 view)
	{
		player = player.addVector(-x, -y, -z);
		view = view.addVector(-x, -y, -z);

		List<Object[]> returns = new ArrayList<Object[]>();

		//Creates all possible collisions and returns any that collide
		returns = CollisionRayTraceStandard.rayTraceSubBlocks(b, world, player, view, x, y, z, returns);

		if (!returns.isEmpty())
		{
			Object[] min = null;
			double distMin = 0;
			for (Object[] ret : returns)
			{
				double dist = (Double) ret[2];
				if (min == null || dist < distMin)
				{
					distMin = dist;
					min = ret;
				}
			}
			if (min != null)
			{

				((Block) b).setBlockBounds(0, 0, 0, 1, 1, 1);
				CollisionRayTraceStandard.rayTraceBound(
						AxisAlignedBB.getBoundingBox(((Block) b).getBlockBoundsMinX(), ((Block) b).getBlockBoundsMinY(), ((Block) b).getBlockBoundsMinZ(),
								((Block) b).getBlockBoundsMaxX(), ((Block) b).getBlockBoundsMaxY(), ((Block) b).getBlockBoundsMaxZ()), x, y, z, player, view);
				((Block) b).setBlockBounds(1, 1, 1, 1, 1, 1);

				return new MovingObjectPosition(x, y, z, (Byte) min[1], ((Vec3) min[0]).addVector(x, y, z));
			}
		}
		((Block) b).setBlockBounds(0, 0, 0, 1, 1, 1);

		return null;
	}

	public static Object[] rayTraceBound(AxisAlignedBB bound, int i, int j, int k, Vec3 player, Vec3 view)
	{
		Vec3 minX = player.getIntermediateWithXValue(view, bound.minX);
		Vec3 maxX = player.getIntermediateWithXValue(view, bound.maxX);
		Vec3 minY = player.getIntermediateWithYValue(view, bound.minY);
		Vec3 maxY = player.getIntermediateWithYValue(view, bound.maxY);
		Vec3 minZ = player.getIntermediateWithZValue(view, bound.minZ);
		Vec3 maxZ = player.getIntermediateWithZValue(view, bound.maxZ);
		if (!isVecInsideYZBounds(bound, minX))
		{
			minX = null;
		}
		if (!isVecInsideYZBounds(bound, maxX))
		{
			maxX = null;
		}
		if (!isVecInsideXZBounds(bound, minY))
		{
			minY = null;
		}
		if (!isVecInsideXZBounds(bound, maxY))
		{
			maxY = null;
		}
		if (!isVecInsideXYBounds(bound, minZ))
		{
			minZ = null;
		}
		if (!isVecInsideXYBounds(bound, maxZ))
		{
			maxZ = null;
		}
		Vec3 tracedBound = null;
		if (minX != null && (tracedBound == null || player.distanceTo(minX) < player.distanceTo(tracedBound)))
		{
			tracedBound = minX;
		}
		if (maxX != null && (tracedBound == null || player.distanceTo(maxX) < player.distanceTo(tracedBound)))
		{
			tracedBound = maxX;
		}
		if (minY != null && (tracedBound == null || player.distanceTo(minY) < player.distanceTo(tracedBound)))
		{
			tracedBound = minY;
		}
		if (maxY != null && (tracedBound == null || player.distanceTo(maxY) < player.distanceTo(tracedBound)))
		{
			tracedBound = maxY;
		}
		if (minZ != null && (tracedBound == null || player.distanceTo(minZ) < player.distanceTo(tracedBound)))
		{
			tracedBound = minZ;
		}
		if (maxZ != null && (tracedBound == null || player.distanceTo(maxZ) < player.distanceTo(tracedBound)))
		{
			tracedBound = maxZ;
		}
		if (tracedBound == null) { return null; }
		byte side = -1;
		if (tracedBound == minX)
		{
			side = 4;
		}
		if (tracedBound == maxX)
		{
			side = 5;
		}
		if (tracedBound == minY)
		{
			side = 0;
		}
		if (tracedBound == maxY)
		{
			side = 1;
		}
		if (tracedBound == minZ)
		{
			side = 2;
		}
		if (tracedBound == maxZ)
		{
			side = 3;
		}
		return new Object[] { tracedBound, side, player.distanceTo(tracedBound) };
	}

	public static List<Object[]> rayTraceSubBlocks(ICustomCollision b, World world, Vec3 player, Vec3 view, int i, int j, int k, List<Object[]> returns)
	{
		List<AxisAlignedBB> bblist = new ArrayList<AxisAlignedBB>();
		b.addCollisionBoxesToList(world, i, j, k, bblist);
		for (AxisAlignedBB o : bblist)
		{
			Object[] ret = CollisionRayTraceStandard.rayTraceBound(o, i, j, k, player, view);

			if (ret != null)
			{
				returns.add(new Object[] { ret[0], ret[1], ret[2] });
			}
		}

		return returns;
	}

	private static boolean isVecInsideYZBounds(AxisAlignedBB bound, Vec3 Vec3)
	{
		if (Vec3 == null)
		{
			return false;
		}
		else
		{
			return Vec3.yCoord >= bound.minY && Vec3.yCoord <= bound.maxY && Vec3.zCoord >= bound.minZ && Vec3.zCoord <= bound.maxZ;
		}
	}

	private static boolean isVecInsideXZBounds(AxisAlignedBB bound, Vec3 Vec3)
	{
		if (Vec3 == null)
		{
			return false;
		}
		else
		{
			return Vec3.xCoord >= bound.minX && Vec3.xCoord <= bound.maxX && Vec3.zCoord >= bound.minZ && Vec3.zCoord <= bound.maxZ;
		}
	}

	private static boolean isVecInsideXYBounds(AxisAlignedBB bound, Vec3 Vec3)
	{
		if (Vec3 == null)
		{
			return false;
		}
		else
		{
			return Vec3.xCoord >= bound.minX && Vec3.xCoord <= bound.maxX && Vec3.yCoord >= bound.minY && Vec3.yCoord <= bound.maxY;
		}
	}
}
