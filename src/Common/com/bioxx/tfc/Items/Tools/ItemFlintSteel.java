package com.bioxx.tfc.Items.Tools;

import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Items.ItemTerra;
import com.bioxx.tfc.TileEntities.TEPottery;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.Enums.EnumItemReach;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;
import com.bioxx.tfc.api.Interfaces.ISize;

public class ItemFlintSteel extends ItemFlintAndSteel implements ISize
{
	public ItemFlintSteel()
	{
		super();
		setCreativeTab(TFCTabs.TFC_TOOLS);
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		if (!world.isRemote)
		{
			Block block = world.getBlock(x, y, z);
			boolean surroundSolids = TFC_Core.isSurroundedSolid(world, x, y, z);
			// Attempt to create forge
			if (block == TFCBlocks.charcoal && world.getBlockMetadata(x, y, z) > 6 ||
				block == Blocks.coal_block)
			{
				if (TFC_Core.isSurroundedStone(world, x, y, z) && surroundSolids)
				{
					triggerUse(itemstack, entityplayer, world, x, y, z);
					world.setBlock(x, y, z, TFCBlocks.forge, 1, 0x2);
					return true;
				}
			}
			// Attempt to light pottery
			else if (block == TFCBlocks.pottery && surroundSolids)
			{
				TEPottery te = (TEPottery) world.getTileEntity(x, y, z);
				te.startPitFire();
				triggerUse(itemstack, entityplayer, world, x, y, z);
				return true;
			}
			// Attempt to create fire pit
			else if (side == 1 && TFC_Core.isTopFaceSolid(world, x, y, z) && world.isAirBlock(x, y + 1, z) &&
					block.getMaterial() != Material.wood && block.getMaterial() != Material.cloth)
			{
				List list = world.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(x, y + 1, z, x + 1, y + 2, z + 1));
				int numsticks = 0;

				if (list != null && !list.isEmpty())
				{
					for (Iterator iterator = list.iterator(); iterator.hasNext();)
					{
						EntityItem entity = (EntityItem) iterator.next();
						if (entity.getEntityItem().getItem() == TFCItems.stick)
							numsticks += entity.getEntityItem().stackSize;
					}

					if (numsticks >= 3)
					{
						for (Iterator iterator = list.iterator(); iterator.hasNext();)
						{
							EntityItem entity = (EntityItem) iterator.next();
							if (entity.getEntityItem().getItem() == TFCItems.stick || entity.getEntityItem().getItem() == TFCItems.straw)
								entity.setDead();
						}
						triggerUse(itemstack, entityplayer, world, x, y, z);
						world.setBlock(x, y + 1, z, TFCBlocks.firepit, 1, 0x2);
						if (world.isRemote)
							world.markBlockForUpdate(x, y + 1, z);
						return true;
					}
				}
			}

			// Only triggers if all previous attempts failed
			if (!block.onBlockActivated(world, x, y, z, entityplayer, side, hitX, hitY, hitZ))
			{
				return super.onItemUse(itemstack, entityplayer, world, x, y, z, side, hitX, hitY, hitZ);
			}
		}

		return false;
	}

	private void triggerUse(ItemStack is, EntityPlayer player, World world, int x, int y, int z)
	{
		world.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "fire.ignite", 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);
		is.damageItem(1, player);
	}


	@Override
	public EnumSize getSize(ItemStack is)
	{
		return EnumSize.VERYSMALL;
	}

	@Override
	public EnumWeight getWeight(ItemStack is)
	{
		return EnumWeight.LIGHT;
	}

	@Override
	public boolean canStack()
	{
		return false;
	}

	@Override
	public EnumItemReach getReach(ItemStack is)
	{
		return EnumItemReach.SHORT;
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag)
	{
		ItemTerra.addSizeInformation(is, arraylist);
	}
}
