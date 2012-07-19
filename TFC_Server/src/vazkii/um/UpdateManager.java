package vazkii.um;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.ModLoader;
import net.minecraft.src.Packet3Chat;
import net.minecraft.src.ServerConfigurationManager;

/**
 *  The main Update Manager class, you shouldn't have to call methods from here.
 *  @author Vazkii
 */
public class UpdateManager {
	
	/**
	 * Used to check if the program is being ran in debug mode.
	 * @see <a href="http://help.eclipse.org/helios/index.jsp?topic=%2Forg.eclipse.jdt.doc.user%2Ftasks%2Ftasks-debug-launch.htm">Eclipse debug Help page</a>
	 */
	public static boolean isDebug = ManagementFactory.getRuntimeMXBean().getInputArguments().toString().indexOf("-agentlib:jdwp") > 0;

	/**
	 * The HashMap that keeps the loaded mods and if they are updated.
	 */
	public static HashMap<UpdateManagerMod, Boolean> loadedModsMap = new HashMap();
	
	/**
	 * The List that keeps the mods loaded, mostly used for iterating.
	 */
	public static Set<UpdateManagerMod> loadedModsList = new LinkedHashSet();
	
	/**
	 * Checks if the server is online, why is this useful on a server you ask?
	 * Well, you could be running in offline mode while you don't have internet for
	 * example, it's more of a failsafe than anything...
	 */
	public static final boolean online = isOnline();
	
	/**
	 * Registers a mod.
	 * @return If the mod was sucessfully registered.
	 */
	public static boolean registerMod(UpdateManagerMod mod){
		if(loadedModsList.contains(mod)) return false;
		return loadedModsList.add(mod);
	}
	
	/**
	 * Loads all of the mods and checks if they are updated.
	 */
	public static void loadMods(){
		loadedModsMap.clear();
		knownWebVersions.clear();
		for(UpdateManagerMod m : loadedModsList)
			loadMod(m);
	}
	
	private static void loadMod(UpdateManagerMod m){
		if(!online || m.disableChecks()){
			loadedModsMap.put(m, true);
			return;
		}
		Object o = m.getObjectToCheck();
		
		if(o instanceof Integer){
			int client = ((Integer)o).intValue();
			try{
				int web = Integer.parseInt(getWebVersionFor(m));
				loadedModsMap.put(m, client >= web);
			}catch(NumberFormatException e){
				e.printStackTrace();
				loadedModsMap.put(m, false);
				return;
			}	
		}else if(o instanceof String)
			loadedModsMap.put(m, m.useParsedChecking() ? compareVersions(m.getUMVersion(), getWebVersionFor(m)) : m.getUMVersion().equals(getWebVersionFor(m)));
		else throw new IllegalArgumentException("Invalid Check Object Type: " + o.toString());
	}
	
	/**
     * Compares two Strings lexigraphically and char-wise. If their length is not equal, but their content in the
     * shorter length is, returns false if the web version string is longer
     * @param umVersion version String reported by the mod
     * @param webVersion version String retrieved from the mod's version URL
     * @return true if umVersion is higher or equal to webVersion, false otherwise
     * @author AtomicStryker
     */
	private static Boolean compareVersions(String umVersion, String webVersion)
	{
		boolean newer = false;
		for (int i = 0; i < Math.min(umVersion.length(), webVersion.length()); i++)
		{
             int comparedchar = webVersion.substring(i, i+1).compareTo(umVersion.substring(i, i+1));
             
             // case: web version is higher, return false immediatly
             if (comparedchar > 0)
                     return false;
             
             // case: local version is not only equal but higher in a digit
             if (comparedchar < 0)
                     newer = true;
		}
    
		// if a web version is LONGER and the local version was equal up to it's end, the web version must be newer
		if (webVersion.length() > umVersion.length() && !newer)
			return false;
		
		return true;
	}
	
	public static boolean areModsUpdated(){
		return !loadedModsMap.containsValue(false);
	}
	
	protected static boolean isModUpdated(UpdateManagerMod m){
		return loadedModsMap.get(m);
	}
	
	/**
	 * Dumps the mods to the console;
	 */
	public static void dumpMods(){
		MinecraftServer server = ModLoader.getMinecraftServerInstance();
		
		if(online && !loadedModsList.isEmpty()){
			server.log("#######################################################");
			server.log("[Mod Update Manager] Dumping mod list.");
			server.log("");
			for(UpdateManagerMod u : loadedModsList)
				server.log(u.getModName() + " : Server Version = " + u.getUMVersion() + ", Web Version = " + getWebVersionFor(u) + " (" + loadedModsMap.get(u) + ").");
			server.log("");
			server.log("Finished dumping mod list, " + loadedModsList.size() + " mods dumped.");
			server.log("#######################################################");
		}
	}
	
	//HashMap containing known web versions to increase performance.
		private static HashMap<UpdateManagerMod, String> knownWebVersions = new HashMap();
		
		protected static String getWebVersionFor(UpdateManagerMod mod){
			if(knownWebVersions.containsKey(mod))
				return knownWebVersions.get(mod);
			
			try {
				URL url = new URL(mod.getUpdateURL());
					BufferedReader r = new BufferedReader(new InputStreamReader(url.openStream()));
					String s = r.readLine();
					r.close();
					
					knownWebVersions.put(mod, s);
					return s;
				} catch (IOException e) {
					e.printStackTrace();
				}
			return null;
		}
	
	private static boolean isOnline(){
		try{
			new URL("http://74.125.230.174").openConnection().connect(); //Confused? Copy the URL and paste it in your browser.
			return true;
		}catch (IOException e) {
			return false;
		}
	}
	
	private static List<UpdateManagerMod> getOutdatedMods(){
		List<UpdateManagerMod> returnList = new LinkedList();
		
		for(UpdateManagerMod mod : loadedModsList)
			if(!loadedModsMap.get(mod))
				returnList.add(mod);
				
		return returnList;
	}
	
	/**
	 * Send a message to a player on the server. (Client Sided for said player's client)
	 */
	public static void sendChatMessageToPlayer(EntityPlayer player, String msg){
		Packet3Chat chatPacket = new Packet3Chat(msg);
		
		if(player != null)
			((EntityPlayerMP)player).playerNetServerHandler.sendPacket(chatPacket);
	}
	
	public static void warnUsersOfOutdated(boolean op){
		List<UpdateManagerMod> outdatedList = getOutdatedMods();
		MinecraftServer ms = ModLoader.getMinecraftServerInstance();
		List<EntityPlayer> players = ms.configManager.playerEntities;
		
		for(EntityPlayer player : players){
			if(ms.configManager.isOp(player.username)){
				sendChatMessageToPlayer(player, "§9[Mod Update Manager] §cYour server is running outdated mods:");
				for(UpdateManagerMod mod : outdatedList)
					sendChatMessageToPlayer(player, "§e    - §o" + mod.getModName());
			}
		}
	}
	
	public static void warnUserOfOutdated(String playerName){
		List<UpdateManagerMod> outdatedList = getOutdatedMods();
		MinecraftServer ms = ModLoader.getMinecraftServerInstance();
		EntityPlayer player = ms.configManager.getPlayerEntity(playerName);

		sendChatMessageToPlayer(player, "§9[Mod Update Manager] §cYour server is running outdated mods:");
			for(UpdateManagerMod mod : outdatedList)
				sendChatMessageToPlayer(player, "§e    - §o" + mod.getModName());
	}
	
	/**
	 * Util, checks if a thread can be initted, this is used to prevent
	 * duplicate threads.
	 */
	public static boolean initThread(Thread thread){
		Set<Thread> threadSet = thread.getAllStackTraces().keySet();
		
		for(Thread t : threadSet)
			if(t.getName().matches(thread.getName()))
				return false;
		
		return true;
	}
	
	/**
	 * Util, checks if an alert message can be sent to a player.
	 */
	public static boolean canAlertPlayer(EntityPlayer player, ServerConfigurationManager manager){
		return Settings.getBoolean("opOnly") ? manager.isOp(player.username) : true; 
	}
}
