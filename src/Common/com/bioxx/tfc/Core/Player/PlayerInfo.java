package com.bioxx.tfc.Core.Player;

import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import com.bioxx.tfc.Core.Player.SkillStats.SkillRank;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.Tools.ChiselManager;

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
	private long lastChange;

	public short moldTransferTimer = 1000;

	//Clientside only variables
	public boolean guishowFoodRestoreAmount = false;
	public float guiFoodRestoreAmount = 0;
	public boolean[] knappingInterface;

	public SkillStats tempSkills;
	public ItemStack[] tempEquipment = new ItemStack[TFC_Core.getExtraEquipInventorySize()];

	public PlayerInfo(String name, UUID uuid)
	{
		Name = name;
		PlayerUUID = uuid;
		ChiselMode = 0;
		specialCraftingType = null;
		specialCraftingTypeAlternate = null;
		lastChange = 0;
		hoeMode = 0;
		knappingInterface = new boolean[25];
	}

	public void switchHoeMode(EntityPlayer player)
	{
		final int MODE_NORMAL = 0; final int MODE_NUTRIENT = 1; final int MODE_WATER= 2; final int MODE_HARVEST = 3;
		SkillRank Agrank = TFC_Core.getSkillStats(player).getSkillRank(Global.SKILL_AGRICULTURE);
		/*if(Agrank != SkillRank.Expert && Agrank != SkillRank.Master)
			return;*/
		if(lastChange+3 < TFC_Time.getTotalTicks())
		{
			boolean isMetalHoe = true;

			if(player.getCurrentEquippedItem() != null &&
					(player.getCurrentEquippedItem().getItem() == TFCItems.IgInHoe ||
							player.getCurrentEquippedItem().getItem() == TFCItems.IgExHoe ||
									player.getCurrentEquippedItem().getItem() == TFCItems.SedHoe ||
											player.getCurrentEquippedItem().getItem() == TFCItems.MMHoe))
			{
				isMetalHoe = false;
			}
			
			hoeMode = hoeMode == 3 ? 0 : ++hoeMode;
			if (hoeMode == MODE_NUTRIENT && (!isMetalHoe || (isMetalHoe && Agrank != SkillRank.Expert && Agrank != SkillRank.Master)))
				hoeMode++;

			lastChange = TFC_Time.getTotalTicks();
		}
	}

	public void switchChiselMode()
	{
		if(lastChange+3 < TFC_Time.getTotalTicks())
		{
			//Bump ChiselMode on switchChiselMode,
			//reset to zero when the last mode is reached.
			if(ChiselMode == ChiselManager.getInstance().getSize() - 1)
			{
				ChiselMode = 0;
			}
			else
			{
				ChiselMode = ++ChiselMode;
			}
			lastChange = TFC_Time.getTotalTicks();
		}
	}

	//Set the ChiselMode directly on the server side.
	public void setChiselMode(byte chiselMode){
		ChiselMode = chiselMode;
	}

	public boolean lockMatches(int x, int y, int z)
	{
		if((lockX == -9999999 || lockX == x) && (lockY == -9999999 || lockY == y) && (lockZ == -9999999 || lockZ == z))
			return true;
		return false;
	}
}
