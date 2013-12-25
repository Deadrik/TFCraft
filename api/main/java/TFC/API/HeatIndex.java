package TFC.API;

import java.util.Random;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import TFC.API.Events.ItemMeltEvent;

public class HeatIndex
{
    public float specificHeat;
    public float meltTemp;

    private ItemStack output;
    private int outputMin;
    private int outputMax;

    private ItemStack morph;
    public ItemStack input;

    public HeatIndex(ItemStack in, float sh, float melt, ItemStack out)
    {
        input = in;
        specificHeat = sh;
        meltTemp = melt;
        outputMin = 0;
        outputMax = 0;
        output = out;
    }

    public HeatIndex(ItemStack in, HeatRaw raw, ItemStack out)
    {
        input = in;
        specificHeat = raw.specificHeat;
        meltTemp = raw.meltTemp;
        outputMin = 0;
        outputMax = 0;
        output = out;
    }
    
    public boolean hasOutput(){
    	return output != null;
    }

    public Item getOutputItem()
    {
        if(output!= null)
            return output.getItem();
        else return null;
    }

    public int getOutputDamage()
    {
        if(output!= null)
            return output.getItemDamage();
        else return 0;
    }

    public HeatIndex setMinMax(int min, int max)
    {
        outputMin = min;
        outputMax = max;
        return this;
    }
    
    public HeatIndex setMinMax(int amt)
    {
        outputMin = amt;
        outputMax = amt;
        return this;
    }

    public HeatIndex setMorph(ItemStack is)
    {
        morph = is;
        return this;
    }

    public ItemStack getMorph()
    {
        return morph;
    }

    public ItemStack getOutput(Random R)
    {
        int rand = 0;
        if(outputMax - outputMin > 0) 
        {
            rand = outputMin + R.nextInt(outputMax - outputMin);
            return new ItemStack(getOutputItem(),output.stackSize, 100-rand);
        }
        else 
        {
        	return new ItemStack(getOutputItem(),output.stackSize, outputMin);
        }
    }
    
    public ItemStack getOutput(ItemStack in, Random R)
    {
    	ItemStack is = getOutput(R);
        ItemMeltEvent eventMelt = new ItemMeltEvent(in, is);
		MinecraftForge.EVENT_BUS.post(eventMelt);
        return eventMelt.result;
    }

    public boolean matches(ItemStack is)
    {
        if(is != null)
        {
            boolean b = is.getItem().getHasSubtypes();
            if(is.getItem() != input.getItem())
                return false;
            else if(is.getItem().getHasSubtypes() && 
                    (input.getItemDamage() != 32767 && 
                    is.getItemDamage() != input.getItemDamage()))
                return false;
        }
        else return false;
        return true;
    }
}
