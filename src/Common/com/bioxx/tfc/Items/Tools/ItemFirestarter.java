package com.bioxx.tfc.Items.Tools;

import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;

import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Sounds;
import com.bioxx.tfc.Items.ItemTerra;
import com.bioxx.tfc.TileEntities.TEPottery;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.Enums.EnumItemReach;
import com.bioxx.tfc.api.Enums.EnumSize;

public class ItemFirestarter extends ItemTerra
{
	private boolean canBeUsed;
	private boolean isCoal;
	private boolean isPottery;

	public ItemFirestarter()
	{
		super();
		this.setMaxDamage(8);
		this.hasSubtypes = false;
		setCreativeTab(TFCTabs.TFC_TOOLS);
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
	public EnumAction getItemUseAction(ItemStack is)
	{
		return EnumAction.bow;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack is)
	{
		return 20;
	}

	@Override
	public void onUsingTick(ItemStack stack, EntityPlayer player, int count)
	{
		this.setFlags(player);
		MovingObjectPosition mop = this.getMovingObjectPositionFromPlayer(player.worldObj, player, true);
		if(mop != null && mop.typeOfHit == MovingObjectType.BLOCK)
		{
			World world = player.worldObj;
			int x = mop.blockX;
			int y = mop.blockY;
			int z = mop.blockZ;
			double hitX = mop.hitVec.xCoord;
			double hitY = mop.hitVec.yCoord;
			double hitZ = mop.hitVec.zCoord;
			int chance = world.rand.nextInt(100);

			if(world.getBlock(x, y + 1, z) == TFCBlocks.firepit)
				player.stopUsingItem();

			if(count > 0 && world.isRemote)
			{
				Boolean genSmoke = canBeUsed || isCoal || isPottery;

				if(genSmoke && chance > 70)
					world.spawnParticle("smoke", hitX, hitY, hitZ, 0.0F, 0.1F, 0.0F);

				if(count < 4 && chance > 70)
					world.spawnParticle("flame", hitX, hitY, hitZ, 0.0F, 0.0F, 0.0F);

				if (count < this.getMaxItemUseDuration(null) - 4 && count % 3 == 1)
					player.playSound(TFC_Sounds.FIRESTARTER, /*volume*/0.5f, /*pitch*/0.05F);
			}
			else if(!world.isRemote && count == 1)
			{
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
							if(entity.getEntityItem().getItem() == TFCItems.straw)
								hasStraw = 40;
							else if(entity.getEntityItem().getItem() == TFCItems.stick)
								numsticks+=entity.getEntityItem().stackSize;
						}

						if (chance > 70 - hasStraw && numsticks >= 3)
						{
							for (Iterator iterator = list.iterator(); iterator.hasNext();)
							{
								EntityItem entity = (EntityItem) iterator.next();
								if (entity.getEntityItem().getItem() == TFCItems.stick || entity.getEntityItem().getItem() == TFCItems.straw)
									entity.setDead();
							}
							world.setBlock(x, y + 1, z, TFCBlocks.firepit, 1, 2);
						}
					}

					stack.damageItem(1, player);
					if(stack.getItemDamage() >= stack.getMaxDamage())
						stack.stackSize = 0;
				}
				else if(isCoal)
				{
					if(chance > 70)
						world.setBlock(x, y, z, TFCBlocks.forge, 1, 2);
					stack.damageItem(1, player);
				}
				else if(isPottery)
				{
					if(chance > 70)
					{
						TEPottery te = (TEPottery) world.getTileEntity(x, y, z);
						te.startPitFire();
					}
					stack.damageItem(1, player);
				}
			}
		}
	}

	@Override
	public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer player)
	{
		if (canBeUsed || isCoal || isPottery)
			player.setItemInUse(is, this.getMaxItemUseDuration(is));
		return is;
	}

	@Override
	public boolean onItemUseFirst(ItemStack is, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		this.setFlags(player);
		return false;
	}

	@Override
	public EnumItemReach getReach(ItemStack is)
	{
		return EnumItemReach.SHORT;
	}
	
	public void setFlags(EntityPlayer player)
	{
		MovingObjectPosition mop = this.getMovingObjectPositionFromPlayer(player.worldObj, player, true);
		if (mop != null && mop.typeOfHit == MovingObjectType.BLOCK)
		{
			World world = player.worldObj;
			int x = mop.blockX;
			int y = mop.blockY;
			int z = mop.blockZ;
			int side = mop.sideHit;

			Block block = world.getBlock(x, y, z);
			boolean surroundSolids = TFC_Core.isSurroundedSolid(world, x, y, z);
			boolean surroundRock = TFC_Core.isSurroundedStone(world, x, y, z);
			canBeUsed = side == 1
					&& TFC_Core.isTopFaceSolid(world, x, y, z)
					&& block.getMaterial() != Material.wood
					&& block.getMaterial() != Material.cloth
					&& world.isAirBlock(x, y + 1, z)
					&& block != TFCBlocks.charcoal
					&& block != Blocks.coal_block
					&& block != TFCBlocks.pottery;
			isCoal = (block == TFCBlocks.charcoal && world.getBlockMetadata(x, y, z) > 6 || block == Blocks.coal_block) && surroundRock && surroundSolids;
			isPottery = block == TFCBlocks.pottery && surroundSolids;
			if (isPottery)
			{
				TEPottery te = (TEPottery) world.getTileEntity(x, y, z);
				isPottery = !te.isLit() && te.wood == 8;
			}
		}
	}
}
