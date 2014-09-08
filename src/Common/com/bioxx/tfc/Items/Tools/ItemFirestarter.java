package com.bioxx.tfc.Items.Tools;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.TFCItems;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Items.ItemTerra;
import com.bioxx.tfc.TileEntities.TEForge;
import com.bioxx.tfc.TileEntities.TEPottery;
import com.bioxx.tfc.api.Enums.EnumItemReach;
import com.bioxx.tfc.api.Enums.EnumSize;

public class ItemFirestarter extends ItemTerra
{
	public ItemFirestarter()
	{
		super();
		this.setMaxDamage(8);
		this.hasSubtypes = false;
		setCreativeTab(TFCTabs.TFCTools);
	}

	@Override
	public EnumSize getSize(ItemStack is)
	{
		return EnumSize.SMALL;
	}

	public int getPlacedBlockMetadata(int i)
	{
		return i;
	}

	@Override
	public boolean canStack()
	{
		return false;
	}

	@Override
	public boolean onItemUseFirst(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		if(!world.isRemote)
		{
			boolean surroundSolids = world.isSideSolid(x, y, z + 1, ForgeDirection.NORTH) && world.isSideSolid(x, y, z - 1, ForgeDirection.SOUTH) &&
					world.isSideSolid(x - 1, y, z, ForgeDirection.EAST) && world.isSideSolid(x + 1, y, z, ForgeDirection.WEST);

			if(side == 1 && world.getBlock(x, y, z).isNormalCube() &&
					world.getBlock(x, y, z).isOpaqueCube() &&
					world.getBlock(x, y, z).getMaterial() != Material.wood &&
					world.getBlock(x, y, z).getMaterial() != Material.cloth &&
					world.isAirBlock(x, y + 1, z) &&
					world.getBlock(x, y, z) != TFCBlocks.Charcoal &&
					world.getBlock(x, y, z) != Blocks.coal_block)
			{

				List list = world.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(x, y + 1, z, x + 1, y + 2, z + 1));
				int numsticks = 0;
				int hasPaper = 0;

				if (list != null && !list.isEmpty())
				{
					for (Iterator iterator = list.iterator(); iterator.hasNext();)
					{
						EntityItem entity = (EntityItem)iterator.next();
						if(entity.getEntityItem().getItem() == Items.paper)
							hasPaper = 40;
						else if(entity.getEntityItem().getItem() == TFCItems.Stick)
							numsticks+=entity.getEntityItem().stackSize;
					}
				}

				itemstack.damageItem(1, entityplayer);

				if(itemstack.getItemDamage() >= itemstack.getMaxDamage())
					itemstack.stackSize = 0;

				int chance = new Random().nextInt(100);
				if(chance > 70 - hasPaper)
				{
					if(numsticks >= 3)
					{
						for (Iterator iterator = list.iterator(); iterator.hasNext();)
						{
							EntityItem entity = (EntityItem)iterator.next();
							if(entity.getEntityItem().getItem() == TFCItems.Stick)
								entity.setDead();
							if(entity.getEntityItem().getItem() == Items.paper)
								entity.setDead();
						}
						world.setBlock(x, y + 1, z, TFCBlocks.Firepit, 1, 2);
					}
					return true;
				}
			}
			else if((world.getBlock(x, y, z) == TFCBlocks.Charcoal && world.getBlockMetadata(x, y, z) > 6) || world.getBlock(x, y, z) == Blocks.coal_block)
			{
				if(world.getBlock(x, y - 1, z).getMaterial() == Material.rock &&
						world.getBlock(x + 1, y, z).getMaterial() == Material.rock &&
						world.getBlock(x - 1, y, z).getMaterial() == Material.rock &&
						world.getBlock(x, y, z + 1).getMaterial() == Material.rock &&
						world.getBlock(x, y, z - 1).getMaterial() == Material.rock &&
						world.getBlock(x, y - 1, z).isNormalCube() && surroundSolids)
				{
					int chance = new Random().nextInt(100);
					if(chance > 70)
					{
						world.setBlock(x, y, z, TFCBlocks.Forge, 1, 2);
						TEForge te = (TEForge)world.getTileEntity(x, y, z);
					}
					itemstack.damageItem(1, entityplayer);
					return true;
				}
			}
			else if(world.getBlock(x, y, z) == TFCBlocks.Pottery && surroundSolids)
			{
				int chance = new Random().nextInt(100);
				if(chance > 70)
				{
					TEPottery te = (TEPottery) world.getTileEntity(x, y, z);
					te.StartPitFire();
				}
				itemstack.damageItem(1, entityplayer);
				return true;
			}
			return false;
		}
		return false;
	}

	@Override
	public EnumItemReach getReach(ItemStack is)
	{
		return EnumItemReach.SHORT;
	}
}
