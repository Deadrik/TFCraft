
package net.minecraft.src;

import java.lang.management.ManagementFactory;

import vazkii.um.ModCompatibility;
import vazkii.um.Settings;
import vazkii.um.ThreadUpdateManager;
import vazkii.um.UpdateManager;
import vazkii.um.UpdateManagerMod;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.forge.NetworkMod;

/**
 * @author Vazkii
 */
public class mod_UpdateManager extends NetworkMod {

	public static ThreadUpdateManager runningThread;
	
	public String getVersion() {
		return "by Vazkii. Version 2.2";
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
    	}else if(command.startsWith("um delay") && server.configManager.isOp(sender)){
    		int newTime = -1;
    		int oldTime = Settings.getInt("checkDelay");
    		try{
    			newTime = Integer.parseInt(command.replaceAll("um delay ", ""));
    		}catch(NumberFormatException e){
    			UpdateManager.sendChatMessageToPlayer(server.configManager.getPlayerEntity(sender), "§cInvalid Number!");
    			return false;
    		}
    		if(newTime < 0){
    			UpdateManager.sendChatMessageToPlayer(server.configManager.getPlayerEntity(sender), "§cInvalid Number!");
    			return false;
    		}
    		
    		Settings.setInt("checkDelay", newTime);
			UpdateManager.sendChatMessageToPlayer(server.configManager.getPlayerEntity(sender), "§9Changed check time from " + oldTime + " to " + newTime + '.');
			
    		return true;
    	}else if(command.startsWith("um op") && server.configManager.isOp(sender)){
    		Settings.setBoolean("opOnly", !Settings.getBoolean("opOnly"));
			UpdateManager.sendChatMessageToPlayer(server.configManager.getPlayerEntity(sender), "§9Set OP Only Warns to " + Settings.getBoolean("opOnly") + ".");
			return true;
    	}else if(command.startsWith("um login") && server.configManager.isOp(sender)){
    		Settings.setBoolean("loginCheck", !Settings.getBoolean("loginCheck"));
			UpdateManager.sendChatMessageToPlayer(server.configManager.getPlayerEntity(sender), "§9Set Login Warns to " + Settings.getBoolean("opOnly") + ".");
			return true;
    	}else if(command.startsWith("um reset") && server.configManager.isOp(sender)) {
    		Settings.setBoolean("loginCheck", true);
    		Settings.setBoolean("opOnly", true);
    		Settings.setInt("checkDelay", 900);
			UpdateManager.sendChatMessageToPlayer(server.configManager.getPlayerEntity(sender), "§9Settings reset!");
    	}else if(command.startsWith("um disable") && server.configManager.isOp(sender)) {
    		Settings.setBoolean("loginCheck", false);
    		Settings.setInt("checkDelay", 0);
			UpdateManager.sendChatMessageToPlayer(server.configManager.getPlayerEntity(sender), "§9Update Manager has been §cdisabled§9.");
    	} else if(command.startsWith("um settings")){
    		UpdateManager.sendChatMessageToPlayer(server.configManager.getPlayerEntity(sender), "§bDelay Time: " + Settings.getInt("checkDelay") + " | Ops Only: " + Settings.getBoolean("opOnly") + " |  Login Warn: " + Settings.getBoolean("loginCheck") + ".");
    		return true;
    	}else if(command.startsWith("um")){
    		UpdateManager.sendChatMessageToPlayer(server.configManager.getPlayerEntity(sender), "§9Update Manager Commands:");
    		UpdateManager.sendChatMessageToPlayer(server.configManager.getPlayerEntity(sender), "§e/um force: §9Forces an Update Check.");
    		UpdateManager.sendChatMessageToPlayer(server.configManager.getPlayerEntity(sender), "§e/um delay <time>: §9Sets the time between checks.");
    		UpdateManager.sendChatMessageToPlayer(server.configManager.getPlayerEntity(sender), "§e/um op: §9Changes if Update Checks should only be notified to OPs.");
    		UpdateManager.sendChatMessageToPlayer(server.configManager.getPlayerEntity(sender), "§e/um login: §9Changes if warns should be delivered to players logging in.");
    		UpdateManager.sendChatMessageToPlayer(server.configManager.getPlayerEntity(sender), "§e/um reset: §9Resets the Settings.");
    		UpdateManager.sendChatMessageToPlayer(server.configManager.getPlayerEntity(sender), "§e/um disable: §9Disables Update Manager.");
    		UpdateManager.sendChatMessageToPlayer(server.configManager.getPlayerEntity(sender), "§e/um settings: §9Views the current Settings.");
    		return true;
    	}
        return false;
    }
    
    public void onClientLogin(EntityPlayer player)
    {
    	String name = player.username;
    	MinecraftServer server = ModLoader.getMinecraftServerInstance();
    	
    	if(Settings.getBoolean("loginCheck")){
        	if(server.configManager.isOp(player.username))
        		UpdateManager.sendChatMessageToPlayer(player, "§9Welcome back! Use §e/um§9 to see Update Manager Commands.");

        	if(UpdateManager.canAlertPlayer(player, server.configManager) && !UpdateManager.areModsUpdated())
        		UpdateManager.warnUserOfOutdated(name);
    	}
    }

	public void load() {
		new ModCompatibility();
		new UpdateHandler(this);
		
		ModLoader.getMinecraftServerInstance().log("-agentlib:jdwp = " + ManagementFactory.getRuntimeMXBean().getInputArguments().toString().indexOf("-agentlib:jdwp"));
	}
	
	public void modsLoaded() {
		UpdateManager.loadMods();
		UpdateManager.dumpMods();
		
		Settings.init();
		
		runningThread = new ThreadUpdateManager(ModLoader.getMinecraftServerInstance());
	}
	
	public class UpdateHandler extends UpdateManagerMod {

		public UpdateHandler(BaseMod m) {
			super(m);
		}
		
		public String getUMVersion(){
			return "2.2";
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
