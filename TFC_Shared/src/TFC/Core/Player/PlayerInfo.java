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
	public int ChiselDetailZoom;
	public ItemStack knappingRockType;
	public INetworkManager networkManager;
	private long lastChange;

	public PlayerInfo(String name, INetworkManager nm)
	{
		Name = name;
		ChiselMode = 0;
		ChiselDetailZoom = 0;
		knappingRockType = null;
		networkManager = nm;
		lastChange = 0;
	}

	public void switchChiselMode()
	{
		boolean allowDetailed = false;
		if(lastChange+3 < TFC_Time.getTotalTicks())
		{
			ChiselMode = ChiselMode == 0 ? 1 : ChiselMode == 1 ? 2 : ChiselMode == 2 && allowDetailed ? 3 : ChiselMode == 3 && allowDetailed ? 4 : 0;
			lastChange = TFC_Time.getTotalTicks();
		}
	}

	public void switchIncreaseDetailZoom()
	{
		if(lastChange+3 < TFC_Time.getTotalTicks())
		{
			if(ChiselDetailZoom < 4)
				ChiselDetailZoom++ ;
			else
				ChiselDetailZoom = 0;
			lastChange = TFC_Time.getTotalTicks();
		}
	}

	public void switchDecreaseDetailZoom()
	{
		if(lastChange+3 < TFC_Time.getTotalTicks())
		{
			if(ChiselDetailZoom > 0)
				ChiselDetailZoom-- ;
			else
				ChiselDetailZoom = 4;
			lastChange = TFC_Time.getTotalTicks();
		}
	}

}
