package TFC.Core;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.src.EntityPlayer;

public class CropManager extends Manager
{
    public static List<CropIndex> Crops;
    
    public CropManager()
    {
        Crops = new ArrayList();
    }
    
    public CropIndex getCropFromName(String n)
    {
        for(CropIndex pi : Crops)
        {
            if(pi.cropName.toLowerCase().equals(n))
                return pi;
        }
        return null;
    } 
    
    static
    {
        Crops.add(new CropIndex("wheat", 0, 120));
        Crops.add(new CropIndex("corn", 0, 125));
    }
}
