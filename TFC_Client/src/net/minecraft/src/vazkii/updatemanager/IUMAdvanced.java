package net.minecraft.src.vazkii.updatemanager;

/**
 * Allows for advanced features for a mod using IUpdateManager.
 * @author Vazkii
 * @param <IUpdateManager>
 */
@SuppressWarnings("hiding")
public interface IUMAdvanced<IUpdateManager> {

	/**
	 * Used to get the Name of the mod that gets displayed in the GUI insted of mod_Whatever.
	 * @return The name of the mod, if you don't want this feature have it return "nil".
	 */
	public String getModName();
	
	/**
	 * Used to get the URL of the changelog for this mod.
	 * @return A String representing the URL for the changelog, if you don't want this feature have it return "nil".
	 */
	public String getChangelogURL();
	
}
