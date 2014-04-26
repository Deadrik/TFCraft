package TFC.API;

public class HeatRaw
{
	public float specificHeat;
	public float meltTemp;

	public HeatRaw(double sh, double melt)
	{
		specificHeat = (float)sh;
		meltTemp = (float)melt;
	}
}
