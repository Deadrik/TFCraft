package net.minecraft.src;

import java.io.File;
import java.util.Arrays;

import net.minecraft.client.Minecraft;

import org.lwjgl.input.Keyboard;

import vazkii.um.CapeHandler;
import vazkii.um.GuiModListWithUMButton;
import vazkii.um.ModReleaseType;
import vazkii.um.ModType;
import vazkii.um.ThreadUpdateManager;
import vazkii.um.UpdateManager;
import vazkii.um.UpdateManagerMod;
import cpw.mods.fml.client.GuiModList;

/**
 * @author Vazkii
 */
public class mod_UpdateManager extends BaseMod {
	
	public static KeyBinding key = new KeyBinding("Mod List", Keyboard.KEY_M);
	
	public static final String[] langs = new String[]{
		"pt_PT", "es_ES", "fr_FR", "de_DE"
	};
	
	public String getVersion() {
		return "by Vazkii. Version 2.2";
	}
	
	public void modsLoaded() {
		vazkii.um.Settings.init();
		CapeHandler.initCapes();
		
		UpdateManager.loadMods();
		UpdateManager.dumpMods();
		
		File f = ModLoader.getMinecraftInstance().getAppDir("minecraft/downloadedMods");
			f.mkdirs();
			
		new ThreadUpdateManager(ModLoader.getMinecraftInstance());
	}

	public void load() {
		new vazkii.um.ModCompatibility();
		new UpdateHandler(this);

		ModLoader.registerKey(this, key, true);
		ModLoader.setInGUIHook(this, true, false);
		
		ModLoader.addLocalization("um.updated", "§9[Mod Update Manager] §aYour mods are up to date!");
		ModLoader.addLocalization("um.outdated", "§9[Mod Update Manager]§c You have outdated mods, open your mod list to see them.");
		ModLoader.addLocalization("um.offline", "§9[Mod Update Manager] §aYou are offline, couldn't check.");
		ModLoader.addLocalization("um.updated.pt_PT", "§9[Mod Update Manager] §aOs teus mods estão atualizados!");
		ModLoader.addLocalization("um.outdated.pt_PT", "§9[Mod Update Manager] §cTens mods desatualizados, abre a tua lista de mods para os ver.");
		ModLoader.addLocalization("um.outdated.pt_PT", "§9[Mod Update Manager] §aEstás offline, a verificação não foi possivel.");
		ModLoader.addLocalization("um.updated.es_ES", "§9[Mod Update Manager] §aTus mods están actualizadas!");
		ModLoader.addLocalization("um.outdated.es_ES", "§9[Mod Update Manager] §cTienes mods desactualizados, abrir tu lista de mods para verlos.");
		ModLoader.addLocalization("um.offline.es_ES", "§9[Mod Update Manager] §aEstás offline, no pudo comprobar.");
		ModLoader.addLocalization("um.updated.fr_FR", "§9[Mod Update Manager] §a Vos mods sont à jours!");
		ModLoader.addLocalization("um.outdated.fr_FR", "§9[Mod Update Manager] §cCertains de vos mods ne sont pas à jours, ouvrez votre liste de mods pour les voir.");
		ModLoader.addLocalization("um.offline.fr_FR", "§9[Mod Update Manager] §cVous êtes déconnecté, ne peut pas vérifier.");
		ModLoader.addLocalization("um.updated.ge_GE", "§9[Mod Update Manager] §aÜberwachte Mods sind aktuell!");
		ModLoader.addLocalization("um.outdated.ge_GE", "§9[Mod Update Manager] §cVeraltete Mods gefunden, für Details siehe Liste.");
		ModLoader.addLocalization("um.offline.ge_GE", "Nicht online, Prüfung nicht möglich.");
		ModLoader.addLocalization("um.updated.de_DE", "§9[Mod Update Manager] §aJe mods zijn up-to-date!");
		ModLoader.addLocalization("um.outdated.de_DE", "§9[Mod Update Manager] §cJe hebt verouderde mods, open je mod lijst om deze te zien.");
		ModLoader.addLocalization("um.offline.de_DE", "§9[Mod Update Manager] §cJe bent offline, kon niet controleren.");
	}
	
	public static String localize(String string, String locale){
		if(Arrays.asList(langs).contains(locale))
			return string + "." + locale;
		return string;
	}
	
	public boolean onTickInGUI(float f, Minecraft mc, GuiScreen gui){
		if(gui instanceof GuiModList && !(gui instanceof GuiModListWithUMButton))
			ModLoader.openGUI(mc.thePlayer, new GuiModListWithUMButton(ModLoader.<GuiScreen, GuiModList>getPrivateValue(GuiModList.class, (GuiModList)gui, 0)));
		
		return true;	
	}
	
	public void keyboardEvent(KeyBinding event) {
		if (event.keyCode == key.keyCode && event.isPressed())
			ModLoader.openGUI(ModLoader.getMinecraftInstance().thePlayer, new GuiModListWithUMButton(ModLoader.getMinecraftInstance().currentScreen));
	}
	
	public class UpdateHandler extends UpdateManagerMod {

		public UpdateHandler(BaseMod m) {
			super(m);
		}

		public String getModURL() {
			return "http://www.minecraftforum.net/topic/1243564-any-version-mod-update-manager-last-updated-22512/";
		}
		
		public String getUpdateURL() {
			return "http://dl.dropbox.com/u/34938401/Update%20Manager/Update%20Manager%20Version.txt";
		}
		
		public String getUMVersion(){
			return "2.2";
		}
		
		public  ModType getModType(){
			return ModType.SRC;
		}
		
		public String getModName(){
			return "Mod Update Manager";
		}
		
		public String getChangelogURL(){
			return "http://dl.dropbox.com/u/34938401/Update%20Manager/Ingame%20Changelog%20UM2.txt";
		}
		
		public String getDirectDownloadURL(){
			return "http://dl.dropbox.com/u/34938401/Update%20Manager/Direct%20Download.txt";
		}
		
		public String getDisclaimerURL(){
			return "http://dl.dropbox.com/u/34938401/Update%20Manager/Disclaimer.txt";
		}
		
		public String[] addNotes(){
			if(UpdateManager.isDebug)
			return null;
			else return new String[]{
					"Update Manager is a source only mod, which means all",
					"updating of the mod is in the modder's hands, not the user's.",
			};
		}
	}
}
