package net.minecraft.src.vazkii.updatemanager;

/**
 * Any mods that should be added to the update manager must implement this Interface.
 * @param <BaseMod>
 * @author Vazkii
 */
public interface IUpdateManager<BaseMod> {

	/**
	 * The String that will get converted to an URL that tells the mod what file to check for updates, this should be the same as the mod's getVersion() method.
	 * @see <a href="http://dl.dropbox.com/u/34938401/Update%20Manager/Update%20Manager%20Version.txt">Example file</a>
	 */
	public String getUpdateURL();
	
	/**
	 * The website of this mod (can be anything just as long as it's an URL).
	 */
	public String getModURL();
	
	/**
	 * Gets the type of mod that this mod is.
	 * @return The ModType correspondent to this mod.
	 * @see ModType
	 */
	public ModType getModType();
	
}
