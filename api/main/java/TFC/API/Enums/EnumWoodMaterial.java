package TFC.API.Enums;

public enum EnumWoodMaterial
{
	ASH("ASH", 696, 25),
	ASPEN("ASPEN", 611, 20),
	BIRCH("BIRCH", 652, 35),
	CHESTNUT("CHESTNUT", 651, 30),
	DOUGLASFIR("DOUGLASFIR", 707, 30),
	HICKORY("HICKORY", 762, 40),
	MAPLE("MAPLE", 745, 40),
	OAK("OAK", 728, 45),
	PINE("PINE", 627, 25),
	REDWOOD("REDWOOD", 612, 35),
	SPRUCE("SPRUCE", 608, 30),
	SYCAMORE("SYCAMORE", 653, 35),
	WHITECEDAR("WHITECEDAR", 625, 30),
	WHITEELM("WHITEELM", 647, 35),
	WILLOW("WILLOW", 603, 25),
	KAPOK("KAPOK", 645, 20),
	PEAT("PEAT", 680, 50),
	ACACIA("ACACIA",650,25);

	public final float burnTimeMax;
	public final float burnTempMax;//degrees celcius


	private static final EnumWoodMaterial Materials[] = new EnumWoodMaterial[] {
		ASH,ASPEN,BIRCH,CHESTNUT,DOUGLASFIR,HICKORY,MAPLE,OAK,PINE,REDWOOD,SPRUCE,
		SYCAMORE,WHITECEDAR,WHITEELM,WILLOW,KAPOK,PEAT,ACACIA};

	private EnumWoodMaterial(String s, float i, float j)
	{
		burnTempMax = i;
		burnTimeMax = j*1.25F*30;
	}

}
