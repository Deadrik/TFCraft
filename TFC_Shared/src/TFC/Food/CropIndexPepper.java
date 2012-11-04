package TFC.Food;

import java.util.Random;

import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public class CropIndexPepper extends CropIndex
{

    public CropIndexPepper(int ID, String name, int type, int growth, int stages, float minGTemp, float minATemp)
    {
        super(ID,name,type,growth,stages,minGTemp,minATemp);
    }
    public CropIndexPepper(int ID, String name, int type, int growth, int stages, float minGTemp, float minATemp, float nutrientUsageMultiplier)
    {
    	super(ID,name,type,growth,stages,minGTemp,minATemp);
        nutrientUsageMult = nutrientUsageMultiplier;
    }
    public CropIndexPepper(int ID, String name, int type, int growth, int stages, float minGTemp, float minATemp, float nutrientUsageMultiplier, int[] nutriRestore)
    {
    	super(ID,name,type,growth,stages,minGTemp,minATemp);
        nutrientExtraRestore = nutriRestore;
        nutrientUsageMult = nutrientUsageMultiplier;
    }

    public ItemStack getOutput1(float stage)
    {
        if(Output1 != null && stage >= 5 && stage < 6)
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
        if(Output2 != null && stage >= 6)
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
}
