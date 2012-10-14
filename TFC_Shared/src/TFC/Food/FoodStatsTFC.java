package TFC.Food;

import net.minecraft.src.*;

public class FoodStatsTFC extends FoodStats
{
	
	/** The player's food level. */
    private int foodLevel = 50;

    /** The player's food saturation. */
    private float foodSaturationLevel = 5.0F;

    /** The player's food exhaustion. */
    private float foodExhaustionLevel;

    /** The player's food timer value. */
    private int foodTimer = 0;
    private int prevFoodLevel = 20;
    
	/**
     * Handles the food game logic.
     */
    public void onUpdate(EntityPlayer player)
    {
        int var2 = player.worldObj.difficultySetting;
        this.prevFoodLevel = this.foodLevel;

        if (this.foodExhaustionLevel > 4.0F)
        {
            this.foodExhaustionLevel -= 4.0F;

            if (this.foodSaturationLevel > 0.0F)
            {
                this.foodSaturationLevel = Math.max(this.foodSaturationLevel - 1.0F, 0.0F);
            }
            else if (var2 > 0)
            {
                this.foodLevel = Math.max(this.foodLevel - 1, 0);
            }
        }

        if (this.foodLevel >= 18 && player.shouldHeal())
        {
            ++this.foodTimer;

            if (this.foodTimer >= 80)
            {
                player.heal(1);
                this.foodTimer = 0;
            }
        }
        else if (this.foodLevel <= 0)
        {
            ++this.foodTimer;

            if (this.foodTimer >= 80)
            {
                if (player.getHealth() > 10 || var2 >= 3 || player.getHealth() > 1 && var2 >= 2)
                {
                    player.attackEntityFrom(DamageSource.starve, 1);
                }

                this.foodTimer = 0;
            }
        }
        else
        {
            this.foodTimer = 0;
        }
    }
    
    /**
     * If foodLevel is not max.
     */
    public boolean needFood()
    {
        return this.foodLevel < 100;
    }
}
