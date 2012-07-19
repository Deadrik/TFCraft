package vazkii.um;

import net.minecraft.src.forge.NetworkMod;
import cpw.mods.fml.common.modloader.BaseMod;

/**
 * The class meant to be used for Update Manager handlers. By convenience, used as
 * an inner class, but you can do whatever flips your vote.
 * @author Vazkii
 */
public abstract class UpdateManagerMod {
	
	/**
	 * The mod associated to this.
	 */
	public BaseMod asocMod;
	
	/**
	 * @param m The mod to register on this.
	 */
	public UpdateManagerMod(BaseMod m){
		if(m == null || !UpdateManager.registerMod(this))
			throw new IllegalArgumentException("Failed to register mod.");
		asocMod = m;
	}

	/**
	 * Gets the URL for this mod that's linked to it's webpage.
	 */
	public abstract String getModURL();
	
	/**
	 * Gets the URL for the mod that gets checked for the version.
	 */
	public abstract String getUpdateURL();
	
	/**
	 * Gets the type of mod this mod is.
	 * @see ModType
	 */
	public ModType getModType(){
		return ModType.UNDEFINED;
	}
	
	/**
	 * Gets the name of the mod, this is used in the mod list GUI to differentiate your mod from others.
	 * If this is missing, it uses the class name.
	 */
	public String getModName(){
		return asocMod.getClass().getSimpleName();
	}
	
	/**
	 * Gets the URL for the mod's changelog.
	 */
	public String getChangelogURL(){
		return null;
	}
	
	/**
	 * Gets the URL for a direct download for the mod's most recent version, note that the URL links to a file
	 * with another URL which actually links to the download URL, this is to more easilly change the files.
	 */
	public String getDirectDownloadURL(){
		return null;
	}
	
	/**
	 * Gets the URL for a disclaimer file that gets displayed when a user downloads the mod,
	 * the first line of the file gets displayed in big red text. If the file is empty
	 * the disclaimer will be skipped and the download will just start.
	 * @return
	 */
	public String getDisclaimerURL(){
		return null;
	}
	
	/**
	 * Enables automatic downloads, if you set this to true and the Direct Download
	 * URL is null, you will recieve a NullPointerException, be wary.
	 */
	public boolean enableAutoDownload(){
		return getDirectDownloadURL() != null && getDisclaimerURL() == null && !UpdateManager.isModUpdated(this) && !UpdateManager.alreadyDownloadedMods.contains(this);
	}

	/**
	 * This is used to get a version independent of the BaseMod.getVersion() method
	 * for UM only, if it's missing it uses BaseMod.getVersion() instead or the getRelease()
	 * method if it's present.
	 */
	public String getUMVersion(){
		return getRelease() > 0 ? "Release " + Integer.toString(getRelease()) : asocMod.getVersion();
	}
	
	/**
	 * Checks if the version checking for the mod should check for actual versions.
	 * @return false if the checking should be modVersion.equals(webVersion), true
	 * if the checking should be done trough AtomicStryker's version comparing
	 * method (UpdateManager.compareVersions(...)).
	 */
	public boolean useParsedChecking(){
		return true;
	}
	
	/**
	 * This is used to get the release of the mod, it overwrites getUMVersion() in version checking and it's integer
	 * based, so if you have release 12, and the latest is release 11 it won't tell you you have outdated mods.
	 */
	public int getRelease(){
		return 0;
	}
	
	/**
	 * Used to disable checks for this mod, this can be useful for beta builds and the like.
	 */
	public boolean disableChecks(){
		return (getModType().debugOnly() && !UpdateManager.isDebug) || !getReleaseType().hasChecks();
	}
	
	/**
	 * Used to make a mod only check for updates if being ran in debug mode.
	 * @see <a href="http://help.eclipse.org/helios/index.jsp?topic=%2Forg.eclipse.jdt.doc.user%2Ftasks%2Ftasks-debug-launch.htm">Eclipse debug Help page</a>
	 */
	public boolean srcOnly(){
		return false;
	}
	
	/**
	 * Gets the Release Type of the mod
	 * @see ModReleaseType
	 */
	public ModReleaseType getReleaseType(){
		return ModReleaseType.RELEASED;
	}	
	
	/**
	 * Used to add notes to the update manager inteface.
	 * @return An array of note lines to write in the interface when this mod is chosen.
	 */
	public String[] addNotes(){
		return null;
	}
	
	/**
	 * This method is used to check for the correct object to check for when version checking,
	 * be that a Integer or a String.
	 * @see Integer
	 * @see String
	 */
	public final Object getObjectToCheck(){
		return getRelease() > 0 ? Integer.valueOf(getRelease()) : getUMVersion();
	}

}
