package TFC.Core.Player;

import TFC.Items.ItemChisel;
import net.minecraft.src.INetworkManager;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ServerPlayerBase;

public class PlayerInfo
{
    public String Name;
    public int ChiselMode;
    public ItemStack knappingRockType;
    public INetworkManager networkManager;
    
    public PlayerInfo(String name, INetworkManager nm)
    {
        Name = name;
        ChiselMode = 0;
        knappingRockType = null;
        networkManager = nm;
    }
    
    public void switchChiselMode()
    {
        ChiselMode = ChiselMode == 0 ? 1 : ChiselMode == 1 ? 2 /*: ChiselMode == 2 ? 3*/ : 0;
    }

}
