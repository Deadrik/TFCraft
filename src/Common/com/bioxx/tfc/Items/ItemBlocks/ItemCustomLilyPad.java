package com.bioxx.tfc.Items.ItemBlocks;

import com.bioxx.tfc.TFCBlocks;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemColored;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemCustomLilyPad extends ItemColored
{
	public ItemCustomLilyPad(Block block)
	{
		super(block, false);
	}

	/**
	 * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
	 */
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(par2World, par3EntityPlayer, true);

		if (movingobjectposition == null)
		{
			return par1ItemStack;
		}
		else
		{
			if (movingobjectposition.typeOfHit == MovingObjectType.BLOCK)
			{
				int i = movingobjectposition.blockX;
				int j = movingobjectposition.blockY;
				int k = movingobjectposition.blockZ;

				if (!par2World.canMineBlock(par3EntityPlayer, i, j, k))
					return par1ItemStack;

				if (!par3EntityPlayer.canPlayerEdit(i, j, k, movingobjectposition.sideHit, par1ItemStack))
					return par1ItemStack;

				if (TFCBlocks.LilyPad.canBlockStay(par2World, i, j + 1, k)&& par2World.isAirBlock(i, j + 1, k))
				{
					par2World.setBlock(i, j + 1, k, TFCBlocks.LilyPad);
					par2World.spawnParticle("splash", i, j + 2, k, 0.0D, 0.0D, 0.0D);
					par2World.playSoundEffect((double)((float)i + 0.5F), (double)((float)j + 0.5F), (double)((float)k + 0.5F), "random.splash", 0.5F, TFCBlocks.LilyPad.stepSound.getPitch() * 0.8F);
					if (!par3EntityPlayer.capabilities.isCreativeMode)
						--par1ItemStack.stackSize;
				}
			}
			return par1ItemStack;
		}
	}

	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack par1ItemStack, int par2)
	{
		return TFCBlocks.LilyPad.getRenderColor(par1ItemStack.getItemDamage());
	}
}
