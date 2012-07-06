package TFC.Core;

public enum EnumWoodMaterial
{
	ASH("ASH", 696, 20),
	ASPEN("ASPEN", 611, 15),
	BIRCH("BIRCH", 652, 30),
	CHESTNUT("CHESTNUT", 651, 25),
	DOUGLASFIR("DOUGLASFIR", 707, 25),
	HICKORY("HICKORY", 762, 35),
	MAPLE("MAPLE", 745, 35),
	OAK("OAK", 728, 40),
	PINE("PINE", 627, 20),
	REDWOOD("REDWOOD", 612, 30),
	SPRUCE("SPRUCE", 608, 25),
	SYCAMORE("SYCAMORE", 653, 30),
	WHITECEDAR("WHITECEDAR", 625, 25),
	WHITEELM("WHITEELM", 647, 30),
	WILLOW("WILLOW", 603, 20),
	KAPOK("KAPOK", 645, 15),
	PEAT("PEAT", 680, 45);

	public final float burnTimeMax;
	public final float burnTempMax;//degrees celcius


	private static final EnumWoodMaterial Materials[] = new EnumWoodMaterial[] {
		ASH,ASPEN,BIRCH,CHESTNUT,DOUGLASFIR,HICKORY,MAPLE,OAK,PINE,REDWOOD,SPRUCE,
		SYCAMORE,WHITECEDAR,WHITEELM,WILLOW,KAPOK,PEAT};

	private EnumWoodMaterial(String s, float i, float j)
	{
		burnTempMax = i;
		burnTimeMax = j*1.25F*30;
	}

}
