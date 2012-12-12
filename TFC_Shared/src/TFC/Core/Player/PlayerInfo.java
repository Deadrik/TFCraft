package TFC.Core.Player;

import TFC.Core.TFC_Time;
import TFC.Items.ItemChisel;
import net.minecraft.src.INetworkManager;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ServerPlayerBase;

public class PlayerInfo
{
	public String Name;
	public int ChiselMode;
	public int hoeMode;

	public ItemStack knappingRockType;
	public INetworkManager networkManager;
	private long lastChange;

	public PlayerInfo(String name, INetworkManager nm)
	{
		Name = name;
		ChiselMode = 0;
		knappingRockType = null;
		networkManager = nm;
		lastChange = 0;
		hoeMode = 0;
	}
	
	public void switchHoeMode()
	{
		if(lastChange+3 < TFC_Time.getTotalTicks())
		{
			hoeMode = hoeMode == 3 ? 0 : 
				++hoeMode;
			lastChange = TFC_Time.getTotalTicks();
		}
	}

	public void switchChiselMode()
	{
		boolean allowDetailed = true;
		if(lastChange+3 < TFC_Time.getTotalTicks())
		{
			ChiselMode = ChiselMode == 0 ? 1 : ChiselMode == 1 ? 2 : ChiselMode == 2 && allowDetailed ? 3 : 0;
			lastChange = TFC_Time.getTotalTicks();
		}
	}
}
