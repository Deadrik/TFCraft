package TFC.Enums;


public enum EnumDamageType 
{
	PIERCING(0), 
	SLASHING(1), 
	CRUSHING(2);

	public int MetalID;
	
	private EnumDamageType(int id)
	{
		MetalID = id;
	}
}
