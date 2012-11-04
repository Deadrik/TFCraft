package TFC.Food;

import java.util.ArrayList;
import java.util.List;

import TFC.TFCItems;
import TFC.Core.TFC_Time;


import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class FloraManager
{
    private static final FloraManager instance = new FloraManager();
    private List floraList;
    
    public static final FloraManager getInstance()
    {
        return instance;
    }
    
    public FloraManager()
    {
        floraList = new ArrayList();
    }
    
    public void addIndex(FloraIndex index)
    {
        floraList.add(index);
    }
    
    public FloraIndex findMatchingIndex(String input)
    {
        for (int k = 0; k < floraList.size(); k++)
        {
            FloraIndex tempIndex = (FloraIndex)floraList.get(k);
            if (tempIndex.type.equalsIgnoreCase(input))
            {
                return tempIndex;
            }
        }

        return null;
    }
    
    
    static
    {
        instance.addIndex(new FloraIndex("red apple", TFC_Time.April,TFC_Time.May,TFC_Time.November,TFC_Time.October,new ItemStack(TFCItems.RedApple,1)));
        instance.addIndex(new FloraIndex("banana", TFC_Time.April,TFC_Time.May,TFC_Time.September,TFC_Time.September,new ItemStack(TFCItems.Banana,1)));
        instance.addIndex(new FloraIndex("orange", TFC_Time.February,TFC_Time.April,TFC_Time.November,TFC_Time.November,new ItemStack(TFCItems.Orange,1)));
        instance.addIndex(new FloraIndex("green apple", TFC_Time.May,TFC_Time.June,TFC_Time.October,TFC_Time.November,new ItemStack(TFCItems.GreenApple,1)));
        instance.addIndex(new FloraIndex("lemon", TFC_Time.May,TFC_Time.June,TFC_Time.August,TFC_Time.August,new ItemStack(TFCItems.Lemon,1)));
        instance.addIndex(new FloraIndex("olive", TFC_Time.June,TFC_Time.June,TFC_Time.October,TFC_Time.October,new ItemStack(TFCItems.Olive,3)));
        instance.addIndex(new FloraIndex("cherry", TFC_Time.April,TFC_Time.April,TFC_Time.June,TFC_Time.June,new ItemStack(TFCItems.Cherry,3)));
        instance.addIndex(new FloraIndex("peach", TFC_Time.April,TFC_Time.May,TFC_Time.September,TFC_Time.September,new ItemStack(TFCItems.Peach,1)));
        instance.addIndex(new FloraIndex("plum", TFC_Time.May,TFC_Time.June,TFC_Time.July,TFC_Time.August,new ItemStack(TFCItems.Plum,1)));
        //instance.addIndex(new FloraIndex("cacao", TFCSeasons.June,TFCSeasons.June,TFCSeasons.October,TFCSeasons.October,new ItemStack(TFCItems.Cacao,1)));
    }
    
}
