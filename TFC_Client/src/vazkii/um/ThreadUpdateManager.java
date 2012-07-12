package vazkii.um;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.src.mod_UpdateManager;

/**
 * @author Vazkii
 */
public class ThreadUpdateManager extends Thread {
	
	final Minecraft mc;
	boolean firstTick;
	
	boolean firstTickUpdate;
	
	public ThreadUpdateManager(Minecraft mc){
		setName("Update Manager Thread");
		this.mc = mc;
		firstTick = true;
		setDaemon(true);
		if(UpdateManager.initThread(this))
			start();
		else
			try {
				finalize();
			} catch (Throwable e) {
				e.printStackTrace();
			}
	}
	
	public void run(){
				try {
					while(mc.running){
						int sleepTime = Settings.getInt("checkDelay");
						System.out.println(Settings.getInt("checkDelay") + " " + sleepTime);
						
						if(sleepTime <= 0)
							finalize();
						
						while(mc.thePlayer == null ||  mc.theWorld == null || (mc.theWorld.isRemote && !Settings.getBoolean("smpEnable"))) sleep(1000);
						System.out.println("[Mod Update Manager] Thread executed check.");
						String lang = mc.gameSettings.language;
						if(firstTick)
							if(UpdateManager.online){
								if(firstTickUpdate = UpdateManager.areModsUpdated())
								mc.thePlayer.addChatMessage(UpdateManager.areModsUpdated() ? mod_UpdateManager.localize("um.updated", lang) : mod_UpdateManager.localize("um.outdated", lang));
								else
									mc.thePlayer.addChatMessage(mod_UpdateManager.localize("um.outdated", lang));
							}else mc.thePlayer.addChatMessage(mod_UpdateManager.localize("um.offline", lang));
						else {
							UpdateManager.loadMods();
							if(!UpdateManager.areModsUpdated() && firstTickUpdate)
								mc.thePlayer.addChatMessage(mod_UpdateManager.localize("um.outdated", lang));
						}
						
						firstTick = false;
						System.out.println(sleepTime);
						sleep(sleepTime*1000);
					}
				} catch (Throwable e) {
					e.printStackTrace();
				}
	}
}
