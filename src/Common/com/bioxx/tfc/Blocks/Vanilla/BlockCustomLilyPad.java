package com.bioxx.tfc.Blocks.Vanilla;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLilyPad;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Core.TFC_Core;

public class BlockCustomLilyPad extends BlockLilyPad
{
	public BlockCustomLilyPad()
	{
		super();
		float f = 0.5F;
		float f1 = 0.015625F;
		this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f1, 0.5F + f);
		this.setCreativeTab(TFCTabs.TFC_DECORATION);
	}

	/**
	 * The type of render function that is called for this block
	 */
	@Override
	public int getRenderType()
	{
		return 23;
	}

	/**
	 * Adds all intersecting collision boxes to a list. (Be sure to only add boxes to the list if they intersect the
	 * mask.) Parameters: World, X, Y, Z, mask, list, colliding entity
	 */
	@Override
	public void addCollisionBoxesToList(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity)
	{
		if (!(par7Entity instanceof EntityBoat))
			super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
	}

	/**
	 * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
	 * cleared to be reused)
	 */
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		return AxisAlignedBB.getBoundingBox(par2 + this.minX, par3 + this.minY, par4 + this.minZ, par2 + this.maxX, par3 + this.maxY, par4 + this.maxZ);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getBlockColor()
	{
		return 2129968;
	}

	@Override
	@SideOnly(Side.CLIENT)
	/**
	 * Returns the color this block should be rendered. Used by leaves.
	 */
	public int getRenderColor(int par1)
	{
		return 2129968;
	}

	@Override
	@SideOnly(Side.CLIENT)
	/**
	 * When this method is called, your block should register all the icons it needs with the given IconRegister. This
	 * is the only chance you get to register icons.
	 */
	public void registerBlockIcons(IIconRegister par1IconRegister)
	{
		this.blockIcon = par1IconRegister.registerIcon(this.getTextureName());
	}

	@Override
	@SideOnly(Side.CLIENT)
	/**
	 * Returns a integer with hex for 0xrrggbb with this color multiplied against the blocks color. Note only called
	 * when first determining what to render.
	 */
	public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		return 2129968;
	}

	@Override
	protected boolean canPlaceBlockOn(Block block)
	{
		return canThisPlantGrowOnThisBlock(block);
	}

	protected boolean canThisPlantGrowOnThisBlock(Block par1)
	{
		return TFC_Core.isFreshWater(par1);
	}

	public boolean canThisPlantGrowOnThisBlock(Block par1, int meta)
	{
		return TFC_Core.isFreshWaterIncludeIce(par1, meta) && !TFC_Core.isWaterFlowing(par1);
	}

	/**
	 * Can this block stay at this position.  Similar to canPlaceBlockAt except gets checked often with plants.
	 */
	@Override
	public boolean canBlockStay(World world, int x, int y, int z)
	{
		Block id = world.getBlock(x, y - 1, z);
		int meta = world.getBlockMetadata(x, y - 1, z);
		return y > 0 && y < 256 ? TFC_Core.isFreshWaterIncludeIce(id, meta) : false;
	}

}
