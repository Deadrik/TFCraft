package TFC.Food;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import TFC.Core.TFC_Time;
import TFC.Core.Player.PlayerManagerTFC;
import TFC.Core.Player.TFC_PlayerServer;
import net.minecraft.src.*;

public class FoodStatsTFC extends FoodStats
{

	/** The player's food level. */
	public int foodLevel = 100;

	/** The player's food saturation. */
	private float foodSaturationLevel = 5.0F;

	/** The player's food exhaustion. */
	private float foodExhaustionLevel;

	/** The player's food timer value. */
	private long foodTimer = 0;
	private int prevFoodLevel = 100;

	public int waterLevel = getMaxWater();
	private long waterTimer = 0;
	
	public FoodStatsTFC()
	{
		waterTimer = TFC_Time.getTotalTicks();
	}

	/**
	 * Handles the food game logic.
	 */
	public void onUpdate(EntityPlayer player)
	{
		int difficulty = player.worldObj.difficultySetting;
		this.prevFoodLevel = this.foodLevel;

		if (this.foodExhaustionLevel > 4.0F)
		{
			this.foodExhaustionLevel -= 4.0F;

			if (this.foodSaturationLevel > 0.0F)
			{
				this.foodSaturationLevel = Math.max(this.foodSaturationLevel - 1.0F, 0.0F);
			}
			else if (difficulty > 0)
			{
				this.foodLevel = Math.max(this.foodLevel - 1, 0);
			}
		}

		if (TFC_Time.getTotalTicks() - this.foodTimer >= TFC_Time.hourLength/2)
		{
			if (this.foodLevel >= 10 && TFC_PlayerServer.shouldHeal(player))
			{
				this.foodTimer = TFC_Time.getTotalTicks();
				player.heal(10);
			}
			else if (this.foodLevel <= 0)
			{
				//if (difficulty > 1 || (difficulty == 1 && player.getHealth() > 50))
				//{
				//    player.attackEntityFrom(DamageSource.starve, 50);
				//}
				this.foodTimer = TFC_Time.getTotalTicks();
			}
			else
			{
				this.foodTimer = TFC_Time.getTotalTicks();
			}
		}
		
		//Handle water related ticking
		if(player.isSprinting())
		{
			waterLevel -= 1;
		}
		
		for(;waterTimer <= TFC_Time.getTotalTicks();waterTimer += 20)
		{
			/**Reduce the player's water for normal living*/
			waterLevel -= 1;
			if(player.isSprinting())
			{
				waterLevel -= 3;
			}
			if(player.isInWater())
			{
				waterLevel = getMaxWater();
			}
			if(waterLevel < 0)
				waterLevel = 0;
		}
	}
	
	public static int getMaxWater()
	{
		return (int) (TFC_Time.dayLength*2/10);
	}

	/**
	 * Get the player's food level.
	 */
	public int getFoodLevel()
	{
		return this.foodLevel;
	}

	@SideOnly(Side.CLIENT)
	public int getPrevFoodLevel()
	{
		return this.prevFoodLevel;
	}

	/**
	 * If foodLevel is not max.
	 */
	public boolean needFood()
	{
		return this.foodLevel < 100;
	}

	/**
	 * Reads food stats from an NBT object.
	 */
	public void readNBT(NBTTagCompound par1NBTTagCompound)
	{
		if (par1NBTTagCompound.hasKey("foodCompound"))
		{
			NBTTagCompound foodCompound = par1NBTTagCompound.getCompoundTag("foodCompound");
			this.waterLevel = foodCompound.getInteger("waterLevel");
			this.foodLevel = foodCompound.getInteger("foodLevel");
			this.foodTimer = foodCompound.getLong("foodTickTimer");
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
		foodCompound.setInteger("waterLevel", this.waterLevel);
		foodCompound.setInteger("foodLevel", this.foodLevel);
		foodCompound.setLong("foodTickTimer", this.foodTimer);
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

	@SideOnly(Side.CLIENT)
	public void setFoodLevel(int par1)
	{
		this.foodLevel = par1;
	}

	@SideOnly(Side.CLIENT)
	public void setFoodSaturationLevel(float par1)
	{
		this.foodSaturationLevel = par1;
	}
}
