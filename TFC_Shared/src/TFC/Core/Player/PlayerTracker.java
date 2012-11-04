package TFC.Core.Player;

import TFC.Food.TFCPotion;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.Potion;
import net.minecraft.src.PotionEffect;
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
			TFC_PlayerServer playerserver = TFC_PlayerServer.getFromEntityPlayer(player);
			playerserver.getFoodStatsTFC().setFoodLevel(player.worldObj.rand.nextInt(25)+35);
			player.setEntityHealth((int) ((float)TFC_PlayerServer.getStartingMaxHealth()*(((float)player.worldObj.rand.nextInt(25)+35)/(float)100)));
			player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 20*(player.worldObj.rand.nextInt(45)+45), 0));
		}
	}

}
