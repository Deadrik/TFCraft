package TFC.API;



public class Armor 
{
	public static Armor[] armorArray = new Armor[256];

	public static Armor CopperPlate = 			new Armor(0, 300, 300, 200, "Copper");//Tier 0
	public static Armor BismuthBronzePlate = 	new Armor(1, 600, 400, 330, "Bismuth Bronze");//Tier 1
	public static Armor BlackBronzePlate = 		new Armor(2, 400, 600, 330, "Black Bronze");//Tier 1
	public static Armor BronzePlate = 			new Armor(3, 500, 500, 330, "Bronze");//Tier 1
	public static Armor WroughtIronPlate = 		new Armor(4, 800, 800, 528, "Wrought Iron");//Tier 2
	public static Armor SteelPlate = 			new Armor(5, 1000, 1200, 660, "Steel");//Tier 3
	public static Armor BlackSteelPlate = 		new Armor(6, 2000, 1800, 1320, "Black Steel");//Tier 4
	public static Armor BlueSteelPlate = 		new Armor(7, 2500, 2000, 2000, "Blue Steel");//Tier 5
	public static Armor RedSteelPlate = 		new Armor(8, 2000, 2500, 2000, "Red Steel");//Tier 5

	private int armorRatingPiercing;
	private int armorRatingSlashing;
	private int armorRatingCrushing;
	public String metaltype;
	public int[] baseDurability = new int[] {2500, 3750, 3000, 2500};//Helm,Chest,Legs,Boots
	public int armorId;

	private Armor(int id, int ARP, int ARS, int ARC, String material)
	{
		armorArray[id] = this;
		armorId = id;
		armorRatingPiercing = ARP;
		armorRatingSlashing = ARS;
		armorRatingCrushing = ARC;
		metaltype = material;
	}

	private Armor(int id, int ARP, int ARS, int ARC, int[] dura, String material)
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
