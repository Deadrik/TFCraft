package TFC.Core.Player;

import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.src.PlayerAPI;
import net.minecraft.src.PlayerBase;
import net.minecraft.util.FoodStats;
import TFC.Core.TFC_Settings;
import TFC.Food.FoodStatsTFC;
import TFC.Food.ItemMeal;
import TFC.Food.ItemTerraFood;

public class TFC_PlayerClient extends PlayerBase
{
	public boolean guishowFoodRestoreAmount = false;
	public int guiFoodRestoreAmount = 0;

	//Last time the spawn protection was updated
	private final long spawnProtectionTimer = -1;

	private final FoodStatsTFC foodstats;
	private FoodStats oldFood;

	public TFC_PlayerClient(PlayerAPI var1) {
		super(var1);
		foodstats = new FoodStatsTFC();
	}

	@Override
	public void beforeOnLivingUpdate() 
	{
		oldFood = this.player.getFoodStats();
		if(this.player.inventory.getCurrentItem() != null)
		{
			if(this.player.inventory.getCurrentItem().getItem() instanceof ItemMeal)
			{
				guishowFoodRestoreAmount = true;
				guiFoodRestoreAmount = ItemMeal.getMealFilling(this.player.inventory.getCurrentItem());
			}
			else if(this.player.inventory.getCurrentItem().getItem() instanceof ItemTerraFood)
			{
				guishowFoodRestoreAmount = true;
				guiFoodRestoreAmount = ((ItemTerraFood)this.player.inventory.getCurrentItem().getItem()).getHealAmount();
			}
			else
			{
				guishowFoodRestoreAmount = false;
			}
		}
		else
		{
			guishowFoodRestoreAmount = false;
		}
	}

	@Override
	public void afterOnLivingUpdate() 
	{
		//player.worldObj.setRainStrength(1F);
		this.player.setFoodStatsField(oldFood);			
		//this.foodstats.onUpdate(player);
	}

	public FoodStatsTFC getFoodStatsTFC()
	{
		return this.foodstats;
	}

	public static TFC_PlayerClient getFromEntityPlayer(EntityPlayer p)
	{
		EntityClientPlayerMP pmp = (EntityClientPlayerMP) p;
		return (TFC_PlayerClient) pmp.getPlayerBase("TFC Player Client");
	}

	@Override
	public int getMaxHealth()
	{
		return Math.min(1000+(this.player.experienceLevel * TFC_Settings.HealthGainRate), TFC_Settings.HealthGainCap);
	}

	public static int getStartingMaxHealth()
	{
		return 1000;
	}

}
