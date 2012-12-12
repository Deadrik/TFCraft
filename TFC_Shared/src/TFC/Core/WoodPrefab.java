package TFC.Core;

import java.util.BitSet;

public class WoodPrefab 
{
	public int prefabID;
	public BitSet data;
	
	public WoodPrefab(int i, BitSet s)
	{
		data = s;
		prefabID = i;
	}
}
