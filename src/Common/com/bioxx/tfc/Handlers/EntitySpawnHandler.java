package com.bioxx.tfc.Handlers;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;

import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;

import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

import com.bioxx.tfc.Chunkdata.ChunkData;
import com.bioxx.tfc.Containers.ContainerPlayerTFC;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.Player.InventoryPlayerTFC;
import com.bioxx.tfc.api.TFCBlocks;

public class EntitySpawnHandler
{
	@SubscribeEvent
	public void onCheckSpawn(LivingSpawnEvent.CheckSpawn event)
	{
		EntityLivingBase entity = event.entityLiving;

		int chunkX = (int)Math.floor(entity.posX) >> 4;
		int chunkZ = (int)Math.floor(entity.posZ) >> 4;

		if(event.world.getBlock((int)Math.floor(entity.posX), (int)Math.floor(entity.posY), (int)Math.floor(entity.posZ)) == TFCBlocks.thatch)
		{
			event.setResult(Result.DENY);
		}

		if (TFC_Core.getCDM(event.world) != null)
		{
			ChunkData data = TFC_Core.getCDM(event.world).getData(chunkX, chunkZ);
			if ( !(data == null || data.getSpawnProtectionWithUpdate() <= 0))
				event.setResult(Result.DENY);
		}
	}

	@SubscribeEvent
	public void onJoinWorld(EntityJoinWorldEvent event)
	{
		if (event.entity instanceof EntityPlayer && !event.entity.getEntityData().hasKey("hasSpawned"))
		{
			if(!(((EntityPlayer)event.entity).inventory instanceof InventoryPlayerTFC))
				((EntityPlayer)event.entity).inventory = TFC_Core.getNewInventory((EntityPlayer)event.entity);

			((EntityPlayer)event.entity).getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(1000);
			((EntityPlayer)event.entity).setHealth(1000);
			event.entity.getEntityData().setBoolean("hasSpawned", true);
		}

		if (event.entity instanceof EntityPlayer)
		{
			if(!(((EntityPlayer)event.entity).inventory instanceof InventoryPlayerTFC))
				((EntityPlayer)event.entity).inventory = TFC_Core.getNewInventory((EntityPlayer)event.entity);

			((EntityPlayer)event.entity).inventoryContainer = new ContainerPlayerTFC(((EntityPlayer)event.entity).inventory, !event.world.isRemote, (EntityPlayer)event.entity);
			((EntityPlayer)event.entity).openContainer = ((EntityPlayer)event.entity).inventoryContainer;
		}
	}
}
