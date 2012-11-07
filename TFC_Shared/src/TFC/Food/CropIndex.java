package TFC.Food;

import java.util.Random;

import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

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
    public int Output1Min;
    public int Output1Max;

    public int chanceForOutput2 = 100;
    public Item Output2;
    public int Output2Min;
    public int Output2Max;

    public boolean needsSunlight = true;
    public float waterUsageMult = 1;
    public Item seedItem;

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

    public CropIndex setOutput1(Item o, int oMin, int oMax)
    {
        Output1 = o;
        Output1Min = oMin;
        Output1Max = oMax;
        return this;
    }
    public CropIndex setOutput2(Item o, int oMin, int oMax)
    {
        Output2 = o;
        Output2Min = oMin;
        Output2Max = oMax;
        return this;
    }
    public CropIndex setOutput1Chance(Item o, int oMin, int oMax, int chance)
    {
        Output1 = o;
        Output1Min = oMin;
        Output1Max = oMax;
        chanceForOutput1 = chance;
        return this;
    }
    public CropIndex setOutput2Chance(Item o, int oMin, int oMax, int chance)
    {
        Output2 = o;
        Output2Min = oMin;
        Output2Max = oMax;
        chanceForOutput2 = chance;
        return this;
    }  
    public ItemStack getOutput1(float stage)
    {
        if(Output1 != null)
        {
            ItemStack is = new ItemStack(Output1);
            Random R = new Random();
            if(R.nextInt(100) < chanceForOutput1)
            {
                int added = 0;
                if(Output1Max > Output1Min)
                    added = R.nextInt(Output1Max-Output1Min);
                is.stackSize = Output1Min + added;
                if(is.stackSize > 0)
                    return is;
            }
        }
        return null;
    }
    public ItemStack getOutput2(float stage)
    {
        if(Output2 != null)
        {
            ItemStack is = new ItemStack(Output2);
            Random R = new Random();
            if(R.nextInt(100) < chanceForOutput2)
            {
                int added = 0;
                if(Output2Max > Output2Min)
                    added = R.nextInt(Output2Max-Output2Min);
                is.stackSize = Output2Min + added;
                if(is.stackSize > 0)
                    return is;
            }
        }
        return null;
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
