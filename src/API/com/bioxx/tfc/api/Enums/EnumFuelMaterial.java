package com.bioxx.tfc.api.Enums;

public enum EnumFuelMaterial
{
	ASH("ASH", 696, 1250, 				new int[]{/*Sweet*/-15,/*Sour*/12,/*Salty*/0,/*Bitter*/0,/*Savory*/0}),
	ASPEN("ASPEN", 611, 1000, 			new int[]{/*Sweet*/0,/*Sour*/-11,/*Salty*/0,/*Bitter*/12,/*Savory*/5}),
	BIRCH("BIRCH", 652, 1750, 			new int[]{/*Sweet*/-4,/*Sour*/-4,/*Salty*/0,/*Bitter*/3,/*Savory*/0}),
	CHESTNUT("CHESTNUT", 651, 1500, 	new int[]{/*Sweet*/6,/*Sour*/-5,/*Salty*/0,/*Bitter*/-6,/*Savory*/-5}),
	DOUGLASFIR("DOUGLASFIR", 707, 1500, new int[]{/*Sweet*/-14,/*Sour*/14,/*Salty*/0,/*Bitter*/18,/*Savory*/-9}),
	HICKORY("HICKORY", 762, 2000, 		new int[]{/*Sweet*/-5,/*Sour*/-6,/*Salty*/0,/*Bitter*/-8,/*Savory*/13}),
	MAPLE("MAPLE", 745, 2000, 			new int[]{/*Sweet*/8,/*Sour*/-4,/*Salty*/0,/*Bitter*/-5,/*Savory*/5}),
	OAK("OAK", 728, 2250, 				new int[]{/*Sweet*/0,/*Sour*/-8,/*Salty*/0,/*Bitter*/-8,/*Savory*/12}),
	PINE("PINE", 627, 1250, 			new int[]{/*Sweet*/-19,/*Sour*/23,/*Salty*/0,/*Bitter*/21,/*Savory*/-19}),
	REDWOOD("REDWOOD", 612, 1750, 		new int[]{/*Sweet*/-13,/*Sour*/9,/*Salty*/0,/*Bitter*/12,/*Savory*/6}),
	SPRUCE("SPRUCE", 608, 1500, 		new int[]{/*Sweet*/-17,/*Sour*/16,/*Salty*/0,/*Bitter*/-9,/*Savory*/-13}),
	SYCAMORE("SYCAMORE", 653, 1750, 	new int[]{/*Sweet*/8,/*Sour*/15,/*Salty*/0,/*Bitter*/17,/*Savory*/-6}),
	WHITECEDAR("WHITECEDAR", 625, 1500, new int[]{/*Sweet*/-5,/*Sour*/9,/*Salty*/0,/*Bitter*/16,/*Savory*/-3}),
	WHITEELM("WHITEELM", 647, 1750, 	new int[]{/*Sweet*/0,/*Sour*/0,/*Salty*/0,/*Bitter*/0,/*Savory*/0}),
	WILLOW("WILLOW", 603, 1000, 		new int[]{/*Sweet*/-4,/*Sour*/-6,/*Salty*/0,/*Bitter*/9,/*Savory*/-2}),
	KAPOK("KAPOK", 645, 1000, 			new int[]{/*Sweet*/7,/*Sour*/0,/*Salty*/0,/*Bitter*/-7,/*Savory*/0}),
	PEAT("PEAT", 680, 2500, 			new int[]{/*Sweet*/-10,/*Sour*/0,/*Salty*/0,/*Bitter*/10,/*Savory*/0}),
	ACACIA("ACACIA",650, 1000, 			new int[]{/*Sweet*/6,/*Sour*/6,/*Salty*/0,/*Bitter*/9,/*Savory*/-6}),
	CHARCOAL("CHARCOAL", 1350, 1800, 	new int[]{/*Sweet*/-10,/*Sour*/8,/*Salty*/0,/*Bitter*/4,/*Savory*/15}),
	COAL("COAL", 1400, 2200, 			new int[]{/*Sweet*/-18,/*Sour*/13,/*Salty*/0,/*Bitter*/20,/*Savory*/12});

	public final int burnTimeMax;
	public final int burnTempMax;//degrees celcius
	public final int[] tasteProfile;

	private EnumFuelMaterial(String s, int i, int j, int[] taste)
	{
		burnTempMax = i;
		burnTimeMax = j;
		tasteProfile = taste.clone();
	}

	public static int[] getFuelProfile(int ordinal)
	{
		if(ordinal < 0 || ordinal > values().length)
			return new int[] {0,0,0,0,0};
		return values()[ordinal].tasteProfile;
	}
}
