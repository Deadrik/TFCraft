package net.minecraft.src.TFC_Core.General;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import net.minecraft.src.TFC_Core.TFCSeasons;

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
        instance.addIndex(new FloraIndex("red apple", TFCSeasons.April,TFCSeasons.May,TFCSeasons.September,TFCSeasons.October,new ItemStack(Item.appleRed,1)));
        instance.addIndex(new FloraIndex("banana", TFCSeasons.April,TFCSeasons.May,TFCSeasons.September,TFCSeasons.October,new ItemStack(Item.appleRed,1)));
        instance.addIndex(new FloraIndex("olive", TFCSeasons.May,TFCSeasons.June,TFCSeasons.October,TFCSeasons.November,new ItemStack(Item.appleRed,1)));
    }
    
}
