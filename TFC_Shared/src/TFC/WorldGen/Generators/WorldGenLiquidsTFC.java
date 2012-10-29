package TFC.WorldGen.Generators;

import java.util.Random;

import TFC.*;


import net.minecraft.src.Block;
import net.minecraft.src.World;
import net.minecraft.src.WorldGenerator;

public class WorldGenLiquidsTFC extends WorldGenerator
{
	private int liquidBlockId;

	public WorldGenLiquidsTFC(int i)
	{
		liquidBlockId = i;
	}

	public boolean generate(World world, Random random, int i, int j, int k)
	{
		if (world.getBlockId(i, j + 1, k) != TFCBlocks.StoneIgIn.blockID && world.getBlockId(i, j + 1, k) != TFCBlocks.StoneSed.blockID &&
				world.getBlockId(i, j + 1, k) != TFCBlocks.StoneIgEx.blockID &&world.getBlockId(i, j + 1, k) != TFCBlocks.StoneMM.blockID)
		{
			return false;
		}
		if (world.getBlockId(i, j - 1, k) != TFCBlocks.StoneIgIn.blockID && world.getBlockId(i, j - 1, k) != TFCBlocks.StoneSed.blockID &&
				world.getBlockId(i, j - 1, k) != TFCBlocks.StoneIgEx.blockID &&world.getBlockId(i, j - 1, k) != TFCBlocks.StoneMM.blockID)
		{
			return false;
		}
		if (world.getBlockId(i, j, k) != 0 && world.getBlockId(i, j, k) != TFCBlocks.StoneIgIn.blockID && world.getBlockId(i, j, k) != TFCBlocks.StoneSed.blockID &&
				world.getBlockId(i, j, k) != TFCBlocks.StoneIgEx.blockID &&world.getBlockId(i, j, k) != TFCBlocks.StoneMM.blockID)
		{
			return false;
		}
		int l = 0;
		if (world.getBlockId(i - 1, j, k) == TFCBlocks.StoneIgIn.blockID && world.getBlockId(i - 1, j, k) == TFCBlocks.StoneSed.blockID &&
				world.getBlockId(i - 1, j, k) == TFCBlocks.StoneIgEx.blockID &&world.getBlockId(i - 1, j, k) == TFCBlocks.StoneMM.blockID)
		{
			l++;
		}
		if (world.getBlockId(i + 1, j, k) == TFCBlocks.StoneIgIn.blockID && world.getBlockId(i + 1, j, k) == TFCBlocks.StoneSed.blockID &&
				world.getBlockId(i + 1, j, k) == TFCBlocks.StoneIgEx.blockID &&world.getBlockId(i + 1, j, k) == TFCBlocks.StoneMM.blockID)
		{
			l++;
		}
		if (world.getBlockId(i, j, k - 1) == TFCBlocks.StoneIgIn.blockID && world.getBlockId(i, j, k - 1) == TFCBlocks.StoneSed.blockID &&
				world.getBlockId(i, j, k - 1) == TFCBlocks.StoneIgEx.blockID &&world.getBlockId(i, j, k - 1) == TFCBlocks.StoneMM.blockID)
		{
			l++;
		}
		if (world.getBlockId(i, j, k + 1) == TFCBlocks.StoneIgIn.blockID && world.getBlockId(i, j, k + 1) == TFCBlocks.StoneSed.blockID &&
				world.getBlockId(i, j, k + 1) == TFCBlocks.StoneIgEx.blockID &&world.getBlockId(i, j, k + 1) == TFCBlocks.StoneMM.blockID)
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
