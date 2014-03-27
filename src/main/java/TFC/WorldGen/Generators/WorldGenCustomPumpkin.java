package TFC.WorldGen.Generators;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import TFC.TFCBlocks;

public class WorldGenCustomPumpkin extends WorldGenerator
{
	public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
	{
		for (int var6 = 0; var6 < 64; ++var6)
		{
			int var7 = par3 + par2Random.nextInt(8) - par2Random.nextInt(8);
			int var8 = par4 + par2Random.nextInt(4) - par2Random.nextInt(4);
			int var9 = par5 + par2Random.nextInt(8) - par2Random.nextInt(8);
			Block var5 = par1World.getBlock(var7, var8 - 1, var9);
			if (par1World.isAirBlock(var7, var8, var9) && 
					(var5 == Blocks.grass || var5 == TFCBlocks.Grass || var5 == TFCBlocks.Grass2 ||
					var5 == TFCBlocks.Dirt || var5 == TFCBlocks.Dirt ||
					var5 == TFCBlocks.ClayGrass || var5 == TFCBlocks.ClayGrass2 ||
					var5 == TFCBlocks.PeatGrass || var5 == TFCBlocks.Peat) && Blocks.pumpkin.canPlaceBlockAt(par1World, var7, var8, var9))
			{
				par1World.setBlock(var7, var8, var9, Blocks.pumpkin, par2Random.nextInt(4), 0x2);
			}
		}

		return true;
	}
}
