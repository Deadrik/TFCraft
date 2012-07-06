package TFC.WorldGen;

import java.util.Random;

import net.minecraft.src.*;

public class WorldGenLooseRocks extends WorldGenerator
{


	public WorldGenLooseRocks()
	{

	}

	public boolean generate(World world, Random random, int var8, int var9, int var10)
	{
		if ((world.isAirBlock(var8, var9+1, var10) || world.getBlockId(var8, var9+1, var10) == Block.snow.blockID || 
				world.getBlockId(var8, var9+1, var10) == Block.tallGrass.blockID) && 
				(world.getBlockMaterial(var8, var9, var10) == Material.grass || 
				world.getBlockMaterial(var8, var9, var10) == Material.rock) && world.isBlockOpaqueCube(var8, var9, var10))
		{
			world.setBlock(var8, var9+1, var10, mod_TFC_Core.LooseRock.blockID);

		}

		return true;
	}
}
