package TFC.Core;

import TFC.Reference;

public class TFC_Sounds 
{
	private static final String LOCATION = Reference.AssetPath + "sounds/";
	private static final String PREFIX = Reference.AssetPathPrefix + "sounds.";
	private static final String LOCATION_MUSIC = Reference.AssetPath + "sounds/m/";
	private static final String PREFIX_MUSIC = Reference.AssetPathPrefix + "sounds.m.";
	
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
			LOCATION + "stonedrag1.ogg",
			LOCATION + "stonedrag2.ogg"};
	
	public static String [] musicFiles = {
		LOCATION_MUSIC + "m0.ogg",
		LOCATION_MUSIC + "m1.ogg",
		LOCATION_MUSIC + "m2.ogg",
		LOCATION_MUSIC + "m3.ogg",
		LOCATION_MUSIC + "m4.ogg"
	};
	
	public static final String FALLININGROCKSHORT = PREFIX + "fRockS";
	public static final String FALLININGROCKLONG = PREFIX + "fRockL";
	public static final String FALLININGDIRTSHORT = PREFIX + "fDirtS";
	public static final String METALIMPACT = PREFIX + "metalimpact";
	public static final String STONEDRAG = PREFIX + "stonedrag";
}
