package com.bioxx.tfc.Food;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.Core.TFC_Climate;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.TileEntities.TECrop;
import com.bioxx.tfc.api.Util.Helper;

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
	public ItemStack getOutput1(TECrop crop)
	{
		if(Output1 != null)
		{
			ItemStack is = new ItemStack(Output1);
			Random R = new Random();
			if(R.nextInt(100) < chanceForOutput1)
			{
				ItemFoodTFC.createTag(is, getWeight(Output1Avg, R));
				addFlavorProfile(crop, is);
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
				addFlavorProfile(crop, is);
				return is;
			}
		}
		return null;
	}

	private Random getGrowthRand(TECrop te)
	{
		Block farmBlock = te.getWorldObj().getBlock(te.xCoord, te.yCoord-1, te.zCoord);
		Block underFarmBlock = te.getWorldObj().getBlock(te.xCoord, te.yCoord-2, te.zCoord);
		if(!TFC_Core.isSoil(farmBlock))
		{
			int soilType1 = (farmBlock == TFCBlocks.tilledSoil ? te.getWorldObj().getBlockMetadata(te.xCoord, te.yCoord-1, te.zCoord) : 
				te.getWorldObj().getBlockMetadata(te.xCoord, te.yCoord-1, te.zCoord)+16);
			int soilType2 = (farmBlock == TFCBlocks.Dirt ? te.getWorldObj().getBlockMetadata(te.xCoord, te.yCoord-2, te.zCoord)*2 : 
				farmBlock == TFCBlocks.Dirt2 ? (te.getWorldObj().getBlockMetadata(te.xCoord, te.yCoord-2, te.zCoord)+16)*2 : 0);

			int ph = TFC_Climate.getCacheManager(te.getWorldObj()).getPHLayerAt(te.xCoord, te.zCoord).data1*100;
			int drainage = 0;

			for(int y = 2; y < 8; y++)
			{
				if(TFC_Core.isGravel(te.getWorldObj().getBlock(te.xCoord, te.yCoord-y, te.zCoord)))
				{
					drainage++;
				}
			}
			drainage *= 1000;

			return new Random(soilType1+soilType2+ph+drainage);
		}
		return null;
	}

	private void addFlavorProfile(TECrop te, ItemStack outFood)
	{
		Random R = getGrowthRand(te);
		if(R != null)
		{
			outFood.getTagCompound().setInteger("tasteSweetMod", R.nextInt(16)-8);
			outFood.getTagCompound().setInteger("tasteSourMod", R.nextInt(16)-8);
			outFood.getTagCompound().setInteger("tasteSaltyMod", R.nextInt(16)-8);
			outFood.getTagCompound().setInteger("tasteBitterMod", R.nextInt(16)-8);
			outFood.getTagCompound().setInteger("tasteUmamiMod", R.nextInt(16)-8);
		}
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
