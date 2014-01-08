package TFC.Blocks.Vanilla;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class BlockCustomStationary extends BlockCustomFluid
{
	public BlockCustomStationary(int par1, Material par2Material)
	{
		super(par1, par2Material);
		this.setTickRandomly(false);

		if (par2Material == Material.lava)
			this.setTickRandomly(true);
	}

	@Override
	public void registerIcons(IconRegister par1IconRegister)
	{
		super.registerIcons(par1IconRegister);
		Block.waterMoving.registerIcons(par1IconRegister);
		Block.lavaMoving.registerIcons(par1IconRegister);
	}

	@Override
	public boolean getBlocksMovement(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		return this.blockMaterial != Material.lava;
	}

	/**
	 * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
	 * their own) Args: x, y, z, neighbor blockID
	 */
	@Override
	public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
	{
		super.onNeighborBlockChange(par1World, par2, par3, par4, par5);

		if (par1World.getBlockId(par2, par3, par4) == this.blockID)
			this.setNotStationary(par1World, par2, par3, par4);
	}

	/**
	 * Changes the block ID to that of an updating fluid.
	 */
	protected void setNotStationary(World world, int i, int j, int k)
	{
		BiomeGenBase biome = world.getBiomeGenForCoords(i, k);

		int var5 = world.getBlockMetadata(i, j, k);
		world.setBlock(i, j, k, this.blockID - 1, var5, 0x2);
		world.scheduleBlockUpdate(i, j, k, this.blockID - 1, this.tickRate(world));
	}

	/**
	 * Ticks the block if it's been scheduled
	 */
	@Override
	public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		if (this.blockMaterial == Material.lava)
		{
			int var6 = par5Random.nextInt(3);
			int var7;
			int var8;

			for (var7 = 0; var7 < var6; ++var7)
			{
				par2 += par5Random.nextInt(3) - 1;
				++par3;
				par4 += par5Random.nextInt(3) - 1;
				var8 = par1World.getBlockId(par2, par3, par4);

				if (var8 == 0)
				{
					if (this.isFlammable(par1World, par2 - 1, par3, par4) || this.isFlammable(par1World, par2 + 1, par3, par4) || this.isFlammable(par1World, par2, par3, par4 - 1) || this.isFlammable(par1World, par2, par3, par4 + 1) || this.isFlammable(par1World, par2, par3 - 1, par4) || this.isFlammable(par1World, par2, par3 + 1, par4))
					{
						par1World.setBlock(par2, par3, par4, Block.fire.blockID);
						return;
					}
				}
				else if (Block.blocksList[var8].blockMaterial.blocksMovement())
					return;
			}

			if (var6 == 0)
			{
				var7 = par2;
				var8 = par4;

				for (int var9 = 0; var9 < 3; ++var9)
				{
					par2 = var7 + par5Random.nextInt(3) - 1;
					par4 = var8 + par5Random.nextInt(3) - 1;

					if (par1World.isAirBlock(par2, par3 + 1, par4) && this.isFlammable(par1World, par2, par3, par4))
						par1World.setBlock(par2, par3 + 1, par4, Block.fire.blockID);
				}
			}
		}
	}

	/**
	 * Checks to see if the block is flammable.
	 */
	private boolean isFlammable(World par1World, int par2, int par3, int par4)
	{
		return par1World.getBlockMaterial(par2, par3, par4).getCanBurn();
	}
}
