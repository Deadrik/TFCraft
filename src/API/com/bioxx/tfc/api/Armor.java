package com.bioxx.tfc.api;



public class Armor 
{
	public static Armor[] armorArray = new Armor[256];
	public static Armor Leather = 				new Armor(0, 200, 250, 300, "Leather");//Tier 0
	public static Armor LeatherQuiver = 		new Armor(10, 0, 0, 0, "Leather Quiver");//Tier 0
	public static Armor CopperPlate = 			new Armor(1, 400, 400, 250, "Copper");//Tier 0
	public static Armor BismuthBronzePlate = 	new Armor(2, 600, 400, 330, "Bismuth Bronze");//Tier 1
	public static Armor BlackBronzePlate = 		new Armor(3, 400, 600, 330, "Black Bronze");//Tier 1
	public static Armor BronzePlate = 			new Armor(4, 500, 500, 330, "Bronze");//Tier 1
	public static Armor WroughtIronPlate = 		new Armor(5, 800, 800, 528, "Wrought Iron");//Tier 2
	public static Armor SteelPlate = 			new Armor(6, 1000, 1200, 660, "Steel");//Tier 3
	public static Armor BlackSteelPlate = 		new Armor(7, 2000, 1800, 1320, "Black Steel");//Tier 4
	public static Armor BlueSteelPlate = 		new Armor(8, 2500, 2000, 2000, "Blue Steel");//Tier 5
	public static Armor RedSteelPlate = 		new Armor(9, 2000, 2500, 2000, "Red Steel");//Tier 5

	private int armorRatingPiercing;
	private int armorRatingSlashing;
	private int armorRatingCrushing;
	public String metaltype;
	public int[] baseDurability = new int[] {2500, 3750, 3000, 2500,0};//Helm,Chest,Legs,Boots,back
	public int armorId;

	public Armor(int id, int ARP, int ARS, int ARC, String material)
	{
		armorArray[id] = this;
		armorId = id;
		armorRatingPiercing = ARP;
		armorRatingSlashing = ARS;
		armorRatingCrushing = ARC;
		metaltype = material;
	}

	public Armor(int id, int ARP, int ARS, int ARC, int[] dura, String material)
	{
		armorArray[id] = this;
		armorId = id;
		armorRatingPiercing = ARP;
		armorRatingSlashing = ARS;
		armorRatingCrushing = ARC;
		metaltype = material;
		baseDurability = dura;
	}

	public int getDurability(int slot)
	{
		return baseDurability[slot];
	}

	public int getPiercingAR()
	{
		return armorRatingPiercing;
	}

	public int getSlashingAR()
	{
		return armorRatingSlashing;
	}

	public int getCrushingAR()
	{
		return armorRatingCrushing;
	}
}
