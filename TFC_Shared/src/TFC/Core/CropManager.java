package TFC.Core;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.TFCItems;

public class CropManager
{
    public List<CropIndex> Crops;
    
    protected static final CropManager instance = new CropManager();
    
    public static final CropManager getInstance()
    {
        return instance;
    }
    
    public CropManager()
    {
        Crops = new ArrayList();
    }
    
    public void addIndex(CropIndex index)
    {
        Crops.add(index);
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
    
    public CropIndex getCropFromId(int n)
    {
        for(CropIndex pi : Crops)
        {
            if(pi.cropId == n)
                return pi;
        }
        return null;
    } 
    
    static
    {
        instance.addIndex(new CropIndex(/*ID*/0, /*Name*/"wheat", /*type*/0, /*time*/130, /*stages*/7, /*minGTemp*/4, /*minATemp*/-4, /*nutrientUsage*/1.1f).setOutput1(Item.wheat, 1, 2));
        
        instance.addIndex(new CropIndex(/*ID*/1, /*Name*/"wild wheat", /*type*/0, /*time*/130, /*stages*/7, /*minGTemp*/8, /*minATemp*/-4, /*nutrientUsage*/1.0f).setOutput1(Item.wheat, 1, 2).setOutput2Chance(TFCItems.SeedsWildWheat, 1, 2, 5));
        
        instance.addIndex(new CropIndex(/*ID*/2, /*Name*/"corn", /*type*/0, /*time*/135, /*stages*/7, /*minGTemp*/8, /*minATemp*/-4, /*nutrientUsage*/1.2f, 
                /*out1Item*/Item.wheat, /*out1Min*/1, /*out1Max*/2, /*out2Item*/TFCItems.SeedsCorn, /*out2Min*/1, /*out2Max*/3));
        
        instance.addIndex(new CropIndex(/*ID*/3, /*Name*/"tomatoes", /*type*/0, /*time*/60, /*stages*/7, /*minGTemp*/8, /*minATemp*/-4, /*nutrientUsage*/1.2f, 
                /*out1Item*/Item.wheat, /*out1Min*/1, /*out1Max*/2, /*out2Item*/TFCItems.SeedsTomato, /*out2Min*/1, /*out2Max*/3));
    }
}
