package com.bioxx.tfc.Blocks.Vanilla;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Core.TFC_Core;

public class BlockCustomCactus extends Block implements IPlantable
{
	@SideOnly(Side.CLIENT)
	private IIcon cactusTopIcon;
	@SideOnly(Side.CLIENT)
	private IIcon cactusSideIcon;

	public BlockCustomCactus()
	{
		super(Material.cactus);
		this.setTickRandomly(true);
		this.setCreativeTab(TFCTabs.TFC_DECORATION);
	}

	/**
	 * Ticks the block if it's been scheduled
	 */
	@Override
	public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		if (par1World.isAirBlock(par2, par3 + 1, par4))
		{
			int var6;

			for (var6 = 1; par1World.getBlock(par2, par3 - var6, par4) == this; ++var6)
			{
				;
			}

			if (var6 < 3)
			{
				int var7 = par1World.getBlockMetadata(par2, par3, par4);

				if (var7 == 15)
				{
					par1World.setBlock(par2, par3 + 1, par4, this, 0, 0x2);
					par1World.setBlockMetadataWithNotify(par2, par3, par4, 0, 0x2);
				}
				else
				{
					par1World.setBlockMetadataWithNotify(par2, par3, par4, var7 + 1, 0x2);
				}
			}
		}
	}

	/**
	 * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
	 * cleared to be reused)
	 */
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		float var5 = 0.0625F;
		return AxisAlignedBB.getBoundingBox(par2 + var5, par3, par4 + var5, par2 + 1 - var5, par3 + 1 - var5, par4 + 1 - var5);
	}

	@SideOnly(Side.CLIENT)
	@Override
	/**
	 * Returns the bounding box of the wired rectangular prism to render.
	 */
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		float var5 = 0.0625F;
		return AxisAlignedBB.getBoundingBox(par2 + var5, par3, par4 + var5, par2 + 1 - var5, par3 + 1, par4 + 1 - var5);
	}

	/**
	 * Returns the block texture based on the side being looked at.  Args: side
	 */
	@Override
	public IIcon getIcon(int par1, int par2)
	{
		return par1 == 1 ? this.cactusTopIcon : par1 == 0 ? this.cactusSideIcon : this.blockIcon;
	}

	/**
	 * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
	 */
	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	/**
	 * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
	 * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
	 */
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	/**
	 * The type of render function that is called for this block
	 */
	@Override
	public int getRenderType()
	{
		return 13;
	}

	/**
	 * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
	 */
	@Override
	public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
	{
		return !super.canPlaceBlockAt(par1World, par2, par3, par4) ? false : this.canBlockStay(par1World, par2, par3, par4);
	}

	/**
	 * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
	 * their own) Args: x, y, z, neighbor blockID
	 */
	@Override
	public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, Block par5)
	{
		if (!this.canBlockStay(par1World, par2, par3, par4))
		{
			this.dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
			par1World.setBlockToAir(par2, par3, par4);
		}
	}

	@Override
	public boolean canBlockStay(World par1World, int par2, int par3, int par4)
	{
		if (par1World.getBlock(par2 - 1, par3, par4).getMaterial().isSolid())
			return false;
		else if (par1World.getBlock(par2 + 1, par3, par4).getMaterial().isSolid())
			return false;
		else if (par1World.getBlock(par2, par3, par4 - 1).getMaterial().isSolid())
			return false;
		else if (par1World.getBlock(par2, par3, par4 + 1).getMaterial().isSolid())
			return false;
		else
			return canSustainPlant(par1World, par2, par3 - 1, par4, ForgeDirection.UP, this);
	}

	@Override
	public boolean canSustainPlant(IBlockAccess world, int x, int y, int z, ForgeDirection direction, IPlantable plant)
	{
		Block block = world.getBlock(x, y, z);
		boolean stay = false;
		if(TFC_Core.isSand(block) || block == this || block.canSustainPlant(world, x, y, z, direction, plant))
			stay = true;
		return stay;
	}

	/**
	 * Triggered whenever an entity collides with this block (enters into the block). Args: world, x, y, z, entity
	 */
	@Override
	public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity)
	{
		par5Entity.attackEntityFrom(DamageSource.cactus, 25);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister par1IconRegister)
	{
		this.blockIcon = par1IconRegister.registerIcon("cactus_side");
		this.cactusTopIcon = par1IconRegister.registerIcon("cactus_top");
		this.cactusSideIcon = par1IconRegister.registerIcon("cactus_bottom");
	}

	@Override
	public EnumPlantType getPlantType(IBlockAccess world, int x, int y, int z)
	{
		return EnumPlantType.Desert;
	}

	@Override
	public Block getPlant(IBlockAccess world, int x, int y, int z)
	{
		return this;
	}

	@Override
	public int getPlantMetadata(IBlockAccess world, int x, int y, int z)
	{
		return -1;
	}

}
