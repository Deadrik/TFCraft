package TFC;

public class Reference 
{
	public static final String ModID = "terrafirmacraft";
	public static final String ModName = "TerraFirmaCraft";

	public static final int VersionMajor = 0;
	public static final int VersionMinor = 77;
	public static final int VersionRevision = 15;

	public static final String ModVersion = VersionMajor+"."+VersionMinor+"."+VersionRevision;

	public static final String ModDependencies = "required-after:Forge@[7.0,);required-after:FML@[5.0.5,)";
	public static final String ModChannel = "TerraFirmaCraft";
	public static final String SERVER_PROXY_CLASS = "TFC.CommonProxy";
	public static final String CLIENT_PROXY_CLASS = "TFC.ClientProxy";

	public static final String AssetPath = "/assets/" + ModID + "/";
	public static final String AssetPathGui = "textures/gui/";
}
