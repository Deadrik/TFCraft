package TFC.Core;

import java.util.Random;

import net.minecraft.src.ItemStack;

public class FloraIndex
{
    public String type;
    public int BloomStart;
    public int BloomFinish;
    public int HarvestStart;
    public int HarvestFinish;
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
        BloomStart = b1;
        BloomFinish = b2;
        HarvestStart = h1;
        HarvestFinish = h2;
        output = o;
        minTemp = 0F;
        maxTemp = 43;
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
        if(month >= HarvestStart && month <= HarvestFinish)
            return true;
        
        return false;
    }
    
    public boolean inBloom(int month)
    {
        if(month >= BloomStart && month <= BloomFinish)
            return true;
        
        return false;
    }
    
    public FloraIndex setTemp(float min, float max)
    {
        minTemp = min;
        maxTemp = max;
        return this;
    }
    
}
