package TFC.Core;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;

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
        instance.addIndex(new CropIndex(/*ID*/0, /*Name*/"wheat", /*type*/0, /*time*/120, /*stages*/7, /*minGTemp*/4, /*minATemp*/-4, /*nutrientUsage*/1.1f).setOutput1(TFCItems.WheatWhole, 1, 1));
        
        instance.addIndex(new CropIndex(/*ID*/1, /*Name*/"wild wheat", /*type*/0, /*time*/130, /*stages*/7, /*minGTemp*/8, /*minATemp*/-4, /*nutrientUsage*/1.0f).setOutput1(TFCItems.WildWheatWhole, 1, 1));
        
        instance.addIndex(new CropIndex(/*ID*/2, /*Name*/"maize", /*type*/0, /*time*/135, /*stages*/5, /*minGTemp*/8, /*minATemp*/-4, /*nutrientUsage*/1.2f).setOutput1(TFCItems.MaizeEar, 1, 3));
        
        instance.addIndex(new CropIndex(/*ID*/3, /*Name*/"wild maize", /*type*/0, /*time*/135, /*stages*/5, /*minGTemp*/8, /*minATemp*/-4, /*nutrientUsage*/1.2f).setOutput1(TFCItems.WildMaizeEar, 1, 3));
        
        instance.addIndex(new CropIndex(/*ID*/4, /*Name*/"tomato", /*type*/0, /*time*/85, /*stages*/7, /*minGTemp*/8, /*minATemp*/-4, /*nutrientUsage*/1.2f).setWaterUsage(1.2f).setOutput1(TFCItems.Tomato, 1, 4));
        
        instance.addIndex(new CropIndex(/*ID*/5, /*Name*/"barley", /*type*/0, /*time*/125, /*stages*/7, /*minGTemp*/4, /*minATemp*/-4, /*nutrientUsage*/1.1f).setOutput1(TFCItems.BarleyWhole, 1, 1));
        
        instance.addIndex(new CropIndex(/*ID*/6, /*Name*/"wild barley", /*type*/0, /*time*/135, /*stages*/7, /*minGTemp*/8, /*minATemp*/-4, /*nutrientUsage*/1.0f).setOutput1(TFCItems.WildBarleyWhole, 1, 1));
        
        instance.addIndex(new CropIndex(/*ID*/7, /*Name*/"rye", /*type*/0, /*time*/120, /*stages*/7, /*minGTemp*/4, /*minATemp*/-4, /*nutrientUsage*/1.1f).setOutput1(TFCItems.RyeWhole, 1, 1));
        
        instance.addIndex(new CropIndex(/*ID*/8, /*Name*/"wild rye", /*type*/0, /*time*/135, /*stages*/7, /*minGTemp*/8, /*minATemp*/-4, /*nutrientUsage*/1.0f).setOutput1(TFCItems.WildRyeWhole, 1, 1));
        
        instance.addIndex(new CropIndex(/*ID*/9, /*Name*/"oat", /*type*/0, /*time*/120, /*stages*/7, /*minGTemp*/4, /*minATemp*/-4, /*nutrientUsage*/1.1f).setWaterUsage(1.4f).setOutput1(TFCItems.OatWhole, 1, 1));
        
        instance.addIndex(new CropIndex(/*ID*/10, /*Name*/"wild oat", /*type*/0, /*time*/135, /*stages*/7, /*minGTemp*/8, /*minATemp*/-4, /*nutrientUsage*/1.0f).setWaterUsage(1.3f).setOutput1(TFCItems.WildOatWhole, 1, 1));
        
        instance.addIndex(new CropIndex(/*ID*/11, /*Name*/"rice", /*type*/1, /*time*/120, /*stages*/7, /*minGTemp*/4, /*minATemp*/-4, /*nutrientUsage*/1.1f).setWaterUsage(1.1f).setOutput1(TFCItems.RiceWhole, 1, 1));
        
        instance.addIndex(new CropIndex(/*ID*/12, /*Name*/"wild rice", /*type*/1, /*time*/135, /*stages*/7, /*minGTemp*/8, /*minATemp*/-4, /*nutrientUsage*/1.0f).setWaterUsage(1.1f).setOutput1(TFCItems.WildRiceWhole, 1, 1));
        
        instance.addIndex(new CropIndex(/*ID*/13, /*Name*/"potato", /*type*/2, /*time*/120, /*stages*/6, /*minGTemp*/4, /*minATemp*/-4, /*nutrientUsage*/1.1f).setOutput1(TFCItems.Potato, 1, 3));
        
        instance.addIndex(new CropIndex(/*ID*/14, /*Name*/"wild potato", /*type*/2, /*time*/135, /*stages*/6, /*minGTemp*/8, /*minATemp*/-4, /*nutrientUsage*/1.0f).setOutput1(TFCItems.WildPotato, 1, 2));
        
        instance.addIndex(new CropIndex(/*ID*/15, /*Name*/"onion", /*type*/1, /*time*/60, /*stages*/6, /*minGTemp*/8, /*minATemp*/-4, /*nutrientUsage*/1.2f).setOutput1(TFCItems.Onion, 1, 4).setGoesDormant(true));
        
        instance.addIndex(new CropIndex(/*ID*/16, /*Name*/"cabbage", /*type*/1, /*time*/110, /*stages*/5, /*minGTemp*/10, /*minATemp*/-4, /*nutrientUsage*/1.0f).setWaterUsage(0.9f).setOutput1(TFCItems.Cabbage, 1, 1));
        
        instance.addIndex(new CropIndex(/*ID*/17, /*Name*/"garlic", /*type*/2, /*time*/95, /*stages*/4, /*minGTemp*/8, /*minATemp*/-4, /*nutrientUsage*/1.0f).setOutput1(TFCItems.Garlic, 1, 2).setGoesDormant(true));
        
        instance.addIndex(new CropIndex(/*ID*/18, /*Name*/"carrot", /*type*/2, /*time*/90, /*stages*/4, /*minGTemp*/8, /*minATemp*/-4, /*nutrientUsage*/1.0f).setOutput1(TFCItems.Carrot, 2, 4).setGoesDormant(true));
    }
}
