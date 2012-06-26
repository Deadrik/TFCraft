package net.minecraft.src.vazkii.updatemanager;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Iterator;

import net.minecraft.src.BaseMod;
import net.minecraft.src.ModLoader;

/**
 * @author Vazkii
 */
public class UMCore {
	
	/**
	 * A list of all the loaded mods.
	 */
	protected static List<IUpdateManager<? extends BaseMod>> loadedMods = new LinkedList();
	
	/**
	 * A list of all the outdated mods.
	 * @see ModListGui
	 */
	protected static List<IUpdateManager<? extends BaseMod>> outdatedMods = new LinkedList();
	
	/**
	 * Gets if the program is being ran in debug mode.
	 * @see <a href="http://help.eclipse.org/helios/index.jsp?topic=%2Forg.eclipse.jdt.doc.user%2Ftasks%2Ftasks-debug-launch.htm">Eclipse debug Help page</a>
	 */
	private static boolean isInDebugMode = ManagementFactory.getRuntimeMXBean().getInputArguments().toString().indexOf("-agentlib:jdwp") > 0;

	/**
	 * Adds a mod to the Update Manager mod list.
	 * @param mod The mod to add.
	 * @see IUpdateManager
	 */
	public static void addMod(IUpdateManager mod){
		if(loadedMods.contains(mod)) return;
		
		loadedMods.add(mod);
	}
	
	/**
	 * Checks if all the mods are updated.
	 */
	public static boolean areModsUpdated(){
		boolean hasOutdatedMods = false;
		
		if(loadedMods.isEmpty()) return true;
		
		Iterator it = loadedMods.iterator();
		while(it.hasNext()){
			IUpdateManager mod = (IUpdateManager)it.next();
			if(checkForUpdate(mod) && !outdatedMods.contains(mod)){
				outdatedMods.add(mod);
				hasOutdatedMods = true;
			}
		}
		return !hasOutdatedMods;
	}
	
	/**
	 * Checks for updates on a specific mod.
	 * @param mod The mod to check for updates.
	 * @return Is the mod outdated.
	 */
	protected static boolean checkForUpdate(IUpdateManager mod){
		if(!loadedMods.contains(mod)) return true;
		
		try {
			URL modPage = new URL(mod.getUpdateURL());
			BufferedReader modReader = new BufferedReader(new InputStreamReader(modPage.openStream()));
			String line = modReader.readLine();
			modReader.close();
			
			if(!mod.getModType().isRegular){
				if(isInDebugMode) return !line.equalsIgnoreCase(((BaseMod)mod).getVersion());
				return false;
			}else return !line.equalsIgnoreCase(((BaseMod)mod).getVersion());

		} catch (Exception e) {
			return true;
		}
	}


}
