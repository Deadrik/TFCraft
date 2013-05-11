package TFC.Core;

public class TFC_Sounds 
{
	private static final String LOCATION = "sounds/tfc/";
	private static final String PREFIX = "sounds.tfc.";
	private static final String LOCATION_MUSIC = "sounds/tfc/music/";
	private static final String PREFIX_MUSIC = "sounds.tfc.music.";
	
	public static String [] soundFiles = {
			LOCATION + "fallingrockshort1.ogg",
			LOCATION + "fallingrockshort2.ogg",
			LOCATION + "fallingrocklong1.ogg",
			LOCATION + "fallingrocklong2.ogg",
			LOCATION + "fallingdirtshort1.ogg",
			LOCATION + "fallingdirtshort2.ogg",
			LOCATION + "metalimpact1.ogg",
			LOCATION + "metalimpact2.ogg",
			LOCATION + "metalimpact3.ogg",
			LOCATION + "metalimpact4.ogg",
			LOCATION + "stonedrag1.ogg",
			LOCATION + "stonedrag2.ogg"};
	
	public static String [] musicFiles = {
		LOCATION_MUSIC + "Through the Willows.ogg",
		LOCATION_MUSIC + "FirmaVista.ogg",
		LOCATION_MUSIC + "Sycamore Heights.ogg",
		LOCATION_MUSIC + "Dreams of the Phae.ogg",
		LOCATION_MUSIC + "Terrafirmacraft.ogg"
	};
	
	public static final String FALLININGROCKSHORT = PREFIX + "fallingrockshort";
	public static final String FALLININGROCKLONG = PREFIX + "fallingrocklong";
	public static final String FALLININGDIRTSHORT = PREFIX + "fallingdirtshort";
	public static final String METALIMPACT = PREFIX + "metalimpact";
	public static final String STONEDRAG = PREFIX + "stonedrag";
}
