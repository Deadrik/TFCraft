package com.bioxx.tfc.Items;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.bioxx.tfc.api.TFCBlocks;

public class ItemReeds extends Item
{
	private Block reeds = TFCBlocks.reeds;

	public ItemReeds()
	{
	}

	/**
	 * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
	 * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
	 */
	@Override
	public boolean onItemUse(ItemStack is, EntityPlayer player, World world, int x, int y, int z, int side, float par8, float par9, float par10)
	{
		Block block = world.getBlock(x, y, z);

		if (block == Blocks.snow_layer && (world.getBlockMetadata(x, y, z) & 7) < 1)
		{
			side = 1;
		}
		else if (block != TFCBlocks.vine && block != TFCBlocks.tallGrass && block != Blocks.deadbush)
		{
			if (side == 0) --y;
			if (side == 1) ++y;
			if (side == 2) --z;
			if (side == 3) ++z;
			if (side == 4) --x;
			if (side == 5) ++x;
		}

		if (!player.canPlayerEdit(x, y, z, side, is))
		{
			return false;
		}
		else if (is.stackSize == 0)
		{
			return false;
		}
		else
		{
			if (world.canPlaceEntityOnSide(this.reeds, x, y, z, false, side, (Entity)null, is))
			{
				int i1 = this.reeds.onBlockPlaced(world, x, y, z, side, par8, par9, par10, 0);
				if (world.setBlock(x, y, z, this.reeds, i1, 3))
				{
					if (world.getBlock(x, y, z) == this.reeds)
					{
						this.reeds.onBlockPlacedBy(world, x, y, z, player, is);
						this.reeds.onPostBlockPlaced(world, x, y, z, i1);
					}
					world.playSoundEffect(x + 0.5F, y + 0.5F, z + 0.5F, this.reeds.stepSound.func_150496_b(), (this.reeds.stepSound.getVolume() + 1.0F) / 2.0F, this.reeds.stepSound.getPitch() * 0.8F);
					--is.stackSize;
				}
			}
			return true;
		}
	}
}
