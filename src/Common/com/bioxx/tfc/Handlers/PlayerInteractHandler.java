package com.bioxx.tfc.Handlers;

import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Core.Player.FoodStatsTFC;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.Util.Helper;

import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class PlayerInteractHandler
{
	private HashMap<UUID, Long> lastDrink = new HashMap<UUID, Long>();

	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		if (event.entityPlayer.worldObj.isRemote)
			return;

		ItemStack itemInHand = event.entityPlayer.getCurrentEquippedItem();

		boolean validAction = event.action == Action.RIGHT_CLICK_BLOCK || event.action == Action.RIGHT_CLICK_AIR;

		if(validAction && event.getResult() != Result.DENY && itemInHand == null)
		{
			handleDrinkingWater( event.entityPlayer );
		}
	}

	private void handleDrinkingWater(EntityPlayer entityPlayer)
	{
		Long lastCheck = lastDrink.get(entityPlayer.getUniqueID());
		if(lastCheck != null && lastCheck + 20 > TFC_Time.getTotalTicks())
			return;
		lastDrink.put(entityPlayer.getUniqueID(), TFC_Time.getTotalTicks());
		World world = entityPlayer.worldObj;
		MovingObjectPosition mop = Helper.getMovingObjectPositionFromPlayer(world, entityPlayer, true);
		FoodStatsTFC fs = TFC_Core.getPlayerFoodStats(entityPlayer);
		Boolean canDrink = fs.getMaxWater(entityPlayer) - 500 > fs.waterLevel;

		// if we found a block, and we can drink proceed
		if(mop != null && canDrink && mop.typeOfHit == MovingObjectType.BLOCK)
		{
			if( TFC_Core.isFreshWater(world.getBlock(mop.blockX, mop.blockY, mop.blockZ)) )
			{
				// recover a portion of players water, this is intentionally slow,
				fs.restoreWater(entityPlayer, 2000);

				// drinking sound..
				world.playSoundAtEntity(entityPlayer,"random.drink", 0.2F, world.rand.nextFloat() * 0.1F + 0.9F);
			}
		}
	}

	/**
	 * When a player picks up a vanilla item it will get replaced with
	 * the appropriate TFC item. This will solve problems like the boat
	 * crash dropping vanilla sticks and planks.
	 */
	@SubscribeEvent
	public void onItemPickup(EntityItemPickupEvent event)
	{
		if(event.item.getEntityItem().getItem() == Items.stick)
		{
			int count = event.item.getEntityItem().stackSize;
			event.item.delayBeforeCanPickup = 100;
			event.item.setDead();
			event.item.setInvisible(true);
			Random rand = event.entityPlayer.worldObj.rand;
			event.entityPlayer.worldObj.playSoundAtEntity(event.entityPlayer, "random.pop", 0.2F, ((rand.nextFloat() - rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
			ItemStack tfcSticks = new ItemStack(TFCItems.stick, count);
			event.entityPlayer.inventory.addItemStackToInventory(tfcSticks);
		}

		if(event.item.getEntityItem().getItem() == Item.getItemFromBlock(Blocks.planks))
		{
			int count = event.item.getEntityItem().stackSize;
			event.item.delayBeforeCanPickup = 100;
			event.item.setDead();
			event.item.setInvisible(true);
			Random rand = event.entityPlayer.worldObj.rand;
			event.entityPlayer.worldObj.playSoundAtEntity(event.entityPlayer, "random.pop", 0.2F, ((rand.nextFloat() - rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
			ItemStack tfcPlanks = new ItemStack(TFCBlocks.planks, count);
			event.entityPlayer.inventory.addItemStackToInventory(tfcPlanks);
		}

		if (event.item.getEntityItem().getItem() == Item.getItemFromBlock(Blocks.lit_pumpkin))
		{
			int count = event.item.getEntityItem().stackSize;
			event.item.delayBeforeCanPickup = 100;
			event.item.setDead();
			event.item.setInvisible(true);
			Random rand = event.entityPlayer.worldObj.rand;
			event.entityPlayer.worldObj.playSoundAtEntity(event.entityPlayer, "random.pop", 0.2F, ((rand.nextFloat() - rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
			ItemStack jackOLanternTFC = new ItemStack(TFCBlocks.litPumpkin, count);
			event.entityPlayer.inventory.addItemStackToInventory(jackOLanternTFC);
		}
	}
}
