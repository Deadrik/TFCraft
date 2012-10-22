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
		player.setEntityHealth(TFC_PlayerServer.getMaxHealth());
		player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 20*60, 1));
		player.addPotionEffect(new PotionEffect(TFCPotion.bleed.id, 20*60, 1));
	}

}
