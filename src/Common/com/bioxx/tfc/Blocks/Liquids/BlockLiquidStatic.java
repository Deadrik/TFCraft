package com.bioxx.tfc.Blocks.Liquids;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidBlock;
import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Sounds;
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
		this.setTickRandomly(true);
		fluid.setBlock(this);
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

	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity e) 
	{
		if (this.blockMaterial == Material.lava)
		{
			if(e instanceof EntityItem)
			{
				e.setFire(15);
			}
		}
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

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand)
	{
		if(!world.isRemote)
		{
			if(world.isAirBlock(x, y + 1, z))
			{
				world.provider.canBlockFreeze(x, y, z, false);
			}

			// Play frog sound at night only in fresh water.
			if(world.getBlock(x, y, z) == TFCBlocks.FreshWaterStationary && world.isAirBlock(x, y + 1, z))
			{
				if(rand.nextInt(100) < 25 && world.getBlockLightValue(x, y, z) < 7)
				{
					outerloop:
					for(int x1 = x - 3; x1 < x + 3; x1++)
					{
						for(int z1 = z - 3; z1 < z + 3; z1++)
						{
							if(TFC_Core.isGrass(world.getBlock(x1, y, z1)) || world.getBlock(x1, y - 1, z1) == TFCBlocks.WaterPlant)
							{
								float mod = rand.nextFloat();
								world.playSoundEffect(x, y, z, TFC_Sounds.FROG, (mod < 0.55F ? mod : 0.55F), (mod < 0.41F ? mod + 0.8F : 0.8F));
								break outerloop;
							}
						}
					}
				}
			}

			if(this.getMaterial() == Material.lava)
			{
				if(world.getBlock(x, y + 1, z) == Blocks.air)
				{
					int i = x-2+rand.nextInt(5);
					int j = y+1+rand.nextInt(4);
					int k = z-2+rand.nextInt(5);
					if(world.getBlock(i, j, k) == Blocks.air && 
							(world.isSideSolid(i, j+1, k, ForgeDirection.DOWN) || world.isSideSolid(i, j-1, k, ForgeDirection.UP) ||
									world.isSideSolid(i-1, j, k, ForgeDirection.EAST) || world.isSideSolid(i+1, j, k, ForgeDirection.WEST) ||
									world.isSideSolid(i, j, k+1, ForgeDirection.NORTH) || world.isSideSolid(i, j, k-1, ForgeDirection.SOUTH)))
					{
						world.setBlock(i, j, k, TFCBlocks.Sulfur, world.rand.nextInt(4), 3);
					}
				}
			}
		}

		super.updateTick(world, x, y, z, rand);
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
		if(this.getMaterial() == Material.lava)
			icons = new IIcon[]{registerer.registerIcon("lava_still"), registerer.registerIcon("lava_flow")};
		else
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
