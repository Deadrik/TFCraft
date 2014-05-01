package com.bioxx.tfc.Blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.TFCItems;
import com.bioxx.tfc.Items.Tools.ItemCustomShovel;

public class BlockCharcoal extends BlockTerra
{
	public BlockCharcoal()
	{
		super(Material.ground);
	}

	@Override
	public IIcon getIcon(int i, int j)
	{
		return blockIcon;
	}

	@Override
	public void registerBlockIcons(IIconRegister iconRegisterer)
	{
		this.blockIcon = iconRegisterer.registerIcon(Reference.ModID + ":" + "devices/Charcoal");
	}

	@Override
	public boolean canBeReplacedByLeaves(IBlockAccess w, int x, int y, int z)
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
				if(equip.getItem() instanceof ItemCustomShovel)
					isShovel = true;

			if(isShovel)
			{
				int top = 0;
				while(world.getBlock(i, j+top+1, k) == this)
					++top;

				dropBlockAsItem(world, i, j, k, new ItemStack(TFCItems.Coal,1,1));
				if(l-1 > 0)
				{
					if(world.getBlock(i, j+1, k) == this)
					{
						int m1 = world.getBlockMetadata(i, j+top, k);
						if(m1-1 > 0)
							world.setBlockMetadataWithNotify(i, j+top, k, m1-1, 2);
						else
							world.setBlockToAir(i, j+top, k);

						world.setBlock(i, j, k, this, 8, 2);
					}
					else
					{
						world.setBlock(i, j, k, this, l-1, 2);
					}
					world.markBlockForUpdate(i, j, k);
					world.markBlockForUpdate(i, j+top, k);
				}
				else
					world.setBlockToAir(i, j, k);
			}
			else
			{
				world.setBlock(i, j, k, this, l, 2);
			}
			if(l == 0)
				world.setBlockToAir(i, j, k);
		}
	}

	@Override
	public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z)
	{
		if(world.getBlockMetadata(x, y, z) > 0)
			return false;
		return super.removedByPlayer(world, player, x, y, z);
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

			world.setBlock(i, j-1, k, this, bottomMeta, 0x2);

			if(m2 > 0)
			{
				world.setBlock(i, j, k, this, m2, 0x2);
				world.notifyBlockOfNeighborChange(i, j+1, k, this);
			}
			else
				world.setBlockToAir(i, j, k);
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

			world.setBlock(i, j, k, this, bottomMeta, 0x2);

			if(m2 > 0)
			{
				world.setBlock(i, j+1, k, this, m2, 0x2);
				world.notifyBlockOfNeighborChange(i, j+2, k, this);
			}
			else
				world.setBlockToAir(i, j+1, k);
		}
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, Block block)
	{
		if(!world.isRemote)
		{
			if(world.isAirBlock(i, j-1, k))
			{
				int meta = world.getBlockMetadata(i, j, k);
				world.setBlock(i, j-1, k, this, meta, 0x2);
				world.setBlockToAir(i, j, k);
			}
			else
			{
				if(world.getBlock(i, j-1, k) == this)
					combineCharcoalDown(world, i, j, k);

				if(world.getBlock(i, j+1, k) == this)
					combineCharcoalUp(world, i, j, k);
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
	public boolean canDropFromExplosion(Explosion ex)
	{
		return false;
	}

	@Override
	public void onBlockExploded(World world, int x, int y, int z, Explosion ex)
	{
		if(!world.isRemote)
		{
			int amount = world.getBlockMetadata(x, y, z);
			if(amount > 0)
			{
				Random rand = new Random();
				// Between 50% and 100% of the amount
				amount = rand.nextInt(amount + 1) + (amount/2);
				dropBlockAsItem(world, x, y, z, new ItemStack(TFCItems.Coal,amount,1));
			}
		}

		super.onBlockExploded(world, x, y, z, ex);
	}

	@Override
	public void onBlockDestroyedByExplosion(World world, int i, int j, int k, Explosion ex)
	{
	}
}
