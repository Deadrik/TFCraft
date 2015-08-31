package com.bioxx.tfc.api.Util;

import java.util.LinkedList;
import java.util.List;

@SuppressWarnings("serial")
public class CollapseList<E> extends LinkedList<CollapseData>
{
	public boolean add(List<ByteCoord> checkedmap, CollapseData e)
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