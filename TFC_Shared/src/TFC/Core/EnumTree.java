package TFC.Core;

public enum EnumTree
{
	OAK("OAK", /*minRain*/400f, /*maxRain*/16000f, /*minTemp*/3, /*maxTemp*/24, /*minEVT*/0.25f, /*maxEVT*/2, false),
	
	ASPEN("ASPEN", /*minRain*/250f, /*maxRain*/16000f, /*minTemp*/3, /*maxTemp*/24, /*minEVT*/0, /*maxEVT*/1, false),
	
	BIRCH("BIRCH", /*minRain*/250f, /*maxRain*/16000f, /*minTemp*/3, /*maxTemp*/24, /*minEVT*/0, /*maxEVT*/1, false),
	
	CHESTNUT("CHESTNUT", /*minRain*/250f, /*maxRain*/16000f, /*minTemp*/3, /*maxTemp*/18, /*minEVT*/0, /*maxEVT*/1, false),
	
	DOUGLASFIR("DOUGLASFIR", /*minRain*/250f, /*maxRain*/16000f, /*minTemp*/3, /*maxTemp*/18, /*minEVT*/0, /*maxEVT*/1, false),
	
	HICKORY("HICKORY", /*minRain*/250f, /*maxRain*/16000f, /*minTemp*/3, /*maxTemp*/18, /*minEVT*/0, /*maxEVT*/1, false),
	
	MAPLE("MAPLE", /*minRain*/250f, /*maxRain*/16000f, /*minTemp*/3, /*maxTemp*/12, /*minEVT*/0, /*maxEVT*/1, false),
	
	ASH("ASH", /*minRain*/250f, /*maxRain*/16000f, /*minTemp*/3, /*maxTemp*/24, /*minEVT*/0, /*maxEVT*/2, false),
	
	PINE("PINE", /*minRain*/250f, /*maxRain*/16000f, /*minTemp*/1, /*maxTemp*/18, /*minEVT*/0, /*maxEVT*/1, true),
	
	REDWOOD("REDWOOD", /*minRain*/850f, /*maxRain*/16000f, /*minTemp*/3, /*maxTemp*/18, /*minEVT*/0, /*maxEVT*/1, true),
	
	SPRUCE("SPRUCE", /*minRain*/250f, /*maxRain*/16000f, /*minTemp*/1, /*maxTemp*/18, /*minEVT*/0, /*maxEVT*/1, true),
	
	SYCAMORE("SYCAMORE", /*minRain*/400f, /*maxRain*/16000f, /*minTemp*/6, /*maxTemp*/24, /*minEVT*/0, /*maxEVT*/1, false),
	
	WHITECEDAR("WHITECEDAR", /*minRain*/250f, /*maxRain*/16000f, /*minTemp*/1, /*maxTemp*/18, /*minEVT*/0, /*maxEVT*/1, true),
	
	WHITEELM("WHITEELM", /*minRain*/400f, /*maxRain*/16000f, /*minTemp*/3, /*maxTemp*/24, /*minEVT*/0, /*maxEVT*/1, false),
	
	WILLOW("WILLOW", /*minRain*/1000f, /*maxRain*/16000f, /*minTemp*/6, /*maxTemp*/24, /*minEVT*/0, /*maxEVT*/1, false),
	
	KAPOK("KAPOK", /*minRain*/4000f, /*maxRain*/16000f, /*minTemp*/15, /*maxTemp*/30, /*minEVT*/0f, /*maxEVT*/0.45f, false);

	public final float minRain;
	public final float maxRain;
	public final float minTemp;
	public final float maxTemp;
	public final float minEVT;
	public final float maxEVT;
	public final boolean isEvergreen;


	private static final EnumTree Materials[] = new EnumTree[] {
		OAK,ASPEN,BIRCH,CHESTNUT,DOUGLASFIR,HICKORY,ASH,MAPLE,PINE,REDWOOD,SPRUCE,
		SYCAMORE,WHITECEDAR,WHITEELM,WILLOW,KAPOK};

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
