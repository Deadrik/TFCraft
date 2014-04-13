package TFC.Core.Player;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import TFC.Core.TFC_Core;
import TFC.Handlers.PacketHandler;
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
			float foodLevel = (player.worldObj.rand.nextFloat() * 12) + 12;
			FoodStatsTFC foodstats = TFC_Core.getPlayerFoodStats(player);
			foodstats.setFoodLevel(foodLevel);
			TFC_Core.setPlayerFoodStats(player, foodstats);
			player.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(1000);
			player.setHealth(1000f * (0.25f + (player.worldObj.rand.nextFloat()*0.25f)));
		}
		PlayerInfo pi = PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(player);
		if( pi.tempSkills != null)
			TFC_Core.setSkillStats(player, pi.tempSkills);

		//Send a request to the server for the skills data.
		ByteArrayOutputStream bos=new ByteArrayOutputStream(140);
		DataOutputStream dos=new DataOutputStream(bos);
		try 
		{
			dos.writeByte(PacketHandler.Packet_Update_Skills_Server);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		pi.networkManager.addToSendQueue(PacketHandler.getPacket(bos));

	}

}
