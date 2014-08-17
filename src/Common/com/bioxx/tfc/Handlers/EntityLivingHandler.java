package com.bioxx.tfc.Handlers;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;

import com.bioxx.tfc.TFCItems;
import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Chunkdata.ChunkDataManager;
import com.bioxx.tfc.Core.TFC_Achievements;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Core.Player.BodyTempStats;
import com.bioxx.tfc.Core.Player.FoodStatsTFC;
import com.bioxx.tfc.Core.Player.PlayerInfo;
import com.bioxx.tfc.Core.Player.PlayerManagerTFC;
import com.bioxx.tfc.Core.Player.SkillStats;
import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.Food.ItemMeal;
import com.bioxx.tfc.Handlers.Network.AbstractPacket;
import com.bioxx.tfc.Handlers.Network.PlayerUpdatePacket;
import com.bioxx.tfc.Items.ItemArrow;
import com.bioxx.tfc.Items.ItemLooseRock;
import com.bioxx.tfc.Items.ItemOreSmall;
import com.bioxx.tfc.Items.ItemQuiver;
import com.bioxx.tfc.Items.Tools.ItemJavelin;
import com.bioxx.tfc.api.TFCOptions;

import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class EntityLivingHandler
{
	@SubscribeEvent
	public void onEntityLivingUpdate(LivingUpdateEvent event)
	{
		if (event.entityLiving instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)event.entityLiving;
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
				AbstractPacket pkt = new PlayerUpdatePacket(player, 0);
				TerraFirmaCraft.packetPipeline.sendTo(pkt, (EntityPlayerMP) player);

				if(foodstats.waterLevel / foodstats.getMaxWater(player) <= 0.25f)
					player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 20, 1));
				else if(foodstats.waterLevel / foodstats.getMaxWater(player) <= 0.5f)
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
					nbt.setLong("spawnProtectionTimer", spawnProtectionTimer);
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
	}

	@SubscribeEvent
	public void handleItemPickup(EntityItemPickupEvent event)
	{
		EntityPlayer player = event.entityPlayer;
		ItemStack quiver = null;
		ItemStack item = event.item.getEntityItem();
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
					event.item.setEntityItemStack(is);
				else
				{
					is = event.item.getEntityItem();
					is.stackSize = 0;
					event.item.setEntityItemStack(is);
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
						is = event.item.getEntityItem();
						is.stackSize = 0;
						event.item.setEntityItemStack(is);
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

	@SubscribeEvent
	public void onEntityDeath(LivingDeathEvent event)
	{
		if(event.entityLiving instanceof EntityPlayer)
		{
			SkillStats skills = TFC_Core.getSkillStats((EntityPlayer) event.entityLiving);
			PlayerInfo pi = PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer((EntityPlayer) event.entityLiving);
			pi.tempSkills = skills;
		}
	}
}
