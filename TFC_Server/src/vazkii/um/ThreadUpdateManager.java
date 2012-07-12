package vazkii.um;

import net.minecraft.server.MinecraftServer;

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
				ms.log("[Mod Update Manager] Thread Executed Check.");
					if(UpdateManager.online){
						UpdateManager.loadMods();
						if(!UpdateManager.areModsUpdated()){
							ms.logWarning("[Mod Update Manager] Detected Outdated Mods.");
							UpdateManager.warnUsersOfOutdated(true);
						}
						sleep(900000);
					}
			}catch(Throwable e){
				ms.logSevere("[Mod Update Manager] Thread Failed! Please Restart Server or contact Vazkii!");
				e.printStackTrace();
			}
		}
	}

}
