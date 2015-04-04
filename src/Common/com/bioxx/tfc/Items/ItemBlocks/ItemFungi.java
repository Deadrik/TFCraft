package com.bioxx.tfc.Items.ItemBlocks;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;

import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.Constant.Global;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemFungi extends ItemTerraBlock
{
	public ItemFungi(Block b)
	{
		super(b);
		MetaNames = Global.FUNGI_META_NAMES;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int par1)
	{
		return TFCBlocks.Fungi.getIcon(0, par1);
	}

	/**
	 * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
	 */
	@Override
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

				if (TFCBlocks.Fungi.canBlockStay(par2World, i, j + 1, k) && par2World.isAirBlock(i, j + 1, k))
				{
					par2World.setBlock(i, j + 1, k, TFCBlocks.Fungi, par1ItemStack.getItemDamage(), 0x3);
					par2World.playSoundEffect(i + 0.5F, j + 0.5F, k + 0.5F, TFCBlocks.Fungi.stepSound.func_150496_b(), (TFCBlocks.Fungi.stepSound.getVolume() + 1.0F) / 2.0F, TFCBlocks.Fungi.stepSound.getPitch() * 0.8F);
					if (!par3EntityPlayer.capabilities.isCreativeMode) --par1ItemStack.stackSize;
				}
			}
			return par1ItemStack;
		}
	}
}
