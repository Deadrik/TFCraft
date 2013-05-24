package TFC.Core.Player;

import net.minecraft.item.ItemStack;
import net.minecraft.network.INetworkManager;
import TFC.Core.TFC_Time;

public class PlayerInfo
{
	public String Name;
	public byte ChiselMode;
	public int hoeMode;
	
	public int lockX = -9999999;
	public int lockY = -9999999;
	public int lockZ = -9999999;

	public ItemStack knappingRockType;
	
	public INetworkManager networkManager;
	private long lastChange;
	
	public boolean shiftPressed = false;

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
		boolean allowSuperDetailed = false;
		if(lastChange+3 < TFC_Time.getTotalTicks())
		{
			ChiselMode = (byte) (ChiselMode == 0 ? 1 : ChiselMode == 1 ? 2 : 
				ChiselMode == 2 && allowDetailed ? 3 : 
				ChiselMode == 3 && allowSuperDetailed ? 4 : 0);
			lastChange = TFC_Time.getTotalTicks();
		}
	}
	
	public boolean lockMatches(int x, int y, int z)
	{
		if((lockX == -9999999 || lockX == x) && (lockY == -9999999 || lockY == y) && (lockZ == -9999999 || lockZ == z))
			return true;
		
		return false;
	}
}
