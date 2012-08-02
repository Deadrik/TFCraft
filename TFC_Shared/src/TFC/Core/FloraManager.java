package TFC.Core;

import java.util.ArrayList;
import java.util.List;


import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TFCItems;
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
        instance.addIndex(new FloraIndex("red apple", TFCSeasons.April,TFCSeasons.May,TFCSeasons.November,TFCSeasons.October,new ItemStack(TFCItems.RedApple,1)));
        instance.addIndex(new FloraIndex("banana", TFCSeasons.April,TFCSeasons.May,TFCSeasons.September,TFCSeasons.September,new ItemStack(TFCItems.Banana,1)));
        instance.addIndex(new FloraIndex("orange", TFCSeasons.February,TFCSeasons.April,TFCSeasons.November,TFCSeasons.November,new ItemStack(TFCItems.Orange,1)));
        instance.addIndex(new FloraIndex("green apple", TFCSeasons.May,TFCSeasons.June,TFCSeasons.October,TFCSeasons.November,new ItemStack(TFCItems.GreenApple,1)));
        instance.addIndex(new FloraIndex("lemon", TFCSeasons.May,TFCSeasons.June,TFCSeasons.August,TFCSeasons.August,new ItemStack(TFCItems.Lemon,1)));
        instance.addIndex(new FloraIndex("olive", TFCSeasons.June,TFCSeasons.June,TFCSeasons.October,TFCSeasons.October,new ItemStack(TFCItems.Olive,3)));
        instance.addIndex(new FloraIndex("cherry", TFCSeasons.April,TFCSeasons.April,TFCSeasons.June,TFCSeasons.June,new ItemStack(TFCItems.Cherry,3)));
        instance.addIndex(new FloraIndex("peach", TFCSeasons.April,TFCSeasons.May,TFCSeasons.September,TFCSeasons.September,new ItemStack(TFCItems.Peach,1)));
        instance.addIndex(new FloraIndex("plum", TFCSeasons.May,TFCSeasons.June,TFCSeasons.July,TFCSeasons.August,new ItemStack(TFCItems.Plum,1)));
        //instance.addIndex(new FloraIndex("cacao", TFCSeasons.June,TFCSeasons.June,TFCSeasons.October,TFCSeasons.October,new ItemStack(TFCItems.Cacao,1)));
    }
    
}
