package com.bioxx.tfc.api.Enums;

public enum EnumDamageType 
{
	GENERIC(-1), 
	PIERCING(0), 
	SLASHING(1), 
	CRUSHING(2);

	public int damageID;

	private EnumDamageType(int id)
	{
		damageID = id;
	}

	@Override
	public String toString()
	{
		switch(this)
		{
		case PIERCING: return "gui.DamageType.Piercing";
		case SLASHING: return "gui.DamageType.Slashing";
		case CRUSHING: return "gui.DamageType.Crushing";
		default: return "gui.DamageType.Error";
		}
	}
}
