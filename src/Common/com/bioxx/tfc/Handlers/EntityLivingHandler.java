package com.bioxx.tfc.Handlers;

import java.util.ArrayList;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;

import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Core.TFC_Achievements;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Core.Player.*;
import com.bioxx.tfc.Entities.EntityProjectileTFC;
import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.Food.ItemMeal;
import com.bioxx.tfc.Handlers.Network.AbstractPacket;
import com.bioxx.tfc.Handlers.Network.PlayerUpdatePacket;
import com.bioxx.tfc.Items.ItemArrow;
import com.bioxx.tfc.Items.ItemBloom;
import com.bioxx.tfc.Items.ItemOreSmall;
import com.bioxx.tfc.Items.ItemQuiver;
import com.bioxx.tfc.Items.Tools.ItemCustomBow;
import com.bioxx.tfc.Items.Tools.ItemJavelin;
import com.bioxx.tfc.api.Food;
import com.bioxx.tfc.api.TFCAttributes;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.TFCOptions;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Interfaces.IEquipable;
import com.bioxx.tfc.api.Interfaces.IEquipable.EquipType;
import com.bioxx.tfc.api.Interfaces.IFood;
import com.bioxx.tfc.api.Util.Helper;

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
				/*BodyTempStats tempStats = TFC_Core.getBodyTempStats(player);
				tempStats.onUpdate(player);
				TFC_Core.setBodyTempStats(player, tempStats);*/



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
					TerraFirmaCraft.PACKET_PIPELINE.sendTo(pkt, (EntityPlayerMP) player);
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
				if (foodstats.stomachLevel / foodstats.getMaxStomach(player) <= 0.25f)
				{
					player.addPotionEffect(new PotionEffect(Potion.digSlowdown.id, 20, 1));
					player.addPotionEffect(new PotionEffect(Potion.weakness.id, 20, 1));
				}

				//Scan the players inventory for any items that are too heavy to carry normally
				boolean isOverburdened = false;
				if(!player.capabilities.isCreativeMode)
				{
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
				}

				setOverburdened(player, isOverburdened);

				//Handle Spawn Protection
				NBTTagCompound nbt = player.getEntityData();
				long spawnProtectionTimer = nbt.hasKey("spawnProtectionTimer") ? nbt.getLong("spawnProtectionTimer") : TFC_Time.getTotalTicks() + TFC_Time.HOUR_LENGTH;
				if(spawnProtectionTimer < TFC_Time.getTotalTicks())
				{
					//Add protection time to the chunks
					for(int i = -2; i < 3; i++)
					{
						for(int k = -2; k < 3; k++)
						{
							int lastChunkX = ((int) Math.floor(player.posX)) >> 4;
							int lastChunkZ = ((int) Math.floor(player.posZ)) >> 4;
							TFC_Core.getCDM(player.worldObj).addProtection(lastChunkX + i, lastChunkZ + k, TFCOptions.protectionGain);
						}
					}

					spawnProtectionTimer += TFC_Time.HOUR_LENGTH;
					nbt.setLong("spawnProtectionTimer", spawnProtectionTimer);
				}
			}
			else
			{
				PlayerInfo pi = PlayerManagerTFC.getInstance().getClientPlayer();
				FoodStatsTFC foodstats = TFC_Core.getPlayerFoodStats(player);
				foodstats.clientUpdate();
				//onUpdate(player) still has a !worldObj.isRemote check, but this allows us to render drunkenness
				if (pi != null && pi.playerUUID.equals(player.getUniqueID()))
				{
					foodstats.onUpdate(player);
					if (player.inventory.getCurrentItem() != null)
					{
						if(player.inventory.getCurrentItem().getItem() instanceof ItemMeal)
						{
							pi.guishowFoodRestoreAmount = true;
							pi.guiFoodRestoreAmount = Food.getWeight(player.inventory.getCurrentItem());
						}
						else if(player.inventory.getCurrentItem().getItem() instanceof ItemFoodTFC)
						{
							pi.guishowFoodRestoreAmount = true;
							pi.guiFoodRestoreAmount = Food.getWeight(player.inventory.getCurrentItem());
						}
						else
							pi.guishowFoodRestoreAmount = false;
					}
					else
						pi.guishowFoodRestoreAmount = false;
				}

			}
		}
	}

	public void setThirsty(EntityPlayer player, boolean b)
	{
		IAttributeInstance iattributeinstance = player.getEntityAttribute(SharedMonsterAttributes.movementSpeed);

		if (iattributeinstance.getModifier(TFCAttributes.THIRSTY_UUID) != null)
		{
			iattributeinstance.removeModifier(TFCAttributes.THIRSTY);
		}

		if (b)
		{
			iattributeinstance.applyModifier(TFCAttributes.THIRSTY);
		}
	}

	public void setOverburdened(EntityPlayer player, boolean b)
	{
		IAttributeInstance iattributeinstance = player.getEntityAttribute(SharedMonsterAttributes.movementSpeed);

		if (iattributeinstance.getModifier(TFCAttributes.OVERBURDENED_UUID) != null)
		{
			iattributeinstance.removeModifier(TFCAttributes.OVERBURDENED);
		}

		if (b)
		{
			iattributeinstance.applyModifier(TFCAttributes.OVERBURDENED);
		}
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void handleFOV(FOVUpdateEvent event)
	{
		EntityPlayer player = event.entity;

		// Force FOV to 1.0f if the player is overburdened to prevent the screen from zooming in a lot.
		IAttributeInstance iattributeinstance = player.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
		if (iattributeinstance.getModifier(TFCAttributes.OVERBURDENED_UUID) != null)
		{
			event.newfov = 1.0f;
			return;
		}

		// Calculate FOV based on the variable draw speed of the bow depending on player armor.
		if (player.isUsingItem() && player.getItemInUse().getItem() instanceof ItemCustomBow)
		{
			float fov = 1.0F;
			int duration = player.getItemInUseDuration();
			float speed = ItemCustomBow.getUseSpeed(player);
			float force = duration / speed;

			if (force > 1.0F)
			{
				force = 1.0F;
			}
			else
			{
				force *= force;
			}

			fov *= 1.0F - force * 0.15F;
			event.newfov = fov;
		}
	}

	@SubscribeEvent
	public void handleItemPickup(EntityItemPickupEvent event)
	{
		EntityPlayer player = event.entityPlayer;
		ItemStack item = event.item.getEntityItem();
		if (player.inventory instanceof InventoryPlayerTFC)
		{
			ItemStack backItem = ((InventoryPlayerTFC) player.inventory).extraEquipInventory[0];

			// Back slot is empty, and the player is picking up an item that can be equipped in the back slot.
			if (backItem == null && item.getItem() instanceof IEquipable)
			{
				IEquipable equipment = (IEquipable) item.getItem();
				if (equipment.getEquipType(item) == EquipType.BACK && (equipment == TFCItems.quiver || equipment.getTooHeavyToCarry(item)))
				{
					player.inventory.setInventorySlotContents(36, item.copy());
					item.stackSize = 0;
					event.item.setEntityItemStack(item);
				}
			}
			// Back slot contains a quiver, handle picking up arrows and javelins.
			else if (backItem != null && backItem.getItem() instanceof ItemQuiver)
			{
				ItemQuiver quiver = (ItemQuiver) backItem.getItem();

				// Attempt to add arrows that are picked up to the quiver instead of standard inventory.
				if(item.getItem() instanceof ItemArrow)
				{
					ItemStack is = quiver.addItem(backItem, item);
					if(is != null)
						event.item.setEntityItemStack(is);
					else
					{
						is = item;
						is.stackSize = 0;
						event.item.setEntityItemStack(is);
						event.setResult(Result.DENY);
					}
				}
				else if(item.getItem() instanceof ItemJavelin)
				{
					// Check to see if the player has at least 1 javelin on their hotbar.
					boolean foundJav = false;
					for (int i = 0; i < 9; i++)
					{
						if (player.inventory.getStackInSlot(i) != null && player.inventory.getStackInSlot(i).getItem() instanceof ItemJavelin)
							foundJav = true;
					}

					// If there is already a javelin on the hotbar, attempt to put the picked up javelin into the quiver.
					if(foundJav)
					{
						ItemStack is = quiver.addItem(backItem, item);
						if(is == null)
						{
							is = item;
							is.stackSize = 0;
							event.item.setEntityItemStack(is);
							event.setResult(Result.DENY);
						}
					}
				}
			}
		}

		if (item.getItem() == TFCItems.looseRock)
			player.triggerAchievement(TFC_Achievements.achLooseRock);
		else if(item.getItem() instanceof ItemOreSmall)
			player.triggerAchievement(TFC_Achievements.achSmallOre);
		else if (item.getItem() instanceof ItemBloom)
			player.triggerAchievement(TFC_Achievements.achIronAge);
		else if(item.getItem().equals(TFCItems.gemDiamond))
			player.triggerAchievement(TFC_Achievements.achDiamond);
		else if(item.getItem().equals(TFCItems.onion) && TFCOptions.onionsAreGross)
			player.triggerAchievement(TFC_Achievements.achRutabaga);
		else if(item.getItem().equals(TFCItems.oreChunk) && (item.getItemDamage() == 11 || item.getItemDamage()== 46 || item.getItemDamage() == 60))
			player.triggerAchievement(TFC_Achievements.achLimonite);
	}

	@SubscribeEvent
	public void onEntityDeath(LivingDeathEvent event)
	{
		EntityLivingBase entity = event.entityLiving;

		if (entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) entity;
			SkillStats skills = TFC_Core.getSkillStats(player);
			PlayerInfo pi = PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(player);
			pi.tempSkills = skills;

			// Save the item in the back slot if keepInventory is set to true.
			if (entity.worldObj.getGameRules().getGameRuleBooleanValue("keepInventory") && player.inventory instanceof InventoryPlayerTFC)
			{
				pi.tempEquipment = ((InventoryPlayerTFC) player.inventory).extraEquipInventory.clone();
			}
		}

		if (event.entity.dimension == 1)
			event.entity.travelToDimension(0);
	}

	@SubscribeEvent
	public void onLivingDrop(LivingDropsEvent event)
	{
		boolean processed = false;
		if (!event.entity.worldObj.isRemote && event.recentlyHit && !(event.entity instanceof EntityPlayer) && !(event.entity instanceof EntityZombie))
		{
			if(event.source.getSourceOfDamage() instanceof EntityPlayer || event.source.isProjectile())
			{
				boolean foundFood = false;
				processed = true;
				ArrayList<EntityItem> drop = new ArrayList<EntityItem>();
				EntityPlayer p = null;
				if(event.source.getSourceOfDamage() instanceof EntityPlayer)
					p = (EntityPlayer)event.source.getSourceOfDamage();
				else if(event.source.getSourceOfDamage() instanceof EntityProjectileTFC)
				{
					EntityProjectileTFC proj = (EntityProjectileTFC)event.source.getSourceOfDamage();
					if(proj.shootingEntity instanceof EntityPlayer)
						p = (EntityPlayer)proj.shootingEntity;
				}
				for(EntityItem ei : event.drops)
				{
					ItemStack is = ei.getEntityItem();
					if (is.getItem() instanceof IFood)
					{
						if(p == null)
							continue;
						foundFood = true;

						int sweetMod = Food.getSweetMod(is);
						int sourMod = Food.getSourMod(is);
						int saltyMod = Food.getSaltyMod(is);
						int bitterMod = Food.getBitterMod(is);
						int umamiMod = Food.getSavoryMod(is);

						float oldWeight = Food.getWeight(is);
						Food.setWeight(is, 0);
						float newWeight = oldWeight * (TFC_Core.getSkillStats(p).getSkillMultiplier(Global.SKILL_BUTCHERING)+0.01f);
						while (newWeight >= Global.FOOD_MIN_DROP_WEIGHT)
						{
							float fw = Helper.roundNumber(Math.min(Global.FOOD_MAX_WEIGHT, newWeight), 10);
							if (fw < Global.FOOD_MAX_WEIGHT)
								newWeight = 0;
							newWeight -= fw;

							ItemStack result = ItemFoodTFC.createTag(new ItemStack(is.getItem(), 1), fw);

							if (sweetMod != 0)
								Food.setSweetMod(result, sweetMod);
							if (sourMod != 0)
								Food.setSourMod(result, sourMod);
							if (saltyMod != 0)
								Food.setSaltyMod(result, saltyMod);
							if (bitterMod != 0)
								Food.setBitterMod(result, bitterMod);
							if (umamiMod != 0)
								Food.setSavoryMod(result, umamiMod);

							drop.add(new EntityItem(event.entity.worldObj, event.entity.posX, event.entity.posY, event.entity.posZ, result));
						}
					}
					else
					{
						drop.add(ei);
					}
				}
				event.drops.clear();
				event.drops.addAll(drop);
				if(foundFood && p != null)
				{
					TFC_Core.getSkillStats(p).increaseSkill(Global.SKILL_BUTCHERING, 1);
				}
			}
		}

		if (!processed && !(event.entity instanceof EntityPlayer) && !(event.entity instanceof EntityZombie))
		{
			ArrayList<EntityItem> drop = new ArrayList<EntityItem>();
			for(EntityItem ei : event.drops)
			{
				if (!(ei.getEntityItem().getItem() instanceof IFood))
				{
					drop.add(ei);
				}
			}
			event.drops.clear();
			event.drops.addAll(drop);
		}
	}
}
