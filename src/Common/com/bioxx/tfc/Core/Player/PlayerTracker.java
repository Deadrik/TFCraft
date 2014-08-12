package com.bioxx.tfc.Core.Player;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.item.ItemTossEvent;

import com.bioxx.tfc.TFCItems;
import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Chunkdata.ChunkDataManager;
import com.bioxx.tfc.Core.TFC_Achievements;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.Food.ItemMeal;
import com.bioxx.tfc.Handlers.Network.AbstractPacket;
import com.bioxx.tfc.Handlers.Network.InitClientWorldPacket;
import com.bioxx.tfc.Handlers.Network.PlayerUpdatePacket;
import com.bioxx.tfc.Items.ItemArrow;
import com.bioxx.tfc.Items.ItemLooseRock;
import com.bioxx.tfc.Items.ItemOreSmall;
import com.bioxx.tfc.Items.ItemQuiver;
import com.bioxx.tfc.Items.Tools.ItemJavelin;
import com.bioxx.tfc.api.TFCOptions;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemPickupEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent.ClientConnectedToServerEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent.ServerDisconnectionFromClientEvent;

public class PlayerTracker
{
	@SubscribeEvent
	public void onPlayerLoggedIn(PlayerLoggedInEvent event)
	{
		//		System.out.println("-----------------------------PLAYER LOGGIN EVENT-------------------");
		//		System.out.println("------"+event.player.getDisplayName()+" : "+ event.player.getUniqueID().toString()+"--------");

		PlayerManagerTFC.getInstance().Players.add(new PlayerInfo(
				event.player.getDisplayName(),
				event.player.getUniqueID()));
		AbstractPacket pkt = new InitClientWorldPacket(event.player);
		TerraFirmaCraft.packetPipeline.sendTo(pkt, (EntityPlayerMP) event.player);

		//		System.out.println("-----------------------------Sending TestPacket");
		//		AbstractPacket pkt2 = new TestPacket("Sent to Player: "+event.player.getDisplayName());
		//		TerraFirmaCraft.packetPipeline.sendTo(pkt2, (EntityPlayerMP) event.player);
	}

	@SubscribeEvent
	public void onClientConnect(ClientConnectedToServerEvent event)
	{

		//		System.out.println("-----"+FMLClientHandler.instance().getClientPlayerEntity().getDisplayName()+" : "+
		//				FMLClientHandler.instance().getClientPlayerEntity().getUniqueID().toString()+"-------");
		//
		//		PlayerManagerTFC.getInstance().Players.add(new PlayerInfo(
		//				Minecraft.getMinecraft().thePlayer.getDisplayName(),
		//				Minecraft.getMinecraft().thePlayer.getUniqueID(),
		//				event.manager));
	}

	@SubscribeEvent
	public void onClientDisconnect(ServerDisconnectionFromClientEvent event)
	{
	}

	@SubscribeEvent
	public void onPlayerRespawn(PlayerRespawnEvent event)
	{
		if(!event.player.worldObj.isRemote)
		{
			float foodLevel = (event.player.worldObj.rand.nextFloat() * 12) + 12;
			FoodStatsTFC foodstats = TFC_Core.getPlayerFoodStats(event.player);
			foodstats.setFoodLevel(foodLevel);
			TFC_Core.setPlayerFoodStats(event.player, foodstats);
			event.player.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(1000);
			event.player.setHealth(1000f * (0.25f + (event.player.worldObj.rand.nextFloat() * 0.25f)));
		}

		PlayerInfo pi = PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(event.player);
		if( pi.tempSkills != null)
			TFC_Core.setSkillStats(event.player, pi.tempSkills);

		//Send a request to the server for the skills data.
		AbstractPacket pkt = new PlayerUpdatePacket(event.player, 4);
		TerraFirmaCraft.packetPipeline.sendToServer(pkt);
	}

	@SubscribeEvent
	// this is players only
	public void onPlayerUpdate(PlayerTickEvent event)
	{
		if (event.phase == Phase.END)
			return;
		EntityPlayer player = (EntityPlayer)event.player;
		//Set Max Health
		float newMaxHealth = FoodStatsTFC.getMaxHealth(player);
		float oldMaxHealth = (float)player.getEntityAttribute(SharedMonsterAttributes.maxHealth).getAttributeValue();
		if(oldMaxHealth != newMaxHealth)
		{
			player.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(newMaxHealth);
			/*if(diff > 0)
				player.heal(diff);*/
		}

		if(!player.worldObj.isRemote)
		{
			//Tick Decay
			TFC_Core.handleItemTicking(player.inventory.mainInventory, player.worldObj, (int)player.posX, (int)player.posY, (int)player.posZ);
			//Handle Food
			BodyTempStats tempStats = TFC_Core.getBodyTempStats(player);
			tempStats.onUpdate(player);
			TFC_Core.setBodyTempStats(player, tempStats);

			//Nullify the Old Food
			player.getFoodStats().addStats(20 - player.getFoodStats().getFoodLevel(), 0.0F);
			//Handle Food
			FoodStatsTFC foodstats = TFC_Core.getPlayerFoodStats(player);
			foodstats.onUpdate(player);
			TFC_Core.setPlayerFoodStats(player, foodstats);
			//Send update packet from Server to Client
			TerraFirmaCraft.packetPipeline.sendTo(new PlayerUpdatePacket(player, 0), (EntityPlayerMP) player);

			if(foodstats.waterLevel / foodstats.getMaxWater(player) < 0.25f)
			{
				player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 20, 1));
			}
			else if(foodstats.waterLevel / foodstats.getMaxWater(player) < 0.5f)
			{
				if(player.isSprinting())
					player.setSprinting(false);
			}

			//Handle Spawn Protection
			NBTTagCompound nbt = player.getEntityData();
			long spawnProtectionTimer = nbt.hasKey("spawnProtectionTimer") ? nbt.getLong("spawnProtectionTimer") : TFC_Time.getTotalTicks() + TFC_Time.hourLength;
			if(spawnProtectionTimer < TFC_Time.getTotalTicks())
			{
				//Add protection time to the chunks
				for(int i = -2; i < 3; i++)
				{
					for(int k = -2; k < 3; k++)
					{
						int lastChunkX = (((int)Math.floor(player.posX)) >> 4);
						int lastChunkZ = (((int)Math.floor(player.posZ)) >> 4);
						ChunkDataManager.addProtection(lastChunkX + i, lastChunkZ + k, TFCOptions.protectionGain);
					}
				}

				spawnProtectionTimer += TFC_Time.hourLength;
			}
		}
		else
		{
			PlayerInfo pi = PlayerManagerTFC.getInstance().getClientPlayer();

			if(pi != null && player.inventory.getCurrentItem() != null)
			{
				if(player.inventory.getCurrentItem().getItem() instanceof ItemMeal)
				{
					pi.guishowFoodRestoreAmount = true;
					pi.guiFoodRestoreAmount = ((ItemMeal)player.inventory.getCurrentItem().getItem()).getFoodWeight(player.inventory.getCurrentItem());
				}
				else if(player.inventory.getCurrentItem().getItem() instanceof ItemFoodTFC)
				{
					pi.guishowFoodRestoreAmount = true;
					pi.guiFoodRestoreAmount = ((ItemFoodTFC)player.inventory.getCurrentItem().getItem()).getFoodWeight(player.inventory.getCurrentItem());
				}
				else
					pi.guishowFoodRestoreAmount = false;
			}
			else if(pi != null)
				pi.guishowFoodRestoreAmount = false;
		}
	}

	@SubscribeEvent
	// this is players only
	public void handleItemPickup(ItemPickupEvent event)
	{
		EntityPlayer player = event.player;
		ItemStack quiver = null;
		ItemStack item = event.pickedUp.getEntityItem();
		boolean foundJav = false;
		quiver = player.inventory.armorItemInSlot(0);
		for(int i = 0; i < 9; i++)
		{
			if(player.inventory.getStackInSlot(i) != null && player.inventory.getStackInSlot(i).getItem() instanceof ItemJavelin)
				foundJav = true;
		}

		if(quiver != null)
		{
			if(item.getItem() instanceof ItemArrow)
			{
				ItemStack is = ((ItemQuiver)quiver.getItem()).addItem(quiver, item);
				if(is != null)
					event.pickedUp.setEntityItemStack(is);
				else
				{
					is = event.pickedUp.getEntityItem();
					is.stackSize = 0;
					event.pickedUp.setEntityItemStack(is);
					event.setResult(Result.DENY);
				}
			}
			else if(item.getItem() instanceof ItemJavelin)
			{
				if(foundJav)
				{
					ItemStack is = ((ItemQuiver)quiver.getItem()).addItem(quiver, item);
					if(is == null)
					{
						is = event.pickedUp.getEntityItem();
						is.stackSize = 0;
							event.pickedUp.setEntityItemStack(is);
						event.setResult(Result.DENY);
					}
				}
			}
		}

		if(item.getItem() instanceof ItemLooseRock)
			player.triggerAchievement(TFC_Achievements.achLooseRock);
		else if(item.getItem() instanceof ItemOreSmall)
			player.triggerAchievement(TFC_Achievements.achSmallOre);
		else if(item.getItem().equals(TFCItems.GemDiamond))
			player.triggerAchievement(TFC_Achievements.achDiamond);
		else if(item.getItem().equals(TFCItems.Onion) && TFCOptions.iDontLikeOnions)
			player.triggerAchievement(TFC_Achievements.achRutabaga);
		else if(item.getItem().equals(TFCItems.OreChunk) && (item.getItemDamage() == 11 || item.getItemDamage()== 46 || item.getItemDamage() == 60))
			player.triggerAchievement(TFC_Achievements.achLimonite);
	}

	// Register the Player Toss Event Handler, workaround for a crash fix
	@SubscribeEvent
	public void onPlayerTossEvent(ItemTossEvent event)
	{
		if(event.entityItem == null)
			event.setCanceled(true);
	}
}

