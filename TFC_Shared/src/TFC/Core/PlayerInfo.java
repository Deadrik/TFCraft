package TFC.Core;

import TFC.Items.ItemChisel;
import net.minecraft.src.ItemStack;

public class PlayerInfo
{
    public String Name;
    public int ChiselMode;
    public ItemStack knappingRockType;
    
    public PlayerInfo(String name)
    {
        Name = name;
        ChiselMode = 0;
        knappingRockType = null;
    }
    
    public void switchChiselMode()
    {
        ChiselMode = ChiselMode == 0 ? 1 : ChiselMode == 1 ? 2 : 0;
        if(TFCSettings.enableDebugMode)
            System.out.println(ChiselMode);
    }
}
