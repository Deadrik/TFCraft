package vazkii.um;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.minecraft.src.ModLoader;


/**
 *  The main Update Manager class, you shouldn't have to call methods from here.
 *  @author Vazkii
 */
public class UpdateManager {

	/**
	 * Used to check if the program is being ran in debug mode.
	 * @see <a href="http://help.eclipse.org/helios/index.jsp?topic=%2Forg.eclipse.jdt.doc.user%2Ftasks%2Ftasks-debug-launch.htm">Eclipse debug Help page</a>
	 */
	public static final boolean isDebug = ManagementFactory.getRuntimeMXBean().getInputArguments().toString().indexOf("-agentlib:jdwp") > 0;

	/**
	 * The HashMap that keeps the loaded mods and if they are updated.
	 */
	public static HashMap<UpdateManagerMod, Boolean> loadedModsMap = new HashMap();
	
	/**
	 * The Set that keeps the mods loaded, mostly used for iterating.
	 */
	public static Set<UpdateManagerMod> loadedModsSet = new LinkedHashSet();
	
	/**
	 * The mods that were already auto-downloaded.
	 */
	protected static List<UpdateManagerMod> alreadyDownloadedMods = new LinkedList();
	
	/**
	 * The webpage for the Update Manager forum topic.
	 */
	public static final String umWebpage = "http://bit.ly/modUpdateManager";
	
	/**
	 * Checks if the client is online.
	 */
	public static final boolean online = isOnline();
	
	/**
	 * Registers a mod.
	 * @return If the mod was sucessfully registered.
	 */
	public static boolean registerMod(UpdateManagerMod mod){
		new ThreadUpdateManager(ModLoader.getMinecraftInstance());
		return loadedModsSet.add(mod);
	}
	
	/**
	 * Loads all of the mods and checks if they are updated.
	 */
	public static void loadMods(){
		loadedModsMap.clear();
		knownWebVersions.clear();
		for(UpdateManagerMod m : loadedModsSet)
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
		
		if(Settings.getBoolean("autoDownload") && m.enableAutoDownload()){
				System.out.println("[Mod Update Manager] Detected outdated mod " + m.getModName() + ", downloading...");	
				new ThreadDownloadMod(m.getDirectDownloadURL(), m);
					alreadyDownloadedMods.add(m);
				}
	}

	protected static boolean areModsUpdated(){
		return !loadedModsMap.containsValue(false);
	}
	
	protected static boolean isModUpdated(UpdateManagerMod m){
		return loadedModsMap.get(m);
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

	protected static boolean openWebpage(String url){
			try {
				Runtime.getRuntime().exec("cmd /c start " + url);
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
			return true;
	}
	
	private static boolean isOnline(){
		try{
			new URL("http://74.125.230.174").openConnection().connect(); //Confused? Copy the URL and paste it in your browser.
			return true;
		}catch (IOException e) {
			return false;
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
	
	/**
	 * Dumps the mods to the console;
	 */
	public static void dumpMods(){
		if(online && !loadedModsSet.isEmpty()){
			System.out.println("#######################################################");
			System.out.println("[Mod Update Manager] Dumping mod list.");
			System.out.println();
			for(UpdateManagerMod u : loadedModsSet)
				System.out.println(u.getModName() + " : Client Version = " + u.getUMVersion() + ", Web Version = " + getWebVersionFor(u) + " (" + loadedModsMap.get(u) + ").");
			System.out.println();
			System.out.println("Finished dumping mod list, " + loadedModsSet.size() + " mods dumped.");
			System.out.println("#######################################################");
		}
	}
	
	
	/**
	 * Sorts the loaded mods.
	 * @param modList The list of mods to sort.
	 * @param modMap The map of mods to check if the mods are updated.
	 * @return A Linked List with the mods sorted propperly.
	 */
	public static LinkedList<UpdateManagerMod> sortMods(Set<UpdateManagerMod> modSet, Map<UpdateManagerMod, Boolean> modMap){
		LinkedList<UpdateManagerMod> outdatedApis = new LinkedList();
		LinkedList<UpdateManagerMod> outdatedOtherMods = new LinkedList();
		LinkedList<UpdateManagerMod> updatedMods = new LinkedList();
		LinkedList<UpdateManagerMod> nonCheckingMods = new LinkedList();
		LinkedList<UpdateManagerMod> totalMods = new LinkedList();
		
		for(UpdateManagerMod mod : loadedModsSet){
			boolean b = loadedModsMap.get(mod).booleanValue();
			if(mod.disableChecks()) nonCheckingMods.add(mod);
			else if(b) updatedMods.add(mod);
			else if(mod.getModType() == ModType.API) outdatedApis.add(mod);
			else outdatedOtherMods.add(mod);
		}
		
		totalMods.addAll(outdatedApis);
		totalMods.addAll(outdatedOtherMods);
		totalMods.addAll(updatedMods);
		totalMods.addAll(nonCheckingMods);
		
		return totalMods;
	}
	
	/**
	 * Util: Get the amount of values of the certain type on a map.
	 * @param object The value to search for.
	 * @param map The map to search on.
	 * @return The amount of entries found.
	 */
	public static int getQuantEntries(Object object, Map map){
		Collection values = map.values();
		int foundEntries = 0;
		for(Object obj : values)
			if(obj.equals(object))
				++foundEntries;
		
		return foundEntries;
	}
	
	/**
	 * Util: checks if a thread can be initted, this is used to prevent
	 * duplicate threads.
	 */
	public static boolean initThread(Thread thread){
		Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
		
		for(Thread t : threadSet)
			if(t.getName().matches(thread.getName()))
				return false;
		
		return true;
	}
	
}
