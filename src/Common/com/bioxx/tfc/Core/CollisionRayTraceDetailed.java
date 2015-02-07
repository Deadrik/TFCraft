package com.bioxx.tfc.Core;

import java.util.BitSet;
import java.util.List;

import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;

import com.bioxx.tfc.Blocks.BlockDetailed;
import com.bioxx.tfc.TileEntities.TEDetailed;

public class CollisionRayTraceDetailed
{
	public static List<Object[]> rayTraceSubBlocks(BlockDetailed construct, Vec3 player, Vec3 view, int i, int j, int k, List<Object[]> returns, BitSet data, TEDetailed te)
	{
		int d = 8;
		//int dd = (d * d);
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

						Object[] ret = construct.rayTraceBound(AxisAlignedBB.getBoundingBox(minX, minY, minZ, maxX, maxY, maxZ), i, j, k, player, view);
						if (ret != null)
							returns.add(new Object[] {ret[0],ret[1],ret[2], subX, subY, subZ});
					}
				}
			}
		}
		return returns;
	}
}
