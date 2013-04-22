package TFC.Enums;

public enum EnumTFCArmor 
{
	CopperPlate			(200, 200, 200, EnumMetalType.COPPER),
	BismuthBronzePlate	(200, 200, 200, EnumMetalType.BISMUTHBRONZE),
	BlackBronzePlate	(200, 200, 200, EnumMetalType.BLACKBRONZE),
	BronzePlate			(200, 200, 200, EnumMetalType.BRONZE),
	WroughtIronPlate	(200, 200, 200, EnumMetalType.WROUGHTIRON),
	SteelPlate			(200, 200, 200, EnumMetalType.STEEL),
	RoseGoldPlate		(200, 200, 200, EnumMetalType.ROSEGOLD),
	BlackSteelPlate		(200, 200, 200, EnumMetalType.BLACKSTEEL),
	BlueSteelPlate		(200, 200, 200, EnumMetalType.BLUESTEEL),
	RedSteelPlate		(200, 200, 200, EnumMetalType.REDSTEEL);
	
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
