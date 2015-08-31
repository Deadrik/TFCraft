package com.bioxx.tfc.Food;

import java.util.ArrayList;
import java.util.List;

import com.bioxx.tfc.api.TFCItems;

public class CropManager
{
	public List<CropIndex> crops;

	protected static final CropManager INSTANCE = new CropManager();

	public static final CropManager getInstance()
	{
		return INSTANCE;
	}

	public CropManager()
	{
		crops = new ArrayList<CropIndex>();
	}

	public void addIndex(CropIndex index)
	{
		crops.add(index);
	}

	public int getTotalCrops()
	{
		return crops.size();
	}

	public CropIndex getCropFromName(String n)
	{
		for(CropIndex pi : crops)
			if (pi.cropName.equalsIgnoreCase(n))
				return pi;
		return null;
	} 

	public CropIndex getCropFromId(int n)
	{
		for(CropIndex pi : crops)
			if(pi.cropId == n)
				return pi;
		return null;
	} 

	static
	{
		INSTANCE.addIndex(new CropIndex(/*ID*/0, /*Name*/"wheat", /*type*/0, /*time*/32, /*stages*/7, /*minGTemp*/4, /*minATemp*/0, /*nutrientUsage*/0.9f, TFCItems.seedsWheat).setOutput1(TFCItems.wheatWhole, 14.0f));

		INSTANCE.addIndex(new CropIndex(/*ID*/1, /*Name*/"maize", /*type*/0, /*time*/36, /*stages*/5, /*minGTemp*/8, /*minATemp*/0, /*nutrientUsage*/0.8f, TFCItems.seedsMaize).setOutput1(TFCItems.maizeEar, 32.0f));//Converts to 4-6oz of corn when kernals removed

		INSTANCE.addIndex(new CropIndex(/*ID*/2, /*Name*/"tomato", /*type*/0, /*time*/23, /*stages*/7, /*minGTemp*/8, /*minATemp*/0, /*nutrientUsage*/1.2f, TFCItems.seedsTomato).setWaterUsage(1.2f).setOutput1(TFCItems.tomato, 16));

		INSTANCE.addIndex(new CropIndex(/*ID*/3, /*Name*/"barley", /*type*/0, /*time*/33, /*stages*/7, /*minGTemp*/4, /*minATemp*/0, /*nutrientUsage*/0.85f, TFCItems.seedsBarley).setOutput1(TFCItems.barleyWhole, 14.0f));

		INSTANCE.addIndex(new CropIndex(/*ID*/4, /*Name*/"rye", /*type*/0, /*time*/32, /*stages*/7, /*minGTemp*/4, /*minATemp*/0, /*nutrientUsage*/0.9f, TFCItems.seedsRye).setOutput1(TFCItems.ryeWhole, 14.0f));

		INSTANCE.addIndex(new CropIndex(/*ID*/5, /*Name*/"oat", /*type*/0, /*time*/32, /*stages*/7, /*minGTemp*/4, /*minATemp*/0, /*nutrientUsage*/0.9f, TFCItems.seedsOat).setWaterUsage(1.4f).setOutput1(TFCItems.oatWhole, 14.0f));

		INSTANCE.addIndex(new CropIndex(/*ID*/6, /*Name*/"rice", /*type*/1, /*time*/32, /*stages*/7, /*minGTemp*/4, /*minATemp*/0, /*nutrientUsage*/0.9f, TFCItems.seedsRice).setWaterUsage(1.1f).setOutput1(TFCItems.riceWhole, 14.0f));

		INSTANCE.addIndex(new CropIndex(/*ID*/7, /*Name*/"potato", /*type*/2, /*time*/32, /*stages*/6, /*minGTemp*/4, /*minATemp*/0, /*nutrientUsage*/0.9f, TFCItems.seedsPotato).setOutput1(TFCItems.potato, 55.0f));

		INSTANCE.addIndex(new CropIndex(/*ID*/8, /*Name*/"onion", /*type*/1, /*time*/16, /*stages*/6, /*minGTemp*/8, /*minATemp*/0, /*nutrientUsage*/1.2f, TFCItems.seedsOnion).setOutput1(TFCItems.onion, 36.0f).setGoesDormant(true));

		INSTANCE.addIndex(new CropIndex(/*ID*/9, /*Name*/"cabbage", /*type*/1, /*time*/29, /*stages*/5, /*minGTemp*/10, /*minATemp*/0, /*nutrientUsage*/0.9f, TFCItems.seedsCabbage).setWaterUsage(0.9f).setOutput1(TFCItems.cabbage, 32.0f).setGoesDormant(true));

		INSTANCE.addIndex(new CropIndex(/*ID*/10, /*Name*/"garlic", /*type*/2, /*time*/25, /*stages*/4, /*minGTemp*/8, /*minATemp*/0, /*nutrientUsage*/0.5f, TFCItems.seedsGarlic).setOutput1(TFCItems.garlic, 20.0f).setGoesDormant(true));

		INSTANCE.addIndex(new CropIndex(/*ID*/11, /*Name*/"carrot", /*type*/2, /*time*/23, /*stages*/4, /*minGTemp*/8, /*minATemp*/0, /*nutrientUsage*/0.75f, TFCItems.seedsCarrot).setOutput1(TFCItems.carrot, 30.0f).setGoesDormant(true));

		INSTANCE.addIndex(new CropIndexPepper(/*ID*/12, /*Name*/"yellowbellpepper", /*type*/2, /*time*/18, /*stages*/6, /*minGTemp*/12, /*minATemp*/4, /*nutrientUsage*/1.2f, TFCItems.seedsYellowBellPepper).setOutput1(TFCItems.greenBellPepper, 6).setOutput2(TFCItems.yellowBellPepper, 6));
		INSTANCE.addIndex(new CropIndexPepper(/*ID*/13, /*Name*/"redbellpepper", /*type*/2, /*time*/18, /*stages*/6, /*minGTemp*/12, /*minATemp*/4, /*nutrientUsage*/1.2f, TFCItems.seedsRedBellPepper).setOutput1(TFCItems.greenBellPepper, 6).setOutput2(TFCItems.redBellPepper, 6));

		INSTANCE.addIndex(new CropIndex(/*ID*/14, /*Name*/"soybean", /*type*/1, /*time*/25, /*stages*/6, /*minGTemp*/8, /*minATemp*/0, /*nutrientUsage*/1.0f, TFCItems.seedsSoybean, new int[]{10,0,10}).setOutput1(TFCItems.soybean, 16));

		INSTANCE.addIndex(new CropIndex(/*ID*/15, /*Name*/"greenbean", /*type*/1, /*time*/24, /*stages*/6, /*minGTemp*/8, /*minATemp*/0, /*nutrientUsage*/1.0f, TFCItems.seedsGreenbean, new int[]{10,0,10}).setOutput1(TFCItems.greenbeans, 16));

		INSTANCE.addIndex(new CropIndex(/*ID*/16, /*Name*/"squash", /*type*/2, /*time*/33, /*stages*/6, /*minGTemp*/8, /*minATemp*/0, /*nutrientUsage*/0.9f, TFCItems.seedsSquash).setOutput1(TFCItems.squash, 16));

		INSTANCE.addIndex(new CropIndexJute(/*ID*/17, /*Name*/"jute", /*type*/1, /*time*/28, /*stages*/5, /*minGTemp*/10, /*minATemp*/5, /*nutrientUsage*/1.0f, TFCItems.seedsJute).setOutput1(TFCItems.jute, 2));

		INSTANCE.addIndex(new CropIndex(/*ID*/18, /*Name*/"sugarcane", /*type*/1, /*time*/96, /*stages*/7, /*minGTemp*/18, /*minATemp*/12, /*nutrientUsage*/0.25f, TFCItems.seedsSugarcane).setOutput1(TFCItems.sugarcane, 8));

		// If adding another crop, use the following spreadsheet to make sure nutrients on average don't hit 0 before crop reaches maturity:
		// https://www.dropbox.com/s/sznzc08nly1i6tt/Crop%20GrowthNutriDrain%20Calculator.xlsx?dl=0
	}
}
