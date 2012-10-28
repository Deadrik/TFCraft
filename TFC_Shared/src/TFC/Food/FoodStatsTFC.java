package TFC.Food;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import TFC.Core.TFC_Time;
import TFC.Core.Player.PlayerManagerTFC;
import TFC.Core.Player.TFC_PlayerServer;
import net.minecraft.src.*;

public class FoodStatsTFC extends FoodStats
{
	/** The player's food level. This measures how much food the player can handle.*/
	public int foodLevel = 100;
	

	/** The player's food saturation. This is how full the player is from the food that they've eaten.*/
	private float foodSaturationLevel = 5.0F;

	/** The player's food exhaustion. This measures the rate of hunger decay. 
	 * When this reaches 4, some of the stored food is consumed by either 
	 * reducing the satiation or the food level.*/
	private float foodExhaustionLevel;

	/** The player's food timer value. */
	private long foodTimer = 0;

	public int waterLevel = getMaxWater();
	private long waterTimer = 0;
	
	public FoodStatsTFC()
	{
		waterTimer = TFC_Time.getTotalTicks();
		foodTimer = TFC_Time.getTotalTicks();
	}

	/**
	 * Handles the food game logic.
	 */
	@Override
	public void onUpdate(EntityPlayer player)
	{
		int difficulty = player.worldObj.difficultySetting;

		if (this.foodExhaustionLevel > 4.0F)
		{
			this.foodExhaustionLevel -= 4.0F;

			if (this.foodSaturationLevel > 0.0F)
			{
				this.foodSaturationLevel = Math.max(this.foodSaturationLevel - 1.0F, 0.0F);
			}
			else if (difficulty > 0)
			{
				this.foodLevel = Math.max(this.foodLevel - 4, 0);
			}
		}

		if (TFC_Time.getTotalTicks() - this.foodTimer >= TFC_Time.hourLength/2)
		{
			this.foodTimer = TFC_Time.getTotalTicks();
			if (this.foodLevel >= 25 && TFC_PlayerServer.shouldHeal(player))
			{
				player.heal(10);
				
				if (this.foodSaturationLevel > 0.0F)
				{
					this.foodSaturationLevel = Math.max(this.foodSaturationLevel - 4.0F, 0.0F);
				}
				else if (difficulty > 0)
				{
					this.foodLevel = Math.max(this.foodLevel - 2, 0);
				}
			}
			else if (this.foodLevel <= 0)
			{
				if (difficulty > 1 || (difficulty == 1 && player.getHealth() > 50))
				{
				    player.attackEntityFrom(DamageSource.starve, 50);
				}
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
//			if(player.isSprinting())
//			{
//				waterLevel -= 3;
//			}
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
	@Override
	public int getFoodLevel()
	{
		return this.foodLevel;
	}

	/**
	 * If foodLevel is not max.
	 */
	@Override
	public boolean needFood()
	{
		return this.foodLevel < 100;
	}

	/**
	 * Reads food stats from an NBT object.
	 */
	@Override
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
	@Override
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
	@Override
	public void addExhaustion(float par1)
	{
		this.foodExhaustionLevel = Math.min(this.foodExhaustionLevel + par1, 40.0F);
	}

	/**
	 * Get the player's food saturation level.
	 */
	@Override
	public float getSaturationLevel()
	{
		return this.foodSaturationLevel;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void setFoodLevel(int par1)
	{
		this.foodLevel = par1;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void setFoodSaturationLevel(float par1)
	{
		this.foodSaturationLevel = par1;
	}
	
	/**
     * Args: int foodLevel, float foodSaturationModifier
     */
	@Override
    public void addStats(int par1, float par2)
    {
        this.foodLevel = Math.min(par1 + this.foodLevel, 100);
        this.foodSaturationLevel = Math.min(this.foodSaturationLevel + (float)par1 / 3 * par2 * 2.0F, (float)this.foodLevel);
    }

    /**
     * Eat some food.
     */
	@Override
    public void addStats(ItemFood par1ItemFood)
    {
        this.addStats(par1ItemFood.getHealAmount(), par1ItemFood.getSaturationModifier());
    }
}
