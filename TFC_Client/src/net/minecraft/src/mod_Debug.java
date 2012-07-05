package net.minecraft.src;

import java.io.File;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.src.TFC_Core.*;
import net.minecraft.src.forge.*;

public class mod_Debug extends NetworkMod
{
	private KeyBinding Key_Rain = new KeyBinding("Key_Rain", 103);
	private KeyBinding Key_Creative = new KeyBinding("Key_Creative", 104);
	private KeyBinding Key_Time = new KeyBinding("Key_Time", 105);

	@Override
	public void keyboardEvent(KeyBinding var1) 
	{
		Minecraft minecraft = ModLoader.getMinecraftInstance();
		if (var1 == this.Key_Rain && minecraft.inGameHasFocus)
		{
			if(!minecraft.theWorld.isRaining())
				minecraft.theWorld.worldInfo.setRaining(true);   
			else
				minecraft.theWorld.worldInfo.setRaining(false);
		}
		else if (var1 == this.Key_Creative && minecraft.inGameHasFocus)
		{
			if(minecraft.theWorld.worldInfo.getGameType() == 0)
			{
				NBTTagCompound nbt = minecraft.theWorld.worldInfo.getNBTTagCompound();
				nbt.setInteger("GameType", 1);
				minecraft.theWorld.worldInfo = new WorldInfo(nbt);
				minecraft.playerController = new PlayerControllerCreative(minecraft);
				minecraft.thePlayer.capabilities.isCreativeMode = true;
				minecraft.thePlayer.capabilities.allowFlying = true;
			}
			else if(minecraft.theWorld.worldInfo.getGameType() == 1)
			{
				NBTTagCompound nbt = minecraft.theWorld.worldInfo.getNBTTagCompound();
				nbt.setInteger("GameType", 0);
				minecraft.theWorld.worldInfo = new WorldInfo(nbt);
				minecraft.playerController = new PlayerControllerSP(minecraft);
				minecraft.thePlayer.capabilities.isCreativeMode = false;
				minecraft.thePlayer.capabilities.allowFlying = false;
				minecraft.thePlayer.capabilities.isFlying = false;
			}
		}
		else if (var1 == this.Key_Time && minecraft.inGameHasFocus)
        {
		    long t = minecraft.theWorld.worldInfo.getWorldTime();
		    System.out.println(t);
		    long day = t/36000;
		    System.out.println(day);
		    long month = day / 30;
            System.out.println(month);
            
            System.out.println("DotW:" +TFCSeasons.Days[TFCSeasons.getDayOfWeek()-1]);
        }

	}

	@Override
	public boolean clientSideRequired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean serverSideRequired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getVersion() 
	{
		return "1";
	}

	@Override
	public void load() 
	{
		ModLoader.registerKey(this, this.Key_Rain, false);
		ModLoader.registerKey(this, this.Key_Creative, false);
		ModLoader.registerKey(this, this.Key_Time, false);
		ModLoader.addLocalization("Key_Rain", "Toggle Rain");
		ModLoader.addLocalization("Key_Creative", "Toggle Creative");
		ModLoader.addLocalization("Key_Time", "Print Time");
		ModLoader.setInGameHook(this, true, false);


	}

}
