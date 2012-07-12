package vazkii.um;

import net.minecraft.client.Minecraft;
import net.minecraft.src.ModLoader;
import net.minecraft.src.mod_UpdateManager;

public class CapeUpdateThread extends Thread{
	
	Minecraft mc;
	
	public CapeUpdateThread(){
		setName("Update Manager Cape Update Thread");
		mc = ModLoader.getMinecraftInstance();
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
		while(mc.running){
			mc = ModLoader.getMinecraftInstance();
			if(mc.theWorld != null)
				CapeHandler.updateCapes(mc.theWorld);
			try {
				sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
