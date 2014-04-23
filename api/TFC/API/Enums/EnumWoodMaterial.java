package TFC.API.Enums;

public enum EnumWoodMaterial
{
	ASH("ASH", 696, 1250),
	ASPEN("ASPEN", 611, 1000),
	BIRCH("BIRCH", 652, 1750),
	CHESTNUT("CHESTNUT", 651, 1500),
	DOUGLASFIR("DOUGLASFIR", 707, 1500),
	HICKORY("HICKORY", 762, 2000),
	MAPLE("MAPLE", 745, 2000),
	OAK("OAK", 728, 2250),
	PINE("PINE", 627, 1250),
	REDWOOD("REDWOOD", 612, 1750),
	SPRUCE("SPRUCE", 608, 1500),
	SYCAMORE("SYCAMORE", 653, 1750),
	WHITECEDAR("WHITECEDAR", 625, 1500),
	WHITEELM("WHITEELM", 647, 1750),
	WILLOW("WILLOW", 603, 1000),
	KAPOK("KAPOK", 645, 1000),
	PEAT("PEAT", 680, 2500),
	ACACIA("ACACIA",650,1000);

	public final int burnTimeMax;
	public final int burnTempMax;//degrees celcius


	private static final EnumWoodMaterial Materials[] = new EnumWoodMaterial[] {
		ASH,ASPEN,BIRCH,CHESTNUT,DOUGLASFIR,HICKORY,MAPLE,OAK,PINE,REDWOOD,SPRUCE,
		SYCAMORE,WHITECEDAR,WHITEELM,WILLOW,KAPOK,PEAT,ACACIA};

	private EnumWoodMaterial(String s, int i, int j)
	{
		burnTempMax = i;
		burnTimeMax = j*50;
	}

}
