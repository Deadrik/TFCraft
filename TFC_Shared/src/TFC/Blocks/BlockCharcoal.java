package TFC.Blocks;

import TFC.*;
import TFC.Core.TFC_Core;
import TFC.Core.TFC_Settings;
import TFC.Items.ItemCustomShovel;
import TFC.TileEntities.TileEntityPartial;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.World;

public class BlockCharcoal extends BlockTerra {

	public BlockCharcoal(int par1) 
	{
		super(par1, Material.ground);
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int i, int j) 
	{
		return 95;
	}

	@Override
	public boolean canBeReplacedByLeaves(World w, int x, int y, int z)
	{
		return false;
	}

	public boolean isOpaqueCube()
	{
		return false;
	}

	public boolean renderAsNormalBlock()
	{
		return false;
	}

	public void onBlockHarvested(World world, int i, int j, int k, int l, EntityPlayer entityplayer) 
	{
		if(!world.isRemote)
		{
			//we need to make sure the player has the correct tool out
			boolean isShovel = false;
			ItemStack equip = entityplayer.getCurrentEquippedItem();
			if(equip!=null)
			{
				if(equip.getItem() instanceof ItemCustomShovel)
				{
					isShovel = true;
				}
			}
			if(isShovel)
			{
				int top = 0;
				while(world.getBlockId(i, j+top+1, k) == blockID)
					++top;
				
				dropBlockAsItem_do(world, i, j, k, new ItemStack(Item.coal,1,1));
				if(l-1 > 0)
				{
					if(world.getBlockId(i, j+1, k) == blockID)
					{
						int m1 = world.getBlockMetadata(i, j+top, k);
						if(m1-1 > 0)
						{
							world.setBlockMetadataWithNotify(i, j+top, k, m1-1);
						}
						else
							world.setBlock(i, j+top, k, 0);

						world.setBlockAndMetadataWithNotify(i, j, k, blockID, 8);
					}
					else
					{
						world.setBlockAndMetadataWithNotify(i, j, k, blockID, l-1);
					}

					world.markBlockForUpdate(i, j, k);
					world.markBlockForUpdate(i, j+top, k);
				}
				else
					world.setBlock(i, j, k, 0);
			}
			else
			{
				world.setBlockAndMetadataWithNotify(i, j, k, blockID, l);
			}

			if(l == 0)
				world.setBlock(i, j, k, 0);
		}
	}

	public boolean removeBlockByPlayer(World world, EntityPlayer player, int x, int y, int z) 
	{
		if(world.getBlockMetadata(x, y, z) > 0)
			return false;

		return world.setBlockWithNotify(x, y, z, 0);
	}

	public void combineCharcoalDown(World world, int i, int j, int k)
	{
		int meta = world.getBlockMetadata(i, j, k);
		int bottomMeta = world.getBlockMetadata(i, j-1, k);
		if(bottomMeta < 8)
		{
			bottomMeta = bottomMeta + meta;
			int m2 = 0;
			if(bottomMeta > 8)
			{
				m2 = bottomMeta - 8;
				bottomMeta = 8;
			}


			if(m2 > 0)
				world.setBlockAndMetadata(i, j, k, blockID, m2);
			else
				world.setBlock(i, j, k, 0);

			world.setBlockAndMetadata(i, j-1, k, blockID, bottomMeta);
		}
	}

	public void combineCharcoalUp(World world, int i, int j, int k)
	{
		int meta = world.getBlockMetadata(i, j+1, k);
		int bottomMeta = world.getBlockMetadata(i, j, k);
		if(bottomMeta < 8)
		{
			bottomMeta = bottomMeta + meta;
			int m2 = 0;
			if(bottomMeta > 8)
			{
				m2 = bottomMeta - 8;
				bottomMeta = 8;
			}


			if(m2 > 0)
				world.setBlockAndMetadata(i, j+1, k, blockID, m2);
			else
				world.setBlock(i, j+1, k, 0);

			world.setBlockAndMetadata(i, j, k, blockID, bottomMeta);
		}
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, int id)
	{
		if(!world.isRemote)
		{
			if(world.getBlockId(i, j-1, k) == 0)
			{
				int meta = world.getBlockMetadata(i, j, k);
				world.setBlockAndMetadata(i, j-1, k, blockID, meta);
				world.setBlockWithNotify(i, j, k, 0);
			}
			else
			{
				if(world.getBlockId(i, j-1, k) == this.blockID)
				{
					combineCharcoalDown(world, i, j, k);
				}

				if(world.getBlockId(i, j+1, k) == this.blockID)
				{
					combineCharcoalUp(world, i, j, k);
				}
			}
		}
	}

	/**
	 * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
	 * cleared to be reused)
	 */
	    @Override
	    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k)
	    {
	        int md = world.getBlockMetadata(i, j, k);
	
	        if (md == 8)
	        	return AxisAlignedBB.getBoundingBox(i, j, k, i + 1, j + 1, k + 1);
	
	        return AxisAlignedBB.getBoundingBox(i, j, k, i + 1, j + (0.125f * md), k + 1);
	    }
	//    
	//    /**
	//     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
	//     * cleared to be reused)
	//     */
	//    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int i, int j, int k)
	//    {
	//        return getCollisionBoundingBoxFromPool(world,i,j,k);
	//    }

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int i, int j, int k) 
	{
		int meta = par1IBlockAccess.getBlockMetadata(i, j, k);
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, (0.125f * meta), 1.0F);
	}

	public void onBlockDestroyedByExplosion(World world, int i, int j, int k) 
	{
		if(!world.isRemote)
		{
		}
	}
}
