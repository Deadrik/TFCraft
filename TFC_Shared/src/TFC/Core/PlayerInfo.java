package TFC.Core;

import TFC.Items.ItemChisel;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NetworkManager;

public class PlayerInfo
{
    public String Name;
    public int ChiselMode;
    public ItemStack knappingRockType;
    public NetworkManager networkManager;
    
    public PlayerInfo(String name, NetworkManager nm)
    {
        Name = name;
        ChiselMode = 0;
        knappingRockType = null;
        networkManager = nm;
    }
    
    public void switchChiselMode()
    {
        ChiselMode = ChiselMode == 0 ? 1 : ChiselMode == 1 ? 2 : 0;
        if(TFC_Settings.enableDebugMode)
            System.out.println(ChiselMode);
    }
}
