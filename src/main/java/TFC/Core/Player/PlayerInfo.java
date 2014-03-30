package TFC.Core.Player;

import java.util.UUID;

import net.minecraft.item.ItemStack;
import net.minecraft.network.NetworkManager;
import TFC.Core.TFC_Time;

public class PlayerInfo
{
	public String Name;
	public UUID PlayerUUID;
	public byte ChiselMode;
	public int hoeMode;

	public int lockX = -9999999;
	public int lockY = -9999999;
	public int lockZ = -9999999;

	public ItemStack specialCraftingType;
	public ItemStack specialCraftingTypeAlternate;

	public NetworkManager networkManager;
	private long lastChange;

	public short moldTransferTimer = 1000;

	//Clientside only variables
	public boolean guishowFoodRestoreAmount = false;
	public float guiFoodRestoreAmount = 0;
	public boolean[] knappingInterface;

	public SkillStats tempSkills;

	public PlayerInfo(String name, UUID uuid) //, NetworkManager nm)
	{
		Name = name;
		PlayerUUID = uuid;
		ChiselMode = 0;
		specialCraftingType = null;
		specialCraftingTypeAlternate = null;
//		networkManager = nm;
		lastChange = 0;
		hoeMode = 0;
		knappingInterface = new boolean[25];
	}

	public void switchHoeMode()
	{
		if(lastChange+3 < TFC_Time.getTotalTicks())
		{
			hoeMode = hoeMode == 3 ? 0 : ++hoeMode;
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
