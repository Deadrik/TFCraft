package net.minecraft.src.vazkii.updatemanager;

/**
 * An enum holding the various types of mods, used in the GUI to differentiate them.
 * @author Vazkii
 */
public enum ModType {

	/**
	 * An API, displays in red.
	 */
	API("(API) ", true, 0xFF0000),
	
	/**
	 * A core mod. (A requirement for all the optional modules of a modular mod), displays in blue.
	 */
	CORE("(Core) ", true, 0x0000FF),
	
	/**
	 * A regular mod.
	 */
	UNDEFINED ("", true, 0xFFFFFF),
	
	/**
	 * A mod that's an addon to another.
	 */
	ADDON ("(Addon) ", true, 0xFFFFFF),
	
	/**
	 * A mod that only appears in the GUI if the program is being ran in debug mode, displays in purple.
	 * @see <a href="http://help.eclipse.org/helios/index.jsp?topic=%2Forg.eclipse.jdt.doc.user%2Ftasks%2Ftasks-debug-launch.htm">Eclipse debug Help page</a>
	 */
	SOURCE_ONLY ("(Source Only) ", false, 0xA020F0);
	
	private ModType(String display, boolean isRegular, int hex){
		displayName = display;
		this.isRegular = isRegular;
		hexColor = hex;
	}
	
	public String displayName;
	public boolean isRegular;
	public int hexColor;
	
}
