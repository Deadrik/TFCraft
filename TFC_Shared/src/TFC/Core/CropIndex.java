package TFC.Core;

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
    public float nutrientUsage;
    
    public int chanceForOutput1 = 100;
    public Item Output1;
    public int Output1Min;
    public int Output1Max;
    
    public int chanceForOutput2 = 100;
    public Item Output2;
    public int Output2Min;
    public int Output2Max;
    
    public boolean needsSunlight = true;
    
    
    
    public CropIndex(int ID, String name, int type, int growth, int stages, float minGTemp, float minATemp, float nUsage, Item out1, int out1Min, int out1Max, Item out2, int out2Min, int out2Max)
    {
        growthTime = growth;
        cycleType = type;
        cropName = name.toLowerCase();
        cropId = ID;
        numGrowthStages = stages;
        minGrowthTemp = minGTemp;
        minAliveTemp = minATemp;
        Output1 = out1;
        Output1Min = out1Min;
        Output1Max = out1Max;
        Output2 = out2;
        Output2Min = out2Min;
        Output2Max = out2Max;
        nutrientUsage = nUsage;
    }
    public CropIndex(int ID, String name, int type, int growth, int stages, float minGTemp, float minATemp, float nUsage)
    {
        growthTime = growth;
        cycleType = type;
        cropName = name.toLowerCase();
        cropId = ID;
        numGrowthStages = stages;
        minGrowthTemp = minGTemp;
        minAliveTemp = minATemp;
        nutrientUsage = nUsage;
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
    
    public ItemStack getOutput1()
    {
        ItemStack is = new ItemStack(Output1);
        Random R = new Random();
        if(R.nextInt(100) < chanceForOutput1)
        {
            is.stackSize = Output1Min + R.nextInt(Output1Max-Output1Min);
            if(is.stackSize > 0)
                return is;
        }
        return null;
    }
    public ItemStack getOutput2()
    {
        ItemStack is = new ItemStack(Output2);
        Random R = new Random();
        if(R.nextInt(100) < chanceForOutput2)
        {
            is.stackSize = Output2Min + R.nextInt(Output2Max-Output2Min);
            if(is.stackSize > 0)
                return is;
        }
        return null;
    }
    
    public CropIndex setNeedsSunlight(boolean b)
    {
        needsSunlight = b;
        return this;
    }
}
