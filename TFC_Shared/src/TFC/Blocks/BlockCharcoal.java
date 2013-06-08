package TFC.Blocks;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import TFC.Reference;
import TFC.Items.Tools.ItemCustomShovel;

public class BlockCharcoal extends BlockTerra {

	public BlockCharcoal(int par1) 
	{
		super(par1, Material.ground);
	}

	@Override
	public Icon getIcon(int i, int j) 
	{
		return blockIcon;
	}
	@Override
	public void registerIcons(IconRegister iconRegisterer)
    {
		this.blockIcon = iconRegisterer.registerIcon(Reference.ModID + ":" + "devices/Charcoal");
    }

	@Override
	public boolean canBeReplacedByLeaves(World w, int x, int y, int z)
	{
		return false;
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
							world.setBlockMetadataWithNotify(i, j+top, k, m1-1, 2);
						}
						else
							world.setBlockToAir(i, j+top, k);

						world.setBlock(i, j, k, blockID, 8, 2);
					}
					else
					{
						world.setBlock(i, j, k, blockID, l-1, 2);
					}

					world.markBlockForUpdate(i, j, k);
					world.markBlockForUpdate(i, j+top, k);
				}
				else
					world.setBlock(i, j, k, 0, 0, 2);
			}
			else
			{
				world.setBlock(i, j, k, blockID, l, 2);
			}

			if(l == 0)
				world.setBlockToAir(i, j, k);
		}
	}
	
	@Override
	public boolean removeBlockByPlayer(World world, EntityPlayer player, int x, int y, int z) 
	{
		if(world.getBlockMetadata(x, y, z) > 0)
			return false;

		return world.setBlockToAir(x, y, z);
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
				world.setBlock(i, j, k, blockID, m2, 0);
			else
				world.setBlockToAir(i, j, k);

			world.setBlock(i, j-1, k, blockID, bottomMeta, 0x3);
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
				world.setBlock(i, j+1, k, blockID, m2, 0x3);
			else
				world.setBlockToAir(i, j+1, k);

			world.setBlock(i, j, k, blockID, bottomMeta, 0x3);
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
				world.setBlock(i, j-1, k, blockID, meta, 0);
				world.setBlockToAir(i, j, k);
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

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int i, int j, int k) 
	{
		int meta = par1IBlockAccess.getBlockMetadata(i, j, k);
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, (0.125f * meta), 1.0F);
	}
	
	@Override
	public void onBlockDestroyedByExplosion(World world, int i, int j, int k, Explosion ex) 
	{
		if(!world.isRemote)
		{
		}
	}
}
