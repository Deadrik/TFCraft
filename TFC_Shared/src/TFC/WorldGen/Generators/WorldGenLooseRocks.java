package TFC.WorldGen.Generators;

import java.util.Random;

import TFC.*;
import TFC.WorldGen.BiomeDecoratorTFC;

import cpw.mods.fml.common.IWorldGenerator;
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
import net.minecraft.world.gen.feature.*;

public class WorldGenLooseRocks implements IWorldGenerator
{


	public WorldGenLooseRocks()
	{

	}

	private boolean generate(World world, Random random, int var8, int var9, int var10)
	{
		if ((world.isAirBlock(var8, var9+1, var10) || world.getBlockId(var8, var9+1, var10) == Block.snow.blockID || 
				world.getBlockId(var8, var9+1, var10) == Block.tallGrass.blockID) && 
				(world.getBlockMaterial(var8, var9, var10) == Material.grass || 
				world.getBlockMaterial(var8, var9, var10) == Material.rock) && world.isBlockOpaqueCube(var8, var9, var10))
		{
			world.setBlock(var8, var9+1, var10, TFCBlocks.LooseRock.blockID, 0, 0x2);

		}

		return true;
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) 
	{
//		chunkX *= 16;
//		chunkZ *= 16;
		BiomeGenBase biome = world.getBiomeGenForCoords(chunkX, chunkZ);
		

        for (int var2 = 0; var2 < ((BiomeDecoratorTFC)biome.theBiomeDecorator).looseRocksPerChunk; var2++)
        {
            int var7 = chunkX + random.nextInt(16) + 8;
            int var3 = chunkZ + random.nextInt(16) + 8;

            generate(world, random, var7, world.getTopSolidOrLiquidBlock(var7, var3)-1, var3);
        }
		
	}
}
