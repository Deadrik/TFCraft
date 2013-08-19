package TFC.API.Enums;

import TFC.API.Util.StringUtil;

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
		case PIERCING: return StringUtil.localize("gui.DamageType.Piercing");
		case SLASHING: return StringUtil.localize("gui.DamageType.Slashing");
		case CRUSHING: return StringUtil.localize("gui.DamageType.Crushing");
		default: return StringUtil.localize("gui.DamageType.Error");
		}
	}
}
