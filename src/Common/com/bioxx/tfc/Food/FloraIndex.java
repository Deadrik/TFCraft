package com.bioxx.tfc.Food;

import java.util.Random;

import net.minecraft.item.ItemStack;


public class FloraIndex
{
	public String type;
	public int bloomStart;
	public int bloomFinish;
	public int harvestStart;
	public int harvestFinish;
	public int fruitHangTime = 1;
	public float minTemp = 0;
	public float maxTemp = 38;
	public float minBioTemp = 10;
	public float maxBioTemp = 30;
	public float minRain = 125;
	public float maxRain = 2000;
	public float minEVT = 0.25f;
	public float maxEVT = 16;
	public ItemStack output;

	/**
	 * n = ID String
	 * b1 = Bloom Start Month
	 * b2 = Bloom End Month
	 * h1 = Harvest Start Month
	 * h2 = Harvest End Month
	 */
	public FloraIndex(String n, int b1, int b2, int h1, int h2, ItemStack o)
	{
		type = n;
		bloomStart = b1;
		bloomFinish = b2;
		harvestStart = h1;
		harvestFinish = h2;
		output = o;
	}

	public FloraIndex(String n, int h1, int h2, ItemStack o)
	{
		type = n;
		bloomStart = 0;
		bloomFinish = 0;
		harvestStart = h1;
		harvestFinish = h2;
		output = o;
	}

	public FloraIndex setHangTime(int time)
	{
		fruitHangTime = time;
		return this;
	}

	public FloraIndex setBioTemp(float min, float max)
	{
		this.minBioTemp = min;
		this.maxBioTemp = max;
		return this;
	}

	public FloraIndex setRain(float min, float max)
	{
		this.minRain = min;
		this.maxRain = max;
		return this;
	}

	public FloraIndex setEVT(float min, float max)
	{
		this.minEVT = min;
		this.maxEVT = max;
		return this;
	}

	public ItemStack getOutput(Random R, int i)
	{
		ItemStack is = output.copy();
		is.stackSize += R.nextInt(i);
		return is;
	}

	public ItemStack getOutput()
	{
		ItemStack is = output.copy();
		return is;
	}

	public boolean inHarvest(int month)
	{
		if(month >= harvestStart && month <= harvestFinish)
		{
			return true;
		}

		return false;
	}

	public boolean inBloom(int month)
	{
		if(month >= bloomStart && month <= bloomFinish)
		{
			return true;
		}

		return false;
	}

	public FloraIndex setTemp(float min, float max)
	{
		minTemp = min;
		maxTemp = max;
		return this;
	}

}
