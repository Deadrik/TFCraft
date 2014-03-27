package TFC.Core.Player;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import TFC.TerraFirmaCraft;
import TFC.Core.TFC_Core;
import TFC.Handlers.PacketHandler;
import TFC.Handlers.Network.AbstractPacket;
import TFC.Handlers.Network.InitClientWorldPacket;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemPickupEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent.ServerConnectionFromClientEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent.ServerDisconnectionFromClientEvent;

public class PlayerTracker
{
	@SubscribeEvent
	public void onPlayerLoggedIn(PlayerLoggedInEvent event)
	{
		PlayerManagerTFC.getInstance().Players.add(new PlayerInfo(event.player.getDisplayName(), event.player.getUniqueID()));
		AbstractPacket pkt = new InitClientWorldPacket(event.player);
		TerraFirmaCraft.packetPipeline.sendTo(pkt, (EntityPlayerMP) event.player);
	}
	
//	@SubscribeEvent
	public void onClientConnect(ServerConnectionFromClientEvent event)
	{
	}

//	@SubscribeEvent
	public void onClientDisconnect(ServerDisconnectionFromClientEvent event)
	{
//			PlayerManagerTFC.getInstance().Players.add(new PlayerInfo(event.manager.clientHandler.getPlayer().username, manager));
//			TerraFirmaCraft.proxy.onClientLogin();
	}

	@SubscribeEvent
	public void onPlayerRespawn(PlayerRespawnEvent event)
	{
		if(!event.player.worldObj.isRemote)
		{
			int foodLevel = (event.player.worldObj.rand.nextInt(25) + 35);
			FoodStatsTFC foodstats = TFC_Core.getPlayerFoodStats(event.player);
			foodstats.addStats(foodLevel - foodstats.getFoodLevel(), 0.0f);
			TFC_Core.setPlayerFoodStats(event.player, foodstats);
			event.player.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(1000);
			event.player.setHealth(1000f * (0.25f + (event.player.worldObj.rand.nextFloat()*0.25f)));
			event.player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 20*(event.player.worldObj.rand.nextInt(45)+45), 0));
		}
		PlayerInfo pi = PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(event.player);
		if( pi.tempSkills != null)
			TFC_Core.setSkillStats(event.player, pi.tempSkills);

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
//		pi.networkManager.scheduleOutboundPacket(PacketHandler.getPacket(bos));
	}

	@SubscribeEvent
	public void notifyPickup(ItemPickupEvent event)
	{
		/*ItemStack quiver = null;
		ItemStack ammo = item.getEntityItem();
		for(int i = 0; i < 9; i++) 
		{
			if(player.inventory.getStackInSlot(i) != null && player.inventory.getStackInSlot(i).getItem() instanceof ItemQuiver)
			{
				quiver = player.inventory.getStackInSlot(i);
				break;
			}
		}

		if(quiver != null && (ammo.getItem() instanceof ItemArrow || ammo.getItem() instanceof ItemJavelin))
		{
			ItemStack is = ((ItemQuiver)quiver.getItem()).addItem(quiver, ammo);
			item.setEntityItemStack(is);
		}*/
	}

	// Register the Player Toss Event Handler, workaround for a crash fix
	@SubscribeEvent
	public void onPlayerTossEvent(ItemTossEvent event)
	{
		if(event.entityItem == null)
			event.setCanceled(true);
	}
}

