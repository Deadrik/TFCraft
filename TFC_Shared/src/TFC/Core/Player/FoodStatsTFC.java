package TFC.Core.Player;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.util.DamageSource;
import TFC.API.Enums.EnumFoodGroup;
import TFC.API.Util.Helper;
import TFC.Core.TFC_Climate;
import TFC.Core.TFC_Core;
import TFC.Core.TFC_Time;
import TFC.Food.ItemFoodTFC;
import TFC.Handlers.PacketHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class FoodStatsTFC
{
	/** The player's food level. This measures how much food the player can handle.*/
	public float stomachLevel = 24;
	private float stomachMax = 24.0f;
	private float prevFoodLevel = 24;

	public float nutrFruit = 20;
	public float nutrVeg = 20;
	public float nutrGrain = 20;
	public float nutrDairy = 20;
	public float nutrProtein = 20;

	public long soberTime = 0;

	/** The player's food saturation. This is how full the player is from the food that they've eaten.*/
	private float satisfaction = 5.0F;

	private float foodExhaustionLevel = 0;
	private float waterExhaustionLevel = 0;

	/** The player's food timer value. */
	public long foodTimer = 0;
	public long foodHealTimer = 0;

	public float waterLevel = TFC_Time.dayLength*2;
	public long waterTimer = 0;

	public EntityPlayer player;


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
			/*if (this.foodExhaustionLevel > 4.0F)
			{
				this.foodExhaustionLevel -= 4.0F;

				if (this.satisfaction > 0.0F)
					this.satisfaction = Math.max(this.satisfaction - 1.0F, 0.0F);
				else if (!player.capabilities.isCreativeMode)
					this.stomachLevel = Math.max(this.stomachLevel - 2, 0);
			}*/
			float satisf = 0;
			if (this.satisfaction < 0.0F)
				satisf = -satisfaction;


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
					this.stomachLevel -= bodyTemp.getExtraFood();
					//Handle water related ticking
					if(player.isSprinting()&& !player.capabilities.isCreativeMode)
						waterLevel -= 5+(tempWaterMod);
					if(!player.capabilities.isCreativeMode)waterLevel-=bodyTemp.getExtraWater();
					this.foodTimer += TFC_Time.hourLength;
					/*if (this.satisfaction > 0.0F)
						this.satisfaction = Math.max(this.satisfaction - 1.0F, 0.0F);
					else*/ 
					if(!player.capabilities.isCreativeMode)
					{
						this.stomachLevel = Math.max(this.stomachLevel - (1 + foodExhaustionLevel), 0);
						nutrFruit = Math.max(this.nutrFruit - (1 + foodExhaustionLevel)/5, 0);
						nutrVeg = Math.max(this.nutrVeg - (1 + foodExhaustionLevel)/5, 0);
						nutrGrain = Math.max(this.nutrGrain - (1 + foodExhaustionLevel)/5, 0);
						nutrProtein = Math.max(this.nutrProtein - (1 + foodExhaustionLevel)/5, 0);
						nutrDairy = Math.max(this.nutrDairy - (1 + foodExhaustionLevel)/5, 0);
					}
				}
			if (TFC_Time.getTotalTicks() - this.foodHealTimer >= TFC_Time.hourLength/2)
			{
				this.foodHealTimer += TFC_Time.hourLength/2;

				if (this.stomachLevel >= this.getMaxStomach(player)/4 && player.shouldHeal())
				{
					player.heal((int) (player.getMaxHealth()*0.01f));

					if (this.satisfaction > 0.0F)
						this.satisfaction = Math.max(this.satisfaction - 4.0F, 0.0F);
					else if (!player.capabilities.isCreativeMode)
						this.stomachLevel = Math.max(this.stomachLevel - 0.25f, 0);
				}
				else if (this.stomachLevel <= 0)
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

	public float getMaxStomach(EntityPlayer player)
	{
		return this.stomachMax;
	}

	/**
	 * Get the player's food level.
	 */
	public float getFoodLevel()
	{
		return this.stomachLevel;
	}

	@SideOnly(Side.CLIENT)
	public float getPrevFoodLevel()
	{
		return this.prevFoodLevel ;
	}

	/**
	 * If foodLevel is not max.
	 */
	public boolean needFood()
	{
		return this.stomachLevel < getMaxStomach(this.player);
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
			this.stomachLevel = foodCompound.getFloat("foodLevel");
			this.foodTimer = foodCompound.getLong("foodTickTimer");
			this.foodHealTimer = foodCompound.getLong("foodHealTimer");
			this.waterTimer = foodCompound.getLong("waterTimer");
			this.satisfaction = foodCompound.getFloat("foodSaturationLevel");
			this.foodExhaustionLevel = foodCompound.getFloat("foodExhaustionLevel");
			this.nutrFruit = foodCompound.getFloat("nutrFruit");
			this.nutrVeg = foodCompound.getFloat("nutrVeg");
			this.nutrGrain = foodCompound.getFloat("nutrGrain");
			this.nutrProtein = foodCompound.getFloat("nutrProtein");
			this.nutrDairy = foodCompound.getFloat("nutrDairy");
		}
	}

	/**
	 * Writes food stats to an NBT object.
	 */
	public void writeNBT(NBTTagCompound par1NBTTagCompound)
	{
		NBTTagCompound foodCompound = new NBTTagCompound();
		foodCompound.setFloat("waterLevel", this.waterLevel);
		foodCompound.setFloat("foodLevel", this.stomachLevel);
		foodCompound.setLong("foodTickTimer", this.foodTimer);
		foodCompound.setLong("foodHealTimer", this.foodHealTimer);
		foodCompound.setLong("waterTimer", this.waterTimer);
		foodCompound.setFloat("foodSaturationLevel", this.satisfaction);
		foodCompound.setFloat("foodExhaustionLevel", this.foodExhaustionLevel);
		foodCompound.setFloat("nutrFruit", nutrFruit);
		foodCompound.setFloat("nutrVeg", nutrVeg);
		foodCompound.setFloat("nutrGrain", nutrGrain);
		foodCompound.setFloat("nutrProtein", nutrProtein);
		foodCompound.setFloat("nutrDairy", nutrDairy);
		par1NBTTagCompound.setCompoundTag("foodCompound", foodCompound);
	}

	public void addFoodExhaustion(float par1)
	{
		this.foodExhaustionLevel = par1;
	}

	public void addWaterExhaustion(float par1)
	{
		this.waterExhaustionLevel = par1;
	}

	/**
	 * Get the player's food saturation level.
	 */
	public float getSaturationLevel()
	{
		return this.satisfaction;
	}

	public void setFoodLevel(int par1)
	{
		this.stomachLevel = par1;
	}

	public void setFoodSaturationLevel(float par1)
	{
		this.satisfaction = par1;
	}

	/**
	 * Eat some food.
	 */
	public void addStats(ItemStack is)
	{
		if(is.getItem() instanceof ItemFoodTFC)
		{
			ItemFoodTFC item = (ItemFoodTFC) is.getItem();
			float weight = item.getFoodWeight(is);
			float decay = item.getFoodDecay(is);
			float eatAmount = Math.min(weight - decay, 5f);
			float stomachDiff = this.stomachLevel+eatAmount-getMaxStomach(this.player);
			if(stomachDiff > 0)
				eatAmount-=stomachDiff;
			//add the nutrition contents
			addNutrition(item.getFoodGroup(), eatAmount);
			//fill the stomach
			this.stomachLevel += eatAmount;
			//Now remove the eaten amount from the itemstack.
			if(reduceFood(is, eatAmount))
				is.stackSize = 0;
		}
	}

	public float getNutritionHealthModifier()
	{
		float nMod = 1.00f;
		nMod -= 0.2f * (nutrFruit/24f);
		nMod -= 0.2f * (nutrVeg/24f);
		nMod -= 0.2f * (nutrGrain/24f);
		nMod -= 0.2f * (nutrProtein/24f);
		nMod -= 0.2f * (nutrDairy/24f);
		return Math.max(nMod, 0.1f);

	}

	/**
	 * 
	 * @return return true if the itemstack should be consumed, else return false
	 */
	private boolean reduceFood(ItemStack is, float amount)
	{
		float weight = is.getTagCompound().getFloat("foodWeight");
		if(weight - amount <= 0)
			return true;
		else
			is.getTagCompound().setFloat("foodWeight", Helper.roundNumber(weight - amount, 10));
		return false;
	}

	private void addNutrition(EnumFoodGroup fg, float amount)
	{
		switch(fg)
		{
		case Dairy:
			this.nutrDairy += amount;
			break;
		case Fruit:
			this.nutrFruit += amount;
			break;
		case Grain:
			this.nutrGrain += amount;
			break;
		case Protein:
			this.nutrProtein += amount;
			break;
		case Vegetable:
			this.nutrVeg += amount;
			break;
		default:
			break;

		}
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
			dos.writeFloat(foodstats.stomachLevel);
			dos.writeFloat(foodstats.waterLevel);
			dos.writeFloat(foodstats.nutrFruit);
			dos.writeFloat(foodstats.nutrVeg);
			dos.writeFloat(foodstats.nutrGrain);
			dos.writeFloat(foodstats.nutrProtein);
			dos.writeFloat(foodstats.nutrDairy);

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
