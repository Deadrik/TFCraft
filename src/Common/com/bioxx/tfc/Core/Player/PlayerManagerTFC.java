package com.bioxx.tfc.Core.Player;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;

public class PlayerManagerTFC
{
	public List<PlayerInfo> Players;
	private static final PlayerManagerTFC instance = new PlayerManagerTFC();

	public static final PlayerManagerTFC getInstance()
	{
		return instance;
	}

	private PlayerManagerTFC()
	{
		Players = new ArrayList();
	}

	public PlayerInfo getPlayerInfoFromPlayer(EntityPlayer player)
	{
		for(PlayerInfo pi : Players)
		{
			if(pi.Name.equals(player.getDisplayName()) && pi.PlayerUUID.equals(player.getUniqueID()))
				return pi;
		}
		return null;
	}

	public PlayerInfo getPlayerInfoFromName(String name)
	{
		for(PlayerInfo pi : Players)
		{
			if(pi.Name.equals(name))
				return pi;
		}
		return null;
	}

	public PlayerInfo getPlayerInfoFromUUID(String uuid)
	{
		for(PlayerInfo pi : Players)
		{
			if(pi.PlayerUUID.equals(uuid))
				return pi;
		}
		return null;
	}

	public PlayerInfo getClientPlayer()
	{
		if(Players.size() > 0)
			return Players.get(0);
		return null;
	}
}
