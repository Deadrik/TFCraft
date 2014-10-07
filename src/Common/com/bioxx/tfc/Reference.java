package com.bioxx.tfc;

public class Reference 
{
	public static final String ModID = "terrafirmacraft";
	public static final String ModName = "TerraFirmaCraft";

	public static final int VersionMajor = 0;
	public static final int VersionMinor = 79;
	public static final int VersionRevision = 4;

	public static final String ModVersion = VersionMajor+"."+VersionMinor+"."+VersionRevision;

	public static final String ModDependencies = "required-after:tfc_coremod";
	public static final String ModChannel = "TerraFirmaCraft";
	public static final String SERVER_PROXY_CLASS = "com.bioxx.tfc.CommonProxy";
	public static final String CLIENT_PROXY_CLASS = "com.bioxx.tfc.ClientProxy";

	public static final String AssetPath = "/assets/" + ModID + "/";
	public static final String AssetPathGui = "textures/gui/";
}
