package com.bioxx.tfc.Items.ItemBlocks;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;

import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Enums.EnumWeight;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemFlowers extends ItemTerraBlock
{
	public ItemFlowers(Block b)
	{
		super(b);
		MetaNames = Global.FLOWER_META_NAMES;
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int dmg)
	{
		return TFCBlocks.Flowers.getIcon(0, dmg);
	}

	/**
	 * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
	 */
	public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer player)
	{
		MovingObjectPosition mop = this.getMovingObjectPositionFromPlayer(world, player, true);

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

				if (!player.canPlayerEdit(x, y, z, mop.sideHit, is))
					return is;

				if (TFCBlocks.Flowers.canBlockStay(world, x, y + 1, z) && world.isAirBlock(x, y + 1, z))
				{
					world.setBlock(x, y + 1, z, TFCBlocks.Flowers, is.getItemDamage(), 0x3);
					world.playSoundEffect((double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), TFCBlocks.Flowers.stepSound.func_150496_b(), (TFCBlocks.Flowers.stepSound.getVolume() + 1.0F) / 2.0F, TFCBlocks.Flowers.stepSound.getPitch() * 0.8F);
					if (!player.capabilities.isCreativeMode) --is.stackSize;
				}
			}
			return is;
		}
	}

	@Override
	public EnumWeight getWeight(ItemStack is)
	{
		return EnumWeight.MEDIUM;
	}
}
