package TFC.Blocks.Vanilla;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockMushroom;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenBigMushroom;
import TFC.Core.TFC_Core;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCustomMushroom extends BlockMushroom
{
	String textureName;
	public BlockCustomMushroom(String tex)
	{
		super();
		float var3 = 0.2F;
		this.setBlockBounds(0.5F - var3, 0.0F, 0.5F - var3, 0.5F + var3, var3 * 2.0F, 0.5F + var3);
		this.setTickRandomly(true);
		textureName = tex;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister par1IconRegister)
	{
		this.blockIcon = par1IconRegister.registerIcon(textureName);
	}

	/**
	 * Can this block stay at this position.  Similar to canPlaceBlockAt except gets checked often with plants.
	 */
	@Override
	public boolean canBlockStay(World par1World, int par2, int par3, int par4)
	{
		if (par3 >= 0 && par3 < 256)
		{
			Block var5 = par1World.getBlock(par2, par3 - 1, par4);
			return var5 == Blocks.mycelium || par1World.getFullBlockLightValue(par2, par3, par4) < 13 && this.canThisPlantGrowOnThisBlock(var5);
		}
		else
		{
			return false;
		}
	}

	protected boolean canThisPlantGrowOnThisBlock(Block block)
	{
		return TFC_Core.isSoil(block);
	}
	
	/**
	 * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
	 */
	@Override
	public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
	{
		Block var5 = par1World.getBlock(par2, par3, par4);
		return (par1World.isAirBlock(par2, par3, par4) || var5.getMaterial().isReplaceable())
				&& this.canThisPlantGrowOnThisBlock(var5)
				&& this.canBlockStay(par1World, par2, par3, par4);
	}

	/**
	 * Fertilize the mushroom.
	 */
	@Override
	public boolean func_149884_c/*fertilizeMushroom*/(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		int var6 = par1World.getBlockMetadata(par2, par3, par4);
		par1World.setBlockToAir(par2, par3, par4);
		WorldGenBigMushroom var7 = null;

		if (this == Blocks.brown_mushroom)
			var7 = new WorldGenBigMushroom(0);
		else if (this == Blocks.red_mushroom)
			var7 = new WorldGenBigMushroom(1);

		if (var7 != null && var7.generate(par1World, par5Random, par2, par3, par4))
			return true;
		else
		{
			par1World.setBlock(par2, par3, par4, this, var6, 3);
			return false;
		}
	}

	/**
	 * Ticks the block if it's been scheduled
	 */
	@Override
	public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		if (par5Random.nextInt(25) == 0)
		{
			byte var6 = 4;
			int var7 = 5;
			int var8;
			int var9;
			int var10;

			for (var8 = par2 - var6; var8 <= par2 + var6; ++var8)
				for (var9 = par4 - var6; var9 <= par4 + var6; ++var9)
					for (var10 = par3 - 1; var10 <= par3 + 1; ++var10)
						if (par1World.getBlock(var8, var10, var9) == this)
						{
							--var7;
							if (var7 <= 0)
								return;
						}

			var8 = par2 + par5Random.nextInt(3) - 1;
			var9 = par3 + par5Random.nextInt(2) - par5Random.nextInt(2);
			var10 = par4 + par5Random.nextInt(3) - 1;

			for (int var11 = 0; var11 < 4; ++var11)
			{
				if (par1World.isAirBlock(var8, var9, var10) && this.canBlockStay(par1World, var8, var9, var10))
				{
					par2 = var8;
					par3 = var9;
					par4 = var10;
				}

				var8 = par2 + par5Random.nextInt(3) - 1;
				var9 = par3 + par5Random.nextInt(2) - par5Random.nextInt(2);
				var10 = par4 + par5Random.nextInt(3) - 1;
			}

			if (par1World.isAirBlock(var8, var9, var10) && this.canBlockStay(par1World, var8, var9, var10))
				par1World.setBlock(var8, var9, var10, this, 0, 2);
		}
	}
}
