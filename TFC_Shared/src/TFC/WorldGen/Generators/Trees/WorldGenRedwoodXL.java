/**
 * Copyright (c) Scott Killen and MisterFiber, 2012
 */
package TFC.WorldGen.Generators.Trees;

import java.util.Random;

import TFC.*;
import TFC.Core.TFC_Core;
import TFC.WorldGen.TFCBiome;
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
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenRedwoodXL extends WorldGenerator {
	final int	blockLeaf, metaLeaf, blockWood, metaWood;

	public WorldGenRedwoodXL(boolean doNotify) {
		super(doNotify);
		blockLeaf = Block.leaves.blockID;
		metaLeaf = 9;
		blockWood = Block.wood.blockID;
		metaWood = 9;
	}

	@Override
	public boolean generate(World world, Random rand, int x, int y,
			int z)
	{
		final int height = rand.nextInt(20) + 22;
		final int j = 5 + rand.nextInt(12);
		final int k = height - j;
		final int l = 4 + rand.nextInt(6);

		if (y < 1 || y + height + 1 > 256) return false;

		if (!TFC_Core.isSoil(world.getBlockId(x, y - 1, z)) || !TFC_Core.isSoil(world.getBlockId(x-1, y - 1, z)) || 
				!TFC_Core.isSoil(world.getBlockId(x, y - 1, z-1)) || !TFC_Core.isSoil(world.getBlockId(x-1, y - 1, z-1)) || y >= 180) return false;

		for (int y1 = y; y1 <= y + 1 + height; y1++) {
			int k1 = 1;

			if (y1 - y < j)
				k1 = 0;
			else
				k1 = l;

			for (int x1 = x - k1; x1 <= x + k1; x1++)
				for (int z1 = z - k1; z1 <= z + k1; z1++)
					if (y1 >= 0 && y1 < 256) {
						final int id1 = world.getBlockId(x1, y1, z1);

						if (Block.blocksList[id1] != null
								&& Block.blocksList[id1].isLeaves(
										world, x1, y1, z1))
							return false;
					} else
						return false;
		}

		int meta = TFCBiome.getSurfaceRockLayer(world, x, z);
		int dirtID =  TFC_Core.getTypeForDirt(meta);
		int dirtMeta =  TFC_Core.getSoilMetaFromStone(dirtID, meta);
		world.setBlockAndMetadataWithNotify(x, y - 1, z, dirtID, meta, 0);
		world.setBlockAndMetadataWithNotify(x - 1, y - 1, z, dirtID, meta, 0);
		world.setBlockAndMetadataWithNotify(x, y - 1, z - 1, dirtID, meta, 0);
		world.setBlockAndMetadataWithNotify(x - 1, y - 1, z - 1, dirtID, meta, 0);
		int l1 = rand.nextInt(2);
		int j2 = 1;
		boolean flag1 = false;

		for (int i3 = 0; i3 <= k; i3++) {
			final int y1 = y + height - i3;

			for (int x1 = x - l1; x1 <= x + l1; x1++) {
				final int k4 = x1 - x;

				for (int z1 = z - l1; z1 <= z + l1; z1++) {
					final int i5 = z1 - z;

					final Block block = Block.blocksList[world
							.getBlockId(x1, y1, z1)];

					if ((Math.abs(k4) != l1 || Math.abs(i5) != l1 || l1 <= 0)
							&& (block == null || block
									.canBeReplacedByLeaves(world, x1,
											y1, z1)))
					{
						setBlockAndMetadata(world, x1, y1, z1,
								blockLeaf, metaLeaf);
						setBlockAndMetadata(world, x1 - 1, y1, z1,
								blockLeaf, metaLeaf);
						setBlockAndMetadata(world, x1, y1, z1 - 1,
								blockLeaf, metaLeaf);
						setBlockAndMetadata(world, x1 - 1, y1, z1 - 1,
								blockLeaf, metaLeaf);
					}
				}
			}

			if (l1 >= j2) {
				l1 = flag1 ? 1 : 0;
				flag1 = true;

				if (++j2 > l) j2 = l;
			} else
				l1++;
		}

		final int j3 = rand.nextInt(3);

		for (int y1 = 0; y1 < height - j3; y1++) {
			final int j4 = world.getBlockId(x, y + y1, z);

			if (Block.blocksList[j4] == null
					|| Block.blocksList[j4].isLeaves(world, x, y + y1,z) && 
					Block.blocksList[j4].canBeReplacedByLeaves(world, x, y + y1, z))
			{
				setBlockAndMetadata(world, x, y + y1, z, blockWood,
						metaWood);
				setBlockAndMetadata(world, x, y + y1, z, blockWood,
						metaWood);
				setBlockAndMetadata(world, x - 1, y + y1, z, blockWood,
						metaWood);
				setBlockAndMetadata(world, x, y + y1, z - 1, blockWood,
						metaWood);
				setBlockAndMetadata(world, x - 1, y + y1, z - 1,
						blockWood, metaWood);
			}
		}

		return true;
	}
}
