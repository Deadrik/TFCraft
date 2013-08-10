package TFC.Core.Player;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import TFC.Core.TFC_Core;
import TFC.Food.FoodStatsTFC;
import cpw.mods.fml.common.IPlayerTracker;

public class PlayerTracker implements IPlayerTracker
{

	@Override
	public void onPlayerLogin(EntityPlayer player) {
	}

	@Override
	public void onPlayerLogout(EntityPlayer player) {	

	}

	@Override
	public void onPlayerChangedDimension(EntityPlayer player) {


	}

	@Override
	public void onPlayerRespawn(EntityPlayer player) 
	{
		if(!player.worldObj.isRemote)
		{
			int foodLevel = (player.worldObj.rand.nextInt(25) + 35);
			FoodStatsTFC foodstats = TFC_Core.getPlayerFoodStats(player);
			foodstats.addStats(foodLevel - foodstats.getFoodLevel(), 0.0f);
			TFC_Core.setPlayerFoodStats(player, foodstats);
			player.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(1000);
			player.setEntityHealth(1000f * (0.25f + (player.worldObj.rand.nextFloat()*0.25f)));
			player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 20*(player.worldObj.rand.nextInt(45)+45), 0));
		}
	}

}
