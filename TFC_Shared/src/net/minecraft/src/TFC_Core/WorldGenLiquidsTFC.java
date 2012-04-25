package net.minecraft.src.TFC_Core;

import java.util.Random;

import net.minecraft.src.*;

public class WorldGenLiquidsTFC extends WorldGenerator
{
	private int liquidBlockId;

	public WorldGenLiquidsTFC(int i)
	{
		liquidBlockId = i;
	}

	public boolean generate(World world, Random random, int i, int j, int k)
	{
		if (world.getBlockId(i, j + 1, k) != mod_TFC_Core.terraStoneIgIn.blockID && world.getBlockId(i, j + 1, k) != mod_TFC_Core.terraStoneSed.blockID &&
				world.getBlockId(i, j + 1, k) != mod_TFC_Core.terraStoneIgEx.blockID &&world.getBlockId(i, j + 1, k) != mod_TFC_Core.terraStoneMM.blockID)
		{
			return false;
		}
		if (world.getBlockId(i, j - 1, k) != mod_TFC_Core.terraStoneIgIn.blockID && world.getBlockId(i, j - 1, k) != mod_TFC_Core.terraStoneSed.blockID &&
				world.getBlockId(i, j - 1, k) != mod_TFC_Core.terraStoneIgEx.blockID &&world.getBlockId(i, j - 1, k) != mod_TFC_Core.terraStoneMM.blockID)
		{
			return false;
		}
		if (world.getBlockId(i, j, k) != 0 && world.getBlockId(i, j, k) != mod_TFC_Core.terraStoneIgIn.blockID && world.getBlockId(i, j, k) != mod_TFC_Core.terraStoneSed.blockID &&
				world.getBlockId(i, j, k) != mod_TFC_Core.terraStoneIgEx.blockID &&world.getBlockId(i, j, k) != mod_TFC_Core.terraStoneMM.blockID)
		{
			return false;
		}
		int l = 0;
		if (world.getBlockId(i - 1, j, k) == mod_TFC_Core.terraStoneIgIn.blockID && world.getBlockId(i - 1, j, k) == mod_TFC_Core.terraStoneSed.blockID &&
				world.getBlockId(i - 1, j, k) == mod_TFC_Core.terraStoneIgEx.blockID &&world.getBlockId(i - 1, j, k) == mod_TFC_Core.terraStoneMM.blockID)
		{
			l++;
		}
		if (world.getBlockId(i + 1, j, k) == mod_TFC_Core.terraStoneIgIn.blockID && world.getBlockId(i + 1, j, k) == mod_TFC_Core.terraStoneSed.blockID &&
				world.getBlockId(i + 1, j, k) == mod_TFC_Core.terraStoneIgEx.blockID &&world.getBlockId(i + 1, j, k) == mod_TFC_Core.terraStoneMM.blockID)
		{
			l++;
		}
		if (world.getBlockId(i, j, k - 1) == mod_TFC_Core.terraStoneIgIn.blockID && world.getBlockId(i, j, k - 1) == mod_TFC_Core.terraStoneSed.blockID &&
				world.getBlockId(i, j, k - 1) == mod_TFC_Core.terraStoneIgEx.blockID &&world.getBlockId(i, j, k - 1) == mod_TFC_Core.terraStoneMM.blockID)
		{
			l++;
		}
		if (world.getBlockId(i, j, k + 1) == mod_TFC_Core.terraStoneIgIn.blockID && world.getBlockId(i, j, k + 1) == mod_TFC_Core.terraStoneSed.blockID &&
				world.getBlockId(i, j, k + 1) == mod_TFC_Core.terraStoneIgEx.blockID &&world.getBlockId(i, j, k + 1) == mod_TFC_Core.terraStoneMM.blockID)
		{
			l++;
		}
		int i1 = 0;
		if (world.isAirBlock(i - 1, j, k))
		{
			i1++;
		}
		if (world.isAirBlock(i + 1, j, k))
		{
			i1++;
		}
		if (world.isAirBlock(i, j, k - 1))
		{
			i1++;
		}
		if (world.isAirBlock(i, j, k + 1))
		{
			i1++;
		}
		if (l == 3 && i1 == 1)
		{
			world.setBlockWithNotify(i, j, k, liquidBlockId);
			world.scheduledUpdatesAreImmediate = true;
			Block.blocksList[liquidBlockId].updateTick(world, i, j, k, random);
			world.scheduledUpdatesAreImmediate = false;
		}
		return true;
	}
}
