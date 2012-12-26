package TFC.Core.Player;

import TFC.Core.TFC_Time;
import TFC.Items.ItemChisel;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.crash.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.packet.*;
import net.minecraft.pathfinding.*;
import net.minecraft.potion.*;
import net.minecraft.server.*;
import net.minecraft.stats.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.village.*;
import net.minecraft.world.*;
import net.minecraft.world.biome.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.gen.feature.*;

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
