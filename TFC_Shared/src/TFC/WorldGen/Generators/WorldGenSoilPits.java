package TFC.WorldGen.Generators;

import java.util.Random;

import TFC.*;
import TFC.WorldGen.BiomeDecoratorTFC;
import TFC.WorldGen.TFCBiome;
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

public class WorldGenSoilPits implements IWorldGenerator
{


	public WorldGenSoilPits()
	{

	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) 
	{
		for (int var1 = 0; var1 < 1; ++var1)
        {
            int var2 = chunkX + random.nextInt(16) + 8;
            int var3 = chunkZ + random.nextInt(16) + 8;
            new WorldGenClayPit(16, (TFCBiome) world.getBiomeGenForCoords(var2, var3)).generate(world, random, var2, world.getTopSolidOrLiquidBlock(var2, var3), var3);
        }

        for (int var1 = 0; var1 < 1; ++var1)
        {
            int var2 = chunkX + random.nextInt(16) + 8;
            int var3 = chunkZ + random.nextInt(16) + 8;
            new WorldGenPeatPit(24, world.getBiomeGenForCoords(var2, var3)).generate(world, random, var2, world.getTopSolidOrLiquidBlock(var2, var3), var3);
        }
		
	}
}
