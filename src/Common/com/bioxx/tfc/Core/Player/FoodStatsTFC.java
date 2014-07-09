package com.bioxx.tfc.Core.Player;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;

import com.bioxx.tfc.Core.TFC_Climate;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.Food.ItemMeal;
import com.bioxx.tfc.api.TFCOptions;
import com.bioxx.tfc.api.Enums.EnumFoodGroup;
import com.bioxx.tfc.api.Interfaces.IFood;
import com.bioxx.tfc.api.Util.Helper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class FoodStatsTFC
{
	/** The player's food level. This measures how much food the player can handle.*/
	public float stomachLevel = 24;
	private float stomachMax = 24.0f;
	private float prevFoodLevel = 24;

	public float nutrFruit = 1.0f;
	public float nutrVeg = 1.0f;
	public float nutrGrain = 1.0f;
	public float nutrDairy = 1.0f;
	public float nutrProtein = 1.0f;
	public long soberTime = 0;

	/**This is how full the player is from the food that they've eaten. 
	 * It could also be how happy they are with what they've eaten*/
	private float satisfaction = 0.0F;

	private float foodExhaustionLevel = 0;
	private float waterExhaustionLevel = 0;

	/** The player's food timer value. */
	public long foodTimer = 0;
	public long foodHealTimer = 0;

	public float waterLevel = TFC_Time.dayLength*2;
	public long waterTimer = 0;

	public EntityPlayer player;
	private long nameSeed = Long.MIN_VALUE;

	public FoodStatsTFC(EntityPlayer player)
	{
		this.player = player;
		waterTimer = Math.max(TFC_Time.getTotalTicks(),TFC_Time.startTime);
		foodTimer = Math.max(TFC_Time.getTotalTicks(),TFC_Time.startTime);
		foodHealTimer = Math.max(TFC_Time.getTotalTicks(),TFC_Time.startTime);
	}

	/**
	 * Handles the food game logic.
	 */
	public void onUpdate(EntityPlayer player)
	{
		if(!player.worldObj.isRemote)
		{
			BodyTempStats bodyTemp = TFC_Core.getBodyTempStats(player);
			float temp = TFC_Climate.getHeightAdjustedTemp(player.worldObj, (int)player.posX, (int)player.posY, (int)player.posZ);

			float tempWaterMod = temp;
			if(tempWaterMod >= 30)
				tempWaterMod = (tempWaterMod-30)*0.1f;
			else
				tempWaterMod = 0;

			/*
			 * Standard filling reduction based upon time.
			 */
			if(this.foodTimer < TFC_Time.startTime)
			{
				this.foodTimer = TFC_Time.startTime;
				this.foodHealTimer = TFC_Time.startTime;
				this.waterTimer = TFC_Time.startTime;
			}

			if (TFC_Time.getTotalTicks() - this.foodTimer >= TFC_Time.hourLength && !player.capabilities.isCreativeMode)
			{
				this.foodTimer += TFC_Time.hourLength;
				float drainMult = 1.0f;
				if(player.getSleepTimer() > 0)
				{
					drainMult = 0.50f;
				}
				//Water
				if(player.isSprinting())
					waterLevel -= 5+(tempWaterMod);
				if(!player.capabilities.isCreativeMode)
					waterLevel -= bodyTemp.getExtraWater()*drainMult;

				//Food
				float hunger = ((1 + foodExhaustionLevel) + bodyTemp.getExtraFood())*drainMult;
				if(this.satisfaction >= hunger)
				{
					satisfaction -= hunger; 
					hunger = 0;
					foodExhaustionLevel = 0;
				}
				else
				{
					hunger -= satisfaction; 
					satisfaction = 0;
					foodExhaustionLevel = 0;
				}
				this.stomachLevel = Math.max(this.stomachLevel - hunger, 0);
				reduceNutrition();
			}

			//Heal or hurt the player based on hunger.
			if (TFC_Time.getTotalTicks() - this.foodHealTimer >= TFC_Time.hourLength/2)
			{
				this.foodHealTimer += TFC_Time.hourLength/2;

				if (this.stomachLevel >= this.getMaxStomach(player)/4 && player.shouldHeal())
				{
					//Player heals 1% per 30 in game minutes
					player.heal((int) (player.getMaxHealth() * 0.01f));
				}
				else if (this.stomachLevel <= 0 && getNutritionHealthModifier() < 0.85f && !TFC_Core.isPlayerInDebugMode(player) && player.getSleepTimer() == 0)
				{
					//Players loses health at a rate of 5% per 30 minutes if they are starving
					player.attackEntityFrom(DamageSource.starve, Math.max((int) (player.getMaxHealth() * 0.05f), 10));
				}
			}

			/****************************************
			 * Handle Alcohol
			 ****************************************/
			soberTime = player.getEntityData().hasKey("soberTime") ? player.getEntityData().getLong("soberTime") : 0;
			if(soberTime > 0)
				soberTime--;
			player.getEntityData().setLong("soberTime", soberTime);
			long time = TFC_Time.getTotalTicks();
			Block block = player.worldObj.getBlock((int)Math.floor(player.posX),(int)Math.floor(player.posY),(int)Math.floor(player.posZ));
			Block block2 = player.worldObj.getBlock((int)Math.floor(player.posX),(int)Math.floor(player.posY - 1),(int)Math.floor(player.posZ));
			if(player.capabilities.isCreativeMode)
			{
				long oldWaterTimer = waterTimer;
				waterTimer = time;
				if(player.isInWater() && (TFC_Core.isFreshWater(block) || TFC_Core.isFreshWater(block2)))
					this.restoreWater(player, 20*(int)(time - oldWaterTimer));
			}
			else
			{
				for(;waterTimer < time;  waterTimer++)
				{
					/**Reduce the player's water for normal living*/
					waterLevel -= 1+(tempWaterMod/2);
					if(player.isInWater() && (TFC_Core.isFreshWater(block) || TFC_Core.isFreshWater(block2)))
						this.restoreWater(player, 20);
					if(waterLevel < 0)
						waterLevel = 0;
					if(!TFC_Core.isPlayerInDebugMode(player) && waterLevel == 0 && temp > 30)
						player.attackEntityFrom(DamageSource.generic, 2);
				}
			}
		}
	}

	protected void reduceNutrition() 
	{
		nutrFruit = Math.max(this.nutrFruit - (0.005f + foodExhaustionLevel), 0);
		nutrVeg = Math.max(this.nutrVeg - (0.005f + foodExhaustionLevel), 0);
		nutrGrain = Math.max(this.nutrGrain - (0.005f + foodExhaustionLevel), 0);
		nutrProtein = Math.max(this.nutrProtein - (0.005f + foodExhaustionLevel), 0);
		nutrDairy = Math.max(this.nutrDairy - (0.005f + foodExhaustionLevel), 0);
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

	public boolean needDrink()
	{
		return this.waterLevel < getMaxWater(this.player) - 500;
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
		par1NBTTagCompound.setTag("foodCompound", foodCompound);
	}

	public void addFoodExhaustion(float par1)
	{
		this.foodExhaustionLevel = par1;
	}

	public void addWaterExhaustion(float par1)
	{
		this.waterExhaustionLevel = par1;
	}

	public float getSatisfaction()
	{
		return this.satisfaction;
	}

	public void setFoodLevel(float par1)
	{
		this.stomachLevel = par1;
	}

	public void setSatisfaction(float par1)
	{
		this.satisfaction = par1;
	}

	public long getPlayerFoodSeed()
	{
		if(nameSeed == Long.MIN_VALUE)
		{
			long seed = 0;
			byte[] nameBytes = player.getCommandSenderName().getBytes();
			for(byte b : nameBytes)
				seed+=b;
			nameSeed = seed + player.worldObj.getSeed();
		}
		return nameSeed;
	}

	/***
	 * @return Returns an array containing taste preferences in the following order: Sweetness, Sourness, Saltiness, Bitterness, Savoriness
	 */
	public int[] getPrefTaste()
	{
		Random R = new Random(getPlayerFoodSeed());
		return new int[]{10+R.nextInt(80),10+R.nextInt(80),10+R.nextInt(80),10+R.nextInt(80),10+R.nextInt(80)};
	}

	float getTasteFactor(ItemStack food)
	{
		Random R = new Random(getPlayerFoodSeed());
		float tasteFactor = 0.8f;
		int[] tastePref = getPrefTaste();

		tasteFactor += getTasteDistanceFactor(tastePref[0], ((IFood)food.getItem()).getTasteSweet(food));
		tasteFactor += getTasteDistanceFactor(tastePref[1], ((IFood)food.getItem()).getTasteSour(food));
		tasteFactor += getTasteDistanceFactor(tastePref[2], ((IFood)food.getItem()).getTasteSalty(food));
		tasteFactor += getTasteDistanceFactor(tastePref[3], ((IFood)food.getItem()).getTasteBitter(food));
		tasteFactor += getTasteDistanceFactor(tastePref[4], ((IFood)food.getItem()).getTasteSavory(food));

		return tasteFactor;
	}

	float getTasteDistanceFactor(int pref, int val)
	{
		int abs = pref-val;
		if(abs < 0)
			abs*= -1;

		if(abs < 11)
		{
			return (11-abs)*0.01f;
		}
		else return 0;
	}

	/**
	 * Eat some food.
	 */
	public void eatFood(ItemStack is)
	{

		if(is.getItem() instanceof ItemFoodTFC)
		{
			ItemFoodTFC item = (ItemFoodTFC) is.getItem();
			float weight = item.getFoodWeight(is);
			float decay = Math.max(item.getFoodDecay(is), 0);
			float eatAmount = Math.min(weight - decay, 5f);
			float stomachDiff = this.stomachLevel+eatAmount-getMaxStomach(this.player);
			if(stomachDiff > 0)
				eatAmount-=stomachDiff;

			float tasteFactor = getTasteFactor(is);
			//add the nutrition contents
			addNutrition(item.getFoodGroup(), eatAmount*tasteFactor);
			//fill the stomach
			this.stomachLevel += eatAmount;
			//Now remove the eaten amount from the itemstack.
			if(reduceFood(is, eatAmount))
				is.stackSize = 0;
		}
		else if(is.getItem() instanceof ItemMeal)
		{
			ItemMeal item = (ItemMeal) is.getItem();
			float weight = item.getFoodWeight(is);
			float decay = Math.max(item.getFoodDecay(is), 0);
			float eatAmount = Math.min(weight - decay, 5f);
			float stomachDiff = this.stomachLevel+eatAmount-getMaxStomach(this.player);
			if(stomachDiff > 0)
				eatAmount-=stomachDiff;
			float tasteFactor = getTasteFactor(is);
			//add the nutrition contents
			int[] fg = new int[]{getfg(is, 0), getfg(is, 1), getfg(is, 2), getfg(is, 3)};
			float[] weights = new float[]{0.5f,0.2f,0.2f,0.1f};
			for(int i = 0; i < 4; i++)
			{
				if(fg[i] != -1)
					addNutrition(EnumFoodGroup.values()[fg[i]], eatAmount*weights[i]*tasteFactor);
			}

			//fill the stomach
			this.stomachLevel += eatAmount;
			float _sat = item.getSatisfaction(is);
			if(!ItemMeal.isWarm(is))
				_sat *= 0.25f;
			this.satisfaction += eatAmount * _sat;
			//Now remove the eaten amount from the itemstack.
			if(reduceFood(is, eatAmount))
			{
				is.stackSize = 0;
			}
		}
		else if(is.getItem() instanceof IFood)
		{
			if(is.hasTagCompound())
			{
				NBTTagCompound nbt = is.getTagCompound();
				float weight = nbt.hasKey("foodWeight") ? nbt.getFloat("foodWeight") : 0;
				float decay = Math.max(nbt.hasKey("foodDecay") ? nbt.getFloat("foodDecay") : 0, 0);
				float eatAmount = Math.min(weight - decay, 5f);
				float tasteFactor = getTasteFactor(is);
				addNutrition(((IFood)(is.getItem())).getFoodGroup(), eatAmount*tasteFactor);
				this.stomachLevel += eatAmount;
				if(reduceFood(is, eatAmount))
					is.stackSize = 0;
			}
			else
			{
				addNutrition(((IFood)(is.getItem())).getFoodGroup(), 1f);
			}
		}
	}

	private int getfg(ItemStack is, int i)
	{
		if(is.getTagCompound().hasKey("FG" + i))
			return Integer.parseInt(is.getTagCompound().getString("FG" + i).split("\\:")[1]);
		return -1;
	}

	public float getNutritionHealthModifier()
	{
		float nMod = 0.00f;
		nMod += 0.2f * nutrFruit;
		nMod += 0.2f * nutrVeg;
		nMod += 0.2f * nutrGrain;
		nMod += 0.2f * nutrProtein;
		nMod += 0.2f * nutrDairy;
		return Math.max(nMod, 0.05f);
	}

	public static int getMaxHealth(EntityPlayer player)
	{
		return (int)(Math.min(1000+(player.experienceLevel * TFCOptions.HealthGainRate),
				TFCOptions.HealthGainCap) * TFC_Core.getPlayerFoodStats(player).getNutritionHealthModifier());
	}

	/**
	 * 
	 * @return return true if the itemstack should be consumed, else return false
	 */
	public static boolean reduceFood(ItemStack is, float amount)
	{
		if(is.hasTagCompound())
		{
			float weight = is.getTagCompound().getFloat("foodWeight");
			float decay = is.getTagCompound().hasKey("foodDecay") ? is.getTagCompound().getFloat("foodDecay") : 0;
			if(decay > 0 && (weight - decay) - amount <= 0)
				return true;
			else if(decay < 0 && weight - amount <= 0)
				return true;
			else
			{
				is.getTagCompound().setFloat("foodWeight", Helper.roundNumber(weight - amount, 10));
				is.getTagCompound().setFloat("foodDecay", Helper.roundNumber(decay - amount, 10));
			}
		}
		return false;
	}

	private void addNutrition(EnumFoodGroup fg, float foodAmt)
	{
		float amount = foodAmt/5f/50f;//converts it to 2% if it is 5oz of food
		switch(fg)
		{
		case Dairy:
			this.nutrDairy = Math.min(nutrDairy + amount, 1.0f);
			break;
		case Fruit:
			this.nutrFruit = Math.min(nutrFruit + amount, 1.0f);
			break;
		case Grain:
			this.nutrGrain = Math.min(nutrGrain + amount, 1.0f);
			break;
		case Protein:
			this.nutrProtein = Math.min(nutrProtein + amount, 1.0f);
			break;
		case Vegetable:
			this.nutrVeg = Math.min(nutrVeg + amount, 1.0f);
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

}
