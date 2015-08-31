package com.bioxx.tfc.Blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Items.Tools.ItemCustomShovel;
import com.bioxx.tfc.api.TFCItems;

public class BlockCharcoal extends BlockTerra
{
	public BlockCharcoal()
	{
		super(Material.ground);
	}

	@Override
	public IIcon getIcon(int side, int meta)
	{
		return blockIcon;
	}

	@Override
	public void registerBlockIcons(IIconRegister iconRegisterer)
	{
		this.blockIcon = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "devices/Charcoal");
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
	public void onBlockHarvested(World world, int x, int y, int z, int side, EntityPlayer entityplayer)
	{
		if(!world.isRemote)
		{
			if (entityplayer.capabilities.isCreativeMode)
			{
				world.setBlockToAir(x, y, z);
			}
			else
			{
				//we need to make sure the player has the correct tool out
				boolean isShovel = false;
				ItemStack equip = entityplayer.getCurrentEquippedItem();
				if (equip != null)
				{
					if (equip.getItem() instanceof ItemCustomShovel)
						isShovel = true;
				}

				if (isShovel)
				{
					int top = 0;
					while (world.getBlock(x, y + top + 1, z) == this)
						++top;

					dropBlockAsItem(world, x, y, z, new ItemStack(TFCItems.coal, 1, 1));
					if (side - 1 > 0)
					{
						if (world.getBlock(x, y + 1, z) == this)
						{
							int m1 = world.getBlockMetadata(x, y + top, z);
							if (m1 - 1 > 0)
								world.setBlockMetadataWithNotify(x, y + top, z, m1 - 1, 2);
							else
								world.setBlockToAir(x, y + top, z);

							world.setBlock(x, y, z, this, 8, 2);
						}
						else
						{
							world.setBlock(x, y, z, this, side - 1, 2);
						}

						world.markBlockForUpdate(x, y, z);
						world.markBlockForUpdate(x, y + top, z);
					}
					else
						world.setBlock(x, y, z, Blocks.air, 0, 2);
				}
				else
				{
					world.setBlock(x, y, z, this, side, 2);
				}

				if (side == 0)
					world.setBlockToAir(x, y, z);
			}
		}
	}

	@Override
	public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z)
	{
		if(world.getBlockMetadata(x, y, z) > 0)
			return false;
		return world.setBlockToAir(x, y, z); // super.removedByPlayer is deprecated, and causes a loop.
	}

	public void combineCharcoalDown(World world, int x, int y, int z)
	{
		int meta = world.getBlockMetadata(x, y, z);
		int bottomMeta = world.getBlockMetadata(x, y - 1, z);

		if(bottomMeta < 8)
		{
			bottomMeta = bottomMeta + meta;
			int m2 = 0;
			if(bottomMeta > 8)
			{
				m2 = bottomMeta - 8;
				bottomMeta = 8;
			}

			world.setBlock(x, y - 1, z, this, bottomMeta, 0x2);

			if(m2 > 0)
			{
				world.setBlock(x, y, z, this, m2, 0x2);
				world.notifyBlockOfNeighborChange(x, y + 1, z, this);
			}
			else
				world.setBlockToAir(x, y, z);
		}
	}

	public void combineCharcoalUp(World world, int x, int y, int z)
	{
		int meta = world.getBlockMetadata(x, y + 1, z);
		int bottomMeta = world.getBlockMetadata(x, y, z);

		if(bottomMeta < 8)
		{
			bottomMeta = bottomMeta + meta;
			int m2 = 0;
			if(bottomMeta > 8)
			{
				m2 = bottomMeta - 8;
				bottomMeta = 8;
			}

			world.setBlock(x, y, z, this, bottomMeta, 0x2);

			if(m2 > 0)
			{
				world.setBlock(x, y+1, z, this, m2, 0x2);
				world.notifyBlockOfNeighborChange(x, y + 2, z, this);
			}
			else
				world.setBlockToAir(x, y + 1, z);
		}
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
	{
		if(!world.isRemote)
		{
			if(world.isAirBlock(x, y - 1, z))
			{
				int meta = world.getBlockMetadata(x, y, z);
				world.setBlock(x, y - 1, z, this, meta, 0x2);
				world.setBlockToAir(x, y, z);
			}
			else
			{
				if(world.getBlock(x, y - 1, z) == this)
					combineCharcoalDown(world, x, y, z);

				if(world.getBlock(x, y + 1, z) == this)
					combineCharcoalUp(world, x, y, z);
			}
		}
	}

	/**
	 * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
	 * cleared to be reused)
	 */
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		int md = world.getBlockMetadata(x, y, z);

		if (md == 8)
			return AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 1, z + 1);

		return AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + (0.125f * md), z + 1);
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess bAccess, int x, int y, int z)
	{
		int meta = bAccess.getBlockMetadata(x, y, z);
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125f * meta, 1.0F);
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
				amount = rand.nextInt(amount + 1) + (amount / 2);
				dropBlockAsItem(world, x, y, z, new ItemStack(TFCItems.coal, amount, 1));
			}
		}

		super.onBlockExploded(world, x, y, z, ex);
	}

	@Override
	public void onBlockDestroyedByExplosion(World world, int x, int y, int z, Explosion ex)
	{
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z)
	{
		return null;
	}
}
