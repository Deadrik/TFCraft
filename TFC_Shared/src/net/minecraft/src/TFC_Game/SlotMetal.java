package net.minecraft.src.TFC_Game;

import net.minecraft.src.*;

public class SlotMetal extends Slot

{
	public SlotMetal(IInventory iinventory, int i, int j, int k)
	{
		super(iinventory, i, j, k);

	}

	public boolean isItemValid(ItemStack itemstack)
	{    	

		if(itemstack.itemID == mod_TFC_Game.terraMeltedBismuth.shiftedIndex || itemstack.itemID == mod_TFC_Game.terraMeltedBismuthBronze.shiftedIndex || 
				itemstack.itemID == mod_TFC_Game.terraMeltedBlackBronze.shiftedIndex || itemstack.itemID == mod_TFC_Game.terraMeltedBlackSteel.shiftedIndex || 
				itemstack.itemID == mod_TFC_Game.terraMeltedBlueSteel.shiftedIndex || itemstack.itemID == mod_TFC_Game.terraMeltedBrass.shiftedIndex || 
				itemstack.itemID == mod_TFC_Game.terraMeltedBronze.shiftedIndex || itemstack.itemID == mod_TFC_Game.terraMeltedCopper.shiftedIndex || 
				itemstack.itemID == mod_TFC_Game.terraMeltedGold.shiftedIndex || itemstack.itemID == mod_TFC_Game.terraMeltedWroughtIron.shiftedIndex || 
				itemstack.itemID == mod_TFC_Game.terraMeltedLead.shiftedIndex || itemstack.itemID == mod_TFC_Game.terraMeltedNickel.shiftedIndex || 
				itemstack.itemID == mod_TFC_Game.terraMeltedPigIron.shiftedIndex || itemstack.itemID == mod_TFC_Game.terraMeltedPlatinum.shiftedIndex || 
				itemstack.itemID == mod_TFC_Game.terraMeltedRedSteel.shiftedIndex || itemstack.itemID == mod_TFC_Game.terraMeltedRoseGold.shiftedIndex || 
				itemstack.itemID == mod_TFC_Game.terraMeltedSilver.shiftedIndex || itemstack.itemID == mod_TFC_Game.terraMeltedSterlingSilver.shiftedIndex || 
				itemstack.itemID == mod_TFC_Game.terraMeltedTin.shiftedIndex || itemstack.itemID == mod_TFC_Game.terraMeltedZinc.shiftedIndex || 
				itemstack.itemID == mod_TFC_Game.terraMeltedHCBlueSteel.shiftedIndex || itemstack.itemID == mod_TFC_Game.terraMeltedWeakBlueSteel.shiftedIndex || 
				itemstack.itemID == mod_TFC_Game.terraMeltedHCRedSteel.shiftedIndex || itemstack.itemID == mod_TFC_Game.terraMeltedWeakRedSteel.shiftedIndex || 
				itemstack.itemID == mod_TFC_Game.terraMeltedHCSteel.shiftedIndex || itemstack.itemID == mod_TFC_Game.terraMeltedWeakSteel.shiftedIndex || 
				itemstack.itemID == mod_TFC_Game.terraMeltedHCBlackSteel.shiftedIndex || itemstack.itemID == mod_TFC_Game.terraCeramicMold.shiftedIndex)
		{
			return true;
		}
		return false;
	}

	public void onPickupFromSlot(ItemStack itemstack)
	{
		super.onPickupFromSlot(itemstack);
	}
}
