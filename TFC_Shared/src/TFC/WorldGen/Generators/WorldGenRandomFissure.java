package TFC.WorldGen.Generators;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import TFC.TFCBlocks;
import TFC.Core.TFC_Climate;
import TFC.WorldGen.TFCBiome;
import cpw.mods.fml.common.IWorldGenerator;

public class WorldGenRandomFissure implements IWorldGenerator
{
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) 
	{
		chunkX *= 16;
		chunkZ *= 16;

		int startX = chunkX + random.nextInt(16) + 8;
		int startZ = chunkZ + random.nextInt(16) + 8;
		int startY = world.getTopSolidOrLiquidBlock(startX, startZ)-1;

		int stability = TFC_Climate.getStability(startX, startZ);

		//Gen Lava fissures
		if(random.nextInt(50-(25*stability)) == 0)
		{
			new GenFissure(Block.lavaStill).setUnderground(true, 20).generate(world, random, startX, startY, startZ);
		}

		//Gen Underground Hot Spring
		if(random.nextInt(70-(35*stability)) == 0)
		{
			if(world.getBiomeGenForCoords(startX, startZ) != TFCBiome.ocean)
				new GenFissure(TFCBlocks.HotWaterStill).setUnderground(true, 50).generate(world, random, startX, startY, startZ);
		}

		if(random.nextInt(40) == 0)
		{
			if(world.getBiomeGenForCoords(startX, startZ) != TFCBiome.ocean)
				new GenFissure(TFCBlocks.FreshWaterStill).generate(world, random, startX, startY, startZ);
		}
	}
}
