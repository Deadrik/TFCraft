package TFC.Core.Player;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.util.DamageSource;
import TFC.Core.TFC_Climate;
import TFC.Core.TFC_Core;
import TFC.Core.TFC_Time;
import TFC.Handlers.PacketHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class FoodStatsTFC
{
	/** The player's food level. This measures how much food the player can handle.*/
	public float foodLevel = 100;
	public long soberTime = 0;


	/** The player's food saturation. This is how full the player is from the food that they've eaten.*/
	private float foodSaturationLevel = 5.0F;

	/** The player's food exhaustion. This measures the rate of hunger decay. 
	 * When this reaches 4, some of the stored food is consumed by either 
	 * reducing the satiation or the food level.*/
	private float foodExhaustionLevel;

	/** The player's food timer value. */
	public long foodTimer = 0;
	public long foodHealTimer = 0;

	public float waterLevel = TFC_Time.dayLength*2;
	public long waterTimer = 0;


	private int prevFoodLevel = 100;



	Random rand = new Random();
	ItemStack itemHead,itemChest,itemLegs,itemFeet;

	public FoodStatsTFC()
	{
		waterTimer = TFC_Time.getTotalTicks();
		foodTimer = TFC_Time.getTotalTicks();
		foodHealTimer = TFC_Time.getTotalTicks();
	}

	/**
	 * Handles the food game logic.
	 */
	public void onUpdate(EntityPlayer player)
	{
		if(!player.worldObj.isRemote)
		{
			int difficulty = player.worldObj.difficultySetting;
			EntityPlayerMP playermp = (EntityPlayerMP)player;
			BodyTempStats bodyTemp = TFC_Core.getBodyTempStats(player);

			float temp = TFC_Climate.getHeightAdjustedTemp((int)player.posX, (int)player.posY, (int)player.posZ);

			/*
			 * Standard filling reduction based upon player exhaustion. This reduces filling faster than the standard time based reduction
			 */
			if (this.foodExhaustionLevel > 4.0F)
			{
				this.foodExhaustionLevel -= 4.0F;

				if (this.foodSaturationLevel > 0.0F)
					this.foodSaturationLevel = Math.max(this.foodSaturationLevel - 1.0F, 0.0F);
				else if (!player.capabilities.isCreativeMode)
					this.foodLevel = Math.max(this.foodLevel - 2, 0);
			}
			float satisfaction = 0;
			if (this.foodSaturationLevel < 0.0F)
				satisfaction = -foodSaturationLevel;


			float tempWaterMod = temp;
			if(tempWaterMod >= 30)
				tempWaterMod = (tempWaterMod-30)*0.1f;
			else
				tempWaterMod = 0;

			/*
			 * Standard filling reduction based upon time.
			 */
			if(!player.capabilities.isCreativeMode)
				if (TFC_Time.getTotalTicks() - this.foodTimer >= TFC_Time.hourLength)
				{
					this.foodLevel -= bodyTemp.getExtraFood();
					//Handle water related ticking
					if(player.isSprinting()&& !player.capabilities.isCreativeMode)
						waterLevel -= 5+(tempWaterMod);
					if(!player.capabilities.isCreativeMode)waterLevel-=bodyTemp.getExtraWater();
					this.foodTimer += TFC_Time.hourLength;
					if (this.foodSaturationLevel > 0.0F)
						this.foodSaturationLevel = Math.max(this.foodSaturationLevel - 1.0F, 0.0F);
					else if(!player.capabilities.isCreativeMode)
						this.foodLevel = Math.max(this.foodLevel - (1 + satisfaction), 0);
				}
			if (TFC_Time.getTotalTicks() - this.foodHealTimer >= TFC_Time.hourLength/2)
			{
				this.foodHealTimer += TFC_Time.hourLength/2;

				if (this.foodLevel >= 25 && player.shouldHeal())
				{
					player.heal((int) (player.getMaxHealth()*0.01f));

					if (this.foodSaturationLevel > 0.0F)
						this.foodSaturationLevel = Math.max(this.foodSaturationLevel - 4.0F, 0.0F);
					else if (!player.capabilities.isCreativeMode)
						this.foodLevel = Math.max(this.foodLevel - 1, 0);
				}
				else if (this.foodLevel <= 0)
					if (!TFC_Core.isPlayerInDebugMode(player) && (difficulty > 1 || (player.getMaxHealth() > 50)))
						player.attackEntityFrom(DamageSource.starve, 50);
			}

			/****************************************
			 * Handle Alcohol
			 ****************************************/

			soberTime = player.getEntityData().hasKey("soberTime") ? player.getEntityData().getLong("soberTime") : 0;
			if(soberTime > 0)
				soberTime--;
			player.getEntityData().setLong("soberTime", soberTime);


			long time = TFC_Time.getTotalTicks();

			if(player.capabilities.isCreativeMode)
			{
				long oldWaterTimer = waterTimer;
				waterTimer = time;
				if(player.isInWater())
					this.restoreWater(player, 20*(int)(time - oldWaterTimer));
			} else
				for(;waterTimer < time;  waterTimer++)
				{
					/**Reduce the player's water for normal living*/
					waterLevel -= 1+(tempWaterMod/2);
					if(player.isInWater())
						this.restoreWater(player, 20);
					if(waterLevel < 0)
						waterLevel = 0;
					if(!TFC_Core.isPlayerInDebugMode(player) && waterLevel == 0 && temp > 30)
						player.attackEntityFrom(DamageSource.generic, 2);
				}
		}
	}



	public int getMaxWater(EntityPlayer player)
	{
		return (TFC_Time.dayLength*2)+(200*player.experienceLevel);
	}



	/**
	 * Get the player's food level.
	 */
	public int getFoodLevel()
	{
		return (int) this.foodLevel;
	}

	@SideOnly(Side.CLIENT)
	public int getPrevFoodLevel()
	{
		return this.prevFoodLevel ;
	}

	/**
	 * If foodLevel is not max.
	 */
	public boolean needFood()
	{
		return this.foodLevel < 100;
	}

	public boolean needFood(int filling)
	{
		return needFood() && this.foodLevel + filling < 140;
	}

	/**
	 * Reads food stats from an NBT object.
	 */
	public void readNBT(NBTTagCompound par1NBTTagCompound)
	{
		if (par1NBTTagCompound.hasKey("foodCompound"))
		{
			NBTTagCompound foodCompound = par1NBTTagCompound.getCompoundTag("foodCompound");
			this.waterLevel = foodCompound.getFloat("waterLevel");
			this.foodLevel = foodCompound.getFloat("foodLevel");
			this.foodTimer = foodCompound.getLong("foodTickTimer");
			this.foodHealTimer = foodCompound.getLong("foodHealTimer");
			this.waterTimer = foodCompound.getLong("waterTimer");
			this.foodSaturationLevel = foodCompound.getFloat("foodSaturationLevel");
			this.foodExhaustionLevel = foodCompound.getFloat("foodExhaustionLevel");
		}
	}

	/**
	 * Writes food stats to an NBT object.
	 */
	public void writeNBT(NBTTagCompound par1NBTTagCompound)
	{
		NBTTagCompound foodCompound = new NBTTagCompound();
		foodCompound.setFloat("waterLevel", this.waterLevel);
		foodCompound.setFloat("foodLevel", this.foodLevel);
		foodCompound.setLong("foodTickTimer", this.foodTimer);
		foodCompound.setLong("foodHealTimer", this.foodHealTimer);
		foodCompound.setLong("waterTimer", this.waterTimer);
		foodCompound.setFloat("foodSaturationLevel", this.foodSaturationLevel);
		foodCompound.setFloat("foodExhaustionLevel", this.foodExhaustionLevel);
		par1NBTTagCompound.setCompoundTag("foodCompound", foodCompound);
	}

	/**
	 * adds input to foodExhaustionLevel to a max of 40
	 */
	public void addExhaustion(float par1)
	{
		this.foodExhaustionLevel = Math.min(this.foodExhaustionLevel + par1, 40.0F);
	}

	/**
	 * Get the player's food saturation level.
	 */
	public float getSaturationLevel()
	{
		return this.foodSaturationLevel;
	}

	public void setFoodLevel(int par1)
	{
		this.foodLevel = par1;
	}

	public void setFoodSaturationLevel(float par1)
	{
		this.foodSaturationLevel = par1;
	}

	/**
	 * Args: int foodLevel, float foodSaturationModifier
	 */
	public void addStats(int food, float satur)
	{
		this.foodLevel = Math.min(food + this.foodLevel, 100);
		if(satur < 0)
			this.foodSaturationLevel = this.foodSaturationLevel + satur;
		else
			this.foodSaturationLevel = Math.min(this.foodSaturationLevel + (float)food / 3 * satur * 2.0F, this.foodLevel);
	}

	/**
	 * Eat some food.
	 */
	public void addStats(ItemFood par1ItemFood)
	{
		this.addStats(par1ItemFood.getHealAmount(), par1ItemFood.getSaturationModifier());
	}

	public void restoreWater(EntityPlayer player, int w)
	{
		this.waterLevel = Math.min(this.waterLevel + w, this.getMaxWater(player));
		this.writeNBT(player.getEntityData());
	}

	public void resetTimers()
	{
		waterTimer = TFC_Time.getTotalTicks();
		foodTimer = TFC_Time.getTotalTicks();
		foodHealTimer = TFC_Time.getTotalTicks();
	}

	public static Packet getStatusPacket(FoodStatsTFC foodstats)
	{
		ByteArrayOutputStream bos=new ByteArrayOutputStream(10);
		DataOutputStream dos=new DataOutputStream(bos);
		Packet250CustomPayload pkt=new Packet250CustomPayload();
		try 
		{
			//The packet type sent determines who is expected to process this packet, the client or the server.
			dos.writeByte(PacketHandler.Packet_Player_Status);
			dos.writeByte((byte)0);
			dos.writeFloat(foodstats.foodLevel);
			dos.writeFloat(foodstats.waterLevel);

			pkt.channel="TerraFirmaCraft";
			pkt.data = bos.toByteArray();
			pkt.length= pkt.data.length;
			pkt.isChunkDataPacket=false;
		} 
		catch (IOException e) 
		{

		}
		return pkt;
	}
}
