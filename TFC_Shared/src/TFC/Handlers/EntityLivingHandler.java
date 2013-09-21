package TFC.Handlers;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import TFC.TFCBlocks;
import TFC.TerraFirmaCraft;
import TFC.API.TFCOptions;
import TFC.Chunkdata.ChunkDataManager;
import TFC.Core.TFC_Climate;
import TFC.Core.TFC_Core;
import TFC.Core.TFC_Time;
import TFC.Core.Player.PlayerInfo;
import TFC.Core.Player.PlayerManagerTFC;
import TFC.Food.FoodStatsTFC;
import TFC.Food.ItemMeal;
import TFC.Food.ItemTerraFood;
import TFC.TileEntities.TileEntityFireEntity;

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
			player.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(getMaxHealth(player));
			if(!player.worldObj.isRemote)
			{
				//Nullify the Old Food
				player.getFoodStats().addStats(20 - player.getFoodStats().getFoodLevel(), 0.0F);
				//Handle Food
				FoodStatsTFC foodstats = TFC_Core.getPlayerFoodStats(player);
				foodstats.onUpdate(player);
				TFC_Core.setPlayerFoodStats(player, foodstats);
				TerraFirmaCraft.proxy.sendCustomPacketToPlayer((EntityPlayerMP)player, FoodStatsTFC.getStatusPacket(foodstats));

				if(foodstats.waterLevel / foodstats.getMaxWater(player) <= 0.25f)
				{
					player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id,20,1));
				}
				else if(foodstats.waterLevel / foodstats.getMaxWater(player) <= 0.5f)
				{
					if(player.isSprinting())
					{
						player.setSprinting(false);
					}
				}

				//Handle Spawn Protection
				long spawnProtectionTimer = nbt.hasKey("spawnProtectionTimer") ? 
						nbt.getLong("spawnProtectionTimer") : TFC_Time.getTotalTicks() + TFC_Time.hourLength;

						if(spawnProtectionTimer < TFC_Time.getTotalTicks())
						{
							//Add protection time to the chunks
							for(int i = -2; i < 3; i++)
							{
								for(int k = -2; k < 3; k++)
								{
									int lastChunkX = (int)player.posX >> 4;
								int lastChunkZ = (int)player.posZ >> 4;
						ChunkDataManager.addProtection(lastChunkX + i, lastChunkZ + k, TFCOptions.protectionGain);
								}
							}

							spawnProtectionTimer += TFC_Time.hourLength;
						}

						//Handle Temperature
						handleBodyTemp(player);	
			}
			else
			{
				PlayerInfo playerclient = PlayerManagerTFC.getInstance().getClientPlayer();
				if(player.inventory.getCurrentItem() != null)
				{
					if(player.inventory.getCurrentItem().getItem() instanceof ItemMeal)
					{
						playerclient.guishowFoodRestoreAmount = true;
						playerclient.guiFoodRestoreAmount = ItemMeal.getMealFilling(player.inventory.getCurrentItem());
					}
					else if(player.inventory.getCurrentItem().getItem() instanceof ItemTerraFood)
					{
						playerclient.guishowFoodRestoreAmount = true;
						playerclient.guiFoodRestoreAmount = ((ItemTerraFood)player.inventory.getCurrentItem().getItem()).getHealAmount();
					}
					else
					{
						playerclient.guishowFoodRestoreAmount = false;
					}
				}
				else
				{
					playerclient.guishowFoodRestoreAmount = false;
				}
			}

		}
	}

	public void handleBodyTemp(EntityPlayer player)
	{
		float bodyTemp = player.getEntityData().hasKey("bodyTemp") ? player.getEntityData().getFloat("bodyTemp") : 37;

		TileEntityFireEntity te = null;
		for (int i = -10;i<10;i++){
			for(int j = -2; j < 3;j++){
				for(int k = -10;k<10;k++){
					if(player.worldObj.getBlockId((int)player.posX+i,(int)player.posY+j,(int)player.posZ+k)==TFCBlocks.Firepit.blockID){
						te = (TileEntityFireEntity)player.worldObj.getBlockTileEntity((int)player.posX+i, (int)player.posY+j, (int)player.posZ+k);
					}
				}
			}
		}
		double netBodyTemp = 0;
		double distanceTE = 0;
		if (te!=null) {
			distanceTE = Math.sqrt(Math.pow(player.posX-te.xCoord,2)+Math.pow(player.posY-te.yCoord,2)+Math.pow(player.posZ-te.zCoord,2));
		}
		float temp =TFC_Climate.getHeightAdjustedTemp((int)player.posX, (int)player.posY, (int)player.posZ);
		if(temp<25) {
			netBodyTemp-= (12*(te!=null&&te.fireTemperature>100?Math.pow(1d/(11-distanceTE),3):1))/(60*20*5*10*(Math.pow(5, temp<0?0:temp/10d)/temp<0?Math.abs(temp)/10d:1d));
		} else if(temp>=30) {
			netBodyTemp+=((temp+7)/bodyTemp)/(60*15*20);
		}
		netBodyTemp+=0.000017889*0.2D*(player.isSprinting()?12:1)*(player.inventory.armorInventory[3]!=null &&player.inventory.armorInventory[3].getItem() ==Item.helmetLeather?2.24:1)*(player.inventory.armorInventory[2]!=null &&player.inventory.armorInventory[2].getItem() ==Item.plateLeather?2.24:1)
				*(player.inventory.armorInventory[1]!=null &&player.inventory.armorInventory[1].getItem() ==Item.legsLeather?2.24:1)*(player.inventory.armorInventory[0]!=null &&player.inventory.armorInventory[0].getItem() ==Item.bootsLeather?2.24:1);
		bodyTemp+=netBodyTemp;
		if(temp<25) {
			bodyTemp=Math.max(temp, bodyTemp);
		}

		player.getEntityData().setFloat("bodyTemp", bodyTemp);
	}

	public int getMaxHealth(EntityPlayer player)
	{
		return Math.min(1000+(player.experienceLevel * TFCOptions.HealthGainRate), TFCOptions.HealthGainCap);
	}
}
