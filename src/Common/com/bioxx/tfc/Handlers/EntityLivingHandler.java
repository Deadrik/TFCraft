package com.bioxx.tfc.Handlers;

import java.util.ArrayList;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;

import com.bioxx.tfc.TFCItems;
import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Core.TFC_Achievements;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Core.Player.BodyTempStats;
import com.bioxx.tfc.Core.Player.FoodStatsTFC;
import com.bioxx.tfc.Core.Player.InventoryPlayerTFC;
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
import com.bioxx.tfc.api.Food;
import com.bioxx.tfc.api.TFCAttributes;
import com.bioxx.tfc.api.TFCOptions;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Interfaces.IEquipable;
import com.bioxx.tfc.api.Interfaces.IFood;
import com.bioxx.tfc.api.Util.Helper;

import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

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
				if(foodstats.shouldSendUpdate())
				{
					AbstractPacket pkt = new PlayerUpdatePacket(player, 0);
					TerraFirmaCraft.packetPipeline.sendTo(pkt, (EntityPlayerMP) player);
				}
				if(foodstats.waterLevel / foodstats.getMaxWater(player) <= 0.25f)
				{
					setThirsty(player, true);
				}
				else if(foodstats.waterLevel / foodstats.getMaxWater(player) <= 0.5f)
				{
					if(player.isSprinting())
						player.setSprinting(false);
				}
				else
				{
					setThirsty(player, false);
				}

				//Scan the players inventory for any items that are too heavy to carry normally
				boolean isOverburdened = false;
				for (int i = 0; i < player.inventory.mainInventory.length;i++)
				{
					ItemStack is = player.inventory.getStackInSlot(i);
					if(is != null && is.getItem() instanceof IEquipable)
					{
						isOverburdened = ((IEquipable)is.getItem()).getTooHeavyToCarry(is);
						if(isOverburdened)
							break;
					}
				}

				setOverburdened(player, isOverburdened);

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
							TFC_Core.getCDM(player.worldObj).addProtection(lastChunkX + i, lastChunkZ + k, TFCOptions.protectionGain);
						}
					}

					spawnProtectionTimer += TFC_Time.hourLength;
					nbt.setLong("spawnProtectionTimer", spawnProtectionTimer);
				}
			}
			else
			{
				PlayerInfo pi = PlayerManagerTFC.getInstance().getClientPlayer();
				//onUpdate(player) still has a !worldObj.isRemote check, but this allows us to render drunkenness
				if(pi != null && pi.PlayerUUID == player.getUniqueID())
				{
					FoodStatsTFC foodstats = TFC_Core.getPlayerFoodStats(player);
					foodstats.onUpdate(player);
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
	}

	public void setThirsty(EntityPlayer player, boolean b)
	{
		IAttributeInstance iattributeinstance = player.getEntityAttribute(SharedMonsterAttributes.movementSpeed);

		if (iattributeinstance.getModifier(TFCAttributes.thirstyUUID) != null)
		{
			iattributeinstance.removeModifier(TFCAttributes.thirsty);
		}

		if (b)
		{
			iattributeinstance.applyModifier(TFCAttributes.thirsty);
		}
	}

	public void setOverburdened(EntityPlayer player, boolean b)
	{
		IAttributeInstance iattributeinstance = player.getEntityAttribute(SharedMonsterAttributes.movementSpeed);

		if (iattributeinstance.getModifier(TFCAttributes.overburdenedUUID) != null)
		{
			iattributeinstance.removeModifier(TFCAttributes.overburdened);
		}

		if (b)
		{
			iattributeinstance.applyModifier(TFCAttributes.overburdened);
		}
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void handleFOV(FOVUpdateEvent event)
	{
		event.newfov = 1.0f;
	}

	@SubscribeEvent
	public void handleItemPickup(EntityItemPickupEvent event)
	{
		EntityPlayer player = event.entityPlayer;
		ItemStack quiver = null;
		ItemStack item = event.item.getEntityItem();
		boolean foundJav = false;
		if(player.inventory instanceof InventoryPlayerTFC){

			quiver = ((InventoryPlayerTFC)player.inventory).extraEquipInventory[0];
			if(quiver != null && !(quiver.getItem() instanceof ItemQuiver)){
				quiver = null;
			}
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

	@SubscribeEvent
	public void onLivingDrop(LivingDropsEvent event)
	{
		boolean processed = false;
		if(!event.entity.worldObj.isRemote && event.recentlyHit)
		{
			if(event.source.getSourceOfDamage() instanceof EntityPlayer)
			{
				EntityPlayer p = (EntityPlayer)event.source.getSourceOfDamage();
				boolean foundFood = false;
				processed = true;
				ArrayList<EntityItem> drop = new ArrayList<EntityItem>();
				for(EntityItem ei : event.drops)
				{
					if(ei.getEntityItem().getItem() instanceof IFood)
					{
						foundFood = true;
						float oldWeight = Food.getWeight(ei.getEntityItem());
						Food.setWeight(ei.getEntityItem(), 0);
						float newWeight = oldWeight * (TFC_Core.getSkillStats(p).getSkillMultiplier(Global.SKILL_BUTCHERING)+0.01f);
						while (newWeight > 0)
						{
							float fw = Helper.roundNumber(Math.min(Global.FOOD_MAX_WEIGHT, newWeight), 10);
							if (fw < Global.FOOD_MAX_WEIGHT)
								newWeight = 0;
							newWeight -= fw;
							ItemStack is = ItemFoodTFC.createTag(new ItemStack(ei.getEntityItem().getItem(), 1), fw);
							drop.add(new EntityItem(event.entity.worldObj, event.entity.posX, event.entity.posY, event.entity.posZ, is));
						}
					}
					else
					{
						drop.add(ei);
					}
				}
				event.drops.clear();
				event.drops.addAll(drop);
				if(foundFood)
				{
					TFC_Core.getSkillStats(p).increaseSkill(Global.SKILL_BUTCHERING, 1);
				}
			}
		}

		if(!processed)
		{
			ArrayList<EntityItem> drop = new ArrayList<EntityItem>();
			for(EntityItem ei : event.drops)
			{
				if(ei.getEntityItem().getItem() instanceof IFood)
				{

				}
				else
				{
					drop.add(ei);
				}
			}
			event.drops.clear();
			event.drops.addAll(drop);
		}
	}
}
