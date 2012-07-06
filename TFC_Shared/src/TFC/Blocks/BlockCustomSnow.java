package TFC.Blocks;

import java.util.Random;

import TFC.Core.TFCSeasons;

import net.minecraft.src.*;

public class BlockCustomSnow extends Block
{
	public BlockCustomSnow(int par1, int par2)
	{
		super(par1, par2, Material.snow);
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
		this.setTickRandomly(true);
	}

	/**
	 * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
	 * cleared to be reused)
	 */
	//    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	//    {
	//        int var5 = par1World.getBlockMetadata(par2, par3, par4) & 7;
	//        return var5 >= 3 ? AxisAlignedBB.getBoundingBoxFromPool((double)par2 + this.minX, (double)par3 + this.minY, (double)par4 + this.minZ, (double)par2 + this.maxX, (double)((float)par3 + 0.5F), (double)par4 + this.maxZ) : null;
	//    }

	/**
	 * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
	 */
	public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
	{
		int var5 = par1World.getBlockId(par2, par3 - 1, par4);
		return var5 != 0 && (var5 == Block.leaves.blockID || Block.blocksList[var5].isOpaqueCube()) ? true : false;
	}

	/**
	 * Checks if this snow block can stay at this location.
	 */
	private boolean canSnowStay(World par1World, int par2, int par3, int par4)
	{
		if (!this.canPlaceBlockAt(par1World, par2, par3, par4))
		{
			par1World.setBlockWithNotify(par2, par3, par4, 0);
			par1World.markBlockNeedsUpdate(par2, par3, par4);
			return false;
		}
		else
		{
			return true;
		}
	}

	/**
	 * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
	 * cleared to be reused)
	 */
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		return null;
	}

	public int getRenderType()
	{
		return mod_TFC_Core.snowRenderId;
	}

	/**
	 * Called when the player destroys a block with an item that can harvest it. (i, j, k) are the coordinates of the
	 * block and l is the block's subtype/damage.
	 */
	public void harvestBlock(World par1World, EntityPlayer par2EntityPlayer, int par3, int par4, int par5, int par6)
	{
		dropBlockAsItem(par1World, par3, par4, par5, par6, 0);
		par2EntityPlayer.addStat(StatList.mineBlockStatArray[this.blockID], 1);
	}

	/**
	 * Returns the ID of the items to drop on destruction.
	 */
	public int idDropped(int par1, Random par2Random, int par3)
	{
		return Item.snowball.shiftedIndex;
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
	 * Triggered whenever an entity collides with this block (enters into the block). Args: world, x, y, z, entity
	 */
	public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity)
	{
		int var5 = par1World.getBlockMetadata(par2, par3, par4);
		if(var5 > 0)
		{
			double speed = 0.58D + 0.4D * (15/var5/15);

			par5Entity.motionX *= speed;
			par5Entity.motionZ *= speed;
		}
	}

	/**
	 * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
	 * their own) Args: x, y, z, neighbor blockID
	 */
	public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
	{
		this.canSnowStay(par1World, par2, par3, par4);
	}

	/**
	 * Returns the quantity of items to drop on block destruction.
	 */
	public int quantityDropped(Random par1Random)
	{
		return 1;
	}

	/**
	 * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
	 */
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	/**
	 * Updates the blocks bounds based on its current state. Args: world, x, y, z
	 */
	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		int var5 = par1IBlockAccess.getBlockMetadata(par2, par3, par4) & 7;
		float var6 = (float)(2 * (1 + var5)) / 16.0F;
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, var6, 1.0F);
	}

	/**
	 * Returns true if the given side of this block type should be rendered, if the adjacent block is at the given
	 * coordinates.  Args: blockAccess, x, y, z, side
	 */
//	public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
//	{
//		return par5 == 1 ? true : super.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, par5);
//	}

	public int tickRate()
	{
		return 50;
	}

	/**
	 * Ticks the block if it's been scheduled
	 */
	public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		int meta = par1World.getBlockMetadata(par2, par3, par4);
		if (par1World.getSavedLightValue(EnumSkyBlock.Block, par2, par3, par4) > 11)
		{
			if(meta > 1 && par5Random.nextInt(5) == 0) {
				par1World.setBlockMetadata(par2, par3, par4, meta-1);
			} else if(meta == 1 && par5Random.nextInt(5) == 0) {
				par1World.setBlockWithNotify(par2, par3, par4, 0);
			}
		}
		
		if(par1World.isRaining() && par1World.getBiomeGenForCoords(par2, par4).getHeightAdjustedTemperature(par3) <= 0.2F)//Raining and Below Freezing
		{      
			if(meta < 3 && par1World.getBlockMaterial(par2, par3-1, par4) != Material.leaves) 
			{
				par1World.setBlockMetadata(par2, par3, par4, meta+1);
			} 
			else if(meta < 15 && par5Random.nextInt(8) == 0 && par1World.getBlockMaterial(par2, par3-1, par4) != Material.leaves)
            {
                par1World.setBlockMetadata(par2, par3, par4, meta+1);
            }
			else if(meta < 3 && par5Random.nextInt(3) == 0 && par1World.getBlockMaterial(par2, par3-1, par4) == Material.leaves)
			{
				par1World.setBlockMetadata(par2, par3, par4, meta+1);
			}
		}
		else if(par1World.isRaining() && par1World.getBiomeGenForCoords(par2, par4).getHeightAdjustedTemperature(par3) > 0.2F)//Raining and above freezing
        {      
            if(meta <= 15 && par1World.getBlockMaterial(par2, par3-1, par4) != Material.leaves) 
            {
                if(meta > 1) 
                {
                    par1World.setBlockMetadata(par2, par3, par4, meta-1);
                } 
                else 
                {
                    par1World.setBlockWithNotify(par2, par3, par4, 0);
                }
            } 
            else if(meta <= 15 && par1World.getBlockMaterial(par2, par3-1, par4) == Material.leaves)
            {
                if(meta > 1) {
                    par1World.setBlockMetadata(par2, par3, par4, meta-1);
                } else {
                    par1World.setBlockWithNotify(par2, par3, par4, 0);
                }
            }
        }
		else if(par1World.getBiomeGenForCoords(par2, par4).getHeightAdjustedTemperature(par3) > 0.2F)//Above fReezing
		{
			if(meta > 1 ) {
				par1World.setBlockMetadata(par2, par3, par4, meta-1);
			} else if(meta == 1) {
				par1World.setBlockWithNotify(par2, par3, par4, 0);
			}
		}
		else//Below Freezing
		{
		    if(meta > 1 && par5Random.nextInt(5) == 0) 
		    {
                par1World.setBlockMetadata(par2, par3, par4, meta-1);
            } 
		    else if(meta == 1 && par5Random.nextInt(5) == 0)
            {
                par1World.setBlockWithNotify(par2, par3, par4, 0);
            }
		}
		par1World.markBlockNeedsUpdate(par2, par3, par4);
	}
}
