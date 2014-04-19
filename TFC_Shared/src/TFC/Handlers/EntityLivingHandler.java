package TFC.Handlers;


import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.Achievement;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import TFC.TFCItems;
import TFC.TerraFirmaCraft;
import TFC.API.TFCOptions;
import TFC.API.Util.Helper;
import TFC.Chunkdata.ChunkDataManager;
import TFC.Core.TFC_Achievements;
import TFC.Core.TFC_Core;
import TFC.Core.TFC_Time;
import TFC.Core.Player.BodyTempStats;
import TFC.Core.Player.FoodStatsTFC;
import TFC.Core.Player.PlayerInfo;
import TFC.Core.Player.PlayerManagerTFC;
import TFC.Core.Player.SkillStats;
import TFC.Food.ItemFoodTFC;
import TFC.Food.ItemMeal;
import TFC.Items.ItemArrow;
import TFC.Items.ItemLooseRock;
import TFC.Items.ItemOreSmall;
import TFC.Items.ItemQuiver;
import TFC.Items.Tools.ItemJavelin;

public class EntityLivingHandler
{
	@ForgeSubscribe
	public void onEntityLivingUpdate(LivingUpdateEvent event) 
	{
		EntityLivingBase entity = event.entityLiving;
		NBTTagCompound nbt = entity.getEntityData();
		if (entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)entity;
			//Set Max Health
			float newMaxHealth = FoodStatsTFC.getMaxHealth(player);
			float oldMaxHealth = (float)player.getEntityAttribute(SharedMonsterAttributes.maxHealth).getAttributeValue();
			if(oldMaxHealth != newMaxHealth)
			{
				player.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(newMaxHealth);
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
				TerraFirmaCraft.proxy.sendCustomPacketToPlayer((EntityPlayerMP)player, FoodStatsTFC.getStatusPacket(foodstats));

				if(foodstats.waterLevel / foodstats.getMaxWater(player) <= 0.25f)
					player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id,20,1));
				else if(foodstats.waterLevel / foodstats.getMaxWater(player) <= 0.5f)
					if(player.isSprinting())
						player.setSprinting(false);

				//Handle Spawn Protection
				long spawnProtectionTimer = nbt.hasKey("spawnProtectionTimer") ? 
						nbt.getLong("spawnProtectionTimer") : TFC_Time.getTotalTicks() + TFC_Time.hourLength;

						if(spawnProtectionTimer < TFC_Time.getTotalTicks())
						{
							//Add protection time to the chunks
							for(int i = -2; i < 3; i++)
								for(int k = -2; k < 3; k++)
								{
									int lastChunkX = (int)player.posX >> 4;
								int lastChunkZ = (int)player.posZ >> 4;
						ChunkDataManager.addProtection(lastChunkX + i, lastChunkZ + k, TFCOptions.protectionGain);
								}

							spawnProtectionTimer += TFC_Time.hourLength;
						}
			}
			else
			{
				PlayerInfo playerclient = PlayerManagerTFC.getInstance().getClientPlayer();
				//Minecraft.getMinecraft().playerController.getBlockReachDistance()
				if(player.inventory.getCurrentItem() != null)
				{
					if(player.inventory.getCurrentItem().getItem() instanceof ItemMeal)
					{
						playerclient.guishowFoodRestoreAmount = true;
						playerclient.guiFoodRestoreAmount = ((ItemMeal)player.inventory.getCurrentItem().getItem()).getFoodWeight(player.inventory.getCurrentItem());
					}
					else if(player.inventory.getCurrentItem().getItem() instanceof ItemFoodTFC)
					{
						playerclient.guishowFoodRestoreAmount = true;
						playerclient.guiFoodRestoreAmount = ((ItemFoodTFC)player.inventory.getCurrentItem().getItem()).getFoodWeight(player.inventory.getCurrentItem());
					} else
						playerclient.guishowFoodRestoreAmount = false;
				} else
					playerclient.guishowFoodRestoreAmount = false;
			}

		}
	}

	@ForgeSubscribe
	public void handleItemPickup(EntityItemPickupEvent event)
	{
		EntityPlayer player = event.entityPlayer;
		ItemStack quiver = null;
		ItemStack item = event.item.getEntityItem();
		boolean foundJav = false;
		quiver = player.inventory.armorItemInSlot(0);
		for(int i = 0; i < 9; i++)
			if(player.inventory.getStackInSlot(i) != null && player.inventory.getStackInSlot(i).getItem() instanceof ItemJavelin)
				foundJav = true;

		if(quiver != null)
		{
			if(item.getItem() instanceof ItemArrow){
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
		if(item.getItem() instanceof ItemLooseRock){
			player.triggerAchievement(TFC_Achievements.achLooseRock);
		}
		else if(item.getItem()  instanceof ItemOreSmall){
			player.triggerAchievement(TFC_Achievements.achSmallOre);
		}
		else if(item.getItem().equals(TFCItems.GemDiamond)){
			player.triggerAchievement(TFC_Achievements.achDiamond);
		}
		else if(item.getItem().equals(TFCItems.Onion) && TFCOptions.iDontLikeOnions){
			player.triggerAchievement(TFC_Achievements.achRutabaga);
		}
		else if(item.getItem().equals(TFCItems.OreChunk) && (item.getItemDamage() == 11 || item.getItemDamage()== 46 || item.getItemDamage() == 60)){
			player.triggerAchievement(TFC_Achievements.achLimonite);
		}
	}

	@ForgeSubscribe
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
