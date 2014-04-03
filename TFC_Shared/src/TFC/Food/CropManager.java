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
			if(pi.cropName.toLowerCase().equals(n))
				return pi;
		return null;
	} 

	public CropIndex getCropFromId(int n)
	{
		for(CropIndex pi : Crops)
			if(pi.cropId == n)
				return pi;
		return null;
	} 

	static
	{
		instance.addIndex(new CropIndex(/*ID*/0, /*Name*/"wheat", /*type*/0, /*time*/32, /*stages*/7, /*minGTemp*/4, /*minATemp*/0, /*nutrientUsage*/1.1f, TFCItems.SeedsWheat).setOutput1(TFCItems.WheatWhole, 14.0f));

		instance.addIndex(new CropIndex(/*ID*/2, /*Name*/"maize", /*type*/0, /*time*/36, /*stages*/5, /*minGTemp*/8, /*minATemp*/0, /*nutrientUsage*/1.2f, TFCItems.SeedsMaize).setOutput1(TFCItems.MaizeEar, 32.0f));//Converts to 4-6oz of corn when kernals removed

		instance.addIndex(new CropIndex(/*ID*/4, /*Name*/"tomato", /*type*/0, /*time*/23, /*stages*/7, /*minGTemp*/8, /*minATemp*/0, /*nutrientUsage*/1.2f, TFCItems.SeedsTomato).setWaterUsage(1.2f).setOutput1(TFCItems.Tomato, 16));

		instance.addIndex(new CropIndex(/*ID*/5, /*Name*/"barley", /*type*/0, /*time*/33, /*stages*/7, /*minGTemp*/4, /*minATemp*/0, /*nutrientUsage*/1.1f, TFCItems.SeedsBarley).setOutput1(TFCItems.BarleyWhole, 14.0f));

		instance.addIndex(new CropIndex(/*ID*/7, /*Name*/"rye", /*type*/0, /*time*/32, /*stages*/7, /*minGTemp*/4, /*minATemp*/0, /*nutrientUsage*/1.1f, TFCItems.SeedsRye).setOutput1(TFCItems.RyeWhole, 14.0f));

		instance.addIndex(new CropIndex(/*ID*/9, /*Name*/"oat", /*type*/0, /*time*/32, /*stages*/7, /*minGTemp*/4, /*minATemp*/0, /*nutrientUsage*/1.1f, TFCItems.SeedsOat).setWaterUsage(1.4f).setOutput1(TFCItems.OatWhole, 14.0f));

		instance.addIndex(new CropIndex(/*ID*/11, /*Name*/"rice", /*type*/1, /*time*/32, /*stages*/7, /*minGTemp*/4, /*minATemp*/0, /*nutrientUsage*/1.1f, TFCItems.SeedsRice).setWaterUsage(1.1f).setOutput1(TFCItems.RiceWhole, 14.0f));

		instance.addIndex(new CropIndex(/*ID*/13, /*Name*/"potato", /*type*/2, /*time*/32, /*stages*/6, /*minGTemp*/4, /*minATemp*/0, /*nutrientUsage*/1.1f, TFCItems.SeedsPotato).setOutput1(TFCItems.Potato, 55.0f));

		instance.addIndex(new CropIndex(/*ID*/15, /*Name*/"onion", /*type*/1, /*time*/16, /*stages*/6, /*minGTemp*/8, /*minATemp*/0, /*nutrientUsage*/1.2f, TFCItems.SeedsOnion).setOutput1(TFCItems.Onion, 36.0f).setGoesDormant(true));

		instance.addIndex(new CropIndex(/*ID*/16, /*Name*/"cabbage", /*type*/1, /*time*/29, /*stages*/5, /*minGTemp*/10, /*minATemp*/0, /*nutrientUsage*/0.9f, TFCItems.SeedsCabbage).setWaterUsage(0.9f).setOutput1(TFCItems.Cabbage, 32.0f).setGoesDormant(true));

		instance.addIndex(new CropIndex(/*ID*/17, /*Name*/"garlic", /*type*/2, /*time*/25, /*stages*/4, /*minGTemp*/8, /*minATemp*/0, /*nutrientUsage*/0.5f, TFCItems.SeedsGarlic).setOutput1(TFCItems.Garlic, 20.0f).setGoesDormant(true));

		instance.addIndex(new CropIndex(/*ID*/18, /*Name*/"carrot", /*type*/2, /*time*/23, /*stages*/4, /*minGTemp*/8, /*minATemp*/0, /*nutrientUsage*/0.75f, TFCItems.SeedsCarrot).setOutput1(TFCItems.Carrot, 30.0f).setGoesDormant(true));

		instance.addIndex(new CropIndexPepper(/*ID*/19, /*Name*/"yellowbellpepper", /*type*/2, /*time*/18, /*stages*/6, /*minGTemp*/8, /*minATemp*/0, /*nutrientUsage*/1.2f, TFCItems.SeedsYellowBellPepper).setOutput1(TFCItems.GreenBellPepper, 6).setOutput2(TFCItems.YellowBellPepper, 6));
		instance.addIndex(new CropIndexPepper(/*ID*/20, /*Name*/"redbellpepper", /*type*/2, /*time*/18, /*stages*/6, /*minGTemp*/8, /*minATemp*/0, /*nutrientUsage*/1.2f, TFCItems.SeedsRedBellPepper).setOutput1(TFCItems.GreenBellPepper, 6).setOutput2(TFCItems.RedBellPepper, 6));

		instance.addIndex(new CropIndex(/*ID*/21, /*Name*/"soybean", /*type*/1, /*time*/25, /*stages*/6, /*minGTemp*/8, /*minATemp*/0, /*nutrientUsage*/1.0f, TFCItems.SeedsSoybean, new int[]{10,10,0}).setOutput1(TFCItems.Soybean, 16));

		instance.addIndex(new CropIndex(/*ID*/22, /*Name*/"greenbean", /*type*/1, /*time*/24, /*stages*/6, /*minGTemp*/8, /*minATemp*/0, /*nutrientUsage*/1.0f, TFCItems.SeedsGreenbean, new int[]{10,10,0}).setOutput1(TFCItems.Greenbeans, 16));

		instance.addIndex(new CropIndex(/*ID*/23, /*Name*/"squash", /*type*/2, /*time*/33, /*stages*/6, /*minGTemp*/8, /*minATemp*/0, /*nutrientUsage*/0.9f, TFCItems.SeedsSquash).setOutput1(TFCItems.Squash, 16));
	}
}
