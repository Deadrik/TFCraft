package TFC.API.Enums;

public enum EnumWoodMaterial
{
	ASH("ASH", 700, 25),
	ASPEN("ASPEN", 700, 20),
	BIRCH("BIRCH", 700, 35),
	CHESTNUT("CHESTNUT", 700, 30),
	DOUGLASFIR("DOUGLASFIR", 800, 30),
	HICKORY("HICKORY", 800, 40),
	MAPLE("MAPLE", 900, 40),
	OAK("OAK", 900, 45),
	PINE("PINE", 700, 25),
	REDWOOD("REDWOOD", 700, 35),
	SPRUCE("SPRUCE", 700, 30),
	SYCAMORE("SYCAMORE", 700, 35),
	WHITECEDAR("WHITECEDAR", 700, 30),
	WHITEELM("WHITEELM", 700, 35),
	WILLOW("WILLOW", 650, 25),
	KAPOK("KAPOK", 650, 20),
	PEAT("PEAT", 600, 50),
	ACACIA("ACACIA",650,25);

	public final int burnTimeMax;
	public final int burnTempMax;


	private static final EnumWoodMaterial Materials[] = new EnumWoodMaterial[] {
		ASH,ASPEN,BIRCH,CHESTNUT,DOUGLASFIR,HICKORY,MAPLE,OAK,PINE,REDWOOD,SPRUCE,
		SYCAMORE,WHITECEDAR,WHITEELM,WILLOW,KAPOK,PEAT,ACACIA};

	private EnumWoodMaterial(String s, int i, int j)
	{
		burnTempMax = i;
		burnTimeMax = j*40;//*40 instead of 20 because of bellows air decreasing the temp drop.
	}

}
