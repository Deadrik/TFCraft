package TFC.Food;

import java.util.ArrayList;
import java.util.List;

import TFC.TFCItems;

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
        instance.addIndex(new CropIndex(/*ID*/0, /*Name*/"wheat", /*type*/0, /*time*/120, /*stages*/7, /*minGTemp*/4, /*minATemp*/0, /*nutrientUsage*/1.1f, TFCItems.SeedsWheat).setOutput1(TFCItems.WheatWhole, 1, 1));
           
        instance.addIndex(new CropIndex(/*ID*/2, /*Name*/"maize", /*type*/0, /*time*/135, /*stages*/5, /*minGTemp*/8, /*minATemp*/0, /*nutrientUsage*/1.2f, TFCItems.SeedsMaize).setOutput1(TFCItems.MaizeEar, 1, 3));
     
        instance.addIndex(new CropIndex(/*ID*/4, /*Name*/"tomato", /*type*/0, /*time*/85, /*stages*/7, /*minGTemp*/8, /*minATemp*/0, /*nutrientUsage*/1.2f, TFCItems.SeedsTomato).setWaterUsage(1.2f).setOutput1(TFCItems.Tomato, 1, 4));
        
        instance.addIndex(new CropIndex(/*ID*/5, /*Name*/"barley", /*type*/0, /*time*/125, /*stages*/7, /*minGTemp*/4, /*minATemp*/0, /*nutrientUsage*/1.1f, TFCItems.SeedsBarley).setOutput1(TFCItems.BarleyWhole, 1, 1));
                
        instance.addIndex(new CropIndex(/*ID*/7, /*Name*/"rye", /*type*/0, /*time*/120, /*stages*/7, /*minGTemp*/4, /*minATemp*/0, /*nutrientUsage*/1.1f, TFCItems.SeedsRye).setOutput1(TFCItems.RyeWhole, 1, 1));
            
        instance.addIndex(new CropIndex(/*ID*/9, /*Name*/"oat", /*type*/0, /*time*/120, /*stages*/7, /*minGTemp*/4, /*minATemp*/0, /*nutrientUsage*/1.1f, TFCItems.SeedsOat).setWaterUsage(1.4f).setOutput1(TFCItems.OatWhole, 1, 1));
          
        instance.addIndex(new CropIndex(/*ID*/11, /*Name*/"rice", /*type*/1, /*time*/120, /*stages*/7, /*minGTemp*/4, /*minATemp*/0, /*nutrientUsage*/1.1f, TFCItems.SeedsRice).setWaterUsage(1.1f).setOutput1(TFCItems.RiceWhole, 1, 1));
            
        instance.addIndex(new CropIndex(/*ID*/13, /*Name*/"potato", /*type*/2, /*time*/120, /*stages*/6, /*minGTemp*/4, /*minATemp*/0, /*nutrientUsage*/1.1f, TFCItems.SeedsPotato).setOutput1(TFCItems.Potato, 1, 3));
   
        instance.addIndex(new CropIndex(/*ID*/15, /*Name*/"onion", /*type*/1, /*time*/60, /*stages*/6, /*minGTemp*/8, /*minATemp*/0, /*nutrientUsage*/1.2f, TFCItems.SeedsOnion).setOutput1(TFCItems.Onion, 1, 4).setGoesDormant(true));
        
        instance.addIndex(new CropIndex(/*ID*/16, /*Name*/"cabbage", /*type*/1, /*time*/110, /*stages*/5, /*minGTemp*/10, /*minATemp*/0, /*nutrientUsage*/0.9f, TFCItems.SeedsCabbage).setWaterUsage(0.9f).setOutput1(TFCItems.Cabbage, 1, 1).setGoesDormant(true));
        
        instance.addIndex(new CropIndex(/*ID*/17, /*Name*/"garlic", /*type*/2, /*time*/95, /*stages*/4, /*minGTemp*/8, /*minATemp*/0, /*nutrientUsage*/0.5f, TFCItems.SeedsGarlic).setOutput1(TFCItems.Garlic, 1, 4).setGoesDormant(true));
        
        instance.addIndex(new CropIndex(/*ID*/18, /*Name*/"carrot", /*type*/2, /*time*/90, /*stages*/4, /*minGTemp*/8, /*minATemp*/0, /*nutrientUsage*/0.75f, TFCItems.SeedsCarrot).setOutput1(TFCItems.Carrot, 2, 4).setGoesDormant(true));
        
        instance.addIndex(new CropIndexPepper(/*ID*/19, /*Name*/"yellowbellpepper", /*type*/2, /*time*/70, /*stages*/6, /*minGTemp*/8, /*minATemp*/0, /*nutrientUsage*/1.2f, TFCItems.SeedsYellowBellPepper).setOutput1(TFCItems.GreenBellPepper, 1, 4).setOutput2(TFCItems.YellowBellPepper, 1, 4));
        instance.addIndex(new CropIndexPepper(/*ID*/20, /*Name*/"redbellpepper", /*type*/2, /*time*/70, /*stages*/6, /*minGTemp*/8, /*minATemp*/0, /*nutrientUsage*/1.2f, TFCItems.SeedsRedBellPepper).setOutput1(TFCItems.GreenBellPepper, 1, 4).setOutput2(TFCItems.RedBellPepper, 1, 4));
    
        instance.addIndex(new CropIndex(/*ID*/21, /*Name*/"soybean", /*type*/1, /*time*/95, /*stages*/6, /*minGTemp*/8, /*minATemp*/0, /*nutrientUsage*/1.0f, TFCItems.SeedsSoybean, new int[]{10,10,0}).setOutput1(TFCItems.Soybean, 4, 8));
        
        instance.addIndex(new CropIndex(/*ID*/22, /*Name*/"greenbean", /*type*/1, /*time*/90, /*stages*/6, /*minGTemp*/8, /*minATemp*/0, /*nutrientUsage*/1.0f, TFCItems.SeedsGreenbean, new int[]{10,10,0}).setOutput1(TFCItems.Greenbeans, 4, 8));
        
        instance.addIndex(new CropIndex(/*ID*/23, /*Name*/"squash", /*type*/2, /*time*/125, /*stages*/6, /*minGTemp*/8, /*minATemp*/0, /*nutrientUsage*/0.9f, TFCItems.SeedsSquash).setOutput1(TFCItems.Squash, 1, 4));
    }
}
