package TFC.Core;

import TFC.Reference;

public class TFC_Sounds 
{
	private static final String LOCATION = Reference.ModID + ":";
	private static final String LOCATION_MUSIC = Reference.AssetPath + "sound/m/";

	public static String [] soundFiles = {
		LOCATION + "fRockS1.ogg",
		LOCATION + "fRockS2.ogg",
		LOCATION + "fRockL1.ogg",
		LOCATION + "fRockL2.ogg",
		LOCATION + "fDirtS1.ogg",
		LOCATION + "fDirtS2.ogg",
		LOCATION + "metalimpact1.ogg",
		LOCATION + "metalimpact2.ogg",
		LOCATION + "metalimpact3.ogg",
		LOCATION + "metalimpact4.ogg",
		LOCATION + "rooster1.ogg",
		LOCATION + "stonedrag1.ogg",
		LOCATION + "stonedrag2.ogg",
		LOCATION + "bellows1.ogg",
		LOCATION + "ceramicbreak1.ogg"};

	public static String [] musicFiles = {
		LOCATION_MUSIC + "m0.ogg",
		LOCATION_MUSIC + "m1.ogg",
		LOCATION_MUSIC + "m2.ogg",
		LOCATION_MUSIC + "m3.ogg",
		LOCATION_MUSIC + "m4.ogg"
	};

	public static final String FALLININGROCKSHORT = LOCATION + "fRockS";
	public static final String FALLININGROCKLONG = LOCATION + "fRockL";
	public static final String FALLININGDIRTSHORT = LOCATION + "fDirtS";
	public static final String METALIMPACT = LOCATION + "metalimpact";
	public static final String STONEDRAG = LOCATION + "stonedrag";
	public static final String ROOSTERCROW = LOCATION + "rooster";
	public static final String BELLOWS = LOCATION + "bellows";
	public static final String CERAMICBREAK = LOCATION + "ceramicbreak";
}
