package com.bioxx.tfc.Handlers;

import java.util.Random;

import net.minecraft.util.ChatComponentTranslation;

import net.minecraftforge.event.ServerChatEvent;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Time;

public class ChatListenerTFC
{
	@SubscribeEvent
	public void onServerChatEvent(ServerChatEvent event)
	{
		String msg = event.message;
		long soberTime = TFC_Core.getPlayerFoodStats(event.player).soberTime;
		if(soberTime > TFC_Time.getTotalTicks())
		{
			String s = "aeiouywrsflzvbnmAEIOUYWRSFLZVBNM";
			Random rand = new Random();
			soberTime-=TFC_Time.getTotalTicks();
			for(int i = 0; i < event.message.length()-1; i++)
			{
				String start = event.message.substring(0, i);
				String s2 = event.message.substring(i, i+1);
				String end = event.message.substring(i+1);

				if(event.message.charAt(0) != '/')
				{
					int chance = Math.max(1, 11 - (int) (soberTime / 1000)); // Can't run rand.nextInt on a negative number.
					if (s.indexOf(s2) != -1 && rand.nextInt(chance) == 0)
					{
						int n = rand.nextInt(2);
						int m = 0;
						msg = start + s2;
						for(int j = 0; j < n; j++)
						{
							msg = msg + (end.substring(0, 1).toUpperCase().equals(end.substring(0, 1)) ? s2 : s2.toLowerCase());
						}
						if (("S".equals(s2) || "s".equals(s2)) && !"S".equals(end.substring(0, 1)) && !"s".equals(end.substring(0, 1)))
						{
							msg += (s2.toUpperCase().equals(s2) ? (end.substring(0, 1).toUpperCase().equals(end.substring(0, 1)) ? "H" : "h".toLowerCase()) : "h");
							m++;
						}
						msg = msg + end;
						i+=m;
					}
				}
			}
			event.component = new ChatComponentTranslation("<" + event.username + "> " + msg);
		}
	}
}
