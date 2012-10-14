package TFC.Handlers;

import TFC.Core.TFC_PlayerMP;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerMP;
import cpw.mods.fml.common.IPlayerTracker;

public class PlayerTracker implements IPlayerTracker
{

	@Override
	public void onPlayerLogin(EntityPlayer player) {
		TFC_PlayerMP playerMP = new TFC_PlayerMP((EntityPlayerMP) player);
		player = playerMP;
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
		TFC_PlayerMP playerMP = new TFC_PlayerMP((EntityPlayerMP) player);
		playerMP.setEntityHealth(playerMP.getMaxHealth());
		player = playerMP;
	}

}
