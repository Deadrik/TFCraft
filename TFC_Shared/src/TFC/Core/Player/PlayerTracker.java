package TFC.Core.Player;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
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
			int foodLevel = (player.worldObj.rand.nextInt(25) + 35);
			FoodStatsTFC foodstats = TFC_Core.getPlayerFoodStats(player);
			//foodstats.addStats(foodLevel - foodstats.getFoodLevel(), 0.0f);
			TFC_Core.setPlayerFoodStats(player, foodstats);
			player.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(1000);
			player.setHealth(1000f * (0.25f + (player.worldObj.rand.nextFloat()*0.25f)));
			player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 20*(player.worldObj.rand.nextInt(45)+45), 0));
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
