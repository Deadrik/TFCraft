package com.bioxx.tfc.api.Util;

import com.bioxx.tfc.api.Enums.TFCDirection;

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
		return o instanceof CollapseData && ((CollapseData) o).coords.equals(coords);
	}
}
