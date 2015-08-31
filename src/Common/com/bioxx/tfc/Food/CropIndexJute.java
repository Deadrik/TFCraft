package com.bioxx.tfc.Food;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.bioxx.tfc.TileEntities.TECrop;

public class CropIndexJute extends CropIndex
{

	public CropIndexJute(int id, String name, int type, int growth, int stages, float minGTemp, float minATemp, Item seed)
	{
		super(id,name,type,growth,stages,minGTemp,minATemp, seed);
	}
	public CropIndexJute(int id, String name, int type, int growth, int stages, float minGTemp, float minATemp, float nutrientUsageMultiplier, Item seed)
	{
		super(id,name,type,growth,stages,minGTemp,minATemp, seed);
		nutrientUsageMult = nutrientUsageMultiplier;
	}
	public CropIndexJute(int id, String name, int type, int growth, int stages, float minGTemp, float minATemp, float nutrientUsageMultiplier, Item seed, int[] nutriRestore)
	{
		super(id,name,type,growth,stages,minGTemp,minATemp, seed);
		nutrientExtraRestore = nutriRestore.clone();
		nutrientUsageMult = nutrientUsageMultiplier;
	}

	@Override
	public ItemStack getOutput1(TECrop crop)
	{
		if (output1 != null && crop.growth >= 5)
		{
			return new ItemStack(output1, (int) this.output1Avg);
		}
		return null;
	}
	@Override
	public ItemStack getOutput2(TECrop crop)
	{
		return null;
	}
}
