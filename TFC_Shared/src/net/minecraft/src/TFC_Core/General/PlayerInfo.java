package net.minecraft.src.TFC_Core.General;

import net.minecraft.src.ItemStack;
import net.minecraft.src.TFC_Core.Items.ItemChisel;

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
