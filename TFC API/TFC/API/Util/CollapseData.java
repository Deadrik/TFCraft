package TFC.API.Util;

import TFC.API.Enums.TFCDirection;

public class CollapseData
{
	public ByteCoord coords;
	public float collapseChance;
	public TFCDirection direction;

	public CollapseData(ByteCoord c, float chance, TFCDirection d)
	{
		coords = c;
		collapseChance = chance;
		direction = d;
	}

	@Override
	public boolean equals(Object o)
	{
		if(o instanceof CollapseData && ((CollapseData)o).coords.equals(coords)) 
		{
			return true;
		}

		return false;
	}
}
