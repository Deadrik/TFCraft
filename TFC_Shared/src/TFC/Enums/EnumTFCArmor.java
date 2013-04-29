package TFC.Enums;

public enum EnumTFCArmor 
{
	CopperPlate			(200, 200, 200, EnumMetalType.COPPER),//Tier 0
	BismuthBronzePlate	(200, 200, 200, EnumMetalType.BISMUTHBRONZE),//Tier 1
	BlackBronzePlate	(200, 200, 200, EnumMetalType.BLACKBRONZE),//Tier 1
	BronzePlate			(200, 200, 200, EnumMetalType.BRONZE),//Tier 1
	WroughtIronPlate	(200, 200, 200, EnumMetalType.WROUGHTIRON),//Tier 2
	SteelPlate			(200, 200, 200, EnumMetalType.STEEL),//Tier 3
	BlackSteelPlate		(200, 200, 200, EnumMetalType.BLACKSTEEL),//Tier 4
	BlueSteelPlate		(200, 200, 200, EnumMetalType.BLUESTEEL),//Tier 5
	RedSteelPlate		(200, 200, 200, EnumMetalType.REDSTEEL);//Tier 5
	
	private final int armorRatingPiercing;
	private final int armorRatingSlashing;
	private final int armorRatingCrushing;
	private final EnumMetalType metaltype;
	
	private EnumTFCArmor(int ARP,int ARS,int ARC, EnumMetalType material)
	{
		armorRatingPiercing = ARP;
		armorRatingSlashing = ARS;
		armorRatingCrushing = ARC;
		metaltype = material;
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
