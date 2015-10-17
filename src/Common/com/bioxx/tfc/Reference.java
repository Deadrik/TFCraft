package com.bioxx.tfc;

public class Reference 
{
	public static final String MOD_ID = "terrafirmacraft";
	public static final String MOD_NAME = "TerraFirmaCraft";

	public static final int VERSION_MAJOR = 0;
	public static final int VERSION_MINOR = 79;
	public static final int VERSION_REVISION = 25;

	public static final String MOD_VERSION = VERSION_MAJOR+"."+VERSION_MINOR+"."+VERSION_REVISION;

	public static final String MOD_DEPENDENCIES = "required-after:tfc_coremod";
	public static final String MOD_CHANNEL = "TerraFirmaCraft";
	public static final String SERVER_PROXY_CLASS = "com.bioxx.tfc.CommonProxy";
	public static final String CLIENT_PROXY_CLASS = "com.bioxx.tfc.ClientProxy";

	public static final String ASSET_PATH = "/assets/" + MOD_ID + "/";
	public static final String ASSET_PATH_GUI = "textures/gui/";

	public static final String GUI_FACTORY = "com.bioxx.tfc.Core.Config.TFC_GuiFactory";
}
