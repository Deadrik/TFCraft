package com.bioxx.tfc.Handlers;

import java.util.HashMap;
import java.util.UUID;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Core.Player.FoodStatsTFC;

import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.eventhandler.Event.Result;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

public class PlayerInteractHandler
{

	private HashMap<UUID, Long> lastDrink = new HashMap<UUID, Long>();
	
	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		if (event.entityPlayer.worldObj.isRemote)
			return;

		ItemStack itemInHand = event.entityPlayer.getCurrentEquippedItem();

		boolean validAction = event.action == Action.RIGHT_CLICK_BLOCK;// || event.action == Action.RIGHT_CLICK_AIR;

		if(validAction && event.getResult() != Result.DENY && itemInHand == null)
		{
			handleDrinkingWater( event.entityPlayer );
		}
	}

	/**
	* rip from Item Method of the same name, sadly couldn't use it in place.
	*
	* @param world
	* @param player
	* @param includeFluids
	* @return
	*/
	private MovingObjectPosition getMovingObjectPositionFromPlayer(World world, EntityPlayer player, boolean includeFluids)
	{
		float f = 1.0F;
		float f1 = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * f;
		float f2 = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * f;
		double d0 = player.prevPosX + (player.posX - player.prevPosX) * (double)f;
		double d1 = player.prevPosY + (player.posY - player.prevPosY) * (double)f + (double)(world.isRemote ? player.getEyeHeight() - player.getDefaultEyeHeight() : player.getEyeHeight()); // isRemote check to revert changes to ray trace position due to adding the eye height clientside and player yOffset differences
		double d2 = player.prevPosZ + (player.posZ - player.prevPosZ) * (double)f;
		Vec3 vec3 = Vec3.createVectorHelper(d0, d1, d2);
		float f3 = MathHelper.cos(-f2 * 0.017453292F - (float)Math.PI);
		float f4 = MathHelper.sin(-f2 * 0.017453292F - (float)Math.PI);
		float f5 = -MathHelper.cos(-f1 * 0.017453292F);
		float f6 = MathHelper.sin(-f1 * 0.017453292F);
		float f7 = f4 * f5;
		float f8 = f3 * f5;
		double d3 = 5.0D;
		
		if (player instanceof EntityPlayerMP)
		{
			d3 = ((EntityPlayerMP)player).theItemInWorldManager.getBlockReachDistance();
		}
		
		Vec3 vec31 = vec3.addVector((double)f7 * d3, (double)f6 * d3, (double)f8 * d3);
		return world.func_147447_a(vec3, vec31, includeFluids, !includeFluids, false);
	}

	private void handleDrinkingWater(EntityPlayer entityPlayer)
	{
		Long last_check = lastDrink.get(entityPlayer.getUniqueID());
		if(last_check != null && last_check + 20 > TFC_Time.getTotalTicks())
			return;
		lastDrink.put(entityPlayer.getUniqueID(), TFC_Time.getTotalTicks());
		World world = entityPlayer.worldObj;
		MovingObjectPosition mop = getMovingObjectPositionFromPlayer(world, entityPlayer, true);
		FoodStatsTFC fs = TFC_Core.getPlayerFoodStats(entityPlayer);
		Boolean canDrink = fs.getMaxWater(entityPlayer) - 500 > fs.waterLevel;

		// you can't drink if you're under water thats silly. // Is it though?
		if ( entityPlayer.isInsideOfMaterial(Material.water))
			return;

		// if we found a block, and we can drink proceed
		if(mop != null && canDrink && mop.typeOfHit == MovingObjectType.BLOCK)
		{
			if( TFC_Core.isFreshWater(world.getBlock(mop.blockX, mop.blockY, mop.blockZ)) )
			{
				// recover a portion of players water, this is intentionally slow,
				fs.restoreWater(entityPlayer, 500 );

				// drinking sound..
				world.playSoundAtEntity(entityPlayer,"random.drink", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
			}
		}
	}

}
