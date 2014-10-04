package com.bioxx.tfc.Blocks.Liquids;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidBlock;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockLiquidStatic extends BlockLiquid implements IFluidBlock
{
	Block flowing;
	Fluid fluidType;
	IIcon[] icons;
	public BlockLiquidStatic(Fluid fluid, Material material, Block f) 
	{
		super(material);
		flowing = f;
		fluidType = fluid;
		this.setTickRandomly(false);
	}

	@Override
	public Fluid getFluid() 
	{
		return fluidType;
	}

	@Override
	public FluidStack drain(World world, int x, int y, int z, boolean doDrain) 
	{
		return null;
	}

	@Override
	public boolean canDrain(World world, int x, int y, int z) 
	{
		return false;
	}

	@Override
	public float getFilledPercentage(World world, int x, int y, int z) 
	{
		return 1;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		if (this.blockMaterial != Material.water)
			return 16777215;
		else
			return 0x354d35;
	}

	/**
	 * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
	 * their own) Args: x, y, z, neighbor Block
	 */
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block b)
	{
		super.onNeighborBlockChange(world, x, y, z, b);

		if (world.getBlock(x, y, z) == this)
		{
			this.setNotStationary(world, x, y, z);
		}
	}

	/**
	 * Changes the block ID to that of an updating fluid.
	 */
	private void setNotStationary(World world, int x, int y, int z)
	{
		int l = world.getBlockMetadata(x, y, z);
		world.setBlock(x, y, z, flowing, l, 2);
		world.scheduleBlockUpdate(x, y, z, flowing, this.tickRate(world));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister registerer)
	{
		icons = new IIcon[]{registerer.registerIcon("water_still"), registerer.registerIcon("water_flow")};
		this.getFluid().setIcons(icons[0], icons[1]);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		return side != 0 && side != 1 ? this.icons[1] : this.icons[0];
	}
}
