package com.bioxx.tfc.api.Util;

import java.util.ArrayList;
import java.util.LinkedList;

public class CollapseList<E> extends LinkedList<CollapseData>
{

	public boolean add(ArrayList<ByteCoord> checkedmap, CollapseData e)
	{
		if(this.peekFirst() != null)
		{
			CollapseData first = peekFirst();
			if(first.coords.equals(e.coords) || checkedmap.contains(e))
				return false;
			else
				this.addLast(e);
		}
		return false;
	}
}