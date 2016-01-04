package com.bioxx.tfc.Core.Player;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayerMP;

import net.minecraftforge.event.entity.item.ItemTossEvent;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemPickupEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent.ClientConnectedToServerEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent.ServerDisconnectionFromClientEvent;

import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.Config.TFC_ConfigFiles;
import com.bioxx.tfc.Handlers.Network.AbstractPacket;
import com.bioxx.tfc.Handlers.Network.ConfigSyncPacket;
import com.bioxx.tfc.Handlers.Network.InitClientWorldPacket;
import com.bioxx.tfc.Handlers.Network.PlayerUpdatePacket;

public class PlayerTracker
{
	@SubscribeEvent
	public void onPlayerLoggedIn(PlayerLoggedInEvent event)
	{
		//		TerraFirmaCraft.log.info("-----------------------------PLAYER LOGGIN EVENT-------------------");
		//		TerraFirmaCraft.log.info("------"+event.player.getDisplayName()+" : "+ event.player.getUniqueID().toString()+"--------");

		PlayerManagerTFC.getInstance().players.add(new PlayerInfo(
				event.player.getCommandSenderName(),
				event.player.getUniqueID()));
		TFC_ConfigFiles.reloadAll();
		AbstractPacket pkt = new InitClientWorldPacket(event.player);
		TerraFirmaCraft.PACKET_PIPELINE.sendTo(pkt, (EntityPlayerMP) event.player);
		TerraFirmaCraft.PACKET_PIPELINE.sendTo(new ConfigSyncPacket(), (EntityPlayerMP) event.player);

		//		TerraFirmaCraft.log.info("-----------------------------Sending TestPacket");
		//AbstractPacket pkt2 = new TestPacket("Sent to Player: "+event.player.getDisplayName());
		//TerraFirmaCraft.packetPipeline.sendTo(pkt2, (EntityPlayerMP) event.player);
	}

	@SubscribeEvent
	public void onClientConnect(ClientConnectedToServerEvent event)
	{

		//		TerraFirmaCraft.log.info("-----"+FMLClientHandler.instance().getClientPlayerEntity().getDisplayName()+" : "+
		//				FMLClientHandler.instance().getClientPlayerEntity().getUniqueID().toString()+"-------");
		//
		TerraFirmaCraft.proxy.onClientLogin();
	}

	@SubscribeEvent
	public void onClientDisconnect(ServerDisconnectionFromClientEvent event)
	{
	}

	@SubscribeEvent
	public void onPlayerRespawn(PlayerRespawnEvent event)
	{
		float foodLevel = event.player.worldObj.rand.nextFloat() * 12 + 12;
		FoodStatsTFC foodstats = TFC_Core.getPlayerFoodStats(event.player);
		foodstats.setFoodLevel(foodLevel);
		TFC_Core.setPlayerFoodStats(event.player, foodstats);
		event.player.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(1000);
		event.player.setHealth(1000f * (0.25f + (event.player.worldObj.rand.nextFloat() * 0.25f)));

		PlayerInfo pi = PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(event.player);
		if( pi.tempSkills != null)
			TFC_Core.setSkillStats(event.player, pi.tempSkills);

		// Load the item in the back slot if keepInventory was set to true.
		if (pi.tempEquipment != null && event.player.worldObj.getGameRules().getGameRuleBooleanValue("keepInventory"))
		{
			InventoryPlayerTFC invPlayer = (InventoryPlayerTFC) event.player.inventory;
			invPlayer.extraEquipInventory = pi.tempEquipment.clone();
			pi.tempEquipment = null;
		}

		//Send a request to the server for the skills data.
		AbstractPacket pkt = new PlayerUpdatePacket(event.player, 3);
		TerraFirmaCraft.PACKET_PIPELINE.sendTo(pkt, (EntityPlayerMP) event.player);
	}

	@SubscribeEvent
	public void notifyPickup(ItemPickupEvent event)
	{
		/*ItemStack quiver = null;
		ItemStack ammo = item.getEntityItem();
		for(int i = 0; i < 9; i++) 
		{
			if(player.inventory.getStackInSlot(i) != null && player.inventory.getStackInSlot(i).getItem() instanceof ItemQuiver)
			{
				quiver = player.inventory.getStackInSlot(i);
				break;
			}
		}

		if(quiver != null && (ammo.getItem() instanceof ItemArrow || ammo.getItem() instanceof ItemJavelin))
		{
			ItemStack is = ((ItemQuiver)quiver.getItem()).addItem(quiver, ammo);
			item.setEntityItemStack(is);
		}*/
	}

	// Register the Player Toss Event Handler, workaround for a crash fix
	@SubscribeEvent
	public void onPlayerTossEvent(ItemTossEvent event)
	{
		if(event.entityItem == null)
			event.setCanceled(true);
	}
}

