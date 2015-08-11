package com.bioxx.tfc.Food;

import java.util.Random;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.bioxx.tfc.TileEntities.TECrop;

public class CropIndexPepper extends CropIndex
{
	public CropIndexPepper(int ID, String name, int type, int growth, int stages, float minGTemp, float minATemp, Item seed)
	{
		super(ID,name,type,growth,stages,minGTemp,minATemp, seed);
	}
	public CropIndexPepper(int ID, String name, int type, int growth, int stages, float minGTemp, float minATemp, float nutrientUsageMultiplier, Item seed)
	{
		super(ID,name,type,growth,stages,minGTemp,minATemp, seed);
		nutrientUsageMult = nutrientUsageMultiplier;
	}
	public CropIndexPepper(int ID, String name, int type, int growth, int stages, float minGTemp, float minATemp, float nutrientUsageMultiplier, Item seed, int[] nutriRestore)
	{
		super(ID,name,type,growth,stages,minGTemp,minATemp, seed);
		nutrientExtraRestore = nutriRestore;
		nutrientUsageMult = nutrientUsageMultiplier;
	}

	@Override
	public ItemStack getOutput1(TECrop crop)
	{
		if(Output1 != null && crop.growth >= 5 && crop.growth < 6)
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
	@Override
	public ItemStack getOutput2(TECrop crop)
	{
		if(Output2 != null && crop.growth >= 6)
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
}
