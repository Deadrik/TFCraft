package TFC.Core;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;


public class HeatManager
{
    private static final HeatManager instance = new HeatManager();
    public static final HeatManager getInstance()
    {
        return instance;
    }

    private List heatList;
    
    private HeatManager()
    {
        heatList = new ArrayList();
    }

    public void addIndex(HeatIndex index)
    {
        heatList.add(index);
    }
    
    public HeatIndex findMatchingIndex(ItemStack input)
    {
        for (int k = 0; k < heatList.size(); k++)
        {
            HeatIndex tempIndex = (HeatIndex)heatList.get(k);
            if (tempIndex.matches(input))
            {
                return tempIndex;
            }
        }

        return null;
    }
}
