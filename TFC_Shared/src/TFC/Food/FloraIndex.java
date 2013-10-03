package TFC.Food;

import java.util.Random;

import net.minecraft.item.ItemStack;


public class FloraIndex
{
	public String type;
	public int bloomStart;
	public int bloomFinish;
	public int harvestStart;
	public int harvestFinish;
	public int fruitHangTime;
	public float minTemp;
	public float maxTemp;
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
		minTemp = 0F;
		maxTemp = 43;
		fruitHangTime = 1;
	}

	public FloraIndex setHangTime(int time)
	{
		fruitHangTime = time;
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
