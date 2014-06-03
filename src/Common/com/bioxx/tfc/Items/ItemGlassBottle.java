package com.bioxx.tfc.Items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import com.bioxx.tfc.TFCItems;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Core.TFC_Core;

public class ItemGlassBottle extends ItemTerra
{
	public ItemGlassBottle()
	{
		this.setCreativeTab(TFCTabs.tabFood);
	}

	/**
	 * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
	 */
	@Override
	public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer player)
	{
		MovingObjectPosition mop = this.getMovingObjectPositionFromPlayer(world, player, true);

		if (mop == null)
		{
			return is;
		}
		else
		{
			if (mop.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
			{
				int x = mop.blockX;
				int y = mop.blockY;
				int z = mop.blockZ;

				if (!world.canMineBlock(player, x, y, z))
				{
					return is;
				}

				if (!player.canPlayerEdit(x, y, z, mop.sideHit, is))
				{
					return is;
				}

				if (!world.isRemote && TFC_Core.isFreshWater(world.getBlock(x, y, z)) && !player.isSneaking())
				{
					--is.stackSize;

					if (is.stackSize <= 0)
					{
						return new ItemStack(TFCItems.Potion);
					}

					if (!player.inventory.addItemStackToInventory(new ItemStack(TFCItems.Potion)))
					{
						player.dropPlayerItemWithRandomChoice(new ItemStack(TFCItems.Potion, 1, 0), false);
					}
				}
			}

			return is;
		}
	}
}
