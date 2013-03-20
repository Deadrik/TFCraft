package TFC.WorldGen.Generators;

import java.util.Random;

import TFC.*;
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
			world.setBlock(i, j, k, liquidBlockId);
			world.scheduledUpdatesAreImmediate = true;
			Block.blocksList[liquidBlockId].updateTick(world, i, j, k, random);
			world.scheduledUpdatesAreImmediate = false;
		}
		return true;
	}
}
