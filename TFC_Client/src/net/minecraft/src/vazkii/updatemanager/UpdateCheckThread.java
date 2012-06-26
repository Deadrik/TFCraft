package net.minecraft.src.vazkii.updatemanager;

import net.minecraft.client.Minecraft;
import net.minecraft.src.mod_UpdateManager;

public class UpdateCheckThread extends Thread {
	
	final Minecraft mc;
	
	public UpdateCheckThread(Minecraft minecraft, String name){
		super(name);
		mc = minecraft;
		setDaemon(true);
		start();
	}

	public void run(){
		if(mod_UpdateManager.disableChecks) return;
		while(mc.running){
			System.out.println("Update Manager: Thread executed check.");
			if(mod_UpdateManager.updateTime > 0 && !mod_UpdateManager.areModsOutdated){
				if(!UMCore.areModsUpdated())
				mc.thePlayer.addChatMessage("§1[Mod Update Manager]§4 You have outdated mods, press '"+ mod_UpdateManager.options.getKeyDisplayString(mod_UpdateManager.key.keyCode) + "' to open the update manager menu.");
				mod_UpdateManager.areModsOutdated = !UMCore.areModsUpdated();
			}
			try {
				Thread.sleep(mod_UpdateManager.updateTime*1000);
			} catch (InterruptedException e) {

			}
		}
	}

}
