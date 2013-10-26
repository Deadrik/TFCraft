package TFC.API.Enums;

public enum EnumTree
{
	OAK("OAK", /*minRain*/400f, /*maxRain*/16000f, /*minTemp*/4, /*maxTemp*/24, /*minEVT*/0.25f, /*maxEVT*/2, false),
	
	ASPEN("ASPEN", /*minRain*/250f, /*maxRain*/16000f, /*minTemp*/2, /*maxTemp*/24, /*minEVT*/0, /*maxEVT*/1, false),
	
	BIRCH("BIRCH", /*minRain*/250f, /*maxRain*/16000f, /*minTemp*/2, /*maxTemp*/24, /*minEVT*/0, /*maxEVT*/1, false),
	
	CHESTNUT("CHESTNUT", /*minRain*/250f, /*maxRain*/16000f, /*minTemp*/3, /*maxTemp*/24, /*minEVT*/0, /*maxEVT*/1, false),
	
	DOUGLASFIR("DOUGLASFIR", /*minRain*/750f, /*maxRain*/16000f, /*minTemp*/1, /*maxTemp*/24, /*minEVT*/0, /*maxEVT*/1, true),
	
	HICKORY("HICKORY", /*minRain*/250f, /*maxRain*/16000f, /*minTemp*/4, /*maxTemp*/24, /*minEVT*/0, /*maxEVT*/1, false),
	
	KOA("KOA", /*minRain*/500f, /*maxRain*/16000f, /*minTemp*/28, /*maxTemp*/44, /*minEVT*/0, /*maxEVT*/1f, false),
	
	MAPLE("MAPLE", /*minRain*/250f, /*maxRain*/16000f, /*minTemp*/3, /*maxTemp*/20, /*minEVT*/0, /*maxEVT*/1, false),
	
	ASH("ASH", /*minRain*/250f, /*maxRain*/16000f, /*minTemp*/4, /*maxTemp*/24, /*minEVT*/0, /*maxEVT*/2, false),
	
	PINE("PINE", /*minRain*/250f, /*maxRain*/16000f, /*minTemp*/-15, /*maxTemp*/24, /*minEVT*/0, /*maxEVT*/1, true),
	
	REDWOOD("REDWOOD", /*minRain*/1000f, /*maxRain*/16000f, /*minTemp*/10, /*maxTemp*/24, /*minEVT*/0, /*maxEVT*/0.5f, true),
	
	SPRUCE("SPRUCE", /*minRain*/250f, /*maxRain*/16000f, /*minTemp*/-5, /*maxTemp*/24, /*minEVT*/0, /*maxEVT*/1, true),
	
	SYCAMORE("SYCAMORE", /*minRain*/400f, /*maxRain*/16000f, /*minTemp*/6, /*maxTemp*/30, /*minEVT*/0, /*maxEVT*/1, false),
	
	UTACACIA("UTACACIA", /*minRain*/75f, /*maxRain*/1000f, /*minTemp*/20, /*maxTemp*/50, /*minEVT*/0, /*maxEVT*/1, false),
	
	WHITECEDAR("WHITECEDAR", /*minRain*/250f, /*maxRain*/16000f, /*minTemp*/2, /*maxTemp*/24, /*minEVT*/0, /*maxEVT*/1, true),
	
	WHITEELM("WHITEELM", /*minRain*/400f, /*maxRain*/16000f, /*minTemp*/4, /*maxTemp*/30, /*minEVT*/0, /*maxEVT*/1, false),
	
	WILLOW("WILLOW", /*minRain*/1000f, /*maxRain*/16000f, /*minTemp*/6, /*maxTemp*/30, /*minEVT*/0, /*maxEVT*/1, false),
	
	KAPOK("KAPOK", /*minRain*/500f, /*maxRain*/16000f, /*minTemp*/28, /*maxTemp*/44, /*minEVT*/0f, /*maxEVT*/1f, false);

	public final float minRain;
	public final float maxRain;
	public final float minTemp;
	public final float maxTemp;
	public final float minEVT;
	public final float maxEVT;
	public final boolean isEvergreen;


	private static final EnumTree Materials[] = new EnumTree[] {
		OAK,ASPEN,BIRCH,CHESTNUT,DOUGLASFIR,HICKORY,KOA,ASH,MAPLE,PINE,REDWOOD,SPRUCE,
		SYCAMORE,UTACACIA,WHITECEDAR,WHITEELM,WILLOW,KAPOK};

	private EnumTree(String s, float i, float j, float mintemp, float maxtemp, float minevt, float maxevt, boolean e)
	{
		minRain = i;
		maxRain = j;
		minTemp = mintemp;
		maxTemp = maxtemp;
		minEVT = minevt;
		maxEVT = maxevt;
		isEvergreen = e;
	}

}
