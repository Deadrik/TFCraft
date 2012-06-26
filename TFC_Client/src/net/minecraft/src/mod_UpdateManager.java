package net.minecraft.src;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.src.vazkii.updatemanager.*;

public class mod_UpdateManager extends BaseMod implements IUpdateManager, IUMAdvanced{

	@MLProp(name = "Time_Update_Delay", info = "How long to wait (in seconds) before updates are checked for again, set to -1 to disable time-based checks.", max = Integer.MAX_VALUE, min = -1) public static int updateTime = 900;
	@MLProp(name = "GUI_MainHex", info = "The Hex code for the main color in the Update Manager GUI")public static String mainHex = "09094C";
	@MLProp(name = "GUI_BarHex", info = "The Hex code for the color of the side bars in the Update Manager GUI")public static String barHex = "0000FF";
	@MLProp(name = "Disable_Checks", info = "Disables any checks for updates for performance issues.")public static boolean disableChecks = false;

	private static boolean hasTicked = false;
	public static boolean areModsOutdated;
	public static boolean isOnline;
	
	public static KeyBinding key = new KeyBinding("Update Manager",Keyboard.KEY_U);
	public static GameSettings options = ModLoader.getMinecraftInstance().gameSettings;
	
	public String getVersion() {
		return "by Vazkii. Version 1.2.2";
	}

	public void load() {
		UMCore.addMod(this);
		ModLoader.setInGameHook(this, true, true);
		ModLoader.registerKey(this, key, true);

		isOnline = isOnline();
		
		//Warnings for broken configs.
		assert mainHex.length() == 6 && mainHex.length() == 6 : "[Mod Update Manager] You broke your config file, go fix it or delete it.";
		if(mainHex.length() != 6 || barHex.length() != 6) throw new IllegalArgumentException("[Mod Update Manager] You broke your config file, go fix it or delete it.");
	}
	
	public String getPriorities(){
		return "before:*";
	}
	
	public boolean onTickInGame(float f, Minecraft minecraft){
		if(!hasTicked){
			if(!isOnline){
				minecraft.thePlayer.addChatMessage("§1[Mod Update Manager]§4 No Internet connection, couldn't check.");
				hasTicked = true;
				areModsOutdated = true;
				return true;
			}else new UpdateCheckThread(ModLoader.getMinecraftInstance(), "Update Manager Thread");
		hasTicked = true;
		areModsOutdated = !UMCore.areModsUpdated();
		}
		
		return !disableChecks;
	}
	
	public void keyboardEvent(KeyBinding event) {
		if (event.keyCode == key.keyCode && event.isPressed())
			ModLoader.openGUI(ModLoader.getMinecraftInstance().thePlayer, new net.minecraft.src.vazkii.updatemanager.ModListGui());
	}

	//Start Implemented Methods//
	
	public String getUpdateURL() {
		return "http://dl.dropbox.com/u/34938401/Update%20Manager/Update%20Manager%20Version.txt";
	}

	public String getModURL() {
		return "http://www.minecraftforum.net/topic/1243564-any-version-mod-update-manager-last-updated-22512/";
	}
	
	public ModType getModType() {
		return ModType.SOURCE_ONLY;
	}
	
	public String getModName() {
		return "Mod Update Manager";
	}

	public String getChangelogURL() {
		return "https://dl.dropbox.com/u/34938401/Update%20Manager/Ingame%20Changelog.txt";
	}
	
	//End Implemented Methods//
	
	private boolean isOnline(){
		try{
			new URL("http://74.125.230.174").openConnection().connect(); //Confused? Copy the URL and paste it in your browser.
		}catch(MalformedURLException e){
			return false;
		} catch (IOException e) {
			return false;
		}
		return true;
	}


}
