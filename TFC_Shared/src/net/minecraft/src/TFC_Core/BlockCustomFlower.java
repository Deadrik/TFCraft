package net.minecraft.src.TFC_Core;

import java.util.Random;

import net.minecraft.src.*;

public class BlockCustomFlower extends BlockFlower
{
	public BlockCustomFlower(int par1, int par2)
	{
		this(par1, par2, Material.plants);
	}

	public BlockCustomFlower(int par1, int par2, Material par3Material)
	{
		super(par1, par2, par3Material);
		this.blockIndexInTexture = par2;
		this.setTickRandomly(true);
		float var4 = 0.2F;
		this.setBlockBounds(0.5F - var4, 0.0F, 0.5F - var4, 0.5F + var4, var4 * 3.0F, 0.5F + var4);
	}

	/**
	 * Can this block stay at this position.  Similar to canPlaceBlockAt except gets checked often with plants.
	 */
	@Override
	public boolean canBlockStay(World par1World, int par2, int par3, int par4)
	{
		return (par1World.getFullBlockLightValue(par2, par3, par4) >= 8 || par1World.canBlockSeeTheSky(par2, par3, par4)) && this.canThisPlantGrowOnThisBlockID(par1World.getBlockId(par2, par3 - 1, par4));
	}

	/**
	 * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
	 */
	public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
    {
            int var5 = par1World.getBlockId(par2, par3, par4);
            return (var5 == 0 || blocksList[var5].blockMaterial.isGroundCover()) && this.canThisPlantGrowOnThisBlockID(par1World.getBlockId(par2, par3 - 1, par4));
    }

	/**
	 * Gets passed in the blockID of the block below and supposed to return true if its allowed to grow on the type of
	 * blockID passed in. Args: blockID
	 */
	@Override
	protected boolean canThisPlantGrowOnThisBlockID(int par1)
	{
		return par1 == mod_TFC_Core.terraGrass.blockID || par1 == mod_TFC_Core.terraGrass2.blockID || 
				par1 == mod_TFC_Core.terraDirt.blockID || par1 == mod_TFC_Core.terraDirt2.blockID ||
				par1 == mod_TFC_Core.terraClayGrass.blockID || par1 == mod_TFC_Core.terraClayGrass2.blockID ||
				par1 == mod_TFC_Core.terraPeatGrass.blockID ||
				par1 == mod_TFC_Core.tilledSoil.blockID || par1 == mod_TFC_Core.tilledSoil2.blockID;
	}

	/**
	 * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
	 * cleared to be reused)
	 */
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		return null;
	}

	/**
	 * The type of render function that is called for this block
	 */
	public int getRenderType()
	{
		return 1;
	}

	/**
	 * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
	 * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
	 */
	public boolean isOpaqueCube()
	{
		return false;
	}

	/**
	 * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
	 */
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	/**
	 * Ticks the block if it's been scheduled
	 */

	public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		this.checkFlowerChange(par1World, par2, par3, par4);
	}
}
