package TFC.Blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.src.*;

public class BlockCustomStairs extends Block
{
	/** The block that is used as model for the stair. */
	private Block modelBlock;

	public BlockCustomStairs(int par1, Block par2Block)
	{
		super(par1, 4, par2Block.blockMaterial);
		this.modelBlock = par2Block;
		this.setHardness(5);
		this.setResistance(10);
		this.setStepSound(par2Block.stepSound);
		this.setLightOpacity(255);
	}

	/**
	 * Returns whether this block is collideable based on the arguments passed in Args: blockMetaData, unknownFlag
	 */
	public boolean canCollideCheck(int par1, boolean par2)
	{
		return this.modelBlock.canCollideCheck(par1, par2);
	}

	/**
	 * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
	 */
	public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
	{
		return this.modelBlock.canPlaceBlockAt(par1World, par2, par3, par4);
	}

	@Override
	public void addCollidingBlockToList(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity)
	{
		int var7 = par1World.getBlockMetadata(par2, par3, par4);
		int var8 = var7 & 3;
		float var9 = 0.0F;
		float var10 = 0.5F;
		float var11 = 0.5F;
		float var12 = 1.0F;

		if ((var7 & 4) != 0)
		{
			var9 = 0.5F;
			var10 = 1.0F;
			var11 = 0.0F;
			var12 = 0.5F;
		}

		this.setBlockBounds(0.0F, var9, 0.0F, 1.0F, var10, 1.0F);
		super.addCollidingBlockToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);

		if (var8 == 0)
		{
			this.setBlockBounds(0.5F, var11, 0.0F, 1.0F, var12, 1.0F);
			super.addCollidingBlockToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
		}
		else if (var8 == 1)
		{
			this.setBlockBounds(0.0F, var11, 0.0F, 0.5F, var12, 1.0F);
			super.addCollidingBlockToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
		}
		else if (var8 == 2)
		{
			this.setBlockBounds(0.0F, var11, 0.5F, 1.0F, var12, 1.0F);
			super.addCollidingBlockToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
		}
		else if (var8 == 3)
		{
			this.setBlockBounds(0.0F, var11, 0.0F, 1.0F, var12, 0.5F);
			super.addCollidingBlockToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
		}

		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}

	/**
	 * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
	 * cleared to be reused)
	 */
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		return super.getCollisionBoundingBoxFromPool(par1World, par2, par3, par4);
	}

	/**
	 * Returns how much this block can resist explosions from the passed in entity.
	 */
	public float getExplosionResistance(Entity par1Entity)
	{
		return this.modelBlock.getExplosionResistance(par1Entity);
	}

	/**
	 * Goes straight to getLightBrightnessForSkyBlocks for Blocks, does some fancy computing for Fluids
	 */
	public int getMixedBrightnessForBlock(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		return this.modelBlock.getMixedBrightnessForBlock(par1IBlockAccess, par2, par3, par4);
	}

	/**
	 * Returns which pass should this block be rendered on. 0 for solids and 1 for alpha
	 */
	public int getRenderBlockPass()
	{
		return this.modelBlock.getRenderBlockPass();
	}

	/**
	 * The type of render function that is called for this block
	 */
	public int getRenderType()
	{
	    return 10;
	}

	/**
	 * Returns the bounding box of the wired rectangular prism to render.
	 */
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		return modelBlock.getSelectedBoundingBoxFromPool(par1World, par2, par3, par4);
	}

	/**
	 * Returns if this block is collidable (only used by Fire). Args: x, y, z
	 */
	public boolean isCollidable()
	{
		return this.modelBlock.isCollidable();
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
	 * Called whenever the block is added into the world. Args: world, x, y, z
	 */
	public void onBlockAdded(World par1World, int par2, int par3, int par4)
	{
		this.onNeighborBlockChange(par1World, par2, par3, par4, 0);
		this.modelBlock.onBlockAdded(par1World, par2, par3, par4);
	}

	/**
	 * Called when the block is clicked by a player. Args: x, y, z, entityPlayer
	 */
	public void onBlockClicked(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer)
	{
		this.modelBlock.onBlockClicked(par1World, par2, par3, par4, par5EntityPlayer);
	}

	/**
	 * Called upon the block being destroyed by an explosion
	 */
	public void onBlockDestroyedByExplosion(World par1World, int par2, int par3, int par4)
	{
		this.modelBlock.onBlockDestroyedByExplosion(par1World, par2, par3, par4);
	}

	/**
	 * Called right before the block is destroyed by a player.  Args: world, x, y, z, metaData
	 */
	public void onBlockDestroyedByPlayer(World par1World, int par2, int par3, int par4, int par5)
	{
		this.modelBlock.onBlockDestroyedByPlayer(par1World, par2, par3, par4, par5);
	}

	/**
	 * Called when a block is placed using an item. Used often for taking the facing and figuring out how to position
	 * the item. Args: x, y, z, facing
	 */
	public void onBlockPlaced(World par1World, int par2, int par3, int par4, int par5)
	{
		if (par5 == 0)
		{
			int var6 = par1World.getBlockMetadata(par2, par3, par4);
			par1World.setBlockMetadataWithNotify(par2, par3, par4, var6 | 4);
		}
	}

	/**
	 * Called when the block is placed in the world.
	 */
	public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLiving par5EntityLiving)
	{
		int var6 = MathHelper.floor_double((double)(par5EntityLiving.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		int var7 = par1World.getBlockMetadata(par2, par3, par4) & 4;

		if (var6 == 0)
		{
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 2 | var7);
		}

		if (var6 == 1)
		{
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 1 | var7);
		}

		if (var6 == 2)
		{
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 3 | var7);
		}

		if (var6 == 3)
		{
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 0 | var7);
		}
	}

	@Override
	public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
	{
		this.modelBlock.breakBlock(par1World, par2, par3, par4, par5, par6);
	}

	/**
	 * Called whenever an entity is walking on top of this block. Args: world, x, y, z, entity
	 */
	public void onEntityWalking(World par1World, int par2, int par3, int par4, Entity par5Entity)
	{
		this.modelBlock.onEntityWalking(par1World, par2, par3, par4, par5Entity);
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
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}

	/**
	 * Returns true if the given side of this block type should be rendered, if the adjacent block is at the given
	 * coordinates.  Args: blockAccess, x, y, z, side
	 */
	public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		return true;
	}

	/**
	 * How many world ticks before ticking
	 */
	public int tickRate()
	{
		return this.modelBlock.tickRate();
	}

	/**
	 * Ticks the block if it's been scheduled
	 */
	public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		this.modelBlock.updateTick(par1World, par2, par3, par4, par5Random);
	}

	/**
	 * Can add to the passed in vector for a movement vector to be applied to the entity. Args: x, y, z, entity, vec3d
	 */
	public void velocityToAddToEntity(World par1World, int par2, int par3, int par4, Entity par5Entity, Vec3 par6Vec3D)
	{
		this.modelBlock.velocityToAddToEntity(par1World, par2, par3, par4, par5Entity, par6Vec3D);
	}
	
	public void addCreativeItems(java.util.ArrayList list)
    {
        list.add(new ItemStack(this,1));
    }
}
