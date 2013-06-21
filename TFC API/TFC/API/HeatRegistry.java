package TFC.API;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;


public class HeatRegistry
{
    private static final HeatRegistry instance = new HeatRegistry();
    public static final HeatRegistry getInstance()
    {
        return instance;
    }

    private List heatList;
    
    private HeatRegistry()
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
