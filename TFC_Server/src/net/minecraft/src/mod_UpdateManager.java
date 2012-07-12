
package net.minecraft.src;

import java.lang.management.ManagementFactory;

import vazkii.um.ModCompatibility;
import vazkii.um.ThreadUpdateManager;
import vazkii.um.UpdateManager;
import vazkii.um.UpdateManagerMod;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.forge.NetworkMod;

/**
 * @author Vazkii
 */
public class mod_UpdateManager extends NetworkMod {

	ThreadUpdateManager runningThread;
	
	public String getVersion() {
		return "by Vazkii. Version 2.0";
	}
	
	public String getPriorities(){
		return "before:*";
	}

    public boolean onServerCommand(String command, String sender, ICommandListener listener)
    {
    	MinecraftServer server = ModLoader.getMinecraftServerInstance();
    	if(command.startsWith("um force") && server.configManager.isOp(sender)){
    			UpdateManager.loadMods();
    			UpdateManager.sendChatMessageToPlayer(server.configManager.getPlayerEntity(sender), "§9Update Check has been forced!");
    			server.log("[Mod Update Manager] Update Check has been forced!");
    			
    			if(!UpdateManager.areModsUpdated())
    				UpdateManager.warnUserOfOutdated(sender);
    			return true;
    	}
        return false;
    }
    
    public void onClientLogin(EntityPlayer player)
    {
    	String name = player.username;
    	MinecraftServer server = ModLoader.getMinecraftServerInstance();
    	
    	if(server.configManager.isOp(name) && !UpdateManager.areModsUpdated())
    		UpdateManager.warnUserOfOutdated(name);
    }

	public void load() {
		new ModCompatibility();
		new UpdateHandler(this);
		ModLoader.getMinecraftServerInstance().log("-agentlib:jdwp = " + ManagementFactory.getRuntimeMXBean().getInputArguments().toString().indexOf("-agentlib:jdwp"));
	}
	
	public void modsLoaded() {
		UpdateManager.loadMods();
		UpdateManager.dumpMods();
		
		runningThread = new ThreadUpdateManager(ModLoader.getMinecraftServerInstance());
	}
	
	public class UpdateHandler extends UpdateManagerMod {

		public UpdateHandler(BaseMod m) {
			super(m);
		}
		
		public String getUpdateURL() {
			return "http://dl.dropbox.com/u/34938401/Update%20Manager/Update%20Manager%20Version.txt";
		}
		
		public String getModName(){
			return "Mod Update Manager";
		}
		
		public boolean disableChecks(){
			return !UpdateManager.isDebug;
		}
	}

}
