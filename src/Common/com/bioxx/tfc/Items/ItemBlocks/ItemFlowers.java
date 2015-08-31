package com.bioxx.tfc.Items.ItemBlocks;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.api.Enums.EnumWeight;

public class ItemFlowers extends ItemTerraBlock
{
	public ItemFlowers(Block b)
	{
		super(b);
		metaNames = new String[]{"flower_dandelion","flower_nasturtium", "flower_meads_milkweed", "flower_tropical_milkweed", "flower_butterfly_milkweed", "flower_calendula"};
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int par1)
	{
		return this.field_150939_a.getIcon(0, par1);
	}

	@Override
	public EnumWeight getWeight(ItemStack is)
	{
		return EnumWeight.LIGHT;
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
			if (mop.typeOfHit == MovingObjectType.BLOCK)
			{
				int i = mop.blockX;
				int j = mop.blockY;
				int k = mop.blockZ;

				if (!world.canMineBlock(player, i, j, k))
					return is;

				if (!player.canPlayerEdit(i, j, k, mop.sideHit, is))
					return is;

				if (this.field_150939_a.canBlockStay(world, i, j + 1, k) && world.isAirBlock(i, j + 1, k))
				{
					world.setBlock(i, j + 1, k, this.field_150939_a, is.getItemDamage(), 0x3);
					world.playSoundEffect(i + 0.5F, j + 0.5F, k + 0.5F, this.field_150939_a.stepSound.func_150496_b(), (this.field_150939_a.stepSound.getVolume() + 1.0F) / 2.0F, this.field_150939_a.stepSound.getPitch() * 0.8F);
					if (!player.capabilities.isCreativeMode) --is.stackSize;
				}
			}
			return is;
		}
	}

}
