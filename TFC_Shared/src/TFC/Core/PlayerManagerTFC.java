package TFC.Core;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.src.EntityPlayer;

public class PlayerManagerTFC
{
    public List<PlayerInfo> Players;
    
    private static final PlayerManagerTFC instance = new PlayerManagerTFC();
    public static final PlayerManagerTFC getInstance()
    {
        return instance;
    }

    
    private PlayerManagerTFC()
    {
        Players = new ArrayList();
    }
    
    public PlayerInfo getPlayerInfoFromPlayer(EntityPlayer player)
    {
        for(PlayerInfo pi : Players)
        {
            if(pi.Name.equals(player.username))
                return pi;
        }
        return null;
    }
}
