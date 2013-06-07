package TFC.API.Enums;


public enum EnumDamageType 
{
	GENERIC(-1), 
	PIERCING(0), 
	SLASHING(1), 
	CRUSHING(2);

	public int DamageID;
	
	private EnumDamageType(int id)
	{
		DamageID = id;
	}
	
	@Override
	public String toString()
	{
		switch(this)
		{
		case PIERCING: return "Piercing";
		case SLASHING: return "Slashing";
		case CRUSHING: return "Crushing";
		default: return "Error: Bad Damage Type";
		}
	}
}
