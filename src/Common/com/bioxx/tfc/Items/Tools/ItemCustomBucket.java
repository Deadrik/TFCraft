package com.bioxx.tfc.Items.Tools;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.FillBucketEvent;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Entities.Mobs.EntityCowTFC;
import com.bioxx.tfc.Items.ItemTerra;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.Entities.IAnimal.GenderEnum;
import com.bioxx.tfc.api.Enums.EnumItemReach;
import com.bioxx.tfc.api.Enums.EnumSize;

import cpw.mods.fml.common.eventhandler.Event;

public class ItemCustomBucket extends ItemTerra
{
	/** field for checking if the bucket has been filled. */
	private Block bucketContents;

	public ItemCustomBucket(Block contents)
	{
		super();
		this.bucketContents = contents;
		this.setFolder("tools/");
		this.setSize(EnumSize.MEDIUM);
	}

	public ItemCustomBucket(Block contents, Item container)
	{
		this(contents);
		this.setContainerItem(container);
	}

	@Override
	public boolean canStack()
	{
		return false;
	}

	/**
	 * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
	 */
	@Override
	public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer player)
	{
		boolean isEmpty = this.bucketContents == Blocks.air;
		MovingObjectPosition mop = this.getMovingObjectPositionFromPlayer(world, player, isEmpty);

		if (mop == null)
		{
			return is;
		}
		else
		{
			if (mop.typeOfHit == MovingObjectType.BLOCK)
			{
				int x = mop.blockX;
				int y = mop.blockY;
				int z = mop.blockZ;

				if (!world.canMineBlock(player, x, y, z))
					return is;

				if (isEmpty)
				{
					if (!player.canPlayerEdit(x, y, z, mop.sideHit, is))
						return is;

					FillBucketEvent event = new FillBucketEvent(player, is, world, mop);
					if (event.isCanceled())
						return is;

					if (event.getResult() == Event.Result.ALLOW)
						return event.result;

					if (TFC_Core.isFreshWater(world.getBlock(x, y, z)) && world.getBlockMetadata(x, y, z) <= 2)
					{
						world.setBlockToAir(x, y, z);
						if (player.capabilities.isCreativeMode)
							return is;

						return new ItemStack(TFCItems.WoodenBucketWater);
					}
					else if (TFC_Core.isSaltWater(world.getBlock(x, y, z)) && world.getBlockMetadata(x, y, z) <= 2)
					{
						world.setBlockToAir(x, y, z);
						if (player.capabilities.isCreativeMode)
							return is;
						return new ItemStack(TFCItems.WoodenBucketSaltWater);
					}
				}
				else
				{
					return new ItemStack(TFCItems.WoodenBucketEmpty);
				}
			}
			else if (this.bucketContents == Blocks.air && mop.entityHit instanceof EntityCowTFC && ((EntityCowTFC)mop.entityHit).getGender() == GenderEnum.FEMALE)
			{
				return new ItemStack(TFCItems.WoodenBucketMilk);
			}
			return is;
		}
	}

	@Override
	public boolean onItemUse(ItemStack is, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		
		boolean isEmpty = this.bucketContents == Blocks.air;
		int[][] map = {{0,-1,0},{0,1,0},{0,0,-1},{0,0,1},{-1,0,0},{1,0,0}};

		if (!isEmpty && world.isAirBlock( x + map[side][0], y + map[side][1], z + map[side][2] ) ) {
			world.setBlock( x + map[side][0], y + map[side][1], z + map[side][2], TFCBlocks.FreshWater, 2, 0x1 );
			player.setCurrentItemOrArmor(0, new ItemStack(TFCItems.WoodenBucketEmpty));
			return true;
		}
		
		return false;
	}

	@Override
	public EnumItemReach getReach(ItemStack is)
	{
		return EnumItemReach.SHORT;
	}
}
