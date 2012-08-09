package TFC.Core;

public class CropIndex
{
    private int growthTime;
    public String cropName;
    private int cycleType;
    
    public CropIndex(String name, int type, int growth)
    {
        growthTime = growth;
        cycleType = type;
        cropName = name.toLowerCase();
    }
}
