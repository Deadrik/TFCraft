package vazkii.um;

/**
 * The Enum for the types of mods for the release status.
 * @author Vazkii
 */
public enum ModReleaseType {

	/**
	 * A fully released mod. Displays in white.
	 */
	RELEASED("Released", true, 0xFFFFFF),
	
	/**
	 * A mod on Beta Status. Displays in green.
	 */
	BETA("Beta Release", true, 0x00FF00),
	
	/**
	 * A mod on Alpha Status. Displays in blue.
	 */
	ALPHA("Alpha Release", true, 0x0000FF),
	
	/**
	 * A mod on Pre-Release Status. Displays in yellow.
	 */
	PRE_RELEASE("Pre-Release", true, 0xF7FF00),
	
	/**
	 * A development build of the mod which doesn't check for updates. Displays in red.
	 */
	DEVBUILD("Development Build", false, 0xFF0000);
	
	String name;
	boolean checks;
	int color;
	
	private ModReleaseType(String name, boolean checks, int color){
		this.name = name;
		this.checks = checks;
		this.color = color;
	}
	
	/**
	 * Gets the name to display on the GUI.
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Gets if the mod should check for updates.
	 */
	public boolean hasChecks(){
		return checks;
	}
	
	/**
	 * Gets the color to display in the GUI.
	 */
	public int getHex(){
		return color;
	}
}
