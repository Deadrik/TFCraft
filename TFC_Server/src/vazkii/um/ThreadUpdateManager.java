package vazkii.um;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.mod_UpdateManager;

/**
 * @author Vazkii
 */
public class ThreadUpdateManager extends Thread {

	MinecraftServer ms;
	
	public ThreadUpdateManager(MinecraftServer ms){
		setName("Update Manager Thread");
		this.ms = ms;
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
		while(MinecraftServer.isServerRunning(ms)){
			try{
				int sleepTime = Settings.getInt("checkDelay");
				
				while(sleepTime <= 0)
					sleep(1000);
				
				ms.log("[Mod Update Manager] Thread Executed Check. " + sleepTime);
					if(UpdateManager.online){
						UpdateManager.loadMods();
						if(!UpdateManager.areModsUpdated()){
							ms.logWarning("[Mod Update Manager] Detected Outdated Mods.");
							UpdateManager.warnUsersOfOutdated(true);
						}

						sleep(sleepTime*1000);
					}
			}catch(Throwable e){
				ms.logSevere("[Mod Update Manager] Thread Failed! Please Restart Server or contact Vazkii!");
				ms.logSevere("Error Report: ");
				ms.logSevere(e.toString());
			}
		}
	}

}
