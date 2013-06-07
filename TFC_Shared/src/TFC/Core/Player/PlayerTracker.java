package TFC.Core.Player;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
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
			player.getFoodStats().setFoodLevel(player.worldObj.rand.nextInt(25)+35);
			player.setEntityHealth((int) (1000f*(((float)player.worldObj.rand.nextInt(25)+35)/100)));
			player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 20*(player.worldObj.rand.nextInt(45)+45), 0));
		}
	}

}
