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

public class WorldGenCustomPumpkin extends WorldGenerator
{
	public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
	{
		for (int var6 = 0; var6 < 64; ++var6)
		{
			int var7 = par3 + par2Random.nextInt(8) - par2Random.nextInt(8);
			int var8 = par4 + par2Random.nextInt(4) - par2Random.nextInt(4);
			int var9 = par5 + par2Random.nextInt(8) - par2Random.nextInt(8);
			int var5 = par1World.getBlockId(var7, var8 - 1, var9);
			if (par1World.isAirBlock(var7, var8, var9) && 
					(var5 == Block.grass.blockID || var5 == TFCBlocks.Grass.blockID || var5 == TFCBlocks.Grass2.blockID ||
					var5 == TFCBlocks.Dirt.blockID || var5 == TFCBlocks.Dirt.blockID ||
					var5 == TFCBlocks.ClayGrass.blockID || var5 == TFCBlocks.ClayGrass2.blockID ||
					var5 == TFCBlocks.PeatGrass.blockID || var5 == TFCBlocks.Peat.blockID) && Block.pumpkin.canPlaceBlockAt(par1World, var7, var8, var9))
			{
				par1World.setBlockAndMetadata(var7, var8, var9, Block.pumpkin.blockID, par2Random.nextInt(4));
			}
		}

		return true;
	}
}
