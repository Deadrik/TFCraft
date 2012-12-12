package TFC.Core;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Iterator;
import java.util.List;

public class WoodPrefabManager 
{
	private static final WoodPrefabManager instance = new WoodPrefabManager();
	public static final WoodPrefabManager getInstance()
	{
		return instance;
	}
	
	private List prefabs;
	
	public WoodPrefabManager()
	{
		prefabs = new ArrayList();
	}
	
	public int addPrefab(BitSet incomingData)
	{
		int id = -1;
		Iterator i = prefabs.iterator();
		boolean exists = false;
		while(i.hasNext() && !exists)
		{
			WoodPrefab fab = (WoodPrefab) i.next();
			if(fab.data.equals(incomingData));
			{
				id = fab.prefabID;
				exists = true;
			}
		}
		
		if(!exists)
		{
			id = prefabs.size();
			WoodPrefab w = new WoodPrefab(id, incomingData);
			prefabs.add(w);
		}
		
		return id;
	}
}
