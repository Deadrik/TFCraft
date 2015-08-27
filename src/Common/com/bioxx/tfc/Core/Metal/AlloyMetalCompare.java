package com.bioxx.tfc.Core.Metal;

import com.bioxx.tfc.api.Metal;

public class AlloyMetalCompare extends AlloyMetal
{
	private float metalMin;
	private float metalMax;

	public AlloyMetalCompare(Metal e, float min)
	{
		super(e, min);
	}

	public AlloyMetalCompare(Metal e, float min, float max)
	{
		super(e, min);
		metalType = e;
		metalMin = min;
		metalMax = max;
	}

	public boolean compare(AlloyMetal b)
	{
		//	Multiplying all the values by 1000 and then rounding to the
		//	nearest int effectively rounds the float values to 3 decimal
		//	places. We do this to avoid rounding errors in the further
		//	decimal places.
		//
		return this.metalType == b.metalType &&	(b.metal == -1 ||
				Math.round(b.metal * 1000f) >= Math.round(this.metalMin * 1000f) && Math.round(b.metal * 1000f) <= Math.round(this.metalMax * 1000f));
	}

    public float getMetalMin()
    {
        return metalMin;
    }

    public float getMetalMax()
    {
        return metalMax;
    }
}
