package com.bioxx.tfc.Items.Tools;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.FillBucketEvent;

import com.bioxx.tfc.TFCItems;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Entities.Mobs.EntityCowTFC;
import com.bioxx.tfc.Items.ItemTerra;
import com.bioxx.tfc.TileEntities.TEBarrel;
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
		float var4 = 1.0F;
		double x = player.prevPosX + (player.posX - player.prevPosX) * var4;
		double y = player.prevPosY + (player.posY - player.prevPosY) * var4 + 1.62D - player.yOffset;
		double z = player.prevPosZ + (player.posZ - player.prevPosZ) * var4;
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
				int i = mop.blockX;
				int j = mop.blockY;
				int k = mop.blockZ;

				if (!world.canMineBlock(player, i, j, k))
					return is;

				if (this.bucketContents == Blocks.air)
				{
					if (!player.canPlayerEdit(i, j, k, mop.sideHit, is))
						return is;

					FillBucketEvent event = new FillBucketEvent(player, is, world, mop);
					if (event.isCanceled())
						return is;

					if (event.getResult() == Event.Result.ALLOW)
						return event.result;

					if (TFC_Core.isFreshWater(world.getBlock(i, j, k)) && world.getBlockMetadata(i, j, k) <= 2)
					{
						world.setBlockToAir(i, j, k);
						if (player.capabilities.isCreativeMode)
							return is;

						return new ItemStack(TFCItems.WoodenBucketWater);
					}
					else if (TFC_Core.isSaltWater(world.getBlock(i, j, k)) && world.getBlockMetadata(i, j, k) <= 2)
					{
						world.setBlockToAir(i, j, k);
						if (player.capabilities.isCreativeMode)
							return is;
						return new ItemStack(TFCItems.WoodenBucketSaltWater);
					}
				}
				else
				{
					if (this.bucketContents != Blocks.air)
						return new ItemStack(TFCItems.WoodenBucketEmpty);

					if (world.getTileEntity(i, j, k) != null && world.getTileEntity(i, j, k) instanceof TEBarrel)
					{
						TEBarrel te = (TEBarrel)world.getTileEntity(i, j, k);

						//FIXME Removed this because the barrel code needs to use blocks and not ids from now on
						/*if(te.checkValidAddition(this.bucketContents))
							return new ItemStack(TFCItems.WoodenBucketEmpty);*/
					}
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
	public EnumItemReach getReach(ItemStack is)
	{
		return EnumItemReach.SHORT;
	}
}
