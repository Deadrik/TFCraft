package com.bioxx.tfc.Items.Tools;

import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.TFCItems;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Items.ItemTerra;
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
		Block block = world.getBlock(x, y, z);
		boolean surroundSolids = world.isSideSolid(x, y, z + 1, ForgeDirection.NORTH)
				&& world.isSideSolid(x, y, z - 1, ForgeDirection.SOUTH)
				&& world.isSideSolid(x - 1, y, z, ForgeDirection.EAST)
				&& world.isSideSolid(x + 1, y, z, ForgeDirection.WEST);
		boolean surroundRock = world.getBlock(x, y - 1, z).getMaterial() == Material.rock
				&& world.getBlock(x + 1, y, z).getMaterial() == Material.rock
				&& world.getBlock(x - 1, y, z).getMaterial() == Material.rock
				&& world.getBlock(x, y, z + 1).getMaterial() == Material.rock
				&& world.getBlock(x, y, z - 1).getMaterial() == Material.rock
				&& world.getBlock(x, y - 1, z).isNormalCube();
		boolean canBeUsed = side == 1
				&& block.isNormalCube()
				&& block.isOpaqueCube()
				&& block.getMaterial() != Material.wood
				&& block.getMaterial() != Material.cloth
				&& world.isAirBlock(x, y + 1, z)
				&& block != TFCBlocks.Charcoal
				&& block != Blocks.coal_block;

		if(!world.isRemote)
		{
			int chance = world.rand.nextInt(100);
			if(canBeUsed)
			{
				List list = world.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(x, y + 1, z, x + 1, y + 2, z + 1));
				int numsticks = 0;
				int hasStraw = 0;

				if (list != null && !list.isEmpty())
				{
					for (Iterator iterator = list.iterator(); iterator.hasNext();)
					{
						EntityItem entity = (EntityItem)iterator.next();
						if(entity.getEntityItem().getItem() == TFCItems.Straw)
							hasStraw = 40;
						else if(entity.getEntityItem().getItem() == TFCItems.Stick)
							numsticks+=entity.getEntityItem().stackSize;
					}
				}

				itemstack.damageItem(1, entityplayer);

				if(itemstack.getItemDamage() >= itemstack.getMaxDamage())
					itemstack.stackSize = 0;

				if(chance > 70 - hasStraw)
				{
					if(numsticks >= 3)
					{
						for (Iterator iterator = list.iterator(); iterator.hasNext();)
						{
							EntityItem entity = (EntityItem)iterator.next();
							if(entity.getEntityItem().getItem() == TFCItems.Stick || entity.getEntityItem().getItem() == TFCItems.Straw)
								entity.setDead();
						}
						world.setBlock(x, y + 1, z, TFCBlocks.Firepit, 1, 2);
					}
					return true;
				}
			}
			else if((block == TFCBlocks.Charcoal && world.getBlockMetadata(x, y, z) > 6) || block == Blocks.coal_block)
			{
				if(surroundRock && surroundSolids)
				{
					if(chance > 70)
					{
						world.setBlock(x, y, z, TFCBlocks.Forge, 1, 2);
					}
					itemstack.damageItem(1, entityplayer);
					return true;
				}
			}
			else if(block == TFCBlocks.Pottery && surroundSolids)
			{
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
		else
		{
			Boolean genSmoke = false;
			Boolean genSpark = false;
			if(canBeUsed)
			{
				genSmoke = true;
			}
			else if((block == TFCBlocks.Charcoal && world.getBlockMetadata(x, y, z) > 6) || block == Blocks.coal_block)
			{
				if(surroundRock && surroundSolids)
				{
					genSmoke = true;
					genSpark = true;
				}
			}
			else if(block == TFCBlocks.Pottery && surroundSolids)
			{
				genSmoke = true;
			}

			if(genSmoke)
				world.spawnParticle("smoke", hitX + x, hitY + y, hitZ + z, 0.0F, 0.1F, 0.0F);

			if(genSpark)
				world.spawnParticle("flame", hitX + x, hitY + y, hitZ + z, 0.0F, 0.0F, 0.0F);
		}
		return false;
	}

	@Override
	public EnumItemReach getReach(ItemStack is)
	{
		return EnumItemReach.SHORT;
	}
}
