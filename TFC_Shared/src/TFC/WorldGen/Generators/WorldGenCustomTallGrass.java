package TFC.WorldGen.Generators;

import java.util.Random;

import TFC.*;
import TFC.Blocks.BlockCustomTallGrass;
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

public class WorldGenCustomTallGrass extends WorldGenerator
{
	/** Stores ID for WorldGenTallGrass */
	private int tallGrassID;
	private int tallGrassMetadata;

	public WorldGenCustomTallGrass(int par1, int par2)
	{
		this.tallGrassID = par1;
		this.tallGrassMetadata = par2;
	}

	public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
	{
		int var11;

		for (boolean var6 = false; ((var11 = par1World.getBlockId(par3, par4, par5)) == 0 || var11 == Block.leaves.blockID) && par4 > 0; --par4)
		{
			;
		}

		for (int var7 = 0; var7 < 128; ++var7)
		{
			int var8 = par3 + par2Random.nextInt(8) - par2Random.nextInt(8);
			int var9 = par4 + par2Random.nextInt(4) - par2Random.nextInt(4);
			int var10 = par5 + par2Random.nextInt(8) - par2Random.nextInt(8);

			if (par1World.isAirBlock(var8, var9, var10) && ((BlockCustomTallGrass)Block.blocksList[this.tallGrassID]).canBlockStay(par1World, var8, var9, var10))
			{
				par1World.setBlockAndMetadataWithNotify(var8, var9, var10, this.tallGrassID, this.tallGrassMetadata, 0);
			}
		}

		return true;
	}
}
