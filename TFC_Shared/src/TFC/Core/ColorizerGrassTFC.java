package TFC.Core;

public class ColorizerGrassTFC
{
    /** Color buffer for grass */
    private static int[] grassBuffer = new int[65536];

    public static void setGrassBiomeColorizer(int[] par0ArrayOfInteger)
    {
        grassBuffer = par0ArrayOfInteger;
    }

    /**
     * Gets grass color from temperature and humidity. Args: temperature, humidity
     */
    public static int getGrassColor(double par0, double par2)
    {
        par2 *= par0;
        int var4 = (int)((1.0D - par0) * 255.0D);
        int var5 = (int)((1.0D - par2) * 255.0D);
        return grassBuffer[var5 << 8 | var4];
    }
    
    public static int getGrassDead()
    {
        return 5198608;
    }
}
