package com.bioxx.tfc.Food;

import java.util.Random;

import com.bioxx.tfc.TileEntities.TECrop;
import com.bioxx.tfc.api.Util.Helper;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CropIndex
{
	public int growthTime;
	public String cropName;
	public int cycleType;
	public int cropId;
	public int numGrowthStages;
	public float minGrowthTemp;
	public float minAliveTemp;
	public float nutrientUsageMult;
	public int[] nutrientExtraRestore;
	public boolean dormantInFrost;
	public int maxLifespan = -1;

	public int chanceForOutput1 = 100;
	public Item Output1;
	public float Output1Avg;

	public int chanceForOutput2 = 100;
	public Item Output2;
	public float Output2Avg;

	public boolean needsSunlight = true;
	public float waterUsageMult = 1;
	public Item seedItem;
	public boolean isMultiUseCrop = false;

	public CropIndex(int ID, String name, int type, int growth, int stages, float minGTemp, float minATemp, Item seed)
	{
		growthTime = growth;
		cycleType = type;
		cropName = name.toLowerCase();
		cropId = ID;
		numGrowthStages = stages;
		minGrowthTemp = minGTemp;
		minAliveTemp = minATemp;
		nutrientExtraRestore = new int[]{0,0,0};
		nutrientUsageMult = 1.0f;
		dormantInFrost = false;
		seedItem = seed;
	}
	public CropIndex(int ID, String name, int type, int growth, int stages, float minGTemp, float minATemp, float nutrientUsageMultiplier, Item seed)
	{
		this(ID,name,type,growth,stages,minGTemp,minATemp,seed);
		nutrientUsageMult = nutrientUsageMultiplier;
	}
	public CropIndex(int ID, String name, int type, int growth, int stages, float minGTemp, float minATemp, float nutrientUsageMultiplier, Item seed, int[] nutriRestore)
	{
		this(ID,name,type,growth,stages,minGTemp,minATemp,seed);
		nutrientExtraRestore = nutriRestore;
		nutrientUsageMult = nutrientUsageMultiplier;
	}

	public CropIndex setMultiUse()
	{
		this.isMultiUseCrop = true;
		return this;
	}

	public CropIndex setOutput1(Item o, float oAvg)
	{
		Output1 = o;
		Output1Avg = oAvg;
		return this;
	}
	public CropIndex setOutput2(Item o, float oAvg)
	{
		Output2 = o;
		Output2Avg = oAvg;
		return this;
	}
	public CropIndex setOutput1Chance(Item o, float oAvg, int chance)
	{
		Output1 = o;
		Output1Avg = oAvg;
		chanceForOutput1 = chance;
		return this;
	}
	public CropIndex setOutput2Chance(Item o, float oAvg, int chance)
	{
		Output2 = o;
		Output2Avg = oAvg;
		chanceForOutput2 = chance;
		return this;
	}  
	public ItemStack getOutput1( TECrop crop)
	{
		if(Output1 != null)
		{
			ItemStack is = new ItemStack(Output1);
			Random R = new Random();
			if(R.nextInt(100) < chanceForOutput1)
			{
				ItemFoodTFC.createTag(is, getWeight(Output1Avg, R));
				return is;
			}
		}
		return null;
	}
	public ItemStack getOutput2(TECrop crop)
	{
		if(Output2 != null)
		{
			ItemStack is = new ItemStack(Output2);
			Random R = new Random();
			if(R.nextInt(100) < chanceForOutput2)
			{
				ItemFoodTFC.createTag(is, getWeight(Output2Avg, R));
				return is;
			}
		}
		return null;
	}

	public static float getWeight(float average, Random R)
	{
		float weight = average + (average * ((10*R.nextFloat())-5)/100);
		return Helper.roundNumber(weight, 10);
	}

	public CropIndex setNeedsSunlight(boolean b)
	{
		needsSunlight = b;
		return this;
	}

	public CropIndex setWaterUsage(float m)
	{
		waterUsageMult = m;
		return this;
	}

	public CropIndex setGoesDormant(boolean b)
	{
		dormantInFrost = b;
		return this;
	}

	public ItemStack getSeed()
	{
		return new ItemStack(seedItem, 1);
	}

	public void onCropGrow(float stage){}
}
