package TFC.API.Enums;



public enum EnumMetalType 
{
	BISMUTH(0), 
	BISMUTHBRONZE(1), 
	BLACKBRONZE(2), 
	BLACKSTEEL(3), 
	BLUESTEEL(4), 
	BRASS(5), 
	BRONZE(6), 
	COPPER(7), 
	GOLD(8), 
	WROUGHTIRON(9), 
	LEAD(10), 
	NICKEL(11), 
	PIGIRON(12), 
	PLATINUM(13), 
	REDSTEEL(14), 
	ROSEGOLD(15), 
	SILVER(16), 
	STEEL(17), 
	STERLINGSILVER(18), 
	TIN(19), 
	ZINC(20);

	public int MetalID;
	
	private EnumMetalType(int id)
	{
		MetalID = id;
	}
}
