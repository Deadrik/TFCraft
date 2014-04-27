package com.bioxx.tfc.Food;

import com.bioxx.tfc.TileEntities.TECrop;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CropIndexJute extends CropIndex
{

	public CropIndexJute(int ID, String name, int type, int growth, int stages, float minGTemp, float minATemp, Item seed)
	{
		super(ID,name,type,growth,stages,minGTemp,minATemp, seed);
	}
	public CropIndexJute(int ID, String name, int type, int growth, int stages, float minGTemp, float minATemp, float nutrientUsageMultiplier, Item seed)
	{
		super(ID,name,type,growth,stages,minGTemp,minATemp, seed);
		nutrientUsageMult = nutrientUsageMultiplier;
	}
	public CropIndexJute(int ID, String name, int type, int growth, int stages, float minGTemp, float minATemp, float nutrientUsageMultiplier, Item seed, int[] nutriRestore)
	{
		super(ID,name,type,growth,stages,minGTemp,minATemp, seed);
		nutrientExtraRestore = nutriRestore;
		nutrientUsageMult = nutrientUsageMultiplier;
	}

	@Override
	public ItemStack getOutput1(TECrop crop)
	{
		if (Output1 != null && crop.growth >= 5)
		{
			ItemStack is = new ItemStack(Output1, (int)this.Output1Avg);
			return is;
		}
		return null;
	}
	@Override
	public ItemStack getOutput2(TECrop crop)
	{
		return null;
	}
}
