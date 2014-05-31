package com.bioxx.tfc.Blocks.Vanilla;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import com.bioxx.tfc.Blocks.BlockTerra;
import com.bioxx.tfc.Core.TFC_Core;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockTFCFlower extends BlockTerra
{
	private static final String[][] flowerNames = new String[][] {{"flower_dandelion"}, {"flower_rose", "flower_blue_orchid", "flower_allium", "flower_houstonia", "flower_tulip_red", "flower_tulip_orange", "flower_tulip_white", "flower_tulip_pink", "flower_oxeye_daisy"}};
	@SideOnly(Side.CLIENT)
	private IIcon[] icons;

	private int flowerType;

	public BlockTFCFlower(int type)
	{
		this.setTickRandomly(true);
		flowerType = type;
		float var4 = 0.2F;
		this.setBlockBounds(0.5F - var4, 0.0F, 0.5F - var4, 0.5F + var4, var4 * 3.0F, 0.5F + var4);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		if (meta >= this.icons.length)
		{
			meta = 0;
		}

		return this.icons[meta];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register)
	{
		this.icons = new IIcon[flowerNames[this.flowerType].length];

		for (int i = 0; i < this.icons.length; ++i)
		{
			this.icons[i] = register.registerIcon(flowerNames[this.flowerType][i]);
		}
	}

	/**
	 * Can this block stay at this position.  Similar to canPlaceBlockAt except gets checked often with plants.
	 */
	@Override
	public boolean canBlockStay(World world, int x, int y, int z)
	{
		return (world.getFullBlockLightValue(x, y, z) >= 8 || world.canBlockSeeTheSky(x, y, z)) && this.canThisPlantGrowOnThisBlock(world.getBlock(x, y - 1, z));
	}

	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z)
	{
		Block block = world.getBlock(x, y, z);
		return (world.isAirBlock(x, y, z) || block.getMaterial().isReplaceable()) && this.canThisPlantGrowOnThisBlock(block);
	}

	protected boolean canThisPlantGrowOnThisBlock(Block block)
	{
		return TFC_Core.isSoil(block);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		return null;
	}

	@Override
	public int getRenderType()
	{
		return 1;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand)
	{
		this.checkAndDropBlock(world, x, y, z);
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
	{
		this.checkAndDropBlock(world, x, y, z);
	}

	protected void checkAndDropBlock(World world, int x, int y, int z)
	{
		if (!this.canBlockStay(world, x, y, z))
		{
			this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
			world.setBlock(x, y, z, getBlockById(0), 0, 2);
		}
	}
}
