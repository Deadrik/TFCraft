package TFC.API.Constant;

import java.util.Arrays;

import TFC.API.Metal;

import com.google.common.collect.ObjectArrays;

public class Global
{
	/* Stone Types */
	public static final String[] STONE_IGIN = {"Granite", "Diorite", "Gabbro"};
	public static final String[] STONE_SED  = {"Siltstone", "Mudstone", "Shale", "Claystone", "Rock Salt", "Limestone", "Conglomerate", "Dolomite", "Chert", "Chalk"};
	public static final String[] STONE_IGEX = {"Rhyolite", "Basalt", "Andesite", "Dacite"};
	public static final String[] STONE_MM   = {"Quartzite", "Slate", "Phyllite", "Schist", "Gneiss", "Marble"};
	
	// Used for loose rocks and other places where the stone list is combined
	public static final int STONE_IGIN_START = 0;
	public static final int STONE_SED_START = STONE_IGIN_START + STONE_IGIN.length;
	public static final int STONE_IGEX_START = STONE_SED_START + STONE_SED.length;
	public static final int STONE_MM_START = STONE_IGEX_START + STONE_IGEX.length;
	public static final String[] STONE_ALL  = ObjectArrays.concat(ObjectArrays.concat(STONE_IGIN, STONE_SED, String.class), ObjectArrays.concat(STONE_IGEX, STONE_MM, String.class), String.class);

	// Stones that can be turned into flux
	public static final int[] STONE_FLUXINDEX = {
		Arrays.asList(STONE_ALL).indexOf("Limestone"),
		Arrays.asList(STONE_ALL).indexOf("Dolomite"),
		Arrays.asList(STONE_ALL).indexOf("Chalk"),
		Arrays.asList(STONE_ALL).indexOf("Marble")
	};
	
	/* Ore Types */
	public static final String[] ORE_METAL = {
		"Native Copper", "Native Gold", "Native Platinum", "Hematite",
		"Native Silver", "Cassiterite", "Galena", "Bismuthinite",
		"Garnierite", "Malachite", "Magnetite", "Limonite",
		"Sphalerite", "Tetrahedrite", "Bituminous Coal", "Lignite"
		};
	public static final String[] ORE_MINERAL = {
		"Kaolinite", "Gypsum", "Satinspar", "Selenite",
		"Graphite", "Kimberlite", "Petrified Wood", "Sulfur",
		"Jet", "Microcline", "Pitchblende", "Cinnabar",
		"Cryolite", "Saltpeter", "Serpentine", "Sylvite"
		};
	public static final String[] ORE_MINERAL2 = {"Borax", "Olivine", "Lapis Lazuli"};
	
	public static final String[] WOOD_ALL = {
		"Oak","Aspen","Birch","Chestnut",
		"Douglas Fir","Hickory","Maple","Ash",
		"Pine","Sequoia","Spruce","Sycamore",
		"White Cedar","White Elm","Willow","Kapok"
		};
	
	
	public static Metal BISMUTH;
	public static Metal BISMUTHBRONZE;
	public static Metal BLACKBRONZE; 
	public static Metal BLACKSTEEL; 
	public static Metal BLUESTEEL; 
	public static Metal BRASS; 
	public static Metal BRONZE; 
	public static Metal COPPER; 
	public static Metal GOLD; 
	public static Metal WROUGHTIRON; 
	public static Metal LEAD; 
	public static Metal NICKEL; 
	public static Metal PIGIRON; 
	public static Metal PLATINUM; 
	public static Metal REDSTEEL; 
	public static Metal ROSEGOLD; 
	public static Metal SILVER; 
	public static Metal STEEL; 
	public static Metal STERLINGSILVER; 
	public static Metal TIN; 
	public static Metal ZINC;
	public static Metal WEAKSTEEL; 
	public static Metal HCBLACKSTEEL; 
	public static Metal WEAKREDSTEEL; 
	public static Metal HCREDSTEEL; 
	public static Metal WEAKBLUESTEEL; 
	public static Metal HCBLUESTEEL; 
	public static Metal UNKNOWN; 
}
