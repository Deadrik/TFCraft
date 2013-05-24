package TFC.API;

import TFC.API.Enums.EnumMetalType;


public class Armor 
{
	public static Armor[] armorArray = new Armor[256];
	
	public static Armor CopperPlate = new Armor(0, 300, 300, 200, new int[]{1000, 2000, 1500, 1000}, EnumMetalType.COPPER);//Tier 0
	public static Armor BismuthBronzePlate = new Armor(1, 600, 400, 330, new int[]{2000, 4000, 3000, 2000}, EnumMetalType.BISMUTHBRONZE);//Tier 1
	public static Armor BlackBronzePlate = new Armor(2, 400, 600, 330, new int[]{2000, 4000, 3000, 2000}, EnumMetalType.BLACKBRONZE);//Tier 1
	public static Armor BronzePlate = new Armor(3, 500, 500, 330, new int[]{2000, 4000, 3000, 2000}, EnumMetalType.BRONZE);//Tier 1
	public static Armor WroughtIronPlate = new Armor(4, 800, 800, 528, new int[]{3000, 6000, 4500, 3000}, EnumMetalType.WROUGHTIRON);//Tier 2
	public static Armor SteelPlate = new Armor(5, 1000, 1000, 660, new int[]{4000, 8000, 6000, 4000}, EnumMetalType.STEEL);//Tier 3
	public static Armor BlackSteelPlate = new Armor(6, 2000, 2000, 1320, new int[]{5000, 10000, 7500, 5000}, EnumMetalType.BLACKSTEEL);//Tier 4
	public static Armor BlueSteelPlate = new Armor(7, 3500, 3000, 2000, new int[]{6000, 12000, 9000, 6000}, EnumMetalType.BLUESTEEL);//Tier 5
	public static Armor RedSteelPlate = new Armor(8, 3000, 3500, 2000, new int[]{6000, 12000, 9000, 6000}, EnumMetalType.REDSTEEL);//Tier 5
	
	private int armorRatingPiercing;
	private int armorRatingSlashing;
	private int armorRatingCrushing;
	public EnumMetalType metaltype;
	public int[] baseDurability;
	public int armorId;
	
	
	
	private Armor(int id, int ARP, int ARS, int ARC, int[] dura, EnumMetalType material)
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
